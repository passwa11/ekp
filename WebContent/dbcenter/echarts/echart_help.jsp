<%@page import="com.landray.kmss.dbcenter.echarts.model.DbEchartsTemplate"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script src="${LUI_ContextPath}/dbcenter/echarts/db_echarts_table/rpt/plugins/layui/layui.js"></script>
<link href="${LUI_ContextPath}/dbcenter/echarts/db_echarts_table/rpt/css/glyphicon.css" rel="stylesheet">
<link href="${LUI_ContextPath}/dbcenter/echarts/db_echarts_table/rpt/plugins/layui/css/layui.css" rel="stylesheet">
<script>
function _showHelpLogInfo(el,w,h,scoll){
	var area = 'auto';
	if(w !="undefined" && typeof h !="undefined"){
		area =[w,h];
	}else if(typeof w !="undefined" && typeof h =="undefined"){
		area =[w,"auto"];
	}else if(typeof w =="undefined" && typeof h !="undefined"){
		area =["auto",h];
	}

	layui.use('layer', function(){
		var layer = layui.layer;
		var eln = "#"+el;
		$(eln).focus();
		var content = $("#_helpFrame").contents().find(eln).html();
		if(scoll==true){
			if(h.indexOf("px")!=-1){
				var t = h.substring(0,h.indexOf("px"));
				h = (parseInt(t)-20)+"px";
			}
			content = '<div style="height:'+h+';overflow-y:scroll;">'+content+'</div>';
		}
		layer.tips(content,eln, {
			tips: [3, '#666'],
			shade: 0,
			time: 0,
			move: '.layui-layer-content',
			area: area,
			closeBtn:1
		});	 
	});        

}
</script>
<iframe name="_helpFrame" id="_helpFrame" src="${LUI_ContextPath}${param.helpPage}" style="display:none"></iframe>