package ru.iu3.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iu3.backend.models.Paintings;

public interface PaintingsRepository extends JpaRepository<Paintings, Long> {
}
