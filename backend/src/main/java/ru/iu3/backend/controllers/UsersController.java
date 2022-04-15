package ru.iu3.backend.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.iu3.backend.models.Museums;
import ru.iu3.backend.models.Users;
import ru.iu3.backend.repositories.MuseumsRepository;
import ru.iu3.backend.repositories.UsersRepository;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class UsersController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    MuseumsRepository museumsRepository;

    @GetMapping("/users")
    public List getAllUsers() {
        return usersRepository.findAll();
    }

    //curl -d '{"name":"England"}' -H "Content-Type: application/json" -X POST http://localhost:8081/api/v1/таблица
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody Users user) throws Exception {
        try {
            Users nc = usersRepository.save(user);
            return new ResponseEntity<>(nc, HttpStatus.OK);
        } catch (Exception ex) {
            String error;
            if (ex.getMessage().contains("ConstraintViolationException"))
                error = "User_is_already_exists";
            else
                error = "Undefined_Error";
            Map<String, String>
                    map = new HashMap<>();
            map.put("Error", error + "\n");

            return ResponseEntity.ok(map);
        }
    }

    //curl -d '{"name":"ГолландияT"}' -H "Content-Type: application/json" -X PUT http://localhost:8081/api/v1/таблица/айди
    @PutMapping("/users/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable(value = "id") Long userId, @RequestBody Users userDetails) {
        Users user = null;
        Optional<Users> uu = usersRepository.findById(userId);

        if (uu.isPresent()) {
            user = uu.get();
            user.login = userDetails.login;
            user.email = userDetails.email;
            usersRepository.save(user);
            return ResponseEntity.ok(user);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User_is_not_found");
        }
    }


    //curl -H "Content-Type: application/json" -X DELETE http://localhost:8081/api/v1/таблица/айди
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long userId)
    {
        Optional<Users> user = usersRepository.findById(userId);
        Map<String, Boolean> resp = new HashMap<>();

        if (user.isPresent()) {
            usersRepository.delete(user.get());
            resp.put("User_Deleted", Boolean.TRUE);
        }
        else
            resp.put("User_Deleted", Boolean.FALSE);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/users/{id}/addmuseums")
    public ResponseEntity<Object> addMuseums(@PathVariable(value = "id") Long userId,
    @Validated @RequestBody Set<Museums> museums) {
        Optional<Users> uu = usersRepository.findById(userId);
        int cnt = 0;
        if (uu.isPresent()) {
            Users u = uu.get();
            for (Museums m : museums) {
                Optional<Museums> mm = museumsRepository.findById(m.id);
                if (mm.isPresent()) {
                    u.museums.add(mm.get());
                    //u.addMuseum(mm.get());
                    ++cnt;
                }
            }
            usersRepository.save(u);
        }
        Map<String, String> response = new HashMap<>();
        response.put("count = ", String.valueOf(cnt));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/{id}/removemuseums")
    public ResponseEntity<Object> removeMuseums(@PathVariable(value = "id") Long userId,
     @Validated @RequestBody Set<Museums> museums) {
        Optional<Users> uu = usersRepository.findById(userId);
        int cnt = 0;
        if (uu.isPresent()) {
            Users u = uu.get();
            for (Museums m : u.museums) {
                u.removeMuseum(m);
                ++cnt;
            }
            usersRepository.save(u);
        }
        Map<String, String> response = new HashMap<>();
        response.put("count = ", String.valueOf(cnt));
        return ResponseEntity.ok(response);
    }

}
