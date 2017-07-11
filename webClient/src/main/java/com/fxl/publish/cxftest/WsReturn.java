
package com.fxl.publish.cxftest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="msg" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="msgbox" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsReturn", propOrder = {
    "data",
    "msg",
    "msgbox"
})
public class WsReturn {

    protected Object data;
    protected int msg;
    protected String msgbox;

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setData(Object value) {
        this.data = value;
    }

    /**
     * Gets the value of the msg property.
     * 
     */
    public int getMsg() {
        return msg;
    }

    /**
     * Sets the value of the msg property.
     * 
     */
    public void setMsg(int value) {
        this.msg = value;
    }

    /**
     * Gets the value of the msgbox property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgbox() {
        return msgbox;
    }

    /**
     * Sets the value of the msgbox property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgbox(String value) {
        this.msgbox = value;
    }

}
