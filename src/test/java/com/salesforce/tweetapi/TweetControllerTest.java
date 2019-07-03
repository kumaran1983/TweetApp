package com.salesforce.tweetapi;

import com.salesforce.tweetapi.app.TweetApplication;
import com.salesforce.tweetapi.resource.entity.HashTagResponse;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TweetApplication.class)
public class TweetControllerTest {

	@LocalServerPort
	private int port;

	private String url;

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.url = "http://localhost:" + port + "/tweets/";
	}

	@Test
	public void testAcceptanceCriteriaFile() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jsonRequest = readInput("example_input_stream.json");

		HttpEntity<String> request = new HttpEntity<String>(jsonRequest, headers);
		ResponseEntity<HashTagResponse> result = this.template.postForEntity(url, request, HashTagResponse.class);
		Assert.assertEquals(2, result.getBody().getTopTags().size());

		Assert.assertEquals("crossplatform", result.getBody().getTopTags().get(0).getHashtag());
		Assert.assertEquals("opensource", result.getBody().getTopTags().get(1).getHashtag());
		Assert.assertEquals(1, result.getBody().getTopTags().get(0).getCount());
		Assert.assertEquals(1, result.getBody().getTopTags().get(1).getCount());

	}


	@Test
	public void testInputFileWithHashTags() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jsonRequest = readInput("input.json");

		HttpEntity<String> request = new HttpEntity<String>(jsonRequest, headers);
		ResponseEntity<HashTagResponse> result = this.template.postForEntity(url, request, HashTagResponse.class);
		Assert.assertEquals(10, result.getBody().getTopTags().size());

		Assert.assertEquals("program", result.getBody().getTopTags().get(0).getHashtag());
		Assert.assertEquals("EXE", result.getBody().getTopTags().get(9).getHashtag());

	}

	@Test
	public void testTweetWithoutTextAttribute()  {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jsonRequest = readInput("TweetWithoutText.json");
		HttpEntity<String> request = new HttpEntity<String>(jsonRequest, headers);
		ResponseEntity<HashTagResponse> result = this.template.postForEntity(url, request, HashTagResponse.class);

		Assert.assertEquals(0, result.getBody().getTopTags().size());
	}


	@Test
	public void testTweetWithValidLimitParameter()  {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jsonRequest = readInput("input.json");
		HttpEntity<String> request = new HttpEntity<String>(jsonRequest, headers);
		ResponseEntity<HashTagResponse> result = this.template.postForEntity(url+"?limit=5", request, HashTagResponse.class);

		Assert.assertEquals(5, result.getBody().getTopTags().size());
	}

	@Test
	public void testTweetWithInValidLimitParameter()  {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jsonRequest = readInput("input.json");
		HttpEntity<String> request = new HttpEntity<String>(jsonRequest, headers);

		//Limit exceeds the max of 10 allowed
		ResponseEntity<HashTagResponse> result = this.template.postForEntity(url+"?limit=200", request, HashTagResponse.class);

		Assert.assertEquals("INVALID LIMIT", result.getBody().getMessage().getDescription());
	}



	private String readInput(String inputFile) {
		String json = null;
		try {
			FileInputStream fis = new FileInputStream("src/test/resources/"+inputFile);
			json = IOUtils.toString(fis, "UTF-8");
		}catch(Exception e)  {
			//do nothing
		}
		return json;
	}




}
