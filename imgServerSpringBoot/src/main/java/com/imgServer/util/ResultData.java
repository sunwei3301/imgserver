package com.imgServer.util;



public class ResultData {

	private boolean flag = true;
	private String code;
	private String msg;
	private Object data;
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public String getCode() {
		return code;
	}
	public void setErr(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public ResultData(){
		
	}
	
	public ResultData(boolean flag){
		super();
		this.flag = flag;
		if (flag) {
			this.msg = "操作成功！";
		}else {
			this.msg = "操作失败！";
		}
	}
	
	public ResultData(boolean flag,String code){
		super();
		this.flag = flag;
		this.code = code;
		if (flag) {
			this.msg = "操作成功！";
		}else {
			this.msg = "操作失败！";
		}
	}
	
	public ResultData(boolean flag,String code,String msg){
		super();
		this.flag = flag;
		this.code = code;
		this.msg = msg;
	}
	
	public ResultData(boolean flag,String code,String msg,Object data){
		super();
		this.flag = flag;
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public ResultData(Object data){
		this(true,"0","数据加载成功",data);
		
	}
}
