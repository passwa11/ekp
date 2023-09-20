<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 模板全类名隐藏域 -->
<c:if test="${not empty sysFormTemplateForm}">
	<input type='hidden' name='fdModelName' value='${sysFormTemplateForm.modelClass.name}'/>
</c:if>
<c:if test="${empty sysFormTemplateForm.modelClass.name}">
	<input type='hidden' name='fdModelName' value='${xFormTemplateForm.fdModelName}'/>
</c:if>
<script>
XForm_Loading_Msg = '<bean:message bundle="sys-xform" key="XForm.Loading.Msg"/>';
XForm_Loading_Img = document.createElement('img');
XForm_Loading_Img.src = Com_Parameter.ContextPath + "sys/xform/designer/style/img/_loading.gif";
XForm_Loading_Div = document.createElement('div');
XForm_Loading_Div.id = "XForm_Loading_Div";
XForm_Loading_Div.style.position = "absolute";
XForm_Loading_Div.style.padding = "5px 10px";
XForm_Loading_Div.style.fontSize = "12px";
XForm_Loading_Div.style.backgroundColor = "#F5F5F5";
XForm_loading_Text = document.createElement("label");
XForm_loading_Text.id = 'XForm_loading_Text_Label';
XForm_loading_Text.appendChild(document.createTextNode(XForm_Loading_Msg));
XForm_loading_Text.style.color = "#00F";
XForm_loading_Text.style.height = "16px";
XForm_loading_Text.style.margin = "5px";
XForm_Loading_Div.appendChild(XForm_Loading_Img);
XForm_Loading_Div.appendChild(XForm_loading_Text);
function XForm_Loading_Show() {
	document.body.appendChild(XForm_Loading_Div);
	<%--XForm_Loading_Div.style.top = document.body.clientHeight / 2 + document.body.scrollTop -30;--%>
	$(XForm_Loading_Div).css('top', 180 + document.body.scrollTop);
	$(XForm_Loading_Div).css('left', document.body.clientWidth / 2 + document.body.scrollLeft -50);
}
function XForm_Loading_Hide() {
	XForm_Loading_Div.style.display = "none";
	var div = document.getElementById('XForm_Loading_Div');
	if (div)
		document.body.removeChild(XForm_Loading_Div);
	var win = this.contentWindow ? this.contentWindow : this;
	if (typeof win._Designer_Control_uploadAttachment_loadAttrMain != "undefined"){
		win._Designer_Control_uploadAttachment_loadAttrMain();
	} else {
        var iframe = win.frames["IFrame_FormTemplate_${JsParam.fdKey}"];
        if (iframe) {
            win = iframe.contentWindow ? iframe.contentWindow : iframe;
            if (typeof win._Designer_Control_uploadAttachment_loadAttrMain != "undefined"){
                win._Designer_Control_uploadAttachment_loadAttrMain();
            }
        }
    }
}

// 表单HTML加密
function Xform_Base64Encodex(val, customIframe, obj){
    if(val.indexOf("\u4645\u5810\u4d40") < 0){
        if(customIframe.Designer){
            // 保存加密前的HTML
            if(obj && obj.length>0){
                if(!customIframe.Designer.instance.designerHtmls){
                    customIframe.Designer.instance.designerHtmls = {};
                }
                customIframe.Designer.instance.designerHtmls[obj.attr("id")] = val;
            }
        }
        //BASE64处理脚本内容
        val = base64Encodex(val);
    }
    return val;
}
</script>