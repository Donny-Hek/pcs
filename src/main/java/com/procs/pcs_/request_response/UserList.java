package com.procs.pcs_.request_response;

import com.procs.pcs_.model.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserList {
    private int id;
    private char name;
    private String surname;
    private Set<RoleEntity> role;

}
