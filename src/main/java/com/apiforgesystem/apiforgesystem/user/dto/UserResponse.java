package com.apiforgesystem.apiforgesystem.user.dto;

import com.apiforgesystem.apiforgesystem.user.Role;
import com.apiforgesystem.apiforgesystem.user.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private UserStatus status;

}
