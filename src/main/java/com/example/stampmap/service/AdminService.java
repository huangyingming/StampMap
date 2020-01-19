package com.example.stampmap.service;
 
import com.example.stampmap.dao.CommentDao;
import com.example.stampmap.dto.Comment;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class AdminService {
    private List<Comment> comments;
    @Autowired
    private CommentDao commentDao;
    
    public Page<Comment> findPaginated(Pageable pageable) {
        comments = commentDao.readComments();
        return processPaginated(pageable, comments);
    }
    
    public Page<Comment> findPaginated(Pageable pageable, String query) {
        comments = commentDao.readCommentsWithQuery(query);
        return processPaginated(pageable, comments);
    }
    
    
    
    private Page<Comment> processPaginated(Pageable pageable, List<Comment> comments) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Comment> list;
        
        if (comments.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, comments.size());
            list = comments.subList(startItem, toIndex);
        }
        Page<Comment> commentPage
          = new PageImpl<Comment>(list, PageRequest.of(currentPage, pageSize), comments.size());
        return commentPage;
    }
}
