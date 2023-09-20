<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.tic.core.register.RegisterPlugin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
    <% 
    	pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); 
    	request.setAttribute("registers", RegisterPlugin.getExtensionArray());
    %>

<template:include ref="default.view">
    <template:replace name="head">
	
<script type="text/javascript">
Com_IncludeFile("jquery.js|data.js|json2.js");
Com_IncludeFile("doclist.js|dialog.js", null, "js");
Com_IncludeFile("base64.js");
Com_IncludeFile("json2.js");

function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
   </template:replace>
	
    <template:replace name="title">
			<c:out value="${ticRestMainForm.fdName} - " />
			<c:out value="${ lfn:message('tic-rest-connector:table.ticRestMain') }" />
	</template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			 <kmss:auth requestURL="/tic/soap/connector/tic_rest_main/ticRestMain.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
			<ui:button text="${ lfn:message('button.edit') }" order="2" 
					onclick="Com_OpenWindow('ticRestMain.do?method=edit&fdId=${param.fdId}','_self');">
				</ui:button>
            </kmss:auth>
            <kmss:auth requestURL="/tic/soap/connector/tic_rest_main/ticRestMain.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
                <ui:button text="${ lfn:message('button.delete') }" order="2" 
					onclick="if(!confirmDelete())return;Com_OpenWindow('ticRestMain.do?method=delete&fdId=${param.fdId}','_self');">
				</ui:button>
           </kmss:auth>
           <ui:button text="${ lfn:message('button.close') }" order="2" 
				onclick="Com_CloseWindow();">
			</ui:button>
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('tic-rest-connector:table.ticRestMain') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">

<html:form action="/tic/rest/connector/tic_rest_main/ticRestMain.do">

<p class="txttitle"><bean:message bundle="tic-rest-connector" key="ticRestMain.manager"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="tic-rest-connector" key="ticRestMain.docSubject"/>
		</td>
		<td width="35%">
			<xform:text property="fdName" style="width:85%" />
		</td>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="tic-rest-connector" key="ticRestMain.fdServerSetting"/>
		</td><td width="35%">
			${ticRestMainForm.ticRestSettingName}
		</td>
	</tr>

	<tr>	
		<td class="td_normal_title" width="15%">
			<bean:message bundle="tic-rest-connector" key="ticRestMain.docCategory"/>
		</td><td width="35%">
			<xform:dialog required="true" subject="${lfn:message('tic-rest-connector:ticRestMain.docCategory') }" propertyId="fdCategoryId" style="width:90%"
					propertyName="fdCategoryName" dialogJs="Tic_treeDialog()">
			</xform:dialog>
		</td>
		<td class="td_normal_title" width="15%">
				<bean:message bundle="tic-rest-connector" key="ticRestMain.fdIsAvailable"/>
			</td><td  width="35%">
				<xform:radio property="fdIsAvailable">
					<xform:enumsDataSource enumsType="rest_yesno" />
				</xform:radio>
		</td>
	</tr>
	<tr>	
		<td class="td_normal_title" width="15%">
				<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReMark"/>
			</td><td  colspan="3" width="85%">
			<xform:textarea property="fdRemark" style="width:85%" />
		</td>
	</tr>

	<tr>
		<td colspan="4" class="com_subject" style="font-size: 110%;font-weight: bold;">
		<!--
			<ui:switch property="fdOauthEnable" checked="${ticRestMainForm.fdOauthEnable }" onValueChange="changeOrgAeraEnable();" enabledText="${lfn:message('tic-rest-connector:ticRestMain.fdOauthEnable')}" disabledText="${lfn:message('tic-rest-connector:ticRestMain.fdOauthEnable')}"></ui:switch>
		-->
			<bean:message bundle="tic-rest-connector" key="ticRestMain.fdOauthEnable"/>
			&nbsp;
			<input type="checkbox" disabled name="fdOauthEnable"  <c:if test="${ticRestMainForm.fdOauthEnable eq 'true'}">checked</c:if> value="${ticRestMainForm.fdOauthEnable}">
	  </td>
	</tr>
 	<tr id="idUseCustAt">
		<td class="td_normal_title" colspan="4">
			<bean:message bundle="tic-rest-connector" key="ticRestMain.fdUseCustAt"/>
			<input type="checkbox" name="fdUseCustAt" disabled  <c:if test="${ticRestMainForm.fdUseCustAt eq 'true'}">checked</c:if> value="${ticRestMainForm.fdUseCustAt}">
				<div style="display: inline-block;float:right;">
					<ui:button style="width:180px" onclick="getAccessToken('${ticRestMainForm.fdId}');" text="调式获取AccessToken" />
				</div>
	  </td>
	</tr>
	<tr id="fdOauthEnableZone" style="display:none;">
		<td colspan="4">
		<table class="tb_normal" id="oauthDef" width=100% style="display:none;">
			<tr>	
				<td class="td_normal_title" width=15%>
					<bean:message bundle="tic-rest-connector" key="ticRestMain.fdAccessTokenClazz"/>
					<td  width="85%" colspan="3">
					<xform:text property="fdAccessTokenClazz" style="width:85%" />
					<br>
					<font color="red"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdUseCustAt.note"/></font>
				</td>
			</tr>
		</table>
		<table class="tb_normal" id="oauthSet" width=100%>
			<tr>	
				<td class="td_normal_title" width=15%>
					<bean:message bundle="tic-rest-connector" key="ticRestMain.fdAccessTokenURL"/>
					<td  width="85%" colspan="3">
						<xform:text property="fdAccessTokenURL" style="width:85%" />
						<br>
						<font color="red"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdOauthEnable.note"/></font>
				</td>
			</tr>
			<tr>	
				<td class="td_normal_title" width=15%>
					<bean:message bundle="tic-rest-connector" key="ticRestMain.fdAppId"/>
				</td><td width="35%">
						<xform:text property="fdAppId" style="width:85%" />
				</td>
				<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-rest-connector" key="ticRestMain.fdAgentId"/>
					</td><td  width="35%">
						<xform:text property="fdAgentId" style="width:85%" />
				</td>
			</tr>
			<tr>	
				<td class="td_normal_title" width=15%>
					<bean:message bundle="tic-rest-connector" key="ticRestMain.fdCorpId"/>
				</td><td width="35%">
						<xform:text property="fdCorpId" style="width:85%" />
				</td>
				<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-rest-connector" key="ticRestMain.fdCorpSecret"/>
					</td><td  width="35%">
						<xform:text property="fdCorpSecret" style="width:85%" />
				</td>
			</tr>
			<tr>	
				<td class="td_normal_title" width=15%>
					<bean:message bundle="tic-rest-connector" key="ticRestMain.fdToken"/>
				</td><td width="35%">
						<xform:text property="fdToken" style="width:85%" />
				</td>
				<td class="td_normal_title" width=15%>
						<bean:message bundle="tic-rest-connector" key="ticRestMain.fdAesKey"/>
					</td><td  width="35%">
						<xform:text property="fdAesKey" style="width:85%" />
				</td>
			</tr>
		</table>
	  </td>
  </tr>

	<tr>
		<td colspan="4" class="com_subject" style="font-size: 110%;font-weight: bold;">
			<bean:message bundle="tic-rest-connector" key="ticRestMain.reqMethod.set"/>
	  </td>
	</tr>
	<tr>
		<td colspan="4">
			<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqMethod"/>
			<xform:select property="fdReqMethod" showStatus="readOnly">
				<xform:enumsDataSource enumsType="rest_fdReqMethod_values" />
			</xform:select>
			<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqURL"/>
			<xform:text property="fdReqURL" style="width:65%" showStatus="readOnly"/>
	  </td>
	</tr>
	<tr>	
		<td width="50%" colspan="2">
			<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqHeader.add"/>
			<select id="idReqAccept" disabled>
				<option value=""><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqHeader.add.reqAccept"/></option>
				<option value="json"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqHeader.add.reqAccept.json"/></option>
				<option value="xml"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqHeader.add.reqAccept.xml"/></option>
				<option value="text"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqHeader.add.reqAccept.text"/></option>
				<option value="wildcard"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqHeader.add.reqAccept.wildcard"/></option>
				<option value="other"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqHeader.add.reqAccept.other"/></option>
			</select>&nbsp;
			<select id="idReqAuth" disabled>
				<option value=""><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqHeader.add.reqAuth"/></option>
				<option value="base"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqHeader.add.reqAuth.base"/></option>
			</select>&nbsp;
			<select id="idReqHeader" disabled>
				<option value=""><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqHeader.add.reqHeader"/></option>
				<option value="add"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqHeader.add.reqHeader"/></option>
			</select>
		</td>
		<td width="50%" colspan="2">
			<table id="TABLE_DocList_Header" class="tb_normal" width=95% style="margin-left: 20px;">
				<tr>
					<td width="95%" align="center" class="td_normal_title"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqHeader"/></td>
					<td width="4%" align="center" class="td_normal_title">
					</td>
				</tr>
				<!--基准行-->
				<tr KMSS_IsReferRow="1" style="display:none">
					<td width="48%">
						<input class="inputsgl" subject="<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.name"/>" style="width:90%" name="fdReqBizHearderList[!{index}].name">
					</td>
				</tr>
				<!--内容行-->
				<%
				JSONArray fdReqBizHearderList = (JSONArray)request.getAttribute("fdReqBizHearderList");
				if(fdReqBizHearderList!=null){
					for(int i=0;i<fdReqBizHearderList.size();i++){
						String reqHeaderValue = fdReqBizHearderList.getString(i);
				%>
				
					<tr KMSS_IsContentRow="1">
						<td width="48%">
							<input validate="required" class="inputsgl" readonly style="width:90%" subject="<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.name"/>" name="fdReqBizHearderList[<%=i%>].name"  value="<%=reqHeaderValue%>">
						</td>
					</tr>
			<%}
				}%>
			</table>
		</td>
	</tr>

	<tr>
		<td colspan="4" class="com_subject" style="font-size: 110%;font-weight: bold;">
			<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set"/>

			<div style="display: inline-block;float:right;">
				<ui:button style="width:120px" onclick="getRestData('${ticRestMainForm.fdId}');" text="调式获取数据" />
			</div>

	  </td>
	</tr>
	<tr>
		<td colspan="2" align="center" width="50%" class="td_normal_title">
			<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.input"/>
	  </td>
		<td colspan="2" align="center" width="50%" class="td_normal_title">
			<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.output"/>
	  </td>
	</tr>
	<tr>
		<td colspan="2" width="50%">
			<table id="TABLE_DocList_Input" class="tb_normal" width=100% >
				<tr>
					<td width="32%" align="center" class="td_normal_title"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.name"/></td>
					<td width="32%" align="center" class="td_normal_title"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.title"/></td>
					<td width="24%" align="center" class="td_normal_title"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.type"/></td>
					<td width="12%" align="center" class="td_normal_title">
					</td>
				</tr>

				<!--内容行-->
				<%
				JSONArray fdReqBizParamInputList = (JSONArray)request.getAttribute("fdReqBizParamInputList");
				if(fdReqBizParamInputList!=null){
					for(int i=0;i<fdReqBizParamInputList.size();i++){
						JSONObject reqBizParamInput = fdReqBizParamInputList.getJSONObject(i);
				%>

					<tr KMSS_IsContentRow="1">
						<td width="32%">
							<input validate="required" readonly class="inputsgl" style="width:75%" subject="<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.name"/>" name="fdReqBizParamInputList[<%=i%>].name"  value="<%=reqBizParamInput.getString("name")%>">
						</td>
						<td width="32%">
							<input validate="required" readonly class="inputsgl" style="width:75%" subject="<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.title"/>" name="fdReqBizParamInputList[<%=i%>].title"  value="<%=reqBizParamInput.getString("title")%>">
						</td>			
					<td width="24%">
						<input type="hidden" name="fdReqBizParamInputList[<%=i%>].children" value='<%=reqBizParamInput.getString("children")%>'>
						<select validate="required" disabled subject="<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.title"/>" style="width:72%" name="fdReqBizParamInputList[<%=i%>].type">
							<option value="string" <%=("string".equals(reqBizParamInput.getString("type"))?"selected":"")%>>字符串型</option>
							<option value="object" <%=("object".equals(reqBizParamInput.getString("type"))?"selected":"")%>>对象型</option>
							<option value="array" <%=("array".equals(reqBizParamInput.getString("type"))?"selected":"")%>>数组型</option>
							<option value="int" <%=("int".equals(reqBizParamInput.getString("type"))?"selected":"")%>>整型</option>
							<option value="long" <%=("long".equals(reqBizParamInput.getString("type"))?"selected":"")%>>长整型</option>
							<option value="double" <%=("double".equals(reqBizParamInput.getString("type"))?"selected":"")%>>浮点型</option>
							<option value="boolean" <%=("boolean".equals(reqBizParamInput.getString("type"))?"selected":"")%>>布尔型</option>
						</select>
					</td>						
						<td width="12%">
							<center>
								<%
								String _style="style='display:none;'";
								if("object".equals(reqBizParamInput.getString("type")) || "array".equals(reqBizParamInput.getString("type"))){
									 _style="";
								}
								%>
								<img class="fdReqBizParamInputList_editPd" <%=_style%> src="${KMSS_Parameter_ContextPath}resource/style/default/icons/edit.gif" title="查看对象或数组类型属性定义" onclick="doExendParamDefine('fdReqBizParamInputList');" style="cursor:pointer">
							</center>
						</td>
					</tr>
				<%}
				}%>
			</table>
		</td>

		<td colspan="2" width="50%">
			<table id="TABLE_DocList_Output" class="tb_normal" width="100%">
				<tr>
					<td width="32%" align="center" class="td_normal_title"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.name"/></td>
					<td width="32%" align="center" class="td_normal_title"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.title"/></td>
					<td width="24%" align="center" class="td_normal_title"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.type"/></td>
					<td width="12%" align="center" class="td_normal_title">
					</td>
				</tr>

				<!--内容行-->
				<%
				JSONArray fdReqBizParamOutputList = (JSONArray)request.getAttribute("fdReqBizParamOutputList");
				if(fdReqBizParamOutputList!=null){
					for(int i=0;i<fdReqBizParamOutputList.size();i++){
						JSONObject reqBizParamOutput = fdReqBizParamOutputList.getJSONObject(i);
				%>
					<tr KMSS_IsContentRow="1">
						<td width="32%">
							<input validate="required" readonly class="inputsgl" style="width:75%" subject="<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.name"/>" name="fdReqBizParamOutputList[<%=i%>].name"  value="<%=reqBizParamOutput.getString("name")%>">
						</td>
						<td width="32%">
							<input validate="required" readonly class="inputsgl" style="width:75%" subject="<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.title"/>" name="fdReqBizParamOutputList[<%=i%>].title"  value="<%=reqBizParamOutput.getString("title")%>">
						</td>		
					<td width="24%">
						<input type="hidden" name="fdReqBizParamOutputList[<%=i%>].children" value='<%=reqBizParamOutput.getString("children")%>'>
						<select validate="required" disabled subject="<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.title"/>" style="width:72%" name="fdReqBizParamOutputList[<%=i%>].type">
							<option value="string" <%=("string".equals(reqBizParamOutput.getString("type"))?"selected":"")%>>字符串型</option>
							<option value="object" <%=("object".equals(reqBizParamOutput.getString("type"))?"selected":"")%>>对象型</option>
							<option value="array" <%=("array".equals(reqBizParamOutput.getString("type"))?"selected":"")%>>数组型</option>
							<option value="int" <%=("int".equals(reqBizParamOutput.getString("type"))?"selected":"")%>>整型</option>
							<option value="long" <%=("long".equals(reqBizParamOutput.getString("type"))?"selected":"")%>>长整型</option>
							<option value="double" <%=("double".equals(reqBizParamOutput.getString("type"))?"selected":"")%>>浮点型</option>
							<option value="boolean" <%=("boolean".equals(reqBizParamOutput.getString("type"))?"selected":"")%>>布尔型</option>
						</select>
					</td>						
						<td width="12%">
							<center>
								<%
								String _style="style='display:none;'";
								if("object".equals(reqBizParamOutput.getString("type")) || "array".equals(reqBizParamOutput.getString("type"))){
									 _style="";
								}
								%>
								<img class="fdReqBizParamOutputList_editPd" <%=_style%> src="${KMSS_Parameter_ContextPath}resource/style/default/icons/edit.gif" title="查看对象或数组类型属性定义" onclick="doExendParamDefine('fdReqBizParamOutputList');" style="cursor:pointer">
							</center>
						</td>
					</tr>
				<%}
				}%>
			</table>
		</td>

	</tr>

</table>
</center>

<input type="hidden" id="cacheParamDefine" name="cacheParamDefine" value="">
<html:hidden property="fdId" />

<html:hidden property="fdReqHeader" />
<html:hidden property="fdOriParaIn" />
<html:hidden property="fdOriParaOut" />
<html:hidden property="fdAppType" />

<html:hidden property="method_GET" />
<script>
	DocList_Info.push("TABLE_DocList_Input");
	DocList_Info.push("TABLE_DocList_Output");
	DocList_Info.push("TABLE_DocList_Header");
	
	Com_AddEventListener(window, "load", function(){
		changeOauthEnable();
	});
	
	function changeOauthEnable(){
		if(document.getElementsByName("fdOauthEnable")[0].checked){
			$("#idUseCustAt").show();
			$("#fdOauthEnableZone").show();
			if(document.getElementsByName("fdUseCustAt")[0].checked){
				$("#oauthDef").show();
				$("#oauthSet").hide();				
			}else{
				$("#oauthDef").hide();
				$("#oauthSet").show();				
			}
		}else{
			$("#fdOauthEnableZone").hide();
			$("#idUseCustAt").hide();
		}
	}
	
	function _selectExendParamDefine(index,type){
		var title = document.getElementsByName(type+"["+index+"].title")[0].value;
		seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
			dialog.iframe('/tic/rest/connector/tic_rest_main/selectExtendParam_diagram_view.jsp?title='+title+'&type='+type+'&index='+index,"查看对象或数组类型属性'"+title+"'定义",function(value){
				if(value!=null){
					var pn = type+"["+index+"].children";
					var extps = document.getElementsByName(pn)[0];
					extps.value = JSON.stringify(value);
				}
			 },{height:'400',width:'650'});
		});
	}

	function doExendParamDefine(type){
		var optTR = DocListFunc_GetParentByTagName("TR");
		var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
		var currRowIndex = Com_ArrayGetIndex(optTB.rows, optTR); 
		var index = currRowIndex-1;
		_selectExendParamDefine(index,type)
	}

	seajs.use(['lui/dialog','lui/jquery'],function(dialog,$) {
		
		window.getAccessToken = function(id) {
				window.file_load = dialog.loading();
				var accessTokenUrl = '${LUI_ContextPath}/tic/rest/connector/tic_rest_main/ticRestMain.do?method=getAccessToken';
				$.ajax({
					url: accessTokenUrl,
					type: 'GET',
					data:{fdId:id},
					dataType: 'json',
					error: function(data){
						if(window.file_load!=null){
							window.file_load.hide(); 
						}
						console.log(data)
						dialog.result(data.responseJSON);
					},
					success: function(data){
						if(data["errcode"]=="0"){
							dialog.alert("获取的access_token="+data["access_token"], '', '',
								'', false,window);
							window.file_load.hide(); 
						}else{
							dialog.alert("获取access_token出错："+data["errmsg"], '', '',
								'', false,window);
							window.file_load.hide(); 
						}
					}
			   });
			}
	});

	function getRestData(id){
		seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
			dialog.iframe('/tic/rest/connector/tic_rest_main/rest_diagram_data.jsp?fdId='+id,"调试获取REST数据",function(value){
				if(value!=null){
				}
			 },{height:'600',width:'850'});
		});
	}

</script>
</html:form>

    </template:replace>
</template:include>