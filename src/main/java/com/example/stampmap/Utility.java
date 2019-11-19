package com.example.stampmap;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static String getCurrentDatetime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDatetime = sdf.format(date);
        return currentDatetime;
    }
}
