<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil" %>
<%
	pageContext.setAttribute("_isWpsWebOffice", new Boolean(SysAttWpsCloudUtil.isEnableWpsWebOffice()));
	pageContext.setAttribute("_isWpsoaassistEmbed", new Boolean(SysAttWpsoaassistUtil.isWPSOAassistEmbed(request)));
%>

<c:choose>
	<c:when test="${fdPrintMode=='2' && pageScope._isWpsWebOffice != 'true'}">
		<%@ include file="/sys/print/import/sysPrintMain_word_view.jsp"%>
	</c:when>
	<c:when test="${fdPrintMode=='2' && pageScope._isWpsoaassistEmbed == 'true'}">
		<%@ include file="/sys/print/import/sysPrintMain_word_view.jsp"%>
	</c:when>
	<c:when test="${fdPrintMode=='2' && pageScope._isWpsWebOffice == 'true' && pageScope._isWpsoaassistEmbed == 'false'}">
		<%@ include file="/sys/print/import/sysPrintMain_wps_word_view.jsp"%>
	</c:when>
	<c:when test="${JsParam.design==true }">
		<%@ include file="/sys/print/import/sysPrintMain_define_design_view.jsp"%>
	</c:when>
	<c:otherwise>
		<%@ include file="/sys/print/import/sysPrintMain_define_view.jsp"%>
	</c:otherwise>
</c:choose>
<script type="text/javascript">


	$(function(){
		var _isWpsWebOffice = "${_isWpsWebOffice}";
		if(_isWpsWebOffice == 'true')
		{
			$(".qrcodeArea").remove();
		}
	});
	function switchPrintPage(){
		var href = window.location.href;
		if(href.indexOf('&_ptype=') > -1){
			href = href.replace('&_ptype=old','').replace('&_ptype=new','');
		}
		if(href.indexOf('?') > -1){
			href = href+"&_ptype=old";
		}else{
			href=href+'?_ptype=old';
		}
		window.location.href=href;
	}
	//初始化话完成是要修改手写签批图片的宽度
	Com_AddEventListener(window,"load",function(){
		$("div.xform_auditshow img").each(function(index,obj){
			if ($(obj).attr("width") && $(obj).attr("width").indexOf("%") > 0) {
				var $imgObj = $(obj);
				//获取原生图片宽度
				var img = new Image();
				img.src = $imgObj.attr("src");
				var orgWidth = img.width;
				//获取48%的宽度
				//$imgObj.attr("width","48%");
				//var maxWidth = $imgObj.width();
				var maxWidth = 100;
				if(orgWidth < maxWidth) {
					$imgObj.attr("width",orgWidth+"px");
				}else{
					$imgObj.attr("width",maxWidth+"px");
				}
			}
		});
	})
</script>