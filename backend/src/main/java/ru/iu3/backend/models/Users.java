package ru.iu3.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс, который использует пользователь
 */
@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
public class Users {
    // ID - первинчый ключ
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    // Поле логина. VARCHAR(MySQL) == String(JAVA)
    @Column(name = "login", unique = true, nullable = false)
    public String login;

    // Скрываем пароль от выведения в запросе
    @JsonIgnore
    @Column(name = "password")
    public String password;

    // Lab_12_Changes: New password
    @Transient
    public String np; // Переменная нового пароля

    // Поле - электронная почта
    @Column(name = "email")
    public String email;

    // Поле - соль (Бугагашенька)
    @JsonIgnore
    @Column(name = "salt")
    public String salt;

    // Поле - токен
    @Column(name = "token")
    public String token;

    // Поле активности
    @Column(name = "activity")
    public LocalDateTime activity;

    // Устанавливаем отношение многим-ко-многим
    // Важно: для отношений многие-ко-многим нужно использовать именно множество, потому что
    // JPA генерирует очень неэффективный код
    @ManyToMany(mappedBy = "users")
    public Set<Museum> museums = new HashSet<>();

    public Users() {}

    /**
     * Конструктор, который принимает в себя ID
     * @param id - ID пользователя
     */
    public Users(Long id) {
        this.id = id;
    }

    /**
     * Метод добавляет музей. По сути, он просто сокращает нам работу
     * @param m - структура музея
     */
    public void addMuseum(Museum m) {
        this.museums.add(m);
        m.users.add(this);
    }

    /**
     * Метод, который осуществляет удаления музея
     * @param m - структура данных музея
     */
    public void removeMuseum(Museum m) {
        this.museums.remove(m);
        m.users.remove(this);
    }
}
