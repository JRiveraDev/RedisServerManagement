package org.runnerer.redis.redis;

import org.runnerer.redis.ServerType;
import org.runnerer.redis.common.utils.TextUtil;
import org.runnerer.redis.config.RedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.HashMap;
import java.util.Map;

public class RedisServerManagement
{
    private static JedisPool jedisPool;

    public RedisServerManagement()
    {
        jedisPool = RedisConfig.getPool();
    }

    public static void addServer(String name, ServerType type, boolean us, String ip,  String accountOwner, String ram, String cpu) {

        String key = "server." + name;
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("serverType", type.toString());
        map.put("ip", ip);
        map.put("accountName", accountOwner);
        map.put("ram", ram);
        map.put("cpu", cpu);

        if (us) map.put("us", "true");
            else map.put("us", "false");


        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hmset(key, map);

        } catch (JedisException e) {
            if (null != jedis) {
                jedisPool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis)
                jedisPool.returnResource(jedis);
        }
    }

    public static void retrieveServer(String name) {
        String key = "server." + name;

        Jedis jedis = jedisPool.getResource();
        try {
            Map<String, String> retrieveMap = jedis.hgetAll(key);
            for (String keyMap : retrieveMap.keySet()) {
                System.out.println("Posted results in log file: " + "C:/serverlogs/" + key + ".txt");
                try
                {
                    TextUtil.write("C:/serverlogs/" + key + ".txt", keyMap + ": " + retrieveMap.get(keyMap));
                } catch (Exception e)
                {
                    System.out.print("ERROR!");
                }
            }

        } catch (JedisException e) {
            if (null != jedis) {
                jedisPool.returnBrokenResource(jedis);
                System.out.println("Seems like there's an error or the server does not exist!");
                jedis = null;
            }
        } finally {
            if (null != jedis)
                jedisPool.returnResource(jedis);
        }
    }

}
