package com.guyue.common.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName DateUtil
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/9 上午10:12
 **/
public class DateUtil {
    /**
     * 日期时间毫秒格式:yyyyMMddHHmmssS
     */
    public static final SimpleDateFormat dateTimeMsecFormaat = new SimpleDateFormat("yyyyMMddHHmmssS");// 日期时间毫秒格式
    /**
     * 日期时间格式:yyyy-MM-dd HH:mm:ss
     */
    public static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日期时间格式
    /**
     * 日期时间字符串格式:yyyyMMddHHmmss
     */
    public static final SimpleDateFormat dateTimeStrFormat = new SimpleDateFormat("yyyyMMddHHmmss");// 日期时间字符串格式
    /**
     * 日期格式：yyyy-MM-dd
     */
    public static final SimpleDateFormat dateFormaat = new SimpleDateFormat("yyyy-MM-dd");// 日期格式
    /**
     * 日期字符串格式：yyyyMMdd
     */
    public static final SimpleDateFormat dateStrFormaat = new SimpleDateFormat("yyyyMMdd");// 日期格式
    /**
     * 时间格式:HH:mm:ss
     */
    public static final SimpleDateFormat timeFormaat = new SimpleDateFormat("HH:mm:ss");// 时间格式

    public static final SimpleDateFormat timeMMFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * 日期格式 yyMMdd
     **/
    public static final SimpleDateFormat YYMMddFormat = new SimpleDateFormat("yyMMdd");
    /**
     * 时间格式化
     *
     * @param date
     * @param formatPattern
     * @return java.lang.String
     * @Date:17:10 2017/11/7
     */
    public static String format(Date date, String formatPattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat f = new SimpleDateFormat(formatPattern);
        return f.format(date);
    }

    public static String getNow(){
        return format(new Date(),  "yyyy-MM-dd HH:mm:ss");
    }

    public static String getNowTime(){
        return dateTimeMsecFormaat.format(new Date());
    }

    public static String getToday(){
        return dateFormaat.format(new Date());
    }

    public static String getSeq(){
        return format(new Date(),  "yyyyMMddHHmmss");
    }

    public static String getYYMMdd(){ return YYMMddFormat.format(new Date());}


    /**
     * 字符串转为时间
     * @Author zpj
     * @Date 2020/07/16 10:14
     * @Description
     **/
    public static Date parseDate(String date,String formatPattern) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat(formatPattern);
        return f.parse(date);
       // return format.parse(date);
    }


    public static boolean checkTheDateAtNowAfter(String Date,Long diffhours,String formatter){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatter);
        LocalDateTime dateTime = LocalDateTime.parse(Date,dtf);
        dateTime=dateTime.minusHours(diffhours);
        LocalDateTime now = LocalDateTime.now();
        return dateTime.compareTo(now)>=0;
    }
    public static double getDiffHours(String startDate,String endDate,String formatter){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatter);
        LocalDateTime sDate = LocalDateTime.parse(startDate,dtf);
        LocalDateTime eDate = LocalDateTime.parse(endDate,dtf);
        Duration duration = Duration.between(sDate,eDate);
        return duration.toMinutes()/60.0;
    }


    /**
     * 比较date2 是否在date1之后指定时间范围内
     * <p><br/>
     * 没有时间差，则比较的是date2，是否在date1之前,值相同返回true
     * <p><br/>
     * date1 靠前时间<br/>
     * date2 靠后时间<br/>
     * field 范围的单位 Calendar.field <br/>
     * value 范围值<br/>
     * @Author zpj
     * @Date 2020/07/16 11:12
     * @Description
     **/
    public static boolean compareDate(Date date1,Date date2,int field,int value){
        return date2.before(getDateAfter(date1,field,value));
    }

    /**
     * 获取特定时间 指定长度后 的时间
     * <p><br/>
     * date  特定时间<br/>
     * datefield 指定长度的单位 Calendar.field <br/>
     * value 长度值<br/>
     * @Author zpj
     * @Date 2020/07/16 11:32
     * @Description
     **/
    public static Date getDateAfter(Date date,int datefield,int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(datefield,value);
        return calendar.getTime();
    }
}
