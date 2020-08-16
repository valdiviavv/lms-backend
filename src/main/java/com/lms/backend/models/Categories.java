package com.lms.backend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("categories")
public class Categories {

    @Id
    public String id;

    public String name;

}
