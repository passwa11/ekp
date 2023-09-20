<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<script type="text/javascript">
Com_IncludeFile("jquery.js|data.js|json2.js");
</script>
<script type="text/javascript">
Com_IncludeFile("mustache.js", "${KMSS_Parameter_ContextPath}tic/core/resource/js/", "js", true);
Com_IncludeFile("jquery.treeTable.css", "${KMSS_Parameter_ContextPath}tic/core/resource/css/", "css", true);
Com_IncludeFile("jquery.treeTable.js", "${KMSS_Parameter_ContextPath}tic/core/resource/js/", "js", true);
Com_IncludeFile("jdbcDataSet.js", "${KMSS_Parameter_ContextPath}tic/jdbc/resource/js/", "js", true);
Com_IncludeFile("tools.js","${KMSS_Parameter_ContextPath}tic/core/resource/js/","js",true);
Com_IncludeFile("jquery.blockUI.js","${KMSS_Parameter_ContextPath}tic/core/resource/js/","js",true);
</script>
<div id="optBarDiv">
	<input type="button"
		value="${lfn:message('tic-core-common:ticCoreCommon.dataSearch')}"
		onclick="Com_OpenWindow('ticJdbcDataSet.do?method=viewQueryEdit&fdFuncId=${param.fdId}','_blank');">

	<kmss:auth requestURL="/tic/jdbc/tic_jdbc_data_set/ticJdbcDataSet.do?method=edit&fdId=${param.fdId}&fdAppType=${param.fdAppType}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('ticJdbcDataSet.do?method=edit&fdId=${param.fdId}&fdAppType=${param.fdAppType}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/tic/jdbc/tic_jdbc_data_set/ticJdbcDataSet.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('ticJdbcDataSet.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-jdbc" key="table.ticJdbcDataSet"/></p>

<center>
<table id="Label_Tabel" width=95%>
	<!-- 主文档 -->
	<tr LKS_LabelName="<bean:message bundle="tic-jdbc" key="table.ticJdbcDataSet.main"/>">
		<td>
				<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.docSubject"/>
						</td><td width="35%">
							<xform:text property="fdName" style="width:85%" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.fdDataSource"/>
						</td>
						<td width="35%">
							<xform:select property="fdDataSource" style="float: left;" showStatus="view">
							 	<xform:beanDataSource serviceBean="compDbcpService"
									selectBlock="fdId,fdName" orderBy="" />
							</xform:select>
							<select name="fdDataSource" style="display:none;">
								<option value="${ticJdbcDataSetForm.fdDataSource }" selected="selected">${ticJdbcDataSetForm.fdDataSource }</option>
							</select>
						</td>
					</tr>
					<tr>		
						<td class="td_normal_title" width=15%>
							<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.docCategory"/>
						</td>
						<td width="35%">
							<c:out value="${ticJdbcDataSetForm.fdCategoryName}" />
						</td>
						<td class="td_normal_title" width=15%>
								<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.fdIsAvailable"/>
							</td><td  width="35%">
								<xform:radio property="fdIsAvailable">
									<xform:enumsDataSource enumsType="tic_ws_yesno" />
								</xform:radio>
						</td>
					</tr>
								<tr>
								<td class="td_normal_title" width=15%><bean:message
										bundle="tic-core-common" key="ticCoreFuncBase.fdKey" /></td>
									<td width="35%" colspan="3">
									<c:out value="${ticJdbcDataSetForm.fdKey}" />
									</td>
								</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.fdSqlExpression"/>
						</td><td colspan="3" width="85%">
							<xform:text property="fdSqlExpression" style="width:85%" />
							<textarea name="fdSqlExpression" style="display:none;" rows="" cols="">${ticJdbcDataSetForm.fdSqlExpression }</textarea>
						</td>
					</tr>
				<%-- 	<tr>
						<td class="td_normal_title" colspan="4" width=100% align="center">
							<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.fdData"/>
							<textarea name="fdData" style="width:85%; display:none;">${ticJdbcDataSetForm.fdData }</textarea>
						</td>
					</tr> --%>
				<!-- 	<tr>
						<td width="50%" colspan="2" valign="top">
					       <div id="jdbc_data_set_in"></div>
					    </td>
						<td width="50%" colspan="2" valign="top">
					       <div id="jdbc_data_set_out"></div>
					    </td>
					</tr> -->
					<tr>
				                    <td class="td_normal_title" width="15%">
				                        ${lfn:message('tic-core-common:ticCoreFuncBase.fdInvoke')}
				                    </td>
				                    <td colspan="3" width="85.0%">
				                       <xform:checkbox property="fdInvoke">
				                           <xform:enumsDataSource enumsType="tic_core_invoke" />
				                       </xform:checkbox>
				                    </td>
				                </tr>
							 <tr>
				                <td class="td_normal_title" width="15%">
				                    ${lfn:message('tic-core-common:ticCoreFuncBase.authReaders')}
				                </td>
				                <td colspan="3" width="85.0%">
				                    <div id="_xform_authReaderIds" _xform_type="address">
				                        <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
				                    </div>
				                </td>
				            </tr>
				</table>
				</td>
			</tr>
	<!-- 查询历史 -->
	<c:import
		url="/tic/jdbc/tic_jdbc_query/tic_jdbc_view_history.jsp"
		charEncoding="UTF-8">
		<c:param name="formName" value="ticJdbcDataSetForm" />
	</c:import>
	<c:import
			url="/tic/core/common/tic_core_trans_sett/ticCore_searchInfo_view_history.jsp"
			charEncoding="UTF-8">
	</c:import>
	</table>
</center>
<%--
<!-- js input脚本模板 -->
<script id="jdbc_data_set_template_in" type="text/mustache">
<table class="erp_template" style="table-layout:fixed;width:100%" >
<col style="width: 40%" />
<col style="width: 40%" />
<col style="width: 20%" />
	<caption>{{info.caption}}</caption>
	<thead>
		<tr>
		{{#info.thead}}
			<td style='font-size:13px; text-align:center;' nowrap >{{{th}}}</td>
		{{/info.thead}}
		</tr>
	</thead>
	<tbody>
		{{#info.tbody}}
		<tr  id="{{nodeKey}}">
			<td>{{tagName}}</td>
			<td>
				{{ctype}}
			</td>
			<td>
				<input type="checkbox" name="required" disabled="true" value="是否必填" commentName="required" tagName="{{tagName}}"
					{{required}} nodeKey="{{nodeKey}}"/>
			</td>
		</tr>
		{{/info.tbody}}
	</tbody>
</table>
</script>
<!-- js output脚本模板 -->
<script id="jdbc_data_set_template_out" type="text/mustache">
<table class="erp_template" style="table-layout:fixed;width:100%" >
<col style="width: 40%" />
<col style="width: 40%" />
<col style="width: 20%" />
	<caption>{{info.caption}}</caption>
	<thead>
		<tr>
		{{#info.thead}}
			<td style='font-size:13px; text-align:center;' nowrap >{{{th}}}</td>
		{{/info.thead}}
		</tr>
	</thead>
	<tbody>
		{{#info.tbody}}
		<tr  id="{{nodeKey}}">
			<td>{{tagName}}</td>
			<td>
				{{ctype}}
			</td>
			<td>
				<select name="disp" nodeKey="{{nodeKey}}" disabled="true" commentName="disp" tagName="{{tagName}}"
					defaultValue="{{disp}}">
				</select>
			</td>
		</tr>
		{{/info.tbody}}
	</tbody>
</table>
</script>
 --%>
<%@ include file="/resource/jsp/view_down.jsp"%>