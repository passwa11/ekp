<%@page import="com.landray.kmss.sys.xform.interfaces.ISysFormTemplateForm"%>
<%@page import="com.landray.kmss.km.archives.util.KmArchivesUtil"%>
<%@page import="com.landray.kmss.km.archives.interfaces.IKmArchivesFileTemplateForm"%>
<%@page import="com.landray.kmss.common.forms.IExtendForm"%>
<%@ page import="com.landray.kmss.sys.archives.util.SysArchivesUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
	Object _form = request.getAttribute(request.getParameter("formName"));
	String moduleUrl = request.getParameter("moduleUrl");
	if((_form instanceof IKmArchivesFileTemplateForm) && (KmArchivesUtil.isStartFile(moduleUrl)|| SysArchivesUtil.isStartFile(moduleUrl))) {
%>
<c:set var="templateForm" value="${requestScope[param.formName]}" />
<c:set var="fileTemplateForm" value="${templateForm.kmArchivesFileTemplateForm}" />
<script>
Com_IncludeFile("jquery.js|dialog.js|formula.js|doclist.js");
</script>

<!-- 设计器区域 -->
<c:if test="${param.useLabel != 'false'}">
	<tr LKS_LabelName="<bean:message bundle="km-archives" key="table.kmArchivesFileTemplate"/>" style="display:none" id="kmArchivesFile_tab">
		<td>
</c:if>
<table class="tb_normal" width=100% style="border-top:0;">
	<html:hidden property="kmArchivesFileTemplateForm.fdId"/>
	<html:hidden property="kmArchivesFileTemplateForm.docCreatorId"/>
	<html:hidden property="kmArchivesFileTemplateForm.docCreateTime"/>
	<tr>
		<td colspan="4" style="color:red;">${lfn:message('km-archives:file.include.tip') }</td>
	</tr>
	
	<!-- 合同归档设置 -->
	<c:if test="${param.formName eq 'kmAgreementTmplForm'}">
		<c:if test="${param.fdKey eq 'kmAgreementReview'}">
			<tr>
				<td colspan="4" style="color:red;">${lfn:message('km-archives:file.include.agreement.tip') }</td>
			</tr>
		</c:if>
		<tr>
			<td colspan="4">合同归档设置</td>
		</tr>
		<!-- 是否进行文本文档 -->
		<c:if test="${version eq 'professional' }">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-agreement" key="kmAgreementTmpl.fdReviewTextArchive"/>
			</td>
			<td colspan="3">
				<ui:switch property="fdReviewTextArchive" 
					id = "fdReviewTextArchiveId"
					onValueChange="archives_text_change(this.checked);" 
					checked = "${kmAgreementTmplForm.fdReviewTextArchive}"
					checkVal="true"
					unCheckVal="false"
					enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
					disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
				</ui:switch>
				<br>
				<span style="">
				${lfn:message('km-agreement:kmAgreementTmpl.fdReviewTextArchive.desc') }
				</span>
			</td>
		</tr>
		<!-- 是否进行履约过程归档 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-agreement" key="kmAgreementTmpl.fdNextProcessArchive"/>
			</td>
			<td colspan="3">
				<ui:switch property="fdNextProcessArchive" 
					id = "fdNextProcessArchiveId"
					onValueChange="archives_process_change(this.checked);" 
					checked = "${kmAgreementTmplForm.fdNextProcessArchive}"
					checkVal="true"
					unCheckVal="false"
					enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
					disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
				</ui:switch>
				<br>
				<span style="">
				${lfn:message('km-agreement:kmAgreementTmpl.fdNextProcessArchive.desc') }
				</span>
			</td>
		</tr>
		</c:if>
		<!-- 是否允许自动归档 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-agreement" key="kmAgreementTmpl.fdAutoArchive"/>
			</td>
			<td colspan="3">
				<ui:switch property="fdAutoArchive" 
					checked = "${kmAgreementTmplForm.fdAutoArchive}"
					checkVal="true"
					unCheckVal="false"
					enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
					disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
				</ui:switch>
				<br>
				<span style="">
				${lfn:message('km-agreement:kmAgreementTmpl.fdAutoArchive.desc') }
				</span>
			</td>
		</tr>
	</c:if>
	
	<!-- 基础设置 -->
	<tr>
		<td colspan="4">
			<bean:message bundle="km-archives" key="py.JiChuSheZhi"/>
			<% if(_form instanceof ISysFormTemplateForm) {%>
			 <%@include file="/km/archives/include/kmArchivesFileSetting_importFields.jsp" %>
			<%} %>
		</td>
	</tr>
	<!-- 归档的存放路径 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesFileTemplate.category"/>
		</td>
		<td colspan="3">
            <xform:dialog subject="${lfn:message('km-archives:kmArchivesFileTemplate.category') }" propertyId="kmArchivesFileTemplateForm.categoryId" propertyName="kmArchivesFileTemplateForm.categoryName" showStatus="edit" style="width:220px;">
            	Dialog_SimpleCategory('com.landray.kmss.km.archives.model.KmArchivesCategory','kmArchivesFileTemplateForm.categoryId','kmArchivesFileTemplateForm.categoryName',false,null,'00',file_afterSelectCategory,false);
            </xform:dialog>
		</td>
	</tr>
	<!-- 归档人身份设置 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdFilePerson"/>
		</td>
		<td colspan="3">
			<p>
				<xform:radio property="kmArchivesFileTemplateForm.selectFilePersonType" onValueChange="file_changeFilePerson">
					<xform:simpleDataSource value="org" textKey="kmArchivesFileTemplate.fromOrg" bundle="km-archives"></xform:simpleDataSource>
					<xform:simpleDataSource value="formula" textKey="kmArchivesFileTemplate.fromFormula" bundle="km-archives"></xform:simpleDataSource>
				</xform:radio>
			</p>
			<p>
				<div id="orgSelect">
					<xform:address orgType="ORG_TYPE_PERSON" subject="${lfn:message('km-archives:kmArchivesFileTemplate.fdFilePerson') }" style="width:220px;" propertyName="kmArchivesFileTemplateForm.fdFilePersonName" propertyId="kmArchivesFileTemplateForm.fdFilePersonId">
					</xform:address>
				</div>
				<div id="formulaSelect">
					<input name="kmArchivesFileTemplateForm.fdFilePersonFormula" class="inputsgl" style="width:200px" type="hidden" value='${fileTemplateForm.fdFilePersonFormula }'>
					<input name="kmArchivesFileTemplateForm.fdFilePersonFormulaName" class="inputsgl" style="width:200px" readonly="readonly" value='${fileTemplateForm.fdFilePersonFormulaName==null? fileTemplateForm.fdFilePersonFormula:fileTemplateForm.fdFilePersonFormulaName}'>
	            	<a href="#" onclick="file_selectByFormula('kmArchivesFileTemplateForm.fdFilePersonFormula', 'kmArchivesFileTemplateForm.fdFilePersonFormulaName');"><bean:message key="button.select"/></a>
				</div>
			</p>
		</td>
	</tr>
	<!-- 归档后保存审批意见 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdSaveApproval"/>
		</td>
		<td colspan="3">
			<ui:switch property="kmArchivesFileTemplateForm.fdSaveApproval" 
				enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
				disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
		</td>
	</tr>
	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdPreFile"/>
		</td>
		<td colspan="3">
			<ui:switch property="kmArchivesFileTemplateForm.fdPreFile" 
				enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
				disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
		</td>
	</tr>
	
	<!-- 归档后保留原始文件 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdSaveOldFile"/>
		</td>
		<td colspan="3">
			<ui:switch property="kmArchivesFileTemplateForm.fdSaveOldFile" 
				enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
				disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			<span style="color: red;">${lfn:message('km-archives:kmArchivesFileTemplate.fdSaveOldFile.attention') }</span>
		</td>
	</tr>
	<!-- 档案信息设置 -->
	<c:if test="${'false' != param.custom }">
	<tr>
		<td colspan="4"><bean:message bundle="km-archives" key="kmArchivesFileTemplate.info.setting"/></td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesFileTemplate.info.field"/>
		</td>
		<td colspan="3">
			<bean:message bundle="km-archives" key="kmArchivesFileTemplate.template.field"/>
		</td>
	</tr>
	<!-- 档案名称 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.docSubject"/>
		</td>
		<td colspan="3">
			<select ftype="String" name="kmArchivesFileTemplateForm.docSubjectMapping" class="inputsgl" style="width:220px;">
				<c:import url="/km/archives/include/kmArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="String" />
					<c:param name="selected" value="${fileTemplateForm.docSubjectMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	<!-- 所属卷库 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdLibrary"/>
		</td>
		<td colspan="3">
			<select ftype="String" name="kmArchivesFileTemplateForm.fdLibraryMapping" class="inputsgl" style="width:220px;">
				<c:import url="/km/archives/include/kmArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="String" />
					<c:param name="selected" value="${fileTemplateForm.fdLibraryMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	<!-- 组卷年度 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdVolumeYear"/>
		</td>
		<td colspan="3">
			<select ftype="String" name="kmArchivesFileTemplateForm.fdVolumeYearMapping" class="inputsgl" style="width:220px;">
				<c:import url="/km/archives/include/kmArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="String" />
					<c:param name="selected" value="${fileTemplateForm.fdVolumeYearMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	<!-- 保管期限 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdPeriod"/>
		</td>
		<td colspan="3">
			<select ftype="String" name="kmArchivesFileTemplateForm.fdPeriodMapping" class="inputsgl" style="width:220px;">
				<c:import url="/km/archives/include/kmArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="String" />
					<c:param name="selected" value="${fileTemplateForm.fdPeriodMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	<!-- 保管单位 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdUnit"/>
		</td>
		<td colspan="3">
			<select ftype="String" name="kmArchivesFileTemplateForm.fdUnitMapping" class="inputsgl" style="width:220px;">
				<c:import url="/km/archives/include/kmArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="String" />
					<c:param name="selected" value="${fileTemplateForm.fdUnitMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	<!-- 保管员 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdStorekeeper"/>
		</td>
		<td colspan="3">
			<select ftype="com.landray.kmss.sys.organization.model.SysOrgElement" name="kmArchivesFileTemplateForm.fdKeeperMapping" class="inputsgl" style="width:220px;">
				<c:import url="/km/archives/include/kmArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="com.landray.kmss.sys.organization.model.SysOrgPerson|com.landray.kmss.sys.organization.model.SysOrgPost" />
					<c:param name="selected" value="${fileTemplateForm.fdKeeperMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	<!-- 档案有效期 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdValidityDate"/>
		</td>
		<td colspan="3">
			<select ftype="Date|DateTime" name="kmArchivesFileTemplateForm.fdValidityDateMapping" class="inputsgl" style="width:220px;">
				<c:import url="/km/archives/include/kmArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="Date|DateTime" />
					<c:param name="selected" value="${fileTemplateForm.fdValidityDateMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	<!-- 密级程度 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdDenseLevel"/>
		</td>
		<td colspan="3">
			<select ftype="String" name="kmArchivesFileTemplateForm.fdDenseLevelMapping" class="inputsgl" style="width:220px;">
				<c:import url="/km/archives/include/kmArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="String" />
					<c:param name="selected" value="${fileTemplateForm.fdDenseLevelMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	-->
	<!-- 归档日期 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdFileDate"/>
		</td>
		<td colspan="3">
			<select ftype="Date|DateTime" name="kmArchivesFileTemplateForm.fdFileDateMapping" class="inputsgl" style="width:220px;">
				<c:import url="/km/archives/include/kmArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="Date|DateTime" />
					<c:param name="selected" value="${fileTemplateForm.fdFileDateMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	</c:if>
	<!-- 文件级设置 -->
	<tr>
		<td colspan="4"><bean:message bundle="km-archives" key="kmArchivesFileTemplate.file.setting"/></td>
	</tr>
	<tr>
		<td colspan="4">
			<table id="file_extendFieldTB" class="tb_normal" style="width:100%">
			</table>
		</td>
	</tr>
	<html:hidden property="kmArchivesFileTemplateForm.fdTmpXml"/>
</table>
<script>
	Com_AddEventListener(window, 'load', function() {
		var extendDatas = $("input[name='kmArchivesFileTemplateForm.fdTmpXml']").val();
		if(extendDatas) {
			extendDatas = JSON.parse(extendDatas);
			file_buildExtendData(extendDatas);
		}
		file_changeFilePerson('${fileTemplateForm.selectFilePersonType}');
	});
	//公式选择器
	function file_selectByFormula(idField,nameField) {
		Formula_Dialog_Simple(idField, nameField, "${param.modelName }", "com.landray.kmss.sys.organization.model.SysOrgElement[]");
	}
	//切换归档人身份设置类型
	function file_changeFilePerson(type) {
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
	//选择分类之后
	function file_afterSelectCategory(rtn) {
		if(rtn && rtn.data && rtn.data.length > 0) {
			var url = '${LUI_ContextPath}/km/archives/km_archives_category/kmArchivesCategory.do?method=getExtendData&fdId='+rtn.data[0].id;
			$.get(url,function(data) {
				if(data) {
					file_buildExtendData(data);
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
				data:{type:row.fdType,modelName:'${param.modelName}',templateService:'${param.templateService}',templateId:'${templateForm.fdId}',otherModelName:'${param.otherModelName}'},
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
		var $select = $("<select ftype=\""+row.fdType+"\" class=\"inputsgl\" style=\"width:220px;\">");
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
		$("input[name='kmArchivesFileTemplateForm.fdTmpXml']").val(JSON.stringify(tmpXml));
		return true;
	};
	//文本归档开关切换事件
	function archives_text_change(checked) {
		//文本归档关闭时，过程归档自动关闭
		if (checked == false) {
			if (LUI("fdNextProcessArchiveId").checkbox.prop("checked") == true) {
				LUI("fdNextProcessArchiveId").checkbox.click();
			}
		}
	}
	//过程归档开关切换事件
	function archives_process_change(checked) {
		//过程归档打开时，文本归档自动打开
		if (checked == true) {
			if (LUI("fdReviewTextArchiveId").checkbox.prop("checked") == false) {
				LUI("fdReviewTextArchiveId").checkbox.click();
			}
		}
	}
</script>
<c:if test="${param.useLabel != 'false'}">
	</tr>
		</td>
</c:if>
<%}%>