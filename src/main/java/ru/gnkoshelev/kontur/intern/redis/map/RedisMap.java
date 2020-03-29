package ru.gnkoshelev.kontur.intern.redis.map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

/**
 * @author Gregory Koshelev
 */
public class RedisMap implements Map<String,String> {
    private static String thisKey = null;

    public RedisMap() {
        Random random = new Random();
        thisKey = this.toString() + random.nextInt(1000000);
    }

    @Override
    public int size() {
        try (Jedis jedis = RedisConnection.getJedisPool().getResource()) {
            Map<String, String> values = jedis.hgetAll(thisKey);
            return values.size();
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        try (Jedis jedis = RedisConnection.getJedisPool().getResource()) {
            Map<String, String> values = jedis.hgetAll(thisKey);
            return values.containsKey(key);
        }
    }

    @Override
    public boolean containsValue(Object value) {

       try (Jedis jedis = RedisConnection.getJedisPool().getResource()) {
           Map<String, String> values = jedis.hgetAll(thisKey);
           return values.containsValue(value);
       }
    }

    @Override
    public String get(Object key) {
        try (Jedis jedis = RedisConnection.getJedisPool().getResource()) {
            return jedis.hget(thisKey, key.toString());
        }
    }

    @Override
    public String put(String key, String value) {
        try (Jedis jedis = RedisConnection.getJedisPool().getResource()) {
            Boolean wasEmpty = isEmpty();

            jedis.hset(thisKey, key, value);

            if (wasEmpty) {
                return null;
            } else {
                return value;
            }
        }
    }

    @Override
    public String remove(Object key) {
        try (Jedis jedis = RedisConnection.getJedisPool().getResource()) {
            String value = jedis.hdel(thisKey, key.toString()).toString();
            return value;
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        for (Entry<? extends String, ? extends String> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        try (Jedis jedis = RedisConnection.getJedisPool().getResource()) {
            jedis.del(thisKey);
        }
    }

    @Override
    public Set<String> keySet() {
        try (Jedis jedis = RedisConnection.getJedisPool().getResource()) {
            Map<String, String> values = jedis.hgetAll(thisKey);
            return values.keySet();
        }
    }

    @Override
    public Collection<String> values() {
        try (Jedis jedis = RedisConnection.getJedisPool().getResource()) {
            Map<String, String> values = jedis.hgetAll(thisKey);
            return values.values();
        }
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        try (Jedis jedis = RedisConnection.getJedisPool().getResource()) {
            Map<String, String> values = jedis.hgetAll(thisKey);
            return values.entrySet();
        }
    }
}
