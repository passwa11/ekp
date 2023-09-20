<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.sunbor.web.tag.Page"%>  
<%@ include file="/sys/ui/jsp/common.jsp" %>
 <%@ include file="/sys/ui/jsp/jshead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head> 
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<script type="text/javascript">
		Com_IncludeFile("common.css","${LUI_ContextPath}/third/mall/resource/css/","css",true);
	</script>
</head>  
<body style="min-width:500px;max-width:920px">  
  <div class="fy-process-preViews-xform">
 	<div class="preViews-title"><span>${dataInfo.docSubject}</span></div>
 	<div class="preViews-desc">
 		<div id="_xform_docContent" _xform_type="rtf">
			<xform:rtf property="docContent" showStatus="view" width="95%" />
		</div>
 	</div>
 	<div class="preViews-sub-title"> 
 		${lfn:message('third-mall:kmReuseCommon.tip.subtitle')}
 	</div>
 	<c:forEach
		items="${subList}"
		var="fd_xform_details_FormItem" varStatus="vstatus">
	 	<div class="preViews-list-content">
	 		<div class="listtile"> 
	 		   	<img src="${KMSS_Parameter_ContextPath}third/mall/resource/images/u226.png" />
				<div class="name" title="${fd_xform_details_FormItem.get('xformName')}" >${fd_xform_details_FormItem.get('xformName')}</div>
	 		</div>
	 	</div>

 	</c:forEach>
 	 
 </div>
 </body>
 </html>
 
	  