<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("data.js|dialog.js");
	Com_IncludeFile("tag.js","${LUI_ContextPath}/sys/tag/resource/js/","js",true);
</script>
<c:set var="tag_MainForm" value="${requestScope[param.formName]}"/>
<c:set var="sysTagMainForm" value="${tag_MainForm.sysTagMainForm}"/>
<script type="text/javascript">
	var tag_params = {
			"model":"edit",
			<%-- 填写标签的时候弹出的热门标签的查询条件使用的字段，
				 相同条件的才显示（比如新闻用"fdTemplateId;fdDepartmentId",所属分类和所属部门一样的标签才会被显示出来，
				 在tag.js中会获取页面的fdTemplateId和fdDepartmentId的值，用；隔开当做查询条件的值 --%>
			"fdQueryCondition":"zoneQueryCondition",
			"tag_msg1":"<bean:message bundle='sys-tag' key='sysTagMain.message.1'/>",
			"tag_msg2":"<bean:message bundle='sys-tag' key='sysTagMain.message.2'/>",
			"tree_title":"<bean:message key='sysTagTag.tree' bundle='sys-tag'/>",
			};
	if(window.tag_opt==null){
		window.tag_opt = new TagOpt('${tag_modelName}','${sysTagMainForm.fdModelId}','${JsParam.fdKey}',
					tag_params);
	}
	Com_AddEventListener(window,'load',function(){
			window.tag_opt.onload(); 
		});
	// 这里只显示公有标签
	(Com_Parameter.top || window.top).addTagSign = 1;
</script>
<html:form action="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do" styleId="tagsForm">
	<input type="hidden" name="zoneQueryCondition" value="sysZonePersonInfo"/>
	<input type="hidden" name="sysTagMainForm.fdId" value="${sysTagMainForm.fdId}"/>   
	<input type="hidden" name="sysTagMainForm.fdKey" value="${JsParam.fdKey}"/>
	<input type="hidden" name="sysTagMainForm.fdModelName"/>
	<input type="hidden" name="sysTagMainForm.fdModelId" value="${tag_MainForm.fdId}"/> 
	<input type="hidden" name="sysTagMainForm.fdQueryCondition" value="${JsParam.fdQueryCondition}"/> 
	<input type="hidden" name="sysTagMainForm.fdTagIds" />
	<input type="hidden" name="fdId" value="${tag_MainForm.fdId}" />
		<div class="inputselectsgl"  style="width:98%">
			<div class="input">
				<html:text property="sysTagMainForm.fdTagNames" styleClass=""/> 
			</div>
			<div class="selectitem" id="tag_selectItem"></div>		
		</div>
		<div class="tag_prompt_area" id="id_application_div">
            <p><bean:message bundle="sys-tag" key="sysTagMain.message.0"/></p>
            <p id="hot_id"><bean:message bundle="sys-tag" key="sysTagMain.message.1"/></p>
            <p id="used_id"><bean:message bundle="sys-tag" key="sysTagMain.message.2"/></p>
        </div>
        <div style="text-align: center;margin-top: 8px">
        	<ui:button text="${lfn:message('button.save') }" order="2" onclick="TagformSubmit();"></ui:button>
        </div>
</html:form>	
<script>
	
	function TagformSubmit() {
		 if("${JsParam.isAjax}" == "false") {
			 Com_Submit($("#tagsForm")[0], "updateTag");
		 } else {
			 var tagsForm = $("#tagsForm");
			 var hiddenList = tagsForm.find("input[type='hidden']");
			 var params = {}
			 for(var i=0;i<hiddenList.length;i++){
			    var hiddenDom = hiddenList[i];
			    var attrName = hiddenDom.name;
			    var attrValue = hiddenDom.value;
			    params[attrName]=attrValue;
			 }
			 var tagConent = $.trim(tagsForm.find("input[type='text'][name='sysTagMainForm.fdTagNames']").val());
			 params["sysTagMainForm.fdTagNames"] = base64Encode(tagConent);
			 seajs.use(['lui/jquery','lui/dialog', 'lui/util/env'], function($, dialog,env){
				 $.ajax({
						url : env.fn.formatUrl("/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=updateTag"),
						async: false, 
						cache: false,
						data: params,
						type : "POST",
						dataType : "json",
						success : function(data) {
							dialog.success("${lfn:message('return.optSuccess')}");
						},
						error : function() {
							dialog.failure("${lfn:message('return.optFailure')}");
						}
					  });
			 });	
		 }
	}
</script>


