/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package com.fxl.client.hisWS;

import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.wsdl.Binding;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.AxisProperties;
import org.apache.axis.client.Call;
import org.apache.axis.encoding.TypeMapping;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.message.MessageElement;
import org.apache.axis.wsdl.gen.Parser;
import org.apache.axis.wsdl.symbolTable.BindingEntry;
import org.apache.axis.wsdl.symbolTable.Parameter;
import org.apache.axis.wsdl.symbolTable.Parameters;
import org.apache.axis.wsdl.symbolTable.ServiceEntry;
import org.apache.axis.wsdl.symbolTable.SymTabEntry;
import org.apache.axis.wsdl.symbolTable.SymbolTable;
import org.apache.axis.wsdl.toJava.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebService {
	private static final Logger log = LoggerFactory.getLogger(WebService.class);
	private String wsdlUrl;
	private String endpoint;
	private String nameSpaceUri;
	private static Map<String, Parser> wsdlParserMap = new HashMap();
	private static Map<String, Map<String, SymTabEntry>> servicesMap = new HashMap();
	private static Object object = new Object();
	private static final ParameterMode[] modes = {null, ParameterMode.IN, ParameterMode.OUT, ParameterMode.INOUT};

	public WebService(String wsdlUrl) throws Exception {
		this(wsdlUrl, "", "");
	}

	public WebService(String wsdlUrl, String endpoint, String nameSpaceUri) throws Exception {
		this.wsdlUrl = "";
		this.endpoint = "";
		this.nameSpaceUri = "";

		this.wsdlUrl = wsdlUrl;
		this.endpoint = endpoint;
		this.nameSpaceUri = nameSpaceUri;
		if (StringUtils.isEmpty(wsdlUrl)) {
			return;
		}
		Parser wsdlParser = (Parser) wsdlParserMap.get(wsdlUrl);
		if (wsdlParser == null) {
			synchronized (object) {
				wsdlParser = (Parser) wsdlParserMap.get(wsdlUrl);
				if (wsdlParser == null) {
					wsdlParser = new Parser();
					wsdlParser.run(wsdlUrl);
					wsdlParserMap.put(wsdlUrl, wsdlParser);

					Map services = initService();
					servicesMap.put(wsdlUrl, services);
				}
			}
		}

		if (StringUtils.isEmpty(this.endpoint)) {
			this.endpoint = this.wsdlUrl;
		}

		if (StringUtils.isEmpty(this.nameSpaceUri))
			this.nameSpaceUri = getCilentService(null).getServiceName().getNamespaceURI();
	}

	private Map<String, SymTabEntry> initService() {
		LinkedHashMap ret = new LinkedHashMap();
		HashMap map = ((Parser) wsdlParserMap.get(this.wsdlUrl)).getSymbolTable().getHashMap();
		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			QName key = (QName) entry.getKey();
			Vector v = (Vector) entry.getValue();
			int size = v.size();
			for (int i = 0; i < size; ++i) {
				SymTabEntry symTabEntry = (SymTabEntry) v.elementAt(i);
				if (ServiceEntry.class.isInstance(symTabEntry)) {
					ret.put(key.getLocalPart(), symTabEntry);
				}
			}
		}
		return ret;
	}

	public Call getCall(String method) throws Exception {
		return getCall(null, method);
	}

	public Call getCall(String serviceName, String method) throws Exception {
		Call call = null;
		org.apache.axis.client.Service service = getCilentService(serviceName);
		call = (Call) service.createCall(QName.valueOf(getPortName(serviceName, 0)), QName.valueOf(method));

		return call;
	}

	public Call getCall(String method, Object[] data, String[] fileds) throws Exception {
		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());

				return true;
			}
		};
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		AxisProperties.setProperty("axis.socketSecureFactory",
				"org.apache.axis.components.net.SunFakeTrustSocketFactory");
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new URL(this.endpoint));
		call.setOperation(method);
		call.setUseSOAPAction(true);
		call.setSOAPActionURI(new StringBuilder().append(this.nameSpaceUri)
				.append((this.nameSpaceUri.endsWith("/")) ? "" : "/").append(method).toString());
		call.setOperationName(new QName(this.nameSpaceUri, method));
		call.setTimeout(Integer.valueOf(45000));
		try {
			Object[] para = getParamList(method);
			for (int i = 0; i < para.length; ++i) {
				Parameter p = (Parameter) para[i];
				QName paramType = Utils.getXSIType(p);
				ParameterMode mode = modes[p.getMode()];
				if ((p.isInHeader()) || (p.isOutHeader()))
					call.addParameterAsHeader(p.getQName(), paramType, mode, mode);
				else {
					call.addParameter(p.getQName(), paramType, mode);
				}
			}
			if (para.length != 0)
				call.setReturnType(XMLType.XSD_STRING);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		if ((fileds != null) && (fileds.length > 0)) {
			for (String filed : fileds) {
				call.addParameter(new QName(this.nameSpaceUri, filed), XMLType.XSD_STRING, ParameterMode.IN);
			}
			call.setReturnType(XMLType.XSD_STRING);
		}
		call.setReturnType(new QName(this.nameSpaceUri, "UserManagerResponseUserManagerResult"));
		//call.setReturnClass(org.tempuri.UserManagerResponseUserManagerResult.class);
		call.setReturnQName(new QName(this.nameSpaceUri, "UserManagerResponseUserManagerResult"));
		//注册序列化和反序列化类 
		call.registerTypeMapping(
		        UserManagerResponseUserManagerResult.class, 
		        new QName(this.nameSpaceUri,"UserManagerResponseUserManagerResult"),
		        new BeanSerializerFactory(UserManagerResponseUserManagerResult.class, new QName(this.nameSpaceUri, "UserManagerResponseUserManagerResult")),
		        new BeanDeserializerFactory(UserManagerResponseUserManagerResult.class, new QName(this.nameSpaceUri, "UserManagerResponseUserManagerResult")));
		return call;
	}
	
	protected void registerBeanMapping(TypeMapping mapping1, Class type,  
            javax.xml.namespace.QName qname) {  
        mapping1.register(type, qname, new BeanSerializerFactory(type, qname),  
        new BeanDeserializerFactory(type, qname));  
    } 

	private org.apache.axis.client.Service getCilentService(String serviceName) throws Exception {
		return new org.apache.axis.client.Service((Parser) wsdlParserMap.get(this.wsdlUrl),
				getService(serviceName).getQName());
	}

	private String getPortName(String serverName, int index) {
		Vector v = new Vector();
		Map ports = getService(serverName).getPorts();
		Iterator i = ports.keySet().iterator();
		while (i.hasNext()) {
			v.add((String) i.next());
		}
		return ((String) v.get(index));
	}

	private javax.wsdl.Service getService(String serverName) {
		ServiceEntry serviceEntry = null;
		if ((serverName == null) || (serverName.equals(""))) {
			serverName = (String) ((Map) servicesMap.get(this.wsdlUrl)).keySet().toArray()[0];
		}

		serviceEntry = (ServiceEntry) (ServiceEntry) ((Map) servicesMap.get(this.wsdlUrl)).get(serverName);

		return serviceEntry.getService();
	}

	public Object[] getServiceList() {
		return ((Map) servicesMap.get(this.wsdlUrl)).keySet().toArray();
	}

	public Object[] getMethodList() {
		return getMethodList(null);
	}

	public Object[] getMethodList(String serverName) {
		Vector v = new Vector();

		Port port = getService(serverName).getPort(getPortName(serverName, 0));
		Binding binding = port.getBinding();
		SymbolTable table = ((Parser) wsdlParserMap.get(this.wsdlUrl)).getSymbolTable();

		BindingEntry entry = table.getBindingEntry(binding.getQName());

		Set operations = entry.getOperations();
		Iterator i = operations.iterator();
		while (i.hasNext()) {
			Operation o = (Operation) i.next();
			String s = o.getName();
			v.addElement(s);
		}
		return v.toArray();
	}

	public Object[] getParamList(String methodName) {
		return getParamList(null, methodName);
	}

	public Object[] getParamList(String serverName, String methodName) {
		BindingEntry entry = getBindingEntry(serverName);
		Operation o = getOperation(entry, methodName);
		Parameters parameters = entry.getParameters(o);
		return parameters.list.toArray();
	}

	private BindingEntry getBindingEntry(String serverName) {
		Port port = getService(serverName).getPort(getPortName(serverName, 0));
		Binding binding = port.getBinding();
		SymbolTable table = ((Parser) wsdlParserMap.get(this.wsdlUrl)).getSymbolTable();
		return table.getBindingEntry(binding.getQName());
	}

	private Operation getOperation(BindingEntry entry, String operationName) {
		Set operations = entry.getOperations();
		Iterator i = operations.iterator();
		while (i.hasNext()) {
			Operation o = (Operation) i.next();
			if (operationName.equals(o.getName())) {
				return o;
			}
		}
		return null;
	}

	public String getResponseXML(String method, Object[] params, Integer timeOut) throws Exception {
		Call call = getCall(method);
		if (timeOut != null) {
			call.setTimeout(timeOut);
		}
		Object returnStr = null;
		returnStr = call.invoke(params);

		return ((returnStr == null) ? "" : returnStr.toString());
	}

	public String getResponseXML(String method, Object[] params) throws Exception {
		Call call = getCall(method);
		Object returnStr = null;
		returnStr = call.invoke(params);
		return ((returnStr == null) ? "" : returnStr.toString());
	}

	@Deprecated
	public String getResponseXML(String method, String[] params) throws Exception {
		Call call = getCall(method);
		Object returnStr = null;
		returnStr = call.invoke(params);

		return ((returnStr == null) ? "" : returnStr.toString());
	}

	public String getResponseXML(String method, Object[] params, String[] fileds, Integer timeOut) throws Exception {
	    UserManagerResponseUserManagerResult returnStr = null;
		Call call = getCall(method, params, fileds);
		if (timeOut != null) {
			call.setTimeout(timeOut);
		}
		String result = "";
		returnStr = (UserManagerResponseUserManagerResult) call.invoke(params);
		if (returnStr != null) {
		    MessageElement[] any = returnStr.get_any();
		    result = any[0].getAsString();
        }
		return result;
	}

	public String getResponseXML(String method, Object[] params, String[] fileds) throws Exception {
		return getResponseXML(method, params, fileds, null);
	}

	public String getFromHisWsdl(String url, String method, String[] pram) {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(url);
		Object[] objects = null;
		try {
			if (pram.length == 1)
				objects = client.invoke(method, new Object[]{pram[0]});
			else if (pram.length == 2)
				objects = client.invoke(method, new Object[]{pram[0], pram[1]});
			else if (pram.length == 3)
				objects = client.invoke(method, new Object[]{pram[0], pram[1], pram[2]});
			else if (pram.length == 4) {
				objects = client.invoke(method, new Object[]{pram[0], pram[1], pram[2], pram[3]});
			} else if (pram.length == 5) {
				objects = client.invoke(method, new Object[]{pram[0], pram[1], pram[2], pram[3], pram[4]});
			} else if (pram.length == 6)
				objects = client.invoke(method, new Object[]{pram[0], pram[1], pram[2], pram[3], pram[4], pram[5]});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return objects[0].toString();
	}

	private void trustAllHttpsCertificates() throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[1];
		TrustManager tm = new MiTM();
		trustAllCerts[0] = tm;
		SSLContext sc = SSLContext.getInstance("TLS");

		sc.init(null, trustAllCerts, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	static class MiTM implements TrustManager, X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(X509Certificate[] certs) {
			return true;
		}

		public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
		}

		public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
		}
	}
}