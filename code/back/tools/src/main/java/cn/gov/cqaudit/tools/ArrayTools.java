package cn.gov.cqaudit.tools;

public class ArrayTools<T> {
	
	public static <T> java.util.List<T> convertList(T[] array){
		java.util.ArrayList<T> list=new java.util.ArrayList<T>();
		for (int i=0;i<array.length;i++) {
			if (array[i]!=null) {
				list.add(array[i]);
			}
			
		}
		return list;
	}

}
