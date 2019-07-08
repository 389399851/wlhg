package com.yd.taozi.shiro;
import com.yd.taozi.pojo.User;
import com.yd.taozi.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xiaotaozi on 2019/6/29.
 */
public class MyRealm extends AuthorizingRealm{
    //注入业务接口
    @Autowired
    private UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
    //处理登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户名
         String principal= (String) authenticationToken.getPrincipal();
         User userByName = userService.findUserByName(principal);
        //SimpleAuthenticationInfo创建对象传的参数，
        //用户名是AuthenticationToken传过来的用户名
        SimpleAuthenticationInfo authenticationInfo=null;
        if (userByName!=null){
            authenticationInfo= new SimpleAuthenticationInfo(
                    principal,userByName.getUpw(),this.getName());
        }
        return authenticationInfo;
    }
}
