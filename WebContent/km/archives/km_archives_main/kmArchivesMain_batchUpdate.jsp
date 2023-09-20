<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">
<script type="text/javascript">
	Com_IncludeFile("jquery.js|xform.js");
	function submitUpdateForm() {
		seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
			var key = $("#fieldNameSelect").val();
			if("docTemplate" == key || "fdStorekeeper" == key){
				key =  key+"Name";
			}else{
				if(document.getElementsByName(key).length == 0) {
					key = key+"Id";
				}
			}
			var $field = $("[name='"+key+"']");
			if($field.val()==null || $.trim($field.val()) == '') {
				var required = $field.attr('validate') != null && $field.attr('validate').indexOf('required')>-1;
				if(required) {
					var subject = $field.attr('subject');
					if(subject == null) {
						subject = $("[name='"+$("#filedNameSelect").val()+"Name']").attr('subject');
					}
					dialog.alert(subject+"${lfn:message('km-archives:kmArchivesMain.canNotNull')}");
					return;
				}
			}
			document.updateForm.submit();
		})
	}
	function changeSelect(select) {
		var key = $(select).val();
		$(".change-div-class").hide();
		$("#"+key+"Div").show();
	}
</script>
<style>
.change-div-class {
	display:none;
}
</style>

<center>

<%-- <p class="txttitle"><bean:message bundle="km-archives" key="kmArchivesMain.batchUpdate.data"/></p> --%>
<form name="updateForm" action="${LUI_ContextPath }/km/archives/km_archives_main/kmArchivesMain.do?method=batchUpdate" method="post">
<div class='lui_form_title_frame'>
    <div class='lui_form_subject'>
        <bean:message bundle="km-archives" key="kmArchivesMain.batchUpdate.data"/>
    </div>
</div>
<input type="hidden" name="selects" value="${param.selectedIds }"/>
<table class="tb_normal" style="width:400px;">
	<tr>
		<td class="td_normal_title" style="width:15%;white-space:nowrap;">
			<bean:message bundle="km-archives" key="kmArchivesMain.fieldName"/>
		</td>
		<td>
			<select id="fieldNameSelect" name="fieldName" class="inputsgl" onchange="changeSelect(this);">
				<option value="docTemplate">${lfn:message('km-archives:kmArchivesMain.docTemplate')}</option>
				<option value="fdLibrary">${lfn:message('km-archives:kmArchivesMain.fdLibrary')}</option>
				<option value="fdVolumeYear">${lfn:message('km-archives:kmArchivesMain.fdVolumeYear')}</option>
				<option value="fdPeriod">${lfn:message('km-archives:kmArchivesMain.fdPeriod')}</option>
				<option value="fdUnit">${lfn:message('km-archives:kmArchivesMain.fdUnit')}</option>
				<option value="fdDenseId">${lfn:message('km-archives:kmArchivesMain.fdDenseLevel')}</option>
				<option value="fdStorekeeper">${lfn:message('km-archives:kmArchivesMain.fdStorekeeper')}</option>
				<option value="fdValidityDate">${lfn:message('km-archives:kmArchivesMain.fdValidityDate')}</option>
				<option value="fdFileDate">${lfn:message('km-archives:kmArchivesMain.fdFileDate')}</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" style="width:15%;white-space:nowrap;">
			<bean:message bundle="km-archives" key="kmArchivesMain.fieldValue"/>
		</td>
		<td>
			<div id="docTemplateDiv" class="change-div-class" style="display:block;">
				<xform:dialog propertyId="docTemplateId" propertyName="docTemplateName" required="true" showStatus="edit" style="width:95%;" subject="${lfn:message('km-archives:kmArchivesMain.docTemplate')}">
                	Dialog_SimpleCategory('com.landray.kmss.km.archives.model.KmArchivesCategory','docTemplateId','docTemplateName',false,null,'01');
                </xform:dialog>
			</div>
			<div id="fdLibraryDiv" class="change-div-class">
				<xform:select property="fdLibrary" showStatus="edit" subject="${lfn:message('km-archives:kmArchivesMain.fdLibrary')}">
                    <xform:beanDataSource serviceBean="kmArchivesLibraryService" selectBlock="fdName" />
                </xform:select>
			</div>
			<div id="fdVolumeYearDiv" class="change-div-class">
				<xform:select property="fdVolumeYear" showStatus="edit" subject="${lfn:message('km-archives:kmArchivesMain.fdVolumeYear')}">
               	<% int nowYear = Calendar.getInstance().get(Calendar.YEAR);
               		for(int x = nowYear;x>=1967;x--) { 
               		pageContext.setAttribute("selectYearIndex",x);%>
               		<xform:simpleDataSource value="${selectYearIndex }"></xform:simpleDataSource>
               		<%} %>
               	</xform:select>
			</div>
			<div id="fdPeriodDiv" class="change-div-class">
				<xform:select property="fdPeriod" showStatus="edit" subject="${lfn:message('km-archives:kmArchivesMain.fdPeriod')}">
                	<xform:beanDataSource serviceBean="kmArchivesPeriodService" selectBlock="fdId,fdName" whereBlock="" orderBy="" />
                </xform:select>
			</div>
			<div id="fdUnitDiv" class="change-div-class">
				<xform:select property="fdUnit" showStatus="edit" required="true" subject="${lfn:message('km-archives:kmArchivesMain.fdUnit')}">
                    <xform:beanDataSource serviceBean="kmArchivesUnitService" selectBlock="fdName" whereBlock="" orderBy="fdOrder asc" />
                </xform:select>
			</div>
			<div id="fdDenseIdDiv" class="change-div-class">
				<xform:select property="fdDenseId" showStatus="edit" subject="${lfn:message('km-archives:kmArchivesMain.fdDenseLevel')}">
					<xform:beanDataSource serviceBean="kmArchivesDenseService" selectBlock="fdId,fdName" />
				</xform:select>
			</div>
			<div id="fdStorekeeperDiv" class="change-div-class">
				<xform:address propertyId="fdStorekeeperId" propertyName="fdStorekeeperName" orgType="ORG_TYPE_PERSON|ORG_TYPE_POST" showStatus="edit" required="true" subject="${lfn:message('km-archives:kmArchivesMain.fdStorekeeper')}" style="width:95%;" />
			</div>
			<div id="fdValidityDateDiv" class="change-div-class">
				<xform:datetime onValueChange="null" property="fdValidityDate" subject="${lfn:message('km-archives:kmArchivesMain.fdValidityDate')}" showStatus="edit" required="true" dateTimeType="date" style="width:95%;" />
			</div>
			<div id="fdFileDateDiv" class="change-div-class">
				<xform:datetime onValueChange="null" property="fdFileDate" subject="${lfn:message('km-archives:kmArchivesMain.fdFileDate')}" showStatus="edit" required="true" dateTimeType="date" style="width:95%;" />
			</div>
		</td>
	</tr>
</table>
<div style="margin-top:10px;">
	<ui:button text="${lfn:message('button.submit')}" onclick="submitUpdateForm();"></ui:button>
	<ui:button text="${lfn:message('button.close')}" onclick="Com_CloseWindow();"></ui:button>
</div>
</form>
</center>
</template:replace>
</template:include>