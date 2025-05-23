package com.procs.pcs_.request_response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
//    private int id;
//    private String username;
    private String email;
    private List<String> roles;
}
