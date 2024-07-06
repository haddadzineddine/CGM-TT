package org.acme.core.models.visite;
import org.acme.core.enumeration.VisiteType;
import lombok.Builder;
import java.time.LocalDate;

@Builder
public record VisiteUpdateRequest(String reason, String familyHistory, LocalDate visiteDate, VisiteType type) {

}
