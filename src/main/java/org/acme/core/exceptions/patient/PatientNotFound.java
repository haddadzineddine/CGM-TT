package org.acme.core.exceptions.patient;

import org.acme.core.enumeration.ErrorEnum;
import org.acme.core.exceptions.config.CgmException;

public class PatientNotFound extends CgmException {
     private static final ErrorEnum errorEnum = ErrorEnum.E00001;

    public PatientNotFound(String description) {
        super(errorEnum, description);
    }

    public PatientNotFound() {
        super(errorEnum);
    }

}
