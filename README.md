### Requirement 

Write a web service that can evaluate a dump of tweets and produce a summary of unique hashtags and the number of occurrences. A skeleton has been provided for you that will parse the incoming JSON into a list of tweets. Your challenge is to:

- Extract the hashtags for each tweet
- Store unique hashtags and their counts (Duplicate hashtags in a single tweet should count as 1)
- Provide a summary of the top *10* hashtags as a JSON response
- Provide adequate unit testing (as time permits)

### Design 

TweetController.java is the main resource class.

The endpoint http://localhost:8080/tweets?limit=10 takes a optional limit query parameter. This when passed will be used to limit 
the number of results returned by the API. If not the default is 10 (Top 10 hashtags).

#### High level flow 
1. First validate if the limit passed as query param is within the maximum allowed.If invalid throw 400 error. 
2. Extract the hashtags from the tweets.
3. Find the count of each unique hashtag.
4. Pick the top 10 hashtags and return. 

Size of the dataset passed to the API ? 

There are 2 approaches presented to process the tweets 

1. TweetServiceSequentialImpl - Processes the tweets sequentially
2. TweetServiceParallelImpl - Processes the tweets using java parallel streams 

Have to run performance tests for above approaches. 

There are 2 approches to pick the top 10 hashtags 
1. Sort the unique hashtag in decending order of count. Pick the top 10. Time complexity nlogn where n is the number of
hashtags
2. Keep adding the hashtag with count into a minheap. Once the heap size exceeds the limit of 10 then pop 
of the hashtag with the least count. By this way in the end the minHeap will contain the hashtags with the most count.
Time complexity is nlogl where l is the limit (10 in the case of top 10 hashtags)



### Sample Request/Response 

Request
```
POST /tweets HTTP/1.1
Host: localhost:8080
Accept: application/json
Content-Type: application/json
Cache-Control: no-cache
Postman-Token: f6737fd4-d614-aec0-1457-980e098387c1

[
  {
    "id": 6218016346307687282,
    "id_str": "6218016346307687282",
    "contributors": null,
    "coordinates": {
      "lat": "73.3294380554758",
      "lng": "-135.14778637735031",
      "state": "fl"
    },
    "created_at": "sun jun 16 00:00:00 +0000 2013",
    "favorite_count": 1871,
    "favorited": false,
    "geo": null,
    "in_reply_to_screen_name": null,
    "in_reply_to_status_id": null,
    "in_reply_to_user_id_str": null,
    "in_reply_to_user_id": null,
    "is_quote_status": false,
    "lang": "kp",
    "nil": null,
    "place": null,
    "possibly_sensitive": false,
    "retweet_count": 3736,
    "retweeted_status": null,
    "retweeted": false,
    "source": "<a href=\"http://example.com/herminia\" rel=\"nofollow\">jast-hegmann</a>",
    "text": "you can't connect the matrix without navigating the multi-byte agp firewall! #opensource #crossplatform #opensource",
    "truncated": false
  }
]
```
Response 
```
{
    "topTags": [
        {
            "hashtag": "crossplatform",
            "count": 1
        },
        {
            "hashtag": "opensource",
            "count": 1
        }
    ]
}
```



### Testing 

TweetControllerTest.java tests has the testcases to test the API. It has the acceptance testcase provided in the problem
statement. 
There are also unit testcases added for few other classes. 
