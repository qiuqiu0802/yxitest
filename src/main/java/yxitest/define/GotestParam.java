package yxitest.define;

public class GotestParam {

	public String yxtestRequestType;   //请求类型，http、wzp
	public String yxtestUrl;           //请求链接
	public String yxtestUrlType;       //链接类型，get、post
	public String yxparams;            //暂没使用
	public Yxheaders yxheaders;       // wzp header中加  application/json
	public Object yxbodyparams;       //body参数
	
	/**
	 * {
	"yxtestRequestType": "http",
	"yxtestUrl": "baidu.com",
	"obj": "obj"
}
	 * @return
	 */
	public String getYxtestRequestType() {
		return yxtestRequestType;
	}
	public void setYxtestRequestType(String yxtestRequestType) {
		this.yxtestRequestType = yxtestRequestType;
	}
	public void setYxheaders(Yxheaders yxheaders) {
		this.yxheaders = yxheaders;
	}
	public String getYxtestUrl() {
		return yxtestUrl;
	}
	public void setYxtestUrl(String yxtestUrl) {
		this.yxtestUrl = yxtestUrl;
	}
	 
	public String getYxtestUrlType() {
		return yxtestUrlType;
	}
	public Object getYxbodyparams() {
		return yxbodyparams;
	}
	public void setYxbodyparams(Object yxbodyparams) {
		this.yxbodyparams = yxbodyparams;
	}
	public void setYxtestUrlType(String yxtestUrlType) {
		this.yxtestUrlType = yxtestUrlType;
	}
		 
	public void setYxparams(String yxparams) {
		this.yxparams = yxparams;
	}
	public Yxheaders getYxheaders() {
		return yxheaders;
	}
	public String getYxparams() {
		return yxparams;
	}
 
	 
	
	
	
	
	
}
