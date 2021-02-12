package com.georgidinov.roiti.schoolgrading.validation;

import com.georgidinov.roiti.schoolgrading.domain.Mark;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_MARK_DATE_TIME_IS_NULL;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_MARK_VALUE_IS_NOT_PRESENT;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_MARK_VALUE_IS_OUT_OF_BOUNDS_LESS;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_MARK_VALUE_IS_OUT_OF_BOUNDS_MORE;

@Component
public class MarkValidator implements EntityValidator<Mark> {

    private final BaseEntityValidator baseEntityValidator;

    @Autowired
    public MarkValidator(BaseEntityValidator baseEntityValidator) {
        this.baseEntityValidator = baseEntityValidator;
    }


    @Override
    public void validate(Mark entity) throws EntityValidationException {
        this.baseEntityValidator.validate(entity);

        Double mark = entity.getMark();
        this.validateMarkValue(mark);

        LocalDateTime markDateTime = entity.getMarkDate();
        this.validateMarkDateTime(markDateTime);

    }

    private void validateMarkValue(Double mark) throws EntityValidationException {
        if (mark == null) {
            throw new EntityValidationException(ERROR_ENTITY_MARK_VALUE_IS_NOT_PRESENT);
        }
        if (mark < 2.00d) {
            throw new EntityValidationException(ERROR_ENTITY_MARK_VALUE_IS_OUT_OF_BOUNDS_LESS);
        }
        if (mark > 6.00d) {
            throw new EntityValidationException(ERROR_ENTITY_MARK_VALUE_IS_OUT_OF_BOUNDS_MORE);
        }
    }

    private void validateMarkDateTime(LocalDateTime markDateTime) throws EntityValidationException {
        if (markDateTime == null) {
            throw new EntityValidationException(ERROR_ENTITY_MARK_DATE_TIME_IS_NULL);
        }
    }

}