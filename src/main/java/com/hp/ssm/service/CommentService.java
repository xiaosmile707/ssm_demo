package com.hp.ssm.service;

import com.hp.ssm.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getShowComments();
    List<Comment> getCommentsBySubmitId(int submitId);
    void deleteCommentById(int id);
    void deleteCommentsBySubmitId(int submitId);
    void addComment(Comment comment);
    int getCommentCountByMissionId(int missionId);
    List<Comment> getCommentsByMissionId(int missionId);
}
