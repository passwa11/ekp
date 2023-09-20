<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.ui.plugin.SysUiTools"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	request.setAttribute("themePath", SysUiPluginUtil.getThemePath(request));
%>

<template:include ref="default.simple">
	<template:replace name="title">选择Icon</template:replace>
	<template:replace name="head">
		<template:super/>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}" />
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/ui/help/font/css/iconfont.css?s_cache=${LUI_Cache }"/>
	</template:replace>
	<template:replace name="body">
		<div class="lui_iconList">
			<div class="lui_profile_iconSelect_header">
				
				<div class="lui_profile_iconSelect_tab">
					<ul name="iconType" id="iconType">
						<li class="status_current" data-target="module"><bean:message bundle="sys-ui" key="ui.iconfont.system"/></li>
					</ul>
				</div>
				
				<div class="lui_profile_iconSelect_search">
					<div class="lui_profile_iconSelect_searchbox">
						<input type="text" class="lui_profile_iconSelect_searchinput" placeholder="${lfn:message('sys-modeling-base:modeling.keyword.search.icon')}"/>
						<a href="javascript:void(0)" class="lui_profile_iconSelect_searchbtn"></a>
					</div>
				</div>
			</div>
			<div class="lui_profile_iconSelect_content">
				<div class="lui_profile_iconSelect_list">
				</div>
			</div>
		</div>
			
		<script>

			function onIconClick(title){
				window.$dialog.hide(title);
			}
			function loadDatas(type){
				var ulNode = $('<ul />');
				$('.lui_profile_iconSelect_content .lui_profile_iconSelect_list').empty().append(ulNode);
				// 加载扩展配置文件
				if(type == 'ext') {
					
					$.getJSON('${LUI_ContextPath}/${themePath}json/ext.json', function(result) {
						ulNode.append(buildList(result.extIconFont.iconList, 'lui_iconfont_ext_', 'iconfont_ext'));
					});
					
				} else if (type == 'module') {
					
					$.getJSON('${LUI_ContextPath}/sys/ui/help/font/jsp/nav.jsp', function(result) {
						if(!result) {
							return;
						}
						ulNode.append(buildList(getIconList(result),result.prefix,result.iconClass));
					});
				} else if(type == 'model'){
					$.getJSON('${LUI_ContextPath}/sys/ui/help/font/jsp/navLeft.jsp', function(_result) {
						if(!_result) {
							return;
						}
						ulNode.append(buildList(getIconList(_result),_result.prefix,_result.iconClass));
					});		
				}
				
			}
			function buildList(datas,prefix,iconClass){
				var list = $(document.createDocumentFragment());
				for(var i = 0; i < datas.length;i++){
					var className = datas[i].className;
					var liNode = $('<li />').append($('<span />').addClass(iconClass + " " + prefix + className));
					liNode.attr('title',datas[i].name);
					(function(ele){
						seajs.use(['lui/pinyin'], function (Pinyin) {
							ele.attr('title-pinyin', Pinyin.convertToPinyin(ele.attr('title')));
						});
					})(liNode);
					$('<p/>').text(datas[i].name).appendTo(liNode);
					liNode.appendTo(list);
					liNode.click(function(){
						window.$dialog.hide($(this).find('span').attr('class'));
					});
				}
				return list;
			}
			function getIconList(result){
				var newIcons = [];
				var len = result.moduleList.length;
				for(var i = 0; i < len;i++){
					var record = result.moduleList[i];
					for(var idx in record.iconList){
						newIcons.push(record.iconList[idx]);
					}
				}
				return newIcons;
			}
			function searchIcon(value){
				$('.lui_profile_iconSelect_content .lui_profile_iconSelect_list li').each(function(){
					
					if(!value){
						$(this).show();
					}else {
						var title = $(this).attr('title') || '';
						var titlePinyin = $(this).attr('title-pinyin') || '';
						
						if(title.indexOf(value) > -1 || titlePinyin.indexOf(value.toLowerCase()) > -1){
							$(this).show();
						}else {
							$(this).hide();
						}
					}
					
				});
			}
			LUI.ready(function(){
				// type:ext|module|model
				// type:ext|module|model
				loadDatas('<c:out value="${param.type}" />');
				
				LUI.$('#iconType').on('click', 'li', function(e){
					
					$('#iconType li').removeClass('status_current');
					$(this).addClass('status_current');
					
					var target = $(this).attr('data-target');
					loadDatas(target);				
				});
				
				
				$('.lui_profile_iconSelect_search .lui_profile_iconSelect_searchinput').keyup(function(r){
					var value = $.trim($(this).val());
					searchIcon(value);
				});

				$.getJSON('${LUI_ContextPath}/${themePath}json/ext.json', function(result) {
					if(result.extIconFont) {
						//$('<option value="ext">扩展图标</option>').appendTo($('#iconType'));
						$('<li data-target="ext">${lfn:message('sys-modeling-base:modeling.extension.icon')}</li>').appendTo($('#iconType'));
					}
				});
				
			});
		</script>
	</template:replace>
</template:include>