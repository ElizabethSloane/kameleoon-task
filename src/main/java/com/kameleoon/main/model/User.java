package com.kameleoon.main.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String login;

    @Column(nullable = false)
    @Setter
    @Getter
    private String password;

    @Column(name = "reg_time", nullable = false)
    @Setter
    @Getter
    private LocalDateTime regTime;

    @Setter
    @Getter
    private String photo;
}
