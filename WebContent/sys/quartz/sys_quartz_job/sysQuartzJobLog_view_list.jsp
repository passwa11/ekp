<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>	
Com_IncludeFile("optbar.js");
	
</script>
<tr LKS_LabelName="<bean:message  bundle="sys-quartz" key="sysQuartzJob.tab.log"/>" style="display:none;">
	<td>
		<c:import url="/sys/quartz/sys_quartz_job/sysQuartzJobLog_view_list_index.jsp" charEncoding="UTF-8">
			<c:param name="fdJobService" value="${param.fdJobService}" />
			<c:param name="fdJobMethod" value="${param.fdJobMethod}" />
			<c:param name="fdSubject" value="${param.fdSubject}" />
			<c:param name="rowsize" value="10" />
		</c:import>	
	</td>
</tr>
