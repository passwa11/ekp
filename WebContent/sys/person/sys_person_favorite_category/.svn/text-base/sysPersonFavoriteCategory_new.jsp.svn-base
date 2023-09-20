<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.person.service.plugin.*" %>

<template:include ref="default.dialog">

	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/person/sys_person_favorite_category/resource/css/new.css?s_cache=${MUI_Cache}"></link>
	</template:replace>

	<template:replace name="content">
		
		<div id="models">
			<div class="searchbar">
				<input placeholder="${lfn:message('sys-person:favorite.setting.search')}" id="modelSearchInput"/>
				<button type="button" id="modelSearchBtn">&nbsp;</button>
			</div>
			<table id="modelList">
			</table>
		</div>
		<iframe id="categories" frameborder="0" width="100%" height="100%" style="display: none;"></iframe>

	</template:replace>
</template:include>

<script>Com_IncludeFile('jquery.js');</script>
<script>

	var categoryModels = <%=FavoriteCategoryHelp.buildCategoryJson() %>;
	
	var selectedModels = '${JsParam.selectedModels}' || '';
	
	$(document).ready(function(){
		
		var modelList = $('#modelList');
		var models = $('#models');
		var categories = $('#categories');
		
		/** 弹窗组件逻辑 **/
		var dialogTitle = $(window.parent.document).find('.lui_dialog_head_left');
		var checkBtn = $(window.parent.document).find('.lui_toolbar_btn_check');
		var backBtn = $(window.parent.document).find('.lui_toolbar_btn_back');
		var closeBtn = $(window.parent.document).find('.lui_toolbar_btn_close');
		

		/** 第一步——选择分类  **/
		function handleFirstStep(){
			dialogTitle.html('<bean:message key="favorite.setting.select.module" bundle="sys-person"/>');
			checkBtn.hide();
			backBtn.hide();
			closeBtn.show();
			
		}
		
		/** 第二步——选择分类项 **/
		function handleLastStep(){
			dialogTitle.html('<bean:message key="favorite.setting.select.item" bundle="sys-person"/>');
			checkBtn.show();
			backBtn.show();
			closeBtn.hide();
		}
		
		backBtn.click(function(){
			models.show();
			categories.hide();
			handleFirstStep();
		});
		
		handleFirstStep();
		
		
		/** 搜索框逻辑 **/
		var modelSearchInput = $('#modelSearchInput');
		
		function handleSearch(){
			var val = modelSearchInput.val();
			if(!val){
				$('.modelListItem').closest('.modelListItem_wrap').show();
			}else {

				$('.modelListItem').each(function(){
					var t = $(this).attr('data-model-name');
					if(t.indexOf(val) > -1){
						$(this).closest('.modelListItem_wrap').show();
					}else{
						$(this).closest('.modelListItem_wrap').hide();
					}
				});
			}
		}
		
		modelSearchInput.bind('keypress',function(e){  
			if(e.keyCode == "13") {
				handleSearch();
			} 
		});
		
		var modelSearchBtn = $('#modelSearchBtn');
		modelSearchBtn.click(function(){
			handleSearch();		
		});
		
		/** 初始化模块列表  **/
		$.each(categoryModels, function(idx, cm){

			if( !cm.dialogJS || selectedModels.indexOf(cm.text) > -1 ){
				return;
			}
			
			var modelListItemWrap = $('<div class="modelListItem_wrap"></div>').appendTo(modelList);
			var modelListItem = $('<div class="modelListItem"/>').appendTo(modelListItemWrap);
			modelListItem.attr('data-model', cm.model);
			modelListItem.attr('data-model-name', cm.text);		
			if(cm.dialogJS.indexOf('simpleCategory') > -1){
				modelListItem.attr('data-cat', 'simple-category');
			}else if(cm.dialogJS.indexOf('category') > -1){
				modelListItem.attr('data-cat', 'category');
			}
	
			$('<span class="modelListItem_dot"></span>').appendTo(modelListItem);
			$('<span class="modelListItem_label">' + cm.text + '</span>').appendTo(modelListItem);
			
		});
		
		modelList.on('click', '.modelListItem', function(){
			handleLastStep();
			
			models.hide();
			categories.show();
	
			var cat = $(this).attr('data-cat');
			
			var model = $(this).attr('data-model');
			var modelName = $(this).attr('data-model-name');
			
			categories.attr('data-model', model);
			categories.attr('data-model-name', modelName);
			
			var authType = 0;
			switch(model) {
				case 'com.landray.kmss.km.imissive.model.KmImissiveSendTemplate': 
				case 'com.landray.kmss.km.imissive.model.KmImissiveReceiveTemplate':
				case 'com.landray.kmss.km.imissive.model.KmImissiveSignTemplate':
				case 'com.landray.kmss.km.review.model.KmReviewTemplate':
					authType = 2;
					break;
				default: 
					authType = 0; 
					break;	
			}
			
			switch(cat){
			
				case 'simple-category': 
					categories.attr('src', Com_Parameter.ContextPath + 'sys/person/sys_person_favorite_category/resource/tmpl/simple-category.jsp?modelName=' + model + '&noFavorite=true&authType=' + authType + '&mulSelect=true');
					break;
				case 'category': 
					categories.attr('src', Com_Parameter.ContextPath + 'sys/person/sys_person_favorite_category/resource/tmpl/category.jsp?modelName=' + model + '&authType=' + authType + '&noFavorite=true&isShowTemp=1&mulSelect=true');
					break;
				default: break;		
			}
					
		});
		
	});	
	
	
</script>

