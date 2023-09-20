<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script>
	function confirm_invalidated(){
		var msg = confirm("<bean:message bundle="sys-organization" key="organization.invalidated.comfirm"/>");
		return msg;
	}
</script>
<script>
	LUI.ready(function(){
		changeReviewRange();
		changeReviewHideRange();
	});

	function confirm_invalidated(){
		var msg = confirm("<bean:message bundle="sys-organization" key="organization.invalidated.comfirm"/>");
		return msg;
	}

	function changeReviewHideRange() {
		var fdIsOpenLimit = "${sysOrgOrgForm.fdHideRange.fdIsOpenLimit}";
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

	function changeReviewHideType() {
		var fdViewType = "${sysOrgOrgForm.fdHideRange.fdViewType}";
		if (fdViewType == undefined || fdViewType == null || fdViewType == '') {
			hideAndDisabled('otherReviewHide');
			return;
		}
		if (fdViewType == 1) {
			showAndAbled('otherReviewHide');
		} else {
			hideAndDisabled('otherReviewHide');
		}
	}

	function changeReviewRange() {
		var fdIsOpenLimit = "${sysOrgOrgForm.fdRange.fdIsOpenLimit}";
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

	function changeReviewType() {
		var fdViewType = "${sysOrgOrgForm.fdRange.fdViewType}";
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

	function changeSubType() {
		var fdViewSubType = "${sysOrgOrgForm.fdRange.fdViewSubType}";
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
		parentDom.show();
	};

	var hideAndDisabled= function(id) {
		var parentDom = $('#' + id);
		if(!parentDom)
			return;
		var childInputs = parentDom.find(':input');
		parentDom.hide();
	};
</script>
<style type="text/css">
	.tb_simple tr td{border:0}
	.typeDiv {padding-left:47px}
	.paddingLeft {padding-left:61px}
</style>
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/organization/sys_org_org/sysOrgOrg.do?method=edit&fdId=${sysOrgOrgForm.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message key="button.edit"/>" onClick="Com_OpenWindow('sysOrgOrg.do?method=edit&fdId=<bean:write name="sysOrgOrgForm" property="fdId" />','_self');">
	</kmss:auth>
	<c:if test="${sysOrgOrgForm.fdIsAvailable}">
		<kmss:auth requestURL="/sys/organization/sys_org_org/sysOrgOrg.do?method=invalidated&fdId=${sysOrgOrgForm.fdId}" requestMethod="GET">
			<input type="button" value="<bean:message bundle="sys-organization" key="organization.invalidated" />" onClick="if(!confirm_invalidated())return;Com_OpenWindow('sysOrgOrg.do?method=invalidated&fdId=<bean:write name="sysOrgOrgForm" property="fdId" />','_self');">
		</kmss:auth>
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<div class="txttitle"><bean:message bundle="sys-organization" key="sysOrgElement.org"/></div>
<center>
	<table id="Label_Tabel" width="95%">
		<tr LKS_LabelName="<bean:message bundle="sys-organization" key="sysOrgElement.baseInfo"/>">
			<td>
				<table class="tb_normal" width=95%>
					<tr>
						<!-- 机构名称  -->
						<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgOrg.fdName"/></td>
						<td width=35% colspan="3"><pre><bean:write name="sysOrgOrgForm" property="fdName"/></pre></td>
					</tr>
					<tr>
						<!-- 上级机构  -->
						<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgOrg.fdParent"/></td>
						<td width=35%>
							<pre><%=com.landray.kmss.sys.organization.util.SysOrgUtil.getFdParentsNameByForm((com.landray.kmss.sys.organization.forms.SysOrgOrgForm)request.getAttribute("sysOrgOrgForm"))%></pre>
						</td>
						<!-- 编号  -->
						<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgOrg.fdNo"/></td>
						<td width=35%><bean:write name="sysOrgOrgForm" property="fdNo"/></td>
					</tr>
					<tr>
						<!-- 机构领导  -->
						<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgOrg.fdThisLeader"/></td>
						<td width=35%><pre><bean:write name="sysOrgOrgForm" property="fdThisLeaderName"/></pre></td>
						<!-- 上级领导  -->
						<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgOrg.fdSuperLeader"/></td>
						<td width=35%><pre><bean:write name="sysOrgOrgForm" property="fdSuperLeaderName"/></pre></td>
					</tr>
					<tr>
						<!-- 关键字  -->
						<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgOrg.fdKeyword"/></td>
						<td width=35%><pre><bean:write name="sysOrgOrgForm" property="fdKeyword"/></pre></td>
						<!-- 排序号  -->
						<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgOrg.fdOrder"/></td>
						<td width=35%><bean:write name="sysOrgOrgForm" property="fdOrder"/></td>
					</tr>
					<tr>
						<!-- 邮件地址  -->
						<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgElement.fdOrgEmail"/></td>
						<td width=35% colspan="3"><pre><bean:write name="sysOrgOrgForm" property="fdOrgEmail"/></pre></td>
					</tr>
					<tr>
						<!-- 是否业务相关  -->
						<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgOrg.fdIsBusiness"/></td>
						<td width=35%><sunbor:enumsShow value="${sysOrgOrgForm.fdIsBusiness}" enumsType="common_yesno" /></td>
						<!-- 是否有效  -->
						<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgOrg.fdIsAvailable"/></td>
						<td width=35%><sunbor:enumsShow value="${sysOrgOrgForm.fdIsAvailable}" enumsType="sys_org_available_result" /></td>
					</tr>
					<tr>
						<!-- 管理员  -->
						<td class="td_normal_title" width=15%><bean:message bundle="sys-organization" key="sysOrgElement.authElementAdmins"/></td>
						<td width="85%" colspan="3">
							<pre><xform:address propertyId="authElementAdminIds" propertyName="authElementAdminNames" mulSelect="true" orgType="ORG_TYPE_POSTORPERSON" style="width:85%" /></pre>
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
										<ui:switch property="fdRange.fdIsOpenLimit" enabledText="${ lfn:message('sys-organization:sysOrgEco.view') }" disabledText="${ lfn:message('sys-organization:sysOrgEco.view.not') }" showType="show"></ui:switch>
									</td>
								</tr>
								<tr id="typeReview" style="display:none">
									<td>
										<div class="typeDiv">
											<xform:radio property="fdRange.fdViewType" value="${sysOrgOrgForm.fdRange.fdViewType}" title="${ lfn:message('sys-attend:sysAttendCategory.fdOvtReviewType') }" alignment="V">
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
											<xform:checkbox property="fdRange.fdViewSubType" value="${sysOrgOrgForm.fdRange.fdViewSubType}" subject="${ lfn:message('sys-organization:sysEco.view.subType') }" isArrayValue="false" alignment="V">
												<xform:enumsDataSource enumsType="sys_org_eco_sub_view" />
											</xform:checkbox>
										</div>
									</td>
								</tr>
								<tr id="otherReview" style="display:none">
									<td>
										<div class="paddingLeft">
											<pre><c:out value="${sysOrgOrgForm.fdRange.fdOtherNames}"/></pre>
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
										<ui:switch property="fdHideRange.fdIsOpenLimit" enabledText="${ lfn:message('sys-organization:sysOrgEco.hide') }${ lfn:message('sys-organization:sysOrgEco.hide.type.in1') }" disabledText="${ lfn:message('sys-organization:sysOrgEco.hide.not') }${ lfn:message('sys-organization:sysOrgEco.hide.type.in1') }" showType="show"></ui:switch>
									</td>
								</tr>
								<tr id="typeReviewHide" style="display:none">
									<td>
										<div class="typeDiv">
											<xform:radio property="fdHideRange.fdViewType" value="${sysOrgOrgForm.fdHideRange.fdViewType}" alignment="V">
												<xform:simpleDataSource value="0"><bean:message bundle="sys-organization" key="sysOrgEco.hide.type.all.in"/></xform:simpleDataSource>
												<xform:simpleDataSource value="1"><bean:message bundle="sys-organization" key="sysOrgEco.hide.type.special.in"/></xform:simpleDataSource>
											</xform:radio>
										</div>
									</td>
								</tr>
								<tr id="otherReviewHide" style="display:none">
									<td>
										<div class="paddingLeft">
											<pre><c:out value="${sysOrgOrgForm.fdHideRange.fdOtherNames}"/></pre>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<!-- 备注  -->
						<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgOrg.fdMemo"/></td>
						<td colspan="3"><pre><kmss:showText value="${sysOrgOrgForm.fdMemo}"/></pre></td>
					</tr>
					<%-- 引入动态属性 --%>
					<c:import url="/sys/property/custom_field/custom_fieldView.jsp" charEncoding="UTF-8" />
				</table>
			</td>
		</tr>
		<tr LKS_LabelName="<bean:message bundle="sys-organization" key="sysOrgElement.logInfo"/>">
			<td>
				<iframe name="IFRAME" src='<c:url value="/sys/organization/sys_log_organization/index.jsp?fdId=${sysOrgOrgForm.fdId}" />' frameBorder=0 width="100%"> </iframe>
			</td>
		</tr>
	</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>