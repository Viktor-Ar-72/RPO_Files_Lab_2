package ru.iu3.backend.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.iu3.backend.models.Artists;

public interface ArtistsRepository extends JpaRepository<Artists, Long> {
}