<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>	
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.third.pda.util.PdaFlagUtil"%>
<%@ page import="com.landray.kmss.util.KmssMessageWriter,com.landray.kmss.util.KmssReturnPage" %>
<%   
	KmssMessageWriter msgWriter = null;
	if(request.getAttribute("KMSS_RETURNPAGE")!=null){
		msgWriter = new KmssMessageWriter(request, (KmssReturnPage)request.getAttribute("KMSS_RETURNPAGE"));
	}else{
		msgWriter = new KmssMessageWriter(request, null);
	}
	
	if(msgWriter!=null){
		String messages = msgWriter.DrawTitle(false);
		pageContext.setAttribute("messages", messages);
	}
	
	if(request.getAttribute("errMsg")!=null){
		pageContext.setAttribute("messages", "<div class=\"msgtitle\">" + request.getAttribute("errMsg") + "</div>");
	}
	
%>
<template:include file="/third/pda/template.jsp" compatibleMode="true">
	<template:replace name="title">
		<bean:message key="return.systemInfo"/>
	</template:replace>
	<template:replace name="head">
		<style>
			.muiLoginPage{background-color: #f0eff5;}
			.msgtitle{padding: 2rem;}
		</style>
	</template:replace>
	<template:replace name="content">
		<div class="noDataTipWrapper gray">
	        <div class="icon">
	            <i class="mui mui-noFind"></i>
	        </div>
			<c:if test="${not empty messages}">
				${messages }
			</c:if>
			<c:if test="${empty messages}">
				<p class="tips"><bean:message key="return.optFailure" /></p>
			</c:if>
	        
	        <a class="btnReturn" onclick="redirectTo()"><i class="mui mui-back"></i></i><bean:message key="phone.view.back"  bundle="third-pda"/></a>
    	</div>
    
		
	</template:replace>
</template:include>
		<script type="text/javascript">
			require(['mui/util','mui/device/adapter'],function(util,adapter){
				window.redirectTo = function(){
					var forwardUrl = "${forwardUrl}";
					if(forwardUrl) {
						location.href= util.formatUrl(forwardUrl,true);
					} else {
						var rtn = adapter.goBack();
						if(rtn == null){
							history.back();
						}
					}
				};
	        });
		</script>