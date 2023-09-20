<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/resource/jsp/common.jsp"%>

<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" />

<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
	<c:if test="${sysWfBusinessForm.docStatus<'30'}">
	<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props='id:"process_review_button",colSize:3' class="mainTabBarButton"
		onclick="${(empty param.onClickSubmitButton) ? 'Com_Submit(document.sysWfProcessForm, &quot;update&quot;);' : (param.onClickSubmitButton)}">
		<bean:message  key="button.submit" /> 
	</li>
	</c:if>
	<c:if test="${sysWfBusinessForm.docStatus>='30'}">
	<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props='colSize:2'></li>
	</c:if>
	<c:choose>
	<c:when test="${sysWfBusinessForm.sysWfBusinessForm.fdTemplateType == '4'}">
		<li data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowChartButton" class="lbpmSwitchButton muiSplitterButton"
			data-dojo-props='icon1:"mui mui-flowchart",showType:"dialog"'>
			<bean:message bundle="sys-lbpmservice" key="lbpm.tab.graphic"/>
		</li>
	</c:when>
	<c:otherwise>
		<li data-dojo-type="mui/tabbar/TabBarButton" class="lbpmSwitchButton muiSplitterButton"
			data-dojo-props='icon1:"mui mui-flowchart",onClick:showFlowChart'>
			<bean:message bundle="sys-lbpmservice" key="lbpm.tab.graphic"/>
		</li>
	</c:otherwise>
	</c:choose>
</ul>