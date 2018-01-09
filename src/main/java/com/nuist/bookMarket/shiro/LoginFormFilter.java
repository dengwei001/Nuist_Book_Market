package com.nuist.bookMarket.shiro;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Service
public class LoginFormFilter extends FormAuthenticationFilter {


    public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
    public static final String DEFAULT_MESSAGE_PARAM = "message";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;
    private String messageParam = DEFAULT_MESSAGE_PARAM;

    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        if (password==null){
            password = "";
        }
        String captcha = getCaptcha(request);
        return new UsernamePasswordToken(username, password.toCharArray(), captcha);
    }

    /**
     * 获取登录用户名
     */
    protected String getUsername(ServletRequest request, ServletResponse response) {
        String username = super.getUsername(request);
        if (StringUtils.isBlank(username)){
            username = request.getAttribute(getUsernameParam())==null ? StringUtils.EMPTY:request.getAttribute(getUsernameParam()).toString();
        }
        return username;
    }

    /**
     * 获取登录密码
     */
    @Override
    protected String getPassword(ServletRequest request) {
        String password = super.getPassword(request);
        if (StringUtils.isBlank(password)){
            password = request.getAttribute(getPasswordParam())==null?StringUtils.EMPTY:request.getAttribute(getPasswordParam()).toString();
        }
        return password;
    }


    public String getCaptchaParam() {
        return captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    public String getMessageParam() {
        return messageParam;
    }

    /**
     * 登录成功之后跳转URL
     */
    public String getSuccessUrl() {
        return super.getSuccessUrl();
    }

    @Override
    protected void issueSuccessRedirect(ServletRequest request,
                                        ServletResponse response) throws Exception {
//		Principal p = UserUtils.getPrincipal();
//		if (p != null && !p.isMobileLogin()){
        WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
//		}else{
//			super.issueSuccessRedirect(request, response);
//		}
    }

    /**
     * 登录失败调用事件
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,
                                     AuthenticationException e, ServletRequest request, ServletResponse response) {
        String className = e.getClass().getName(), message = "";
        if (IncorrectCredentialsException.class.getName().equals(className)
                || UnknownAccountException.class.getName().equals(className)){
            message = "用户或密码错误, 请重试.";
        }
        else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")){
            message = StringUtils.replace(e.getMessage(), "msg:", "");
        }
        else{
            message = "系统出现点问题，请稍后再试！";
            e.printStackTrace(); // 输出到控制台
        }
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageParam(), message);
        return true;
    }


}
