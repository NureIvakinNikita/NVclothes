package com.example.nvclothes.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String telephoneNumber;

}
