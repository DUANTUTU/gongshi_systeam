<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
    <div class="layui-form-item">

        <div class="layui-inline">
            <label class="layui-form-label">职位名称</label>
            <div class="layui-input-inline">
                <input type="text" name="jname" maxlength="20" placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">职位状态</label>
            <div class="layui-input-inline">
                <select name="jstatus" id="jstatus"></select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">职位类型</label>
            <div class="layui-input-inline">
                <select name="jtype" id="jtype"></select>
            </div>
        </div>

    </div>
    <!-- 按钮组 -->
    <button class="layui-btn" id="btnSearch" type="button">立即查询</button>
    <button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
    <button class="layui-btn" data-type="btnAdd" type="button">新增</button>
    <button class="layui-btn layui-btn-danger" type="button" data-type="btnDelAll">批量删除</button>
</form>
<script>
    layui.use(['form', 'layedit', 'laydate', 'jquery'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laydate = layui.laydate;

        //时间控件
        laydate.render({
            elem: '#jcreatedate'
            , type: 'datetime'
            , format: 'yyyy-MM-dd HH:mm:dd'
        });


        //初始化职位状态下拉框
        pbInitCombox($, form, 'dictTypeCd=job_status', 'jstatus');//job_status为数据字典类型编号
        pbInitCombox($, form, 'dictTypeCd=job_type', 'jtype');//job_type为数据字典类型编号

    });
</script>


