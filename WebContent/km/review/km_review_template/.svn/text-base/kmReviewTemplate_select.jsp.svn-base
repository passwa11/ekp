<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit" sidebar="no">
	 <template:replace name="head">
		<script type="text/javascript">
			seajs.use(['theme!profile']);
			seajs.use(['theme!iconfont']);
			Com_IncludeFile("formList.css","${LUI_ContextPath}/km/review/km_review_template/resource/","css",true);
		</script>
	 </template:replace>

	  <template:replace name="content">
	  	<script>
	  		var parentId = '${parentId}';
	  		var netWork_reachable = '${netWork_reachable}';
	  		var version = '${version}';
	  		var extensions = '${extArr}';
	  		var __absPath = '${__absPath}';
	  		var isAuth = '${isAuth}';
	  		var modelLang = {
	  				enterTemplateCenter : '<bean:message key="km-review:kmReviewTemplate.enterTemplateCenter"/>',
	  				authorize : '<bean:message key="km-review:kmReviewTemplate.authorize"/>',
	  				wordTitle : '<bean:message key="km-review:kmReviewTemplate.wordTitle"/>',
	  				rtfTitle : '<bean:message key="km-review:kmReviewTemplate.rtfTitle"/>',
	  				xformTitle : '<bean:message key="km-review:kmReviewTemplate.xformTitle"/>',
	  				xformDesc : '<bean:message key="km-review:kmReviewTemplate.xformDesc"/>',
	  				multiXformTitle : '<bean:message key="km-review:kmReviewTemplate.multiXformTitle"/>',
	  				createByTemplateCenter : '<bean:message key="km-review:kmReviewTemplate.createByTemplateCenter"/>',
	  				createByTemplateCenterDesc : '<bean:message key="km-review:kmReviewTemplate.createByTemplateCenterDesc"/>',
	  				noNetWork : "<bean:message key='km-review:kmReviewTemplate.noNetWork'/>",
	  				noNetWorkDesc : '<bean:message key="km-review:kmReviewTemplate.noNetWorkDesc"/>',
	  				noAuth : '<bean:message key="km-review:kmReviewTemplate.noAuth"/>',
	  		};
	  	</script>
	  	<div class="cm-hot-template">
	  		<!-- 模式 -->
	  		<div class="hot-template-top">
			  	<div id="modeList" class="lui_mode_content" data-lui-type="km/review/km_review_template/resource/formListDataView!FormListDataView" style="display:none;">
					<ui:source type="Static">
						${mode}
					</ui:source>
					<ui:render type="Javascript">
						<c:import url="/km/review/km_review_template/resource/modeRender.js" charEncoding="UTF-8"></c:import>
					</ui:render>
				</div>
			</div>
			<div class="hot-template-main">
				<!-- 新建模板url -->
			  	<input type="hidden" name="add_url" value='<c:out value="km/review/km_review_template/kmReviewTemplate.do?method=add&parentId=${parentId}"></c:out>' />
			  	<!-- tab页签 -->
			  	 <div class="lui_template_tabs lui_main_content">
		               <ui:tabpanel scroll="true" id="templateTabPanel">
		                   <c:forEach items="${extensions}" var="extension" varStatus="vstatus">
		                    <ui:content title="${extension.sourceName}">
		                        <ui:iframe id="template_listview_${extension.sourceUUID}"
		                                   src="${LUI_ContextPath }/${extension.sourceURL}?fdMainModelName=${JsParam.fdMainModelName}&fdKey=${JsParam.fdKey}&fdModelName=${JsParam.fdModelName}&fdType=${extension.sourceUUID}&parentId=${parentId}&version=${version}"></ui:iframe>
		                    </ui:content>
		  	 			</c:forEach>
		               </ui:tabpanel>
		         </div>
	         </div>
           </div>
	  </template:replace>
</template:include>