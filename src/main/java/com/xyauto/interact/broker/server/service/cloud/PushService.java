package com.xyauto.interact.broker.server.service.cloud;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import com.google.common.io.BaseEncoding;
import com.xyauto.interact.broker.server.util.HttpClient4Utils;
import com.xyauto.interact.broker.server.util.ILogger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.impl.client.CloseableHttpClient;

@Service
@ConfigurationProperties(prefix = "xyauto.interact.push")
public class PushService implements ILogger {

    private String secret;
    private String appKey;
    private String pushUrl;
    private boolean prod = true;
    private int time_to_live = 3600;
    
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(500);
    
    private static CloseableHttpClient httpClient = HttpClient4Utils.createHttpClient(500, 30, 2000, 10000, 10000);

    private JSONObject generateJson(List<String> aliases, String alert, boolean apns_production, int time_to_live, Map<String, String> extras) {
        JSONObject json = new JSONObject();
        JSONArray platform = new JSONArray();//平台
        Arrays.asList(new String[]{"android", "ios"}).forEach((String item) -> {
            platform.add(item);
        });

        JSONObject audience = new JSONObject();//推送目标
        JSONArray aliasArr = new JSONArray();
        aliases.forEach((String item) -> {
            aliasArr.add(item);
        });
        audience.put("alias", aliasArr);

        JSONObject notification = new JSONObject();//通知内容
        JSONObject android = new JSONObject();//android通知内容
        android.put("alert", alert);
        android.put("builder_id", 1);
        JSONObject android_extras = new JSONObject();//android额外参数
        android_extras.putAll(extras);
        android.put("extras", android_extras);

        JSONObject ios = new JSONObject();//ios通知内容
        ios.put("alert", alert);
        ios.put("sound", "default");
        ios.put("badge", "+1");
        JSONObject ios_extras = new JSONObject();//ios额外参数
        ios_extras.putAll(extras);
        ios.put("extras", ios_extras);
        notification.put("android", android);
        notification.put("ios", ios);

        JSONObject options = new JSONObject();//设置参数
        options.put("time_to_live", time_to_live);
        options.put("apns_production", apns_production);

        json.put("platform", platform);
        json.put("audience", audience);
        json.put("notification", notification);
        json.put("options", options);
        return json;
    }

    public void push(String alias, String alert, Map<String, String> extras) {
        List<String> aliases = new ArrayList<>();
        aliases.add(alias);
        this.push(aliases, alert, extras);
    }

    public void push(List<String> aliases, String alert, Map<String, String> extras) {
        fixedThreadPool.execute(() -> {
            this.info("推送内容:" + alert);
            String base64_auth_string = encryptBASE64(getAppKey() + ":" + getSecret());
            String authorization = "Basic " + base64_auth_string;
            this.sendPostRequest(getPushUrl(), generateJson(aliases, alert, isProd(), getTime_to_live(), extras).toString(), "UTF-8", authorization);
        });
    }

    private String sendPostRequest(String reqURL, String data, String encodeCharset, String authorization) {
        HttpPost httpPost = new HttpPost(reqURL);
        HttpResponse response;
        String result = "";
        try {
            StringEntity entity = new StringEntity(data, encodeCharset);
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            httpPost.setHeader("Authorization", authorization.trim());
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), encodeCharset);
            JSONObject retJson = new JSONObject();
            retJson.put("req", data);
            retJson.put("resp", result);
            this.info("极光推送:" + result);
        } catch (IOException | UnsupportedCharsetException | ParseException ex) {
            this.error("极光推送异常:"+ex.getMessage());
        }
        return result;
    }

    private String encryptBASE64(String str) {
        byte[] key = str.getBytes();
        String strs = BaseEncoding.base64().encode(key);
        return strs;
    }

    /**
     * @return the secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret the secret to set
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * @return the appKey
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * @param appKey the appKey to set
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * @return the pushUrl
     */
    public String getPushUrl() {
        return pushUrl;
    }

    /**
     * @param pushUrl the pushUrl to set
     */
    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    /**
     * @return the apns_production
     */
    public boolean isProd() {
        return prod;
    }

    /**
     * @param prod
     */
    public void setProd(boolean prod) {
        this.prod = prod;
    }

    /**
     * @return the time_to_live
     */
    public int getTime_to_live() {
        return time_to_live;
    }

    /**
     * @param time_to_live the time_to_live to set
     */
    public void setTime_to_live(int time_to_live) {
        this.time_to_live = time_to_live;
    }

}
