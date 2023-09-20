<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="title">
		<c:out value="${ lfn:message('kms-medal:table.kmsMedalCategory') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ kmsMedalCategoryForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="update();"></ui:button>
				</c:when>
				<c:when test="${ kmsMedalCategoryForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="save();"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
<html:form action="/kms/medal/kms_medal_category/kmsMedalCategory.do">
 
<p class="txttitle"><bean:message bundle="kms-medal" key="table.kmsMedalCategory"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-medal" key="kmsMedalCategory.fdName"/>
		</td><td width="35%">
			<xform:text property="fdName" style="width:85%" validators="noSpecialChar checkNameOnly "/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-medal" key="kmsMedalCategory.fdOrder"/>
		</td><td width="35%">
			<xform:text property="fdOrder" style="width:63%" validators="positiveInteger"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-medal" key="kmsMedalCategory.authDefenders"/>
		</td><td width="35%" colspan="3">
			<html:hidden  property="authDefenderIds"/>
				<xform:address textarea="true" mulSelect="true" propertyId="authDefenderIds" propertyName="authDefenderNames" style="width:85%;height:90px;" ></xform:address>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-medal" key="kmsMedalCategory.fdDescription"/>
		</td><td width="35%" colspan="3">
			<xform:textarea property="fdDescription" validators="maxLength(1500)" style="width:85%"/>
		</td>
	</tr>		
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-medal" key="kmsMedalCategory.docCreator"/>
		</td><td width="35%">
			<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
		</td>	
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-medal" key="kmsMedalCategory.docCreateTime"/>
		</td><td width="35%">
			<xform:datetime property="docCreateTime" showStatus="view" />
		</td>
	</tr>
	<c:if test="${not empty kmsMedalCategoryForm.docAlterorId}">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-medal" key="kmsMedalCategory.docAlteror"/>
		</td><td width="35%">
			<xform:address propertyId="docAlterorId" propertyName="docAlterorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
		</td>			
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-medal" key="kmsMedalCategory.docAlterTime"/>
		</td><td width="35%">
			<xform:datetime property="docAlterTime" showStatus="view" />
		</td>	
	</tr>
	</c:if>
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="docCreatorId" />
<html:hidden property="docCreateTime" />
<html:hidden property="method_GET" />
</html:form>
<script>
	Com_IncludeFile("dialog.js");
	var validations = {
			'noSpecialChar':
			{
				error:"<span style='color:#cc0000;'>名称</span>&nbsp;不能包含特殊字符",
				test:function(v,e,o) {
					//过滤特殊字符
					var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&;—|{}【】‘；：”“'。，、？]");
					v = $.trim(v);
					if(pattern.test(v)) {
						return false;
					}
					return true;
				}
			},
			'checkNameOnly'://校验有提示，但是保存操作可以继续，待解决？？
			{
				error:"<span style='color:#cc0000;'>名称</span>&nbsp;不能重复",
				test:function(v,e,o) {
					//数据库判断
					v = encodeURI($.trim(v));
					var propertyName = $(e).attr("name");
					var fdName = propertyName.substring(propertyName.lastIndexOf(".") + 1, propertyName.length);
					propertyName = propertyName.substring(0,propertyName.lastIndexOf("."));
					//明细表每行的fdId（编辑的时候，用于过滤本身）
					var fdId = $("input[name='fdId']").val();
					var data = new KMSSData();
					data.UseCache = false;
					data.AddBeanData("kmsMedalCheckNameOnlyServiceImpl&fdId="+ fdId +"&beanName=kmsMedalCategoryService&fieldName=" + fdName +"&fieldValue="+v);
					var retVal = data.GetHashMapArray();
					if(retVal[0]['size']>0){
						return false;
					}
					return true;
				}
			},
			'positiveInteger':
			{
				error:"<span style='color:#cc0000;'>排序号</span>&nbsp;必须为正整数",
				test:function(v,e,o) {
					var reg = /^[1-9][0-9]*$/;
					 v = $.trim(v);
					 if(v == "" || v == null || v == "0") {
						 return true;
					 }
					 if(!reg.test(v)) {
						 return false;
					 }
					return true;
				}
			}
		};
	$KMSSValidation().addValidators(validations);
	function save() {
		//排序号去空
		$("input[name='fdOrder']").val($.trim($("input[name='fdOrder']").val()));
		Com_Submit(document.kmsMedalCategoryForm, 'save');
	}
	function update() {
		//排序号去空
		$("input[name='fdOrder']").val($.trim($("input[name='fdOrder']").val()));
		Com_Submit(document.kmsMedalCategoryForm, 'update');
	}
</script>
</template:replace>
</template:include>
