<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/sys/lbpmext/businessauth/lbpmext_businessauth_util/lbpmextBusinessAuth_util.jsp"%>
<%
Object isCloneAction = request.getAttribute("isCloneAction");
if (isCloneAction == null) {
	request.setAttribute("isCloneAction","false");
}
%>
<script type="text/javascript">
Com_IncludeFile("dialog.js");
</script>
<style>
.lui_businessauth_frame{
	height: 45px;
    line-height: 45px;
    float:right;
    margin-right:30px;
} 
.lui_businessauth_frame td{
    padding: 0; 
    border: none;
}
.btn_txt{
    color: #2574ad;
    border-bottom: 1px solid transparent
}
</style>
<kmss:windowTitle moduleKey="sys-lbpmext-businessauth:table.lbpmext.businessAuthInfo" subjectKey="sys-lbpmext-businessauth:lbpmext.businessAuthInfo.set" />
<html:form action="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do">
<ui:toolbar var-navwidth="90%" id="toolbar" layout="sys.ui.toolbar.float" count="6">
	<logic:equal name="lbpmExtBusinessAuthInfoForm" property="method_GET" value="edit">
		<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=update&fdId=${lbpmExtBusinessAuthInfoForm.fdId}">
			<ui:button text="${ lfn:message('button.update') }"
				onclick="Com_Submit(document.lbpmExtBusinessAuthInfoForm, 'update');">
			</ui:button>
		</kmss:auth>
	</logic:equal>
	<c:if test="${lbpmExtBusinessAuthInfoForm.method_GET=='add' || lbpmExtBusinessAuthInfoForm.method_GET=='clone'}">
		<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=save&category=${JsParam.category}">
			<ui:button text="${ lfn:message('button.save') }"
				onclick="Com_Submit(document.lbpmExtBusinessAuthInfoForm, 'save');">
			</ui:button>
		</kmss:auth>
		<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=saveadd&category=${JsParam.category}">
			<ui:button text="${ lfn:message('button.saveadd') }"
				onclick="Com_Submit(document.lbpmExtBusinessAuthInfoForm, 'saveadd');">
			</ui:button>
		</kmss:auth>
	</c:if>
	<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()">
	</ui:button>
</ui:toolbar>
<p class="txttitle"><bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.set"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<!-- 被授权人 -->
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.fdAuthorizedPerson"/>
		</td>
		<td width=35%>
			<xform:address propertyId="fdAuthorizedPersonId" onValueChange="onAuthrizedPersonChange()" propertyName="fdAuthorizedPersonName" orgType="ORG_TYPE_PERSON"
				showStatus="edit" style="width:150px" validators="checkExist checkRequired" subject="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdAuthorizedPerson') }">
			</xform:address>
		</td>
		<!-- 被授权岗位 -->
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.fdAuthorizedPost"/>
		</td>
		<td width=35%>
			<xform:address propertyId="fdAuthorizedPostId" propertyName="fdAuthorizedPostName" orgType="ORG_TYPE_POST"
				showStatus="edit" style="width:150px" validators="checkExist checkRequired" subject="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdAuthorizedPost') }">
			</xform:address>
		</td>
	</tr>
	<tr>
		<!-- 开始时间 -->
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.fdStartTime"/>
		</td>
		<td width=35%>
			<xform:datetime property="fdStartTime" dateTimeType="datetime" required="true" onValueChange="checkAuth()" validators="checkTime" style="width:150px;">
				</xform:datetime>
		</td>
		
		<!-- 结束时间 -->
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.fdEndTime"/>
		</td>
		<td width=35%>
			<xform:datetime property="fdEndTime" dateTimeType="datetime" required="true" onValueChange="checkAuth()" validators="checkTime checkEndTime" style="width:150px;" htmlElementProperties="data-init-hour='23' data-init-minute='59'">
				</xform:datetime>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.fdCategroy"/>
		</td>
		<td width=35%>
			<html:hidden property="fdCategoryId"/>
			<xform:text style="width:150px;" property="fdCategoryName" required="true" subject="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdCategroy') }" htmlElementProperties="readOnly='true'"/> 
			<a href="javascript:void(0)" onclick="Dialog_Tree(
				false,
				'fdCategoryId',
				'fdCategoryName',
				null,
				'lbpmExtBusinessAuthInfoCateService&parentId=!{value}',
				'<bean:message bundle="sys-lbpmext-businessauth" key="table.lbpmext.businessAuthInfoCate"/>');">
				<bean:message key="dialog.selectOrg"/>
			</a>
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.status"/>
		</td>
		<td width=35%>
			<xform:radio property="docStatus">
				<xform:enumsDataSource enumsType="authInfo_docStatus"></xform:enumsDataSource>
			</xform:radio>
		</td>		
	</tr>	
	<!-- 条目编号开始 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.authorizationCode"/>
		</td>
		<td width=85% colspan="3">     
			<c:if test="${lbpmExtBusinessAuthInfoForm.fdNumber!=null}">
				<html:text property="fdNumber" readonly="true" style="width:95%"/>
			</c:if>
			<c:if test="${lbpmExtBusinessAuthInfoForm.fdNumber==null}">
				<span class="com_help">(<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuth.fdNumber.help"/>)</span>
			</c:if>
		</td>
	</tr>
	<!-- 条目编号结束 -->
	<!-- 可维护者 -->
	<tr>
		<td class="td_normal_title" width=15%><bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.authEditors"/></td>
		<td width=85% colspan="3"><html:hidden property="authEditorIds" /> <html:textarea
			property="authEditorNames" style="width:90%" readonly="true" /> <a
			href="#"
			onclick="Dialog_Address(true, 'authEditorIds','authEditorNames', ';',null);"><bean:message
			key="dialog.selectOther" /></a><%-- <br>
			<bean:message bundle="sys-rule" key="sysRuleSetCate.authEditors.describe"/> --%>
		</td>
	</tr>
	<%--管辖范围--%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.fdScope"/>
		</td>
		<td width=85% colspan="3"><html:textarea property="fdScope" style="width:97%;" /></td>
	</tr>
	<%--描述--%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.fdDesc"/>
		</td>
		<td width=85% colspan="3"><html:textarea property="fdDesc" style="width:97%;" /></td>
	</tr>
	<tr>
		<td colspan="4">
			<div>
				<div class="lui_businessauth_frame">
					<ui:toolbar var-navwidth="90%" layout="sys.ui.toolbar.default" count="6">  
						<ui:button text="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.changeTimeBatch') }" onclick="changeTimeBatch();"/>
						<ui:button text="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.addBatch') }" onclick="addBatch();"/>
						<ui:button text="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.add') }" onclick="addDetail();"/>
					</ui:toolbar>
				</div>
				<div>
					<table class="tb_normal" width=100% id="TABLE_DocList_Details" align="center" style="table-layout:fixed;" frame=void>
						<tr>
							<td align="center" width="50px">
								<label><input type="checkbox" name="List_Select_All" onclick="selectedAll(this);"><bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.selectAll"/></label>
							</td>
							<td align="center">
								<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdAuthorizer"/>
							</td>
							<!-- 分类 -->
							<td align="center">
								<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthCate"/>
							</td>
							<td align="center">
								<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdAuth"/>
							</td>
							<td align="center">
								<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdNumber"/>
							</td>
							<td align="center">
								<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdStartTime"/>
							</td>
							<td align="center">
								<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdEndTime"/>
							</td>
							<td align="center" width="20%">
								<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdType"/>
							</td>
							<td align="center" style="white-space:nowrap" width="10%">
								<bean:message key="list.operation"/>
							</td>
						</tr>
						<!-- 基准行 -->
						<tr KMSS_IsReferRow="1" style="display: none">
							<td align="center">
								<input type="checkbox" name="List_Selected" onclick="checkCanSelectAll();">
							</td>
							<td align="center">
								<input type="hidden" name="fdAuthDetails[!{index}].fdId" />
								<input type="hidden" name="fdAuthDetails[!{index}].fdAuthorizerId" />
								<input type="hidden" name="fdAuthDetails[!{index}].fdAuthorizerName" />
								<input type="hidden" name="fdAuthDetails[!{index}].fdAuthorizedPersonId" />
								<input type="hidden" name="fdAuthDetails[!{index}].fdAuthorizedPersonName" />
								<input type="hidden" name="fdAuthDetails[!{index}].fdAuthorizedPostId" />
								<input type="hidden" name="fdAuthDetails[!{index}].fdAuthorizedPostName" />
								<input type="hidden" name="fdAuthDetails[!{index}].fdCreatorId" />
								<input type="hidden" name="fdAuthDetails[!{index}].fdCreatorName" />
								<input type="hidden" name="fdAuthDetails[!{index}].fdCreateTime" />
								<input type="hidden" name="fdAuthDetails[!{index}].fdAuthorizeId" />
								<span class="fdAuthorizerName"></span>
								<!-- 授权人 -->
							</td>
							<!-- 条目分类名 -->
							<td align="center">
								<input type="hidden" name="fdAuthDetails[!{index}].fdCateName"/>
								<span class="fdCateName"></span>
							</td>
							<td align="center">
								<input type="hidden" name="fdAuthDetails[!{index}].fdAuthId" />
								<input type="hidden" name="fdAuthDetails[!{index}].fdAuthName"/>
								<span class="fdAuthName"></span>
							</td>
							<td align="center">
								<input type="hidden" name="fdAuthDetails[!{index}].fdNumber"/>
								<span class="fdNumber"></span>
							</td>
							<td align="center">
								<input type="hidden" name="fdAuthDetails[!{index}].fdStartTime"/>
								<span class="fdStartTime"></span>
							</td>
							<td align="center">
								<input type="hidden" name="fdAuthDetails[!{index}].fdEndTime"/>
								<span class="fdEndTime"></span>
							</td>
							<td align="center">
								<div class="fdLimitInfo">
									<input type="radio" checked="checked" readonly="readonly"><span class="fdType"></span><span class="limitRange">&nbsp;<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdLimit"/>&nbsp;<span class="fdMinLimit"></span>~<span class="fdLimit"></span></span>
								</div>
								<input type="hidden" name="fdAuthDetails[!{index}].fdType" />
								<input type="hidden" name="fdAuthDetails[!{index}].fdLimit" />
								<input type="hidden" name="fdAuthDetails[!{index}].fdMinLimit" />
							</td>
							<td style="white-space:nowrap" align="center" width="">
								<a class="btn_txt" onclick="editDetail(this);"><bean:message key="button.edit"/></a>&nbsp;&nbsp;
								<a class="btn_txt" onclick="DocList_DeleteRow();"><bean:message key="button.delete"/></a>
							</td>
						</tr>
						<c:forEach items="${lbpmExtBusinessAuthInfoForm.fdAuthDetails}" var="item" varStatus="vstatus">
							<tr KMSS_IsContentRow="1">
								<td align="center">
									<input type="checkbox" name="List_Selected" onclick="checkCanSelectAll();">
								</td>
								<td align="center">
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdId" value="<c:out value='${item.fdId}'/>"/>
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizerId" value="<c:out value='${item.fdAuthorizerId}'/>"/>
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizerName" value="<c:out value='${item.fdAuthorizerName}'/>"/>
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizedPersonId" value="<c:out value='${item.fdAuthorizedPersonId}'/>" />
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizedPersonName" value="<c:out value='${item.fdAuthorizedPersonName}'/>" />
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizedPostId" value="<c:out value='${item.fdAuthorizedPostId}'/>" />
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizedPostName" value="<c:out value='${item.fdAuthorizedPostName}'/>" />
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdCreatorId" value="<c:out value='${item.fdCreatorId}'/>" />
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdCreatorName" value="<c:out value='${item.fdCreatorName}'/>" />
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdCreateTime" value="<c:out value='${item.fdCreateTime}'/>" />
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizeId" value="<c:out value='${item.fdAuthorizeId}'/>" />
									<span class="fdAuthorizerName"><c:out value='${item.fdAuthorizerName}'/></span>
									<!-- 授权人 -->
								</td>
								<!-- 条目分类名 -->
								<td align="center">
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdCateName" value="<c:out value='${item.fdCateName}'/>"/>
									<span class="fdCateName"><c:out value='${item.fdCateName}'/></span>
								</td>
								<td align="center">
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthId" value="<c:out value='${item.fdAuthId}'/>" />
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthName" value="<c:out value='${item.fdAuthName}'/>"/>
									<span class="fdAuthName"><c:out value='${item.fdAuthName}'/></span>
								</td>
								<td align="center">
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdNumber" value="<c:out value='${item.fdNumber}'/>"/>
									<span class="fdNumber"><c:out value='${item.fdNumber}'/></span>
								</td>
								<td align="center">
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdStartTime" value="<c:out value='${item.fdStartTime}'/>" class="inputread_normal" readonly="readonly"/>
									<span class="fdStartTime"><c:out value='${item.fdStartTime}'/></span>
								</td>
								<td align="center">
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdEndTime" value="<c:out value='${item.fdEndTime}'/>" class="inputread_normal" readonly="readonly"/>
									<span class="fdEndTime"><c:out value='${item.fdEndTime}'/></span>
								</td>
								<td align="center">
									<div class="fdLimitInfo">
										<input type="radio" checked="checked" readonly="readonly"><span class="fdType"><c:out value="${item.fdTypeName}"/></span><span class="limitRange" style="display:${item.fdType==3?'none':''}">&nbsp;<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdLimit"/>&nbsp;<span class="fdMinLimit"><c:out value='${item.fdMinLimit}'/></span>~<span class="fdLimit"><c:out value='${item.fdLimit}'/></span></span>
									</div>
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdType" value="<c:out value='${item.fdType}'/>" />
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdLimit" value="<c:out value='${item.fdLimit}'/>" />
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdMinLimit" value="<c:out value='${item.fdMinLimit}'/>" />
								</td>
								<td style="white-space:nowrap" align="center" width="">
									<c:if test="${item.fdIsCanEdit eq 'true'}">
										<a class="btn_txt" onclick="editDetail(this,'<c:out value='${item.fdId}'/>');"><bean:message key="button.edit"/></a>&nbsp;&nbsp;
										<a class="btn_txt" onclick="DocList_DeleteRow();"><bean:message key="button.delete"/></a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</td>
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>
<html:hidden property="fdId"/>
<script>Com_IncludeFile('doclist.js');</script>
<script>
DocList_Info.push("TABLE_DocList_Details");

function addDetail(){
	if(!_$validation.validate()){
		return;
	}
	var param = {
			fdAuthorizerId:$("#TABLE_DocList_Details").find("tr:last").find('[name$="fdAuthorizerId"]').val(),
			fdAuthorizerName:$("#TABLE_DocList_Details").find("tr:last").find('[name$="fdAuthorizerName"]').val(),
			fdAuthorizedPersonId:$("input[name='fdAuthorizedPersonId']").val(),
			fdAuthorizedPersonName:$("input[name='fdAuthorizedPersonName']").val(),
			fdAuthorizedPostId:$("input[name='fdAuthorizedPostId']").val(),
			fdAuthorizedPostName:$("input[name='fdAuthorizedPostName']").val(),
			fdAuthStartTime:$("input[name='fdStartTime']").val(),
			fdAuthEndTime:$("input[name='fdEndTime']").val()
		};
	var url='/sys/lbpmext/businessauth/lbpmext_businessauth_detail/lbpmextBusinessAuthDetail.jsp';
	if("${lbpmExtBusinessAuthInfoForm.canChangeAuthorizerAuth}"=="false"){
		url += "?cannotChangeAuthorizerAuth=true";
		if(!param.fdAuthorizerId){
			param.fdAuthorizerId = '<%=UserUtil.getUser().getFdId()%>';
			param.fdAuthorizerName ='<%=UserUtil.getUser().getFdName()%>';
		}
	}
	Com_Parameter.Dialog = param;
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
		dialog.iframe(url,"<bean:message bundle='sys-lbpmext-businessauth' key='table.lbpmext.businessAuthDetail'/>",function(rtn){
			if(rtn){
				var fieldValues = new Object();
				for(var key in rtn){
					if(rtn[key]){
						fieldValues["fdAuthDetails[!{index}]."+key]=rtn[key];
					}
				}
				var newRow = DocList_AddRow("TABLE_DocList_Details",null,fieldValues);
				if(newRow){
					for(var key in rtn){
						if(rtn[key]){
							if(key=="fdType"){
								$(newRow).find("."+key).text(businessAuth.getControlTypeName(rtn[key]));
							}else{
								$(newRow).find("."+key).text(rtn[key]);
							}
						}
					}
					if(rtn["fdType"]==businessAuth.controlType.qualitative){
						$(newRow).find(".limitRange").hide();
					}else{
						$(newRow).find(".limitRange").show();
					}
				}
			}
		}
		,{width:500,height:550,close:false,params:param});
	});
}
//批量新增
function addBatch(){
	if(!_$validation.validate()){
		return;
	}
	var param = {
			/* 批量新增时获取授权列表中的最后一个授权人赋值给弹出框中的授权人开始 */
			fdAuthorizerId:$("#TABLE_DocList_Details").find("tr:last").find('[name$="fdAuthorizerId"]').val(),
			fdAuthorizerName:$("#TABLE_DocList_Details").find("tr:last").find('[name$="fdAuthorizerName"]').val(),
			/* 批量新增时获取授权列表中的最后一个授权人赋值给弹出框中的授权人结束 */
			fdAuthorizedPersonId:$("input[name='fdAuthorizedPersonId']").val(),
			fdAuthorizedPersonName:$("input[name='fdAuthorizedPersonName']").val(),
			fdAuthorizedPostId:$("input[name='fdAuthorizedPostId']").val(),
			fdAuthorizedPostName:$("input[name='fdAuthorizedPostName']").val(),
			fdAuthStartTime:$("input[name='fdStartTime']").val(),
			fdAuthEndTime:$("input[name='fdEndTime']").val()
		};
	var url='/sys/lbpmext/businessauth/lbpmext_businessauth_detail/lbpmextBusinessAuthBatch.jsp';
	if("${lbpmExtBusinessAuthInfoForm.canChangeAuthorizerAuth}"=="false"){
		url += "?cannotChangeAuthorizerAuth=true";
	}
	Com_Parameter.Dialog = param;
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
		dialog.iframe(url,"<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthInfo.addBatch.title'/>",function(rtn){
			if(rtn){
				var fdAuthIds = rtn.fdAuthId.split(";");
				var fdAuthNames = rtn.fdAuthName.split(";");
				//给条目分类文本分割赋值
				var fdCateNames = rtn.fdCateName.split(";");
				
				var fieldValues = new Object();
				for(var key in rtn){
					if(rtn[key]){
						fieldValues["fdAuthDetails[!{index}]."+key]=rtn[key];
					}
				}
				
				var _fdAuthMaps = window.__fdAuthInfoMaps;
				for(var i = 0;i<fdAuthIds.length;i++){
					var fdAuthId = fdAuthIds[i];
					var fdAuthName = fdAuthNames[i];
					//给条目分类文本分割赋值
					var fdCateName = fdCateNames[i];
					var record = $.extend({}, fieldValues);
					record["fdAuthDetails[!{index}].fdAuthId"]=fdAuthId;
					record["fdAuthDetails[!{index}].fdAuthName"]=fdAuthName;
					//给条目分类文本分割赋值
					record["fdAuthDetails[!{index}].fdCateName"]=fdCateName;
					var authMap = getBusAuth(_fdAuthMaps,fdAuthId);
					if(authMap.fdType){
						var type = authMap.fdType;
						if(type==businessAuth.controlType.qualitative){
							record["fdAuthDetails[!{index}].fdLimit"]=0;
						}else{
							record["fdAuthDetails[!{index}].fdLimit"]="";
						}
						
						if(type==businessAuth.controlType.none){
							record["fdAuthDetails[!{index}].fdType"]="";
						}else{
							record["fdAuthDetails[!{index}].fdType"]=type;
						}
						record["fdAuthDetails[!{index}].fdNumber"]=authMap.fdNumber;
					}
					
					var newRow = DocList_AddRow("TABLE_DocList_Details",null,record);
					if(newRow){
						for(var key in record){
							if(record[key]){
								if(key.indexOf("fdType")>-1){
									$(newRow).find(".fdType").text(businessAuth.getControlTypeName(record[key]));
								}else if(key.indexOf("fdAuthName")>-1){
									$(newRow).find(".fdAuthName").text(fdAuthName);
								}else if(key.indexOf("fdCateName")>-1){
									//给条目分类文本分割赋值
									$(newRow).find(".fdCateName").text(fdCateName);
								}else if(key.indexOf("fdNumber")>-1){
									$(newRow).find(".fdNumber").text(authMap.fdNumber);
								}else{
									$(newRow).find(key.substr(key.indexOf('.'))).text(record[key]);
								}
							}
						}
						if(authMap.fdType==businessAuth.controlType.qualitative){
							$(newRow).find(".limitRange").hide();
						}else{
							$(newRow).find(".limitRange").show();
						}
						if(authMap.fdType==businessAuth.controlType.none){
							$(newRow).find(".fdLimitInfo").hide();
						}
					}
				}
				
			}
		}
		,{width:500,height:600,close:false,params:param});
	});
}
function getBusAuth(fdAuthMaps,fdAuthId){
	if(fdAuthMaps && fdAuthMaps.length>0){
		for(var i=0;i<fdAuthMaps.length;i++){
			if(fdAuthMaps[i].id==fdAuthId){
				return fdAuthMaps[i];
			}
		}
	}
	return null;
}

function editDetail(dom,fdId){
	if(!_$validation.validate()){
		return;
	}
	var url='/sys/lbpmext/businessauth/lbpmext_businessauth_detail/lbpmextBusinessAuthDetail.jsp';
	var domRow = $(dom).closest("tr");
	var param = {
			fdAuthorizedPersonId:$("input[name='fdAuthorizedPersonId']").val(),
			fdAuthorizedPersonName:$("input[name='fdAuthorizedPersonName']").val(),
			fdAuthorizedPostId:$("input[name='fdAuthorizedPostId']").val(),
			fdAuthorizedPostName:$("input[name='fdAuthorizedPostName']").val(),
			fdAuthorizerId:domRow.find("input[name$='fdAuthorizerId']").val(),
			fdAuthorizerName:domRow.find("input[name$='fdAuthorizerName']").val(),
			fdAuthId:domRow.find("input[name$='fdAuthId']").val(),
			fdAuthName:domRow.find("input[name$='fdAuthName']").val(),
			fdCateName:domRow.find("input[name$='fdCateName']").val(),
			fdNumber:domRow.find("input[name$='fdNumber']").val(),
			fdStartTime:domRow.find("input[name$='fdStartTime']").val(),
			fdEndTime:domRow.find("input[name$='fdEndTime']").val(),
			fdType:domRow.find("input[name$='fdType']").val(),
			fdLimit:domRow.find("input[name$='fdLimit']").val(),
			fdMinLimit:domRow.find("input[name$='fdMinLimit']").val(),
			fdAuthStartTime:$("input[name='fdStartTime']").val(),
			fdAuthEndTime:$("input[name='fdEndTime']").val()
	};
	//编辑已有的，直接掉lbpmBusinessAuthDetail.do的edit
	var isUpdate = domRow.attr("isUpdate")=="true" || ${isCloneAction } == true;
	if(fdId&&!isUpdate){
		param = {
				fdStartTime:$("input[name='fdStartTime']").val(),
				fdEndTime:$("input[name='fdEndTime']").val()
		};
		url = '/sys/lbpmext/businessauth/lbpmBusinessAuthDetail.do?method=edit&fdId='+fdId;
	}
	if("${lbpmExtBusinessAuthInfoForm.canChangeAuthorizerAuth}"=="false"){
		if(fdId&&!isUpdate){
			url += "&cannotChangeAuthorizerAuth=true";
		}else{
			url += "?cannotChangeAuthorizerAuth=true";
		}
	}
	Com_Parameter.Dialog = param;
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
		dialog.iframe(url,"<bean:message bundle='sys-lbpmext-businessauth' key='table.lbpmext.businessAuthDetail'/>",function(rtn){
			if(rtn){
				var row = $(dom).closest("tr");
				row.attr("isUpdate","true");
				for(var key in rtn){
					if(rtn[key]){
						row.find("input[name$='"+key+"']").val(rtn[key]);
						if(key=="fdType"){
							$(row).find("."+key).text(businessAuth.getControlTypeName(rtn[key]));
						}else{
							$(row).find("."+key).text(rtn[key]);
						}
					}
				}
				if(rtn["fdType"]==businessAuth.controlType.qualitative){
					$(row).find(".limitRange").hide();
				}else{
					$(row).find(".limitRange").show();
				}
				$(row).find(".fdLimitInfo").show();
			}
		}
		,{width:500,height:550,close:false,params:param});
	});
}

//选择|取消所有
function selectedAll(obj){
	$("input[name='List_Selected']").each(function(){
		if(obj.checked){
			this.checked = true;
		}else{
			this.checked = false;
		}
	});	
}

function checkCanSelectAll(){
	if($("input[name='List_Selected']").length==$("input[name='List_Selected']:checked").length){
		$("input[name='List_Select_All']").prop("checked",true);
	}else{
		$("input[name='List_Select_All']").prop("checked",false);
	}
}

function changeTimeBatch(){
	if(!_$validation.validate()){
		return;
	}
	if($("input[name='List_Selected']:checked").length==0){
		alert('<bean:message key="page.noSelect"/>');
		return;
	}
	var url='/sys/lbpmext/businessauth/lbpmext_businessauth_info/lbpmextBusinessAuthInfo_time.jsp'
	var param = {
		fdAuthStartTime:$("input[name='fdStartTime']").val(),
		fdAuthEndTime:$("input[name='fdEndTime']").val()
	};
	Com_Parameter.Dialog = param;
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
		dialog.iframe(url,"<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthInfo.changeTimeBatch'/>",function(rtn){
			if(rtn){
				$("input[name='List_Selected']:checked").each(function(){
					var row = $(this).closest("tr");
					for(var key in rtn){
						if(rtn[key]){
							row.find("input[name$='"+key+"']").val(rtn[key]);
							$(row).find("."+key).text(rtn[key]);
						}
					}
				});	
			}
		}
		,{width:600,height:600,close:false,params:param});
	});
}

Com_Parameter.event["submit"].push(function(){
	var fdDetails = document.getElementById("TABLE_DocList_Details");
	var fdStartTime = $("input[name='fdStartTime']").val();
	var fdEndTime = $("input[name='fdEndTime']").val();
	for(var i = 1;i<fdDetails.rows.length;i++){
		var row = fdDetails.rows[i];
		var startTime = $(row).find("input[name$='fdStartTime']").val();
		var endTime = $(row).find("input[name$='fdEndTime']").val();
		if(Date.parse(startTime)<Date.parse(fdStartTime)){
			$(row).css("background-color","#F0F0F0");
			alert("<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthDetail.startTimeMsg'/>")
			return false;
		}
		if(Date.parse(endTime)>Date.parse(fdEndTime)){
			$(row).css("background-color","#F0F0F0");
			alert("<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthDetail.endTimeMsg'/>")
			return false;
		}
		var fdType = $(row).find("input[name$='fdType']").val(); 
		if(fdType != businessAuth.controlType.qualitative){
			var fdLimit = $(row).find("input[name$='fdLimit']").val(); 
			if(!fdLimit){
				$(row).css("background-color","#F0F0F0");
				alert("<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthDetail.limitMsg2'/>");
				return false;
			}
		}
	}
	//校验条目的fdtype是否为空
	var fdTypes = $('input[type="hidden"][name$=".fdType"]');
	var fdTypeArr = [];
	if(fdTypes && fdTypes.length>0){
		for(var i=0;i<fdTypes.length;i++){
			var value = fdTypes[i].value;
			if(!value){
				var tdNode = $(fdTypes[i]).parent();
				var trNode = tdNode.parent();
				var fdAuthName = $(trNode).find('.fdAuthName').html();
				fdTypeArr.push(fdAuthName);
				
			}
		}
		if(fdTypeArr.length>0){
			checkSubmit(fdTypeArr.join('<BR/>'));
			return false;
		}
	}
	return true;
});

function checkSubmit(tip){
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
		var txt= "<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthInfo.submit.validate'/>";
		dialog.alert(txt+'<BR/>'+'<BR/>'+tip);
	});
}

Com_Parameter.event["confirm"].push(function(){
	$("#TABLE_DocList_Details").find("input[name$='fdAuthorizedPersonId']").val($("input[name='fdAuthorizedPersonId']").val());
	$("#TABLE_DocList_Details").find("input[name$='fdAuthorizedPersonName']").val($("input[name='fdAuthorizedPersonName']").val());
	$("#TABLE_DocList_Details").find("input[name$='fdAuthorizedPostId']").val($("input[name='fdAuthorizedPostId']").val());
	$("#TABLE_DocList_Details").find("input[name$='fdAuthorizedPostName']").val($("input[name='fdAuthorizedPostName']").val());
	return true;
});
</script>
</html:form>
<html:javascript formName="lbpmExtBusinessAuthInfoForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<script>
function checkAuth(){
	var fdStartTime = $("input[name='fdStartTime']").val();
	var fdEndTime = $("input[name='fdEndTime']").val();
	if(fdStartTime && fdEndTime){
		_$validation.validateElement($("input[data-propertyname='fdAuthorizedPersonName']")[0]);
	}
}

_$validation.addValidator('checkExist',
	"<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthInfo.existMsg'/>",
	function(v,e,o){
		var bool = true;
		var fdAuthorizedPersonId = $("input[name='fdAuthorizedPersonId']").val();
		var fdAuthorizedPostId = $("input[name='fdAuthorizedPostId']").val();
		var fdStartTime = $("input[name='fdStartTime']").val();
		var fdEndTime = $("input[name='fdEndTime']").val();
		var docStatus = $("input[name='docStatus']:checked").val();
		if(docStatus == "30") {
			return bool;
		}
		
		$.ajax({
			async:false,
			url:'<c:url value="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=validateAuthorizedPersonAndPost"/>',
			data:{fdAuthorizedPersonId:fdAuthorizedPersonId,fdAuthorizedPostId:fdAuthorizedPostId,fdStartTime:fdStartTime,fdEndTime:fdEndTime, fdId:'${lbpmExtBusinessAuthInfoForm.fdId }'},
			success:function(data){
	        	if(data=='false'){
	    			bool = false;
	    		}
	        }});
		return bool;
	}
);

_$validation.addValidator('checkRequired',
	"<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthInfo.requiredMsg'/>",
	function(v,e,o){
		var fdAuthorizedPersonId = $("input[name='fdAuthorizedPersonId']").val();
		var fdAuthorizedPostId = $("input[name='fdAuthorizedPostId']").val();
		if(!fdAuthorizedPersonId && !fdAuthorizedPostId){
			return false;
		}
		new Reminder($("input[name='fdAuthorizedPersonName']")[0]).hide();
		new Reminder($("input[name='fdAuthorizedPostName']")[0]).hide();
		new Reminder($("input[name='docStatus']")[0]).hide();
		return true;
	}
);

_$validation.addValidator('checkTime',
	"<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthInfo.timeMsg'/>",
	function(v,e,o){
		var startTime = $("input[name='fdStartTime']").val();
		var endTime = $("input[name='fdEndTime']").val();
		if(startTime && endTime){
			if(Date.parse(startTime)>Date.parse(endTime)){
				return false;
			}else{
				new Reminder($("input[name='fdStartTime']")[0]).hide();
				new Reminder($("input[name='fdEndTime']")[0]).hide();
			}
		}
		return true;
	}
);

_$validation.addValidator('checkEndTime',
	"<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthInfo.endTimeMsg'/>",
	function(v,e,o){
		if(v){
			if(new Date().getTime()>=Date.parse(v)){
				return false;
			}
		}
		return true;
	}
);
window.onAuthrizedPersonChange = function(){
	var author=document.getElementsByName("fdAuthorizedPersonId")[0];
	var _authorId =author.value;
	if (!_authorId) {
		return;
	}
	var data = new KMSSData();
    data.AddBeanData("lbpmExtBusinessAuthInfoService&type=getPost&personId=" + _authorId);
    data.PutToField("fdAuthorizedPostId:fdAuthorizedPostName", "fdAuthorizedPostId:fdAuthorizedPostName", "", false);
};
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>