<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.km.review.forms.KmReviewMainForm"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<c:set var="__kmReviewMainForm" value="${requestScope[param.formBeanName]}" />


<script type="text/javascript">
   var kmReviewMain={};
   
   <%
   		KmReviewMainForm kmReviewMainForm=(KmReviewMainForm)pageContext.getAttribute("__kmReviewMainForm");
        String docSubject=kmReviewMainForm.getDocSubject();
        pageContext.setAttribute("docSubject",StringUtil.XMLEscape(StringUtil.lineEscape(docSubject)));
   %>
   
    kmReviewMain["docSubject"] = "${lfn:escapeJs(docSubject)}"; //标题
    kmReviewMain["headerItemDatas"] = [
          {name: '<bean:message bundle="km-review" key="kmReviewMain.mobile.category" />', value: '${lfn:escapeJs(requestScope.categoryPath)}'}, <%-- 所属分类  --%>
          {name: '<bean:message bundle="km-review" key="kmReviewMain.fdNumber" />', value: '${__kmReviewMainForm.fdNumber}'}, <%-- 申请单编号  --%>
          {name: '<bean:message bundle="km-review" key="kmReviewMain.docCreateTime" />', value: '${__kmReviewMainForm.docCreateTime}'} <%-- 创建时间  --%>
   ];
</script>

<%-- 文档查看页公共头部   --%>
<div data-dojo-type="mui/header/DocViewHeader" 
	 data-dojo-props='subject:window.kmReviewMain.docSubject,
					 userId:"${__kmReviewMainForm.docCreatorId}",
					 userName:"${lfn:escapeJs(__kmReviewMainForm.docCreatorName)}",
					 docStatus:"${__kmReviewMainForm.docStatus}",
					 itemDatas:window.kmReviewMain.headerItemDatas'></div>