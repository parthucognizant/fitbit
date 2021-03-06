package com.project.fitbit.parser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	// constructor
	public JSONParser() {

	}

	// function get json from url
	// by making HTTP POST or GET mehtod
	public JSONObject makeHttpRequest(String url, String method) {

		// Making HTTP request
		try {

			// check for request method
			if(method == "POST"){
				// request method is POST
				// defaultHttpClient

				// 5. set json to StringEntity
				StringEntity se = new StringEntity(json);

				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				// 6. set httpPost Entity
				httpPost.setEntity(se);
				// 7. Set some headers to inform server about the type of the content
				httpPost.setHeader("Accept", "application/json");
				httpPost.setHeader("Content-type", "application/json");

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();

			}else if(method == "GET"){

				// 5. set json to StringEntity
				//StringEntity se = new StringEntity(json);

				// request method is GET
				DefaultHttpClient httpClient = new DefaultHttpClient();

				HttpGet httpGet = new HttpGet(url);
				// 6. set httpPost Entity
				// httpGet.setEntity(se);
				// 7. Set some headers to inform server about the type of the content
				httpGet.setHeader("Authorization", "Bearer " +
						"eyJhbGciOiJIUzI1NiJ9.eyJleHAiOj" +
						"E0NTc5MzgwNDcsInNjb3BlcyI6Indwcm8" +
						"gd2xvYyB3bnV0IHdzbGUgd3NldCB3aHI" +
						"gd3dlaSB3YWN0IHdzb2MiLCJzdWIiO" +
						"iIzVDZXODQiLCJhdWQiOiIyMjdINkIiLC" +
						"Jpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nl" +
						"c3NfdG9rZW4iLCJpYXQiOjE0NTczMzMyNDd9." +
						"PBzMUpgnCfiA0utlW9s1pp9Or8ggfcX2JS1bwK-ft20");
				httpGet.setHeader("Accept", "application/json");
				httpGet.setHeader("Content-type", "application/json");

				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Receiving Data", json);
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			Log.e("Receiving Data", json);
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}
}