package com.softserve.edu.jom.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity

public class Marathon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
}
