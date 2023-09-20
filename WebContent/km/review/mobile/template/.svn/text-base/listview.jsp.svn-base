<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<kmss:ifModuleExist path="/third/mall/">
	<kmss:authShow roles="ROLE_KMREVIEW_CREATE">
		<div class="mui-goCreate" data-dojo-type="km/review/mobile/resource/js/button/ReuseTemplateGoCreate" 
			data-dojo-props="href:'/third/mall/thirdMallTemplate.do?method=index',fdName:'<bean:message bundle="km-review" key="mui.kmreviewTemplate.createByThirdMall" />',
							fdDesc:'<bean:message bundle="km-review" key="mui.kmreviewTemplate.createByThirdMallDesc" />',iconClass:'icon',
							__createUrl:'/km/review/km_review_template/kmReviewTemplate.do?method=add&sourceFrom=Reuse&sourceKey=Reuse&type=2'">
		</div>
	</kmss:authShow>
</kmss:ifModuleExist>

<kmss:authShow roles="ROLE_KMREVIEW_CREATE">
	<div class="mui-template-created"> 
		<div class="muiTemplateListHead">
			<div class="muiTemplateListTitle muiFontSizeM"><bean:message bundle="km-review" key="mui.kmreviewTemplate.created" /></div>
		</div>
		<%--  页签内容展示区域，可纵向上下滑动   --%>
			<div data-dojo-type="mui/list/StoreElementScrollableView">
				<%--  默认列表模板   --%>
				<ul class="muiList"
					data-dojo-type="mui/list/JsonStoreList" data-dojo-props="url:'/km/review/km_review_template/kmReviewTemplate.do?method=list&orderby=docCreateTime&ordertype=down',lazy:false"
					data-dojo-mixins="km/review/mobile/resource/js/list/ReviewTemplateItemListMixin">
				</ul>
			</div>
	</div>
</kmss:authShow>
