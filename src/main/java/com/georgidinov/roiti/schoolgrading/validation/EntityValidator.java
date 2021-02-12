package com.georgidinov.roiti.schoolgrading.validation;

import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;

public interface EntityValidator<T> {

    void validate(T entity) throws EntityValidationException;

}
