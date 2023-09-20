<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*,com.landray.kmss.util.*,net.sf.json.JSONArray,net.sf.json.JSONObject"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script>
	Com_IncludeFile("sea.js");

	function confirmDelete(msg){
		var del = confirm("<bean:message key="page.comfirmDelete"/>");
		return del;
	}

	
	$(function(){
		
	});
</script>
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/notify/sys_notify_mkrequest/sysNotifyMKRequest.do?method=edit">
	<c:if test="${sysNotifyMKRequestForm.fdSuccess=='false'}">
			<input type="button"
		value="重发"
		onclick="javascript:retry('${sysNotifyMKRequestForm.fdId}');">
	</c:if>
	<input type="button"
		value="<bean:message key="button.delete"/>"
		onclick="if(!confirmDelete())return;Com_OpenWindow('sysNotifyMKRequest.do?method=delete&fdId=${sysNotifyMKRequestForm.fdId}','_self');">
		
	</kmss:auth>

	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-notify" key="table.sysNotifyMKRequest"/></p>

<center>
<table class="tb_normal" width=95% style="table-layout:fixed; word-wrap:break-word;">
	<tr>
		<td class="td_normal_title" width=20%>
			${ lfn:message('sys-notify:sysNotifyMKRequest.fdTraceId') }
		</td>
		<td width=25% >
			<c:out value="${sysNotifyMKRequestForm.fdTraceId}" />
		</td>
		<td class="td_normal_title" width=20%>
			<bean:message bundle="sys-notify" key="sysNotifyMKRequest.fdSuccess"/>
		</td>
		<td width=30%>
			<c:if test="${sysNotifyMKRequestForm.fdSuccess=='true'}">
				成功
			 </c:if>
			<c:if test="${sysNotifyMKRequestForm.fdSuccess=='false'}">
				失败
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=20%>
			ModelId
		</td>
		<td width=30% >
			<c:out value="${sysNotifyMKRequestForm.fdModelId}" />
		</td>
		<td class="td_normal_title" width=20%>
			ModelName
		</td>
		<td width=30%>
			<c:out value="${sysNotifyMKRequestForm.fdModelName}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=20%>
			${ lfn:message('sys-notify:sysNotifyMKRequest.fdCreateTime') }
		</td>
		<td width=30% >
			<sunbor:date datePattern="yyyy-MM-dd HH:mm:ss" value="${sysNotifyMKRequestForm.fdCreateTime}" />
		</td>
		<td class="td_normal_title" width=20%>
			<bean:message bundle="sys-notify" key="sysNotifyMKRequest.fdUrl"/>
		</td>
		<td width=30%>
			<c:out value="${sysNotifyMKRequestForm.fdUrl}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=20%>
			${ lfn:message('sys-notify:sysNotifyMKRequest.fdRequestBody') }
		</td>
		<td width=30% ><c:out value="${sysNotifyMKRequestForm.fdRequestBody}" /></td>
		<td class="td_normal_title" width=20%>
			${ lfn:message('sys-notify:sysNotifyMKRequest.fdResponseBody') }
		</td>
		<td width=30%><c:out value="${sysNotifyMKRequestForm.fdResponseBody}" /></td>
	</tr>
	
</table>
</center>
<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 重发
		 		window.retry = function(id) {
		 			var values = [];
		 			if(id) {
		 				values.push(id);
			 		} else {
						$("input[name='List_Selected']:checked").each(function() {
							values.push($(this).val());
						});
			 		}
					
					var url  = '<c:url value="/sys/notify/sys_notify_mkrequest/sysNotifyMKRequest.do?method=retryall"/>';
					dialog.confirm('所选记录的相关数据是否存在重复！您确认要执行此重发操作吗？', function(value) {
						if(value == true) {
							window.retry_load = dialog.loading();
							$.ajax({
								url : url,
								type : 'POST',
								data : $.param({"List_Selected" : values}, true),
								dataType : 'json',
								error : function(data) {
									if(window.retry_load != null) {
										window.retry_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: function(data) {
									if(window.retry_load != null){
										window.retry_load.hide(); 
										topic.publish("list.refresh");
									}
									dialog.result(data);
								}
						   });
						}
					});
				};
		 	});
	 	</script>
<%@ include file="/resource/jsp/view_down.jsp"%>