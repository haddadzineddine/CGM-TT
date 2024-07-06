package org.cgm.core.models.patient;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;

@Builder
public record PatientAddRequest(@NotBlank String name, @NotBlank String surname, @PastOrPresent LocalDate birthDate,
        @NotBlank String socialSecurityNumber) {
}
