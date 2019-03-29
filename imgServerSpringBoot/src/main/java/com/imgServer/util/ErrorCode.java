package com.imgServer.util;


public class ErrorCode {
	
	//数据为空
	public static  String ERROR_EMPTY_DATA = "1001";

	//服务器异常
	public static final String ERROR_SERVICE_ERROR = "1002";

	//缺少参数
	public static final String ERROR_PARAMS_LACK = "1003";
	
	//参数格式不正确
	public static final String ERROR_PARAMS_WRONG = "1004";
	
	//登录失效
	public static final String ERROR_PARAMS_NOLOGIN = "1005";
}
