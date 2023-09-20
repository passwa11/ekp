<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">
		<script language="JavaScript">
			Com_IncludeFile("dialog.js");
		</script>
		<script language="JavaScript">
			seajs.use(['lui/dialog'],function(dialog) {
				//提交验证类别不可为空
				window.validateForm=function (){
				var fdCateId = document.getElementsByName("fdCategoryId")[0].value;
					if(fdCateId == null || fdCateId == "" || fdCateId == typeof("undefined")){
						dialog.alert("${lfn:message('sys-anonym:error.notSelect.cate')}");
						return false;
					}
					return true;
				}
			});
		</script>
		<html:form action="/sys/anonym/sys_anonym_main/sysAnonymMain.do" onsubmit="return validateForm();">
			<center>
				<p class="txttitle">
					<bean:message bundle="sys-anonym"
								key="button.publish.label" />
				</p>
				<table class="tb_normal" width=95%>
				 	<html:hidden property="fdId" />
				 	<html:hidden property="fdModelId" value="${fdModelId}"/>
					<html:hidden property="fdModelName" value="${fdModelName}"/>
					<html:hidden property="fdModelName" value="${fdKey}"/>
					<tr>
						<td class="td_normal_title" width=30%>
							<bean:message  bundle="sys-anonym" key="sysAnonymMain.fdCategory" />
						</td>
						<td width=60%>			
							<div id="_xform_fdCateId" _xform_type="dialog">
                                  <xform:dialog propertyId="fdCategoryId" propertyName="fdCategoryName" showStatus="edit" style="width:95%;">
                                      Dialog_Tree(false, 'fdCategoryId', 'fdCategoryName', ',', 'sysAnonymCateService&parentId=!{value}&fdId=${sysAnonymMainForm.fdCategoryId}&modelName=${fdModelName}&showType=read', '${lfn:message('sys-anonym:table.sysAnonymCate')}', null, null, null, null, false, null);
                                  </xform:dialog>
                                  <span class="txtstrong">*</span>	
                              </div>
						 </td>
					</tr>
				</table>
				<div style="padding-top:17px">
				    <ui:button  text="${lfn:message('button.update') }" onclick="Com_Submit(document.sysAnonymMainForm, 'addManualAnonym');" />
			        <ui:button style="padding-left:5px" text="${lfn:message('button.close') }" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();" />
				</div>
			</center>
			<html:hidden property="method_GET"/>
		</html:form> 
	</template:replace>
</template:include>