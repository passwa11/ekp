<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<style type="text/css">
.lui_paragraph_title {
	font-size: 15px;
	color: #15a4fa;
	padding: 15px 0px 5px 0px;
}

.lui_paragraph_title span {
	display: inline-block;
	margin: -2px 5px 0px 0px;
}

.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
	border: 0px;
	color: #868686
}
</style>
<script type="text/javascript">
	if ("${thirdDingLeavelogForm.fdEkpUserid}" != "") {
		window.document.title = "${thirdDingLeavelogForm.docSubject} -  ${ lfn:message('third-ding:table.thirdDingLeavelog') }";
	}
	Com_IncludeFile("swf_attachment.js",
			"${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">
	<input type="button" value="${lfn:message('button.close')}"
		onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-ding:third.ding.buss.data.overtime') }</p>
<center>

	<div style="width: 95%;">
		<table class="tb_normal" width="100%">
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('third-ding:thirdDingLeavelog.docSubject')}</td>
				<td colspan="3">
					<%-- 流程主题--%>
					<div id="_xform_docSubject" _xform_type="text">
						<a href="${thirdDingLeavelogForm.fdJumpUrl}" class="com_btn_link"
							style="text-decoration: none;" target="_blank"> <xform:text
								property="docSubject" showStatus="view" style="width:95%;" />
						</a>
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('third-ding:thirdDingLeavelog.docCreator')}</td>
				<td width="35%">
					<%-- 申请人--%>
					<div id="_xform_docCreatorId" _xform_type="address">
						<ui:person personId="${thirdDingLeavelogForm.fdEkpUserid}"
							personName="${thirdDingLeavelogForm.fdEkpUsername}" />
					</div>
				</td>
				<td class="td_normal_title" width="15%">
					${lfn:message('third-ding:thirdDingLeavelog.fdUserid')}</td>
				<td width="35%">
					<%-- 钉钉用户ID--%>
					<div id="_xform_fdUserid" _xform_type="text">
						<xform:text property="fdUserid" showStatus="view"
							style="width:95%;" />
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('third-ding:thirdDingLeavelog.fdFromTime')}</td>
				<td width="35%">
					<%-- 开始时间--%>
					<div id="_xform_fdFromTime" _xform_type="text">
						<xform:text property="fdFromTime" showStatus="view"
							style="width:95%;" />
					</div>
				</td>
				<td class="td_normal_title" width="15%">
					${lfn:message('third-ding:thirdDingLeavelog.fdToTime')}</td>
				<td width="35%">
					<%-- 结束时间--%>
					<div id="_xform_fdToTime" _xform_type="text">
						<xform:text property="fdToTime" showStatus="view"
							style="width:95%;" />
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('third-ding:thirdDingLeavelog.docAlterTime')}</td>
				<td width="35%">
					<%-- 同步时间--%>
					<div id="_xform_docAlterTime" _xform_type="datetime">
						<xform:datetime property="docAlterTime" showStatus="view"
							dateTimeType="datetime" style="width:95%;" />
					</div>
				</td>
				<td class="td_normal_title" width="15%">
					${lfn:message('third-ding:thirdDingLeavelog.fdIstrue')}</td>
				<td width="35%">
					<%-- 同步状态--%>
					<div id="_xform_fdIstrue" _xform_type="text">
						<c:if test="${thirdDingLeavelogForm.fdIstrue=='1'}">
							<span style="color: green;"> <bean:message
									bundle="third-ding" key="thirdDingLeavelog.syncSuccess" />
							</span>
						</c:if>
						<c:if test="${thirdDingLeavelogForm.fdIstrue=='0'}">
							<span style="color: red;"> <bean:message
									bundle="third-ding" key="thirdDingLeavelog.syncError" />
							</span>
						</c:if>
						<c:if test="${thirdDingLeavelogForm.fdIstrue=='2'}">
							<bean:message bundle="third-ding"
								key="thirdDingLeavelog.syncCancel" />
						</c:if>
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('third-ding:table.thirdDingOvertime.fdDuration')}</td>
				<td colspan="3">
					<%-- 加班时间--%>
					<div id="_xform_fdDuration" _xform_type="text">
						<xform:text property="fdDuration" showStatus="view"
							style="width:95%;" />
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('third-ding:thirdDingLeavelog.fdReason')}</td>
				<td colspan="3">
					<%-- 同步原因--%>
					<div id="_xform_fdReason" _xform_type="text">
						<xform:text property="fdReason" showStatus="view"
							style="width:95%;" />
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('third-ding:thirdDingLeavelog.fdParams')}</td>
				<td colspan="3">
					<%-- 入参--%>
					<div id="_xform_fdParams" _xform_type="text">
						<xform:text property="fdParams" showStatus="view"
							style="width:95%;" />
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('third-ding:thirdDingLeavelog.fdResult')}</td>
				<td colspan="3">
					<%-- 出参--%>
					<div id="_xform_fdResult" _xform_type="text">
						<xform:text property="fdResult" showStatus="view"
							style="width:95%;" />
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('third-ding:thirdDingLeavelog.fdSyncResultTip')}</td>
				<td colspan="3">
					<table class="tb_normal" width="100%"
						id="TABLE_DocList_fdOvertimeDetail_Form" align="center"
						tbdraggable="true">
						<tr align="center" class="tr_normal_title">
							<td>${lfn:message('third-ding:thirdDingOvertime.fdDate')}</td>
							<td>${lfn:message('third-ding:thirdDingOvertime.fdDuration')}</td>
						</tr>
						<c:forEach items="${thirdDingLeavelogForm.fdLeaveDetail_Form}"
							var="fdOvertimeDetail_FormItem" varStatus="vstatus">
							<tr class="docListTr">
								
								<td class="docList" align="center">
									<%-- 日期 --%>
									<div id="_xform_fdOvertimeDetail_Form[${vstatus.index}].fdDate"
										_xform_type="text">
										<xform:text
											property="fdOvertimeDetail_FormItem.fdDate"
											showStatus="view" style="width:95%;" value="${fdOvertimeDetail_FormItem.fdDate}"/>
									</div>
								</td>
								<td class="docList" align="center">
									<%-- 天数--%> 
									<input type="hidden"
									name="fdOvertimeDetail_Form[${vstatus.index}].fdId"
									value="${fdOvertimeDetail_FormItem.fdId}" />
									<div
										id="_xform_fdOvertimeDetail_Form[${vstatus.index}].fdDuration"
										_xform_type="text">
										<xform:text
											property="fdOvertimeDetail_FormItem.fdDuration"
											showStatus="view" style="width:95%;" value="${fdOvertimeDetail_FormItem.fdDuration}"/>
									</div>
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</div>
</center>
<script>
	var formInitData = {

	};
	console.log(${thirdDingLeavelogForm.fdLeaveDetail_Form});

	function confirmDelete(msg) {
		return confirm('${ lfn:message("page.comfirmDelete") }');
	}

	function openWindowViaDynamicForm(popurl, params, target) {
		var form = document.createElement('form');
		if (form) {
			try {
				target = !target ? '_blank' : target;
				form.style = "display:none;";
				form.method = 'post';
				form.action = popurl;
				form.target = target;
				if (params) {
					for ( var key in params) {
						var v = params[key];
						var vt = typeof v;
						var hdn = document.createElement('input');
						hdn.type = 'hidden';
						hdn.name = key;
						if (vt == 'string' || vt == 'boolean' || vt == 'number') {
							hdn.value = v + '';
						} else {
							if ($.isArray(v)) {
								hdn.value = v.join(';');
							} else {
								hdn.value = toString(v);
							}
						}
						form.appendChild(hdn);
					}
				}
				document.body.appendChild(form);
				form.submit();
			} finally {
				document.body.removeChild(form);
			}
		}
	}

	function doCustomOpt(fdId, optCode) {
		if (!fdId || !optCode) {
			return;
		}

		if (viewOption.customOpts && viewOption.customOpts[optCode]) {
			var param = {
				"List_Selected_Count" : 1
			};
			var argsObject = viewOption.customOpts[optCode];
			if (argsObject.popup == 'true') {
				var popurl = viewOption.contextPath + argsObject.popupUrl
						+ '&fdId=' + fdId;
				for ( var arg in argsObject) {
					param[arg] = argsObject[arg];
				}
				openWindowViaDynamicForm(popurl, param, '_self');
				return;
			}
			var optAction = viewOption.contextPath + viewOption.basePath
					+ '?method=' + optCode + '&fdId=' + fdId;
			Com_OpenWindow(optAction, '_self');
		}
	}
	window.doCustomOpt = doCustomOpt;
	var viewOption = {
		contextPath : '${LUI_ContextPath}',
		basePath : '/third/ding/third_ding_leavelog/thirdDingLeavelog.do',
		customOpts : {

			____fork__ : 0
		}
	};
	Com_IncludeFile("security.js");
	Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp"%>