package com.lms.backend.controllers;


import com.lms.backend.exceptions.UserNotFoundException;
import com.lms.backend.models.Users;
import com.lms.backend.repositories.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.lms.backend.constants.LmsConstants.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(LMS_V1_PATH)
public class UsersController {

    private UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping(USERS_PATH)
    public ResponseEntity<List<Users>> getUsers() {
        try {
            List<Users> users = usersRepository.findAll();

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(USERS_PATH + "/{id}")
    public ResponseEntity<Users>getUserById(@PathVariable("id")String id) {

        Optional<Users> optionalResponse = usersRepository.findById(id);
        if (optionalResponse.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return new ResponseEntity<>(optionalResponse.get(),HttpStatus.OK);
    }

    @DeleteMapping(USERS_PATH + "/{id}")
    public void deleteUser(@PathVariable("id") String id) {

        Optional<Users> optionalResponse = usersRepository.findById(id);
        if(optionalResponse.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        usersRepository.deleteById(id);
    }


    @PutMapping(USERS_PATH + "/{id}")
    public ResponseEntity<Users> updateUser(@RequestBody Users users,
                                            @PathVariable("id")String id) {
        Optional<Users> optionalResponse = usersRepository.findById(id);
        if(optionalResponse.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        users.id = id;
        usersRepository.save(users);
        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @PostMapping(USERS_PATH)
    public @ResponseBody ResponseEntity<Users>addUser(@Validated @RequestBody Users users) {
        usersRepository.save(users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }




}// end












