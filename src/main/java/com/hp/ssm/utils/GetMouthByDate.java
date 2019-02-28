package com.hp.ssm.utils;

import com.hp.ssm.exception.DateTransException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetMouthByDate {
    private static volatile SimpleDateFormat simpleDateFormat;

    public static String getMouth(Date date) throws Exception {
        String mouth = null;
        if (simpleDateFormat == null) {
            synchronized (GetMouthByDate.class) {
                if (simpleDateFormat == null) {
                    simpleDateFormat = new SimpleDateFormat("MM");
                }
            }
        }
        int number = Integer.valueOf(simpleDateFormat.format(date));
        mouth = convert(number);

        return mouth;
    }

    public static String convert(int mouth) throws Exception {
        String res = null;
        switch (mouth) {
            case 1:
                res = "Jun";
                break;
            case 2:
                res = "Feb";
                break;
            case 3:
                res = "Mar";
                break;
            case 4:
                res = "Apr";
                break;
            case 5:
                res = "May";
                break;
            case 6:
                res = "Jun";
                break;
            case 7:
                res = "Jul";
                break;
            case 8:
                res = "Aug";
                break;
            case 9:
                res = "Sep";
                break;
            case 10:
                res = "Oct";
                break;
            case 11:
                res = "Nov";
                break;
            case 12:
                res = "Dec";
                break;
            default:
                throw new DateTransException("月份出错");
        }
        return res;
    }
}
