package com.fxl.service;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.fxl.entity.WSReturn;

/**
 * cxf springmvc
 */
@WebService
public interface CxftestService {
	public WSReturn getAuth(@WebParam(name = "userName") String userName, @WebParam(name = "password") String password) throws Exception;
	
	public String getPass(String userName, int type);
}

