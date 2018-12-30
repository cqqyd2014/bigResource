package cn.gov.cqaudit.tools;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTools {
	private static final String hexDigIts[] = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    /**
     * MD5加密
     * @param origin 字符
     * @param charsetname 编码
     * @return
     */
    public static String MD5Encode(String origin, String charsetname){
        String resultString = null;
        try{
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if(null == charsetname || "".equals(charsetname)){
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            }else{
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        }catch (Exception e){
        }
        return resultString;
    }
    public static String MD5EncodeUTF8(String origin) {
    	return MD5Encode(origin,"UTF-8");
    }


    private static String byteArrayToHexString(byte b[]){
        StringBuffer resultSb = new StringBuffer();
        for(int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b){
        int n = b;
        if(n < 0){
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }

	 /**
	  * 
	  * 检测一个字符串是不是合法的日期格式YYYY-MM-DD
	  */

	 
	 public static boolean checkDate(String checkValue) {
		 boolean back = true;
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 try {
		 sdf.parse(checkValue);
		 } catch (ParseException e) {
			 System.out.println("异常：\"" + checkValue + "\"不是毫秒时间...");
			 back = false;
		 }
		 return back;
	 }
	 
	 /**
	  * 
	  * 检测一个字符串是不是合法的日期格式YYYY-MM-DD
	  */
	 public static boolean checkDateMiSecond(String checkValue) {
		 boolean back = true;
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		 try {
		 sdf.parse(checkValue);
		 } catch (ParseException e) {
			 System.out.println("异常：\"" + checkValue + "\"不是毫秒时间...");
			 back = false;
		 }
		 return back;
	 }
	 //检测是否为数字
    public static boolean checkFloat(String str)
    {
      try{
    	  Float.parseFloat(str);
    	  return true;
      }catch(NumberFormatException e)
      {
    	  System.out.println("异常：\"" + str + "\"不是数字...");
    	  return false;
      }
    }


}
