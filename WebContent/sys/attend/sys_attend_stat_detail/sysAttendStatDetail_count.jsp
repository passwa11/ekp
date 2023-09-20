<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.Date,java.util.Calendar,com.landray.kmss.util.DateUtil,com.landray.kmss.util.UserUtil,com.landray.kmss.util.DateUtil" %>
<%@ page import="com.landray.kmss.sys.attend.service.ISysAttendStatService,com.landray.kmss.util.SpringBeanUtil" %>
<%
	ISysAttendStatService sysAttendStatService = (ISysAttendStatService) SpringBeanUtil.getBean("sysAttendStatService");
	request.setAttribute("isStatDeptLeader", sysAttendStatService.isStatDeptLeader());
	request.setAttribute("isStatCateReader", sysAttendStatService.isStatCateReader());
	
	String deptId =  UserUtil.getUser().getFdParent() == null ? "" :
		UserUtil.getUser().getFdParent().getFdId();
	String deptName = UserUtil.getUser().getFdParentsName();
%>
<template:include ref="default.simple4list">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/sys/attend/resource/css/attend.css" />
		<style type="text/css">
			td.orgTd {position: relative;}fdDelayedTime
			td.orgTd label{position: absolute;top: 10px;margin-left: 5px;}
			.btn_txt {margin: 0px 2px;color: #2574ad;border-bottom: 1px solid transparent;}
			.btn_txt:hover {color: #123a6b;border-bottom-color: #123a6b;}
			.sign_status {color: #2574ad}
			.sign_status:hover {border-bottom:1px solid #2574ad;}
			.sign_status_exc {color: #f00;font-weight: bold;}
			.sign_status_exc:hover {border-bottom:1px solid #f00;}
		</style>
		<script type="text/javascript">
			seajs.use(['theme!form']);
			Com_IncludeFile("validation.js|plugin.js|validation.jsp|xform.js|eventbus.js");
			/**
			 * 添加js和样式到顶部
			 * @type {HTMLScriptElement}
			 */
			var link = document.createElement('link');
			link.rel = 'Stylesheet';
			link.href = "${LUI_ContextPath}/sys/attend/resource/css/export.css";
			window.top.document.body.appendChild(link);

			var script = document.createElement('script');
			script.type = 'text/javascript';
			script.src = "${LUI_ContextPath}/sys/attend/resource/js/reportDrawer.js";
			window.top.document.body.appendChild(script);

		</script>
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
				   <c:param name="key" value="sysAttendStatDetail"></c:param>
				   <c:param name="criteria" value="statDetailCriteria"></c:param>
				</c:import>		 
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${ lfn:message('sys-attend:table.sysAttendStatDetail') }">
				<form id="calDayForm" name="calDayForm" action="${LUI_ContextPath}/sys/attend/sys_attend_stat_detail/sysAttendStatDetail.do" method="post">
				 	<div style="margin-top: 20px; padding: 0 30px;">
					 	<table class="tb_normal" width=100%>
					 		<tr>
					 			<td class="td_normal_title" width="150px">
									${ lfn:message('sys-attend:sysAttendCategory.range') }
								</td>			
								<td class="orgTd">
									<c:set var="showTargetType" value="${isStatDeptLeader ? 1 : (isStatCateReader ? 2 : 1) }"></c:set>
									<c:if test="${isStatDeptLeader || isStatCateReader}">
									<div style="width: 150px;float:left;">
										<xform:select property="fdTargetType" style="width: 90%;height: 25px;margin-top: 2px;" showPleaseSelect="false" showStatus="edit" onValueChange="changeTargetType" value="${showTargetType}">
											<c:if test="${isStatDeptLeader}">
												<xform:simpleDataSource value="1">${lfn:message('sys-attend:sysAttendReport.range.byDepartment') }</xform:simpleDataSource>
											</c:if>
											<c:if test="${isStatCateReader}">
												<xform:simpleDataSource value="2">${lfn:message('sys-attend:sysAttendReport.range.byAttendanceGroup') }</xform:simpleDataSource>
											</c:if>
										</xform:select>
									</div>
									</c:if>
									<%-- 部门 --%>
									<c:if test="${isStatDeptLeader}">
									<div id='targetDept' style="width: 50%;float:left;${showTargetType != 1 ? 'display:none;' : ''}">
										<xform:address propertyId="fdTargetId" propertyName="fdTargetName"
											validators="${showTargetType == 1 ? 'required' : ''}"
													   mulSelect="true" showStatus="edit"
													   subject="${ lfn:message('sys-attend:sysAttendStatDetail.dept') }"
														idValue="<%=deptId %>"
													   nameValue="<%=deptName %>"
													   orgType="ORG_FLAG_AVAILABLEALL"
											style="width:95%;height: 25px;"></xform:address>
										<span class="txtstrong">*</span>
									</div>
									</c:if>
									<%-- 考勤组 --%>
									<c:if test="${isStatCateReader}">
									<div id='targetCate' style="width: 50%;float:left;${showTargetType != 2 ? 'display:none;' : ''}">
										<xform:dialog propertyId="fdCategoryIds" propertyName="fdCategoryNames" showStatus="edit" 
											style="width:95%;height: 26px;" validators="${showTargetType == 2 ? 'required' : ''}" subject="${ lfn:message('sys-attend:sysAttendCategory.attend') }">
									  	 	selectCategory();
										</xform:dialog>
										<span class="txtstrong">*</span>
									</div>
									</c:if>
									<c:if test="${isStatDeptLeader || isStatCateReader}">
									<xform:checkbox property="fdIsQuit" showStatus="edit">
										<xform:simpleDataSource value="true">${ lfn:message('sys-attend:sysAttendStatDetail.fdIsQuit') }</xform:simpleDataSource>
									</xform:checkbox>
									</c:if>
								</td>
					 		</tr>
					 		<tr>
								<td class="td_normal_title" width="150px">
									${ lfn:message('sys-attend:sysAttendStatDetail.dateSection') }
								</td>			
								<td>
									<%
										Calendar cal = Calendar.getInstance();
										cal.setTime(new Date());
										Date endTime = cal.getTime();
										cal.set(Calendar.DATE, 1);
										Date startTime = cal.getTime();
										String _startTime =  DateUtil.convertDateToString(startTime, DateUtil.TYPE_DATE, request.getLocale());
										String _endTime =  DateUtil.convertDateToString(endTime, DateUtil.TYPE_DATE, request.getLocale());
										
									%>
									<xform:datetime property="fdStartTime" dateTimeType="date" showStatus="edit" 
										required="true" validators="beforeEndTime" subject="${ lfn:message('sys-attend:sysAttendStatDetail.fdStartTime') }" value="<%=_startTime %>"
										style="width:150px"></xform:datetime>
									<span style="position: relative;top:-5px;">-</span>
									<xform:datetime property="fdEndTime" dateTimeType="date" showStatus="edit" 
										required="true" subject="${ lfn:message('sys-attend:sysAttendStatDetail.fdEndTime') }" value="<%=_endTime %>"
										style="width:150px"></xform:datetime>	 	
								</td>
						 	</tr>
						 	<tr>
						 		<td class="td_normal_title" width="150px">
									${ lfn:message('sys-attend:sysAttendStatDetail.docStatus') }
									<xform:checkbox property="fdAllStatus" showStatus="edit" isArrayValue="false" value="1" onValueChange="selectAll">
					 				<xform:simpleDataSource value="1">${ lfn:message("sys-ui:ui.listview.selectall") }</xform:simpleDataSource>
					 			</xform:checkbox>
								</td>			
								<td>
									<xform:checkbox property="fdStatus"  showStatus="edit" 
										subject="${ lfn:message('sys-attend:sysAttendStatDetail.docStatus') }" 
										isArrayValue="false" value="0;1;2;3;4;5;6;7;8;9" required="true" onValueChange="changeColumn"> 
										<xform:simpleDataSource value="1">${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok') }</xform:simpleDataSource>
										<xform:simpleDataSource value="6">${ lfn:message('sys-attend:sysAttendMain.outside') }</xform:simpleDataSource>
										<xform:simpleDataSource value="2">${ lfn:message('sys-attend:sysAttendMain.fdStatus.late') }</xform:simpleDataSource>
										<xform:simpleDataSource value="3">${ lfn:message('sys-attend:sysAttendMain.fdStatus.left') }</xform:simpleDataSource>
										<xform:simpleDataSource value="0">${ lfn:message('sys-attend:sysAttendMain.fdStatus.unSign') }</xform:simpleDataSource>
										<xform:simpleDataSource value="7">${ lfn:message('sys-attend:sysAttendMain.fdStatus.missed') }</xform:simpleDataSource>
										<xform:simpleDataSource value="4">${ lfn:message('sys-attend:sysAttendMain.fdStatus.business') }</xform:simpleDataSource>
										<xform:simpleDataSource value="5">${ lfn:message('sys-attend:sysAttendMain.fdStatus.askforleave') }</xform:simpleDataSource>
										<xform:simpleDataSource value="8">${ lfn:message('sys-attend:sysAttendMain.fdStatus.overtime') }</xform:simpleDataSource>
										<xform:simpleDataSource value="9">${ lfn:message('sys-attend:sysAttendMain.fdStatus.outgoing') }</xform:simpleDataSource>
									</xform:checkbox>
								</td>
						 	</tr>
					 	</table>
					 	<div style="text-align: center;padding: 20px;">
					 		<ui:button text="${ lfn:message('button.list') }" order="1" style="margin-right: 10px;"
						      onclick="listDetail();">
							</ui:button>
							<ui:button text="${ lfn:message('sys-attend:sysAttendReport.exportExcel') }" order="2"  style="margin-right: 10px;"
							  onclick="exportDetail();">
							</ui:button>

							<ui:button text="${ lfn:message('sys-attend:sysAttendReportLog.list') }" order="3"
									   onclick="window.top.exportLog('1');">
							</ui:button>

					 	</div>
				 	</div>
				 </form>
				 <div class="lui-attend-stat-table" style="display:none;">
				 	<list:listview id="attendStatListview">
						<ui:source type="AjaxJson">
								{url:""}
						</ui:source>
					<!-- 列表视图 -->	
					<list:colTable isDefault="false" layout="sys.attend.listview.stat" 
						rowHref="">
						<list:col-serial></list:col-serial>
						<list:col-auto props="docCreator.fdName;docCreator.fdNo;docCreator.fdDept;fdCategoryName;"></list:col-auto>
						<list:col-auto props="fdFirstLevelDepartmentName;fdSecondLevelDepartmentName;fdThirdLevelDepartmentName;"></list:col-auto>
						
						<list:col-html id="fdDate" headerStyle="min-width: 130px;" sort="fdDate" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdDate') }">{$ {%row['fdDate']%} $}</list:col-html>
						<list:col-auto props="fdDateType"></list:col-auto>
						<list:col-auto props="fdRestTime;fdStandWorkTime;fdMonthLateNum;fdMonthForgerNum;fdMonthLateMinNum;fdDelayedTime;fdAttendResult;"></list:col-auto>
						<list:col-auto props="fdStartTime;fdSignTime;docStatus;fdEndTime;fdSignTime2;docStatus2;"></list:col-auto>
						<list:col-auto props="fdSignTime3;docStatus3;fdState3;fdSignTime4;docStatus4;fdState4;"></list:col-auto>
						<list:col-auto props="fdSignTime5;docStatus5;fdState5;fdSignTime6;docStatus6;fdState6;"></list:col-auto>
						<list:col-auto props="fdSignTime7;docStatus7;fdState7;fdSignTime8;docStatus8;fdState8;"></list:col-auto>
						<list:col-auto props="fdSignTime9;docStatus9;fdState9;fdSignTime10;docStatus10;fdState10;"></list:col-auto>
						<list:col-html sort="fdTotalTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdTotalTime') }">{$ {%row['fdTotalTime']%} $}</list:col-html>
						<list:col-html sort="fdOverTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdOverTime') }">{$ {%row['fdOverTime']%} $}</list:col-html>
						<list:col-html sort="fdOutgoingTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdOutgoingTime') }">{$ {%row['fdOutgoingTime']%} $}</list:col-html>
						<list:col-html sort="fdLateTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdLateTime') }">{$ {%Math.round(row['fdLateTime']*60)%} $}</list:col-html>
						<list:col-html sort="fdLeftTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdLeftTime') }">{$ {%Math.round(row['fdLeftTime']*60)%} $}</list:col-html>
						<list:col-html sort="fdAbsentDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdAbsentDays') }">{$ {%row['fdAbsentDays']%} $}</list:col-html>
						<list:col-html sort="fdPersonalLeaveDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdPersonalLeaveDays') }">{$ {%row['fdPersonalLeaveDays']%} $}</list:col-html>
<%-- 						<list:col-html sort="fdOffDays" headerClass="lui_thead" title="请假<br>(小时)">{$ {%row['fdOffDays']%} $}</list:col-html> --%>
						<list:col-html sort="fdOffDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdOffDays') }">{$ {%row['fdOffDays']%} $}</list:col-html>
						<list:col-html sort="fdTripDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdTripDays') }">{$ {%row['fdTripDays']%} $}</list:col-html>
						<list:col-auto props="operation"></list:col-auto>
					</list:colTable>
					</list:listview> 
					 <ui:event topic="list.loaded">
					 	if(window.list_load != null){
					 		window.list_load.hide();
					 	}
					 </ui:event>
				 	<list:paging></list:paging>
				 </div>
			</ui:content>
		</ui:tabpanel>	
		 
	 	<script type="text/javascript">	
	 		var detailValidation = $KMSSValidation(document.forms['calDayForm']);
	 		
	 		seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/data/source'], function($, dialog , topic, Source){

				LUI.ready(function(){
					window.top.initAttendReport("1");
				});
				window.listDetail = function(pageNo){
					if(!validateCalForm())
						return;
					var doSearch =  function(){						
						var listview = LUI('attendStatListview');
						listview.source.on("error", function() {
							// 监听到异常事件
							if (window.list_load != null) {
								window.list_load.hide();
							}
						});
						window.list_load =dialog.loading();
						var __url = '/sys/attend/sys_attend_stat_detail/sysAttendStatDetail.do?method=listDetail';
						if(pageNo) {
							__url = __url + '&pageno=' + pageNo;
						}
						__url+="&time="+new Date().getTime();
						var source = new Source.AjaxJson({
								url : __url,
								params:$(document.forms['calDayForm']).serialize(),
								commitType:'POST'
							});
						source.on("error", function() {
							// 监听到异常事件
							if (window.list_load != null) {
								window.list_load.hide();
							}
						});
						listview.table.redrawBySource(source);
						$('.lui-attend-stat-table').show();
					};
					$.ajax({
						url : '${LUI_ContextPath}/sys/attend/sys_attend_report/sysAttendReport.do?method=checkDuplicatedRequest&reportType=listDetail',
						type: 'get',
						dataType: 'json',
						success : function(data){
							if(data.status == 'processing'){
								dialog.alert(data.message);
								return;
							}
							doSearch();
						},
						error : function(e) {
							console.error(e);
						}
					});
				};
				window.exportDetail = function(){
					if(!validateCalForm())
						return;
					let url='${KMSS_Parameter_ContextPath}sys/attend/sys_attend_stat_detail/sysAttendStatDetail.do?method=exportDetail';
					window.top.exportCommon(url,$(document.forms['calDayForm']).serializeArray(),"1");
					// Com_SubmitNoEnabled(document.forms['calDayForm'], 'exportDetail');
				};
				
				detailValidation.addValidator('beforeEndTime',"${ lfn:message('sys-attend:sysAttendStatDetail.time.validation') }",function(v,e,o){
					var fdStartTime = $('[name="fdStartTime"]').val(),
						fdEndTime = $('[name="fdEndTime"]').val();
					if(fdStartTime && fdEndTime) {
						return fdStartTime <= fdEndTime;
					} else {
						return true;
					}
				});
				
				function validateCalForm() {
					if(window.detailValidation && window.detailValidation.validate()) {
						return true;
					} else {
						return false;
					}
				};
				window.switchAttendPage = function(url,hash){
					url = Com_SetUrlParameter(url,'j_iframe','true');
					url = Com_SetUrlParameter(url,'j_aside','false');
					if(hash){
						url = url + hash;
					}
					LUI.pageOpen(url,'_rIframe');
				}
				
				window.updateStatus = function(id, status){
					dialog.confirm("${ lfn:message('sys-attend:sysAttend.tree.config.normal') }",function(value){
						if(value==true){
							window.update_load = dialog.loading();
							$.post('${LUI_ContextPath}/sys/attend/sys_attend_stat_detail/sysAttendStatDetail.do?method=updateStatus',
									{fdId : id, fdStatus : status}, 
									function(data){
										if(window.update_load != null)
											window.update_load.hide();
										if(data.status){
											dialog.success("${ lfn:message('return.optSuccess')}");
											var pageNo = $('input[data-lui-mark="paging.pageno"]').val();
											window.listDetail(pageNo);
										}else{
											dialog.failure("${ lfn:message('return.optFailure')}");
										}
									},'json');
						}
					});
				};
				
				window.selectCategory = function(){
					var categoryIds = $('[name="fdCategoryIds"]').val();
					var url="/sys/attend/sys_attend_category/sysAttendCategory_showDialog.jsp?categoryIds=" + categoryIds;
					dialog.iframe(url, "${ lfn:message('sys-attend:sysAttendCategory.select.attend')}", function(arg){
						if(arg){
							$('[name="fdCategoryIds"]').val(arg.cateIds);
							$('[name="fdCategoryNames"]').val(arg.cateNames);
						}
					},{width:800,height:540});
				};
				
				window.changeTargetType = function(value) {
					if(value){
						if(value == '1') {
							$('#targetCate').hide();
							$('#targetCate input').each(function(){
								detailValidation.removeElements($(this)[0]);
							});
							$('#targetDept').show();
							detailValidation.addElements($('#targetDept input[name="fdTargetName"]')[0], 'required');
							detailValidation.validate();
						} else if(value == '2'){
							$('#targetDept').hide();
							$('#targetDept input').each(function(){
								detailValidation.removeElements($(this)[0]);
							});
							$('#targetCate').show();
							detailValidation.addElements($('#targetCate input[name="fdCategoryNames"]')[0], 'required');
							detailValidation.validate();
						}
					}
				};
				
				window.openMainList = function(userId, date){
					window.open('${LUI_ContextPath }/sys/attend/sys_attend_main/sysAttendMain_index.jsp'
							+'#cri.q=docCreator:'+ userId +''
							+';docCreateTime:' + date + ';docCreateTime:' + date, '_blank');
				}
				
				window.changeColumn = function(){
					var count=0;
					$('input[name="_fdStatus"]').each(function(){
						if(this.checked){
							count++;
						}
					});
					if($('input[name="_fdStatus"]').length!=0&&$('input[name="_fdStatus"]').length==count){
						$('input[name="_fdAllStatus"]').prop("checked",true);
					}else{
						$('input[name="_fdAllStatus"]').prop("checked",false);
					}
				};
				
				window.selectAll=function(){
					var fdStatus="";
					var allcols=$('input[name="_fdAllStatus"]').is(':checked');
					$('input[name="_fdStatus"]').each(function(){
						if(allcols){
							this.checked=true;
							if(fdStatus){
								fdStatus+=";"+this.value;
							}else{
								fdStatus+=this.value;
							}
						}
						else{
							this.checked=false;
						}
					});
					$('input[name="fdStatus"]').val(fdStatus);
				}
			});
		</script>
	</template:replace>
</template:include>
