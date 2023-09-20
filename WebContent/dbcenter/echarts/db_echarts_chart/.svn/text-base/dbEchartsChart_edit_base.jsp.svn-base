<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<table class="tb_normal" width=95%> 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsChart.docSubject"/>
		</td><td width="85%" colspan="3">
			<xform:text property="docSubject" style="width:98%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.category') }
		</td>
		<td width="85%" colspan="3">
			<xform:dialog required="true" subject="分类" propertyId="fdDbEchartsTemplateId" style="width:50%" propertyName="fdDbEchartsTemplateName" dialogJs="dbEcharts_treeDialog();">
			</xform:dialog>
		</td>
	</tr>	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsChart.fdTheme"/>
		</td><td width="85%" colspan="3">
			<xform:select property="fdTheme" showPleaseSelect="false">
				<xform:simpleDataSource value="default"><bean:message bundle="dbcenter-echarts" key="dbcenterEcharts.theme.default"/></xform:simpleDataSource>
				<c:forEach items="${themes }" var="theme">
					<xform:simpleDataSource value="${theme }">${theme }</xform:simpleDataSource>
				</c:forEach>
			</xform:select>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			${ lfn:message('dbcenter-echarts:moshiqiehuan') }
		</td><td width="85%" colspan="3">
			<label><input name="editMode" onclick="changeMode();" value="configMode" type="radio" checked>${ lfn:message('dbcenter-echarts:peizhimoshi') }</label>
			<label><input name="editMode" onclick="changeMode();" value="codeMode" type="radio">${ lfn:message('dbcenter-echarts:daimamoshi') }</label>
		</td>
	</tr>

	<tbody id="codeMode" style="display:none;">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsChart.fdConfig"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdConfig" style="width:98%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsChart.fdCode"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdCode" style="width:98%;height:200px;" />
		</td>
	</tr>
	</tbody>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsChart.docCreateTime"/>
		</td><td width="35%">
			<xform:datetime property="docCreateTime" showStatus="view" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsChart.docCreator"/>
		</td><td width="35%">
			<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
		</td>
	</tr>
</table>
