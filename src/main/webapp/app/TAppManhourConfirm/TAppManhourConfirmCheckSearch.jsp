<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">项目ID</label>
            <div class="layui-input-inline">
                <select name="mprojectid" id="mprojectid"></select>
            </div>
        </div>

        <div class="layui-inline">
            <label class="layui-form-label">工作内容</label>
            <div class="layui-input-inline">
                <input type="text" name="mworkdetails" maxlength="20" placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">审核状态</label>
            <div class="layui-input-inline">
                <select name="mcheckstatus" id="mcheckstatus"></select>
            </div>
        </div>
    </div>
    <!-- 按钮组 -->
    <button class="layui-btn" id="btnSearch" type="button">立即查询</button>
    <button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
    <%--<button class="layui-btn" data-type="btnAdd" type="button">新增</button>--%>
    <button class="layui-btn layui-btn-danger" type="button" data-type="btnDelAll">批量同意</button>
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

        //xiangmu下拉菜单
        pbInitComCombox($,form,'tAppProject/getListByAppointNoComplete','mprojectid','id','pname');
        //shenpizhuangtai下拉菜单
        pbInitCombox($, form, 'dictTypeCd=check_status', 'mcheckstatus');//check_status为数据字典类型编号
    });
</script>


