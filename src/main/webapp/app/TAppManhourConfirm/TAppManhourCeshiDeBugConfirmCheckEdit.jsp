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
            <label class="layui-form-label">项目ID</label>
            <div class="layui-input-block">
                <input type="text" name="mprojectidNm" placeholder="无" readonly autocomplete="off" class="layui-input"/>
                <input type="hidden" name="mprojectid"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">项目里程碑ID</label>
            <div class="layui-input-block">
                <input type="text" name="mprojectplanidNm" placeholder="无" readonly autocomplete="off"
                       class="layui-input"/>
                <input type="hidden" name="mprojectplanid"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">人员id</label>
            <div class="layui-input-block">
                <input type="text" name="mopercdNm" placeholder="无" readonly autocomplete="off" class="layui-input"/>
                <input type="hidden" name="mopercd"/>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-sm12">
                <label class="layui-form-label">工作内容</label>
                <div class="layui-input-block">
                    <textarea name="mworkdetails" maxlength="1000" placeholder="请输入" readonly
                              class="layui-textarea"></textarea>
                </div>
            </div>
        </div>
        <%--        <div class="layui-inline">--%>
        <%--            <label class="layui-form-label">工作内容</label>--%>
        <%--            <div class="layui-input-block">--%>
        <%--                <input type="text" name="mworkdetails" placeholder="无" readonly autocomplete="off" class="layui-input"/>--%>
        <%--            </div>--%>
        <%--        </div>--%>
        <div class="layui-inline">
            <label class="layui-form-label">工时</label>
            <div class="layui-input-block">
                <input type="text" name="mmanhour" placeholder="无" readonly autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">改Bug工时</label>
            <div class="layui-input-block">
                <input type="text" name="debugFinishDate" placeholder="无"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">审核状态</label>
            <div class="layui-input-block">
                <input type="text" name="mcheckstatusNm" placeholder="无" readonly autocomplete="off"
                       class="layui-input"/>
                <input type="hidden" name="mcheckstatus"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">创建时间</label>
            <div class="layui-input-block">
                <input type="text" name="mcreatedate" placeholder="无" readonly autocomplete="off" class="layui-input"/>
            </div>
        </div>

        <div class="layui-inline">
            <label class="layui-form-label">缺陷标题</label>
            <div class="layui-input-block">
                <input type="text" name="mremark" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">严重程度</label>
            <div class="layui-input-block">
                <%--                <input type="text" name="debugLeave" placeholder="无"  autocomplete="off" class="layui-input"/>--%>
                <select id="mySelect" name="debugLeave" placeholder="debugLeave" autocomplete="off" class="layui-input">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">缺陷号顺序</label>
            <div class="layui-input-block">
                <input type="text" name="debugID" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-sm12">
                <label class="layui-form-label">测试1意见</label>
                <div class="layui-input-block">
                    <textarea name="debugContent" maxlength="1000" placeholder="请输入" class="layui-textarea"></textarea>
                </div>
            </div>
        </div>
    </div>


    <!-- 按钮组 -->
    <div class="layui-form-item">
        <div class="layui-input-block">
            <%--            <button class="layui-btn" lay-submit="" lay-filter="okSubmit">同意</button>--%>
            <button class="layui-btn layui-btn-danger" lay-submit="" lay-filter="notokSubmit">打回提交</button>
            <button class="layui-btn layui-btn-danger" lay-submit="" lay-filter="SubmitList">提交</button>
            <button class="layui-btn layui-btn-primary" id="close">关闭</button>
        </div>
    </div>
</form>
<table id="demo" lay-filter="test"></table>

<script>

    layui.use(['form', 'layedit', 'laydate', 'jquery', 'table'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laydate = layui.laydate;


        //时间控件
        laydate.render({
            elem: '#mcreatedate'
            , type: 'datetime'
            , format: 'yyyy-MM-dd HH:mm:dd'
        });
        //时间控件
        laydate.render({
            elem: '#mcheckdate'
            , type: 'datetime'
            , format: 'yyyy-MM-dd HH:mm:dd'
        });
        //获取传参
        var data = JSON.parse(decodeURIComponent(getRequestParam().obj));

        //监听提交
        form.on('submit(okSubmit)', function (data) {
            // layer.msg(JSON.stringify(data.field));
            var index = layer.load(1);//开启进度条
            $.ajax({
                url: '<%=request.getContextPath()%>/tAppManhourConfirm/modifyCeShiShenHeDetail?type=okSubmit',
                data: data.field,
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

        form.on('submit(notokSubmit)', function (data) {
            // layer.msg(JSON.stringify(data.field));
            var index = layer.load(1);//开启进度条
            $.ajax({
                url: '<%=request.getContextPath()%>/tAppManhourConfirm/modifyCeShiShenHeDetail?type=dahuitijiao',
                data: data.field,
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
        form.on('submit(SubmitList)', function (data) {
            // layer.msg(JSON.stringify(data.field));
            var index = layer.load(1);//开启进度条
            $.ajax({
                url: '<%=request.getContextPath()%>/tAppManhourConfirm/modifyCeShiShenHeDetail?type=SubmitList',
                data: data.field,
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

        var table = layui.table;

        //第一个实例
        table.render({
            elem: '#demo',
            height: 312,
            url: '<%=request.getContextPath()%>/tAppDebugDetail/datagrid' , //数据接口
            page: true ,//开启分页
            method: 'POST', //方式
            page: true,//是否开启分页
            limits: [10, 20, 30, 60, 90, 100],
            limit: 20, //默认采用20
            cellMinWidth: 120,
            even: true, //开启隔行背景
            // id: 'searchID',
            height: 'full-200',
            done: function (res, curr, count) {
                //加载后回调
                layer.close(index);//关闭
                noAuthTip(res);//无权限提示
            },
            cols: [[ //表头
                // {field: 'id', title: 'ID', width: 80, sort: true, fixed: 'left'},
                 {field: 'mprojectidNm', title: '项目名称', width: 80}
                , {field: 'mprojectplanidNm', title: '项目里程碑', width: 80, sort: true}
                , {field: 'mopercd', title: '人员ID', width: 80}
                // , {field: 'mworkdetails', title: '工作内容', width: 177}
                , {field: 'mmanhour', title: '工时', width: 80, sort: true}
                , {field: 'debugFinishDate', title: '改Bug工时', width: 80, sort: true}
                // , {field: 'mcheckstatusNm', title: '审核状态', width: 80}
                , {field: 'mcreatedate', title: '创建时间', width: 135, sort: true}
                , {field: 'mremark', title: '缺陷标题', width: 135, sort: true}
                , {field: 'debugLeave', title: '缺陷程度', width: 135, sort: true}
                , {field: 'debugID', title: '缺陷号顺序', width: 135, sort: true}
                , {field: 'debugContent', title: '测试意见', width: 135, sort: true}
            ]]
        });
    });
</script>
</body>
</html>

