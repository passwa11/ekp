<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String appName = request.getParameter("appName");
	if(com.landray.kmss.util.StringUtil.isNotNull(appName)){
		request.setAttribute("__appName", java.net.URLEncoder.encode(appName,"UTF-8"));
	}
%>
<template:include ref="default.simple4list">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="nav">
		<!-- 新建按钮 -->
		<div class="lui_list_noCreate_frame">
			<ui:combin ref="menu.nav.create">
				<ui:varParam name="title" value="${ lfn:message('sys-attend:module.sys.attend') }" />
				<ui:varParam name="button">
					[
						{
							"text": "${ lfn:message('sys-attend:module.sys.attend') }",
							"href": "javascript:void(0)",
							"icon": "lui_icon_l_icon_89"
						}
					]
				</ui:varParam>				
			</ui:combin>
		</div>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
			 	<c:import url="/sys/attend/nav.jsp" charEncoding="UTF-8">
				   <c:param name="key" value="sysAttendMain"></c:param>
				   <c:param name="criteria" value="custMainCriteria"></c:param>
				   <c:param name="appKey" value="${JsParam.appKey}"></c:param>
				   <c:param name="me" value="${JsParam.me}"></c:param>
				</c:import>		 
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<c:if test="${param.appKey=='default' && param.me=='true' }">
				<c:set var="navTitle" value="${ lfn:message('sys-attend:sysAttend.nav.mySignRecord') }"></c:set>
			</c:if>
			<c:if test="${param.appKey=='default' && param.me !='true' }">
				<c:set var="navTitle" value="${ lfn:message('sys-attend:sysAttend.nav.allSignRecord') }"></c:set>
			</c:if>
			<c:if test="${param.appKey !='default' && param.me=='true' }">
				<c:set var="navTitle" value="${ lfn:message('sys-attend:sysAttend.nav.myMeetingSign') }"></c:set>
			</c:if>
			<ui:content title="${navTitle }">
				<!-- 查询条件  -->
				<list:criteria id="custMainCriteria" expand="false">
					<list:cri-auto modelName="com.landray.kmss.sys.attend.model.SysAttendMain" property="docCreateTime" />
					<c:if test="${not JsParam.me eq true}">
						<list:cri-ref key="docCreator" ref="criterion.sys.person" multi="false" title="${lfn:message('sys-attend:sysAttendMain.docCreator') }" />
						<list:cri-ref key="docCreatorDept" ref="criterion.sys.dept" multi="false" title="${lfn:message('sys-attend:sysAttendMain.docCreatorDept') }" />
					</c:if>
					<c:if test="${param.appKey !='default' }">
						<list:cri-criterion title="${ lfn:message('sys-attend:sysAttendMain.fdStatus')}" key="fdStatus"> 
						<list:box-select>
							<list:item-select>
								<ui:source type="Static">
									[{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok')}', value:'1'},
									{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.late')}',value:'2'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					</c:if>
					<list:cri-ref key="fdCategoryName" ref="criterion.sys.string" title="${ lfn:message('sys-attend:sysAttendCategory.custom') }"></list:cri-ref>
				</list:criteria>
				 
				<!-- 列表工具栏 -->
				<div class="lui_list_operation">
					<div style='color: #979797;float: left;padding-top:1px;'>
						${ lfn:message('list.orderType') }：
					</div>
					<div style="float:left">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
								<list:sort property="sysAttendMain.docCreateTime" text="${lfn:message('sys-attend:sysAttendMain.docCreateTime1') }" group="sort.list" value="down"></list:sort>
							</ui:toolbar>
						</div>
					</div>
					<div style="float:left;">
						<list:paging layout="sys.ui.paging.top" > 		
						</list:paging>
					</div>
					<div style="float:right">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar count="3">
								<kmss:auth requestURL="/sys/attend/sys_attend_main/sysAttendMain.do?method=exportRecordExcel">					 								
									<ui:button text="${lfn:message('button.export')}" onclick="exportRecordExcel();" order="2"></ui:button>
								</kmss:auth>
							</ui:toolbar>
						</div>
					</div>
				</div>
				
				<ui:fixed elem=".lui_list_operation"></ui:fixed>
				 
				 
			 	<list:listview id="listview">
					<ui:source type="AjaxJson">
							{url:'/sys/attend/sys_attend_main/sysAttendMain.do?method=list&categoryType=custom&appKey=${JsParam.appKey}&appName=${lfn:escapeJs(__appName)}&fdStatus=1;2&me=${JsParam.me}'}
					</ui:source>
					<!-- 列表视图 -->	
					<list:colTable isDefault="false"
						rowHref="/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId=!{fdId}"  name="columntable">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						<list:col-auto props="docCreator.fdName;docCreateTime;fdLocation;fdCategory.fdName;"></list:col-auto>
					</list:colTable>
				</list:listview> 
				 
			 	<list:paging></list:paging>	 
			</ui:content>
		</ui:tabpanel>
		
	 	
	 	<script>
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
				window.exportRecordExcel=function(){
					var listview = LUI('listview'),
						_url = listview.source.url;
					var	__url = '${LUI_ContextPath}/sys/attend/sys_attend_main/sysAttendMain.do?method=exportRecordExcel&cateType=custom' + _url.match(/&.*/);
					
					var del_load = dialog.loading();
					var iframe = document.createElement("iframe"); 
					iframe.src = __url; 
					iframe.style.display = 'none'; 
					document.body.appendChild(iframe);
					setTimeout(function(){
						if(del_load != null){
							del_load.hide();
						}
					}, 0);
				};
			});
		</script>
	</template:replace>
</template:include>
