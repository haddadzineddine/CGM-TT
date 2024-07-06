package org.cgm.service;

import java.util.List;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.UUID;
import org.cgm.core.exceptions.patient.PatientNotFound;
import org.cgm.core.mappers.PatientMapper;
import org.cgm.core.models.patient.Patient;
import org.cgm.core.models.patient.PatientAddRequest;
import org.cgm.data.repository.PatientRepository;

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
                .onItem().ifNull().failWith(PatientNotFound::new)
                .map(this.patientMapper::patientEntityToPatient);
    }

    public Uni<List<Patient>> getAll() {
        return patientRepository.getAll()
                .map((patients) -> patients.stream().map(patientMapper::patientEntityToPatient).toList());

    }
}
