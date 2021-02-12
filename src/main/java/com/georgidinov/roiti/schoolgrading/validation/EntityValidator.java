package com.georgidinov.roiti.schoolgrading.validation;

import com.georgidinov.roiti.schoolgrading.baseentity.BaseEntity;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;

public interface EntityValidator<T extends BaseEntity> {

    void validate(T entity) throws EntityValidationException;

}
