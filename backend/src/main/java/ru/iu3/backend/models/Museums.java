package ru.iu3.backend.models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "museums")
@Access(AccessType.FIELD)

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
    @JsonIgnore
    @OneToMany(mappedBy = "museumid")
    public List<Paintings> paintings = new ArrayList<>();

    // Указываем связь "многие-ко-многим". Идём через промежуточную таблицу usermuseums
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "usersmuseums", joinColumns = @JoinColumn(name = "museum_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
            public Set<Users>
            users = new HashSet<>();
}
