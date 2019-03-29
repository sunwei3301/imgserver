package com.imgServer.service;

import java.util.Map;


public interface ImgService {
   public int insert(Map map);
   public int findUser(String token);
   public int insertByBuyer(Map map);
   public int update(Map map);
   public Map findSourceByIndex(Map map);
   public Map findImgByBuyer(Map map);
   public int updateByBuyer(Map map);
   
   public Map findImgBySeller(Map map);
   
   public int updatePortrait(Map map);
   
   public Map findMemberPortrait(Map map);
}
