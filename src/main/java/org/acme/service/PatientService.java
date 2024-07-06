package org.acme.service;

import org.acme.core.exceptions.visite.VisiteNotFound;
import org.acme.core.mappers.PatientMapper;
import org.acme.core.models.patient.Patient;
import org.acme.core.models.patient.PatientAddRequest;
import org.acme.data.repository.PatientRepository;
import java.util.List;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class PatientService {

    @Inject
    PatientRepository patientRepository;

    @Inject
    PatientMapper patientMapper;

    public Uni<Patient> create(PatientAddRequest patientAddRequest) {
        return Uni.createFrom().item(patientAddRequest)
                .map(patientMapper::patientAddRequestToPatientEntity)
                .chain(patientRepository::persistAndFlush)
                .map(patientMapper::patientEntityToPatient);
    }

     public Uni<Patient> get(UUID uuid) {
        return this.patientRepository.findByUuid(uuid)
                .onItem().ifNull().failWith(new VisiteNotFound())
                .map(this.patientMapper::patientEntityToPatient);
    }

    public Uni<List<Patient>> getAll() {
        return patientRepository.findAll().list()
                .map((patients) -> patients.stream().map(patientMapper::patientEntityToPatient).toList());

    }
}
