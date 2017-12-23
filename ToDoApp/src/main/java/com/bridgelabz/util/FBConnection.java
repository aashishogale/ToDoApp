package com.bridgelabz.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FBConnection {

	public static final String APP_ID = "155877838375438";
	public static final String APP_SECRET = "f3fbec334f9534ddaad35dfaed42faee";
	public static final String REDIRECT_URI = "http://localhost:8080/ToDoApp/user/redirectFB/";
	static String accessToken = "";

	public String getAuthURL() {
		String fbLoginUrl = "";
		fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id=" + FBConnection.APP_ID + "&redirect_uri="
				+ REDIRECT_URI + "&scope=public_profile,email";
		return fbLoginUrl;

	}

	public String getFBGraphUrl(String code) {
		String fbGraphUrl = "";
		fbGraphUrl = "https://graph.facebook.com/oauth/access_token?" + "client_id=" + FBConnection.APP_ID
				+ "&redirect_uri=" + REDIRECT_URI + "&client_secret=" + APP_SECRET + "&code=" + code;
		return fbGraphUrl;
	}

	public String getAccessToken(String code) {
		if ("".equals(accessToken)) {
			URL fbGraphURL;
			try {
				fbGraphURL = new URL(getFBGraphUrl(code));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException("Invalid code received " + e);
			}
			URLConnection fbConnection;
			StringBuffer b = null;
			try {
				fbConnection = fbGraphURL.openConnection();
				BufferedReader in;
				in = new BufferedReader(new InputStreamReader(fbConnection.getInputStream()));
				String inputLine;
				b = new StringBuffer();
				while ((inputLine = in.readLine()) != null)
					b.append(inputLine + "\n");
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Unable to connect with Facebook " + e);
			}

			accessToken = b.toString();
			System.out.println(accessToken);
			/*
			 * if (accessToken.startsWith("{")) { throw new
			 * RuntimeException("ERROR: Access Token Invalid: " + accessToken); }
			 */
		}
		return accessToken;
	}

}
