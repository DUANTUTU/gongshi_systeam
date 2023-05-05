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

        <div class="layui-inline">
            <label class="layui-form-label">项目</label>
            <div class="layui-input-block">
                <select name="mprojectid" id="mprojectid" lay-filter="project"></select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">项目里程碑</label>
            <div class="layui-input-block">
                <select name="mprojectplanid" id="mprojectplanid"></select>
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

        //监听提交
        form.on('submit(btnSubmit)', function (data) {
            // layer.msg(JSON.stringify(data.field));
            var index = layer.load(1);//开启进度条
            var isMustPlanBoolean = true;
            if ($("#mprojectid").val() != "" && $("#mprojectplanid").val() == "") {
                //获取select中的ID
                var selectId = document.getElementById("mprojectplanid");
                //获取select下拉框中第一个值
                var selectLength = selectId.options.length;
                if (selectLength > 1) {
                    pubUtil.msg("因里程碑存在则里程碑必填", layer, 2, function () {
                    }, 5 * 1000);
                    isMustPlanBoolean = false;
                }
            }
            if (isMustPlanBoolean) {
                $.ajax({
                    url: '<%=request.getContextPath()%>/tAppManhourConfirm/add',
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
            }
            return false;
        });

        form.on('select(project)', function (data) {
            //data.value 得到被选中的值
            console.log(data.value);
            pbInitComCombox($, form, 'tAppProjectPlan/getListPlanByProjectid?pprojectid=' + data.value, 'mprojectplanid', 'id', 'pplanname');
        });

        //是否完成下拉菜单
        pbInitCombox($, form, 'dictTypeCd=check_status', 'mcheckstatus');//is_complete为数据字典类型编号

        pbInitComCombox($, form, 'tAppProject/getListByAppointNoComplete', 'mprojectid', 'id', 'pname');
    });
</script>
</body>
</html>

