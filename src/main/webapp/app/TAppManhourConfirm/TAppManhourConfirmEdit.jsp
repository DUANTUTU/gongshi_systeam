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
<%--            <label class="layui-form-label">项目ID</label>--%>
            <div class="layui-input-block">

<%--                <input type="text"  name="mprojectid" maxlength="5"  readonly="true" autocomplete="off"--%>
<%--                       class="layui-input"/>--%>
            </div>
        </div>
        <div class="layui-inline">
<%--            <label class="layui-form-label">项目里程碑ID</label>--%>
            <div class="layui-input-block">
<%--                <select name="mprojectplanid" id="mprojectplanid"></select>--%>
        <input type="hidden"  name="mprojectplanid" maxlength="5"  readonly="true" autocomplete="off"
                       class="layui-input"/>
        <input type="hidden"  name="mcheckstatus" maxlength="5"  readonly="true" autocomplete="off" value="0"
               class="layui-input"/>
        <input type="hidden"  name="mopercd" maxlength="5"  readonly="true" autocomplete="off"
               class="layui-input"/>
    <input type="hidden"  name="mprojectid" maxlength="5"  readonly="true" autocomplete="off"
           class="layui-input"/>
    <input type="hidden" name="mconfirmId" maxlength="5"  readonly="true" autocomplete="off">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">工时</label>
            <div class="layui-input-block">
                <input type="text" name="mmanhour" maxlength="5" placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-sm12">
                <label class="layui-form-label">工作内容</label>
                <div class="layui-input-block">
                    <textarea name="mworkdetails" maxlength="1000" placeholder="请输入" class="layui-textarea"></textarea>
                </div>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">人员id</label>
            <div class="layui-input-block">
                <input type="text" name="mopercdNm" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">拒绝理由</label>
            <div class="layui-input-block">
                <input type="text" name="mremark" placeholder="无" readonly autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">改bug工时</label>
            <div class="layui-input-block">
                <input type="text" name="debugFinishDate" placeholder="无" autocomplete="off" class="layui-input"/>
               

            </div>

        </div>

    </div>

    <!-- 按钮组 -->
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="btnSubmit">4修改完成提交测试</button>
            <button class="layui-btn layui-btn-primary" id="close">关闭</button>
        </div>
    </div>
</form>
<script>
    layui.use(['form', 'layedit', 'laydate', 'jquery'], function () {
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

        form.on('select(project)', function (data) {
            //data.value 得到被选中的值
            console.log(data.value);
            pbInitComCombox($, form, 'tAppProjectPlan/getListPlanByProjectid?pprojectid=' + data.value, 'mprojectplanid', 'id', 'pplanname');
        });
        pbInitComCombox($, form, 'tAppProject/getListByAppointNoComplete', 'mprojectid', 'id', 'pname', data.mprojectid);
        pbInitComCombox($, form, 'tAppProjectPlan/getListPlanByProjectid?pprojectid=' + data.mprojectid, 'mprojectplanid', 'id', 'pplanname', data.mprojectplanid);
        //监听提交
        form.on('submit(btnSubmit)', function (data) {
            data.field.mconfirmId= data.field.id;
            console.log(data.field.mconfirmId);
            // var postdata= Object.create(data.field);
            // postdata.id = null;
            // layer.msg(JSON.stringify(data.field));
            var index = layer.load(1);//开启进度条
            $.ajax({
                url: '<%=request.getContextPath()%>/tAppManhourConfirm/modify?type=edit',
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
            $.ajax({

                url: '<%=request.getContextPath()%>/tAppDebugDetail/add', //提交保存bug修改记录
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

    });
</script>
</body>
</html>

