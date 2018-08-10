package yxitest.wzp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by weiyi on 18/4/25.
 */
public abstract class Client implements Constants{

    protected Runner runner;

    /**
     *
     * @param product
     */
    public void setProduct(Object product){
        runner.setProduct(product);
    }

    /**
     * urs login.
     * @param user
     * @param pwd
     * @param timeout
     * @return True if login success.
     */
    public abstract boolean login(String user, String pwd, int timeout);

    /**
     * Close client.
     */
    public abstract void close();

    /**
     * Get request
     * @param url
     * @param params
     * @param headers
     * @param timeout
     * @return
     */
    public abstract String get(String url, Map<String, String> params, Map<String, String> headers, int timeout);

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @param timeout
     * @return
     */
    public JSONObject getJson(String url, Map<String, String> params, Map<String, String> headers, int timeout){
        return JSON.parseObject(get(url, params, headers, timeout));
    }

    /**
     * Post request
     * @param url
     * @param params
     * @param body
     * @param headers
     * @param timeout
     * @return
     */
    public abstract String post(String url, Map<String, String> params, String body, Map<String, String> headers, int timeout);

    public JSONObject postJson(String url,Map<String,String> params,String body,Map<String, String> headers, int timeout){
        return JSON.parseObject(post(url, params, body, headers, timeout));
    }
}
