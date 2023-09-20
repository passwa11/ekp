<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.StringUtil,java.util.*"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSettingDefault"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	request.setAttribute("xxx", Math.random());
%>
<template:include ref="default.simple" sidebar="no" >
	<template:replace name="body"> 
	<META HTTP-EQUIV="pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<script>seajs.use(['theme!portal']);</script>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/notify/resource/css/notify.css?v=1.9"/>
<script src="${KMSS_Parameter_ResPath}js/domain.js"></script>
<script type="text/javascript">
Com_IncludeFile("jquery.js");
</script>
<c:set var="totalrows" value="${queryPage.totalrows}" />
<c:if test="${param.isShowBtLable!=0}">
	<div id='buttonDiv'>
		<nobr>
			<%-- “待审”和“暂挂” --%>
			<input type="button" value='<bean:message bundle="sys-notify" key="sysNotifyTodo.type.toDo" />(${toDoCount})'
				class='<c:choose><c:when test="${param.fdType == 1 || empty param.fdType && empty param.finish}">labelbg1</c:when><c:otherwise>labelbg2</c:otherwise></c:choose>'
				onclick='window.open("<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=list&home=1&rowsize=10&forKK=${JsParam.forKK}" />","_self");'>
			<input type="button" id="notifyBtn2" value='<bean:message bundle="sys-notify" key="sysNotifyTodo.type.toView" />(${toViewCount})'
				class='<c:if test="${param.fdType == 2}">labelbg1</c:if><c:if test="${param.fdType != 2}">labelbg2</c:if>'
				onclick='window.open("<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=list&fdType=2&rowsize=10&home=1&forKK=${JsParam.forKK}" />","_self");'>
			<c:if test='${empty param.forKK }'>
			<input type="button" value='<bean:message bundle="sys-notify" key="sysNotifyTodo.type.done" />'
				class='<c:if test="${param.finish == 1}">labelbg1</c:if><c:if test="${param.finish != 1}">labelbg2</c:if>'
				onclick='window.open("<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=list&finish=1&rowsize=10&home=1" />","_self");'>
			</c:if>
		</nobr>
	<div style='font-size:8px'>&nbsp;</div>
	</div>
</c:if>
	<div class="lui_dataview_classic" >
			<!-- 如果是被KK集成，则显示最后登录时间和IP 苏轶 2010年6月28日 -->
			<c:if test="${param.forKK == 'true' }">
				<div class="lui_dataview_classic_row clearfloat">
					<div class="lui_dataview_classic_textArea clearfloat">
						<bean:message bundle="sys-notify" key="sysNotifyTodo.last.login.time" />
						<span style='color:#ff3300'>
							<kmss:showDate value="${lastLoginInfo.fdCreateTime }" type="datetime"></kmss:showDate>
						</span>
						<br>
						<bean:message bundle="sys-notify" key="sysNotifyTodo.last.login.IP" />
						<span style='color:#ff3300'>
							${lastLoginInfo.fdIp }
						</span>
					</div>
				</div>
			</c:if>
			<!-- 如果是被KK集成，则显示最后登录时间和IP 苏轶 2010年6月28日 END -->

			<% /**  消息部件顶部提醒内容（  “您有 N 条 需处理事项” 、邮件数量、 刷新图标 、快速审批图标   ）    **/ %>
			<c:if test="${portletType!='category' }">
                    <%@ include file="/sys/notify/portal/sysNotifyTodo_home_remind.jsp"%>
			</c:if>
	
			<!-- 待办显示类型-->
					<c:forEach var="model" items="${queryPage.list}" varStatus="status">
						<div class="lui_dataview_classic_row clearfloat lui_notify_todo">
							<span class="lui_notify_title_icon lui_notify_content_<c:choose><c:when test="${param.finish == 1 }">${model.fdType}</c:when><c:otherwise>${model.fdType}</c:otherwise></c:choose>" style="display:none;"></span>
				 			<div class="lui_dataview_classic_textArea clearfloat">
							<c:choose>
								<c:when test="${param.finish == 1}">
									<!-- <span id="lui_notify_title"> #161939 待办列表重叠问题-->
										<a title='<c:out value="${model.subject4View}"/>' class="lui_dataview_classic_link"  target="${target}"<c:if test="${not empty model.fdLink}">  onclick="Com_OpenNewWindow(this)" data-href="<c:url value="${model.fdLink}"/>"</c:if>>
											<span class="lui_notify_done_flag">${lfn:message('sys-notify:sysNotifyTodo.subject.done')}</span>
											<c:if test="${showAppHome==1}">
												<c:set var="appName" value="${model.fdAppName}"/>
												<c:if test="${appName!=null && appName!='' }">
													<bean:message bundle="sys-notify" key="sysNotifyTodo.todo.appTitle.left" /><c:out value="${appName}"/><bean:message bundle="sys-notify" key="sysNotifyTodo.todo.appTitle.right" />
												</c:if>
											</c:if>
											<c:if test="${fn:contains(model.fdExtendContent,'lbpmPress')}"> 
						                 		<span class="lui_notify_level lui_notify_level1">${lfn:message('sys-notify:sysNotifyTodo.process.press')}</span>
						                 	</c:if> 
											<c:if test="${model.fdType==3}"><span class="lui_notify_pending">${lfn:message('sys-notify:sysNotifyTodo.type.suspend')} </span></c:if>
											<c:if test="${model.fdLevel==1}"><span class="lui_notify_level lui_notify_level1">${lfn:message('sys-notify:sysNotifyTodo.level.title.1')}</span></c:if>
											<c:if test="${model.fdLevel==2}"><span class="lui_notify_level lui_notify_level2">${lfn:message('sys-notify:sysNotifyTodo.level.title.2')}</span></c:if>
											<c:out value="${model.subject4View}"/> 
									 	</a>
									 	<c:if test="${map[status.index] !='' && map[status.index] !=null}">
										 	<span class="lui_dataview_classic_new" style="display:none;"></span>
										 </c:if>
									 	<c:if test="${showFdCreateTime !='' && showFdCreateTime !=null}">
										 	<span class="lui_dataview_classic_created">${fn:substring(model.fdCreateTime,0,10)}</span>
										 </c:if>
										 <c:if test="${showDocCreator !='' && showDocCreator !=null}">
											<span class="lui_dataview_imageFirst_creator">${model.docCreator.fdName}</span>
										</c:if>
								 	<!-- </span> -->
								</c:when>
								<c:otherwise>
									<c:set var="_readStyle" value="style=''" /> 
									<c:if test="${model.read == true}">
										<c:set var="_readStyle" value="style='color:#999;'" /> 
									</c:if>
									<a title='<c:out value="${model.subject4View}"/>'  class="lui_dataview_classic_link" ${_readStyle} target="${target}" onclick="onNotifyClick(this,'${model.fdType}')"
										<c:if test="${not empty model.fdLink}"> data-href="<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId=${model.fdId}&r=${xxx }" />"</c:if>>
										<c:if test="${fn:contains(model.fdExtendContent,'lbpmPress')}"> 
					                 		<span class="lui_notify_level lui_notify_level1">${lfn:message('sys-notify:sysNotifyTodo.process.press')}</span>
					                 	</c:if> 
										<c:if test="${model.fdType==3}"><span class="lui_notify_pending">${lfn:message('sys-notify:sysNotifyTodo.type.suspend') }</span></c:if>
										<c:if test="${model.fdLevel==1}"><span class="lui_notify_level lui_notify_level1">${lfn:message('sys-notify:sysNotifyTodo.level.title.1')}</span></c:if>
										<c:if test="${model.fdLevel==2}"><span class="lui_notify_level lui_notify_level2">${lfn:message('sys-notify:sysNotifyTodo.level.title.2')}</span></c:if>
										<c:if test="${showAppHome==1}">
											<c:set var="appName" value="${model.fdAppName}"/>
											<c:if test="${appName!=null && appName!='' }">
												<bean:message bundle="sys-notify" key="sysNotifyTodo.todo.appTitle.left" /><c:out value="${appName}"/><bean:message bundle="sys-notify" key="sysNotifyTodo.todo.appTitle.right" />
											</c:if>
										</c:if>
										<c:out value="${model.subject4View}"/> 
								 	</a>
								 	<c:if test="${map[status.index] !='' && map[status.index] !=null}">
									 	<span class="lui_dataview_classic_new" style="display:none;"></span>
									 </c:if>
								 	<c:if test="${showFdCreateTime !='' && showFdCreateTime !=null}">
									 	<span class="lui_dataview_classic_created" style="display:none;">${fn:substring(model.fdCreateTime,0,10)}</span>
									 </c:if>
									 <c:if test="${showDocCreator !='' && showDocCreator !=null}">
										<span class="lui_dataview_imageFirst_creator" style="display:none;">${model.docCreator.fdName}</span>
									</c:if>
								</c:otherwise>
							</c:choose>
							</div>
						</div>
					</c:forEach>

	</div>
	<script type="text/javascript">
	domain.autoResize();
	</script>

<script language='javascript'>
	function ellipsisText(){
		$(".lui_dataview_classic_row").each(function(index,textArea){
		
		   var textAreaObj = $(this).find(".lui_dataview_classic_textArea");
		   var textBox = textAreaObj.find("a.lui_dataview_classic_link");
		   var rowHeight = $(this).height() == 0 ? 20 : $(this).height();
		   if(rowHeight > 26){
			   rowHeight = 26;
		   }
		   $(this).children("span").show();
		   textBox.show();
		   textAreaObj.children("span").show();
<%--		   var text = textBox.html().replace(/\s+/g," ");--%>
<%--		   --%>
<%--		   textBox.html(text);--%>
<%--		 --%>
<%--		   for(var i=text.length-2; i>0 && $(this).height()-6 > 26; i--){--%>
<%--			   --%>
<%--			  textBox.html(text.substring(0, i)+'...');--%>
<%--		   }--%>
		  }); 
	}

	function onNotifyClick(hrefObj,notifyType){
		//待阅点击后及时消失
		if(notifyType=='2'){
			//当前行顶级节点
			var p = $(hrefObj)[0].parentNode.parentNode;
			p.style.display="none";
			//待阅异步更新
			setTimeout(function(){
				reloadDatas();
			},2000);
			
			//待阅portlet窗口数量刷新
			var toViewCount = $('#toViewCount').html();
			if(!isNaN(toViewCount)){
				toViewCount = parseInt(toViewCount,10);
				if(toViewCount > 0){
					$('#toViewCount').html(toViewCount-1);
					updatePortalNotifyTitleCount();
				}
			}
			
			var countObj=document.getElementById("notifyCount2");
			var btnObj=document.getElementById("notifyBtn2");
			if(countObj!=null){
				if(!isNaN(countObj.innerText)){
					var countInt=parseInt(countObj.innerText,10);
					if(countInt>0)
						$(countObj).text(countInt-1);
				}
			}
			if(btnObj!=null){
				var oldBtnVal=btnObj.value;
				if(oldBtnVal.indexOf("(")>-1){
					var labelName=oldBtnVal.substring(0,oldBtnVal.indexOf("("));
					var countVar=oldBtnVal.substring(oldBtnVal.indexOf("(")+1,oldBtnVal.length-1);
					if(!isNaN(countVar)){
						var count=parseInt(countVar,10);
						if(count>0){
							btnObj.value=labelName+"("+(count-1)+")";
						}
					}
				}
			}	
		}	
		var href = $(hrefObj).data("href");
		if(href) {
			Com_OpenWindow(href);
		}
	}
	//分类数量
	function onNotifyCountClick(cateId,notifyType){
		//待阅点击后及时消失
		if(notifyType=='2'){
			setTimeout(function(){
				reloadDatas();
			},1000);
		}
	}
	function onPublishPortalRefresh(){
		domain.call(window.parent,"fireEvent",[{
			type:"topic", 
			name:"portal.notify.refresh"
		}]);
	}
	function updatePortalNotifyTitleCount(){
		var count = $('#toViewCount').html() || 0;
		if(!isNaN(count) && count>=0){
			//跨域事件处理 
			domain.call(window.parent,"fireEvent",[{
				type:"topic", 
				name:"portal.notify.title.count",
				luiId:"${lfn:escapeJs(LUI_ID)}",
				count:count
			}]);
		}
	}
	function reloadDatas(){
		var pWin = window.parent;
		if(pWin && pWin.refreshNotifyData){
			 pWin.refreshNotifyData();
		}else{
			location.reload();
		}
		onPublishPortalRefresh();
	}
	function refreshGlobalNotifyData(){
		var key = '${JsParam.LUIID}';
		if(window.parent && key){
			var pWin = window.parent;
			if(!pWin.notifyEventTargets){
				pWin.notifyEventTargets = {};
				pWin.notifyEventTargets[key]=location;
			}else if(pWin.notifyEventTargets){
				pWin.notifyEventTargets[key]=location;
			}
			pWin.refreshNotifyData = function(){
				for(var prop in pWin.notifyEventTargets){
					if(pWin.notifyEventTargets[prop]){
						pWin.notifyEventTargets[prop].reload();
					}
				}
			}
		}
	}
	seajs.use(['lui/jquery','lui/topic'], function($ , topic) {
		//审批等操作完成后，自动刷新列表
		topic.subscribe('successReloadPage', function() {
			reloadDatas();
		});
	});
	//统一门户列表中待办数量
	$(document).ready(function(){
		updatePortalNotifyTitleCount();
		//定义全局刷新事件
		refreshGlobalNotifyData();
		//ellipsisText();
	});
	 if(window.addEventListener){
		window.addEventListener('load',function(){
			//IE11渲染慢，加延迟
			setTimeout(function(){
				ellipsisText();
			},300);
		})
	}else{
		window.attachEvent('onload',reEllipsisText);
	} 
	function reEllipsisText(){
		setTimeout(function(){
			ellipsisText();
		},300);
	}
</script>
</template:replace>
</template:include>