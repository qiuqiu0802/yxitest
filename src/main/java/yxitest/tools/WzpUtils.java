package yxitest.tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.commons.httpclient.URIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import yxitest.controllers.ItestController;
import yxitest.define.Yxheaders;
import yxitest.wzp.Client;
import yxitest.wzp.ClientFactory;

public class WzpUtils {

	HttpUtils httpUtils = new HttpUtils();
	MyResponse myresponse = new MyResponse();
	Gson gjson = new Gson();
	private static final Logger LOGGER = LoggerFactory.getLogger(ItestController.class);

	Client client = null;
	String res = null;

	public Map<String, String> dealparams(String params) {
		String[] liParams;
		Map<String, String> paramsMap = null;

		liParams = params.split("&");
		int j = liParams.length;
		for (String ll : liParams) {
			paramsMap.put(ll.split("=")[0], ll.split("=")[1]);
		}
		return paramsMap;
	}

	public Map<String, String> dealheaders(Yxheaders headers) {
		String[] liParams;
		Map<String, String> headersMap = new HashMap<String, String>();

		if (headers.getConnection() != null && headers.getConnection() != "") {

			System.out.println(headers.getConnection());
			headersMap.put("Connection", headers.getConnection().toString());
		}
		if (headers.getContentType() != null && headers.getContentType() != "") {

			headersMap.put("Content-Type", headers.getContentType().toString());
		}

		return headersMap;
	}

	public String httpGet(String requestType, String urlType, String yxtestUrl) {

		if ((requestType.equals("http") || requestType.equals("HTTP"))
				&& (urlType.equals("get") || urlType.equals("GET"))) {

			try {
				res = httpUtils.execHttpGet(yxtestUrl);
				return res;
			} catch (URIException e) {
				// TODO Auto-generated catch block
				LOGGER.error("request failed, Please check Error");
				return "request failed, Please check !";
			}

		}
		return "F";
	}

	public String httpPost(String requestType, String urlType, String yxtesturl, String yxbodyparams) {
		if ((requestType.equals("http") || requestType.equals("HTTP"))
				&& (urlType.equals("post") || urlType.equals("POST"))) {

			myresponse = httpUtils.postUrlJson(yxtesturl, yxbodyparams);
			return gjson.toJson(myresponse);

		}

		return "F";
	}

	public String wzpGet(String requestType, String urlType, String appEvn, String yxtestUrl,
			Map<String, String> headers, Map<String, String> params, String user, String pwd) {
		if ((requestType.equals("wzp") || requestType.equals("WZP"))
				&& (urlType.equals("get") || urlType.equals("GET"))) {
			try {
				client = ClientFactory.getClient(appEvn);
				client.login(user, pwd, 1000);
				res = client.get(yxtestUrl, headers, params, 1000);
				System.out.println("res====:" + res);
				return res;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOGGER.error("IOException Error");
				return "F";

			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				LOGGER.error("TimeoutException Error");
				return "F";
			}
		}

		return "F";
	}

	public String wzpPost(String requestType, String urlType, String appEvn, String yxtestUrl, String yxbodyParams,
			Map<String, String> headers, String user, String pwd) {
		if ((requestType.equals("wzp") || requestType.equals("WZP"))
				&& (urlType.equals("post") || urlType.equals("POST"))) {

			try {
				client = ClientFactory.getClient(appEvn);
				client.login(user, pwd, 1000);

				/*
				 * System.out.println(gotestParam.getYxbodyparams());
				 * System.out.println(gjson.toJson(gotestParam.getYxbodyparams()));
				 */
				res = client.post(yxtestUrl, null, yxbodyParams, headers, 1000);
				System.out.println("res====:" + res);
				return res;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOGGER.error("IOException Error");
				return "wzp IO Failed!";

			} catch (TimeoutException e) {
				LOGGER.error("TimeoutException Error");
				// TODO Auto-generated catch block
				return "wzp Timeout Failed!";
			}

		}
		return "F";
	}

	
	public String returnRes(String res) {
		if (res == "F") {
			return "your requestType or urlType  is unkown!";
		} else {
			return res;
		}
	}
	
	
}
