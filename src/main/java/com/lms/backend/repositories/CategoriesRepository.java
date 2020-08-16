package com.lms.backend.repositories;

import com.lms.backend.models.Categories;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoriesRepository extends MongoRepository<Categories, String> {



}
