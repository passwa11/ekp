<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:if test="${JsParam.enable ne 'false'}">
	<c:set var="order" value="${empty param.order ? '70' : param.order}"/>
	<c:set var="disable" value="${empty param.disable ? 'false' : param.disable}"/>
	<c:set var="expand" value="${empty param.expand ? 'false' : param.expand}"/>
	<ui:content title="${ lfn:message('kms-multidoc:kmsMultidocSubside.tab.record') }"  cfg-order="${order}" cfg-disable="${disable}" titleicon="${not empty param.titleicon?param.titleicon:''}" expand="${expand }">
	<list:listview>
		<ui:source type="AjaxJson">
			{url:'/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=subsideListBack&q.fdDelFlag=0&orderby=docPublishTime&ordertype=down&status=all&forwordPage=data&subModelId=${param.fdId}'}
		</ui:source>
		<list:colTable rowHref="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=view&fdId=!{fdId}"
		 isDefault="true" layout="sys.ui.listview.columntable" cfg-norecodeLayout="${param.norecodeLayout !=null and param.norecodeLayout != ''?param.norecodeLayout:'simple'}">
			<list:col-serial></list:col-serial>
			<list:col-auto props="docSubject;docCreator.fdName;docDept.fdName;docStatus;docCreateTime;"></list:col-auto>
		</list:colTable>
		<list:paging></list:paging>
	</list:listview>
	</ui:content>
</c:if>