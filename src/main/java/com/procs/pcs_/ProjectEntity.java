package com.procs.pcs_;

import com.procs.pcs_.model.UserData;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_id_gen")
//    @SequenceGenerator(name = "project_id_gen", sequenceName = "project_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = Integer.MAX_VALUE, nullable = false)
    private String name;

    @Column(name = "json_data")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> jsonData;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "owner_project",
            joinColumns = @JoinColumn(name = "projectid"),
            inverseJoinColumns = @JoinColumn(name = "managerid"))
    private UserData users;

    @ManyToMany
    @JoinTable(name = "project_workers",
            joinColumns = @JoinColumn(name = "projectid"),
            inverseJoinColumns = @JoinColumn(name = "workerid"))
    private List<UserData> workers = new ArrayList<>();
}