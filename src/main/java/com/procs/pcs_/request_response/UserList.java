package com.procs.pcs_.request_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserList {
    private int id;
    private char name;
    private String surname;
    private Set<String> role;

}
