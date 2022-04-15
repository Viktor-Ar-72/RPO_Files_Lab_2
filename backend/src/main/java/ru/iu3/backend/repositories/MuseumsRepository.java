package ru.iu3.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iu3.backend.models.Museums;

public interface MuseumsRepository extends JpaRepository<Museums, Long> {
}
