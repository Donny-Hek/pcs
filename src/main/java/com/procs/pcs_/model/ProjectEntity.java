package com.procs.pcs_.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.json.JSONObject;
import org.springframework.boot.jackson.JsonObjectSerializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
@Data
@Table(name = "project")
@NoArgsConstructor
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_id_gen")
//    @SequenceGenerator(name = "project_id_gen", sequenceName = "project_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = Integer.MAX_VALUE, nullable = false)
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "json_data")
    @JdbcTypeCode(SqlTypes.JSON)
//    private Map<String, Object> jsonData;
    private JSONObject jsonData = new JSONObject();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "owner_project",
            joinColumns = @JoinColumn(name = "projectid"),
            inverseJoinColumns = @JoinColumn(name = "managerid"))
    private UserData user;

    @ManyToMany
    @JoinTable(name = "project_workers",
            joinColumns = @JoinColumn(name = "projectid"),
            inverseJoinColumns = @JoinColumn(name = "workerid"))
    private List<UserData> workers = new ArrayList<>();

    public ProjectEntity(String nameSociety, UserData owner) {
        this.name = nameSociety;
        this.user = owner;
        this.startDate = LocalDate.now();
        this.jsonData.put("description", Optional.ofNullable(null));
    }
}