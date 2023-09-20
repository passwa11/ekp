<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" >
			<ui:button text="${lfn:message('button.submit')}" onclick="Com_SubmitForm(document.sysPraiseInfoConfigMainForm, 'save');">
			</ui:button>
		</ui:toolbar>
</template:replace>
<template:replace name="content">
<html:form action="/sys/praise/sys_praise_info_config_main/sysPraiseInfoConfigMain.do">
<p class="txttitle"><bean:message bundle="sys-praise" key="sysPraiseInfoConfigMain.title"/></p>
<center>
	<table class="tb_normal" id = "config_tb" width=60%>
	  <tbody id ="config_tbody">
		<tr>
			<td style="width:50%" class="td_normal_title">
				<bean:message bundle="sys-praise" key="sysPraiseInfoConfigMain.module"/>
				<span class="txttitle moduleAdd" onclick="addExtendModule()"><bean:message bundle="sys-praise" key="sysPraiseInfoConfigMain.module.add"/></span>
			</td>
			<td style="width:50%" class="td_normal_title">
				<bean:message bundle="sys-praise" key="sysPraiseInfoConfigMain.open"/>
			</td>
		</tr>
		<c:forEach var="configItem" items="${sysPraiseInfoConfigMainForm.configList}" varStatus="vStatus">
			<tr class="contentTr">
				<td >
					<bean:message key="${configItem.fdModuleKey}"/>(<c:out value="${configItem.fdModuleUrlPrefix}"/>)
				</td>
				<td class="openConfig">
					<xform:radio property="configList[${vStatus.index}].fdIsUsed">
						<xform:enumsDataSource enumsType="common_yesno"></xform:enumsDataSource>
					</xform:radio>
				</td>
				<html:hidden property="configList[${vStatus.index}].fdId"/>
				<html:hidden property="configList[${vStatus.index}].fdModuleKey"/>
				<html:hidden property="configList[${vStatus.index}].fdModuleUrlPrefix"/>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</center>
</html:form>
<style>
#config_tb{
	margin-bottom: 30px;
}
#config_tb .td_normal_title{
	font-size: 15px;
	font-weight: 400;
	text-align: center;
}
#config_tb .moduleAdd{
	font-size: 12px;
	font-weight: 400;
	text-decoration: underline;
	cursor: pointer;
}
.openConfig label {
	margin-left: 10px;
}
</style>
<script>
	function addExtendModule(){
	    var url='/sys/praise/sys_praise_info_config_main/sysPraiseInfoConfigMain.do?method=importModule';
	    seajs.use(['lui/dialog','lui/jquery'],function(dialog,$){
	        dialog.iframe(url,"${lfn:message('sys-praise:sysPraiseInfoConfigMain.module.config')}",null, {
	            width:980,
				height:550,
	            buttons:[{
	                name : "${lfn:message('button.ok')}",
					value : true,
					focus : true,
					fn : function(value,_dialog) {
					    var moduleInfo = _dialog.content.iframeObj[0].contentWindow.getSelectModule();
					    if(moduleInfo==""){
					        dialog.alert("${lfn:message('sys-praise:sysPraiseInfoConfigMain.module.select.oneNeed')}");
					    }else{
					        $.getJSON(
									"${LUI_ContextPath}/sys/praise/sys_praise_info_config_main/sysPraiseInfoConfigMain.do?method=updateCache",
									$.param({"moduleInfo":moduleInfo},true),
									function(data) {
										 if(data.rtnObj){
										     buildContent(data.rtnObj);
										     _dialog.hide();
										 }
									});
					    }
					    
					}
	            },{
	                name : "${lfn:message('button.cancel')}",
					styleClass:"lui_toolbar_btn_gray iframeBtn",
					value : false,
					fn : function(value, _dialog) {
						_dialog.hide();
					}
	            }]
	        })
	        
	    })
	}
	
	function buildContent(contentList){
	    var existNum = $("#config_tb .contentTr").length;
	    seajs.use([ 'lui/view/Template', 'lui/jquery'],function(Template,$){
	        for(var i = 0;i < contentList.length;i++ ){
	            var item =	contentList[i];
	            var html =   new Template($('#content_templ').html()).render({
	                _fdModuleKey : item.moduleKey,
	                _urlPrefix : item.urlPrefix,
	                _moduleName : item.moduleName,
	                _fdNum : existNum
	            })
	            $("#config_tbody").append(html);
	            existNum++;
	        }
	    }) 
	}
</script>
<script type="text/template" id="content_templ"> 
{$
	<tr class="contentTr">
		<td>
			{%_moduleName%}
		</td>
		<td class="openConfig">
			<label>
				<input type="radio" name="configList[{%_fdNum%}].fdIsUsed" checked value="true"><bean:message key="message.yes"/>
			</label>
			<label>
				<input type="radio" name="configList[{%_fdNum%}].fdIsUsed" value="false"><bean:message key="message.no"/>
			</label>
		</td>
		<input type="hidden" name="configList[{%_fdNum%}].fdModuleKey" value="{%_fdModuleKey%}"/>
		<input type="hidden" name="configList[{%_fdNum%}].fdModuleUrlPrefix" value="{%_urlPrefix%}"/>
	</tr>
$}
</script>
</template:replace>
</template:include>