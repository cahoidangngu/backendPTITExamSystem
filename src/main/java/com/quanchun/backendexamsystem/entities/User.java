package com.quanchun.backendexamsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true)
    private String username;
    private String password;
    private String fullName;
    private Date dob;
    private String studyClass;
    private String phone;
    private Boolean gender;
    private String address;
    private String imagePath;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RegisterQuizz> registerQuizzes;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "roleId")
    private Role role;

    public void addRegisterQuizz(RegisterQuizz rq)
    {
        if(registerQuizzes == null)
        {
            registerQuizzes = new ArrayList<>();
        }
        registerQuizzes.add(rq);
    }
}
