package org.acme.core.models.visite;

import java.time.LocalDate;
import org.acme.core.enumeration.VisiteType;
import org.acme.core.models.patient.Patient;

import lombok.Builder;

@Builder
public record VisiteCreationRequest(
        String reason,
        String familyHistory,
        LocalDate visiteDate,
        VisiteType type,
        Patient patient) {

}
