/**
 * 
 */
package com.cat.shiro;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.cat.spring.entity.Permission;
import com.cat.spring.entity.Role;
import com.cat.spring.entity.User;
public class ShiroFilter implements Filter {

	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		Principal principal = httpRequest.getUserPrincipal();

		if (principal != null) {
			Subject subjects = SecurityUtils.getSubject();
			// 为了简单，这里初始化一个用户。实际项目项目中应该去数据库里通过名字取用户：
			// 例如：User user = userService.getByAccount(principal.getName());
			User user = new User();
			user.setName("shi");
			user.setPassword("123456");
			Permission p1=new Permission();
			Permission p2=new Permission();
			Permission p3=new Permission();
			Permission p4=new Permission();
			p1.setDescription("user:create");
			p2.setDescription("user:update");
			p3.setDescription("user:del");
			p4.setDescription("user:select");
			List<Permission> list=new ArrayList<Permission>();
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			user.setRole(new Role("member",list));
			if (user.getName().equals(principal.getName())) {
				UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user
						.getPassword());
				subjects = SecurityUtils.getSubject();
				subjects.login(token);
				subjects.getSession();
			} else {
				// 如果用户为空，则subjects信息登出
				if (subjects != null) {
					subjects.logout();
				}
			}
		}
		chain.doFilter(httpRequest, httpResponse);

	}
//	public void doFilter(ServletRequest request, ServletResponse response,   
//            FilterChain chain) throws IOException, ServletException {   
//        request.setCharacterEncoding("UTF-8");   
//        response.setCharacterEncoding("UTF-8");   
//        HttpServletRequest req = (HttpServletRequest) request;   
//        HttpServletResponse res = (HttpServletResponse) response;   
//        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
//        if (null != user) {
//        	String conString = "";
//        	//获取父url--如果不是直接输入的话就是先前的访问过来的页面，要是用户输入了，这个父url是不存在的
//    		conString = req.getHeader("REFERER");
//    		//判断如果上一个目录为空的话，说明是用户直接输入url访问的
//    		if("".equals(conString) || null==conString){ 
//    			//当前请求url，去掉几个可以直接访问的页面
//    			String servletPath = req.getServletPath();
//    			//跳过/login和/
//    			if(servletPath.equals("/")||servletPath.equals("/login")||servletPath.equals("/login/")){ 
//    				chain.doFilter(request, response);
//    			} else {
//    				//res.sendRedirect(req.getContextPath()+"/login");//跳回首页
//    				req.getRequestDispatcher("/login").forward(req, res);
//    			}
//    		} else {
//    			chain.doFilter(request, response);
//    		}
//        } else {
//        	chain.doFilter(request, response);
//        }
//    }   

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
