package org.cgm.core.exceptions.config;

import org.cgm.core.enumeration.ErrorEnum;

import lombok.Getter;

@Getter
public class CgmException extends RuntimeException {
    final ErrorEnum error;
    final String description;

    public CgmException(ErrorEnum error, String description) {
        this.error = error;
        this.description = description;
    }

    public CgmException(ErrorEnum error) {
        this.error = error;
        this.description = error.getDescription();
    }

}
