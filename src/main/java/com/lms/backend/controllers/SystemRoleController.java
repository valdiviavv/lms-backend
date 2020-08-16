package com.lms.backend.controllers;

import com.lms.backend.exceptions.SystemRoleNotFoundException;
import com.lms.backend.models.SystemRole;
import com.lms.backend.repositories.SystemRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.lms.backend.constants.LmsConstants.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(LMS_V1_PATH)
public class SystemRoleController {

    private SystemRoleRepository systemRolesRepository;

    @Autowired
    public SystemRoleController(SystemRoleRepository systemRolesRepository) {
        this.systemRolesRepository = systemRolesRepository;
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    @GetMapping(SYSTEM_ROLES_PATH)
    public ResponseEntity<List<SystemRole>> getAllSystemRoles(@RequestParam(defaultValue = "id,asc") String[] sort) {

        try {
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

            List<SystemRole> systemRoles = systemRolesRepository.findAll(Sort.by(orders));

            if (systemRoles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(systemRoles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(SYSTEM_ROLES_PATH + "/{id}")
    public ResponseEntity<SystemRole> getSystemRolesById(@PathVariable("id") String id) {

        Optional<SystemRole> optionalResponse = systemRolesRepository.findById(id);
        if (!optionalResponse.isPresent()) {

            throw new SystemRoleNotFoundException(id);
        }
        return new ResponseEntity<>(optionalResponse.get(), HttpStatus.OK);
    }

    @PostMapping(SYSTEM_ROLES_PATH)
    public @ResponseBody ResponseEntity<SystemRole> addSystemRoles(@Validated @RequestBody SystemRole systemRoles) {
        systemRolesRepository.save(systemRoles);
        return new ResponseEntity<>(systemRoles, HttpStatus.OK);
    }

    @DeleteMapping(SYSTEM_ROLES_PATH + "/{id}")
    public void deleteSystemRoles(@PathVariable("id") String id) {

        Optional<SystemRole> optionalResponse = systemRolesRepository.findById(id);
        if (!optionalResponse.isPresent()) {

            throw new SystemRoleNotFoundException(id);
        }
        systemRolesRepository.deleteById(id);
    }

    @PutMapping(SYSTEM_ROLES_PATH + "/{id}")
    public ResponseEntity<SystemRole> updateSystemRolesById(@Validated @RequestBody SystemRole systemRoles, @PathVariable("id") String id) {

        Optional<SystemRole> optionalResponse = systemRolesRepository.findById(id);
        if (!optionalResponse.isPresent()) {

            throw new SystemRoleNotFoundException(id);
        }
        systemRoles.id = id;
        systemRolesRepository.save(systemRoles);
        return new ResponseEntity<>(systemRoles, HttpStatus.OK);
    }

}