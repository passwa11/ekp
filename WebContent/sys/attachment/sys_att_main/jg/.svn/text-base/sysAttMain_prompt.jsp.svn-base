<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/jg/style/jg.css">
<div class="jg_tip_box">
	<div class="jg_tip_icon_content">
		<div class="jg_tip_icon"></div>
	</div>
	<c:choose>
		<c:when test="${not empty param.promptInfo}">
			<div class="jg_tip_message">
				<span>
					<i></i>
				</span> 
				<br> 
				<span>
					${param.promptInfo}
				</span>
				<br> 
				<i class="jg_tip_message_notic"></i>
			</div>
		</c:when>
		<c:otherwise>
			<div class="jg_tip_message">
				<span>
						<bean:message bundle="sys-attachment" key="JG.notSupport.info0" />
					<i>
						<bean:message bundle="sys-attachment" key="JG.notSupport.info1" />
					</i>
				</span> 
				<br> 
				<span>
						<bean:message bundle="sys-attachment" key="JG.notSupport.info2" />
				</span>
				<br> 
				<i class="jg_tip_message_notic"><bean:message bundle="sys-attachment" key="JG.notSupport.info3" /></i>
			</div>
		</c:otherwise>
	</c:choose>
	
</div>