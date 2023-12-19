package models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Comment {
    private Long commentId;
    private Task task;
    private User user;
    private String text;
    private Date createdAt;
    public Comment(Long commentId, Task task, User user, String text, Date createdAt) {
        this.commentId = commentId;
        this.task = task;
        this.user = user;
        this.text = text;
        this.createdAt = createdAt;
    }


}
