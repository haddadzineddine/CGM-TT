package org.acme.service;

import org.acme.core.exceptions.patient.PatientNotFound;
import org.acme.core.exceptions.visite.VisiteNotFound;
import org.acme.core.mappers.PatientMapper;
import org.acme.core.mappers.VisiteMapper;
import org.acme.core.models.visite.Visite;
import org.acme.core.models.visite.VisiteAddRequest;
import org.acme.core.models.visite.VisiteCreationRequest;
import org.acme.core.models.visite.VisiteUpdateRequest;
import org.acme.data.repository.PatientRepository;
import org.acme.data.repository.VisiteRepository;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

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
                .onItem().ifNull().failWith(new VisiteNotFound())
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
        return visiteRepository.findAll().list()
                .map((patients) -> patients.stream().map(visiteMapper::visiteEntityToVisite).toList());

    }

    private Uni<VisiteCreationRequest> creatVisiteCreationRequest(VisiteAddRequest visiteAddRequest) {
        return this.patientRepository.findByUuid(visiteAddRequest.patientUuid())
                .onItem().ifNull().failWith(new PatientNotFound())
                .map(patientMapper::patientEntityToPatient)
                .map((patient) -> visiteMapper.visiteAddRequestToVisiteCreationRequest(visiteAddRequest, patient));
    }
}
