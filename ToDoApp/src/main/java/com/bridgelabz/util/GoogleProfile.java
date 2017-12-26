package com.bridgelabz.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bridgelabz.model.GoogleUser;
import com.bridgelabz.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GoogleProfile {
	private String accessToken;
	private static final Logger logger = Logger.getLogger("GoogleProfile");

	public GoogleProfile(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getgoogleProfile() {
		String profile = null;
		String outputString = "";
		try {

			String g = "https://www.googleapis.com/plus/v1/people/me?access_token=" + accessToken;
			URL u = new URL(g);

			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.addRequestProperty("User-Agent", "Mozilla/4.76");

			BufferedReader reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				outputString += line;
			}
			System.out.println(outputString);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in getting google profile data. " + e);
		}
		return outputString;
	}

	public User getProfileData(String profile) {
		User user = new User();
		GoogleUser guser=new Gson().fromJson(profile, GoogleUser.class);
		logger.info(guser.toString());
		user.setEmail(guser.getEmail());
		user.setFname(guser.getName());
		return user;
	}

}
