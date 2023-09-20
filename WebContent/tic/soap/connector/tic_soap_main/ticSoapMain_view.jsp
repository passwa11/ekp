<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>

<script type="text/javascript">
Com_IncludeFile("json2.js");
Com_IncludeFile("dialog.js", null, "js");
</script>
<script>
Com_IncludeFile("jquery.js");

	function confirmDelete(msg) {
		var del = confirm("<bean:message key="page.comfirmDelete"/>");
		return del;
	}
	
	
	function query_edit() {
		var url = Com_Parameter.ContextPath + 
				"tic/soap/connector/tic_soap_main/ticSoapMain.do"+
				"?method=viewQueryEdit&funcId=${param.fdId}&isQuery=true";
		Com_OpenWindow(url);
	}

	function cache_edit() {
		var url = Com_Parameter.ContextPath + 
				"tic/core/cacheindb/config/ticCacheDbTable.do"+
				"?method=edit&funcId=${param.fdId}";
		Com_OpenWindow(url);
	}

	function cache_job_edit() {
		var url = Com_Parameter.ContextPath + 
				"tic/core/cacheindb/config/ticCacheSyncJob.do"+
				"?method=edit&funcId=${param.fdId}";
		Com_OpenWindow(url);
	}
</script>



<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/mustache.js" type="text/javascript"></script>
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/erp.parser.js" type="text/javascript"></script>
<link href="${KMSS_Parameter_ContextPath}tic/core/resource/css/jquery.treeTable.css" rel="stylesheet" type="text/css" />

<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/jquery.treeTable.js" type="text/javascript"></script>
<script src="${KMSS_Parameter_ContextPath}tic/soap/connector/tic_soap_main/ticSoapFunc.js" type="text/javascript"></script>
<script type="text/javascript">
/************标记修正。。。************
 * 使用国际化的资源文件
 ************************/
$(function(){
	var spanNode = document.createElement("span");
	spanNode.id = "Include_Custom_Validations_Span_Id";
	document.body.appendChild(spanNode);
	$("#Include_Custom_Validations_Span_Id").load(Com_Parameter.ContextPath +
			"tic/core/resource/jsp/custom_validations.jsp");
	var fdReturnValue='${ticSoapMainForm.fdReturnValue}';
	var fdSuccess='${ticSoapMainForm.fdSuccess}';
	var fdFail='${ticSoapMainForm.fdFail}';
	setTimeout(function(){ 
		if(fdReturnValue){
	    	var fdReturn=$('<td rowspan="100%" style="vertical-align:top;">'
	        +fdReturnValue+'<br>'
			+'返回 '+fdSuccess+'  时表示接口请求成功<br>'
			+'返回 '+fdFail+'   时表示接口请求失败</td>');
	    	$("#wsMapperTemplateOut_tr").append(fdReturn);
		} 
		}, 100);

});
</script>



<html:form action="/tic/soap/connector/tic_soap_main/ticSoapMain.do">
<div id="optBarDiv">
	<input type="button"
		value="<bean:message key="button.queryData" bundle="tic-soap-connector"/>"
		onclick="query_edit();">

<!-- 	<input type="button"
		value="缓存表定义"
		onclick="cache_edit();">

	<input type="button"
		value="缓存任务配置"
		onclick="cache_job_edit();"> -->

	<kmss:auth requestURL="/tic/soap/connector/tic_soap_main/ticSoapMain.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('ticSoapMain.do?method=edit&fdId=${param.fdId}&fdAppType=${param.fdAppType}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/tic/soap/connector/tic_soap_main/ticSoapMain.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('ticSoapMain.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-soap-connector" key="ticSoapMain.func.config"/></p>

<center>
<table id="Label_Tabel" width=95%>
	<!-- 主文档 -->
	<tr LKS_LabelName="<bean:message bundle="tic-soap-connector" key="table.ticSoapMain.main"/>">
		<td>
			<table class="tb_normal" width="100%">
				<!-- 函数名，所属分类 -->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapMain.func.docSubject"/>
					</td><td width="35%">
						<xform:text property="fdName" style="width:85%" />
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapMain.docCategory"/>
					</td><td width="35%">
						<c:out value="${ticSoapMainForm.fdCategoryName}" />
					</td>
						</tr>

				<!-- 所属服务 -->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapMain.wsServerSetting"/>
					</td><td width="35%">
						<c:out value="${ticSoapMainForm.wsServerSettingName}" />
					</td>
					<!-- 是否启用，版本号 -->
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapMain.wsEnable"/>
					</td><td  width="35%">
						<xform:select property="fdIsAvailable">
							<xform:enumsDataSource enumsType="tic_ws_yesno" />
						</xform:select>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%><bean:message
						bundle="tic-core-common" key="ticCoreFuncBase.fdKey" /></td>
					<td width="35%">
					<c:out value="${ticSoapMainForm.fdKey}" />
					</td>
				</tr>
			<tr>
				<td colspan="4" class="com_subject" style="font-size: 110%;font-weight: bold;">
				<bean:message bundle="tic-soap-connector" key="ticSoapMain.FuncInfo"/>
		      </td>
		      </tr>
				<!-- 绑定函数，soap版本 -->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapMain.wsBindFunc"/>
					</td><td width="35%" colspan="3" >
						<xform:text property="wsBindFunc" style="width:85%" />
					</td>
					
				</tr>
				<!-- 映射模版 -->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapMain.wsMapperTemplate"/>
					</td><td colspan="3" width="85%">
						<xform:textarea style="display:none"  showStatus="edit" property="wsMapperTemplate" >
						</xform:textarea>
						<!-- 传入参数 -->
						<div id="wsMapperTemplateIn" style="width: 100%"></div>
						<p></p>
						<!-- 传出参数 -->
						<div id="wsMapperTemplateOut" style="width: 100%"></div>
						<p></p>
						<!-- 出错信息-->
						<div id="wsMapperTemplateFault" style="width: 100%"></div>
					
					</td>
				</tr>
<%-- 				<!-- 函数说明 -->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapMain.wsBindFuncInfo"/>
					</td><td colspan="3" width="35%">
						<xform:text property="wsBindFuncInfo" style="width:85%" />
						
					</td>
				</tr> --%>
				
				<!-- 备注，创建人 -->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapMain.wsMarks"/>
					</td><td colspan="3" width="85%">
						<xform:text property="wsMarks" style="width:85%" />
					</td>
				</tr>
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
		url="/tic/soap/connector/tic_soap_query/tic_soap_view_history.jsp"
		charEncoding="UTF-8">
		<c:param name="formName" value="ticSoapMainForm" />
	</c:import>
	<c:import
			url="/tic/core/common/tic_core_trans_sett/ticCore_searchInfo_view_history.jsp"
			charEncoding="UTF-8">
	</c:import>
</table>
<html:hidden property="method_GET" />
</center>
<script id="tree_template" type="text/mustache">
<table class="erp_template" style="width:90%" >
	<caption>{{info.caption}}</caption>
		<tbody>
		<tr>
		{{#info.thead}}
			<td width='{{width}}' >{{th}}</td>
		{{/info.thead}}
		</tr>

		{{#info.tbody}}
		<tr  id="{{nodeKey}}"  {{^root}} class="child-of-{{parentKey}}" {{/root}} >
			<td><span {{^hasNext}} class='file' {{/hasNext}} {{#hasNext}} class='folder' {{/hasNext}}  >{{nodeName}}</span></td>
			<td style="display:none;">
				<input type="checkbox" disabled="true" name="nodeEnable" value="是否启用" commentNode="nodeEnable" nodeKey="{{nodeKey}}" {{comment.nodeEnable}} />
			</td>	
			<td>{{dataType}}</td>
			<td>
			 {{^hasNext}} 
				<label nodeKey="{{nodeKey}}" erp-node="true" >{{#comment.title}} {{comment.title}} {{/comment.title}} </label></td>
			 {{/hasNext}} 
		</tr>
	{{/info.tbody}}
	</tbody>
</table>
</script>
<script id="tree_out_template" type="text/mustache">
<table class="erp_template" style="width:90%" >
	<caption>{{info.caption}}</caption>
	<tbody width="100%" >
		<tr id="wsMapperTemplateOut_tr">
		{{#info.thead}}
			<td width='{{width}}' >{{th}}</td>
		{{/info.thead}}
		</tr>
		{{#info.tbody}}
		<tr  id="{{nodeKey}}"  {{^root}} class="child-of-{{parentKey}}" {{/root}} >
			<td><span {{^hasNext}} class='file' {{/hasNext}} {{#hasNext}} class='folder' {{/hasNext}}  >{{nodeName}}</span>

			{{^hasNext}} {{#comment.isDataList}} 数据列表属性 {{/comment.isDataList}} {{/hasNext}} 
			
			</td>

<%-- 
			<td>
				{{^hasNext}}
					<select name="disp" nodeKey="{{nodeKey}}" defaultValue="{{comment.disp}}" disabled="true">
					</select>
				{{/hasNext}}
			</td>
--%>
			<td>{{dataType}}</td>
			<td>
			 {{^hasNext}} 
				<input type="text"  class="inputread" readOnly commentNode="title"  {{#comment.title}}value="{{comment.title}}" {{/comment.title}}  nodeKey="{{nodeKey}}" erp-node="true" />
			 {{/hasNext}} 
			</td>
<%-- 		<td>
			 {{^hasNext}} 
			{{#comment.isSuccess}}<label><bean:message bundle="tic-soap-connector" key="ticSoapMain.lang.return"/></label><input  type="text" commentNode="isSuccess" style="width:40px"  nodeKey="{{nodeKey}}" value="{{comment.isSuccess}}"  readOnly class="inputread" /><bean:message bundle="tic-soap-connector" key="ticSoapMain.lang.success"/>{{/comment.isSuccess}}<br>
			{{#comment.isFail}}<label><bean:message bundle="tic-soap-connector" key="ticSoapMain.lang.return"/></label><input type="text"  commentNode="isFail" style="width:40px"  nodeKey="{{nodeKey}}" value="{{comment.isFail}}"  readOnly  class="inputread" /><bean:message bundle="tic-soap-connector" key="ticSoapMain.lang.fail"/>{{/comment.isFail}}
			 {{/hasNext}} 
			</td>--%>
		</tr>
	{{/info.tbody}}
	</tbody>
</table>
</script>
<script id="tree_fault_template" type="text/mustache">
<table class="erp_template" style="width:90%" >
	<caption>{{info.caption}}</caption>
	<tbody>
		<tr>
		{{#info.thead}}
			<td width='{{width}}' >{{th}}</td>
		{{/info.thead}}
		</tr>
		{{#info.tbody}}
		<tr  id="{{nodeKey}}"  {{^root}} class="child-of-{{parentKey}}" {{/root}} >
			<td><span {{^hasNext}} class='file' {{/hasNext}} {{#hasNext}} class='folder' {{/hasNext}}  >{{nodeName}}</span></td>
			<td>{{dataType}}</td>
			<td>
			 {{^hasNext}} 
				<label nodeKey="{{nodeKey}}" erp-node="true" >{{#comment.title}} {{comment.title}} {{/comment.title}} </label></td>
			 {{/hasNext}} 
		</tr>
	{{/info.tbody}}
	</tbody>
</table>
</script>

<script type="text/javascript">
//默认加载
$(document).ready(function(){
try{
	erp_loadDataInfo();
}catch(e){

}
		
});


/**********************
* 启动构造树形结构树
*/
function erp_loadDataInfo(){
	var init_str=$(document.getElementsByName("wsMapperTemplate")[0]).val();
	if(!init_str) {
	 return ;
	}
	var xmldom=ERP_parser.parseXml(init_str);
		reloadInfo(xmldom);
	}
</script>
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/custom_validations.js" type="text/javascript"></script>
</html:form>
<%@ include file="/resource/jsp/view_down.jsp"%>
