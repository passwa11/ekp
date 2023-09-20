<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>

<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.list" tiny="true">
	<template:replace name="head">
	
		<mui:cache-file name="mui-eval.css" cacheType="md5"/>
		<mui:cache-file name="mui-eval.js" cacheType="md5"/>
		<mui:cache-file name="mui-form.js" cacheType="md5"/>
		
		<c:set var="s_requestUrl" value="/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=add&fdModelName=${param.modelName}&fdModelId=${param.modelId}" />
		<script type="text/javascript">
			require([ "dojo/topic", "dojo/dom","dojo/query", "dojo/dom-construct","dijit/registry","dojo/domReady!" ], function(
					topic, dom, query,domConstruct,registry) {

				// 没数据给最小高度
				topic.subscribe('/mui/list/noData',function(){
					var muiOpt = registry.byId('muiEvalOpt');
					if (muiOpt)
						muiOpt.showMask();
					
					var h = dom.byId('eval_scollView').offsetHeight
							- dom.byId('eval_header').offsetHeight - 20;
					query('.muiListNoData').style({
						'line-height' : h + 'px',
						'height' : h + 'px'
					});
				});

				// 校验点评回复字符数
				window.validateReplyPost = function(popup){
					var areaDom = popup.textClaz.domNode;
					var replyContent = "";
					var muiEditorTextarea = query("div.muiEditorTextarea",areaDom);
					if(muiEditorTextarea.length > 0) {
						replyContent = muiEditorTextarea[0].innerHTML;
					}
					var muiEditorPlugin = query("div.muiEditorPlugin",areaDom);
					if(muiEditorPlugin.length > 0) {
						var beyondLen = query("span[name=beyondLen]",muiEditorPlugin[0]);
						if(beyondLen.length < 1)
							domConstruct.create("span", {"name":"beyondLen","style":{"color":"red","float":"right","padding-right":"30px"}}, muiEditorPlugin[0]);
					}
					replyContent = replyContent.replace(/<img[^>]*src[^>]*>/ig,"[face]");//过滤表情
					var leng = replyContent.length;
					var l = 0;
					var limitNum = 1000;
					var flag = false;
					for (var i = 0; i < leng; i++) {
						if (replyContent[i].charCodeAt(0) < 299)
							l++;
						else
							l += 2;
					}
					if (l <= 2 * limitNum && l > 0)
						flag = true;

					if (flag) {
						query("span[name=beyondLen]",muiEditorPlugin[0])[0].innerHTML = "";
					}else{
						query("span[name=beyondLen]",muiEditorPlugin[0])[0].innerHTML = Math.ceil((limitNum * 2 - l) / 2);
					}

					return flag;
				}
			});
		</script>
	</template:replace>
	<template:replace name="content">
		
		<div id="eval_scollView"
			data-dojo-type="mui/view/DocScrollableView"
			data-dojo-mixins="mui/list/_ViewPushAppendMixin,mui/list/_ViewPullReloadMixin">
			<div data-dojo-type="sys/evaluation/mobile/js/EvaluationHeader" id="eval_header"
			 	data-dojo-props="fdModelId:'${HtmlParam.modelId}',fdModelName:'${HtmlParam.modelName}'">
			</div>
			
			<ul class="muiEvalStoreList" data-dojo-type="mui/list/JsonStoreList"
				data-dojo-mixins="sys/evaluation/mobile/js/EvaluationItemListMixin"
				data-dojo-props="url:'/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=list&forward=mobileList&fdModelId=!{fdModelId}&fdModelName=!{fdModelName}&type=top',lazy:false,fdModelName:'${HtmlParam.modelName}',fdModelId:'${HtmlParam.modelId}'">
			</ul> 
		</div>
		<kmss:auth requestURL="${s_requestUrl}" requestMethod="GET">
			<div style="display: none;" id="eval_formData">
				<input type="hidden" name="fdId" /> 
				<input type="hidden" name="fdEvaluatorName" value="${KMSS_Parameter_CurrentUserName}" />
				<input type="hidden" name="fdEvaluationTime" /> 
				<input type="hidden" name="fdKey" value="${HtmlParam.key}" /> 
				<input type="hidden" name="fdModelId" value="${HtmlParam.modelId}" /> 
				<input type="hidden" name="fdModelName" value="${HtmlParam.modelName}" /> 
				<input type="hidden" name="fdEvaluationScore" value="1" /> 
			</div>
		</kmss:auth>

		<ul data-dojo-type="mui/tabbar/TabBar" id="eval_tabbar" fixed="bottom">
			<kmss:auth requestURL="${s_requestUrl}" requestMethod="GET">
					<div class="muiEvalOpt" 
						data-dojo-type="${LUI_ContextPath}/sys/evaluation/mobile/js/Eval.js"
						data-dojo-props="attModelId:'<%=IDGenerator.generateID() %>',notifyOtherName:'${HtmlParam.notifyOtherName}',notifyOtherNameText:'${HtmlParam.notifyOtherNameText}'"
						id="muiEvalOpt">
						<input type="text" readonly="readonly" class="muiEvalText" placeholder='<bean:message bundle="sys-evaluation" key="mui.sysEvaluation.mobile.reply"/>' />
					</div>
			</kmss:auth>
		</ul>
	</template:replace>
</template:include>