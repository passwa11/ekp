<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.view">
<template:replace name="content">
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/sedimentFile.css" />
<html:form action="/kms/multidoc/kms_multidoc_subside/kmsMultidocSubside.do">
    <input type="hidden" name="fdId" value="${HtmlParam.fdId }"/>
    <input type="hidden" name="serviceName" value="${HtmlParam.serviceName }"/>
    <input type="hidden" name="userSetting" value="1"/>
    <div class="sediment_content">
    <div class="sediment_table_box">
	    <table class="sediment_dialog tb_normal" width="100%">
	    	<tr><td class="heading" colspan="4">基本信息</td></tr>
	    	<!-- 存放路径 -->
	    	<tr>
				<td class="td_normal_title" width=25%>
					<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.category"/>
				</td>
				<td colspan="3">
		         <xform:dialog subject="${lfn:message('kms-multidoc:kms.knowledge.subside.category') }"  required="true"  propertyId="categoryId" propertyName="categoryName" showStatus="edit" style="width:90%;" htmlElementProperties="placeholder='请选择' " className="categoryPath">
	            	modifyMultidocCate();
	            </xform:dialog>
				</td>
			</tr>
			<!-- 审批意见 -->
			<c:if test = "${HtmlParam.isFlow }">
				<tr>
					<td class="td_normal_title" width=25%>
						<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.Approval"/>
					</td>
					<td colspan="3">
						<ui:switch property="fdSaveApproval" ></ui:switch>
					</td>
				</tr>
			</c:if>
			<!-- 信息设置 -->
			<tr>
				<td colspan="4" class="heading"><bean:message bundle="kms-multidoc" key="kms.knowledge.subside.info"/></td>
			</tr>
			<tr>
			<td class="td_normal_title" width="25%"></td>
			<td colspan="3">
			<table class="sediment_subTable" width="90%">
			<tr>
				<th class="td_normal_title">
					<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.info.field"/>
				</th>
				<th>
					<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.info.field.temple"/>
				</th>
			</tr>
			<!-- 标题 -->
			<tr>
				<td class="td_normal_title">
					<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.docSubject"/>
				</td>
				<td>
					<select ftype="String" name="docSubjectMapping" class="inputsgl" style="width:80%;">
						<option value="">==请选择==</option>
						<option value="DocSubject">主题自动生成规则</option>
					</select>
				</td>
			</tr>
			<!-- 作者 -->
			<tr>
				<td class="td_normal_title">
					<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.docCreator"/>
				</td>
				<td >
					<select ftype="com.landray.kmss.sys.organization.model.SysOrgPerson" name="docCreatorMapping" class="inputsgl" style="width:80%;">
						<option value="">==请选择==</option>
						<option value="currentUser">当前用户</option>
						<option value="docCreator">录入者</option>
					</select>
				</td>
			</tr>
			</table></td>
			</tr>
			<%-- <tr>
				<td colspan="4" align="center">
					<ui:button text="${lfn:message('button.submit') }" onclick="submitSubside()"></ui:button>
					<ui:button text="${lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
				</td>
			</tr> --%>
	    </table>
	    </div>		
	    <div class="sediment_btn">
	        <ui:button text="${lfn:message('button.submit') }" onclick="submitSubside()" ></ui:button>
			<ui:button text="${lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
	    </div>
    </div>
</html:form>
</template:replace>
</template:include>
<script>
Com_IncludeFile("jquery.js|dialog.js|formula.js|doclist.js|validation.js|plugin.js|validation.jsp");

Com_AddEventListener(window,"load",function(){
	var url = "${LUI_ContextPath}/sys/modeling/base/modelingAppView.do?method=getSubjectOption&fdModelId=${HtmlParam.fdmodelId }";
	$.ajax({
		type:"POST",
		url:url,
		success:function(result){
			doCreateOption(result);
		},
		error:function(){
			alert("获取标题下拉列表数据错误");
		}
	});
})

function doCreateOption(result){
	var rtn = $.parseJSON(result);
	var html = "";
	for(var key in rtn){
		//过滤明细表字段
		if(rtn[key].name.indexOf(".") != -1){
			continue;
		}
		if(rtn[key].businessType == "inputText"){
			html += '<option value="'+key+'">'+rtn[key].label+'</option>';
		}
	}
	$("[name='docSubjectMapping']").append(html);
}
 
function modifyMultidocCate() {
	seajs.use([ 'lui/dialog' ],function(dialog, env) {
		dialog.simpleCategory({
				modelName : 'com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory',
				authType : 0,
				idField : 'categoryId',
				nameField : 'categoryName',
				mulSelect : false,
				canClose : true,
				notNull : false,
				___urlParam : {
					'fdTemplateType' : '1,3'
				}
		});
	})
}

function submitSubside(){
	var validator = $KMSSValidation(document.kmsMultidocSubsideForm);
	if(!validator.validate())
		return;
	var url = '${LUI_ContextPath}/kms/multidoc/kms_multidoc_subside/kmsMultidocSubside.do?method=fileDoc';
	var formObj = document.getElementsByName('kmsMultidocSubsideForm')[0];
	var dia =window.parent.document.getElementById('lui-id-111');
	if(dia!=null){
		dia.style.display='none';
	}
	$.ajax({
		type:"POST",
		dataType:"json",
		url:url,
		data:$(formObj).serialize(),
		success:function(result){
			seajs.use(['lui/dialog'],function(dialog){
				dialog.confirm('<bean:message key="kmsMultidocSubside.isTurnTo" bundle="kms-multidoc"/>',function(value){
					if(result.id){
						if(value == true){
							var url = "${LUI_ContextPath}/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=view&fdId="+result.id;
							window.open(url,"_blank");
							top.location.reload(true);
						}else{
							top.location.reload(true);
						}
					}else{
						alert("沉淀失败");
					}
					
				});
			});
		},
		error:function(){
			alert("沉淀失败");
		}
	});
	Com_DisableFormOpts();
}
</script>