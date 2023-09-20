<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict,com.landray.kmss.sys.config.dict.SysDictModel" %>
<template:include ref="default.edit" showQrcode="false">
<c:set var="navTreeForm" value="${requestScope[param.formName]}" />
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ navTreeForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="DbEcharts_Application_NavTree_Submit('update');"></ui:button>
				</c:when>
				<c:when test="${ navTreeForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="DbEcharts_Application_NavTree_Submit('save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="DbEcharts_Application_NavTree_Submit('saveadd');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
<template:replace name="content">
	<c:choose>
		<c:when test="${navTreeForm.method_GET=='add' }">
			<p class="txttitle"><c:out value="${ lfn:message('dbcenter-echarts-application:dbEchartsNavTree.addChart') }"></c:out></p>	
		</c:when>
		<c:otherwise>
			<p class="txttitle"><c:out value="${navTreeForm.fdNavTitleTxt}"></c:out></p>
		</c:otherwise>
	</c:choose>
	<script>
		Com_IncludeFile('chartMode.js',Com_Parameter.ContextPath+'dbcenter/echarts/application/common/','js',true);
		Com_IncludeFile('inputs.js',Com_Parameter.ContextPath+'dbcenter/echarts/application/common/','js',true);
		Com_IncludeFile('userInfo.js',Com_Parameter.ContextPath+'dbcenter/echarts/application/common/','js',true);
		Com_IncludeFile('DbEchartsApplication_Dialog.js',Com_Parameter.ContextPath+'dbcenter/echarts/application/common/','js',true);
	</script>

<center>
	<%
		String tempModelName = request.getParameter("tempModelName");
		SysDictModel dictModel = SysDataDict.getInstance().getModel(tempModelName);
		String viewUrl = dictModel.getUrl();
		viewUrl = viewUrl.substring(1, viewUrl.indexOf("?"));
	%>
	<html:form action="${param.actionUrl }">
	<table class="tb_normal" width=95%> 
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.fdNavTitleTxt"/>
			</td>
			<td width="85%" colspan="3">
				<label><input type="radio" name="fdNavTitleType" value="1" onclick="DbCenter_Application_NavTree_ChangeMode('1');" />
					${ lfn:message('dbcenter-echarts-application:dbEchartsNavTree.sameToSubject') }</label>
				<label><input type="radio" name="fdNavTitleType" value="2" onclick="DbCenter_Application_NavTree_ChangeMode('2');" />
					${ lfn:message('dbcenter-echarts-application:dbEchartsNavTree.custom') }</label>
				<div class="custom-titleTxt" style="width:30%;display:none;">
					<xform:text property="fdNavTitleTxt" required="true"/>
				</div>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.fdEchartsTemplate"/>
			</td>
			<td width="35%">
				<xform:dialog required="true" subject="${lfn:message('dbcenter-echarts-application:dbEchartsNavTree.fdEchartsTemplate') }" propertyId="fdTemplateId" style="width:30%"
						propertyName="fdTemplateName" dialogJs="DbCenter_Application_NavTree_Dialog();">
				</xform:dialog>
				<div style="display:inline-block;margin-left:8px;color:#4285f4;">
					<a href="javascript:void(0);" onclick="DbCenter_Application_NavTree_ViewTemp();">${ lfn:message('dbcenter-echarts-application:dbEchartsNavTree.view') }</a>
				</div>
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.fdEchartsCategory"/>
			</td>
			<td width=35%>
				<input type="text" readOnly name="fdEchartsCategoryName" style="border:0px;" value="${navTreeForm.fdEchartsCategoryName }"/>
				<html:hidden property="fdEchartsCategoryId" />
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.fdConfig"/>
			</td>
			<td width="85%" colspan="3">
				<div class="navTree-inputs">
				</div>
			</td>
		</tr>
		<c:if test="${navTreeForm.method_GET=='edit'}">
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.docCreator"/>
				</td>
				<td width="35%">
					<html:text property="docCreatorName" readonly="true" style="width:50%;" />
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.docCreateTime"/>
				</td>
				<td width="35%">
					<html:text property="docCreateTime" readonly="true" style="width:50%;" />
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.docAlteror"/>
				</td>
				<td width="35%">
					<bean:write name="navTreeForm" property="docAlterorName" />
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.docAlterTime"/>
				</td>
				<td width="35%">
					<bean:write name="navTreeForm" property="docAlterTime" />
				</td>
			</tr>
		</c:if>
	</table>
	<html:hidden property="fdId" />
	<html:hidden property="fdModelName" />
	<html:hidden property="fdKey" />
	<html:hidden property="method_GET" />
	<html:hidden property="fdConfig" />
	</html:form>
<br>
</center>
<script>
	var g_validator = $KMSSValidation();
	
	var dbEchartsAppInputs = new DbEchartsAppInputs($(".navTree-inputs"));
	
	function DbCenter_Application_NavTree_ViewTemp(){
		var fdTemplateId = $("input[name='fdTemplateId']").val();
		if(typeof(fdTemplateId) == 'undefined' || fdTemplateId == ''){
			seajs.use(['lui/dialog'],function(dialog){
				dialog.alert("${ lfn:message('dbcenter-echarts-application:dbEchartsNavTree.plzChooseTemplate') }");
			});
			return false;
		}
		var url = Com_Parameter.ContextPath + "<%=viewUrl%>?method=view&fdId=" + fdTemplateId;
		Com_OpenWindow(url);
	}

	// 切换导航标题类型
	function DbCenter_Application_NavTree_ChangeMode(type){
		// 1为同图表标题 2为自定义
		if(type == '1'){
			$("[name='fdNavTitleType'][value='1']").prop('checked',true);
			$(".custom-titleTxt").hide();
			$("[name='fdNavTitleTxt']").removeAttr('validate');
			if(window.Reminder){
				new Reminder($("[name='fdNavTitleTxt']")).hide();
			}
		}else if(type == '2'){
			$("[name='fdNavTitleType'][value='2']").prop('checked',true);
			$(".custom-titleTxt").css("display","inline-block");
			$("[name='fdNavTitleTxt']").attr('validate', 'required');
		}
	}
	
	function DbCenter_Application_NavTree_ChangeChartType(value,dom){
		// 切换图表类型的时候，清空图表模板和入参
		$("[name='fdTemplateId']").val('');
		$("[name='fdTemplateName']").val('');
		$(".navTree-inputs").html("");
		dbEchartsAppInputs.clearValues();
	}
	
	function DbCenter_Application_NavTree_Dialog(){
		window.focus();
		dbEchartChartMode.getMode("chart;table;custom");
		var item = dbEchartChartMode.getItemByMainModelName("${param.tempModelName}");
		var url = Com_Parameter.ContextPath + "dbcenter/echarts/application/dbEchartsApplication.do?method=dialog&echartModelName="
				+ item.templateModelName;
		var dialog = new DbEchartsApplication_Dialog(url,item,DbCenter_Application_NavTree_Dialog_Cb);
		dialog.show();
	}
	
	// 关闭弹框的回调
	function DbCenter_Application_NavTree_Dialog_Cb(rn){
		if(rn){
			// 回填模板信息
			$("[name='fdTemplateId']").val(rn.value);
			$("[name='fdTemplateName']").val(rn.text);
			$KMSSValidation().validateElement($("input[name='fdTemplateName']")[0]);
			// 构建入参
			dbEchartsAppInputs.buildInput(rn,DbCenter_Application_NavTree_Dialog_FillCategory);
		}
	}
	
	function DbCenter_Application_NavTree_Dialog_FillCategory(baseInfo){
		$("[name='fdEchartsCategoryName']").val(baseInfo.categoryName);
		$("[name='fdEchartsCategoryId']").val(baseInfo.categoryId);
	}
	
	function DbEcharts_Application_NavTree_Submit(method){
		// 导航标题
		var type = $("[name='fdNavTitleType']:checked").val();
		if(type == '1'){
			$("[name='fdNavTitleTxt']").val($("[name='fdTemplateName']").val());
		}
		// 入参
		$('[name="fdConfig"]').val(LUI.stringify(dbEchartsAppInputs.getKeyData()));
		Com_Submit(document.forms[0],method);
	}
	
	function DbCenter_Application_NavTree_Init(){
		// 初始化导航标题
		var fdNavTitleType = "${navTreeForm.fdNavTitleType}";
		if(fdNavTitleType == ''){
			fdNavTitleType = '1';
		}
		DbCenter_Application_NavTree_ChangeMode(fdNavTitleType);
		
		// 初始化入参
		var fdConfig = $.trim($('[name="fdConfig"]').val());
		var inputs = fdConfig == ''?{}:LUI.toJSON(fdConfig);
		var param = {};
		param.item = {};
		param.item.mainModelName = "${param.tempModelName}";
		param.value = $("[name='fdTemplateId']").val();
		if(param.value != ''){
			dbEchartsAppInputs.buildInput(param);
			dbEchartsAppInputs.setValuesAndDom(inputs);			
		}
	}

	Com_AddEventListener(window,'load',DbCenter_Application_NavTree_Init);
</script>
</template:replace>
</template:include>