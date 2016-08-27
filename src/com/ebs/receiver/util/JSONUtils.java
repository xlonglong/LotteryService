package com.ebs.receiver.util;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JSONUtils {

    private static Logger logger = Logger.getLogger(JSONUtils.class);

	private  static ObjectMapper objectMapper = new ObjectMapper();;
	  public static ObjectMapper getInstance() {
    	objectMapper.setSerializationInclusion(Include.NON_NULL);
    	
    	    /*//当找不到对应的序列化器时 忽略此字段  
    	    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);  
    	    //使Jackson JSON支持Unicode编码非ASCII字符  
    	    SimpleModule module = new SimpleModule();  
    	    //module.addSerializer(String.class, new StringUnicodeSerializer());  
    	    module.addDeserializer(String.class, new StringUnicodeDeserializer());
    	    objectMapper.registerModule(module);  
    	    //设置null值不参与序列化(字段不被显示)  
    	    objectMapper.setSerializationInclusion(Include.NON_NULL);  
    	    //支持结束  
*/    	
        return objectMapper;
    }

    /**
     * javaBean,list,array convert to json string
     */
    public static String obj2json(Object obj)  {
        try {
			return getInstance().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
            logger.error("对象转json异常{}",e.fillInStackTrace());
		}
        return "";
    }

    /**
     * json string convert to javaBean
     */
    public static <T> T json2pojo(String jsonStr, Class<T> clazz){
        try {
			return getInstance().readValue(jsonStr, clazz);
		} catch (Exception e) {
            logger.error("json转对象异常{}", e.fillInStackTrace());
		} 
        return null;
    }

    /**
     * json string convert to map
     */
    public static <T> Map<String, Object> json2map(String jsonStr)
            throws Exception {
        return getInstance().readValue(jsonStr, Map.class);
    }

    /**
     * json string convert to map with javaBean
     */
    public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz)
            throws Exception {
        Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr,
                new TypeReference<Map<String, T>>() {
                });
        Map<String, T> result = new HashMap<String, T>();
        for (Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * json array string convert to list with javaBean
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz)
            throws Exception {
        List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr,
                new TypeReference<List<T>>() {
                });
        List<T> result = new ArrayList<T>();
        for (Map<String, Object> map : list) {
            result.add(map2pojo(map, clazz));
        }
        return result;
    }

    /**
     * map convert to javaBean
     */
    public static <T> T map2pojo(Map map, Class<T> clazz) {
        return getInstance().convertValue(map, clazz);
    }
	
    
  
}
