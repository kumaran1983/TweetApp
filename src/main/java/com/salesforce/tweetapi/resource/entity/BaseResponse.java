package com.salesforce.tweetapi.resource.entity;

public class BaseResponse {

	private Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
}
