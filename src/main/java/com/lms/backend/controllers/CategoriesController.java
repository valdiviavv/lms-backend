package com.lms.backend.controllers;

import com.lms.backend.exceptions.CategoriesNotFoundException;
import com.lms.backend.models.Categories;
import com.lms.backend.repositories.CategoriesRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.lms.backend.constants.LmsConstants.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(LMS_V1_PATH)
public class CategoriesController {

    private CategoriesRepository categoriesRepository;

    public CategoriesController(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    @GetMapping(CATEGORIES_PATH)
    public ResponseEntity<List<Categories>>getAllCategories(@RequestParam(defaultValue = "id,asc") String[] sort) {

        try{
            List<Sort.Order> orders = new ArrayList<Sort.Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Categories> categories = categoriesRepository.findAll(Sort.by(orders));

            if (categories.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(categories, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }//end getAllCategories

    @GetMapping(CATEGORIES_PATH + "/{id}")
    public ResponseEntity<Categories> getCategoriesById(@PathVariable("id") String id) {

        Optional<Categories> optionalResponse = categoriesRepository.findById(id);
        if (optionalResponse.isEmpty()) {
            throw new CategoriesNotFoundException(id);
        }
        return new ResponseEntity<>(optionalResponse.get(),HttpStatus.OK);
    }

    @PostMapping(CATEGORIES_PATH)
    public @ResponseBody ResponseEntity<Categories> addCategory(@Validated @RequestBody Categories categories) {
        categoriesRepository.save(categories);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @DeleteMapping(CATEGORIES_PATH + "/{id}")
    public void deleteCategory(@PathVariable("id") String id) {

        Optional<Categories> optionalResponse = categoriesRepository.findById(id);
        if (optionalResponse.isEmpty()) {

            throw new CategoriesNotFoundException(id);
        }
        categoriesRepository.deleteById(id);
    }



    @PutMapping(CATEGORIES_PATH + "/{id}")
    public ResponseEntity<Categories> updateCategory(@RequestBody Categories categories,
                                                     @PathVariable("id") String id) {
        Optional<Categories> optionalResponse = categoriesRepository.findById(id);
        if (optionalResponse.isEmpty()) {
            throw new CategoriesNotFoundException(id);
        }
        categories.id = id;
        categoriesRepository.save(categories);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
