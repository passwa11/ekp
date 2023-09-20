<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>

<script>
	Com_IncludeFile("dialog.js");
</script>
<script>
function callBackTagsAction(rtnVal){
	if(rtnVal.data.length >1) {
		$("input[name='fdIsSpecial']").each(function () {
		   $(this).attr("disabled", false);
		})
	}
	else if (rtnVal.data.length ==1){
			if(rtnVal.data[0].isSpecial=="0"){
				$('input[id="fdIsSpecial1"]').prop("checked", "checked");
				$('input[id="fdIsSpecial2"]').remove("checked");
			}else{
				$('input[id="fdIsSpecial2"]').prop("checked", "checked");
				$('input[id="fdIsSpecial1"]').remove("checked");
			}
	}else{
		$('input[id="fdIsSpecial1"]').prop("checked", "checked");
		$('input[id="fdIsSpecial2"]').remove("checked");
	};
}
</script>
<html:form action="/sys/tag/sys_tag_tags/sysTagTags.do" onsubmit="return validateSysTagTagsForm(this);">
<div id="optBarDiv">
	<c:if test="${sysTagTagsForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysTagTagsForm, 'update');">
	</c:if>
	<c:if test="${sysTagTagsForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysTagTagsForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.sysTagTagsForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message  bundle="sys-tag" key="table.sysTagTags"/></p>

<center>
<table class="tb_normal" width=95%>
		<html:hidden property="fdId"/>
		<html:hidden property="fdIsPrivate"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-tag" key="sysTagTags.fdName"/>
		</td><td width=35%>
			<xform:text property="fdName" validators="checkName notNull checkillegal"/>
			<span class="txtstrong">*</span>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-tag" key="sysTagTags.fdCategoryId"/>
		</td>
		<td width=35% onclick="choiceCategory()">
			<html:hidden property="fdCategoryId"/>
			<xform:text property="fdCategoryName"   style="inputsgl"  showStatus="edit" required="true"/>
			<a href="#">
			<bean:message key="dialog.selectOther" /></a>
		</td>
	</tr>
	<tr> 
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-tag" key="sysTagCategory.fdIsSpecial"/>
		</td><td colspan=3>
			<input type="radio" name="fdIsSpecial" id="fdIsSpecial1" value="0"  checked/>
			<bean:message bundle="sys-tag" key="sysTagCategory.isSpecial.no"/>
			
			<input type="radio" name="fdIsSpecial" id="fdIsSpecial2" value="1"  />
			<bean:message bundle="sys-tag" key="sysTagCategory.isSpecial.yes"/>
		</td>
	</tr> 
	<c:if test="">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message  bundle="sys-tag" key="table.sysOrgElement"/>
			</td><td width=35%>
				${sysTagTagsForm.docCreatorName}	
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message  bundle="sys-tag" key="sysTagTags.docCreateTime"/>
			</td><td width=35%>
				${sysTagTagsForm.docCreateTime }
			</td>
		</tr>
	</c:if>
</table>
</center>
<html:hidden property="method_GET"/>
<script>
	function choiceCategory(){
		var top = Com_Parameter.top || window.top;
		top.window.fdCategoryNames = $("input[name='fdCategoryName']").val();
		top.window.fdCategoryIds = $("input[name='fdCategoryId']").val();
		var url = "/sys/tag/sys_tag_tags/sysTagTags_choice_category.jsp?mulSelect=true";
		seajs.use(['lui/dialog', 'lui/util/env','lang!sys-tag','lang!sys-ui'],function(dialog, env,lang,ui_lang) {
			dialog.iframe(url, lang["sysTag.choiceTagCategory"], null, {
				width : 600,
				height : 550,
				buttons : [{
					name : ui_lang["ui.dialog.button.ok"],
					value : true,
					focus : true,
					fn : function(value,_dialog) {
						if(_dialog.frame && _dialog.frame.length > 0){
							var _frame = _dialog.frame[0];
							var contentWindow = $(_frame).find("iframe")[0].contentWindow;
							if(contentWindow.onSubmit()) {
								var datas = contentWindow.onSubmit().slice(0);
								if(datas.length>0){
									selectWordCallBack(datas);	
	
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
				}]	
			});
		});
	}
	function selectWordCallBack(datas){
		if (datas != null && typeof (datas) != "undefined") {
			var item;
			var categoryId="";
			var categoryName="";
			for(var i=0; i<datas.length; i++){
				item=datas[i];
				categoryId += item.fdId+";";
				categoryName += item.fdName+";";
			}
			categoryId = categoryId.substring(0,categoryId.length-1);
			categoryName = categoryName.substring(0,categoryName.length-1);
			$("input[name='fdCategoryId']").val(categoryId);
			$("input[name='fdCategoryName']").val(categoryName);
		}
	}
	$KMSSValidation();
	Com_IncludeFile("jquery.js|dialog.js|data.js");
	var _validation = $KMSSValidation(document.forms['sysTagTagsForm']);
	var validators = {
			'checkName' : 
			{
				error : "<bean:message key='sysTagTags.notAllowSemicolon' bundle='sys-tag'/>",
				test:function(v,e,o) {
					v = $.trim(v);
					if ( v.indexOf(";") == -1 && v.indexOf("；") == -1 ) {//如果存在;或者； 返回false
						return true;
					}else {
						return false;
					}
				}
			},
			'notNull' : 
			{
				error : "<bean:message key='sysTagTags.notAllowNull' bundle='sys-tag'/>",
				test:function(v,e,o) {
				
					v = $.trim(v);
					if (v.length==0) {
						return false;

					} else {
						return true;
					}
				}
			},
			'checkillegal' :
			{
				error : "存在非法字符'\\'",
				test:function(v,e,o){
					v = $.trim(v);
					if ( v.indexOf("\\") == -1 && v.indexOf("\\") == -1 ) {
						return true;
					}else {
						return false;
					}
				}
			}
		};
	_validation.addValidators(validators);
</script>
</html:form>

<html:javascript formName="sysTagTagsForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<%@ include file="/resource/jsp/edit_down.jsp"%>