<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.relation.forms.SysRelationConditionForm"%>
<%@ page import="com.landray.kmss.sys.relation.forms.SysRelationStaticNewForm"%>
<%@ page import="com.landray.kmss.sys.relation.forms.SysRelationEntryForm"%>
<%@ page import="com.landray.kmss.sys.relation.util.SysRelationUtil"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="java.util.List"%>

<c:if test="${param.formName!=null}">
	<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request" />
	<c:set var="currModelId" value="${mainModelForm.fdId}" scope="request" />
	<c:set var="currModelName" value="${mainModelForm.modelClass.name}" scope="request" />

	<c:if test="${mainModelForm.method_GET=='add' || mainModelForm.method_GET=='edit'}">
		<c:set var="sysRelationMainForm" value="${mainModelForm.sysRelationMainForm}" scope="request" />

		<script type="text/javascript">
			var relationEntrys = {};
			<c:forEach items="${sysRelationMainForm.sysRelationEntryFormList}" varStatus="vstatus" var="sysRelationEntryForm">
				var relationConditions = {};
				var staticInfos = {};
				<%JSONObject relationEntry = new JSONObject();
						SysRelationEntryForm sysRelationEntryForm = (SysRelationEntryForm) pageContext.getAttribute("sysRelationEntryForm");
						relationEntry.accumulate("fdId", sysRelationEntryForm.getFdId());
						relationEntry.accumulate("fdType", sysRelationEntryForm.getFdType());
						relationEntry.accumulate("fdModuleName", sysRelationEntryForm.getFdModuleName());
						relationEntry.accumulate("fdModuleModelName", sysRelationEntryForm.getFdModuleModelName());
						relationEntry.accumulate("fdOrderBy", sysRelationEntryForm.getFdOrderBy());
						relationEntry.accumulate("fdOrderByName", sysRelationEntryForm.getFdOrderByName());
						relationEntry.accumulate("fdPageSize", sysRelationEntryForm.getFdPageSize());
						relationEntry.accumulate("fdParameter", sysRelationEntryForm.getFdParameter());
						relationEntry.accumulate("fdKeyWord", sysRelationEntryForm.getFdKeyWord());
						relationEntry.accumulate("docCreatorId", sysRelationEntryForm.getDocCreatorId());
						relationEntry.accumulate("docCreatorName", sysRelationEntryForm.getDocCreatorName());
						relationEntry.accumulate("fdFromCreateTime", sysRelationEntryForm.getFdFromCreateTime());
						relationEntry.accumulate("fdToCreateTime", sysRelationEntryForm.getFdToCreateTime());
						relationEntry.accumulate("fdSearchScope", sysRelationEntryForm.getFdSearchScope());
						relationEntry.accumulate("fdOtherUrl", sysRelationEntryForm.getOtherUrlNoPattern());
						relationEntry.accumulate("fdChartId", sysRelationEntryForm.getFdChartId());
						relationEntry.accumulate("fdChartName", sysRelationEntryForm.getFdChartName());
						relationEntry.accumulate("fdChartType", sysRelationEntryForm.getFdChartType());
						relationEntry.accumulate("fdCCType", sysRelationEntryForm.getFdCCType());
						relationEntry.accumulate("fdDynamicData", sysRelationEntryForm.getFdDynamicData());
						relationEntry.accumulate("fdIndex", sysRelationEntryForm.getFdIndex());
						relationEntry.accumulate("docStatus",sysRelationEntryForm.getDocStatus());
						relationEntry.accumulate("fdDiffusionAuth",sysRelationEntryForm.getFdDiffusionAuth());
						relationEntry.accumulate("fdIsTemplate",sysRelationEntryForm.getFdIsTemplate());
						out.println("relationEntrys['" + SysRelationUtil .replaceJsonQuotes(sysRelationEntryForm .getFdId()) + "'] = " + relationEntry.toString() + ";");
						List conditionList = sysRelationEntryForm.getSysRelationConditionFormList();
						if (!conditionList.isEmpty()) {
							for (int m = 0; m < conditionList.size(); m++) {
								JSONObject conditionEntry = new JSONObject();
								SysRelationConditionForm sysRelationConditionForm = (SysRelationConditionForm) conditionList.get(m);
								conditionEntry.accumulate("fdId", sysRelationConditionForm.getFdId());
								conditionEntry.accumulate("fdItemName", sysRelationConditionForm.getFdItemName());
								conditionEntry.accumulate("fdParameter1", sysRelationConditionForm.getFdParameter1());
								conditionEntry.accumulate("fdParameter2", sysRelationConditionForm.getFdParameter2());
								conditionEntry.accumulate("fdParameter3", sysRelationConditionForm.getFdParameter3());
								conditionEntry.accumulate("fdBlurSearch", sysRelationConditionForm.getFdBlurSearch());
								conditionEntry.accumulate("fdVarName", sysRelationConditionForm.getFdVarName());
								out.println("relationConditions['" + SysRelationUtil.replaceJsonQuotes(sysRelationConditionForm.getFdItemName()) + "'] = " + conditionEntry.toString() + ";");
							}
							out.println("relationEntrys['" + SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId()) + "'].relationConditions = relationConditions;");
						}

						List staticInfoList = sysRelationEntryForm.getSysRelationStaticNewFormList();
						if (!staticInfoList.isEmpty()) {
							JSONObject staticEntry = new JSONObject();
							for (int i = 0; i < staticInfoList.size(); i++) {
								JSONObject staticMsg = new JSONObject();
								SysRelationStaticNewForm sysRelationStaticNewForm = (SysRelationStaticNewForm) staticInfoList.get(i);
								staticMsg.accumulate("fdId", sysRelationStaticNewForm.getFdId());
								staticMsg.accumulate("fdSourceId", sysRelationStaticNewForm.getFdSourceId());
								staticMsg.accumulate("fdSourceModelName", sysRelationStaticNewForm.getFdSourceModelName());
								staticMsg.accumulate("fdSourceDocSubject", sysRelationStaticNewForm.getFdSourceDocSubject());
								staticMsg.accumulate("fdRelatedId", sysRelationStaticNewForm.getFdRelatedId());
								staticMsg.accumulate("fdRelatedModelName", sysRelationStaticNewForm.getFdRelatedModelName());
								staticMsg.accumulate("fdRelatedUrl", sysRelationStaticNewForm.getFdRelatedUrl());
								staticMsg.accumulate("fdRelatedName", sysRelationStaticNewForm.getFdRelatedName());
								staticMsg.accumulate("fdRelatedType", sysRelationStaticNewForm.getFdRelatedType());
								staticMsg.accumulate("fdEntryId", sysRelationStaticNewForm.getFdEntryId());
								String fdIndex = sysRelationStaticNewForm.getFdIndex();
								if (StringUtil.isNull(fdIndex)) {
									fdIndex = i + "";
								}
								staticMsg.accumulate("fdIndex", fdIndex);
								staticEntry.accumulate(fdIndex, staticMsg);
							}
							out.println("staticInfos['" + sysRelationEntryForm.getFdId() + "'] = " + staticEntry.toString() + ";");

							out.println("relationEntrys['" + SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId()) + "'].staticInfos = staticInfos;");
						}%>
			</c:forEach>
		</script>
		
		<input type="hidden" name="sysRelationMainForm.fdId" value="<c:out value='${sysRelationMainForm.fdId}' />" />
		<input type="hidden" name="sysRelationMainForm.fdKey" value="<c:out value='${param.fdKey}' />"/>
		<input type="hidden" name="sysRelationMainForm.fdModelName" value="<c:out value='${currModelName}' />"/>
		<input type="hidden" name="sysRelationMainForm.fdModelId" value="<c:out value='${currModelId}' />"/>
		<input type="hidden" name="sysRelationMainForm.fdParameter" value="<c:out value='${sysRelationMainForm.fdParameter}' />"/>
		
		<script type="text/javascript">
			if(relationEntrys != null) {
				var formObj = $("form[name=${param.formName}]"); 
				var i = 0;
				var entryPrefix = 'sysRelationMainForm.sysRelationEntryFormList';
				for(var tmpKey in relationEntrys){
					var entry =  relationEntrys[tmpKey];
					for(var tmpField in entry){
						if(tmpField == 'relationConditions'){
							var count = 0; 
							var conditions = entry.relationConditions;
							for(var condition in conditions){
								var conditionPrefix = entryPrefix +"[" + i + "].sysRelationConditionFormList";
								for(var condProp in conditions[condition]){
									var condFileName = conditionPrefix+ "[" + count + "]." + condProp;
									var condFiledObj = $("input[name='"+ condFileName +"']");
									if(condFiledObj.length<=0){
										condFiledObj = $("<input type='hidden' name='" + condFileName+ "'/>");
									}
									condFiledObj.val(conditions[condition][condProp]);
									condFiledObj.appendTo(formObj);
								}
								count++;
							}
						}else if(tmpField == 'staticInfos'){
							var cnt = 0;
							var staticInfos = entry.staticInfos;
							for(var staticInfo in staticInfos){
								var staticPrefix = entryPrefix +"[" + i + "].sysRelationStaticNewFormList";
								for(var index in staticInfos[staticInfo]){
									for(var condProp in staticInfos[staticInfo][index]){
										var condFileName = staticPrefix+ "[" + cnt + "]." + condProp;
										var condFiledObj = $("input[name='"+ condFileName +"']");
										if(condFiledObj.length<=0){
											condFiledObj = $("<input type='hidden' name='" + condFileName+ "'/>");
										}
										var staticVal = staticInfos[staticInfo][index][condProp];
										//源文档名为空时
										if("fdSourceDocSubject" == condProp && (staticVal==''||staticVal==null)){
											staticVal = $("input[name='docSubject']").val();
										}
										condFiledObj.val(staticVal);
										condFiledObj.appendTo(formObj);
									}
									cnt++;
								}
							}
							
						}else{
							var filedName = entryPrefix + "[" + i + "]." + tmpField;
							var filedObj = $("input[name='"+filedName+"']");
							if(filedObj.length<=0){
								filedObj = $("<input type='hidden' name='" + filedName + "'/>");
							}
							if("fdIndex" == tmpField){
								filedObj.val(i);
							}else{
								filedObj.val(entry[tmpField]);
							}
							filedObj.appendTo(formObj);
						}
					}
					i++;
				}
			}
		</script>
	</c:if>

</c:if>
