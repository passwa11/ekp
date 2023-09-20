<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant" %>
<%@ include file="/resource/jsp/common.jsp" %>
<c:set var="sysFormTemplateForm" value="${requestScope[JsParam.formName]}"/>
<c:set var="xFormTemplateForm" value="${sysFormTemplateForm.sysFormTemplateForms[JsParam.fdKey]}"/>
<c:set var="sysFormTemplateFormPrefix" value="sysFormTemplateForms.${JsParam.fdKey}."/>
<c:set var="entityName" value="${JsParam.fdMainModelName}_${sysFormTemplateForm.fdId}"/>

<%-- <input type='hidden' name="${sysFormTemplateFormPrefix}fdMode" value="<%=XFormConstant.TEMPLATE_DEFINE%>" /> --%>

<%@ include file="/sys/modeling/base/form/xform/template_display_edit.jsp" %>

<script>

    var _xform_MainModelName = '${JsParam.fdMainModelName}';

    function _XForm_GetSysDictObj(modelName) {
        return Formula_GetVarInfoByModelName(modelName);
    }

    function _XForm_GetSysDictObj_${JsParam.fdKey}() {
        return _XForm_GetSysDictObj(_xform_MainModelName);
    }

    function XForm_getXFormDesignerInstance_${JsParam.fdKey}() {
        var obj = {};
        var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
        if (!customIframe) {
            customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
        }
        if (customIframe.Designer && customIframe.Designer.instance && customIframe.Designer.instance.hasInitialized) {
            obj["designer"] = customIframe.Designer.instance;
            obj["modelName"] = customIframe.parent._xform_MainModelName;
            return obj;
        }
        return null;
    }

    // 加载表单引擎
    $(document).ready(function () {
        var funStr = $("#TD_FormTemplate_${JsParam.fdKey}").attr("onresize");
        var tmpFunc = new Function(funStr);
        tmpFunc.call(this, Com_GetEventObject());
    });

    function Form_getModeValue(key) {
        var typeEl = document.getElementsByName('sysFormTemplateForms.' + key + '.fdMode')[0];
        var typeVe = "";
        if (typeEl && typeEl != null) {
            typeVe = typeEl.value;
        }
        return typeVe;
    }

    Com_AddEventListener(window, "load", function () {
        // 表单引用方式 选项初始化
        Form_Template_OptionInit("${JsParam.fdKey}");
        if (typeof Form_Template_View_Plat_Init != "undefined") {
            Form_Template_View_Plat_Init("${JsParam.fdKey}");
        }
        // 由于标签解析的时候，可能会不存在扩展的选项（通过addOptionType扩展的），所以此处再做一层处理
        var modeDao = "${xFormTemplateForm.fdMode}"; // 数据库存储的引用方式
        var modeInit = modeDao;
        if (modeDao == "1") {
            modeDao = "3";
        }
        var mode = Form_getModeValue("${JsParam.fdKey}"); // 当前选中的引用方式
        var method = Com_GetUrlParameter(location.href, "method");
        var select = $("[name='sysFormTemplateForms.${JsParam.fdKey}.fdMode']");
        if (method != "add" && modeDao && modeDao != '' && modeDao != mode) {
            if (select.length > 0) {
                $(select[0]).val(modeDao);
                mode = modeDao;
            }
        }
        Form_ChgDisplay("${JsParam.fdKey}", mode);
        if ((method == 'edit' || method == 'update') && modeInit != "1") {
            //#120174 表单编辑时不允许切换表单模式
            if (select.length > 0) {
                select.prop('disabled', 'false');
            }
            $("span[name='subFormSpan']").show();
            $("span[name='subFormSpan']").text("<kmss:message key='sys-xform:sysSubFormTemplate.edit_msg'/>");
        }
        //Form_ShowCommonTemplatePreview("${JsParam.fdKey}");
        
        /***#115908移动端默认布局开始***/
        var layout = "${xFormTemplateForm.fdUseDefaultLayout}"; // 数据库存储的布局方式
        var $input = $("[name='sysFormTemplateForms.${JsParam.fdKey}.fdUseDefaultLayout']");
        var _$layout = $("[name='_sysFormTemplateForms.${JsParam.fdKey}.fdUseDefaultLayout']");
        if ($input.length > 0 && _$layout.length > 0) {
        	$input.val(layout);
        	if(layout === "false"){
        		_$layout.click();
        	}
        }
        //悬浮出现提示
        $(".lui_prompt_tooltip_drop").hover(function(){
        	$(".lui_dropdown_tooltip_menu").css("display","block");
        })
        $(".lui_prompt_tooltip_drop").mouseout(function(){
			$(".lui_dropdown_tooltip_menu").css("display","none");
		})
		/***#115908移动端默认布局结束***/
    });

    // 选项初始化
    // addOptionType: 显示值|实际值;显示值2|实际值2 ,多个选项以分号分开，显示值和实际值以|分开，如果没有|，默认显示值和实际值一样，扩展实际值不能选择0-4，从5开始
    // removeOptionType: 1;2 ，只需要填写实际值，以分号分开
    function Form_Template_OptionInit(fdKey) {
        // 先判断是否有需要变更的传入参数
        var addOptionType = "${JsParam.addOptionType}";
        var removeOptionType = "${JsParam.removeOptionType}";
        var enableFlow = "${JsParam.enableFlow}";
        var select = $("select[name='sysFormTemplateForms." + fdKey + ".fdMode']");
        if (addOptionType || removeOptionType) {
            if (select.length > 0) {
                if (addOptionType) {
                    Form_Template_AddOption(addOptionType, select[0]);
                }
                if (removeOptionType) {
                    Form_Template_RemoveOption(removeOptionType, select[0]);
                }
            }
        }
        if (enableFlow != "true") {
            Form_Template_RemoveOption("4", select[0]);
        }
    }

    // 初始化的时候，根据业务模块传进来的参数，动态增加表单引用方式的选项
    function Form_Template_AddOption(optionType, dom) {
        var options = optionType.split(";");
        for (var i = 0; i < options.length; i++) {
            var option = options[i];
            var val, text;
            if (option.indexOf("|") > -1) {
                var array = option.split("|");
                text = Data_GetResourceString(array[0]);
                if (!text) {
                    text = array[0];
                }
                val = array[1];
            } else {
                text = option;
                val = option;
            }
            if (val == '' || parseInt(val) < 4) {
                continue;
            }
            $(dom).append("<option value='" + val + "'>" + text + "</option>");
        }
    }

    //初始化的时候，根据业务模块传进来的参数，动态删除表单引用方式的选项
    function Form_Template_RemoveOption(optionType, dom) {
        var selectOptions = $(dom).find("option");
        var removeVals = optionType.split(";");
        for (var i = 0; i < selectOptions.length; i++) {
            var option = selectOptions[i];
            if ($.inArray(option.value, removeVals) > -1) {
                $(option).remove();
            }
        }
    }

    <%-- 0 引用方式； 1 富文本模式； 2 引用其他类型；3 已定义表单；4 自设计 --%>

    function Form_ChgDisplay(key, value) {
        if ((value == "<%=XFormConstant.TEMPLATE_DEFINE%>") || (value == "<%=XFormConstant.TEMPLATE_SUBFORM%>")) {  // 只有自定义表单需要多语言页签
            if (window.currentDisplay == "none") {
                Doc_ShowLabelById("Label_Tabel", "tr_uu_lang");
            }
        } else {
            Doc_HideLabelById("Label_Tabel", "tr_uu_lang");
            window.currentDisplay = "none";
        }
        var divSubForm = document.getElementById("DIV_SubForm_" + key);
        if ((value == "<%=XFormConstant.TEMPLATE_DEFINE%>") || (value == "<%=XFormConstant.TEMPLATE_SUBFORM%>")) {
            if (value == "<%=XFormConstant.TEMPLATE_SUBFORM%>") {
                $("#Subform_operation").attr("src", "${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/varrowleft.gif");
                $("#SubForm_main_tr").show();
                $("#SubForm_operation_tr").show();
                $("span[name='subFormSpan']").show();
                $("span[name='subFormSpan']").text("<kmss:message key='sys-xform:sysSubFormTemplate.change_msg'/>");
            } else {
                $("#SubForm_main_tr").hide();
                $("#SubForm_operation_tr").hide();
                $("span[name='subFormSpan']").hide();
            }
        } else {

        }
        if (typeof XForm_Mode_Listener != 'undefined') {
            XForm_Mode_Listener(key, value);
        }
    }
</script>