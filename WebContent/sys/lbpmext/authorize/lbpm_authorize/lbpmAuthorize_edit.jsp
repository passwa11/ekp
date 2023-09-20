<%@page import="com.landray.kmss.sys.lbpmext.authorize.forms.LbpmAuthorizeForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
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
<script>Com_IncludeFile('doclist.js');</script>
<style>
	.lui_businessauth_frame{
		height: 38px;
		line-height: 38px;
	}
	.lui_businessauth_frame .lui_toolbar_btn_l{
		text-align: center;
		border: 1px #bbbbbb solid;
		background-color: #fff;
	}
	.lui_businessauth_frame .lui_widget_btn_txt{
		color: #666 !important;
	}
	.lui_businessauth_frame .lui_widget_btn:hover .lui_widget_btn_txt {
		color: #fff !important;
	}
	.btn_txt{
		color: #2574ad;
		border-bottom: 1px solid transparent
	}
</style>
<%@ include file="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize_script.jsp"%>
<html:form action="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do" onsubmit="return validateLbpmAuthorizeForm(this);">
	<%
		String currentUserId = UserUtil.getUser().getFdId();
		pageContext.setAttribute("currentUserId", currentUserId);
	%>
	<div id="optBarDiv">
		<c:if test="${lbpmAuthorizeForm.method_GET=='edit'}">
			<input type=button value="<bean:message key="button.save"/>"
				   onclick="validateSubmitForm('update');">
		</c:if>
		<c:if test="${lbpmAuthorizeForm.method_GET=='add'}">
			<input type=button value="<bean:message key="button.save"/>"
				   onclick="validateSubmitForm('save');">
		</c:if>
		<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
	</div>

	<kmss:windowTitle
			moduleKey="sys-lbpmext-authorize:table.lbpmAuthorize"/>

	<p class="txttitle"><bean:message  bundle="sys-lbpmext-authorize" key="table.lbpmAuthorize"/></p>

	<center>
		<table class="tb_normal" width=95%>
			<html:hidden property="fdId"/>
			<html:hidden property="currentUserRoleIds"/>
			<html:hidden property="currentUserRoleNames"/>
			<html:hidden property="fdLbpmAuthorizeItemIds"/>
			<html:hidden property="fdLbpmAuthorizeItemNames"/>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.catagory.choose"/>
				</td>
				<td width=85% colspan=3>
					<xform:radio property="fdAuthorizeCategory" htmlElementProperties="onClick=authorizeCatagoryChanged(this);"  >
						<xform:enumsDataSource enumsType="lbpmAuthorize_catagory"/>
					</xform:radio>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<!-- 授权类型 -->
					<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdAuthorizeType"/>
				</td>
				<td width=35%>
					<xform:radio property="fdAuthorizeType" htmlElementProperties="onClick=authorizeTypeChanged(this);"  >
						<xform:enumsDataSource enumsType="lbpmAuthorize_authorizeType"/>
					</xform:radio>
					<p style="display:none;color: red;" id='authorizeTypeDesc'></p>
					<c:if test="${lbpmAuthorizeForm.method_GET=='add'}">
						<script type="text/javascript">
							var SettingInfo = new KMSSData().AddBeanData("lbpmSettingInfoService").GetHashMapArray()[0];
							if(SettingInfo.businessauth!="true"||SettingInfo.personBusinessauth!="true"){
								$("input[name='fdAuthorizeType'][value=4]").parent().hide();
							}
							if(SettingInfo.advanceApprovalAuth!="true"){
								$("input[name='fdAuthorizeType'][value=3]").parent().hide();
							}
							$("#authorizeTypeDesc").show();
						</script>
					</c:if>
					<c:if test="${lbpmAuthorizeForm.method_GET=='edit'}">
						<script type="text/javascript">
							$("input[name='fdAuthorizeType']").each(function(){
								if(this.checked){
									$(this).hide();
								}else{
									$(this).parent().hide();
								}
							});
						</script>
					</c:if>
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdAuthorizer"/>
				</td>
				<td width=35%>
					<c:if test="${lbpmAuthorizeForm.canChangeAuthorizerAuth != 'true'}">
						<html:hidden property="fdAuthorizerId"/>
						<html:hidden property="fdAuthorizerName"/>
						${lbpmAuthorizeForm.fdAuthorizerName}
					</c:if>
					<c:if test="${lbpmAuthorizeForm.canChangeAuthorizerAuth == 'true'}">
						<xform:address propertyId="fdAuthorizerId" propertyName="fdAuthorizerName" orgType="ORG_TYPE_PERSON"
									   showStatus="edit" onValueChange="showAuthorizeItems" style="width:90%">
						</xform:address>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<span class="lbpm_authorize_row"><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorizeItem.fdAuthorizeOrgId"/></span>
					<span class="lbpm_businessautho_row"  style="display: none;">
				<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.fdAuthorizedPost"/>
			</span>
				</td>
				<td width=35% id="lbpmAuthorizeItem">
					<div id="fdLbpmAuthorizeItemDiv" class="lbpm_authorize_row"></div>
					<div class="lbpm_businessautho_row"  style="display: none;">
						<xform:address propertyId="fdAuthorizedPostId" propertyName="fdAuthorizedPostName" orgType="ORG_TYPE_POST"
									   showStatus="edit" style="width:90%" validators="checkrequired" subject="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdAuthorizedPost') }">
						</xform:address>
					</div>
				</td>
				<td class="td_normal_title" id="fdAuthorizedPersonTitle" width=15%>
					<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdAuthorizedPerson"/>
				</td>
				<td width=35% id='fdAuthorizedPerson'>
					<div class="lbpm_authorize_other_row">
						<xform:address propertyId="fdAuthorizedPersonId" propertyName="fdAuthorizedPersonName" orgType="ORG_TYPE_PERSON" subject="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizedPerson') }"
									   showStatus="edit" onValueChange="showAuthorizeItems" htmlElementProperties="data-propertyid='fdAuthorizedPersonId'" validators="checkIsAuthorizer" required="true" style="width:90%">
						</xform:address>
					</div>
					<div class="lbpm_authorize_read_row">
						<xform:address propertyId="fdAuthorizedReaderIds" propertyName="fdAuthorizedReaderNames" orgType="ORG_TYPE_PERSON" subject="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizedPerson') }"
									   showStatus="edit" mulSelect="true" htmlElementProperties="data-propertyid='fdAuthorizedReaderIds'" validators="checkIsAuthorizer" required="true" style="width:90%">
						</xform:address>
					</div>
				</td>
			</tr>
			<tr class="lbpm_authorize_row">
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdDrafterDeptConstraints"/>
				</td>
				<td width=85% colspan=3>
					<html:hidden property="fdDrafterDeptConstraintIds" />
					<textarea style="width:90%" readonly name="fdDrafterDeptConstraintNames">${lbpmAuthorizeForm.fdDrafterDeptConstraintNames}</textarea>
					<br>
					<a href="#" onclick="Dialog_Address(true, 'fdDrafterDeptConstraintIds', 'fdDrafterDeptConstraintNames', ';', ORG_TYPE_ORGORDEPT|ORG_TYPE_POSTORPERSON);">
						<bean:message key="dialog.selectOrg"/>
					</a><bean:message key="lbpmAuthorize.lbpmAuthorizeScope.note" bundle="sys-lbpmext-authorize"/>
				</td>
			</tr>

			<tr class="lbpm_authorize_row" id="lbpmAuthorizeRow">
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.lbpmAuthorizeScope"/>
				</td>
				<td width=85% colspan=3>
					<html:hidden property="fdScopeFormAuthorizeCateIds"/>
					<html:hidden property="fdScopeFormAuthorizeCateNames"/>
					<html:hidden property="fdScopeFormModelNames"/>
					<html:hidden property="fdScopeFormModuleNames"/>
					<html:hidden property="fdScopeFormTemplateIds"/>
					<html:hidden property="fdScopeFormTemplateNames"/>
					<html:hidden property="scopeTempValues"/>
					<textarea style="width:90%" readonly name="fdScopeFormAuthorizeCateShowtexts">${lbpmAuthorizeForm.fdScopeFormAuthorizeCateShowtexts}</textarea>
					<br>
					<a href="#"
					   onclick="importAuthorizeCateDialog();">
						<bean:message key="dialog.selectOther" /></a><bean:message key="lbpmAuthorize.lbpmAuthorizeScope.note" bundle="sys-lbpmext-authorize"/>
				</td>
			</tr>
			<!-- 授权流程 -->
			<tr class="lbpm_authorize_process" id="lbpmAuthorizeProcess" style="display: none">
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.process"/>
				</td>
				<td width=85% colspan=3>
					<input type="hidden" name="processIdVal" value="${lbpmAuthorizeForm.fdAuthorizeCategory eq '1' ? lbpmAuthorizeForm.scopeTempValues : ''}">
					<input name="processNameVal" id="processName" subject='${lfn:message("sys-lbpmext-authorize:lbpmAuthorize.process") }' validate='required' value="${ requestScope['processName']}" class="inputsgl" style="width:400px" readonly>
					<span id="processId">
				<a href="#" onclick="showDocSgl();"><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.process.choice"/></a><span style="color:red"> *</span>
			</span>
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
					<table class="tb_normal" width=100% id="TABLE_Setting_Info">
						<tr class="tr_normal_title" style="text-align: center;">
							<td width=10%><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.setting.number"/></td>
							<td width=55%><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.setting.condition"/></td>
							<td width=20%><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.setting.authorized.person"/></td>
							<td width=15%><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.setting.operation"/></td>
						</tr>
							<%-- 基准行，KMSS_IsReferRow = 1 --%>
						<tr style="display:none;" KMSS_IsReferRow="1">
							<!-- 序号列，KMSS_IsRowIndex = 1 -->
							<td KMSS_IsRowIndex="1" align="center">
								<span class='rowIndex'>!{index}</span>
							</td>
							<td>
								<input type="hidden" name='authContents[!{index}].fdId'>
								<div style="margin-top:5px;min-height: 20px">
									<label style='display:none;<kmss:authShow roles="ROLE_LBPM_WORKFLOW_AUTHORITY_MANAGER;ROLE_LBPM_WORKFLOW_AUTHORIZE_ASSIGN">display:inline-block;</kmss:authShow>'>
										<input type="radio" name="authContents[!{index}].fdType" value="condition" onchange="switchSettingType(this)" checked><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.setting.use.condition"/>
									</label>
									<label style='display:none;<kmss:authShow roles="ROLE_LBPM_WORKFLOW_AUTHORITY_MANAGER;ROLE_LBPM_WORKFLOW_AUTHORIZE_ASSIGN">display:inline-block;</kmss:authShow>'>
										<input type="radio" name="authContents[!{index}].fdType" value="formula" onclick="switchSettingType(this)"><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.setting.use.formulas"/>
									</label>
									<label  class="add_condition_btn" style="float:right"><a href="javascript:void(0)" style="color: #4285f4;" onclick="addCondition(this);"><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.setting.new.conditions"/></a></label>
								</div>
								<div class="condition_content">
								</div>
								<div class="formula_content" style="display: none">
									<input name="authContents[!{index}].fdFormula" type="hidden"/>
									<xform:text property="authContents[!{index}].fdDisFormula" htmlElementProperties="subject='条件设置' validate='required'" showStatus="readOnly" style="width: 80%;"></xform:text>
									<a href="javascript:void(0)" onclick="openFormulaDialog('authContents[!{index}].fdFormula','authContents[!{index}].fdDisFormula')"><bean:message key="button.select"/></a>
									<span class="txtstrong">*</span>
								</div>
							</td>
							<td>
								<xform:address subject="被授权人" propertyId="authContents[!{index}].fdAuthorizedPersonId" propertyName="authContents[!{index}].fdAuthorizedPersonName" orgType="ORG_TYPE_PERSON"
											   showStatus="edit" onValueChange="showAuthorizeItems" htmlElementProperties="data-propertyid='fdAuthorizedPersonId'" validators="checkIsAuthorizer" required="true" style="width:90%">
								</xform:address>
							</td>
							<td align="center">
								<span class="setting_del_btn" onclick="DocList_DeleteRow(this.parentNode.parentNode)"><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.setting.delete"/></span>
							</td>
						</tr>
							<%-- 内容行 --%>
						<c:forEach items="${requestScope['fdAuthContents']}" var="fdAuthContent" varStatus="vstatus">
							<tr KMSS_IsContentRow="1">
								<td align="center">
									<span class='rowIndex'>${vstatus.index + 1}</span>
									<input type="hidden" name='authContents[${vstatus.index}].fdId' value='${fdAuthContent.fdId }'>
								</td>
								<td>
									<div style="margin-top:5px;min-height: 20px;">
										<label style='display:none;<kmss:authShow roles="ROLE_LBPM_WORKFLOW_AUTHORITY_MANAGER;ROLE_LBPM_WORKFLOW_AUTHORIZE_ASSIGN">display:inline-block;</kmss:authShow>'>
											<input type="radio" name="authContents[${vstatus.index}].fdType" value="condition" onchange="switchSettingType(this)" <c:if test='${fdAuthContent.fdType == "condition" }'>checked</c:if>><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.setting.use.condition"/>
										</label>
										<label style='display:none;<kmss:authShow roles="ROLE_LBPM_WORKFLOW_AUTHORITY_MANAGER;ROLE_LBPM_WORKFLOW_AUTHORIZE_ASSIGN">display:inline-block;</kmss:authShow>'>
											<input type="radio" name="authContents[${vstatus.index}].fdType" value="formula" onchange="switchSettingType(this)" <c:if test='${fdAuthContent.fdType == "formula" }'>checked</c:if>><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.setting.use.formulas"/>
										</label>
										<label class="add_condition_btn" style="float:right"><a style="color: #4285f4;" href="javascript:void(0)" onclick="addCondition(this);"><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.setting.new.conditions"/></a></label>
									</div>
									<div class="condition_content" style="<c:if test='${fdAuthContent.fdType != "condition" }'>display: none</c:if>">
									</div>
									<div class="formula_content" style="<c:if test='${fdAuthContent.fdType != "formula" }'>display: none</c:if>">
										<input name="authContents[${vstatus.index}].fdFormula" type="hidden" value='<c:out value="${fdAuthContent.fdFormula }"></c:out>'/>
										<xform:text property="authContents[${vstatus.index}].fdDisFormula"  htmlElementProperties="subject='条件设置' validate='required'" value="${fdAuthContent.fdDisFormula }" showStatus="readOnly" style="width: 80%;"></xform:text>
										<a href="javascript:void(0)" onclick="openFormulaDialog('authContents[${vstatus.index}].fdFormula','authContents[${vstatus.index}].fdDisFormula')"><bean:message key="button.select"/></a>
										<span class="txtstrong">*</span>
									</div>
								</td>
								<td>
									<xform:address subject="被授权人" propertyId="authContents[${vstatus.index}].fdAuthorizedPersonId" propertyName="authContents[${vstatus.index}].fdAuthorizedPersonName" idValue="${fdAuthContent.fdAuthorizedPersonId }" nameValue="${fdAuthContent.fdAuthorizedPersonName }" orgType="ORG_TYPE_PERSON"
												   showStatus="edit" onValueChange="showAuthorizeItems" htmlElementProperties="data-propertyid='fdAuthorizedPersonId'" validators="checkIsAuthorizer" required="true" style="width:90%">
									</xform:address>
								</td>
								<td align="center">
									<span class="setting_del_btn" onclick="DocList_DeleteRow(this.parentNode.parentNode)"><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.setting.delete"/></span>
								</td>
							</tr>
						</c:forEach>
					</table>
					<div class="tb_normal" width=100% id="" style="text-align:center;margin-top: 20px;border:1px dashed #d2d2d2;cursor: pointer;" onclick="addSettingRow();">
						<span class="lui_icon_s lui_icon_s_icon_plusx" ></span><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.setting.added"/>
					</div>
				</td>
			</tr>
			<!-- 授权设置end -->
			<tr id="processTypeRow">
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorizationTitle"/>
				</td>
				<td width=85% colspan=3>
					<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorizationTitle.from"/>
					<xform:datetime property="fdStartTime" dateTimeType="datetime" required="true" style="width:20%;">
					</xform:datetime>
					<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorizationTitle.to"/>
					<xform:datetime property="fdEndTime" dateTimeType="datetime" required="true" style="width:20%;">
					</xform:datetime>
					&nbsp;&nbsp;&nbsp;
					<label id="expireRecoverLabel">
						<xform:checkbox property="fdExpireRecover">
							<xform:simpleDataSource value="true" bundle="sys-lbpmext-authorize" textKey="lbpmAuthorize.fdExpireRecover"/>
						</xform:checkbox>
					</label>
					<script type="text/javascript">
						$(function(){
							if($("input[name='fdAuthorizeType']").val()==2 || ${lbpmAuthorizeForm.fdAuthorizeType == '2'}){
								$("#expireRecoverLabel").show();
							}else{
								$("#expireRecoverLabel").hide();
							}

						});
					</script>
						<%-- <label>
                        <xform:checkbox property="fdExpireDeleted">
                            <xform:simpleDataSource value="true" bundle="sys-lbpmext-authorize" textKey="lbpmAuthorize.fdExpireDeleted"/>
                        </xform:checkbox>
                        </label> --%>
				</td>
			</tr>

			<tr class="lbpm_businessautho_row" style="display: none;">
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="sys-lbpmext-businessauth" key="table.lbpmext.businessAuthInfo"/>
				</td>
				<td width=85% colspan=3>
					<div>
						<div class="lui_businessauth_frame" style="float:right;margin-right:30px;">
							<ui:button text="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.add') }" onclick="addDetail();"/>
						</div>
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
										<input type="hidden" name="fdAuthDetails[!{index}].fdId" />
										<input type="hidden" name="fdAuthDetails[!{index}].fdAuthorizerId" />
										<input type="hidden" name="fdAuthDetails[!{index}].fdAuthorizerName" />
										<input type="hidden" name="fdAuthDetails[!{index}].fdAuthorizedPersonId" />
										<input type="hidden" name="fdAuthDetails[!{index}].fdAuthorizedPersonName" />
										<input type="hidden" name="fdAuthDetails[!{index}].fdAuthorizedPostId" />
										<input type="hidden" name="fdAuthDetails[!{index}].fdAuthorizedPostName" />
										<input type="hidden" name="fdAuthDetails[!{index}].fdAuthId" />
										<input type="hidden" name="fdAuthDetails[!{index}].fdAuthName"/>
										<input type="hidden" name="fdAuthDetails[!{index}].fdCreatorId" />
										<input type="hidden" name="fdAuthDetails[!{index}].fdCreatorName" />
										<input type="hidden" name="fdAuthDetails[!{index}].fdCreateTime" />
										<span class="fdAuthName"></span>
									</td>
									<!-- 条目分类名 -->
									<td align="center">
										<input type="hidden" name="fdAuthDetails[!{index}].fdCateName"/>
										<span class="fdCateName"></span>
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
								<c:forEach items="${lbpmAuthorizeForm.fdAuthDetails}" var="item" varStatus="vstatus">
									<tr KMSS_IsContentRow="1">
										<td align="center">
											<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdId" value="<c:out value='${item.fdId}'/>"/>
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
										<!-- 条目分类名 -->
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

			<c:if test="${lbpmAuthorizeForm.method_GET=='edit'}">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdCreator"/>
					</td>
					<td width=35%>
						<html:hidden property="fdCreatorName"/>
							${lbpmAuthorizeForm.fdCreatorName}
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdCreateTime"/>
					</td>
					<td width=35%>
						<html:hidden property="fdCreateTime"/>
							${lbpmAuthorizeForm.fdCreateTime}
					</td>
				</tr>
			</c:if>
			<tr class="lbpm_description_row">
				<td colspan="4" style="color:red">
					<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorize.description"/><br/>
					<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorize.description3"/>
				</td>
			</tr>
		</table>
	</center>
	<html:hidden property="method_GET"/>
</html:form>
<html:javascript formName="lbpmAuthorizeForm"  cdata="false"
				 dynamicJavascript="true" staticJavascript="false"/>
<script>
	_$validation.addValidator('checkrequired',
			"<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthInfo.requiredMsg'/>",
			function(v,e,o){
				var fdAuthorizeCategory = $("input[name='fdAuthorizeCategory']:checked").val();
				if(fdAuthorizeCategory == "1"){//当选择流程信息条件方式时不需要必填
					return true;
				}
				var fdAuthorizedPersonId = $("input[name='fdAuthorizedPersonId']").val();
				var fdAuthorizedPostId = $("input[name='fdAuthorizedPostId']").val();
				if(!fdAuthorizedPersonId && !fdAuthorizedPostId){
					return false;
				}
				new Reminder($("input[name='fdAuthorizedPersonName']")[0]).hide();
				new Reminder($("input[name='fdAuthorizedPostName']")[0]).hide();
				return true;
			}
	);

	_$validation.addValidator('checktime',
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

	_$validation.addValidator('checkendtime',
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

	_$validation.addValidator('checkIsAuthorizer',
			"<bean:message bundle='sys-lbpmext-authorize' key='lbpmAuthorize.checkIsAuthorizer'/>",
			function(v,e,o){
				if(v){
					var fdAuthorizerId = $("[name='fdAuthorizerId']").val();
					if(fdAuthorizerId){
						var propertyid = $(e).data("propertyid");
						var checkids = $(e).closest(".inputselectsgl").find("[name$='"+propertyid+"']").val();
						return checkids.indexOf(fdAuthorizerId)==-1;
					}
				}
				return true;
			}
	);

	DocList_Info.push("TABLE_Setting_Info");
	DocList_Info.push("TABLE_Setting_Details");
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>