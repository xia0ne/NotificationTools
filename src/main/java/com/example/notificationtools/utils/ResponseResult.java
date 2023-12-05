package com.example.notificationtools.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseResult<T>{
	private long timestamp;
	private String status;
	private String message;
	private T data;

	public static <T> ResponseResult<T> success(){
		return success(null);
	}

	public static <T> ResponseResult<T> success(T data){
		return ResponseResult.<T>builder().data(data)
						.message(ResponeseStatus.SUCCESS.getDescription())
						.status(ResponeseStatus.SUCCESS.getResponeseCode())
						.timestamp(System.currentTimeMillis())
						.build();
	}

	public static <T> ResponseResult<T> fail(String message){
		return fail(null, message);
	}

	public static <T> ResponseResult<T> fail(T data, String message){
		return ResponseResult.<T>builder().data(data)
						.message(message)
						.status(ResponeseStatus.FAIL.getResponeseCode())
						.timestamp(System.currentTimeMillis())
						.build();
	}
}