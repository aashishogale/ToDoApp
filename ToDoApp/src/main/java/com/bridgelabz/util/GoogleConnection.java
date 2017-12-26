package com.bridgelabz.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class GoogleConnection {
	public static final String APP_ID = "839674040295-jmp3e3ufmkkg5dfc63po2h83k1bkbpu3.apps.googleusercontent.com";
	public static final String APP_SECRET = "z4b5YsMZK_YVYy6I6kRPOE3J";
	public static final String REDIRECT_URI = "http://localhost:8080/ToDoApp/user/redirectGoogle/";
	public String accessToken = "";
	private static final Logger logger = Logger.getLogger("GoogleConnection");

	public String getgoogleAuthURL() throws UnsupportedEncodingException {
		String googleLoginUrl = "";
		googleLoginUrl = "https://accounts.google.com/o/oauth2/auth?client_id=" + APP_ID + "&redirect_uri="
				+ REDIRECT_URI
				+ "&state=123&response_type=code&scope=profile email&approval_prompt=force&access_type=offline";
		return googleLoginUrl;

	}

/*	public String getgoogleAccessToken(String code) {
		String googletokenUrl = "";
		googletokenUrl = "https://accounts.google.com/o/oauth2/token?client_id=" + APP_ID + "&redirect_uri="
				+ REDIRECT_URI + "&client_secret=" + APP_SECRET + "&code=" + code;
		return googletokenUrl;
	}*/

	public String getAccessToken(String code) throws IOException {
	
		String urlParameters = "code="
		                    + code
		                    + "&client_id="+APP_ID
		                    + "&client_secret="+APP_SECRET
		                    + "&redirect_uri="+REDIRECT_URI
		                    + "&grant_type=authorization_code";

		
		 //post parameters
        URL url = new URL("https://accounts.google.com/o/oauth2/token");
        URLConnection urlConn = url.openConnection();
        urlConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(
                urlConn.getOutputStream());
        writer.write(urlParameters);
        writer.flush();
        
        //get output in outputString 
        String line, outputString = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                urlConn.getInputStream()));
        while ((line = reader.readLine()) != null) {
            outputString += line;
        }
			
			
			/*
			 * if (accessToken.startsWith("{")) { throw new
			 * RuntimeException("ERROR: Access Token Invalid: " + accessToken); }
			 */
		logger.info(outputString);
		return outputString;

	}
}
