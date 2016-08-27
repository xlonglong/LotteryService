package com.ebs.receiver.util;

import com.google.gson.Gson;

public class GsonUtil {
	private static Gson gson ;
	public static Gson getInstense(){
		if(gson==null){
			gson = new Gson();
		}
		return gson;
	}
}
