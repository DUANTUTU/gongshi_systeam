<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
	<div class="layui-form-item">
			    <div class="layui-inline">
		<label class="layui-form-label">ID</label>
			<div class="layui-input-inline">
			<input type="text" name="id" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">项目ID</label>
			<div class="layui-input-inline">
			<input type="text" name="mprojectid" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">项目里程碑ID</label>
			<div class="layui-input-inline">
			<input type="text" name="mprojectplanid" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">人员id</label>
			<div class="layui-input-inline">
			<input type="text" name="mopercd" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">工作内容</label>
			<div class="layui-input-inline">
			<input type="text" name="mworkdetails" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">工时</label>
			<div class="layui-input-inline">
			<input type="text" name="mmanhour" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">审核状态</label>
			<div class="layui-input-inline">
			<input type="text" name="mcheckstatus" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">创建时间</label>
			<div class="layui-input-inline">
			<input type="text" class="layui-input"  name="mcreatedate"  id="mcreatedate" lay-verify="required"  placeholder="请选择" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">审核时间</label>
			<div class="layui-input-inline">
			<input type="text" class="layui-input"  name="mcheckdate"  id="mcheckdate" lay-verify="required"  placeholder="请选择" />
		</div>
	</div>
	</div>
	<!-- 按钮组 -->
	<button class="layui-btn" id="btnSearch" type="button">立即查询</button>
	<button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
    <button class="layui-btn" data-type="btnAdd"  type="button">新增</button>
	<button class="layui-btn" id="btnExport" type="button">导出报表</button>
    <button class="layui-btn layui-btn-danger"  type="button" data-type="btnDelAll">批量删除</button>
</form>
<script>
layui.use(['form', 'layedit', 'laydate','jquery'], function(){
  var form = layui.form;
  var $ = layui.jquery;
  var laydate= layui.laydate;
  
  //时间控件
  laydate.render({
	    elem: '#mcreatedate'
	    ,type: 'datetime'
	    ,format: 'yyyy-MM-dd HH:mm:dd'
  });
  //时间控件
  laydate.render({
	    elem: '#mcheckdate'
	    ,type: 'datetime'
	    ,format: 'yyyy-MM-dd HH:mm:dd'
  });
  

		});
</script>


