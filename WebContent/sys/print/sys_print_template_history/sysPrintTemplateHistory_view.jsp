<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<c:set var="fdPrintOperType" value="templateHistory"></c:set>
<c:set var="fdPrintSubTemplateSize" value="${fn:length(sysPrintTemplateForm.fdSubTemplates) }"></c:set>
<c:set var="authUrl" value="${actionUrl}?method=edit" />
<kmss:windowTitle moduleKey="sys-xform:xform.title"
	subjectKey="sys-xform:tree.xform.def"
	subject="历史打印模板" />
	
<div id="optBarDiv">
	<c:if test="${versionType eq null || versionType ne 'new'}">
		<kmss:auth
			requestURL="${actionUrl}?method=edit&fdId=${param.fdModelTemplateId}"
			requestMethod="GET">
			<input type="button" value="<bean:message key="button.edit"/>"
				onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/print/sys_print_template_history/sysPrintTemplateHistory.do?method=edit&fdId=${param.fdId }&modelName=${fdMainModelName}&fdKey=${HtmlParam.fdKey }&authUrl=&fdModelTemplateId=${param.fdModelTemplateId}','_self');" />
		</kmss:auth>
	</c:if>
	
	<input type="button" value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">打印历史模板_V${sysPrintTemplateHistoryForm.fdTemplateEdition }</p>
<center>
<table id="Label_Tabel" width=95%>
	<tr LKS_LabelName="模板信息">
		<td>
			<div id="sysPrintdesignPanel">
				<table class="tb_normal" width=100% style="border-top:0;">
					<tr>
						<td class="td_normal_title" width=15%>
							<kmss:message key="sys-print:table.sysPrint.fdPrintMode" />
						</td>
						<td width=25% id="tr_fdPrintMode">
						 	<xform:select property="sysPrintTemplateForm.fdPrintMode" value="${sysPrintTemplateForm.fdPrintMode}"  showPleaseSelect="false" showStatus="readOnly" onValueChange="onFdPrintModeChange">
								<xform:enumsDataSource enumsType="fdPrint_mode" />
							</xform:select>
						</td>
						<td class="td_normal_title" width=15%>
							<kmss:message key="sys-print:table.sysPrint.config" />
						</td>
						<td>
						 	<xform:checkbox property="sysPrintTemplateForm.fdPrintTemplate" value="${sysPrintTemplateForm.fdPrintTemplate}">
							   	<xform:simpleDataSource value="true"><kmss:message key="sys-print:table.sysPrint.fdPrintTemplate" />
							   	</xform:simpleDataSource>
							</xform:checkbox>
						</td>
					</tr>
					<c:if test="${sysPrintTemplateHistoryForm.fdPrintMode=='2'}">
						<tr id="tr_word_printEdit">
							<td class="td_normal_title" width=15%>
								<kmss:message key="sys-print:table.sysPrint.fdPrintEdit" />
							</td>
							<td colspan="3">
							 	<xform:checkbox property="sysPrintTemplateHistoryForm.fdPrintEdit" showStatus="view">
								   	<xform:simpleDataSource value="true"><kmss:message key="message.yes" />
								   	</xform:simpleDataSource>
								</xform:checkbox>
							</td>
						</tr>
					</c:if>
				
					<c:if test="${sysPrintTemplateHistoryForm.fdPrintMode!='2'}">
						<tr>
							<td class="td_normal_title" width=15%>
								<kmss:message key="sys-print:table.sysPrint.description" />
							</td>
							<td colspan="3">
							 	<kmss:message key="sys-print:table.sysPrint.description.txt" />
							</td>
						</tr>
					</c:if>
					
				</table>
				<c:if test="${sysPrintTemplateForm.fdPrintMode!='2'}">
					<div id="sysPrintdesignPanel">
						<table class="tb_normal" width="100%">
							<tr>
								<td id="sysPrintdesignPanelTd">${sysPrintTemplateForm.fdTmpXml}</td>
							</tr>
						</table>
					</div>
				</c:if>
				<input type="hidden" name="sysPrintTemplateForm.fdTmpXml"  />
				</div>
			
		</td>
	</tr>
</table>
	
</center>
<%@ include file="/sys/print/sys_print_template_history/sysPrintTemplateHistory_view_script.jsp"%>
<%@ include file="/resource/jsp/view_down.jsp"%>