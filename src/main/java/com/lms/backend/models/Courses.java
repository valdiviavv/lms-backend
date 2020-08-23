package com.lms.backend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("courses")
public class Courses {

    @Id
    public String id;

    public String name;
}
