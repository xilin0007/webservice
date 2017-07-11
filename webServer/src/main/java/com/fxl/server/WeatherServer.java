package com.fxl.server;

import javax.xml.ws.Endpoint;

import com.fxl.service.impl.WeatherInterfaceImpl;

/**
 * 天气查询服务发布类
 * 查看wsdl：http://192.168.1.235:12345/weather?wsdl
 */
public class WeatherServer {
	public static void main(String[] args) {
		//1 第一个参数：服务发布URL
		String address =  "http://192.168.1.235:12345/weather";
		//2 第二个产数：SEI实现类对象
		WeatherInterfaceImpl impl = new WeatherInterfaceImpl();
		Endpoint.publish(address, impl);
		System.out.println("发布成功！");
	}
}
