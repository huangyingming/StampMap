package com.example.stampmap.controller;

import com.example.stampmap.dao.CommentDao;
import com.example.stampmap.dao.ImageDao;
import com.example.stampmap.dto.Comment;
import com.example.stampmap.dto.Place;
import com.example.stampmap.service.DetailService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
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
    @GetMapping("/{placeId}") 
    public String detailRender(@PathVariable int placeId, Model model) {
        Place place = detailService.populatePlace(placeId);
        List<Comment> commentList = commentDao.readComments(placeId);
        model.addAttribute("place", place);
        model.addAttribute("commentList", commentList);
        return "detail";
    }
    
    @PostMapping("/{placeId}/image")
    public String addImage(@PathVariable int placeId, @RequestBody MultipartFile[] images) {
        imageDao.addImages(images, placeId);
        return "redirect:/detail/" + Integer.toString(placeId);
    }
}
