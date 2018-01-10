package com.bridgelabz.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.apache.log4j.Logger;
import com.bridgelabz.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	public User getProfileData(String profile) throws IOException {
		User user = new User();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(profile);
		JsonNode emailsnode = rootNode.path("emails");
		Iterator<JsonNode> itr = emailsnode.elements();
		JsonNode valueNode = mapper.readTree(itr.next().toString());
		JsonNode emailnode = valueNode.path("value");
		user.setEmail(emailnode.asText());
		JsonNode namenode = rootNode.path("name");
		JsonNode givennamenode = namenode.path("givenName");
		user.setFname(givennamenode.asText());

		return user;
	}

}
