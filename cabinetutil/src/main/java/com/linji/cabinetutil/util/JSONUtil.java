package com.linji.cabinetutil.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * JSON转换工具库(Json String to Object,Object to Json String,代码由Google提供 google-gson Version 2.2.4)
 * @author qingf
 */
public class JSONUtil {
	
	private static Gson gson = new Gson();
	
	public static Gson getGson() {
		return gson;
	}
	
	public static String toJson(Object obj) {
		try {
			return gson.toJson(obj);
		} catch (Exception e) {
			LogUtil.e("json:"+e.toString());
			return null;
		}
	}
	
	public static <T> T fromJson(String json, Class<T> classOfT){
		try {
			return gson.fromJson(json, classOfT);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e("json:"+e.toString());
			return null;
		}
	}
	
	public static <T> T fromJson(JsonElement jsonelement, Class<T> classOfT){
		try {
			return gson.fromJson(jsonelement, classOfT);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e("json:"+e.toString());
			return null;
		}
	}
	
	public static <T> Map<String,JsonElement> mapJsonFromJson(String json) {
		try {
			JsonElement el = new JsonParser().parse(json);
			if (el.isJsonObject() == false) return null;
			Map<String,JsonElement> objs = new Hashtable<String,JsonElement>();
			for (Entry<String, JsonElement> entry: el.getAsJsonObject().entrySet()) {
				objs.put(entry.getKey(), entry.getValue());
			}
			return objs;
		}
		catch (Exception e) {
			e.printStackTrace();
			LogUtil.e("json:"+e.toString());
			return null;
		}
	}
	
	public static List<JsonElement> listJsonFromJson(String json) {
		try {
			JsonElement el = new JsonParser().parse(json);
			if (!el.isJsonObject()) return null;
			List<JsonElement> objs = new ArrayList<JsonElement>();
			for (Entry<String, JsonElement> entry: el.getAsJsonObject().entrySet()) {
				objs.add(entry.getValue());
			}
			return objs;
		}
		catch (Exception e) {
			e.printStackTrace();
			LogUtil.e("json:"+e.toString());
			return null;
		}
	}
	
	public static Hashtable<String,String> fromJson(String Json) {
		try {
			return gson.fromJson(Json, new TypeToken<Hashtable<String,String>>(){}.getType());
		} catch (Exception e) {
			return null;
		}
	}
	
	public static <T> List<T> fromJsons(String json, Class<T> classOfT) {
		try {
			JsonElement el = new JsonParser().parse(json);
			if (!el.isJsonArray() || el.getAsJsonArray().size()<=0) return null;
			
			Iterator<?> it = el.getAsJsonArray().iterator();
			List<T> objs = new ArrayList<T>();
			while(it.hasNext()) {
			  objs.add(gson.fromJson((JsonElement)it.next(), classOfT));
			}
			return objs;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e("json:"+e.toString());
			return null;
		}
	}
	
	public static String toJsonSort(Object obj) {
		try {
			Map<Object,Object> map = gson.fromJson(toJson(obj), new TypeToken<TreeMap<Object,Object>>(){}.getType());
			return gson.toJson(map);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e("json:"+e.toString());
			return null;
		}
	}
	
	public static Map<Object,Object> mapFromJson(String Json) {
		try {
			return gson.fromJson(Json, new TypeToken<TreeMap<Object,Object>>(){}.getType());
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e("json:"+e.toString());
			return null;
		}
	}
}