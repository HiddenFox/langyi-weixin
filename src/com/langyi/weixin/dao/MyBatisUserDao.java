package com.langyi.weixin.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.langyi.weixin.model.User;

@Repository
public interface MyBatisUserDao extends UserDao{
	
	@Select("SELECT ID, SFZH, HYKH, JF, MM FROM employee WHERE MM = #{weixinAccount}")
	public User getByWeixinAccount(String weixinAccount);
	
}
