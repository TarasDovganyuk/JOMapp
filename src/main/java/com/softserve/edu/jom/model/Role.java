package com.softserve.edu.jom.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Role must be populated")
    @Enumerated(EnumType.STRING)
    private User.Role role;

    @OneToMany(mappedBy = "role")
    private Set<User> users = new HashSet<>(0);

    @Override
    public String getAuthority() {
        return role != null ? role.name() : null;
    }
}
