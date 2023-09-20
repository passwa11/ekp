<%@page import="com.landray.kmss.sys.person.interfaces.LinkInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
    <link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/ui/extend/theme/default/style/dialog.css"/>
<template:include ref="default.simple">
	<template:replace name="title"><bean:message bundle="sys-person" key="sysPersonLink.selectLink"/></template:replace>
	<template:replace name="body">
	<script>
		seajs.use(['theme!form']);
		 Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/mobile/fssc_mobile_link/", 'js', true);
	</script>

	<div id="selectedBean" data-lui-type="lui/selected/multi_selected!Selected" style="width: 95%;margin: 10px auto;">
		<script type="lui/event" data-lui-event="changed" data-lui-args="evt">
 			$('#selectedBean').hide();
		</script>
	</div>
	
	<script>
	
		function refreshCheckbox() {
			var vals = LUI('selectedBean').getValues();
			LUI.$('[name="List_Selected"]').each(function() {
				for (var i = 0; i < vals.length; i ++) {
					if (vals[i].id == this.value) {
						if (!this.checked)
							this.checked = true;
						return;
					}
				}
				if (this.checked)
					this.checked = false;
			});
		}
		function submitSelected(data) {
			var categoryNames = $("[name='categoryNames']").val();
			var categoryIds = $("[name='categoryIds']").val();
			if(categoryNames && categoryIds && null != categoryNames && null != categoryIds){
				var categoryName = categoryNames.split(";");
				var categoryId = categoryIds.split(";");
				for(var i = 0;i<categoryName.length;i++){
					var url = $("select[name='categories']").find("option:selected").attr ("addUrl");
					url = url.replace('{0}', categoryId[i]);
					selectLink(categoryId[i], categoryName[i], url, '', '');
				}
				
			}
			
			window.$dialog.hide(data || LUI('selectedBean').getValues());
		}
		function selectLink(id, name, url, icon, server, langNames) {
			var data = {
					"id": id,
					"name":name,
					"url":url.replace(/&amp;/g, "&"),
					"icon":icon,
					"server":server,
					"langNames":langNames
			}; 
			/* if (multi == false) {
				submitSelected([data]);
				return;
			}
			if (LUI('selectedBean').hasVal(data)) {
				LUI('selectedBean').removeVal(data);
				return;
			} */
			LUI('selectedBean').addVal(data);
		}
	</script>

	<table id="categoryTable" class="tb_normal " width="100%">
			<tr class="tr_normal_title">
				<td width=30%>
					${lfn:message('sys-person:sysPerson.config.module')}
				</td>
				<td width=50%>
					${lfn:message('sys-person:sysPerson.config.myCategory')}
				</td>
			</tr>
			<tr KMSS_IsContentRow="1" >
				<td>
					<select name="categories" onchange="FixCategoryModelSelector();">
						<option value="">=== <bean:message bundle="sys-person" key="sysPersonLink.selectCategory"/> ===</option>
						<c:forEach items="${rtnCategoryMap }" var="category">
							<option value="${category.value.fdModelName }" addUrl="${category.value.addUrl }" dataSource="${category.value.dataSource }"><c:out value="${category.value.messageKey }" /></option>
						</c:forEach>
					</select>
				</td>
				<td>
					<xform:dialog 
							propertyId="categoryIds" 
							propertyName="categoryNames" 
							style="width:100%"
							textarea="true"
							showStatus="edit"
							idValue="${category.value.fdFollowId}"
							nameValue="${category.value.fdFollowName}">
						selectCategorys(this);
					</xform:dialog>
				</td>
			</tr>
		</table>

	<c:if test="${param.multi != 'false'}">
		<div data-lui-mark="dialog.content.buttons" style="position: fixed;bottom:0;left: 15px;width:95%;background: #fff;padding-top:2px;text-align:center;" class="lui_dialog_common_buttons clearfloat">
			<ui:button onclick="submitSelected();" text="${lfn:message('button.ok') }" />
		</div>
	</c:if>
	</template:replace>
</template:include>
<style>
	.lui_listview_body .lui_listview_columntable_table{
		table-layout: fixed;
	}
</style>
<script type="text/javascript">
seajs.use(['lui/jquery','lui/dialog','fssc/common/resource/js/dialog_common','lui/util/str'], function($, dialog, dialogCommon,strutil){
	window.dialogSelect=function(mul, key, idField, nameField,targetWin,extendParam){
	    targetWin = targetWin||window;
		if(key==null||key==''){
			window.alert("请修改此段代码");
			return;
		}
		if(idField.indexOf('*')>-1 && window.DocListFunc_GetParentByTagName){
			//明细表
			dialogSelectForDetail(mul, key, idField, nameField);
		}else{
			var dialogCfg = formOption.dialogs[key];
    		if(dialogCfg){
    			var params='';
    			if(extendParam){
    				for(var i in extendParam){
    					params+='&'+i+"="+extendParam[i];
    				}
    			}
    			var inputs=getDialogInputs(idField);
    			if(inputs){
    				for(var i=0;i<inputs.length;i++){
    					var argu = inputs[i];
    					var modelVal=$form(argu["value"]).val();
    					if(modelVal==null||modelVal==''){
    						if(argu["required"]=="true"){
    							var errorInfo="当前对话框缺失必须传递的参数【"+argu["label"]+"】，请检查表单数据或相关配置";
    							alert(errorInfo);
    							return;
    						}
    						params+='&'+argu["key"]+'='+formInitData[argu["value"]];
    					}else{
    						params+='&'+argu["key"]+'='+modelVal;
    					}
    				}
    			}
    			params=encodeURI(params);
    			targetWin['__dialog_' + idField + '_dataSource'] = function(){
                    return strutil.variableResolver(dialogCfg.sourceUrl+params ,null);
                }
    			dialogCommon.dialogSelect(dialogCfg.modelName,
    					mul,dialogCfg.sourceUrl+params, null, idField, nameField,null,function(data){
    				var outputs=getDialogOutputs(idField);
    				if(outputs){
						if(data.length==1){
							for(var i=0;i<outputs.length;i++){
								var output=outputs[i];
								$form(output["value"]).val(data[0][output["key"]]);
        					}
						}
    				}
    			});
    		}
		}
	}
});
function getDialogInputs(idField){
	var dialogLinks=formOption.dialogLinks;
	if(dialogLinks==null||dialogLinks.length==0){
		return null;
	}
	for(var i=0;i<dialogLinks.length;i++){
		var dialogLink=dialogLinks[i];
		if(idField==dialogLink.idField){
			return dialogLink.inputs;
		}
	}
	return null;
};

function getDialogOutputs(idField){
	var dialogLinks=formOption.dialogLinks;
	if(dialogLinks==null||dialogLinks.length==0){
		return null;
	}
	for(var i=0;i<dialogLinks.length;i++){
		var dialogLink=dialogLinks[i];
		if(idField==dialogLink.idField){
			return dialogLink.outputs;
		}
	}
	return null;
};
//让已选的失效
function FixCategoryModelSelector() {
	$("[name='categoryNames']").val('');
	$("[name='categoryIds']").val('');
	$("#addUrl").val('');
}


function selectCategorys(sel) {
	var ms = mArray;
	var select = $("select[name='categories']");
	var modelName = select.val();
	console.log("modelName--" + modelName);
	var idfield = $(sel).find('[name^=categoryIds]').attr('name');
	var namefield = $(sel).find('[name^=categoryNames]').attr('name');
	var categoryModelName;
	var categoryType;
	for(var i = 0; i < ms.length; i++) {
		var m = ms[i];
		if(m.modelName == modelName) {
			categoryModelName = m.categoryModelName;
			categoryType = m.categoryType;
		}
	}
	if(categoryModelName && categoryModelName != "") {
		var dataSource=$("select[name='categories']").find("option:selected").attr ("dataSource");
		dialogSelect(false,dataSource,idfield,namefield,null,{source:'mobile'});
	}
}
</script>

<script>
	var data = <% out.print(request.getAttribute("data").toString()); %>;
	var mArray = data.array;
	//已经订阅的分类的modelname数组
	var followedArray = [];
	
</script>
