package com.softserve.edu.jom.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    public enum Role {
        MENTOR, TRAINEE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    @Size(min = 2, max = 20, message =
            "Last name must be between 2 and 20 characters")
    private String lastName;

//    @Column(unique = true)
    @NotNull
    @Pattern(regexp = ".+@.\\..+", message = "Please provide a valid email address")
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private String password;

//    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "marathon_user_link",
            joinColumns = @JoinColumn(name = "user_fk"),
            inverseJoinColumns = @JoinColumn(name = "marathon_fk"))
    private List<Marathon> marathons;

}
