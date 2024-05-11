package com.procs.pcs_.request_response;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;
@Data
//@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class SignupRequest {
    private String name;
    private String surname;
    private String email;
    private Set<String> role;
    private String password;
}
