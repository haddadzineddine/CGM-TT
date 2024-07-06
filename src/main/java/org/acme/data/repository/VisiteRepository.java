package org.acme.data.repository;

import org.acme.data.entity.VisiteEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VisiteRepository implements PanacheRepository<VisiteEntity> {

}
