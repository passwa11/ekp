<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld"
	prefix="person"%>
<%@ page import="com.landray.kmss.hr.organization.model.HrOrganizationPersoninfoSetting"%>
<%
HrOrganizationPersoninfoSetting hrorganizationpersoninfoSetting =new HrOrganizationPersoninfoSetting();
String isuniquegroupname=hrorganizationpersoninfoSetting.getIsUniqueGroupName();
%>
<template:include ref="default.dialog">
	<template:replace name="head">
	<script type="text/javascript">
		seajs.use(['theme!form']);
		Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
		Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js|data.js", null, "js");
	</script>	
	<style>
		.org-info-edit-table{
			width:100%;
		}
		.org-info-edit-table>tbody>tr{
			height:50px;
			
		}
		.org-info-edit-table>tbody>tr>td:nth-child(odd){
			width:15%;
		}
		.org-info-edit-table>tbody>tr>td:nth-child(even){
			width:30%;
		}
		.org-info-edit-box{
			height:100%;
			width:90%;
			margin:10px auto;
		}
		#blongOrg{
			color: #47b5e6;
    		cursor: pointer;
		}
		.belongOrg_text{
			width: 200px;
    		display: inline-block;
    		border-bottom: 1px solid #d5d5d5;
		}		
	</style>
	<link rel="stylesheet" href="${LUI_ContextPath}/hr/organization/resource/css/reset.css" />
	</template:replace>
	<template:replace name="content">
	<%
		String formUrl="";
		String orgType=request.getParameter("orgType");
		String form = "";
		if(orgType!=null){
			if(orgType.equals("1")){
				/* 新增组织 */
				formUrl ="hr/organization/hr_organization_org/hrOrganizationOrg.do";
				form = "hrOrganizationOrgForm";
			}else{
				/* 新增部门 */
				formUrl ="hr/organization/hr_organization_dept/hrOrganizationDept.do";
				form = "hrOrganizationDeptForm";
			}
		}else{
			/* 编辑组织单元 */
			formUrl="hr/organization/hr_organization_element/hrOrganizationElement.do";
			form = "hrOrganizationElementForm";
		}
	%>
	<%-- <html:form action="hr/organization/hr_organization_element/hrOrganizationElement.do"> --%>
	<html:form action="<%=formUrl %>">
		<html:hidden property="fdId"/>
		<div class="org-info-edit-box">
			<table class="org-info-edit-table">
				<tr>
					<td>${lfn:message('hr-organization:hrOrganizationElement.fdNo')}</td>
					<td class="newInput" colspan="3">
						<xform:text style='width:96%;' showStatus="edit" property="fdNo" htmlElementProperties="placeholder='${ lfn:message('hr-organization:hrOrganizationElement.fdNo.tips') }'"></xform:text>
					</td>
				</tr>
				<tr>
					<td>${lfn:message('hr-organization:hrOrganizationElement.fdName')}</td>
					<td colspan="3">
						<xform:text style='width:96%;' showStatus="edit" property="fdName" htmlElementProperties="placeholder='${ lfn:message('hr-organization:hrOrganizationElement.fdName.tips') }'"></xform:text>
					</td>
				</tr>
				<tr>
					<td>${lfn:message('hr-organization:hrOrganizationElement.fdOrgType')}</td>
					<td>
						<div class="newInputDisable">
					        <xform:select property="fdOrgType" showStatus="view" value="${not empty param.orgType?param.orgType:hrOrganizationElementForm.fdOrgType }">
								<xform:enumsDataSource enumsType="hr_organization_type" />
							</xform:select>
							<c:choose>
								<c:when test="${not empty param.orgType }">
									<input name="fdOrgType" value="${param.orgType }" type="hidden"/>
								</c:when>
								<c:otherwise>
									<input name="fdOrgType" value="${hrOrganizationElementForm.fdOrgType }" type="hidden"/>
									<input name="fdCreateTime" value="${hrOrganizationElementForm.fdCreateTime }" type="hidden"/>
								</c:otherwise>
							</c:choose>
						</div>
					</td>
					<td>
					${lfn:message('hr-organization:hrOrganizationElement.fdParent.title')} 
					</td>
					<td class="newAddress">
						<xform:dialog propertyId="fdParentId" propertyName="fdParentName" showStatus="edit" style="width:95%;">
							Dialog_Tree(false, 'fdParentId', 'fdParentName', ',', 'hrOrganizationTree&parent=!{value}&filterId=${hrOrganizationElementForm.fdId }&orgType=${not empty param.orgType?param.orgType:hrOrganizationElementForm.fdOrgType }', '${lfn:message('hr-organization:hrOrganizationElement.fdParent.title')}', null, null, null, null, false, null);
						</xform:dialog>
					</td>
				</tr>
				<tr>
					<td>${lfn:message('hr-organization:hrOrganizationElement.hbmThisLeader')}</td>
					<td class="newAddress">
						<xform:address isHrAddress="true" orgType="ORG_TYPE_PERSON|ORG_TYPE_POST"
						propertyName="fdThisLeaderName" propertyId="fdThisLeaderId" showStatus="edit"
						></xform:address>
					</td>
					<td>
					${lfn:message('hr-organization:hrOrganizationElement.fdBranLeader')} 
					</td>
					<td class="newAddress">
						<xform:address isHrAddress="true" propertyName="fdBranLeaderName" 
						orgType="ORG_TYPE_PERSON|ORG_TYPE_POST" propertyId="fdBranLeaderId" showStatus="edit"
						></xform:address>					
					</td>
				</tr>
				<tr>
					<td>${lfn:message('hr-organization:hrOrganizationElement.fdNameAbbr')} </td>
					<td class="newInput">
						<xform:text showStatus="edit" property="fdNameAbbr" htmlElementProperties="placeholder='${ lfn:message('hr-organization:hrOrganizationElement.fdNameAbbr.tips') }'"></xform:text>
					</td>
					<td>
						${lfn:message('hr-organization:hr.organization.info.startData')}
					</td>
					<td class="newInput">
						<xform:datetime property="fdCreateTime" dateTimeType="date" showStatus="view" value="${hrOrganizationElementForm.fdCreateTime }"/>
					</td>
				</tr>
				<tr>
					<td>
						${lfn:message('hr-organization:hrOrganizationElement.fdOrder')}
					</td>
					<td class="newInput">
						<xform:text validators="number" property="fdOrder" showStatus="edit" htmlElementProperties="placeholder='${ lfn:message('hr-organization:hrOrganizationElement.fdOrder.tips') }'"></xform:text>
					</td>
					<td>
						${lfn:message('hr-organization:hrOrganizationElement.fdIsVirOrg')}
					</td>
					<td>
						<ui:switch property="fdIsVirOrg"></ui:switch>
					</td>
				</tr>
				<tr>
					<td>
						${lfn:message('hr-organization:hrOrganizationElement.fdIsBusiness')}
					</td>
					<td  colspan="3">
						<ui:switch property="fdIsBusiness"></ui:switch>
					</td>
				</tr>
				<tr>
					<td>${lfn:message('hr-organization:hrOrganizationElement.fdMemo')}</td>
					<td class="newInput" colspan="3">
						<xform:text style='width:100%;' property="fdMemo" showStatus="edit" htmlElementProperties="placeholder='${ lfn:message('hr-organization:hrOrganizationElement.fdMemo.tips') }'"></xform:text>
					</td>
				</tr>
			</table>
		</div>
	</html:form>
	<script>
	var _validation = $KMSSValidation();
	/*校验组织名称唯一*/
	var FdNameValidators = {
			'uniqueFdName' : {
				error : "组织名称必须唯一！",
				test : function (value) {
					var result = null;
					var fdId = '${hrOrganizationElementForm.fdId}';
					var fdOrgType = '${not empty param.orgType?param.orgType:hrOrganizationElementForm.fdOrgType }';
					$.ajax({
				        type: "post",
				        url: "${LUI_ContextPath}/hr/organization/hr_organization_element/hrOrganizationElement.do?method=checkFdNameUnique",
				        data: {"fdName":value, "fdId":fdId, "fdOrgType":fdOrgType},
				        async : false,
				        dataType: "json",
				        success: function (data ,textStatus, jqXHR){
				        result = data.result;
				        }
			    	 });
					return result;
				}
			}
	};
	_validation.addValidators(FdNameValidators);
	
	var FdNoValidators = {
			'uniqueFdNo' : {
				error : "${lfn:message('hr-organization:hr.organization.info.tip.2')}",
				test : function (value) {
					var result = null;
					var fdId = '${hrOrganizationElementForm.fdId}';
					var fdOrgType = '${not empty param.orgType?param.orgType:hrOrganizationElementForm.fdOrgType }';
					$.ajax({
				        type: "post",
				        url: "${LUI_ContextPath}/hr/organization/hr_organization_element/hrOrganizationElement.do?method=checkFdNoUnique",
				        data: {"fdNo":value, "fdId":fdId, "fdOrgType":fdOrgType},
				        async : false,
				        dataType: "json",
				        success: function (data ,textStatus, jqXHR){
				        	result = data.result;
				        }
			    	 });
					return result;
			    }
			}
		};
	
	_validation.addValidators(FdNoValidators);
	
	seajs.use(["lui/dialog","lui/topic","hr/organization/resource/js/tree/dialogTree",'lang!hr-organization'],function(dialog,topic,widgetTree,lang){
		var formobj = "<%=form%>";
		var _validator = $KMSSValidation(document.forms[formobj]);
		$("#blongOrg").on("click",function(){
			var url = "/hr/organization/hr_organization_tree/orgInfo/belongOrg.jsp?fdId=${param.fdId}&orgType=${not empty param.orgType?param.orgType:hrOrganizationElementForm.fdOrgType }"
			var dialogObj = dialog.iframe(url,
					 lang['hr.organization.info.fdParent']
					,function(data){
				if(data){
					$("#belongOrg span").eq(0).text(data['name']);
					$("#belongOrg input").eq(0).val(data['id']);
					$("#belongOrg input").eq(1).val(data['name']);
				}
			},{
				width:500,
				height:600,
				buttons:[
				        	{
				        		fn:function(){
				        			dialogObj.hide();
				        		},
				        		name:lang['hr.organization.info.button.cancel'] 
				        	}
				        ]
			})
		})
		window.orgEditSubmit = function (type){
			_validation.removeElements($("input[name='fdNo']")[0]);
			var fdNo = $("input[name=fdNo]").val();
			if(null != fdNo && fdNo != ""){
				_validation.addElements($("input[name='fdNo']")[0],"uniqueFdNo");
			}
			var isuniquegroupname = <%=isuniquegroupname%>;
			<%  if("true".equals(isuniquegroupname)){%>
			var fdName = $("input[name=fdName]").val();
			if(null != fdName && fdName != ""){
				_validation.addElements($("input[name='fdName']")[0],"uniqueFdName");
			}
			<% } %>
			
			var fdIsVirOrg = $("input[name='fdIsVirOrg']").val();
			if(_validator.validate()){
				if(fdIsVirOrg == 'true'){
					dialog.confirm(lang['hr.organization.info.tip.3'] , function(ok) {
						console.log("ok="+ok)
		  				if(ok == true) {
		  					orgSubmit(type);
		  				}
		  			});
				}else{
					orgSubmit(type);
				}
			}
		}
		
		window.orgSubmit = function(type){
			// 更新前需要检查与业务相关的数据
			var _fdIsBusiness = document.getElementsByName("fdIsBusiness");
			var fdId = document.getElementsByName("fdId")[0].value;
			var result = "true" == _fdIsBusiness[0].value;

			$("#fdIsBusiness_validate").remove();
			if(!result) {
				var data = new KMSSData();
				data.UseCache = false;
				data.AddBeanData("hrOrganizationElementService&fdId=" + fdId);
				var rtn = data.GetHashMapArray()[0];
				if(rtn) {
					result = false;
					var validate = '<div class="validation-advice" id="fdIsBusiness_validate" _reminder="true"><table class="validation-table"><tbody><tr><td><div class="lui_icon_s lui_icon_s_icon_validator"></div></td><td class="validation-advice-msg">'+rtn.msg+'</td></tr></tbody></table></div>';
					$(_fdIsBusiness).parents("td").append(validate);
				} else {
					result = true;
				}
			}
			var formUrl = Com_Parameter.ContextPath+"<%=formUrl %>"+"?method="+type;
			if(_validator.validate() && result){
				 $.ajax(formUrl,{
					data:$("form[name="+formobj+"]").serialize(),
					type:"post",
					success:function(data){
						if(data){
							dialog.success(type=="update"?lang['hr.organization.info.tip.4']:lang['hr.organization.info.tip.5']);
							window.$dialog.hide("success");
						}
					}
				}) 
			}
		}
		
		
		
	})

	</script>	
	</template:replace>
</template:include>
