package org.cgm.service;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

import org.cgm.core.exceptions.patient.PatientNotFound;
import org.cgm.core.exceptions.visite.VisiteNotFound;
import org.cgm.core.mappers.PatientMapper;
import org.cgm.core.mappers.VisiteMapper;
import org.cgm.core.models.visite.Visite;
import org.cgm.core.models.visite.VisiteAddRequest;
import org.cgm.core.models.visite.VisiteCreationRequest;
import org.cgm.core.models.visite.VisiteUpdateRequest;
import org.cgm.data.repository.PatientRepository;
import org.cgm.data.repository.VisiteRepository;

@ApplicationScoped
public class VisiteService {

    @Inject
    VisiteRepository visiteRepository;

    @Inject
    PatientRepository patientRepository;

    @Inject
    VisiteMapper visiteMapper;

    @Inject
    PatientMapper patientMapper;

    public Uni<Visite> create(VisiteAddRequest visiteAddRequest) {
        return creatVisiteCreationRequest(visiteAddRequest)
                .map(visiteMapper::visiteCreationRequestToVisitetEntity)
                .chain(visiteRepository::persistAndFlush)
                .map(visiteMapper::visiteEntityToVisite);
    }

    public Uni<Visite> get(UUID uuid) {
        return this.visiteRepository.findByUuid(uuid)
                .onItem().ifNull().failWith(VisiteNotFound::new)
                .map(this.visiteMapper::visiteEntityToVisite);
    }

    @WithTransaction
    public Uni<Visite> update(UUID uuid, VisiteUpdateRequest visiteUpdateRequest) {
        return this.visiteRepository.findByUuid(uuid)
                .onItem().ifNull().failWith(VisiteNotFound::new)
                .map((visiteEntity) -> {
                    this.visiteMapper.visiteUpdateRequestToVisiteEntity(visiteUpdateRequest, visiteEntity);
                    return visiteEntity;
                })
                .map(this.visiteMapper::visiteEntityToVisite);
    }

    public Uni<List<Visite>> getAll() {
        return visiteRepository.getAll()
                .map((patients) -> patients.stream().map(visiteMapper::visiteEntityToVisite).toList());

    }

    private Uni<VisiteCreationRequest> creatVisiteCreationRequest(VisiteAddRequest visiteAddRequest) {
        return this.patientRepository.findByUuid(visiteAddRequest.patientUuid())
                .onItem().ifNull().failWith(PatientNotFound::new)
                .map(patientMapper::patientEntityToPatient)
                .map((patient) -> visiteMapper.visiteAddRequestToVisiteCreationRequest(visiteAddRequest, patient));
    }
}
