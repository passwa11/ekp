<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
<script>Com_IncludeFile("jquery.js");</script>
<c:if test="${_isWpsCloudEnable || _isWpsWebOffice || _isWpsCenterEnable}">
    <script>Com_IncludeFile("polyfill.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
    <script>Com_IncludeFile("web-office-sdk-1.1.1.umd.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
    <script>Com_IncludeFile("wps_cloud_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/cloud/js/","js",true);</script>
</c:if>
<c:if test="${_isWpsoaassistEmbed}">
    <script>Com_IncludeFile("wps_utils.js",Com_Parameter.ContextPath + "sys/attachment/sys_att_main/wps/oaassist/js/","js",true);</script>
</c:if>
<script type="text/javascript">
    var _isWpsPrintCloudEnable = "${_isWpsCloudEnable}";
    var _isWpsPrintWebOffice = "${_isWpsWebOffice}";
    var _isWpsCenterEnable = "${_isWpsCenterEnable}";
    var _isWpsoaassistEmbed = "${_isWpsoaassistEmbed}";
    var wpsPrintFlag = false;
    var preIsWpsOaassit = false;
    var __modelName__ = "${JsParam.modelName}";
    var PRINT_XFORM_TEMPLATE_SUBFORM='<%=XFormConstant.TEMPLATE_SUBFORM%>';
    var PRINT_XFORM_TEMPLATE_DEFINE='<%=XFormConstant.TEMPLATE_DEFINE%>';
    var PRINT_OPER_TYPE = "${fdPrintOperType}";//历史模板配置时标识
    var PRINT_SUB_TEMPLATE_SIZE = "${fdPrintSubTemplateSize}";
    var IS_PRINT_SUB_TEMPLATE = PRINT_OPER_TYPE =='templateHistory' && PRINT_SUB_TEMPLATE_SIZE>0;//历史模板配置时是否多表单标识
    Com_AddEventListener(window, 'load', Sys_Print_DesignOnLoad);
    function load_word(){
        setTimeout(function(){
            try{
                var objPrint = document.getElementById("JGWebOffice_sysprint_editonline");
                if(objPrint&&Attachment_ObjectInfo['sysprint_editonline']&&!jg_attachmentObject_sysprint_editonline.hasLoad){
                    $('.lui_dialog_loading_content').hide();
                    $('.bookmarkHelp').show();
                    $('.imageBtn').show();
                    jg_attachmentObject_sysprint_editonline.load();
                    jg_attachmentObject_sysprint_editonline.show();
                    jg_attachmentObject_sysprint_editonline.ocxObj.Active(true);
                } else {
                    $('.lui_dialog_loading_content').hide();
                }
            }catch(e){
                $('.lui_dialog_loading_content').hide();
                if(window.console){
                    window.console.log(e);
                }
            }
        },1000);

    }
    function onFdPrintModeChange(value){
        document.getElementById( "fd_Print_mode_word").style.visibility= "hidden";
        document.getElementById( "fd_Print_mode_book").style.visibility= "hidden";
        var $trDesc = $('#tr_desc');
        if(value=="2"){
            $('.lui_dialog_loading_content').show();
            $('#printTemplate').hide();
            $trDesc.hide();
            //$('#printWordTemplate').show();
            $("#printButtonDiv").show();

            if(_isWpsCenterEnable=="true") {
                $("#content_print").css({
                    width: '100%',
                    height: '750px'
                });
            }else if(_isWpsoaassistEmbed=="true"){
                $("#content_print").css({
                    width: '100%',
                    height: '750px'
                });
            }else{
                $("#content_print").css({
                    width: '100%',
                    height: '650px'
                });
            }
            $('#tr_word_printEdit').show();
            $("#SubPrint_main_tr").hide();
            $("#SubPrint_operation_tr").hide();

            if(!wpsPrintFlag&&_isWpsPrintCloudEnable=="true"){
                if(typeof(wps_cloud_sysprint_editonline) != 'undefined')
                    wps_cloud_sysprint_editonline.load();
                else
                    wpsprintiframe.contentWindow.wps_cloud_sysprint_editonline.load();
                wpsPrintFlag = true;
            }else if(!wpsPrintFlag&&_isWpsCenterEnable=="true"){
                if(typeof(wps_center_sysprint_editonline) != 'undefined')
                    wps_center_sysprint_editonline.load();
                else
                    wpsprintiframe.contentWindow.wps_center_sysprint_editonline.load();
                wpsPrintFlag = true;
            }else if(_isWpsoaassistEmbed=="true"){

                if(typeof(wpsprintiframe.contentWindow.wps_linux_sysprint_editonline) != 'undefined'){
                    setTimeout(function(){
                        preIsWpsOaassit = true;
                        wpsprintiframe.contentWindow.wps_linux_sysprint_editonline.load();
                    },800);
                }

            }else if(_isWpsPrintWebOffice == 'true' && _isWpsoaassistEmbed == "false"){
                document.getElementById( "fd_Print_mode_word").style.visibility= "visible";
                document.getElementById( "fd_Print_mode_book").style.visibility= "visible";
                //请求在线编辑附件的id
                var url="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=addWpsOaassistOnlineFile";
                $.ajax({
                    type:"post",
                    url:url,
                    data:{fdTemplateModelId:"${param.fdTemplateModelId}",fdTemplateModelName:"${param.fdTemplateModelName}",fdTemplateKey:"${param.fdTemplateKey}",fdModelName:"${HtmlParam.templateModelName}",fdKey:"sysprint_editonline",fdTempKey:"${param.fdTempKey}",fdModelId:"${templateForm.fdId}",fdFileExt:"${param.fdFileExt}"},
                    async:false,    //用同步方式
                    success:function(data){
                        if (data){
                            var results =  eval("("+data+")");
                            if(results['editOnlineAttId']!=null){
                                fdAttMainId = results['editOnlineAttId'];
                                var wpsParam = {};
                                editWpsOAAssit(fdAttMainId,wpsParam);
                            }
                        }
                    }
                });
            }else{
                load_word();
                chromeHideJGByObjId_2015(1, 'JGWebOffice_sysprint_editonline');
            }
        }else{
            initDefineTemplate();
            if(PRINT_OPER_TYPE !='templateHistory'){
                //打印模板是否存在控件,不存在则初始化数据
                var isExistCtrl = sysPrintButtons.isPrintTempContainCtrls(sysPrintDesigner.instance.builderDomElement);
                if(!isExistCtrl && sysPrintButtons.isCanImportXFormTemp(sysPrintDesigner.instance)){
                    sysPrintButtons.importXFormTemp(sysPrintDesigner.instance);
                }
                //提前加载表单
                var method = "${JsParam.method}",xFormMode = sysPrintButtons.getXFormMode(sysPrintDesigner.instance.fdKey);
                if(sysPrintButtons.isXFormSupport() && xFormMode=='3'){
                    var xFormId = "TD_FormTemplate_${JsParam.fdKey}";
                    //LoadXForm(xFormId);
                }
            }


            //$('#printWordTemplate').hide();
            $("#printButtonDiv").hide();
            $("#content_print").css({
                width:'0px',
                height:'0px'
            });
            $('#tr_word_printEdit').hide();
            $trDesc.show();
            $('#printTemplate').show();
            if(IS_PRINT_SUB_TEMPLATE || PRINT_OPER_TYPE !='templateHistory' && (sysPrintButtons.getXFormMode("${JsParam.fdKey}")==PRINT_XFORM_TEMPLATE_SUBFORM||(sysPrintButtons.getXFormMode("${JsParam.fdKey}")==PRINT_XFORM_TEMPLATE_DEFINE && (typeof isModeling == "undefined" || isModeling == "false")))){
                $("#SubPrint_operation").attr("src","${LUI_ContextPath}/sys/print/resource/icon/varrowleft.gif");
                $("#SubPrint_main_tr").show();
                $("#SubPrint_operation_tr").show();
            }else{
                $("#SubPrint_main_tr").hide();
                $("#SubPrint_operation_tr").hide();
            }
            chromeHideJG_2015(0);
        }
    }
    //初始化表单设计模板
    function initDefineTemplate(){
        if(!sysPrintDesigner.instance.hasInitialized){
            sysPrintDesigner.instance.init(document.getElementById('sysPrintdesignPanel'));
            sysPrintDesigner.instance.hasInitialized = false;
            sysPrintDesigner.instance.fdKey = '${JsParam.fdKey}';
            sysPrintDesigner.instance.modelName = '${JsParam.modelName}';
            sysPrintDesigner.instance.hasInitialized = true;
            if($("[name='sysPrintTemplateForm.fdTmpXml']").val()){
                sysPrintDesigner.instance.builder.setHTML($("[name='sysPrintTemplateForm.fdTmpXml']").val());
            }else{
                new sysPrintDesignerTableControl(sysPrintDesigner.instance.builder,"table").draw();
            }
        }
    }

    function getfdPrintMode(){
        var fdPrintMode = document.getElementsByName('sysPrintTemplateForm.fdPrintMode')[0].value;
        return fdPrintMode;
    }
    function init(){
        var fdPrintMode = getfdPrintMode();
        onFdPrintModeChange(fdPrintMode);
    }
    function onBookmarkHelp(){
        var mainModelName = "${JsParam.modelName}";
        var fdKey = "${JsParam.fdKey}";
        var _xformCloneTemplateId = $('#_xformCloneTemplateId').val();
        var baseObjs = sysPrintCommon.getDocDict(fdKey,mainModelName,_xformCloneTemplateId);
        var datas = [];

        for(var i = 0 ; i< baseObjs.length;i++){
            if(baseObjs[i].wordSubList){
                var wordSubList = JSON.parse(baseObjs[i].wordSubList);
                for(var j = 0; j < wordSubList.length; j++){
                    var record_sub = {};
                    record_sub.label=wordSubList[j].label;
                    record_sub.name=wordSubList[j].name;
                    datas.push(record_sub);
                }
                continue;
            }
            var record = {};
            record.label=baseObjs[i].label;
            var name =baseObjs[i].name;
            if(baseObjs[i].isXFormDict && name.indexOf('.') >-1){
                name=name.split('.')[1];
            }
            if(baseObjs[i].isXFormDict && baseObjs[i].controlType && baseObjs[i].controlType=='auditShow'){
                name = name + "_auditShow";//区分审批意见
            }
            if(baseObjs[i].isXFormDict && (baseObjs[i].businessType && baseObjs[i].businessType=='qrCode' || baseObjs[i].controlType && baseObjs[i].controlType=='qrCode')){
                name = name + "_qrCode";
            }
            record.name = name;

            datas.push(record);
        }

        PostNewWin(JSON.stringify(datas));
    }

    function PostNewWin(data){
        var postUrl = Com_Parameter.ContextPath+'sys/print/sys_print_template/sysPrintTemplate.do?method=bookmark&fdModelName=${JsParam.modelName}&fdKey=${JsParam.fdKey}';
        var postData = data;
        var iframe = document.getElementById("tmp_postData_iframe");
        if(!iframe){
            iframe = document.createElement("iframe");
            iframe.id = "tmp_postData_iframe";
            iframe.scr= "about:blank";
            iframe.frameborder = "0";
            iframe.style.width = "0px";
            iframe.style.height = "0px";

            var form = document.createElement("form");
            form.id = "postData_form";
            form.method = "post";
            form.target = "_blank";

            document.body.appendChild(iframe);
            iframe.contentWindow.document.write("<body>" + form.outerHTML + "</body>");
        }
        iframe.contentWindow.document.getElementById("postData_form").innerHTML = "<input name='bookmarkData' id='postData' type='hidden' value='" + postData + "'/>";
        iframe.contentWindow.document.getElementById("postData_form").action = postUrl;
        iframe.contentWindow.document.getElementById("postData_form").submit();
    }

    function removeTemplateStyle(){
        //提交前清除相关样式
        sysPrintDesigner.instance.builder.clearSeclectedCtrl();
        var $drawDom = $('#sys_print_designer_draw');
        $drawDom.find('.sysprint_cursor_n').removeClass('sysprint_cursor_n');
        $drawDom.find('.sysprint_cursor_e').removeClass('sysprint_cursor_e');
        $drawDom.find('.border_dashed').removeClass('border_dashed');
        $drawDom.find('.table_select').removeClass('table_select');
        //分页控件
        $drawDom.find('.nextpage').removeClass('nextpage');
        $drawDom.find("hr[fd_type='page']").each(function(){
            var p = $(this).parent();
            if(p[0].nodeName.toUpperCase()=='TD'){
                p.parent().addClass('nextpage');
            }
            if(p[0].id=='sys_print_designer_draw'){
                var prev = $(this).prev();
                if(prev && prev.length>0){
                    prev.addClass('nextpage');
                }
            }
        });
    }

    function defineTemplateSubmit(){
        var defaultTr = $("#TABLE_DocList_Print").find('tr:eq(0)');
        if(IS_PRINT_SUB_TEMPLATE || PRINT_OPER_TYPE !='templateHistory'
            && (sysPrintButtons.getXFormMode("${JsParam.fdKey}")==PRINT_XFORM_TEMPLATE_SUBFORM
                || (sysPrintButtons.getXFormMode("${JsParam.fdKey}")==PRINT_XFORM_TEMPLATE_DEFINE
                    && (typeof isModeling == "undefined" || isModeling != "true")))){
            //先保存当前选择的模板信息，防止最后操作的模板未保存
            var tr = $("#TABLE_DocList_Print").find("tr[ischecked='true']");
            removeTemplateStyle();
            var fdDesignerHtml = sysPrintDesigner.instance.builder.getHTML();
            var fdCss = $("input[name='sysFormTemplateForms.${JsParam.fdKey}.fdCss']").val();
            var myfdDesignerHtml = tr.find("input[name$='fdTmpXml']");
            var myfdCss = tr.find("input[name$='fdCss']");
            if(fdDesignerHtml!=myfdDesignerHtml.val()){
                myfdDesignerHtml.val(fdDesignerHtml);
            }
            if(fdCss!=myfdCss.val()){
                myfdCss.val(fdCss);
            }
            //清除样式
            needLoad_subprint = false;
            $("#TABLE_DocList_Print").find("tr[ischecked]").each(function(){
                $(this).find("a[name='subPrintText']").click();
                removeTemplateStyle();
            });
            tr.find("a[name='subPrintText']").click();
            needLoad_subprint = true;
            //保存基础模板
            //BASE64处理脚本内容
            $("[name='sysPrintTemplateForm.fdTmpXml']").val(base64Encodex(defaultTr.find("input[name$='fdTmpXml']").val()));
            //全局样式支持
            var fdCss = defaultTr.find("input[name$='fdCss']").val();
            $("[name='sysPrintTemplateForm.fdCss']").val(fdCss);
            $("input[name='sysPrintTemplateForm.fdName']").val(defaultTr.find("input[name$='fdName']").val());

        }else{
            removeTemplateStyle();
            //BASE64处理脚本内容
            $("[name='sysPrintTemplateForm.fdTmpXml']").val(base64Encodex(sysPrintDesigner.instance.builder.getHTML()));
            //全局样式支持
            var fdCss = $("[name='sysFormTemplateForms.${JsParam.fdKey}.fdCss']").val();
            $("[name='sysPrintTemplateForm.fdCss']").val(fdCss);
            defaultTr.find("input[name$='fdTmpXml']").val(sysPrintDesigner.instance.builder.getHTML());
            //清除多表单打印模板信息
            $("#TABLE_DocList_Print").find('tr:gt(0)').each(function(){
                DocList_DeleteRow(this);
            });
        }
        //BASE64处理子打印模板脚本内容
        $("#TABLE_DocList_Print").find('tr[ischecked]').each(function(){
            var subHtml = $(this).find("input[name$='fdTmpXml']");
            subHtml.val(Sys_Print_Base64Encodex(subHtml.val(), $(this)));
        });
        return true;
    }
    Com_Parameter.event["submit_failure_callback"][Com_Parameter.event["submit_failure_callback"].length] = function(){
        var fdTmpXmlObj = $("[name='sysPrintTemplateForm.fdTmpXml']");
        if(sysPrintDesigner){
            if(sysPrintDesigner.instance.designerPrintHtmls){
                $("#TABLE_DocList_Print").find('tr[ischecked]').each(function(){
                    var subHtml = $(this).find("input[name$='fdTmpXml']");
                    subHtml.val(sysPrintDesigner.instance.designerPrintHtmls[$(this).attr("id")]);
                });
                var defaultTr = $("#TABLE_DocList_Print").find('tr:eq(0)');
                fdTmpXmlObj.val(defaultTr.find("input[name$='fdTmpXml']").val());
            }
        }else{
            $("#TABLE_DocList_Print").find('tr[ischecked]').each(function(){
                var subHtml = $(this).find("input[name$='fdTmpXml']");
                if(subHtml.val().indexOf('\u4645\u5810\u4d40') > -1){
                    var vData = {"fdDesignerHtml":subHtml.val()};
                    subHtml.val(Sys_Print_Base64Decodex(vData));
                }
            });
            var defaultTr = $("#TABLE_DocList_Print").find('tr:eq(0)');
            fdTmpXmlObj.val(defaultTr.find("input[name$='fdTmpXml']").val());
        }
    };

    //打印模板表单HTML解码
    function Sys_Print_Base64Decodex(arg) {
        var result = null;
        $.ajax({
            url: Com_Parameter.ContextPath + "sys/lbpm/engine/jsonp.jsp?s_bean=convertBase64ToHtmlService",
            async: false,
            data: arg,
            type: "POST",
            dataType: 'json',
            success: function (data) {
                result = data.html;
            },
            error: function (er) {

            }
        });
        return result;
    }

    // 打印模板表单HTML加密
    function Sys_Print_Base64Encodex(val, obj){
        if(val.indexOf("\u4645\u5810\u4d40") < 0){
            if(sysPrintDesigner){
                // 保存加密前的HTML
                if(obj && obj.length>0){
                    if(!sysPrintDesigner.instance.designerPrintHtmls){
                        sysPrintDesigner.instance.designerPrintHtmls = {};
                    }
                    sysPrintDesigner.instance.designerPrintHtmls[obj.attr("id")] = val;
                }
            }
            //BASE64处理脚本内容
            val = base64Encodex(val);
        }
        return val;
    }
    function wordTemplateSubmit(){
        var objPrint = document.getElementById("JGWebOffice_sysprint_editonline");
        if(objPrint&&Attachment_ObjectInfo['sysprint_editonline'] && jg_attachmentObject_sysprint_editonline.hasLoad){
            jg_attachmentObject_sysprint_editonline.ocxObj.Active(true);
            jg_attachmentObject_sysprint_editonline._submit();
        }
    }
    function Sys_Print_DesignOnLoad(){
        initDefineTemplate();
        // 添加标签切换事件
        var table = document.getElementById("Label_Tabel");
        if(table != null && window.Doc_AddLabelSwitchEvent){
            Doc_AddLabelSwitchEvent(table, "sysPrint_labelSwitch");
        }
        if(IS_PRINT_SUB_TEMPLATE){
            init();
            //多表单模式
            setTimeout(function (){
                if(IS_PRINT_SUB_TEMPLATE || PRINT_OPER_TYPE !='templateHistory' && (sysPrintButtons.getXFormMode("${JsParam.fdKey}")==PRINT_XFORM_TEMPLATE_SUBFORM||sysPrintButtons.getXFormMode("${JsParam.fdKey}")==PRINT_XFORM_TEMPLATE_DEFINE)){
                    var height = $("#SubForm_Print_table").outerHeight(false)-5;
                    $("#DIV_SubForm_Print").css("height",height);
                    $("#SubPrintDiv").css("height",(height-20-37)*0.35);
                    $("#SubPrintControlsDiv").css("height",(height-20-37)*0.65);
                    //清空控件div中信息
                    $("#SubPrintControlsDiv").html("");

                }
            },0);
        }
    }
    //标签切换事件
    function sysPrint_labelSwitch(tableName,index){
        var trs = document.getElementById(tableName).rows;
        if(trs[index].id =="sysPrint_tab"){
            init();
            //多表单模式
            setTimeout(function (){
                if(IS_PRINT_SUB_TEMPLATE || PRINT_OPER_TYPE !='templateHistory' && (sysPrintButtons.getXFormMode("${JsParam.fdKey}")==PRINT_XFORM_TEMPLATE_SUBFORM||sysPrintButtons.getXFormMode("${JsParam.fdKey}")==PRINT_XFORM_TEMPLATE_DEFINE)){
                    var height = $("#SubForm_Print_table").outerHeight(false)-5;
                    $("#DIV_SubForm_Print").css("height",height);
                    $("#SubPrintDiv").css("height",(height-20-37)*0.35);
                    $("#SubPrintControlsDiv").css("height",(height-20-37)*0.65);
                    //清空控件div中信息
                    $("#SubPrintControlsDiv").html("");
                    if(XForm_XformIframeIsLoad(window) == false){
                        // 加载表单内容
                        LoadXForm('TD_FormTemplate_${JsParam.fdKey}');
                    }
                    var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
                    if(!customIframe){
                        customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
                    }
                    if(customIframe.Designer != null && customIframe.Designer.instance != null){
                        if(needLoad_subprint){
                            SubPrint_Load();
                        }
                    }
                }
            },0);
        }else{

            if(_isWpsoaassistEmbed=="true"&&preIsWpsOaassit){
                wpsprintiframe.contentWindow.wps_linux_sysprint_editonline.setTmpFileByAttKey();
                wpsprintiframe.contentWindow.wps_linux_sysprint_editonline.isCurrent=false;
                preIsWpsOaassit=false;
            }else{
                preIsWpsOaassit=false;
            }
        }
    }

    //WPS加载项打印
    function wpsPrintModul()
    {
        //请求在线编辑附件的id
        var url="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=addWpsOaassistOnlineFile";
        $.ajax({
            type:"post",
            url:url,
            data:{fdTemplateModelId:"${param.fdTemplateModelId}",fdTemplateModelName:"${param.fdTemplateModelName}",fdTemplateKey:"${param.fdTemplateKey}",fdModelName:"${HtmlParam.templateModelName}",fdKey:"sysprint_editonline",fdTempKey:"${param.fdTempKey}",fdModelId:"${templateForm.fdId}",fdFileExt:"${param.fdFileExt}"},
            async:false,    //用同步方式
            success:function(data){
                if (data){
                    var results =  eval("("+data+")");
                    if(results['editOnlineAttId']!=null){
                        fdAttMainId = results['editOnlineAttId'];
                        var wpsParam = {};
                        editWpsOAAssit(fdAttMainId,wpsParam);
                    }
                }
            }
        });
    }
    //提交表单前保存打印模板内容
    Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function(){
        var fdPrintMode = getfdPrintMode();
        defineTemplateSubmit();
        if(fdPrintMode=="2"){
            wordTemplateSubmit();
        }
        return true;
    };
    domain.register("wps_load",function(){
        wps_cloud_sysprint_editonline.load();
    });
</script>