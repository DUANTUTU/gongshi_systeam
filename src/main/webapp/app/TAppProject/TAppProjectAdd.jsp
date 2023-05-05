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
    <title>新增</title>
    <jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
<form name="form" class="layui-form" style="margin-top: 20px;" method="post" action="">

    <div class="layui-form-item">

        <h2>项目基本信息:</h2>

        <div class="layui-inline">
            <label class="layui-form-label" style="color:#F00">项目名称</label>
            <div class="layui-input-block">
                <input type="text" name="pname" maxlength="500" lay-verify="required" placeholder="请输入"
                       autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" style="color:#F00">项目描述</label>
            <div class="layui-input-block">
                <input type="text" name="pdetails" maxlength="1000" placeholder="请输入" autocomplete="off"
                       class="layui-input" lay-verify="required"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" style="color:#F00">工时预估</label>
            <div class="layui-input-block">
                <input type="text" name="pmanhourplan" maxlength="5" lay-verify="required" placeholder="请输入"
                       autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" style="color:#F00">项目类型</label>
            <div class="layui-input-block">
                <select name="ptype" id="ptype" lay-verify="required"></select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">

        <h2>设置项目里程碑信息:</h2>

        <div class="layui-inline">
            <label class="layui-form-label">里程碑名称</label>
            <div class="layui-input-block">
                <input type="text" name="pplanname" id="pplanname" maxlength="500" placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">工时预估</label>
            <div class="layui-input-block">
                <input type="text" name="pplanmanhour" id="pplanmanhour" maxlength="5" placeholder="请输入"
                       autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">里程碑顺序</label>
            <div class="layui-input-block">
                <input type="text" name="pplanorder" id="pplanorder" maxlength="5" placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <input type="button" id="addTable" style="width: 100px;" value="添加里程碑"></input>
        </div>
        <table class="layui-hide" id="table" lay-filter="table"></table>
    </div>


    <!-- 按钮组 -->
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="btnSubmit">立即提交</button>
            <button class="layui-btn layui-btn-primary" id="close">关闭</button>
        </div>
    </div>
</form>
<script>
    var oldData;
    layui.use(['form', 'layedit', 'table', 'laydate', 'jquery'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laydate = layui.laydate;
        var table = layui.table; //表格
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

        //项目类型下拉菜单
        pbInitCombox($, form, 'dictTypeCd=project_type', 'ptype');//project_type为数据字典类型编号
        //是否委派下拉菜单
        pbInitCombox($, form, 'dictTypeCd=is_appoint', 'pisappoint');//is_appoint为数据字典类型编号
        //是否完成下拉菜单
        pbInitCombox($, form, 'dictTypeCd=is_complete', 'piscomplete');//is_complete为数据字典类型编号


        //监听提交
        form.on('submit(btnSubmit)', function (data) {
            // layer.msg(JSON.stringify(data.field));
            var index = layer.load(1);//开启进度条
            debugger
            if (data.field.ptype == '0') {
                if (JSON.stringify(table.cache["searchID"]) == undefined || null == JSON.stringify(table.cache["searchID"]) || "" == JSON.stringify(table.cache["searchID"])) {
                    pubUtil.msg("主线任务类型项目里程碑必填", layer, 2, function () {
                    }, 5 * 1000);
                }
            }
            $.ajax({
                url: '<%=request.getContextPath()%>/tAppProject/add',
                data: {
                    tAppProject: JSON.stringify(data.field),
                    tAppProjectPlans: JSON.stringify(table.cache["searchID"])
                },
                type: 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
                dataType: 'json',
                success: function (obj) {
                    layer.close(index);//关闭
                    if (obj.success) {
                        pubUtil.msg(obj.msg, layer, 1, function () {
                            $("#close").click();
                        }, 500);
                    } else {
                        pubUtil.msg(obj.msg, layer, 2, function () {

                        }, 5 * 1000);
                    }
                }
            });
            return false;
        });
        var options = {
            elem: '#table',//table id
            cellMinWidth: 100,
            even: true, //开启隔行背景
            height: 200,
            id: 'searchID',
            cols: [[ //标题栏
                {checkbox: true},
                {field: 'pplanname', title: '里程碑名称', align: 'center', sort: true},
                {field: 'pplanmanhour', title: '里程碑工时预估', align: 'center', sort: true},
                {field: 'pplanorder', title: '里程碑顺序', align: 'center', sort: true},
                {fixed: 'right', title: '操作', align: 'center', toolbar: '#toobar'}
            ]]
        };
        table.render(options);

        //监听工具条
        table.on('tool(table)', function (obj) { //注：tool是工具条事件名，table是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                , layEvent = obj.event; //获得 lay-event 对应的值
            debugger
            if (layEvent === 'del') {
                oldData = table.cache["searchID"];
                // if (obj.tr.data('index') != 0) {
                oldData.splice(obj.tr.data('index'), 1);
                table.render(options);
                table.reload("searchID", {data: oldData});
                // }
                // oldData.splice(obj.tr.index(), 1);
                // obj.del();
                // table.reload('table', {data: oldData});
            }
        });
        $("#addTable").click(function () {
            oldData = table.cache["searchID"];
            if (oldData == null || oldData == undefined) {
                oldData = [];
            }
            if ("" == $("#pplanname").val() || $("#pplanmanhour").val() == "" || $("#pplanorder").val() == "") {
                return;
            }
            var data1 = {
                "pplanname": $("#pplanname").val(),
                "pplanmanhour": $("#pplanmanhour").val(),
                "pplanorder": $("#pplanorder").val()
            };
            oldData.push(data1);
            table.reload('searchID', {
                data: oldData
            });
            $("#pplanorder").val("");
            $("#pplanmanhour").val("");
            $("#pplanname").val("");
        })

    });


</script>
<script type="text/html" id="toobar">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
</body>
</html>

