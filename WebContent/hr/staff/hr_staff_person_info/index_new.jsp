<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%> 
		<list:criteria id="criteria">
			<list:cri-ref key="_fdKey" ref="criterion.sys.docSubject" title="${ lfn:message('hr-staff:hrStaffPersonInfo.criteria.fdKey') }" style="width:300px">
			</list:cri-ref>
				<list:cri-criterion title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus') }" key="_fdStatus">
					<list:box-select>
						<list:item-select cfg-enable="true">
								<ui:source type="Static">
								[{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.trial') }', value:'trial'},
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.official') }',value:'official'},
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.temporary') }',value:'temporary'},
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.trialDelay') }',value:'trialDelay'}]
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
					<div style='color: #979797;float: left;padding-top:1px;'>
						${ lfn:message('list.orderType') }：
					</div>
					<div style="float:left">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
							<list:sortgroup>
							    <list:sort property="fdTimeOfEnterprise" text="${lfn:message('hr-staff:hrStaffPersonInfo.fdTimeOfEnterprise') }" group="sort.list" value="down"></list:sort>
							</list:sortgroup>
							</ui:toolbar>
						</div>
					</div>
					<div style="float:left;">
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
								<ui:button text="${lfn:message('hr-staff:hrStaff.import.button.modify') }" onclick="_modify();" order="3"></ui:button>
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
						{url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=list'}
					</ui:source>
					<!-- 列表视图 -->	
					<list:colTable  layout="sys.ui.listview.columntable" 
						rowHref="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=!{fdId}" name="columntable" >
						<kmss:authShow roles="ROLE_HRSTAFF_DELETE">
						<list:col-checkbox></list:col-checkbox>
						</kmss:authShow>
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
					<kmss:authShow roles="ROLE_HRSTAFF_DELETE">
					<ui:event topic="list.loaded">
						initTabelCheckbox();
					</ui:event>
					</kmss:authShow>
				</list:listview> 
				<list:paging></list:paging>
				<!-- 模板下载表单 -->
<%-- 				<form id="downloadTempletForm" action="<c:url value="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=downloadTemplet" />" method="post"> --%>
				<script type="text/javascript">
					// 初始化表格多选框（同步的记录禁用多选框）
				 	function initTabelCheckbox() {
				 		$("input[name='List_Tongle']").attr("disabled", true);
				 		$("input[name='List_Selected']").each(function() {
				 			if( $(this).parent().html().indexOf("com_author")>-1){//摘要视图过滤
				 				if("false" == $(this).next().val() || "" ==  $(this).next().val()){//手动新建或无效的人员
				 					$(this).attr("disabled", false);
				 				}else{
				 					$(this).attr("disabled", true);
				 				}
				 			}else{//列表视图过滤
				 				if($(this).parent().parent().children().last().html().indexOf("del") == -1){
									$(this).attr("disabled", true);
								}
				 			}
						});
					 }
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
						/* 导出excel */
						window.exportPerson = function(){
							var url = '${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=exportPerson';
							var criterions = LUI('criteria')._buildCriteriaSelectedValues();
							var urlParam = serializeParams(criterions);
							if (urlParam) {
									if (url.indexOf('?') > 0) {
										url += "&" + urlParam;
									} else {
										url += "?" + urlParam;
									}
								}
							Com_OpenWindow(url);
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