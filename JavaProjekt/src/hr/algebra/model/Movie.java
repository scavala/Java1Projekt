/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author zakesekresa
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Movie {

    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @XmlTransient
    private int id;

    private String title;

    @XmlElement(name = "publisheddate")
    @XmlJavaTypeAdapter(PublishedDateTimeAdapter.class)
    private LocalDateTime publishedDate;

    private String description;
    private String orignaziv;

    @XmlElementWrapper
    @XmlElement(name = "person")
    private List<Person> directors;

    @XmlElementWrapper
    @XmlElement(name = "person")
    private List<Person> actors;

    private int length;

    @XmlElementWrapper
    @XmlElement(name = "genre")
    private List<Genre> genres;

    @XmlElement(name = "poster")
    private String posterPath;

    public Movie() {
    }

    public Movie(String title, LocalDateTime publishedDate, String description, String orignaziv, int trajanje, String plakat) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.description = description;
        this.orignaziv = orignaziv;
        this.length = trajanje;
        this.posterPath = plakat;
    }

    public Movie(int id, String title, LocalDateTime publishedDate, String description, String orignaziv, List<Person> directors, List<Person> actors, int trajanje, List<Genre> genres, String plakat) {
        this(title, publishedDate, description, orignaziv, trajanje, plakat);
        this.actors = actors;
        this.genres = genres;
        this.directors = directors;
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrignaziv() {
        return orignaziv;
    }

    public void setOrignaziv(String orignaziv) {
        this.orignaziv = orignaziv;
    }

    public List<Person> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Person> directors) {
        this.directors = directors;
    }

    public List<Person> getActors() {
        return actors;
    }

    public void setActors(List<Person> actors) {
        this.actors = actors;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public String toString() {
        return id + " - " + title + " - " + publishedDate;
    }

}
