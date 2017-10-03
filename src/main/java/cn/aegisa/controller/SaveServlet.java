package cn.aegisa.controller;

import cn.aegisa.utils.RedisUtil;
import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("Duplicates")
@WebServlet(name = "SaveServlet", urlPatterns = "/save")
public class SaveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("key");
        String value = request.getParameter("value");
        Map<String, Object> result = new LinkedHashMap<>();
        if (key == null || value == null || "".equals(key) || "".equals(value)) {
            result.put("success", false);
            result.put("message", "empty key or value");
            String jsonString = JSON.toJSONString(result);
            response.getWriter().write(jsonString);
            return;
        }
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.set(key, value);
            jedis.expire(key, 300);
            result.put("success", true);
            String jsonString = JSON.toJSONString(result);
            response.getWriter().write(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "Exception");
            String jsonString = JSON.toJSONString(result);
            response.getWriter().write(jsonString);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
