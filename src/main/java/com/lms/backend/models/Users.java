package com.lms.backend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("users")
public class Users {

    @Id
    public String id;

    public String firstName;
    public String lastName;
    public String email;
    public String password;

}
