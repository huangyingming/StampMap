package com.example.stampmap;

import com.example.stampmap.dto.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Utility {
    public static String getCurrentDatetime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDatetime = sdf.format(date);
        return currentDatetime;
    }
    
    public static User getCurrentUser() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        return (session.getAttribute("user") != null) ? (User) session.getAttribute("user") : null;
    }
    public static boolean isLoggedIn() {
        return getCurrentUser() != null;
    }
    
    public static int getCurrentUserId() {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return -1;
        } 
        return currentUser.getUserId();
    }
    
    public static boolean isAdmin() {
        User user = getCurrentUser();
        if (user == null || !user.getUserName().equals("admin")) {
            return false;
        }
        return true;
    }
}
