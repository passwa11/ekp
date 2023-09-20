<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/jg/style/jg.css">
	<%
	   String promptInfo = request.getAttribute("promptInfo").toString();
	%>
	<style>
.jg_tip_box{
   width: 560px;
    margin: 0 auto;
    border: 1px solid #DCDDDE;
    height: 115px;
    padding: 10px;
    position: absolute;
    top: 50%;
    left: 50%;
    margin-left: -285px;
    margin-top: -72px;
}
</style>
<div class="jg_tip_box">
	<div class="jg_tip_icon_content">
		<div class="jg_tip_icon"></div>
	</div>
	<c:choose>
		<c:when test="${promptInfo != ''}">
			<div class="jg_tip_message">
				<span>
					<i></i>
				</span> 
				<br> 
				<span>
					${promptInfo}
				</span>
				<br> 
				<i class="jg_tip_message_notic"></i>
			</div>
		</c:when>
		
	</c:choose>
	
</div>