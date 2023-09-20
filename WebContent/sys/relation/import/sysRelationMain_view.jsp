<%@ page import="com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="com.landray.kmss.sys.lbpm.engine.persistence.AccessManager" %>
<%@ page import="com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess" %>
<%@ page import="com.landray.kmss.sys.lbpm.engine.service.ProcessInstanceInfo" %>
<%@ page import="com.landray.kmss.common.forms.ExtendForm" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:if test="${JsParam.enable ne 'false'}">
<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request"/>
<c:set var="sysRelationMainForm" value="${mainModelForm.sysRelationMainForm}" scope="request"/>
<c:set var="currModelId" value="${mainModelForm.fdId}" scope="request"/>
<c:set var="currModelName" value="${mainModelForm.modelClass.name}" scope="request"/>
<link rel="stylesheet" type="text/css"
	  href="${LUI_ContextPath}/sys/relation/sys_relation_main/style/relaPageBtn.css">
<%
	boolean isShowBtn = false;
	String url = com.landray.kmss.util.ModelUtil.getModelUrl(request.getAttribute("currModelName"));
	if(url != null) {
		url = url.replace("method=view", "method=edit").replace("${fdId}", (String)request.getAttribute("currModelId"));
		isShowBtn = com.landray.kmss.util.UserUtil.checkAuthentication(url, "GET");
	}
	
	// 没有编辑权限，还需要判断是不是处理人
	if (!isShowBtn) {
		Object relationConfig = request.getAttribute("relationConfig");
		if (relationConfig != null && "true".equals(relationConfig.toString())) {
			try {
				ExtendForm formObj = (ExtendForm) request.getAttribute("mainModelForm");
				AccessManager accessManager = (AccessManager) SpringBeanUtil
						.getBean("accessManager");
				ProcessExecuteService processExecuteService = (ProcessExecuteService) SpringBeanUtil
						.getBean("lbpmProcessExecuteService");
				List<LbpmProcess> lbpmProcesses = accessManager.find("LbpmProcess.selectByFdModelId", formObj.getFdId());
				ProcessInstanceInfo info = processExecuteService.load(lbpmProcesses.get(0));
				isShowBtn = info.isHandler();
			} catch (Exception e) {
				// 忽略异常，不显示关联配置
			}
		}
	}
	if(isShowBtn) {
		// 有可编辑者权限才能使用
	%>
		<script type="text/javascript">
			window.sysRelationMainForm_param = {
					'fdDesSubject': '<c:out value="${sysRelationMainForm.fdDesSubject}" />',
					'fdDesContent': '<c:out value="${sysRelationMainForm.fdDesContent}" />',
					'fdId': '<c:out value="${sysRelationMainForm.fdId}" />',
					'fdKey': '<c:out value="${param.fdKey}" />',
					'fdModelName': '<c:out value="${currModelName}" />',
					'fdModelId': '<c:out value="${currModelId}" />',
					'fdParameter': '<c:out value="${sysRelationMainForm.fdParameter}" />'
			}
		</script>
		<c:choose>
			<c:when test="${param.approveType eq 'right'}">
				<ui:event event="layoutDone">
					<c:choose>
						<c:when test="${param.needTitle eq 'true'}">
							var relationMainContent = LUI("sysRelationMainContent");
							if(typeof relationOpt != "undefined" && relationOpt && relationMainContent!=null){
								$("<span style='margin-left:10px' class='lui_tabpanel_navs_item_title' id='rela_config_btn_view_right'>${lfn:message('button.create')}</span>").appendTo(relationMainContent.element.find(".lui-fm-tab-title"));
								$("#rela_config_btn_view_right").click(function(){
									Com_EventStopPropagation();
									relationOpt.optType = "view"; // View页面中的编辑
									relationOpt.openAddUrl = true;
									relationOpt.editConfig();
								});
							}
							<c:if test="${empty sysRelationMainForm.sysRelationEntryFormList}">
								$("i.lui-fm-icon-1").closest(".lui_tabpanel_vertical_icon_navs_item_l").hide();
							</c:if>
						</c:when>
						<c:otherwise>
							var relationMainContent = LUI("sysRelationMainContent");
							if(relationMainContent.nav){
								relationMainContent.nav.find(".lui_accordionpanel_nav_text").append("<span style='margin-left:10px' class='lui_tabpanel_navs_item_title' id='rela_config_btn_view_right'>${lfn:message('button.create')}</span>");
							}
							if(typeof relationOpt != "undefined" && relationOpt){
								$("#rela_config_btn_view_right").click(function(){
									Com_EventStopPropagation();
									relationOpt.optType = "view"; // View页面中的编辑
									relationOpt.openAddUrl = true;
									relationOpt.editConfig();
								});
							}
							var isEmpty = ${empty sysRelationMainForm.sysRelationEntryFormList};
							if(isEmpty){
								relationMainContent.element.closest(".lui_accordionpanel_content_frame").hide();
								if(relationMainContent.nav){
									relationMainContent.nav.hide();
								}
							}
						</c:otherwise>
					</c:choose>
			    </ui:event>
				<ui:content title="${lfn:message('sys-relation:title.sysRelationMain.setting')}" titleicon="lui-fm-icon-1" id="sysRelationMainContent">
					<ui:accordionpanel id="sysRelationMainContentPanel" channel="relation" style="min-width:200px;" layout="sys.ui.accordionpanel.simpletitle">
						<%@ include file="sysRelationMain_include_edit.jsp" %>
						<ui:button parentId="toolbar" 
							text="${lfn:message('sys-relation:title.sysRelationMain.setting')}" order="4" id="rela_config_btn_view">
						</ui:button>
					</ui:accordionpanel>
				</ui:content>
			</c:when>
			<c:otherwise>
				<ui:accordionpanel id="sysRelationMainContentPanel" channel="relation" style="min-width:200px;" layout="${not empty param.layout ? param.layout : '' }">
					<%@ include file="sysRelationMain_include_edit.jsp" %>
					<ui:button parentId="toolbar" 
						text="${lfn:message('sys-relation:title.sysRelationMain.setting')}" order="4" id="rela_config_btn_view">
					</ui:button>
				</ui:accordionpanel>
			</c:otherwise>
		</c:choose>
	<%
	} else {
		// 如果没有编辑者权限，还是按原来的方式展示
	%>
		<c:if test="${not empty sysRelationMainForm.sysRelationEntryFormList}">
		<c:choose>
			<c:when test="${param.approveType eq 'right'}">
				<ui:content title="${lfn:message('sys-relation:title.sysRelationMain.setting')}" titleicon="lui-fm-icon-1">
					<ui:accordionpanel style="min-width:200px;" layout="sys.ui.accordionpanel.simpletitle"> 
						<c:set var="_max_count" value="2"/>
						<c:forEach items="${sysRelationMainForm.sysRelationEntryFormList}" varStatus="vstatus" var="sysRelationEntryForm">
							<c:set var="_render_id" value="sys.ui.classic.tile"/>
							<c:if test="${ '7' eq sysRelationEntryForm.fdType }">
								<c:set var="_render_id" value="sys.ui.maindata.tile"/>
							</c:if>
							<c:choose>
								<c:when test="${ '5' eq sysRelationEntryForm.fdType && 'true' eq sysRelationEntryForm.fdIsTemplate }">
									<c:set var="_max_count" value="3"/>
								</c:when>
								<c:otherwise>
									<c:set var="isExpanded" value="true"/>
									<c:if test="${ vstatus.index > _max_count}">
										<c:set var="isExpanded" value="false"/>
									</c:if>
									<ui:content title="${sysRelationEntryForm.fdModuleName}" expand="${isExpanded}">
										<ui:dataview>
											<ui:source type="AjaxJson">
												{
													url:'/sys/relation/relation.do?method=result&forward=listUi&currModelId=${currModelId}&currModelName=${currModelName}&fdKey=${JsParam.fdKey}&sortType=time&fdType=${sysRelationEntryForm.fdType}&moduleModelId=${sysRelationEntryForm.fdId}&moduleModelName=${sysRelationEntryForm.fdModuleModelName}&showCreateInfo=${JsParam.showCreateInfo}'
												}
											</ui:source>
											<c:if test="${'6' ne sysRelationEntryForm.fdType}">
												<ui:render ref="${_render_id}" var-showCreator="true" var-showCreated="true" var-ellipsis="false">
												</ui:render>
											</c:if>
											<c:if test="${'6' eq sysRelationEntryForm.fdType}">
												<ui:render type="Template">
													if(data && data.length > 0) {
														for(var i =0 ; i <data.length; i ++) {
															{$
															<div>{%env.fn.formatText(data[i])%}</div>
															$}
														}
													}
												</ui:render>
											</c:if>
											<ui:event event="load">
												if(window.Sidebar_Refresh){
													var interval, intervalCount = 0;
													interval = setInterval(function() {
														window.Sidebar_Refresh();
														if(intervalCount >= 3) {
															clearInterval(interval);
														}
														intervalCount += 1;
													}, 500);
												}
											</ui:event>
										</ui:dataview>
									</ui:content>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ui:accordionpanel> 
				</ui:content>
			</c:when>
			<c:otherwise>
				<ui:accordionpanel style="min-width:200px;"> 
					<c:set var="_max_count" value="2"/>
					<c:forEach items="${sysRelationMainForm.sysRelationEntryFormList}" varStatus="vstatus" var="sysRelationEntryForm">
						<c:set var="_render_id" value="sys.ui.classic.tile"/>
						<c:if test="${ '7' eq sysRelationEntryForm.fdType }">
							<c:set var="_render_id" value="sys.ui.maindata.tile"/>
						</c:if>
						<c:choose>
							<c:when test="${ '5' eq sysRelationEntryForm.fdType && 'true' eq sysRelationEntryForm.fdIsTemplate }">
								<c:set var="_max_count" value="3"/>
							</c:when>
							<c:otherwise>
								<c:set var="isExpanded" value="true"/>
								<c:if test="${ vstatus.index > _max_count}">
									<c:set var="isExpanded" value="false"/>
								</c:if>
								<ui:content title="${sysRelationEntryForm.fdModuleName}" expand="${isExpanded}">
									<ui:dataview>
										<ui:source type="AjaxJson">
											{
												url:'/sys/relation/relation.do?method=result&forward=listUi&currModelId=${currModelId}&currModelName=${currModelName}&fdKey=${JsParam.fdKey}&sortType=time&fdType=${sysRelationEntryForm.fdType}&moduleModelId=${sysRelationEntryForm.fdId}&moduleModelName=${sysRelationEntryForm.fdModuleModelName}&showCreateInfo=${JsParam.showCreateInfo}'
											}
										</ui:source>
										<c:if test="${'6' ne sysRelationEntryForm.fdType}">
											<ui:render ref="${_render_id}" var-showCreator="true" var-showCreated="true" var-ellipsis="false">
											</ui:render>
										</c:if>
										<c:if test="${'6' eq sysRelationEntryForm.fdType}">
											<ui:render type="Template">
												if(data && data.length > 0) {
													for(var i =0 ; i <data.length; i ++) {
														{$
														<div>{%env.fn.formatText(data[i])%}</div>
														$}
													}
												}
											</ui:render>
										</c:if>
										<ui:event event="load">
											if(window.Sidebar_Refresh){
												var interval, intervalCount = 0;
												interval = setInterval(function() {
													window.Sidebar_Refresh();
													if(intervalCount >= 3) {
														clearInterval(interval);
													}
													intervalCount += 1;
												}, 500);
											}
										</ui:event>
									</ui:dataview>
								</ui:content>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ui:accordionpanel> 
			</c:otherwise>
		</c:choose>
		
	</c:if>
<%
	}
%>
</c:if>
