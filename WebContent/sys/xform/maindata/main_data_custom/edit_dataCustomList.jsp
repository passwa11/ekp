<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit"  showQrcode="false" sidebar="no">
<template:replace name="content" >

<style>

body{
    min-width: 320px;
    margin: 0 auto;
    line-height: 1.5;
    background: #f2f2f2;
    overflow-x:hidden;
    -webkit-tap-highlight-color: transparent;
}

</style>
<script type="text/javascript">
	
	if(window.showModalDialog){
		dialogObject = window.dialogArguments;
	}else{
		dialogObject = opener.Com_Parameter.Dialog;
	}
	
	//添加关闭事件
	Com_AddEventListener(window, "beforeunload", function(){dialogObject.AfterShow();});
	
</script>
<html:form action="/sys/xform/maindata/main_data_custom/sysFormMainDataCustomList.do">
<p class="txttitle"></p>

<table class="tb_normal" width=85%>
    
	<html:hidden property="fdId"/>
	<tr>
		<td class="td_normal_title" width=25%>
			${lfn:message('sys-xform-maindata:sysFormMainDataCustom.showValue') }
		</td>
		<td width=75%>
			<xform:text  showStatus="edit" property="fdValueText" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.showValue') }" ></xform:text>
		</td>
	</tr>
</table>

<html:hidden property="method_GET"/>
</html:form>
</template:replace>
<template:replace name="toolbar">
<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
   <ui:button text="${lfn:message('button.submit')}" order="2" onclick=" Com_Submit(document.sysFormMainDataCustomListForm,'save');">
   </ui:button>
   <ui:button text="${ lfn:message('button.close')}" order="5"  onclick="Com_CloseWindow();">
   </ui:button>
</ui:toolbar>
</template:replace>
</template:include>