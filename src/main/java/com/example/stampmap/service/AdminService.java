package com.example.stampmap.service;
 
import com.example.stampmap.dao.CommentDao;
import com.example.stampmap.dao.ImageDao;
import com.example.stampmap.dao.PlaceDao;
import com.example.stampmap.dao.UserDao;
import com.example.stampmap.dto.Comment;
import com.example.stampmap.dto.Image;
import com.example.stampmap.dto.User;
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
    private List<Image> images;
    private List<User> users;
    @Autowired
    private CommentDao commentDao;
    @Autowired 
    private ImageDao imageDao;
    @Autowired
    private PlaceDao placeDao;
    @Autowired
    private UserDao userDao;
    
    public Page<Comment> findCommentPaginated(Pageable pageable) {
        comments = commentDao.readComments();
        return processCommentPaginated(pageable, comments);
    }
    
    public Page<Comment> findCommentPaginated(Pageable pageable, String query) {
        comments = commentDao.readCommentsWithQuery(query);
        return processCommentPaginated(pageable, comments);
    }
    
    private Page<Comment> processCommentPaginated(Pageable pageable, List<Comment> comments) {
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
    
    public Page<Image> findImagePaginated(Pageable pageable) {
        images = imageDao.readImages();
        return processImagePaginated(pageable, images);
    }
    
    private Page<Image> processImagePaginated(Pageable pageable, List<Image> images) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Image> list;
        
        if (images.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, images.size());
            list = images.subList(startItem, toIndex);
        }
        Page<Image> imagePage
          = new PageImpl<Image>(list, PageRequest.of(currentPage, pageSize), images.size());
        return imagePage;
    }
    
    public Page<User> findUserPaginated(Pageable pageable) {
        users = userDao.readUsers();
        return processUserPaginated(pageable, users);
    }
    
    public Page<User> findUserPaginated(Pageable pageable, String query) {
        users = userDao.readUsersWithQuery(query);
        return processUserPaginated(pageable, users);
    }
    
    private Page<User> processUserPaginated(Pageable pageable, List<User> users) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<User> list;
        
        if (users.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, users.size());
            list = users.subList(startItem, toIndex);
        }
        Page<User> userPage
          = new PageImpl<User>(list, PageRequest.of(currentPage, pageSize), users.size());
        return userPage;
    }
    
    public void deletePlace(int placeId) {
        List<String> publicIdList = imageDao.readPublicId(placeId);
        for (String publicId : publicIdList) imageDao.deleteImage(publicId);
        commentDao.deleteCommentWithPlaceId(placeId);
        placeDao.deletePlace(placeId);
    }
}
