package com.fxl.server;

import java.lang.reflect.Method;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fxl.entity.WSReturn;

public class WSClient {
	
	private static Logger logger = LoggerFactory.getLogger(WSClient.class);

    /**
     * 调用代理
     * @param cls 服务接口代理
     * @param method 方法名
     * @param wsdl wsdl地址
     * @param params 参数Object[]
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static WSReturn invoke(Class cls,String method,String wsdl, Object[] params) throws Exception {
        synchronized(WSClient.class) {
            logger.info("[WSClient invoking] - class:"+cls.getName()+"; method:"+method+"; wsdl:"+
                    wsdl+"; params:"+getParams(params));

            JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
            factory.getInInterceptors().add(new LoggingInInterceptor());
            factory.getOutInterceptors().add(new LoggingOutInterceptor());
            factory.setServiceClass(cls);
            factory.setAddress(wsdl);
            Object cInstance = factory.create();
            Method invokeMethod = null;
            for(Method m : cls.getDeclaredMethods()){
                if(m.getName().equalsIgnoreCase(method)){
                    invokeMethod = m;
                    break;
                }
            }
            if(invokeMethod == null)
                throw new Exception("ERROR:method not found");

            WSReturn res = (WSReturn) invokeMethod.invoke(cInstance, params);
            return res;
        }
    }

    private static String getParams(Object[] params){
        StringBuilder sb = new StringBuilder("{");
        for(Object b : params){
            sb.append(b).append(",");
        }
        if(sb.length()==1)
            return "{}";
        else 
            return sb.substring(0,sb.length()-1)+"}";
    }
}
