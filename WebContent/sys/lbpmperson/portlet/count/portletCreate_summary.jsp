<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div style="display:none;position:absolute;">
</div>
<div style="margin: 0 auto; padding-top:15px;padding-bottom:15px; background-color:white;width:100%;float:left">
	<ui:chart height="350px" width="100%" id="lbpmPersonStatChart">
		<ui:source type="AjaxJson">
			{"url":"/sys/lbpmperson/SysLbpmPersonSummary.do?method=listCreateSummary"}
		</ui:source>
	</ui:chart>		
</div>

		
