package org.cgm.core.models.visite;

import java.time.LocalDate;

import org.cgm.core.enumeration.VisiteType;
import org.cgm.core.models.patient.Patient;

import lombok.Builder;

@Builder
public record VisiteCreationRequest(
        String reason,
        String familyHistory,
        LocalDate visiteDate,
        VisiteType type,
        Patient patient) {

}
