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
    <title>查看</title>
    <jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body>
<form name="form" class="layui-form" style="margin-top: 20px;" method="post" action="">

    <div class="layui-form-item">

        <div class="layui-inline">
            <label class="layui-form-label">职位</label>
            <div class="layui-input-block">
                <input type="text" name="mjobidNm" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">工时设定</label>
            <div class="layui-input-block">
                <input type="text" name="mmanhour" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">任务目标</label>
            <div class="layui-input-block">
                <input type="text" name="mtarget" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">任务内容</label>
            <div class="layui-input-block">
                <input type="text" name="mdetails" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">是否关联项目</label>
            <div class="layui-input-block">
                <input type="text" name="mislinkprojectNm" placeholder="无" autocomplete="off" class="layui-input"/>
            </div>
        </div>

    </div>

    <!-- 按钮组 -->
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-primary" id="close">关闭</button>
        </div>
    </div>
</form>
</body>
</html>

