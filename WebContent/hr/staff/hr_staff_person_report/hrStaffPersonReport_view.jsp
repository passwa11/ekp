<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.view" sidebar="auto">
	<template:replace name="title">
		${ hrStaffPersonReportForm.fdName } - ${ lfn:message('hr-staff:table.hrStaffPersonReport') }
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			<kmss:authShow roles="ROLE_HRSTAFF_REPORT">
			<ui:button text="${lfn:message('button.edit')}" 
				onclick="Com_OpenWindow('hrStaffPersonReport.do?method=edit&fdId=${param.fdId}&fdReportType=${hrStaffPersonReportForm.fdReportType}','_self');" order="1">
			</ui:button>
			<ui:button text="${lfn:message('button.delete')}" order="3" onclick="deleteInfo('hrStaffPersonReport.do?method=delete&fdId=${param.fdId}');">
			</ui:button>
			</kmss:authShow>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:module.hr.staff') }" href="/hr/staff/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffPersonReport') }" href="/hr/staff/hr_staff_person_report/" target="_self"></ui:menu-item>
		</ui:menu>
	</template:replace>	
	<template:replace name="head">
		<script language="JavaScript">
			seajs.use("${LUI_ContextPath}/hr/staff/resource/css/stat.css");
			seajs.use(["${LUI_ContextPath}/hr/staff/resource/js/stat.js"],function(stat){
				window.stat = stat;
			});
			
			seajs.use(['lui/dialog'],function(dialog){
				window.deleteInfo = function(delUrl){
					dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(isOk) {
						if(isOk){
							Com_OpenWindow(delUrl,'_self');
						}	
					});
					return;
				};
			});
			
			
			LUI.ready(function() {
				<c:if test="${!empty hrStaffPersonReportForm.fdPeriod}">
				$("select[name=fdPeriod]").val("${hrStaffPersonReportForm.fdPeriod}");
				</c:if>
				<c:if test="${'custom' eq hrStaffPersonReportForm.fdPeriod}">
				$("#custom").show();
				</c:if>
				
				$("#div_condtionSection").attr("isShow", "1");
				window.stat.expandDiv($("#div_condtionArea .div_titleArea"), 'div_condtionSection');
				//禁用
				var queryAreaEles = $("#div_condtionSection *");
				queryAreaEles.prop("disabled", true);
				queryAreaEles.removeAttr("onclick");
				//执行查询
				window.stat.statExecutor();
				
				// 如果有“员工状态”，自动勾选
				<c:if test="${!empty hrStaffPersonReportForm.fdStatus}">
				var status = "${hrStaffPersonReportForm.fdStatus}".split(",");
				for(var i in status) {
					$("input:checkbox[value='"+status[i]+"']").prop("checked",true);
				}
				</c:if>
			});
		</script>
	</template:replace>
	<template:replace name="content"> 
		<html:form action="/hr/staff/hr_staff_person_report/hrStaffPersonReport.do" >
			<p class="lui_form_subject">
				<bean:message key="hr-staff:hrStaffPersonReport.type.${hrStaffPersonReportForm.fdReportType}"/>
			</p>
			<div class="lui_form_content_frame" style="padding-top:20px">
				<%--条件筛选--%>	
				<div id="div_condtionArea">
					<div class="div_section">
						<div class="div_line"></div>
						<div class="div_titleArea"  onclick="window.stat.expandDiv(this,'div_condtionSection');">
							${lfn:message('hr-staff:hrStaffPersonReport.page.filter') }
							<span class="div_icon_coll"></span>
						</div>
					</div>	
					<div id="div_condtionSection">
						<html:hidden property="method_GET"/>
						<html:hidden property="fdId"/>
						<html:hidden property="fdReportType"/>	
						<table class="tb_simple" width="100%">
							<%--统计名称 --%>
							<tr>
								<td width="20%" class="td_normal_title">
									<bean:message key="hrStaffPersonReport.fdName" bundle="hr-staff"/>
								</td>
								<td width="80%">
									<xform:text property="fdName" style="width:80%" showStatus="edit"></xform:text>
								</td>
							</tr>
							<%--组织部门 --%>
							<tr>
								<td class="td_normal_title">
									<bean:message key="hrStaffPersonReport.fdDept" bundle="hr-staff"/>
								</td>
								<td>
									<xform:address propertyId="fdQueryIds" propertyName="fdQueryNames" style="width:80%" mulSelect="true" 
										subject="${lfn:message('hr-staff:hrStaffPersonReport.fdDept')}"
								        showStatus="edit" orgType="ORG_TYPE_ORGORDEPT" textarea="false" required="true"></xform:address>
								</td>
							</tr>
							<%--阅读者 --%>
							<tr>
								<td class="td_normal_title"><bean:message bundle="sys-right" key="right.read.authReaders" /></td>
								<td>
									<xform:address textarea="false" mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" style="width:80%;" showStatus="edit">
									</xform:address>
								</td>
							</tr>
							<%--统计期间 --%>
							<tr>
								<td class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonReport.fdPeriod" /></td>
								<td>
									<xform:select property="fdPeriod" showPleaseSelect="false" showStatus="edit">
										<xform:simpleDataSource value="all">${lfn:message('hr-staff:hrStaffPersonReport.fdPeriod.all')}</xform:simpleDataSource>
										<xform:simpleDataSource value="thisMonth">${lfn:message('hr-staff:hrStaffPersonReport.fdPeriod.thisMonth')}</xform:simpleDataSource>
										<xform:simpleDataSource value="lastMonth">${lfn:message('hr-staff:hrStaffPersonReport.fdPeriod.lastMonth')}</xform:simpleDataSource>
										<xform:simpleDataSource value="thisSeason">${lfn:message('hr-staff:hrStaffPersonReport.fdPeriod.thisSeason')}</xform:simpleDataSource>
										<xform:simpleDataSource value="lastSeason">${lfn:message('hr-staff:hrStaffPersonReport.fdPeriod.lastSeason')}</xform:simpleDataSource>
										<xform:simpleDataSource value="thisYear">${lfn:message('hr-staff:hrStaffPersonReport.fdPeriod.thisYear')}</xform:simpleDataSource>
										<xform:simpleDataSource value="lastYear">${lfn:message('hr-staff:hrStaffPersonReport.fdPeriod.lastYear')}</xform:simpleDataSource>
										<xform:simpleDataSource value="custom">${lfn:message('hr-staff:hrStaffPersonReport.fdPeriod.custom')}</xform:simpleDataSource>
									</xform:select>
									&nbsp;&nbsp;
									<span id="custom" style="display: none;">
										<xform:datetime property="fdBeginPeriod" dateTimeType="date" showStatus="edit"></xform:datetime>
										${lfn:message('hr-staff:hrStaffPersonReport.fdPeriod.to')}
										<xform:datetime property="fdEndPeriod" dateTimeType="date" showStatus="edit"></xform:datetime>
									</span>
								</td>
							</tr>
							<%-- 年龄区间 --%>
							<c:if test="${'reportAge' eq hrStaffPersonReportForm.fdReportType}">
							<tr>
								<td class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonReport.age.fdAgeRange" /></td>
								<td>
									<xform:text property="fdAgeRange" showStatus="edit"></xform:text>
									${lfn:message('resource.period.type.year.name')}
								</td>
							</tr>
							</c:if>
							<%-- 司龄区间  --%>
							<c:if test="${'reportWorkTime' eq hrStaffPersonReportForm.fdReportType}">
							<tr>
								<td class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonReport.workTime.fdAgeRange" /></td>
								<td>
									<xform:select property="fdAgeRange" showStatus="edit">
										<xform:simpleDataSource value="3">3${lfn:message('resource.period.type.year.name')}</xform:simpleDataSource>
										<xform:simpleDataSource value="5">5${lfn:message('resource.period.type.year.name')}</xform:simpleDataSource>
										<xform:simpleDataSource value="10">10${lfn:message('resource.period.type.year.name')}</xform:simpleDataSource>
										<xform:simpleDataSource value="15">15${lfn:message('resource.period.type.year.name')}</xform:simpleDataSource>
									</xform:select>
								</td>
							</tr>
							</c:if>
							
							<%-- 员工状态 --%>
							<tr>
								<td class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus" /></td>
								<td>
									<label><input type="checkbox" name="fdStatusAll" />${lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.all')}</label>
									<br>
									<xform:checkbox property="fdStatus" showStatus="edit">
										<xform:enumsDataSource enumsType="hrStaffPersonInfo_fdStatus"></xform:enumsDataSource>
									</xform:checkbox>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div id="div_chartArea">
					<div class="div_section">
						<div class="div_line"></div>
						<div class="div_titleArea" onclick="window.stat.expandDiv(this,'div_reportSection');">
							${lfn:message('hr-staff:hrStaffPersonReport.page.resultsShow')}
							<span class="div_icon_exp"></span>
						</div>
					</div>
					<div id="div_reportSection">
						<div id="div_chartSection">
							<%--echart图表--%>
							<ui:chart height="350px" width="800px;" id="hrStaffPersonReportChart">
			  					<ui:source type="AjaxJson">
									{"url":""}
			  					</ui:source>
							</ui:chart>
						</div>
						<div id="div_listSection">
							<div id="div_list" class="div_list"></div>
						</div>
					</div>
				</div>
			</div>
		</html:form>
	</template:replace>
</template:include>