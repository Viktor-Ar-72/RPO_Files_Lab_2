package ru.iu3.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.iu3.backend.models.Artists;
import ru.iu3.backend.models.Country;
import ru.iu3.backend.models.Museums;
import ru.iu3.backend.models.Users;
import ru.iu3.backend.repositories.ArtistsRepository;
import ru.iu3.backend.repositories.CountryRepositories;

import java.util.*;

@RestController
@RequestMapping("api/v1")
public class ArtistsController {
    @Autowired
    ArtistsRepository artistsRepository;

    @Autowired
    CountryRepositories countryRepositories;

    @GetMapping("/artists")
    public List getAllArtists() {
        return artistsRepository.findAll();
    }

    //curl -d '{"name":"Mone"}' -H "Content-Type: application/json" -X POST http://localhost:8081/api/v1/таблица
    @PostMapping("/artists")
    public ResponseEntity<Object> createArtist(@RequestBody Artists artists) throws Exception {
            try {
                // Извлекаем самостоятельно страну из пришедших данных
                Optional<Country> cc = countryRepositories.findById(artists.country.id);
                if (cc.isPresent()) {
                    artists.country = cc.get();
                }
                // Формируем новый объект класса Artists и сохраняем его в репозиторий
                Artists nc = artistsRepository.save(artists);
                return new ResponseEntity<Object>(nc, HttpStatus.OK);
            } catch (Exception exception) {
                // Указываем тип ошибки
                String error;
                if (exception.getMessage().contains("ConstraintViolationException")) {
                    error = "artistAlreadyExists";
                } else {
                    error = exception.getMessage();
                }

                Map<String, String> map = new HashMap<>();
                map.put("error", error + "\n");

                return ResponseEntity.ok(map);
            }
    }

    @PutMapping("/artists/{id}")
    public ResponseEntity<Artists> updateArtists(@PathVariable(value = "id") Long artistsID, @RequestBody Artists artistDetails) {
        Artists artist = null;
        Optional<Artists> cc = artistsRepository.findById(artistsID);

        if (cc.isPresent()) {
            artist = cc.get();

            artist.name = artistDetails.name;
            artist.age = artistDetails.age;
            artist.country = artistDetails.country;

            artistsRepository.save(artist);
            return ResponseEntity.ok(artist);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found!");
        }
    }

    @DeleteMapping("/artists/{id}")
    public ResponseEntity<Object> deleteArtists(@PathVariable(value = "id") Long artistID) {
        Optional<Artists> artists = artistsRepository.findById(artistID);
        Map<String, Boolean> resp = new HashMap<>();

        // Возвратит true, если объект существует (не пустой)
        if (artists.isPresent()) {
            artistsRepository.delete(artists.get());
            resp.put("Artist_Deleted", Boolean.TRUE);
        } else {
            resp.put("Artist_Deleted", Boolean.FALSE);
        }
        return ResponseEntity.ok(resp);
    }

        @GetMapping("/artists/{id}/paintings")
        public ResponseEntity<Object> getPaintingsFromArtist(@PathVariable(value = "id") Long artistID) {
            Optional<Artists> optionalArtists = artistsRepository.findById(artistID);

            if (optionalArtists.isPresent()) {
                return ResponseEntity.ok(optionalArtists.get().paintings);
            }

            return ResponseEntity.ok(new ArrayList<Museums>());
        }
}
