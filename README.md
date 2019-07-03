### Design 

TweetController.java is the main resource class.

The endpoint http://localhost:8080/tweets takes a optional limit parameter. This when passed will be used to limit 
the number of results returned by the API. If not the default is 10 (Top 10 hashtags).


High level flow 
1. First validate if the limit passed as query param is within the maximum allowed.If invalid throw 400 error. 
2. Extract the hashtags from the tweets.
3. Find the count of each unique hashtag.
4. Pick the top 10 hashtags and return. 

How big could be the dataset passed to the API ? 

There are 2 approaches presented to process the tweets 

1. TweetServiceSequentialImpl - Processes the tweets sequentially
2. TweetServiceParallelImpl - Processes the tweets using java parallel streams 

Have to run performance tests for above approaches. 

To pick the top 10 hashtags we can either sort the hastags by count and pick the top 10 or use heap. 
I have used a heap datatructure as it provides better time complexity. 


### Testing 

TweetControllerTest.java tests has the testcases to test the API. It has the acceptance testcase provided in the problem
statement. 
There are also unit testcases added for few other classes. 
