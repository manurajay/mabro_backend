package com.mabrotodo.Task;


import jakarta.persistence.Id;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.LabelUI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(path = "/mabro_tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/add")
    public Taskitem addTask(@Valid @RequestBody Taskitem todo, @RequestParam(required = false) Date date) {
        if (date != null) {
            todo.setDate(date);
        }
        return taskRepository.save(todo);
    }

    @GetMapping("/get")
    public List<Taskitem> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public List<Taskitem> getTasks(@PathVariable Long id) {
        return taskRepository.findAllById(Collections.singletonList(id));
    }

    @GetMapping("/getByDate")
    public ResponseEntity<List<Taskitem>> getTaskByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date) {
        List<Taskitem> tasks = taskRepository.findByDate(date);

        if (!tasks.isEmpty()) {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }






    @PutMapping("update/{id}")
    public ResponseEntity updateTask(@PathVariable int id, @RequestParam(required = false) Date date) {
        boolean exist = taskRepository.existsById((long) id);
        if (exist) {
            Taskitem task = taskRepository.getReferenceById((long) id);
            if (date != null) {
                task.setDate(date);
            }
            boolean done = task.isDone();
            task.setDone(!done);
            taskRepository.save(task);
            return new ResponseEntity<>("Task is updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Task is not exist", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTask(@PathVariable Long id) {
        boolean exist = taskRepository.existsById(id);
        if (exist) {
            taskRepository.deleteById(id);
            return new ResponseEntity<>("Task is deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Task is not exist", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/completedTaskCount")
    public ResponseEntity<Map<String, Long>> getcompletedTaskCount(){
        long completedCount = taskRepository.countByDone(true);
//        long pendingCount = taskRepository.countByDone(false);

        Map<String, Long> taskCounts = new HashMap<>();
        taskCounts.put("completed", completedCount);
//        taskCounts.put("pending", pendingCount);

        return ResponseEntity.ok(taskCounts);
    }

    @GetMapping("/pendingTaskCount")
    public ResponseEntity<Map<String, Long>> getpendingTaskCount(){
//        long completedCount = taskRepository.countByDone(true);
        long pendingCount = taskRepository.countByDone(false);

        Map<String, Long> taskCounts = new HashMap<>();
//        taskCounts.put("completed", completedCount);
        taskCounts.put("pending", pendingCount);

        return ResponseEntity.ok(taskCounts);
    }
}
