/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.nuist.bookMarket.shiro;

/**
 * 用户和密码（包含验证码）令牌类
 * @author dengwei
 * @version 2018-1-3
 */
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String captcha;

	public UsernamePasswordToken() {
		super();
	}

	public UsernamePasswordToken(String username, char[] password, String captcha) {
		super(username, password);
		this.captcha = captcha;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public void setPassword(String password) {
	}
}