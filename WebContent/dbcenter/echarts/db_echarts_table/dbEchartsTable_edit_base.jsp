<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<table class="tb_normal" width=95%> 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsTable.docSubject"/> 
		</td><td width="85%" colspan="3">
			<xform:text property="docSubject" style="width:98%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			${lfn:message('dbcenter-echarts:dbEcharts.echart.table.caregory') }
		</td>
		<td width="85%" colspan="3">
			<xform:dialog required="true" subject="${lfn:message('dbcenter-echarts:dbEchartsTable.dbEchartsTemplate') }" propertyId="fdDbEchartsTemplateId" style="width:50%"
					propertyName="fdDbEchartsTemplateName" dialogJs="dbEcharts_treeDialog()">
			</xform:dialog>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			${lfn:message('dbcenter-echarts:table_edit_02')}
			<a href="javascript:_showHelpLogInfo('editModeHelp','400px','120px');"><span id="editModeHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>
		</td><td width="85%" colspan="3">
			<label><input name="editMode" onclick="changeMode();" value="configMode" type="radio" checked>${lfn:message('dbcenter-echarts:table_edit_03')}</label>
			<label><input name="editMode" onclick="changeMode();" value="codeMode" type="radio">${lfn:message('dbcenter-echarts:table_edit_05')}</label>
		</td>
	</tr>
	<tbody id="codeMode" style="display:none;">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsTable.fdCode"/>
			<a href="javascript:_showHelpLogInfo('fdCodeHelp','500px','350px');"><span id="fdCodeHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdCode" style="width:98%;height:300px;" />
		</td>
	</tr>
	</tbody>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsTable.docCreateTime"/>
		</td><td width="35%">
			<xform:datetime property="docCreateTime" showStatus="view" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsTable.docCreator"/>
		</td><td width="35%">
			<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
		</td>
	</tr>
</table>