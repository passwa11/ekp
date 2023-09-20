<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<ui:content title="${ lfn:message('kms-medal:table.kmsMedalLog') }">
	<ui:event event="show">
		document.getElementById('logContent').src= "<c:url value="/kms/medal/kms_medal_log/kmsMedalLog_list.jsp" />?fdMedalId=${param.fdMedalId}";
	</ui:event>
	<table width=100%>
		<tr>
			<td>
				<iframe id="logContent" width="100%" height="1000" frameborder=0 scrolling=no></iframe>
			</td> 
		</tr>
	</table>
</ui:content>
