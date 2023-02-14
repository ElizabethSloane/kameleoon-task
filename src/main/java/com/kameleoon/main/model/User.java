package com.kameleoon.main.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    private Integer id;

    @Column(nullable = false)
    @Setter
    @Getter
    private String name;

    @Column(nullable = false)
    @Setter
    @Getter
    private String password;

    @Column(nullable = false)
    @Setter
    @Getter
    private String email;

    @Column(name = "creation_date", nullable = false)
    @Setter
    @Getter
    private LocalDate creationDate;


}
