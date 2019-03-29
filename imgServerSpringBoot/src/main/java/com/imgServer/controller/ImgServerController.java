package com.imgServer.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.imgServer.service.ImgService;
import com.imgServer.util.DateUtil;
import com.imgServer.util.ErrorCode;
import com.imgServer.util.FileUtil;
import com.imgServer.util.GlobalStaticParas;
import com.imgServer.util.JsonUtil;
import com.imgServer.util.RequestUtil;
import com.imgServer.util.ResultData;


/***
 * 
*    图片服务器控制层
* 项目名称：imgServer   
* 类名称：imgServerController   
* 类描述：   
* 创建人：孙伟
* 创建时间：Aug 18, 2018 9:16:25 PM   
* 修改人：孙伟 
* 修改时间：Aug 18, 2018 9:16:25 PM   
* 修改备注：   
* @version    
*
 */
@Controller
public class ImgServerController {
	@Autowired
	private ImgService imgService;

	@RequestMapping("/hello1")
	@ResponseBody
    public String index(HttpServletRequest request) {
		System.out.println("111");
        return "Hello Worldsss";
    }
	
	
	 /***
	  * 
	 * 方法名: searchImgByImg
	 * 描述: TODO(描述这个方法的作用)卖家上传图片，前端使用form表单提交的方式
	 * 参数: @param file
	 * 参数: @param request
	 * 参数: @param response
	 * 参数: @return
	 * 参数: @throws IOException    
	 * 返回类型: ResultData    
	 * 创建人：孙伟 
	 * 创建时间：Aug 20, 2018 4:18:00 PM
	  */
	  @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	  @ResponseBody
		public ResultData  searchImgByImg(@RequestParam(value = "file") MultipartFile file , HttpServletRequest request,HttpServletResponse response) throws  IOException {
		  try {
			 
				Map params =  RequestUtil.getParams(request);
				  
				  String token = params.get("token")==null?"":params.get("token").toString();
				  
					//用户id
					int userId = imgService.findUser(token);
					if(userId<1){
						return  new ResultData(false,ErrorCode.ERROR_PARAMS_NOLOGIN,"登录失效或者用户不存在！");
					}else{
						
						
					    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   // 获得文件：        
						MultipartFile multipartFile = multipartRequest.getFile("file"); 
						InputStream in=multipartFile.getInputStream();
						
						String fileName=multipartFile.getOriginalFilename().toLowerCase();//获取文件名
						String fileNameNew=UUID.randomUUID().toString().replace("-", "")+".jpg";//新的图片名
						
						String directory=DateUtil.getCurrentDate("yyyyMMdd");//数据库保存路径
						//String dir=request.getSession().getServletContext().getRealPath("").replace("\\", "/")+"/img/resource/"+DateUtil.getCurrentDate("yyyyMM")+"/"+directory;
						//原图路径
						String dir=GlobalStaticParas.IMG_ORIGINAL_PATH+DateUtil.getCurrentDate("yyyyMM")+"/"+directory;
						//缩略图路径
						String thumbnailDir=GlobalStaticParas.IMG_THUMBNAIL_PATH+DateUtil.getCurrentDate("yyyyMM")+"/"+directory;
						
						  params.put("userID", userId);
					      params.put("imagename", fileNameNew);
					      params.put("imgdir", directory);
					      params.put("create_date", DateUtil.getCurrentDate());
						
						if(!regexImg(fileName)){
							return new ResultData(false,"0","图片格式不正确");
						}
					
						if(FileUtil.saveImg(in,dir,fileNameNew)){	//1.保存新图片
							//2.生成缩略图
							FileUtil.saveImgThumbnail(dir,thumbnailDir,fileNameNew);
							
							//3.删除相同序列号的旧图片
							Map map=imgService.findSourceByIndex(params);
							if(map!=null) {
								//String dirOld=request.getSession().getServletContext().getRealPath("").replace("\\", "/")+"/img/resource/"+map.get("imgdir").toString().substring(0,6)+"/"+map.get("imgdir")+"/"+map.get("imagename");
								String dirOld=GlobalStaticParas.IMG_ORIGINAL_PATH+map.get("imgdir").toString().substring(0,6)+"/"+map.get("imgdir")+"/"+map.get("imagename");
								FileUtil.deleteFile(dirOld);
								
								//4相同序列号的记录的删除标记改为删除	 
								imgService.update(map);
							}
							
							
							//4.保存新图片数据
							 imgService.insert(params);
							 
							  return new ResultData(true,"0","图片上传成功");

						}else{
							return new ResultData(false,"0","图片上传失败");
						}

					}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResultData(false,ErrorCode.ERROR_SERVICE_ERROR,"服务器异常");
			}
	
		}
	  
	  
	  
	  //判断图片格式正则表达式
	  public static boolean regexImg(String s){
	       String regex = ".+(jpg|bmp|png|jpeg|gif|tiff)$";
	         Pattern p = Pattern.compile(regex);
	         Matcher m = p.matcher(s);
	        if( m.find()){
	          s =  m.group().toString();
	          return true;
	        }
	        return false;

	     }
	  
	  
	
	  
	  
	  
	  /***
		  * 
		 * 方法名: uploadImgByBuyer
		 * 描述: TODO(描述这个方法的作用)买家上传图片，前端使用form表单提交的方式
		 * 买家收到货之后拍摄一张图片与溯源图片比对
		 * 参数: @param file
		 * 参数: @param request
		 * 参数: @param response
		 * 参数: @return
		 * 参数: @throws IOException    
		 * 返回类型: ResultData    
		 * 创建人：孙伟 
		 * 创建时间：Aug 20, 2018 4:18:00 PM
		  */
		  @RequestMapping(value = "/uploadImgByBuyer", method = RequestMethod.POST)
		  @ResponseBody
			public ResultData  uploadImgByBuyer(@RequestParam(value = "file") MultipartFile file , HttpServletRequest request,HttpServletResponse response) throws  IOException {
			  try {
				 
					Map params =  RequestUtil.getParams(request);
					  
					  String token = params.get("token")==null?"":params.get("token").toString();
					
						//用户id
						int userId = imgService.findUser(token);
						if(userId<1){
							return  new ResultData(false,ErrorCode.ERROR_PARAMS_NOLOGIN,"登录失效或者用户不存在！");
						}else{
							
							
						    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   // 获得文件：        
							MultipartFile multipartFile = multipartRequest.getFile("file"); 
							InputStream in=multipartFile.getInputStream();
							
							String fileName=multipartFile.getOriginalFilename().toLowerCase();//获取文件名
							String fileNameNew=UUID.randomUUID().toString().replace("-", "")+".jpg";//新的图片名
							
							String directory=DateUtil.getCurrentDate("yyyyMMdd");//数据库保存路径
							//String dir=request.getSession().getServletContext().getRealPath("").replace("\\", "/")+"/img/resource/"+DateUtil.getCurrentDate("yyyyMM")+"/"+directory;
							//图片保存地址
							//原图路径
							String dir=GlobalStaticParas.IMG_ORIGINAL_PATH+DateUtil.getCurrentDate("yyyyMM")+"/"+directory;
							if(!regexImg(fileName)){
								return new ResultData(false,"0","图片格式不正确");
							}
												
							if(FileUtil.saveImg(in,dir,fileNameNew)){	//1.保存新图片
							      params.put("userID", userId);
							      params.put("imagename", fileNameNew);
							      params.put("imgdir", directory);
							      params.put("create_date", DateUtil.getCurrentDate());
							      
							      //2.删除旧图片，
							      Map map=imgService.findImgByBuyer(params);
									if(map!=null) {
										//String dirOld=request.getSession().getServletContext().getRealPath("").replace("\\", "/")+"/img/resource/"+map.get("imgdir").toString().substring(0,6)+"/"+map.get("imgdir")+"/"+map.get("imagename");
										//图片地址
										String dirOld=GlobalStaticParas.IMG_ORIGINAL_PATH+map.get("imgdir").toString().substring(0,6)+"/"+map.get("imgdir")+"/"+map.get("imagename");
										FileUtil.deleteFile(dirOld);
										
										//3.将旧图片记录标记改为删除
										imgService.updateByBuyer(map);
									}
								
							      
								//4.保存新数据
								 imgService.insertByBuyer(params);
								  //5.与溯源图片比对，调王磊接口
								  String urlBuyer=GlobalStaticParas.IMG_URL+directory.substring(0,6)+"/"+directory+"/"+fileNameNew;
								  //获取卖家设置的溯源图片，放大120倍的
								/*  Map map1=imgService.findImgBySeller(params);
								  String imgdir=map1.get("imgdir").toString();
								  String imagename=map1.get("imagename").toString();
								  String sourceUrl=GlobalStaticParas.IMG_URL+imgdir.substring(0,6)+"/"+imgdir+"/"+imagename;*/
								  String sourceUrl=params.get("sourceUrl").toString();
								 
								  String url=GlobalStaticParas.IMG_COMPARE_URL+"?web_imgA="+urlBuyer+"&web_imgB="+sourceUrl;

								  JSONObject j=JSONObject.parseObject(JsonUtil.getURLContent(url));
								  String num= j.get("rtnData").toString();
//								  if(!StringUtils.isEmpty(num)&&num.length()>3) {
//									  num=num.substring(0, 4);  
//								  }
								 
							      return new ResultData(true,"0","比对结果",num);
								 
							}else{
								return new ResultData(false,"0","图片上传失败");
							}

						}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return new ResultData(false,ErrorCode.ERROR_SERVICE_ERROR,"服务器异常");
				}
		
			}
	  
	  
		  /***
		   * 上传头像
		   * @param file
		   * @param request
		   * @param response
		   * @return
		   * @throws IOException
		   */
		  @RequestMapping(value = "/uploadPortrait", method = RequestMethod.POST)
		  @ResponseBody
			public ResultData  memberHeadPicture(@RequestParam(value = "file") MultipartFile file , HttpServletRequest request,HttpServletResponse response) throws  IOException {
			  try {
				 
					Map params =  RequestUtil.getParams(request);
					  
					  String token = params.get("token")==null?"":params.get("token").toString();
					  
						//用户id
						int userId = imgService.findUser(token);
						if(userId<1){
							return  new ResultData(false,ErrorCode.ERROR_PARAMS_NOLOGIN,"登录失效或者用户不存在！");
						}else{
							
							
						    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   // 获得文件：        
							MultipartFile multipartFile = multipartRequest.getFile("file"); 
							
							if(multipartFile.getSize()>5242880) {
								return  new ResultData(false,ErrorCode.ERROR_PARAMS_WRONG,"图片不能大于5M");
							}
							
							
							InputStream in=multipartFile.getInputStream();
							
							String fileName=multipartFile.getOriginalFilename().toLowerCase();//获取文件名
							String fileNameNew=UUID.randomUUID().toString().replace("-", "")+".jpg";//新的图片名
							
							String directory=String.valueOf(userId);//保存路径
							
							//原图路径
							String dir=GlobalStaticParas.IMG_PORTRAIT_ORIGINAL_PATH+directory;
							//缩略图路径
							String thumbnailDir=GlobalStaticParas.IMG_PORTRAIT_THUMBNAIL_PATH+directory;
							
							  params.put("userID", userId);
						      params.put("imagename", fileNameNew);
						      params.put("imgdir", directory);
						      params.put("create_date", DateUtil.getCurrentDate());
							
							if(!regexImg(fileName)){
								return new ResultData(false,"0","图片格式不正确");
							}
						
							if(FileUtil.saveImg(in,dir,fileNameNew)){	//1.保存新图片
								//2.生成缩略图
								FileUtil.saveImgThumbnail(dir,thumbnailDir,fileNameNew);
								
								//3.删除旧图片
								Map map=imgService.findMemberPortrait(params);
								if(map!=null&&map.get("imagename")!=null) {
									//String dirOld=request.getSession().getServletContext().getRealPath("").replace("\\", "/")+"/img/resource/"+map.get("imgdir").toString().substring(0,6)+"/"+map.get("imgdir")+"/"+map.get("imagename");
									String dirOld=GlobalStaticParas.IMG_PORTRAIT_ORIGINAL_PATH+map.get("imgdir").toString()+"/"+map.get("imagename").toString();
									FileUtil.deleteFile(dirOld);	
								}
								
								
								//4.保存新图片数据
								 imgService.updatePortrait(params);
								 
								  return new ResultData(true,"0","图片上传成功");

							}else{
								return new ResultData(false,"0","图片上传失败");
							}

						}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return new ResultData(false,ErrorCode.ERROR_SERVICE_ERROR,"服务器异常");
				}
		
			}
		  
	  
}
