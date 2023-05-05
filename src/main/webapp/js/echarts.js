function getContextPath() {

    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}

// $.ajax({
//     url: getContextPath() + '/tBusUser/getSexCharts',
//     type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
//     dataType: 'json',
//     success: function (data) {
//         console.log(data);
//     }
// });


$(function () {

    // echarts_chexingfenlei();// return  俩list

    echarts_pressSearchCus_day();//柱状图

    echarts_pressSearchCusToCus_day();//柱状图


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
            url: getContextPath() + '/tAppStatisticsCus/getEchartsStatisticsCusByDay?type=search',
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
            url: getContextPath() + '/tAppStatisticsCus/getEchartsRealStatisticsCusByDay?type=search',
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

