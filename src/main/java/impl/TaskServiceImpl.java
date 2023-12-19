package impl;

import interfaces.TaskRepository;
import models.Category;
import models.Task;
import service.TaskService;

import java.util.List;

public class TaskServiceImpl implements TaskService {
    //реализация TaskService
    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void createTask(Task task) {
        taskRepository.createTask(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    @Override
    public List<Task> getTasksByCategory(Category category) {
        return taskRepository.getTasksByCategory(category);
    }

    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.getTaskById(taskId);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteTask(taskId);
    }
}

