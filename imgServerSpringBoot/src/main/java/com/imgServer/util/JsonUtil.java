package com.imgServer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;



public class JsonUtil {
	//获取https协议的数据的方法，如下：

	
	
	
	
	public static String getURLContent(String urlStr) {
		/** 网络的url地址 */
		URL url = null;
		/** http连接 */
		HttpURLConnection httpConn = null;
		/**//** 输入流 */
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer();
		try {
			url = new URL(urlStr);
			in = new BufferedReader(new InputStreamReader(url.openStream(), "GBK"));
			String str = null;
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
		} catch (Exception ex) {
 
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
		}
		String result = sb.toString();
		return result;
	}

	
	
	 public static void main(String[] args) throws Exception {
//		 String url="http://49.4.11.246:45534/art/compare/v1.0?url0=http://e.hiphotos.baidu.com/image/h%3D300/sign=273b06cc3bd12f2ed105a8607fc3d5ff/94cad1c8a786c91723e93522c43d70cf3ac757c6.jpg&url1=http://c.hiphotos.baidu.com/image/h%3D300/sign=cfce96dfa251f3dedcb2bf64a4eff0ec/4610b912c8fcc3ce912597269f45d688d43f2039.jpg";
//		String str= getURLContent(url);
//		JSONObject j=JSONObject.parseObject(str);
//		System.out.println(j.get("data"));
		 
//		String s= "03:22:28 CT<br /> 30 Aug 2018";
//		System.out.println(s.substring(18, 20));
//		System.out.println(s.substring(21, 24));4
		
//		System.out.println(s.substring(25, 29));
		 
		 
		 System.out.println(PropertyUtils.getProperty("IMG_URL"));
	 }
	
}
