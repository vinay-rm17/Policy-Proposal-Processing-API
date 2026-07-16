package org.example.p3api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private Long customerId;

    private String firstName;
    private String lastName;
    private Integer age;
    private String gender;
    private String email;
    private String mobileNumber;
    private String panNumber;

}
