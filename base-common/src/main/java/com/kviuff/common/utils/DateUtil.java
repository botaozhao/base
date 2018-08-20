package com.kviuff.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期处理工具
 *
 * @author panda
 */
public class DateUtil extends org.apache.commons.lang3.time.DateUtils {
    /**
     * 私有化构造
     */
    private DateUtil() {
    }

    /**
     * 日志信息
     */
    private final static Logger log = LoggerFactory.getLogger(DateUtil.class);

    /**
     *
     */
    public static final String DEFAULT_FORMAT = "yyyyMMddHHmmssSSS";
    public static final String DEFAULT_FORMAT_STRING = "yyyyMMddHHmmss";
    public static final String YMD = "yyyyMMdd";
    public static final String YMD_ = "yyyy-MM-dd";
    public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD$HMS = "yyyy/MM/dd HH:mm:ss";
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};


    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 获取当前时间
     *
     * @return Timestamp
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * Date转string 获取时间YMDHHmmss 获取当前时间
     *
     * @return String
     */
    public static String getDateString() {
        return DateTime.now().toString(DEFAULT_FORMAT_STRING);
    }

    /**
     * Date转string 获取时间YMDHHmmss 获取当前时间
     *
     * @return String
     */
    public static String getDateStringFFF() {
        return DateTime.now().toString(DEFAULT_FORMAT);
    }

    /**
     * String 日期转DATE
     *
     * @return DATE
     */
    public static Date parse(String strDate) throws Exception {
        DateTimeFormatter format = DateTimeFormat
                .forPattern(YMD_);
        return DateTime.parse(strDate, format).toDate();
    }

    /**
     * 获取当前时间 new Date()
     *
     * @return String date
     */
    public static String getDate() {
        SimpleDateFormat df = new SimpleDateFormat(YMD);
        String date = df.format(new Date());
        return date;
    }

    /**
     * 获取当前时间 new Date() yyyy-MM-dd
     *
     * @return String date
     */
    @Deprecated
    public static String getDate24() {
        SimpleDateFormat df = new SimpleDateFormat(YMD_);
        String date = df.format(new Date());
        return date;
    }

    /**
     * 获取月最后一天
     *
     * @return
     */
    @Deprecated
    public static String lastDayOfMonth(String str) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(str);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.roll(Calendar.DAY_OF_MONTH, -1);
            String monthDay = new SimpleDateFormat("dd").format(cal.getTime());
            return monthDay;
        } catch (ParseException e) {
            log.error("获取月最后一天异常！");
        }
        return "30";
    }

    /**
     * 判断字符串是否为空
     *
     * @param param (param != null && param.split(",").length > 1 ) ? true : false
     * @return boolean
     */
    public static boolean paramLength(String param) {
        return (param != null && param.split(",").length > 1) ? true : false;
    }

    /**
     * @param strDate 参数日期 20140404
     * @param t       日期的加减算法
     * @return String
     */
    public static String getNextDayYMD(String strDate, int t) {

        SimpleDateFormat format = new SimpleDateFormat(YMD);
        Date newDate = null;
        try {
            Date date = format.parse(strDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            // 让日期加1
            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.get(Calendar.DAY_OF_MONTH) + t);
            newDate = calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format.format(newDate);
    }


    /**
     * 根据日期 获取 月份
     *
     * @param @param  date （20141111）
     * @param @param  t 数字 正负
     * @param @return
     * @param @throws Exception 设定文件
     * @return String 返回类型
     * @throws Exception
     */
    @Deprecated
    public static String getMonth(String date, int t) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            Date dt = sdf.parse(date);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            // 日期的计算
            rightNow.add(Calendar.MONTH, -1);
            Date dt1 = rightNow.getTime();
            String reStr = sdf.format(dt1);
            return reStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取当前日期字符串
     *
     * @return
     */
    public static String getDateStr() {
        return getDateStr(YMD_HMS);
    }

    /**
     * 取得当前时间字符串
     *
     * @return
     */
    public static String getDateStr(String pattern) {
        return DateTime.now().toString(pattern);
    }

    /**
     * 格式化日期
     *
     * @param date    日期实例
     * @param pattern 格式
     * @return
     */
    public static String getDateStr(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 格式化日期
     *
     * @param date 日期实例
     * @return
     */
    public static String getDateStr(Date date) {
        return DateFormatUtils.format(date, DEFAULT_FORMAT);
    }

    /**
     * 获取当前日期的年月
     *
     * @return
     */
    public static String getDateYYYYMM() {
        return getDateStr("yyyyMM");
    }

    /**
     * 日期字符串转换成Date
     *
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return
     * @throws Exception
     */
    public static Date parse(String dateStr, String pattern) {
        DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
        return DateTime.parse(dateStr, format).toDate();
    }

    /**
     * 取得下一天
     *
     * @param dateStr       日期字符串
     * @param sourcePattern 传入的日期格式
     * @param resultPattern 返回之后的日期格式
     * @return
     */
    public static String getNextDay(String dateStr, String sourcePattern,
                                    String resultPattern) {
        return getAfterDay(dateStr, 1, sourcePattern, resultPattern);
    }

    /**
     * 取得下一天
     *
     * @param dateStr 日期字符串
     * @return
     */
    public static String getNextDay(String dateStr) {
        return getAfterDay(dateStr, 1, DEFAULT_FORMAT, DEFAULT_FORMAT);
    }

    /**
     * 取得下一天
     *
     * @param dateStr       日期字符串
     * @param days          天数
     * @param sourcePattern 传入的日期格式
     * @param resultPattern 返回之后的日期格式
     * @return
     */
    public static String getAfterDay(String dateStr, int days,
                                     String sourcePattern, String resultPattern) {
        DateTimeFormatter format = DateTimeFormat.forPattern(sourcePattern);
        DateTime dateTime = DateTime.parse(dateStr, format);
        return dateTime.plusDays(days).toString(resultPattern);
    }

    /**
     * 取得前一天
     *
     * @param dateStr 日期字符串
     * @return
     */
    public static String getBeforeDay(String dateStr) {
        return getBeforeDays(dateStr, 1, DEFAULT_FORMAT, DEFAULT_FORMAT);
    }

    /**
     * 取得前一天
     *
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static String getBeforeDay(String dateStr, String pattern) {
        return getBeforeDays(dateStr, 1, pattern, pattern);
    }

    /**
     * 取得前一天
     *
     * @param dateStr 日期字符串
     * @param days    天数
     * @param pattern 日期格式
     * @return
     */
    public static String getBeforeDays(String dateStr, int days, String pattern) {
        return getBeforeDays(dateStr, days, pattern, pattern);
    }

    /**
     * 取得前一天
     *
     * @param dateStr 日期字符串
     * @param days    天数
     * @return
     */
    public static String getBeforeDays(String dateStr, int days) {
        return getBeforeDays(dateStr, days, DEFAULT_FORMAT, DEFAULT_FORMAT);
    }

    /**
     * 取得前一天
     *
     * @param dateStr       日期字符串
     * @param days          天数
     * @param sourcePattern 传入的日期格式
     * @param resultPattern 返回之后的日期格式
     * @return
     */
    public static String getBeforeDays(String dateStr, int days,
                                       String sourcePattern, String resultPattern) {
        return getAfterDay(dateStr, -days, sourcePattern, resultPattern);
    }

    /**
     * @param dateStr       日期字符串
     * @param sourcePattern 传入的日期格式
     * @param resultPattern 返回之后的日期格式
     * @return
     */
    public static String lastDayOfMonth(String dateStr, String sourcePattern,
                                        String resultPattern) {
        DateTimeFormatter format = DateTimeFormat.forPattern(sourcePattern);
        DateTime dateTime = DateTime.parse(dateStr, format);
        return dateTime.dayOfMonth().withMaximumValue().toString(resultPattern);
    }

    public static String firstDayOfMonth(String dateStr) {
        DateTimeFormatter format = DateTimeFormat.forPattern(DEFAULT_FORMAT);
        DateTime dateTime = DateTime.parse(dateStr, format);
        return dateTime.dayOfMonth().withMinimumValue()
                .toString(DEFAULT_FORMAT);
    }

    /**
     * 获取上个月的第一天(不加时分秒)
     *
     * @return
     */
    public static String firstDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calendar.getTime());
    }

    /**
     * 获取上个月的最后一天(不加时分秒)
     *
     * @return
     */
    public static String lastDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calendar.getTime());
    }

    /**
     * 获取当前月第一天
     *
     * @param pattern 时间格式,传""默认为"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String firstDayOfCurrentMonth(String pattern) {
        if (pattern == "") {
            pattern = YMD_HMS;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(calendar.getTime());
    }

    /**
     * 获取当前月最后一天
     *
     * @param pattern 时间格式,传""默认为"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String lastDayOfCurrentMonth(String pattern) {
        if (pattern == "") {
            pattern = YMD_HMS;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(calendar.getTime());
    }

    /**
     * 获得指定月
     *
     * @param dateStr       日期字符串
     * @param sourcePattern 传入的日期格式
     * @param resultPattern 返回之后的日期格式
     * @param month         　往前或往后几个月
     * @return
     */
    public static String getMonth(String dateStr, String sourcePattern,
                                  String resultPattern, int month) {
        DateTimeFormatter format = DateTimeFormat.forPattern(sourcePattern);
        DateTime dateTime = DateTime.parse(dateStr, format);
        return dateTime.plusMonths(month).toString(resultPattern);
    }

    /**
     * 获取当前时间的timestamp
     *
     * @return
     */
    public static Timestamp getNowTimesTamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取指定时间的timestamp
     *
     * @param time
     * @return
     */
    public static Timestamp getTimestampByLong(long time) {
        return new Timestamp(time);
    }

    /**
     * 将一个字符串转换成日期格式, 字符串类型必须于格式化对应
     * 例如：2015-09-01对应yyyy-MM-dd
     * 例如：2015-09-01 00:00:00对应yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date toDate(String date, String pattern) {
        if ("".equals(date)) {
            return null;
        }
        if (pattern == null) {
            pattern = YMD_HMS;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date newDate = new Date();
        try {
            newDate = sdf.parse(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newDate;
    }

    /**
     * 字符串转为long型 必须带时、分、秒
     * 例如：2015-09-01对应yyyy-MM-dd
     * 例如：2015-09-01 00:00:00对应yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static long strToLong(String dateStr, String pattern) {
        Date date = toDate(dateStr, pattern);
        return date.getTime();
    }

    /**
     * 字符串转为long型
     *
     * @param dateStr 必须带时、分、秒
     * @return
     */
    public static long strToLong(String dateStr) {
        Date date = toDate(dateStr, YMD_HMS);
        return date.getTime();
    }


    /**
     * 获取增加月数以后的日期
     */
    public static String getDateAddMonths(int months) {
        try {
            Calendar date = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    YMD_HMS);
            date.add(Calendar.MONTH, months);
            return dateFormat.format(date.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取增加天数以后的日期
     */
    public static String getDateAddDays(int days) {
        try {
            Calendar date = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    YMD_HMS);
            date.add(Calendar.DAY_OF_MONTH, days);
            return dateFormat.format(date.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取所传日期前后天数的日期
     *
     * @param date
     * @param days   往后传正数往前传负数
     * @param format 时间格式,为空为默认"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getDateAddDays(Date date, int days, String format) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            format = StringUtils.isNotBlank(format) ? format : YMD_HMS;
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            calendar.add(Calendar.DAY_OF_MONTH, days);
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * long 转为 日期
     *
     * @param time
     * @return
     */
    public static String formatLongToStr(long time, String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = YMD_HMS;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = new Date(time);
        String sDateTime = sdf.format(date);
        return sDateTime;
    }


    /**
     * 获取一个星期之前的时间戳
     *
     * @param weeknum 传入几个星期
     * @return
     */
    public static Long getweektime(Integer weeknum) {
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.WEEK_OF_MONTH, curr.get(Calendar.WEEK_OF_MONTH) - weeknum);
        Date date = curr.getTime();
        return date.getTime();
    }

    /**
     * 获取一个月之前的时间戳
     *
     * @param monthnum 传入几个月
     * @return
     */
    public static Long getmonthtime(Integer monthnum) {
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.MONTH, curr.get(Calendar.MONTH) - monthnum);
        Date date = curr.getTime();
        return date.getTime();
    }

    /**
     * 获取一年之前的时间戳
     *
     * @param yearnum 传入几年
     * @return
     */
    public static Long getyeartime(Integer yearnum) {
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.YEAR, curr.get(Calendar.YEAR) - yearnum);
        Date date = curr.getTime();
        return date.getTime();
    }

    /**
     * 转换为默认的时间格式
     *
     * @param times
     * @return
     */
    public static Long getlongtimes(String times) {
        String viewtime = times;
        Date time = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            time = sdf.parse(viewtime);
        } catch (ParseException e) {
            e.printStackTrace();
            try {
                time = sdf2.parse(viewtime);
            } catch (ParseException e1) {
                e.printStackTrace();
            }
        }
        viewtime = sdf2.format(time);
        System.out.println(strToLong(viewtime));
        return strToLong(viewtime);
    }

    /**
     * 获取当天开始时间
     *
     * @return
     */
    public static Long getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    /**
     * 获取当天结束时间
     *
     * @return
     */
    public static Long getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime().getTime();
    }

    /**
     * 获取当天星期数,星期天为0
     *
     * @return
     */
    public static int getWeek() {
        Calendar cal = Calendar.getInstance();
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return week;
    }

    /**
     * 获取当天日期为几号
     *
     * @return
     */
    public static int getDayOfMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取上周一时间(不加时分秒)
     *
     * @return
     */
    public static String getLastMonday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(YMD_);
        cal.add(Calendar.WEEK_OF_MONTH, -1);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        return df.format(cal.getTime());
    }

    /**
     * 获取周一时间(不加时分秒)
     *
     * @return
     */
    public static String getMonday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(YMD_);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        return df.format(cal.getTime());
    }

    /**
     * 获取上周天时间(不加时分秒)
     *
     * @return
     */
    public static String getLastSunday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(YMD_);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        return df.format(cal.getTime());
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取当天时间 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getnowDate() {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf2.format(new Date());
        return date;
    }

    /**
     * 计算距离现在多久，非精确
     *
     * @param date
     * @return
     */
    public static String timeBefore(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        } else if (hour > 0) {
            r += hour + "小时";
        } else if (min > 0) {
            r += min + "分";
        } else if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * 计算距离现在多久，精确
     *
     * @param date
     * @return
     */
    public static String timeBeforeAccurate(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        }
        if (hour > 0) {
            r += hour + "小时";
        }
        if (min > 0) {
            r += min + "分";
        }
        if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * 时间差计算
     */
    public static Map<String, Double> timeDifference(Long starTime, Long endTime) {
        //时间差的毫秒数
        Long date3 = endTime - starTime;
        //计算出相差天数
        Double allDays = Math.ceil(date3 / (24 * 3600 * 1000));
        Double days = Math.floor(date3 / (24 * 3600 * 1000));
        //计算出小时数
        Long leave1 = date3 % (24 * 3600 * 1000);
        //计算天数后剩余的毫秒数
        Double hours = Math.floor(leave1 / (3600 * 1000));
        //计算相差分钟数
        Long leave2 = leave1 % (3600 * 1000);
        //计算小时数后剩余的毫秒数
        Double minutes = Math.floor(leave2 / (60 * 1000));
        //计算相差秒数
        Long leave3 = leave2 % (60 * 1000);
        //计算分钟数后剩余的毫秒数
        Double seconds = Math.floor(leave3 / 1000);

        Map<String, Double> map = new HashMap<>();
        map.put("allDays", allDays);
        map.put("days", days);
        map.put("hours", hours);
        map.put("minutes", minutes);
        map.put("seconds", seconds);

        return map;
    }

    /**
     * 获取当前时间
     */
    public static Date getNow() {
        return new Date();
    }


}
