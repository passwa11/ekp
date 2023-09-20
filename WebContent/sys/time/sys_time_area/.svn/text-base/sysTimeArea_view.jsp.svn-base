<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="auto">
<template:replace name="title">
		<c:out value="${sysTimeAreaForm.fdName}-${ lfn:message('sys-time:module.sys.time')}"></c:out>
	</template:replace>
	<template:replace name="head">
		<link href="resource/css/maincss.css" rel="stylesheet">
		<link href="resource/css/css.css" rel="stylesheet">
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6" var-navwidth="90%" style="display:none;">
			<kmss:auth requestURL="/sys/time/sys_time_area/sysTimeArea.do?method=edit&fdId=${sysTimeAreaForm.fdId}" requestMethod="GET">
				<ui:button order="1" text="${ lfn:message('sys-time:sysTimeArea.button.workEdit') }" onclick="onDoScheduling('${sysTimeAreaForm.fdId}')"></ui:button>
			 </kmss:auth>
			 <kmss:auth requestURL="/sys/time/sys_time_area/sysTimeArea.do?method=edit&fdId=${JsParam.fdId}" requestMethod="GET">
				<ui:button order="2" text="${ lfn:message('button.edit') }" onclick="Com_OpenWindow('sysTimeArea.do?method=edit&fdId=${JsParam.fdId}','_self');"></ui:button>
			 </kmss:auth>
			 <kmss:auth requestURL="/sys/time/sys_time_area/sysTimeArea.do?method=clone&fdId=${JsParam.fdId}" requestMethod="GET">
				<c:if test="${sysTimeAreaForm.fdIsBatchSchedule != true}">
					<ui:button order="3" text="${ lfn:message('sys-time:sysTimeArea.btn.clone') }" onclick="Com_OpenWindow('sysTimeArea.do?method=clone&cloneModelId=${JsParam.fdId}','_blank');"></ui:button>
				</c:if>
			</kmss:auth>
			<kmss:auth requestURL="/sys/time/sys_time_area/sysTimeArea.do?method=delete&fdId=${JsParam.fdId}" requestMethod="GET">
				<ui:button order="4" text="${ lfn:message('button.delete') }" onclick="confirmDelete()"></ui:button>
			</kmss:auth>
			 <ui:button order="5" onclick="top.close();" text="${ lfn:message('button.close') }"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<p class="txttitle"><bean:message  bundle="sys-time" key="title.timeSetting"/></p>
		<center>
			<table class="tb_normal" width=98%>
				<html:hidden name="sysTimeAreaForm" property="fdId"/>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-time" key="sysTimeArea.fdName"/>
					</td>
					<td width=85% colspan=3>
						<bean:write name="sysTimeAreaForm" property="fdName"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-time" key="sysTimeArea.fdHoliday"/>
					</td>
					<td width=85% colspan=3>
						<bean:write name="sysTimeAreaForm" property="fdHolidayName"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-time" key="sysTimeArea.scope"/>
					</td>
					<td width=85% colspan=3 word-break="break-all">
						<bean:write name="sysTimeAreaForm" property="areaMemberNames"/>	
						<br/><br/>
						<xform:checkbox property="fdIsBatchSchedule" showStatus="noShow">
							<xform:simpleDataSource value="true">
								<bean:message bundle="sys-time" key="sysTimeArea.isBatch"/>
							</xform:simpleDataSource>
						</xform:checkbox>					
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-time" key="sysTimeArea.timeAdmin"/>
					</td>
					<td width=85% colspan=3>
						<bean:write name="sysTimeAreaForm" property="areaAdminNames"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-time" key="sysTimeArea.docCreatorId"/>
					</td><td width=35%>
						<bean:write name="sysTimeAreaForm" property="docCreatorName"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-time" key="sysTimeArea.docCreateTime"/>
					</td><td width=35%>
						<bean:write name="sysTimeAreaForm" property="docCreateTime"/>
					</td>
				</tr>
			</table>
		</center>
		<script>
		seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
			window.confirmDelete = function(msg){
				var del = confirm("<bean:message key="page.comfirmDelete"/>");
				if(del){
					Com_OpenWindow('sysTimeArea.do?method=delete&fdId=${JsParam.fdId}','_self');
				}
			};
			window.onDoScheduling = function(id){
	 			var url = '<c:url value="/sys/time/sys_time_area/sysTimeArea.do" />?method=getTimeArea&fdId=' + id;
	 			$.ajax({
	 			   type: "GET",
	 			   url: url,
	 			   dataType: "json",
	 			   success: function(result){
	 				  if(result && result.status==1){
	 					 doScheduling(id,result.data.fdBatchSchedule,result.data.isScheduled);
	 				  }else{
	 					 dialog.failure("${lfn:message('return.optFailure')}");
	 				  }
	 			   },
	 			   error : function(e){
	 				  dialog.failure("${lfn:message('return.optFailure')}");
	 			   }
	 			});
	 		};
			//排班
			window.doScheduling = function(id,fdIsBatchSchedule,isScheduled){
					var url = "/sys/time/sys_time_area/sysTimeArea_selectCalendar.jsp?fdId=" + id;
					var pUrl = '<c:url value="/sys/time/sys_time_area/sysTimeArea.do" />?method=editMCalendar&fdId=' + id;
					var bUrl = '<c:url value="/sys/time/sys_time_area/sysTimeArea.do" />?method=editCalendar&fdId=' + id;
					if(fdIsBatchSchedule=='true'){
						url =pUrl
						Com_OpenWindow(url,'_blank');
					}else if(isScheduled=='true'){
						url = bUrl;
						Com_OpenWindow(url,'_blank');
					}else{
						 dialog.iframe(url,
					            '选择排班方式',
					            function(result) {
					                if (!result) { return; }
					               	if(result.value==1){
					               		url = bUrl;
					               	}else{
					               		url = pUrl;
					               	}
					               Com_OpenWindow(url);
					            },
					            {
					                width: 500,
					                height: 250,
					                params: {
					                    data: null,
					                    method: 'add'
					                }
					            }
					        );
					}
					
				};
		});
			
		</script>
	</template:replace>
</template:include>