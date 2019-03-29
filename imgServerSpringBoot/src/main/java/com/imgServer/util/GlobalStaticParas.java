package com.imgServer.util;


public class GlobalStaticParas {
	
		
		//85paipai登录用户的token有效期
		public static final int  ACCESSTOKEN_DAY=30;
		//图片服务的外网地址
		public static final String   IMG_URL=PropertyUtils.getProperty("IMG_URL");
	
		//比对接口
		public static final String   IMG_COMPARE_URL=PropertyUtils.getProperty("IMG_COMPARE_URL");
		
		//原图保存路径
		public static final String   IMG_ORIGINAL_PATH=PropertyUtils.getProperty("imgOriginalPath");
		//缩略图保存路径
		public static final String   IMG_THUMBNAIL_PATH=PropertyUtils.getProperty("imgThumbnailPath");
		
		
		//头像原图保存路径
		public static final String   IMG_PORTRAIT_ORIGINAL_PATH=PropertyUtils.getProperty("img_portrait_original_Path");
		//头像缩略图保存路径
		public static final String   IMG_PORTRAIT_THUMBNAIL_PATH=PropertyUtils.getProperty("img_portrait_thumbnail_Path");
}
