package com.nuist.bookMarket.shiro;

import com.nuist.bookMarket.model.User;
import com.nuist.bookMarket.service.UserService;
import com.nuist.bookMarket.util.MD5Utils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException{
        //获取用户输入的username
        String username = (String) token.getPrincipal();
//        try {
//            String password = MD5Utils.EncoderByMd5(token.getCredentials().toString());
//            ((UsernamePasswordToken) token).setPassword(password);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        //根据username查询数据库的User
        User user = userService.getUserByUserName(username);
        if (user == null ){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
           user, //用户名
           user.getPassword(), //密码
             getName()  //realm name
      );

        return authenticationInfo;
    }
}
