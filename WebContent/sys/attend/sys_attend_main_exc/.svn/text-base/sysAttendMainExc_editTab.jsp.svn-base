<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="content">

	<c:if test="${param.approveModel ne 'right'}">
		<form name="sysAttendMainExcForm" method="post" action ="${KMSS_Parameter_ContextPath}sys/attend/sys_attend_main_exc/sysAttendMainExc.do">
	</c:if>
			<html:hidden property="fdId" />
			<html:hidden property="fdAttendMainId" />
			<html:hidden property="fdCateTemplId" />
			<html:hidden property="docStatus" />
			<html:hidden property="docSubject" />
			<html:hidden property="fdManagerId" />
			<html:hidden property="method_GET" />
			<div class='lui_form_title_frame'>
				<div class='lui_form_subject'>
					${ lfn:message('sys-attend:table.sysAttendMainExc') }
				</div>
				<div class='lui_form_baseinfo'>
				</div>
			</div>
			<div class="lui_form_content_frame">
			<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width=15%>
							${ lfn:message('sys-attend:sysAttendCategory.attend.fdName') }
						</td>
						<td width="85%" colspan="3">
							<c:out value="${sysAttendMainExcForm.fdAttendMainCateName }" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendMain.docPersion"/>
						</td>
						<td width=30%>
							<c:out value="${sysAttendMainForm.docCreatorName}" />
						</td>
						<td class="td_normal_title" width=15%>
							${ lfn:message('sys-attend:sysAttendMain.export.dept') }
						</td>
						<td width=30%>
							${sysAttendMainForm.docCreatorDept }
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendMain.docCreateTime"/>
						</td>
						<td width=30%>
							<xform:datetime property="fdAttendTime" dateTimeType="datetime" showStatus="edit" validators="checkAttendTime required" subject="补卡时间"></xform:datetime>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendMain.fdLocation1"/>
						</td>
						<td width=30%>
							<c:if test="${not empty sysAttendMainForm.fdLocation }">
							<%@ taglib uri="/WEB-INF/KmssConfig/sys/attend/map.tld" prefix="map"%>
							<c:set var="fdLocationCoordinate" value="${sysAttendMainForm.fdLat}${','}${sysAttendMainForm.fdLng}"/>
							<map:location propertyName="fdLocation" nameValue="${sysAttendMainForm.fdLocation }"
								propertyCoordinate="fdLocationCoordinate" coordinateValue="${fdLocationCoordinate }" 
								showStatus="view"></map:location>
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendMain.fdStatus1"/>
						</td>
						<td colspan="3">
							<c:choose>
								<c:when test="${sysAttendMainForm.fdStatus=='1' && sysAttendMainForm.fdOutside}">
									${ lfn:message('sys-attend:sysAttendMain.fdOutside') }
								</c:when>
								<c:otherwise>
									<sunbor:enumsShow value="${sysAttendMainForm.fdStatus}" enumsType="sysAttendMain_fdStatus" />
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.reason') }</td>
						<td colspan="3">
<%-- 							<xform:textarea showStatus="edit" property="fdDesc" validators="maxLength(2000)" style="width:85%" />
 --%>                                    <xform:select property="fdDesc" showStatus="edit" required="true">
									<xform:simpleDataSource value="忘记打卡">忘记打卡</xform:simpleDataSource>
									<xform:simpleDataSource value="忘带工牌">忘带工牌</xform:simpleDataSource>
									<xform:simpleDataSource value="工牌丢失">工牌丢失</xform:simpleDataSource>
									</xform:select>
							<br></br>
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="attachment"/>
								<c:param name="fdAttType" value="pic"/>
							</c:import>
						</td>
					</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMain.illustrate') }
					</td>
					<td colspan="3">
						<span style="color:red">${ lfn:message('sys-attend:sysAttendMain.illustrateContent') }</span>
					</td>
				</tr>
				</table>
		</div>
		
		<c:choose> 
			<c:when test="${param.approveModel eq 'right'}">
				<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-supportExpand="true" var-expand="true"  var-average='false' var-useMaxWidth='true'>
					<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="sysAttendMainExcForm" />
						<c:param name="fdKey" value="attendMainExc" />
						<c:param name="showHistoryOpers" value="true" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
					</c:import>
					<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="sysAttendMainExcForm" />
						<c:param name="moduleModelName" value="com.landray.kmss.sys.attend.model.SysAttendMainExc" />
					</c:import>
				</ui:tabpanel>
			</c:when>
			<c:otherwise>
				<ui:tabpage expand="false">
					<c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="sysAttendMainExcForm" />
						<c:param name="fdKey" value="attendMainExc" />
					</c:import>
					<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="sysAttendMainExcForm" />
						<c:param name="moduleModelName" value="com.landray.kmss.sys.attend.model.SysAttendMainExc" />
					</c:import>
				</ui:tabpage>
			</c:otherwise>
		</c:choose>
		<script>
		seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
			var __validation = $KMSSValidation(document.forms['sysAttendMainExcForm']);
			__validation.addValidator('checkAttendTime','补卡时间须在打卡范围内', function(value){
				var fdStartTime = '${fdStartTime}';
				var fdEndTime ='${fdEndTime}';
				if(value && fdStartTime && fdEndTime) {
					var date = Com_GetDate(value, 'datetime',Com_Parameter.DateTime_format);
					fdStartTime = Com_GetDate('${fdStartTime}', 'datetime', Com_Parameter.DateTime_format);
					fdEndTime = Com_GetDate('${fdEndTime}', 'datetime', Com_Parameter.DateTime_format);
					if(date && fdStartTime && fdEndTime) {
						return date.getTime() >= fdStartTime.getTime() && date.getTime() <= fdEndTime.getTime();
					}
				}
				return true;
			});
			
			window.commitMethod = function(method, saveDraft) {
				window.loading = dialog.loading();
				var url =  "${LUI_ContextPath }/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=isAllowPatch&fdAttendMainId=${param.fdAttendMainId}";
				$.ajax({
				   type: "GET",
				   url: url,
				   dataType: "json",
				   async:false,
				   success: function(data){
					   loading && loading.hide();
					   if(data) {
						  if(data.status == 0 && data.msg){
							  var text = '';
							  switch(data.msg) {
								  case 'notAllow' : text = "${ lfn:message('sys-attend:sysAttendMainExc.error.notAllow') }";break;
								  case 'overdue' : text = "${ lfn:message('sys-attend:sysAttendMainExc.error.overdue') }";break;
								  case 'overTimes': text = "${ lfn:message('sys-attend:sysAttendMainExc.error.overdue') }";break;
								  default : 'error';
							  }
							  dialog.alert(text);
							  return;
						  }  else if(data.status == 3 && data.msg){
							  //补卡超过次数按事假半天计算
							  var text = '';
							  switch(data.msg) {
								  case 'overTimes': text = Msg['sysAttendMainExc.error.overdue.overTimes'];break;
								  default : Msg['mui.submit.exc.fail'];
							  }
							  Tip.fail({
								  text:text
							  });
						  }
					   }
					   _commitMethod(method, saveDraft);
				   },
				   error : function(e){
					   loading && loading.hide();
					   console.error(e);
					   _commitMethod(method, saveDraft);
				   }
				});
			}
			
			var _commitMethod = function(method, saveDraft) {
				var docStatus = document.getElementsByName("docStatus")[0];
				if (saveDraft != null && saveDraft == 'true'){
					docStatus.value = "10";
				} else {
					docStatus.value = "20";
				}
				Com_Submit(document.forms['sysAttendMainExcForm'], method);
			};
		});
		</script>
		<c:if test="${param.approveModel ne 'right'}">
			</form>
		</c:if>
	</template:replace>
	<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
				 <c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysAttendMainExcForm" />
					<c:param name="fdKey" value="attendMainExc" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
				</c:import> 
			</ui:tabpanel>
		</template:replace>
	</c:when>
</c:choose>