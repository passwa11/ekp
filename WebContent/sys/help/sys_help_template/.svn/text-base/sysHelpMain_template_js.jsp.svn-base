<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<script>
	// 通过锚点进行定位
	function toTarget(targetId){
		var targetDom = $('#'+targetId);
		if(targetDom){
			var parentDom = targetDom.parent();
			var height = 0;
			$.each(parentDom.children(), function(index, value){
				var child = value;
				if(child.id == targetId){
					height = child.offsetTop;
					return false;
				}
			});
			var css = {scrollTop : height + 'px'};
			$($(".content")[0]).animate(css, 300, null);
		}
	}
</script>

<script>
	function buildIndex(indexData){
		var indexData = eval('(${indexData})');
		var indexContent = $('#indexContent');
		if(indexContent){
			for(var i = 0;i < indexData.length; i++) {
				var dom = $('<div class="part"/>')
				dom.append('<a onclick="toTarget(\'sys_help_catelog_' + indexData[i].fdId + '\')">' + indexData[i].docSubject + '</a>');
				indexContent.append(dom);
			}
		}
	}
</script>

<script>
	window.indexType = 'hidden';
	function controlIndex(){
		var type = window.indexType;
		var floatDom = $('#floatBox');
		var btnDom = $('#showFloat');
		if(type == 'show'){
			var css = {right : '-210px'};
			floatDom.animate(css, 500, null);
			window.indexType = 'hidden';
			
			var cssBtn = {right : '0px'};
			btnDom.animate(cssBtn, 500, null);
		}else{
			var css = {right : '0px'};
			floatDom.animate(css, 500, null);
			window.indexType = 'show';
			
			var cssBtn = {right : '200px'};
			btnDom.animate(cssBtn, 500, null);
		}
	}
</script>

<script>
var json = eval('(${json})');

if (json.message =='success') { // 成功
	$('#tree').on("changed.jstree", function (e, data) {
		if(data.selected.length && data.node && data.node.data) {
			toTarget('sys_help_catelog_'+data.node.data);
		}
	}).jstree({
		'core' : {
			'multiple' : false,
			'data' : json.tree
		}
	});
	var targetId = '${targetId}';
	if(targetId){
		setTimeout("toTarget('sys_help_catelog_'+targetId)",200);
	}
	buildIndex('${indexData}');
}
</script>
