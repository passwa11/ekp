<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
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
		var fdIsOpenLimit = "${sysOrgDeptForm.fdHideRange.fdIsOpenLimit}";
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
		var fdViewType = "${sysOrgDeptForm.fdHideRange.fdViewType}";
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
		var fdIsOpenLimit = "${sysOrgDeptForm.fdRange.fdIsOpenLimit}";
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
		var fdViewType = "${sysOrgDeptForm.fdRange.fdViewType}";
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
		var fdViewSubType = "${sysOrgDeptForm.fdRange.fdViewSubType}";
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
	.paddingLeft {padding-left:14px}
	.hideRangeTip {color: red;padding: 10px 0;}
</style>
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=edit&fdId=${sysOrgDeptForm.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message key="button.edit"/>" onClick="Com_OpenWindow('sysOrgElementExternalDept.do?method=edit&fdId=${sysOrgDeptForm.fdId}','_self');">
	</kmss:auth>
	<c:if test="${'true' eq sysOrgDeptForm.fdIsAvailable}">
		<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=invalidated&fdId=${sysOrgDeptForm.fdId}" requestMethod="GET">
			<input type="button" value="<bean:message bundle="sys-organization" key="organization.invalidated" />" onClick="if(!confirm_invalidated())return;Com_OpenWindow('sysOrgElementExternalDept.do?method=invalidated&fdId=${sysOrgDeptForm.fdId}','_self');">
		</kmss:auth>
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<div class="txttitle"><bean:message bundle="sys-organization" key="table.sysOrgElementExternal"/></div>
<center>
	<c:set var="showLog" value="false" />
	<kmss:auth requestURL="/sys/organization/sys_log_organization/index.jsp?fdId=${sysOrgDeptForm.fdId}" requestMethod="GET">
		<c:set var="showLog" value="true" />
	</kmss:auth>
	<c:if test="${'true' eq showLog}">
	<table id="Label_Tabel" width="95%">
		<tr LKS_LabelName="<bean:message bundle="sys-organization" key="sysOrgElement.baseInfo"/>">
			<td>
				</c:if>
				<table class="tb_normal" width=95%>
					<tr>
						<!-- 机构名称  -->
						<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgElement.fdName"/></td>
						<td width=35% ><pre><c:out value="${sysOrgDeptForm.fdName}"/></pre></td>
						<!-- 编号 -->
						<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgElement.fdNo"/></td>
						<td width=35%>${sysOrgDeptForm.fdNo}</td>
					</tr>
					<tr>
						<!-- 上级组织 -->
						<td width=15% class="td_normal_title">
							<bean:message bundle="sys-organization" key="sysOrgElementExternal.parent"/>
						</td>
						<td width=35%>
							<c:out value="${sysOrgDeptForm.fdParentName}"/>
						</td>
						<!-- 排序号 -->
						<td class="td_normal_title" width=15%><bean:message bundle="sys-organization" key="sysOrgElement.fdOrder"/></td>
						<td width="35%">
							${sysOrgDeptForm.fdOrder}
						</td>
					</tr>
					<tr>
						<!-- 负责人 -->
						<td width=15% class="td_normal_title">
							<bean:message bundle="sys-organization" key="sysOrgElementExternal.authElementAdmin"/>
						</td>
						<td width=35%>
							<c:out value="${sysOrgDeptForm.authElementAdminNames}"/>
						</td>
						<!-- 是否有效 -->
						<td width=15% class="td_normal_title">
							<bean:message bundle="sys-organization" key="sysOrgDept.fdIsAvailable"/>
						</td>
						<td width="35%">
							<sunbor:enumsShow value="${sysOrgDeptForm.fdIsAvailable}" enumsType="sys_org_available_result" />
						</td>
					</tr>
					<%-- 扩展属性 --%>
					<c:import url="/sys/organization/sys_org_element_external/sysOrgExternal_common_ext_props_view.jsp" charEncoding="UTF-8">
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
										<ui:switch property="fdRange.fdIsOpenLimit" enabledText="${ lfn:message('sys-organization:sysOrgEco.view') }" disabledText="${ lfn:message('sys-organization:sysOrgEco.view.not') }" showType="show"></ui:switch>
									</td>
								</tr>
								<tr id="typeReview" style="display:none">
									<td>
										<div>
											<xform:radio property="fdRange.fdViewType" value="${sysOrgDeptForm.fdRange.fdViewType}" title="${ lfn:message('sys-attend:sysAttendCategory.fdOvtReviewType') }" alignment="V">
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
											<xform:checkbox property="fdRange.fdViewSubType" value="${sysOrgDeptForm.fdRange.fdViewSubType}" subject="${ lfn:message('sys-organization:sysEco.view.subType') }" isArrayValue="false" alignment="V">
												<xform:enumsDataSource enumsType="sys_org_eco_sub_view" />
											</xform:checkbox>
										</div>
									</td>
								</tr>
								<tr id="otherReview" style="display:none">
									<td>
										<div class="paddingLeft">
											<pre><c:out value="${sysOrgDeptForm.fdRange.fdOtherNames}"/></pre>
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
										<ui:switch property="fdHideRange.fdIsOpenLimit" enabledText="${ lfn:message('sys-organization:sysOrgEco.hide') }${ lfn:message('sys-organization:sysOrgEco.hide.type.eco') }" disabledText="${ lfn:message('sys-organization:sysOrgEco.hide.not') }${ lfn:message('sys-organization:sysOrgEco.hide.type.eco') }" showType="show"></ui:switch>
									</td>
								</tr>
								<tr id="typeReviewHide" style="display:none">
									<td>
										<c:if test="${not empty hideRangeTip}">
											<div class="hideRangeTip">${hideRangeTip}</div>
										</c:if>
										<div class="typeDiv">
											<xform:radio property="fdHideRange.fdViewType" value="${sysOrgDeptForm.fdHideRange.fdViewType}" alignment="V">
												<xform:simpleDataSource value="0"><bean:message bundle="sys-organization" key="sysOrgEco.hide.type.all.eco"/></xform:simpleDataSource>
												<xform:simpleDataSource value="1"><bean:message bundle="sys-organization" key="sysOrgEco.hide.type.special.eco"/></xform:simpleDataSource>
											</xform:radio>
										</div>
									</td>
								</tr>
								<tr id="otherReviewHide" style="display:none">
									<td>
										<div class="paddingLeft">
											<pre><c:out value="${sysOrgDeptForm.fdHideRange.fdOtherNames}"/></pre>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width=15% class="td_normal_title">
							<bean:message bundle="sys-organization" key="sysOrgElementExternal.ding.url"/>
						</td>
						<td width=65% colspan="2" style="word-break: break-all;">
							<span id="fdInviteUrl"><c:out value="${sysOrgDeptForm.fdRange.fdInviteUrl}"/></span>
						</td>
						<td style="text-align: center;">
							<div id="__qrcode"></div>
						</td>
					</tr>
				</table>
				<c:if test="${'true' eq showLog}">
			</td>
		</tr>
		<tr LKS_LabelName="<bean:message bundle="sys-organization" key="sysOrgElement.logInfo"/>">
			<td>
				<iframe name="IFRAME" src='<c:url value="/sys/organization/sys_log_organization/index.jsp?fdId=${sysOrgDeptForm.fdId}" />' frameBorder=0 width="100%"> </iframe>
			</td>
		</tr>
	</table>
	</c:if>
	<script>
		window.onload=function() {
			seajs.use(["lui/jquery", "lui/qrcode"], function($, qrcode) {
				var val = $("#fdInviteUrl").text();
				if(val.length > 0) {
					qrcode.Qrcode({
						text : val,
						element : $("#__qrcode")[0],
						render : 'canvas',
						width : 150,
						height : 150
					});
				}
			});
		}
	</script>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>