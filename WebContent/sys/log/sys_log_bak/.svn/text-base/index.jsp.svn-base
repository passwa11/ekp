<%@page import="com.landray.kmss.sys.log.config.SysLogConfig"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%
	//无年份默认今年
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
	
	//清理间隔
	Integer cleanInteval = SysLogConfig.cleanCycle;
	request.setAttribute("cleanInteval", cleanInteval);
%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-log:table.sysLogBak') }</template:replace>
	<template:replace name="content">
		<!-- 筛选 -->
		<list:criteria channel="sysLogBak">
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
        </list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall channel="sysLogBak"></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="sysLogBak">
					<list:sortgroup>
					<list:sort channel="sysLogBak" property="fdMonth" text="${lfn:message('sys-log:sysLogBak.fdMonth') }" group="sort.list" value="down"></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top" channel="sysLogBak"> 		
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar count="5" channel="sysLogBak">
						<kmss:auth requestURL="/sys/log/sys_log_bak/sysLogBak.do?method=audit" requestMethod="POST">
							<!-- 批量备份  去掉批量备份  elastic 只支持单快照执行，如果需要做批量备份，批量备份逻辑需要调整；by chenpeng
							<ui:button text="${lfn:message('sys-log:sysLogBak.backupAll')}" onclick="backup()" order="1" ></ui:button>
							-->
							<!-- 批量清理 -->
							<ui:button text="${lfn:message('sys-log:sysLogBak.cleanAll')}" onclick="clean()" order="2" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		
		<!-- 内容列表 -->
		<list:listview id="sysLogBak" channel="sysLogBak">
			<ui:source type="AjaxJson">
				{url:'/sys/log/sys_log_bak/sysLogBak.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/log/sys_log_bak/sysLogBak.do?method=view&fdId=!{fdId}" channel="sysLogBak">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
                  <list:col-auto props="fdDate;fdBackupStatus.name;fdBackupDate;fdBackupSource.name;fdCleanStatus.name;fdCleanDate;fdRecoveryStatus.name;fdDesc;operations" />
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging channel="sysLogBak"/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		//备份
		 		window.backup = function(id) {
		 			var url = '<c:url value="/sys/log/sys_log_bak/sysLogBak.do?method=backup"/>';
		 			var confirm = '<bean:message bundle="sys-log" key="page.comfirmBackup" arg0="${cleanInteval }"/>';
		 			customOpt(id, url, confirm, true);
		 		}

		 		//清理
		 		window.clean = function(id) {
		 			var url = '<c:url value="/sys/log/sys_log_bak/sysLogBak.do?method=clean"/>';
		 			var confirm = '<bean:message bundle="sys-log" key="page.comfirmClean"/>';
		 			customOpt(id, url, confirm, true);
		 		}

		 		//恢复
		 		window.recovery = function(id) {
		 			var title = '<bean:message bundle="sys-log" key="enums.backup_detail_type.recovery"/>';
		 	    	var actionUrl = '<c:url value="/sys/log/sys_log_bak/sysLogBak.do?method=recovery"/>';
		 			dialog.iframe('/sys/log/sys_log_bak/import/iframe_recovery.jsp?fdId='+id, title,
		 				function (value){
		 	                // 回调方法
		 					if(value) {
		 	                    dialog.result(value);
								topic.channel("sysLogBak").publish("list.refresh");
		 					}
		 				},
		 				{width:400,height:220,params:{url:actionUrl,data:"fdId="+id}}
		 			);
		 		}

		 		window.customOpt = function(id, url, confirm, batch) {
		 			var values = [];
		 			if(id) {
		 				//单个备份
		 				values.push(id);
			 		} else if(batch){
			 			//批量备份
			 			$("#sysLogBak input[name='List_Selected']:checked").each(function() {
							values.push($(this).val());
						});
			 		}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm(confirm, function(value) {
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
										topic.channel("sysLogBak").publish("list.refresh");
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
