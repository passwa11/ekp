<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>



<div style="display:none;position:absolute;">
</div>
		<jsp:include page="/sys/lbpmperson/person_efficiency/summary_count.jsp"></jsp:include>
		<div style="margin: 0 0 0 10px; padding-top:15px;padding-bottom:15px; background-color:white;width:48%;float:left">
				<ui:chart height="350px" width="100%" id="lbpmPersonStatChart">
  					<ui:source type="AjaxJson">
						{"url":"/sys/lbpmperson/SysLbpmPersonSummary.do?method=listCreateSummary"}
  					</ui:source>
				</ui:chart>		
		</div>
		<div style="margin: 0 0 0 10px; padding-top:15px;padding-bottom:15px; background-color:white;width:48%;float:left">
				<ui:chart height="350px" width="100%" id="lbpmPersonStatChart2">
  					<ui:source type="AjaxJson">
						{"url":"/sys/lbpmperson/SysLbpmPersonSummary.do?method=listApprovedSummary"}
  					</ui:source>
				</ui:chart>		
		</div>
		<div style="clear: both;"></div>
