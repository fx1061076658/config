$(function(){
	tree.loadTree();
})

var tree={
		loadTree:function(){
			$("#tree").tree({
				height:$(window).height(),
				width:$(window).width(),
				url:ctx+"/tree",
				loadFilter:function(data,node){
					return data.data;
				},
				onLoadSuccess:function(node,data){
					var defnode = $("#tree").tree("find",'1');
					$("#tree").tree("select",defnode.target);
				},
				onSelect:function(node){
					$("#grid").attr("src",ctx+"/conf_list.jsp?path="+node.attributes.path);
				}
			})
		}
	}