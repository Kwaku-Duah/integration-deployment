package com.threads.concurrency.Controllers;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import com.threads.concurrency.Services.TaskService;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "tasks")
public class TaskEndpoint {

    private final TaskService taskService;

    public TaskEndpoint(TaskService taskService) {
        this.taskService = taskService;
    }

    @ReadOperation
    public int getActiveTasks() {
        return taskService.getAllTasks().size();
    }
}
