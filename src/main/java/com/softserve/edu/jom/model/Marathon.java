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
    @ManyToMany(mappedBy = "marathons")
    private List<User> users;

    @OneToMany
    private List<Sprint> sprints;
}
