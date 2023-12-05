package com.example.notificationtools.utils;

import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SendMessage {
	@Value("${pushTools.api")
	private static String api;

	public static boolean sendMessage(String key, String message) throws Exception {
		String to = String.format("%s/message/push?pushkey=%s&text=%s", api, key, message);
		URL obj = new URL(to);
		HttpURLConnection con = (HttpURLConnection)obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line;
		StringBuilder sb = new StringBuilder();
		while((line = in.readLine()) != null){
			sb.append(line);
		}
		System.out.println(sb);
		return responseCode == 200;
	}

}
