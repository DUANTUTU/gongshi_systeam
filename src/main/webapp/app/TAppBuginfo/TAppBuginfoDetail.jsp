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
				<label class="layui-form-label" ></label>
				<div class="layui-input-block">
					<input type="text" name="id"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >标题</label>
				<div class="layui-input-block">
					<input type="text" name="title"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >内容</label>
				<div class="layui-input-block">
					<input type="text" name="content"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >负责人</label>
				<div class="layui-input-block">
					<input type="text" name="responsiblePerson"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >提交人</label>
				<div class="layui-input-block">
					<input type="text" name="submitter"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >状态</label>
				<div class="layui-input-block">
					<input type="text" name="status"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" ></label>
				<div class="layui-input-block">
					<input type="text" name="createdTime"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" ></label>
				<div class="layui-input-block">
					<input type="text" name="updatedTime"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >预计时间</label>
				<div class="layui-input-block">
					<input type="text" name="estimatedTime"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >项目名称</label>
				<div class="layui-input-block">
					<input type="text" name="projectName"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >项目id</label>
				<div class="layui-input-block">
					<input type="text" name="projectId"  placeholder="无" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" >项目里程碑</label>
				<div class="layui-input-block">
					<input type="text" name="projectMilestones"  placeholder="无" autocomplete="off" class="layui-input" />
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

