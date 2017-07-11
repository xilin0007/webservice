
package com.fxl.publish.weather;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "WeatherInterfaceImpl", targetNamespace = "http://impl.service.fxl.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface WeatherInterfaceImpl {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getWeatherByCity", targetNamespace = "http://impl.service.fxl.com/", className = "com.fxl.publish.GetWeatherByCity")
    @ResponseWrapper(localName = "getWeatherByCityResponse", targetNamespace = "http://impl.service.fxl.com/", className = "com.fxl.publish.GetWeatherByCityResponse")
    @Action(input = "http://impl.service.fxl.com/WeatherInterfaceImpl/getWeatherByCityRequest", output = "http://impl.service.fxl.com/WeatherInterfaceImpl/getWeatherByCityResponse")
    public String getWeatherByCity(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}
