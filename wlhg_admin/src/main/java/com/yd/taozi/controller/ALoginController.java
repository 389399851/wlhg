package com.yd.taozi.controller;
import com.yd.taozi.pojo.User;
import com.yd.taozi.service.AdminService;
import com.yd.taozi.utils.ImgCode;
import com.yd.taozi.vo.AdminVo;
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
public class ALoginController {
    @Autowired
    private AdminService adminService;
    @RequestMapping("/index")
    public String index(){return "index";}
    //验证码
    @RequestMapping(value = "/getImg",method = RequestMethod.GET)
    public void yanZhengMa(HttpServletRequest request, HttpServletResponse response){
        ImgCode imgCode = new ImgCode();
        imgCode.getRandcode(request,response);
    }
    //登录
    @RequestMapping(value = "/dengLu",method = RequestMethod.POST)
    public String login(AdminVo adminVo, HttpSession session, Model model){
        System.out.println("hgkugkg");
        String s = (String) session.getAttribute(ImgCode.RANDOMCODEKEY);
        if ("".equals(adminVo.getYanzhengma()) && "".equals(adminVo.getUsername()) && "".equals(adminVo.getPassword())){
            model.addAttribute("kongZhi","请输入登录信息");
            return "index";
        }else if (StringUtils.startsWithIgnoreCase(s,adminVo.getYanzhengma())){//验证码正确
            try {
                //从安全管理获取主体对象
                Subject subject = SecurityUtils.getSubject();
                //构建令牌
                UsernamePasswordToken token = new UsernamePasswordToken(adminVo.getUsername(), adminVo.getPassword());
                //登录
                subject.login(token);
                if (subject.isAuthenticated()){//登录成功后
                    User adminByName = adminService.findUserByName(adminVo.getUsername());
                    if ("admin".equals(adminVo.getLeiX()) && adminByName.getType().equals("admin")){
                        session.setAttribute("adMin",adminVo.getUsername());
                        return "manage";
                    }else {
                        model.addAttribute("wuQuan","你无权登录");
                        return "index";
                    }
                }
            }catch (AuthenticationException e){
                System.out.println("用户名或密码错误");
                model.addAttribute("cuoWu","用户名或密码错误");
                return "index";
            }
        }else {//验证码错误
            model.addAttribute("yanMaCuo","请输入正确的验证码");
            return "index";
        }
        return "index";
    }
}
