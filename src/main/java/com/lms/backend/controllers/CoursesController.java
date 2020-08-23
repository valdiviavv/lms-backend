package com.lms.backend.controllers;


import com.lms.backend.models.Courses;
import com.lms.backend.repositories.CoursesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.lms.backend.constants.LmsConstants.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(LMS_V1_PATH)
public class CoursesController {

    private CoursesRepository coursesRepository;

    public CoursesController(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    @GetMapping(COURSES_PATH)
    public ResponseEntity<List<Courses>> getCourses() {
        try {
            List<Courses> courses = coursesRepository.findAll();

            if (courses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
