package com.java.util;

import java.util.Calendar;
import java.util.UUID;



public class IdUtil {

	
	/**
	 * ��ȡ���ݺ���Ʒ��id
	 * @return
	 */
	public static String getInvoicesId(){
		
		Calendar calendar = Calendar.getInstance();
		String id = ""+calendar.getTime().getTime();
		return id;
		
	}
	
	/**
	 * ��ȡuuid�ķ���
	 * @return
	 */
	public static String getUuid(){
		
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}
	
	
}
