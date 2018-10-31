package com.ideas2it.dvdstore.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

// Where is this used?
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.SQLDelete; 
import org.hibernate.annotations.Where; 

import static com.ideas2it.dvdstore.common.Constants.*;

@Entity
@Table(name="genre")
@NamedNativeQuery(
    name    =   "recoverGenre",
    query   =   "update genre set expired_date = null where id = ?"
    )
@SQLDelete(sql="update genre set expired_date = current_date() where id=?")
@Where(clause = "expired_date is null")
public class Genre {
    @Id  
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name")
    private String name;

    @ManyToMany(mappedBy="genres", fetch=FetchType.EAGER)
    private List<Dvd> dvds = new ArrayList<Dvd>();

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Dvd> getDvds() {
        return this.dvds;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDvds(List<Dvd> dvds) {
        this.dvds = dvds;
    }

    /**
     * Returns a string representation of the Genre object
     */
    @Override 
    public String toString() {
        return new StringBuilder(GENRE_ID).append(this.id)
            .append(COMMA).append(GENRE_NAME).append(this.name).toString();
    }
}
