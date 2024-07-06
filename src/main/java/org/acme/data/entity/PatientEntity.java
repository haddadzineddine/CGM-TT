package org.acme.data.entity;

import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "patients")
public class PatientEntity {

    @Id
    @UuidGenerator
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @Column(name = "birth_date")
    protected LocalDate birthDate;

    @Column(nullable = false)
    private String socialSecurityNumber;
}
