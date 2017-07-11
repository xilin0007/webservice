package com.fxl.client;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;



public class CxftestClient {
	public static void main(String[] args) throws Exception {
		//创建服务视图
		/*CxftestServiceImplService service = new CxftestServiceImplService();
		//从服务视图中获取PortType对象
		CxftestService portType = service.getCxftestServiceImplPort();
		//调用服务端方法
		WsReturn ret = portType.getAuth("admin", "123456");
		System.out.println(ret.getMsgbox());*/
		
		/*try {
			System.out.println("开始");
			String endpoint = "http://192.168.1.235:8080/webServer/cxf/cxftest?wsdl";  
            Service service = new Service();  
            Call call = (Call) service.createCall();  
            call.setTargetEndpointAddress(endpoint);  
            // WSDL里面描述的接口名称(要调用的方法)  
            //call.setOperationName(new QName("http://service.fxl.com/", "getAuth"));
            //call.setOperationName("getAuth");
            // 接口方法的参数名, 参数类型,参数模式  IN(输入), OUT(输出) or INOUT(输入输出)  
            call.addParameter("userName", XMLType.XSD_STRING, ParameterMode.IN);  
            call.addParameter("password", XMLType.XSD_STRING, ParameterMode.IN);  
            // 设置被调用方法的返回值类型  
            call.setReturnType(XMLType.XSD_ANY);  
            //设置方法中参数的值  
            Object[] paramValues = new Object[] {"admin", "123456789"};  
            // 给方法传递参数，并且调用方法  
            //String result = (String) call.invoke(paramValues);
            Object result = call.invoke(paramValues);
            System.out.println("result is " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		//调不带@WebParam参数的方法
		try {
			String serviceUrl = "http://192.168.1.235:8080/webServer/cxf/cxftest?wsdl";
			String tns = "http://service.fxl.com/";
			String methodName = "getPass";
            //使用RPC方式调用WebService  
            RPCServiceClient serviceClient = new RPCServiceClient();  
            Options options = serviceClient.getOptions();  
            //设置2秒超时  
            options.setTimeOutInMilliSeconds(2000L);  
            //指定调用WebService的URL  
            EndpointReference targetEPR = new EndpointReference(serviceUrl);  
            options.setTo(targetEPR);  
             
            //指定接口方法的参数值  
            Object[] opAddEntryArgs = new Object[] {"admin", 1};
            //指定方法返回值的数据类型的Class对象  
            Class[] classes = new Class[] { String.class };  
            //指定调用的方法及WSDL文件的命名空间  QName("targetNamespace","method Name");  
            QName qName = new QName(tns, methodName);  
            //调用getVersioin方法并输出该方法的返回值,  
            //返回对象是一个Object的数组,拿数组的第一个值，转换强转即可  
            Object result = serviceClient.invokeBlocking(qName, opAddEntryArgs, classes)[0];
            //OMElementImpl ele =  (OMElementImpl) result;
            System.out.println("返回值result=" + result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//调带@WebParam参数的方法
		try {
			String serviceUrl = "http://192.168.1.235:8080/webServer/cxf/cxftest?wsdl";
			String tns = "http://service.fxl.com/";
			String methodName = "getAuth";
			
            //使用RPC方式调用WebService  
            RPCServiceClient serviceClient = new RPCServiceClient();  
            Options options = serviceClient.getOptions();  
            //设置2秒超时  
            options.setTimeOutInMilliSeconds(2000L);  
            //指定调用WebService的URL  
            EndpointReference targetEPR = new EndpointReference(serviceUrl);  
            options.setTo(targetEPR);  
            
            Map<String, String> params = new HashMap<String, String>();
    		params.put("userName", "admin");
    		params.put("password", "123456");
            //添加参数并获取方法
    		OMElement method = getMethod(tns, methodName, params);
    		OMElement result = serviceClient.sendReceive(method);
    		//result就是获取的数据,剩下需要自己解析
    		System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
}
