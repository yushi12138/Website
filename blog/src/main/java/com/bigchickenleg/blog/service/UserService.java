package com.bigchickenleg.blog.service;

import com.bigchickenleg.blog.po.User;

public interface UserService {

	User checkUser(String username, String password);
	
}
