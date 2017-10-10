package cn.aegisa.controller;

import cn.aegisa.utils.RedisClusterUtil;
import cn.aegisa.utils.RedisUtil;
import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("Duplicates")
@WebServlet(name = "ReadServlet", urlPatterns = "/read")
public class ReadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("key");
        Map<String, Object> result = new LinkedHashMap<>();
        if (key == null || "".equals(key)) {
            result.put("success", false);
            result.put("message", "empty key");
            String jsonString = JSON.toJSONString(result);
            response.getWriter().write(jsonString);
            return;
        }
//        Jedis jedis = null;
        JedisCluster jedis = null;
        try {
            jedis = RedisClusterUtil.getJedis();
            String redisResult = jedis.get(key);
            if (redisResult == null) {
                redisResult = "null";
            }
            result.put("success", true);
            result.put("message", redisResult);
            String jsonString = JSON.toJSONString(result);
            response.getWriter().write(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", e.toString());
            String jsonString = JSON.toJSONString(result);
            response.getWriter().write(jsonString);
        } finally {
            if (jedis != null) {
//                jedis.close();
            }
        }
    }
}
