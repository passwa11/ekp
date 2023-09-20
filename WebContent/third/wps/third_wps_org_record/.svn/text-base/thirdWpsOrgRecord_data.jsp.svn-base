<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.third.wps.model.ThirdWpsOrgRecord"%>


<list:data>
	<list:data-columns var="thirdWpsOrgRecord" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column property="docCreateTime" title="${lfn:message('third-wps:thirdWpsOrgRecord.docCreateTime')}">
		</list:data-column>
		<list:data-column property="fdStartTime" title="${lfn:message('third-wps:thirdWpsOrgRecord.fdStartTime')}">
		</list:data-column>
		<list:data-column property="fdEndTime" title="${lfn:message('third-wps:thirdWpsOrgRecord.fdEndTime')}">
		</list:data-column>
		<list:data-column col="fdSyncTag" title="${lfn:message('third-wps:thirdWpsOrgRecord.message.isSyncSuccess')}">
			<%
			Object obj = pageContext.getAttribute("thirdWpsOrgRecord");
			String status = "否";
			if(obj != null) {
				ThirdWpsOrgRecord thirdWpsOrgRecord = (ThirdWpsOrgRecord)obj;
				if(thirdWpsOrgRecord.getFdSyncTag() != null && thirdWpsOrgRecord.getFdSyncTag() == 1){
					status = "是";
				}else if(thirdWpsOrgRecord.getFdSyncTag() == null){
					if(thirdWpsOrgRecord.getFdEndTime() == null){
						status = "同步中";
					}
				}
			}
			out.print(status);
			%>
		</list:data-column>
		
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>