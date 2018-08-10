package yxitest.tools;

/**
 * @author hzpanweifeng
 *
 */
public class MyResponse {

	int  status;
	String body;
	String cookie;
	
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	
}
