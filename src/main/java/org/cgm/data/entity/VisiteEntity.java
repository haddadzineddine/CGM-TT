package org.cgm.data.entity;

import java.time.LocalDate;
import java.util.UUID;

import org.cgm.core.enumeration.VisiteType;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Data
@Entity(name = "visites")
public class VisiteEntity {
    @Id
    @UuidGenerator
    private UUID uuid;

    @ManyToOne(fetch = FetchType.EAGER)
    private PatientEntity patient;

    @Column(name = "visite_date")
    private LocalDate visiteDate;

    @Enumerated(EnumType.STRING)
    private VisiteType type;

    @Column(nullable = false)
    private String reason;

    @Column(name = "family_history", nullable = true)
    private String familyHistory;
}
