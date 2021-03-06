package com.bigchickenleg.blog.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="t_user")
public class User {

	@Id
	@GeneratedValue
	private Long id;
	private String nickname;
	private String username;
	private String password;
	private String email;
	private String avatar;
	private Integer priviledge;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTime;
	
	@OneToMany(mappedBy="user")
	private List<Blog> blogs = new ArrayList<>();
	
	@OneToMany(mappedBy="user")
	private List<Comment> comments = new ArrayList<>();

	
	public User() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getPriviledge() {
		return priviledge;
	}

	public void setPriviledge(Integer priviledge) {
		this.priviledge = priviledge;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nickname=" + nickname + ", username=" + username + ", password=" + password
				+ ", email=" + email + ", avatar=" + avatar + ", priviledge=" + priviledge + ", createTime="
				+ createTime + ", lastLoginTime=" + lastLoginTime + ", blogs=" + blogs + ", comments=" + comments + "]";
	}
	

	
	
	
}
