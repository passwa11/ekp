<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
		<div id="previewMportal" style="text-align: center;margin-top: 2.3rem"></div>

<script>
	seajs.use(['lui/jquery','lui/qrcode'],function($,qrcode){
		
		$(document).ready(function(){
			var url = location.origin+"${ LUI_ContextPath}/sys/mportal/mobile/composite/preview.jsp?fdCompositeId=${param.fdId}";
			 var isBitch = navigator.userAgent.indexOf("MSIE") > -1 && document.documentMode == null || document.documentMode <= 8;
	         qrcode.Qrcode({
	             text :url,
	             element : $("#previewMportal"),
	             render : isBitch ? 'table' : 'canvas',
	             width:142,
	             height:142,

	         });
		});
	});


</script>