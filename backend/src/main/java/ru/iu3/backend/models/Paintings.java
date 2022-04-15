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

    // Устанавливаем обратную связь: один ко многим от таблицы картин к таблице артистов. Обратная связь есть у художников
    @ManyToOne
    @JoinColumn(name = "artistid")
    public Artists artistid;

    // Здесь тоже нужно сделать связь: один ко многим от таблицы картин к таблице музея
    @ManyToOne
    @JoinColumn(name = "museumid")
    public Museums museumid;
    @Column(name = "year")
    public Long year;

    public Paintings(){}

    public Paintings(Long id){
        this.id = id;
    }
}