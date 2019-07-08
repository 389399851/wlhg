package com.yd.taozi.config;
import com.yd.taozi.shiro.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Created by xiaotaozi on 2019/6/29.
 */
@Configuration
public class ShiroConfig {
    //自定义一个realm
    @Bean(name="myRealm")
    public MyRealm getRealm(HashedCredentialsMatcher hashedCredentialsMatcher){
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return new MyRealm();
    }
    //创建安全管理器
    @Bean(name = "dwm")
    public DefaultWebSecurityManager defaultWebSecurityManager(
            @Qualifier("myRealm")MyRealm myRealm
    ){
        //创建安全管理器对象
         DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置自定义的Realm(领域)
        securityManager.setRealm(myRealm);
        return securityManager;
    }

    //创建shiro权限过滤器
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(
            @Qualifier("dwm")DefaultWebSecurityManager defaultWebSecurityManager
    ){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //登录页
        filterFactoryBean.setLoginUrl("/index");
        //无权访问时显示页面
        filterFactoryBean.setUnauthorizedUrl("unauth");
        return filterFactoryBean;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        return defaultAdvisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("dwm")DefaultWebSecurityManager defaultWebSecurityManager
    ){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }
    //自定义加盐
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //设置算法
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //设置加密次数
        hashedCredentialsMatcher.setHashIterations(1024);
        //设置十六进制字符串
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }
}
