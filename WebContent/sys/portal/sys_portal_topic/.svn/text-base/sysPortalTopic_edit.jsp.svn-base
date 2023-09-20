<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<template:include ref="default.edit" width="95%" sidebar="no">
	<template:replace name="title"> 
		${ lfn:message('sys-portal:table.sysPortalTopic') }
	</template:replace>
 	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav" style="height:40px;line-height:40px;">
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" >
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-portal:module.sys.portal') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-portal:nav.sys.portal.portlet') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-portal:table.sysPortalTopic') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<c:choose>
					<c:when test="${ sysPortalTopicForm.method_GET == 'add' }">
						<ui:button text="${lfn:message('button.save') }" order="2" onclick=" Com_Submit(document.sysPortalTopicForm, 'save');">
						</ui:button>
					</c:when>
					<c:when test="${ sysPortalTopicForm.method_GET == 'edit' }">					
						<ui:button text="${lfn:message('button.update') }" order="2" onclick=" Com_Submit(document.sysPortalTopicForm, 'update');">
						</ui:button>	
						<kmss:auth requestURL="/sys/portal/sys_portal_topic/sysPortalTopic.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
							<ui:button order="3" text="${lfn:message('button.delete') }" onclick="deleteTopic();">
							</ui:button>
						</kmss:auth> 						
					</c:when>
				</c:choose>
				<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();">
				</ui:button>
		</ui:toolbar>
	</template:replace>	
	<template:replace name="content">
		<script>
		Com_IncludeFile("doclist.js");
		</script>
		<html:form action="/sys/portal/sys_portal_topic/sysPortalTopic.do">
		<p class="txttitle"><bean:message bundle="sys-portal" key="table.sysPortalTopic"/></p>
		<center>
		<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-portal" key="sysPortalTopic.fdName"/>
		</td>
		<td width="85%" colspan="3">
			<xform:text property="fdName" style="width:85%" validators="required maxLength(200)"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-portal" key="sysportal.switch.anonymous"/>
		</td>
		<td colspan="3" >
			 	<c:import
					url="/sys/portal/designer/jsp/sys_anonym_edit.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="sysPortalTopicForm" />
				</c:import>
		</td>
	</tr>
	<html:hidden property="fdPortalId" />
	<tr>
		<td class="td_normal_title"><bean:message key="sysPortalTopic.link" bundle="sys-portal"/></td>
		<td colspan="3">
			<c:if test="${ sysPortalTopicForm.method_GET == 'add' }">
				<xform:text property="fdTopUrl" style="width:85%" value="http://" validators="maxLength(1000)"/>
			</c:if>
			<c:if test="${ sysPortalTopicForm.method_GET == 'edit' }">
				<xform:text property="fdTopUrl" style="width:85%;" validators="maxLength(1000)"/>
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title"><bean:message key="sysPortalTopic.image" bundle="sys-portal"/></td>
		<td colspan="3">
			<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
		    	<c:param name="fdKey" value="sysPortalTopic_fdKey"/>
		    	<c:param name="fdMulti" value="false"/>
		    	<c:param name="fdAttType" value="pic"/>
		    	<c:param name="fdImgHtmlProperty" value="height=150"/>
			</c:import>
			<div class="imgPreview" style="margin-top: 10px;<c:if test="${empty sysPortalTopicForm.fdImg}">display: none</c:if>">
				<img class="img_url" width="200" height="150" src="${LUI_ContextPath}${sysPortalTopicForm.fdImg}" border="0">
				<div class="img_name"></div>
			</div>
			<div class="pop-form-btn pop-form-btn-primary" style="cursor: pointer;background-color: #4285f4;border-color: #4285f4;color: white;display: inline-block; height: 26px; line-height: 26px; padding: 0px 20px; font-size: 14px; vertical-align: top;" onclick="selectIcon()">
				选择系统图片
			</div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:common.msg.editors') }</td>
		<td colspan="3">
			<xform:address textarea="true" mulSelect="true" propertyId="fdEditorIds" propertyName="fdEditorNames" style="width:100%;height:90px;" ></xform:address>
		</td>
	</tr> 
	<c:if test="${sysPortalTopicForm.method_GET=='edit'}">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-portal" key="sysPortalTopic.docCreator"/>
		</td><td width="35%">
			<xform:text property="docCreatorName" showStatus="view"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-portal" key="sysPortalTopic.docCreateTime"/>
		</td><td width="35%">
			<xform:text property="docCreateTime" style="width:85%" showStatus="view"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-portal" key="sysPortalTopic.docAlteror"/>
		</td><td width="35%">
			<xform:text property="docAlterorName" showStatus="view"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-portal" key="sysPortalTopic.docAlterTime"/>
		</td><td width="35%">
			<xform:datetime property="docAlterTime" showStatus="view" />
		</td>
	</tr>
	</c:if>
	</table>
		</center>
		<html:hidden property="fdId" />
		<html:hidden property="fdImg"/>
		<html:hidden property="method_GET" />
		<script type="text/javascript">	
		Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js", null, "js");
		function deleteTopic(){
			seajs.use(['lui/dialog'],function(dialog){
				dialog.confirm("${ lfn:message('sys-portal:sysPortalPage.msg.delete') }",function(val){
					if(val==true){
						location.href = "${LUI_ContextPath}/sys/portal/sys_portal_topic/sysPortalTopic.do?method=delete&fdId=${sysPortalTopicForm.fdId}";
					}
				})
			});
		}
		
		function switchChange(flag){
			$("input[name='fdAnonymous']").val(flag);
		}

		function selectIcon(){
			seajs.use(['lui/dialog'], function(dialog) {
				dialog.build({
					config : {
						width : 750,
						height : 500,
						title : "${ lfn:message('sys-portal:sysPortalLinkDetail.msg.select') }",
						content : {
							type : "iframe",
							url : "/sys/portal/sys_portal_topic/img_material.jsp"
						}
					},
					callback : function(value, dia) {
						if(value==null){
							return ;
						}
						var imgUrl = value.url;
						if(imgUrl.indexOf("/") == 0){
							imgUrl = imgUrl.substring(1);
						}
						//清空附件
						var len = attachmentObject_sysPortalTopic_fdKey.fileList.length;
						if(len > 0) {
							attachmentObject_sysPortalTopic_fdKey.delDoc(attachmentObject_sysPortalTopic_fdKey.fileList[len-1].fdId);
							$("#" + attachmentObject_sysPortalTopic_fdKey.fileList[len-1].fdId).remove()
						}
						$(".imgPreview").css("display","block");
						$(".img_url").attr('src',Com_Parameter.ContextPath+imgUrl);
						$("input[name='fdImg']").val(value.url);
					}
				}).show();
			})
		}
		</script>		
		<script>
		$KMSSValidation();
        Com_AddEventListener(window,"load",function(){
            setTimeout(function(){
				attachmentObject_sysPortalTopic_fdKey.uploadObj.on("uploadFinished",function(){
					$("input[name='fdImg']").val("");
					console.log($("input[name='fdImg']"));
					$(".imgPreview").css("display","none");
					console.log($(".imgPreview"));
				});
			},300)

        })
		</script>
		</html:form>
		<br>
		<br>
	</template:replace>
</template:include>