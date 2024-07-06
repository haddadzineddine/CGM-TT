package org.acme.data.repository;

import org.acme.data.entity.VisiteEntity;
import java.util.UUID;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VisiteRepository implements PanacheRepository<VisiteEntity> {
   
    public Uni<VisiteEntity> findByUuid(UUID uuid) {
        return find("uuid = ?1", uuid).firstResult();
    }

}
