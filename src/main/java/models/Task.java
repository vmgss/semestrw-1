package models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@Builder

public class Task {
    private Long taskId;
    private String title;
    private String description;
    private Date createdAt;
    private Date dueDate;
    private Category category;

    public Task(Long taskId, String title, String description, Date createdAt, Date dueDate, Category category) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.category = category;
    }
}

