package com.eidlink.config.zk.main;

import java.io.File;
import java.io.IOException;

public class Test {

  public static void main(String[] args) {
//    Queue<String> queue = new LinkedBlockingDeque<String>();
//    queue.add("1");
//    queue.add("2");
//    queue.add("3");
//    String str = queue.poll();
//    System.out.println(str);
//    System.err.println(queue.size());
//    queue.add(str);
//    str = queue.poll();
//    System.out.println(str);
//    System.err.println(queue.size());
	  File f = new File("aaaaa.txt");
	  if(!f.exists()){
		  try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
   }

}
