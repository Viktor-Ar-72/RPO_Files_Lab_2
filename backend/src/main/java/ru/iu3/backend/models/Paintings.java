package ru.iu3.backend.models;

import javax.persistence.*;

@Entity
@Table(name = "paintings")
@Access(AccessType.FIELD)


public class Paintings {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "artist_id")
    public Long artistid;

    @Column(name = "museum_id")
    public Long museumid;

    @Column(name = "year")
    public Long year;

    public Paintings(){}

    public Paintings(Long id){
        this.id = id;
    }
}