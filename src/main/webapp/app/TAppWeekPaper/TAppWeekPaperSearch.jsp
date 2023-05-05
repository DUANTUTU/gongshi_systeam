<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">人员ID</label>
            <div class="layui-input-inline">
                <input type="text" name="wopercd" maxlength="20" placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">创建时间</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" name="wcreatedate" id="wcreatedate" lay-verify="required"
                       placeholder="请选择"/>
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
            elem: '#wcreatedate'
            , type: 'datetime'
            , format: 'yyyy-MM-dd HH:mm:dd'
        });


    });
</script>


