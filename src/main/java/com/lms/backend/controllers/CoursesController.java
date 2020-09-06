package com.lms.backend.controllers;

import com.lms.backend.exceptions.CoursesNotFoundException;
import com.lms.backend.models.Courses;
import com.lms.backend.repositories.CoursesRepository;
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

    @GetMapping(COURSES_PATH + "/{id}")
    public ResponseEntity<Courses> getCoursesById(@PathVariable("id")String id) {
        Optional<Courses>optionalResonse = coursesRepository.findById(id);
        if (optionalResonse.isEmpty()) {
            throw new CoursesNotFoundException(id);
        }
        return new ResponseEntity<>(optionalResonse.get(), HttpStatus.OK);
    }

    @PostMapping(COURSES_PATH)
    public @ResponseBody ResponseEntity<Courses> addCourse(@Validated @RequestBody Courses courses) {
        coursesRepository.save(courses);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @DeleteMapping(COURSES_PATH + "/{id}")
    public void deleteCourse(@PathVariable("id") String id) {

        Optional<Courses> optionalResponse = coursesRepository.findById(id);
        if (optionalResponse.isEmpty()) {

            throw new CoursesNotFoundException(id);
        }
        coursesRepository.deleteById(id);
    }

    @PutMapping(COURSES_PATH + "/{id}")
    public ResponseEntity<Courses> updateCourse(@RequestBody Courses courses, @PathVariable("id") String id) {
        Optional<Courses> optionalResponse = coursesRepository.findById(id);
        if (optionalResponse.isEmpty()) {
            throw new CoursesNotFoundException(id);
        }
        courses.id = id;
        coursesRepository.save(courses);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

}
