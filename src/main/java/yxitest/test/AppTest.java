package yxitest.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.Test;

import yxitest.wzp.Client;
import yxitest.wzp.ClientFactory;
 
 
  
 
/**
 * Unit test for simple App.
 */
public class AppTest 
    
{
	 private Client client;
	 
	 
	
	@Test
    public void AppTests()
    {
    	   try {
			client = ClientFactory.getClient("wzp_yx_test_3.6.6");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           client.login("hbyxtest88@163.com","abc123",1000);
           String res = client.get("/xhr/promotionCart/getMiniCartNum.json",null,null,1000);
            System.out.println(res);
    }

 
}
