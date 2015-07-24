<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>shiro 成功界面</title>
</head>
<body>
	<h3>用户：${user.name} 登录成功, 角色为 ${user.role.name},权限为：${user.role.list}</h3>
</body>
</html>