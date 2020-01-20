package com.example.stampmap.controller;

import com.example.stampmap.dao.CommentDao;
import com.example.stampmap.dao.ImageDao;
import com.example.stampmap.dao.UserDao;
import com.example.stampmap.dto.Comment;
import com.example.stampmap.dto.Place;
import com.example.stampmap.dto.Image;
import com.example.stampmap.dto.User;
import com.example.stampmap.service.AdminService;
import com.example.stampmap.service.PlaceListService;
import com.example.stampmap.Utility;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;

@RequestMapping("/admin")
@Controller
public class AdminController {
    @Autowired
    private PlaceListService placeListService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private UserDao userDao;
    
    @GetMapping("/placelist")
    public String renderPlaceList(Model model, 
        @RequestParam("page") Optional<Integer> page, 
        @RequestParam("size") Optional<Integer> size,
        @RequestParam("query") Optional<String> query) {
        if (!Utility.isAdmin()) return "redirect:/map";
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        Page<Place> placePage;
        if (query.isPresent() && query.get() != ""){
            placePage = placeListService.findPaginated(PageRequest.of(currentPage - 1, pageSize), query.get());
            model.addAttribute("query", query.get());
        } else {
            placePage = placeListService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        }
        model.addAttribute("placePage", placePage);
        int totalPages = placePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin-place-list";
    }
    
    @GetMapping("/commentlist")
    public String renderCommentList(Model model, 
        @RequestParam("page") Optional<Integer> page, 
        @RequestParam("size") Optional<Integer> size,
        @RequestParam("query") Optional<String> query) {
        if (!Utility.isAdmin()) return "redirect:/map";
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        Page<Comment> commentPage;
        if (query.isPresent() && query.get() != ""){
            commentPage = adminService.findCommentPaginated(PageRequest.of(currentPage - 1, pageSize), query.get());
            model.addAttribute("query", query.get());
        } else {
            commentPage = adminService.findCommentPaginated(PageRequest.of(currentPage - 1, pageSize));
        }
        model.addAttribute("commentPage", commentPage);
        
        int totalPages = commentPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin-comment-list";
    }
    
    @PostMapping("/deletecomment/{commentId}")
    public String deleteComment(@PathVariable int commentId) {
        commentDao.deleteComment(commentId);
        return "redirect:/admin/commentlist";
    }
    
    @GetMapping("/imagelist")
    public String renderImageList(Model model, 
        @RequestParam("page") Optional<Integer> page, 
        @RequestParam("size") Optional<Integer> size,
        @RequestParam("query") Optional<String> query) {
        if (!Utility.isAdmin()) return "redirect:/map";
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        Page<Image> imagePage;
        imagePage = adminService.findImagePaginated(PageRequest.of(currentPage - 1, pageSize));    
        model.addAttribute("imagePage", imagePage);
        int totalPages = imagePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin-image-list";
    }
    
    @PostMapping("/deleteimage/{imageId}/stamps/{publicId}")
    public String deleteImage(@PathVariable int imageId, @PathVariable String publicId) {
        imageDao.deleteImage(imageId, publicId);
        return "redirect:/admin/imagelist";
    }
    
    @GetMapping("userlist")
    public String renderUserList(Model model, 
        @RequestParam("page") Optional<Integer> page, 
        @RequestParam("size") Optional<Integer> size,
        @RequestParam("query") Optional<String> query) {
        if (!Utility.isAdmin()) return "redirect:/map";
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        Page<User> userPage;
        if (query.isPresent() && query.get() != ""){
            userPage = adminService.findUserPaginated(PageRequest.of(currentPage - 1, pageSize), query.get());
            model.addAttribute("query", query.get());
        } else {
            userPage = adminService.findUserPaginated(PageRequest.of(currentPage - 1, pageSize));
        }
        model.addAttribute("userPage", userPage);
        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin-user-list";
    }
    
    @PostMapping("/deleteuser/{userId}")
    public String deleteUser(@PathVariable int userId) {
        userDao.deleteUser(userId);
    }
}
