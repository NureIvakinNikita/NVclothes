package com.example.nvclothes.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Builder
public class ClientDto {

    private Long id;
    @NotBlank(message = "Name cannot be empty.")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Name isn't assigned properly it must contain letters from a to z and A to Z.")
    private String name;

    @NotBlank(message = "Last name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Last name isn't assigned properly it must contain letters from a to z and A to Z.")
    private String lastName;

    @NotBlank(message = "Email cannot be empty")
    @Pattern(regexp = "^[\\w_.+-]*[\\w_.]@(?:[\\w]+\\.)+[a-zA-Z]{2,7}$", message = "Email isn't assigned properly.")
    private String email;

    @NotBlank(message = "Password cannot be empty.")
    @Length(min = 10, max = 30, message = "Password length must contain from 10 to 30 symbols.")
    private String password;

    @Transient
    @NotBlank(message = "Confirmation of password can not be null!")
    @Length(min = 10, max = 30, message = "Password length must contain from 10 to 30 symbols.")
    private String password2;

    @Column(name = "telephone_number")
    @NotBlank(message = "Telephone number cannot be empty.")
    @Pattern(regexp = "^\\+?(?:38)?0?(\\d{9})$", message = "Invalid phone number.")
    private String telephoneNumber;

}
