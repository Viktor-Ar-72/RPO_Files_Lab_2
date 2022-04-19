package ru.iu3.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "paintings")
@Access(AccessType.FIELD)

/**
 * Класс - модель картин
 */
public class Painting {
    // Поле ID - является первичным ключом
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    // Поле - имя
    @Column(name = "name")
    public String name;

    // Устанавливаем обратную связь: один ко многим от таблицы картин к таблице художников.
    // Обратная связь - у художников
    @ManyToOne
    @JoinColumn(name = "artistid")
    public Artists artistid;

    // Здесь тоже нужно сделать связь: один ко многим от таблицы картин к таблице музея
    @ManyToOne
    @JoinColumn(name = "museumid")
    public Museum museumid;

    // Возраст картины
    @Column(name = "year")
    public Long year;
}
