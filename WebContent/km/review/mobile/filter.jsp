<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.common.service.IBaseService"%>
<%@page import="com.landray.kmss.sys.category.model.SysCategoryMain"%>
<%@page import="com.landray.kmss.km.review.model.KmReviewTemplate"%>

<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4rem'">
	<div data-dojo-type="mui/header/HeaderItem" 
		data-dojo-mixins="mui/folder/_Folder,mui/back/HrefBackMixin"
		data-dojo-props="href:'/km/review/mobile/index.jsp'">
	</div>
	
	<c:choose>
	 	<c:when test="${not empty param.categoryId}">
	 		<%
				String fdCateId = request.getParameter("categoryId");
	 			String fdCategoryName = "";
	 			
	 			SysCategoryMain category = (SysCategoryMain)((IBaseService) SpringBeanUtil.getBean("sysCategoryMainService")).findByPrimaryKey(fdCateId);
	 			
	 			
	 			if (category != null) {
	 				fdCategoryName = category.getFdName();
	 			}else{
	 				KmReviewTemplate kmReviewTemplate = (KmReviewTemplate)((IBaseService) SpringBeanUtil.getBean("kmReviewTemplateService")).findByPrimaryKey(fdCateId);
	 				if (kmReviewTemplate != null) {
		 				fdCategoryName = kmReviewTemplate.getFdName();
	 				}
	 			}
	 			request.setAttribute("cateName", fdCategoryName);
	 		%>
	 		<div data-dojo-type="mui/header/HeaderItem"
				data-dojo-props="label:'${cateName}',referListId:'_filterDataList'">
			</div>
	 	</c:when>
	 	<c:otherwise>
			<div data-dojo-type="mui/header/HeaderItem" 
				data-dojo-props="label:'${param.moduleName}',referListId:'_filterDataList'">
			</div>
		</c:otherwise>
	</c:choose>	
	<div 
		data-dojo-type="mui/header/HeaderItem" 
		data-dojo-mixins="mui/folder/_Folder,mui/syscategory/SysCategoryDialogMixin"
		data-dojo-props="icon:'mui mui-ul',
		    getTemplate:1,
		    selType: 0|1,
			modelName:'com.landray.kmss.km.review.model.KmReviewTemplate',
			redirectURL:'/km/review/mobile/index.jsp?moduleName=!{curNames}&filter=1',
			filterURL:'/km/review/km_review_index/kmReviewIndex.do?method=list&q.fdTemplate=!{curIds}&orderby=docCreateTime&ordertype=down'">
	</div> 
</div>
<div id="scroll" data-dojo-type="mui/list/StoreScrollableView">
	<div
		data-dojo-type="mui/search/SearchBar"
		data-dojo-props="placeHolder:'<bean:message key="button.search"/>',modelName:'com.landray.kmss.km.review.model.KmReviewMain',needPrompt:false,height:'4rem',showLayer:false">
	</div>
	
	<c:choose>
	 	<c:when test="${not empty param.categoryId}">
		    <ul id="_filterDataList"
		    	data-dojo-type="mui/list/JsonStoreList" 
		    	data-dojo-mixins="mui/list/ProcessItemListMixin"
		    	data-dojo-props="url:'/km/review/km_review_index/kmReviewIndex.do?method=list&q.fdTemplate=${param.categoryId}&orderby=docCreateTime&ordertype=down', lazy:false">
			</ul>
	 	</c:when>
	 	<c:otherwise>
		   <ul id="_filterDataList"
		    	data-dojo-type="mui/list/JsonStoreList" 
		    	data-dojo-mixins="mui/list/ProcessItemListMixin"
		    	data-dojo-props="url:'${param.queryStr}',lazy:false">
			</ul>
		</c:otherwise>
	</c:choose>
</div>

