package com.kamiloses.codepred.repository;

import com.kamiloses.codepred.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {



}
