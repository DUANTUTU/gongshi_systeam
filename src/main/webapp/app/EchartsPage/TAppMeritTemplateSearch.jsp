<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
    <div class="layui-form-item">

        <div class="layui-inline">
            <label class="layui-form-label">职位</label>
            <div class="layui-input-inline">
                <select name="mjobid" id="mjobid"></select>
            </div>
        </div>
    </div>
    <!-- 按钮组 -->
    <button class="layui-btn" id="btnSearch" type="button">立即查询</button>
    <button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
</form>
<script>
    layui.use(['form', 'layedit', 'laydate', 'jquery'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laydate = layui.laydate;

        //时间控件
        laydate.render({
            elem: '#mcreatedate'
            , type: 'datetime'
            , format: 'yyyy-MM-dd HH:mm:dd'
        });
        //时间控件
        laydate.render({
            elem: '#mupdatedate'
            , type: 'datetime'
            , format: 'yyyy-MM-dd HH:mm:dd'
        });
        //职位下拉菜单
        pbInitComCombox($, form, 'tSysJob/getList', 'mjobid', 'id', 'jname');
        //是否关联项目下拉菜单
    });
</script>


