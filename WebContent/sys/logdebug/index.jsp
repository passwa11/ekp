<%@page import="com.landray.kmss.sys.log.config.SysLogConfig"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%
	if(!SysLogConfig.DEBUG_MODE){
		response.sendError(404);
		return;
	}

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
%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-log:table.sysLogUserOper') }(测试用)</template:replace>
	<template:replace name="content">
		<!-- 筛选 -->
		<list:criteria channel="sysLogUserOper">
			<%--普通用户日志相关搜索条件 --%>
			<list:cri-ref expand="true" ref="criterion.sys.person" key="fdOperatorId" title="${ lfn:message('sys-log:sysLogUserOper.fdOperatorId') }" />
			<%--操作日志相关搜索条件 --%>
			<list:cri-ref expand="true" key="fdModelDesc" ref="criterion.sys.string" title="${lfn:message('sys-log:sysLogUserOper.fdModelDesc') }" />
			<list:cri-ref expand="true" key="fdParaMethod" ref="criterion.sys.string" title="${lfn:message('sys-log:sysLogUserOper.fdParaMethod') }" />
			<list:cri-ref expand="true" key="fdEventType" ref="criterion.sys.string" title="${lfn:message('sys-log:sysLogUserOper.fdEventType') }" />
			<list:cri-criterion title="${lfn:message('sys-log:sysLogUserOper.fdStatus') }" key="fdStatus" expand="true"> 
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
			<list:cri-criterion title="${lfn:message('sys-log:sysLogUserOper.fdSuccess') }" key="fdSuccess" expand="true"> 
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
			<%--日志测试使用的相关搜索条件--%>
			<list:cri-ref expand="true" key="fdEventTypeNotEquals" ref="criterion.sys.string" title="${lfn:message('sys-log:sysLogUserOper.fdEventType') }不等于(测试用)" />
			<list:cri-criterion title="${lfn:message('sys-log:sysLogUserOper.fdModelDesc') }为空(测试用)" key="fdModelDescIsNull" expand="true"> 
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'为空', value:'true'},
							{text:'不为空',value:'false'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
        </list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
			<div style='color: #979797;float: left;padding-top:1px;'>
				${ lfn:message('list.orderType') }：
			</div>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="sysLogUserOper">
					<list:sortgroup>
						<list:sort channel="sysLogUserOper" property="fdCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list" value="down"></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- mini分页 -->
			<div style="float:left;">
				<list:paging layout="sys.ui.paging.top" channel="sysLogUserOper"> 		
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar count="5" channel="sysLogUserOper">
						<kmss:auth requestURL="/sys/log/sys_log_user_oper/sysLogUserOper.do?method=audit" requestMethod="POST">
							<!-- 批量审计 -->
							<ui:button text="${lfn:message('sys-log:sysLogUserOper.button.auditall')}" onclick="audit()" order="2" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		
		<!-- 内容列表 -->
		<list:listview id="sysLogUserOper" channel="sysLogUserOper">
			<ui:source type="AjaxJson">
				{url:'/sys/logdebug/action.do?method=listDebug'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/logdebug/action.do?method=view&logdebug=true&fdId=!{fdId}" channel="sysLogUserOper">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdCreateTime;fdIp;fdOperator;fdEventType;fdModelDesc;fdSuccess;fdStatus;operations"></list:col-auto>
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
					var url  = '<c:url value="/sys/log/sys_log_user_oper/sysLogUserOper.do?method=audit"/>';
					dialog.confirm('<bean:message bundle="sys-log" key="page.comfirmAudit"/>', function(value) {
						if(value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url : url,
								type : 'POST',
								data : $.param({"List_Selected" : values}, true),
								dataType : 'json',
								error : function(data) {
									if(window.del_load != null) {
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: function(data) {
									if(window.del_load != null){
										window.del_load.hide();
										//后台刷新较慢，前端无法实时更新
										setTimeout(function(){
											topic.channel("sysLogUserOper").publish("list.refresh");
										}, 2400)
									}
									dialog.result(data);
								}
						   });
						}
					});
				};
		 	});
	 	</script>
	</template:replace>
</template:include>
