package com.example.stampmap.dao;

import com.example.stampmap.dto.Comment;
import java.util.List;

public interface CommentDao {
    List<Comment> readComments(int placeId);
    List<Comment> readComments();
    List<Comment> readCommentsWithQuery(String query);
    void addComment(Comment comment);
    void deleteComment(int commentId);
}
