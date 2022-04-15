package ru.iu3.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ru.iu3.backend.models.Museums;
import ru.iu3.backend.models.Paintings;
import ru.iu3.backend.repositories.MuseumsRepository;

import java.util.*;

@RestController
@RequestMapping("api/v1")
public class MuseumsController {
    @Autowired
    MuseumsRepository museumRepository;

    @GetMapping("/museums")
    public List getAllCountries() {
        return museumRepository.findAll();
    }

    @PostMapping("/museums")
    public ResponseEntity<Object> createMuseum(@RequestBody Museums museum) throws Exception {
        try {
            // Попытка сохранить что-либо в базу данных
            Museums newMuseum = museumRepository.save(museum);
            return new ResponseEntity<Object>(newMuseum, HttpStatus.OK);
        } catch (Exception exception) {
            // Указываем тип ошибки
            String error;
            if (exception.getMessage().contains("ConstraintViolationException")) {
                error = "MuseumIsAlreadyExists";
            } else {
                error = exception.getMessage();
            }

            Map<String, String> map = new HashMap<>();
            map.put("error", error + "\n");

            return ResponseEntity.ok(map);
        }
    }

    @PutMapping("/museums/{id}")
    public ResponseEntity<Museums> updateCountry(@PathVariable(value = "id") Long museumID,
                                                @RequestBody Museums museumDetails) {
        Museums museum = null;
        Optional<Museums> cc = museumRepository.findById(museumID);

        if (cc.isPresent()) {
            museum = cc.get();

            museum.name = museumDetails.name;
            museum.location = museumDetails.location;

            museumRepository.save(museum);
            return ResponseEntity.ok(museum);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Museum not found");
        }
    }

    @DeleteMapping("/museums/{id}")
    public ResponseEntity<Object> deleteCountry(@PathVariable(value = "id") Long museumID) {
        Optional<Museums> museum = museumRepository.findById(museumID);
        Map<String, Boolean> resp = new HashMap<>();

        // Возвратит true, если объект существует (не пустой)
        if (museum.isPresent()) {
            museumRepository.delete(museum.get());
            resp.put("Museum_Deleted", Boolean.TRUE);
        } else {
            resp.put("Museum_Deleted", Boolean.FALSE);
        }

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/museums/{id}/paintings")
    public ResponseEntity<List<Paintings>> getPaintingMuseums(@PathVariable(value = "id") Long museumID) {
        Optional<Museums> cc = museumRepository.findById(museumID);
        if (cc.isPresent()) {
            return ResponseEntity.ok(cc.get().paintings);
        }
        return ResponseEntity.ok(new ArrayList<Paintings>());
    }
}