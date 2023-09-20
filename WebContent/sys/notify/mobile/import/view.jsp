<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request" />
<c:set var="sysNotifyRemindMainContextForm" value="${mainModelForm.sysNotifyRemindMainContextForm}" scope="request" />
<link rel="stylesheet" href="${LUI_ContextPath}/sys/notify/mobile/import/view.css" />
<c:if test="${fn:length(sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList) > 0}">
	<div onclick="muiNotifySliceTo(this,'${param.fdPrefix}_${param.fdKey}_details')">
		<c:set var="sysNotifyRemindMainForm" value="${sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[0] }"></c:set>
		<xform:select property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[0].fdNotifyType" value="${sysNotifyRemindMainForm.fdNotifyType}" showStatus="view">
			<xform:enumsDataSource enumsType="sys_notify_fdNotifyType" />
		</xform:select>
		<c:out value="${sysNotifyRemindMainForm.fdBeforeTime}" />
	    <xform:select property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[0].fdTimeUnit" value="${sysNotifyRemindMainForm.fdTimeUnit}" showStatus="view">
			<xform:enumsDataSource enumsType="sys_notify_fdTimeUnit" />
		</xform:select>	
		<i class="mui mui-forward" style="line-height:2rem"></i>
	</div>
</c:if>

<div id="${param.fdPrefix}_${param.fdKey}_details" data-dojo-type="dojox/mobile/ScrollableView">
	<div class="muiNotifyHeader" data-dojo-type="mui/header/Header" data-dojo-props="height:'3.8rem'">
		<div class="muiNotifyHeaderBack" onclick="muiNotifySliceTo(this,'${param.backTo}')">
			<i class="mui mui-back"></i>
			<span class="muiNotifyHeaderBackTxt">返回</span>
		</div>
		<div class="muiNotifyHeaderTitle">消息提醒</div>
		<div></div>
	</div>
	 <ul class="muiNotifyList" width="100%" id="${param.fdPrefix}_${param.fdKey}">
		<c:forEach items="${sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList}" var="sysNotifyRemindMainFormListItem" varStatus="vstatus">
			<li>
				<xform:select property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdNotifyType" value="${sysNotifyRemindMainFormListItem.fdNotifyType}" showStatus="view">
					<xform:enumsDataSource enumsType="sys_notify_fdNotifyType" />
				</xform:select>
				<c:out value="${sysNotifyRemindMainFormListItem.fdBeforeTime}" />
                <xform:select property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdTimeUnit" value="${sysNotifyRemindMainFormListItem.fdTimeUnit}" showStatus="view">
					<xform:enumsDataSource enumsType="sys_notify_fdTimeUnit" />
				</xform:select>
			</li>
		</c:forEach> 
	</ul>
</div>

<script>
	require([
	     'dojox/mobile/TransitionEvent',
	     'dojo/ready',
	     'dojo/dom-construct',
	     'dojo/query'
	],function(TransitionEvent,ready,domConstruct,query){
		
		ready(function(){
			domConstruct.place('${param.fdPrefix}_${param.fdKey}_details',query('#content')[0],'last');
			query('#${param.fdPrefix}_${param.fdKey}_details')[0].style.display="none";
		});
		
		window.muiNotifySliceTo = function(obj,viewName){
			var opts = {
				transition : 'slide',
				transitionDir:-1,
				moveTo:viewName
			};
			new TransitionEvent(obj || document.body, opts).dispatch();
		};
		
		
	});
</script>

