package com.sendmsg.fe.component.tools;

public class SendingProperties {
	
	private String from;
	private String to;
	private String textMessage;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getTextMessage() {
		return textMessage;
	}
	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}
	public SendingProperties(String from, String to, String textMessage) {
		super();
		this.from = from;
		this.to = to;
		this.textMessage = textMessage;
	}
	public SendingProperties() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "SendingProperties [from=" + from + ", to=" + to + ", textMessage=" + textMessage + "]";
	}
	
	

}
