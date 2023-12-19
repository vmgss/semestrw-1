package service;

import models.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByTaskId(Long taskId);
    void createComment(Comment comment);
    void deleteComment(Long commentId);
}

