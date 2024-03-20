package com.mabrotodo.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Taskitem, Long> {
    List<Taskitem> findAllByDate(Date date);

    List<Taskitem> findByDate(Date date);

    List<Taskitem> findByDone(boolean done);

    long countByDone(boolean done);
}
