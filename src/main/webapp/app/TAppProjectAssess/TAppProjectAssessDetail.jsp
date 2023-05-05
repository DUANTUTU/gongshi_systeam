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
            <label class="layui-form-label">项目名称</label>
            <div class="layui-input-block">
                <input type="text" name="pname" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">项目描述</label>
            <div class="layui-input-block">
                <input type="text" name="pdetails" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">工时预估</label>
            <div class="layui-input-block">
                <input type="text" name="pmanhourplan" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">项目类型</label>
            <div class="layui-input-block">
                <input type="text" name="ptypeNm" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">发起人</label>
            <div class="layui-input-block">
                <input type="text" name="popercdNm" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">发起人部门</label>
            <div class="layui-input-block">
                <input type="text" name="pinscdNm" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">确认工时</label>
            <div class="layui-input-block">
                <input type="text" name="pmanhour" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">是否委派</label>
            <div class="layui-input-block">
                <input type="text" name="pisappointNm" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">是否完成</label>
            <div class="layui-input-block">
                <input type="text" name="piscompleteNm" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">创建时间</label>
            <div class="layui-input-block">
                <input type="text" name="pcreatedate" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">完成时间</label>
            <div class="layui-input-block">
                <input type="text" name="pcompletedate" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
    </div>
    <div class="layui-form-item">

        <h2>项目里程碑信息:</h2>
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
        table.render({
            elem: '#table',//table id
            url: '<%=request.getContextPath()%>/tAppProjectPlan/datagrid?pprojectid='+data.id,
            method: 'POST', //方式
            cellMinWidth: 100,
            even: true, //开启隔行背景
            id: 'searchID',
            height: 'full-200',
            cols: [[ //标题栏
                {checkbox: true},
                {field: 'pplanname', title: '里程碑名称', align: 'center', sort: true},
                {field: 'pplanmanhour', title: '里程碑工时预估', align: 'center', sort: true},
                {field: 'pplanorder', title: '里程碑顺序', align: 'center', sort: true},
                {field: 'piscompleteNm', title: '是否完成', align: 'center', templet:'#piscompleteNmTpl'},
                {field: 'psummanhour', title: '确认工时', align: 'center', sort: true},
                {field: 'pcreatedate', title: '创建时间', align: 'center', sort: true},
                {field: 'pcompletedate', title: '完成时间', align: 'center', sort: true}
            ]]
        });

    });


</script>
<script type="text/html" id="piscompleteNmTpl">
    <span class="layui-badge  {{ d.piscomplete == 0 ? 'layui-bg-green' : '' }} ">{{d.piscompleteNm}}</span>
</script>
</body>
</html>

