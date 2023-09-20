<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String[] keys = new String[]{"http:","https:","javascript:","vbscript:"};
	String[] param = new String[]{"nav","main"};
	for(String p : param){
		String v = request.getParameter(p);
		if(StringUtil.isNotNull(v)){
			v = v.trim();
			if(v.startsWith("//") || v.indexOf("\"")>-1 || !v.startsWith("/")){
				throw new RuntimeException("参数"+p+"包含非法字符");
			}
		}
	}
%>
<template:include ref="default.simple">
	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<style>iframe{border:0;}</style>
		<script type="text/javascript">seajs.use(['theme!profile'])</script>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/km/review/km_review_ui/dingSuit/css/tree.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="title">
		<c:if test="${empty HtmlParam.moduleName }">
			高级审批管理后台
		</c:if>
		<c:if test="${not empty HtmlParam.moduleName }">
			<c:out value="${HtmlParam.moduleName}"></c:out>
		</c:if>
	</template:replace>
	<template:replace name="body">
		<c:if test="${HtmlParam.showTopBar=='true' }">
			<c:set var="showTopClass" value="lui_review_showtop" />
			<div class="luiReviewHeadBar luiReviewHeadBarTitleFlag">
				<span><img src="${KMSS_Parameter_ContextPath}km/review/km_review_ui/dingSuit/images/ding_title.png" class="lui_title_config_img"></span>
				<span class="lui_title_config_span">高级审批管理后台</span>
				<span><img src="${KMSS_Parameter_ContextPath}km/review/km_review_ui/dingSuit/images/ding_title_flag_big.png" class="lui_title_config_img_flag"></span>
			</div>
		</c:if>
		<!-- topFrame -->
		<div class="lui_profile_moduleIndex_top" data-frame="topFrame"></div>
		<!-- downFrame -->
		<div class="lui_profile_moduleIndex_down ${showTopClass }" data-frame="downFrame">
			<!-- treeFrame -->
			<div class="lui_profile_moduleIndex_tree" data-frame="treeFrame" >
				<iframe name="treeFrame" class="lui_profile_moduleIndex_treeframe" src="<c:url value="${param.nav}"/>"  frameborder="no" border="0"></iframe>
			</div>
			<!-- ctrlFrame1 -->
			<div class="lui_profile_moduleIndex_ctrl1" data-frame="ctrl1Frame">
				<div class="lui_profile_moduleIndex_ctrlborder"></div>
				<div class="lui_profile_moduleIndex_ctrlbtn toLeft"></div>
				<div class="lui_profile_moduleIndex_ctrlbtn toRight"></div>
			</div>
			<!-- orgFrame -->
			<div class="lui_profile_moduleIndex_org" data-frame="orgFrame">
				<iframe name="orgFrame" class="lui_profile_moduleIndex_orgFrame" src=""  frameborder="no" border="0" style="width: 99%"></iframe>
			</div>
			<!-- ctrlFrame2 -->
			<div class="lui_profile_moduleIndex_ctrl2" data-frame="ctrl2Frame">
				<div class="lui_profile_moduleIndex_ctrlborder"></div>
				<div class="lui_profile_moduleIndex_ctrlbtn toLeft"></div>
				<div class="lui_profile_moduleIndex_ctrlbtn toRight"></div>
			</div>
			<div class="lui_profile_moduleIndex_right" data-frame="rightFrame">
				<div class="lui_profile_moduleIndex_viewStroke"></div>
				<div class="lui_profile_moduleIndex_view">
					<iframe name="viewFrame" class="lui_profile_moduleIndex_viewframe" src="" frameborder="no" border="0"></iframe>
				</div>
				<div class="lui_profile_moduleIndex_ctrl3" data-frame="ctrl3Frame"></div>
				<div class="lui_profile_moduleIndex_doc">
					<iframe name="docFrame" class="lui_profile_moduleIndex_docframe" src=""  frameborder="no" border="0"></iframe>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="${LUI_ContextPath}/km/review/km_review_ui/dingSuit/js/ctrl.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/topic'],function($,topic){
				$(function(){
					//ctrl绑定事件
					$('[data-frame^="ctrl"] > .lui_profile_moduleIndex_ctrlbtn').on('click',function(){
						var ctrlbtn = $(this),
							ctrl = ctrlbtn.parent(),
							direct = -1;
						if(ctrlbtn.hasClass('toLeft')){
							direct = -1;
						}else{
							direct = 1;
						}
						Frame_MoveBy(ctrl.attr('data-frame'),direct);
					});
				});
				//
				topic.subscribe('successReloadPage',function(){
					var iframes = $('iframe');
					for(var i = 0;i < iframes.length;i++){
						var contentWindow = iframes[i].contentWindow;
						if(contentWindow.LUI){
							if(window.console){
								window.console.log('fire evt:successReloadPage');
							}
							contentWindow.LUI.fire({ type: "topic", name: "successReloadPage" });
						}
					}
				});
				
				topic.subscribe('sys.profile.moduleMain.loaded',function(args){
					var iframes = $('iframe');
					for(var i = 0;i < iframes.length;i++){
						var contentWindow = iframes[i].contentWindow;
						if(contentWindow.LUI){
							if(contentWindow.LUI.luihasReady){
								if(window.console){
									window.console.log('fire evt:sys.profile.moduleMain.loaded');
								}
								contentWindow.LUI.fire({ type: "topic", name: "sys.profile.moduleMain.loaded" ,key : args.key });
							}else{
								contentWindow.LUI.ready((function(cw){
									return function(){
										if(window.console){
											window.console.log('fire evt:sys.profile.moduleMain.loaded(ready)');
										}
										cw.LUI.fire({ type: "topic", name: "sys.profile.moduleMain.loaded" ,key : args.key });
									};
									
								})(contentWindow));
							}
						}
					}
					if(args.parentWin && args.parentWin.LUI){
						if(window.console){
							window.console.log('fire evt:sys.profile.moduleMain.change');
						}
						args.parentWin.LUI.fire({ type: "topic", name: "sys.profile.moduleMain.change" , key : args.key });
					}
						
				});
				
			});
		</script>
	</template:replace>
</template:include>