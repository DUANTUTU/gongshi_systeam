<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<form name="searchform" id="searchform" class="layui-form layui-form-pane" method="post" action="">
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
            <label class="layui-form-label">填报人姓名</label>
            <div class="layui-input-block">
                <input type="text" name="operNm" id="operNm" placeholder="填报人姓名" autocomplete="off"
                       class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">选择时间</label>
            <div class="layui-input-inline">
                <input type="text" name="leftDate" id="leftDate" placeholder="开始时间" autocomplete="off"
                       class="layui-input"/>
            </div>
            <div class="layui-input-inline">
                <input type="text" name="rightDate" id="rightDate" placeholder="结束时间" autocomplete="off"
                       class="layui-input"/>
            </div>

        </div>
    </div>
    <!-- 按钮组 -->
    <button class="layui-btn" id="btnSearch" type="button">立即查询</button>
    <button type="reset" id="btnRetSet" type="button" class="layui-btn layui-btn-primary">重置</button>
    <button class="layui-btn" id="btnExport" type="button">导出报表</button>
    <%--<button class="layui-btn layui-btn-danger"  type="button" data-type="btnDelAll">批量删除</button>--%>
</form>
<script>
    layui.use(['form', 'layedit', 'laydate', 'jquery'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laydate = layui.laydate;

        //时间控件
        laydate.render({
            elem: '#leftDate'
            , type: 'datetime'
            , format: 'yyyy-MM-dd HH:mm:dd'
        });
        //时间控件
        laydate.render({
            elem: '#rightDate'
            , type: 'datetime'
            , format: 'yyyy-MM-dd HH:mm:dd'
        });

        form.on('select(project)', function (data) {
            //data.value 得到被选中的值
            console.log(data.value);
            pbInitComCombox($, form, 'tAppProjectPlan/getListPlanByProjectid?pprojectid=' + data.value, 'mprojectplanid', 'id', 'pplanname');
        });


        pbInitComCombox($, form, 'tAppProject/getListByAppointNoComplete', 'mprojectid', 'id', 'pname');

    });
</script>


