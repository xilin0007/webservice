package com.fxl.service.impl;

import com.fxl.service.WeatherInterface;

//@WebService(endpointInterface = "com.fxl.service.WeatherInterface")
public class WeatherInterfaceImpl implements WeatherInterface {

	@Override
	public String getWeatherByCity(String city) {
		System.out.println("接收到客户端发送的城市名称：" + city);
		//查询天气
		String result = "今天天气不错哦。。。";
		System.out.println("返回天气信息：" + result);
		return result;
	}
}
