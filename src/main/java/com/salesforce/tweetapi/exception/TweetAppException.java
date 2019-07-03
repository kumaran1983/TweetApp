package com.salesforce.tweetapi.exception;


import com.salesforce.tweetapi.resource.entity.Message;

public class TweetAppException extends RuntimeException {

	private Message tweetAppMessage;

	public TweetAppException(Message tweetAppMessage) {
		super();
		this.tweetAppMessage = tweetAppMessage;
	}

	public Message getTweetAppMessage() {
		return tweetAppMessage;
	}

}
