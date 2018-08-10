package yxitest.tools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;


public class HttpUtils {

	// private final static Log log = LogFactory.getLog(HttpUtils.class);

	public MyResponse postUrlApplicationJson(String url,
			Map<String, String> pars) {
	//	DefaultHttpClient httpClient = new DefaultHttpClient();
		CloseableHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = null;
		String body = null;
		int status = 0;
		String cookie = null;
		MyResponse myResponse = new MyResponse();

		httpPost = new HttpPost(url);

		JSONObject jSONObject = (JSONObject) JSONObject.toJSON(pars);

		StringEntity entity = new StringEntity(jSONObject.toString(), "utf-8");// 解决中文乱码问题

		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");

		// par.add(new BasicNameValuePair("logistics_interface", "<Request"));

		try {

			System.out.println("请求参数：" + jSONObject);
			httpPost.setEntity(entity);
			// httpPost.setEntity(new UrlEncodedFormEntity(parList, "UTF-8"));
			System.out.println("请求链接：" + httpPost);
			HttpResponse response = httpClient.execute(httpPost);

			System.out.println("调用返回Head：" + response);
			Header[] headers = response.getHeaders("Set-Cookie");
			StringBuilder sb = new StringBuilder();

			for (Header header : headers) {
				System.out.println("key:" + header.getName() + "  " + "value:"
						+ header.getValue());
				String value = header.getValue().split(";")[0];
		 
				if (value.split("=").length == 1) {
					continue;
				}
				sb.append(value).append("; ");
			}

			System.out.println(sb.toString());
			cookie = sb.toString();
			status = response.getStatusLine().getStatusCode();
			System.out.println("调用返回Status：" + status);

			// HttpEntity entity = response.getEntity();
			System.out.println("调用返回Head：" + entity);
			// body = EntityUtils.toString(entity, "utf-8");
			body = EntityUtils.toString(response.getEntity(), "utf-8");
			System.out.println("调用返回Body：" + body);

			System.out
					.println("--------------------------我是用例结束分割线-------------------------------------");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		myResponse.setBody(body);
		myResponse.setStatus(status);
		myResponse.setCookie(cookie);

		return myResponse;

	}
	public MyResponse postUrlApplicationJsonStr(String url,
			List<Object> parJson) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = null;
		String body = null;
		int status = 0;
		String cookie = null;
		MyResponse myResponse = new MyResponse();

		httpPost = new HttpPost(url);

		
		StringEntity entity = new StringEntity(parJson.toString(), "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");

		try {

			System.out.println("请求参数：" + parJson);
			httpPost.setEntity(entity);
			System.out.println("请求链接：" + httpPost);
			HttpResponse response = httpClient.execute(httpPost);

			System.out.println("调用返回Head：" + response);
			Header[] headers = response.getHeaders("Set-Cookie");
			StringBuilder sb = new StringBuilder();

			for (Header header : headers) {
				System.out.println("key:" + header.getName() + "  " + "value:"
						+ header.getValue());
				String value = header.getValue().split(";")[0];
				/*
				 * if (value.split("=")[1]==null ||value.split("=")[1].length()
				 * == 0) { continue; }
				 */
				if (value.split("=").length == 1) {
					continue;
				}
				sb.append(value).append("; ");
			}

			System.out.println(sb.toString());
			cookie = sb.toString();
			status = response.getStatusLine().getStatusCode();
			System.out.println("调用返回Status：" + status);

			// HttpEntity entity = response.getEntity();
			System.out.println("调用返回Head：" + entity);
			// body = EntityUtils.toString(entity, "utf-8");
			body = EntityUtils.toString(response.getEntity(), "utf-8");
			System.out.println("调用返回Body：" + body);

			System.out
					.println("--------------------------我是用例结束分割线-------------------------------------");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		myResponse.setBody(body);
		myResponse.setStatus(status);
		myResponse.setCookie(cookie);

		return myResponse;

	}
	
	public MyResponse postUrlJson(String url,
			String parJson) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = null;
		String body = null;
		int status = 0;
		String cookie = null;
		MyResponse myResponse = new MyResponse();

		httpPost = new HttpPost(url);
		
		StringEntity entity = new StringEntity(parJson, "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");

		try {
			
			System.out.println("请求参数：" + parJson);
			httpPost.setEntity(entity);
			System.out.println("请求链接：" + httpPost);
			HttpResponse response = httpClient.execute(httpPost);

			System.out.println("调用返回Head：" + response);
			Header[] headers = response.getHeaders("Set-Cookie");
			StringBuilder sb = new StringBuilder();

			for (Header header : headers) {
				System.out.println("key:" + header.getName() + "  " + "value:"
						+ header.getValue());
				String value = header.getValue().split(";")[0];
				/*
				 * if (value.split("=")[1]==null ||value.split("=")[1].length()
				 * == 0) { continue; }
				 */
				if (value.split("=").length == 1) {
					continue;
				}
				sb.append(value).append("; ");
			}

			System.out.println(sb.toString());
			cookie = sb.toString();
			status = response.getStatusLine().getStatusCode();
			System.out.println("调用返回Status：" + status);

			// HttpEntity entity = response.getEntity();
			System.out.println("调用返回Head：" + entity);
			// body = EntityUtils.toString(entity, "utf-8");
			body = EntityUtils.toString(response.getEntity(), "utf-8");
			System.out.println("调用返回Body：" + body);

			System.out
					.println("--------------------------我是用例结束分割线-------------------------------------");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		myResponse.setBody(body);
		myResponse.setStatus(status);
		myResponse.setCookie(cookie);

		return myResponse;

	}
	
	

	public MyResponse postUrlEncoded(String url, Map<String, String> pars) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = null;
		String body = null;
		List<BasicNameValuePair> parList = new ArrayList<>();
		int status = 0;
		MyResponse myResponse = new MyResponse();

		httpPost = new HttpPost(url);

		for (Entry<String, String> p1 : pars.entrySet()) {
			parList.add(new BasicNameValuePair(p1.getKey(), p1.getValue()));
		}

		try {

			System.out.println("请求参数：" + parList);
			httpPost.setEntity(new UrlEncodedFormEntity(parList, "UTF-8")); // 将参数放在setEntity中，以list形式放在body中
			System.out.println("请求链接：" + httpPost);
			HttpResponse response = httpClient.execute(httpPost);

			System.out.println("调用返回Head：" + response);

			status = response.getStatusLine().getStatusCode();
			System.out.println("调用返回Status：" + status);

			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity, "utf-8");

			System.out.println("调用返回Body：" + body);

			System.out
					.println("--------------------------我是用例结束分割线-------------------------------------");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		myResponse.setBody(body);
		myResponse.setStatus(status);

		return myResponse;

	}
	

	
	public MyResponse postUrlEncodedCookie(String url, Map<String, String> pars,String cookies) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = null;
		String body = null;
		List<BasicNameValuePair> parList = new ArrayList<>();
		int status = 0;
		MyResponse myResponse = new MyResponse();

		httpPost = new HttpPost(url);

		for (Entry<String, String> p1 : pars.entrySet()) {
			parList.add(new BasicNameValuePair(p1.getKey(), p1.getValue()));
		}

		httpPost.setHeader("cookie", cookies);

		try {

			System.out.println("请求参数：" + parList);
			httpPost.setEntity(new UrlEncodedFormEntity(parList, "UTF-8")); // 将参数放在setEntity中，以list形式放在body中
			
			System.out.println("请求链接：" + httpPost);
			HttpResponse response = httpClient.execute(httpPost);

			System.out.println("调用返回Head：" + response);

			status = response.getStatusLine().getStatusCode();
			System.out.println("调用返回Status：" + status);

			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity, "utf-8");

			System.out.println("调用返回Body：" + body);

			System.out
					.println("--------------------------我是用例结束分割线-------------------------------------");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		myResponse.setBody(body);
		myResponse.setStatus(status);

		return myResponse;

	}
	

	public String execHttpGet(String url) throws URIException {
		GetMethod getMethod = null;
		String resp = null;
		System.out.println("请求连接：" + url);
		try {
			HttpClient httpClient = new HttpClient();
			getMethod = new GetMethod(url);
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(120000);
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(120000);

			int statusCode = httpClient.executeMethod(getMethod);
			/* 判断返回码 */
			if (statusCode != HttpStatus.SC_OK) {
				// log.error("url[" + url + "]请求异常,statusCode[" + statusCode
				// + "].");
				throw new URIException("Send Http Get Request Error : url["
						+ url + "]请求异常,statusCode[" + statusCode + "].");
			}
			resp = getMethod.getResponseBodyAsString();
		} catch (UnsupportedEncodingException e) {
			// log.error("Send Http Get Request Error : :不支持的字符编码或字符集", e);
			throw new URIException("Send Http Get Request Error : 不支持的字符编码或字符集");
		} catch (ConnectTimeoutException e) {
			// log.error("Send Http Get Request Error : :ConnectionTimeOut 异常，请求地址{"
			// + url
			// + "}不存在，请核查请求地址或请检查ESB服务是否启动。", e);
			throw new URIException(
					"Send Http Get Request Error : ConnectionTimeOut 异常，请求地址{"
							+ url + "}不存在，请核查请求地址或请检查ESB服务是否启动。");
		} catch (SocketTimeoutException e) {
			// log.error("Send Http Get Request Error : :SocketTimeOut/异常 请求服务{"
			// + url
			// + "}超时异常，返回超时异常超时12000MS。", e);
			throw new URIException(
					"Send Http Get Request Error : SocketTimeOut/异常 请求服务{"
							+ url + "}超时异常，返回超时异常超时12000MS。");
		} catch (HttpException e) {
			// log.error("Send Http Get Request Error : :(HTTP/异常  请求ERP地址{" +
			// url
			// + "}异常，请检查ESB服务是否启动。", e);
			throw new URIException(
					"Send Http Get Request Error : (HTTP/异常，请求ERP地址{" + url
							+ "}异常，请检查ESB服务是否启动。");
		} catch (IOException e) {
			// log.error("Send Http Get Request Error : :I/O异常  读取{" + url +
			// "}返回信息失败。", e);
			// throw new URIException("Send Http Get Request Error : I/O异常，读取{"
			// + url + "}返回信息失败。");
		} catch (Exception e) {
			// log.error("Send Http Get Request Error : :系统异常请联系网关管理员", e);
			throw new URIException(
					"Send Http Get Request Error : 系统异常请联系网关管理员。");
		} finally {
			// 释放连接。
			if (null != getMethod) {
				getMethod.releaseConnection();
			}
		}
		return resp;
	}

	/**
	 * ��װPOST����������Χϵͳ
	 * 
	 * @Method: execHttpPost
	 * @Description: TODO(��װPOST����������Χϵͳ)
	 * @param @param url
	 * @param @param postParam.toString()
	 * @param @return
	 * @param @throws BaseException
	 * @return String
	 * @throws
	 */
	public String execHttpPost(String url, Map<String, String> params)
			throws URIException {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=gbk");
		String resp = null;// 返回
		StringBuffer sb = new StringBuffer();
		Iterator<Entry<String, String>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			postMethod.setParameter(entry.getKey(), entry.getValue());
			sb.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		try {
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(120000);
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(120000);
			httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			int statusCode = httpClient.executeMethod(postMethod);
			/* 判断返回码 */
			if (statusCode != HttpStatus.SC_OK) {
				// log.error("url[" + url + "]请求异常,statusCode[" + statusCode +
				// "],参数[" + sb.toString() + "].");
				throw new URIException("Send Http Get Request Error : url["
						+ url + "]请求异常,statusCode[" + statusCode + "],参数["
						+ sb.toString() + "].");
			}
			resp = postMethod.getResponseBodyAsString();
		} catch (UnsupportedEncodingException e) {
			// log.error("Send Http Get Request Error : :不支持的字符编码或字符集", e);
			throw new URIException("Send Http Get Request Error : 不支持的字符编码或字符集");
		} catch (ConnectTimeoutException e) {
			// log.error("Send Http Get Request Error : :ConnectionTimeOut 异常，请求地址{"
			// + url + "}不存在，请核查请求地址或请检查ESB服务是否启动。", e.fillInStackTrace());
			throw new URIException(
					"Send Http Get Request Error : ConnectionTimeOut 异常，请求地址{"
							+ url + "}不存在，请核查请求地址或请检查ESB服务是否启动。");
		} catch (SocketTimeoutException e) {
			// log.error("Send Http Get Request Error : :SocketTimeOut/异常 请求服务{"
			// + url + "}超时异常，返回超时异常超时3000MS。", e.fillInStackTrace());
			throw new URIException(
					"Send Http Get Request Error : SocketTimeOut/异常 请求服务{"
							+ url + "}超时异常，返回超时异常超时12000MS。");
		} catch (HttpException e) {
			// log.error("Send Http Get Request Error : :(HTTP/异常  请求ERP地址{" +
			// url + "}异常，请检查ESB服务是否启动。", e.fillInStackTrace());
			throw new URIException(
					"Send Http Get Request Error : (HTTP/异常，请求ERP地址{" + url
							+ "}异常，请检查ESB服务是否启动。");
		} catch (IOException e) {
			// log.error("Send Http Get Request Error : :I/O异常  读取{" + url +
			// "}返回信息失败。", e.fillInStackTrace());
			throw new URIException("Send Http Get Request Error : I/O异常，读取{"
					+ url + "}返回信息失败。");
		} catch (Exception e) {
			// log.error("Send Http Get Request Error : :系统异常请联系网关管理员",
			// e.fillInStackTrace());
			throw new URIException(
					"Send Http Get Request Error : 系统异常请联系网关管理员。");
		} finally {
			// 释放连接
			if (null != postMethod) {
				postMethod.releaseConnection();
			}

		}
		return resp;
	}



}
