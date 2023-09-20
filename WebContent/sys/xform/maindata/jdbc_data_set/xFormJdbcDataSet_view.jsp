<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<title>
	<c:out value="${ lfn:message('sys-xform-maindata:tree.relation.jdbc.root') } - ${ lfn:message('sys-xform-maindata:tree.relation.jdbc.list') }"></c:out>
</title>
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
Com_IncludeFile("mustache.js", "${KMSS_Parameter_ContextPath}sys/xform/maindata/resource/js/", "js", true);
Com_IncludeFile("jdbcDataSet.js", "${KMSS_Parameter_ContextPath}sys/xform/maindata/resource/js/", "js", true);
Com_IncludeFile("tools.js","${KMSS_Parameter_ContextPath}sys/xform/maindata/resource/js/","js",true);
Com_IncludeFile("jquery.blockUI.js","${KMSS_Parameter_ContextPath}sys/xform/maindata/resource/js/","js",true);
</script>
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/xform/maindata/jdbc_data_set/xFormjdbcDataSet.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('xFormjdbcDataSet.do?method=edit&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/sys/xform/maindata/jdbc_data_set/xFormjdbcDataSet.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('xFormjdbcDataSet.do?method=delete&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-xform-maindata" key="tree.relation.jdbc.list"/></p>

<center>
<table id="Label_Tabel" width=95%>
		<!-- 基本信息 -->
		<tr LKS_LabelName="<bean:message bundle='sys-xform-maindata' key='sysFormMainData.basicInfo'/>">
			<td>
				<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.docSubject"/>
						</td><td  " width="35%">
							<xform:text property="docSubject" style="width:85%" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.safeType"/>
						</td><td   width="35%">
							<xform:radio property="fdIsSafe">
								<xform:enumsDataSource enumsType="xformJdbc_dataType" />
							</xform:radio>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdDataSource"/>
						</td>
						<td width="35%">
							<xform:select property="fdDataSource" style="float: left;" showStatus="view">
							 	<xform:beanDataSource serviceBean="compDbcpService"
									selectBlock="fdId,fdName" orderBy="" />
							</xform:select>
							<select name="fdDataSource" style="display:none;">
								<option value="${sysFormJdbcDataSetForm.fdDataSource }" selected="selected">${sysFormJdbcDataSetForm.fdDataSource }</option>
							</select>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.docCategory"/>
						</td>
						<td width="35%">
							<c:out value="${sysFormJdbcDataSetForm.docCategoryName}" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdKey"/>
						</td>
						<td width="35%">
							<xform:text property="fdKey" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.fdKey')}"/>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message key="model.fdOrder"/>
						</td><td width="35%">
							<%-- <xform:text property="fdOrder" style="width:85%" /> --%>
							<xform:text property="fdNewOrder" style="width:85%;" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdSqlExpression"/>
						</td><td colspan="3" width="85%">
							<xform:text property="fdSqlExpression" style="width:85%" />
							<textarea name="fdSqlExpression" style="display:none;" rows="" cols="">${sysFormJdbcDataSetForm.fdSqlExpression }</textarea>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							测试SQL
						</td><td colspan="3" width="85%">
							<xform:text property="fdSqlExpressionTest" style="width:85%" />
							<textarea name="fdSqlExpressionTest" style="display:none;" rows="" cols="">${sysFormJdbcDataSetForm.fdSqlExpressionTest }</textarea>
						</td>
					</tr>
					
						<%--
					<tr>
						<td class="td_normal_title" colspan="4" width=100% align="center">
							<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdData"/>
							<textarea name="fdData" style="width:85%; display:none;">${sysFormJdbcDataSetForm.fdData }</textarea>
						</td>
					</tr>
				 
					<tr>
						<td colspan="4">
						<div id="jdbc_data_set_search" style="float:left;width:40%;margin:1px"></div>
					       <div id="jdbc_data_set_in" style="float:left;width:30%;margin:1px"></div>
					       <div id="jdbc_data_set_out" style="float:left;width:30%;margin:1px"></div>
						 </td>
					</tr>
					--%>
				</table>
			</td>
		</tr>
		<!-- 被引用表单模板 -->
		<c:import url="/sys/xform/maindata/xFormMainDataRef_view.jsp" charEncoding="UTF-8">
			<c:param name="fdModelName" value="com.landray.kmss.sys.xform.maindata.model.SysFormJdbcDataSet"></c:param>
			<c:param name="fdId" value="${sysFormJdbcDataSetForm.fdId }"></c:param>
		</c:import>
</table>
</center>
<%--
<!-- js search脚本模板 -->
<script id="jdbc_data_set_template_search" type="text/mustache">
<table class="erp_template" style="table-layout:fixed;width:100%" >
<col style="width: 20%" />
<col style="width: 30%" />
<col style="width: 20%" />
<col style="width: 30%" />

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
				{{sdesc}}
			</td>
			<td>
				<select name="s_type" nodeKey="{{nodeKey}}" commentName="stype" tagName="{{tagName}}"
					defaultValue="{{stype}}">
				</select>
			</td>
			<td>
				{{sdefault}}
			</td>	
		</tr>
		{{/info.tbody}}
	</tbody>
</table>
</script>
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
<col style="width: 30%" />
<col style="width: 30%" />
<col style="width: 20%" />
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
			<td align="center">
				<select name="isSearch" nodeKey="{{nodeKey}}" commentName="isSearch" tagName="{{tagName}}"
					defaultValue="{{isSearch}}">
				</select>
			</td>
		</tr>
		{{/info.tbody}}
	</tbody>
</table>
</script>
 --%>
<%@ include file="/resource/jsp/view_down.jsp"%>