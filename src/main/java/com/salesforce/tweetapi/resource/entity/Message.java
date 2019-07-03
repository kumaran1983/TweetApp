package com.salesforce.tweetapi.resource.entity;

public class Message {

	private MessageType messageType;
	private int messageCode;
	private String description;

	public Message() {

	}

	public Message(MessageType messageType, int messageCode, String description) {
		super();
		this.messageType = messageType;
		this.messageCode = messageCode;
		this.description = description;
	}


	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public int getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(int messageCode) {
		this.messageCode = messageCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
