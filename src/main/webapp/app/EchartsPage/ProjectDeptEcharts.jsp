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
    <title>之年工时系统</title>
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
<div class="title" style="width: 200px;margin:0 auto"><%--部门任务数量总览--%></div>
<div id="projectDept" style="width: 600px;height:400px;margin:0 auto" class="charts"></div>
<%--<div style="height:50px;margin:0 auto" ></div>--%>
<%--<div class="title"  style="width: 130px;margin:0 auto">部门工时统计</div>--%>
<%--<div id="deptPersonManhour" style="width: 600px;height:400px;margin:0 auto" class="charts"></div>--%>
<div style="height:50px;margin:0 auto"></div>
<div class="title" style="width: 130px;margin:0 auto"><%--各部门委派任务占比--%></div>
<%--<div id="deptAppointPro" style="width: 600px;height:400px;margin:0 auto" class="charts"></div>--%>
<div id="echarts_project_manhour" style="width: 600px;height:400px;margin:0 auto" class="charts"></div>
<div style="height:50px;margin:0 auto"></div>
<%--<div class="title"  style="width: 130px;margin:0 auto">&lt;%&ndash;各部门完成任务占比&ndash;%&gt;</div>--%>
<%--<div id="deptCompletePro" style="width: 600px;height:400px;margin:0 auto" class="charts"></div>--%>

</body>
<script src="<%=request.getContextPath()%>/js/echarts.min.js"></script>
<%--<script src="<%=request.getContextPath()%>/js/echarts.js"></script>--%>
<script>
    $(function () {

        //部门任务数量总览
        projectDept();
        //部门人员工时统计
        // deptPersonManhour();
        //各部门委派任务占比--暂时不用
        // deptAppointPro();
        //各部门完成任务占比 --暂时不用
        // deptCompletePro();
        //各项目预估工时占比
        echarts_project_manhour();

        //各项目预估工时占比
        function echarts_project_manhour() {
            var myChart = echarts.init(document.getElementById('echarts_project_manhour'));
            $.ajax({
                url: '<%=request.getContextPath()%>/tAppProject/getProjectManhourEcharts',
                type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
                dataType: 'json',
                success: function (data) {
                    console.log(data);
                    option = {
                        title: {
                            text: '各项目预计工时比例'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: '{a} <br/>{b} : {c} ({d}%)'
                        },

                        series: [
                            {
                                name: '任务占比',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '55%'],
                                data: data,
                                emphasis: {
                                    itemStyle: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
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
        }

        //部门任务数量总览
        function projectDept() {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('projectDept'));
            $.ajax({
                url: '<%=request.getContextPath()%>/tAppProject/getProjectDeptEcharts',
                type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
                dataType: 'json',
                success: function (data) {
                    console.log(data);
                    option = {
                        color: ['#e5323e', '#006699', '#4cabce'],
                        title: {
                            text: '任务数量总览',
                            // text: '部门任务数量总览',
                            left: 'center'
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'shadow'
                            }
                        },
                        legend: {
                            data: ['委派任务总数', '完成任务数量', '未完成任务数量'],
                            y: 20
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
                                data: data.insnm
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value'
                            }
                        ],
                        series: [
                            {
                                name: '委派任务总数',
                                type: 'bar',
                                barGap: 0,
                                // label: labelOption,
                                data: data.allPro
                            },
                            {
                                name: '已完成任务',
                                type: 'bar',
                                // label: labelOption,
                                data: data.completePro
                            },
                            {
                                name: '未完成任务',
                                type: 'bar',
                                // label: labelOption,
                                data: data.nocompletePro
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
        }

        //部门人员工时统计
        function deptPersonManhour() {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('deptPersonManhour'));
            $.ajax({
                url: '<%=request.getContextPath()%>/tAppProject/getDeptPersonManhourEcharts',
                type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
                dataType: 'json',
                success: function (data) {
                    console.log(data);
                    option = {
                        xAxis: {
                            type: 'category',
                            data: data.insnm
                        },
                        yAxis: {
                            type: 'value'
                        },
                        series: [{
                            data: data.allManhour,
                            type: 'bar',
                            showBackground: true,
                            backgroundStyle: {
                                color: 'rgba(220, 220, 220, 0.8)'
                            }
                        }]
                    };

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                    window.addEventListener("resize", function () {
                        myChart.resize();
                    });
                }
            });
        }

        //各部门委派任务占比
        function deptAppointPro() {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('deptAppointPro'));
            $.ajax({
                url: '<%=request.getContextPath()%>/tAppProject/getProjectDeptProportionEcharts',
                type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
                dataType: 'json',
                success: function (data) {
                    console.log(data);
                    option = {
                        title: {
                            text: '各部门委派任务占比',
                            subtext: '比例展示',
                            left: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: '{a} <br/>{b} : {c} ({d}%)'
                        },

                        series: [
                            {
                                name: '任务占比',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                data: data,
                                emphasis: {
                                    itemStyle: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
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
        }

        //各部门完成任务占比
        function deptCompletePro() {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('deptCompletePro'));
            $.ajax({
                url: '<%=request.getContextPath()%>/tAppProject/getProjectDeptCompleteEcharts',
                type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
                dataType: 'json',
                success: function (data) {
                    console.log(data);
                    option = {
                        title: {
                            text: '各部门完成任务占比',
                            subtext: '比例展示',
                            left: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: '{a} <br/>{b} : {c} ({d}%)'
                        },

                        series: [
                            {
                                name: '任务占比',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                data: data,
                                emphasis: {
                                    itemStyle: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
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
        }


    })

</script>
</html>
