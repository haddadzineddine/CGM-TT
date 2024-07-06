package org.acme.data.repository;

import org.acme.data.entity.PatientEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PatientRepository implements PanacheRepository<PatientEntity> {

}
