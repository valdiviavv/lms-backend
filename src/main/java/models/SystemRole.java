package models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
    @Data
    @Document("system_roles")
    public class SystemRole {

        public String id;
        @NotBlank(message = "Please provide the system roles name.")
        public String name;
}
