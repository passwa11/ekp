<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/kms/common/kms_common_push/kms_common_data_push_script.jsp"%>

<div style="float: right; margin-right: 20px;">
	<ui:dataview>
		<ui:source type="AjaxJson">
			{url:'${url}'}
		</ui:source>
		<ui:render type="Template">
			<c:import url="/kms/common/kms_common_push/kms-common-datapush.html"
				charEncoding="UTF-8">
			</c:import>
		</ui:render>
	</ui:dataview>
</div>

