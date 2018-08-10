package yxitest.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import yxitest.define.GotestParam;
import yxitest.tools.HttpUtils;
import yxitest.tools.MyResponse;
import yxitest.tools.WzpUtils;
import yxitest.wzp.Client;

@Controller
@RequestMapping("/yxitest")
public class ItestController {

	List<String> showInterfaceNamelist = new ArrayList<String>();
	MyResponse myresponse = new MyResponse();
	HttpUtils httpUtils = new HttpUtils();
	private static final Logger LOGGER = LoggerFactory.getLogger(ItestController.class);

	@ResponseBody
	@RequestMapping("/list")
	public String list(Model model) {
		System.out.println("=============");

		return "list";
	}

	@RequestMapping("/list1")
	public String list1() {
		System.out.println("==ss===========");

		throw new NullPointerException("demo");

	}

	@RequestMapping("/list2")
	public String list2() {
		System.out.println("==ss===========");
		try {
			Thread.currentThread().sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("=======bbb===========");
		throw new NullPointerException("demo");

	}

	@RequestMapping(value = "/interfaceNames", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("val", "aaaa");
		// model.addAttribute(new InterfaceName());
		// System.out.println("接收到数据。。。。。。"+new InterfaceName().getStrname());
  
		return "itestGetInterfaceName";
	}

	// , method = RequestMethod.POST

	@RequestMapping(value = "/fromGotest", method = RequestMethod.POST)
	@ResponseBody
	public Object showlist(HttpServletRequest request, @RequestBody GotestParam gotestParam) {

		Gson gjson = new Gson();
		String requestType = null;
		String url;
		String urlType;
		Client client = null;
		String res;
		String appEvn = "wzp_yx_test_3.6.6";
		String user = "sjyxtest1@163.com";
		String pwd = "abc123";

		LOGGER.info("=============开始======================");

        
 		requestType = gotestParam.getYxtestRequestType();
		url = gotestParam.getYxtestUrl();
		urlType = gotestParam.getYxtestUrlType();

		if (requestType == null) {
			return "requestType is null";
		}
		if (url == null) {
			return "url is null";
		}

		System.out.println(requestType);
		System.out.println(url);
		System.out.println(urlType);
		System.out.println(gotestParam.getYxbodyparams());
		System.out.println(gjson.toJson(gotestParam.getYxbodyparams()));
		System.out.println(gotestParam.yxparams);

		Map<String, String> params = new HashMap<String, String>();
		Map<String, String> headers = new HashMap<String, String>();
		if (gotestParam.getYxparams() != null && gotestParam.getYxparams() != "") {
			System.out.println(gotestParam.getYxparams());
			params = new WzpUtils().dealparams(gotestParam.getYxparams());
		}
		if (gotestParam.getYxheaders() != null) {
			headers = new WzpUtils().dealheaders(gotestParam.getYxheaders());
		}

		requestType = requestType.toLowerCase();
		urlType = urlType.toLowerCase();

		// WZP的GET请求
		res = new WzpUtils().wzpGet(requestType, urlType, appEvn, gotestParam.getYxtestUrl(), headers, params, user,
				pwd);
		if (res != "F") {
			return res;
		}
		
		// WZP的POST请求
		res = new WzpUtils().wzpPost(requestType, urlType, appEvn, gotestParam.getYxtestUrl(),
				gjson.toJson(gotestParam.getYxbodyparams()), headers, user, pwd);
		if (res != "F") {
			return res;
		}
		
		// http的POST请求
		res = new WzpUtils().httpPost(requestType, urlType, gotestParam.getYxtestUrl(),
				gjson.toJson(gotestParam.getYxbodyparams()));
		if (res != "F") {
			return res;
		}
		
		// http的get请求
		res = new WzpUtils().httpGet(requestType, urlType, gotestParam.getYxtestUrl().toString());
		if (res != "F") {
			return res;
		}

		return "your requestType or urlType  is unkown!";	 
	}

	/*
	 * model.addAttribute("val", "1211"); System.out.println(testd.getStrname());
	 * 
	 * ClassUtil classUtil=new ClassUtil(); List<String> lfunc =
	 * classUtil.getAllAssignedInterfaceFuncPath(testd.getStrname());
	 * System.out.println("显示指定的接口下面所有的方法：" + lfunc);
	 * 
	 * 
	 * showInterfaceNamelist.add("name"); return
	 * InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/itest/interfaceName";
	 */

	// 展示接口中的方法
	// @RequestMapping(value="/showInterfaceName")
	// public String showAll(Model model){
	// model.addAttribute(new InterfaceName());
	// model.addAttribute("showInterfacelist",showInterfaceNamelist);
	//
	// return "list";
	// }

}
