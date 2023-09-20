<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/sys/organization/sys_org_org/sysOrgOrg.do">
	<style type="text/css">
		.tb_simple tr td{border:0}
		.typeDiv {padding-left:47px}
		.paddingLeft {padding-left:61px}
	</style>
	<div id="optBarDiv">
		<logic:equal name="sysOrgOrgForm" property="method_GET" value="edit">
			<input type=button value="<bean:message key="button.update"/>"
				   onclick="__update();">
		</logic:equal>
		<logic:equal name="sysOrgOrgForm" property="method_GET" value="add">
			<input type=button value="<bean:message key="button.save"/>"
				   onclick="Com_Submit(document.sysOrgOrgForm, 'save');">
			<input type=button value="<bean:message key="button.saveadd"/>"
				   onclick="Com_Submit(document.sysOrgOrgForm, 'saveadd');">
		</logic:equal>
		<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
	</div>
	<p class="txttitle"><bean:message bundle="sys-organization" key="sysOrgElement.org"/><bean:message key="button.edit"/></p>
	<center>
		<table class="tb_normal" width=95%>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgOrg.fdName"/>
				</td><td width=35% colspan="3">
				<xform:text property="fdName" validators="uniqueName invalidName" style="width:90%"></xform:text>
				<div id="fdName_id"></div>
			</td>
			</tr>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgOrg.fdParent"/>
				</td><td width=35%>
				<html:hidden property="fdParentId"/>
				<html:text style="width:90%" property="fdParentName" readonly="true" styleClass="inputsgl"/>
				<a href="#" onclick="Dialog_Tree(
						false,
						'fdParentId',
						'fdParentName',
						null,
						'organizationTree&fdIsExternal=false&parent=!{value}&orgType='+(ORG_TYPE_ORG|ORG_FLAG_BUSINESSALL),
						'<bean:message bundle="sys-organization" key="organization.moduleName"/>',
						null,
						null,
						'<bean:write name="sysOrgOrgForm" property="fdId"/>');">
					<bean:message key="dialog.selectOrg"/>
				</a>
			</td>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgOrg.fdNo"/>
				</td><td width=35%>
					<%-- 引用通用的编号属性 --%>
				<input type="hidden" name="fdOrgType" value="1">
				<%@ include file="/sys/organization/org_common_fdNo_edit.jsp"%>
			</td>
			</tr>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgOrg.fdThisLeader"/>
				</td><td width=35%>
				<html:hidden property="fdThisLeaderId"/>
				<html:text style="width:90%" property="fdThisLeaderName" readonly="true" styleClass="inputsgl"/>
				<a href="#" onclick="Dialog_Address(false, 'fdThisLeaderId', 'fdThisLeaderName', null, ORG_TYPE_POSTORPERSON, null, null, null, null, null, null, null, null, false);">
					<bean:message key="dialog.selectOrg"/>
				</a>
			</td>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgOrg.fdSuperLeader"/>
				</td><td width=35%>
				<html:hidden property="fdSuperLeaderId"/>
				<html:text style="width:90%" property="fdSuperLeaderName" readonly="true" styleClass="inputsgl"/>
				<a href="#" onclick="Dialog_Address(false, 'fdSuperLeaderId', 'fdSuperLeaderName', null, ORG_TYPE_POSTORPERSON, null, null, null, null, null, null, null, null, false);">
					<bean:message key="dialog.selectOrg"/>
				</a>
			</td>
			</tr>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgOrg.fdKeyword"/>
				</td><td width=35%>
				<xform:text property="fdKeyword" style="width:90%"></xform:text>

			</td>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgOrg.fdOrder"/>
				</td><td width=35%>
				<xform:text property="fdOrder" style="width:90%"></xform:text>
			</td>
			</tr>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgElement.fdOrgEmail"/>
				</td><td width=35% colspan="3">
				<xform:text property="fdOrgEmail" style="width:90%"></xform:text>
			</td>
			</tr>
			<tr>
				<c:set var="_colspan" value="3" />
				<c:if test="${sysOrgOrgForm.method_GET=='edit'}">
					<c:set var="_colspan" value="1" />
					<td width=15% class="td_normal_title">
						<bean:message bundle="sys-organization" key="sysOrgDept.fdIsAvailable"/>
					</td>
					<td width="35%">
						<sunbor:enums property="fdIsAvailable" enumsType="sys_org_available" elementType="radio" />
					</td>
				</c:if>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgDept.fdIsBusiness"/>
				</td>
				<td width=35% colspan="${_colspan}">
					<sunbor:enums property="fdIsBusiness" enumsType="common_yesno" elementType="radio" />
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-organization" key="sysOrgElement.authElementAdmins"/>
				</td><td width="85%" colspan="3">
				<xform:address propertyId="authElementAdminIds" propertyName="authElementAdminNames" mulSelect="true" orgType="ORG_TYPE_POSTORPERSON" style="width:85%" isExternal="false"/>
				<div class="description_txt">
					<bean:message bundle="sys-organization" key="sysOrgElement.authElementAdmins.describe"/>
				</div>
			</td>
			</tr>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgEco.view.range"/>
				</td>
				<td width=85% colspan="3">
					<table class="tb_simple" width="100%" >
						<tr>
							<td>
								<ui:switch property="fdRange.fdIsOpenLimit" enabledText="${ lfn:message('sys-organization:sysOrgEco.view') }" disabledText="${ lfn:message('sys-organization:sysOrgEco.view.not') }" onValueChange="changeReviewRange()"></ui:switch>
							</td>
						</tr>
						<tr id="typeReview" style="display:none">
							<td>
								<div class="typeDiv">
									<xform:radio property="fdRange.fdViewType" title="${ lfn:message('sys-attend:sysAttendCategory.fdOvtReviewType') }"  onValueChange="changeReviewType" alignment="V">
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
									<xform:checkbox property="fdRange.fdViewSubType" subject="${ lfn:message('sys-organization:sysEco.view.subType') }" onValueChange="changeSubType" isArrayValue="false" alignment="V" required="true">
										<xform:enumsDataSource enumsType="sys_org_eco_sub_view" />
									</xform:checkbox>
								</div>
							</td>

						</tr>
						<tr id="otherReview" style="display:none">
							<td>
								<div class="paddingLeft">
									<xform:address textarea="true" subject="组织/人员" mulSelect="true" propertyId="fdRange.fdOtherIds" propertyName="fdRange.fdOtherNames" orgType="ORG_TYPE_ORGORDEPT|ORG_TYPE_PERSON" style="width:97%;height:90px;" required="true"></xform:address>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgEco.hide.range1"/>
				</td>
				<td width=85% colspan="3">
					<table class="tb_simple" width="100%" >
						<tr>
							<td>
								<ui:switch property="fdHideRange.fdIsOpenLimit" enabledText="${ lfn:message('sys-organization:sysOrgEco.hide') }${ lfn:message('sys-organization:sysOrgEco.hide.type.in1') }" disabledText="${ lfn:message('sys-organization:sysOrgEco.hide.not') }${ lfn:message('sys-organization:sysOrgEco.hide.type.in1') }" onValueChange="changeReviewHideRange()"></ui:switch>
							</td>
						</tr>
						<tr id="typeReviewHide" style="display:none">
							<td>
								<div class="typeDiv">
									<xform:radio property="fdHideRange.fdViewType"  onValueChange="changeReviewHideType" alignment="V">
										<xform:simpleDataSource value="0"><bean:message bundle="sys-organization" key="sysOrgEco.hide.type.all.in"/></xform:simpleDataSource>
										<xform:simpleDataSource value="1"><bean:message bundle="sys-organization" key="sysOrgEco.hide.type.special.in"/></xform:simpleDataSource>
									</xform:radio>
								</div>

							</td>
						</tr>
						<tr id="otherReviewHide" style="display:none">
							<td>
								<div class="paddingLeft">
									<xform:address textarea="true" subject="${ lfn:message('sys-organization:sysOrgEco.hide.type.special.in') }" mulSelect="true" propertyId="fdHideRange.fdOtherIds" propertyName="fdHideRange.fdOtherNames" orgType="ORG_TYPE_ORGORDEPT|ORG_TYPE_PERSON" style="width:97%;height:90px;" required="true"></xform:address>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgOrg.fdMemo"/>
				</td><td colspan="3">
				<xform:textarea property="fdMemo" style="width:100%"></xform:textarea>
			</td>
			</tr>
				<%-- 引入动态属性 --%>
			<c:import url="/sys/property/custom_field/custom_fieldEdit.jsp" charEncoding="UTF-8" />

			<logic:equal name="sysOrgOrgForm" property="method_GET" value="edit">
				<c:if test="${'true' eq sysOrgOrgForm.fdIsRelation}">
					<tr>
						<td width=15% class="td_normal_title">
							<bean:message bundle="sys-organization" key="sysOrgOrg.fdIsRelation"/>
						</td><td colspan="3">
						<xform:checkbox property="fdIsRelation" showStatus="edit">
							<xform:simpleDataSource value="true">
								<bean:message bundle="sys-organization" key="sysOrgOrg.fdIsRelation.desc"/>
							</xform:simpleDataSource>
						</xform:checkbox>
					</td>
					</tr>
				</c:if>
			</logic:equal>
			<tr>
				<td colspan="4">
					<bean:message bundle="sys-organization" key="organization.org.dept.different"/><br>
					<bean:message bundle="sys-organization" key="organization.org.dept.different1"/><br>
					<bean:message bundle="sys-organization" key="organization.org.dept.different2"/><br>
					<bean:message bundle="sys-organization" key="organization.org.dept.different3"/><br>
				</td>
			</tr>
		</table>

	</center>
	<html:hidden property="method_GET"/>
	<html:hidden property="fdId"/>
	<html:hidden property="fdRange.fdId"/>
	<html:hidden property="fdHideRange.fdId"/>
</html:form>
<script>Com_IncludeFile("dialog.js");</script>
<script language="JavaScript">
	Com_IncludeFile("data.js");
	var _validation = $KMSSValidation(document.forms['sysOrgOrgForm']);

	var NameValidators = {
		'uniqueName' : {
			error : "<bean:message key='sysOrgOrg.error.fdName.mustUnique' bundle='sys-organization' />",
			test : function (value) {
				var fdId = document.getElementsByName("fdId")[0].value;
				var result = checkNameUnique("sysOrgElementService",value,fdId,"unique");
				if (!result)
					return false;
				return true;
			}
		},
		'invalidName': {
			error : "<bean:message key='sysOrgOrg.error.newNameSameOldName' bundle='sys-organization' />",
			test  : function(value) {
				if (NameValidators["fdName"] && (NameValidators["fdName"]==value)){
					return true;
				}
				NameValidators["fdName"]=null;
				var fdId = document.getElementsByName("fdId")[0].value;
				var result = checkNameUnique("sysOrgElementService",value,fdId,"invalid");
				if (!result){
					if(window.confirm("<bean:message key='sysOrgOrg.warn.fdName.ConfirmMsg' bundle='sys-organization' />")){
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
		fdName = encodeURIComponent(fdName);
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

	function __update() {
		// 更新前需要检查与业务相关的数据
		var _fdIsBusiness = document.getElementsByName("fdIsBusiness");
		var fdIsBusiness;
		for(var i=0; i<_fdIsBusiness.length; i++) {
			if(_fdIsBusiness[i].checked) {
				fdIsBusiness = _fdIsBusiness[i].value;
			}
		}
		var fdId = document.getElementsByName("fdId")[0].value;
		var result = "true" == fdIsBusiness;
		$("#fdIsBusiness_validate").remove();
		if(!result) {
			var data = new KMSSData();
			data.UseCache = false;
			data.AddBeanData("sysOrgElementService&type=1&fdId=" + fdId);
			var rtn = data.GetHashMapArray()[0];
			if(rtn) {
				result = false;
				var validate = '<div class="validation-advice" id="fdIsBusiness_validate" _reminder="true"><table class="validation-table"><tbody><tr><td><div class="lui_icon_s lui_icon_s_icon_validator"></div></td><td class="validation-advice-msg">'+rtn.msg+'</td></tr></tbody></table></div>';
				$(_fdIsBusiness).parents("td").append(validate);
			} else {
				result = true;
			}
		}
		if (result) {
			Com_Submit(document.sysOrgOrgForm, 'update');
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
			// 设置默认选择
			var _fdViewSubType = $(':hidden[name="fdRange.fdViewSubType"]').val();
			if (_fdViewSubType == '') {
				$($(':checkbox[name="_fdRange.fdViewSubType"]')[0]).attr("checked",true);
				$(':hidden[name="fdRange.fdViewSubType"]').val("1");
			}
		} else {
			hideAndDisabled('subTypeReview');
			hideAndDisabled('otherReview');
		}
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
			var textNames = $(".paddingLeft .inputselectmul textarea[name='fdHideRange.fdOtherNames']");
			textNames.hide();
			textNames.parent().find("div.mf_container").css("display", "inline-block");
		} else {
			hideAndDisabled('otherReviewHide');
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

	LUI.ready(function(){
		changeReviewRange();
		changeReviewHideRange();
	});
</script>
<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
<c:if test="${frameShowTop=='yes' }">
	<ui:top id="top"></ui:top>
	<kmss:ifModuleExist path="/sys/help">
		<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
	</kmss:ifModuleExist>
</c:if>
<%@ include file="/resource/jsp/edit_down.jsp"%>