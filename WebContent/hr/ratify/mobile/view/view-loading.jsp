<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<c:set var="_sysLoadingForm" value="${requestScope[param.formBeanName]}" />
<div class="muiFlowBack">
<div class="muiFlowInfoW muiFormContent" style="text-align: left;">
	<div class="muiFormSubject">${_sysLoadingForm.docSubject }</div>
	<table class="muiSimple muiFlowTable">
		<tbody>
			<tr>
				<td rowspan="2" class="muiFlowIconTd">
			 		<div class="muiProcessIcon"> <div class="muiProcessImg"> </div>
			    	<div class="muiFlowCreator">
			              ${_sysLoadingForm.docCreatorName }
			      	</div>
				</td>
				<td class="muiFlowSummary">
					<div>${lfn:message('hr-ratify:kmReviewTemplate.fdName') }： ${_sysLoadingForm.fdTemplateName } </div>
					<div>${lfn:message('hr-ratify:kmReviewMain.fdNumber') }： ${_sysLoadingForm.fdNumber } </div>
					<div>${lfn:message('hr-ratify:kmReviewMain.docCreateTime') }： ${_sysLoadingForm.docCreateTime } </div>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<div class="muiFlowInfoW">
	<div class="muiFlowFixedItem">
		<div class="mblScrollableView muiNavbar" style="top: 0px; touch-action: none; height: 4rem;">
			<ul class="muiNavbarContainer">
				<li class="muiNavitem muiNavitemSelected" style="margin-left: 44.5px; margin-right: 44.5px;">
					<span class="muiNavitemSpan">审批内容</span>
				</li>
				<li class="muiNavitem"  style="margin-left: 44.5px; margin-right: 44.5px;">
					<span class="muiNavitemSpan">流程记录</span>
				</li>
			</ul>
		</div>
	</div>
	<div style="height: 200rem;padding-top: 10rem;">
		<%@ include file="/sys/mobile/extend/combin/loading.jsp"%>
	</div>
</div>
</div>