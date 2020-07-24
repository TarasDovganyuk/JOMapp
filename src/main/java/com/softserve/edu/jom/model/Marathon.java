package com.softserve.edu.jom.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
public class Marathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

//    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "marathon_user_link",
            joinColumns = @JoinColumn(name = "marathon_fk"),
            inverseJoinColumns = @JoinColumn(name = "user_fk"))
    private List<User> users;

    @OneToMany(mappedBy="marathon")
    private List<Sprint> sprints;
}
