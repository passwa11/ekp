<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script>

window.__sysAttendStartTime__ = '${sysAttendCategoryForm.fdStartTime }';
window.__sysAttendEndTime__ = '${sysAttendCategoryForm.fdEndTime }';
window.__sysAttendLateTime__ = '${sysAttendCategoryForm.fdRule[0].fdLateTime }';
window.__sysAttendInTime__ = '${sysAttendCategoryForm.fdRule[0].fdInTime }';

</script>

<template:include ref="maxhub.edit">
	<template:replace name="head">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/sys/attend/sys_attend_category/maxhub/resource/css/edit_custom.css?s_cache=${MUI_Cache}">
		
		<style>
			.mhui-template-body {
				background: white;
			}
		</style>
		
	</template:replace>

	<template:replace name="content">
	
		<div class="mhuiSysAttendForm">
			<div class="mhuiSysAttendFormItem">
				<span>${sysAttendCategoryForm.fdStartTime }</span>
				<div>签到开始</div>
			</div>
			
			<div class="mhuiSysAttendFormSplit">
				<span id="sysAttendLateTime"></span>后为迟到
			</div>
			
			<div class="mhuiSysAttendFormItem">
				<span>${sysAttendCategoryForm.fdEndTime }</span>
				<div>签到结束</div>
			</div>
		</div>
		
		<html:form action="/sys/attend/sys_attend_category/sysAttendCategory.do" style="display: none;">
			<html:hidden property="fdOrder" />
			<html:hidden property="fdAppId" />
			<html:hidden property="fdAppName" />
			<html:hidden property="fdAppUrl" />
			<html:hidden property="fdStatus" />
			<html:hidden property="docCreatorId" />
			<html:hidden property="docCreatorName" />
			<html:hidden property="docCreateTime" />
			<html:hidden property="fdId" />
			<html:hidden property="fdTemplateId" />
			<html:hidden property="method_GET" />
			<html:hidden property="docStatus" value="30" />
			
			<%-- 签到类型 --%>
			<html:hidden property="fdType" />
			
			<!-- 签到组名称 -->
			<html:hidden property="fdName" />
			
			<!-- 负责人 -->
			<html:hidden property="fdManagerId" />
			
			<!-- 签到范围 -->
			<html:hidden property="fdTargetIds" />
			
			<!-- 排除人员 -->
			<html:hidden property="fdExcTargetIds" />
			
			<!-- 是否允许范围外人员签到 -->
			<c:if test="${not empty sysAttendCategoryForm.fdAppName }">
				<html:hidden property="fdUnlimitTarget" />
 			</c:if>
 			
			<!-- 签到方式（自定义） -->
			<input type="hidden" name="fdPeriodType" value="2"/>
			
			<!-- 签到周期 -->
			<html:hidden property="fdWeek" />
			
			<!-- 排除日期明细表 -->
			<c:forEach items="${sysAttendCategoryForm.fdExcTimes}" var="fdExcTimesItem" varStatus="vstatus">
				<input type="hidden" name="fdExcTimes[${vstatus.index}].fdId" value="${fdExcTimesItem.fdId}" /> 
				<input type="hidden" name="fdExcTimes[${vstatus.index}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
				<input type="hidden" name="fdExcTimes[${vstatus.index}].fdExcTime" value="${sysAttendCategoryForm.fdExcTime}" />
			</c:forEach>
			
			<!-- 自定义日期明细表 -->
			<c:forEach items="${sysAttendCategoryForm.fdTimes}" var="fdTimesItem" varStatus="vstatus">
				<input type="hidden" name="fdTimes[${vstatus.index}].fdId" value="${fdTimesItem.fdId}" /> 
				<input type="hidden" name="fdTimes[${vstatus.index}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
				<input type="hidden" name="fdTimes[${vstatus.index}].fdTime" value="${fdTimesItem.fdTime}" />
			</c:forEach>
			
	    	<c:if test="${not empty sysAttendCategoryForm.fdAppName }">
	    		<!-- 签到时间 -->
	    		<html:hidden property="fdRule[0].fdInTime" />
				<!-- 迟到时间 -->
	    		<html:hidden property="fdRule[0].fdLateTime" />
	    	</c:if>
	    	
			<!-- 开放打卡时间 -->
			<html:hidden property="fdStartTime" />
			<!-- 关闭打卡时间 -->
			<html:hidden property="fdEndTime" />
			
			<!-- 签到范围 -->
			<html:hidden property="fdRule[0].fdLimit" />
			
			<!-- 签到地点 -->
			<c:forEach items="${sysAttendCategoryForm.fdLocations}" var="fdLocationsItem" varStatus="vstatus">
				<input type="hidden" name="fdLocations[${vstatus.index}].fdId" value="${fdLocationsItem.fdId}" /> 
				<input type="hidden" name="fdLocations[${vstatus.index}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
				<input type="hidden" name="fdLocations[${vstatus.index}].fdLocation" value="${sysAttendCategoryForm.fdLocation}" />
			</c:forEach>
			
			<!-- 签到二维码有效期 -->
			<c:if test="${not empty sysAttendCategoryForm.fdAppName }">
				<html:hidden property="fdQRCodeTime" />
			</c:if>
			
			<!-- 提前通知时间 -->
			<html:hidden property="fdNotifyOnTime" />
			
			<!-- 打卡结果 -->
			<html:hidden property="fdNotifyResult" />
			
			<!-- 
			<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="sysAttendCategoryForm" />
				<c:param name="moduleModelName" value="com.landray.kmss.sys.attend.model.SysAttendCategory" />
			</c:import>
			-->
			
		</html:form>
		
		<script type="text/javascript" src="${LUI_ContextPath}/sys/attend/sys_attend_category/maxhub/resource/js/edit_custom.js"></script>
	</template:replace>	
</template:include>