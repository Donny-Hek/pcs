package com.procs.pcs_.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

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
    private UsersEntity user;

    @ManyToOne()
    private SocietyEntity society;

    public UserData(String name, String surname, UsersEntity userid) {
        this.name = name;
        this.surname = surname;
        this.user = userid;
        this.email = userid.getLogin();
    }
}
