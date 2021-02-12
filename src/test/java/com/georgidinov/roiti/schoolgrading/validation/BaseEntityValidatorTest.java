package com.georgidinov.roiti.schoolgrading.validation;

import com.georgidinov.roiti.schoolgrading.baseentity.BaseEntity;
import com.georgidinov.roiti.schoolgrading.domain.Course;
import com.georgidinov.roiti.schoolgrading.domain.Mark;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.stream.Stream;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_ID_IS_NULL;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_IS_NULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;

class BaseEntityValidatorTest {

    BaseEntityValidator validator;

    @BeforeEach
    void setUp() {
        validator = new BaseEntityValidator();
    }

    @Test
    void validateEntityIsOk() throws EntityValidationException {
        //given
        Mark mark = new Mark(1L, null, null, null, null);
        //when then
        this.validator.validate(mark);
    }

    @ParameterizedTest(name = ARGUMENTS_PLACEHOLDER)
    @MethodSource("baseEntityProvider")
    void validate(BaseEntity baseEntity, String expectedMessage) {
        //given
        Exception exception =
                assertThrows(EntityValidationException.class,
                        () -> this.validator.validate(baseEntity));
        //when
        String exceptionMessage = exception.getMessage();
        //then
        assertEquals(expectedMessage, exceptionMessage);
    }


    //== private methods ==
    private static Stream<Arguments> baseEntityProvider() {
        return Stream.of(
                Arguments.of(null, ERROR_ENTITY_IS_NULL),
                Arguments.of(new Course(null, "Test", new HashSet<>()), ERROR_ENTITY_ID_IS_NULL)
        );
    }

}