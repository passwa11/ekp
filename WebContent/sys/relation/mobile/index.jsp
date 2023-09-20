<%@page import="com.landray.kmss.sys.relation.model.SysRelationMain"%>
<%@page import="java.util.List"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@page
	import="com.landray.kmss.sys.relation.service.ISysRelationMainService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.list" tiny="true">
	<template:replace name="head">
		<%
					String modelName = request.getParameter("modelName");
					String modelId = request.getParameter("modelId");
					ISysRelationMainService service = (ISysRelationMainService) SpringBeanUtil
							.getBean("sysRelationMainService");
					List<SysRelationMain> list = service.findModel(modelName, modelId);
					if(list.size()>0)
						request.setAttribute("relations",list.get(0).getFdRelationEntries());
		%>
		
		<mui:cache-file name="mui-rela.css" cacheType="md5"/>
		<mui:cache-file name="mui-rela.js" cacheType="md5"/>
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="mui/view/DocScrollableView" class="muiRelation">
			<div data-dojo-type="mui/panel/AccordionPanel" class="muiRelationAccordion" data-dojo-mixins="mui/panel/_FixedPanelMixin">
				<c:forEach items="${relations }" var="relation" varStatus="status">
					<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${relation.fdModuleName }',icon:'mui-relaType'">
						<c:if test="${relation.fdType != '6' }">
							<ul class="muiRelationStoreList"
								data-dojo-type="mui/list/JsonStoreList"
								data-dojo-mixins="${LUI_ContextPath}/sys/relation/mobile/js/RelationItemListMixin.js"
								data-dojo-props="url:'/sys/relation/relation.do?method=result&forward=mobileList&currModelId=${param.modelId}&currModelName=${param.modelName}&sortType=time&fdType=${relation.fdType}&moduleModelId=${relation.fdId}&moduleModelName=${relation.fdModuleModelName}&showCreateInfo=true&rowsize=10',lazy:false">
							</ul>
						</c:if>
						
						<c:if test="${relation.fdType == '6' }">
							<div data-dojo-type="sys/relation/mobile/js/RelationText"
								 data-dojo-props="url:'/sys/relation/relation.do?method=result&currModelId=${param.modelId}&currModelName=${param.modelName}&sortType=time&fdType=${relation.fdType}&moduleModelId=${relation.fdId}&moduleModelName=${relation.fdModuleModelName}&showCreateInfo=true&rowsize=10&fdKey=${param.fdKey }'">
							</div>
						</c:if>
					</div>
				</c:forEach>
			</div>
		</div>
	</template:replace> 
</template:include>