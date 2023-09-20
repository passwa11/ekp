<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<script type="text/javascript" >
			Com_IncludeFile("doclist.js|jquery.js");
			Com_IncludeFile("rela_setting.js","${KMSS_Parameter_ContextPath}sys/relation/import/resource/","js",true);
		</script>
	</template:replace>
	<template:replace name="content"> 
		<script type="text/javascript">
			var _params = {
					'varName':'rela_opt',
					'submit.prompt':"<bean:message key="sysRelationMain.submit.prompt" bundle="sys-relation"/>",
					'operation.coll':'<bean:message key="sysRelationMain.operation.coll" bundle="sys-relation"/>',
					'operation.exp':'<bean:message key="sysRelationMain.operation.exp" bundle="sys-relation"/>'
				};
			if('${not empty param.key}'=='true')
				_params.key = '${JsParam.key}';
			new RelationSetting(_params);
			
	        //设置弹出层样式
			//$(".lui-component.lui_dialog_main",window.parent.document).css("z-index","1100");
		</script>
		
		<%-- 多关联设置的时候有个关联说明 --%>
		<c:if test="${not empty param.key == true}">
			<%-- 默认不要这个描述，功能与文本关联重叠，这里是为了兼容旧数据保留 --%>
			<div class="rela_conf_des_area" style="display:none;">
				<div class="rela_conf_tab" style="margin-bottom:5px;">
					<div class="rela_td_title">
						<bean:message key="sysRelationMain.config.desTtitle" bundle="sys-relation"/>
					</div>
					<div class="rela_opt_tb_area">
						<div class="com_bgcolor_n com_bordercolor_n com_fontcolor_n" 
							style="padding:0 10px;cursor: pointer;border-width: 1px;border-style: solid;"
							id="main_other_content_btn">
							${lfn:message('sys-relation:sysRelationMain.config.desTtitle.close')}
						</div>
					</div>
				</div>
				<div class="rela_des_content" id="main_other_content">
					<table class="tb_simple" style="width: 100%">
						<tr>
							<td width="15%">${lfn:message('sys-relation:sysRelationMain.fdDesSubject')}</td>
							<td width="85%"><input type="text" name="fdDesSubject" class="inputsgl" style="width:98%"></td>
						</tr>
						<tr>
							<td width="15%">${lfn:message('sys-relation:sysRelationMain.fdDesContent')}</td>
							<td width="85%">
								<textarea style="width:98%"  name="fdDesContent" class="rela_des_textarea"></textarea>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</c:if>
		<div class="rela_conf_tab">
			<div class="rela_td_title"><bean:message key="title.sysRelationMain.hasSetting" bundle="sys-relation"/></div>
			<div class="rela_opt_tb_area">
				<ui:button text="${lfn:message('button.insert')}" id="rela_add_btn"></ui:button>
				<ui:button text="${lfn:message('sys-relation:button.setting.ok')} " id="rela_save_btn"></ui:button>
				<ui:button text="${lfn:message('button.cancel')} " id="rela_close_btn"></ui:button>
			</div>
		</div>
		<div class="rela_config">
			<div class="rela_config_table">
				<table id="TABLE_DocList">
					<tr KMSS_IsReferRow="1" style="display:none">
						<td>
							<span name="fdModuleName"></span>
							<input type="hidden" name="fdModuleName"/>
							<input type="hidden" name="fdId" />
							<input type="hidden" name="fdType" />
							<input type="hidden" name="fdModuleModelName" />
							<input type="hidden" name="fdOrderBy" />
							<input type="hidden" name="fdOrderByName" />
							<input type="hidden" name="fdPageSize" />
							<input type="hidden" name="fdRelationProperty" />
							<input type="hidden" name="fdParameter" />
							<input type="hidden" name="fdKeyWord" />
							<input type="hidden" name="docCreatorId" />
							<input type="hidden" name="docCreatorName" />
							<input type="hidden" name="fdFromCreateTime" />
							<input type="hidden" name="fdToCreateTime" />
							<input type="hidden" name="fdSearchScope" />
							<input type="hidden" name="fdIsTemplate" />
							<input type="hidden" name="fdOtherUrl" />
							<input type="hidden" name="fdDiffusionAuth" />
							<input type="hidden" name="fdIndex" />
							<input type="hidden" name="fdCCType" />
							<input type="hidden" name="fdChartId" />
							<input type="hidden" name="fdChartName" />
							<input type="hidden" name="fdChartType" />
							<input type="hidden" name="fdDynamicData" />
							<input type="hidden" name="fdDescription" />
						</td>
						<td class="rela_opt_area rela_opt_area_main">
							<div class="lui_icon_s lui_icon_s_icon_pencil" title="${lfn:message('button.edit')}" onclick="rela_opt.editConfig();"></div>
							<div class="lui_icon_s lui_icon_s_icon_remove" title="${lfn:message('button.delete')}"  onclick="rela_opt.deleteConfig();"></div>
							<div class="lui_icon_s lui_icon_s_icon_arrow_up" title="${lfn:message('button.moveup') }"  onclick="DocList_MoveRow(-1);"></div>
							<div class="lui_icon_s lui_icon_s_icon_arrow_down" title="${lfn:message('button.movedown') }"  onclick="DocList_MoveRow(1);"></div>
						</td>
					</tr>
				</table>
			</div>
			<div class="rela_config_inline">
				<div class="rela_config_inline_txt">
					<bean:message key="sysRelationMain.operation.coll" bundle="sys-relation"/>
				</div>
			</div>
			<div id="rela_config_detail" class="rela_config_detail">
				<div class="rela_detail_title_area rela_title">
					<bean:message key="title.sysRelationMain.condition.setting" bundle="sys-relation"/>
				</div>
				<table>
					<tr><td width="20%">
						<div><bean:message bundle="sys-relation" key="sysRelationEntry.select.type" /></div>
					</td>
					<td>
						<nobr>
							<label>
							<input type="radio" name="fdRelaType" value="7" checked="checked"/>
							<bean:message bundle="sys-relation" key="sysRelationEntry.fdType7" />
							</label>
							<label>
							<input type="radio" name="fdRelaType" value="5"/>
							<bean:message bundle="sys-relation" key="sysRelationEntry.fdType5" />
							</label>
							<label>
							<input type="radio" name="fdRelaType" value="4"/>
							<bean:message bundle="sys-relation" key="sysRelationEntry.fdType4" />
							</label>
							<label>
							<input type="radio" name="fdRelaType" value="1"/>
							<bean:message bundle="sys-relation" key="sysRelationEntry.fdType1" />
							</label>
							<label>
							<input type="radio" name="fdRelaType" value="2"/>
							<bean:message bundle="sys-relation" key="sysRelationEntry.fdType2" />
							</label>
							<label>
							<input type="radio" name="fdRelaType" value="6"/>
							${ lfn:message('sys-relation:sysRelationEntry.text') }
							</label>
							
							<!-- czk2019 -->
							<label>
							<input type="radio" name="fdRelaType" value="8"/>
							<bean:message bundle="sys-relation" key="sysRelationEntry.fdType8" />
							</label>
							<!-- 图表中心 -->
							<kmss:ifModuleExist path="/dbcenter/echarts/">
							<label>
							<input type="radio" name="fdRelaType" value="9"/>
							<bean:message bundle="sys-relation" key="sysRelationEntry.fdType9" />
							</label>
							</kmss:ifModuleExist>
							
						</nobr>
					</td></tr>
				</table>
				<iframe id="rela_search_config" frameborder="0" scrolling="no" width="100%"></iframe>
			</div>
		</div>
	</template:replace>
</template:include>
