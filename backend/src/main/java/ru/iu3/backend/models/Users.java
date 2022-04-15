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
    public Users() {
    }

    public Users(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public long id;

    @Column(name = "login", nullable = false, unique = true)
    public String login;

    @JsonIgnore
    @Column(name = "password")
    public String password;


    @Column(name = "email")
    public String email;

    @JsonIgnore
    @Column(name = "salt")
    public String salt;

    @JsonIgnore
    @Column(name = "token")
    public String token;

    @Column(name = "activity")
    public LocalDateTime activity;

    @ManyToMany(mappedBy = "users")
    public Set<Museums> museums = new HashSet<>();

    public void addMuseum(Museums m) {
        this.museums.add(m);
        m.users.add(this);
    }
    public void removeMuseum(Museums m) {
        this.museums.remove(m);
        m.users.remove(this);
    }
}