<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<template:include ref="person.home">
	<template:replace name="title">
		<c:out value="${lfn:message('sys-praise:sysPraise.person.my') }"/>
	</template:replace>
	<template:replace name="content">
		<c:set var="navTitle" value="${ lfn:message('sys-praise:sysPraise.person.my') }"></c:set>
		<c:if test="${not empty param.navTitle}">
			<c:set var="navTitle" value="${param.navTitle}"></c:set>
		</c:if>
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${navTitle}">
				<list:criteria id="criteria1" multi="false" >
					<list:tab-criterion title="" key="mydoc"> 
						<list:box-select>
							<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-required="true" cfg-defaultValue="create">
								<ui:source type="Static">
									[{text:'${lfn:message('sys-praise:sysPraiseInfo.myCreate') }', value:'create'},{text:'${lfn:message('sys-praise:sysPraiseInfo.myReceive') }',value:'receive'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:tab-criterion>
					<list:cri-criterion title="${lfn:message('sys-praise:sysPraiseInfo.fdType') }" key="fdType" multi="false">
						<list:box-select>
							<list:item-select >
								<ui:source type="Static">
									[{text:"${lfn:message('sys-praise:sysPraiseInfo.fdType.praise') }", value: '1'},
									{text:"${lfn:message('sys-praise:sysPraiseInfo.fdType.rich') }", value:'2'},
									{text:"${lfn:message('sys-praise:sysPraiseInfo.fdType.unPraise') }", value:'3'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<list:cri-auto modelName="com.landray.kmss.sys.praise.model.SysPraiseInfo" property="fdPraisePerson" />
					<list:cri-auto modelName="com.landray.kmss.sys.praise.model.SysPraiseInfo" property="fdTargetPerson" />
				</list:criteria>
				<div class="lui_list_operation">
				</div>
				<list:listview id="listview">
					<ui:source type="AjaxJson">
						{url:'/sys/praise/sys_praise_info/sysPraiseInfo.do?method=data&fdModelId=${param.fdModelId}'}
					</ui:source>
					<list:colTable name="columntable" rowHref="/sys/praise/sys_praise_info/sysPraiseInfo.do?method=view&fdId=!{fdId}" >
					<list:col-serial />
					 <list:col-auto props="fdTargetPersonName;fdRich"></list:col-auto>
					<list:col-html title="${lfn:message('sys-praise:sysPraiseInfo.fdReason')}" style="width:45%;padding:0 8px">
						{$
							<span class="com_subject">{%row['fdReason']%}</span> 
						$}
					</list:col-html>
					 <list:col-auto props="fdPraisePersonName;fdType;docCreateTime"></list:col-auto>
					 </list:colTable>
				</list:listview> 
				<list:paging></list:paging>
			</ui:content>
		</ui:tabpanel>
		<script>
			function gotoAdmin(){
			    var fdUrl = "${LUI_ContextPath}/sys/praise/sys_praise_info/index_admin.jsp"
			    Com_OpenWindow(fdUrl,"_self");
			}
		</script>
		<style>
			.lui_dataview_picmenu_content{
				cursor: auto !important;
			}
		</style>
	</template:replace>
</template:include>
