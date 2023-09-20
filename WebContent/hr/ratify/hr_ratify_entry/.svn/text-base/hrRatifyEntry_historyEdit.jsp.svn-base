<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<table class="tb_normal" width="100%">
	<tr>
		<td><span class="fsBtnGroup"> <a class="fsBtnAdd"
				href="javascript:void(0);"
				onclick="HR_AddRatifyEntryNew('TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit');">
					<bean:message bundle="hr-ratify"
						key="hrRatifyHistory.addRatifyHistory" />
			</a>
		</span></td>
	</tr>

	<tr>   
		<td colspan="4" width="100%">
			<table class="tb_normal" width="100%" id="TABLE_DocList_fdHistory_Form" align="center" tbdraggable="true">
				<tr align="center" >
					<td style="width: 20px;" class="bgColorTd"></td>
					<td style="width: 40px;" class="bgColorTd">${lfn:message('page.serial')}</td>
					<td class="bgColorTd">
						${lfn:message('hr-ratify:hrRatifyHistory.fdName')}</td>
					<td class="bgColorTd">
						${lfn:message('hr-ratify:hrRatifyHistory.fdPost')}</td>
					<td class="bgColorTd">
						${lfn:message('hr-ratify:hrRatifyHistory.fdStartDate')}</td>
					<td class="bgColorTd">
						${lfn:message('hr-ratify:hrRatifyHistory.fdEndDate')}</td>
					</td>
				</tr>
				<tr KMSS_IsReferRow="1" style="display: none;" class="fdItem">
					<td align="center">
					   <a href="javascript:void(0);" onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit');"
						title="${lfn:message('doclist.delete')}"> 
						<img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
					    </a> 
						<xform:text property="fdHistory_Form[!{index}].fdDesc" showStatus="noShow" />
						<xform:text property="fdHistory_Form[!{index}].fdLeaveReason" showStatus="noShow" />
					</td>
					<td align="center" KMSS_IsRowIndex="1">
					   <span id="fdOOrderHistory!{index}"><c:out value="!{index}"></c:out></span>
					</td>
					<td align="center">
						<%-- 公司名称--%> 
						<input type="hidden" name="fdHistory_Form[!{index}].fdId" value="" disabled="true" />
						<input type="text" name="fdHistory_Form[!{index}].fdNameNew" disabled="true" class="inputsgl">
						<div id="_xform_fdHistory_Form[!{index}].fdName" _xform_type="text" style="display: none;">
						<xform:text property="fdHistory_Form[!{index}].fdName" showStatus="edit"
								subject="${lfn:message('hr-ratify:hrRatifyHistory.fdName')}"
								validators=" maxLength(200)" style="width:95%;" />
						</div>
					</td>
					<td align="center">
						<%-- 职位--%> <input type="text"
						name="fdHistory_Form[!{index}].fdPostNew" disabled="true"
						class="inputsgl">
						<div id="_xform_fdHistory_Form[!{index}].fdPost"
							_xform_type="text" style="display: none;">
							<xform:text property="fdHistory_Form[!{index}].fdPost"
								showStatus="edit"
								subject="${lfn:message('hr-ratify:hrRatifyHistory.fdPost')}"
								validators=" maxLength(200)" style="width:95%;" />
						</div>
					</td>
					 <td align="center">
                                        <%-- 开始日期--%>
                                        <input type="text" name="fdHistory_Form[!{index}].fdStartDateNew" disabled="true"  class="inputsgl">
                                        <div id="_xform_fdHistory_Form[!{index}].fdStartDate" _xform_type="datetime" style="display:none;">
                                            <xform:datetime property="fdHistory_Form[!{index}].fdStartDate" showStatus="edit" dateTimeType="date" style="width:95%;" />
                                        </div>
                                   </td>
					<td align="center">
						<%-- 结束日期--%> 
						<input type="text" name="fdHistory_Form[!{index}].fdEndDateNew" disabled="true" class="inputsgl">
						<div id="_xform_fdHistory_Form[!{index}].fdEndDate" _xform_type="datetime" style="display: none;">
							<xform:datetime property="fdHistory_Form[!{index}].fdEndDate" showStatus="edit" dateTimeType="date" style="width:95%;" />
						</div>
					</td>
				</tr>
				<input type="hidden" name="fdHistory_vstatusLength" value="${fn:length(hrRatifyEntryForm.fdHistory_Form)}">
				<c:forEach items="${hrRatifyEntryForm.fdHistory_Form}"
					var="fdHistory_FormItem" varStatus="vstatus">
					<tr KMSS_IsContentRow="1" class="fdItem">
						<td align="center"><a href="javascript:void(0);"
							onclick="DocList_DeleteRow();closeDetail('TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit');"
							title="${lfn:message('doclist.delete')}"> <img
								src="${KMSS_Parameter_StylePath}icons/icon_del.png"
								border="0" />
						</a> <xform:text
								property="fdHistory_Form[${vstatus.index}].fdDesc"
								showStatus="noShow" /> <xform:text
								property="fdHistory_Form[${vstatus.index}].fdLeaveReason"
								showStatus="noShow" /></td>
						<td align="center"
							onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit')">
							<span id="fdOOrderHistory!{index}">${vstatus.index+1}</span>
						</td>
						<td align="center"
							onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit')">
							<%-- 公司名称--%> 
							<input type="hidden" name="fdHistory_Form[${vstatus.index}].fdId" value="${fdHistory_FormItem.fdId}" />
							<input type="text" name="fdHistory_Form[${vstatus.index}].fdNameNew" disabled="true"  class="inputsgl" />
							<div id="_xform_fdHistory_Form[${vstatus.index}].fdName"
								_xform_type="text" style="display:none;">
								<xform:text
									property="fdHistory_Form[${vstatus.index}].fdName"
									showStatus="edit"
									subject="${lfn:message('hr-ratify:hrRatifyHistory.fdName')}"
									validators=" maxLength(200)" style="width:95%;" />
							</div>
						</td>
						<td align="center"
							onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit')">
							<%-- 职位--%>
							<input type="text" name="fdHistory_Form[${vstatus.index}].fdPostNew" disabled="true"  class="inputsgl" />
							<div id="_xform_fdHistory_Form[${vstatus.index}].fdPost"
								_xform_type="text" style="display:none;">
								<xform:text
									property="fdHistory_Form[${vstatus.index}].fdPost"
									showStatus="edit"
									subject="${lfn:message('hr-ratify:hrRatifyHistory.fdPost')}"
									validators=" maxLength(200)" style="width:95%;" />
							</div>
						</td>
						<td align="center"
							onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit')">
							<%-- 开始日期--%>
							<input type="text" name="fdHistory_Form[${vstatus.index}].fdStartDateNew" disabled="true"  class="inputsgl">
							<div
								id="_xform_fdHistory_Form[${vstatus.index}].fdStartDate"
								_xform_type="datetime " style="display:none;">
								<xform:datetime
									property="fdHistory_Form[${vstatus.index}].fdStartDate"
									showStatus="edit" dateTimeType="date"
									style="width:95%;" />
							</div>
						</td>
						<td align="center" onclick="HR_EditRatifyEntryNew(this,'TABLE_DocList_fdHistory_Form','table_of_fdHistory_detail_edit')">
							<%-- 结束日期--%>
							<input type="text" name="fdHistory_Form[${vstatus.index}].fdEndDateNew" disabled="true"  class="inputsgl">
							<div id="_xform_fdHistory_Form[${vstatus.index}].fdEndDate" _xform_type="datetime" style="display:none;">
								<xform:datetime
									property="fdHistory_Form[${vstatus.index}].fdEndDate"
									showStatus="edit" dateTimeType="date"
									style="width:95%;" />
							</div>
						</td>
					</tr>
				</c:forEach>
			</table> <input type="hidden" name="fdHistory_Flag" value="1"> 
			<script>Com_IncludeFile("doclist.js");</script> 
                           <script>DocList_Info.push('TABLE_DocList_fdHistory_Form');</script>
		</td>
	</tr>
</table>
	