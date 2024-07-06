package org.acme.core.models.visite;

import java.time.LocalDate;
import java.util.UUID;

import org.acme.core.enumeration.VisiteType;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record VisiteAddRequest(
        @NotBlank String reason,
        @NotBlank String familyHistory,
        @FutureOrPresent LocalDate visiteDate,
        @NotNull VisiteType type,
        @NotNull UUID patientUuid) {

}
