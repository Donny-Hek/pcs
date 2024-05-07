package com.procs.pcs_.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "users", schema = "public", catalog = "pcs_db",
        uniqueConstraints = {@UniqueConstraint(columnNames = "login")})
public class UsersEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "roleid"))
    private Set<RoleEntity> roles = new HashSet<>();

//    @OneToOne(fetch = FetchType.LAZY,mappedBy = "user")
//    private UserData userData;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private SocietyEntity society;

    public UsersEntity(String email, String password) {
        this.login = email;
        this.password = password;
    }
}
