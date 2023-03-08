package com.linji.cabinetutil.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Adminstrator on 2018/10/12.
 */

public class DateUtil {
    /**
     * 年-月-日 时:分:秒 显示格式
     */
    // 备注:如果使用大写HH标识使用24小时显示格式,如果使用小写hh就表示使用12小时制格式。
    public static String DATE_TO_STRING_DETAIAL_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年-月-日 显示格式
     */
    public static String DATE_TO_STRING_SHORT_PATTERN = "yyyy-MM-dd";

    private static SimpleDateFormat simpleDateFormat;

    /**
     * Date类型转为指定格式的String类型
     *
     * @param source
     * @param pattern
     * @return
     */
    public static String DateToString(Date source, String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(source);
    }

    /**
     * 指定格式的String类型转为Date类型
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date StringToDate(String date, String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDateToString(long milSecond, String pattern) {
        if (milSecond <= 0) return "---- - -- - -- --:--";
        Date date = new Date(milSecond);
        return DateToString(date, pattern);
    }

    public static String getDateToString2(long milSecond, String pattern) {
        if (milSecond <= 0) return "";
        Date date = new Date(milSecond);
        return DateToString(date, pattern);
    }

    public static String stringDate2Long(String date, String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date parse = simpleDateFormat.parse(date);
            return String.valueOf(parse.getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            return "-1";
        }
    }

    /**
     * 将字符串格式的日期转成时间戳
     *
     * @param date   字符串日期（yyyy-MM-dd...）
     * @param format 日期格式
     * @return 时间戳
     */
    public static Long date2TimeStamp(String date, String format) {
        if (StringUtil.isNotEmptyIgnoreBlank(date)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.parse(date).getTime() / 1000;
            } catch (Exception e) {
                e.printStackTrace();
                return 0L;
            }
        }
        return 0L;
    }

    public static String getNowDateString(String pattern) {
        return DateToString(new Date(), pattern);
    }

    public static String getDayTimeString() {
        return DateToString(new Date(), DATE_TO_STRING_DETAIAL_PATTERN);
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     */
    public static boolean IsToday(Object day) {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = null;
        if (day instanceof String)
            date = StringToDate((String) day, "yyyy-MM-dd");
        else if (day instanceof Long) {
            date = new Date((Long) day);
        }
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TO_STRING_DETAIAL_PATTERN);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }


    /**
     * 提取一个月中的最后一天
     *
     * @param day
     * @return
     */
    public static Date getLastDate(long day) {
        Date date = new Date();
        long date_3_hm = date.getTime() - 3600000 * 34 * day;
        Date date_3_hm_date = new Date(date_3_hm);
        return date_3_hm_date;
    }

    /**
     * 得到二个日期间的间隔时间
     *
     * @return times long类型的毫秒数
     */
    public static long getTwoLongDate(String nowDate, String lastDate) {
        LogUtil.e("nowdate:" + nowDate);
        LogUtil.e("lastdate" + lastDate);
        try {
            Long time1 = strToDateLong(nowDate).getTime();
            Long time2 = strToDateLong(lastDate).getTime();
            return (time2 - time1);
        } catch (ParseException e) {
            LogUtil.e(e.toString());
            LogUtil.e("时间格式不正确");
            return -1L;
        }
    }

    /**
     * 将一个时间段转换成日期
     *
     * @param times 时间段
     * @return string xx天xx时xx分xx秒
     */
    public static String millisecondToDate(long times) {
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long day = 0;
        long hour = 0;
        long minutes = 0;
        long seconds = 0;

        if (times <= 0) return "00|00|00|00";
        day = times / nd;// 计算差多少天
        hour = times % nd / nh;// 计算差多少小时
        minutes = times % nd % nh / nm;// 计算差多少分钟
        seconds = times % nd % nh % nm / ns;// 计算差多少秒

        StringBuilder stringBuilder = new StringBuilder();
        if (day < 10) stringBuilder.append("0").append(day).append("|");
        else stringBuilder.append(day).append("|");
        if (hour < 10) stringBuilder.append("0").append(hour).append("|");
        else stringBuilder.append(hour).append("|");
        if (minutes < 10) stringBuilder.append("0").append(minutes).append("|");
        else stringBuilder.append(minutes).append("|");
        if (seconds < 10) stringBuilder.append("0").append(seconds);
        else stringBuilder.append(seconds);

        return stringBuilder.toString();

//        if (day == 0) {
//            if (hour == 0) {
//                return minutes + ":" + seconds;
//            }
//            return hour + ":" + minutes + ":" + seconds;
//        }
//        return day + "天" + hour + ":" + minutes + ":" + seconds;

    }
    public static String setCountDownTime(Long time) {
        LogUtil.e("倒计时:" + time);
        String[] split = DateUtil.millisecondToDate(time).split("\\|");
        LogUtil.e("倒计时:" + Arrays.asList(split));
        String times;
        if (split[0].equals("00")) {
            if (split[1].equals("00")) {
                times = Integer.valueOf(split[2]) + "分";
                if (split[2].equals("00"))return "";
            } else {
                times = Integer.valueOf(split[1]) + "时" + Integer.valueOf(split[2]) + "分";
            }
        } else {
            if (split[1].equals("00")){
                times = Integer.valueOf(split[0]) + "天" + Integer.valueOf(split[2]) + "分";
            }else {
                times = Integer.valueOf(split[0]) + "天" + Integer.valueOf(split[1]) + "时" + Integer.valueOf(split[2]) + "分";
            }
        }
       return times;
    }
    /**
     * 时间前推或后推分钟,其中JJ表示分钟.
     */
    public static String getPreTime(String sj1, String jj) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mydate1 = "";
        try {
            Date date1 = format.parse(sj1);
            long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
            date1.setTime(Time * 1000);
            mydate1 = format.format(date1);
        } catch (Exception e) {
        }
        return mydate1;
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     */
    public static String getNextDay(String nowdate, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = strToDate(nowdate);
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 判断是否润年
     *
     * @param ddate
     * @return
     */
    public static boolean isLeapYear(String ddate) {

        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        Date d = strToDate(ddate);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        if ((year % 400) == 0)
            return true;
        else if ((year % 4) == 0) {
            if ((year % 100) == 0)
                return false;
            else
                return true;
        } else
            return false;
    }

    /**
     * 获取一个月的最后一天
     *
     * @param dat
     * @return
     */
    public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
        String str = dat.substring(0, 8);
        String month = dat.substring(5, 7);
        int mon = Integer.parseInt(month);
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            str += "31";
        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            str += "30";
        } else {
            if (isLeapYear(dat)) {
                str += "29";
            } else {
                str += "28";
            }
        }
        return str;
    }


    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     *
     * @return
     */
    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1)
            week = "0" + week;
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }


    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

}
