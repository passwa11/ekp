<%@page import="com.landray.kmss.sys.relation.model.SysRelationMain"%>
<%@page import="java.util.List"%>
<%@page
	import="com.landray.kmss.sys.relation.service.ISysRelationMainService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.relation.model.SysRelationEntry" %>
<%@page import="com.landray.kmss.sys.relation.model.SysRelationStaticNew" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	<%
					String modelName = request.getParameter("modelName");
					String modelId = request.getParameter("modelId");
					String fdKey = request.getParameter("fdKey");
					ISysRelationMainService service = (ISysRelationMainService) SpringBeanUtil
							.getBean("sysRelationMainService");
					List<SysRelationMain> list = service.findModel(modelName, modelId, fdKey);
					if(list.size() > 0)  {
						List relations = list.get(0).getFdRelationEntries();
						if(relations != null && relations.size() > 0) {
							boolean isList = true;
							if(relations.size() == 1) {
								SysRelationEntry entry =  (SysRelationEntry)relations.get(0);
								if(entry.getFdType() == 4) {
									List<SysRelationStaticNew> stas = entry.getFdRelationStaticNews();
									if(stas != null && stas.size() == 1) {
										SysRelationStaticNew single = (SysRelationStaticNew)stas.get(0);
										out.print(single.getFdRelatedUrl());
										isList = false;
									}
								}
							}
							if(isList)  {
								request.setAttribute("relations", relations);
						
	%>
	
		<div data-dojo-type="mui/panel/AccordionPanel" 
			data-dojo-props="fixed:false,slide:false"
			class="muiRelationAccordion">
			<c:forEach items="${relations }" var="relation" varStatus="status">
					<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${relation.fdModuleName }',icon:'mui-relaType'">
						<c:if test="${relation.fdType == '6' }">
							<div data-dojo-type="sys/relation/mobile/js/RelationText"
								 data-dojo-props="url:'/sys/relation/relation.do?method=result&currModelId=${param.modelId}&currModelName=${param.modelName}&sortType=time&fdType=${relation.fdType}&moduleModelId=${relation.fdId}&moduleModelName=${relation.fdModuleModelName}&showCreateInfo=true&rowsize=10&fdKey=${param.fdKey }'">
							</div>
						</c:if>
						<c:if test="${relation.fdType != '6' }">
							<ul class="muiRelationStoreList"
								data-dojo-type="mui/list/JsonStoreList"
								data-dojo-mixins="${LUI_ContextPath}/sys/relation/mobile/js/RelationItemListMixin.js"
								data-dojo-props="url:'/sys/relation/relation.do?method=result&forward=mobileList&currModelId=${param.modelId}&currModelName=${param.modelName}&sortType=time&fdType=${relation.fdType}&moduleModelId=${relation.fdId}&moduleModelName=${relation.fdModuleModelName}&showCreateInfo=true&rowsize=10&fdKey=${param.fdKey }',lazy:false">
							</ul>
						</c:if>
					</div> 
			</c:forEach>
		</div>
	<%
					 	 }
					}
				}
	%>
