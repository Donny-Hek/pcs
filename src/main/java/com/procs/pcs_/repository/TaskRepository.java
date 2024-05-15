package com.procs.pcs_.repository;

import com.procs.pcs_.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Integer, TaskEntity> {
}
