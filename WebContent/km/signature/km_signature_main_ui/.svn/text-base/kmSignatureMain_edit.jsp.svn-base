<%@page import="com.landray.kmss.km.signature.forms.KmSignatureMainForm"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<template:include ref="default.edit" sidebar="no">
	<%--标签页标题--%>
	<template:replace name="title">
		<c:out value="${kmSignatureMainForm.fdMarkName} - ${ lfn:message('km-signature:module.km.signature') }"></c:out>	
	</template:replace>
	<%--操作按钮--%>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<c:if test="${kmSignatureMainForm.method_GET=='edit'}">
			<%-- 保存 --%>
			<ui:button text="${lfn:message('button.save') }" order="2" onclick="Com_Submit(document.kmSignatureMainForm, 'update');">
			</ui:button>
			</c:if>
			<%--关闭--%>
			<ui:button text="${lfn:message('button.close')}" order="3" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<%--路径导航栏 --%>
	<template:replace name="path">
		<ui:combin ref="menu.path.simplecategory">
				<ui:varParams 
					moduleTitle="${ lfn:message('km-signature:module.km.signature') }" 
					modulePath="/km/signature/" 
										
					autoFetch="false" />
		</ui:combin>
	</template:replace>
	<%--左侧主内容--%>
	<template:replace name="content">
		<html:form action="/km/signature/km_signature_main/kmSignatureMain.do" >
			<script>
			$(function(){
				var fdIsDefault = "${kmSignatureMainForm.fdIsDefault}";
				var $fdIsDefault = $("input[type='checkbox'][name$='fdIsDefault']");
				if ((fdIsDefault=='false') || (fdIsDefault =="")){					
					$fdIsDefault.removeAttr("checked");					
				}
				initDialogAddress();
				var fdIsFreeSign = "${kmSignatureMainForm.fdIsFreeSign}";
				if (fdIsFreeSign == 'true' || fdIsFreeSign == '1'){
					$("input[name='fdPassword']").val("");
					passwordShowOrHide(1);
				}else{
					$("input[name='fdPassword']").val("");
					passwordShowOrHide(0);
				}
			});
			
			function Check_edit(){
				var fdDocType = document.getElementsByName("fdDocType");
				var flag = true;
				for( var i = 0; i < fdDocType.length;i++){
					if(fdDocType[i].checked){
						flag = false;
					}
				}
				if(fdDocType[0].value == null || fdDocType[0].value == ""){
					if(flag){
						alert('<bean:message bundle="km-signature" key="signature.warn11"/>');
						return false;
					}
				}
				
				var p1=document.getElementById("tempFdPassword").value;
				var p2=document.getElementById("confirmPassword").value;
				var isFreeSign = $('input:radio[name="fdIsFreeSign"]:checked').val();
				var password = $("input[name='fdPassword']").val();
            	
				if((p1 == null || p1 == "") && 
						(isFreeSign==0 || isFreeSign=='false' ) && 
						(password=="" || password==null) ){
					alert('<bean:message bundle="km-signature" key="signature.warn8"/>');
					return false;
				}
				if(p1==p2){
					return true;
				}else{
					alert('<bean:message bundle="km-signature" key="signature.warn2"/>');
					return false;
				}
			}
			function validateBothSignPwd(type){			
				var p1=$("#tempFdPassword").val();			
				var p2=$("#confirmPassword").val();
				if(p1 == null || p1 == ""){
					$("#passwordArea").find(".txtstrong").html('<bean:message bundle="km-signature" key="signature.warn8"/>');
					$("#tempFdPassword").focus();
					return false;
				}else{
					$("#passwordArea").find(".txtstrong").html("*");
				}
				
				if(p1 != p2){
					$("#confirmPasswordArea").find(".txtstrong").html('<bean:message bundle="km-signature" key="signature.warn2"/>');
					$("#confirmPassword").focus();
					return false;
				}else{
					$("#confirmPasswordArea").find(".txtstrong").html("*");
				}
				return true;
			}
			function onChangeFreeSign(opt){
				var $opt= $(opt);
				var freeSignValue = 1;
				if ($opt && $opt.selector && ($opt.selector =="false")){//如果是 非免密签名则
					freeSignValue = 0;
				}
				passwordShowOrHide(freeSignValue);
			}

			function passwordShowOrHide(selector){
				var $passwordArea = $("#passwordArea");
				var $confirmPasswordArea = $("#confirmPasswordArea");
				var $tempFdPassword = $("#tempFdPassword");
				var $confirmPassword = $("#confirmPassword");
				if (selector && (selector=="1")){
					$passwordArea.hide();				
					$confirmPasswordArea.hide();
					$tempFdPassword.val("");
					$confirmPassword.val("");
				}else{
					$passwordArea.show();
					$confirmPasswordArea.show();
				}
			}			

			function initDialogAddress(){			
				var address = "Dialog_Address(";
				var mulSelect = true;
				var fdDocType = $("input:radio[name='fdDocType']:checked").val();
				if (!fdDocType){
					fdDocType = $("input[name='fdDocType']").val();
				}
				if (fdDocType==1){ //个人签章
					address = address + "false,";
				}else{
					address = address + "true,";
				}
				address = address + "'fdUsersIds','fdUsersNames',';',";
				if (fdDocType==1){ //个人签章
					address = address + "ORG_TYPE_PERSON)";
				}else{
					address = address + "ORG_TYPE_ORG|ORG_TYPE_DEPT|ORG_TYPE_POST|ORG_TYPE_PERSON)";
				}
				$("input[name='fdUsersIds']").parent().find("div[class=orgelement]").attr("onclick",address);
			}
			
			</script>
			<p class="txttitle"><bean:message bundle="km-signature" key="table.signature"/></p>
			<center>
			<table class="tb_normal" width=95%>
				<input type="hidden" name="fdId" value="${kmSignatureMainForm.fdId}" />
				<input type="hidden" name="fdPassword" value="${kmSignatureMainForm.fdPassword}" />
				<input type="hidden"  name="fdMarkPath" value="${kmSignatureMainForm.fdMarkPath}" />
				<input type="hidden"  name="fdMarkDate" value="${kmSignatureMainForm.fdMarkDate}" />
				<input type="hidden"  name="fdSignatureId" value="1"/>
				
				<input type="hidden" property="method_GET" />
				<input type="hidden" name="fdUserName" value="${kmSignatureMainForm.fdUserName}" />
				<!-- 用户名称
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-signature" key="signature.username"/>
					</td>
					<td>
						${kmSignatureMainForm.fdUserName}
					</td>
				</tr>
				 -->
				<!-- 签章名称 -->
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="km-signature" key="signature.markname"/>
					</td><td width="85%" colspan="3">
						<xform:text property="fdMarkName" required="true" style="width:60%;"/>
						<c:if test="${(docTypeFlag == '1') || (docTypeFlag == '3')}">
						<span id="fdIsDefaultSpan" style="display: ${(kmSignatureMainForm.fdDocType == 1)? '':'none'}">
							<xform:checkbox property="fdIsDefault"  showStatus="edit"  >
					            <xform:simpleDataSource value="${kmSignatureMainForm.fdIsDefault}"> ${lfn:message("km-signature:signature.setDefaultForPersonal")}</xform:simpleDataSource>
					        </xform:checkbox>
				        </span>
					    </c:if>
					</td>
				</tr>
				<!-- 签章分类 -->
				
				<!-- 签章类型 -->
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="km-signature" key="signature.docType"/>
					</td><td width="85%" colspan="3">
						<html:hidden property="fdDocType" value="${kmSignatureMainForm.fdDocType }"></html:hidden>
						<c:if test="${kmSignatureMainForm.fdDocType == 1}">
						   <bean:message bundle="km-signature" key="signature.fdDocType.handWrite"/>
						</c:if>
						<c:if test="${kmSignatureMainForm.fdDocType == 2}">
							<bean:message bundle="km-signature" key="signature.fdDocType.companySignature"/>
						</c:if>
					</td>
				</tr>
				<!-- 授权用户 -->
				<tr id="fdUsersArea">
					<td class="td_normal_title" width="15%">
						<bean:message bundle="km-signature" key="signature.users"/>
					</td><td width="85%" colspan="3">
						<%
							boolean isMulti = true;
							String orgType = "ORG_TYPE_ORG|ORG_TYPE_DEPT|ORG_TYPE_POST|ORG_TYPE_PERSON";
							KmSignatureMainForm mainForm = (KmSignatureMainForm)request.getAttribute("kmSignatureMainForm");
							if("1".equals(mainForm.getFdDocType())) {
								isMulti = false;
								orgType = "ORG_TYPE_PERSON";
							}
						%>
						<xform:address propertyId="fdUsersIds" propertyName="fdUsersNames" orgType="<%=orgType %>" style="width:98%" mulSelect="<%=isMulti %>"></xform:address>
						<span class="txtstrong"><bean:message bundle="km-signature" key="signature.warn14"/></span>
					</td>
				</tr>
				<!-- 是否免密签名 -->
				<c:if test="${(docTypeFlag == '1') || (docTypeFlag == '3')}">
				<tr id="fdIsFreeSignTr" style="display: ${(kmSignatureMainForm.fdDocType == 1)? '':'none'}">
					<td class="td_normal_title" width="15%">
						<bean:message bundle="km-signature" key="signature.fdIsFreeSign"/>
					</td>
					<td width="85%" colspan="3">
						<xform:radio property="fdIsFreeSign" onValueChange="onChangeFreeSign" subject="${ lfn:message('km-signature:signature.fdIsFreeSign') }"  >
							<xform:simpleDataSource value="false">
								<bean:message bundle="km-signature" key="signature.docInForce.no"/>
							</xform:simpleDataSource>
							<xform:simpleDataSource value="true">
								<bean:message bundle="km-signature" key="signature.docInForce.yes"/>
							</xform:simpleDataSource>
						</xform:radio>
					</td>
				</tr>
				</c:if>
				<!-- 用户密码 -->
				<tr id="passwordArea" style="display: none;">
					<td class="td_normal_title" width="15%">
						<bean:message bundle="km-signature" key="signature.password"/>
					</td><td width="85%" colspan="3">
						<input id="tempFdPassword" type="password" name="tempFdPassword" size="50" maxlength="32" required="required" class="inputsgl">
						<span class="txtstrong">*</span>
						<!-- xform:text type="password" property="fdPassword" required="true" size="50" validators="maxLength(32)" / -->
					</td>
				</tr>
				<!-- 确认密码 -->
				<tr id="confirmPasswordArea" style="display: none;">
					<td class="td_normal_title" width="15%">
						<bean:message bundle="km-signature" key="signature.confirmPassword"/>
					</td><td width="85%" colspan="3">
						<input id="confirmPassword" type="password" name="confirmPassword" size="50" maxlength="32"  required="required" class="inputsgl" 
						  onchange="validateBothSignPwd()" >
						<span class="txtstrong">*</span>
						<!-- xform:text type="password" property="fdPassword1" required="true" size="50" validators="maxLength(32)" / -->
					</td>
				</tr>
				
				<!-- 签章信息 -->
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="km-signature" key="signature.markbody"/>
					</td><td width="85%" colspan="3">
						<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
							<c:param name="fdModelId" value="${param.fdId}"/>
							<c:param name="fdModelName" value="com.landray.kmss.km.signature.model.KmSignatureMain"/>
							<c:param name="fdKey" value="sigPic"/>
							<c:param name="fdAttType" value="pic"/>
							<c:param name="fdMulti" value="false"/>
							<c:param name="fdShowMsg" value="false"/>
							<c:param name="enabledFileType" value="jpg,gif,bmp"/>
							<c:param name="proportion" value="false" />
							<c:param name="fdLayoutType" value="pic"/>
							<c:param name="fdViewType" value="pic_single"/>
						    <c:param name="picWidth" value="312" />
						    <c:param name="picHeight" value="234" />
							<c:param name="fdRequired" value="true"/>
						    <c:param name="fdPicContentWidth" value="312"/>
						    <c:param name="fdPicContentHeight" value="234"/>
							<c:param name="fdImgHtmlProperty" value="width=312 height=234"/>
				        </c:import>
				        <div style="float: left;">
				          <span class="txtstrong"><bean:message bundle="km-signature" key="signature.signaturePicTypeTips"/></span>
				        </div>
					</td>
				</tr>
				<!-- 是否有效 -->
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="km-signature" key="signature.fdIsAvailable"/>
					</td><td width="85%" colspan="3">
						<sunbor:enums property="fdIsAvailable" enumsType="common_yesno" elementType="radio" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="km-signature" key="signature.docCreator"/>
					</td><td width="35%">
						<xform:address propertyId="docCreatorId" propertyName="docCreatorName" showLink="false" showStatus="view"></xform:address>
					</td>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="km-signature" key="signature.docCreateTime"/>
					</td><td width="35%">
						<c:out value="${kmSignatureMainForm.docCreateTime }"></c:out>
					</td>
				</tr>
				<c:if test="${not empty kmSignatureMainForm.docAlterorName}">
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="km-signature" key="signature.docAlteror"/>
					</td><td width="35%">
						<xform:address propertyId="docAlterorId" propertyName="docAlterorName" showLink="false" showStatus="view"></xform:address>
					</td>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="km-signature" key="signature.docAlterTime"/>
					</td><td width="35%">
						<c:out value="${kmSignatureMainForm.docAlterTime }"></c:out>
					</td>
				</tr>
				</c:if>
			</table>
			</center>
			<html:hidden property="method_GET"/>
			<script type="text/javascript">
				Com_IncludeFile("calendar.js");
				Com_IncludeFile("common.js");
				Com_IncludeFile("dialog.js");
				Com_IncludeFile("doclist.js");
				Com_IncludeFile("jquery.js");
				Com_IncludeFile("data.js");
				var _validation = $KMSSValidation();
				$KMSSValidation(document.forms[0]);
				Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function() {
					var $fdIsDefaults = $("input[type='checkbox'][name$='fdIsDefault']");
					if ($fdIsDefaults && $fdIsDefaults.length>0){
		    	   	 	$("input[name='fdIsDefault']").val($fdIsDefaults[0].checked);
					}
		    	    if (Check_edit()){
		    	    	return true ;
			    	}else{
			    		return false ;
		    	    }
			    	
			    };
			</script>
		</html:form>
	</template:replace>
</template:include>