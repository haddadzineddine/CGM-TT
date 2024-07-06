package org.cgm.data.repository;

import java.util.List;
import java.util.UUID;
import org.cgm.data.entity.VisiteEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VisiteRepository implements PanacheRepository<VisiteEntity> {

    public Uni<VisiteEntity> findByUuid(UUID uuid) {
        return find("uuid = ?1", uuid).firstResult();
    }

    public Uni<List<VisiteEntity>> getAll() {
        return findAll().list();
    }
}
