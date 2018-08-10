package yxitest.wzp;

import com.alibaba.fastjson.JSONObject;
import com.netease.mail.android.wzp.WzpObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by weiyi on 18/4/26.
 */
public class YXWzpProdcut extends AbstractWzpProduct {

    private static final String DEVICEINFO = "YANXUAN/%s (750/1334/2x; IOS10.3.1; iPhone/iPhone 7) device-id/93d4c44cd974efd995b877532";

    private static final String USERAGENT = "NeteaseYanxuan/%s (iPhone; iOS 10.3.10; Scale/3.00)";

    public YXWzpProdcut(){
        this.appid = 12;
        this.service = 137;
    }

    @Override
    public WzpObject builder(int service, String url, Map<String, String> params, Map<String, String> headers, String body) {
        if(headers==null){
            headers = new HashMap<>();
        }
        headers.put("User-Agent",String.format(USERAGENT,version));
        headers.put("device-info",String.format(DEVICEINFO,version));
        return super.builder(service, url, params, headers, body);
    }

    @Override
    protected String loginBody(String user, String pwd) {
        JSONObject loginBody = new JSONObject();
        loginBody.put("df","yanxuan_android");
        loginBody.put("product","yanxuan");
        loginBody.put("deviceType","8692-A00");
        //loginBody.put("savelogin","1");
        loginBody.put("passtype","0");
        //loginBody.put("type","1");
        loginBody.put("funcid","ursMobiInitAndLogin");
        loginBody.put("latitude","0.0*0.0");
        loginBody.put("pdtVersion","2.2.0");
        loginBody.put("resolution","1080*1920");
        loginBody.put("systemVersion","22");
        loginBody.put("mac","74:ac:5f:25:64:80");
        loginBody.put("systemName","android");
        loginBody.put("imei","867662020413463");
        loginBody.put("uniqueID","3684cb0a-7afb-4d58-a97f-13999f1e310b");
        loginBody.put("device-id", UUID.randomUUID().toString());
        loginBody.put("app-version","3.6.15");
        //loginBody.put("appTimestamp",System.currentTimeMillis());

        loginBody.put("password",DigestUtils.md5Hex(pwd.getBytes()));
        loginBody.put("username",user);

        return loginBody.toJSONString();
    }
}
