package com.procs.pcs_.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.json.JSONObject;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_gen")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @ColumnDefault("'not started'::text")
    @Column(name = "state", length = Integer.MAX_VALUE)
    private String state;

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "create_time")
    private LocalDate createTime;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "json_data")
    @JdbcTypeCode(SqlTypes.JSON)
    private JSONObject jsonData;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "user_tasks",
            joinColumns = @JoinColumn(name = "taskid"),
            inverseJoinColumns = @JoinColumn(name = "userid"))
    private UserData user;
}