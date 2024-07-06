package org.cgm.core.models.patient;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;

@Builder
public record Patient(UUID uuid, String name, String surname, LocalDate birthDate, String socialSecurityNumber) {

}
