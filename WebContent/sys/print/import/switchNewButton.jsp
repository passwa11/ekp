<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
function switchPrintPage(){
	var href = window.location.href;
	if(href.indexOf('&_ptype=') > -1){
		href = href.replace('&_ptype=old','').replace('&_ptype=new','');
	}
	if(href.indexOf('?') > -1){
		href = href+"&_ptype=new";
	}else{
		href=href+'?_ptype=new';
	}
	window.location.href=href;
}
</script>
<c:if test="${isShowSwitchBtn=='true'}">
  <ui:button text="${ lfn:message('sys-print:sysPrintPage.switchBtn.new')}" onclick="switchPrintPage();">
  </ui:button>
</c:if>
