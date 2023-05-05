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
					<input type="text" name="mprojectid"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >项目里程碑ID</label>
				<div class="layui-input-block">
					<input type="text" name="mprojectplanid"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >人员id</label>
				<div class="layui-input-block">
					<input type="text" name="mopercd"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >工作内容</label>
				<div class="layui-input-block">
					<input type="text" name="mworkdetails"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >工时</label>
				<div class="layui-input-block">
					<input type="text" name="mmanhour"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >审核状态</label>
				<div class="layui-input-block">
					<input type="text" name="mcheckstatus"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >创建时间</label>
				<div class="layui-input-block">
					<input type="text" name="mcreatedate"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >审核时间</label>
				<div class="layui-input-block">
					<input type="text" name="mcheckdate"  placeholder="无" autocomplete="off" class="layui-input" />
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

