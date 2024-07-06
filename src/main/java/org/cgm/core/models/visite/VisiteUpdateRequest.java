package org.cgm.core.models.visite;
import lombok.Builder;
import java.time.LocalDate;

import org.cgm.core.enumeration.VisiteType;

@Builder
public record VisiteUpdateRequest(String reason, String familyHistory, LocalDate visiteDate, VisiteType type) {

}
