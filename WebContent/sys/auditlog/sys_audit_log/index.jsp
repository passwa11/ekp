<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.auditlog.service.ISysAuditlogService"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="net.sf.json.JSONObject"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">
		<c:out value="${lfn:message('sys-auditlog:table.auditlog')}"/>
	</template:replace>
	<template:replace name="head">
		<script type="text/javascript">
		</script>
	</template:replace>
	
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdSubject" ref="criterion.sys.docSubject" title="${lfn:message('sys-auditlog:sysAuditlog.fdSubject')}">
			 </list:cri-ref>
			 <list:cri-ref key="fdOperator" ref="criterion.sys.string" title="${lfn:message('sys-auditlog:sysAuditlog.fdOperator')}">
			 </list:cri-ref>
			 <list:cri-criterion title="${lfn:message('sys-auditlog:sysAuditlog.fdModelName') }" key="fdModelName" multi="true">
				<list:box-select>
					<list:item-select type="lui/criteria!CriterionSelectDatas" >
						<ui:source type="AjaxJson" >
							{url: "/sys/auditlog/sys_audit_log/sysAuditlog.do?method=getModuleList"}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-criterion title="${lfn:message('sys-auditlog:sysAuditlog.fdbuckupTime') }" key="fdbuckupTime" multi="false">
				<list:box-select>
					<list:item-select cfg-required="true" cfg-defaultValue="0">
						<ui:source type="Static">
							<% 
								ISysAuditlogService auditlogService = (ISysAuditlogService)SpringBeanUtil.getBean("sysAuditlogService");
								List list = auditlogService.getAuditBackTableList();
								JSONArray array = new JSONArray();
								SysDataDict dict = SysDataDict.getInstance();
								JSONObject jsonObj = new JSONObject();
								jsonObj.put("text", "当前时间");
								jsonObj.put("value", "0");
								array.add(jsonObj);
								for (Iterator iter = list.iterator(); iter.hasNext();) {
									Object[] backInfo = (Object[]) iter.next();
									if (backInfo != null) {
										String key = (String) backInfo[0];
										String name = (String) backInfo[1];
										if (StringUtil.isNotNull(key) && StringUtil.isNotNull(name)) {
											jsonObj = new JSONObject();
											jsonObj.put("text", name);
											jsonObj.put("value", key);
											array.add(jsonObj);
										}
									}
								}
								out.print(array.toString()); 
							%>
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-auto modelName="com.landray.kmss.sys.auditlog.model.SysAuditlog" property="fdOptType" />
		</list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
						<list:sort property="fdCreateTime" text="${lfn:message('sys-auditlog:sysAuditlog.fdCreateTime') }" group="sort.list" value="down"></list:sort>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/auditlog/sys_audit_log/sysAuditlog.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			     rowHref="/sys/auditlog/sys_audit_log/sysAuditlog.do?method=view&fdId=!{fdId}&fdBackUpKey=!{fdBackUpKey}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdSubject,fdOperator,fdIp,fdModelName,fdOptType,fdCreateTime"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	</template:replace>
</template:include>