package cn.gov.cqaudit.tools;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("dateTools")

public class DateTools {
	

	
	
	/**
	 * 求两个时间之间的间隔天数
	 * @param d1 前一个时间，或者更早的时间，或者更小的时间
	 * @param d2 后一个时间，后者更晚的时间，或者更小的时间
	 * @return  返回天数
	 */
	/*
	 * public static long daysDistance(java.util.Date d1,java.util.Date d2){
	 * 
	 * 
	 * d1=cleanHHmiss(d1); d2=cleanHHmiss(d2);
	 * 
	 * long earlydate = d1.getTime(); long latedate=d2.getTime();
	 * 
	 * long
	 * betweenDate=(latedate-earlydate)/(1000*60*60*24);//计算间隔多少天，则除以毫秒到天的转换公式  
	 * 
	 * return betweenDate; }
	 */
	
	/*
	 * 得到两个天数之间的随机一天,含开始时间和含结束时间
	 * @param d1 前一个时间，或者更早的时间，或者更小的时间
	 * @param d2 后一个时间，后者更晚的时间，或者更小的时间
	 */
	/*
	 * public static java.util.Date getRandomDate(java.util.Date d1,java.util.Date
	 * d2){ d1=cleanHHmiss(d1); d2=cleanHHmiss(d2); long
	 * distance=daysDistance(d1,getNextDays(d2,1));
	 * 
	 * Random randSeed = new Random();
	 * 
	 * int flag=randSeed.nextInt((int)distance) ; System.out.println(flag); return
	 * getNextDays(d1,flag); }
	 */
	 
	 /**
	  * 得到之后的某一天
	  * @param d1 起始时间按
	  * @param days 天数
	  */
	/*
	 * public static java.util.Date getNextDays(java.util.Date d1,int days){
	 * d1=cleanHHmiss(d1); Calendar calendars = Calendar.getInstance();//得到日历  
	 * 
	 * calendars.setTime(d1);//把当前时间赋给日历
	 * 
	 * calendars.add(Calendar.DAY_OF_MONTH,days); //设置为之后的某一天 Date
	 * nextDate=calendars.getTime(); return nextDate; }
	 */
	 private static final int PATTERN_CACHE_SIZE = 500;
	 private static final ConcurrentMap<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>();
	 /**
	  * 日期格式转换
	  * @param d1 日期
	  * @param type 格式
	  */
	 
	/*
	 * public static String convertString(java.util.Date d1,String format) {
	 * 
	 * DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
	 * LocalDateTime now = dateToLocalDateTime(d1); return
	 * now.format(dateTimeFormatter);
	 * 
	 * 
	 * }
	 */
	 /**
	     * 将Date转换成LocalTime
	     *
	     * @param d date
	     * @return
	     */
	    public static LocalTime dateToLocalTime(Date d) {
	        Instant instant = d.toInstant();
	        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
	        return localDateTime.toLocalTime();
	    }
	    /**
	     * 将Date转换成LocalDateTime
	     *
	     * @param d date
	     * @return
	     */
	    public static LocalDateTime dateToLocalDateTime(Date d) {
	        Instant instant = d.toInstant();
	        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
	        return localDateTime;
	    }
	
	 /**
	     * 将Date转换成LocalDate
	     *
	     * @param d date
	     * @return
	     */
	    public static LocalDate dateToLocalDate(Date d) {
	        Instant instant = d.toInstant();
	        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
	        return localDateTime.toLocalDate();
	    }

	 /**
	     * localDateTime转换为格式化时间
	     * @param localDateTime localDateTime
	     * @param pattern 格式
	     * @return
	     */
	    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern){
	        DateTimeFormatter formatter = createCacheFormatter(pattern);
	        return localDateTime.format(formatter);
	    }
	    public static String formatLocalDate(LocalDate localDate, String pattern){
	        DateTimeFormatter formatter = createCacheFormatter(pattern);
	        return localDate.format(formatter);
	    }
	    /**
	     * 在缓存中创建DateTimeFormatter
	     * @param pattern 格式
	     * @return
	     */
	    private static DateTimeFormatter createCacheFormatter(String pattern){
	        if (pattern == null || pattern.length() == 0) {
	            throw new IllegalArgumentException("Invalid pattern specification");
	        }
	        DateTimeFormatter formatter = FORMATTER_CACHE.get(pattern);
	        if(formatter == null){
	            if(FORMATTER_CACHE.size() < PATTERN_CACHE_SIZE){
	                formatter = DateTimeFormatter.ofPattern(pattern);
	                DateTimeFormatter oldFormatter = FORMATTER_CACHE.putIfAbsent(pattern, formatter);
	                if(oldFormatter != null){
	                    formatter = oldFormatter;
	                }
	            }
	        }

	        return formatter;
	    }
	 
	 /**
	  * 转换文本为日期模式
	  * 
	  * @param s1 收入文本
	  * @param type 类型模板
	  * @return 日期
	  * @throws ParseException
	  */
	/*
	 * public static java.util.Date convertDate(String dateStr,String type) throws
	 * ParseException{ DateTimeFormatter formatter =
	 * DateTimeFormatter.ofPattern(type); LocalDate date= LocalDate.parse(dateStr,
	 * formatter);
	 * 
	 * return localDateToDate(date); }
	 */
	 public static LocalDateTime convertLocalDateTime(String dateStr,String type) throws ParseException{
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern(type);
		 LocalDateTime datetime= LocalDateTime.parse(dateStr, formatter);
		 
		 return datetime;
	 }
	 public static LocalDate convertLocalDate(String dateStr,String type) throws ParseException{
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern(type);
		 LocalDate date= LocalDate.parse(dateStr, formatter);
		 
		 return date;
	 }
	 private static final ZoneId ZONE = ZoneId.systemDefault();
	 /**
	     * 将LocalDate转换成Date
	     *
	     * @param localDate
	     * @return date
	     */
	    public static Date localDateToDate(LocalDate localDate) {
	        Instant instant = localDate.atStartOfDay().atZone(ZONE).toInstant();
	        return Date.from(instant);
	    }
	
	 /**
	     * 格式化字符串转为LocalDateTime
	     * @param time 格式化时间
	     * @param pattern 格式
	     * @return
	     */
	    public static LocalDateTime parseLocalDateTime(String time, String pattern){
	        DateTimeFormatter formatter = createCacheFormatter(pattern);
	        return LocalDateTime.parse(time, formatter);
	    }
	
	 /**
	  * 将日期中的小时分秒去除
	  * @param d1 需要清洗的日期
	  */
	/*
	 * public static java.util.Date cleanHHmiss(java.util.Date d1){ String
	 * s1=convertString(d1,"yyyy-MM-dd");
	 * 
	 * try { d1=convertDate(s1,"yyyy-MM-dd"); } catch(ParseException e) {
	 * System.out.println(e.toString()); } s1=null; return d1; }
	 */
	 
	 public static LocalDate convertLocalDatetimeToLocalDate(LocalDateTime ldt) throws ParseException {
		 String temp=formatLocalDateTime(ldt,"yyyy-MM-dd");
		 return convertLocalDate(temp,"yyyy-MM-dd");
	 }
	 
	 /**
	     * 获取两个日期的差  field参数为ChronoUnit.使用方法betweenTwoTime(start, end, ChronoUnit.YEARS)
	     * @param startTime
	     * @param endTime
	     * @param field  单位(年月日时分秒)
	     * @return
	     */
	    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
	        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
	        if (field == ChronoUnit.YEARS) return period.getYears();
	        if (field == ChronoUnit.MONTHS) return period.getYears() * 12 + period.getMonths();
	        return field.between(startTime, endTime);
	    }
	  //获取一天的开始时间，2017,7,22 00:00
	    public static LocalDateTime getDayStart(LocalDateTime time) {
	        return time.withHour(0)
	                .withMinute(0)
	                .withSecond(0)
	                .withNano(0);
	    }

	    //获取一天的结束时间，2017,7,22 23:59:59.999999999
	    public static LocalDateTime getDayEnd(LocalDateTime time) {
	        return time.withHour(23)
	                .withMinute(59)
	                .withSecond(59)
	                .withNano(999999999);
	    }
	    //日期加上一个数,根据field不同加不同值,field为ChronoUnit.*
	    //使用方法.plus(LocalDateTime.now(),20, ChronoUnit.MINUTES)
	    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
	        return time.plus(number, field);
	    }

	    //日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*
	    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field){
	        return time.minus(number,field);
	    }
	
	 
	 
}

