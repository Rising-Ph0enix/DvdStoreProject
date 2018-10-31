package com.ideas2it.dvdstore.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.SQLDelete; 
import org.hibernate.annotations.Where; 
import org.springframework.format.annotation.DateTimeFormat;

import com.ideas2it.dvdstore.model.Genre;
import com.ideas2it.dvdstore.model.LineItem;

import static com.ideas2it.dvdstore.common.Constants.*;

@Entity
@Table(name="dvd")
@NamedNativeQuery(
    name    =   "recoverDvd",
    query   =   "update dvd set expired_date = null where id = ?"
    )
@SQLDelete(sql="update dvd set expired_date = current_date() where id=?")
@Where(clause = "expired_date is null")
public class Dvd {
    @Id  
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="movie_name")
    private String movieName;
    @Column(name="release_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    @Column(name="language")
    private String language;
    @Column(name="expired_date")
    private LocalDate expiredDate;
    @Column(name="price")
    private Double price;
    @Column(name="quantity")
    private Integer quantity;

    // Not needed for a uni-directional relationship
    @OneToMany(mappedBy = "dvd")
    private List<LineItem> lineItems = new ArrayList<LineItem>();

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name = "dvd_genre", 
        joinColumns = { @JoinColumn(name = "dvd_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "genre_id") }
    )
    private List<Genre> genres = new ArrayList<Genre>();

    public Integer getId() {
        return this.id;
    }

    public String getMovieName() {
        return this.movieName;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public String getLanguage() {
        return this.language;
    }

    public Double getPrice() {
        return this.price;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public LocalDate getExpiredDate() {
        return this.expiredDate;
    }

    public List<LineItem> getLineItems() {
        return this.lineItems;
    }

    public List<Genre> getGenres() {
        return this.genres;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
