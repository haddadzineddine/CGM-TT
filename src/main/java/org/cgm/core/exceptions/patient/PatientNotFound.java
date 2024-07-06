package org.cgm.core.exceptions.patient;

import org.cgm.core.enumeration.ErrorEnum;
import org.cgm.core.exceptions.config.CgmException;

public class PatientNotFound extends CgmException {
     private static final ErrorEnum errorEnum = ErrorEnum.E00001;

    public PatientNotFound(String description) {
        super(errorEnum, description);
    }

    public PatientNotFound() {
        super(errorEnum);
    }

}
