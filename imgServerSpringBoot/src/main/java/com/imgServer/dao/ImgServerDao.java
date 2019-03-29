/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yee/yide">yide</a> All rights reserved.
 */
package com.imgServer.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;



/**
 * 图片数据操作
 * @author sunwei
 * @version 2018-08-14
 */
@Mapper
public interface ImgServerDao  {
	public int insert(Map map);
	public List<Map> findUser(String token);
	public int insertByBuyer(Map map);
	public int updateSourceByIndex(Map map);
	 public Map findSourceByIndex(Map map);
	 public Map findImgByBuyer(Map map);
	 public int updateImgByBuyer(Map map);
	 
	 public Map findImgBySeller(Map map);
	 public int updatePortrait(Map map);
	 
	 public Map findMemberPortrait(Map map);
}