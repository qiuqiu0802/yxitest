package yxitest.wzp;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by weiyi on 18/4/26.
 */
public class ClientFactory {

    /**
     *
     * @param product
     *              null HttpClient
     *              http://you.yxp.163.com  HttpClient, product 为登录回调地�?�?
     *              wzp_* wzpClient
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Client getClient(String product) throws IOException, TimeoutException {

        if(product!=null && product.toLowerCase().startsWith("wzp")){
            return new WzpClientImpl(product);
        }else{
       //     return new HttpClientImpl(product);
        	return null;
        }
    }
}
