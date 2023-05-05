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
            <label class="layui-form-label" style="color:#F00">职位</label>
            <div class="layui-input-block">
                <input type="hidden" name="mjobid"/>
                <input type="text" name="mjobidNm" readOnly="readOnly" lay-verify="required" maxlength="5" placeholder="请输入"
                       autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" style="color:#F00">工时设定</label>
            <div class="layui-input-block">
                <input type="text" name="mmanhour" lay-verify="required" maxlength="5" placeholder="请输入"
                       autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" style="color:#F00">任务目标</label>
            <div class="layui-input-block">
                <input type="text" name="mtarget" lay-verify="required" maxlength="1000" placeholder="请输入"
                       autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" style="color:#F00">任务内容</label>
            <div class="layui-input-block">
                <input type="text" name="mdetails" lay-verify="required" maxlength="1000" placeholder="请输入"
                       autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" style="color:#F00">是否关联项目</label>
            <div class="layui-input-block">
                <select name="mislinkproject" id="mislinkproject" lay-verify="required"></select>
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
            elem: '#mupdatedate'
            , type: 'datetime'
            , format: 'yyyy-MM-dd HH:mm:dd'
        });

        //获取传参
        var data = JSON.parse(decodeURIComponent(getRequestParam().obj));
        //职位下拉菜单
        pbInitComCombox($, form, 'tSysJob/getList', 'mjobid', 'id', 'jname', data.mjobid);
        //是否关联项目下拉菜单
        pbInitCombox($, form, 'dictTypeCd=is_link_project', 'mislinkproject', data.mislinkproject);//is_link_project为数据字典类型编号

        //监听提交
        form.on('submit(btnSubmit)', function (data) {
            // layer.msg(JSON.stringify(data.field));
            var index = layer.load(1);//开启进度条
            $.ajax({
                url: '<%=request.getContextPath()%>/tAppMeritTemplate/modify',
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

