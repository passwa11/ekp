<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script language="JavaScript">
Com_IncludeFile("dialog.js");

function validateEmpty() {
	var fdRemoveTagNames = document.getElementsByName("fdRemoveTagNames")[0];
	if(fdRemoveTagNames.value=="") {
		seajs.use(['lui/dialog'],function(dialog) {
			dialog.alert("<bean:message key="sysTagCategory.removeTags.msg.tags" bundle="sys-tag"/>");
		});
		return false;
	}
	return true;
}

function dialog_tag(){
	var top = Com_Parameter.top || window.top;
	top.window.selectTagNames = $("input[name='fdRemoveTagNames']").val();
	var url = "/sys/tag/import/addTag.jsp?mulSelect=true&catRemoveSign=1&fdCategoryId=${JsParam.fdCategoryId}";
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
									after_cat_tag_remove(datas);	

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

function after_cat_tag_remove(datas) {
	if (datas != null && typeof (datas) != "undefined") {
		var item;
		var str="";
		for(var i=0; i<datas.length; i++){
			item=datas[i];
			str=str+item.fdName+";";
		}
		str = str.substring(0,str.length-1);
		$("input[name='fdRemoveTagNames']").val(str);
	}
}
window.onload = function(){
	dialog_tag();
}

</script>

<html:form action="/sys/tag/sys_tag_category/sysTagCategory.do">
	<div id="optBarDiv">
		<input type=button
			   value="<bean:message key="button.save"/>"
			   onclick="if(validateEmpty())Com_Submit(document.sysTagCategoryForm, 'saveRemoveTags');">
		<input type="button" value="<bean:message key="button.close"/>"
			   onclick="Com_CloseWindow();">
	</div>

	<p class="txttitle" style="color:#3e9ece"><bean:message bundle="sys-tag" key="sysTagCategory.removeTags.title" /></p>
	<center>
	<div>
		<div>
			<bean:message key="sysTagCategory.removeTags.tags" bundle="sys-tag"/>
		
			<input type="text" property="fdRemoveTagNames" name="fdRemoveTagNames" class="inputsgl" readonly>
			<span class="txtstrong">*</span>&nbsp;&nbsp;
			<a href="#" onclick="dialog_tag();">
				<bean:message key="dialog.selectOther" />
			</a>
		</div>

	</<div>
	</center>
	<html:hidden property="fdCategoryId" value="${HtmlParam.fdCategoryId}"/>
	<html:hidden property="method_GET" />
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
