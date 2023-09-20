<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script>
	// 该脚本引入在jshead之前
	Com_IncludeFile("mustache.js", "${KMSS_Parameter_ContextPath}sys/xform/maindata/resource/js/", "js", true);
</script>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="xFormJdbcDataSet_lang.jsp"%>
<% response.setHeader("X-UA-Compatible","IE=edge"); %>
<script type="text/javascript">
Com_IncludeFile("jquery.js|data.js|json2.js|security.js");
</script>
<script type="text/javascript">
	// 添加多语言，在js里面使用
	var __lang = [];
	__lang.push("${ lfn:message('sys-xform-maindata:sysFormJdbcDataSet.plzChooseSource') }");
	__lang.push("${ lfn:message('sys-xform-maindata:sysFormJdbcDataSet.sqlGenerator') }");
	Com_IncludeFile("jdbcDataSet.js", "${KMSS_Parameter_ContextPath}sys/xform/maindata/resource/js/", "js", true);
	Com_IncludeFile("tools.js","${KMSS_Parameter_ContextPath}sys/xform/maindata/resource/js/","js",true);
	Com_IncludeFile("jquery.blockUI.js","${KMSS_Parameter_ContextPath}sys/xform/maindata/resource/js/","js",true);

</script>
<title>
	<c:out value="${ lfn:message('sys-xform-maindata:tree.relation.jdbc.root') } - ${ lfn:message('sys-xform-maindata:tree.relation.jdbc.list') }"></c:out>
</title>
<html:form action="/sys/xform/maindata/jdbc_data_set/xFormjdbcDataSet.do">
<div id="optBarDiv">
	<c:if test="${sysFormJdbcDataSetForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="before_submit('update');">
	</c:if>
	<c:if test="${sysFormJdbcDataSetForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="before_submit('save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="before_submit('saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-xform-maindata" key="tree.relation.jdbc.list"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td colspan="4">
			<div style="color:red"><bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.Description"/></div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.docSubject"/>
		</td>
		<td width="35%">
			<xform:text property="docSubject" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.safeType"/>
		</td>
		<td width="35%">
			<xform:radio property="fdIsSafe" htmlElementProperties="onclick='sqlSafeType_change(this);'">
				<xform:enumsDataSource enumsType="xformJdbc_dataType" />
			</xform:radio>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdDataSource"/>
		</td><td width="35%">
			<xform:select property="fdDataSource" style="float: left;" showStatus="edit" value="${fdDataSource }">
			 	<xform:beanDataSource serviceBean="compDbcpService"
					selectBlock="fdId,fdName" orderBy="" />
			</xform:select>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.docCategory"/>
		</td><td width="35%">
			<xform:dialog required="true" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory') }" propertyId="docCategoryId" style="width:90%"
					propertyName="docCategoryName" dialogJs="XForm_treeDialog()">
			</xform:dialog>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdKey"/>
		</td>
		<td width="35%">
			<xform:text property="fdKey" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.fdKey')}" style="width:85%" validators="myAlphanum"/></br>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdKeyTip"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message key="model.fdOrder"/>
		</td><td width="35%">
			<%-- <xform:text property="fdOrder" style="width:85%" /> --%>
			<xform:text property="fdNewOrder" style="width:85%;" validators="digits min(0)" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdSqlExpression"/>
		</td>
		<td colspan="3" width="85%">
			
			<div class="div_sql_help">
				<input type="button" class="lui_form_button" style="cursor:pointer;" value="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.sqlTool')}" onclick="fdSqlGenerationWizard();" />
				<input type="button" class="lui_form_button" style="cursor:pointer;" value="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.help')}" onclick="fdSqlGenerationHelp();" />
			</div>
			<xform:textarea property="fdSqlExpression" style="width:85%" htmlElementProperties="onchange='fdSqlExpression_change();'"></xform:textarea>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdSqlExpressionTest"/>
		</td><td colspan="3" width="85%">
			<font color="">
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdSqlExpressionTestMsg"/>
			</font>
			<xform:textarea property="fdSqlExpressionTest" style="width:85%" htmlElementProperties="onchange='fdSqlExpression_change();'"></xform:textarea>
			<input type="button" onclick="sqlPreview();" class="lui_form_button" style="cursor:pointer;" value="<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.extractStructure"/>"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" colspan="4" width=100% align="center">
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdData"/>
			<xform:textarea property="fdData" style="width:85%; display:none;"></xform:textarea>
		</td>
	</tr>
	<tr>
		<td  colspan="4" valign="top">
	       <div id="jdbc_data_set_search" style="float:left;width:50%;margin:1px"></div>
	       <div id="jdbc_data_set_in" style="float:left;width:30%;margin:1px"></div>
	       <div id="jdbc_data_set_out" style="float:left;width:30%;margin:1px"></div>
	    </td>
		
	</tr>
</table>
</center>
<script>

function sqlSafeType_change(obj){
	if($(obj).val()=='true'){
		$("#div_sql_safe").show();
		$("#div_sql_conn").hide();
	}
	else{
		$("#div_sql_safe").hide();
		$("#div_sql_conn").show();
	}
}
$(function(){
	sqlSafeType_change($("[name='fdIsSafe']:checked")[0]);
});
</script>
<!-- js search脚本模板 -->
<script id="jdbc_data_set_template_search" type="text/mustache">
<table class="erp_template" style="table-layout:fixed;width:100%" >
<col style="width: 24%" />
<col style="width: 24%" />
<col style="width: 20%" />
<col style="width: 17.5%" />
<col style="width: 14.5%" />

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
				<input type="text" style="width:100%;" name="s_desc" class="inputsgl" commentName="sdesc" tagName="{{tagName}}"
					value="{{sdesc}}" nodeKey="{{nodeKey}}"/>
			</td>
			<td>
				<select name="s_type" style="width:100%;" nodeKey="{{nodeKey}}" commentName="stype" tagName="{{tagName}}"
					defaultValue="{{stype}}">
				</select>
			</td>
			<td>
				<select name="s_type_data" style="width:100%;" nodeKey="{{nodeKey}}" commentName="stypeData" tagName="{{tagName}}"
					defaultValue="{{stypeData}}">
				</select>
			</td>
			<td>
				<input type="text" style="width:100%;" name="s_default" class="inputsgl" commentName="sdefault" tagName="{{tagName}}"
					value="{{sdefault}}" nodeKey="{{nodeKey}}"/>
			</td>	
		</tr>
		{{/info.tbody}}
	</tbody>
</table>
</script>
<!-- js input脚本模板 -->
<script id="jdbc_data_set_template_in" type="text/mustache">
<table class="erp_template" style="table-layout:fixed;width:100%" >
<col style="width: 38%" />
<col style="width: 38%" />
<col style="width: 24%" />

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
				<select name="s_type_indata" style="width:100%;" nodeKey="{{nodeKey}}" commentName="ctype" tagName="{{tagName}}"
					defaultValue="{{ctype}}">
				</select>
			</td>
			<td>
				<input type="checkbox" name="required" value="<kmss:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.isRequired" />" commentName="required" tagName="{{tagName}}"
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
<col style="width: 34%" />
<col style="width: 32%" />
<col style="width: 34%" />
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
				<select name="disp" style="width:100%;" nodeKey="{{nodeKey}}" commentName="disp" tagName="{{tagName}}"
					defaultValue="{{disp}}">
				</select>
			</td>
		</tr>
		{{/info.tbody}}
	</tbody>
</table>
</script>


<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	var validation = $KMSSValidation();
	
	//自定义校验方法
	validation.addValidator('myAlphanum','${lfn:message("sys-xform-maindata:sysFormJdbcDataSet.fdKeyWaring") }',function(v, e, o){
		return this.getValidator('isEmpty').test(v) || !/\W/.test(v);
	});
	
	function XForm_treeDialog() {
		Dialog_Tree(false, 'docCategoryId', 'docCategoryName', ',', 
				'sysFormJdbcDataSetCategoryTreeService&parentId=!{value}', 
				'<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.category"/>', 
				null, null, null, null, null, 
				'<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.category"/>');
	}
	
	Com_Parameter.event["submit"].push(validateKeyUnique);
	
	function validateKeyUnique(){
		var fdKey = document.getElementsByName("fdKey")[0];
		var isUnique = true;
		if(fdKey && fdKey.value != ''){
			var url = Com_Parameter.ContextPath + "sys/xform/maindata/jdbc_data_set/xFormjdbcDataSet.do?method=isUnique&fdKey=" + fdKey.value + "&fdId=${param.fdId}";
			$.ajax({ url: url, async: false, dataType: "json", cache: false, success: function(rtn){
				if("true" != rtn.isUnique){
					isUnique = false;
					alert("${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.fdKeyNotUniqueWarning')}");
					
				}
			}});		
		}
		return isUnique;
	}
	
	function fdSqlGenerationHelp(){
		var url = Com_Parameter.ContextPath + "sys/xform/maindata/jdbc_data_set/xFormMainDataExtend_edit_sqlGeneration_help.jsp";
		Com_OpenWindow(url,"_blank");
	}
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>