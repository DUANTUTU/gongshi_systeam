function getContextPath() {

    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}


$(function () {
    //各部门委派任务占比
    echarts_dept_appoint_pro();
    //各部门完成任务占比
    echarts_dept_complete_pro();
    //公司人员工时统计
    echarts_person_manhour();
    //人员参与项目数量
    echarts_person_project();
    //部门任务数量总览
    echarts_dept_project();

    //部门任务数量总览
    function echarts_dept_project(){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echarts_dept_project'));
        $.ajax({
            url: getContextPath()+'/tAppProject/getProjectDeptEcharts',
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            dataType: 'json',
            success: function (data) {
                console.log(data);
                option = {
                    color: ['#e5323e', '#006699', '#4cabce' ],
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: ['委派任务总数','完成任务数量','未完成任务数量']
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
    function echarts_person_project(){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echarts_person_project'));
        $.ajax({
            url: getContextPath()+'/tAppProjectPerson/getPersonProjectEcharts',
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            dataType: 'json',
            success: function (data) {
                console.log(data);
                option = {
                    color: ['#e5323e', '#006699', '#4cabce' ],
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

    //公司人员工时统计
    function echarts_person_manhour(){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echarts_person_manhour'));
        $.ajax({
            url: getContextPath()+'/tAppDayPaper/getPersonManhourEcharts',
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            dataType: 'json',
            success: function (data) {
                console.log(data);
                option = {
                    color: ['#e5323e', '#006699', '#4cabce' ],
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

    //各部门委派任务占比
    function echarts_dept_appoint_pro() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echarts_dept_appoint_pro'));
        $.ajax({
            url: getContextPath()+'/tAppProject/getProjectDeptProportionEcharts',
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            dataType: 'json',
            success: function (data) {
                console.log(data);
                option = {

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
    function echarts_dept_complete_pro() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('echarts_dept_complete_pro'));
        $.ajax({
            url: getContextPath()+'/tAppProject/getProjectDeptCompleteEcharts',
            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
            dataType: 'json',
            success: function (data) {
                console.log(data);
                option = {

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