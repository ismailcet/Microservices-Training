package com.ismailcet.emailservice.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Customer {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
