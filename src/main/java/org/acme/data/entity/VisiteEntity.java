package org.acme.data.entity;

import java.time.LocalDate;
import java.util.UUID;

import org.acme.core.enumeration.VisiteType;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class VisiteEntity {
    @Id
    @UuidGenerator
    private UUID uuid;

    @ManyToOne(fetch = FetchType.EAGER)
    private PatientEntity patient;

    @Column(name = "visite_date")
    protected LocalDate visiteDate;

    @Enumerated(EnumType.STRING)
    private VisiteType type;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = true)
    private String familyHistory;
}
