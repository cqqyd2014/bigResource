package cn.gov.cqaudit.tools;

import java.util.ArrayList;
public class ArrayListTools{
  public static ArrayList<Object> add(ArrayList<Object> a,ArrayList<Object> b){

    for (int i=0;i<b.size();i++){
      a.add(b.get(i));
    }
    return a;

  }
}
