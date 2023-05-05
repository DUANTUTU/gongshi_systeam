<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
	<div class="layui-form-item">
			    <div class="layui-inline">
		<label class="layui-form-label"></label>
			<div class="layui-input-inline">
			<input type="text" name="id" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">标题</label>
			<div class="layui-input-inline">
			<input type="text" name="title" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">内容</label>
			<div class="layui-input-inline">
			<input type="text" name="content" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">负责人</label>
			<div class="layui-input-inline">
			<input type="text" name="responsiblePerson" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">提交人</label>
			<div class="layui-input-inline">
			<input type="text" name="submitter" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">状态</label>
			<div class="layui-input-inline">
			<input type="text" name="status" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label"></label>
			<div class="layui-input-inline">
			<input type="text" class="layui-input"  name="createdTime"  id="createdTime" lay-verify="required"  placeholder="请选择" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label"></label>
			<div class="layui-input-inline">
			<input type="text" class="layui-input"  name="updatedTime"  id="updatedTime" lay-verify="required"  placeholder="请选择" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">预计时间</label>
			<div class="layui-input-inline">
			<input type="text" name="estimatedTime" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">项目名称</label>
			<div class="layui-input-inline">
			<input type="text" name="projectName" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">项目id</label>
			<div class="layui-input-inline">
			<input type="text" name="projectId" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">项目里程碑</label>
			<div class="layui-input-inline">
			<input type="text" name="projectMilestones" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	</div>
	<!-- 按钮组 -->
	<button class="layui-btn" id="btnSearch" type="button">立即查询</button>
	<button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
    <button class="layui-btn" data-type="btnAdd"  type="button">新增</button>
    <button class="layui-btn layui-btn-danger"  type="button" data-type="btnDelAll">批量删除</button>
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
  

		});
</script>


