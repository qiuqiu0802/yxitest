package yxitest.wzp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by weiyi on 18/4/25.
 */
public class WzpClientImpl extends Client{

 //   private static final Logger LOG = LoggerFactory.getLogger(WzpClientImpl.class);

    private String cookie;

    public WzpClientImpl(String env) throws IOException, TimeoutException {
        runner = new WzpRunner(env);
    }

    @Override
    public boolean login(String user, String pwd,int timeout) {
        String cookie = null;
        try {
            cookie = getCookie(user,pwd,DEFAULT_COOKIE,timeout);
        } catch (Exception e) {
          System.out.println("Cannot get cookie of {}");
        }
        if(cookie==null){
            return false;
        }else{
            this.cookie = cookie;
            return true;
        }
    }

    @Override
    public void close() {
        ((WzpRunner)runner).close();
    }

    @Override
    public String get(String url, Map<String, String> params, Map<String, String> headers,int timeout) {
        return httpRequest(url,params,headers,null,timeout);
    }

    @Override
    public String post(String url, Map<String, String> params, String body, Map<String, String> headers,int timeout) {
        return httpRequest(url,params,headers,body,timeout);
    }

    /**
     * Add cookies if have any value.
     * @param url
     * @param params
     * @param headers
     * @param body
     * @param timeout
     * @return
     */
    private String httpRequest(String url, Map<String, String> params, Map<String, String> headers, String body,int timeout) {
        if(cookie!=null){
            if(headers==null){
                headers = new HashMap<>();
            }
            headers.put("Cookie",DEFAULT_COOKIE+"="+cookie);
        }
        return ((WzpRunner)runner).requestHttp(url,params,headers,body,timeout);
    }

    /**
     * Parse cookies after login.
     * @param user
     * @param pwd
     * @param cookie
     * @param timeout
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    private String getCookie(String user,String pwd,String cookie,int timeout) throws IOException, TimeoutException {
        String body = ((WzpRunner)runner).requestLogin(user,pwd,timeout);
     //   LOG.debug("[WzpLogin] login response {}",body);
        System.out.println("Cannot get cookie of {}"+body);
        JSONObject response = JSON.parseObject(body);
        if(response!=null) {
            JSONArray cookies = response.getJSONArray("cookie");
            if(cookies!=null) {
                for (int i = 0; i < cookies.size(); i++) {
                    String cookie_all = cookies.getString(i);
                    if (cookie_all.indexOf(cookie) >= 0) {
                        return cookie_all.substring(cookie.length()+1);
                    }
                }
            }
        }
        System.out.println("Cannot get cookie of {}");
      //  LOG.warn("[WzpLogin] {} login failed",user);
        return null;
    }
}
