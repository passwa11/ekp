<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.framework.plugin.core.config.IExtension"%>
<%@ page import="com.landray.kmss.km.imeeting.util.StatExecutorPlugin"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String fdType=(String)request.getAttribute("fdType");
	IExtension ext=StatExecutorPlugin.getExtensionForStat(fdType);
	if(ext!=null){
		if(StringUtil.isNotNull(StatExecutorPlugin.getConditionJsp(ext))){
			request.setAttribute("conditionJsp4m", StatExecutorPlugin.getConditionJsp4m(ext));
		}
	}
%>
<template:include ref="mobile.view" compatibleMode="true">
	<%-- 标签页标题 --%>
	<template:replace name="title">
		<bean:message bundle="km-imeeting"  key="kmImeetingStat.${fdType}" />
	</template:replace>	
	<template:replace name="head">
	<style type="text/css">
	#chart canvas{
	   left:-6px!important;
	}
	  </style>
	</template:replace>
	<template:replace name="content">
		<div id="scrollView"  class="gray" data-dojo-type="mui/view/DocScrollableView">
			<div data-dojo-type="mui/panel/AccordionPanel">
				<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="km-imeeting" key="mobile.kmImeetingStat.result" />',icon:'mui-ul'">
					<div id="chart" data-dojo-type="mui/chart/Chart" data-dojo-props="isLazy:true"></div>
				</div>
				<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="km-imeeting" key="mobile.kmImeetingStat.condition" />',icon:'mui-ul'">
					<c:import url="${conditionJsp4m}" charEncoding="UTF-8">
						<c:param name="fdType" value="${kmImeetingStatForm.fdType}"/>
						<c:param name="formName" value="kmImeetingStatForm"/>
						<c:param name="mode" value="view"></c:param>
					</c:import>
				</div>
			</div>
		</div>
		
		<script type="text/javascript">
			require(['km/imeeting/mobile/stat/common/stat','dojo/ready'],function(stat,ready){
				ready(function(){
					stat.statExecutor();
				});
			});
		</script>
	</template:replace>
</template:include>




