<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>查看</title>
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
	<form name="form" class="layui-form" style="margin-top: 20px;" method="post" action="">

		<div class="layui-form-item">
			 			<div class="layui-inline">
				<label class="layui-form-label" >编码</label>
				<div class="layui-input-block">
					<input type="text" name="id"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >姓名</label>
				<div class="layui-input-block">
					<input type="text" name="name"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >性别</label>
				<div class="layui-input-block">
					<input type="text" name="sex"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
		</div>
	  
		<!-- 按钮组 -->
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn layui-btn-primary" id="close">关闭</button>
			</div>
		</div>
	</form>
</body>
</html>

