<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do">
	<style type="text/css">
		.tb_simple tr td{border:0}
		.typeDiv {padding-left:47px}
		.paddingLeft {padding-left:14px}
		.hideRangeTip {color: red;padding: 10px 0;}
	</style>
	<div id="optBarDiv">
		<logic:equal name="sysOrgDeptForm" property="method_GET" value="edit">
			<input type=button value="<bean:message key="button.update"/>" onclick="Com_Submit(document.sysOrgDeptForm, 'update');">
		</logic:equal>
		<logic:equal name="sysOrgDeptForm" property="method_GET" value="add">
			<input type=button value="<bean:message key="button.save"/>" onclick="Com_Submit(document.sysOrgDeptForm, 'save');">
		</logic:equal>
		<logic:equal name="sysOrgDeptForm" property="method_GET" value="add">
			<input type=button value="<bean:message key="button.saveadd"/>" onclick="Com_Submit(document.sysOrgDeptForm, 'saveadd');">
		</logic:equal>
		<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
	</div>
	<p class="txttitle"><bean:message bundle="sys-organization" key="table.sysOrgElementExternal"/><bean:message key="button.edit"/></p>
	<center>
		<table class="tb_normal" width=95%>
			<tr>
				<!-- 组织名称 -->
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgElement.fdName"/>
				</td><td width=35%>
				<xform:text property="fdName" subject="${ lfn:message('sys-organization:sysOrgElement.fdName') }" validators="uniqueName invalidName" style="width:90%"></xform:text>
			</td>
				<!-- 编号 -->
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgElement.fdNo"/>
				</td>
				<td width=35%>
						<%-- 引用通用的编号属性 --%>
					<input type="hidden" name="fdOrgType" value="2">
					<%@ include file="/sys/organization/org_common_fdNo_edit.jsp"%>
				</td>
			</tr>
			<tr>
				<!-- 上级组织 -->
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgElementExternal.parent"/>
				</td>
				<td width=35%>
					<html:hidden property="fdParentId"/>
					<input class="inputsgl" style="width: 90%" name="fdParentName" readonly="readonly" subject="${ lfn:message('sys-organization:sysOrgElementExternal.parent') }" value="${sysOrgDeptForm.fdParentName}" type="text" validate="required">
					<a href="#" onclick="Dialog_Tree(
							false,
							'fdParentId',
							'fdParentName',
							null,
							'organizationTree&fdIsExternal=true&deptLimit=${cateId}&parent=!{value}&orgType='+(ORG_TYPE_DEPT|ORG_FLAG_BUSINESSALL)+'&sys_page=true&eco_type=outer',
							'<bean:message bundle="sys-organization" key="organization.moduleName"/>',
							null,
							null,
							'<bean:write name="sysOrgDeptForm" property="fdId"/>');">
						<bean:message key="dialog.selectOrg"/>
					</a>
					<span class="txtstrong">*</span>
				</td>
				<!-- 排序号 -->
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgElement.fdOrder"/>
				</td><td width=35%>
				<xform:text property="fdOrder" style="width:90%"></xform:text>
			</td>
			</tr>
			<tr>
				<!-- 负责人 -->
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgElementExternal.authElementAdmin"/>
				</td>
				<td width=35%>
					<xform:address required="false" mulSelect="true" propertyId="authElementAdminIds" propertyName="authElementAdminNames" orgType='ORG_TYPE_PERSON|ORG_FLAG_BUSINESSALL' subject="${ lfn:message('sys-organization:sysOrgElementExternal.authElementAdmin') }" />
					<br>
					<span style="color: red;"><bean:message bundle="sys-organization" key="sysOrgElementExternal.authElementAdmin.tips"/></span>
				</td>
				<!-- 是否有效 -->
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgDept.fdIsAvailable"/>
				</td>
				<td width="35%">
					<sunbor:enums property="fdIsAvailable" enumsType="sys_org_available" elementType="radio" />
				</td>
			</tr>
				<%-- 扩展属性 --%>
			<c:import url="/sys/organization/sys_org_element_external/sysOrgExternal_common_ext_props_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="sysOrgDeptForm"/>
			</c:import>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgEco.view.range"/>
				</td>
				<td width=85% colspan="3">
					<table class="tb_simple" width="100%" >
						<tr style="display:none">
							<td>
								<ui:switch property="fdRange.fdIsOpenLimit" checked="true" showType="show" enabledText="${ lfn:message('sys-organization:sysOrgEco.view') }" disabledText="${ lfn:message('sys-organization:sysOrgEco.view.not') }" onValueChange="changeReviewRange()"></ui:switch>
							</td>
						</tr>
						<tr id="typeReview" style="display:none">
							<td>
								<div>
									<xform:radio property="fdRange.fdViewType" title="${ lfn:message('sys-organization:sysOrgEco.view.type') }"  onValueChange="changeReviewType" alignment="V">
										<xform:simpleDataSource value="0">
											<bean:message bundle="sys-organization" key="sysOrgEco.view.type.myself"/>
										</xform:simpleDataSource>
										<xform:simpleDataSource value="1"><bean:message bundle="sys-organization" key="sysOrgEco.view.type.inner"/></xform:simpleDataSource>
										<xform:simpleDataSource value="2"><bean:message bundle="sys-organization" key="sysOrgEco.view.type.special"/></xform:simpleDataSource>
									</xform:radio>
								</div>

							</td>
						</tr>
						<tr id="subTypeReview" style="display:none">
							<td>
								<div class="paddingLeft">
									<xform:checkbox property="fdRange.fdViewSubType" subject="${ lfn:message('sys-organization:sysOrgEco.view.subType') }" onValueChange="changeSubType" isArrayValue="false" alignment="V" required="true">
										<xform:enumsDataSource enumsType="sys_org_eco_sub_view" />
									</xform:checkbox>
								</div>
							</td>

						</tr>
						<tr id="otherReview" style="display:none">
							<td>
								<div class="paddingLeft">
									<xform:address textarea="true" mulSelect="true" propertyId="fdRange.fdOtherIds" propertyName="fdRange.fdOtherNames" orgType="ORG_TYPE_ORGORDEPT|ORG_TYPE_PERSON" style="width:97%;height:90px;" required="true"></xform:address>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgEco.hide.range3"/>
				</td>
				<td width=85% colspan="3">
					<table class="tb_simple" width="100%" >
						<tr>
							<td>
								<ui:switch property="fdHideRange.fdIsOpenLimit" checked="true" showType="edit" enabledText="${ lfn:message('sys-organization:sysOrgEco.hide') }${ lfn:message('sys-organization:sysOrgEco.hide.type.eco') }" disabledText="${ lfn:message('sys-organization:sysOrgEco.hide.not') }${ lfn:message('sys-organization:sysOrgEco.hide.type.eco') }" onValueChange="changeReviewHideRange()"></ui:switch>
							</td>
						</tr>
						<tr id="typeReviewHide" style="display:none">
							<td>
								<c:if test="${not empty hideRangeTip}">
									<div class="hideRangeTip">${hideRangeTip}</div>
								</c:if>
								<div class="typeDiv">
									<xform:radio property="fdHideRange.fdViewType"  onValueChange="changeReviewHideType" alignment="V">
										<xform:simpleDataSource value="0"><bean:message bundle="sys-organization" key="sysOrgEco.hide.type.all.eco"/></xform:simpleDataSource>
										<xform:simpleDataSource value="1"><bean:message bundle="sys-organization" key="sysOrgEco.hide.type.special.eco"/></xform:simpleDataSource>
									</xform:radio>
								</div>
							</td>
						</tr>
						<tr id="otherReviewHide" style="display:none">
							<td>
								<div class="paddingLeft">
									<xform:address textarea="true" subject="${ lfn:message('sys-organization:sysOrgEco.hide.type.special.eco') }" mulSelect="true" propertyId="fdHideRange.fdOtherIds" propertyName="fdHideRange.fdOtherNames" orgType="ORG_TYPE_ORGORDEPT|ORG_TYPE_PERSON" style="width:97%;height:90px;" required="true"></xform:address>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<logic:equal name="sysOrgDeptForm" property="method_GET" value="edit">
				<tr>
					<td width=15% class="td_normal_title">
						<bean:message bundle="sys-organization" key="sysOrgElementExternal.ding.url"/>
					</td>
					<td width=65% colspan="3">
						<xform:textarea property="fdRange.fdInviteUrl" style="width:100%" onValueChange="showQrcode"></xform:textarea>
					</td>
					<td style="text-align: center;">
						<div id="__qrcode"></div>
					</td>
				</tr>
			</logic:equal>
		</table>

	</center>
	<html:hidden property="method_GET"/>
	<html:hidden property="fdId"/>
	<html:hidden property="fdParentId"/>
	<html:hidden property="fdRange.fdId"/>
	<html:hidden property="fdHideRange.fdId"/>
	<html:hidden property="docCreatorId"/>
	<input type="hidden" name="cateId" value="${cateId}">

</html:form>
<script>
	Com_IncludeFile("dialog.js");
</script>
<script language="JavaScript">
	Com_IncludeFile("data.js");
	var _validation = $KMSSValidation(document.forms['sysOrgDeptForm']);

	LUI.ready(function(){
		changeReviewRange();
		changeReviewHideRange();
	});

	var NameValidators = {
		'uniqueName' : {
			error : "<bean:message key='organization.error.fdName.mustUnique' bundle='sys-organization' />",
			test : function (value) {
				var fdId = document.getElementsByName("fdId")[0].value;
				var result = checkNameUnique("sysOrgElementService",value,fdId,"unique");
				if (!result)
					return false;
				return true;
			}
		},
		'invalidName': {
			error : "<bean:message key='organization.error.newNameSameOldName' bundle='sys-organization' />",
			test  : function(value) {
				if (NameValidators["fdName"] && (NameValidators["fdName"]==value)){
					return true;
				}
				NameValidators["fdName"]=null;
				var fdId = document.getElementsByName("fdId")[0].value;
				var result = checkNameUnique("sysOrgElementService",value,fdId,"invalid");
				if (!result){
					if(window.confirm("<bean:message key='organization.warn.fdName.ConfirmMsg' bundle='sys-organization' />")){
						NameValidators["fdName"]=value;
						return true;
					}else{
						return false;
					}
				}
				return true;
			}
		}
	};
	_validation.addValidators(NameValidators);

	//校验名称是否唯一
	function checkNameUnique(bean, fdName,fdId,checkType) {
		var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName="
				+ bean + "&fdName=" + fdName+"&fdId="+fdId+"&checkType="+checkType+"&fdOrgType=1&date="+new Date());
		var xmlHttpRequest;
		if (window.XMLHttpRequest) { // Non-IE browsers
			xmlHttpRequest = new XMLHttpRequest();
		} else if (window.ActiveXObject) { // IE
			try {
				xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (othermicrosoft) {
				try {
					xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (failed) {
					xmlHttpRequest = false;
				}
			}
		}
		if (xmlHttpRequest) {
			xmlHttpRequest.open("GET", url, false);
			xmlHttpRequest.send();
			var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
			if (result != "") {
				return false;
			}
		}
		return true;
	}
	function changeReviewHideRange(value) {
		var fdIsOpenLimit = value || $(':hidden[name="fdHideRange.fdIsOpenLimit"]').val();
		if (fdIsOpenLimit == 'undefined' || fdIsOpenLimit == null || fdIsOpenLimit == '') {
			hideAndDisabled('typeReviewHide');
			hideAndDisabled('otherReviewHide');
			return;
		}
		if (fdIsOpenLimit == 'true') {
			showAndAbled('typeReviewHide');
			changeReviewHideType();
		} else {
			hideAndDisabled('typeReviewHide');
			hideAndDisabled('otherReviewHide');
		}
	}

	function changeReviewHideType(value) {
		var fdViewType = value || $(':radio[name="fdHideRange.fdViewType"]:checked').val();
		if (fdViewType == undefined || fdViewType == null || fdViewType == '') {
			hideAndDisabled('otherReviewHide');
			return;
		}

		if (fdViewType == 1) {
			showAndAbled('otherReviewHide');
			// 解决地址本不能校验输入值的问题
			$(".paddingLeft .inputselectmul textarea[name='fdHideRange.fdOtherNames']").hide();
			$(".paddingLeft div.mf_container").css("display", "inline-block");
		} else {
			hideAndDisabled('otherReviewHide');
		}
	}

	function changeReviewRange(value) {
		var fdIsOpenLimit = value || $(':hidden[name="fdRange.fdIsOpenLimit"]').val();
		if (fdIsOpenLimit == 'undefined' || fdIsOpenLimit == null || fdIsOpenLimit == '') {
			hideAndDisabled('typeReview');
			hideAndDisabled('subTypeReview');
			hideAndDisabled('otherReview');
			return;
		}
		if (fdIsOpenLimit == 'true') {
			showAndAbled('typeReview');
			changeReviewType();
		} else {
			hideAndDisabled('typeReview');
			hideAndDisabled('subTypeReview');
			hideAndDisabled('otherReview');
		}
	}

	function changeReviewType(value) {
		var fdViewType = value || $(':radio[name="fdRange.fdViewType"]:checked').val();
		if (fdViewType == undefined || fdViewType == null || fdViewType == '') {
			hideAndDisabled('subTypeReview');
			hideAndDisabled('otherReview');
			return;
		}

		if (fdViewType == 2) {
			showAndAbled('subTypeReview');
			changeSubType();
		} else {
			hideAndDisabled('subTypeReview');
			hideAndDisabled('otherReview');
		}
	}

	function changeSubType(value) {
		var fdViewSubType = value || $(':hidden[name="fdRange.fdViewSubType"]').val();
		if (fdViewSubType == undefined || fdViewSubType == null || fdViewSubType == '') {
			hideAndDisabled('otherReview');
			return;
		}

		if (fdViewSubType.indexOf('2') != -1) {
			showAndAbled('otherReview');
		} else {
			hideAndDisabled('otherReview');
		}
	}

	var showAndAbled = function(id) {
		var parentDom = $('#' + id);
		if(!parentDom)
			return;
		var childInputs = parentDom.find(':input');
		if(childInputs)
			childInputs.removeAttr('disabled');
		parentDom.show();
	};

	var hideAndDisabled= function(id) {
		var parentDom = $('#' + id);
		if(!parentDom)
			return;
		var childInputs = parentDom.find(':input');
		if(childInputs)
			childInputs.prop('disabled', 'disabled');
		parentDom.hide();
	};
</script>
<script>
	function showQrcode(val) {
		if(val.length > 0) {
			seajs.use(["lui/jquery", "lui/qrcode"], function($, qrcode) {
				$("#__qrcode").empty();
				qrcode.Qrcode({
					text : val,
					element : $("#__qrcode")[0],
					render : 'canvas',
					width : 150,
					height : 150
				});
			});
		}
	}
	window.onload=function() {
		var val = document.getElementsByName("fdRange.fdInviteUrl")[0].value;
		if(val.length > 0) {
			showQrcode(val);
		}
	}
</script>
<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
<c:if test="${frameShowTop=='yes' }">
	<ui:top id="top"></ui:top>
	<kmss:ifModuleExist path="/sys/help">
		<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
	</kmss:ifModuleExist>
</c:if>
<%@ include file="/resource/jsp/edit_down.jsp"%>