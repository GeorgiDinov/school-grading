package com.georgidinov.roiti.schoolgrading.util;

public final class ApplicationConstants {

    //== Entity constants ==
    public static final String ENTITY_COURSE_TABLE_NAME = "course";
    public static final String ENTITY_COURSE_COLUMN_NAME_COURSE_ID = "course_id";
    public static final String ENTITY_COURSE_COLUMN_NAME_COURSE_NAME = "course_name";
    public static final String ENTITY_MAPPING_COURSE = "course";

    public static final String ENTITY_STUDENT_TABLE_NAME = "student";
    public static final String ENTITY_STUDENT_COLUMN_NAME_STUDENT_ID = "student_id";
    public static final String ENTITY_STUDENT_COLUMN_NAME_STUDENT_NAME = "student_name";
    public static final String ENTITY_MAPPING_STUDENT = "student";

    public static final String ENTITY_MARK_TABLE_NAME = "mark";
    public static final String ENTITY_MARK_COLUMN_NAME_MARK_ID = "mark_id";
    public static final String ENTITY_MARK_COLUMN_NAME_MARK_NAME = "mark";
    public static final String ENTITY_MARK_COLUMN_NAME_MARK_DATE = "mark_date";
    public static final String ENTITY_MARK_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    //== Entity Exception Messages
    public static final String ERROR_ENTITY_IS_NULL = "Entity Is Null";
    public static final String ERROR_ENTITY_ID_IS_NULL = "Entity Id Is Null";
    public static final String ERROR_ENTITY_NAME_IS_NULL = "Entity Name Is Null";
    public static final String ERROR_ENTITY_NAME_IS_EMPTY = "Entity Name Is Empty";
    public static final String ERROR_ENTITY_NAME_IS_BLANK = "Entity Name Is Blank";

    public static final String ERROR_COURSE_NOT_FOUND = "Course with id = %d not found";
    public static final String ERROR_MARK_NOT_FOUND = "Mark with id = %d not found";
    public static final String ERROR_STUDENT_NOT_FOUND = "Student with id = %d not found";
    public static final String ERROR_STUDENT_EXISTS = "Student with name = %s exists";
    public static final String ERROR_COURSE_EXISTS = "Student with name = %s exists";

    //== Mark Exception Messages
    public static final String ERROR_ENTITY_MARK_VALUE_IS_NOT_PRESENT = "Mark Value Is Null";
    public static final String ERROR_ENTITY_MARK_VALUE_IS_OUT_OF_BOUNDS_LESS = "Mark Value Is Less Than 2.00";
    public static final String ERROR_ENTITY_MARK_VALUE_IS_OUT_OF_BOUNDS_MORE = "Mark Value Is Greater Than 6.00";
    public static final String ERROR_ENTITY_MARK_DATE_TIME_IS_NULL = "Mark Date Time Is Null";

    //== CSV header names constants ==
    public static final String CSV_HEADER_COURSE_ID = "course_id";
    public static final String CSV_HEADER_COURSE_NAME = "course_name";

    public static final String CSV_HEADER_STUDENT_ID = "student_id";
    public static final String CSV_HEADER_STUDENT_NAME = "student_name";

    public static final String CSV_HEADER_MARK_ID = "mark_id";
    public static final String CSV_HEADER_MARK_NAME = "mark";
    public static final String CSV_HEADER_MARK_DATE = "mark_date";

    //End Point Mappings
    public static final String API_V1 = "/api/v1";
    public static final String MARK_BASE_URL = API_V1 + "/mark";
    public static final String STUDENT_BASE_URL = API_V1 + "/student";
    public static final String COURSE_BASE_URL = API_V1 + "/course";
    public static final String REPORT_BASE_URL = API_V1 + "/report";

    private ApplicationConstants() {
    }

}