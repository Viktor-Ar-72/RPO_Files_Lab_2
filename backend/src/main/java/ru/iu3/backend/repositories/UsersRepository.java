package ru.iu3.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iu3.backend.models.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    // Добавлен0 в Lab_7

    // Методы можно создавать таким образом:
    // нет никакой реализации, но
    // при этом на уровне определения JAVA понимает, что нужно обращаться к конкретному полю
    Optional<Users> findByToken (String valueOf);
    Optional<Users> findByLogin (String login);
}
