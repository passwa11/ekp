<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="no">
	<%-- 标签页标题--%>
	<template:replace name="title">
		<c:out value="${ lfn:message('km-signature:table.signature') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<%--操作按钮--%>
			<script>
			seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar'], function($, dialog , topic,toolbar) {
				//确认框
				window.LUIconfirm=function(msg,url){
					window.signatureURL=url;
					dialog.confirm(msg,function(value){
						if(value==true){
							Com_OpenWindow(window.signatureURL,"_self");
						}
					});
				};	
				
				window.resetPassword = function(url,obj) {
					dialog.iframe(url,"${ lfn:message('km-signature:module.km.signature') } - ${lfn:message('km-signature:signature.resetPassword') }",
					   function(value){
						
					   },
					   { "width" : 650,"height" : 300}
					);
				};

				window.invalidated = function(){
					dialog.confirm("${ lfn:message('km-signature:signature.fdIsAvailableConfirm') }",function(value){
						if(value==true){
							var url = "${LUI_ContextPath}/km/signature/km_signature_main/kmSignatureMain.do?method=invalidated&fdId=${param.fdId}";
							window.location.href = url;
						}
					});
				};

				window.settingDefaultSignature = function(){
					dialog.confirm("${lfn:message('km-signature:signature.fdIsDefaultConfirm')}",function(value){
						if(value==true){
							var url = "${LUI_ContextPath}/km/signature/km_signature_main/kmSignatureMain.do?method=settingDefaultSignature&fdId=${param.fdId}";
							window.location.href = url;		
						}
					});
				};
			});
			</script>
			<%-- 重置密码 --%>
			<kmss:auth requestURL="/km/signature/km_signature_main/kmSignatureMain.do?method=chgPwd&fdId=${param.fdId}" requestMethod="GET">
				<c:if test="${kmSignatureMainForm.fdIsFreeSign!='true'}">
					<ui:button text="${lfn:message('km-signature:signature.resetPassword')}" order="1" onclick="resetPassword('kmSignatureMain.do?method=chgPwd&fdId=${param.fdId}','_self');">
					</ui:button>
				</c:if>
			</kmss:auth>
			
			<%-- 设置默认 --%>
			<kmss:auth requestURL="/km/signature/km_signature_main/kmSignatureMain.do?method=settingDefaultSignature&fdId=${param.fdId}" requestMethod="GET">
				<c:if test="${kmSignatureMainForm.fdIsDefault=='false' && kmSignatureMainForm.fdDocType == '1'}">
				<ui:button text="${lfn:message('km-signature:signature.setDefault')}" order="2" onclick="settingDefaultSignature();">
				</ui:button>
				</c:if>
			</kmss:auth>
			<%-- 置为无效 --%>
			<kmss:auth requestURL="/km/signature/km_signature_main/kmSignatureMain.do?method=invalidated&fdId=${param.fdId}">
				<c:if test="${kmSignatureMainForm.fdIsAvailable}">
					<ui:button text="${lfn:message('km-signature:signature.isNotAvailable')}" order="5" onclick="invalidated();">
					</ui:button>
				</c:if>
			</kmss:auth>
			<%-- 编辑 --%>
			<kmss:auth requestURL="/km/signature/km_signature_main/kmSignatureMain.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('button.edit')}" order="3" onclick="Com_OpenWindow('kmSignatureMain.do?method=edit&fdId=${param.fdId}','_self');">
				</ui:button>
			</kmss:auth>
			<%-- 删除 --%>
			<kmss:auth requestURL="/km/signature/km_signature_main/kmSignatureMain.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('button.delete')}" order="4" onclick="LUIconfirm('${ lfn:message('page.comfirmDelete')}','kmSignatureMain.do?method=delete&fdId=${param.fdId}');">
				</ui:button>
			</kmss:auth>
			<%--关闭--%>
			<ui:button text="${lfn:message('button.close')}" order="6" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<%--导航路径--%>
	<template:replace name="path">
		<ui:combin ref="menu.path.simplecategory">
			<ui:varParams 
				moduleTitle="${ lfn:message('km-signature:module.km.signature') }" 
				modulePath="/km/signature/" 
				
				autoFetch="false"
				href="/km/signature/" />
				
		</ui:combin>
	</template:replace>
	<%--主文档--%>
	<template:replace name="content"> 
		<center>
		<br>
		<table class="tb_normal" width=95%>
			<!-- 用户名称
			<tr>
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.username"/>
				</td><td width="85%">
					<xform:text property="fdUserName" style="width:97%" />
				</td>
			</tr>
			 -->
			<!-- 签章名称 -->
			<tr>
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.markname"/>
				</td><td width="35%">
					<c:out value="${kmSignatureMainForm.fdMarkName}"></c:out>
				</td>
				<!-- 是否有效 -->
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.fdIsAvailable"/>
				</td><td width="35%">
					<sunbor:enumsShow value="${kmSignatureMainForm.fdIsAvailable}" enumsType="common_yesno" />
				</td>
			</tr>
			<c:if test="${kmSignatureMainForm.fdDocType == '1'}">
			<tr>
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.setDefaultForPersonal"/>
				</td><td width="35%">
					<sunbor:enumsShow value="${kmSignatureMainForm.fdIsDefault}" enumsType="common_yesno" />
				</td>
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.fdIsFreeSign"/>
				</td><td width="35%">
					<sunbor:enumsShow value="${kmSignatureMainForm.fdIsFreeSign}" enumsType="common_yesno" />
				</td>
			</tr>
			</c:if>
			<!-- 签章分类 -->
			
			<!-- 签章信息 -->
			<tr>
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.markbody"/>
				</td><td width="85%" colspan="3">
					<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
						<c:param name="fdKey" value="sigPic"/>
						<c:param name="fdAttType" value="pic"/>
						<c:param name="fdMulti" value="false"/>
						<c:param name="fdShowMsg" value="false"/>
						<c:param name="formBeanName" value="kmSignatureMainForm" />
						<c:param name="fdModelId" value="${param.fdId}"/>
						<c:param name="fdModelName" value="com.landray.kmss.km.signature.model.KmSignatureMain"/>
						<c:param name="proportion" value="false" />
						<c:param name="fdLayoutType" value="pic"/>
						<c:param name="fdViewType" value="pic_single"/>
					    <c:param name="picWidth" value="312" />
					    <c:param name="picHeight" value="234" />
					    <c:param name="fdPicContentWidth" value="312"/>
					    <c:param name="fdPicContentHeight" value="234"/>
						<c:param name="fdImgHtmlProperty" value="width=312 height=234"/>
			        </c:import>
				</td>
			</tr>
			<!-- 签章类型 -->
			<tr>
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.docType"/>
				</td><td width="85%" colspan="3">
					<c:if test="${kmSignatureMainForm.fdDocType == '1'}">
						<bean:message bundle="km-signature" key="signature.fdDocType.handWrite"/>
					</c:if>
					<c:if test="${kmSignatureMainForm.fdDocType == '2'}">
						<bean:message bundle="km-signature" key="signature.fdDocType.companySignature"/>
					</c:if>
				</td>
			</tr>
			<!-- 授权用户 -->
			<tr>
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.users"/>
				</td><td width="85%" colspan="3">
					<c:out value="${kmSignatureMainForm.fdUsersNames}"></c:out>
				</td>
			</tr>
			<!-- 印章保存时间
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-signature" key="signature.markdate"/>
				</td><td width="85%">
					<xform:datetime property="fdMarkDate" dateTimeType="datetime"/>
				</td>
			</tr>
			 -->
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
	</template:replace>
</template:include>