package com.georgidinov.roiti.schoolgrading.security.role;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.georgidinov.roiti.schoolgrading.security.role.SchoolUserPermission.COURSE_READ;
import static com.georgidinov.roiti.schoolgrading.security.role.SchoolUserPermission.COURSE_WRITE;
import static com.georgidinov.roiti.schoolgrading.security.role.SchoolUserPermission.MARK_READ;
import static com.georgidinov.roiti.schoolgrading.security.role.SchoolUserPermission.MARK_WRITE;
import static com.georgidinov.roiti.schoolgrading.security.role.SchoolUserPermission.STUDENT_READ;
import static com.georgidinov.roiti.schoolgrading.security.role.SchoolUserPermission.STUDENT_WRITE;

public enum SchoolUserRole {

    USER(Sets.newHashSet(MARK_READ)),
    ADMIN(Sets.newHashSet(
            MARK_READ, MARK_WRITE,
            STUDENT_READ, STUDENT_WRITE,
            COURSE_READ, COURSE_WRITE));

    //== fields ==
    private final Set<SchoolUserPermission> permissions;

    //== constructors ==
    SchoolUserRole(Set<SchoolUserPermission> permissions) {
        this.permissions = permissions;
    }

    //== public methods ==
    public Set<SchoolUserPermission> getPermissions() {
        return this.permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}