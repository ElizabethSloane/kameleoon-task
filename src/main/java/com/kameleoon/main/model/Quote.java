package com.kameleoon.main.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    private Integer id;

    @Column(nullable = false)
    @Setter
    @Getter
    private String text;

    @JoinColumn(name = "user_id")
    @Setter
    @Getter
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    private Integer userId;

    @Setter
    @Getter
    @OneToMany(targetEntity = QuoteVote.class, cascade = CascadeType.ALL)
    private List<QuoteVote> votes;
}
