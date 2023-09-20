<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('hr-staff:module.hr.staff') }"></c:out>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<c:choose>
				<%-- 生日预警 --%>
				<c:when test="${'warningBirthday' eq param.type}">
					<ui:menu-item text="${ lfn:message('hr-staff:hr.staff.nav.alert.warning') }" href="/hr/staff/" target="_self"></ui:menu-item>
					<ui:menu-item text="${ lfn:message('hr-staff:hr.staff.nav.last.birthday')}"></ui:menu-item>
				</c:when>
				<%-- 合同预警 --%>
				<c:when test="${'warningContract' eq param.type}">
					<ui:menu-item text="${ lfn:message('hr-staff:hr.staff.nav.alert.warning') }" href="/hr/staff/" target="_self"></ui:menu-item>
					<ui:menu-item text="${ lfn:message('hr-staff:hr.staff.nav.contract.expiration')}"></ui:menu-item>
				</c:when>
				<%-- 试用期预警 --%>
				<c:when test="${'warningTrial' eq param.type}">
					<ui:menu-item text="${ lfn:message('hr-staff:hr.staff.nav.alert.warning') }" href="/hr/staff/" target="_self"></ui:menu-item>
					<ui:menu-item text="${ lfn:message('hr-staff:hr.staff.nav.trial.expiration')}"></ui:menu-item>
				</c:when>
				<%-- 员工信息 --%>
				<c:otherwise>
					<ui:menu-item text="${ lfn:message('hr-staff:module.hr.staff') }" href="/hr/staff/" target="_self"></ui:menu-item>
					<ui:menu-item text="${ lfn:message('hr-staff:table.HrStaffPersonInfo')}"></ui:menu-item>
				</c:otherwise>
			</c:choose>
		</ui:menu>
	</template:replace>
	<template:replace name="nav">
		<%
			boolean hasCreate = true;
			String s_method = request.getParameter("method");
			String mainText = ResourceUtil.getString("hr-staff:table.HrStaffPersonInfo");
			if("lastBirthdayShow".equalsIgnoreCase(s_method)){
				mainText = ResourceUtil.getString("hr-staff:hrStaff.overview.lastBirthday");
				hasCreate = false;
			}
			if("contractExpirationShow".equalsIgnoreCase(s_method)){
				mainText = ResourceUtil.getString("hr-staff:hrStaff.overview.contractExpiration");
				hasCreate = false;
			}
			if("trialExpirationShow".equalsIgnoreCase(s_method)){
				mainText = ResourceUtil.getString("hr-staff:hrStaff.overview.trialExpiration");
				hasCreate = false;
			}
			pageContext.setAttribute("hasCreate", hasCreate);
		%>
		<!-- lui_list_noCreate_frame：没有新建功能样式 -->
		<div class="${hasCreate ? '' : 'lui_list_noCreate_frame'}">
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('hr-staff:module.hr.staff') }" />
			<ui:varParam name="button">
				[
					{
						"text": "<%=mainText%>",
						"href": "javascript:${hasCreate ? 'add();' : ';'}",
						"icon": "lui_icon_l_icon_26"
					}
				]
			</ui:varParam>			
		</ui:combin>
		</div>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<c:import url="/hr/staff/import/nav.jsp" charEncoding="UTF-8">
					<c:param name="type" value="${empty param.type ? 'personInfo_quit' : param.type}" />
				</c:import>
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="body">
	<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript" src="${LUI_ContextPath}/hr/staff/resource/js/hrImageUtil.js?s_cache=${LUI_Cache}"></script>
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/hr/staff/resource/css/hr_box.css?s_cache=${LUI_Cache}">	
		<!-- 筛选器 -->
		<list:criteria id="criteria">
			<list:cri-ref key="_fdKey" ref="criterion.sys.docSubject" title="${ lfn:message('hr-staff:hrStaffPersonInfo.criteria.fdKey') }" style="width:300px;">
			</list:cri-ref>
				<list:cri-criterion title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus') }" key="_fdStatus">
					<list:box-select>
						<list:item-select>
								<ui:source type="Static">
								[{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.dismissal') }',value:'dismissal'},
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.leave') }',value:'leave'},
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.retire') }',value:'retire'}]
							</ui:source>
						</list:item-select>
					</list:box-select> 
				</list:cri-criterion>
			<list:cri-ref key="_fdDept" ref="criterion.sys.dept" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }"></list:cri-ref>
			
			<c:choose>
				<%-- 生日预警 --%>
				<c:when test="${'warningBirthday' eq param.type}">
					<list:cri-ref key="fdBirthdayOfYear" ref="criterion.sys.Calendar_MothDay" title="${ lfn:message('hr-staff:hrStaffLastBirthday.birthday') }">
					</list:cri-ref>
				</c:when>
				<%-- 合同预警 --%>
				<c:when test="${'warningContract' eq param.type}">
					<list:cri-ref key="fdEndDate" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hr.staff.contract.expires.date') }"></list:cri-ref>
				</c:when>
				<%-- 试用期预警 --%>
				<c:when test="${'warningTrial' eq param.type}">
					<list:cri-ref key="fdTrialExpirationTime" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdTrialExpirationTime') }"></list:cri-ref>
				</c:when>
				<%-- 员工信息 --%>
				<c:otherwise>
					<list:cri-ref key="fdTimeOfEnterprise" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdTimeOfEnterprise') }"></list:cri-ref>
				</c:otherwise>
			</c:choose>
			
			<list:cri-criterion title="${ lfn:message('hr-staff:hrStaffPersonInfo.criteria.fdLabel') }" key="_fdLabel">
				<list:box-select>
					<list:item-select>
						<ui:source type="AjaxJson">
							{url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getTags'}
						</ui:source>
					</list:item-select>
				</list:box-select> 
			</list:cri-criterion>
		</list:criteria>
		
		
		<c:choose>
			<%-- 生日预警 --%>
			<c:when test="${'warningBirthday' eq param.type}">
				<c:import url="/hr/staff/hr_staff_alert_warning/last_birthday/index.jsp" charEncoding="UTF-8"></c:import>
			</c:when>
			<%-- 合同预警 --%>
			<c:when test="${'warningContract' eq param.type}">
				<c:import url="/hr/staff/hr_staff_alert_warning/contract_expiration/index.jsp" charEncoding="UTF-8"></c:import>
			</c:when>
			<%-- 试用期预警 --%>
			<c:when test="${'warningTrial' eq param.type}">
				<c:import url="/hr/staff/hr_staff_alert_warning/trial_expiration/index.jsp" charEncoding="UTF-8"></c:import>
			</c:when>
			<%-- 员工信息 --%>
			<c:otherwise>
				<!-- 排序 -->
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
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
							<list:sortgroup>
							    <list:sort property="fdTimeOfEnterprise" text="${lfn:message('hr-staff:hrStaffPersonInfo.fdTimeOfEnterprise') }" group="sort.list" value="down"></list:sort>
							</list:sortgroup>
							</ui:toolbar>
						</div>
					</div>
					<!-- 分页 -->
					<div class="lui_list_operation_page_top">
						<list:paging layout="sys.ui.paging.top" >
						</list:paging>
					</div>
					<div style="float:right"> 
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar count="4">
								<%-- 视图切换 --%>
								<ui:togglegroup order="0">
										 <ui:toggle icon="lui_icon_s_zaiyao" title="${ lfn:message('list.rowTable') }" 
											  group="tg_1" text="${ lfn:message('list.rowTable') }" value="gridtable" 
											onclick="LUI('listview').switchType(this.value);">
										</ui:toggle>
										<ui:toggle icon="lui_icon_s_liebiao" title="${ lfn:message('list.columnTable') }" 
											value="columntable"	group="tg_1" text="${ lfn:message('list.columnTable') }" 
											onclick="LUI('listview').switchType(this.value);">
										</ui:toggle>
								 </ui:togglegroup>
								<kmss:authShow roles="ROLE_HRSTAFF_EDIT">
								<ui:button text="${lfn:message('hr-staff:hr.staff.btn.sync') }" onclick="sync();" order="4"></ui:button>
								<ui:button text="${lfn:message('hr-staff:hrStaff.import.button.modify') }" onclick="_modify();" order="1"></ui:button>
								</kmss:authShow>
								<kmss:authShow roles="ROLE_HRSTAFF_CREATE">
								<ui:button text="${lfn:message('button.add') }" onclick="add();" order="2"></ui:button>
								</kmss:authShow>
								<kmss:authShow roles="ROLE_HRSTAFF_DELETE">
								<ui:button text="${lfn:message('button.deleteall') }" onclick="del();" order="5"></ui:button>
								</kmss:authShow>
								<kmss:authShow roles="ROLE_HRSTAFF_EXPORT">
								<ui:button text="${lfn:message('hr-staff:hr.staff.btn.export') }" onclick="exportPerson();" order="3"></ui:button>
								</kmss:authShow>
							</ui:toolbar>
						</div>
					</div>
				</div>
				<ui:fixed elem=".lui_list_operation"></ui:fixed>
				
				<!-- 列表 -->
				<list:listview id="listview">
					<ui:source type="AjaxJson">
						{url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=list&personStatus=quit'}
					</ui:source>
					<!-- 列表视图 -->	
					<list:colTable  layout="sys.ui.listview.columntable" 
						rowHref="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=!{fdId}" name="columntable" >
						<kmss:authShow roles="ROLE_HRSTAFF_DELETE">
						</kmss:authShow>
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial> 
						<list:col-auto props="fdName;fdLoginName;fdStaffNo;fdTimeOfEnterprise;fdDeptName;fdStatus"></list:col-auto>
						<kmss:authShow roles="ROLE_HRSTAFF_DELETE|ROLE_HRSTAFF_EDIT">
						<list:col-auto props="operations"></list:col-auto>
						</kmss:authShow>
					</list:colTable>
					<!-- 摘要 -->	
					<list:gridTable name="gridtable" columnNum="2">
						<list:row-template >
							<c:import url="/hr/staff/hr_staff_person_info/hrStaffPersonInfo_box_content_tmpl.jsp" charEncoding="UTF-8"></c:import>
						</list:row-template>
					</list:gridTable>
				</list:listview> 
				<list:paging></list:paging>
				
				<!-- 模板下载表单 -->
				<form id="downloadTempletForm" action="<c:url value="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=downloadTemplet" />" method="post">
				<script type="text/javascript">
					// 初始化表格多选框（同步的记录禁用多选框）
					seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
						// 组织机构同步
						window.sync = function() {
							window.del_load = dialog.loading();
							$.ajax({
								url : '<c:url value="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do" />?method=sync',
								type : 'GET',
								dataType : 'json',
								error : function(data) {
									if(window.del_load != null) {
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: function(data) {
									// 这里只显示操作成功，不显示具体的操作内容项
									data.message = undefined;
									if(window.del_load != null){
										window.del_load.hide(); 
										topic.publish("list.refresh");
									}
									dialog.result(data);
								}
						   });
						};
						
						// 模板下载
						window.download = function() {
							$("#downloadTempletForm").submit();
						};
		
						// 批量修改
						window._modify = function() {
							dialog.iframe('/hr/staff/upload_files/common_upload_download.jsp?uploadActionUrl=${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=fileUpload&downLoadUrl=${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=downloadTemplet', 
									'<bean:message key="hr-staff:hrStaff.import.button.modify"/>', function(data) {
									topic.publish('list.refresh');
							}, {
								width : 680,
								height : 380
							});
						};
						
						// 新建
						window.add = function() {
							Com_OpenWindow('<c:url value="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do" />?method=add');
						};
		
						// 修改
						window.edit = function(id) {
							if(id)
				 				Com_OpenWindow('<c:url value="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do" />?method=edit&fdId=' + id);
						};
						/* 导出excel */
						window.exportPerson = function(){
							dialog.iframe('/hr/staff/hr_staff_person_info/hrStaffPersonInfo_exportPerson02.jsp?personStatus=quit','<bean:message bundle="hr-staff" key="hr.staff.btn.export" />',null,{width:600,height:250});
						};
						window.serializeParams =function (params) {
							var array = [];
							for (var i = 0; i < params.length; i++) {
								var p = params[i];
								if (p.nodeType) {
									array.push('nodeType=' + p.nodeType);
								}
								for (var j = 0; j < p.value.length; j++) {
									array.push("q." + encodeURIComponent(p.key) + '='
											+ encodeURIComponent(p.value[j]));
								}
								if (p.op) {
									array.push(encodeURIComponent(p.key) + '-op='
											+ encodeURIComponent(p.op));
								}
								for (var k in p) {
									if (k == 'key' || k == 'op' || k == 'value' || k == 'nodeType') {
										continue;
									}
									array.push(encodeURIComponent(p.key + '-' + k) + "="
											+ encodeURIComponent(p[k] || ""));
								}
							}
							var str = array.join('&');
							return str;
						}
		
						// 删除
						window.del = function(id) {
							var values = [];
				 			if(id) {
				 				values.push(id);
					 		} else {
								$("input[name='List_Selected']:checked").each(function() {
									values.push($(this).val());
								});
					 		}
							if(values.length==0){
								dialog.alert('<bean:message key="page.noSelect"/>');
								return;
							}
							var url  = '<c:url value="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=deleteall"/>';
							dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
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
			</c:otherwise>
		</c:choose>

		<script type="text/javascript">
			//编辑员工信息
			function editStaffInfo(fdId) {
				window.open('<c:url value="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do" />?method=edit&fdId=' + fdId);
			}
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					setTimeout(function() {
						seajs.use(['lui/topic'], function(topic) {
							topic.publish('list.refresh');
						});
					}, 100);
				});
			});
		</script>
	</template:replace>
</template:include>
