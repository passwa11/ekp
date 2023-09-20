<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.lbpmext.authorize.forms.LbpmAuthorizeForm"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%
	//解析授权方式
	com.landray.kmss.sys.lbpmext.authorize.forms.LbpmAuthorizeForm lbpmauthorzeForm = (com.landray.kmss.sys.lbpmext.authorize.forms.LbpmAuthorizeForm)request.getAttribute("lbpmAuthorizeForm");
	String fdAuthorizeCategory = lbpmauthorzeForm.getFdAuthorizeCategory();
	if(StringUtil.isNull(fdAuthorizeCategory)){
		lbpmauthorzeForm.setFdAuthorizeCategory("0");
		fdAuthorizeCategory = "0";
	}
	if(fdAuthorizeCategory.equals("1")){//流程信息条件授权
		String texts = lbpmauthorzeForm.getFdScopeFormAuthorizeCateShowtexts();
		if(StringUtil.isNotNull(texts)){
			//String[] textArr = texts.split("/");
			//String text = textArr[textArr.length-1];
			request.setAttribute("processName", texts.substring(0,texts.length()-1));
		}else{
			request.setAttribute("processName", "");
		}
	}
%>
<script>
	Com_IncludeFile("lbpmAuthorize_common.js","${LUI_ContextPath}/sys/lbpmext/authorize/resource/js/","js",true);
	Com_IncludeFile("lbpmAuthorize_view.js","${LUI_ContextPath}/sys/lbpmext/authorize/resource/js/","js",true);
	Com_IncludeFile("edit.css","${LUI_ContextPath}/sys/lbpmext/authorize/resource/css/","css",true);
	function confirmDelete(fdid,msg){
	$.ajaxSettings.async = false;	
	var canDel=false;
	$.post('<c:url value="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=findAuthInstanceCount"/>'+'&fdId='+fdid,
				{},function(data){
				if(data=='0'){
					canDel=true;
				}
				else{
					alert('<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorizeItem.notdel.msg"/>');
				}
			});	
	$.ajaxSettings.async = true;
	if(canDel){
		var del = confirm("<bean:message key="page.comfirmDelete"/>");
		return del;
	}
	return false;	
	
}
</script>
<div id="optBarDiv">
		<c:if test="${_gtValue eq true && lbpmAuthorizeForm.fdAuthorizeType ne '4' }">
			<kmss:auth requestURL="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=stop&fdId=${param.fdId}" requestMethod="GET">
			<c:if test="${lbpmAuthorizeForm.fdAuthorizeType ne '1'}">
			<input type="button"
				value="<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorize.btn.stop.title"/>"
				onclick="Com_OpenWindow('lbpmAuthorize.do?method=stopView&fdId=${JsParam.fdId}','_self');">
			</c:if>
			</kmss:auth>
		</c:if>
	
		<kmss:auth requestURL="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
			<input type="button"
				value="<bean:message key="button.edit"/>"
				onclick="Com_OpenWindow('lbpmAuthorize.do?method=edit&fdId=${JsParam.fdId}','_self');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
			<input type="button" 
				value="<bean:message key="button.delete"/>"
				onclick="if(!confirmDelete('${JsParam.fdId}'))return;Com_OpenWindow('lbpmAuthorize.do?method=delete&fdId=${JsParam.fdId}','_self');">
		</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<kmss:windowTitle 
	moduleKey="sys-lbpmext-authorize:table.lbpmAuthorize"/>
<p class="txttitle"><bean:message  bundle="sys-lbpmext-authorize" key="table.lbpmAuthorize"/></p>
<center>
<table id="Label_Tabel" width="95%">
	<tr LKS_LabelName="<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorize.baseInfo"/>">
		<td>
			<table class="tb_normal" width=100%>
					<html:hidden name="lbpmAuthorizeForm" property="fdId"/>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.catagory.choose"/>
					</td>
					<td width=85% colspan=3>
						<html:hidden property="fdAuthorizeCategory"/>
						<xform:radio property="fdAuthorizeCategory" showStatus="view"  >
							<xform:enumsDataSource enumsType="lbpmAuthorize_catagory"/>
						</xform:radio>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdAuthorizeType"/>
					</td><td width=35%>
						<sunbor:enumsShow value="${lbpmAuthorizeForm.fdAuthorizeType}" enumsType="lbpmAuthorize_authorizeType" />
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdAuthorizer"/>
					</td><td width=35%>
						${lbpmAuthorizeForm.fdAuthorizerName }
					</td>
				</tr>
				<tr>
					<c:choose>
						<c:when test="${lbpmAuthorizeForm.fdAuthorizeType == 4}">
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.fdAuthorizedPost"/>
							</td><td width=35%>
								${lbpmAuthorizeForm.fdAuthorizedPostName}
							</td>
						</c:when>
						<c:otherwise>
							<td class="td_normal_title" width=15%>
								<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorizeItem.fdAuthorizeOrgId"/>
							</td><td width=35% id="lbpmAuthorizeItem">
								${lbpmAuthorizeForm.fdLbpmAuthorizeItemNames}
							</td>
						</c:otherwise>
					</c:choose>
					<td class="td_normal_title" width=15%  id="fdAuthorizedPersonTitle">
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdAuthorizedPerson"/>
					</td><td width=35%  id="fdAuthorizedPerson">
						<c:choose>
							<c:when test="${lbpmAuthorizeForm.fdAuthorizeType == '1'}">
								${lbpmAuthorizeForm.fdAuthorizedReaderNames}
							</c:when>
							<c:otherwise>
								${lbpmAuthorizeForm.fdAuthorizedPersonName}
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<c:if test="${lbpmAuthorizeForm.fdAuthorizeType != 4}">
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdDrafterDeptConstraints"/>
						</td><td width=85% colspan=3>
							<textarea style="width:95%" readonly>${lbpmAuthorizeForm.fdDrafterDeptConstraintNames}</textarea>
						</td>
					</tr>
					<tr id="lbpmAuthorizeRow">
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.lbpmAuthorizeScope"/>
						</td><td width=85% colspan=3>
							<textarea style="width:95%" readonly>${lbpmAuthorizeForm.fdScopeFormAuthorizeCateShowtexts}</textarea>
						</td>
					</tr>
					<!-- 授权流程 -->
					<tr class="lbpm_authorize_process" id="lbpmAuthorizeProcess" style="display: none">
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.process"/>
						</td>
						<td width=85% colspan=3>
							<span>${ requestScope['processName']}</span>
						</td>
					</tr>
					<!-- 授权流程end -->
					<!-- 授权设置 -->
					<tr class="lbpm_authorize_process" id="lbpmAuthorizeSetting" style="display: none;">
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.setting"/>
						</td>
						<td width=85% colspan=3>
							<html:hidden property="fdAuthContents"/>
							<html:hidden property="fdScopeFormModelNames"/>
							<html:hidden property="fdScopeFormTemplateIds"/>
							<html:hidden property="fdScopeFormAuthorizeCateIds"/>
							<table class="tb_normal" width=100% id="TABLE_Setting_Info">
								<tr class="tr_normal_title" style="text-align: center;">
									<td width=10%>序号</td>
									<td width=55%>条件设置</td>
									<td width=35%>被授权人</td>
								</tr>
								<c:forEach items="${requestScope['fdAuthContents']}" var="fdAuthContent" varStatus="vstatus">
								<tr KMSS_IsContentRow="1">
									<td align="center">
										<span class='rowIndex'>${vstatus.index + 1}</span>
										<input type="hidden" name='authContents[${vstatus.index}].fdId' value='${fdAuthContent.fdId }'>
									</td>
									<td>
										<div style="margin-top:5px">
											<kmss:authShow roles="ROLE_LBPM_WORKFLOW_AUTHORITY_MANAGER;ROLE_LBPM_WORKFLOW_AUTHORIZE_ASSIGN">
											<label><input type="radio" readonly="readonly" name="authContents[${vstatus.index}].fdType" value="condition" <c:if test='${fdAuthContent.fdType == "condition" }'>checked</c:if> <c:if test='${fdAuthContent.fdType != "condition" }'>disabled</c:if>>使用条件选择</label>
											<label><input type="radio" readonly="readonly" name="authContents[${vstatus.index}].fdType" value="formula" <c:if test='${fdAuthContent.fdType == "formula" }'>checked</c:if> <c:if test='${fdAuthContent.fdType != "formula" }'>disabled</c:if>>使用高级公式</label>
											</kmss:authShow>
										</div>
										<div class="condition_content" style="<c:if test='${fdAuthContent.fdType != "condition" }'>display: none</c:if>">
										</div>
										<div class="formula_content" style="padding: 10px 2px;<c:if test='${fdAuthContent.fdType != "formula" }'>display: none</c:if>">
											<span><c:out value="${fdAuthContent.fdDisFormula }"></c:out></span>
										</div>
									</td>
									<td align="center">
										${fdAuthContent.fdAuthorizedPersonName }
									</td>
								</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
					<!-- 授权设置end -->
				</c:if>
				<c:if test="${lbpmAuthorizeForm.fdAuthorizeType != 1}">
					<tr id="processTypeRow">
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorizationTitle"/>
						</td><td width=85% colspan=3>
							<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorizationTitle.from"/>${lbpmAuthorizeForm.fdStartTime}
							<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorizationTitle.to"/>${lbpmAuthorizeForm.fdEndTime}
						&nbsp;&nbsp;&nbsp;
							<c:if test="${lbpmAuthorizeForm.fdAuthorizeType == '2'}">
							<input type="checkbox" 
							<c:if test="${lbpmAuthorizeForm.fdExpireRecover=='true'}">
							checked
							</c:if>
							disabled="true"><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdExpireRecover"/>
							</c:if>
							<%-- <input type="checkbox" 
							<c:if test="${lbpmAuthorizeForm.fdExpireDeleted=='true'}">
							checked
							</c:if>
							disabled="true"><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdExpireDeleted"/> --%>
						</td>
					</tr>
				</c:if>
				<c:if test="${lbpmAuthorizeForm.fdAuthorizeType == 4}">
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="sys-lbpmext-businessauth" key="table.lbpmext.businessAuthInfo"/>
						</td>
						<td width=85% colspan=3>
							<div>
								<div>
									<table class="tb_normal" width=100% id="TABLE_DocList_Details" align="center" style="table-layout:fixed;" frame=void>
										<tr>
											<td align="center">
												<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdAuth"/>
											</td>
											<!-- 分类 -->
											<td align="center">
												<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthCate"/>
											</td>
											<td align="center">
												<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdNumber"/>
											</td>
											<td align="center">
												<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdStartTime"/>
											</td align="center">
											<td align="center">
												<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdEndTime"/>
											</td>
											<td align="center">
												<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdType"/>
											</td>
										</tr>
										<c:forEach items="${lbpmAuthorizeForm.fdAuthDetails}" var="item" varStatus="vstatus">
											<tr KMSS_IsContentRow="1">
												<td align="center">
													<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizerId" value="<c:out value='${item.fdAuthorizerId}'/>"/>
													<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizerName" value="<c:out value='${item.fdAuthorizerName}'/>"/>
													<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizedPersonId" value="<c:out value='${item.fdAuthorizedPersonId}'/>" />
													<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizedPersonName" value="<c:out value='${item.fdAuthorizedPersonName}'/>" />
													<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizedPostId" value="<c:out value='${item.fdAuthorizedPostId}'/>" />
													<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizedPostName" value="<c:out value='${item.fdAuthorizedPostName}'/>" />
													<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthId" value="<c:out value='${item.fdAuthId}'/>" />
													<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthName" value="<c:out value='${item.fdAuthName}'/>"/>
													<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdCreatorId" value="<c:out value='${item.fdCreatorId}'/>" />
													<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdCreatorName" value="<c:out value='${item.fdCreatorName}'/>" />
													<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdCreateTime" value="<c:out value='${item.fdCreateTime}'/>" />
													<span class="fdAuthName"><c:out value='${item.fdAuthName}'/></span>
												</td>
												<!--  条目分类名 -->
												<td align="center">
													<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdCateName" value="<c:out value='${item.fdCateName}'/>"/>
													<span class="fdCateName"><c:out value='${item.fdCateName}'/></span>
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
											</tr>
										</c:forEach>
									</table>
								</div>
							</div>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="td_normal_title" width=15%> 
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdCreator"/>
					</td><td width=35%>
						${lbpmAuthorizeForm.fdCreatorName} 
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdCreateTime"/>
					</td><td width=35%>
						${lbpmAuthorizeForm.fdCreateTime} 
					</td>
				</tr>
			
				<c:if test="${lbpmAuthorizeForm.fdStoppedFlag eq '1' }">
				<!--
				<tr>
					<td class="td_normal_title" width=15%> 
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.stop.label"/>
					</td>
					<td width=85% colspan="3">
						<span style="color:red">
							<kmss:message key="sys-lbpmext-authorize:lbpmAuthorize.stop.info" arg0="${lbpmAuthorizeForm.fdStoppedPersonName}" arg1="${lbpmAuthorizeForm.fdStoppedDate}"/>
						</span>
					</td>
				</tr>
				-->
				</c:if>
			</table>
		</td>
	</tr>
	<c:if test="${lbpmAuthorizeForm.fdAuthorizeType ne '3' && lbpmAuthorizeForm.fdAuthorizeType ne '4' }">
		<tr LKS_LabelName="<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorize.historyInfo"/>">
			<td>
				<iframe name="HISTORY_IFRAME" src='<c:url value="/sys/lbpmext/authorize/lbpm_authorize_history/lbpmAuthorizeHistory_index.jsp?fdId=${lbpmAuthorizeForm.fdId}" />' frameBorder=0 width="100%"> 
				</iframe>
			</td>
		</tr>
	</c:if>
	
	<tr LKS_LabelName="<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorize.logInfo"/>">
		<td>
			<iframe name="LOG_IFRAME" src='<c:url value="/sys/lbpmext/authorize/log/index.jsp?fdId=${lbpmAuthorizeForm.fdId}" />' frameBorder=0 width="100%"> 
			</iframe>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>