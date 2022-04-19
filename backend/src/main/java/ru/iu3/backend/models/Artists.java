package ru.iu3.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс-модель, художники
 */
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
    @Column(name = "name", nullable = false)
    public String name;

    // Поле, которое показывает отношение многие-к-одному
    @ManyToOne
    @JoinColumn(name = "countryid")
    public Country countryid;

    // Устанавливаем обратную связь: один ко многим от таблицы художников в музее
    // JSON IGNORE - чтобы не было бесконечных циклических запросов
    @JsonIgnore
    @OneToMany(mappedBy = "artistid")
    public List<Painting> paintings = new ArrayList<Painting>();

    // Поле "Возраст"
    @Column(name = "age")
    public String age;

    // Конструктор без параметров
    public Artists() {}

   // Конструктор
    public Artists(Long id) {
        this.id = id;
    }
}
