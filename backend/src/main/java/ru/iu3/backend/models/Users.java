package ru.iu3.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
public class Users {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "login", unique = true, nullable = false)
    public String login;

    @JsonIgnore
    @Column(name = "password")
    public String password;

    @Column(name = "email")
    public String email;

    @JsonIgnore
    @Column(name = "salt")
    public String salt;

    // JSON IGNORE был убран в Lab_7, чтобы можно было нормально смотреть текущий токен пользователя
    //@JsonIgnore
    @Column(name = "token")
    public String token;

    @Column(name = "activity")
    public LocalDateTime activity;
    //public String activity;

    // Устанавливаем отношение многим-ко-многим
    // Важно: для отношений многие-ко-многим нужно использовать именно множество, потому что
    // JPA генерирует очень неэффективный код (если верить Mister A.B.)
    @ManyToMany(mappedBy = "users")
    public Set<Museum> museums = new HashSet<>();

    public Users() {}

    public Users(Long id) {
        this.id = id;
    }


    public void addMuseum(Museum m) {
        this.museums.add(m);
        m.users.add(this);
    }

    public void removeMuseum(Museum m) {
        this.museums.remove(m);
        m.users.remove(this);
    }
}
