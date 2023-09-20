<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%
	//默认今年
	Calendar c = Calendar.getInstance();
	c.setTime(new Date());
	int endYear = c.get(Calendar.YEAR);
	int startYear = 2018;
	JSONArray jsonArr = new JSONArray();
	JSONObject jsonObj = new JSONObject();
	for(int i=endYear; i>=startYear; i--){
		String text = i + ResourceUtil.getString("calendar.year"); 
		jsonObj.put("text", text);
		jsonObj.put("value", i);
		jsonArr.add(jsonObj);
	}
	request.setAttribute("year", endYear);
	request.setAttribute("yearArr", jsonArr.toString());
	//默认当月
	c = Calendar.getInstance();
	c.setTime(new Date());
	int currentMonth = c.get(Calendar.MONTH)+1;
	int endMonth = 12;
	int startMonth = 1;
	jsonArr = new JSONArray();
	jsonObj = new JSONObject();
	for(int i=endMonth; i>=startMonth; i--){
		String text = i + ResourceUtil.getString("calendar.month"); 
		jsonObj.put("text", text);
		jsonObj.put("value", i);
		jsonArr.add(jsonObj);
	}
	request.setAttribute("month", currentMonth);
	request.setAttribute("monthArr", jsonArr.toString());
	//是否为超级管理员、非三员情况
	Boolean isAdmin=UserUtil.getKMSSUser().isAdmin();
	request.setAttribute("isAdmin", isAdmin);
	
	// 系统来源
	String source = ResourceUtil.getKmssConfigString("log.elastic.source");
	if(StringUtil.isNotNull(source)) {
		JSONArray sourceArray = new JSONArray();
		// 本系统
		JSONObject obj = new JSONObject();
		obj.put("text", ResourceUtil.getString("sys-log:elastic.search.source.ekp"));
		obj.put("value", "ekp");
		sourceArray.add(obj);
		String[] temp = source.toLowerCase().split(";");
		for(String s : temp) {
			JSONObject o = new JSONObject();
			o.put("text", s);
			o.put("value", s);
			sourceArray.add(o);
		}
		request.setAttribute("source", sourceArray.toString());
	}
%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-log:table.sysLogUserOper') }</template:replace>
	<template:replace name="content">
		<!-- 筛选 -->
		<list:criteria channel="sysLogUserOper" >
			<%-- 未开三员情况下增加按用户筛选条件 --%>
			<c:if test="${param.method == 'listUser' || isAdmin}"> 
	            <list:cri-ref expand="true" ref="criterion.sys.person" key="fdOperatorId" title="${ lfn:message('sys-log:sysLogUserOper.fdOperatorId') }" multi="false" />
	        </c:if> 
			<%--操作日志相关搜索条件 --%>
			<c:if test="${param.eventType == 'oper' }">
				<list:cri-ref expand="true" key="fdModelDesc" ref="criterion.sys.string" title="${lfn:message('sys-log:sysLogUserOper.fdModelDesc') }" />
				<list:cri-ref expand="true" key="fdParaMethod" ref="criterion.sys.string" title="${lfn:message('sys-log:sysLogUserOper.fdParaMethod') }" />
				<list:cri-ref expand="true" key="fdEventType" ref="criterion.sys.string" title="${lfn:message('sys-log:sysLogUserOper.fdEventType') }" />
			</c:if>
			
			<%--组织架构、权限变更相关搜索条件 --%>
			<c:if test="${param.method == 'listOrg' || param.method == 'listAuth' }">
				<list:cri-ref expand="true" key="fdModelDesc" ref="criterion.sys.string" title="${lfn:message('sys-log:sysLogUserOper.fdModelDesc') }" />
				<list:cri-ref expand="true" key="fdEventType" ref="criterion.sys.string" title="${lfn:message('sys-log:sysLogUserOper.fdEventType') }" />
			</c:if>
			
			<list:cri-criterion title="${lfn:message('sys-log:sysLogUserOper.fdStatus') }" key="fdStatus" expand="true" multi="false"> 
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${lfn:message('sys-log:sysLogUserOper.enum.fdStatus.waitAudited') }', value:'0'},
							{text:'${lfn:message('sys-log:sysLogUserOper.enum.fdStatus.audited') }',value:'1'},
							{text:'${lfn:message('sys-log:sysLogUserOper.enum.fdStatus.noNeedAudited') }',value:'2'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion> 
			<list:cri-criterion title="${lfn:message('sys-log:sysLogUserOper.fdSuccess') }" key="fdSuccess" expand="true" multi="false"> 
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${lfn:message('sys-log:sysLogUserOper.enum.fdSuccess.fail') }', value:'0'},
							{text:'${lfn:message('sys-log:sysLogUserOper.enum.fdSuccess.success') }',value:'1'},
							{text:'${lfn:message('sys-log:sysLogUserOper.enum.fdSuccess.nonPrivileged') }',value:'2'}
							<%-- 锁定 --%>
							<c:if test="${param.eventType == 'login' }">
								,{text:'${lfn:message('sys-log:sysLogUserOper.enum.fdSuccess.locked') }',value:'9'}
							</c:if>
							]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<c:if test="${!empty source}">
			<!-- 来源 -->
			<list:cri-criterion title="${lfn:message('sys-log:elastic.search.source') }" key="source" expand="true">
				<list:box-select>
					<list:item-select cfg-defaultValue="ekp">
						<ui:source type="Static">
							${source}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			</c:if>
    		<!-- 年份 -->
			<list:cri-criterion title="${lfn:message('sys-log:sysLogBak.fdYear') }" key="fdYear" expand="true">
				<list:box-select>
					<list:item-select cfg-required="true" cfg-defaultValue="${year}">
						<ui:source type="Static">
							${yearArr}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<!-- 月份 -->
			<list:cri-criterion title="${lfn:message('sys-log:sysLogBak.fdMonth') }" key="fdMonth" expand="true">
				<list:box-select>
					<list:item-select cfg-required="true" cfg-defaultValue="${month}">
						<ui:source type="Static">
							${monthArr}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
        </list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall channel="sysLogUserOper"></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="sysLogUserOper">
					<list:sortgroup>
						<list:sort channel="sysLogUserOper" property="fdCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list" value="down"></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top" channel="sysLogUserOper"> 		
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar count="5" channel="sysLogUserOper">
                    <%-- 批量审计按钮 --%>
                    <kmss:auth requestURL="/sys/log/sys_log_user_oper/sysLogUserOper.do?method=audit&listMethod=${param.method }" requestMethod="POST">
                        <!-- 批量审计 -->
                        <ui:button text="${lfn:message('sys-log:sysLogUserOper.button.auditall')}" onclick="audit()" order="2" ></ui:button>
                    </kmss:auth>
                    <%-- 日志导出按钮 --%>
						<c:import url="/sys/log/sys_log_export/ui/buttons.jsp">
							<c:param name="listMethod" value="${param.method }"/>
							<c:param name="eventType" value="${param.eventType }"/>
						</c:import>
					</ui:toolbar>
				</div>
			</div>
		</div>
		
		<!-- 内容列表 -->
		<list:listview id="sysLogUserOper" channel="sysLogUserOper">
			<ui:source type="AjaxJson">
				{url:'/sys/log/sys_log_user_oper/sysLogUserOper.do?method=${param.method }&eventType=${param.eventType }'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/log/sys_log_user_oper/sysLogUserOper.do?method=view&fdId=!{fdId}" channel="sysLogUserOper">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<c:choose>
					<%-- 登录、登出 --%>
					<c:when test="${param.eventType == 'login' || param.eventType == 'logout'}">
						<list:col-auto props="fdCreateTime;fdIp;fdOperator;fdEventType;fdSuccess;fdStatus;operations"></list:col-auto>
					</c:when>
					<c:otherwise>
						<list:col-auto props="fdCreateTime;fdIp;fdOperator;fdEventType;fdModelDesc;fdSuccess;fdStatus;operations"></list:col-auto>
					</c:otherwise>
				</c:choose>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
	 			/* 隐藏最后一页按钮及翻页功能 */
	 			$('.lui_paging_jump_left').hide();
	 			if($('.lui_paging_ellipsis_left').length > 0) $('.lui_paging_num_left:last').hide();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging channel="sysLogUserOper"/>
	 	
	 	<script type="text/javascript">
	 		if(seajs.data.themes.dialog){
				seajs.use(seajs.data.themes.dialog[0]);
			}
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		//审计
		 		window.audit = function(id) {
		 			var values = [];
		 			if(id) {
		 				//单个审计
		 				values.push(id);
			 		} else {
			 			//批量审计
			 			$("#sysLogUserOper input[name='List_Selected']:checked").each(function() {
							values.push($(this).val());
						});
			 		}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
		 			var title = '<bean:message bundle="sys-log" key="sysLogUserOper.view.content.audit"/>';
					var url = '<c:url value="/sys/log/sys_log_user_oper/sysLogUserOper.do?method=audit"/>';
		 			dialog.iframe('/sys/log/sys_log_user_oper/import/iframe_audit.jsp', title,
		 				function (value){
		 	                // 回调方法
		 					if(value) {
								//后台刷新较慢，前端无法实时更新
								setTimeout(function(){
									topic.channel("sysLogUserOper").publish("list.refresh");
								}, 2400)
		 					}
		 				},
		 				{width:400,height:300,params:{url:url,data:$.param({"List_Selected" : values}, true)}}
		 			);
				}
		 	});
	 	</script>
	</template:replace>
</template:include>
