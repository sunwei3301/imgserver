package com.imgServer.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imgServer.dao.ImgServerDao;
import com.imgServer.service.ImgService;
import com.imgServer.util.DateUtil;
import com.imgServer.util.GlobalStaticParas;

@Service
public class ImgServiceImpl implements ImgService{
	@Autowired
	private ImgServerDao imgServerDao;
	
	public int insert(Map map) {
		// TODO Auto-generated method stub
		return imgServerDao.insert(map);
	}

	public int findUser(String token) {
		// TODO Auto-generated method stub
		 int user_id = 0;
		 String zhdlsj;//最后登录时间
		List <Map>list= imgServerDao.findUser(token);
		
	        if(list.size()>0){
	        	user_id = Integer.parseInt(list.get(0).get("user_id").toString());
	        	zhdlsj=list.get(0).get("zhdlsj").toString();//最后登陆时间
	        	
	        	int i=DateUtil.differentDaysByMillisecond(zhdlsj);//最后登陆时间与当前时间相比是否超30天
	        	if(i>GlobalStaticParas.ACCESSTOKEN_DAY){
	        		user_id=0;
	        	}
	        }
			return user_id;
	}
	
	
	public int insertByBuyer(Map map) {
		// TODO Auto-generated method stub
		return imgServerDao.insertByBuyer(map);
	}

	@Override
	public int update(Map map) {
		// TODO Auto-generated method stub
		return imgServerDao.updateSourceByIndex(map);
	}

	@Override
	public Map findSourceByIndex(Map map) {
		// TODO Auto-generated method stub
		return imgServerDao.findSourceByIndex(map);
	}

	@Override
	public Map findImgByBuyer(Map map) {
		// TODO Auto-generated method stub
		return imgServerDao.findImgByBuyer(map);
	}

	@Override
	public int updateByBuyer(Map map) {
		// TODO Auto-generated method stub
		return imgServerDao.updateImgByBuyer(map);
	}

	@Override
	public Map findImgBySeller(Map map) {
		// TODO Auto-generated method stub
		return imgServerDao.findImgBySeller(map);
	}

	@Override
	public int updatePortrait(Map map) {
		// TODO Auto-generated method stub
		return imgServerDao.updatePortrait(map);
	}

	@Override
	public Map findMemberPortrait(Map map) {
		// TODO Auto-generated method stub
		return imgServerDao.findMemberPortrait(map);
	}
   
}
