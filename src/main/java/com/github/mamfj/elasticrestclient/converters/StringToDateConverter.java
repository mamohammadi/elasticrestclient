package com.github.mamfj.elasticrestclient.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter {

    public static Date toDate(String dateStr) {
        Date date = null;

        if(dateStr != null)
        {
            String pattern = "yyyy-MM-dd";
            java.text.DateFormat dateFormat = new SimpleDateFormat(pattern);
            try {
                date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return date;
    }
}
