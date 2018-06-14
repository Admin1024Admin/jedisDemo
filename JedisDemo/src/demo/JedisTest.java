package demo;

import redis.clients.jedis.Jedis;

public class JedisTest {
	public static void main(String[] args){
		Jedis jedis = new Jedis("119.23.70.63",6379);
		System.out.println(jedis.ping());
		jedis.set("k1","v1");
		System.out.println(jedis.get("k1"));
	}
}
