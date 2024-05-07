package com.procs.pcs_.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "role", schema = "public", catalog = "pcs_db")
public class RoleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ERole name;
}
