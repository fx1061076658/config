package com.eidlink.config.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ObjUtils {

  public static void main(String[] args) {
    Map<String,String> map = new HashMap<String,String>();
    map.put("a", "1");
    map.put("b", "1");
    map.put("c", "1");
    byte[] bytes = objectToByteArray(map);
    
   System.out.println(byteArrayToObject(bytes));

  }
  
  public static byte[] objectToByteArray(Object obj){
    byte[] bytes=new byte[1024];
    try {
     ByteArrayOutputStream bo = new ByteArrayOutputStream();
     ObjectOutputStream oo = new ObjectOutputStream(bo);
     oo.writeObject(obj);
     //obj to byteArray
     bytes = bo.toByteArray();

     bo.close();
     oo.close();
    } catch (Exception e) {
     System.out.println("translation" + e.getMessage());
     e.printStackTrace();
    }
    return (bytes);
    
  }
  
  public static String byteArrayToObject(byte[] bytes) {
    if(bytes == null || bytes.length ==0){
      return "";
    }
    return new String(bytes);
   }
  
  

}
