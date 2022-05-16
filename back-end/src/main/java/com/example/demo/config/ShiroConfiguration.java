package com.example.demo.config;

import javax.servlet.Filter;
import com.example.demo.Realm.Realm;
//import com.example.demo.filter.URLPathMatchingFilter;
import com.example.demo.filter.ShiroUserFilter;
import com.example.demo.filter.URLPathMatchingFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfiguration {
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @CrossOrigin(value = "http://localhost:8080")
    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("http://localhost:8080/login");

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        Map<String, Filter> customizedFilter = new HashMap<>();  // 自定义过滤器设置 1
        Map<String, Filter> filterMap=new LinkedHashMap<>();
        filterMap.put("url",getShiroUserFilter());
        customizedFilter.put("url", getURLPathMatchingFilter()); // 自定义过滤器设置 2，命名，需在设置过滤路径前

        //filterChainDefinitionMap.put("/*","port[8080]");
        filterChainDefinitionMap.put("/home","port[8080]");
        filterChainDefinitionMap.put("/personal","port[8080]");
//        filterChainDefinitionMap.put("/logout","logout");
//        //filterChainDefinitionMap.put("/login","anon");
//        filterChainDefinitionMap.put("/authentication", "authc");
        filterChainDefinitionMap.put("/home", "anon");
        filterChainDefinitionMap.put("/personal", "authc");
        //filterChainDefinitionMap.put("/*", "authc");

        //filterChainDefinitionMap.put("/**", "authc");  // 自定义过滤器设置 3，设置过滤路径

        shiroFilterFactoryBean.setFilters(filterMap); // 自定义过滤器设置 4，启用
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;



//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        return shiroFilterFactoryBean;

    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(getRealm());
        return securityManager;
    }

    @Bean
    public Realm getRealm() {
        Realm realm = new Realm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    public Filter getShiroUserFilter() {
        return new ShiroUserFilter();
    }

    //这个不能使用Bean 会有歧义
    public Filter getURLPathMatchingFilter() {
        return new URLPathMatchingFilter();
    }
    //开启 cglib动态代理
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        //开启cglib ProxyTargetClass 代理的目标是一个java类 默认为false 代理目标是接口
        daap.setProxyTargetClass(true);
        return daap;
    }


//    //1.创建shiroFilter
//    @Bean
//    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultSecurityManager defaultSecurityManager){
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        //给filter设置安全管理器
//        shiroFilterFactoryBean.setSecurityManager(defaultSecurityManager);
//        //配置系统公共资源
//        Map<String,String> map = new HashMap<>();
//        //anon设置为公共资源，放行资源在下面
//        map.put("/user/login","anon");
//        map.put("/**","authc");//authc 是需要授权认证的
//
//        //默认认证界面路径
//        shiroFilterFactoryBean.setLoginUrl("/login.jsp");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
//        return shiroFilterFactoryBean;
//
//    }
//
//    //2.创建安全管理工具
//    @Bean
//    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm){
//        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
//        defaultWebSecurityManager.setRealm(realm);
//        return defaultWebSecurityManager;
//    }
//
//    @Bean
//    public Realm getRealm() {
//        Realm realm = new Realm();
//        //realm.setCredentialsMatcher(hashedCredentialsMatcher());
//        return realm;
//    }

}

