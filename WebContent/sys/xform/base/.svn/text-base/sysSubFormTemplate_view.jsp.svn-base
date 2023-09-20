<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
<%@ include file="/resource/jsp/common.jsp"%>

<style>
.subTable {
	border:none ! important;
}
.subTable td {
	border:none ! important;
}
.subTable a{
	cursor: pointer;
} 
</style>

<table id="TABLE_SUBFORM" class="tb_normal subTable" width=100% align="center" frame=void>
	<tr ischecked="true">
		<td>
			<input type="hidden" name="subformfdId" value="<c:out value="${xFormTemplateForm.fdId}" />" pcDefault="true"/>
			<input type="hidden" name="subformfdName" value="<c:out value="${sysPrintTemplateForm.fdName}" />" />
			<a style="font-weight:bold;color:#47b5e6;" onclick="SubForm_Click(this);"><c:out value="${xFormTemplateForm.fdName}" /></a>
			<input type="hidden" name="subformfdDesignerHtml" value="<c:out value="${xFormTemplateForm.fdDesignerHtml}" />" />
		</td>
	</tr>
	<c:forEach items="${xFormTemplateForm.fdSubForms}"  var="item" varStatus="vstatus">
		<tr KMSS_IsContentRow="1" ischecked="false" defaultWebForm="<c:out value="${item.fdIsDefWebForm}" />"> 
			<td>
				<input type="hidden" name="subformfdId" value="<c:out value="${item.fdId}" />" />
				<input type="hidden" name="subformfdName" value="<c:out value="${item.fdName}" />" />
				<a style="font-weight:bold;" onclick="SubForm_Click(this);"><c:out value='${item.fdName}'/></a>
				<input type="hidden" name="subformfdDesignerHtml" value="<c:out value="${item.fdDesignerHtml}" />" />
				<input type="hidden" name="subformPcId" value="<c:out value="${item.fdPcFormId}" />" />
			</td>
		</tr>
	</c:forEach>
</table>

<script>
var xform_subform_fdMode = "${xFormTemplateForm.fdMode}"

$(function(){
	if(xform_subform_fdMode=="<%=XFormConstant.TEMPLATE_SUBFORM%>"){
		$("#DIV_SubForm_View").show();
		$("#TB_FormTemplate_View").css("width","87%");
		$("#TB_FormTemplate_View").css("float","right");
	}
});

function SubForm_Click(dom){
	//切换前选中的表单
	var tr = $("#TABLE_SUBFORM").find("tr[ischecked='true']");
	$("#TABLE_SUBFORM").find("tr").each(function(){
		if($(this)[0]!=$(dom).parents("tr[ischecked]")[0]){
			$(this).attr("ischecked","false");
			$(this).find("a").css("color","");
		}else{
			$(this).find("a").css("color","#47b5e6");
			$(this).attr("ischecked","true");
		}
	});
	//切换后选中的表单
	var tr2 = $("#TABLE_SUBFORM").find("tr[ischecked='true']");
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	if(customIframe.Designer != null && customIframe.Designer.instance != null){
		var html = tr2.find("input[name='subformfdDesignerHtml']").val();
		//设置html
		$(customIframe.document.getElementById('designPanel')).html(html);
		//重新调整iframe高度
		setTimeout(function(){
			SubForm_AdjustViewHeight(customIframe);
		}, 200);
	}
	var div = $("#sysFormTemplateChangeHistoryDiv_${param.fdKey}");
	if(!div.is(":hidden") && tr[0]!=tr2[0]) {
	    if (typeof sys_form_history_updateVersion != "undefined") {
            sys_form_history_updateVersion(div);
        }
	}
}

function SubForm_AdjustViewHeight(iframe) {
	$("#DIV_SubForm_View").css("height",10);
	var _height = iframe.document.getElementById("designPanel").offsetHeight;
	iframe.frameElement.style.height =  _height + 10 + 'px';
	var myHeight = $("#DIV_SubForm_View").parent().outerHeight(false);
	$("#DIV_SubForm_View").css("height",myHeight-9);
}

Com_AddEventListener(window, 'load', function () {
	var table = document.getElementById("Label_Tabel");
	if(table!=null && window.Doc_AddLabelSwitchEvent){
		Doc_AddLabelSwitchEvent(table, "SubForm_OnLabelSwitch_${JsParam.fdKey}");
	} 
});

function SubForm_OnLabelSwitch_${JsParam.fdKey}(table,index){
	if(index==2){
		setTimeout(function (){
			var myHeight = $("#DIV_SubForm_View").parent().outerHeight(false);
			$("#DIV_SubForm_View").css("height",myHeight-9);
		},0);
	}
}

function XForm_getSubFormViewInfo_${JsParam.fdKey}() {
	var subObj = [];
	var pcDefaultId;
    var defaultLayout = "${xFormTemplateForm.fdUseDefaultLayout}";
	if (xform_subform_fdMode == '<%=XFormConstant.TEMPLATE_SUBFORM%>') {
		$("#TABLE_SUBFORM").find("tr[ischecked]").each(function(i) {
			var subformObj = {};
			var formId = $(this).find("input[name$='fdId']").val();
			if(i==0){
				subformObj.id='default';
				pcDefaultId = formId;
				subformObj.pcDefault = true;
				subformObj.name='<bean:message bundle="sys-xform" key="sysSubFormTemplate.default_form" />';
			}else{
			    if (!defaultLayout) {
                    if($(this).attr("defaultWebForm") && $(this).attr("defaultWebForm")=="true"){
                        subformObj.mobileForm = "true";
                        subformObj.pcFormId = $(this).find("input[name$='subformPcId']").val();
                    }
                }
				subformObj.id= formId;
				subformObj.name=$(this).find("input[name$='fdName']").val();
			}
			subObj.push(subformObj);
		});
		//移动默认放到移动第一个
		var mobileDefaultIndex;
		var mobileStartIndex;
		var mobileDefaultForm;
		for (var i = 0; i < subObj.length; i++) {
			if (subObj[i].mobileForm === "true") {
				mobileStartIndex = i;
				break;
			}
		}
		for (var i = 0; i < subObj.length; i++) {
			if (subObj[i].mobileForm === "true" && subObj[i].pcFormId === pcDefaultId) {
				mobileDefaultForm = subObj[i];
				subObj[i].mobileDefault = true;
				mobileDefaultIndex = i;
				break;
			}
		}
		if (mobileDefaultIndex && mobileStartIndex) {
			subObj[mobileDefaultIndex] = subObj[mobileStartIndex];
			subObj[mobileStartIndex] = mobileDefaultForm;
		}
	}
	return subObj;
}
</script>