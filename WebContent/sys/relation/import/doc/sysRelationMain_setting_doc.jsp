<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.ftsearch.db.service.ISysFtsearchConfigService"%>
<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="java.util.List"%>

<template:include ref="default.dialog">
	<template:replace name="head">
		<link rel="stylesheet" href="<c:url value="/sys/relation/import/resource/rela_doc.css" />">
	</template:replace>
	<template:replace name="content"> 
	<script type="text/javascript" >
		var mainModelName = "${JsParam.currModelName}";
		Com_IncludeFile("validator.jsp|validation.jsp|validation.js|plugin.js|jquery.js|dialog.js|calendar.js", null, "js");
		Com_IncludeFile("rela_doc.js","${KMSS_Parameter_ContextPath}sys/relation/import/resource/","js",true);
	</script>
	<script type="text/javascript">
		var _params = {
				'tempId':'<%=com.landray.kmss.util.IDGenerator.generateID()%>',
				'varName':'rela_opt',
				'format.error':'<bean:message bundle="sys-relation" key="validate.fdOtherUrl.format.error"/>',
				'fdModuleName.isNull':'<bean:message bundle="sys-relation" key="sysRelationMain.fdModuleName.isNull"/>',
				'search.title':'<bean:message key="button.search"/>',
				'button.ok':'<bean:message key="button.ok"/>',
				'button.cancel':'<bean:message key="button.cancel"/>',
				'validateSelector':'#TABLE_DocList_2'
		};
		var _setting = new RelationDocSetting(_params);
		
		function setSearchField(select){
			if($(select).val()=="createTime"){
				$(".relevance_startTime_Field").show();
				$(".relevance_startTime_Field").attr("style","top:39px");
				$(".relevance_dialog_moduleSelect").hide();
			}else{
				$(".relevance_startTime_Field").hide();
				$(".relevance_dialog_moduleSelect").show();
				var option = $(select).find("option:selected");
				var type = option.text();
				var placeholderMsg = "<bean:message bundle="sys-xform" key="sysFormMain.relevance.enter" />";
				$(".relevance_dislog_moduleSelect_input").attr("placeholder",placeholderMsg+type);
			}
			$(".relevance_dislog_moduleSelect_input").val("");
			$("input[name='startTime']").val("");
			$("input[name='endTime']").val("");
		}
		
	</script>
	<div class="rela_config_subset">
		<input type="hidden" name="fdModuleModelName">
		<table id="TABLE_DocList_2" class="tb_simple" style="width:100%" >
			<tr>
				<td width="22%" class="td_normal_title" style="padding-right: 20px;word-break: break-word;">
					<bean:message bundle="sys-relation" key="sysRelationMain.relation.name"/>
				</td>
				<td>
					<xform:text property="fdModuleName" required="true" validators="maxLength(200)" showStatus="edit" 
					subject="${lfn:message('sys-relation:sysRelationMain.relation.name') }" style="width:80%"></xform:text>
				</td>
			</tr>			
			<tr>
				<td colspan="2" style="padding-left: 0px;">
					<div style="height: 32px;background-color:#f3f9fd;">
						<!-- 分类导航 -->
						<div>
							<div style="color: #008cee;font-size:13px; line-height:32px;height:32px;padding:0 5px;">
								<span id="__categoryNavigation__" style="cursor:pointer;">
									<bean:message bundle="sys-relation" key="sysRelationDoc.categotyNavigation"></bean:message>
									<img src='${KMSS_Parameter_ContextPath }sys/xform/designer/relevance/icon/select.png' style='display:inline-block;width:16px;height:16px;top:7px;'/>
								</span>
							</div>
							<ui:popup align="down-left" positionObject="#__categoryNavigation__" style="background:white;">
								<div style="width:260px;">
									<ui:menu layout="sys.ui.menu.ver.default">
										<ui:menu-source id="__categoryNavigationMenu" autoFetch="true" target="_self" href="javascript:updateList('!{fdTemplateId}','!{modelPath}','!{fdKey}','sysRelationMain_setting_doc','!{isBase}','!{fdHierarchyId}');">
											<ui:source type="AjaxJsonp">
												{"url":""}
											</ui:source>
										</ui:menu-source>
									</ui:menu>
								</div>
							</ui:popup>
						</div>
						<!-- 模块搜索 -->
						<select class="relevance_search_select" style="top:45px;" onchange="setSearchField(this);">
							<option value="subject"><bean:message bundle="sys-xform" key="sysFormMain.relevance.subject" /></option>
							<option value="number"><bean:message bundle="sys-xform" key="sysFormMain.relevance.number" /></option>
							<option value="createTime"><bean:message bundle="sys-xform" key="sysFormMain.relevance.createTime" /></option>
							<option value="creator"><bean:message bundle="sys-xform" key="sysFormMain.relevance.creator" /></option>
						</select>
						<div class="relevance_dialog_moduleSelect" style="top:45px;">
							<input type='text' class='relevance_dislog_moduleSelect_input' title='${lfn:message("sys-relation:sysRelationDoc.moduleSelect")}' onkeyup='enterTrigleSelect(event,this);' placeholder='${lfn:message("sys-xform:sysFormMain.relevance.inputSubject")}'></input>
							<div class='relevance_dislog_moduleSelect_delWord' style='display:none;' onclick='relevance_dialog_delWord();'></div>
							<input type='button' class='relevance_dislog_moduleSelect_select' title='${lfn:message("sys-relation:sysRelationDoc.search")}' onclick='relevance_dialog_moduleSelect();'></input>
						</div>
						<div class="relevance_startTime_Field" style="display:none">
							<!-- 因校验框架使用需要父节点中有TD，故这里使用table标签 -->
							<table>
								<tr>
									<td>
										<xform:datetime property="startTime" showStatus="edit" dateTimeType="date" subject="${lfn:message('sys-xform:sysFormMain.relevance.startTime')}" placeholder="yyyy-MM-dd" style="display:inline-block;width:120px;height:26px;"></xform:datetime>
									</td>
									<td>
										<span style="top:-10px;position:relative;color:#4285f4;">__</span>
									</td>
									<td>
										<xform:datetime property="endTime" showStatus="edit" dateTimeType="date" subject="${lfn:message('sys-xform:sysFormMain.relevance.endTime')}" placeholder="yyyy-MM-dd" style="display:inline-block;width:120px;height:26px;"></xform:datetime>
									</td>
									<td>
										<ui:button text="${lfn:message('sys-xform:sysFormMain.relevance.search')}" title="${lfn:message('sys-xform:sysFormMain.relevance.search')}" style="width:60px;position:relative;top:-2px;" onclick="relevance_dialog_moduleSelect();"></ui:button>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class='relevance_dialog_wrap'>
						<!-- 当前路径 -->
						<div style="display:inline-block;">
						 <span class=txtlistpath><bean:message key="page.curPath"/><span id="modelPath"></span></span>
						</div>
						<!-- 数据展示区 -->
						<div id="dataShowDiv" style="min-height:200px;">
							<iframe id="dataShowList" width="100%" scrolling="no" onload="_setting.resizeFrame();" frameborder="no" style="border:0px;"></iframe>
							<div id="relevance_dialog_loading" class="relevance_dialog_loading" title='${lfn:message("sys-relation:sysRelationDoc.dataLoading")}' style="diplay:none;"></div>
						</div>
						<!-- 操作区 -->
						<div align="center" class="rela_scope_button">
							<ui:button text="${lfn:message('button.ok')}" id="rela_config_save"></ui:button>
							&nbsp;&nbsp;&nbsp;
							<ui:button text="${lfn:message('button.cancel')}" id="rela_config_close" styleClass="lui_toolbar_btn_gray"></ui:button>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</template:replace>
</template:include>