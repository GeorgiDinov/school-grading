package com.georgidinov.roiti.schoolgrading.domain;

import com.georgidinov.roiti.schoolgrading.security.role.SchoolUserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ENTITY_SCHOOL_CREDENTIALS_COLUMN_NAME_USER_CREDENTIALS_ID;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ENTITY_SCHOOL_CREDENTIALS_TABLE_NAME;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = ENTITY_SCHOOL_CREDENTIALS_TABLE_NAME)
public class SchoolUserCredentials {

    // == fields ==
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ENTITY_SCHOOL_CREDENTIALS_COLUMN_NAME_USER_CREDENTIALS_ID)
    private Long id;

    private String username;
    private String password;

    @OneToOne(mappedBy = "credentials", fetch = FetchType.EAGER)
    private Student student;

    @Enumerated(value = EnumType.STRING)
    private SchoolUserRole userRole;


    @Override
    public String toString() {
        return "SchoolUserCredentials{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}