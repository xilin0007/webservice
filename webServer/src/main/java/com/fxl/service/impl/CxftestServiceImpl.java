package com.fxl.service.impl;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.fxl.entity.WSReturn;

@WebService(endpointInterface = "com.fxl.service.CxftestService")
public class CxftestServiceImpl implements com.fxl.service.CxftestService {
	@Override
	@WebMethod(action = "getAuth")
	public WSReturn getAuth(String userName, String password) throws Exception {
		System.out.println("接收到客户端发送的参数userName：" + userName + "，password：" + password);
		WSReturn wsReturn = new WSReturn(1, "登录成功", null);
		return wsReturn;
	}

	@Override
	@WebMethod(action = "getPass")
	public String getPass(String userName, int type) {
		System.out.println("接收到客户端发送的参数userName：" + userName + "，type：" + type);
		/*WSReturn wsReturn = new WSReturn(1, "获取密码成功", "12345678");
		return wsReturn;*/
		return "12345678";
	}
}
