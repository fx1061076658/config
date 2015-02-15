<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	request.setAttribute("ctx", request.getContextPath());
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>
EIDLINK 平台应用配置管理中心
</title>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/icon.css"/>
	<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/index.js"></script>
	<script>
		var ctx = '${ctx}';
		function init(){
			$.ajax({
				url:ctx+"/init",
				async:false,
				success:function(data){
					alert("初始化成功！");
				}
			})
		}
	</script>
</head>
<body>
<div><h2>EIDLINK 平台应用配置管理中心<div style="overflow:left;"><input type="button" id="init" name="init" value="初始化导入" onclick="init();"></div></h2></div>
<hr>
<div id="tree" name="tree" style="float:left;height:400px;width:20%;background:#ccc"></div>
<div><iframe id="grid" style="width:79%;height:400px;" src="${ctx}/conf_list.jsp" name="grid" frameBorder=0></iframe></div>
</body>
</html>
