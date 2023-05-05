<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">项目名称</label>
            <div class="layui-input-inline">
                <input type="text" name="pname" maxlength="20" placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">项目描述</label>
            <div class="layui-input-inline">
                <input type="text" name="pdetails" maxlength="20" placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">项目类型</label>
            <div class="layui-input-inline">
                <select name="ptype" id="ptype"></select>

            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">发起人</label>
            <div class="layui-input-inline">
                <input type="text" name="popercd" maxlength="20" placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">发起人部门</label>
            <div class="layui-input-inline">
                <input type="text" name="pinscd" maxlength="20" placeholder="请输入" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">是否委派</label>
            <div class="layui-input-inline">
                <select name="pisappoint" id="pisappoint"></select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">是否完成</label>
            <div class="layui-input-inline">
                <select name="piscomplete" id="piscomplete"></select>
            </div>
        </div>
    </div>
    <!-- 按钮组 -->
    <button class="layui-btn" id="btnSearch" type="button">立即查询</button>
    <button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
    <%--<button class="layui-btn" data-type="btnAdd" type="button">新增</button>--%>
    <%--<button class="layui-btn layui-btn-danger" type="button" data-type="btnDelAll">批量删除</button>--%>
</form>
<script>
    layui.use(['form', 'layedit', 'laydate', 'jquery'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laydate = layui.laydate;

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

    });
</script>


