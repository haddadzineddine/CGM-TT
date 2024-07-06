package org.cgm.core.models.visite;

import java.time.LocalDate;
import java.util.UUID;

import org.cgm.core.enumeration.VisiteType;
import org.cgm.core.models.patient.Patient;

import lombok.Builder;

@Builder
public record Visite(
        UUID uuid, LocalDate visiteDate,
        VisiteType type, String reason, String familyHistory, Patient patient) {
}
