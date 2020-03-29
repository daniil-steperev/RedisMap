package ru.gnkoshelev.kontur.intern.redis.map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisConnection {
    private static JedisPool jedisPool;

    protected static JedisPool getJedisPool() {
        if (null == jedisPool) {
            createJedisPool();
        }

        return jedisPool;
    }

    private static void createJedisPool() {
        jedisPool = new JedisPool("localhost");
    }
}
