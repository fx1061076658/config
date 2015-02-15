package com.eidlink.config.utils;

import org.apache.commons.lang.StringUtils;
/**
 * 
 * @author fuxing
 *
 */
public class StringUtil extends StringUtils {
	/**
	 * ����ַ��������ж��Ƿ�Ϊ��
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
	 * ���ݵ���·����ȡ���һ��ֵ
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
