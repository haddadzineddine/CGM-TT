package org.cgm.data.entity;

import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "patients")
public class PatientEntity {

    @Id
    @UuidGenerator
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "social_security_number", nullable = false)
    private String socialSecurityNumber;
}
