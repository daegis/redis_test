package cn.aegisa.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class RedisUtil {
    //配置对象
    static JedisPoolConfig config;
    // 连接池
    static JedisPool pool;

    static {
        // 创建一个连接池的配置对象
        config = new JedisPoolConfig();
        // 1可以设置初始化的连接数
        config.setMaxTotal(100);
        // 2设置空闲时期连接池的最大连接数
        config.setMaxIdle(5);
        Properties properties = new Properties();
        try {
            properties.load(new BufferedReader(new FileReader(new File(RedisUtil.class.getClassLoader().getResource("redis.properties").getFile()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String auth = properties.getProperty("auth");
        // 连接池
        pool = new JedisPool(config, host, Integer.parseInt(port), 20, auth);
    }

    // 获取jedis对象的方法
    public static Jedis getJedis() {
        return pool.getResource();
    }
}
