package com.anlohse.raspberry_awards.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "_year")
    private Integer year;
    private String title;
    private String studios;
    private String producer;
    private String winner;

}
