package com.procs.pcs_.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_data", schema = "public", catalog = "pcs_db")
public class UserData {
    @Id
    private int id;
    @Column(name = "name", nullable = true, length = 20)
    private String name;
    @Column(name = "surname", nullable = true, length = 20)
    private String surname;
    @Column(name = "email", length = 20)
    private String email;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    @JsonIgnore
    private UsersEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private SocietyEntity society;

    @Transient
    @OneToMany(fetch = FetchType.LAZY)
    private List<ProjectEntity> ownerOfProjects = new ArrayList<>();

    @Transient
    @ManyToMany(fetch = FetchType.LAZY)
    private List<ProjectEntity> workOnProjects = new ArrayList<>();

    public UserData(String name, String surname, UsersEntity userid) {
        this.name = name;
        this.surname = surname;
        this.user = userid;
        this.email = userid.getLogin();
    }
}
