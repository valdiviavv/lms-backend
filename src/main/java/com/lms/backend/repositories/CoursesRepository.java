package com.lms.backend.repositories;

import com.lms.backend.models.Courses;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CoursesRepository extends MongoRepository<Courses, String> {
}
