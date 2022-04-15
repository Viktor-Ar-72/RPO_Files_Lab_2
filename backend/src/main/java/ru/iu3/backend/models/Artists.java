package ru.iu3.backend.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artists")
@Access(AccessType.FIELD)

public class Artists {
    // Поле ID (not null, auto increment)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;

    // Поле с именем
    @Column(name = "name", nullable = false, unique = true)
    public String name;

    // Поле-внешний ключ countryID
    //@Column(name = "country_id")
    //public Long countryid;

    @ManyToOne()
    @JoinColumn(name = "country_id")
    public Country country;

    // Поле "Возраст"
    @Column(name = "age")
    public String age;

    @JsonIgnore
    @OneToMany(mappedBy = "artistid")
    public List<Paintings> paintings = new ArrayList<Paintings>();

    // Конструктор без параметров
    public Artists() {}

    public Artists(Long id) {
        this.id = id;
    }
}

