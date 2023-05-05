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
		<label class="layui-form-label">人员ID</label>
			<div class="layui-input-inline">
			<input type="text" name="popercd" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">部门ID</label>
			<div class="layui-input-inline">
			<input type="text" name="pinscd" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">项目ID</label>
			<div class="layui-input-inline">
			<input type="text" name="pprojectid" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">委派状态</label>
			<div class="layui-input-inline">
			<input type="text" name="pappointstatus" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">委派工时</label>
			<div class="layui-input-inline">
			<input type="text" name="pmanhour" maxlength="20"  placeholder="请输入" autocomplete="off" class="layui-input" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">创建时间</label>
			<div class="layui-input-inline">
			<input type="text" class="layui-input"  name="pcreatedate"  id="pcreatedate" lay-verify="required"  placeholder="请选择" />
		</div>
	</div>
	    <div class="layui-inline">
		<label class="layui-form-label">确认时间</label>
			<div class="layui-input-inline">
			<input type="text" class="layui-input"  name="pconfirmdate"  id="pconfirmdate" lay-verify="required"  placeholder="请选择" />
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
	    elem: '#pcreatedate'
	    ,type: 'datetime'
	    ,format: 'yyyy-MM-dd HH:mm:dd'
  });
  //时间控件
  laydate.render({
	    elem: '#pconfirmdate'
	    ,type: 'datetime'
	    ,format: 'yyyy-MM-dd HH:mm:dd'
  });
  

		});
</script>


