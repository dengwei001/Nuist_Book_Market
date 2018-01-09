package com.nuist.bookMarket.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils
{
    /**
     * 获取系统时间
     *
     * @param format 14位:yyyyMMddHHmmss,19位：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getSysdate(String format)
    {
        Date now = new Date();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(now);
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return calendar
     */
    public static Calendar strToDate(String str)
    {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        try
        {
            Date date = format.parse(str);
            calendar.setTime(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return calendar;
    }

    /**
     * 查询日期的年份
     *
     * @param calendar
     * @return String
     */
    public static String getYear(Calendar calendar)
    {
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    /**
     * 查询日期的月份
     *
     * @param calendar
     * @return String
     */
    public static String getMonth(Calendar calendar)
    {
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month < 10)
        {
            return "0" + month;
        }
        else
        {
            return String.valueOf(month);
        }
    }

    /**
     * 查询日期的day
     *
     * @param calendar
     * @return String
     */
    public static String getDay(Calendar calendar)
    {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day < 10)
        {
            return "0" + day;
        }
        else
        {
            return String.valueOf(day);
        }
    }

    /**
     * 获取时间差
     *
     * @param endTime
     * @return
     */
    public static long getTimeDifference(String endTime)
    {
        Calendar calendar = strToDate(endTime);
        return calendar.getTimeInMillis() - System.currentTimeMillis();
    }

    /**
     * 获取时间差
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getTimeDifference(String startTime, String endTime)
    {
        Calendar startCalendar = strToDate(startTime);
        Calendar endCalendar = strToDate(endTime);
        return endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
    }

    public static String getPercent(long son, long mother)
    {
        String percent;
        Double p = 0.0;
        if (mother == 0)
        {
            p = 0.0;
        }
        else
        {
            p = son * 1.0 / mother;
        }
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);//控制保留小数点后几位，2：表示保留2位小数点
        percent = nf.format(p);
        return percent;
    }

    /**
     * 获取最近日期的相关信息
     * @return
     */
    public static  Map<String,Object> getDateInfo(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH )+1;
        int week= calendar.get(Calendar.WEEK_OF_YEAR);
        Map<String,Object> returnMap=new HashMap<String,Object>();
        //当前的年月周信息
        returnMap.put("NOW_YEAR",year);
        returnMap.put("NOW_MONTH",month);
        returnMap.put("NOW_WEEK",week);
        return returnMap;
    }

    public static Date lastMonday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = 1 - dayOfWeek;
        calendar.add(Calendar.DATE, offset - 7);
        return getFirstDayOfWeek(calendar.getTime(), 2);
    }




    public static  Date getFirstDayOfWeek(Date date, int firstDayOfWeek) {
        Calendar cal = Calendar.getInstance();
        if (date != null)
            cal.setTime(date);
        cal.setFirstDayOfWeek(firstDayOfWeek);//设置一星期的第一天是哪一天
        cal.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);//指示一个星期中的某天
        cal.set(Calendar.HOUR_OF_DAY, 0);//指示一天中的小时。HOUR_OF_DAY 用于 24 小时制时钟。例如，在 10:04:15.250 PM 这一时刻，HOUR_OF_DAY 为 22。
        cal.set(Calendar.MINUTE, 0);//指示一小时中的分钟。例如，在 10:04:15.250 PM 这一时刻，MINUTE 为 4。
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
//        tempStart.add(Calendar.DAY_OF_YEAR, 1);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String startTime=sdf.format(tempStart.getTime());
        String endTime=sdf.format(tempEnd.getTime());

        while (tempStart.before(tempEnd)||startTime.equals(endTime)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            if(startTime.equals(endTime)){
                break;
            }
        }
        return result;
    }

    public static List<String> getMondayToNow(){
        //本周
        Date now = new Date();
        //本周周一
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date mondayDate = cal.getTime();

        List<Date> dayList = getBetweenDates(mondayDate, now);
        List<String> days = new ArrayList<String>();
        //从本周周一到当前时间的List
        for(int i = 0;i<dayList.size();i++){
            days.add(df.format(dayList.get(i))); //格式化当前时间
        }

        return days;
    }

    //获取今天，昨天，前天的时间
    public static Map<String,String> getTimeMap(){
        Date now = new Date();   //当前时间
        Date nowBefore = new Date(); //昨天时间
        Date yesterdayBefore = new Date(); //前天时间

        Calendar calendar = Calendar.getInstance();  //得到日历
        calendar.setTime(now);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天

        nowBefore = calendar.getTime();   //得到前一天的时间

        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前前一天
        yesterdayBefore = calendar.getTime();   //得到前前一天的时间

        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
        String defaultNow = sdf.format(now); //格式化当前时间
        String defaultNowBefore = sdf.format(nowBefore);    //格式化昨天时间
        String defaultYesterdayBefore = sdf.format(yesterdayBefore);    //格式化前天时间

        Map<String,String> timeMap = new HashMap<String,String>();

        timeMap.put("defaultNow",defaultNow);
        timeMap.put("defaultNowBefore",defaultNowBefore);
        timeMap.put("defaultYesterdayBefore",defaultYesterdayBefore);

        return timeMap;
    }

    public static Map<String,Date> getMonthMap(){
        Map<String,Date> timeMap = new HashMap<String,Date>();
        //获取前一个月
        Calendar oneCalendar = Calendar.getInstance();
        oneCalendar.add(Calendar.MONTH, -1);

        //获取上上一个月
        Calendar twoCalendar = Calendar.getInstance();
        twoCalendar.add(Calendar.MONTH, -2);

        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.add(Calendar.MONTH, 0);
        currentCalendar.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天

        timeMap.put("lastLastMonth",twoCalendar.getTime());
        timeMap.put("lastMonth",oneCalendar.getTime());
        timeMap.put("currentFirstDay",currentCalendar.getTime());

        return timeMap;
    }

    /**
     * 格式化本月,上月，上上月的月份
     * @param timeMap
     * @return
     */
    public static List<Map<String,String>> getMonthList(Map<String,Date> timeMap){
        List<Map<String,String>> returnList=new ArrayList<Map<String,String>>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
        String defaultNow = sdf.format(timeMap.get("currentFirstDay").toString());
        Map<String,String> returnMap=new HashMap<String,String>();
        returnMap.put("YEAR",defaultNow.substring(0,4));
        returnMap.put("MONTH",defaultNow.substring(4,6));
        returnMap.put("NOW_FIRST_DAY",defaultNow);//当月的第一天
        returnList.add(returnMap);//当月
        String lastMonth = sdf.format(timeMap.get("lastMonth").toString());
        returnMap.put("YEAR",lastMonth.substring(0,4));
        returnMap.put("MONTH",lastMonth.substring(4,6));
        returnList.add(returnMap);//上月
        String lastLastMonth = sdf.format(timeMap.get("lastLastMonth").toString());
        returnMap.put("YEAR",lastLastMonth.substring(0,4));
        returnMap.put("MONTH",lastLastMonth.substring(4,6));
        returnList.add(returnMap);//上上月
        return returnList;
    }
}
