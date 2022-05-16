package com.example.demo.Realm;

import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class Realm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    // 简单重写获取授权信息方法
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
//        return s;
//    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取当前用户的所有权限
        System.out.println("授权");
        String username = principalCollection.getPrimaryPrincipal().toString();
        Set<String> permissions= new HashSet<>();
        //Set<String> permissions = adminPermissionService.listPermissionURLsByUser(username);
        permissions.add("user:show");
        permissions.add("user:admin");
        // 将权限放入授权信息中
        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
        //s.setStringPermissions(permissions);
        s.setStringPermissions(permissions);
        return s;
    }


    // 获取认证信息，即根据 token 中的用户名从数据库中获取密码、盐等并返回
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = token.getPrincipal().toString();
        User user = userService.getByName(userName);
        String passwordInDB = user.getPassword();
        String salt = user.getSalt();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, passwordInDB, ByteSource.Util.bytes(salt), getName());
        System.out.println("认证:"+userName+" sid:"+user.getSID());
        return authenticationInfo;
    }

//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
//        return s;
//
//    }

    //授权
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        return null;
////        String principal = (String) principals.getPrimaryPrincipal();
////        System.out.println("身份信息："+principal);
////        //根据身份信息，用户名，获取当前角色信息，权限信息
////        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
////        info.addRole("admin");
////        info.addRole("user");
////
////        //将数据库中查询的权限信息赋值权限
////        info.addStringPermission("user:*:01");
////
////        return info;
//    }
}
