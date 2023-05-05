<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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
            <label class="layui-form-label">人员ID</label>
            <div class="layui-input-block">
                <input type="text" name="mopercd" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">工时总计</label>
            <div class="layui-input-block">
                <input type="text" name="msummanhour" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">主线工时</label>
            <div class="layui-input-block">
                <input type="text" name="mmaster" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">支线工时</label>
            <div class="layui-input-block">
                <input type="text" name="mbranch" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">主线工时权重</label>
            <div class="layui-input-block">
                <input type="text" name="mmasterrate" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">支线工时权重</label>
            <div class="layui-input-block">
                <input type="text" name="mbranchrate" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">创建时间</label>
            <div class="layui-input-block">
                <input type="text" name="mcreatedate" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
    </div>
    <div class="layui-form-item">

        <h2>个人确认工时详情-月:</h2>
        <table class="layui-hide" id="table" lay-filter="table"></table>
    </div>

    <!-- 按钮组 -->
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-primary" id="close">关闭</button>
        </div>
    </div>
</form>


<script>
    layui.use(['form', 'layedit', 'table', 'laydate', 'jquery'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laydate = layui.laydate;
        var table = layui.table; //表格
        //时间控件
        laydate.render({
            elem: '#pcreatedate'
            , type: 'datetime'
            , format: 'yyyy-MM-dd HH:mm:dd'
        });
        //时间控件
        laydate.render({
            elem: '#pcompletedate'
            , type: 'datetime'
            , format: 'yyyy-MM-dd HH:mm:dd'
        });

        var data = JSON.parse(decodeURIComponent(getRequestParam().obj));
        //
        table.render({
            elem: '#table',//table id
            url: '<%=request.getContextPath()%>/tAppManhour/getEntitysByOpercdDateToDate?opercd=' + data.mopercd + '&createdate=' + data.mcreatedate + '&type=month',
            method: 'POST', //方式
            cellMinWidth: 100,
            even: true, //开启隔行背景
            id: 'searchID',
            height: 200,
            cols: [[ //标题栏
                {checkbox: true},
                {field: 'projectname', title: '项目名', align: 'center', sort: true},
                {field: 'projectplanname', title: '项目里程碑', align: 'center', sort: true},
                {field: 'projecttypeNm', title: '项目类型', align: 'center', templet: '#projecttypeNmTpl'},
                {field: 'workdetails', title: '工作内容', align: 'center', sort: true},
                {field: 'manhour', title: '确认工时', align: 'center', sort: true},
                {field: 'createdate', title: '创建时间', align: 'center', sort: true}
            ]]
        });

    });


</script>
<script type="text/html" id="projecttypeNmTpl">
    <span class="layui-badge  {{ d.projecttype == 0 ? 'layui-bg-green' : '' }} ">{{d.projecttypeNm}}</span>
</script>
</body>
</html>

