<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%
    	request.setAttribute("ctx", request.getContextPath());
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/icon.css"/>
	<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/conf_list.js"></script>
	<script>
		var ctx = '${ctx}';
		var path = '${param.path}';
	</script>
</head>
<body>
<div id="data_grid" name="data_grid"></div>
	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" id="save" name="save" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">Add</a>
		<a href="javascript:void(0)" id="edit" name="edit" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">Edit</a>
		<a href="javascript:void(0)" id="delete" name="delete" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="del();">Remove</a>
	</div>
	<div id="dialog" name="dialog"></div>
</body>
</html>