package com.ebs.receiver.comm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil
{

    private static final String formatDateSign = "-";
    private static final String formatDandTSign = "/";
    private static final String formatTimeSign = ":";
    private static final String simpleDateFormat = "yyyy-MM-dd";
    private static final String simpleTimeFormat = "yyyy-MM-dd HH:mm:ss";
    static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DateUtil()
    {
    }

    public static String dateTo8(String date)
    {
        if(date == null)
            return "";
        if(date.trim().length() != 10)
            return date;
        else
            return date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10);
    }

    

    public static String dateTo10(String date)
    {
        if(date == null)
            return "";
        if(date.trim().length() != 8)
            return "";
        else
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
    }

    public static String timeTo6(String time)
    {
        int len = time.length();
        if(len < 7 || len > 8)
            return "";
        else
            return time.substring(0, len - 6) + time.substring(len - 5, len - 3) + time.substring(len - 2, len);
    }

    public static String ymonto6(String yearmon)
    {
        int len = yearmon.length();
        if(len == 5)
            return yearmon.substring(0, 4) + "0" + yearmon.substring(4, 5);
        if(len > 6)
            return yearmon.substring(0, 6);
        else
            return yearmon;
    }

    public static String timeTo8(String time)
    {
        int len = time.length();
        if(len < 5 || len > 6)
            return "";
        else
            return time.substring(0, len - 4) + ":" + time.substring(len - 4, len - 2) + ":" + time.substring(len - 2, len);
    }

    public static String dateTo19(String date)
    {
        int len = date.length();
        if(len != 14)
            return date;
        else
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8) + "/" + date.substring(8, 10) + ":" + date.substring(10, 12) + ":" + date.substring(12, 14);
    }
    
    public static Date stringToSqlDate(String str)
    {
        if(stringToUtilDate(str) == null || str.length() < 1)
            return null;
        else
            return new Date(stringToUtilDate(str).getTime());
    }

    public static java.util.Date stringToUtilDate(String str)
    {
        if(str != null && str.length() > 0)
            try
            {
                if(str.length() <= "yyyy-MM-dd".length())
                    return (new SimpleDateFormat("yyyy-MM-dd")).parse(str);
                else
                    return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(str);
            }
            catch(ParseException ex)
            {
                return null;
            }
        else
            return null;
    }

    public static Date utilDateToSql(java.util.Date date)
    {
        return new Date(date.getTime());
    }

    public static java.util.Date sqlDateToUtil(Date date)
    {
        return new java.util.Date(date.getTime());
    }


    public static Date getCurrentDateTime()
    {
        Calendar newcalendar = null;
        newcalendar = Calendar.getInstance();
        String year = String.valueOf(newcalendar.get(1));
        String month = String.valueOf(newcalendar.get(2) + 1);
        String day = String.valueOf(newcalendar.get(5));
        String hour = String.valueOf(newcalendar.get(11));
        String min = String.valueOf(newcalendar.get(12));
        String sec = String.valueOf(newcalendar.get(13));
        return stringToSqlDate(year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec);
    }

    public static java.util.Date getCurUtilDate()
    {
        Calendar newcalendar = null;
        newcalendar = Calendar.getInstance();
        String year = String.valueOf(newcalendar.get(1));
        String month = String.valueOf(newcalendar.get(2) + 1);
        String day = String.valueOf(newcalendar.get(5));
        return stringToUtilDate(year + "-" + month + "-" + day);
    }

    public static Date getCurrentDate()
    {
        Calendar newcalendar = null;
        newcalendar = Calendar.getInstance();
        String year = String.valueOf(newcalendar.get(1));
        String month = String.valueOf(newcalendar.get(2) + 1);
        String day = String.valueOf(newcalendar.get(5));
        return stringToSqlDate(year + "-" + month + "-" + day);
    }

    public static String getCurrentMon()
    {
        Calendar newcalendar = null;
        newcalendar = Calendar.getInstance();
        String year = String.valueOf(newcalendar.get(1));
        String month = String.valueOf(newcalendar.get(2) + 1);
        return year + month;
    }

    public static String getCurrentTime()
    {
        SimpleDateFormat dataFormat = new SimpleDateFormat("HHmmss");
        java.util.Date date = new java.util.Date();
        String timeString = dataFormat.format(date);
        return timeTo8(timeString);
    }

    public static String getCurrentDateTimeStr()
    {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.SIMPLIFIED_CHINESE);
        java.util.Date date = new java.util.Date();
        String timeString = dataFormat.format(date);
        return timeString;
    }
    
    public static String getYyyyMMddHHmmssStr()
    {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        String timeString = dataFormat.format(date);
        return timeString;
    }

    public static String getCurrentDateStr()
    {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        String timeString = dataFormat.format(date);
        return timeString;
    }
 
    public static String getWeekStr()
    {
        String s = "";
        int week = getWeek();
        switch(week)
        {
        case 1: // '\001'
            s = "星期一";
            break;

        case 2: // '\002'
            s = "星期二";
            break;

        case 3: // '\003'
            s = "星期三";
            break;

        case 4: // '\004'
            s = "星期四";
            break;

        case 5: // '\005'
            s = "星期五";
            break;

        case 6: // '\006'
            s = "星期六";
            break;

        case 7: // '\007'
            s = "星期天";
            break;
        }
        return s;
    }

    public static int getWeek()
    {
        java.util.Date date = new java.util.Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int posOfWeek = cal.get(7);
        return --posOfWeek;
    }

    public static Date addYear(Date beginTime, int i)
    {
        Calendar date = Calendar.getInstance();
        date.setTime(beginTime);
        date.add(1, i);
        return utilDateToSql(date.getTime());
    }

    public static Date addMonth(Date beginTime, int i)
    {
        Calendar date = Calendar.getInstance();
        date.setTime(beginTime);
        date.add(2, i);
        return utilDateToSql(date.getTime());
    }

    public static Date addDay(Date beginTime, int i)
    {
        Calendar date = Calendar.getInstance();
        date.setTime(beginTime);
        date.add(5, i);
        return utilDateToSql(date.getTime());
    }

    public static int compareYear(Date beginTime, Date endTime)
    {
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        begin.setTime(beginTime);
        end.setTime(endTime);
        int compareYear = end.get(1) - begin.get(1);
        return compareYear;
    }

    public static int compareMonth(Date beginTime, Date endTime)
    {
        int compareYear = compareYear(beginTime, endTime);
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        begin.setTime(beginTime);
        end.setTime(endTime);
        int compareMonth = compareYear * 12 + (end.get(2) - begin.get(2));
        return compareMonth;
    }

    public static int compareDay(Date beginTime, Date endTime)
    {
        long compare = (endTime.getTime() - beginTime.getTime()) / 0x5265c00L;
        String compareStr = String.valueOf(compare);
        return Integer.parseInt(compareStr);
    }

    public static String getLastDayOfMonth(String date)
    {
        if(date.length() != 8)
            return "";
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = 0;
        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
            day = 31;
        else
        if(month == 2)
        {
            if(year % 4 == 0 && year % 100 != 0)
                day = 29;
            else
                day = 28;
        } else
        {
            day = 30;
        }
        String newDate = year+"";
        if(month < 10)
            newDate = newDate + "0" + month + day;
        else
            newDate = newDate + month + day;
        return newDate;
    }

    public static String getWeek(String sDate)
    {
        String dayNames[] = {
            "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"
        };
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        java.util.Date date = new java.util.Date();
        try
        {
            date = sdfInput.parse(sDate);
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int dayOfWeek = calendar.get(7);
        return dayNames[dayOfWeek - 1];
    }


    public static boolean timeIsEffective(String time1, String time2)
    {
        boolean time = true;
        Calendar newcalendar = null;
        newcalendar = Calendar.getInstance();
        String year = String.valueOf(newcalendar.get(1));
        String month = String.valueOf(newcalendar.get(2) + 1);
        String day = String.valueOf(newcalendar.get(5));
        String hour = String.valueOf(newcalendar.get(11));
        String min = String.valueOf(newcalendar.get(12));
        String sec = String.valueOf(newcalendar.get(13));
        java.util.Date date = stringToSqlDate(year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec);
        java.util.Date date1 = stringToSqlDate(year + "-" + month + "-" + day + " " + time1);
        java.util.Date date2 = stringToSqlDate(year + "-" + month + "-" + day + " " + time2);
        if(date.after(date1) || date.before(date2))
            time = false;
        return time;
    }
    /*返回包头中需要的格式*/
    public static String dateSs()
    {
    	   SimpleDateFormat simple=new SimpleDateFormat("yyyyMMddhhmmssSSS");
		   String time=simple.format(new java.util.Date());
		   String returnStr=time.substring(0,8)+time.substring(14,time.length());
		   return returnStr;
    }
    /*add by zap*/
    /**
     * @函数名称：dateTo8 @功能描述：8位yyyyMMdd 转换为yyy/MM/dd,yyyy-MM-dd,yyyy:MM:dd
     * @param date 要格式化的日期字符串: 8位yyyyMMdd
     * @return 返回格式化后的日期
     */
    public static String dateTo16(String date) {
        if (date == null)
            return "";
        if (date.trim().length() != 14)
            return "";
        return date.substring(0, 4) + formatDateSign + date.substring(4, 6) + formatDateSign + date.substring(6, 8)+"  "+date.substring(8, 10)+formatTimeSign+date.substring(10, 12)+formatTimeSign+date.substring(12, 14);
    }
    
    /**
     * @函数名称：dateTo6 @功能描述：10位yyyy/MM/dd,yyyy-MM-dd,yyyy:MM:dd 转换为6位yyyyMM
     * @param date 要格式化的日期字符串: 10位 yyyy/MM/dd或yyyy-MM-dd或yyyy:MM:dd
     * @return 返回格式化后的日期
     */
    public static String dateTo6(String date) {
        if (date == null)
            return "";
        if (date.trim().length() != 10) {
            return date;
        }
        return date.substring(0, 4) + date.substring(5, 7);
    }
    public static String xtoy(String o){
    	String rt = "";
    	
    	//System.out.println("o:"+o);
    	DateFormat oFormat =  new SimpleDateFormat("yyyyMMddHHmmss");
    	DateFormat nFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	try {
			java.util.Date od = oFormat.parse(o);
			rt = nFormat.format(od);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return rt;
    }
    
    public static boolean isAllowTradeTime(String beginTime, String endTime) {
		try {
			if (beginTime.equals(endTime))
				return true;
			DateFormat df = new SimpleDateFormat("HH:mm:ss");
			Date now = new Date();
			String nowString = df.format(now);
//			 System.out.println(nowString);
			if (beginTime.compareTo(endTime) > 0) {
				if (nowString.compareTo(beginTime) < 0
						&& nowString.compareTo(endTime) > 0)
					return true;
				else
					return false;
			} else {
				if (nowString.compareTo(beginTime) < 0
						|| nowString.compareTo(endTime) > 0)
					return true;
				else
					return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 如果出现异常则让打交易这个步骤失效，可以继续做交易
			return true;
		}
	}
    
    public static void main(String[] args){
    	String str = getYyyyMMddHHmmssStr();
    	System.out.println(str);
    	
    	isAllowTradeTime("2012-07-03 12:12:12","2012-07-03 00:12:12");
//    	System.out.println(Long.parseLong(str)+30);
    }

}
