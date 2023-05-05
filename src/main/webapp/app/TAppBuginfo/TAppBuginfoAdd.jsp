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
<title>新增</title>
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
	<form name="form" class="layui-form" style="margin-top: 20px;" method="post" action="">
	  
	  <div class="layui-form-item">
			 			<div class="layui-inline">
				<label class="layui-form-label"  style="color:#F00"></label>
				<div class="layui-input-block">
					<input type="text" name="id" maxlength="36" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"  >标题</label>
				<div class="layui-input-block">
					<input type="text" name="title" maxlength="255"  placeholder="请输入" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"  >内容</label>
				<div class="layui-input-block">
					<input type="text" name="content" maxlength="2"  placeholder="请输入" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"  >负责人</label>
				<div class="layui-input-block">
					<input type="text" name="responsiblePerson" maxlength="255"  placeholder="请输入" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"  >提交人</label>
				<div class="layui-input-block">
					<input type="text" name="submitter" maxlength="255"  placeholder="请输入" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"  >状态</label>
				<div class="layui-input-block">
					<input type="text" name="status" maxlength="2"  placeholder="请输入" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"  ></label>
				<div class="layui-input-block">
					  <input type="text" class="layui-input"  name="createdTime"  id="createdTime" lay-verify="required"  placeholder="请选择" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"  ></label>
				<div class="layui-input-block">
					  <input type="text" class="layui-input"  name="updatedTime"  id="updatedTime" lay-verify="required"  placeholder="请选择" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"  >预计时间</label>
				<div class="layui-input-block">
					<input type="text" name="estimatedTime" maxlength="2"  placeholder="请输入" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"  >项目名称</label>
				<div class="layui-input-block">
					<input type="text" name="projectName" maxlength="255"  placeholder="请输入" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"  >项目id</label>
				<div class="layui-input-block">
					<input type="text" name="projectId" maxlength="255"  placeholder="请输入" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"  >项目里程碑</label>
				<div class="layui-input-block">
					<input type="text" name="projectMilestones" maxlength="255"  placeholder="请输入" autocomplete="off" class="layui-input" />
				</div>
			</div>
		</div>
		
		<!-- 按钮组 -->
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit="" lay-filter="btnSubmit">立即提交</button>
				<button class="layui-btn layui-btn-primary" id="close">关闭</button>
			</div>
		</div>
	</form>
	<script>
layui.use(['form', 'layedit', 'laydate','jquery'], function(){
  var form = layui.form;
  var $ = layui.jquery;
  var laydate= layui.laydate;
  
  //时间控件
  laydate.render({
	    elem: '#createdTime'
	    ,type: 'datetime'
	    ,format: 'yyyy-MM-dd HH:mm:dd'
  });
  //时间控件
  laydate.render({
	    elem: '#updatedTime'
	    ,type: 'datetime'
	    ,format: 'yyyy-MM-dd HH:mm:dd'
  });
  
   //监听提交
   form.on('submit(btnSubmit)', function(data){
   // layer.msg(JSON.stringify(data.field));
   var index = layer.load(1);//开启进度条
      $.ajax({
		url : '<%=request.getContextPath()%>/tAppBuginfo/add',
		data : data.field,
		type : 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
		dataType : 'json',
		success : function(obj) {
			layer.close(index);//关闭   
			if (obj.success) {
				pubUtil.msg(obj.msg, layer, 1, function() {
					$("#close").click();
				}, 500);
			} else {
				pubUtil.msg(obj.msg, layer, 2, function() {

				}, 5 * 1000);
			}
		}
	});
	return false;
});

		});
	</script>
</body>
</html>

