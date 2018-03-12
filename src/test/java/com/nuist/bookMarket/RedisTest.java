package com.nuist.bookMarket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void testConectionNum(){
        Jedis jedis = null;
        for(int i = 0; i < 600; i++) {
            try {
                jedis = jedisPool.getResource();
                jedis.set("foo", "bar");
                System.out.println("第" + (i+1) + "个连接, 得到的值为" + jedis.get("foo"));
            } finally {
                if (jedis != null) {
//                  jedis.close();
                    jedisPool.returnResource(jedis);
                }
            }
        }
    }
}
