package ru.iu3.backend.controllers;


import org.springframework.web.server.ResponseStatusException;
import ru.iu3.backend.models.Artists;
import ru.iu3.backend.models.Country;
import ru.iu3.backend.repositories.ArtistsRepository;
import ru.iu3.backend.repositories.CountryRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class CountryController {
    @Autowired
    CountryRepositories countryRepository;

    @Autowired
    ArtistsRepository artistsRepository;

    @GetMapping("/countries")
    public List getAllCountries() {
        return countryRepository.findAll();
    }

    @GetMapping("/countries/{id}/artists")
    public ResponseEntity<List<Artists>> getCountryArtists(@PathVariable(value = "id") Long id) {
            //Optional<Country> cc = countryRepository.findById(countryId);
        Optional<Country> cc = countryRepository.findById(id);
            if (cc.isPresent()) {
            return ResponseEntity.ok(cc.get().artists);
            }
            return ResponseEntity.ok(new ArrayList<Artists>());
}


    // @PostMapping("/countries")
    // public ResponseEntity<Object> createCountry(@RequestBody Country country) {
    //    Country nc = countryRepository.save(country);
    //    return new ResponseEntity<Object>(nc, HttpStatus.OK);

    //curl -d '{"name":"England"}' -H "Content-Type: application/json" -X POST http://localhost:8081/api/v1/таблица
    @PostMapping("/countries")
    public ResponseEntity<Object> createCountry(@RequestBody Country country) throws Exception {
        try {
            Country nc = countryRepository.save(country);
            return new ResponseEntity<Object>(nc, HttpStatus.OK);
        } catch (Exception ex) {
            String error;
            if (ex.getMessage().contains("countries.name_UNIQUE"))
                error = "Country_is_already_exists";
            else
                error = "Undefined_Error";
            Map<String, String>
                    map = new HashMap<>();
            map.put("Error", error);
            //return new ResponseEntity<Object> (map, HttpStatus.OK);
            return ResponseEntity.ok(map);
        }
    }

    //curl -d '{"name":"ГолландияT"}' -H "Content-Type: application/json" -X PUT http://localhost:8081/api/v1/таблица/айди
    @PutMapping("/countries/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable(value = "id") Long countryId, @RequestBody Country countryDetails) {
        Country country = null;
        Optional<Country> cc = countryRepository.findById(countryId);

        if (cc.isPresent()) {
            country = cc.get();
            country.name = countryDetails.name;
            countryRepository.save(country);
            return ResponseEntity.ok(country);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country_is_not_found");
        }
    }


    //curl -H "Content-Type: application/json" -X DELETE http://localhost:8081/api/v1/таблица/айди
    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Object> deleteCountry(@PathVariable(value = "id") Long countryId)
    {
        Optional<Country> country = countryRepository.findById(countryId);
        Map<String, Boolean> resp = new HashMap<>();

        if (country.isPresent()) {
        countryRepository.delete(country.get());
        resp.put("Country_Deleted", Boolean.TRUE);
        }
        else
        resp.put("Country_Deleted", Boolean.FALSE);
        return ResponseEntity.ok(resp);
    }

}
