package org.runnerer.redis.config;

import redis.clients.jedis.JedisPool;

public class RedisConfig
{
    private static String redisHost = "localhost";
    private static Integer redisPort = 6379;
    private static JedisPool pool = null;

    public RedisConfig(String hostName, Integer redisPort)
    {
        this.redisHost = hostName;
        this.redisPort = redisPort;

        try
        {
            pool = new JedisPool(this.redisHost, this.redisPort);
        } catch (Exception e)
        {
            System.out.print("Error while connecting to Redis, Houston, we have a problem!");
        }
    }

    public static JedisPool getPool()
    {
        return pool;
    }
}
