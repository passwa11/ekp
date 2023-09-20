<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="sysCirculationForm" value="${requestScope[param.formName]}" />
<c:if test="${JsParam.enable ne 'false' && sysCirculationForm.circulationForm.fdIsShow=='true'}">
<script type="text/javascript">
seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
	window.circulate = function (){
		var path = "/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=add&fdModelName=${sysCirculationForm.circulationForm.fdModelName}&fdModelId=${sysCirculationForm.circulationForm.fdModelId}&fdIsNewVersion=${sysCirculationForm.circulationForm.fdIsNewVersion}&isNew=${param.isNew}";
		dialog.iframe(path,' ',null,{width:750,height:550});
	};
	
	window.addCirculate = function (){
		var path = "/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=addCirculate&fdModelName=${sysCirculationForm.circulationForm.fdModelName}&fdModelId=${sysCirculationForm.circulationForm.fdModelId}";
		dialog.iframe(path,' ',null,{width:750,height:450});
	};
	
	
	
	window.openCirculation = function(fdId){
		var path = "/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=view&fdId="+fdId+"&docStatus=all&fdModelName=${sysCirculationForm.circulationForm.fdModelName}&fdModelId=${sysCirculationForm.circulationForm.fdModelId}";
		dialog.iframe(path,'${ lfn:message("sys-circulation:sysCirculationMain.detail") }',null,{width:1000,height:680});
	};
	window.recallCirculation = function(fdId){
		var path = "/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=view&fdId="+fdId+"&docStatus=10&fdModelName=${sysCirculationForm.circulationForm.fdModelName}&fdModelId=${sysCirculationForm.circulationForm.fdModelId}";
		dialog.iframe(path,'${ lfn:message("sys-circulation:sysCirculationMain.detail") }',null,{width:1000,height:680});
		
	};
	window.remindCirculation = function(fdId){
		var path = "/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=view&fdId="+fdId+"&docStatus=10&fdModelName=${sysCirculationForm.circulationForm.fdModelName}&fdModelId=${sysCirculationForm.circulationForm.fdModelId}";
		dialog.iframe(path,'${ lfn:message("sys-circulation:sysCirculationMain.detail") }',null,{width:1000,height:680});
	};
	
	window.deleteCirculation=function(fdId){
		dialog.confirm("${lfn:message('sys-circulation:sysCirculationMain.message.delete')}",function(flag){
	    	if(flag==true){
	    		window.del_load  = dialog.loading(null,$("#CirculationMain"));
				$.get('<c:url value="/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=delete"/>', $.param({"fdId":fdId,"fdModelId":"${sysCirculationForm.circulationForm.fdModelId}","fdModelName":"${sysCirculationForm.circulationForm.fdModelName}"},true),delCallback,'json');
	    	}else{
	    		return;
		    }
	    },"warn");
	};
    
	window.delCallback = function(data){
		if(window.del_load!=null)
			window.del_load.hide();
		if(data!=null && data.status==true){
			var count = cir_getNumber();
			topic.publish("circulation.delete.success",{"data":{"recordCount": count}});
			topic.channel("paging").publish("list.refresh");
			dialog.success('<bean:message key="return.optSuccess" />',$("#CirculationMain"));
		}else{
			dialog.failure('<bean:message key="return.optFailure" />',$("#CirculationMain"));
		}
	};
	//获取原来的条数
	window.cir_getNumber = function(){
		var incNum = -1;
		var count = 0;
		$('div[id="cir_label_title"]').each(function(){
			var scoreInfo = $(this).text();
			var numinfo = "0";
			if(scoreInfo.indexOf("(")>0){
				var prefix=scoreInfo.substring(0,scoreInfo.indexOf("("));
				numinfo = scoreInfo.substring(scoreInfo.indexOf("(") + 1 , scoreInfo.indexOf(")"));
				count = parseInt(numinfo) + incNum;
				$(this).text(prefix+"("+(parseInt(numinfo) + incNum)+")");
			}
		});
		return count;
	};

	//删除成功后更新条数
	window.refreshNum = function(info){
		var count = 0;
		if(info.data){
			if(info.data.recordCount!=null){
				count = info.data.recordCount;
			}
		}
	};
});  
</script>
	<kmss:auth
		requestURL="/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=delete&fdModelName=${sysCirculationForm.circulationForm.fdModelName}&fdModelId=${sysCirculationForm.circulationForm.fdModelId}"
		requestMethod="GET">
		<c:set var="validateAuth" value="true" />
	</kmss:auth>
	<c:set var="circulationUrl"
		value="/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=add&fdModelName=${sysCirculationForm.circulationForm.fdModelName}&fdModelId=${sysCirculationForm.circulationForm.fdModelId}&fdIsNewVersion=${sysCirculationForm.circulationForm.fdIsNewVersion}" />
	<c:set var="showButton" value="false"></c:set>
	<c:set var="toolbarOrder" value="3"></c:set>
	
	<c:set var="isTab" value="${empty param.isTab ? 'false' : param.isTab}"/>
	
	<c:if test="${ not empty param && not empty param.toolbarOrder}">
		<c:set var="toolbarOrder" value="${param.toolbarOrder}"></c:set>
	</c:if>

	<c:set var="isNew" value="false" />
	<c:if test="${param.isNew !=null && param.isNew == 'true'}">
		<c:set var="isNew" value="true" />
	</c:if>

	<c:if test="${isTab ne true }">
		<c:choose>
			<c:when test="${param.isModeling eq true}">
				<kmss:auth requestURL="${circulationUrl}" requestMethod="GET">
					<ui:button parentId="toolbar" order="${toolbarOrder}" text="${param.text}" onclick="circulate();">
					</ui:button>
					<c:set var="showButton" value="true"></c:set>
				</kmss:auth>
				<c:if test="${showButton eq 'false' and  isNew}">
					<kmss:auth requestURL="/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=addCirculate&fdModelName=${sysCirculationForm.circulationForm.fdModelName}&fdModelId=${sysCirculationForm.circulationForm.fdModelId}" requestMethod="GET">
						<ui:button parentId="toolbar" order="${toolbarOrder}" text="${param.text}" onclick="addCirculate();">
						</ui:button>
					</kmss:auth>
				</c:if>
			</c:when>
			<c:otherwise>
				<kmss:auth requestURL="${circulationUrl}" requestMethod="GET">
					<ui:button parentId="toolbar" order="${toolbarOrder}" text="${ lfn:message('sys-circulation:sysCirculationMain.button.circulation') }" onclick="circulate();">
					</ui:button>
					<c:set var="showButton" value="true"></c:set>
				</kmss:auth>
				<c:if test="${showButton eq 'false' and  isNew}">
					<kmss:auth requestURL="/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=addCirculate&fdModelName=${sysCirculationForm.circulationForm.fdModelName}&fdModelId=${sysCirculationForm.circulationForm.fdModelId}" requestMethod="GET">
						<ui:button parentId="toolbar" order="${toolbarOrder}" text="${ lfn:message('sys-circulation:sysCirculationMain.button.circulation') }" onclick="addCirculate();">
						</ui:button>
					</kmss:auth>
				</c:if>
			</c:otherwise>
		</c:choose>
	</c:if>
	
	<c:set var="order" value="${empty param.order ? '5' : param.order}"/>
	<c:set var="disable" value="${empty param.disable ? 'false' : param.disable}"/>
	<c:set var="expand" value="${empty param.expand ? 'false' : param.expand}"/>
	<c:set var="isOper" value="${empty param.isOper ? 'false' : param.isOper}"/>
	<c:if test="${isOper ne true }">
		<%
		    boolean isShowUiContent = !"false".equals(request.getParameter("isShowUiContent"));
		if(isShowUiContent){ %>
			<ui:content titleicon="${not empty param.titleicon?param.titleicon:''}" expand="${expand }" cfg-order="${order}" cfg-disable="${disable}" title="<div id='cir_label_title'>${ lfn:message('sys-circulation:sysCirculationMain.tab.circulation.label') }${sysCirculationForm.circulationForm.fdCirculationCount}</div>">
			 <ui:event topic="circulation.delete.success" args="info">
				refreshNum(info);
			</ui:event>
			  <%@ include file="sysCirculationMain_viewSimple.jsp"%>
			</ui:content>
		<%}else{ %>
		       <%@ include file="sysCirculationMain_viewSimple.jsp"%>
		<% }%>
	</c:if>
</c:if>
