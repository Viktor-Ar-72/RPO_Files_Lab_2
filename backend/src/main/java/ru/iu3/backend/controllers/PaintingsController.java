package ru.iu3.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.iu3.backend.models.Paintings;
import ru.iu3.backend.repositories.MuseumsRepository;
import ru.iu3.backend.repositories.PaintingsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")

public class PaintingsController {
    @Autowired
    PaintingsRepository paintingsRepository;

    @Autowired
    MuseumsRepository museumsRepository;

    @GetMapping("/paintings")
    public List getAllPaintings() {
        return paintingsRepository.findAll();
    }

    @PostMapping("/paintings")
    public ResponseEntity<Object> createPainting(@RequestBody Paintings painting) {
        try {
            Paintings newPainting = paintingsRepository.save(painting);
            return new ResponseEntity<Object>(newPainting, HttpStatus.OK);
        } catch (Exception exception) {
            // Указываем тип ошибки
            String error;
            if (exception.getMessage().contains("ConstraintViolationException")) {
                error = "PaintingIsAlreadyExists";
            } else {
                error = exception.getMessage();
            }

            Map<String, String> map = new HashMap<>();
            map.put("error", error + "\n");

            return ResponseEntity.ok(map);
        }
    }

    @PutMapping("/paintings/{id}")
    public ResponseEntity<Paintings> updatePainting(@PathVariable(value = "id") Long id,
                                                   @RequestBody Paintings paintingDetails) {
        Paintings painting = null;
        Optional<Paintings> cc = paintingsRepository.findById(id);

        if (cc.isPresent()) {
            painting = cc.get();

            painting.name = paintingDetails.name;
            painting.museumid = paintingDetails.museumid;
            painting.artistid = paintingDetails.artistid;
            painting.year = paintingDetails.year;

            paintingsRepository.save(painting);

            return ResponseEntity.ok(painting);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Painting_not_found");
        }
    }

    @DeleteMapping("/paintings/{id}")
    public ResponseEntity<Object> deletePainting(@PathVariable(value = "id") Long paintingID) {
        Optional<Paintings> cc = paintingsRepository.findById(paintingID);
        Map<String, Boolean> resp = new HashMap<>();

        if (cc.isPresent()) {
            paintingsRepository.delete(cc.get());
            resp.put("Painting_Deleted", Boolean.TRUE);
        } else {
            resp.put("Painting_Deleted", Boolean.FALSE);
        }

        return ResponseEntity.ok(resp);
    }
}