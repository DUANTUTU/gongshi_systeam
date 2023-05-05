<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>修改</title>
    <jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
<form name="form" class="layui-form" style="margin-top: 20px;" method="post" action="">

    <div class="layui-form-item">
        <input type="hidden" name="id"/>
        <div class="layui-inline">
            <label class="layui-form-label">项目名称</label>
            <div class="layui-input-block">
                <input type="text" name="pname" readonly maxlength="500" placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">项目描述</label>
            <div class="layui-input-block">
                <input type="text" name="pdetails" readonly maxlength="1000" placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">工时预估</label>
            <div class="layui-input-block">
                <input type="text" name="pmanhourplan" readonly maxlength="2" placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">项目类型</label>
            <div class="layui-input-block">
                <input type="text" name="ptypeNm" readonly maxlength="2" placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">发起人</label>
            <div class="layui-input-block">
                <input type="hidden" name="popercd"/>
                <input type="text" name="popercdNm" maxlength="30" readonly placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">发起人部门</label>
            <div class="layui-input-block">
                <input type="hidden" name="pinscd"/>
                <input type="text" name="pinscdNm" maxlength="36" readonly placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>

    </div>
    <div class="layui-form-item">

        <h2>项目里程碑信息:</h2>
        <table class="layui-hide" id="table" lay-filter="table"></table>
    </div>
    <div class="layui-form-item">

        <div class="layui-inline">
            <h2>委派:</h2>
            <button class="layui-btn layui-btn-normal" type="button" id="treeSelect">请选择</button>
        </div>
        <table class="layui-hide" id="tablePerson" lay-filter="table"></table>
    </div>

    <!-- 按钮组 -->
    <div class="layui-form-item">
        <div class="layui-input-block">
            <%--<button class="layui-btn" lay-submit="" lay-filter="btnSubmit">立即提交</button>--%>
            <button class="layui-btn layui-btn-primary" id="close">关闭</button>
        </div>
    </div>
</form>
<script>
    layui.use(['form', 'layedit', 'table', 'laydate', 'jquery'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laydate = layui.laydate;
        var table = layui.table;


        //时间控件
        laydate.render({
            elem: '#pcreatedate'
            , type: 'datetime'
            , format: 'yyyy-MM-dd HH:mm:dd'
        });
        //时间控件
        laydate.render({
            elem: '#pcompletedate'
            , type: 'datetime'
            , format: 'yyyy-MM-dd HH:mm:dd'
        });
        //获取传参
        var data = JSON.parse(decodeURIComponent(getRequestParam().obj));
        //项目里程碑
        var tablerender = {
            elem: '#table',//table id
            url: '<%=request.getContextPath()%>/tAppProjectPlan/datagrid?pprojectid=' + data.id,
            method: 'POST', //方式
            cellMinWidth: 100,
            even: true, //开启隔行背景
            id: 'searchID',
            height: '200',
            cols: [[ //标题栏
                {checkbox: true},
                {field: 'pplanname', title: '里程碑名称', align: 'center', sort: true},
                {field: 'pplanmanhour', title: '里程碑工时预估', align: 'center', sort: true},
                {field: 'pplanorder', title: '里程碑顺序', align: 'center', sort: true},
                {field: 'piscompleteNm', title: '是否完成', align: 'center', templet: '#piscompleteNmTpl'},
                {field: 'psummanhour', title: '确认工时', align: 'center', sort: true},
                {field: 'pcreatedate', title: '创建时间', align: 'center', sort: true},
                {field: 'pcompletedate', title: '完成时间', align: 'center', sort: true}
                // {fixed: 'right', title: '操作', align: 'center', toolbar: '#toobar'}
            ]]
        }

        //委派人情况
        var tablePersonRender = {
            elem: '#tablePerson',//table id
            url: '<%=request.getContextPath()%>/tAppProjectPerson/datagrid?pprojectid=' + data.id,
            method: 'POST', //方式
            cellMinWidth: 100,
            even: true, //开启隔行背景
            id: 'searchID',
            height: '200',
            cols: [[ //标题栏
                {checkbox: true},
                {field: 'popercdNm', title: '委派人', align: 'center', sort: true},
                {field: 'pinscdNm', title: '委派人部门', align: 'center', sort: true},
                {field: 'pappointstatusNm', title: '委派状态', align: 'center',templet: '#pappointstatusNmTpl'},
                {field: 'pcreatedate', title: '创建时间', align: 'center', sort: true},
                {field: 'pconfirmdate', title: '确认时间', align: 'center', sort: true},
                {fixed: 'right', title: '操作', align: 'center', toolbar: '#toobar'}
            ]]
        }
        table.render(tablerender);
        table.render(tablePersonRender);
//监听工具条
        table.on('tool(table)', function (obj) { //注：tool是工具条事件名，table是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                , layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'del') {
                delPerson(obj.data.id)
            }
        });

        function delPerson(id) {
            layer.open({
                title: '确认删除' //显示标题栏
                , closeBtn: false
                , area: '300px;'
                , shade: 0.5
                , id: 'LAY_layuipro' //设定一个id，防止重复弹出
                , btn: ['删除', '关闭']
                , content: '您是否要删除当前选中的记录？'
                , success: function (layero) {
                    var btn = layero.find('.layui-layer-btn');
                    btn.css('text-align', 'center');//居中
                    btn.find('.layui-layer-btn0').on('click', function () {
                        var loadindex = layer.load(1);//开启进度条
                        $.ajax({
                            url: '<%=request.getContextPath()%>/tAppProjectPerson/remove',
                            data: {
                                id: id
                            },
                            type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
                            dataType:
                                'json',
                            success:

                                function (r) {
                                    layer.close(loadindex);//关闭进程对话框
                                    if (r.success) {
                                        pubUtil.msg(r.msg, layer, 1, function () {
                                            location.reload();//刷新
                                        }, 500);
                                    } else {
                                        pubUtil.msg(r.msg, layer, 2, function () {

                                        }, 5 * 1000);
                                    }
                                }
                        })
                        ;
                    });
                }
            });
        }

        //按钮的点击事件
        $('button#treeSelect').on('click', function() {
            layer.open({
                type: 2,
                title: '授权',
                shadeClose: false,//点击遮罩关闭
                anim: public_anim,
                btnAlign: 'c',
                shade: public_shade,//是否有遮罩，可以设置成false
                maxmin: true, //开启最大化最小化按钮
                area: ['700px', '400px'],
                //area : [ '100%', '100%' ],
                boolean: true,
                content: ['TAppProjectAppointTree.jsp?obj=' + encodeURIComponent(JSON.stringify(data)), 'yes'], //iframe的url，no代表不显示滚动条
                btn: ['授权', '重置', '关闭'],
                yes: function (index, layero) {
                    var body = layer.getChildFrame('body', index);
                    //绑定解锁按钮的点击事件
                    body.find('button#btnSubmit').click();
                    table.render(tablePersonRender);
                    return false;
                },
                btn2: function (index, layero) {
                    var body = layer.getChildFrame('body', index);
                    //绑定解锁按钮的点击事件
                    body.find('button#reSet').click();
                    return false;
                },
                btn3: function () {
                    layer.closeAll();
                },
                success: function (layero, lockIndex) {
                    var body = layer.getChildFrame('body', lockIndex);
                    //绑定解锁按钮的点击事件
                    body.find('button#close').on('click', function () {
                        layer.close(lockIndex);
                        location.reload();//刷新
                    });
                }
            });
        });

    });


</script>
<script type="text/html" id="toobar">
    <a class="layui-btn layui-btn-danger layui-btn-xs {{ d.pappointstatus == 0 ? 'layui-btn-disabled' : ''  }}"lay-event="{{ d.pappointstatus == 0 ? '' : 'del' }}" >删除</a>
</script>
<script type="text/html" id="pappointstatusNmTpl">
    <span class="layui-badge  {{ d.pappointstatus == 0 ? 'layui-bg-green' : '' }} ">{{d.pappointstatusNm}}</span>
</script>
</body>
</html>

