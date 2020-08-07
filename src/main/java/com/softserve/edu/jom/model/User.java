package com.softserve.edu.jom.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(exclude = "marathons")
public class User implements UserDetails {
    public enum Role {
        MENTOR, TRAINEE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name must be populated")
    @Size(min = 2, max = 20, message =
            "First name must be between 2 and 20 characters")
    private String firstName;

    @NotBlank(message = "Last name must be populated")
    @Size(min = 2, max = 20, message =
            "Last name must be between 2 and 20 characters")
    private String lastName;

    @Column(unique = true)
    @NotBlank(message = "Email must be populated")
    @Pattern(regexp = "^(.+)@(.+)$", message = "Please provide a valid email address")
    private String email;

    @NotNull(message = "Role must be populated")
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private com.softserve.edu.jom.model.Role role;

    @NotBlank(message = "Password must be populated")
    private String password;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "marathon_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "marathon_id"))
    private Set<Marathon> marathons = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
