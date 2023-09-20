<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="auto">
	<template:replace name="head">
		<template:super/>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/person/resource/css/person.css?s_cache=${LUI_Cache }"/>
		<script>
			seajs.use(['theme!module']);
		</script>
	</template:replace>
	<template:replace name="body">
			<c:if test="${fn:length(myNavs) > 0}">
				<div class="lui_personal_tabHeader">
					<div class="lui_personal_tabHeader_nav" style="margin: 0px auto;${empty HtmlParam['pagewidth'] ? 'width:90%' : lfn:concat('width:',HtmlParam['pagewidth']) };min-width:980px; max-width:${fdPageMaxWidth}">
						<ul>
							<c:forEach var="item" items="${myNavs}" varStatus="varStatus">
								<li name="${item.fdId}" class="<c:if test="${varStatus.index==0 }">current</c:if>" >
									<a href="javascript:void(0)" onclick="onSelectTabItemClick('${item.fdId}',this)"><c:out value="${item.fdName }" escapeXml="true"></c:out></a>
								</li>
							</c:forEach>
						</ul>
					</div>
					
				</div>
				<div class="lui_personal_tabpage_content" style="margin: 0px auto;${empty HtmlParam['pagewidth'] ? 'width:90%' : lfn:concat('width:',HtmlParam['pagewidth']) };min-width:980px; max-width:${fdPageMaxWidth}">
					<c:forEach var="item" items="${myNavs}" varStatus="varStatus1">
						<div class="lui_personal_tabpage_item " id="item_${item.fdId }" style="display:none;">
							<div class="lui_personal_tab_iconList">
								<div class="lui_personal_btnPrev" onclick="leftNavMoving();"><i></i></div>
								<div class="lui_iconList_wrap">
									<ul>
										<c:forEach var="link" items="${item.fdLinks }" varStatus="varStatus">
											<%
												pageContext.getAttribute("varStatus");
											%>
											<c:set var="idx" value="${(varStatus.index+1)%8}"></c:set>
											<c:if test="${idx==0 }">
												<c:set var="idx" value="8"></c:set>
											</c:if>
											<li var-icon="${ link.icon}" var-url="${link.url }" var-target="${link.fdTarget}" class="bgColor_${idx }">
												<span class="<c:if test="${empty link.icon || fn:indexOf(link.icon,'iconfont') == -1 }">iconfont_nav </c:if>${ link.icon}"></span>
												<p>${link.fdName }</p>
											</li>
										</c:forEach>
									</ul>
								</div>
								<div class="lui_personal_btnNext" onclick="rightNavMoving();"><i></i></div>
							</div>
							<ui:iframe id="item_iframe_content_${item.fdId }"></ui:iframe>
						</div>
					</c:forEach>
				</div>
			
	
			</c:if>

		
	
<script>
	seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/util/env'], function($, dialog,topic,env) {
		LUI.ready(function(){
			onLoadInit();
			showContent();
			initNav();
		});
		window.onItemClick = function(selectedNode,frameNodeId){
			var url = $(selectedNode).attr('var-url'),
				icon = $(selectedNode).attr('var-icon'),
				title = $(selectedNode).find('p').text(),
				target = $(selectedNode).attr('var-target');
			
			url = env.fn.formatUrl(url);
			if(target !='_blank'){
				var frameNode = LUI(frameNodeId);
				if(env.fn.isInternalLink(url)){
					url = Com_SetUrlParameter(url,'j_iframe',"true");
					url = Com_SetUrlParameter(url,'j_aside',"false");
					url = Com_SetUrlParameter(url,'navTitle',title);
				}
				
			//	frameNode.element.children('iframe').attr('scrolling','no');
				frameNode.reload(url);
				frameNode.element.show();
				//计算页面最小高度
				setPageMinHeight(frameNode);
				//恢复iframe默认高度
				setTimeout(function(){
					frameNode.$iframeNode.removeAttr('height').css('height','');
					var pIframe = $('#idx_personal_body_iframe',window.parent.document);
					pIframe.children('.lui_widget_iframe').removeAttr('height').css('height','')
				},350);
			}else{
				window.open(url,target);
			}
			
		};
		window.setPageMinHeight = function(frameNode){
			var $header = $('.lui_portal_header',window.parent.document);
			var headerH = $header && $header.length > 0 ? $header.height() : 0;
			
			//var $tabHeader = $('.lui_personal_tabHeader');
			//var tabHeaderH = $tabHeader.height();
			
			//var $iconList = $(selectedNode).parents('.lui_personal_tab_iconList');
			//var iconListH = $iconList.height();
			var screenH = $(window.parent).height();
			var minContentH = screenH-headerH;
			frameNode.element.children('iframe').css('min-height',minContentH);
		};
		
		window.onSelectTabItemClick = function(value,evt){
			var parent = $(evt).parent();
			if(parent.hasClass('current')){
				return;
			}
			$('.lui_personal_tabHeader li.current').removeClass('current');
			parent.addClass('current');
			var id = parent.attr('name');
			$('.lui_personal_tabpage_content .lui_personal_tabpage_item').hide();
			$('#item_' + id).show();
			showContent();
		};
		window.onLoadInit = function(){
			$('.lui_personal_tabpage_item .lui_personal_tab_iconList .lui_iconList_wrap li').click(function(){
				var contentNode = $(this).parents('.lui_personal_tabpage_item');
				if(contentNode && contentNode.length>0){
					if($(this).attr('var-target')!='_blank'){
						contentNode.find('.lui_personal_tab_iconList .lui_iconList_wrap li.current').removeClass('current');
						$(this).addClass('current');
					}
					var frameNodeId = "item_iframe_content_" + contentNode.attr('id').replace('item_','');
					onItemClick(this,frameNodeId);
				}
			});
			//默认选中样式
			$('.lui_personal_tabpage_item').each(function(){
				var liNodes = $(this).find('.lui_personal_tab_iconList .lui_iconList_wrap li');
				for(var i = 0 ;i < liNodes.length;i++){
					var $li = $(liNodes[i]);
					if($li.attr('var-target')!='_blank'){
						$li.addClass('current');
						break;
					}
				}
			});
		};
		
		window.showContent = function(){
			var currentNode = $('.lui_personal_tabHeader .current');
			var currentId = currentNode.attr('name');
			var modelItem = $('#item_' + currentId + ' .lui_iconList_wrap .current');
			if(modelItem && modelItem.length>0)
				onItemClick(modelItem,'item_iframe_content_'+currentId);
			$('#item_' + currentId).show();
		}
		window.leftNavMoving = function(){
			var currentNode = $('.lui_personal_tabHeader .current');
			var currentId = currentNode.attr('name');
			var frame=$("#item_" + currentId + " .lui_iconList_wrap");
			var xl = frame.scrollLeft();
			var scro = xl - 180;
			frame.animate({scrollLeft: scro}, 200);
		};
		window.rightNavMoving = function(){
			var currentNode = $('.lui_personal_tabHeader .current');
			var currentId =currentNode.attr('name');
			var selector = "#item_" + currentId + " .lui_iconList_wrap";
			var frame=$(selector);
			var xl = frame.scrollLeft();
			var scro = xl + 180;
			frame.animate({scrollLeft: scro}, 200);
		};
		window.initNav = function(){
			var currentNode = $('.lui_personal_tabHeader .current');
			var currentId =currentNode.attr('name');
			var selector = "#item_" + currentId + " .lui_personal_tab_iconList";
			//1px兼容处理
			var contentWidth=$(selector).width()-50-1;
			$(".lui_personal_tabpage_item").each(function(){
				$(this).find('.lui_iconList_wrap').width(contentWidth);
				var itemSize = $(this).find('.lui_iconList_wrap li').length;
				var _itemWidth =  $(this).find('.lui_iconList_wrap li').outerWidth(true);
				var itemsWidth = _itemWidth*parseInt(itemSize);
				$(this).find('.lui_iconList_wrap ul').width(itemsWidth);
				if(itemSize * _itemWidth < contentWidth){
					$(this).find('.lui_personal_btnPrev').hide();
					$(this).find('.lui_personal_btnNext').hide();
				}
			});
			
		};
		
	});
</script>
	</template:replace>
</template:include>
