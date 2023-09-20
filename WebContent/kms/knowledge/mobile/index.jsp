<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@page import="com.landray.kmss.util.StringUtil"%>

<template:include ref="mobile.list" tiny="true" canHash="true">
	<template:replace name="title">
		<c:if test="${param.moduleName!=null && param.moduleName!=''}">
			<c:out value="${param.moduleName}"></c:out>
		</c:if>
		<c:if test="${param.moduleName==null || param.moduleName==''}">
			${lfn:message('kms-knowledge:module.kms.knowledge') }
		</c:if>
	</template:replace>

	<template:replace name="head">
		<mui:cache-file name="mui-simpleCate.js" cacheType="md5" />
		<mui:cache-file name="mui-kms-knowledge.js" cacheType="md5" />
		<style>
			#kms-knowledge-view-container .muiComplexLBox .muiComplexLLeft {
				background-size: contain!important;
			}
			#dojox_mobile_View_0{
				display: none !important;
			}

		</style>

        <mui:cache-file name="mui-portal.css" cacheType="md5" />
		<mui:cache-file name="mui-kmsknowledge-portlet-allcountitem.css" cacheType="md5" />
		<mui:cache-file name="mui-kmsknowledge-portlet-mycountitem.css" cacheType="md5" />
		<script>
			var type = "${param.type}";
			if(type != ""){
				var index = 0;
					if("docIsIntroduced" == type){
					index = 2;
				} else  if("docPublishTime" == type || "fdTotalCount" == type){
					index = 1;
				}

				var hrefIndex = location.href.indexOf("?");
				var newHref = location.href.substr(0,hrefIndex);
				newHref+="#path="+index;
				var categoryId = "${param.categoryId}";
				if(categoryId){
					newHref+="&query=q.categoryId%3A"+categoryId+"%3B";
				}
				window.history.replaceState(null,"",newHref);
			}
		</script>
	</template:replace>

	<template:replace name="content">

		<c:import url="/kms/knowledge/mobile/listview.jsp"
			charEncoding="UTF-8">
		</c:import>
		<kmss:auth
			requestURL="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=addTest"
			requestMethod="GET">
			<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
				<li data-dojo-type="mui/tabbar/CreateButton"
					data-dojo-mixins="mui/simplecategory/SimpleCategoryMixin"
					data-dojo-props="icon1:'',createUrl:'/kms/multidoc/mobile/add.jsp?fdTemplateId=!{curIds}',
		  		                     modelName:'com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory',
		  		                     showFavoriteCate: 'true',
		  		                     ___urlParam:'fdTemplateType:1;fdTemplateType:3'
		  		                     ">
					${lfn:message('kms-knowledge:kms.knowledge.4m.create.knowledge') }</li>
			</ul>
		</kmss:auth>

	</template:replace>
</template:include>
