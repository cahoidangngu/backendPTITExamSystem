package com.quanchun.backendexamsystem.entities;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
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


    @OneToMany(mappedBy = "user")
    private Set<RegisterQuizz> registerQuizzes;
    @ManyToMany(
            cascade = CascadeType.PERSIST
    )
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "userId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "roleId"
            )
    )
    private Set<Role> roles;

    public void addRole(Role role){
        if(roles == null)roles = new HashSet<>();
        roles.add(role);
    }
}
