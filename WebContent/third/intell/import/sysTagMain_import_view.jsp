<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.tag.forms.SysTagMainForm"  %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.third.intell.model.IntellConfig"%>
<%
	IntellConfig intellConfigEdit = new IntellConfig();
	if("true".equalsIgnoreCase(intellConfigEdit.getItEnabled())
			&& "true".equalsIgnoreCase(intellConfigEdit.getSmartTag())){
%>

<c:set var="mainForm" value="${requestScope[param.formName]}" />
<c:set var="sysTagMainForm"
	value="${requestScope[param.formName].sysTagMainForm}" />
<c:set var="modelName" value="${mainForm.modelClass.name}" />


<c:set var="useTab" value="false"></c:set>

<c:if test="${param.useTab!=null && param.useTab==true}">
	<c:set var="useTab" value="true"></c:set>
</c:if>
		<ui:content title="${lfn:message('third-intell:intellTags.title') } ">
			<%@ include file="/third/intell/import/sysTagMain_import_view_include.jsp"%>
		</ui:content>
<kmss:auth
	requestURL="/sys/tag/sys_tag_main/sysTagMain.do?method=updateTag&fdModelName=${modelName}&fdModelId=${mainForm.fdId }"
	requestMethod="GET">
		<c:if test="${param.onlyShowAddButton !='true' }">
			<ui:button text="${lfn:message('third-intell:edit.smartTag') } "
				parentId="toolbar" onclick="___modifyIntellTag___()" order="2" />
		</c:if>
				<div class="selectitem" id="intell_selectItem">
				<input type="hidden" value = "${modelName}" name="intellTagModelName">
				<input type="hidden" value = "${mainForm.fdId}" name="intellTagModelId">
			</div>
	<script>
		// 调整标签，空的时候也只走这条路径
		
		window.___modifyIntellTag___ = function() {
			var url = "/third/intell/ui/tag_frame.jsp";				
			var m = $("input[name='fdCatelogList[1]']")
			seajs.use(['lui/dialog'], function(dialog) {
							var dialogObj = dialog.build({
								config:{
									width: 900,
									height: 600,
									lock: true,
									cache: false,
									title : "${lfn:message('third-intell:edit.smartTag') } ",
									content : {
										id : 'dialog_iframe',
										scroll : false,
										type : "iframe",
										url : url
									}
								}
							});
							dialogObj.content.on("layoutDone",function(){
								var iframe = dialogObj.content.iframeObj;
								if(iframe !=null && iframe.length>0){
									iframe[0].scrolling="no";
								}
								iframe.bind('load',function(){
									if (iframe[0].contentWindow.init){
										iframe[0].contentWindow.init(setting);	
									}
								});
							});
							dialogObj.show();
						});
		}
	</script>
</kmss:auth>
<% 
	}
%>