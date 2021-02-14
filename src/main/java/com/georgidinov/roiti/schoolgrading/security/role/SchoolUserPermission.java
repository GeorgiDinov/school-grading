package com.georgidinov.roiti.schoolgrading.security.role;

public enum SchoolUserPermission {

    MARK_READ("mark:read"),
    MARK_WRITE("mark:write"),
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write"),
    AVERAGE_MARK_READ("avg:read");


    private final String permission;

    SchoolUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}