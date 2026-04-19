package com.embarcados.api.features.driver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "driver")
@NoArgsConstructor
@AllArgsConstructor
public class DriverEntity {

    @Id
    private String id;
    private String name;
    private String email;
    private String document;
    private String password;
    private String companyId;
}
package com.embarcados.api.features.driver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection = "driver")
@NoArgsConstructor
@AllArgsConstructor
public class DriverEntity {

    @Id
    private String id;
    private String name;
    private String email;
    private String document;
    private String password;

    private String companyId;
}
