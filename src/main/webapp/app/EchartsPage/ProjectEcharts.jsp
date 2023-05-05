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
            <jsp:include page="ProjectEchartsSearch.jsp"></jsp:include>
        </div>
    </div>
</div>
<div class="loading">
    <div class="loadbox"><img src="<%=request.getContextPath()%>/picture/loading.gif"> 页面加载中...</div>

</div>
<div class="title" style="width: 200px;margin:0 auto"><%--项目里程碑计划工时占比--%></div>
<div id="proManhour" style="width: 600px;height:400px;margin:0 auto" class="charts"></div>
<div style="height:50px;margin:0 auto" ></div>
<div class="title"  style="width: 200px;margin:0 auto"><%--项目里程碑完成工时占比--%></div>
<div id="finshProManhour" style="width: 600px;height:400px;margin:0 auto" class="charts"></div>
<div style="height:50px;margin:0 auto" ></div>
<div class="title"  style="width: 200px;margin:0 auto"><%--项目工时情况--%></div>
<div id="proHourAndFinshHour" style="width: 600px;height:400px;margin:0 auto" class="charts"></div>
</body>
<script src="<%=request.getContextPath()%>/js/echarts.min.js"></script>
<%--<script src="<%=request.getContextPath()%>/js/echarts.js"></script>--%>
<script>
    //查询按钮
    $('#btnSearch').on('click', function () {
        //人员确认工时统计
        var projectId = $("#projectId").val();
        if (projectId==""){
            return;
        }
        // 项目里程碑计划工时占比
        var myChartProManhour = echarts.init(document.getElementById('proManhour'));
        $.ajax({
            url: '<%=request.getContextPath()%>/tAppManhour/getProManhour',
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            data: {
                projectId: projectId
            },
            dataType: 'json',
            success: function (data) {
                // console.log(data);
                option = {
                    title: {
                        text: '项目里程碑计划工时占比',
                        // subtext: '纯属虚构',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: data.planName
                    },
                    series: [
                        {
                            name: '项目里程碑',
                            type: 'pie',
                            radius: '65%',
                            center: ['50%', '60%'],
                            data: data.planHour,
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
                myChartProManhour.setOption(option);
                window.addEventListener("resize", function () {
                    myChartProManhour.resize();
                });
            }
        });
        // 项目里程碑完成工时占比
        var myChartFinshProManhour = echarts.init(document.getElementById('finshProManhour'));
        $.ajax({
            url: '<%=request.getContextPath()%>/tAppManhour/getFinshProManhour',
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            data: {
                projectId: projectId
            },
            dataType: 'json',
            success: function (data) {
                // console.log(data);
                option = {
                    title: {
                        text: '项目里程碑完成工时占比',
                        // subtext: '纯属虚构',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: data.planName
                    },
                    series: [
                        {
                            name: '项目里程碑',
                            type: 'pie',
                            radius: '65%',
                            center: ['50%', '60%'],
                            data: data.finshHour,
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
                myChartFinshProManhour.setOption(option);
                window.addEventListener("resize", function () {
                    myChartFinshProManhour.resize();
                });
            }
        });
        // 项目工时情况
        var myChartProHourAndFinshHour = echarts.init(document.getElementById('proHourAndFinshHour'));
        $.ajax({
            url: '<%=request.getContextPath()%>/tAppManhour/getProHourAndFinshHour',
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            data: {
                projectId: projectId
            },
            dataType: 'json',
            success: function (data) {
                // console.log(data);
                option = {
                    color: ['#e5323e', '#006699', '#4cabce'],
                    title: {
                        text: '项目工时情况',
                        // subtext: '纯属虚构',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: ['计划工时', '完成工时'],
                        y:20
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
                            data: data.planName
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '计划工时',
                            type: 'bar',
                            barGap: 0,
                            // label: labelOption,
                            data: data.planHour
                        },
                        {
                            name: '完成工时',
                            type: 'bar',
                            // label: labelOption,
                            data: data.finshHour
                        }
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChartProHourAndFinshHour.setOption(option);
                window.addEventListener("resize", function () {
                    myChartProHourAndFinshHour.resize();
                });
            }
        });



    });

    $(function () {

        //人员确认工时统计
        // personManhour();
        //人员参与项目统计
        // personProjectSum();


        //人员确认工时统计
        function personManhour() {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('personManhour'));
            $.ajax({
                url: '<%=request.getContextPath()%>/tAppDayPaper/getPersonManhourEcharts',
                type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
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
                            data: ['总确认工时', '主线任务工时', '临时任务工时']
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
                                data: data.personName
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value'
                            }
                        ],
                        series: [
                            {
                                name: '总工时',
                                type: 'bar',
                                barGap: 0,
                                // label: labelOption,
                                data: data.sumManhour
                            },
                            {
                                name: '主线任务工时',
                                type: 'bar',
                                // label: labelOption,
                                data: data.masterManhour
                            },
                            {
                                name: '临时任务工时',
                                type: 'bar',
                                // label: labelOption,
                                data: data.branchManhour
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

        //人员参与项目统计
        function personProjectSum() {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('personProjectSum'));
            $.ajax({
                url: '<%=request.getContextPath()%>/tAppProjectPerson/getPersonProjectEcharts',
                type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
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
                            data: ['总任务数量', '主线任务数量', '临时任务数量']
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
                                data: data.personName
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value'
                            }
                        ],
                        series: [
                            {
                                name: '总任务数量',
                                type: 'bar',
                                barGap: 0,
                                // label: labelOption,
                                data: data.proAllNum
                            },
                            {
                                name: '主线任务数量',
                                type: 'bar',
                                // label: labelOption,
                                data: data.proMasterNum
                            },
                            {
                                name: '临时任务数量',
                                type: 'bar',
                                // label: labelOption,
                                data: data.proBranchNum
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


        // echarts_chexingfenlei();// return  俩list

        // echarts_pressSearchCus_day();//柱状图
        //
        // echarts_pressSearchCusToCus_day();//柱状图


        function echarts_pressSearchCus_day() {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('echarts_pressSearchCus_day'));
            // var xData = function () {
            //     var data = ['NO.1', 'NO.2', 'NO.3', 'NO.4', 'NO.5'];
            //
            //     return data;
            // }();
            //
            // var data = [23, 22, 20, 30, 22]

            $.ajax({
                url: '<%=request.getContextPath()%>/tAppStatisticsCus/getEchartsStatisticsCusByDay?type=search',
                type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
                dataType: 'json',
                success: function (data) {
                    console.log(data);
                    option = {
                        // backgroundColor: "#141f56",

                        tooltip: {
                            show: "true",
                            trigger: 'item',
                            backgroundColor: 'rgba(0,0,0,0.4)', // 背景
                            padding: [8, 10], //内边距
                            // extraCssText: 'box-shadow: 0 0 3px rgba(255, 255, 255, 0.4);', //添加阴影
                            formatter: function (params) {
                                if (params.seriesName != "") {
                                    return params.name + ' ：  ' + params.value + '';
                                }
                            },

                        },
                        grid: {
                            borderWidth: 0,
                            top: 20,
                            bottom: 35,
                            left: 80,
                            right: 10,
                            textStyle: {
                                color: "#fff"
                            }
                        },
                        xAxis: [{
                            type: 'category',

                            axisTick: {
                                show: false
                            },

                            axisLine: {
                                show: true,
                                lineStyle: {
                                    color: 'rgba(255,255,255,0.2)',
                                }
                            },
                            axisLabel: {
                                inside: false,
                                textStyle: {
                                    color: '#bac0c0',
                                    fontWeight: 'normal',
                                    fontSize: '12',
                                },
                                // formatter:function(val){
                                //     return val.split("").join("\n")
                                // },
                            },
                            data: data[0],
                        }, {
                            type: 'category',
                            axisLine: {
                                show: false
                            },
                            axisTick: {
                                show: false
                            },
                            axisLabel: {
                                show: false
                            },
                            splitArea: {
                                show: false
                            },
                            splitLine: {
                                show: false
                            },
                            data: data[0],
                        }],
                        yAxis: {
                            min: 10,
                            type: 'value',
                            axisTick: {
                                show: false
                            },
                            axisLine: {
                                show: true,
                                lineStyle: {
                                    color: 'rgba(255,255,255,0.2)',
                                }
                            },
                            splitLine: {
                                show: true,
                                lineStyle: {
                                    color: 'rgba(255,255,255,0.1)',
                                }
                            },
                            axisLabel: {
                                textStyle: {
                                    color: '#bac0c0',
                                    fontWeight: 'normal',
                                    fontSize: '12',
                                },
                                formatter: '{value}',
                            },
                        },
                        series: [{
                            type: 'bar',
                            itemStyle: {
                                normal: {
                                    show: true,
                                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                        offset: 0,
                                        color: '#00c0e9'
                                    }, {
                                        offset: 1,
                                        color: '#3b73cf'
                                    }]),
                                    barBorderRadius: 50,
                                    borderWidth: 0,
                                },
                                emphasis: {
                                    shadowBlur: 15,
                                    shadowColor: 'rgba(105,123, 214, 0.7)'
                                }
                            },
                            zlevel: 2,
                            barWidth: '20%',
                            data: data[1],
                        },
                            {
                                name: '',
                                type: 'bar',
                                xAxisIndex: 1,
                                zlevel: 1,
                                itemStyle: {
                                    normal: {
                                        color: 'transparent',
                                        borderWidth: 0,
                                        shadowBlur: {
                                            shadowColor: 'rgba(255,255,255,0.31)',
                                            shadowBlur: 10,
                                            shadowOffsetX: 0,
                                            shadowOffsetY: 2,
                                        },
                                    }
                                },
                                barWidth: '20%',
                                data: data[1]
                            }
                        ]
                    }


                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                    window.addEventListener("resize", function () {
                        myChart.resize();
                    });
                }
            });
        }

        function echarts_pressSearchCusToCus_day() {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('echarts_pressSearchCusToCus_day'));
            // var xData = function () {
            //     var data = ['NO.1', 'NO.2', 'NO.3', 'NO.4', 'NO.5'];
            //
            //     return data;
            // }();
            //
            // var data = [23, 22, 20, 30, 22]

            $.ajax({
                url: '<%=request.getContextPath()%>/tAppStatisticsCus/getEchartsRealStatisticsCusByDay?type=search',
                type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
                dataType: 'json',
                success: function (data) {
                    console.log(data);
                    option = {
                        // backgroundColor: "#141f56",

                        tooltip: {
                            show: "true",
                            trigger: 'item',
                            backgroundColor: 'rgba(0,0,0,0.4)', // 背景
                            padding: [8, 10], //内边距
                            // extraCssText: 'box-shadow: 0 0 3px rgba(255, 255, 255, 0.4);', //添加阴影
                            formatter: function (params) {
                                if (params.seriesName != "") {
                                    return params.name + ' ：  ' + params.value + '';
                                }
                            },

                        },
                        grid: {
                            borderWidth: 0,
                            top: 20,
                            bottom: 35,
                            left: 80,
                            right: 10,
                            textStyle: {
                                color: "#fff"
                            }
                        },
                        xAxis: [{
                            type: 'category',

                            axisTick: {
                                show: false
                            },

                            axisLine: {
                                show: true,
                                lineStyle: {
                                    color: 'rgba(255,255,255,0.2)',
                                }
                            },
                            axisLabel: {
                                inside: false,
                                textStyle: {
                                    color: '#bac0c0',
                                    fontWeight: 'normal',
                                    fontSize: '12',
                                },
                                // formatter:function(val){
                                //     return val.split("").join("\n")
                                // },
                            },
                            data: data[0],
                        }, {
                            type: 'category',
                            axisLine: {
                                show: false
                            },
                            axisTick: {
                                show: false
                            },
                            axisLabel: {
                                show: false
                            },
                            splitArea: {
                                show: false
                            },
                            splitLine: {
                                show: false
                            },
                            data: data[0],
                        }],
                        yAxis: {
                            min: 10,
                            type: 'value',
                            axisTick: {
                                show: false
                            },
                            axisLine: {
                                show: true,
                                lineStyle: {
                                    color: 'rgba(255,255,255,0.2)',
                                }
                            },
                            splitLine: {
                                show: true,
                                lineStyle: {
                                    color: 'rgba(255,255,255,0.1)',
                                }
                            },
                            axisLabel: {
                                textStyle: {
                                    color: '#bac0c0',
                                    fontWeight: 'normal',
                                    fontSize: '12',
                                },
                                formatter: '{value}',
                            },
                        },
                        series: [{
                            type: 'bar',
                            itemStyle: {
                                normal: {
                                    show: true,
                                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                        offset: 0,
                                        color: '#FFE4C4'
                                    }, {
                                        offset: 1,
                                        color: '#FFA500'
                                    }]),
                                    barBorderRadius: 50,
                                    borderWidth: 0,
                                },
                                emphasis: {
                                    shadowBlur: 15,
                                    shadowColor: 'rgba(105,123, 214, 0.7)'
                                }
                            },
                            zlevel: 2,
                            barWidth: '20%',
                            data: data[1],
                        },
                            {
                                name: '',
                                type: 'bar',
                                xAxisIndex: 1,
                                zlevel: 1,
                                itemStyle: {
                                    normal: {
                                        color: 'transparent',
                                        borderWidth: 0,
                                        shadowBlur: {
                                            shadowColor: 'rgba(255,255,255,0.31)',
                                            shadowBlur: 10,
                                            shadowOffsetX: 0,
                                            shadowOffsetY: 2,
                                        },
                                    }
                                },
                                barWidth: '20%',
                                data: data[1]
                            }
                        ]
                    }


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
