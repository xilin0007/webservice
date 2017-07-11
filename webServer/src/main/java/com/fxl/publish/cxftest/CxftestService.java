
package com.fxl.publish.cxftest;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CxftestService", targetNamespace = "http://service.fxl.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CxftestService {


    /**
     * 
     * @param userName
     * @param password
     * @return
     *     returns com.fxl.publish.cxftest.WsReturn
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAuth", targetNamespace = "http://service.fxl.com/", className = "com.fxl.publish.cxftest.GetAuth")
    @ResponseWrapper(localName = "getAuthResponse", targetNamespace = "http://service.fxl.com/", className = "com.fxl.publish.cxftest.GetAuthResponse")
    public WsReturn getAuth(
        @WebParam(name = "userName", targetNamespace = "")
        String userName,
        @WebParam(name = "password", targetNamespace = "")
        String password)
        throws Exception_Exception
    ;

}
