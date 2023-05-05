<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML>
<html>

<head>
    <title>项目计划</title>
    <jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
<div class="layui-tab">
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <jsp:include page="TAppProjectAppointSearch.jsp"></jsp:include>
        </div>
    </div>
</div>
<table class="layui-hide" id="table" lay-filter="table"></table>
<script>
    layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function () {
        var layer = layui.layer; //弹层
        var laydate = layui.laydate; //日期
        var laypage = layui.laypage; //分页
        var table = layui.table; //表格
        var carousel = layui.carousel; //轮播
        var upload = layui.upload; //上传
        var element = layui.element; //元素操作


        var index = layer.load(1);//开启进度条
        //绑定table
        table.render({
            elem: '#table',//table id
            url: '<%=request.getContextPath()%>/tAppProject/datagridWP',
            method: 'POST', //方式
            page: true,//是否开启分页
            limits: [10, 20, 30, 60, 90, 100],
            limit: 20, //默认采用20
            cellMinWidth: 120,
            even: true, //开启隔行背景
            id: 'searchID',
            height: 'full-200',
            done: function (res, curr, count) {
                //加载后回调
                layer.close(index);//关闭
                noAuthTip(res);//无权限提示
            },
            cols: [[ //标题栏
                {checkbox: true},
                {field: 'pname', title: '项目名称', align: 'center', sort: true},
                {field: 'pdetails', title: '项目描述', align: 'center', sort: true},
                {field: 'pmanhourplan', title: '工时预估', align: 'center', sort: true},
                {field: 'ptypeNm', title: '项目类型', align: 'center', templet: '#ptypeNmTpl'},
                {field: 'popercdNm', title: '发起人', align: 'center', sort: true},
                {field: 'pinscdNm', title: '发起人部门', align: 'center', sort: true},
                {field: 'pmanhour', title: '确认工时', align: 'center', sort: true},
                {field: 'pisappointNm', title: '是否委派', align: 'center', templet: '#pisappointNmTpl'},
                {field: 'piscompleteNm', title: '是否完成', align: 'center', templet: '#piscompleteNmTpl'},
                {field: 'pcreatedate', title: '创建时间', align: 'center', sort: true},
                {field: 'pcompletedate', title: '完成时间', align: 'center', sort: true},
                {fixed: 'right', title: '操作', width: 170, align: 'center', toolbar: '#toobar'}
            ]]
        });

        //监听工具条
        table.on('tool(table)', function (obj) { //注：tool是工具条事件名，table是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                , layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'detail') {
                openDetail(data);
            }
            if (layEvent === 'update') {
                openUpdate(data);
            }
        });

        //监听表格复选框选择
        table.on('checkbox(table)', function (data) {
            //layer.alert(JSON.stringify(data));
        });

        //打开新增按钮
        function openAdd() {
            layer.open({
                type: 2,
                title: '新增',
                shadeClose: false,//点击遮罩关闭
                anim: public_anim,
                btnAlign: 'c',
                shade: public_shade,//是否有遮罩，可以设置成false
                maxmin: true, //开启最大化最小化按钮
                //area: ['700px', '400px'],
                area: ['100%', '100%'],
                boolean: true,
                content: ['TAppProjectAdd.jsp', 'yes'], //iframe的url，no代表不显示滚动条
                success: function (layero, lockIndex) {
                    var body = layer.getChildFrame('body', lockIndex);
                    //绑定解锁按钮的点击事件
                    body.find('button#close').on('click', function () {
                        layer.close(lockIndex);
                        location.reload();//刷新
                    });
                }
            });
        }



        //修改按钮
        function openUpdate(data) {
            if (data.pisappoint == 0) {

            }
            layer.open({
                type: 2,
                title: '修改',
                shadeClose: false,//点击遮罩关闭
                anim: public_anim,
                btnAlign: 'c',
                shade: public_shade,//是否有遮罩，可以设置成false
                maxmin: true, //开启最大化最小化按钮
                area: ['100%', '100%'],
                boolean: true,
                content: ['TAppProjectAppointEdit.jsp?obj=' + encodeURIComponent(JSON.stringify(data)), 'yes'], //iframe的url，no代表不显示滚动条
                success: function (layero, lockIndex) {
                    //alert(JSON.stringify(data));
                    var body = layer.getChildFrame('body', lockIndex);
                    //绑定解锁按钮的点击事件
                    body.find('button#close').on('click', function () {
                        layer.close(lockIndex);
                        //location.reload();//刷新
                        $('#btnSearch').click();
                    });
                    pubUtil.load(body, data);//填充表单
                    //body.find("select,:radio,:checkbox").attr("disabled", "disabled");
                }
            });
        }

        //打开查看按钮
        function openDetail(data) {
            layer.open({
                type: 2,
                title: '查看',
                shadeClose: false,//点击遮罩关闭
                anim: public_anim,
                btnAlign: 'c',
                shade: public_shade,//是否有遮罩，可以设置成false
                maxmin: true, //开启最大化最小化按钮
                //area: ['700px', '400px'],
                area: ['100%', '100%'],
                boolean: true,
                content: ['TAppProjectAppointDetail.jsp?obj=' + encodeURIComponent(JSON.stringify(data)), 'yes'], //iframe的url，no代表不显示滚动条
                success: function (layero, lockIndex) {
                    var body = layer.getChildFrame('body', lockIndex);
                    //绑定解锁按钮的点击事件
                    body.find('button#close').on('click', function () {
                        layer.close(lockIndex);
                        //location.reload();//刷新
                    });

                    pubUtil.load(body, data);//填充表单
                    body.find("input").attr("readonly", "readonly");
                }
            });
        }

        //查询按钮
        $('#btnSearch').on('click', function () {
            index = layer.load(1);//开启进度条
            var searchform = pubUtil.serializeObject($("#searchform"));//查询页面表单ID
            //alert(JSON.stringify(searchform));
            table.reload('searchID', {
                where: searchform
            });
        });

        //重置按钮
        $('#btnRetSet').on('click', function () {
            index = layer.load(1);//开启进度条
            table.reload('searchID', {
                where: ""
            });
        });

        var active = {
            btnDelAll: function () { //批量删除
                var checkStatus = table.checkStatus('searchID');
                var data = checkStatus.data;
                //layer.alert(JSON.stringify(data));
                if (data.length == 0) {
                    pubUtil.msg("请至少选择一条要删除的记录", layer, 2, function () {
                    });
                    return;
                }
                var ids = '';
                for (var i = 0; i < data.length; i++) {
                    if (i != (data.length - 1)) {
                        ids += data[i].id + ",";
                    } else {
                        ids += data[i].id;
                    }
                }
                openDelete(ids);
            }
            , btnAdd: function () { //新增操作
                openAdd();
            }
        };

        $('.layui-form-pane .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

    });

</script>
<script type="text/html" id="toobar">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-xs "lay-event="update">委派</a>
</script>
<script type="text/html" id="ptypeNmTpl">
    <span class="layui-badge  {{ d.ptype == 0 ? 'layui-bg-green' : '' }} ">{{d.ptypeNm}}</span>
</script>
<script type="text/html" id="pisappointNmTpl">
    <span class="layui-badge  {{ d.pisappoint == 0 ? 'layui-bg-green' : '' }} ">{{d.pisappointNm}}</span>
</script>
<script type="text/html" id="piscompleteNmTpl">
    <span class="layui-badge  {{ d.piscomplete == 0 ? 'layui-bg-green' : '' }} ">{{d.piscompleteNm}}</span>
</script>
</body>
</html>













