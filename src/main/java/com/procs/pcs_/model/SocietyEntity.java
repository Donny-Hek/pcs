package com.procs.pcs_.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "society", schema = "public", catalog = "pcs_db")
@AllArgsConstructor
@NoArgsConstructor
public class SocietyEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "users_society",
            joinColumns = @JoinColumn(name = "societyid"),
            inverseJoinColumns = @JoinColumn(name = "userid"))
    private List<UserData> usersList = new ArrayList<>();

    public SocietyEntity(String nameSociety,UserData newUser) {
        this.name = nameSociety;
        this.usersList.add(newUser);
    }

    public void addUserToList(UserData user) {
        this.usersList.add(user);
    }
}
