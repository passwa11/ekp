<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
<%
	// 获取传入的授权类型
	String authType = request.getParameter("authType");
	if(com.landray.kmss.util.StringUtil.isNull(authType)) {
		authType = "01"; // 如果没有传入授权，则使用默认的授权
	}
	request.setAttribute("authType", authType);
%>
<script language="javascript" for="document" event="onkeydown">   
        var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;   
        if (keyCode == 13) {   
        	 return false;
        }    
</script> 
<script language="JavaScript">
seajs.use(['theme!form']);
Com_IncludeFile("dialog.js");
function validateCateChgForm(of){
	if(!validateEmpty()){
		return false;
	}
	return true;
}

function validateEmpty() {
	var fdCateId = document.getElementsByName("fdCateId")[0];
	if(fdCateId.value=="") {
		seajs.use(["lui/dialog"],function(dialog){
			dialog.alert("<bean:message key="sysSimpleCategory.msg.cateEmpty" bundle="sys-simplecategory"/>");
		})
		
		return false;
	}
	return true;
}

window.onload = function() {
	<c:if test="${not empty param.fdCateId}">
		window.close();
	</c:if>
	<c:if test="${empty param.fdCateId}">
		Dialog_SimpleCategory('${JsParam.cateModelName}','fdCateId','fdCateName',false,null,'${authType}',simpleCategorySelected,true,'${JsParam.fdId}',null,'${JsParam.extProps}');
	</c:if>
};

function simpleCategorySelected(rtnVal){
	if(rtnVal){
		var values = "";
		var __win;
		if(window.opener){
			__win = window.opener;
		}else if(window.parent){
			__win = window.parent;
		}
		if(__win){
			var listSelectedName = "${param.listSelectedName}" ? "${param.listSelectedName}" : "List_Selected";
			var	select = __win.document.getElementsByName(listSelectedName);
			for(var i=0;i<select.length;i++) {
				if(select[i].checked){
					values+=select[i].value;
					values+=",";
				}
			}
		}
		if(!values)
			values = '${JsParam.fdIds}';
		document.getElementsByName("fdIds")[0].value=values; 
	}
}
</script>

<html:form action="/sys/sc/cateChg.do?method=cateChgUpdate" onsubmit="return validateCateChgForm(this);">
	<p class="txttitle"><bean:message bundle="sys-simplecategory"
		key="sysSimpleCategory.chg.title" /></p>
	<center>
	<table class="tb_normal" width=70%>
		<tr>
			<td class="td_normal_title" width=20% align="center">
				<bean:message key="sysSimpleCategory.chg.to" bundle="sys-simplecategory"/>
			</td>
			<td>
				<xform:dialog propertyId="fdCateId" propertyName="fdCateName" showStatus="edit" style="width:98%" >
					Dialog_SimpleCategory('${HtmlParam.cateModelName}','fdCateId','fdCateName',false,null,'${authType}',simpleCategorySelected,true,'${HtmlParam.fdId}',null,'${JsParam.extProps}')
				</xform:dialog>
			</td>
		</tr>
	</table>
	<div style="padding-top:17px">
       <ui:button text="${ lfn:message('button.save') }"  onclick="Com_Submit(document.cateChgForm, 'cateChgUpdate');">
	   </ui:button>
       <ui:button text="${ lfn:message('button.close') }" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();">
	   </ui:button>
    </div>
	</center>
	<html:hidden property="fdIds" value="${HtmlParam.fdIds}"/>
	<html:hidden property="modelName" value="${HtmlParam.modelName}"/>
	<html:hidden property="cateModelName" value="${HtmlParam.cateModelName}"/>
	<html:hidden property="docFkName" value="${HtmlParam.docFkName}"/>
	<html:hidden property="method_GET" />
</html:form>
	</template:replace>
</template:include>
