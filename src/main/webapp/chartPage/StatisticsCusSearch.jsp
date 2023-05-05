<%--
  Created by IntelliJ IDEA.
  User: Ming
  Date: 2020/4/3
  Time: 13:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>工时系统</title>
    <script src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
    <script>
        $(window).load(function () {
            $(".loading").fadeOut()
        })
    </script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
</head>
<body>
<div class="loading">
    <div class="loadbox"><img src="<%=request.getContextPath()%>/picture/loading.gif"> 页面加载中...</div>

</div>
<div class="title" style="width: 200px;margin:0 auto" >每日爬取数量统计</div>
<div id="echarts_time"  style="width: 600px;height:400px;margin:0 auto" class="charts"></div>
<div style="height:50px;margin:0 auto" ></div>
<div class="title"  style="width: 130px;margin:0 auto">爬取网站数据比例</div>
<div id="echarts_pNetname" style="width: 600px;height:400px;margin:0 auto" class="charts"></div>
</body>
<script src="<%=request.getContextPath()%>/js/echarts.min.js"></script>
<script src="<%=request.getContextPath()%>/js/china.js"></script>
<script src="<%=request.getContextPath()%>/js/echarts.js"></script>
</html>
