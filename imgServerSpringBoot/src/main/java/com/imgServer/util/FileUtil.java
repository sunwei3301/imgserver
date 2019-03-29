package com.imgServer.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.coobird.thumbnailator.Thumbnails;

public class FileUtil {
	
	
	/***
	 * 保存图片
	 * @param instream
	 * @param dir
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	 public static boolean saveImg( InputStream instream,String dir,String fileName) throws IOException{
		  FileOutputStream output =null;
		  try {
			 
			// 文件保存位置
				File saveDir = new File(dir);
				if (!saveDir.exists()) {
					saveDir.mkdirs();
				}
				
				 File file=new File(dir+"/"+fileName);
				 if(!file.exists()){
					 file.createNewFile();
				 }
				  output = new FileOutputStream(file); 
			  	
			  byte b[] = new byte[1024];  
			  int j = 0;  
			  while( (j = instream.read(b))!=-1){  
			      output.write(b,0,j);  
			  }  
			  output.flush();  
			  output.close();
			  
			  return true;
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			if (output != null) {
				output.close();
			}
			if (instream != null) {
				instream.close();
			}
			
		}  
	  }
	 
	 
	 
	 
	
	 /***
	  * 删除单个文件
	  * @param fileName
	  * @return
	  */
	    public static boolean deleteFile(String fileName) {
	        File file = new File(fileName);
	        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
	        if (file.exists() && file.isFile()) {
	            if (file.delete()) {
	               // System.out.println("删除单个文件" + fileName + "成功！");
	                return true;
	            } else {
	               // System.out.println("删除单个文件" + fileName + "失败！");
	                return false;
	            }
	        } else {
	           // System.out.println("删除单个文件失败：" + fileName + "不存在！");
	            return false;
	        }
	    }
	    
	    
	    
	    public static  void saveImgThumbnail(String dir,String thumbnailDir, String fileName){
		    try {
		    	// 文件保存位置
				File saveDir = new File(thumbnailDir);
				if (!saveDir.exists()) {
					saveDir.mkdirs();
				}
				 File file=new File(thumbnailDir+"/"+fileName);
				 if(!file.exists()){
					 file.createNewFile();
				 }
				Thumbnails.of(dir+"/"+fileName)   
				.scale(0.15f)  
				.toFile(thumbnailDir+"/"+fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	    }
	    
	    
	    public static void main(String[] args) throws Exception {
	    	//saveImgSmall("","");
	    }
}
