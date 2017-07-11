package com.fxl.entity;

import java.io.Serializable;

public class WSReturn implements Serializable {
	
	private static final long serialVersionUID = 6589008794478961923L;
	
	private int msg;
	private String msgbox;
	private Object data;
	public WSReturn(int msg, String msgbox, Object data) {
		this.msg = msg;
		this.msgbox = msgbox;
		this.data = data;
	}
	public int getMsg() {
		return msg;
	}
	public void setMsg(int msg) {
		this.msg = msg;
	}
	public String getMsgbox() {
		return msgbox;
	}
	public void setMsgbox(String msgbox) {
		this.msgbox = msgbox;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "WSReturn [msg=" + msg + ", msgbox=" + msgbox + ", data=" + data
				+ "]";
	}
}
