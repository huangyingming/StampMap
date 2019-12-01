package com.example.stampmap.dao;

import com.example.stampmap.dto.Comment;
import java.util.List;

public interface CommentDao {
    List<Comment> readComments(int placeId);
}
