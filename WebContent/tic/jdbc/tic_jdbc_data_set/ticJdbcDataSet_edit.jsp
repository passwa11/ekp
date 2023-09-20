<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.tic.jdbc.forms.TicJdbcDataSetForm"%>	
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@page import="com.alibaba.fastjson.JSONArray,com.landray.kmss.util.StringUtil,com.landray.kmss.common.dao.HQLInfo,
java.util.List,com.landray.kmss.component.dbop.service.ICompDbcpService,com.landray.kmss.component.dbop.model.CompDbcp" %>
<script>
	// 该脚本引入在jshead之前
	Com_IncludeFile("mustache.js", "${KMSS_Parameter_ContextPath}sys/xform/maindata/resource/js/", "js", true);
</script>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="ticJdbcDataSet_lang.jsp"%>
<% 
    response.setHeader("X-UA-Compaticle","IE=edge");
	String fdEnviromentId = request.getParameter("fdEnviromentId");
	if(StringUtil.isNull(fdEnviromentId)){
		TicJdbcDataSetForm ticJdbcDataSetForm = (TicJdbcDataSetForm) request.getAttribute("ticJdbcDataSetForm");
		if(ticJdbcDataSetForm!=null){
			fdEnviromentId = ticJdbcDataSetForm.getFdEnviromentId();
		}
	}
	request.setAttribute("fdEnviromentId", fdEnviromentId);
	JSONArray dbcpList = new JSONArray();
	String jsonTemplate = "{fdName:'!{fdName}',compId:'!{compId}'}";
	if (StringUtil.isNotNull(fdEnviromentId)) {
		String whereBlock = "ticSceneCompDbcp.fdEnviromentId=:fdEnviromentId";
		HQLInfo info = new HQLInfo();
		info.setWhereBlock(whereBlock);
		info.setSelectBlock("ticSceneCompDbcp.fdDataSourceId");
		info.setFromBlock(
				"com.landray.kmss.tic.scene.model.TicSceneCompDbcp ticSceneCompDbcp");
		info.setParameter("fdEnviromentId",
				fdEnviromentId);;
		List<String> list = (List<String>) ((ICompDbcpService)SpringBeanUtil.getBean("compDbcpService"))
				.findValue(info);
		for (String fdDataSourceId : list) {
			CompDbcp compDbcp =null;
			try{
				compDbcp=(CompDbcp)((ICompDbcpService)SpringBeanUtil.getBean("compDbcpService")).findByPrimaryKey(fdDataSourceId,null,true);
			}
			catch(Exception e){
				
			}
			if(compDbcp==null){
				continue;
			}
			String fdName = compDbcp.getFdName();
			String elementString = jsonTemplate.replace("!{fdName}",
					fdName).replace("!{compId}",
							fdDataSourceId);
			dbcpList.add(com.alibaba.fastjson.JSON.parseObject(elementString));
		} 
	}else {
		List<CompDbcp> compDbcpList = ((ICompDbcpService)SpringBeanUtil.getBean("compDbcpService")).findList(null,
				null);
		for (CompDbcp compDbcp : compDbcpList) {
			String elementString = jsonTemplate.replace("!{fdName}",
					compDbcp.getFdName()).replace("!{compId}",
					compDbcp.getFdId());
			dbcpList.add(com.alibaba.fastjson.JSON.parseObject(elementString));
		}
	}
	request.setAttribute("dbcpList", dbcpList);
%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|data.js|json2.js");
</script>
<script type="text/javascript">
var fdId='${param.fdId}';
var fdInvoke='${ticJdbcDataSetForm.fdInvoke}';
Com_IncludeFile("jquery.treeTable.css", "${KMSS_Parameter_ContextPath}tic/core/resource/css/", "css", true);
Com_IncludeFile("jquery.treeTable.js", "${KMSS_Parameter_ContextPath}tic/core/resource/js/", "js", true);
Com_IncludeFile("jdbcDataSet.js", "${KMSS_Parameter_ContextPath}tic/jdbc/resource/js/", "js", true);
Com_IncludeFile("tools.js","${KMSS_Parameter_ContextPath}tic/core/resource/js/","js",true);
Com_IncludeFile("jquery.blockUI.js","${KMSS_Parameter_ContextPath}tic/core/resource/js/","js",true);
</script>
<html:form action="/tic/jdbc/tic_jdbc_data_set/ticJdbcDataSet.do">
<div id="optBarDiv">
<input type=button value="${lfn:message('home.help')}" onclick="Com_OpenWindow(Com_Parameter.ContextPath+'tic/jdbc/help/func_setting.html','_blank');">
	<c:if test="${ticJdbcDataSetForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="before_submit('update');">
	</c:if>
	<c:if test="${ticJdbcDataSetForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="before_submit('save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="before_submit('saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-jdbc" key="table.ticJdbcDataSet"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.docSubject"/>
		</td>
		<td width="35%">
			<xform:text property="fdName" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.fdDataSource"/>
		</td><td width="35%">
			<xform:select property="fdDataSource" style="float: left;" showStatus="edit" value="${param.fdDataSource }">
			    <c:forEach items="${dbcpList}" var="item" varStatus="status">
                    <xform:simpleDataSource value="${item.compId}">${item.fdName}</xform:simpleDataSource>
                 </c:forEach>
			</xform:select>
		</td>
	</tr>
	<tr>
		
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.docCategory"/>
		</td><td width="35%">
			<xform:dialog  subject="${lfn:message('tic-jdbc:ticJdbcDataSet.docCategory') }" propertyId="fdCategoryId" style="width:90%"
					propertyName="fdCategoryName" dialogJs="Tic_treeDialog()">
			</xform:dialog>
		</td>
		<td class="td_normal_title" width=15%>
				<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.fdIsAvailable"/>
			</td><td  width="35%">
				<xform:radio property="fdIsAvailable" >
					<xform:enumsDataSource enumsType="tic_yesno" />
				</xform:radio>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%><bean:message
						bundle="tic-core-common" key="ticCoreFuncBase.fdKey" /></td>
			<td width="35%" colspan="3"> 
					<xform:text property="fdKey" required="true" style="width:35%"  htmlElementProperties="ONBLUR=\"key_unique_Submit()\""></xform:text>
					<div id="key_error" style="display:inline-block;color:red;"></div>
			</td>
	</tr>
	<%-- 	<tr>
	<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.fdKey"/>
		</td>
		<td width="35%">
			<xform:text property="fdKey" subject="${lfn:message('tic-jdbc:ticJdbcDataSet.fdKey')}" style="width:85%" validators="myAlphanum"/></br>
			<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.fdKeyTip"/>
		</td> 
	</tr>--%>
	<tr>
		<td colspan="4" class="com_subject" style="font-size: 110%;font-weight: bold;">
				<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.FuncInfo"/>
		  </td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.fdSqlExpression"/>
		</td>
		<td colspan="3" width="85%">
			
			<div class="div_sql_help">
				<input type="button" class="lui_form_button" style="cursor:pointer;" value="${lfn:message('tic-jdbc:ticJdbcDataSet.sqlTool')}" onclick="fdSqlGenerationWizard();" />
				<input type="button" class="lui_form_button" style="cursor:pointer;" value="${lfn:message('tic-jdbc:ticJdbcDataSet.help')}" onclick="fdSqlGenerationHelp();" />
			</div>
			<xform:textarea property="fdSqlExpression" style="width:85%" htmlElementProperties="onchange='fdSqlExpression_change();'"></xform:textarea>
		     <input type="button" onclick="sqlPreview();" class="lui_form_button" style="cursor:pointer;" value="<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.extractStructure"/>"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.fdSqlExpressionTest"/>
		</td><td colspan="3" width="85%">
			<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.fdSqlExpressionTestMsg"/>
			</font>
			<xform:textarea property="fdSqlExpressionTest" style="width:85%" htmlElementProperties="onchange='fdSqlExpression_change();'"></xform:textarea>	
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" colspan="4" width=100% align="center">
			<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.fdData"/>
			<xform:textarea property="fdData" style="width:85%; display:none;"></xform:textarea>
		</td>
	</tr>
	<tr>	
		<td class="td_normal_title" width=15%>
			${lfn:message('tic-core-common:ticCoreCommon.searchSetting')}
		</td>
		<td width="35%" colspan="3" >
			 <div id="jdbc_data_set_search" ></div>
		</td>
	</tr>
	<tr>	
		<td class="td_normal_title" width=15%>
			${lfn:message('tic-core-common:ticCoreFuncBase.fdParaIn')}
		</td>
		<td width="35%" colspan="3" >
			 <div id="jdbc_data_set_in" ></div>
		</td>
	</tr>
   <tr>	
		<td class="td_normal_title" width=15%>
			${lfn:message('tic-core-common:ticCoreFuncBase.fdParaOut')}
		</td>
		<td width="35%" colspan="3" >
			 <div id="jdbc_data_set_out"></div>
		</td>
	</tr>
				<tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('tic-core-common:ticCoreFuncBase.fdInvoke')}
                    </td>
                    <td colspan="3" width="85.0%">
                       <xform:checkbox property="fdInvoke"  htmlElementProperties="on_change_invoke=\"invokeSelect(source)\"">
                           <xform:enumsDataSource enumsType="tic_core_invoke" />
                       </xform:checkbox>
                       <br>
                       <bean:message bundle="tic-core-common" key="ticCoreFuncBase.fdInvoke.explain"/>
                    </td>
                </tr>
				<tr id="auth_readers" style="display:none">
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('tic-core-common:ticCoreFuncBase.authReaders')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_authReaderIds" _xform_type="address">
                            <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                        </div>
                    </td>
                </tr>
			</table>
			</td>
		</tr>
</table>
</center>
<!-- js search脚本模板 -->
<script id="jdbc_data_set_template_search" type="text/mustache">
<table class="erp_template" style="table-layout:fixed;width:100%" >
<col style="width: 24%" />
<col style="width: 24%" />
<col style="width: 20%" />
<col style="width: 17.5%" />
<col style="width: 14.5%" />
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
			<td style="display:none;">
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
	function Tic_treeDialog() {
		Dialog_Tree(false, 'fdCategoryId', 'fdCategoryName', ',', 
				'ticCoreBusiCateTreeService&parentId=!{value}&fdAppType=${param.fdAppType}', 
				'所属分类', 
				null, null, '${ticCoreBusiCateForm.fdCategoryId}', null, null, 
				'所属分类');
	}
	function fdSqlGenerationHelp(){
		var url = Com_Parameter.ContextPath + "tic/jdbc/tic_jdbc_data_set/ticJdbcDataExtend_edit_sqlGeneration_help.jsp";
		Com_OpenWindow(url,"_blank");
	}
	function invokeSelect(source){
		if(source.value=="0"){
			 if(source.checked)
					document.getElementById("auth_readers").style.display = "";
				else
					document.getElementById("auth_readers").style.display = "none"; 
		}
	} 
	
	var fdEnviromentId='${fdEnviromentId}';

</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>