package yxitest.wzp;

import com.alibaba.fastjson.JSON;
import com.netease.mail.android.wzp.TransferMessage;
import com.netease.mail.android.wzp.WzpObject;
import com.netease.mail.wzp.entity.WZPCommEHCode;
import com.netease.mail.wzp.entity.WZPExtraHeader;
import com.netease.mail.wzp.entity.WZPUnit;
import org.apache.commons.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by weiyi on 18/4/26.
 */
public abstract class AbstractWzpProduct {

    private static final int LOGIN_SERVICE = 4;

    /**
     * Yanxuan test:12,release:11
     * Moneykeeper 8
     */
    protected int appid;

    /**
     * wzp-http service;
     */
    protected int service;

    /**
     * app version
     */
    protected String version;

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public int getAppid() {
        return appid;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * In case other product might use this.
     * @param user
     * @param pwd
     * @return
     */
    protected abstract String loginBody(String user,String pwd);

    /**
     * Build login object.
     * @param user
     * @param pwd
     * @return
     */
    public WzpObject buildLoginObject(String user,String pwd){
        String body = loginBody(user,pwd);
        return builder(LOGIN_SERVICE,null,null,null,body);
    }



    /**
     * Build wzp-http request.
     * @param service
     * @param url
     * @param params
     * @param headers
     * @param body
     * @return
     */
    public WzpObject builder(int service, String url, Map<String, String> params, Map<String, String> headers, String body) {

        TransferMessage message =  new TransferMessage() {
            @Override
            public void addExtraHeader(WZPUnit unit) {
                WZPExtraHeader trace = new WZPExtraHeader(WZPCommEHCode.TRACE_ID, "automation" + System.currentTimeMillis());
                unit.addExtraHeader(trace);
                if(url!=null) {
                    WZPExtraHeader actionHeader = new WZPExtraHeader(WZPHTTPEHTag.ACTION.getCode(), null);
                    actionHeader.addValue(url.getBytes());
                    unit.addExtraHeader(actionHeader);
                }

                if(params!=null) {
                    WZPExtraHeader paramsHeader = new WZPExtraHeader(WZPHTTPEHTag.PARAMS.getCode(), null);
                    paramsHeader.addValue(JSON.toJSONString(params).getBytes());
                    unit.addExtraHeader(paramsHeader);
                }

                if(headers!=null) {
                    WZPExtraHeader extraHeader = new WZPExtraHeader(WZPHTTPEHTag.ADDITIONAL_HEADER.getCode(), null);
                    extraHeader.addValue(JSON.toJSONString(headers).getBytes());
                    unit.addExtraHeader(extraHeader);
                }
            }

            @Override
            public int getContentLength() {
                if(body==null){
                    return 0;
                }
                return body.length();
            }

            @Override
            public InputStream getContentAsStream() {
                try {
                    if(body==null){
                        return null;
                    }
                    return IOUtils.toInputStream(body, "UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public byte[] getContenteAsBytes() {
                try {
                    return body.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return new byte[0];
                }
            }
        };

        WzpObject.WzpObjectBuilder<TransferMessage> builder = WzpObject.newBuilder(message)
                .setService(service).setApplication(appid).asSecurity();

        return builder.build();
    }

    /**
     * WZPHttp Proxy TAG
     */
    private enum WZPHTTPEHTag {
        ACTION(0x100), HTTP_HOST(0x101), PARAMS(0x102), ADDITIONAL_HEADER(0x103), HTTP_VERSION(0x107);
        private final int code;
        private WZPHTTPEHTag(int code) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }
    }
}
