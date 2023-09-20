<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	<%@ include file="/sys/mobile/js/mui/simplecategory/simplecategory_auth.jsp"%>
	<div data-dojo-type="mui/simplecategory/SimpleCategoryHeader"
		data-dojo-props="key:'{categroy.key}',height:'4rem',modelName:'{categroy.modelName}',title:'<bean:message bundle="km-forum" key="dialog.title"/>'"></div>
	<div data-dojo-type="dojox/mobile/ScrollableView"
		data-dojo-mixins="mui/category/_ViewScrollResizeMixin"
		data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}'">
		<ul data-dojo-type="km/forum/mobile/resource/js/ForumCategoryList"
			data-dojo-mixins="km/forum/mobile/resource/js/ForumCategoryItemListMixin"
			data-dojo-props="authCateIds:'${_authIds}',isMul:{categroy.isMul},key:'{categroy.key}',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',
				selType:{categroy.type},modelName:'{categroy.modelName}',authType:'{categroy.authType}'">
		</ul>
	</div>
