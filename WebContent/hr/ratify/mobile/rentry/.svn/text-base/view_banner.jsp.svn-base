<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<c:set var="__hrRatifyEntryForm" value="${requestScope[param.formBeanName]}" />
<div class="muiFlowInfoW muiFormContent">
	<div class="muiFormSubject">
		<xform:text property="docSubject"></xform:text>
	</div> 
	<c:if test="${__hrRatifyEntryForm.docStatus eq '30'}">
   		<div class="muiProcessStatus"  <c:if test="${empty param.loading or param.loading == 'false' }">id=processStatusDiv</c:if>  >
       		<i class="mui mui-processPass"></i>
      	</div>
    </c:if>
   	<c:if test="${__hrRatifyEntryForm.docStatus eq '00'}">
    	<div class="muiDiscardStatus" <c:if test="${empty param.loading or param.loading == 'false' }">id="discardStatusDiv"</c:if> >
       		<i class="mui mui-processDiscard"></i>
      	</div>
   	</c:if>
	<table class="muiSimple muiFlowTable" cellpadding="0" cellspacing="0">
		<tr>
			<td rowspan="2" class="muiFlowIconTd">
				 <div class="muiProcessIcon">
                    <img class="muiProcessImg" src='<person:headimageUrl contextPath="true"  personId="${__hrRatifyEntryForm.docCreatorId}" size="m" />'/>
                </div>
                <div class="muiFlowCreator">
                	<xform:text property="docCreatorName"></xform:text>
                </div>
			</td>
			<td class="muiFlowSummary">
				<div>${lfn:message('hr-ratify:hrRatifyTemplate.fdName') }： <xform:text property="docTemplateName"></xform:text> </div>
				<div>${lfn:message('hr-ratify:hrRatifyMain.docNumber') }： <xform:text property="docNumber"></xform:text> </div>
				<div>${lfn:message('hr-ratify:hrRatifyMain.docCreateTime') }： <xform:text property="docCreateTime"></xform:text> </div>
			</td>
		</tr>
	</table>
</div>
<c:if test="${not empty param.loading and param.loading == 'true' }">
	<div style="height: 100%;padding-top: 10rem;">
		<%@ include file="/sys/mobile/extend/combin/loading.jsp"%>
	</div>
</c:if>