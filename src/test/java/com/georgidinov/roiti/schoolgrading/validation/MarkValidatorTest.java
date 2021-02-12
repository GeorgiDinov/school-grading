package com.georgidinov.roiti.schoolgrading.validation;

import com.georgidinov.roiti.schoolgrading.domain.Course;
import com.georgidinov.roiti.schoolgrading.domain.Mark;
import com.georgidinov.roiti.schoolgrading.domain.Student;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_MARK_DATE_TIME_IS_NULL;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_MARK_VALUE_IS_NOT_PRESENT;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_MARK_VALUE_IS_OUT_OF_BOUNDS_LESS;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_ENTITY_MARK_VALUE_IS_OUT_OF_BOUNDS_MORE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;

class MarkValidatorTest {

    @Mock
    BaseEntityValidator baseEntityValidator;

    @InjectMocks
    MarkValidator markValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateMarkIsOk() throws EntityValidationException {
        //given
        Mark mark = new Mark(1L, 6.00d, LocalDateTime.now(), new Course(), new Student());
        //when then
        this.markValidator.validate(mark);
    }


    @ParameterizedTest(name = ARGUMENTS_PLACEHOLDER)
    @MethodSource("markObjectsProvider")
    void validate(Mark mark, String expectedMessage) {
        //given
        Exception exception = assertThrows(
                EntityValidationException.class,
                () -> markValidator.validate(mark));
        //when then
        String exceptionMessage = exception.getMessage();
        assertEquals(expectedMessage, exceptionMessage);
    }


    private static Stream<Arguments> markObjectsProvider() {
        return Stream.of(
                Arguments.of(new Mark(1L, null, LocalDateTime.now(), new Course(), new Student()),
                        ERROR_ENTITY_MARK_VALUE_IS_NOT_PRESENT),
                Arguments.of(new Mark(1L, 1.99d, LocalDateTime.now(), new Course(), new Student()),
                        ERROR_ENTITY_MARK_VALUE_IS_OUT_OF_BOUNDS_LESS),
                Arguments.of(new Mark(1L, 6.01d, LocalDateTime.now(), new Course(), new Student()),
                        ERROR_ENTITY_MARK_VALUE_IS_OUT_OF_BOUNDS_MORE),
                Arguments.of(new Mark(1L, 6.00d, null, new Course(), new Student()),
                        ERROR_ENTITY_MARK_DATE_TIME_IS_NULL)
        );
    }


}