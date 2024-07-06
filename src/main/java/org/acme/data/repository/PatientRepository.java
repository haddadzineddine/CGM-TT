package org.acme.data.repository;

import java.util.UUID;

import org.acme.data.entity.PatientEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PatientRepository implements PanacheRepository<PatientEntity> {

    public Uni<PatientEntity> findByUuid(UUID uuid) {
        return find("uuid = ?1", uuid).firstResult();
    }
}
