<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>Com_IncludeFile("doclist.js");</script>
<table class="tb_normal" width=100% id="TABLE_DocList" align="center" tbdraggable="true">
	<tr align="center">
		<%--复选框--%> 
		<td class="td_normal_title" style="width: 3%"></td>
		<%--序号--%> 
		<td class="td_normal_title" style="width: 5%">
			<bean:message key="page.serial"/>
		</td>
		<%--会议议题--%> 
		<td class="td_normal_title" style="width: 18%">
			<bean:message bundle="km-imeeting" key="kmImeetingAgenda.docSubject"/>
		</td>
		<%--汇报人--%>
		<td class="td_normal_title" style="width: 12%">
			<bean:message bundle="km-imeeting" key="kmImeetingAgenda.docReporter"/>
		</td>
		<%--汇报时间--%> 
		<td class="td_normal_title" style="width: 10%">
			<bean:message bundle="km-imeeting" key="kmImeetingAgenda.docReporterTime"/>
		</td>
		<%--上会所需材料--%> 
		<td class="td_normal_title" style="width: 16%">
			<bean:message bundle="km-imeeting" key="kmImeetingAgenda.attachmentName"/>
		</td>
		<%--材料负责人--%> 
		<td class="td_normal_title" style="width: 12%">
			<bean:message bundle="km-imeeting" key="kmImeetingAgenda.docRespons"/>
		</td>
		<%--提交时间--%> 
		<td class="td_normal_title" style="width: 10%">
			<bean:message bundle="km-imeeting" key="kmImeetingAgenda.attachmentSubmitTime"/>
		</td>
		<%--按钮--%>
		<td class="td_normal_title" style="width: 7%;" align="center">
			
		</td>
	</tr>
	
	<%--基准行--%>
	<tr KMSS_IsReferRow="1" style="display: none">
		<%--复选框--%> 
		<td style="width: 3%">
			<input type='checkbox' name='DocList_Selected' />
		</td>
		<td KMSS_IsRowIndex="1" width="5%" align="center"></td>
		<td style="width: 18%" align="center">
			<xform:text property="kmImeetingAgendaForms[!{index}].docSubject" showStatus="edit" style="width:92%;" required="true" validators="maxLength(200)" subject="${lfn:message('km-imeeting:kmImeetingAgenda.docSubject') }"/>
			<input type="hidden" name="kmImeetingAgendaForms[!{index}].fdId" value="${kmImeetingAgendaitem.fdId}" /> 
			<input type="hidden" name="kmImeetingAgendaForms[!{index}].fdMainId" value="${kmImeetingMainForm.fdId}" />
		</td>
		<td style="width: 12%" align="center">
			<xform:address propertyName="kmImeetingAgendaForms[!{index}].docReporterName" propertyId="kmImeetingAgendaForms[!{index}].docReporterId" orgType="ORG_TYPE_PERSON" showStatus="edit"></xform:address>
		</td>
		<td style="width: 10%" align="center">
			<xform:text property="kmImeetingAgendaForms[!{index}].docReporterTime" validators="digits min(0)" style="width:65%;" showStatus="edit"/>
			<bean:message key="date.interval.minute"/>
		</td>
		<td style="width: 16%" align="center">
			<xform:text property="kmImeetingAgendaForms[!{index}].attachmentName" validators="maxLength(200)" subject="${lfn:message('km-imeeting:kmImeetingAgenda.attachmentName') }" style="width:98%;" showStatus="edit"/>
		</td>
		<td style="width: 12%" align="center">
			<xform:address propertyName="kmImeetingAgendaForms[!{index}].docResponsName" propertyId="kmImeetingAgendaForms[!{index}].docResponsId" orgType="ORG_TYPE_PERSON" showStatus="edit"></xform:address>
		</td>
		<td style="width: 10%" align="center">
			<bean:message bundle="km-imeeting" key="kmImeetingAgenda.attachmentSubmitTime.tip1"/>
			<xform:text property="kmImeetingAgendaForms[!{index}].attachmentSubmitTime" style="width:20%;" validators="digits min(0)" showStatus="edit"/>
			<bean:message bundle="km-imeeting" key="kmImeetingAgenda.attachmentSubmitTime.tip2"/>
		</td>
		<!-- 删除 -->
		<td width="7%" align="center">
			<!-- 
			<a href="javascript:void(0);" onclick="DocList_MoveRow(-1);" title="${lfn:message('doclist.moveup')}">
				<img src="${KMSS_Parameter_StylePath}/icons/icon_up.png" border="0" />
			</a>
			<a href="javascript:void(0);" onclick="DocList_MoveRow(1);" title="${lfn:message('doclist.movedown')}">
				<img src="${KMSS_Parameter_StylePath}/icons/icon_down.png" border="0" />
			</a> -->
			<a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
				<img src="${KMSS_Parameter_StylePath}/icons/icon_copy.png" border="0" />
			</a>
			&nbsp;
			<a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
				<img src="${KMSS_Parameter_StylePath}/icons/icon_del.png" border="0" />
			</a>
		</td>
	</tr>
	
	<%--内容行--%>
	<c:forEach items="${kmImeetingMainForm.kmImeetingAgendaForms}"  var="kmImeetingAgendaitem" varStatus="vstatus">
		<tr KMSS_IsContentRow="1">
			<%--复选框--%> 
			<td style="width: 3%">
				<input type='checkbox' name='DocList_Selected' />
			</td>
			<td width="5%" align="center">
				${vstatus.index+1}
			</td>
			<td width=18% align="center">
				<input type="hidden" name="kmImeetingAgendaForms[${vstatus.index}].fdId" value="${kmImeetingAgendaitem.fdId}" /> 
				<input type="hidden" name="kmImeetingAgendaForms[${vstatus.index}].fdMainId" value="${kmImeetingMainForm.fdId}" />
				<xform:text property="kmImeetingAgendaForms[${vstatus.index}].docSubject" value="${kmImeetingAgendaitem.docSubject}"
					subject="${lfn:message('km-imeeting:kmImeetingAgenda.docSubject') }"
					style="width:92%"  required="true" showStatus="edit"/>
			</td>
			<td style="width: 12%" align="center">
				<xform:address propertyName="kmImeetingAgendaForms[${vstatus.index}].docReporterName" propertyId="kmImeetingAgendaForms[${vstatus.index}].docReporterId" 
					orgType="ORG_TYPE_PERSON" showStatus="edit"></xform:address>
			</td>
			<td style="width: 10%" align="center">
				<xform:text property="kmImeetingAgendaForms[${vstatus.index}].docReporterTime"  validators="digits min(0)" style="width:65%;" showStatus="edit"/>
				<bean:message key="date.interval.minute"/>
			</td>
			<td style="width: 16%" align="center">
				<xform:text property="kmImeetingAgendaForms[${vstatus.index}].attachmentName" style="width:98%;" subject="${lfn:message('km-imeeting:kmImeetingAgenda.attachmentName') }" showStatus="edit"/>
			</td>
			<td style="width: 12%" align="center">
				<xform:address propertyName="kmImeetingAgendaForms[${vstatus.index}].docResponsName" propertyId="kmImeetingAgendaForms[${vstatus.index}].docResponsId" orgType="ORG_TYPE_PERSON" showStatus="edit"></xform:address>
			</td>
			<td style="width: 10%" align="center">
				<bean:message bundle="km-imeeting" key="kmImeetingAgenda.attachmentSubmitTime.tip1"/>
				<xform:text property="kmImeetingAgendaForms[${vstatus.index}].attachmentSubmitTime"  validators="digits min(0)" style="width:20%;" showStatus="edit"/>
				<bean:message bundle="km-imeeting" key="kmImeetingAgenda.attachmentSubmitTime.tip2"/>
			</td>
			<!-- 删除 -->
			<td width="7%" align="center">
				<!-- 
				<a href="javascript:void(0);" onclick="DocList_MoveRow(-1);" title="${lfn:message('doclist.moveup')}">
					<img src="${KMSS_Parameter_StylePath}/icons/icon_up.png" border="0" />
				</a>
				<a href="javascript:void(0);" onclick="DocList_MoveRow(1);" title="${lfn:message('doclist.movedown')}">
					<img src="${KMSS_Parameter_StylePath}/icons/icon_down.png" border="0" />
				</a> -->
				<a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
					<img src="${KMSS_Parameter_StylePath}/icons/icon_copy.png" border="0" />
				</a>
				&nbsp;
				<a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
					<img src="${KMSS_Parameter_StylePath}/icons/icon_del.png" border="0" />
				</a>
			</td>
		</tr>
	</c:forEach>
	
	<tr type="optRow" class="tr_normal_opt" invalidrow="true">
		<td colspan="9">
			<a href="javascript:void(0);" onclick="DocList_AddRow();" title="${lfn:message('doclist.add')}">
				<img src="${KMSS_Parameter_StylePath}/icons/icon_add.png" border="0" />
			</a>
			&nbsp;
			<a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(-1);" title="${lfn:message('doclist.moveup')}">
				<img src="${KMSS_Parameter_StylePath}/icons/icon_up.png" border="0" />
			</a>
			&nbsp;
			<a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(1);" title="${lfn:message('doclist.movedown')}">
				<img src="${KMSS_Parameter_StylePath}/icons/icon_down.png" border="0" />
			</a>
			&nbsp;
		</td>
	</tr>
	
</table>
<c:if test="${kmImeetingMainForm.method_GET=='add' && copyMeeting != true}">
	<script type="text/javascript">
		LUI.ready(function(){
			setTimeout(function(){
				for (var i = 0; i < 1; i ++) {
					//DocList_AddRow('TABLE_DocList');
				}
			},100);
		});
	</script>
</c:if>