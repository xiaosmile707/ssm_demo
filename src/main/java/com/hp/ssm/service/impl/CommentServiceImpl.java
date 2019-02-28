package com.hp.ssm.service.impl;

import com.hp.ssm.dao.CommentDao;
import com.hp.ssm.model.Comment;
import com.hp.ssm.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Override
    public List<Comment> getShowComments() {
        return commentDao.getShowComments();
    }

    @Override
    public List<Comment> getCommentsBySubmitId(int submitId) {
        return commentDao.getCommentsBySubmitId(submitId);
    }

    @Override
    public void deleteCommentById(int id) {
        commentDao.deleteCommentById(id);
    }

    @Override
    public void deleteCommentsBySubmitId(int submitId) {
        commentDao.deleteCommentsBySubmitId(submitId);
    }

    @Override
    public void addComment(Comment comment) {
        commentDao.addComment(comment);
    }

    @Override
    public int getCommentCountByMissionId(int missionId) {
        return commentDao.getCommentCountByMissionId(missionId);
    }

    @Override
    public List<Comment> getCommentsByMissionId(int missionId) {
        return commentDao.getCommentsByMissionId(missionId);
    }
}
