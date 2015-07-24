/**
 * 
 */
package com.cat.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import com.cat.spring.entity.Permission;
import com.cat.spring.entity.Role;
import com.cat.spring.entity.User;

public class ShiroRealm extends AuthorizingRealm {
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 根据用户配置用户与权限
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}
		String name = (String) getAvailablePrincipal(principals);
		List<String> roles = new ArrayList<String>();
		List<String> per = new ArrayList<String>();
		// 简单默认一个用户与角色，实际项目应User user = userService.getByAccount(name);
		User user = new User("shi", "123456");
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
		if (user.getName().equals(name)) {
			if (user.getRole() != null&&user.getRole().getList().size()>0) {
				roles.add(user.getRole().getName());
				for(Permission permiss:user.getRole().getList()){
					per.add(permiss.getDescription());
				}
			}
		} else {
			throw new AuthorizationException();
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 增加角色
		info.addRoles(roles);
		info.addStringPermissions(per);
		return info;
	}

	@SuppressWarnings("unused")
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		// 简单默认一个用户,实际项目应User user = userService.getByAccount(token.getUsername());
		User user = new User("shi", "123456");
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
		Session session=SecurityUtils.getSubject().getSession();
		if (user == null) {
			throw new AuthorizationException();
		}
		SimpleAuthenticationInfo info = null;
		if (user.getName().equals(token.getUsername())) {
			info = new SimpleAuthenticationInfo(user.getName(), user.getPassword(), getName());
			session.setAttribute("user", user);
		}
		return info;
	}
}
