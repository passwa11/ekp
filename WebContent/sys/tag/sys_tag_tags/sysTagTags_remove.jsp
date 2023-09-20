<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script language="JavaScript">
Com_IncludeFile("dialog.js");

function validateEmpty() {
	var fdRemoveAliasIds = document.getElementsByName("fdRemoveAliasIds")[0];
	if(fdRemoveAliasIds.value=="") {
		seajs.use(['lui/dialog'],function(dialog) {
			dialog.alert("<bean:message key="sysTagTags.removeTag.msg.aliasTag" bundle="sys-tag"/>");
		});
		return false;
	}
	return true;
}

function dialog_aliasTag(){
	//排除当前标签
	var top = Com_Parameter.top || window.top;
	top.window.selectTagNames = $("input[name='fdRemoveTagNames']").val();
	var url = "/sys/tag/sys_tag_cloud/addTagCloud.jsp?mulSelect=true&removeAlieTagSign=1&fdRemoveMainId=${JsParam.fdRemoveMainId}";
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
									after_choice_tag_remove(datas);	

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

function after_choice_tag_remove(datas) {
	if (datas != null && typeof (datas) != "undefined") {
		var item;
		var str="";
		var str1="";
		for(var i=0; i<datas.length; i++){
			item=datas[i];
			str=str+item.fdName+";";
			str1=str1+item.fdId+";";
		}
		str = str.substring(0,str.length-1);
		str1 = str1.substring(0,str1.length-1);
		$("input[name='fdRemoveAliasNames']").val(str);
		$("input[name='fdRemoveAliasIds']").val(str1);
	}
}

window.onload = function(){
	dialog_aliasTag();
}

</script>

<html:form action="/sys/tag/sys_tag_tags/sysTagTags.do">
	<div id="optBarDiv">
		<input type=button
			   value="<bean:message key="button.save"/>"
			   onclick="if(validateEmpty())Com_Submit(document.sysTagTagsForm, 'saveRemoveAliasTags');">
		<input type="button" value="<bean:message key="button.close"/>"
			   onclick="Com_CloseWindow();">
	</div>

	<p class="txttitle" style="color:#3e9ece"><bean:message bundle="sys-tag" key="sysTagTags.removeTag.title" /></p>
	<center>
		<div>
			<div>
				<bean:message key="sysTagTags.removeTag.alias" bundle="sys-tag"/>
				
				<html:hidden property="fdRemoveAliasIds"/>
				<input type="text" name="fdRemoveAliasNames" class="inputsgl" readonly>
				<span class="txtstrong">*</span>&nbsp;&nbsp;
				<a href="#" onclick="dialog_aliasTag();">
					<bean:message key="dialog.selectOther" />
				</a>
			</div>
	
		</div>
	</center>
	<html:hidden property="fdRemoveMainId" value="${HtmlParam.fdRemoveMainId}"/>
	<html:hidden property="method_GET" />
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
