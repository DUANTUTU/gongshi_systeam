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
        <input type="hidden" name="id" />
        <div class="layui-inline">
            <label class="layui-form-label">职位名称</label>
            <div class="layui-input-block">
                <input type="text" name="jname" maxlength="100" placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">职位状态</label>
            <div class="layui-input-block">
                <select  name="jstatus" id="jstatus" lay-verify="required">
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">职位类型</label>
            <div class="layui-input-block">
                <select  name="jtype" id="jtype" lay-verify="required">
                </select>
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
            elem: '#jcreatedate'
            , type: 'datetime'
            , format: 'yyyy-MM-dd HH:mm:dd'
        });
        //获取传参
        var data =JSON.parse(decodeURIComponent(getRequestParam().obj));

        //初始化性别下拉框
        pbInitCombox($,form,'dictTypeCd=job_status','jstatus',data.jstatus);//job_status为数据字典类型编号
        pbInitCombox($,form,'dictTypeCd=job_type','jtype',data.jtype);//job_type为数据字典类型编号
        //监听提交
        form.on('submit(btnSubmit)', function (data) {
            // layer.msg(JSON.stringify(data.field));
            var index = layer.load(1);//开启进度条
            $.ajax({
                url: '<%=request.getContextPath()%>/tSysJob/modify',
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

