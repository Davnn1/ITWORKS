package com.bca.itworks.service;

import com.bca.itworks.model.Task;
import com.bca.itworks.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
@Slf4j
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public List<Task> getAll(){
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAllByOrderByCreatedDateAsc().forEach(tasks::add);

        if(tasks.isEmpty()){
            log.info("You have no tasks!");
            return new ArrayList<>();
        }
        return tasks;
    }
    public Task addTask(Task task){
        return taskRepository.save(new Task(task.getTitle()));
    }
    public Task updateTask(String id){
        Optional<Task> task = taskRepository.findById(UUID.fromString(id));
        if (task.isPresent()){
            Task existingTask = task.get();
            existingTask.setIsDone(!existingTask.getIsDone());
            existingTask.setModifiedDate(new Date());
            return taskRepository.save(existingTask);
        } else {
            throw new IllegalArgumentException(String.format("task data with id %s not found",id));
        }
    }

    public Task findById(String id) {
        Task task = taskRepository.findById(UUID.fromString(id)).get();
        return task;
    }

}

