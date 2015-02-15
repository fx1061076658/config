package com.eidlink.config.utils;

import org.apache.commons.lang.StringUtils;
/**
 * 
 * @author fuxing
 *
 */
public class StringUtil extends StringUtils {
	/**
	 * 多个字符串进行判断是否为空
	 * @param params
	 * @return
	 */
	public static boolean isAllNotEmpty(String ...params){
		for(String param : params){
			if(isEmpty(param)){
				return false;
			}
		}
		return true;
	}
	/**
	 * 根据导航路径获取最后一个值
	 */
	public static String getPathValue(String path,String split){
	  if(StringUtil.isAllNotEmpty(path,split)){
	    String[] datas = path.split(split);
	    int size = datas.length;
	    return datas[size-1];
	  }
	 return null;
	}
}
