
package com.fxl.publish.cxftest;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.fxl.publish.cxftest package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetAuth_QNAME = new QName("http://service.fxl.com/", "getAuth");
    private final static QName _Exception_QNAME = new QName("http://service.fxl.com/", "Exception");
    private final static QName _GetAuthResponse_QNAME = new QName("http://service.fxl.com/", "getAuthResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.fxl.publish.cxftest
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAuthResponse }
     * 
     */
    public GetAuthResponse createGetAuthResponse() {
        return new GetAuthResponse();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link GetAuth }
     * 
     */
    public GetAuth createGetAuth() {
        return new GetAuth();
    }

    /**
     * Create an instance of {@link WsReturn }
     * 
     */
    public WsReturn createWsReturn() {
        return new WsReturn();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuth }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.fxl.com/", name = "getAuth")
    public JAXBElement<GetAuth> createGetAuth(GetAuth value) {
        return new JAXBElement<GetAuth>(_GetAuth_QNAME, GetAuth.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.fxl.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuthResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.fxl.com/", name = "getAuthResponse")
    public JAXBElement<GetAuthResponse> createGetAuthResponse(GetAuthResponse value) {
        return new JAXBElement<GetAuthResponse>(_GetAuthResponse_QNAME, GetAuthResponse.class, null, value);
    }

}
