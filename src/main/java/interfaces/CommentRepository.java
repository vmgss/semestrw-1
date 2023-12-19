package interfaces;

import models.Comment;

import java.util.List;

public interface CommentRepository {
    void createComment(Comment comment);
    List<Comment> getCommentsByTaskId(Long taskId);
    void deleteComment(Long commentId);
}
