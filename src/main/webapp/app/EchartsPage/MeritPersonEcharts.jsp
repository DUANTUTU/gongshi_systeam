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
    <jsp:include page="../../inc.jsp"></jsp:include>
    <script src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
    <script>
        $(window).load(function () {
            $(".loading").fadeOut()
        })
    </script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
</head>
<body>
<div class="layui-tab">
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <jsp:include page="TAppMeritTemplateSearch.jsp"></jsp:include>
        </div>
    </div>
</div>
<div class="loading">
    <div class="loadbox"><img src="<%=request.getContextPath()%>/picture/loading.gif"> 页面加载中...</div>

</div>
<div class="title" style="width: 200px;margin:0 auto">职位人员绩效统计图表</div>
<div id="personMerite" style="width: 600px;height:400px;margin:0 auto" class="charts"></div>
</body>
<script src="<%=request.getContextPath()%>/js/echarts.min.js"></script>
<%--<script src="<%=request.getContextPath()%>/js/echarts.js"></script>--%>
<script>
    //查询按钮
    $('#btnSearch').on('click', function () {
        //人员确认工时统计
        var sjobid = $("#mjobid").val();
        if (sjobid==""){
            return;
        }
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('personMerite'));
        $.ajax({
            url: '<%=request.getContextPath()%>/tAppMeritAssess/getMeritAssessEcharts',
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            data: {
                sJobId: sjobid
            },
            dataType: 'json',
            success: function (data) {
                console.log(data);
                option = {
                    color: ['#e5323e', '#006699', '#4cabce'],
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: ['绩效完成度', '主线工时占比', '临时工时占比']
                    },
                    toolbox: {
                        show: true,
                        orient: 'vertical',
                        left: 'right',
                        top: 'center',
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    xAxis: [
                        {
                            type: 'category',
                            axisTick: {show: false},
                            data: data.opernm
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '绩效完成率',
                            type: 'bar',
                            barGap: 0,
                            // label: labelOption,
                            data: data.msumhourrate
                        },
                        {
                            name: '主线工时占比',
                            type: 'bar',
                            // label: labelOption,
                            data: data.mmasterrate
                        },
                        {
                            name: '临时工时占比',
                            type: 'bar',
                            // label: labelOption,
                            data: data.mbranchrate
                        }
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
                window.addEventListener("resize", function () {
                    myChart.resize();
                });
            }
        });
    });



</script>
</html>
