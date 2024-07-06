package org.cgm;

import org.cgm.core.enumeration.VisiteType;
import org.cgm.core.models.patient.PatientAddRequest;
import org.cgm.core.models.visite.VisiteAddRequest;
import org.cgm.core.models.visite.VisiteUpdateRequest;
import java.util.UUID;
import java.time.LocalDate;

public class TestHelpers {
     public static VisiteAddRequest createVisiteAddRequest(UUID patientUuid) {
        return VisiteAddRequest.builder()
                .patientUuid(patientUuid)
                .reason("Reason")
                .visiteDate(LocalDate.now().plusDays(1))
                .type(VisiteType.AT_HOME)
                .familyHistory("Family History")
                .build();
    }

    public static VisiteUpdateRequest createVisiteUpdateRequest() {
        return VisiteUpdateRequest.builder()
                .reason("Reason Updated")
                .build();
    }

    public static PatientAddRequest createAddRequest() {
                return PatientAddRequest.builder()
                                .name("Mohamed")
                                .surname("Ali")
                                .birthDate(LocalDate.of(1990, 1, 1))
                                .socialSecurityNumber("123456789")
                                .build();
        }
}
