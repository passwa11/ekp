<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<c:set var="kmArchivesMainForm" value="${requestScope[param.formBeanName]}" />
		<div class="muiFlowInfoW muiFormContent">
				<div class="muiFormSubject">
						<xform:text property="docSubject"></xform:text>
				</div> 
				 <c:if test="${kmArchivesMainForm.docStatus eq '30'}">
	                <div class="muiProcessStatus"  <c:if test="${empty param.loading or param.loading == 'false' }">id=processStatusDiv</c:if>>
	                    <i class="mui mui-processPass"></i>
	                </div>
                </c:if>
                 <c:if test="${kmArchivesMainForm.docStatus eq '00'}">
	                <div class="muiDiscardStatus" <c:if test="${empty param.loading or param.loading == 'false' }">id="discardStatusDiv"</c:if>>
	                    <i class="mui mui-processDiscard"></i>
	                </div>
                </c:if>
				<table class="muiSimple muiFlowTable" cellpadding="0" cellspacing="0">
					<tr>
						<td rowspan="2" class="muiFlowIconTd">
							 <div class="muiProcessIcon">
			                    <img class="muiProcessImg" src='<person:headimageUrl contextPath="true"  personId="${kmArchivesMainForm.docCreatorId}" size="m" />'/>
			                </div>
			                <div class="muiFlowCreator">
			                	<xform:text property="docCreatorName"></xform:text>
			                </div>
						</td>
						<td class="muiFlowSummary">
							<div>${lfn:message('km-archives:kmArchivesMain.docTemplate') }：<xform:dialog propertyId="docTemplateId" propertyName="docTemplateName" showStatus="view"></xform:dialog></div>
						    <div>${lfn:message('km-archives:kmArchivesMain.docNumber') }：  <xform:text property="docNumber" showStatus="view" /></div>
						     <div>${lfn:message('km-archives:kmArchivesMain.fdFileDate') }：  <xform:text property="fdFileDate" showStatus="view" /></div>
							<div>${lfn:message('km-archives:kmArchivesMain.fdValidityDate') }：<xform:datetime property="fdValidityDate" showStatus="view" /></div>
						</td>
					</tr>
				</table>
			</div>
<c:if test="${not empty param.loading and param.loading == 'true' }">
	<div style="height: 100%;padding-top: 10rem;">
		<%@ include file="/sys/mobile/extend/combin/loading.jsp"%>
	</div>
</c:if>