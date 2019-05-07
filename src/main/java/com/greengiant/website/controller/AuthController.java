package com.greengiant.website.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.greengiant.website.utils.CaptchaUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
public class AuthController {
	private static Log logger = LogFactory.getLog(AuthController.class);
	
	@RequestMapping("/login")
	public String login()
	{
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() && subject.isRemembered())
		{
			return "index";
		}
		
		return "auth/login";
	}
	
	@RequestMapping(value="/loginjudge", method = RequestMethod.GET)
	@ResponseBody
	public String loginjudge(HttpServletRequest request)
	{
		Subject subject = SecurityUtils.getSubject();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String captchaCode = request.getParameter("code").toLowerCase();
		boolean remember = request.getParameter("remember").equals("true");
		
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(remember);
        if (!captchaCode.equals(request.getSession().getAttribute("code").toString().toLowerCase()))
        {
        	return "WrongCaptcha";
        }
        try {
            subject.login(token);
            logger.info("[" + username + "]" + " login successful...");
        } catch (UnknownAccountException e) {
        	//TODO 是否抽到枚举类型的vo里面？
            return "UnknownAccountException";
        } catch (IncorrectCredentialsException e) {
        	return "IncorrectCredentialsException";
        } catch (ExcessiveAttemptsException e) {
        	return "ExcessiveAttemptsException"; 
        } catch (AuthenticationException e) {
        	return "AuthenticationException"; 
        }
        //TODO 如果登陆后把一小块搞成ajax怎么做(改变一小块区域的显示内容)？写成组件化的呢？
        return "auth/success";//TODO 这样写就没法用shiroFilter里面配置的successUrl
	}
	
	@RequestMapping("/success")
	public String loginsuccess()
	{
		return "auth/success";
	}
	
	@RequestMapping("/logout")
	public String logout()
	{
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		
		return "auth/login";
	}
	
	@RequestMapping("/unauthorized")
	public String unauthorized()
	{
		return "auth/unauthorized";
	}
	
	@RequestMapping("/captchaCode")
	public void getCode(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
		CaptchaUtil.getCode(req, resp);
	}
	
}
