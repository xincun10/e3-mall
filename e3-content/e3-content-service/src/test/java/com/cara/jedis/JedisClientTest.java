package com.cara.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cara.common.jedis.JedisClient;

public class JedisClientTest {

	@Test
	public void testJedisClient() throws Exception
	{
		//初始化spring容器
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		//从容器中获得JedisClient对象
		JedisClient jedisClient = context.getBean(JedisClient.class);
		jedisClient.set("test", "jedisClient");
		System.out.println(jedisClient.get("test"));
	}
}
