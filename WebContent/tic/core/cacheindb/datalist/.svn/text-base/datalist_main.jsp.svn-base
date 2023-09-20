<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/dialog_htmlhead.jsp" %>
<meta http-equiv="x-ua-compaticle" content="IE=6"/>
<script>
Com_IncludeFile("data.js");
</script>
<script>
var dialogRtnValue = null;
var dialogObject = null;
if(window.showModalDialog)
	dialogObject = window.dialogArguments;
else{
	dialogObject = opener.Com_Parameter.Dialog;
}

Com_AddEventListener(window, "beforeunload", beforeClose);
function Com_DialogReturn(value){
	if(window.showModalDialog){
		dialogRtnValue = value;
	}else{
		//opener.Com_Parameter.Dialog.rtnData=value;
		opener._closeCallback(value);
	}
	close();
}
function beforeClose(){
	dialogObject.rtnData = dialogRtnValue;
}
if(dialogObject.winTitle!=null) {
	Com_SetWindowTitle(dialogObject.winTitle);
}else{
	Com_SetWindowTitle('列表数据选择');
}
Com_Parameter.DialogLang = {
	btnOk:"确定",
	btnCancel:"取消",
	btnSelectNone:"清除",
	currentValue:"当前值："
}
</script>
</head>
<FRAMESET rows="*,50" framespacing=1 bordercolor=#003048 frameborder=1> 
	<FRAME name=dataFrame id=dataFrame frameborder=0 src="pageDataList.jsp?s_css=${param.s_css}"> 
	<FRAME noresize scrolling=no name=optFrame id=optFrame frameborder=0 src="datalist_confirm.html?s_css=${param.s_css}&v=1">
</FRAMESET>
</html>