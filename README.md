# redis-app
Sample Java app for Redis cache.

The purpose of this sample project is to find out why an Exception is thrown when we try to read all keys from a Redis cache, using Jedis client.

This project is using Java 8, Maven (3+), Spring Data Redis (1.8.3.RELEASE), Lettuce (4.3.1.Final) and Jedis (2.9.0).
This project was tested with Redis server 3.2.9.

How to see the problem.
Clone the project: git clone https://github.com/michaelweber2/redis-app.git
Build the project: cd redis-app && mvn clean package

Make sure you have a Redis running locally and using the port 6379. 
In case your Redis is not running locally and/or is using a different port, you will need to provide them via system properties.

Run the project with Jedis client: java -cp target/redis-app.jar test.RedisApp
Run the project with Lettuce client: java -DuseJedis=false -cp target/redis-app.jar test.RedisApp.

Example of running the project againts a non local Redis: java -Dhostname=myHostname -Dport=9999 -cp target/redis-app.jar test.RedisApp

When you run the project with Lettuce, everything is working as expected.
When you will run the project with Jedis the following Exception is trown and the question is why:
org.springframework.data.redis.serializer.SerializationException: Cannot deserialize; nested exception is org.springframework.core.serializer.support.SerializationFailedException: Failed to deserialize payload. Is the byte array a result of corresponding serialization for DefaultDeserializer?; nested exception is java.io.StreamCorruptedException: invalid stream header: EFBFBDEF
	at org.springframework.data.redis.serializer.JdkSerializationRedisSerializer.deserialize(JdkSerializationRedisSerializer.java:82)
	at test.MyCache.deserializeKeys(MyCache.java:70)
	at test.MyCache.getKeys(MyCache.java:52)
	at test.RedisApp.main(RedisApp.java:54)
Caused by: org.springframework.core.serializer.support.SerializationFailedException: Failed to deserialize payload. Is the byte array a result of corresponding serialization for DefaultDeserializer?; nested exception is java.io.StreamCorruptedException: invalid stream header: EFBFBDEF
	at org.springframework.core.serializer.support.DeserializingConverter.convert(DeserializingConverter.java:78)
	at org.springframework.core.serializer.support.DeserializingConverter.convert(DeserializingConverter.java:36)
	at org.springframework.data.redis.serializer.JdkSerializationRedisSerializer.deserialize(JdkSerializationRedisSerializer.java:80)
	... 3 more
Caused by: java.io.StreamCorruptedException: invalid stream header: EFBFBDEF
	at java.io.ObjectInputStream.readStreamHeader(ObjectInputStream.java:808)
	at java.io.ObjectInputStream.<init>(ObjectInputStream.java:301)
	at org.springframework.core.ConfigurableObjectInputStream.<init>(ConfigurableObjectInputStream.java:63)
	at org.springframework.core.ConfigurableObjectInputStream.<init>(ConfigurableObjectInputStream.java:49)
	at org.springframework.core.serializer.DefaultDeserializer.deserialize(DefaultDeserializer.java:68)
	at org.springframework.core.serializer.support.DeserializingConverter.convert(DeserializingConverter.java:73)
	... 5 more
