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

	function changeReviewRange() {
		var fdIsOpenLimit = "${sysOrgElementExternalForm.fdElement.fdRange.fdIsOpenLimit}";
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
		var fdViewType = "${sysOrgElementExternalForm.fdElement.fdRange.fdViewType}";
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

	function changeReviewHideRange() {
		var fdIsOpenLimit = "${sysOrgElementExternalForm.fdElement.fdHideRange.fdIsOpenLimit}";
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
		var fdViewType = "${sysOrgElementExternalForm.fdElement.fdHideRange.fdViewType}";
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

	function changeSubType() {
		var fdViewSubType = "${sysOrgElementExternalForm.fdElement.fdRange.fdViewSubType}";
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
	.paddingLeft {padding-left:14px}
</style>
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=edit&fdId=${sysOrgElementExternalForm.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message key="button.edit"/>" onClick="Com_OpenWindow('sysOrgElementExternal.do?method=edit&fdId=${sysOrgElementExternalForm.fdId}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=invalidated&fdId=${sysOrgElementExternalForm.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message bundle="sys-organization" key="organization.invalidated" />" onClick="if(!confirm_invalidated())return;Com_OpenWindow('sysOrgElementExternal.do?method=invalidated&fdId=${sysOrgElementExternalForm.fdId}','_self');">
	</kmss:auth>
	<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<div class="txttitle"><bean:message bundle="sys-organization" key="sysOrgEco.type"/></div>
<center>
	<table id="tb_normal" width="95%">
		<table class="tb_normal" width=95%>
			<tr>
				<!-- 机构名称  -->
				<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgEco.fdName"/></td>
				<td width=35% ><pre>${sysOrgElementExternalForm.fdElement.fdName}</pre></td>
				<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgElement.fdNo"/></td>
				<td width=35%>${sysOrgElementExternalForm.fdElement.fdNo}</td>
			</tr>
			<tr>
				<!-- 排序号 -->
				<td class="td_normal_title" width=15%><bean:message bundle="sys-organization" key="sysOrgElement.fdOrder"/></td>
				<td width="35%">
					${sysOrgElementExternalForm.fdElement.fdOrder}
				</td>
				<td class="td_normal_title" width=15%><bean:message bundle="sys-organization" key="sysOrgEco.is.available"/></td>
				<td width="35%">
					<c:if test="${'true' eq sysOrgElementExternalForm.fdElement.fdIsAvailable}">
						<bean:message bundle="sys-organization" key="sys.org.available.result.true"/>
					</c:if>
					<c:if test="${'false' eq sysOrgElementExternalForm.fdElement.fdIsAvailable}">
						<bean:message bundle="sys-organization" key="sys.org.available.result.false"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<!-- 管理员  -->
				<td class="td_normal_title" width=15%><bean:message bundle="sys-organization" key="sysOrgElement.authElementAdmins"/></td>
				<td width="85%" colspan="3">
					<pre><c:out value="${sysOrgElementExternalForm.fdElement.authElementAdminNames}"/></pre>
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
						</tr>
						<c:forEach items="${sysOrgElementExternalForm.fdDeptProps}" var="prop">
							<tr class="add_prop" style="text-align: center;">
								<td>
										${prop.fdName}
								</td>
								<td>
									<c:choose>
										<c:when test="${prop.fdDisplayType == 'text'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.text"/>
										</c:when>
										<c:when test="${prop.fdDisplayType == 'textarea'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.textarea"/>
										</c:when>
										<c:when test="${prop.fdDisplayType == 'radio'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.radio"/>
										</c:when>
										<c:when test="${prop.fdDisplayType == 'checkbox'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.checkbox"/>
										</c:when>
										<c:when test="${prop.fdDisplayType == 'select'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.select"/>
										</c:when>
										<c:when test="${prop.fdDisplayType == 'datetime'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.datetime"/>
										</c:when>
										<c:when test="${prop.fdDisplayType == 'date'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.date"/>
										</c:when>
										<c:when test="${prop.fdDisplayType == 'time'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.time"/>
										</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${prop.fdRequired == 'true'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.required.yes"/>
										</c:when>
										<c:otherwise>
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.required.no"/>
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${prop.fdShowList == 'true'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.required.yes"/>
										</c:when>
										<c:otherwise>
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.required.no"/>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</table>
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
						</tr>
						<c:forEach items="${sysOrgElementExternalForm.fdPersonProps}" var="prop">
							<tr class="add_prop" style="text-align: center;">
								<td>
										${prop.fdName}
								</td>
								<td>
									<c:choose>
										<c:when test="${prop.fdDisplayType == 'text'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.text"/>
										</c:when>
										<c:when test="${prop.fdDisplayType == 'textarea'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.textarea"/>
										</c:when>
										<c:when test="${prop.fdDisplayType == 'radio'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.radio"/>
										</c:when>
										<c:when test="${prop.fdDisplayType == 'checkbox'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.checkbox"/>
										</c:when>
										<c:when test="${prop.fdDisplayType == 'select'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.select"/>
										</c:when>
										<c:when test="${prop.fdDisplayType == 'datetime'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.datetime"/>
										</c:when>
										<c:when test="${prop.fdDisplayType == 'date'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.date"/>
										</c:when>
										<c:when test="${prop.fdDisplayType == 'time'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.type.time"/>
										</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${prop.fdRequired == 'true'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.required.yes"/>
										</c:when>
										<c:otherwise>
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.required.no"/>
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${prop.fdShowList == 'true'}">
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.required.yes"/>
										</c:when>
										<c:otherwise>
											<bean:message bundle="sys-organization" key="sysOrgEco.prop.required.no"/>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</table>
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
								<ui:switch property="fdElement.fdRange.fdIsOpenLimit" enabledText="${ lfn:message('sys-organization:sysOrgEco.view') }" disabledText="${ lfn:message('sys-organization:sysOrgEco.view.not') }" showType="show"></ui:switch>
							</td>
						</tr>
						<tr id="typeReview" style="display:none">
							<td>
								<div id="typeDiv">
									<xform:radio property="fdElement.fdRange.fdViewType" value="${sysOrgElementExternalForm.fdElement.fdRange.fdViewType}" title="${ lfn:message('sys-attend:sysAttendCategory.fdOvtReviewType') }" alignment="V">
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
									<xform:checkbox property="fdElement.fdRange.fdViewSubType" value="${sysOrgElementExternalForm.fdElement.fdRange.fdViewSubType}" subject="${ lfn:message('sys-organization:sysEco.view.subType') }" isArrayValue="false" alignment="V">
										<xform:enumsDataSource enumsType="sys_org_eco_sub_view" />
									</xform:checkbox>
								</div>
							</td>
						</tr>
						<tr id="otherReview" style="display:none">
							<td>
								<div class="paddingLeft">
									<pre><c:out value="${sysOrgElementExternalForm.fdElement.fdRange.fdOtherNames}"/></pre>
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
                                <ui:switch property="fdElement.fdHideRange.fdIsOpenLimit" enabledText="${ lfn:message('sys-organization:sysOrgEco.hide') }${ lfn:message('sys-organization:sysOrgEco.hide.type.eco') }" disabledText="${ lfn:message('sys-organization:sysOrgEco.hide.not') }${ lfn:message('sys-organization:sysOrgEco.hide.type.eco') }" showType="show"></ui:switch>
                            </td>
                        </tr>
                        <tr id="typeReviewHide" style="display:none">
                            <td>
                                <div id="typeDiv">
                                    <xform:radio property="fdElement.fdHideRange.fdViewType" value="${sysOrgElementExternalForm.fdElement.fdHideRange.fdViewType}" alignment="V">
                                        <xform:simpleDataSource value="0"><bean:message bundle="sys-organization" key="sysOrgEco.hide.type.all.eco"/></xform:simpleDataSource>
                                        <xform:simpleDataSource value="1"><bean:message bundle="sys-organization" key="sysOrgEco.hide.type.special.eco"/></xform:simpleDataSource>
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr id="otherReviewHide" style="display:none">
                            <td>
                                <div class="paddingLeft">
                                    <pre><c:out value="${sysOrgElementExternalForm.fdElement.fdHideRange.fdOtherNames}"/></pre>
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
					<pre><c:out value="${sysOrgElementExternalForm.authReaderNames}"/></pre>
				</td>
			</tr>
		</table>
	</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>