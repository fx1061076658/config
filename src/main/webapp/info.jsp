<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/icon.css"/>
	<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	<script type="text/javascript">
		
	</script>
</head>
<body>
<div class="easyui-layout" style="width:100%;height:100%">
<div data-options="region:'center'">
	<div id="dlg" style="margin-left:50px;height:75%;padding:10px 0 0 0;">
			<div style="margin:10px 0 0 0">
				<div style="width:40px;float:left;text-align:right">key:</div>
				<Div style="margin-left:10px;"><input id="key" name="key" value="${param.key}"/></div></div>
			<div style="margin:10px 0 0 0">
				<div style="width:40px;float:left;text-align:right">value:</div>
			    <Div  style="margin-left:10px;"><input id="value" name="value" value="${param.value}"></div></div>
	</div>
	<div id="dlg-buttons" STYLE="text-align:right;margin-right:20px;">
		<a href="javascript:save();" class="easyui-linkbutton">Save</a>
		<a href="javascript:closeWin();" id="close" class="easyui-linkbutton">Close</a>
	</div>
</div>
 
</div>
		
		
</body>
</html>