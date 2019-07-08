package com.yd.taozi.controller;

import com.yd.taozi.pojo.User;
import com.yd.taozi.service.UserService;
import com.yd.taozi.utils.ImgCode;
import com.yd.taozi.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by xiaotaozi on 2019/6/29.
 */
@Controller
public class ULoginController {
    @Autowired
    private UserService userService;
    @RequestMapping("/index")
    public String index(){return "index";}
    @RequestMapping("/reg")
    public String reg(){return "reg";}
    @RequestMapping("/login")
    public String login(){return "login";}
    @RequestMapping("/member")
    public String member(){return "member";}
    @RequestMapping("/main")
    public String main(){return "main";}
    //验证码
    @RequestMapping(value = "/getImg",method = RequestMethod.GET)
    public void yanZhengMa(HttpServletRequest request, HttpServletResponse response){
        ImgCode imgCode = new ImgCode();
        imgCode.getRandcode(request,response);
    }
    //登录
    @RequestMapping(value = "/dengLu",method = RequestMethod.POST)
    public String login(UserVo userVo, HttpSession session, Model model){
        String s = (String) session.getAttribute(ImgCode.RANDOMCODEKEY);
        if ("".equals(userVo.getYanzhengma()) && "".equals(userVo.getUsername()) && "".equals(userVo.getPassword())){
            model.addAttribute("kongZhi","请输入登录信息");
            return "login";
        }else if (StringUtils.startsWithIgnoreCase(s,userVo.getYanzhengma())){//验证码正确
            try {
                //从安全管理获取主体对象
                Subject subject = SecurityUtils.getSubject();
                //构建令牌
                UsernamePasswordToken token = new UsernamePasswordToken(userVo.getUsername(), userVo.getPassword());
                //登录
                subject.login(token);
                if (subject.isAuthenticated()){//登录成功后
                    User userByName = userService.findUserByName(userVo.getUsername());
                    if ("user".equals(userVo.getLeiX()) && userByName.getType().equals("user")){
                        session.setAttribute("yongHu",userVo.getUsername());
                        return "member";
                    }else if ("admin".equals(userVo.getLeiX()) && userByName.getType().equals("admin")){
                        session.setAttribute("adMin",userVo.getUsername());
                        return "main";
                    }else {
                        model.addAttribute("wuQuan","你无权登录");
                        return "login";
                    }
                }
            }catch (AuthenticationException e){
                System.out.println("用户名或密码错误");
                model.addAttribute("cuoWu","用户名或密码错误");
                return "login";
            }
        }else {//验证码错误
            model.addAttribute("yanMaCuo","请输入正确的验证码");
            return "login";
        }
        return "login";
    }
    //注册
    @RequestMapping(value = "/zhuCe",method = RequestMethod.POST)
    public String zhuCe(@Param("username")String username,
                        @Param("password")String password,
                        @Param("type")String type,
                        @Param("yanZhengMa")String yanZhengMa,
                        @Param("Cpassword")String Cpassword,
                        Model model, HttpSession session){
        String s = (String) session.getAttribute(ImgCode.RANDOMCODEKEY);
        User user = new User();
        user.setUname(username);
        user.setType(type);
        user.setUpw(password);
        System.out.println(username+""+type+""+yanZhengMa+""+password+""+Cpassword);
        User userByName = userService.findUserByName(username);
        if ("".equals(username)||"".equals(password)||"".equals(yanZhengMa)||"".equals(Cpassword)){
            model.addAttribute("zhuKong","请输入你的注册信息");
                return "reg";
        }else if (StringUtils.startsWithIgnoreCase(s,yanZhengMa)){
            if (password.equals(Cpassword)){
                if (userByName==null){
                    userService.insertUser(user);
                    return "login";
                }else {
                    model.addAttribute("cunZai","用户名重复请重新输入");
                    return "reg";
                }
            }else {
                model.addAttribute("miC","两次密码不一致,请重新输入");
                return "reg";
            }
        }else {
            model.addAttribute("yanMaCuo","请输入正确的验证码");
            return "reg";
        }
    }

}
