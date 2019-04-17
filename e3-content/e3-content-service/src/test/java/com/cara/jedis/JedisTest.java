package com.cara.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	@Test
	public void testJedis() throws Exception {
		//创建一个连接Jedis对象，参数：host,port
		Jedis jedis = new Jedis("192.168.42.130", 6379);
		//至二级使用jedis操作redis,所有jedis的命令都对应一个方法
		jedis.set("test", "my first jedis test");
		String str = jedis.get("test");
		System.out.println(str);
		//关闭连接
		jedis.close();
	}
	
	@Test
	public void testJedisPool() throws Exception{
		//创建一个连接池对象，两个参数：host,port
		JedisPool jedisPool = new JedisPool("192.168.42.130", 6379);
		//从连接池获取一个连接，就是一个jedis对象
		Jedis jedis = jedisPool.getResource();
		//使用jedis操作redis
		String str = jedis.get("test");
		System.out.println(str);
		//关闭连接，连接池回收资源
		jedis.close();
		//关闭连接池
		jedisPool.close();
	}
	
	@Test
	public void testJedisCluster() throws Exception {
		//创建一连接，JedisCluster对象,在系统中是单例存在
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.42.130", 7001));
		nodes.add(new HostAndPort("192.168.42.130", 7002));
		nodes.add(new HostAndPort("192.168.42.130", 7003));
		nodes.add(new HostAndPort("192.168.42.130", 7004));
		nodes.add(new HostAndPort("192.168.42.130", 7005));
		nodes.add(new HostAndPort("192.168.42.130", 7006));
		
		//创建一个JedisCluster对象，有一个参数nodes是一个set类型，set中包含若干个HostAndPort对象
		JedisCluster cluster = new JedisCluster(nodes);
		//直接使用JedisCluster对象操作redis
		cluster.set("cluster-test", "my jedis cluster test");
		String str = cluster.get("cluster-test");
		System.out.println(str);
		//程序结束时需要关闭JedisCluster对象
		cluster.close();
	}
}
