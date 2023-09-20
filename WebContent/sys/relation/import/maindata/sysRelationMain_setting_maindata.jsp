<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.ftsearch.db.service.ISysFtsearchConfigService"%>
<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="java.util.List"%>

<template:include ref="default.dialog">
	<template:replace name="head">
		<script type="text/javascript" >
			Com_IncludeFile("validator.jsp|validation.jsp|validation.js|plugin.js|doclist.js|jquery.js|dialog.js", null, "js");
			Com_IncludeFile("rela_maindata.js","${KMSS_Parameter_ContextPath}sys/relation/import/resource/","js",true);
		</script>
		<style>
			.tb_simple .inputsgl[readonly] {
				border:1px solid #ccc;
				background:#f0f0f0;
				cursor: not-allowed;
			}
		</style>
	</template:replace>
	<template:replace name="content"> 
	<script type="text/javascript">
		var _params = {
				'tempId':'<%=com.landray.kmss.util.IDGenerator.generateID()%>',
				'varName':'rela_opt',
				'format.error':'<bean:message bundle="sys-relation" key="validate.fdOtherUrl.format.error"/>',
				'fdModuleName.isNull':"<bean:message bundle="sys-relation" key="sysRelationMain.fdModuleName.isNull"/>",
				'maindata.null':'<bean:message key="dialog.requiredSelect"/>',
				'maindata.error1':"<bean:message bundle="sys-relation" key="sysRelationMainData.error1"/>",
				'maindata.error2':"<bean:message bundle="sys-relation" key="sysRelationMainData.error2"/>",
				'selectCategoryTitle':"<bean:message bundle="sys-xform-maindata" key="sysFormMainData.select.category"/>",
				'search.title':'<bean:message key="button.search"/>',
				'button.ok':'<bean:message key="button.ok"/>',
				'button.cancel':'<bean:message key="button.cancel"/>',
				'button.back':"<bean:message bundle="sys-xform-maindata" key="sysFormMainData.select.back"/>",
				'validateSelector':'#TABLE_DocList'
		};
		var _setting = new RelationMainDataSetting(_params);
	</script>
	<div class=rela_config_subset>
			<table id="TABLE_DocList" class="tb_simple" style="width:100%" >
				<tr>
					<td width="20%" class="td_normal_title" style="padding-right: 20px">
						<bean:message bundle="sys-relation" key="sysRelationMain.relation.name"/>
					</td>
					<td width="60%" colspan="2">
						<span>
							<xform:text 
								property="fdModuleName" 
							    required="true" 
							    validators="maxLength(200)" 
								showStatus="edit" 
								subject="${lfn:message('sys-relation:sysRelationMain.relation.name') }" 
								style="width:97%">
							</xform:text>
							<input type="hidden" name="staticSourceDocSubject">
						</span>
					</td>
					<td width="20%">
					</td>
				</tr>
				<tr align="center">
					<td width="30%">
						${lfn:message('sys-relation:sysRelationMainData.fdName') }
					</td>
					<td width="30%">
						${lfn:message('sys-relation:sysRelationMainData.fdTemplateSubject') }
					</td>
					<td width="30%">
						${lfn:message('sys-relation:sysRelationMainData.fdMainDataName') }
					</td>
					<td>
						<a href="javascript:;" class="com_btn_link" id="rela_config_add">${lfn:message('button.insert')}</a>
					</td>
				</tr>
				<tr KMSS_IsReferRow="1" style="display:none">
					<td>
						<input class="inputsgl" name="fdName" style="width:94%" validate="required"/><span class="txtstrong">*</span>
					</td>
					<td>
						<input type="hidden" name="fdTemplateId" />
						<input type="hidden" name="fdTemplateModelName" />
						<input class="inputsgl" name="fdTemplateSubject" style="width:94%" readonly="readonly"/>
					</td>
					<td>
						<input type="hidden" name="fdMainDataId" />
						<input type="hidden" name="fdMainDataModelName" validate="required"/>
						<input class="inputsgl" name="fdMainDataName" style="width:80%" readonly="readonly"/><span class="txtstrong">*</span>
						<a href="javascript:void(0);" onclick="_select_main_data(this);" class="com_btn_link">${lfn:message('button.select')}</a>
					</td>
					<td class="rela_opt_area">
						<div class="lui_icon_s lui_icon_s_icon_remove" onclick="rela_opt.deleteConfig();"></div>
						<div class="lui_icon_s lui_icon_s_icon_arrow_up" onclick="DocList_MoveRow(-1);"></div>
						<div class="lui_icon_s lui_icon_s_icon_arrow_down" onclick="DocList_MoveRow(1);"></div>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center" class="rela_scope_button">
						<ui:button text="${lfn:message('button.ok')}" id="rela_config_save"></ui:button>
						&nbsp;&nbsp;&nbsp;
						<ui:button text="${lfn:message('button.cancel')}" id="rela_config_close" styleClass="lui_toolbar_btn_gray"></ui:button>
					</td>
				</tr>
			</table>
		</div>
	</template:replace>
</template:include>