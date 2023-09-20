<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page
	import="com.landray.kmss.sys.praise.service.ISysPraiseInfoService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	ISysPraiseInfoService sysPraiseInfoService = (ISysPraiseInfoService) SpringBeanUtil
			.getBean("sysPraiseInfoService");
	Integer praiseNum = sysPraiseInfoService.getPraiseNum(request);
	request.setAttribute("praiseNum", praiseNum);
%>
<c:set var="btnName"
	value="${lfn:message('sys-praise:module.sys.praiseInfo')}" />
<c:if test="${!empty param.btnName}">
	<c:set var="btnName" value="${param.btnName}" />
</c:if>
<c:if test="${empty param.showTotal||param.showTotal eq 'true'}">
	<span class="totalPraise"
		onclick="showPraiseInfo('${param.fdModelId}','${param.fdModelName}')">
		${btnName} (<font style="color: #4285f4">${praiseNum}</font>) </span>
</c:if>
<c:if test="${empty param.showBtn||param.showBtn eq 'true'}">
	<ui:button styleClass="lui_toolbar_btn_def praiseInfoBtn"
		text="${btnName}"
		onclick="showInfo('${param.fdModelId}','${param.fdModelName}','${param.fdTargetPersonId}')"></ui:button>
</c:if>
<script>
    function showInfo(fdModelId, fdModelName, fdTargetPersonId,
            fdTargetPersonName) {
        if (fdModelId != "" && fdModelName != "") {
            var fdUrl = "/sys/praise/sys_praise_info/sysPraiseInfo.do?method=add&fdModelId="
                    + fdModelId
                    + "&fdModelName="
                    + fdModelName
                    + "&fdPersonId=" + fdTargetPersonId;
            seajs.use([ 'lui/dialog' ], function (dialog) {
                dialog.iframe(fdUrl,
                        "${ lfn:message('sys-praise:module.sys.praiseInfo') }",
                        null, {
                            width : 600,
                            height : 370
                        });
            });
        }
    }
    
    function showPraiseInfo(fdModelId,fdModelName){
        Com_OpenWindow("${LUI_ContextPath}/sys/praise/sys_praise_info/index.jsp?fdModelId="+fdModelId+"&fdModelName="+fdModelName,"_blank");
    }
</script>
