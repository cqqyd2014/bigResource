package cn.gov.cqaudit.tools;

import java.util.ArrayList;
public class ArrayListTools{
	
  /**
   * 将输入的数字范围变为字符串的List
   * @param start
   * @param end
   */
   public static ArrayList<String> intsToStringList(int start,int end){
	   java.util.ArrayList<String> a=new java.util.ArrayList<>();
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
   

}
