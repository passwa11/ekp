<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="no">
	<%-- 标签页标题--%>
	<template:replace name="title">
		<c:out value="${ lfn:message('hr-staff:module.hr.staff') }"></c:out>
	</template:replace>
	
	<%--导航路径--%>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:module.hr.staff') }" href="/hr/staff/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffEkp_H14_S')}"></ui:menu-item>
		</ui:menu>
	</template:replace>
	
	<%--操作按钮--%>
	<template:replace name="toolbar">
		<script type="text/javascript">
			Com_IncludeFile("calendar.js",null,"js");
			seajs.use(['lui/jquery','sys/ui/js/dialog'],function($,dialog){
				//删除
				window.deleteDoc=function(delUrl){
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(isOk){
						if(isOk){
							Com_OpenWindow(delUrl,'_self');
						}	
					});
					return;
				};

				window.sendEmailChoose=function(val){
					if(val && val.indexOf('email') > -1){
						$("#sendEmailChoose").show();
					}else{
						$("#sendEmailChoose").hide();
					}
				};
			});
		</script>  
		
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<%--删除--%>
			<ui:button text="${lfn:message('button.delete') }" onclick="deleteDoc('hrStaffEkp_H14_S.do?method=delete&fdId=${param.fdId}');" order="4">
			</ui:button>
			<%--关闭--%>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	
	<%--资源借用单信息--%>
	<template:replace name="content">
		<html:form action="/hr/staff/hr_staff_ekp_H14_S/hrStaffEkp_H14_S.do">		
			<html:hidden property="fdId" />
			<div class="lui_form_content_frame">
				<p class="lui_form_subject">
					<bean:message bundle="hr-staff" key="table.hrStaffEkp_H14_S" />
				</p>
				<table class="tb_normal" width=100%>

				<tr>
					<!-- 姓名 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdName" />
					</td>
					<td width="35%">
						<c:choose>
							<c:when test="${ hrStaffEkp_H14_SForm.method_GET == 'add' }">
								<xform:address propertyId="fdOrgPersonId" idValue="${ hrStaffEkp_H14_SForm.fdPersonInfoId }" 
								propertyName="fdOrgPersonName" nameValue="${ hrStaffEkp_H14_SForm.fdPersonInfoName }" 
								validators="required" orgType="ORG_TYPE_PERSON" style="width:95%" onValueChange="personInfoChange"></xform:address>
								<span class="txtstrong">*</span>
							</c:when>
							<c:otherwise>
								<xform:text property="fdPersonInfoId" showStatus="noShow"></xform:text>
								<xform:text property="fdPersonInfoName" showStatus="view"></xform:text>
							</c:otherwise>
						</c:choose>
						
					</td>
					<!-- 工资账户名 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffEkp_H14_S.fdFirstLevelDepartment" />
					</td>
					<td width="35%">
						<bean:write name="hrStaffEkp_H14_SForm" property="fdFirstLevelDepartment"  />
					</td>
				</tr>
				<tr>
					
					
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffEkp_H14_S.fdSecondLevelDepartment" />
					</td>
					<td width="35%">
						<xform:text property="fdSecondLevelDepartment" style="width:95%;" className="inputsgl" />
					</td>
					
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffEkp_H14_S.fdThirdLevelDepartment" />
					</td>
					<td width="35%">
						<xform:text property="fdThirdLevelDepartment" style="width:95%;" className="inputsgl" />
					</td>
					
				</tr>
				
				
				
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffEkp_H14_S.fdExpiryDate" />
					</td>
					<td width="35%">
						<xform:datetime property="fdExpiryDate" dateTimeType="date" style="width:95%;" className="inputsgl" />
					</td>
					
					
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffEkp_H14_S.fdEvaluationDimension" />
					</td>
					<td width="35%">
						<xform:text property="fdEvaluationDimension" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffEkp_H14_S.fdEvaluationIndex" />
					</td>
					<td width="35%">
						<xform:text property="fdEvaluationIndex" style="width:95%;" className="inputsgl" />
					</td>
					
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffEkp_H14_S.fdTargetValue" />
					</td>
					<td width="35%">
						<xform:text property="fdTargetValue" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffEkp_H14_S.fdWeight" />
					</td>
					<td width="35%">
						<xform:text property="fdWeight" style="width:95%;" className="inputsgl" />
					</td>
					
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffEkp_H14_S.fdBeginDate" />
					</td>
					<td width="35%">
						<xform:datetime property="fdBeginDate" dateTimeType="date" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffEkp_H14_S.fdJobNature" />
					</td>
					<td width="35%">
						<xform:text property="fdJobNature" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
			</table>
			</div>
			</html:form>
	</template:replace>
</template:include>