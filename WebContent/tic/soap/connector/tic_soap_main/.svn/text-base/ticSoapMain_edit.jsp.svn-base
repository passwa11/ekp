<%@page import="com.landray.kmss.tic.soap.connector.forms.TicSoapMainForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/tic/soap/connector/resource/js/resource_properties.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/tic/soap/erp-soapui.tld" prefix="soap"%>
<%
	String fdEnviromentId = request.getParameter("fdEnviromentId");
	if(StringUtil.isNull(fdEnviromentId)){
		TicSoapMainForm ticSoapMainForm = (TicSoapMainForm) request.getAttribute("ticSoapMainForm");
		if(ticSoapMainForm!=null){
			fdEnviromentId = ticSoapMainForm.getFdEnviromentId();
		}
	}
	request.setAttribute("fdEnviromentId", StringUtil.getString(fdEnviromentId));
%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|json2.js");
Com_IncludeFile("dialog.js", null, "js");
var fdId='${param.fdId}';
//var fdReturnValue='${ticSoapMainForm.fdReturnValue}';
</script>
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/mustache.js" type="text/javascript"></script>
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/erp.parser.js" type="text/javascript"></script>
<link href="${KMSS_Parameter_ContextPath}tic/core/resource/css/jquery.treeTable.css" rel="stylesheet" type="text/css" />

<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/jquery.treeTable.js" type="text/javascript"></script>
<script src="${KMSS_Parameter_ContextPath}tic/soap/connector/tic_soap_main/ticSoapFunc.js" type="text/javascript"></script>
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/custom_validations.js" type="text/javascript"></script>
<script type="text/javascript">
/************标记修正。。。************
 * 使用国际化的资源文件
 ************************/
 var titleValue= '<bean:message bundle="tic-soap-connector" key="table.ticSoapSettCategory"/>';
 
$(function(){
	var spanNode = document.createElement("span");
	spanNode.id = "Include_Custom_Validations_Span_Id";
	document.body.appendChild(spanNode);
	$("#Include_Custom_Validations_Span_Id").load(Com_Parameter.ContextPath +
			"tic/core/resource/jsp/custom_validations.jsp");
	if('${ticSoapMainForm.fdInvoke}'=="0"){
		document.getElementById("auth_readers").style.display = "";
	}
});
function invokeSelect(source){
	if(source.value=="0"){
		 if(source.checked)
				document.getElementById("auth_readers").style.display = "";
			else
				document.getElementById("auth_readers").style.display = "none"; 
	}
} 

</script>

<html:form action="/tic/soap/connector/tic_soap_main/ticSoapMain.do">

<div id="optBarDiv">
	<input type=button value="${lfn:message('home.help')}"
			onclick="Com_OpenWindow(Com_Parameter.ContextPath+'tic/soap/help/tic_Soap_Main.html','_blank');"/>
	<c:if test="${ticSoapMainForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Erp_webSubmit(document.ticSoapMainForm, 'update');">
	</c:if>
	<c:if test="${ticSoapMainForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Erp_webSubmit(document.ticSoapMainForm,'save' )">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Erp_webSubmit(document.ticSoapMainForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-soap-connector" key="ticSoapMain.func.config"/></p>
<div id="mydiv"></div>

<center>

<table id="Label_Tabel" width=95%>
	<!-- 主文档 -->
	<tr LKS_LabelName="<bean:message bundle="tic-soap-connector" key="table.ticSoapMain.main"/>">
		<td>
			<table class="tb_normal" width="100%">
				<!-- 函数名称-->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapMain.func.docSubject"/>
					</td><td width="35%">
						<xform:text  property="fdName" style="width:85%" />
					</td>
					<td class="td_normal_title" width=15%><bean:message bundle="tic-soap-connector" key="ticSoapMain.wsServerSetting"/></td>
					<td width="35%">		
						<c:if test="${empty fdEnviromentId}">
							<xform:select property="wsServerSettingId" value="${param.wsServerSettingId}"
								required="true" onValueChange="takeOutFunc();" >
							<xform:beanDataSource serviceBean="ticSoapSettingService"
							selectBlock="fdId,docSubject" whereBlock="ticSoapSetting.fdAppType='${param.fdAppType}'" orderBy="" />
							</xform:select> 
						</c:if>
						<c:if test="${not empty fdEnviromentId}">
							<xform:select property="wsServerSettingId" value="${param.wsServerSettingId}"
								required="true" onValueChange="takeOutFunc();" >
								<xform:beanDataSource serviceBean="ticSoapSettingService"
							selectBlock="fdId,docSubject" whereBlock="ticSoapSetting.fdAppType='${param.fdAppType}' and ticSoapSetting.fdEnviromentId='${fdEnviromentId}'" orderBy="" />
							</xform:select> 
						</c:if>
					</td>
				</tr>
				<!-- 是否启用，版本 -->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapMain.docCategory"/>
					</td><td width="35%">
					<html:hidden property="fdCategoryId" /> <xform:text
						property="fdCategoryName" style="width:34%" /> <a
						href="#" 
						onclick="categoryJs();">
					<bean:message key="dialog.selectOther" /> </a>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapMain.wsEnable"/>
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
					<xform:text property="fdKey" required="true" style="width:35%"  htmlElementProperties="ONBLUR=\"key_unique_Submit()\""></xform:text>
					<div id="key_error" style="display:inline-block;color:red;"></div>
					</td>
				</tr>
			<tr>
				<td colspan="4" class="com_subject" style="font-size: 110%;font-weight: bold;">
				<bean:message bundle="tic-soap-connector" key="ticSoapMain.FuncInfo"/>
		      </td>
		      </tr>
				<!-- 绑定函数 -->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapMain.wsBindFunc"/>
					</td><td width="35%">
						<input type="hidden" value="<c:out value='${ticSoapMainForm.wsBindFunc}' />" name="wsBindFuncHidden">
						<xform:select  property="wsBindFunc" style="float:left" onValueChange="bindFuncChange();">
						  <soap:erpSoapFuncDataSource ticSoapSettingId="${ticSoapMainForm.wsServerSettingId}" ticSoapversion="${ticSoapMainForm.wsSoapVersion}"></soap:erpSoapFuncDataSource>
						</xform:select>
						<!-- 抽取函数 -->
						<input type="button" style="float: left;" class="btnopt" onclick="loadFuncInfo()" 
							title="<bean:message bundle="tic-soap-connector" key="ticSoapMain.func.getFunc"/>" 
							value="<bean:message bundle="tic-soap-connector" key="ticSoapMain.func.getFunc"/>">
						<img style="display: none; float: left;" id="erp_loading_bar" alt="loading"  src="${KMSS_Parameter_ResPath}style/common/images/loading.gif"   />
					</td>				
				</tr>
				<!-- 映射模版 -->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapMain.wsMapperTemplate"/>
					</td><td colspan="3" width="85%">
						<xform:textarea  style="display:none" showStatus="edit" property="wsMapperTemplate" >
						</xform:textarea>
						<!-- 传入参数 -->
						<div id="wsMapperTemplateIn" style="width: 100%"></div>
						<p></p>
						<!-- 传出参数 -->
						<div id="wsMapperTemplateOut" style="width: 100%"></div>
						<p></p>
						<!-- 错误信息 -->
						<div id="wsMapperTemplateFault" style="width: 100%"></div>
					</td>
				</tr>


				<!-- 函数说明 -->
				<%-- <tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapMain.wsBindFuncInfo"/>
					</td><td colspan="3" width="85%">
						<html:textarea
							property="wsBindFuncInfo" style="width:90%;"
							styleClass="inputmul" /> 
					</td>
				</tr> --%>
				<!-- 备注 -->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdMarks"/>
					</td><td colspan="3" width="85%">
						<xform:textarea property="wsMarks" style="width:90%" />
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
			<%-- 	<!-- 创建者,创建时间 -->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapMain.docCreator"/>
					</td><td width="35%">
						<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-soap-connector" key="ticSoapMain.docCreateTime"/>
					</td><td width="35%">
						<xform:datetime property="docCreateTime" showStatus="view" />
					 
					</td>
				</tr> --%>
			</table>
		</td>
	</tr>
</table>

</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script type="text/javascript">
	$KMSSValidation();	
	function categoryJs(){
		Dialog_Tree(false, 'fdCategoryId', 'fdCategoryName', ',', 'ticCoreBusiCateTreeService&parentId=!{value}&fdAppType=${JsParam.fdAppType}', 
				'函数分类', null, null, 
				'', null, null, 
				'函数分类');
	}

	//默认加载
	$(document).ready(function(){
		// 增加验证
		FUN_AddValidates("wsBindFunc:required");
		// 版本
		var soapV = "${ticSoapMainForm.wsSoapVersion}";
		var wsServerSettingId="${param.wsServerSettingId}";
		//backLoadVersion("", soapV);
		try{
			erp_loadDataInfo();
			if(wsServerSettingId){
				 takeOutFunc() ;
			}
		}catch(e){
	
		}
		/* var fdReturnValue='${ticSoapMainForm.fdReturnValue}';
		setTimeout(function(){ 
			if(fdReturnValue){
				$("input[name='fdReturnFlag']").attr("checked","checked");
				erp_toggle($("input[name='fdReturnFlag']")[0],'');
				$("#returnFlag option[value='${ticSoapMainForm.fdReturnValue}']").attr("selected","selected");
		}
			}, 100); */
	});

/**********************
 * 启动构造树形结构树
 */
	function erp_loadDataInfo(){
		var init_str=$(document.getElementsByName("wsMapperTemplate")[0]).val();
		var xmldom = null;
		if(init_str) {
			xmldom=ERP_parser.parseXml(init_str);
		}
		reloadInfo(xmldom);
	}
//加载所有方法
	function erp_loadWsFunc(){
 			var settingId=$(document.getElementsByName("wsServerSettingId")[0]).val();
 			var soapversion=$(document.getElementsByName("wsSoapVersion")[0]).val();
			var func=$(document.getElementsByName("wsBindFunc")[0]).val();
			if(!(settingId&&soapversion&&func))
			{
				//alert(settingId+soapversion+func);
				return ;
			}
			var data = new KMSSData();
			var bean_str="ticSoapSelectOptionsBean&serviceId=!{serviceId}&soapversion=!{soapversion}"
			bean_str=bean_str.replace("!{serviceId}",serviceId).replace("!{soapversion}",soapVerson);
			
			data.SendToBean(bean_str, 
					function(rtnVal){
						var options=[];
						if(!rtnVal){
							return ;
						}
						options = rtnVal.GetHashMapArray();
						refreshOption(document.getElementsByName("wsBindFunc")[0],options,true,func);
		});
	}
	
	// 绑定函数变更后执行的操作
	function bindFuncChange(){
		FUN_RemoveValidMsg("wsBindFunc");
		$("#wsMapperTemplateIn").empty();
		$("#wsMapperTemplateOut").empty();
		$("#wsMapperTemplateFault").empty();
		loadFuncInfo();
	}
	
	var fdEnviromentId='${fdEnviromentId}';
</script>

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
				<input onclick="partCheckedClick(this, '{{nodeKey}}');" type="checkbox" name="nodeEnable" value="是否启用" commentNode="nodeEnable" nodeKey="{{nodeKey}}" {{comment.nodeEnable}} />
			</td>
			<td><input type="text" readonly nodeKey="{{nodeKey}}" commentNode="ctype" class="inputread" value="{{dataType}}"/></td>
			<td>
			{{^hasNext}} 
				<input type="text" class="inputsgl" commentNode="title"   {{#comment.title}}value="{{comment.title}}" {{/comment.title}}  nodeKey="{{nodeKey}}" erp-node="true" />
			{{/hasNext}}
			</td>
		</tr>
	{{/info.tbody}}
	</tbody>
</table>
</script>
<script id="tree_out_template" type="text/mustache">
<table class="erp_template" style="width:90%" >
	<caption>{{info.caption}}</caption>
	<tbody>
		<tr>
		{{#info.thead}}
			<td width='{{width}}' >{{th}}</td>
		{{/info.thead}}
<%--			<td rowspan="100%" style="vertical-align:top;">
            <input type="checkbox" name="fdReturnFlag" onclick="erp_toggle(this,'{{nodeKey}}')">业务异常参数配置<br>
             <select id="returnFlag" name="fdReturnValue"  style="display:none;margin-top:10px;">
                  <option value ='' >==请选择==</option>
           </select>
			<div id="{{nodeKey}}_flag" style="float:left;padding:3px;display:none;">
			<label><bean:message bundle="tic-soap-connector" key="ticSoapMain.lang.return"/></label><input type="text" name="fdSuccess" value="${ticSoapMainForm.fdSuccess}" commentNode="isSuccess" style="width:40px"  nodeKey="{{nodeKey}}" {{#comment.isSuccess}}value="{{comment.isSuccess}}" {{/comment.isSuccess}} class="inputsgl" /><bean:message bundle="tic-soap-connector" key="ticSoapMain.lang.success"/><br>
			<label><bean:message bundle="tic-soap-connector" key="ticSoapMain.lang.return"/></label><input type="text" name="fdFail"  value="${ticSoapMainForm.fdFail}" commentNode="isFail" style="width:40px"  nodeKey="{{nodeKey}}" {{#comment.isFail}}value="{{comment.isFail}}" {{/comment.isFail}}  class="inputsgl" /><bean:message bundle="tic-soap-connector" key="ticSoapMain.lang.fail"/>
			<div style="clear:both"></div>
			</div>
			</td>--%>
		</tr>
		{{#info.tbody}}
		<tr  id="{{nodeKey}}"  {{^root}} class="child-of-{{parentKey}}" {{/root}} >

			<td><span {{^hasNext}} class='file' {{/hasNext}} {{#hasNext}} class='folder' {{/hasNext}}  >{{nodeName}}</span>
			{{^hasNext}}<input type="radio" name="isDataList" style="display:none;" {{#comment.isDataList}} checked {{/comment.isDataList}} nodeKey="{{nodeKey}}" commentNode="isDataList">{{/hasNext}} 
			</td>

<%-- 
			<td>
				{{^hasNext}}
					<select name="disp" nodeKey="{{nodeKey}}" commentNode="disp" defaultValue="{{comment.disp}}">
					</select>
				{{/hasNext}}
			</td>
	--%>
			<td><input type="text" readonly nodeKey="{{nodeKey}}" commentNode="ctype" class="inputread" value="{{dataType}}"/></td>
			<td>
			{{^hasNext}} 
				<input type="text" class="inputsgl" commentNode="title"  {{#comment.title}}value="{{comment.title}}" {{/comment.title}}  nodeKey="{{nodeKey}}" erp-node="true" />
			{{/hasNext}} 
			</td>
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
				<input type="text" class="inputsgl" commentNode="title"   {{#comment.title}}value="{{comment.title}}" {{/comment.title}}  nodeKey="{{nodeKey}}" erp-node="true" />
			{{/hasNext}}
			</td>
		</tr>
	{{/info.tbody}}
	</tbody>
</table>
</script>

</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
