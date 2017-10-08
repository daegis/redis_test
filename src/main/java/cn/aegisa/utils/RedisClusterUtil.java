package cn.aegisa.utils;

import redis.clients.jedis.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RedisClusterUtil {

    private static JedisCluster jedisCluster;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new BufferedReader(new FileReader(new File(RedisClusterUtil.class.getClassLoader().getResource("redis_cluster.properties").getFile()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String hosts = properties.getProperty("host");
        String[] hostNodes = hosts.split(",");
        Set<HostAndPort> hostAndPortSet = new HashSet<>();
        for (String hostNode : hostNodes) {
            String[] split = hostNode.split(":");
            String host = split[0];
            Integer port = Integer.valueOf(split[1]);
            HostAndPort hostAndPort = new HostAndPort(host, port);
            hostAndPortSet.add(hostAndPort);
        }
        jedisCluster = new JedisCluster(hostAndPortSet);
    }

    public static JedisCluster getJedis() {
        return jedisCluster;
    }
}
