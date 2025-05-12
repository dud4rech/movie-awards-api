package com.razzies.movie_awards_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "year")
    private int year;
    @Column(name = "studios")
    private String studios;
    @Column(name = "producers")
    private String producers;
    @Column(name = "winner")
    private Boolean winner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getStudios() {
        return studios;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public String getProducers() {
        return producers;
    }

    public void setProducers(String producers) {
        this.producers = producers;
    }

    public Boolean isWinner() {
        return winner;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }
}
