<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/common/kms_share/style/share_view.css" />
<%@page import="com.landray.kmss.kms.common.service.IKmsShareMainService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%
	IKmsShareMainService kmsShareMainService = 
			(IKmsShareMainService)SpringBeanUtil.getBean("kmsShareMainService");
	String fdModelId = (String)request.getParameter("fdModelId"); 
	String fdModelName = (String)request.getParameter("fdModelName");
	Long count = kmsShareMainService.getShareCount(fdModelId,fdModelName);
	String iconClass = "fontluis luis-share";
	request.setAttribute("shareCountNum",count);
	request.setAttribute("iconClass",iconClass);
%>
<script type="text/javascript">
	function shareAction(){
		$("body").css("overFlow-y","hidden;");//父窗口禁掉滚动条
		seajs.use(['lui/dialog'],function(dialog){
			dialog.iframe('/kms/common/kms_share/kmsShareMain.do?method=listShareModules&'+
					'fdModelId=${param.fdModelId}&fdModelName=${param.fdModelName}&fdCategoryId=${param.fdCategoryId}', 
							"${lfn:message('kms-common:kmsShareMain.share')}",
							function(){
								$("body").css("overFlow-y","auto;");
							}, 
							 {	
								width:710,
								height:570
							}
			);
		});
	}
</script>
<kmss:authShow roles="ROLE_KMSCOMMON_DEFAULT" >
<span class="${iconClass}" id="share_action_span"
	 title="${lfn:message('kms-common:kmsShareMain.share')}" href="#">
	 <c:if test="${param.showText == 'yes' }">
	 	${lfn:message('kms-common:kmsShareMain.share')}
	 </c:if>
</span>
</kmss:authShow>
<c:if test="${param.showNum != 'no' }">
    <div style="margin-top:6px;">
	    <span class="share_nums_left">${lfn:message('kms-common:kmsShareLog.readRecord')}(</span>
		<span class="share_nums" id="share_count_${param.fdModelId}">${shareCountNum}</span>
		<span class="share_nums_right">)</span>
    </div>
</c:if>
