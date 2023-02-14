package com.kameleoon.main.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quote_votes")
public class QuoteVote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    private Integer id;

    @JoinColumn(name = "user_id", nullable = false)
    @Setter
    @Getter
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    private Integer userId;

    @Column(name = "quote_id", nullable = false)
    @Setter
    @Getter
    private Integer quoteId;

    @Column(nullable = false)
    @Setter
    @Getter
    private int value;

    @Column(nullable = false)
    @Setter
    @Getter
    private LocalDateTime time;
}
