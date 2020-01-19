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
        String sql = "SELECT * FROM comments WHERE place_id=? ORDER BY comment_created_at ASC";
        RowMapper rowMapper = new ColumnMapRowMapper();
        List<Map<String, Object>> resultList = jdbcTemplate.query(sql, rowMapper, placeId);
        List<Comment> commentList = new ArrayList<Comment>();
        for (int i = 0; i < resultList.size(); i++) {
            commentList.add(makeCommentFromRow(resultList.get(i)));
        }
        return commentList;
    }
    
    public List<Comment> readComments() {
        String sql = "SELECT * FROM comments ORDER BY comment_created_at ASC";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
        List<Comment> commentList = new ArrayList<Comment>();
        for (int i = 0; i < resultList.size(); i++) {
            commentList.add(makeCommentFromRow(resultList.get(i)));
        }
        return commentList;
    }
    
    public List<Comment> readCommentsWithQuery(String query) {
        String sql = "SELECT * FROM comments WHERE comments.user_name like ? OR comments.content like ?";
        String strSearch = query.replaceAll("%","\\\\%").replaceAll("_","\\\\_");
        strSearch = "%" + strSearch + "%";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, strSearch, strSearch);
        List<Comment> comments = new ArrayList<Comment>();
        for (int i = 0; i < resultList.size(); i++) {
            Comment comment = makeCommentFromRow(resultList.get(i));
            comments.add(comment);
        }
        return comments;
    }
    
    private Comment makeCommentFromRow(Map<String, Object> row) {
        Comment comment = new Comment();
        comment.setCommentId(Integer.valueOf(row.get("comment_id").toString()));
        comment.setPlaceId(Integer.valueOf(row.get("place_id").toString()));
        comment.setContent(row.get("content").toString());
        comment.setUserId(Integer.valueOf(row.get("user_id").toString()));
        comment.setUserName(row.get("user_name").toString());
        comment.setCommentCreatedAt(row.get("comment_created_at").toString());
        if (row.get("comment_updated_at") != null) {
            comment.setCommentUpdatedAt(row.get("comment_updated_at").toString());
        }
        return comment;
    }
    
    public void addComment(Comment comment) {
        String sql = "INSERT INTO comments VALUES(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, null, comment.getPlaceId(), comment.getContent(), comment.getUserId(), comment.getUserName(), comment.getCommentCreatedAt(),
                comment.getCommentUpdatedAt());
    }
    
    public void deleteComment(int commentId) {
        String sql = "DELETE FROM comments WHERE comment_id = ?";
        jdbcTemplate.update(sql, commentId);
    }
}
