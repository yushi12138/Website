package com.bigchickenleg.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bigchickenleg.blog.po.User;
//only need to define the interface and self-defined method, 
//all the implementations are finished automatically by jpa
public interface UserRepository extends JpaRepository<User, Long>{
	
	//the method name should follow jps name pattern : find(..verb defined in jpa) + camel pattern  
	
	User findByUsernameAndPassword(String username,String password);
}
