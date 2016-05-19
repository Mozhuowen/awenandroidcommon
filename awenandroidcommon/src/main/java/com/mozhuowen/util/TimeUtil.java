package com.mozhuowen.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Awen on 16/2/24.
 * Email:mozhuowen@gmail.com
 */
public class TimeUtil {

    private static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat monthFormat = new SimpleDateFormat("MM-dd HH:mm");
    private static SimpleDateFormat lastDayFormat = new SimpleDateFormat("昨天 HH:mm");
    private static SimpleDateFormat monthDayFormat = new SimpleDateFormat("M月d日");

    public static String getWeekStr(Calendar time) {
        int weekday = time.get(Calendar.DAY_OF_WEEK);
        String[] weekstrs = new String[]{"周日","周一","周二","周三","周四","周五","周六",};
        return weekstrs[weekday-1];
    }

    public static String prettyTime(long targetTime) {

        long now = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(now));
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int nowDay = calendar.get(Calendar.DAY_OF_MONTH);

        Date targetDate = new Date(targetTime);
        calendar.setTime(targetDate);
        int targetYear = calendar.get(Calendar.YEAR);
        int targetMonth = calendar.get(Calendar.MONTH) + 1;
        int targetDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (nowYear == targetYear) {

            if (nowMonth == targetMonth) {

                if (nowDay == targetDay) {

                    long interval = now - targetTime;
                    System.err.println(interval);
                    if (interval < 60 * 1000) {
                        return "刚刚";
                    } else if (interval < 60 * 60 * 1000) {
                        return interval / 60 / 1000 + "分钟前";
                    } else {
                        return interval / 60 / 60 / 1000 + "小时前";
                    }

                } else if (nowDay - targetDay == 1) {
                    return lastDayFormat.format(targetDate);
                } else {
                    return monthFormat.format(targetDate);
                }

            } else {
                return monthFormat.format(targetDate);
            }
        } else {

            return yearFormat.format(targetDate);
        }
    }

    public static String getDeathLine(long targetTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(targetTime);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return year+"-"+month+"-"+day;
    }
}
