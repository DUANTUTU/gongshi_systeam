<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>

<head>
<title>之年工时系统</title>


	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css"></link>

	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/indexassets/css/main.css" />
	<noscript><link rel="stylesheet" href="<%=request.getContextPath()%>/resources/indexassets/css/noscript.css" /></noscript>
	<jsp:include page="inc.jsp"></jsp:include>

</head>
<style type="text/css">
	.login_form {
		background-color: #10090996;
	}
	.layui-layer-dialog .layui-layer-padding {
		color: black;
	}
	.form_name {
		width: 100%;
	}
</style>
<body class="is-preload">
<div id="wrapper">
	<div id="bg"></div>
	<div id="overlay"></div>
	<div id="main">

		<!-- Header -->
		<header id="header">
			<div id="top">
				<h1 style="margin-top:25px;color:#FFFFFF">之年工时系统</h1>
			</div>
			<div id="loginbg" class="loginbg" style="margin:0 auto; width:500px; height:500px;">
				<div id="login_form" class="login_form">
					<p class="form_name">登录管理系统</p>
					<div id="table_login">
						<form name="loginForm" id="Login_loginForm" method="post" action="">
							<input type="text" placeholder="请输入用户名" value="admin" name="operCd" id="Login_operCd" class="name" />
							<input type="password" placeholder="请输入密码" value="1" class="password" name="operPwd" id="Login_operPwd" />
							<div class="yzm">
								<input style="margin-left: 19px;width: 135px;" type="text" placeholder="请输入验证码" class="code" name="code" id="Login_code" />
								<img id="Login_code_img" src="<%=request.getContextPath()%>/ImageServlet" style="cursor:pointer;" onclick="changeValidateCode()" title="点击图片刷新验证码" />
							</div>
							<button class="layui-btn"  style="width:200px" lay-submit="" lay-filter="btnSubmit">立即登录</button>
						</form>
					</div>
				</div>
			</div>
		</header>
		<!-- Footer -->
		<footer id="footer">
			<span class="copyright">&copy; 之年科技 2023 </span>
<%--			<span class="copyright">&copy; 富瑞能源: <a href="javascript:void(0);">2020</a></span>--%>
		</footer>

	</div>
</div>

</body>
<script type="text/javascript">
	window.onload = function() { document.body.classList.remove('is-preload'); }
	window.ontouchmove = function() { return false; }
	window.onorientationchange = function() { document.body.scrollTop = 0; }

layui.use(['form', 'layedit', 'laydate','jquery'], function(){
  var form = layui.form;
  var $ = layui.jquery;

//监听提交
  form.on('submit(btnSubmit)', function(data){
	  	var operCd = $('#Login_operCd').val();//用户名
		var operPwd = $('#Login_operPwd').val();//密码
		var code = $('#Login_code').val();//验证码

		if (operCd == '') {
			pubUtil.msg("请输入用户名!", layer, 2, function() {
			}, 3 * 1000);
			return false;
		}

		if (operPwd == '') {
			pubUtil.msg("请输入密码!", layer, 2, function() {
			}, 3 * 1000);
			return false;
		}
		if (code == '') {
			pubUtil.msg("请输入验证码！", layer, 2, function() {
			}, 500 * 1000);
			return false;
		}
  	 var index = layer.load(1);//开启进度条
     $.ajax({
		url : '<%=request.getContextPath()%>/operInf/execute',
		data :{
			operCd:$("#Login_operCd").val(),
			operPwd:$("#Login_operPwd").val(),
			code:$("#Login_code").val()
		},
		type : 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
		dataType : 'json',
		success : function(obj) {
			layer.close(index);//关闭
			if (obj && obj.success) {
			 	layer.close(index);//关闭
				window.location.href = "<%=request.getContextPath()%>/"+"main.jsp";
			} else {
				layer.close(index);//关闭
				changeValidateCode();//修改验证码
				$('#Login_operPwd').val('');//密码
				$('#Login_code').val('');//验证码
				pubUtil.msg(obj.msg, layer, 2, function() {
				}, 3 * 1000);
			}
		}
	});
	return false;
});

});

/**
 *刷新验证码
 */
function changeValidateCode() {
	var obj = $('#Login_code_img');
	var timenow = new Date().getTime();
	var url = "${pageContext.request.contextPath}/ImageServlet?d=" + timenow;
	$(obj).attr("src", url);
}

</script>
</html>
