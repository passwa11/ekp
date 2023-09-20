<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.relation.forms.SysRelationConditionForm"%>
<%@page import="com.landray.kmss.sys.relation.forms.SysRelationStaticNewForm"%>
<%@page import="com.landray.kmss.sys.relation.forms.SysRelationTextForm"%>
<%@page import="com.landray.kmss.sys.relation.forms.SysRelationEntryForm"%>
<%@page import="com.landray.kmss.sys.relation.forms.SysRelationMainDataForm"%>
<%@page import="com.landray.kmss.sys.relation.util.SysRelationUtil"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="java.util.List"%>

<!-- czk2019 -->

<c:set var="sysRelationMainForm" value="${mainModelForm.sysRelationMainForm}" scope="request"/>
<script type="text/javascript">
	var relationEntrys={};
	<c:forEach items="${sysRelationMainForm.sysRelationEntryFormList}" varStatus="vstatus" var="sysRelationEntryForm">
		var relationConditions={};
		var relationTexts={};
		var staticInfos={};
		var mainDatas={};
		<%
			JSONObject relationEntry = new JSONObject();
			SysRelationEntryForm sysRelationEntryForm = (SysRelationEntryForm) pageContext.getAttribute("sysRelationEntryForm");
			relationEntry.accumulate("fdId",sysRelationEntryForm.getFdId());
			relationEntry.accumulate("fdType",sysRelationEntryForm.getFdType());
			relationEntry.accumulate("fdModuleName",sysRelationEntryForm.getFdModuleName());
			relationEntry.accumulate("fdModuleModelName",sysRelationEntryForm.getFdModuleModelName());
			relationEntry.accumulate("fdOrderBy",sysRelationEntryForm.getFdOrderBy());
			relationEntry.accumulate("fdOrderByName",sysRelationEntryForm.getFdOrderByName());
			relationEntry.accumulate("fdPageSize",sysRelationEntryForm.getFdPageSize());
			relationEntry.accumulate("fdParameter",sysRelationEntryForm.getFdParameter());
			relationEntry.accumulate("fdKeyWord",sysRelationEntryForm.getFdKeyWord());
			relationEntry.accumulate("docCreatorId",sysRelationEntryForm.getDocCreatorId());
			relationEntry.accumulate("docCreatorName",sysRelationEntryForm.getDocCreatorName());
			relationEntry.accumulate("fdFromCreateTime",sysRelationEntryForm.getFdFromCreateTime());
			relationEntry.accumulate("fdToCreateTime",sysRelationEntryForm.getFdToCreateTime());
			relationEntry.accumulate("fdSearchScope",sysRelationEntryForm.getFdSearchScope());
			relationEntry.accumulate("fdOtherUrl",sysRelationEntryForm.getOtherUrlNoPattern());
			relationEntry.accumulate("fdCCType",sysRelationEntryForm.getFdCCType());
			relationEntry.accumulate("fdChartId",sysRelationEntryForm.getFdChartId());
			relationEntry.accumulate("fdChartName",sysRelationEntryForm.getFdChartName());
			relationEntry.accumulate("fdChartType",sysRelationEntryForm.getFdChartType());
			relationEntry.accumulate("fdDynamicData",sysRelationEntryForm.getFdDynamicData());
			relationEntry.accumulate("fdIndex",sysRelationEntryForm.getFdIndex());
			relationEntry.accumulate("docStatus",sysRelationEntryForm.getDocStatus());
			relationEntry.accumulate("fdDiffusionAuth",sysRelationEntryForm.getFdDiffusionAuth());
			relationEntry.accumulate("fdIsTemplate",sysRelationEntryForm.getFdIsTemplate());
			out.println("relationEntrys['"+SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())+"'] = "+ relationEntry.toString()+";");
			List conditionList = sysRelationEntryForm.getSysRelationConditionFormList();
			if(!conditionList.isEmpty()){
				for(int m=0; m<conditionList.size();m++){
					JSONObject conditionEntry = new JSONObject();
					SysRelationConditionForm sysRelationConditionForm = (SysRelationConditionForm)conditionList.get(m);
					conditionEntry.accumulate("fdId",sysRelationConditionForm.getFdId());
					conditionEntry.accumulate("fdItemName",sysRelationConditionForm.getFdItemName());
					conditionEntry.accumulate("fdParameter1",sysRelationConditionForm.getFdParameter1());
					conditionEntry.accumulate("fdParameter2",sysRelationConditionForm.getFdParameter2());
					conditionEntry.accumulate("fdParameter3",sysRelationConditionForm.getFdParameter3());
					conditionEntry.accumulate("fdBlurSearch",sysRelationConditionForm.getFdBlurSearch());
					conditionEntry.accumulate("fdVarName",sysRelationConditionForm.getFdVarName());
					out.println("relationConditions['"+SysRelationUtil.replaceJsonQuotes(sysRelationConditionForm.getFdItemName())+"'] = " +conditionEntry.toString()+";");
				}
				out.println("relationEntrys['" + SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())+"'].relationConditions = relationConditions;");
			}
			List textList = sysRelationEntryForm.getSysRelationTextFormList();
			if(!textList.isEmpty()){
				for(int n=0; n<textList.size();n++){
					JSONObject TextEntry = new JSONObject();
					SysRelationTextForm sysRelationTextForm = (SysRelationTextForm)textList.get(n);
					TextEntry.accumulate("fdDescription",sysRelationTextForm.getFdDescription());
					
					out.println("relationTexts = " +TextEntry.toString()+";");
				}
				out.println("relationEntrys['" + SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())+"'].relationTexts = relationTexts;");
			}
			
			List staticInfoList = sysRelationEntryForm.getSysRelationStaticNewFormList();
			if (!staticInfoList.isEmpty()) {
				JSONObject staticEntry = new JSONObject();
				for (int i = 0; i < staticInfoList.size(); i++) {
					JSONObject staticMsg = new JSONObject();
					SysRelationStaticNewForm sysRelationStaticNewForm = (SysRelationStaticNewForm) staticInfoList.get(i);
					staticMsg.accumulate("fdId",
							sysRelationStaticNewForm.getFdId());
					staticMsg.accumulate("fdSourceId",
							sysRelationStaticNewForm.getFdSourceId());
					staticMsg.accumulate("fdSourceModelName",
							sysRelationStaticNewForm.getFdSourceModelName());
					staticMsg.accumulate("fdSourceDocSubject",
							sysRelationStaticNewForm.getFdSourceDocSubject());
					staticMsg.accumulate("fdRelatedId",
							sysRelationStaticNewForm.getFdRelatedId());
					staticMsg.accumulate("fdRelatedModelName",
							sysRelationStaticNewForm.getFdRelatedModelName());
					staticMsg.accumulate("fdRelatedUrl",
							sysRelationStaticNewForm.getFdRelatedUrl());
					staticMsg.accumulate("fdRelatedName",
							sysRelationStaticNewForm.getFdRelatedName());
					staticMsg.accumulate("fdRelatedType",
							sysRelationStaticNewForm.getFdRelatedType());
					staticMsg.accumulate("fdEntryId",
							sysRelationStaticNewForm.getFdEntryId());
					staticMsg.accumulate("fdIsCreator",
							sysRelationStaticNewForm.getFdIsCreator());
					String fdIndex = sysRelationStaticNewForm.getFdIndex();
					if(StringUtil.isNull(fdIndex)){
						fdIndex = i+"";
					}
					staticMsg.accumulate("fdIndex",fdIndex);
					staticEntry.accumulate(fdIndex,staticMsg);
				}
					out
					.println("staticInfos['"
							+ sysRelationEntryForm.getFdId()
							+ "'] = "
							+ staticEntry.toString() + ";");
				
					out
						.println("relationEntrys['"
								+ SysRelationUtil
										.replaceJsonQuotes(sysRelationEntryForm
												.getFdId())
								+ "'].staticInfos = staticInfos;");
			}
			// 主数据
			List mainDataList = sysRelationEntryForm.getSysRelationMainDataFormList();
			if (!mainDataList.isEmpty()) {
				JSONObject mainData = new JSONObject();
				for (int i = 0; i < mainDataList.size(); i++) {
					JSONObject dataMsg = new JSONObject();
					SysRelationMainDataForm mainDataForm = (SysRelationMainDataForm) mainDataList.get(i);
					dataMsg.accumulate("fdId", mainDataForm.getFdId());
					dataMsg.accumulate("fdName", mainDataForm.getFdName());
					dataMsg.accumulate("fdTemplateId", mainDataForm.getFdTemplateId());
					dataMsg.accumulate("fdTemplateModelName", mainDataForm.getFdTemplateModelName());
					dataMsg.accumulate("fdTemplateSubject", mainDataForm.getFdTemplateSubject());
					dataMsg.accumulate("fdMainDataId", mainDataForm.getFdMainDataId());
					dataMsg.accumulate("fdMainDataModelName", mainDataForm.getFdMainDataModelName());
					dataMsg.accumulate("fdMainDataName", mainDataForm.getFdMainDataName());
					dataMsg.accumulate("fdEntryId", mainDataForm.getFdEntryId());
					
					String fdIndex = mainDataForm.getFdIndex();
					if(StringUtil.isNull(fdIndex)) {
						fdIndex = i + "";
					}
					dataMsg.accumulate("fdIndex", fdIndex);
					mainData.accumulate(fdIndex, dataMsg);
				}
				out.println("mainDatas['"
							+ sysRelationEntryForm.getFdId()
							+ "'] = "
							+ mainData.toString() + ";");
				
				out.println("relationEntrys['"
								+ SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())
								+ "'].mainDatas = mainDatas;");
			}
			//czk2019  人员关联
			String personIds = sysRelationEntryForm.getFdPersonIds();
			if (null != personIds && !"".equals(personIds)) {
				JSONObject personEntry = new JSONObject();
				personEntry.accumulate("fdPersonIds",sysRelationEntryForm.getFdPersonIds());
				personEntry.accumulate("fdPersonNames",sysRelationEntryForm.getFdPersonNames());
				out.println("relationPersons = " +personEntry.toString()+";");
	        	out.println("relationEntrys['" + SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())+"'].relationPersons = relationPersons;");
			}

		%>
	</c:forEach>
	var rela_params = {
			'varName':'rela_opt',
			'rela.mainform.name':'${JsParam.formName}',
			'rela.button.ok':'<bean:message key="button.ok"/>',
			'rela.button.cancel':'<bean:message key="button.cancel"/>',
			'rela.setting.title':'<bean:message key="title.sysRelationMain.setting" bundle="sys-relation"/>'
	};
	if (typeof relaCheckData === 'function') {
		// 页面加载时候，检查数据，需要业务端自己实现具体的方法
		relaCheckData('edit');
	}
</script>
<%-- #112391 经过几个星期验证，这个样式好像没有用，暂去掉。--%>
<%-- <link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/relation/import/resource/view.css" /> --%>
<script type="text/javascript">
	Com_IncludeFile("rela.js","${KMSS_Parameter_ContextPath}sys/relation/import/resource/","js",true);
</script>
<script type="text/javascript">
	var isRightModel = ${param.approveType eq 'right'};
	var needTitle = ${param.needTitle eq 'true'};
	var relationOpt = new RelationOpt('${currModelName}','','',rela_params);
</script>
<div style="display: none;">
	<input type="hidden" name="sysRelationMainForm.fdDesSubject"  value="<c:out value='${sysRelationMainForm.fdDesSubject}' />">
	<input type="hidden" name="sysRelationMainForm.fdDesContent"  value="<c:out value='${sysRelationMainForm.fdDesContent}' />">

	<input type="hidden" name="sysRelationMainForm.fdId" value="<c:out value='${sysRelationMainForm.fdId}' />" />
	<input type="hidden" name="sysRelationMainForm.fdKey" value="<c:out value='${param.fdKey}' />"/>
	<input type="hidden" name="sysRelationMainForm.fdModelName" value="<c:out value='${currModelName}' />"/>
	<input type="hidden" name="sysRelationMainForm.fdModelId" value="<c:out value='${currModelId}' />"/>
	<input type="hidden" name="sysRelationMainForm.fdParameter" value="<c:out value='${sysRelationMainForm.fdParameter}' />"/>
</div>

<ui:event event="layoutDone">
	seajs.use(['lui/jquery'],function($) {
		if($.isEmptyObject(relationEntrys) != true) {
			window['rela_opt'].refreshConfig();
		} else {
			var _self = this;
			/*由于draw中layout处理为异步，此处用定时器做组件隐藏处理*/
			var interNum = window.setInterval(function(){
				/*#140131 如果组件有内容不隐藏*/
				if(_self.isDrawed && (!_self.contents || _self.contents.length <= 0)){
					_self.element.hide();
					window.clearInterval(interNum);
				}
			},50);
		}
	});
</ui:event>
<ui:event event="load" args="evt">
	seajs.use(['lui/jquery'],function($){
		var array = evt.source.element.find(".lui_dataview_classic_tile_nolink");
		if(array.length>0){
			array.each(function(index, obj){
				var target = $(obj);
				var text = target.html().replace(/(\r\n)|(\n)/g,'<br>');
				target.html(text);
			});
	    }else{
	    	return;
	    } 
    });
</ui:event>
