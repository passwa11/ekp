<%@page import="com.landray.kmss.sys.config.design.SysCfgFlowDef"%>
<%@page import="com.landray.kmss.sys.config.design.SysConfigs"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<template:include ref="default.simple" rwd="true">
	
	<template:replace name="body" >
	<script type="text/javascript">
		seajs.use(['theme!list','sys/lbpmperson/style/css/docreater.css#']);	
	</script>
	
	<%
		String mainModelName = request.getParameter("mainModelName");
		if(StringUtil.isNotNull(mainModelName)){
			SysDictModel dict = SysDataDict.getInstance().getModel(mainModelName);
			String url = dict.getUrl();
			if(StringUtil.isNotNull(url) && url.indexOf(".do")>=0){
				String addUrl = url.substring(0, url.indexOf(".do"))
						+ ".do";
				SysCfgFlowDef sysCfgFlowDef = SysConfigs.getInstance()
								.getFlowDefByMain(mainModelName);
				//客户端定义的key
				if(StringUtil.isNotNull(request.getParameter("customTemplateKey"))){
					addUrl += "?method=add&"+request.getParameter("customTemplateKey")+"=!{id}";
				}
				else if(StringUtil.isNotNull(sysCfgFlowDef.getCreateDocTemplateIdName())){
					addUrl += "?method=add&"+sysCfgFlowDef.getCreateDocTemplateIdName()+"=!{id}";
				}
				else{
					addUrl += "?method=add&fdTemplateId=!{id}&.fdTemplate=!{id}&i.docTemplate=!{id}";
				}
				
				if (!addUrl.startsWith("/")) {
					addUrl += "/";
				}
				request.setAttribute("addUrl", addUrl);
			}
		}
	%>

		
		<script>
		// 最常用的流程
		var isFrequentEmpty = false;
		// 最近使用的流程
		var isRecentEmpty = false;
		</script>
  		<%--
  			<%@ include file="/sys/lbpmperson/import/category_search.jsp"%>
  			 --%>
  			<%@ include file="/sys/lbpmperson/import/usualCate2.jsp"%>
  			
  			<c:if test="${not empty  JsParam.mainModelName and not empty  JsParam.cateType}">
  				<%@ include file="/sys/person/sys_person_favorite_category/favorite_category_flat.jsp"%>
  			</c:if>
  			
  			<c:if test="${empty  JsParam.mainModelName}">
	  			<ui:dataview>
					<ui:source type="AjaxJson">
					    {"url":"/sys/lbpmperson/SysLbpmPersonCreate.do?method=listUsual&type=frequent"}
					</ui:source>
					<ui:render type="Template">
						<c:import url="/sys/lbpmperson/style/tmpl/listTemplate.jsp" charEncoding="UTF-8"></c:import>
					</ui:render>
					<ui:event event="load">
						//dataview加载完成后，若无最常用的流程，且选择的所有模块，则修改最常用的流程是否为空的状态
						try {
							isFrequentEmpty = arguments[0].source.data.list.length == 0 && ${empty  JsParam.mainModelName};
						} catch (e) {
						
						}
						//若无最常用的流程，且无最近使用流程，则修改定位
						if(isFrequentEmpty && isRecentEmpty && parent.isFrist){
							changeSelectedIfEmpty();
						}
					</ui:event>
				</ui:dataview>
  				
  			</c:if>
  		<script>
  			//流程发起页面为空的时候，默认定位到流程管理模块，如果没有流程管理模块，则默认定位第一个模块
			function changeSelectedIfEmpty(){
				parent.isFrist = false;
				var modelName = "";
				if("com.landray.kmss.km.review.model.KmReviewMain" in parent._mapModuleConfigInfo){
					modelName = "com.landray.kmss.km.review.model.KmReviewMain";
				}else{
					if(!$.isEmptyObject(parent._mapModuleConfigInfo)){
						modelName = $("#search_module",parent.document).children(":nth-child(2)").val();
					}
				}
				if(modelName){
					$("#search_module",parent.document).val(modelName);
					parent.selectByModelName(modelName);
				}
			}
  			var enterKeywordFirst = '<bean:message key="lbpmperson.createDoc.search.enterKeywordFirst" bundle="sys-lbpmperson"/>';
			Com_IncludeFile("listRelationDoc.js",Com_Parameter.ContextPath + "sys/lbpmperson/resource/js/","js",true);
		</script>
	</template:replace> 
</template:include>