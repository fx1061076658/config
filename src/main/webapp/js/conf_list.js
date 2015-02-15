$(function(){
	grid.loadGrid();
	$("#save").click(function(){
		$('#dialog').dialog({
			title: '配置',   
		    width: 400,   
		    height: 200,   
		    closed: false,   
		    cache: false,
		    href:ctx+"/info.jsp",
		    modal: true  
		});
	})
	$("#edit").click(function(){
		var checkData = $("#data_grid").datagrid("getChecked");
		var key,value;
		if(checkData){
			if(checkData.length ==1){
				key = checkData[0].key;
				value = checkData[0].value;
			}else{
				alert("只能选择一项数据进行修改！");
				return;
			}
			
		}else{
			alert("请选择数据进行修改！");
			return;
		}
		$('#dialog').dialog({
			title: '配置',   
		    width: 400,   
		    height: 200,   
		    closed: false,   
		    cache: false,
		    href:ctx+"/info.jsp?key="+key+"&value="+value,
		    modal: true  
		});
	})
})

var grid = {
	loadGrid:function(){
		$("#data_grid").datagrid({
			height:'auto',
			width:'auto',
			url:ctx+"/list",
			queryParams:{path:path},
			fit:true,
			toolbar:'#tb',
			idField:'key',
			fitColumns:true,
			rownumbers:true,
			pagination:true,
			selectOnCheck:true,
			pageSize:20,
			columns:[[{field:'ck',width:20,checkbox:true},{title:'key', field:'key',width:200},{title:'value', field:'value',width:200}]],
			loadFilter:function(data,node){
				return {rows:data.data,total:data.data.length};
			},
			onLoadSuccess:function(node,data){
				
			}
				
		})
	}
	
};
function closeWin(){
	$('#dialog').dialog('close');
}
function save(){
	var checkData = $("#data_grid").datagrid("getChecked");
	var okey;
	if(checkData && checkData.length ==1){
		okey = checkData[0].key
	}
	var key = $("#key").val();
	var value = $("#value").val();
	if(key){
		$.ajax({
			url:ctx+'/saveorupdate',
			method:'POST',
			async:false,
			data:{path:path,key:key,value:value,okey:okey},
			success:function(){
				alert('保存成功！');
				closeWin();
				grid.loadGrid();
			}
		});
	}else{
		alert('参数不能为空！');
	}
}

function getKeys(datas){
	var array = new Array();
	for(var i in datas){
		array.push(datas[i].key);
	}
	
	return array.join(',');
}

function del(){
	var checkData = $("#data_grid").datagrid("getChecked");
	if(checkData && checkData.length >0){
		if(confirm("确认删除？")){
			var keys = getKeys(checkData);
			$.ajax({
				url:ctx+'/del',
				method:'POST',
				async:false,
				data:{path:path,keys:keys},
				success:function(){
					alert('删除成功！');
					closeWin();
					grid.loadGrid();
				}
			});
		}
	}else{
		alert("请选择数据进行删除！");
	}
}