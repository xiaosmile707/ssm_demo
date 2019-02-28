package com.hp.ssm.dao;

import com.hp.ssm.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentDao {
    List<Comment> getShowComments();
    List<Comment> getCommentsBySubmitId(int submitId);
    List<Comment> getCommentsByMissionId(int missionId);
    void deleteCommentById(int id);
    void deleteCommentsBySubmitId(int submitId);
    void addComment(Comment comment);
    int getCommentCountByMissionId(int missionId);
}
