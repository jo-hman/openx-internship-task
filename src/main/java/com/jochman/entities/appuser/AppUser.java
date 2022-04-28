package com.jochman.entities.appuser;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    private Address address;
    private Integer id;
    private String email;
    private String password;
    private Name name;
    private String phone;
    private Long __v;
}
