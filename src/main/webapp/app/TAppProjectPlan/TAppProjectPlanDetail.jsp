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
				<label class="layui-form-label" >ID</label>
				<div class="layui-input-block">
					<input type="text" name="id"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >项目ID</label>
				<div class="layui-input-block">
					<input type="text" name="pprojectid"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >里程碑名称</label>
				<div class="layui-input-block">
					<input type="text" name="pplanname"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >里程碑工时预估</label>
				<div class="layui-input-block">
					<input type="text" name="pplanmanhour"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >里程碑顺序</label>
				<div class="layui-input-block">
					<input type="text" name="pplanorder"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >是否完成</label>
				<div class="layui-input-block">
					<input type="text" name="piscomplete"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >里程碑确认工时</label>
				<div class="layui-input-block">
					<input type="text" name="psummanhour"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >创建时间</label>
				<div class="layui-input-block">
					<input type="text" name="pcreatedate"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >完成时间</label>
				<div class="layui-input-block">
					<input type="text" name="pcompletedate"  placeholder="无" autocomplete="off" class="layui-input" />
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

