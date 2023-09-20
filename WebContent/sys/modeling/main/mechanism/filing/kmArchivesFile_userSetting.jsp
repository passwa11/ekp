<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.view">
<template:replace name="content">
<style>
.mp_input{
	width:100% !important;
}
</style>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/main/resources/css/sedimentFile.css" />
<script>
Com_IncludeFile("validation.js|plugin.js|validation.jsp");
</script>
<html:form action="/km/archives/km_archives_file_template/kmArchivesFileTemplate.do">
	<html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <input type="hidden" name="fdMainModelId" value="${HtmlParam.fdMainModelId }"/>
    <input type="hidden" name="serviceName" value="${HtmlParam.serviceName }"/>
    <input type="hidden" name="userSetting" value="1"/>
    <input type="hidden" name="fdOldSaveApproval" value="${kmArchivesFileTemplateForm.fdSaveApproval}"/>
    <input type="hidden" name="fdOldSaveOldFile" value="${kmArchivesFileTemplateForm.fdSaveOldFile}"/>
    <input type="hidden" name="fdOldFilePersonId" value="${kmArchivesFileTemplateForm.fdFilePersonId}"/>
     <div class="file_content">
    <div class="file_table_box">
    <table class="file_dialog tb_normal" width="100%">
    <tr><td class="heading" colspan="4">${lfn:message('sys-modeling-main:main.config.base') }</td></tr>
    	<!-- 归档的存放路径 -->
    	<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="km-archives" key="kmArchivesFileTemplate.category"/>
			</td>
			<td colspan="3">
	            <xform:dialog required="true" subject="${lfn:message('km-archives:kmArchivesFileTemplate.category') }" style="width:90%" propertyId="categoryId" propertyName="categoryName" htmlElementProperties="placeholder='请选择' " showStatus="edit">
	            	Dialog_SimpleCategory('com.landray.kmss.km.archives.model.KmArchivesCategory','categoryId','categoryName',false,null,'02',file_afterSelectCategory,false);
	            </xform:dialog>
			</td>
		</tr>
		<!-- 归档人身份设置 -->
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdFilePerson"/>
			</td>
			<td colspan="3">
				<p class="filePerson_box">
					<xform:radio property="selectFilePersonType" showStatus="edit" onValueChange="file_changeFilePerson" value="${kmArchivesFileTemplateForm.selectFilePersonType}">
						<xform:simpleDataSource value="org" textKey="kmArchivesFileTemplate.fromOrg" bundle="km-archives"></xform:simpleDataSource>
						<xform:simpleDataSource value="formula" textKey="kmArchivesFileTemplate.fromFormula" bundle="km-archives"></xform:simpleDataSource>
					</xform:radio>
				</p>
				<p>
					<div id="orgSelect">
						<xform:address orgType="ORG_TYPE_PERSON" htmlElementProperties="placeholder='${lfn:message('sys-modeling-main:modeling.please.select') }' " showStatus="edit" style="width:90%" subject="${lfn:message('km-archives:kmArchivesFileTemplate.fdFilePerson') }" propertyName="fdFilePersonName" propertyId="fdFilePersonId">
						</xform:address>
					</div>
					<div id="formulaSelect" style="display:none">
						<%-- <input name="fdFilePersonFormula" style="width:220px" class="inputsgl" readonly="" value="${kmArchivesFileTemplateForm.fdFilePersonFormula}">
		            	<a href="#" onclick="file_selectByFormula('fdFilePersonFormula', 'fdFilePersonFormula');"><bean:message key="button.select"/></a> --%>
					</div>
				</p>
			</td>
		</tr>
		<!-- 归档后保存审批意见 -->
		<c:if test = "${HtmlParam.isFlow }">
			<tr>
				<td class="td_normal_title" width=25%>
					<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdSaveApproval"/>
				</td>
				<td colspan="3">
					<ui:switch property="fdSaveApproval"></ui:switch>
				</td>
			</tr>
		</c:if>
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdPreFile"/>
			</td>
			<td colspan="3">
				<ui:switch property="fdPreFile"></ui:switch>
			</td>
		</tr>
		<!-- 归档后保留原始文件 -->
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdSaveOldFile"/>
			</td>
			<td colspan="3" class="oldFile">
				<ui:switch property="fdSaveOldFile" ></ui:switch>
				<span>${lfn:message('km-archives:kmArchivesFileTemplate.fdSaveOldFile.attention') }</span>
			</td>
		</tr>
		<!-- 档案信息设置 -->
	 	<tr>
			<td colspan="4" class="heading"><bean:message bundle="km-archives" key="kmArchivesFileTemplate.info.setting"/></td>
		</tr>
		<tr>
		<td class="td_normal_title" width=25%></td>
		<td>
		<table  class="file_subTable" width="90%">
		<tr>
			<th class="td_normal_title" width=40%>
				<bean:message bundle="km-archives" key="kmArchivesFileTemplate.info.field"/>
			</th>
			<th colspan="3">
				<bean:message bundle="km-archives" key="kmArchivesFileTemplate.template.field"/>
			</th>
		</tr>
	<!-- 档案名称 -->
	<tr>
		<td class="td_normal_title" width=40%>
			<bean:message bundle="km-archives" key="kmArchivesMain.docSubject"/>
		</td>
		<td colspan="3">
			<input type="hidden" name="selectedValue_docSubjectMapping" value="${kmArchivesFileTemplateForm.docSubjectMapping}">
			<select ftype="String" name="docSubjectMapping" class="inputsgl" style="width:80%;">
				<option value="">${lfn:message('sys-modeling-main:modeling.please.choose') }</option>
			</select>
		</td>
    </tr>
	 <!-- 所属卷库 -->
	<tr>
		<td class="td_normal_title" width=40%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdLibrary"/>
		</td>
		<td colspan="3">
			<input type="hidden" name="selectedValue_fdLibraryMapping" value="${kmArchivesFileTemplateForm.fdLibraryMapping}">
			<select ftype="String" name="fdLibraryMapping" class="inputsgl" style="width:80%;">
				<option value="">${lfn:message('sys-modeling-main:modeling.please.choose') }</option>
			</select>
		</td>
	</tr>
	<!-- 组卷年度 -->
	<tr>
		<td class="td_normal_title" width=40%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdVolumeYear"/>
		</td>
		<td colspan="3">
			<input type="hidden" name="selectedValue_fdVolumeYearMapping" value="${kmArchivesFileTemplateForm.fdVolumeYearMapping}">
			<select ftype="String" name="fdVolumeYearMapping" class="inputsgl" style="width:80%;">
				<option value="">${lfn:message('sys-modeling-main:modeling.please.choose') }</option>
			</select>
		</td>
	</tr>
	<!-- 保管期限 -->
	<tr>
		<td class="td_normal_title" width=40%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdPeriod"/>
		</td>
		<td colspan="3">
			<input type="hidden" name="selectedValue_fdPeriodMapping" value="${kmArchivesFileTemplateForm.fdPeriodMapping}">
			<select ftype="String" name="fdPeriodMapping" class="inputsgl" style="width:80%;">
				<option value="">${lfn:message('sys-modeling-main:modeling.please.choose') }</option>
			</select>
		</td>
	</tr>
	<!-- 保管单位 -->
	<tr>
		<td class="td_normal_title" width=40%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdUnit"/>
		</td>
		<td colspan="3">
			<input type="hidden" name="selectedValue_fdUnitMapping" value="${kmArchivesFileTemplateForm.fdUnitMapping}">
			<select ftype="String" name="fdUnitMapping" class="inputsgl" style="width:80%;">
				<option value="">${lfn:message('sys-modeling-main:modeling.please.choose') }</option>
			</select>
		</td>
	</tr>
	<!-- 保管员 -->
	<tr>
		<td class="td_normal_title" width=40%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdStorekeeper"/>
		</td>
		<td colspan="3">
			<select ftype="com.landray.kmss.sys.organization.model.SysOrgPerson" name="fdKeeperMapping" class="inputsgl" style="width:80%;">
				<c:import url="/km/archives/include/kmArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="com.landray.kmss.sys.organization.model.SysOrgPerson" />
					<c:param name="selected" value="${kmArchivesFileTemplateForm.fdKeeperMapping }"></c:param>
					<c:param name="modelName" value="${modelName }" />
					<c:param name="templateService" value="modelingAppModelService" />
					<c:param name="templateId" value="${kmArchivesFileTemplateForm.fdModelId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
				<%--<select ftype="com.landray.kmss.sys.organization.model.SysOrgPerson" name="fdKeeperMapping" class="inputsgl" style="width:80%;">
					<option value="">${lfn:message('sys-modeling-base:modeling.archives.option')}</option>
				</select>--%>
		</td>
	</tr>
	<!-- 档案有效期 -->
	<tr>
		<td class="td_normal_title" width=40%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdValidityDate"/>
		</td>
		<td colspan="3">
			<input type="hidden" name="selectedValue_fdValidityDateMapping" value="${kmArchivesFileTemplateForm.fdValidityDateMapping}">
			<select ftype="Date|DateTime" name="fdValidityDateMapping" class="inputsgl" style="width:80%;">
				<option value="">${lfn:message('sys-modeling-main:modeling.please.choose') }</option>
			</select>
		</td>
	</tr>
	<!-- 归档日期 -->
	<tr>
		<td class="td_normal_title" width=40%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdFileDate"/>
		</td>
		<td colspan="3">
			<input type="hidden" name="selectedValue_fdFileDateMapping" value="${kmArchivesFileTemplateForm.fdFileDateMapping}">
			<select ftype="Date|DateTime" name="fdFileDateMapping" class="inputsgl" style="width:80%;">
				<option value="">${lfn:message('sys-modeling-main:modeling.please.choose') }</option>
			</select>
		</td> 
	</tr>
		</table>
		</td>
    </tr>
		<!-- 文件级设置 -->
	<tr>
		<td colspan="4" class="heading"><bean:message bundle="km-archives" key="kmArchivesFileTemplate.file.setting"/></td>
	</tr>
	<tr>
	<td class="td_normal_title" width=25%></td>
	<td>
			<table id="file_extendFieldTB" class="file_subTable" style="width:90%">
			</table>
    </td>
	</tr>
		
	<html:hidden property="fdTmpXml"/>
    </table>
    </div>
    <div class="file_btn">
	        <ui:button text="${lfn:message('button.submit') }" onclick="Com_Submit(kmArchivesFileTemplateForm,'fileDoc')"></ui:button>
			<ui:button text="${lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
	    </div></div>
</html:form>
<script>
Com_IncludeFile("formula.js");
Com_IncludeFile("archivesOption.js", Com_Parameter.ContextPath + 'sys/modeling/base/mechanism/archives/resources/js/', 'js', true);
Com_AddEventListener(window,"load",function(){
	//公式定义器
	seajs.use(['sys/modeling/base/relation/trigger/behavior/js/formulaBuilder.js']
	,function(formulaBuilder){
		formulaBuilder.initFieldList('${HtmlParam.xformId }');
         var html = $("<div class='modeling_formula'/>");
				html.append("<input type='hidden' name='fdFilePersonFormula' value='${kmArchivesFileTemplateForm.fdFilePersonFormula}' class='inputsgl' style='width:150px' readonly/>");
				html.append("<input type='text' name='fdFilePersonFormulaName' value='${kmArchivesFileTemplateForm.fdFilePersonFormulaName}' class='inputsgl' style='width:150px' readonly/>");
				var $formula = $("<span class='highLight'><a href='javascrip:void(0);'>${lfn:message('sys-modeling-main:modeling.select') }</a></span>");
        
         $formula.on("click", function (e) {
        	 Formula_Dialog('fdFilePersonFormula', 'fdFilePersonFormulaName', formulaBuilder.getFieldList() || "", "com.landray.kmss.sys.organization.model.SysOrgElement");
         
         });
         html.append($formula);
		$("#formulaSelect").append(html);
	})
	//构建文件级属性下拉
	/* var extendDatas = $("input[name='fdTmpXml']").val();
	if(extendDatas) {
		extendDatas = JSON.parse(extendDatas);
		file_buildExtendData(extendDatas);
	} */
	var fdmodelId = '${HtmlParam.modelId }';
	initxformData(fdmodelId);
});


//选择分类之后
function file_afterSelectCategory(rtn) {
	var fdmodelId = '${HtmlParam.modelId }';
	if(rtn && rtn.data && rtn.data.length > 0) {
		var url = '${LUI_ContextPath}/km/archives/km_archives_category/kmArchivesCategory.do?method=getExtendData&fdId='+rtn.data[0].id;
		$.get(url,function(data) {
			if(data) {
				file_buildExtendData(data);
				initxformData(fdmodelId,"extend");
			}
		});
	}
}

//根据扩展数据构建文件级标签的表格
function file_buildExtendData(data) {
	var $table = $("#file_extendFieldTB");
	$table.empty();
	for (var i = 0; i < data.length; i++) {
		var row = data[i];
		var url = '${LUI_ContextPath}/km/archives/km_archives_file_template/kmArchivesFileTemplate.do?method=getTypeFields';
		$.ajax({
			type:'get',
			url:url,
			data:{type:row.fdType,modelName:'${modelName}',templateService:'modelingAppModelService',templateId:'${kmArchivesFileTemplateForm.fdModelId}',otherModelName:'${param.otherModelName}'},
			dataType:'json',
			async:false,
			success:function(options) {
				$table.append(file_buildTR(row,options));
			}
		})
	}
}

//构建行
function file_buildTR(row,options) {
	//选项
	var $select = file_buildOptions(row,options);
	var $tr = $("<tr>");
	$("<td class=\"td_normal_title\" width=\"15%\">").html(row.fdDisplayName).appendTo($tr);
	$("<td colspan=\"3\">").append($select).appendTo($tr);
	if(row.value) {
		$select.val(row.value);
	}
	return $tr;
}
//构建select
function file_buildOptions(row,options) {
	var $select = $("<select ftype=\""+row.fdType+"\" class=\"inputsgl\" style=\"width:80%;\">");
	$select.append($("<option value>").text("${lfn:message('page.firstOption') }"))
	for(var o in options) {
		$select.append($("<option value=\""+o+"\">").text(options[o]));
	}
	$select.data('extendData',row);
	return $select;
}

//提交表单前保存归档扩展字段数据
Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function(){
	var tmpXml = [];
	$("#file_extendFieldTB select").each(function(item,index) {
		var $select = $(this);
		var obj = $select.data('extendData');
		obj.value = $select.val();
		if(obj.value != null && obj.value != '') {
			obj.valueName = $select.find('option:selected').text();
		}
		tmpXml.push(obj);
	});
	$("input[name='fdTmpXml']").val(JSON.stringify(tmpXml));
	return true;
};

seajs.use(['lui/jquery'],function($) {
	var _validate = $KMSSValidation(document.kmArchivesFileTemplateForm);
	// 切换归档人身份设置类型
	window.file_changeFilePerson = function(type) {
		var $formulaSelect = $("#formulaSelect");
		var $orgSelect = $("#orgSelect");
		if(type == 'org') {
			$orgSelect.show();
			$formulaSelect.hide();
		}else {
			$orgSelect.hide();
			$formulaSelect.show();
		}
	}
	//公式选择器
	window.file_selectByFormula = function(idField,nameField) {
		Formula_Dialog_Simple(idField, nameField, "${JsParam.fdMainModelName}", "com.landray.kmss.sys.organization.model.SysOrgElement[]");
	}
	
	$(document).ready(function() { 
		// 控制 归档人身份设置 显示
		file_changeFilePerson("${kmArchivesFileTemplateForm.selectFilePersonType}");
	}); 

})
</script>
</template:replace>
</template:include>
