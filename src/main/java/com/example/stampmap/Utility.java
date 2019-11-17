/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.stampmap;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author aaa
 */
public class Utility {
    public static String getCurrentDatetime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDatetime = sdf.format(date);
        return currentDatetime;
    }
    
}
