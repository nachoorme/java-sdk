package com.signaturit.api.java_sdk;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import okhttp3.MediaType;
import okhttp3.MultipartBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestHelper {

	/**
	 * 
	 * @param route
	 * @param parameters
	 * @return
	 */
	protected static String putGetParamsToUrl(String route, Map<String, Object> parameters)
	{
		if (parameters != null) {
			for (Entry<String, Object>  entry : parameters.entrySet()) {
				StringBuilder routeBuilder = new StringBuilder();
			    if (entry.getKey().equals("ids")) {
			    	
			    	String delim="";
			    	for (CharSequence i : (CharSequence[]) entry.getValue()) {
			    		routeBuilder.append(delim).append(i);
			    		delim = ",";
			    	}
			    }
			    
			    route += String.format(
			    	"&%s=%s", entry.getKey(), routeBuilder.toString()
			    );
			}
		}
		return route;
	}
	
	/**
	 * 
	 * @param bodyBuilder
	 * @param recipients
	 * @param key
	 */
	protected static void parseParameters(Builder bodyBuilder, Object recipients, String key) 
	{	
		if (recipients instanceof String) {
			bodyBuilder.addFormDataPart(key, recipients.toString());
		} else if (recipients instanceof int[]) {
			int[] listArray = (int[]) recipients;
			int i = 0;
			for (Object item: listArray) {
				bodyBuilder.addFormDataPart(key+"["+i+"]", item.toString());
				++i;
			}
		} else if (recipients instanceof String[]) {
			String[] listArray = (String[]) recipients;
			int i = 0;
			for (Object item: listArray) {
				bodyBuilder.addFormDataPart(key+"["+i+"]", item.toString());
				++i;
			}
		} else if (recipients instanceof ArrayList<?>) {
			int i = 0;
			for ( HashMap<String, Object> recipient: (ArrayList<HashMap<String, Object>>) recipients ) {
				for (Entry<String, Object>  entry : recipient.entrySet()) {
					
					if (entry.getValue() instanceof ArrayList<?> || entry.getValue() instanceof HashMap) {
						parseParameters(bodyBuilder, entry.getValue(), key+"["+i+"]["+entry.getKey()+"]");
					} else if (entry.getValue() instanceof HashMap) {
						parseParameters(bodyBuilder, entry.getValue(), key+"["+i+"]["+entry.getKey()+"]");
					} else {
						parseParameters(bodyBuilder, entry.getValue(), key+"["+entry.getKey()+"]");
					}
				}
				++i;
			}
		} else if (recipients instanceof HashMap) {
			for (Entry<String, Object> entry: ((Map<String, Object>) recipients).entrySet()) {
				if (entry.getValue() instanceof ArrayList<?> || entry.getValue() instanceof HashMap) {
					parseParameters(bodyBuilder, entry.getValue(), key+"["+entry.getKey()+"]");
				} else if (entry.getValue() instanceof HashMap) {
					parseParameters(bodyBuilder, entry.getValue(), key+"["+entry.getKey()+"]");
				} else {
					parseParameters(bodyBuilder, entry.getValue(), key+"["+entry.getKey()+"]");
				}
			}
		}
	}
	
	/**
	 * 
	 * @param route
	 * @param token
	 * @param parameters
	 * @param files
	 * @return
	 * @throws IOException 
	 */
	protected static Response requestPost(String route, String token, Map<String, Object> parameters, ArrayList<String> files) 
			throws IOException 
	{
		OkHttpClient client = new OkHttpClient();
		
		Builder requestPostBuilder = new okhttp3.MultipartBody.Builder()
		.setType(okhttp3.MultipartBody.FORM);
		
		for (Entry<String, Object> entry: parameters.entrySet()) {
			parseParameters(requestPostBuilder, entry.getValue(), entry.getKey());
		}
		
		if (files != null) {
			int i = 0;
			for (String temp : files) {
				File file = new File(temp);
				requestPostBuilder.addFormDataPart(
						"files["+i+"]", 
						file.getName(),
						RequestBody.create(MediaType.parse("*/*"), file)
				);
				++i;
			}
		}
	
		RequestBody requestBody = requestPostBuilder.build();
		Request request = new Request.Builder()
				.post(requestBody)
				.addHeader("Authorization", token)
				.addHeader("user-agent", "signaturit-java-sdk 1.0.0")
				.url("http://api.signaturit.dev/app_dev.php/v3/signatures.json")
				.build();
		
		Response response = client.newCall(request).execute();
		
		return response;
	}
	
	/**
	 * 
	 * @param route
	 * @param token
	 * @return
	 * @throws IOException
	 */
	protected static Response requestGet(String route,String token) throws IOException 
	{
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder()
				.get()
				.addHeader("Authorization", token)
				.addHeader("user-agent", "signaturit-java-sdk 1.0.0")
				.url(route)
				.build();
		
		Response response = client.newCall(request).execute();
		
		return response;
	}
	
	/**
	 * 
	 * @param route
	 * @param token
	 * @return
	 * @throws IOException
	 */
	protected static InputStream requestGetFile(String route, String token) throws IOException 
	{
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder()
				.get()
				.addHeader("Authorization", token)
				.addHeader("user-agent", "signaturit-java-sdk 1.0.0")
				.url(route)
				.build();
		
		Response response = client.newCall(request).execute();
		
		return response.body().byteStream();
	}
	
	/**
	 * 
	 * @param route
	 * @param token
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	protected static Response requestPatch(String route, String token, HashMap<String, Object> parameters) throws IOException 
	{
		OkHttpClient client = new OkHttpClient();
		
		Builder requestPostBuilder = new okhttp3.MultipartBody.Builder()
		.setType(okhttp3.MultipartBody.FORM);
		
		for (Entry<String, Object> entry: parameters.entrySet()) {
			parseParameters(requestPostBuilder, entry.getValue(), entry.getKey());
		}
		
		RequestBody requestBody = requestPostBuilder.build();
		Request request = new Request.Builder()
				.post(requestBody)
				.addHeader("Authorization", token)
				.addHeader("user-agent", "signaturit-java-sdk 1.0.0")
				.url("http://api.signaturit.dev/app_dev.php/v3/signatures.json")
				.build();
		
		Response response = client.newCall(request).execute();
		
		return response;
	}
}
