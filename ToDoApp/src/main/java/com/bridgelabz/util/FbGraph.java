package com.bridgelabz.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import com.bridgelabz.model.User;



public class FbGraph {
	private String accessToken;

	public FbGraph(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getFBGraph() {
		String graph = null;
		try {
            
			String g = "https://graph.facebook.com/v2.9/me?access_token=" + accessToken+"&fields=id,name,email,picture";
			URL u = new URL(g);
			URLConnection c = u.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					c.getInputStream()));
			String inputLine;
			StringBuffer b = new StringBuffer();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			in.close();
			graph = b.toString();
			System.out.println(graph);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in getting FB graph data. " + e);
		}
		return graph;
	}

	public User getGraphData(String fbGraph) {
		User user=new User();
		try {
			JSONObject json = new JSONObject(fbGraph);

			user.setFname(json.getString("name"));
	
			user.setEmail(json.getString("email"));
			
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in parsing FB graph data. " + e);
		}
		return user;
	}
}
