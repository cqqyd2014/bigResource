package cn.gov.cqaudit.tools;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
	public static long daysDistance(java.util.Date d1,java.util.Date d2){

		
		d1=cleanHHmiss(d1);
		d2=cleanHHmiss(d2);
		
		long earlydate = d1.getTime();
		long latedate=d2.getTime();
		
		long betweenDate=(latedate-earlydate)/(1000*60*60*24);//计算间隔多少天，则除以毫秒到天的转换公式  
		
		return betweenDate;
	}
	
	/*
	 * 得到两个天数之间的随机一天,含开始时间和含结束时间
	 * @param d1 前一个时间，或者更早的时间，或者更小的时间
	 * @param d2 后一个时间，后者更晚的时间，或者更小的时间
	 */
	 public static java.util.Date getRandomDate(java.util.Date d1,java.util.Date d2){
		 d1=cleanHHmiss(d1);
		 d2=cleanHHmiss(d2);
		 long distance=daysDistance(d1,getNextDays(d2,1));

		 Random randSeed = new Random();
		 
		 int flag=randSeed.nextInt((int)distance) ;
		 System.out.println(flag);
		 return getNextDays(d1,flag);
	 }
	 
	 /**
	  * 得到之后的某一天
	  * @param d1 起始时间按
	  * @param days 天数
	  */
	 public static java.util.Date getNextDays(java.util.Date d1,int days){
		 d1=cleanHHmiss(d1);
		 Calendar calendars = Calendar.getInstance();//得到日历  
		 
		 calendars.setTime(d1);//把当前时间赋给日历
		 
		 calendars.add(Calendar.DAY_OF_MONTH,days); //设置为之后的某一天
		 Date nextDate=calendars.getTime();
		 return nextDate;
	 }
	 
	 /**
	  * 日期格式转换
	  * @param d1 日期
	  * @param type 格式
	  */
	 public static java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat();
	 public static String convertString(java.util.Date d1,String type) {
		  sdf.applyPattern(type);
		/*
		 * java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(type,
		 * java.util.Locale.CHINA);
		 */
	           String result = sdf.format(d1);
	           return result;

	 }
	 
	 /**
	  * 转换文本为日期模式
	  * 
	  * @param s1 收入文本
	  * @param type 类型模板
	  * @return 日期
	  * @throws ParseException
	  */
	 public static java.util.Date convertDate(String s1,String type) throws ParseException{
		/* SimpleDateFormat sdf = new SimpleDateFormat(type); */
		 sdf.applyPattern(type);
		 return sdf.parse(s1);
	 }
	 
	 
	 /**
	  * 将日期中的小时分秒去除
	  * @param d1 需要清洗的日期
	  */
	 public static java.util.Date cleanHHmiss(java.util.Date d1){
		 String s1=convertString(d1,"yyyy-MM-dd");
		 
		 try {
			 d1=convertDate(s1,"yyyy-MM-dd");
		 }
		 catch(ParseException e) {
			 System.out.println(e.toString());
		 }
		 s1=null;
		 return d1;
	 }
	 
	 
	 
	 
}

