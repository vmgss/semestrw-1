package service;

import models.Category;
import models.Task;

import java.util.List;

public interface TaskService {
    void createTask(Task task);
    List<Task> getAllTasks();
    List<Task> getTasksByCategory(Category category);
    Task getTaskById(Long taskId);
    void deleteTask(Long taskId);
}

