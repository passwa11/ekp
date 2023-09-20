<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script language="JavaScript">
Com_IncludeFile("dialog.js");

function validateTagEmpty() {
	if(${param.type == 'main'}){
		var fdMergerTargetName = document.getElementsByName("fdMergerTargetName")[0];
		if(fdMergerTargetName.value=="") {
			alert("<bean:message key="sysTagTags.mergerTag.msg.mainTag" bundle="sys-tag"/>");
			return false;
		}
	}
	if(${param.type == 'alias'}){
		var fdMergerTagNames = document.getElementsByName("fdMergerTagNames")[0];
		if(fdMergerTagNames.value=="") {
			seajs.use(['lui/dialog'],function(dialog) {
				dialog.alert("<bean:message key="sysTagTags.mergerTag.msg.aliasTag" bundle="sys-tag"/>");
			});
			return false;
		}
	}
	return true;
}
function dialog_tag(){
	//list页面选择标签合并到主标签
	if(${param.type == 'main'}){
		//排除已选标签
		var fdMergerTagIds = "${JsParam.fdMergerTagIds}".split(",");
		var mergerTagsIds = '';
		for(var i=0;i<fdMergerTagIds.length;i++){
			if(i == fdMergerTagIds.length-1){
				mergerTagsIds += '\''+fdMergerTagIds[i]+'\'';
			}else{
				mergerTagsIds += '\''+fdMergerTagIds[i]+'\''+',';
			}
		}
		
		var url = "/sys/tag/import/addTag.jsp?mulSelect=false&mergerSign=main&fdMergerTagIds="+fdMergerTagIds;
		seajs.use(['lui/dialog', 'lui/util/env','lang!sys-tag','lang!sys-ui'],function(dialog, env,lang,ui_lang) {
			dialog.iframe(url, lang["sysTag.choiceTag"], null, {
				width : 900,
				height : 550,
				buttons : [
					{
						name : ui_lang["ui.dialog.button.ok"],
						value : true,
						focus : true,
						fn : function(value,_dialog) {
							if(_dialog.frame && _dialog.frame.length > 0){
								var _frame = _dialog.frame[0];
								var contentWindow = $(_frame).find("iframe")[0].contentWindow;
								if(contentWindow.onSubmit()) {
									var datas = contentWindow.onSubmit().slice(0);
									if(datas.length>=0){
										selectWord_merger_main(datas);	

										setTimeout(function() {
											_dialog.hide(value);
										}, 200);
										
									}
								}
							}
							
						}
					}
					,{
						name :ui_lang["ui.dialog.button.cancel"],
						value : false,
						styleClass : 'lui_toolbar_btn_gray',
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					}
				]	
			});
		});
	}
	//view页面选择别名标签合并到当前标签
	if(${param.type == 'alias'}){
		var fdTagId = "${JsParam.fdMergerTargetId}";
		var top = Com_Parameter.top || window.top;
		top.window.selectTagNames = $("input[name='fdMergerTagNames']").val();
		//排除当前标签
		//Dialog_List(true, 'fdMergerTagIds', 'fdMergerTagNames', ';', Data_GetBeanNameOfFindPage('sysTagTagsService', 'fdId:fdName',1,100,'sysTagTags.fdMainTagId is null  and sysTagTags.fdStatus = 1 and sysTagTags.fdId != \'${JsParam.fdMergerTargetId}\'','sysTagTags.docCreateTime desc'),null,null,null,null,'<bean:message key="table.sysTagTags" bundle="sys-tag"/>');
		var url = "/sys/tag/import/addTag.jsp?mulSelect=true&mergerSign=alias&fdTagId="+fdTagId;
		seajs.use(['lui/dialog', 'lui/util/env','lang!sys-tag','lang!sys-ui'],function(dialog, env,lang,ui_lang) {
			dialog.iframe(url, lang["sysTag.choiceTag"], null, {
				width : 900,
				height : 550,
				buttons : [
					{
						name : ui_lang["ui.dialog.button.ok"],
						value : true,
						focus : true,
						fn : function(value,_dialog) {
							if(_dialog.frame && _dialog.frame.length > 0){
								var _frame = _dialog.frame[0];
								var contentWindow = $(_frame).find("iframe")[0].contentWindow;
								if(contentWindow.onSubmit()) {
									var datas = contentWindow.onSubmit().slice(0);
									if(datas.length>=0){
										selectWord_merger(datas);	

										setTimeout(function() {
											_dialog.hide(value);
										}, 200);
										
									}
								}
							}
							
						}
					}
					,{
						name :ui_lang["ui.dialog.button.cancel"],
						value : false,
						styleClass : 'lui_toolbar_btn_gray',
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					}
				]	
			});
		});
	}
}

function selectWord_merger(datas) {
	if (datas != null && typeof (datas) != "undefined") {
		var item;
		var str="";
		for(var i=0; i<datas.length; i++){
			item=datas[i];
			str=str+item.fdName+";";
		}
		str = str.substring(0,str.length-1);
		$("input[name='fdMergerTagNames']").val(str);
	}
}

function selectWord_merger_main(datas) {
	if (datas != null && typeof (datas) != "undefined") {
		var item;
		var str="";
		for(var i=0; i<datas.length; i++){
			item=datas[i];
			str=str+item.fdName+";";
		}
		str = str.substring(0,str.length-1);
		$("input[name='fdMergerTargetName']").val(str);
	}
}

window.onload = function(){
	dialog_tag();
}
</script>

<html:form action="/sys/tag/sys_tag_tags/sysTagTags.do">
	<div id="optBarDiv">
		<input type=button
			   value="<bean:message key="button.save"/>"
			   onclick="if(validateTagEmpty())Com_Submit(document.sysTagTagsForm, 'saveMergerTags');">
		<input type="button" value="<bean:message key="button.close"/>"
			   onclick="Com_CloseWindow();">
	</div>

	<p class="txttitle" style="color:#3e9ece"><bean:message bundle="sys-tag" key="sysTagTags.mergerTag.title" /></p>
	<center>
	<div>
		<div>
			<c:if test="${param.type == 'main' }">
				<span>
					<bean:message key="sysTagTags.mergerTag.to" bundle="sys-tag"/>
				</span>
				<span>
					<input type="text" property="fdMergerTargetName" name="fdMergerTargetName" style="width:50%;" class="inputsgl" readonly>
					<span class="txtstrong">*</span>&nbsp;&nbsp;
					<a href="#" onclick="dialog_tag();">
						<bean:message key="dialog.selectOther" />
					</a>	
					<html:hidden property="fdMergerTagIds" value="${HtmlParam.fdMergerTagIds}"/>				
				</span>
			</c:if>
			<c:if test="${param.type == 'alias' }">
				<span>
					<bean:message key="sysTagTags.mergerTag.merger" bundle="sys-tag"/>
				</span>
				<span>
					<input type="text" property="fdMergerTagNames" name="fdMergerTagNames" style="width:50%;" class="inputsgl" readonly>
					<span class="txtstrong">*</span>&nbsp;&nbsp;
					<a href="#" onclick="dialog_tag();">
						<bean:message key="dialog.selectOther" />
					</a>	
					<html:hidden property="fdMergerTargetId" value="${HtmlParam.fdMergerTargetId}"/>				
				</span>
			</c:if>
		</div>
	</div>
</center>
	<html:hidden property="method_GET" />
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
