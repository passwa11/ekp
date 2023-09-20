<%@ page import="com.landray.kmss.sys.organization.model.SysOrganizationConfig" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>

<style type="text/css">
	.tb_simple tr td{border:0}
	.paddingLeft {padding-left:14px}
</style>
<script>
	// 部门和人员扩展属性
	window.fdDeptProps = [];
	window.fdPersonProps = [];
</script>
<div id="optBarDiv">
	<logic:equal name="sysOrgElementExternalForm" property="method_GET" value="edit">
		<input type=button value="<bean:message key="button.update"/>"
			   onclick="Com_Submit(document.sysOrgElementExternalForm, 'update');">
	</logic:equal>
	<logic:equal name="sysOrgElementExternalForm" property="method_GET" value="add">
		<input type=button value="<bean:message key="button.save"/>"
			   onclick="Com_Submit(document.sysOrgElementExternalForm, 'save');">
	</logic:equal>
	<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-organization" key="sysOrgEco.type"/><bean:message key="button.edit"/></p>
<html:form action="/sys/organization/sys_org_element_external/sysOrgElementExternal.do">
	<center>
		<table class="tb_normal" width=95%>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgEco.fdName"/>
				</td><td width=35%>
				<xform:text property="fdElement.fdName" subject="${ lfn:message('sys-organization:sysOrgEco.fdName') }" validators="uniqueName invalidName maxLength(200)" style="width:90%" required="true"></xform:text>
				<div id="fdName_id"></div>
			</td>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgElement.fdNo"/>
				</td>
				<td width=35%>
					<% if (new SysOrganizationConfig().isNoRequired()) { %>
					<xform:text property="fdElement.fdNo" validators="required uniqueNo" style="width:90%"></xform:text>
					<span class="txtstrong">*</span>
					<% } else { %>
					<xform:text property="fdElement.fdNo" style="width:90%"></xform:text>
					<% } %>
				</td>
			</tr>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgElement.fdOrder"/>
				</td>
				<td width=35% id="fdOrderTd">
					<xform:text property="fdElement.fdOrder" validators="digits maxLength(10)" style="width:90%"></xform:text>
				</td>
				<logic:equal name="sysOrgElementExternalForm" property="method_GET" value="edit">
					<td width=15% class="td_normal_title">
						<bean:message bundle="sys-organization" key="sysOrgEco.is.available"/>
					</td>
					<td width=35%>
						<xform:radio property="fdElement.fdIsAvailable" title="${ lfn:message('sys-organization:sysOrgEco.is.available') }" alignment="H">
							<xform:simpleDataSource value="true"><bean:message bundle="sys-organization" key="sysOrgEco.available.open"/></xform:simpleDataSource>
							<xform:simpleDataSource value="false"><bean:message bundle="sys-organization" key="sysOrgEco.available.close"/></xform:simpleDataSource>
						</xform:radio>
					</td>
				</logic:equal>
				<logic:equal name="sysOrgElementExternalForm" property="method_GET" value="add">
					<script>
						$("#fdOrderTd").attr("colspan", 3);
					</script>
				</logic:equal>
			</tr>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgElement.authElementAdmins"/>
				</td><td width=85% colspan=3>
				<xform:address propertyId="fdElement.authElementAdminIds" propertyName="fdElement.authElementAdminNames" mulSelect="true" orgType="ORG_TYPE_POSTORPERSON" style="width:85%" />
				<div style="color:#F00">
					<bean:message bundle="sys-organization" key="sysOrgElement.authElementAdmins.desc"/>
				</div>
			</td>
			</tr>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgEco.attribute"/>
				</td>
				<td width=85% colspan="3">
					<table id="detpTaple" class="tb_normal" width="100%">
						<tr class="tr_normal_title">
							<td width="20%">
								<bean:message bundle="sys-organization" key="sysOrgEco.prop.name"/>
							</td>
							<td width="20%">
								<bean:message bundle="sys-organization" key="sysOrgEco.prop.mode"/>
							</td>
							<td width="20%">
								<bean:message bundle="sys-organization" key="sysOrgEco.prop.required"/>
							</td>
							<td width="20%">
								<bean:message bundle="sys-organization" key="sysOrgEco.prop.default.showlist"/>
							</td>
							<td width="20%">
								<a href="javascript:;" class="com_btn_link" onclick="dialogProp('detpTaple', 'dept', 'add');">
									<bean:message bundle="sys-organization" key="sysOrgEco.prop.add"/>
								</a>
							</td>
						</tr>
						<c:forEach items="${sysOrgElementExternalForm.fdDeptProps}" var="dept" varStatus="vs">
							<script>
								var obj = {};
								obj["idx"] = "${vs.index}" || "";
								obj["fdId"] = "${dept.fdId}" || ""
								obj["fdName"] = "${dept.fdName}" || "";
								obj["fdColumnName"] = "${dept.fdColumnName}" || "";
								obj["fdFieldName"] = "${dept.fdFieldName}" || "";
								obj["fdRequired"] = "${dept.fdRequired}" || "";
								obj["fdFieldType"] = "${dept.fdFieldType}" || "";
								obj["fdStatus"] = "${dept.fdStatus}" || "";
								obj["fdOrder"] = "${dept.fdOrder}" || "";
								obj["fdFieldLength"] = "${dept.fdFieldLength}" || "";
								obj["fdScale"] = "${dept.fdScale}" || "";
								obj["fdDisplayType"] = "${dept.fdDisplayType}" || "";
								obj["fdFieldEnums"] = [];
								<c:forEach items="${dept.fdFieldEnums}" var="fieldEnum">
								var _fdName = "${fieldEnum.fdName}";
								var _fdValue = "${fieldEnum.fdValue}";
								if(_fdName && _fdValue) {
									obj["fdFieldEnums"].push({"fdName": _fdName, "fdValue": _fdValue});
								}
								</c:forEach>
								fdDeptProps.push(obj);
							</script>
							<tr name="propTr" class="add_prop" style="text-align: center;">
								<td>${dept.fdName}</td>
								<td>
									<c:choose>
										<c:when test="${dept.fdDisplayType == 'text'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.text"/>
										</c:when>
										<c:when test="${dept.fdDisplayType == 'textarea'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.textarea"/>
										</c:when>
										<c:when test="${dept.fdDisplayType == 'radio'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.radio"/>
										</c:when>
										<c:when test="${dept.fdDisplayType == 'checkbox'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.checkbox"/>
										</c:when>
										<c:when test="${dept.fdDisplayType == 'select'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.select"/>
										</c:when>
										<c:when test="${dept.fdDisplayType == 'datetime'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.datetime"/>
										</c:when>
										<c:when test="${dept.fdDisplayType == 'date'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.date"/>
										</c:when>
										<c:when test="${dept.fdDisplayType == 'time'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.time"/>
										</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${dept.fdRequired == 'true'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.required.yes"/>
										</c:when>
										<c:otherwise>
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.required.no"/>
										</c:otherwise>
									</c:choose>
								</td>
								<td><input type="checkbox" name="fdDeptProps[${vs.index}].fdShowList" ${dept.fdShowList == 'true' ? 'checked' : '' }></td>

								<td>
									<a href="javascript:;" class="com_btn_link" onclick="editProp('detpTaple', 'dept', 'edit', this);"><bean:message bundle="sys-organization" key="sysOrgEco.prop.edit"/></a>
									<a href="javascript:;" class="com_btn_link" onclick="changePropStatus('detpTaple', 'dept', 'edit', this, '${dept.fdId}');">${dept.fdStatus == 'true' ? '禁用' : '启用'}</a>
									<a href="javascript:;" class="com_btn_link" onclick="delProp('detpTaple', 'dept', 'edit', this,'${dept.fdId}');"><bean:message bundle="sys-organization" key="sysOrgEco.prop.delete"/></a>
								</td>
							</tr>
						</c:forEach>
					</table>
					<input type="hidden" name="detpProp" validators="checkDetpProp">
					<c:forEach var="prop" items="${fdDeptProps}" varStatus="status">
						<c:out value="${status.index}"/>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgEco.person.attribute"/>
				</td>
				<td width=85% colspan="3">
					<table id="personTaple" class="tb_normal" width="100%">
						<tr class="tr_normal_title">
							<td width="20%">
								<bean:message bundle="sys-organization" key="sysOrgEco.prop.name"/>
							</td>
							<td width="20%">
								<bean:message bundle="sys-organization" key="sysOrgEco.prop.mode"/>
							</td>
							<td width="20%">
								<bean:message bundle="sys-organization" key="sysOrgEco.prop.required"/>
							</td>
							<td width="20%">
								<bean:message bundle="sys-organization" key="sysOrgEco.prop.default.showlist"/>
							</td>
							<td width="20%">
								<a href="javascript:;" class="com_btn_link" onclick="dialogProp('personTaple', 'person', 'add');">
									<bean:message bundle="sys-organization" key="sysOrgEco.prop.add"/>
								</a>
							</td>
							<c:forEach items="${sysOrgElementExternalForm.fdPersonProps}" var="person" varStatus="vs">
							<script>
								var obj = {};
								obj["idx"] = "${vs.index}" || "";
								obj["fdId"] = "${person.fdId}" || "";
								obj["fdName"] = "${person.fdName}" || "";
								obj["fdColumnName"] = "${person.fdColumnName}" || "";
								obj["fdFieldName"] = "${person.fdFieldName}" || "";
								obj["fdRequired"] = "${person.fdRequired}" || "";
								obj["fdFieldType"] = "${person.fdFieldType}" || "";
								obj["fdStatus"] = "${person.fdStatus}" || "";
								obj["fdOrder"] = "${person.fdOrder}" || "";
								obj["fdFieldLength"] = "${person.fdFieldLength}" || "";
								obj["fdScale"] = "${person.fdScale}" || "";
								obj["fdDisplayType"] = "${person.fdDisplayType}" || "";
								obj["fdFieldEnums"] = [];
								<c:forEach items="${person.fdFieldEnums}" var="fieldEnum">
								var _fdName = "${fieldEnum.fdName}";
								var _fdValue = "${fieldEnum.fdValue}";
								if(_fdName && _fdValue) {
									obj["fdFieldEnums"].push({"fdName": _fdName, "fdValue": _fdValue});
								}
								</c:forEach>
								fdPersonProps.push(obj);
							</script>
						<tr name="propTr" class="add_prop" style="text-align: center;">
							<td>${person.fdName}</td>
							<td>
								<c:choose>
									<c:when test="${person.fdDisplayType == 'text'}">
										<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.text"/>
									</c:when>
									<c:when test="${person.fdDisplayType == 'textarea'}">
										<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.textarea"/>
									</c:when>
									<c:when test="${person.fdDisplayType == 'radio'}">
										<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.radio"/>
									</c:when>
									<c:when test="${person.fdDisplayType == 'checkbox'}">
										<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.checkbox"/>
									</c:when>
									<c:when test="${person.fdDisplayType == 'select'}">
										<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.select"/>
									</c:when>
									<c:when test="${person.fdDisplayType == 'datetime'}">
										<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.datetime"/>
									</c:when>
									<c:when test="${person.fdDisplayType == 'date'}">
										<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.date"/>
									</c:when>
									<c:when test="${person.fdDisplayType == 'time'}">
										<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.time"/>
									</c:when>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${person.fdRequired == 'true'}">
										<bean:message bundle="sys-organization" key="sysOrgEco.prop.required.yes"/>
									</c:when>
									<c:otherwise>
										<bean:message bundle="sys-organization" key="sysOrgEco.prop.required.no"/>
									</c:otherwise>
								</c:choose>
							</td>
							<td><input type="checkbox" name="fdPersonProps[${vs.index}].fdShowList" ${person.fdShowList == 'true' ? 'checked' : '' }></td>
							<td>
								<a href="javascript:;" class="com_btn_link" onclick="editProp('personTaple', 'person', 'edit', this);"><bean:message bundle="sys-organization" key="sysOrgEco.prop.edit"/></a>
								<a href="javascript:;" class="com_btn_link" onclick="changePropStatus('personTaple', 'person', 'edit', this, '${person.fdId}');">${person.fdStatus == 'true' ? '禁用' : '启用'}</a>
								<a href="javascript:;" class="com_btn_link" onclick="delProp('personTaple', 'person', 'edit', this, '${person.fdId}');"><bean:message bundle="sys-organization" key="sysOrgEco.prop.delete"/></a>
							</td>
						</tr>
						</c:forEach>
						</tr>
					</table>
					<input type="hidden" name="personProp" validators="checkPersonProp">
					<c:forEach var="prop" items="${fdPersonProps}" varStatus="status">
						<c:out value="${status.index}"/>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgEco.view.range"/>
				</td>
				<td width=85% colspan="3">
					<table class="tb_simple" width="100%" >
						<tr style="display:none">
							<td>
								<ui:switch property="fdElement.fdRange.fdIsOpenLimit" checked="true" showType="show" enabledText="${ lfn:message('sys-organization:sysOrgEco.view') }" disabledText="${ lfn:message('sys-organization:sysOrgEco.view.not') }" onValueChange="changeReviewRange()"></ui:switch>
							</td>
						</tr>
						<tr id="typeReview" style="display:none">
							<td>
								<div class="typeDiv">
									<xform:radio property="fdElement.fdRange.fdViewType" title="${ lfn:message('sys-organization:sysOrgEco.view.type') }"  onValueChange="changeReviewType" alignment="V">
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
									<xform:checkbox property="fdElement.fdRange.fdViewSubType" subject="${ lfn:message('sys-organization:sysOrgEco.view.subType') }" onValueChange="changeSubType" isArrayValue="false" alignment="V" required="true">
										<xform:enumsDataSource enumsType="sys_org_eco_sub_view" />
									</xform:checkbox>
								</div>
							</td>

						</tr>
						<tr id="otherReview" style="display:none">
							<td>
								<div class="paddingLeft">
									<xform:address textarea="true" mulSelect="true" propertyId="fdElement.fdRange.fdOtherIds" propertyName="fdElement.fdRange.fdOtherNames" orgType="ORG_TYPE_ORGORDEPT|ORG_TYPE_PERSON" style="width:97%;height:90px;" required="true"></xform:address>
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
								<ui:switch property="fdElement.fdHideRange.fdIsOpenLimit" checked="true" showType="edit" enabledText="${ lfn:message('sys-organization:sysOrgEco.hide') }${ lfn:message('sys-organization:sysOrgEco.hide.type.eco') }" disabledText="${ lfn:message('sys-organization:sysOrgEco.hide.not') }${ lfn:message('sys-organization:sysOrgEco.hide.type.eco') }" onValueChange="changeReviewHideRange()"></ui:switch>
							</td>
						</tr>
						<tr id="typeReviewHide" style="display:none">
							<td>
								<div class="typeDiv">
									<xform:radio property="fdElement.fdHideRange.fdViewType"  onValueChange="changeReviewHideType" alignment="V">
										<xform:simpleDataSource value="0"><bean:message bundle="sys-organization" key="sysOrgEco.hide.type.all.eco"/></xform:simpleDataSource>
										<xform:simpleDataSource value="1"><bean:message bundle="sys-organization" key="sysOrgEco.hide.type.special.eco"/></xform:simpleDataSource>
									</xform:radio>
								</div>

							</td>
						</tr>
						<tr id="otherReviewHide" style="display:none">
							<td>
								<div class="paddingLeft">
									<xform:address textarea="true" subject="${ lfn:message('sys-organization:sysOrgEco.hide.type.special.eco') }" mulSelect="true" propertyId="fdElement.fdHideRange.fdOtherIds" propertyName="fdElement.fdHideRange.fdOtherNames" orgType="ORG_TYPE_ORGORDEPT|ORG_TYPE_PERSON" style="width:97%;height:90px;" required="true"></xform:address>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgEco.admin"/>
				</td>
				<td width=85% colspan="3">
					<xform:address textarea="true" mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" orgType="ORG_TYPE_POSTORPERSON" style="width:97%;height:90px;" ></xform:address>
					<div class="description_txt">
						<bean:message bundle="sys-organization" key="sysOrgEco.admin.desc"/>
					</div>
				</td>
			</tr>
		</table>

	</center>
	<html:hidden property="method_GET"/>
	<html:hidden property="fdId"/>
	<html:hidden property="fdElement.fdId"/>
	<html:hidden property="fdElement.fdRange.fdId"/>
	<html:hidden property="fdElement.fdHideRange.fdId"/>
</html:form>
<script>
	Com_IncludeFile("dialog.js");
</script>
<script src="${LUI_ContextPath}/sys/organization/resource/js/sysOrgExternal_dialog.js"></script>
<script language="JavaScript">
	Com_IncludeFile("data.js");
	var _validation = $KMSSValidation(document.forms['sysOrgElementExternalForm']);

	LUI.ready(function(){
		changeReviewRange();
		changeReviewHideRange();
	});

	var NameValidators = {
		'uniqueName' : {
			error : "${ lfn:message('sys-organization:sysOrgEco.org.name.uniqueName') }",
			test : function (value) {
				var fdId = document.getElementsByName("fdId")[0].value;
				var result = checkNameUnique("sysOrgElementService",value,fdId,"unique",'');
				if (!result)
					return false;
				return true;
			}
		},
		'invalidName': {
			error : "${ lfn:message('sys-organization:sysOrgEco.org.name.invalidName') }",
			test  : function(value) {
				if (NameValidators["fdName"] && (NameValidators["fdName"]==value)){
					return true;
				}
				NameValidators["fdName"]=null;
				var fdId = document.getElementsByName("fdId")[0].value;
				var result = checkNameUnique("sysOrgElementService",value,fdId,"invalid",'');
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
		},
		'checkDetpProp': {
			error: "组织扩展属性不能为空！",
			test: function(value) {
				// 检查组织属性
				return window.fdDeptProps.length > 0;
			}
		},
		'checkPersonProp': {
			error: "人员扩展属性不能为空！",
			test: function(value) {
				// 检查人员属性
				return window.fdPersonProps.length > 0;
			}
		}
	};
	_validation.addValidators(NameValidators);

	// 编号校验
	_validation.addValidator(
			'uniqueNo',
			"<bean:message key='organization.error.fdNo.mustUnique' bundle='sys-organization' />",
			function(v, e, o) {
				if (v.length < 1)
					return true;
				var fdId = document.getElementsByName("fdId")[0].value,
						fdName = document.getElementsByName("fdElement.fdName")[0].value,
						fdNo = document.getElementsByName("fdElement.fdNo")[0].value;
				return checkNameUnique("sysOrgElementService",fdName, fdId,"invalid",fdNo);
			});

	//校验名称是否唯一
	function checkNameUnique(bean, fdName,fdId,checkType,fdNo) {
		var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName="
				+ bean + "&fdName=" + fdName+"&fdId="+fdId+"&checkType="+checkType+"&fdOrgType=1&date="+new Date()+"&fdNo="+fdNo);
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

	function changeReviewRange(value) {
		var fdIsOpenLimit = value || $(':hidden[name="fdElement.fdRange.fdIsOpenLimit"]').val();
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

	function changeReviewHideRange(value) {
		var fdIsOpenLimit = value || $(':hidden[name="fdElement.fdHideRange.fdIsOpenLimit"]').val();
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
		var fdViewType = value || $(':radio[name="fdElement.fdHideRange.fdViewType"]:checked').val();
		if (fdViewType == undefined || fdViewType == null || fdViewType == '') {
			hideAndDisabled('otherReviewHide');
			return;
		}

		if (fdViewType == 1) {
			showAndAbled('otherReviewHide');
			// 解决地址本不能校验输入值的问题
			$(".paddingLeft .inputselectmul textarea[name='fdElement.fdHideRange.fdOtherNames']").hide();
			$(".paddingLeft div.mf_container").css("display", "inline-block");
		} else {
			hideAndDisabled('otherReviewHide');
		}
	}

	function changeReviewType(value) {
		var fdViewType = value || $(':radio[name="fdElement.fdRange.fdViewType"]:checked').val();
		if (fdViewType == undefined || fdViewType == null || fdViewType == '') {
			hideAndDisabled('subTypeReview');
			hideAndDisabled('otherReview');
			return;
		}

		if (fdViewType == 2) {
			showAndAbled('subTypeReview');
			changeSubType();
			// 设置默认选择
			var _fdViewSubType = $(':hidden[name="fdElement.fdRange.fdViewSubType"]').val();
			if (_fdViewSubType == '') {
				$($(':checkbox[name="_fdElement.fdRange.fdViewSubType"]')[0]).attr("checked",true);
				$(':hidden[name="fdElement.fdRange.fdViewSubType"]').val("1");
			}
		} else {
			hideAndDisabled('subTypeReview');
			hideAndDisabled('otherReview');
		}
	}

	function changeSubType(value) {
		var fdViewSubType = value || $(':hidden[name="fdElement.fdRange.fdViewSubType"]').val();
		if (fdViewSubType == undefined || fdViewSubType == null || fdViewSubType == '') {
			hideAndDisabled('otherReview');
			return;
		}

		if (fdViewSubType.indexOf('2') != -1) {
			showAndAbled('otherReview');
			// 解决地址本不能校验输入值的问题
			$(".paddingLeft .inputselectmul textarea[name='fdElement.fdRange.fdOtherNames']").hide();
			$(".paddingLeft div.mf_container").css("display", "inline-block");
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
<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
<c:if test="${frameShowTop=='yes' }">
	<ui:top id="top"></ui:top>
	<kmss:ifModuleExist path="/sys/help">
		<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
	</kmss:ifModuleExist>
</c:if>
<%@ include file="/resource/jsp/edit_down.jsp"%>