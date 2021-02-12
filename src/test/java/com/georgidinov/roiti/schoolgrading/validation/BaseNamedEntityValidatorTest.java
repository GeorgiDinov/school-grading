package com.georgidinov.roiti.schoolgrading.validation;

import com.georgidinov.roiti.schoolgrading.baseentity.BaseNamedEntity;
import com.georgidinov.roiti.schoolgrading.domain.Course;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.stream.Stream;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_NAME_IS_BLANK;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_NAME_IS_EMPTY;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_NAME_IS_NULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;

class BaseNamedEntityValidatorTest {

    @Mock
    BaseEntityValidator baseEntityValidator;

    @InjectMocks
    BaseNamedEntityValidator baseNamedEntityValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateEntityIsOk() throws EntityValidationException {
        //given
        Course course = new Course(1L, "TestName", new HashSet<>());
        //when then
        this.baseNamedEntityValidator.validate(course);
    }


    @ParameterizedTest(name = ARGUMENTS_PLACEHOLDER)
    @MethodSource("baseNamedEntityProvider")
    void validate(BaseNamedEntity baseNamedEntity, String expectedMessage) {
        Exception exception = assertThrows(EntityValidationException.class,
                () -> this.baseNamedEntityValidator.validate(baseNamedEntity));
        String exceptionMessage = exception.getMessage();
        assertEquals(expectedMessage, exceptionMessage);
    }

    //== private methods ==
    private static Stream<Arguments> baseNamedEntityProvider() {
        return Stream.of(
                Arguments.of(new Course(1L, null, new HashSet<>()), ERROR_ENTITY_NAME_IS_NULL),
                Arguments.of(new Course(2L, "", new HashSet<>()), ERROR_ENTITY_NAME_IS_EMPTY),
                Arguments.of(new Course(3L, " ", new HashSet<>()), ERROR_ENTITY_NAME_IS_BLANK)
        );
    }


}