
package org.cgm.core.exceptions.visite;

import org.cgm.core.enumeration.ErrorEnum;
import org.cgm.core.exceptions.config.CgmException;

public class VisiteNotFound extends CgmException {
    private static final ErrorEnum errorEnum = ErrorEnum.E00001;

    public VisiteNotFound(String description) {
        super(errorEnum, description);
    }

    public VisiteNotFound() {
        super(errorEnum);
    }

}
