<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.km.review.forms.KmReviewMainForm"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<c:set var="__kmReviewMainForm" value="${requestScope[param.formBeanName]}" />
<c:set var="__serviceSource" value="${param.serviceSource}" />


<script type="text/javascript">
   var kmReviewMain={};
   
   <%
   		KmReviewMainForm kmReviewMainForm=(KmReviewMainForm)pageContext.getAttribute("__kmReviewMainForm");
        String docSubject=kmReviewMainForm.getDocSubject();
        pageContext.setAttribute("docSubject",StringUtil.lineEscape(docSubject));
   %>
   
   
    kmReviewMain["docSubject"] = "${lfn:escapeJs(docSubject)}"; //标题
    kmReviewMain["headerItemDatas"] = [
          {name: '<bean:message bundle="km-review" key="kmReviewMain.mobile.category" />', value: '${lfn:escapeJs(requestScope.categoryPath)}'}, <%-- 所属分类  --%>
          {name: '<bean:message bundle="km-review" key="kmReviewMain.fdNo" />', value: '${__kmReviewMainForm.fdNumber}'}, <%-- 申请单编号  --%>
          {name: '<bean:message bundle="km-review" key="kmReviewMain.docCreateTime" />', value: '${__kmReviewMainForm.docCreateTime}'} <%-- 创建时间  --%>
   ];
</script>
<span id='nodeNames' style='display: none;'><kmss:showWfPropertyValues  var='handlerValue' idValue='${__kmReviewMainForm.fdId}' propertyName='handlerName' mobile='true'/></span>

<%-- 文档查看页公共头部   --%>
<div data-dojo-type="mui/header/DingViewHeader" 
	 data-dojo-props='subject:window.kmReviewMain.docSubject,
					 userId:"${__kmReviewMainForm.docCreatorId}",
					 userName:"${lfn:escapeJs(__kmReviewMainForm.docCreatorName)}",
					 serviceSource:"${__serviceSource}",
					 handlerValue:"${handlerValue}",
					 docStatus:"${__kmReviewMainForm.docStatus}",
					 itemDatas:window.kmReviewMain.headerItemDatas'></div>