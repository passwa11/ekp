<%@page import="com.landray.kmss.sys.xform.interfaces.ISysFormTemplateForm"%>
<%@page import="com.landray.kmss.km.archives.util.KmArchivesUtil"%>
<%@page import="com.landray.kmss.km.archives.interfaces.IKmArchivesFileTemplateForm"%>
<%@ page import="com.landray.kmss.common.module.core.register.loader.ModuleDictUtil" %>
<%@ page import="com.landray.kmss.common.forms.IExtendForm" %>
<%@ page import="com.landray.kmss.km.archives.interfaces.IKmArchivesFileTemplateModel" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
	Object _form = request.getAttribute(request.getParameter("formName"));
	String moduleUrl = request.getParameter("moduleUrl");
	if((_form instanceof IKmArchivesFileTemplateForm || ModuleDictUtil.isRequired((IExtendForm)_form, IKmArchivesFileTemplateModel.class))
			&& KmArchivesUtil.isStartFile(moduleUrl)) {
%>
<c:set var="templateForm" value="${requestScope[param.formName]}" />
<c:if test="${param.mechanismMap == 'true'}">
	<c:set var="fileTemplateForm" value="${templateForm.mechanismMap['KmArchivesFileTemplate']}" />
</c:if>

<script>
Com_IncludeFile("jquery.js|dialog.js|formula.js|doclist.js");
</script>

<!-- 设计器区域 -->
<c:if test="${param.useLabel != 'false'}">
	<tr LKS_LabelName="<bean:message bundle="km-archives" key="table.kmArchivesFileTemplate"/>" style="display:none" id="kmArchivesFile_tab">
		<td>
</c:if>
<table class="tb_normal" width=100% style="border-top:0;">
	<html:hidden property="mechanismMap(KmArchivesFileTemplate).fdId"/>
	<html:hidden property="mechanismMap(KmArchivesFileTemplate).docCreatorId"/>
	<html:hidden property="mechanismMap(KmArchivesFileTemplate).docCreateTime"/>
	<!-- 基础设置 -->
	<tr>
		<td colspan="4">
			<bean:message bundle="km-archives" key="py.JiChuSheZhi"/>
		</td>
	</tr>
	<!-- 归档的存放路径 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesFileTemplate.category"/>
		</td>
		<td colspan="3">
            <xform:dialog subject="${lfn:message('km-archives:kmArchivesFileTemplate.category') }" propertyId="mechanismMap(KmArchivesFileTemplate).categoryId" propertyName="mechanismMap(KmArchivesFileTemplate).categoryName" showStatus="edit" style="width:220px;">
            	Dialog_SimpleCategory('com.landray.kmss.km.archives.model.KmArchivesCategory','mechanismMap(KmArchivesFileTemplate).categoryId','mechanismMap(KmArchivesFileTemplate).categoryName',false,null,'00',file_afterSelectCategory,false);
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
				<xform:radio property="mechanismMap(KmArchivesFileTemplate).selectFilePersonType" onValueChange="file_changeFilePerson" showStatus="edit">
					<xform:simpleDataSource value="org" textKey="kmArchivesFileTemplate.fromOrg" bundle="km-archives"></xform:simpleDataSource>
					<xform:simpleDataSource value="formula" textKey="kmArchivesFileTemplate.fromFormula" bundle="km-archives"></xform:simpleDataSource>
				</xform:radio>
			</p>
			<p>
				<div id="orgSelect">
					<xform:address orgType="ORG_TYPE_PERSON" subject="${lfn:message('km-archives:kmArchivesFileTemplate.fdFilePerson') }" style="width:220px;"
								   propertyName="mechanismMap(KmArchivesFileTemplate).fdFilePersonName" propertyId="mechanismMap(KmArchivesFileTemplate).fdFilePersonId" showStatus="edit">
					</xform:address>
				</div>
				<%--<div id="formulaSelect">
					<input name="mechanismMap(KmArchivesFileTemplate).fdFilePersonFormula" class="inputsgl" style="width:200px" type="hidden" value='${fileTemplateForm.fdFilePersonFormula }'>
					<input name="mechanismMap(KmArchivesFileTemplate).fdFilePersonFormulaName" class="inputsgl" style="width:200px" readonly="readonly" value='${fileTemplateForm.fdFilePersonFormulaName==null? fileTemplateForm.fdFilePersonFormula:fileTemplateForm.fdFilePersonFormulaName}'>
	            	<a href="#" onclick="file_selectByFormula('mechanismMap(KmArchivesFileTemplate).fdFilePersonFormula', 'mechanismMap(KmArchivesFileTemplate).fdFilePersonFormulaName');"><bean:message key="button.select"/></a>
				</div>--%>
				<div id="formulaSelect" style="display:none">
				</div>
			</p>
		</td>
	</tr>
	<!-- 归档后保存审批意见 -->
	<c:if test="${param.enableFlow eq 'true'}">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdSaveApproval"/>
			</td>
			<td colspan="3">
				<ui:switch property="mechanismMap(KmArchivesFileTemplate).fdSaveApproval"
						   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
						   disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			</td>
		</tr>
	</c:if>

	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdPreFile"/>
		</td>
		<td colspan="3">
			<ui:switch property="mechanismMap(KmArchivesFileTemplate).fdPreFile"
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
			<ui:switch property="mechanismMap(KmArchivesFileTemplate).fdSaveOldFile"
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
			<input type="hidden" name="selectedValue_docSubjectMapping" value="${fileTemplateForm.docSubjectMapping}">
			<select ftype="String" name="mechanismMap(KmArchivesFileTemplate).docSubjectMapping" class="inputsgl" style="width:220px;">
					<option value="">${lfn:message('sys-modeling-base:modeling.archives.option')}</option>
			</select>
		</td>
	</tr>
	<!-- 所属卷库 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdLibrary"/>
		</td>
		<td colspan="3">
			<input type="hidden" name="selectedValue_fdLibraryMapping" value="${fileTemplateForm.fdLibraryMapping}">
			<select ftype="String" name="mechanismMap(KmArchivesFileTemplate).fdLibraryMapping" class="inputsgl" style="width:220px;">
					<option value="">${lfn:message('sys-modeling-base:modeling.archives.option')}</option>
			</select>
		</td>
	</tr>
	<!-- 组卷年度 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdVolumeYear"/>
		</td>
		<td colspan="3">
			<input type="hidden" name="selectedValue_fdVolumeYearMapping" value="${fileTemplateForm.fdVolumeYearMapping}">
			<select ftype="String" name="mechanismMap(KmArchivesFileTemplate).fdVolumeYearMapping" class="inputsgl" style="width:220px;">
					<option value="">${lfn:message('sys-modeling-base:modeling.archives.option')}</option>
			</select>
		</td>
	</tr>
	<!-- 保管期限 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdPeriod"/>
		</td>
		<td colspan="3">
			<input type="hidden" name="selectedValue_fdPeriodMapping" value="${fileTemplateForm.fdPeriodMapping}">
			<select ftype="String" name="mechanismMap(KmArchivesFileTemplate).fdPeriodMapping" class="inputsgl" style="width:220px;">
					<option value="">${lfn:message('sys-modeling-base:modeling.archives.option')}</option>
			</select>
		</td>
	</tr>
	<!-- 保管单位 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdUnit"/>
		</td>
		<td colspan="3">
			<input type="hidden" name="selectedValue_fdUnitMapping" value="${fileTemplateForm.fdUnitMapping}">
			<select ftype="String" name="mechanismMap(KmArchivesFileTemplate).fdUnitMapping" class="inputsgl" style="width:220px;">
					<option value="">${lfn:message('sys-modeling-base:modeling.archives.option')}</option>
			</select>
		</td>
	</tr>
	<!-- 保管员 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdStorekeeper"/>
		</td>
		<td colspan="3">
			<input type="hidden" name="selectedValue_fdKeeperMapping" value="${fileTemplateForm.fdKeeperMapping}">
			<%--<select ftype="com.landray.kmss.sys.organization.model.SysOrgPerson" name="mechanismMap(KmArchivesFileTemplate).fdKeeperMapping" class="inputsgl" style="width:220px;">
					<option value="">${lfn:message('sys-modeling-base:modeling.archives.option')}</option>
			</select>--%>
			<select ftype="com.landray.kmss.sys.organization.model.SysOrgPerson" name="mechanismMap(KmArchivesFileTemplate).fdKeeperMapping" class="inputsgl" style="width:220px;">
				<c:import url="/km/archives/include/kmArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="com.landray.kmss.sys.organization.model.SysOrgPerson" />
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
			<input type="hidden" name="selectedValue_fdValidityDateMapping" value="${fileTemplateForm.fdValidityDateMapping}">
			<select ftype="Date|DateTime" name="mechanismMap(KmArchivesFileTemplate).fdValidityDateMapping" class="inputsgl" style="width:220px;">
					<option value="">${lfn:message('sys-modeling-base:modeling.archives.option')}</option>
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
			<input type="hidden" name="selectedValue_fdFileDateMapping" value="${fileTemplateForm.fdFileDateMapping}">
			<select ftype="Date|DateTime" name="mechanismMap(KmArchivesFileTemplate).fdFileDateMapping" class="inputsgl" style="width:220px;">
					<option value="">${lfn:message('sys-modeling-base:modeling.archives.option')}</option>
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
	<html:hidden property="mechanismMap(KmArchivesFileTemplate).fdTmpXml"/>
</table>
<script>
	Com_IncludeFile("formula.js");
	Com_IncludeFile("archivesOption.js", Com_Parameter.ContextPath + 'sys/modeling/base/mechanism/archives/resources/js/', 'js', true);
	Com_AddEventListener(window, 'load', function() {
		var extendDatas = $("input[name='mechanismMap(KmArchivesFileTemplate).fdTmpXml']").val();
		if(extendDatas) {
			extendDatas = JSON.parse(extendDatas);
			file_buildExtendData(extendDatas);
		}
		//公式定义器
		seajs.use(['sys/modeling/base/relation/trigger/behavior/js/formulaBuilder.js']
			,function(formulaBuilder){
				formulaBuilder.initFieldList('${xformId }');
				var html = $("<div class='modeling_formula'/>");
				html.append("<input type='hidden' name='mechanismMap(KmArchivesFileTemplate).fdFilePersonFormula' value='${fileTemplateForm.fdFilePersonFormula}' class='inputsgl' style='width:150px'/>");
				html.append("<input type='text' name='mechanismMap(KmArchivesFileTemplate).fdFilePersonFormulaName' value='${fileTemplateForm.fdFilePersonFormulaName}' class='inputsgl' style='width:150px'/>");
				var $formula = $("<span class='highLight'><a href='javascrip:void(0);'>选择</a></span>");

				$formula.on("click", function (e) {
					Formula_Dialog('mechanismMap(KmArchivesFileTemplate).fdFilePersonFormula', 'mechanismMap(KmArchivesFileTemplate).fdFilePersonFormulaName', formulaBuilder.getFieldList() || "", "com.landray.kmss.sys.organization.model.SysOrgElement");
				});
				html.append($formula);
				$("#formulaSelect").append(html);
		})

		file_changeFilePerson('${fileTemplateForm.selectFilePersonType}');
		var fdmodelId = '${HtmlParam.modelId }';
		initxformData(fdmodelId);
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
		$("input[name='mechanismMap(KmArchivesFileTemplate).fdTmpXml']").val(JSON.stringify(tmpXml));
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