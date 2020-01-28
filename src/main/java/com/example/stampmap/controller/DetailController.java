package com.example.stampmap.controller;

import com.example.stampmap.dao.CommentDao;
import com.example.stampmap.dao.ImageDao;
import com.example.stampmap.dao.UserDao;
import com.example.stampmap.dto.Comment;
import com.example.stampmap.dto.Place;
import com.example.stampmap.dto.User;
import com.example.stampmap.service.DetailService;
import com.example.stampmap.Utility;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 
import org.springframework.ui.Model;


@RequestMapping("/detail")
@Controller
public class DetailController {
    @Autowired
    private DetailService detailService;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserDao userDao;
    @GetMapping("/{placeId}") 
    public String detailRender(@PathVariable int placeId, Model model) {
        boolean isLoggedIn = (Utility.isLoggedIn()) ? true : false;        
        model.addAttribute("isLoggedIn", isLoggedIn);
        Place place = detailService.populatePlace(placeId);
        List<Comment> commentList = commentDao.readComments(placeId);
        model.addAttribute("place", place);
        model.addAttribute("commentList", commentList);
        model.addAttribute("newComment", new Comment());
        detailService.handleRedirectAttributes(model);
        return "detail";
    }
    
    @PostMapping("/{placeId}/image")
    public String addImage(@PathVariable int placeId, @RequestBody MultipartFile[] images, RedirectAttributes redirectAttrs) {
        if (!Utility.isLoggedIn()) {
            redirectAttrs.addFlashAttribute("imageLoggedIn", "notLoggedIn");
            return "redirect:/detail/" + Integer.toString(placeId);
        }
        if (images.length == 0 || images == null || images[0].isEmpty()) {
            return "redirect:/detail/" + Integer.toString(placeId);
        }
        imageDao.addImages(images, placeId);
        return "redirect:/detail/" + Integer.toString(placeId);
    }
    
    @PostMapping("/{placeId}/commentsubmit")
    public String commentSubmit(@PathVariable int placeId, @ModelAttribute Comment newComment, HttpSession session, RedirectAttributes redirectAttrs) {
        if (!Utility.isLoggedIn()) { 
            redirectAttrs.addFlashAttribute("commentLoggedIn", "notLoggedIn");
            return "redirect:/detail/" + Integer.toString(placeId);
        }
        if (newComment.getContent().equals("")) {
            return "redirect:/detail/" + Integer.toString(placeId);
        }
        String currentDateTime = Utility.getCurrentDatetime();
        newComment.setCommentCreatedAt(currentDateTime);
        User user = (User) session.getAttribute("user");
        newComment.setUserId(user.getUserId());
        newComment.setUserName(user.getUserName());
        newComment.setPlaceId(placeId);
        System.out.println(newComment.getContent());
        commentDao.addComment(newComment);
        return "redirect:/detail/" + Integer.toString(placeId);
    }
}
