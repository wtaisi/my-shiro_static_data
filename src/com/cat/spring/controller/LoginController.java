/**
 * 
 */
package com.cat.spring.controller;


import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cat.spring.entity.Permission;
import com.cat.spring.entity.User;

@Controller
public class LoginController {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("/login");
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public ModelAndView submit(String username, String password) {
		try {
				UsernamePasswordToken token = new UsernamePasswordToken(username,password);
				Subject subject = SecurityUtils.getSubject();
				subject.login(token);
				if (subject.hasRole("member")) {// 券商
					User u = (User) subject.getSession().getAttribute("user");
					String q = u.getName();
					System.out.println("用户名:	"+q+"，角色："+u.getRole().getName());
				}
				if(subject.isPermitted("user:create")){
					User u = (User) subject.getSession().getAttribute("user");
					List<Permission> li= u.getRole().getList();
					System.out.println("权限为名:	"+li);
				}
		}catch (UnknownAccountException e) {
			return new ModelAndView("member/UnknownAccountException");
		}catch (IncorrectCredentialsException  e) {
			return new ModelAndView("member/IncorrectCredentialsException");
		}catch (LockedAccountException  e) {
			return new ModelAndView("member/LockedAccountException");
		}catch (ExcessiveAttemptsException  e) {
			return new ModelAndView("member/ExcessiveAttemptsException");
		}catch (AuthenticationException  e) {
			return new ModelAndView("member/AuthenticationException");
		}
		
		return new ModelAndView("redirect:/member/index.htm");
	}
}
