<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="java.util.Date"%>
<template:include ref="default.archive" sidebar="no">
<template:replace name="head">
</template:replace>
<template:replace name="title">
	<c:out value="${modelingAppSimpleMainForm.docSubject }"></c:out>
</template:replace>
<template:replace name="toolbar">
</template:replace>
<template:replace name="content">
<c:set var="p_defconfig" value="${p_defconfig}" scope="request"/>
<form name="modelingAppSimpleMainForm" method="post" action="<c:url value="/sys/modeling/main/modelingAppSimpleMain.do"/>">
<center>
<div class="print_title_header">
	<p id="title" class="print_txttitle">${modelingAppSimpleMainForm.docSubject }</p>
	<div class="printDate">
	  <bean:message bundle="sys-modeling-base" key="kmReviewMain.transferDate" />:<% out.print(DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATE, request.getLocale()));%>
	</div>
</div>
<div id="printTable" style="border: none;">
<div printTr="true" style="border: none;">

<%-- 基本信息 width="650px" --%>
<div>
    <div class="tr_label_title"> 
       <div class="title">
      	 <bean:message bundle="sys-modeling-base" key="kmReviewDocumentLableName.baseInfo" />
       </div>
    </div>
	<table class="tb_normal" width=100%>
		<!--主题-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-modeling-base" key="kmReviewMain.docSubject" /></td>
			<td colspan=3>
				${modelingAppSimpleMainForm.docSubject }
			</tr>
		<!--模板名称-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-modeling-base" key="kmReviewTemplate.fdName" /></td>
			<td colspan=3><bean:write name="modelingAppSimpleMainForm"
				property="fdModelName" /></td>
		</tr>
		<!--申请人-->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-modeling-base" key="kmReviewMain.docCreatorName" />
			</td>
			<td width=35%>
				<html:hidden name="modelingAppSimpleMainForm" property="docCreatorName" /> 
				<bean:write name="modelingAppSimpleMainForm" property="docCreatorName" />
			</td>
		</tr>
	</table>
</div>

<%-- 审批内容--%>
<div>
    <div class="tr_label_title">
	    <div class="title">
	       <bean:message bundle="sys-modeling-base" key="kmReviewDocumentLableName.reviewContent" />
	    </div>
    </div> 
	<table id="info_content" width=100% >
		<tr>
			<td id="_xform_detail">
					${lfn:message('sys-modeling-main:modeling.form') }
				<c:import url="/resource/html_locate/sysForm.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="modelingAppSimpleMainForm" />
					<c:param name="fdKey" value="reviewMainDoc" />
					<c:param name="messageKey" value="sys-modeling-base:kmReviewDocumentLableName.reviewContent" />
					<c:param name="useTab" value="false" />
					<c:param name="isPrint" value="true" />
				</c:import>
			</td>
		</tr>
	</table>
</div>
</div>
</div>

</center>
</form>
<script>
Com_AddEventListener(window,"load",function(){
	var colspan = $("[fd_type='detailsTable']").closest("td").attr("colspan");
	$("#_xform_detail").attr("colSpan",colspan); 
})
</script>
</template:replace>
		
</template:include>

