package cn.gov.cqaudit.tools;

import java.util.ArrayList;
import java.util.List;

public class ListTools {

	  /**
	   * 将输入的数字范围变为字符串的List
	   * @param start
	   * @param end
	   */
	   public static List<String> intsToStringList(int start,int end){
		   java.util.List<String> a=new java.util.ArrayList<>();
		   if (start>end) {
			   int br=end;
			   end=start;
			   start=br;
		   }

		   for (int i=start-1;i<end;i++) {
			   a.add(String.valueOf(i+1));
		   }
		   return a;
	   }
	   /**
	    * 单个对象变为list
	 * @param <T>
	    * 
	    */
	   public static <T> List<T> oneToList(T object){
		   java.util.List<T> a=new java.util.ArrayList<>();
		   a.add(object);
		   return a;
	   }
}
