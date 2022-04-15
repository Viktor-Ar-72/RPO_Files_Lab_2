package ru.iu3.backend.models;
import javax.persistence.*;

@Entity
@Table(name = "museums")
@Access(AccessType.FIELD)

/**
 * Класс - модель музея
 */
public class Museums {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "location")
    public String location;

    public Museums() {
    }

    public Museums(Long id) {
        this.id = id;
    }
}
