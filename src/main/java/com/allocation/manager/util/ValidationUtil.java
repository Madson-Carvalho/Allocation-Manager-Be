package com.allocation.manager.util;

import jakarta.persistence.EntityNotFoundException;

public class ValidationUtil {

    public static <T> T checkNotNullOrThrowEntityNotFound(T object, String errorMessage) {
        if (object == null) {
            throw new EntityNotFoundException(errorMessage);
        }
        return object;
    }
}
