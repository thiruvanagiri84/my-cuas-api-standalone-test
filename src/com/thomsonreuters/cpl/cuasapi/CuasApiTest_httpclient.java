package com.thomsonreuters.cpl.cuasapi;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import org.apache.http.client.ClientProtocolException;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

//Source: https://www.javacodegeeks.com/2012/09/simple-rest-client-in-java.html - GET/POST/PUT/DELETE
public class CuasApiTest_httpclient {

	public static void main(String[] args) throws ClientProtocolException, IOException, ParseException {
		getRequest();
		postRequest();
	}
		
	private static void getRequest() throws ClientProtocolException, IOException {
		
		//@SuppressWarnings("deprecation")
		//HttpClient client = new DefaultHttpClient(); //Depricated
		
		HttpClient client = HttpClientBuilder.create().build(); //new way of creating HttpClient
		HttpGet request = new HttpGet("http://localhost:8070/firmAdmins/2787");
		HttpResponse response = client.execute(request);
		
		/*4 Steps to read content of the response.
		//Creating HttpEntity
		HttpEntity httpEntity = response.getEntity();
		//System.out.println("response.getEntity().getContentType(): "+response.getEntity().getContentType());
		
		//Creating InputStream
		InputStream ins = response.getEntity().getContent();
		
		//Creating InputStreamReader
		InputStreamReader insReader = new InputStreamReader(ins);
		
		//Creating BufferedReader
		BufferedReader buff = new BufferedReader(insReader);*/
		
		//Above 4 steps replaced with this single line statement.
		BufferedReader bufferReader = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		
		String output = "";
		while ((output = bufferReader.readLine()) != null) {
		  System.out.println("String json: "+output);
		}
		System.out.println("------------------------");
	}

	@SuppressWarnings("unchecked")
	private static void postRequest() throws ClientProtocolException, IOException {
	  HttpClient client = HttpClientBuilder.create().build(); 
	  HttpPost post = new HttpPost("http://localhost:8070/admin");
	  
	  JSONObject json = new JSONObject();
	  json.put("orgId", 2787);
	  json.put("email", "prodtestfirm3@tr.com");
	  StringEntity input = new StringEntity( json.toString());
	  input.setContentType("application/json");
	  
	  //StringEntity input = new StringEntity('product');
	  post.setEntity(input);
	  HttpResponse response = client.execute(post);
	  BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	  String line = "";
	  while ((line = rd.readLine()) != null) {
	   System.out.println(line);
	  }
	}

}