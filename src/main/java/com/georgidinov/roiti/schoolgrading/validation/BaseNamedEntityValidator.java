package com.georgidinov.roiti.schoolgrading.validation;

import com.georgidinov.roiti.schoolgrading.baseentity.BaseNamedEntity;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import org.springframework.stereotype.Component;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_NAME_IS_BLANK;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_NAME_IS_EMPTY;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_NAME_IS_NULL;

@Component
public class BaseNamedEntityValidator implements EntityValidator<BaseNamedEntity> {

    @Override
    public void validate(BaseNamedEntity entity) throws EntityValidationException {
        String entityName = entity.getName();
        this.validateName(entityName);
    }

    //== private methods ==
    private void validateName(String entityName) throws EntityValidationException {
        if (entityName == null) {
            throw new EntityValidationException(ERROR_ENTITY_NAME_IS_NULL);
        }
        if (entityName.isEmpty()) {
            throw new EntityValidationException(ERROR_ENTITY_NAME_IS_EMPTY);
        }
        if (entityName.isBlank()) {
            throw new EntityValidationException(ERROR_ENTITY_NAME_IS_BLANK);
        }
    }

}
