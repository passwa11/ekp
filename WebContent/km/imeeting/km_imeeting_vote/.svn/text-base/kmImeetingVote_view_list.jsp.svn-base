<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp" >
	<%-- 右侧内容区域 --%>
	<template:replace name="content"> 
		
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<%-- 列表视图 --%>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/km/imeeting/km_imeeting_vote/kmImeetingVote.do?method=data&fdTemporaryId=${JsParam.fdMeetingId}'}
			</ui:source>
			<!-- 列表视图 -->	
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" cfg-norecodeLayout="simple"
				rowHref="/km/imeeting/km_imeeting_vote/kmImeetingVote.do?method=view&fdId=!{fdId}"  name="columntable">
				<list:col-serial></list:col-serial> 
				<list:col-auto props="docSubject"></list:col-auto>				
			</list:colTable>
		</list:listview> 
		<list:paging></list:paging>
	</template:replace>
</template:include>