package com.imgServer.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	
	/**
	 * 
	 * @Description: 获取当前时间 格式为yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrentDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar rightNow = Calendar.getInstance();
		return df.format(rightNow.getTime());
	}
	
	

	
	
	/**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(String date1Str){
    	Date date1=StrToDate(date1Str,"yyyy-MM-dd HH:mm:ss");
        int days = (int) ((new Date().getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
	
    
    
    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(String date1Str,String date2Str){
    	Date date1=StrToDate(date1Str,"yyyy-MM-dd HH:mm:ss");
    	Date date2=StrToDate(date2Str,"yyyy-MM-dd HH:mm:ss");
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
    
    
    /**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str, String formatStr) {

		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	
	/**
	 * 
	 * @Description: 获取当前时间 格式为yyyy-MM-dd
	 * @return
	 */
	public static String getCurrentDate(String str) {
		SimpleDateFormat df = new SimpleDateFormat(str);
		Calendar rightNow = Calendar.getInstance();
		return df.format(rightNow.getTime());
	}

}
