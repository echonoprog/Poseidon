package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rating")
@Getter
@Setter
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "moodysRating")
    private String moodysRating;

    @Column(name = "sandPRating")
    private String sandPRating;

    @Column(name = "fitchRating")
    private String fitchRating;

    @Column(name = "orderNumber")
    private Integer orderNumber;
}