<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<style>
.thirdCtripXformTreeTip{
	position:absolute;
	font-famliy:Microsoft YaHei,Geneva,"sans-serif",SimSun;
	right:0px;
	top:10px;
	font-size:13px;
	color:blue;
	width:150px;
	float:right;
}
</style>
<script>
	if(window.showModalDialog){
		dialogObject = window.dialogArguments;
	}else{
		dialogObject = opener.Com_Parameter.Dialog;
	}
	Com_AddEventListener(window, "load", function(){
		var showType = dialogObject.varInfo.type;
		var showText = '';
		if(showType && showType != ''){
			if(showType.indexOf("|") > -1){
				var types = showType.split("|");
				for(var i = 0;i < types.length;i++){
					if(i > 0){
						showText += "、";	
					}					
					showText += getText(types[i]);
				}
			}else{
				showText = getText(showType);
			}
			if(document.getElementById("thirdCtripXformTreeTip")){
				document.getElementById("thirdCtripXformTreeTip").innerHTML = '<bean:message bundle="third-ctrip" key="third.Ctrip.XformTreeTip"/>' + showText + "!";
			}			
		}
	});
	
	function getText(type){
		var text = "";
		switch(type){
			case 'inputCheckbox': text = '<bean:message bundle="third-ctrip" key="Designer_Lang.bookTicket_checkboxControl"/>';break;
			case 'address': text = '<bean:message bundle="third-ctrip" key="Designer_Lang.bookTicket_addressControl"/>';break;
			case 'new_address': text = '<bean:message bundle="third-ctrip" key="Designer_Lang.bookTicket_newAddressControl"/>';break;
			case 'datetime': text = '<bean:message bundle="third-ctrip" key="Designer_Lang.bookTicket_dateControl"/>';break;
			case 'inputRadio': text = '<bean:message bundle="third-ctrip" key="Designer_Lang.bookTicket_radioControl"/>';break;
			case 'select': text = '<bean:message bundle="third-ctrip" key="Designer_Lang.bookTicket_selectControl"/>';break;
			case 'inputText': text = '<bean:message bundle="third-ctrip" key="Designer_Lang.bookTicket_inputTextControl"/>';break;
			case 'textarea': text = '<bean:message bundle="third-ctrip" key="Designer_Lang.bookTicket_textAreaControl"/>';break;
		}
		return text;
	}
	
	function ok(){
		dialogObject.previousSibling.value = dialogObject.rtnData.text;
		dialogObject.previousSiblingHidden.value = dialogObject.rtnData.value;
		window.close();
	}
	
	function cancel(){
		dialogObject.rtnData = {};
		window.close();
	}

	function removeValue(){
		dialogObject.previousSibling.value = '';
		dialogObject.previousSiblingHidden.value = '';
		window.close();
	}

//添加关闭事件
Com_AddEventListener(window, "beforeunload", function(){dialogObject.AfterShow();});
</script>
<div class="thirdCtripXformTreeTip" id ="thirdCtripXformTreeTip">

</div>
<table cellpadding=0 cellspacing=0 style="border-collapse:collapse;border: 0px #303030 solid; width: 100%">
	<tr>
		<td valign="top">
			<iframe width=100% height="430px;" frameborder=0 scrolling="auto" src='thirdCtripXformTemplateAllAttr_tree.jsp'></iframe>
		</td>
	</tr>
	<tr>
		<td align=center>
			<br>
       		<input type=button class="btnopt" value="<bean:message key="button.ok"/>" onclick="ok();">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="btnopt" value="<bean:message key="button.cancel"/>" onClick="cancel();">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="btnopt" value="<bean:message key="button.cancelSelect"/>" onClick="removeValue();">
		</td>
	</tr>
</table>