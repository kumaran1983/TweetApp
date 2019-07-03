package com.salesforce.tweetapi.resource.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

public class BaseResponse {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
}
