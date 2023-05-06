<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/3/26
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>之年工时系统</title>
    <script src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
    <script>
        $(window).load(function () {
            $(".loading").fadeOut()
        })
    </script>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/map.css">
</head>
<body>
<div class="loading">
    <div class="loadbox"><img src="<%=request.getContextPath()%>/picture/loading.gif"> 页面加载中...</div>
</div>
<div class="data">
    <div class="data-title">
        <div class="title-center ">爬取数据统计</div>
    </div>
    <div class="data-content">
        <div class="con-left fl">
            <div class="left-top">
                <div class="info boxstyle">
                    <div class="title"><%--实时统计--%></div>
                    <div class="info-main">
                        <ul>
                            <li><img src="<%=request.getContextPath()%>/picture/info-img-1.png" alt=""><span><%--车辆总数(辆)--%></span>
                                <p><%--12,457--%></p></li>
                            <li><img src="<%=request.getContextPath()%>/picture/info-img-2.png" alt=""><span><%--当前在线数(辆)--%></span>
                                <p><%--12,457--%></p></li>
                            <li><img src="<%=request.getContextPath()%>/picture/info-img-3.png" alt=""><span><%--今日活跃数(辆)--%></span>
                                <p><%--12,457--%></p></li>
                            <li><img src="<%=request.getContextPath()%>/picture/info-img-4.png" alt=""><span><%--今日活跃率(%)--%></span>
                                <p><%--74%--%></p></li>
                        </ul>
                    </div>
                </div>
                <div class="top-bottom boxstyle">
                    <div class="title"><%--行业分类--%></div>
                    <div id="echarts_hangyefenlei" class="charts"></div>
                </div>
            </div>
            <div class="left-bottom boxstyle">
                <div class="title"><%--车型分类--%></div>
                <div id="echarts_chexingfenlei" class="charts"></div>
            </div>
        </div>
        <div class="con-center fl">
            <div class="map-num">
                <p>爬取总数</p>
                <div class="num">
                    <%--<span>1123</span>--%>
                    <%--<span>5</span>--%>
                    <%--<span>4</span>--%>
                    <%--<span>9</span>--%>
                    <%--<span>2</span>--%>
                    <%--<span>6</span>--%>
                    <%--<span>8</span>--%>
                </div>
            </div>
           <%-- <div class="cen-top map" id="map"></div>--%>
            <div id="echarts_paquliang" class="charts"></div>
            <div class="cen-bottom boxstyle">
                <div class="title"><%--车辆充电高峰时间--%></div>
                <div id="echarts_chongdiangaofeng" class="charts"></div>
            </div>
        </div>
        <div class="con-right fr">
            <div class="right-top boxstyle">
                <div class="title"><%--本月行驶里程TOP5--%></div>
                <div id="echarts_xingshilicheng" class="charts"></div>
            </div>
            <div class="right-center boxstyle">
                <div class="title"><%--报警车辆TOP5--%></div>
                <div id="echarts_baojingcheliang" class="charts"></div>
            </div>
            <div class="right-bottom boxstyle">
                <div class="title"><%--电池报警车辆TOP10--%></div>
                <div id="echarts_dianchibaojing" class="charts"></div>
            </div>
        </div>
    </div>
</div>
</body>

<script src="<%=request.getContextPath()%>/js/echarts.min.js"></script>
<script src="<%=request.getContextPath()%>/js/china.js"></script>
<script src="<%=request.getContextPath()%>/js/echarts.js"></script>
<script >
    $(function () {

    })


</script>
</html>
