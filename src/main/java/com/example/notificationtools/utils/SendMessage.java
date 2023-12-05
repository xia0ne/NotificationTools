package com.example.notificationtools.utils;

import cn.hutool.core.date.DateUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Log
public class SendMessage {
//	@Value("${pushTools.api}")
	private static String api = "http://api2.pushdeer.com";

	public static void sendMessage(String key, String message) throws Exception {
		String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
		String to = String.format("%s/message/push?pushkey=%s&text=%s", api, key, encodedMessage);
		URL obj = new URL(to);
		HttpURLConnection con = (HttpURLConnection)obj.openConnection();
		con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);
		con.setRequestMethod("GET");
		log.info("在 " + DateUtil.now() + " 发送了一条消息 " + message + " 状态" + con.getResponseCode());
	}

}
