package yxitest.wzp;

import com.netease.mail.android.wzp.WZP;
import com.netease.mail.android.wzp.WZPChannel;
import com.netease.mail.android.wzp.WzpObject;
import com.netease.mail.android.wzp.handler.TransferProtocol;
import com.netease.mail.android.wzp.key.PublicKeyStore;
import com.netease.mail.wzp.entity.WZPUnit;
import com.netease.mail.wzp.json.FastJsonImpl;
import com.netease.mail.wzp.locate.LocateServers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by weiyi on 18/4/26.
 */
public class WzpRunner implements Runner {

    private static final Logger LOG = LoggerFactory.getLogger(WzpRunner.class);

    private AbstractWzpProduct product;

    private Map<Integer,WZPChannel> channels;

    private final WZP wzp = WZP.INSTANCE();

    private static final int CONNECT_TIMEOUT = 2000;

    private static final int LOGIN_SERVICE = 4;

    /**
     * Store wzp keys.
     */
    private static final Map<Integer,String> store = new HashMap<>();

    public WzpRunner(String env) throws IOException, TimeoutException {
    	System.out.println(env);
        channels = new HashMap<>();
        String lowerEnv = env.toLowerCase();
        String[] profiles = lowerEnv.split("_");
        if(profiles.length>=3) {
            if (profiles[1].equals("yx")) {
                this.product = new YXWzpProdcut();
                if(profiles[2].equals("test")){
                    init(true);
                    LOG.debug("YX test env");
                    wzp.initLocate(LocateServers.DEV_YX);
                }else {
                    init(false);
                    LOG.debug("Online env");
                    wzp.initLocate(LocateServers.RELEASE);
                }
            }
            if(profiles.length>3) {
                this.product.setVersion(profiles[3]);
            }
        }
    }

    @Override
    public void setProduct(Object product) {
        this.product = (AbstractWzpProduct) product;
    }

    /**
     * Send wzp-http request
     * @param url
     * @param params
     * @param headers
     * @param body
     * @param timeout
     * @return
     */
    public String requestHttp(String url,Map<String, String> params,Map<String, String> headers,String body,int timeout){
        return this.request(product.builder(product.getService(),url,params,headers,body),timeout);
    }

    /**
     * send wzp login request
     * @param user
     * @param pwd
     * @param timeout
     * @return
     */
    public String requestLogin(String user,String pwd,int timeout){
        WZPChannel channel = null;
        try {
            channel = getWZPChannel(LOGIN_SERVICE);
            WZPUnit unit = channel.sendRequestSync(product.buildLoginObject(user,pwd),timeout, TimeUnit.MILLISECONDS);
            return getResponse(unit);
        } catch (Exception e) {
            LOG.warn("Get channel error.",e);
        }
        return null;
    }

    /**
     * Close wzp channel.
     */
    public void close(){
        for(WZPChannel channel:channels.values()) {
            channel.close();
        }
    }

    /**
     * Sync request.
     * @param object
     * @param timeout
     * @return
     */
    private String request(WzpObject object,int timeout){
        WZPUnit unit = null;
        try {
            WZPChannel channel = getWZPChannel(product.getService());
            unit = channel.sendRequestSync(object,timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            LOG.warn("Send wzp data error.",e);
        }
        return getResponse(unit);
    }

    /**
     * Init wzp keys.
     * @throws IOException
     * @throws TimeoutException
     */
    private void init(boolean test) throws IOException, TimeoutException {
        /*final List<String> list = Arrays.asList(
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0AVMvNiVmHdTxWiagcHKT5lMcjYSKg/dROqD0ib00L7CKTyAM1Y8IE/7anYFDnxuGLSlOCFfbtyR+WvUHH4S+wV5RVM0e7nGxP9O8QkGZInKkQvlQbc+uuGoeSuolRQhdyER1K9AHFSD/6jzkvdlNr/Za25yopgxfPwOqsXhzXZ17b32kK6RHnQhREuWiFRV6o1bmAUg4hBkm9tJZwJvfWSeirRX1yOm5lPRXtvk4LQfQ/o5VKTmG3uoDJKMoCGHuhep5vAfDXRDXjifezFrQEwKKZYIaXwFWFvishqVzn2yox3mww66WZ/Sgbasuu5zBU34UHiEEFkc6eqWN0zZPQIDAQAB",
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8IYXxAI1tcDx0kV9Vc1Q/Gl2ajLoKhl4GgbTMNFUeU+1KHRePvF5//awihjEuwzw/QYR6aEXV4106Cco+8e8ffadxVVI8BgG1rFo424mslPnKVuqcXIPNDmhv3BiGtWN/cTUq7V/J3uuf9Kd50YeMaddEKhqsJWwx6Ybi13oLnwIDAQAB"
        );*/
        List<String> list = new ArrayList<>();
        list.add(MAIN_KEY);
        if(test){
            list.add(TEST_KEY);
        }else{
            list.add(ONLINE_KEY);
        }

        for(int i=0;i<list.size();i++){
            store.put(i,list.get(i));
        }
        wzp.registerJsonImpl(new FastJsonImpl());
        wzp.initKeyStore(new PublicKeyStore() {
            @Override
            public Map<Integer, String> getAll() {
                return store;
            }

            @Override
            public void set(int index, String key) {
                store.put(index,key);
            }
        });
    }

    private WZPChannel getWZPChannel(int service) throws IOException, TimeoutException {
        WZPChannel channel = channels.get(service);
        if(channel==null || !channel.isValid()){
            channel = connect(service,null);
            channels.put(service,channel);
        }
        return channel;
    }

    private WZPChannel connect(int serviceId,String user) throws IOException, TimeoutException {
        return wzp.connect(product.getAppid(),serviceId,user,CONNECT_TIMEOUT, TransferProtocol.WZP);
    }

    private String getResponse(WZPUnit unit){
        Object obj = unit.getBody();
        String body;
        if(obj instanceof ByteBuffer){
            ByteBuffer buffer = (ByteBuffer)obj;
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            try {
                body = new String(bytes,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                LOG.warn("Unsupported UTF-8",e);
                body=null;
            }
        }else{
            body = obj.toString();
        }
        return body;
    }

    private static final String MAIN_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0AVMvNiVmHdTxWiagcHKT5lMcjYSKg/dROqD0ib00L7CKTyAM1Y8IE/7anYFDnxuGLSlOCFfbtyR+WvUHH4S+wV5RVM0e7nGxP9O8QkGZInKkQvlQbc+uuGoeSuolRQhdyER1K9AHFSD/6jzkvdlNr/Za25yopgxfPwOqsXhzXZ17b32kK6RHnQhREuWiFRV6o1bmAUg4hBkm9tJZwJvfWSeirRX1yOm5lPRXtvk4LQfQ/o5VKTmG3uoDJKMoCGHuhep5vAfDXRDXjifezFrQEwKKZYIaXwFWFvishqVzn2yox3mww66WZ/Sgbasuu5zBU34UHiEEFkc6eqWN0zZPQIDAQAB";

    private static final String TEST_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8IYXxAI1tcDx0kV9Vc1Q/Gl2ajLoKhl4GgbTMNFUeU+1KHRePvF5//awihjEuwzw/QYR6aEXV4106Cco+8e8ffadxVVI8BgG1rFo424mslPnKVuqcXIPNDmhv3BiGtWN/cTUq7V/J3uuf9Kd50YeMaddEKhqsJWwx6Ybi13oLnwIDAQAB";

    private static final String ONLINE_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD30Cx3fgd6wSMpmy71N6L2+70S1NQwi8JvpQ/ifhcy8M+MFnaU1Zw44FebXSCQGCJf9xIHDSVNi1tvULYPwZPW8NO/nIYz6JEYZDsyyTazphipJF5eZ01DtQWwFoZgkEf2M6TCOUY56Km3sXPQ1rVhvru/dMnUNl5PHDuQZbMtNQIDAQAB";

}
