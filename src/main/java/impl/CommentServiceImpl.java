package impl;

import interfaces.CommentRepository;
import models.Comment;
import service.CommentService;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    //реализация CommentService
    private CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getCommentsByTaskId(Long taskId) {
        return commentRepository.getCommentsByTaskId(taskId);
    }

    @Override
    public void createComment(Comment comment) {
        commentRepository.createComment(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteComment(commentId);
    }
}

