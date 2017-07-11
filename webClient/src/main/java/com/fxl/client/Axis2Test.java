/**
 * This file created at 2015年1月14日.
 *
 * Copyright (c) 2002-2015 Bingosoft, Inc. All rights reserved.
 */
package com.fxl.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

/**
 * <code>{@link Axis2Test}</code>
 *
 * axis2测试
 *
 * @author fengjianbo
 */
public class Axis2Test {
	public static void main(String[] args) throws Exception {
		
		String url = "";//调用地址
		String methodName = "";//调用方法
		String tns = "";//调用方法的tns
		//调用接口时所附带的参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("startTime", "2015-01-11");
		params.put("endTime", "2015-01-15");
		// 头部验证系想你
		String appId = "";
		String appKey = "";


		List<Map<String, String>> headParams = new ArrayList<Map<String, String>>();
		
		Map<String, String> headParam = new HashMap<String, String>();
		headParam.put("id", appId);
		headParam.put("key", appKey);
		headParams.add(headParam);
		
		
		List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
		Map<String, String> param = new HashMap<String, String>();
		param.put("tns", "");//tns
		param.put("tnsName", "");//默认空就好
		param.put("methodName", "");//验证对应实体
		listMap.add(param);
		//如果有多个头部验证
//		Map<String, String> param1 = new HashMap<String, String>();
//		param1.put("tns", "");
//		param1.put("tnsName", "");
//		param1.put("methodName", "");
//		listMap.add(param1);
		
		Options options = new Options();
		options.setTimeOutInMilliSeconds(600000L);
		// 指定调用WebService的URL
		EndpointReference targetEPR = new EndpointReference(url);
		options.setTo(targetEPR);
		ServiceClient serviceClient = new ServiceClient();
		serviceClient.setOptions(options);
		//头部验证
		addValidation(serviceClient, listMap, headParams);
		
		//添加参数并获取方法
		OMElement method = getMethod(tns, methodName, params);
		OMElement result = serviceClient.sendReceive(method);
		//result就是获取的数据,剩下需要自己解析
		System.out.println(result.toString());

		
	}
	
	

	
	/**
	 * 获取请求方法
	 * @param tns 命名空间
	 * @param methodName 方法名
	 * @param params 参数
	 * @return
	 */
	private static OMElement getMethod(String tns, String methodName, Map<String, String> params){
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace(tns, "");
		OMElement method = fac.createOMElement(methodName, omNs);
		if(params != null) {
			for (String key : params.keySet()) {
				OMElement param = fac.createOMElement(key, null);
				param.addChild(fac.createOMText(param, params.get(key)));
				method.addChild(param);
			}
			method.build();
		}
		return method;
	}
	
	
	/**
	 * 添加头部验证
	 * @return
	 */
	private static void addValidation(ServiceClient serviceClient, List<Map<String, String>> listMap, List<Map<String, String>> paramList){
		OMFactory fac = OMAbstractFactory.getOMFactory();
		for (int i = 0; i < listMap.size(); i++) {
			Map<String, String> map = listMap.get(i);
			OMNamespace omNs = fac.createOMNamespace(map.get("tns"), map.get("tnsName"));
			OMElement header = fac.createOMElement(map.get("methodName"), omNs);
			Map<String, String> params = paramList.get(i);
			for (String paramKey : params.keySet()) {
				OMElement param = fac.createOMElement(paramKey, omNs);
				param.addChild(fac.createOMText(param, params.get(paramKey)));
				header.addChild(param);
			}
			serviceClient.addHeader(header);
		}

		
	}
	
	
}
