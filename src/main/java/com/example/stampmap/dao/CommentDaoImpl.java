package com.example.stampmap.dao;

import com.example.stampmap.dto.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDaoImpl implements CommentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<Comment> readComments(int placeId) {
        String sql = "SELECT * FROM comments WHERE placeId=? ORDER BY comment_created_at ASC";
        RowMapper rowMapper = new ColumnMapRowMapper();
        List<Map<String, Object>> resultList = jdbcTemplate.query(sql, rowMapper, placeId);
        List<Comment> commentList = new ArrayList<Comment>();
        for (int i = 0; i < resultList.size(); i++) {
            commentList.add(makeCommentFromRow(resultList.get(i)));
        }
        return commentList;
    }
    
    private Comment makeCommentFromRow(Map<String, Object> row) {
        Comment comment = new Comment();
        comment.setCommentId(Integer.valueOf(row.get("comment_id").toString()));
        comment.setPlaceId(Integer.valueOf(row.get("place_id").toString()));
        comment.setComment(row.get("comment").toString());
        comment.setUserId(Integer.valueOf(row.get("user_id").toString()));
        comment.setCommentCreatedAt(row.get("comment_created_at").toString());
        if (row.get("comment_updated_at") != null) {
            comment.setCommentUpdatedAt(row.get("comment_updated_at").toString());
        }
        return comment;
    }
}
