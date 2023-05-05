<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<head>
    <meta charset="utf-8">
    <title>工时系统</title>
    <jsp:include page="inc.jsp"></jsp:include>
    <%--<script src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>--%>
    <%--<script>--%>
    <%--$(window).load(function () {--%>
    <%--$(".loading").fadeOut()--%>
    <%--})--%>
    <%--</script>--%>

    <%--<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">--%>
    <%--<link rel="stylesheet" href="<%=request.getContextPath()%>/css/map.css">--%>
</head>

<body>
<html>
<%--<div class="loading">--%>
<%--<div class="loadbox"><img src="<%=request.getContextPath()%>/picture/loading.gif"> 页面加载中...</div>--%>
<%--</div>--%>
<style type="text/css">
    h1 {
        font-style: normal;
        font-size: 30px;
        font-weight: 300;
        color: #01aaed;
    }

    .layui-card {
        background-color: #f2f2f2;
    }

    h1 .layui-icon {
        font-size: 46px;
    }

</style>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-sm4 layui-col-md3">
            <div class="layui-card" style="background: rgba(191, 191, 191, 0.36)">
                <div class="layui-card-header">
                    项目计划
                    <span class="layui-badge layui-bg-blue layuiadmin-badge">个</span>
                </div>
                <div class="layui-card-body layuiadmin-card-list">
                    <%--  <p class="layuiadmin-big-font"></p>--%>
                    <p class="layuiadmin-big-font">
                    <h1><i class="layui-icon"></i>&nbsp;&nbsp;
                        <home-para id="all_pro_num"></home-para>
                    </h1>
                    </p>
                    <p>
                        项目总数
                        <span class="layuiadmin-span-color"> 	<home-para id="visit_total"></home-para> <i
                                class="layui-inline layui-icon layui-icon-flag"></i></span>
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-sm4 layui-col-md3">
            <div class="layui-card" style="background: rgba(191, 191, 191, 0.36)">
                <div class="layui-card-header">
                    项目委派
                    <span class="layui-badge layui-bg-blue layuiadmin-badge">个</span>
                </div>
                <div class="layui-card-body layuiadmin-card-list">

                    <p class="layuiadmin-big-font">
                    <h1><i class="layui-icon"></i>&nbsp;&nbsp;
                        <home-para id="appoint_pro_num"></home-para>
                    </h1>
                    </p>

                    <p>
                        已委派项目总数
                        <span class="layuiadmin-span-color">
							<home-para id="user_total_num"></home-para>
							<i class="layui-inline layui-icon layui-icon-face-smile-b"></i>
						</span>
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-sm4 layui-col-md3">
            <div class="layui-card" style="background: rgba(191, 191, 191, 0.36)">
                <div class="layui-card-header">
                    提报工时
                    <span class="layui-badge layui-bg-green layuiadmin-badge">工时</span>
                </div>
                <div class="layui-card-body layuiadmin-card-list">

                    <p class="layuiadmin-big-font">
                    <h1><i class="layui-icon"></i>&nbsp;&nbsp;
                        <home-para id="manhour_confirm_num"></home-para>
                    </h1>
                    </p>
                    <p>
                        提报工时总数
                        <span class="layuiadmin-span-color">  <home-para id="tra_total_num"></home-para> <i
                                class="layui-inline layui-icon layui-icon-dollar"></i></span>
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-sm4 layui-col-md3">
            <div class="layui-card" style="background: rgba(191, 191, 191, 0.36)">
                <div class="layui-card-header">
                    人员数量
                    <span class="layui-badge layui-bg-green layuiadmin-badge">人</span>
                </div>
                <div class="layui-card-body layuiadmin-card-list">
                    <p class="layuiadmin-big-font">
                    <h1>
                        <i class="layui-icon"></i>&nbsp;&nbsp;
                        <home-para id="oper_num"></home-para>
                    </h1>

                    </p>

                    <p>
                        人员总数
                        <span class="layuiadmin-span-color">  <home-para id="tra_total_num"></home-para> <i
                                class="layui-inline layui-icon layui-icon-dollar"></i></span>
                    </p>
                </div>
            </div>
        </div>

        <div class="layui-col-sm6">
            <div class="layui-card">
                <div class="layui-card-body">
                    <div id="echarts_person_project" style="width: 100%;height:450px;margin:0 auto"
                         class="charts"></div>
                    <%--<div id="equNetworkHeart_today" style="width: 100%;height:400px;margin:0 auto" class="charts"></div>--%>
                </div>
            </div>
        </div>

<%--        <div class="layui-col-sm4">--%>
<%--            <div class="layui-card">--%>
<%--                <div class="layui-card-body">--%>

<%--                    <div id="echarts_dept_project" style="width: 100%;height:400px;margin:0 auto" class="charts"></div>--%>
<%--                    &lt;%&ndash;<div id="equRunDituation" style="width: 100%;height:400px;margin:0 auto" class="charts"></div>&ndash;%&gt;--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>

        <div class="layui-col-sm6">
            <div class="layui-card">
                <div class="layui-card-body">
                    <div id="echarts_project_manhour" style="width: 100%;height:450px;margin:0 auto"
                         class="charts"></div>
                    <%--<div id="equRunDituation" style="width: 100%;height:400px;margin:0 auto" class="charts"></div>--%>
                </div>
            </div>
        </div>
        <div class="layui-col-sm12">
            <div class="layui-card">
                <div class="layui-card-body">
                    <div id="echarts_account_7day_manhour" style="width: 100%;height:400px;margin:0 auto"
                         class="charts"></div>
                    <%--<div id="equRunDituation" style="width: 100%;height:400px;margin:0 auto" class="charts"></div>--%>
                </div>
            </div>
        </div>
    </div>

</div>


</html>
</body>
<script src="<%=request.getContextPath()%>/js/echarts.min.js"></script>
<%--<script src="<%=request.getContextPath()%>/js/china.js"></script>--%>
<%--<script src="<%=request.getContextPath()%>/js/echartsBigData.js"></script>--%>
<script>
    //项目计划总数
    $(function () {
        $.ajax({
            url: '<%=request.getContextPath()%>/tAppProject/datagridWecomePage',
            data: {},//
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            dataType: 'json',
            success: function (obj) {
                $("#all_pro_num").html(obj.count);//当年访问数量
            }
        });

    })
    //项目委派总数
    $(function () {
        $.ajax({
            url: '<%=request.getContextPath()%>/tAppProject/datagridWecomePage',
            data: {
                pisappoint: "1"
            },//
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            dataType: 'json',
            success: function (obj) {
                $("#appoint_pro_num").html(obj.count);//当年访问数量
            }
        });

    })

    //工时提报总数
    $(function () {
        $.ajax({
            url: '<%=request.getContextPath()%>/tAppManhourConfirm/queryAllManhourConfirm',
            data: {},//
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            dataType: 'json',
            success: function (obj) {
                $("#manhour_confirm_num").html(obj);//当年访问数量
            }
        });

    })


    //工时提报总数
    $(function () {
        $.ajax({
            url: '<%=request.getContextPath()%>/operInf/queryAllOperNum',
            data: {},//
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            dataType: 'json',
            success: function (obj) {
                $("#oper_num").html(obj.count - 1);//当年访问数量
            }
        });

    })

    $(function () {
        //部门任务数量总览  暂时不用
        // echarts_dept_project();
        //人员参与项目统计
        echarts_person_project();
        //各部门委派任务占比  暂时不用
        // echarts_dept_appoint_pro();
        //各项目预估工时占比
        echarts_project_manhour();
        //人员七日内工时总计
        echarts_account_7day_manhour();
    })
</script>
<script type="text/javascript">
    // layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload',
    //     'element'], function () {
    //
    // });

    //人员七日内工时总计
    function echarts_account_7day_manhour() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echarts_account_7day_manhour'));
        $.ajax({
            url: '<%=request.getContextPath()%>/tAppManhourConfirm/echartsAccount7DayManhour',
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            dataType: 'json',
            success: function (data) {
                console.log(data);
                option = {
                    color: ['#e5323e', '#006699', '#4cabce'],
                    title: {
                        text: '人员7日工时统计'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: ['提报工时', '审核通过工时'],
                        y: 30
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
                            data: data.accountList
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '提报工时',
                            type: 'bar',
                            barGap: 0,
                            // label: labelOption,
                            data: data.manhourList
                        },
                        {
                            name: '审核通过工时',
                            type: 'bar',
                            // label: labelOption,
                            data: data.checkManhourList
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
    function echarts_dept_project() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echarts_dept_project'));
        $.ajax({
            url: '<%=request.getContextPath()%>/tAppProject/getProjectDeptEcharts',
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            dataType: 'json',
            success: function (data) {
                console.log(data);
                option = {
                    color: ['#e5323e', '#006699', '#4cabce'],
                    title: {
                        text: '部门任务数量总览'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: ['委派任务总数', '完成任务数量', '未完成任务数量'],
                        y: 30
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

    //人员参与项目统计
    function echarts_person_project() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echarts_person_project'));
        $.ajax({
            url: '<%=request.getContextPath()%>/tAppProjectPerson/getPersonProjectEcharts',
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            dataType: 'json',
            success: function (data) {
                console.log(data);
                option = {
                    color: ['#e5323e', '#006699', '#4cabce'],
                    title: {
                        text: '人员参与项目统计'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: ['总任务数量', '主线任务数量', '临时任务数量'],
                        y: 30
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

    //各部门委派任务占比
    function echarts_dept_appoint_pro() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echarts_dept_appoint_pro'));
        $.ajax({
            url: '<%=request.getContextPath()%>/tAppProject/getProjectDeptProportionEcharts',
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            dataType: 'json',
            success: function (data) {
                console.log(data);
                option = {
                    title: {
                        text: '各部门委派任务占比'
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
</script>
