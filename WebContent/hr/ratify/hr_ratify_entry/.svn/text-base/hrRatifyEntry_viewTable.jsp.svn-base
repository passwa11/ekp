<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:if test="${param.type eq 'history' }">
	<table class="tb_normal" width="100%"
		id="TABLE_DocList_fdHistory_Form" align="center" tbdraggable="true">
		<tr align="center" class="tr_normal_title"  >
			<td style="width: 40px;" class="bgColorTd">
				${lfn:message('page.serial')}</td>
			<td class="bgColorTd">
				${lfn:message('hr-ratify:hrRatifyHistory.fdName')}</td>
			<td class="bgColorTd">
				${lfn:message('hr-ratify:hrRatifyHistory.fdPost')}</td>
			<td class="bgColorTd">
				${lfn:message('hr-ratify:hrRatifyHistory.fdStartDate')}</td>
			<td class="bgColorTd">
				${lfn:message('hr-ratify:hrRatifyHistory.fdEndDate')}</td>
			<td class="bgColorTd">
				${lfn:message('hr-ratify:hrRatifyHistory.fdDesc')}</td>
			<td class="bgColorTd">
				${lfn:message('hr-ratify:hrRatifyHistory.fdLeaveReason')}</td>
		</tr>
		<c:forEach items="${hrRatifyEntryForm.fdHistory_Form}"
			var="fdHistory_FormItem" varStatus="vstatus">
			<tr KMSS_IsContentRow="1" class="fdItem">
				<td align="center" >
					<span id="fdOOrderHistory!{index}">${vstatus.index+1}</span>
				</td>
				<td align="center">
					<%-- 公司名称--%> <input type="hidden"
					name="fdHistory_Form[${vstatus.index}].fdId"
					value="${fdHistory_FormItem.fdId}" />
					<div id="_xform_fdHistory_Form[${vstatus.index}].fdName"
						_xform_type="text">
						<xform:text
							property="fdHistory_Form[${vstatus.index}].fdName"
							showStatus="view"
							subject="${lfn:message('hr-ratify:hrRatifyHistory.fdName')}"
							style="width:95%;" />
					</div>
				</td>
				<td align="center">
					<%-- 职位--%>
					<div id="_xform_fdHistory_Form[${vstatus.index}].fdPost"
						_xform_type="text">
						<xform:text
							property="fdHistory_Form[${vstatus.index}].fdPost"
							showStatus="view"
							subject="${lfn:message('hr-ratify:hrRatifyHistory.fdPost')}"
							style="width:95%;" />
					</div>
				</td>
				<td align="center">
					<%-- 开始日期--%>
					<div
						id="_xform_fdHistory_Form[${vstatus.index}].fdStartDate"
						_xform_type="datetime">
						<xform:datetime
							property="fdHistory_Form[${vstatus.index}].fdStartDate"
							showStatus="view" dateTimeType="date"
							style="width:95%;" />
					</div>
				</td>
				<td align="center">
					<%-- 结束日期--%>
					<div id="_xform_fdHistory_Form[${vstatus.index}].fdEndDate"
						_xform_type="datetime">
						<xform:datetime
							property="fdHistory_Form[${vstatus.index}].fdEndDate"
							showStatus="view" dateTimeType="date"
							style="width:95%;" />
					</div>
				</td>
				<td align="center">
					<%-- 工作描述 --%>
					<div id="_xform_fdHistory_Form[${vstatus.index}].fdDesc"
						_xform_type="text">
						<xform:text
							property="fdHistory_Form[${vstatus.index}].fdDesc"
							showStatus="view" style="width:95%;" />
					</div>
				</td>
				<td align="center">
					<%-- 离职原因 --%>
					<div id="_xform_fdHistory_Form[${vstatus.index}].fdLeaveReason"
						_xform_type="text">
						<xform:text
							property="fdHistory_Form[${vstatus.index}].fdLeaveReason"
							showStatus="view" style="width:95%;" />
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<c:if test="${param.type eq 'eduExp' }">
	<table class="tb_normal" width="100%"
		id="TABLE_DocList_fdEducations_Form" align="center"
		tbdraggable="true">

		<tr align="center" class="tr_normal_title">
			<td style="width: 40px;">${lfn:message('page.serial')}</td>
			<td width="30%">${lfn:message('hr-ratify:hrRatifyEduExp.fdName')}</td>
			<td>${lfn:message('hr-ratify:hrRatifyEduExp.fdMajor')}
			</td>
			<td width="15%">
				${lfn:message('hr-ratify:hrRatifyEduExp.fdAcademic')}</td>
			<td>
				${lfn:message('hr-ratify:hrRatifyEduExp.fdEntranceDate')}</td>
			<td>
				${lfn:message('hr-ratify:hrRatifyEduExp.fdGraduationDate')}
			</td>
			<td>
				${lfn:message('hr-ratify:hrRatifyEduExp.fdRemark')}
			</td>
		</tr>
		<c:forEach items="${hrRatifyEntryForm.fdEducations_Form}"
			var="fdEducations_FormItem" varStatus="vstatus">
			<tr KMSS_IsContentRow="1" class="fdItem">
				<td class="fdItem" align="center"
					>
					<span id="fdOOrderEducations!{index}">${vstatus.index+1}</span>
				</td>
				<td align="center" >
					<%-- 学校名称--%> <input type="hidden"
					name="fdEducations_Form[${vstatus.index}].fdId"
					value="${fdEducations_FormItem.fdId}" />
					<div id="_xform_fdEducations_Form[${vstatus.index}].fdName"
						_xform_type="text">
						<xform:text
							property="fdEducations_Form[${vstatus.index}].fdName"
							showStatus="view" 
							subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdName')}"
							style="width:95%;" />
					</div>
				</td>
				<td align="center" >
					<%-- 专业名称--%>
					<div id="_xform_fdEducations_Form[${vstatus.index}].fdMajor"
						_xform_type="text">
						<xform:text
							property="fdEducations_Form[${vstatus.index}].fdMajor"
							showStatus="view"
							subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdMajor')}"
							style="width:95%;" />
					</div>
				</td>
				<td align="center" >
					<%-- 学位--%>
					<div
						id="_xform_fdEducations_Form[${vstatus.index}].fdAcadeRecordName"
						_xform_type="text">
						<xform:text
							property="fdEducations_Form[${vstatus.index}].fdAcadeRecordName"
							showStatus="view"
							subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdAcadeRecord')}"
							style="width:95%;" />
					</div>
				</td>
				<td align="center" >
					<%-- 入学日期--%>
					<div
						id="_xform_fdEducations_Form[${vstatus.index}].fdEntranceDate"
						_xform_type="datetime">
						<xform:datetime
							property="fdEducations_Form[${vstatus.index}].fdEntranceDate"
							showStatus="view" dateTimeType="date" style="width:95%;" />
					</div>
				</td>
				<td align="center" >
					<%-- 毕业日期--%>
					<div
						id="_xform_fdEducations_Form[${vstatus.index}].fdGraduationDate"
						_xform_type="datetime">
						<xform:datetime
							property="fdEducations_Form[${vstatus.index}].fdGraduationDate"
							showStatus="view" dateTimeType="date" style="width:95%;" />
					</div>
				</td>
				<td align="center">
					<%-- 备注 --%>
					<div
						id="_xform_fdEducations_Form[${vstatus.index}].fdRemark"
						_xform_type="text">
						<xform:text
							property="fdEducations_Form[${vstatus.index}].fdRemark"
							showStatus="view" style="width:95%;" />
					</div>
				</td>
			</tr>
		</c:forEach>	
	</table>
</c:if>
<c:if test="${param.type eq 'certifi' }">
	<table class="tb_normal" width="100%"
		id="TABLE_DocList_fdCertificate_Form" align="center"
		tbdraggable="true">

		<tr align="center" class="tr_normal_title">
			<td style="width: 40px;">${lfn:message('page.serial')}</td>
			<td>${lfn:message('hr-ratify:hrRatifyCertifi.fdName')}
			</td>
			<td>
				${lfn:message('hr-ratify:hrRatifyCertifi.fdIssuingUnit')}</td>
			<td>
				${lfn:message('hr-ratify:hrRatifyCertifi.fdIssueDate')}</td>
			<td>
				${lfn:message('hr-ratify:hrRatifyCertifi.fdInvalidDate')}</td>
		</tr>
		<c:forEach items="${hrRatifyEntryForm.fdCertificate_Form}"
			var="fdCertificate_FormItem" varStatus="vstatus">
			<tr KMSS_IsContentRow="1" class="fdItem">
				<xform:text
						property="fdCertificate_Form[${vstatus.index}].fdRemark"
						showStatus="noShow" />
				<xform:text property="fdCertificate_Form[${vstatus.index}].fdIssuingUnit" showStatus="noShow" />
				<xform:text property="fdCertificate_Form[${vstatus.index}].fdName" showStatus="noShow" />
				<xform:text property="fdCertificate_Form[${vstatus.index}].fdIssueDate" showStatus="noShow" />
				<xform:text property="fdCertificate_Form[${vstatus.index}].fdInvalidDate" showStatus="noShow" />
				<td align="center">
					<span id="fdOOrderCertificate!{index}">${vstatus.index+1}</span>
				</td>
				<td align="center">
					<%-- 证书名称--%> <input type="hidden"
					name="fdCertificate_Form[${vstatus.index}].fdId"
					value="${fdCertificate_FormItem.fdId}" />
					<div id="_xform_fdCertificate_Form[${vstatus.index}].fdName"
						_xform_type="text">
						<xform:text
							property="fdCertificate_Form[${vstatus.index}].fdName"
							showStatus="view" 
							subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdName')}"
							style="width:95%;" />
					</div>
				</td>
				<td align="center">
					<%-- 颁发单位--%>
					<div
						id="_xform_fdCertificate_Form[${vstatus.index}].fdIssuingUnit"
						_xform_type="text">
						<xform:text
							property="fdCertificate_Form[${vstatus.index}].fdIssuingUnit"
							showStatus="view"
							subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdIssuingUnit')}"
							style="width:95%;" />
					</div>
				</td>
				<td align="center">
					<%-- 颁发日期--%>
					<div
						id="_xform_fdCertificate_Form[${vstatus.index}].fdIssueDate"
						_xform_type="datetime">
						<xform:datetime
							property="fdCertificate_Form[${vstatus.index}].fdIssueDate"
							showStatus="view" dateTimeType="date" style="width:95%;" />
					</div> 
				</td>
				<td align="center">
					<%-- 失效日期--%>
					<div
						id="_xform_fdCertificate_Form[${vstatus.index}].fdInvalidDate"
						_xform_type="datetime">
						<xform:datetime
							property="fdCertificate_Form[${vstatus.index}].fdInvalidDate"
							showStatus="view" dateTimeType="date" style="width:95%;" />
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<c:if test="${param.type eq 'rewPuni' }">
	<table class="tb_normal" width="100%"
		id="TABLE_DocList_fdRewardsPunishments_Form" align="center"
		tbdraggable="true">

		<tr align="center" class="tr_normal_title">
			<td style="width: 40px;">${lfn:message('page.serial')}</td>
			<td>${lfn:message('hr-ratify:hrRatifyRewPuni.fdName')}
			</td>
			<td>${lfn:message('hr-ratify:hrRatifyRewPuni.fdDate')}
			</td>
			<td>
				${lfn:message('hr-ratify:hrRatifyRewPuni.fdRemark')}
			</td>
		</tr>
		<c:forEach
			items="${hrRatifyEntryForm.fdRewardsPunishments_Form}"
			var="fdRewardsPunishments_FormItem" varStatus="vstatus">
			<tr KMSS_IsContentRow="1" class="fdItem">
				<td class="fdItem" align="center">
					<span id="fdOOrderRewardsPunishments!{index}">${vstatus.index+1}</span>
				</td>
				<td  align="center" >
					<%-- 奖惩名称--%> <input type="hidden"
					name="fdRewardsPunishments_Form[${vstatus.index}].fdId"
					value="${fdRewardsPunishments_FormItem.fdId}" />
					<div
						id="_xform_fdRewardsPunishments_Form[${vstatus.index}].fdName"
						_xform_type="text">
						<xform:text
							property="fdRewardsPunishments_Form[${vstatus.index}].fdName"
							showStatus="view"
							subject="${lfn:message('hr-ratify:hrRatifyRewPuni.fdName')}"
							style="width:95%;" />
					</div>
				</td>
				<td  align="center" >
					<%-- 奖惩日期--%>
					<div
						id="_xform_fdRewardsPunishments_Form[${vstatus.index}].fdDate"
						_xform_type="datetime">
						<xform:datetime
							property="fdRewardsPunishments_Form[${vstatus.index}].fdDate"
							showStatus="view" dateTimeType="date" style="width:95%;" />
					</div>
				</td>
				<td align="center">
					<%-- 备注 --%>
					<div
						id="_xform_fdRewardsPunishments_Form[${vstatus.index}].fdRemark"
						_xform_type="text">
						<xform:text
							property="fdRewardsPunishments_Form[${vstatus.index}].fdRemark"
							showStatus="view" style="width:95%;" />
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>