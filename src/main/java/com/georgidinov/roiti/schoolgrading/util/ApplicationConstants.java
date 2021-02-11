package com.georgidinov.roiti.schoolgrading.util;

public final class ApplicationConstants {

    //== entity related constants ==
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

    //== CSV header names constants ==
    public static final String CSV_HEADER_COURSE_ID = "course_id";
    public static final String CSV_HEADER_COURSE_NAME = "course_name";

    public static final String CSV_HEADER_STUDENT_ID = "student_id";
    public static final String CSV_HEADER_STUDENT_NAME = "student_name";

    public static final String CSV_HEADER_MARK_ID = "mark_id";
    public static final String CSV_HEADER_MARK_NAME = "mark";
    public static final String CSV_HEADER_MARK_DATE = "mark_date";

    private ApplicationConstants() {
    }

}