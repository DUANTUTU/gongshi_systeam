<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<head>
    <meta charset="utf-8">
    <title>工时系统</title>
    <jsp:include page="inc.jsp"></jsp:include>
</head>

<body>
<html>
<H1>工时系统</H1><br>
<fieldset class="layui-elem-field layui-field-title alone-title" style="width: 300px;">
    <legend>历史版本事件</legend>
</fieldset>
<ul class="layui-timeline" style="margin-left:50px">
    <li class="layui-timeline-item"><i class="layui-icon layui-timeline-axis">&#xe63f;</i>
        <div class="layui-timeline-content layui-text">
            <h3 class="layui-timeline-title">2020年03月25日</h3>
            <p>
                工时系统立项
            </p>
        </div>
    </li>
    <li class="layui-timeline-item"><i class="layui-icon layui-timeline-axis">&#xe63f;</i>
        <div class="layui-timeline-content layui-text">
            <h3 class="layui-timeline-title">2020年03月26日</h3>
            <p>
                完成需求分析
            </p>
        </div>
    </li>
    <li class="layui-timeline-item"><i class="layui-icon layui-timeline-axis">&#xe63f;</i>
        <div class="layui-timeline-content layui-text">
            <h3 class="layui-timeline-title">2020年03月29日</h3>
            <p>
                完成概要设计及系统运行数据设计
            </p>
        </div>
    </li>
    <li class="layui-timeline-item"><i class="layui-icon layui-timeline-axis">&#xe63f;</i>
        <div class="layui-timeline-content layui-text">
            <h3 class="layui-timeline-title">2020年03月31日</h3>
            <p>
                工时系统代码构建
            </p>
        </div>
    </li>
    <li class="layui-timeline-item"><i class="layui-icon layui-timeline-axis">&#xe63f;</i>
        <div class="layui-timeline-content layui-text">
            <h3 class="layui-timeline-title">2020年04月3日</h3>
            <p>
                完成基本demo:百度及黄页88数据获取功能
            </p>
        </div>
    </li>
    <li class="layui-timeline-item"><i class="layui-icon layui-timeline-axis">&#xe63f;</i>
        <div class="layui-timeline-content layui-text">
            <div class="layui-timeline-title">过去</div>
        </div>
    </li>
</ul>
</body>

</html>
<script type="text/javascript">
    layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload',
        'element'], function () {

    });
</script>
