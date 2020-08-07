package com.softserve.edu.jom.model;

public enum RoleEnum {
    MENTOR(1, "Mentor"),
    TRAINEE(2, "Trainee");

    long id;
    String description;

    RoleEnum(long id, String description) {
        this.id = id;
        this.description = description;
    }
}
