<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script>Com_IncludeFile('doclist.js|jquery.js|docutil.js');Com_IncludeFile('json2.js');</script>
<script type="text/javascript">Com_IncludeFile("docutil.js|security.js|dialog.js|formula.js");</script>
<script>Com_IncludeFile('jquery.colorpicker.js','../sys/xform/designer/globalStyle/');Com_IncludeFile("dialog.js", "style/"+Com_Parameter.Style+"/dialog/");</script>

<script>
    var flag = (navigator.userAgent.indexOf('MSIE') > -1) || navigator.userAgent.indexOf('Trident') > -1
    if(window.showModalDialog && flag){
        dialogObject = window.dialogArguments;
    }else{
        dialogObject = opener.Com_Parameter.Dialog;
    }
    var objData=dialogObject.data;
    var callback=dialogObject.AfterShow;

</script>
<script>
    GlobalStyle_Config = {};
    GlobalStyle_Config.controlType=[{text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelInfoName"/>',value:'.xform_label'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlInputTextInfoName"/>',value:'.xform_inputText, .xform_inputText input'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextareaInfoName"/>',value:'.xform_textArea'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlInputRadioInfoName"/>',value:'.xform_inputRadio label, .xform_inputRadio .txtstrong'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlInputCheckboxInfoName"/>',value:'.xform_inputCheckBox label, .xform_inputCheckBox .txtstrong'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlSelectInfoName"/>',value:'.xform_Select'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlChinaValue_info_controlName"/>',value:'.xform_chinaValue'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlCalculation_info_name"/>',value:'.xform_Calculation'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlFieldLayoutName"/>',value:'.xform_fieldlayout'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.buttonsAddress"/>',value:'.xform_address, .xform_address div.mf_container ol.mf_list li.mf_item'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.new_address_name"/>',value:'.xform_new_address, .xform_new_address .input input[readonly], .xform_new_address div.mf_container ol.mf_list li.mf_item'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.buttonsDatetime"/>',value:'.xform_datetime, .xform_datetime input'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.relation_select_name"/>',value:'.xform_relation_select, .xform_relation_select span'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.auditNote"/>',value:'.xform_auditNote, .xform_auditNote textarea'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.auditshow_name"/>',value:'.xform_auditshow, .xform_auditshow p'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.relation_radio_name"/>',value:'.xform_relation_radio, .xform_relation_radio label, .xform_relation_radio .txtstrong'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.relation_checkbox_name"/>',value:'.xform_relation_checkbox, .xform_relation_checkbox label, .xform_relation_checkbox .txtstrong'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.relation_choose_name"/>',value:'.xform_relation_choose, .xform_relation_choose .txtstrong'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlAttrFormulaLoad"/>',value:'.xform_formula_load'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.buttonsFSelect"/>',value:'.xform_fselect, .xform_fselect .fs-label'},
        {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.circulationOpinionShow_name"/>',value:'.xform_circulationOpinionShow, .xform_circulationOpinionShow div'}];
    GlobalStyle_Config.font = {
        style : [
            {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontSelect"/>', value:''},
            {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontSongTi"/>', value:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontSongTi"/>', style:'font-family:<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontSongTi"/>'},
            {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontXinSongTi"/>', value:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontXinSongTi"/>', style: 'font-family:'+'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontXinSongTi"/>'},
            {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontKaiTi"/>', value:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontKaiTi"/>', style: 'font-family:'+'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontKaiTi"/>'},
            {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontHeiTi"/>', value:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontHeiTi"/>', style: 'font-family:'+'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontHeiTi"/>'},
            {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontYouYuan"/>', value:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontYouYuan"/>', style: 'font-family:'+'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontYouYuan"/>'},
            {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontYaHei"/>', value:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontYaHei"/>', style: 'font-family:'+'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontYaHei"/>'},
            {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontFZXBSJT" />',value:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontFZXBSJT" />',style: 'font-family:'+'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontFZXBSJT" />'},
            {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontZhongsong" />',value:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontZhongsong" />',style: 'font-family:'+'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontZhongsong" />'},
            {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontFangSongGB2312" />',value:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontFangSongGB2312" />',style: 'font-family:'+'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontFangSongGB2312" />'},
            {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontCourierNew"/>', value:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontCourierNew"/>', style: 'font-family:\"'+'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontCourierNew"/>'+'\"'},
            {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontTimesNewRoman"/>', value:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontTimesNewRoman"/>', style: 'font-family:\"'+'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontTimesNewRoman"/>'+'\"'},
            {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontTahoma"/>', value:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontTahoma"/>', style: 'font-family:\"'+'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontTahoma"/>'+'\"'},
            {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontArial"/>', value:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontArial"/>', style: 'font-family:\"'+'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontArial"/>'+'\"'},
            {text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontVerdana"/>', value:'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontVerdana"/>', style: 'font-family:\"'+'<bean:message bundle="sys-xform-base" key="Designer_Lang.configFontVerdana"/>'+'\"'}
        ],
        size : (function() {
            var ops = [];
            ops.push({text: '<bean:message bundle="sys-xform-base" key="Designer_Lang.configSizeSelect2"/>', value:''});
            for (var i = 9; i < 26; i ++) {
                ops.push({text: i + '<bean:message bundle="sys-xform-base" key="Designer_Lang.configSizePx"/>', value: i + 'px'});
            }
            //新加入字号
            var sizes = [26,28,30,32,34,36,40,48,56,72];
            for(var j in sizes){
                ops.push({text: sizes[j] + '<bean:message bundle="sys-xform-base" key="Designer_Lang.configSizePx"/>', value: sizes[j] + 'px'});
            }
            return ops;
        })(),
        b:[{text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrDefault"/>',value:''},{text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrBold"/>',value:'bold',style:'font-weight:bold'},{text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrNormal1"/>',value:'normal',style:'font-weight:normal'}],
        i:[{text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrDefault"/>',value:''},{text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrItalic"/>',value:'italic',style:'font-style:italic'},{text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrNormal1"/>',value:'normal',style:'font-style:normal'}],
        underline:[{text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrDefault"/>',value:''},{text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrUnderline"/>',value:'underline',style:'text-decoration:underline'},{text:'<bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrNormal1"/>',value:'none',style:'text-decoration:none'}]
    };
</script>
</script>
<div style="margin-left: 3px; margin-top: -25px">
    <table width=100% border=0 cellspacing=0 cellpadding=0 style="margin-top:12px">
        <tr><td>
            <div style="height:450px;width:850px;overflow-x:auto ">
                <TABLE id=id_g_styleDetail class=tb_normal style="width: 850px" fd_type="detailsTable"  showStatisticRow="false" showRow="0" showIndex="false">
                    <TR class=tr_normal_title style="HEIGHT: 33px" type="titleRow">
                        <td row='1' column='0' align='center' coltype='selectCol' style='width: 15px;'></td>

                        <td><bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextGlobalTestContent"/></td>

                        <TD class=td_normal_title align=center column="2" row="0">
                            <bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelContorlType"/>

                        </TD>
                        <TD class=td_normal_title align=center column="3" row="0">
                            <bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrFont"/>
                        </TD>
                        <TD class=td_normal_title align=center column="8" row="0">
                            <bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrSize"/>
                        </TD>
                        <TD column="7" row="0">
                            <bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrColor"/>
                        </TD>
                        <TD column="6" row="0">
                            <bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrBold"/>

                        </TD>
                        <TD column="5" row="0">
                            <bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrItalic"/>
                        </TD>
                        <TD column="4" row="0">
                            <bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrUnderline"/>
                        </TD>

                        <TD class=td_normal_title style="width:30px;" align=center column="9" row="0" colType="blankTitleCol"></TD>
                    </TR>
                    <%-- 基准行 --%>
                    <TR KMSS_IsReferRow="1" style="display:none"  type="templateRow">

                        <td row='1' column='0' align='center' coltype='selectCol' style='width: 15px;'><input type='checkbox' name='DocList_Selected' /></td>


                        <TD>
                            <LABEL name="g_styleDetail.!{index}.preLabel"><bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextGlobalTestContent"/></LABEL>
                        </TD>
                        <TD align=center column="2" row="1">
                            <%-- 明细表行ID --%>
                            <xform:text showStatus="noShow"  property="g_styleDetail.!{index}.fdId" />
                            <select name="g_styleDetail.!{index}.controlType" style='width: 95px;'>
                                <script>
                                    for(var i=0;i<GlobalStyle_Config.controlType.length;i++){
                                        document.write("<option value='"+GlobalStyle_Config.controlType[i].value+"'>"+GlobalStyle_Config.controlType[i].text+"</option>");
                                    }
                                </script>
                            </select>
                        </TD>
                        <TD align=center column="3" row="1">
                            <select name="g_styleDetail.!{index}.g_fontFamily" style='width: 95px;'>
                                <script>
                                    for(var i=0;i<GlobalStyle_Config.font.style.length;i++){
                                        document.write("<option value='"+GlobalStyle_Config.font.style[i].value+"'>"+GlobalStyle_Config.font.style[i].text+"</option>");
                                    }
                                </script>
                            </select>
                        </TD>
                        <TD align=center column="8" row="1">
                            <select name="g_styleDetail.!{index}.g_fontSize">
                                <script>
                                    for(var i=0;i<GlobalStyle_Config.font.size.length;i++){
                                        document.write("<option value='"+GlobalStyle_Config.font.size[i].value+"'>"+GlobalStyle_Config.font.size[i].text+"</option>");
                                    }
                                </script>
                            </select>
                        </TD>
                        <TD column="7" row="1" align=center>
                            <input name="g_styleDetail.!{index}.g_fontColor" class="inputsgl" style="width:60px"/>
                        </TD>
                        <TD column="6" row="1" align=center>
                            <select name="g_styleDetail.!{index}.g_fontWeight">
                                <script>
                                    for(var i=0;i<GlobalStyle_Config.font.b.length;i++){
                                        document.write("<option value='"+GlobalStyle_Config.font.b[i].value+"'>"+GlobalStyle_Config.font.b[i].text+"</option>");
                                    }
                                </script>
                            </select>	</TD>
                        <TD column="5" row="1" align=center>
                            <select name="g_styleDetail.!{index}.g_fontStyle">
                                <script>
                                    for(var i=0;i<GlobalStyle_Config.font.i.length;i++){
                                        document.write("<option value='"+GlobalStyle_Config.font.i[i].value+"'>"+GlobalStyle_Config.font.i[i].text+"</option>");
                                    }
                                </script>
                            </select>
                        </TD>
                        <TD column="4" row="1" align=center>
                            <select name="g_styleDetail.!{index}.g_textDecoration">
                                <script>
                                    for(var i=0;i<GlobalStyle_Config.font.underline.length;i++){
                                        document.write("<option value='"+GlobalStyle_Config.font.underline[i].value+"'>"+GlobalStyle_Config.font.underline[i].text+"</option>");
                                    }
                                </script>
                            </select>
                        </TD>

                        <TD style="WIDTH: 30px" align=center column="9" row="1" colType="copyCol"><span style='cursor:pointer;display:inline-block;' class='optStyle opt_del_style' title='<bean:message bundle="sys-xform" key="xform.button.delete" />' onmousedown='DocList_DeleteRow_ClearLast();XFom_RestValidationElements();'></span>&nbsp;&nbsp;</TD>
                    </TR>

                    <TR class=tr_normal_opt type="optRow">

                        <TD colSpan=10 align=center column="0" row="3" colType="optCol"><nobr><a id="a_scroll_end" name="1" href="#1">&nbsp</a><span style='cursor:pointer;display:inline-block;' class='optStyle opt_add_style'  title='<bean:message bundle="sys-xform" key="xform.button.add" />' onclick='DocList_AddRow();XFom_RestValidationElements();div_scroll_end()'></span>&nbsp;&nbsp;<span style='cursor:pointer;display:inline-block;' class='optStyle opt_up_style' title='<bean:message bundle="sys-xform" key="xform.button.moveup" />' onclick='DocList_MoveRowBySelect(-1);'></span>&nbsp;&nbsp;<span style='cursor:pointer;display:inline-block;' class='optStyle opt_down_style'  title='<bean:message bundle="sys-xform" key="xform.button.movedown" />' onclick='DocList_MoveRowBySelect(1);'></span></nobr></TD>
                    </TR>
                </TABLE>
            </div>
        </td>

        </tr>

    </table>
</div>
<div style="text-align: center;margin-top: 10px;">
    <input id="btnOk" type=button class="btndialog" style="width:50px"
           onclick="Ok_Submit();" value="<bean:message key="button.ok"/>">&nbsp;
    <input id="btnCancel" type=button class="btndialog" style="width:50px"
           onclick="window.parent.close();" value="<bean:message key="button.cancel"/>">
</div>
<script>DocList_Info.push('id_g_styleDetail');</script>
<script>
    function div_scroll_end(){
        document.getElementById("a_scroll_end").click();
    }
    $(function(){
        $(document).on('table-add','table[showStatisticRow][id=id_g_styleDetail]',function(e,row){
            $(row).find("input[name*='g_fontColor']").colorpicker({
                fillcolor:true,
                success:function(objs,val){
                    $(objs[0]).css("color","");
                    changeGlobalPreStyle(objs[0]);
                },
                reset:function(obj){
                    //清楚颜色的时候需要清除预览样式
                    var preLabel=$(GetDomOwnerDomTag("tr",obj[0])).find("[name*=preLabel]");
                    $(GetDomOwnerDomTag("tr",obj[0])).find("[name*=g_fontColor]").val("")
                    preLabel.css("color","");
                }
            });
        });
        $(document).on('change','select',function(){

            changeGlobalPreStyle(this);
        });
    });
    function changeGlobalPreStyle(obj){
        var preLabel=$(GetDomOwnerDomTag("tr",obj)).find("[name*=preLabel]");
        preLabel.css("fontFamily",$(GetDomOwnerDomTag("tr",obj)).find("[name*=g_fontFamily]").val());
        preLabel.css("fontSize",$(GetDomOwnerDomTag("tr",obj)).find("[name*=g_fontSize]").val());
        preLabel.css("fontWeight",$(GetDomOwnerDomTag("tr",obj)).find("[name*=g_fontWeight]").val());
        preLabel.css("color",$(GetDomOwnerDomTag("tr",obj)).find("[name*=g_fontColor]").val());
        preLabel.css("fontStyle",$(GetDomOwnerDomTag("tr",obj)).find("[name*=g_fontStyle]").val());
        preLabel.css("textDecoration",$(GetDomOwnerDomTag("tr",obj)).find("[name*=g_textDecoration]").val());
    }
    function getGlobalTemplateStyleDetail(){
        var g={};
        $("#id_g_styleDetail").find("tr").has("input[name=DocList_Selected]").each(function(){
            var o={};
            var key=$(this).find("select[name*=controlType]").val();
            if($(this).find("select[name*=g_fontFamily]").val()) o["font-family"]=$(this).find("select[name*=g_fontFamily]").val();
            if($(this).find("select[name*=g_fontSize]").val()) o["font-size"]=$(this).find("select[name*=g_fontSize]").val();
            if($(this).find("select[name*=g_fontWeight]").val()) o["font-weight"]=$(this).find("select[name*=g_fontWeight]").val();
            if($(this).find("input[name*=g_fontColor]").val()) o["color"]=$(this).find("input[name*=g_fontColor]").val();
            if($(this).find("select[name*=g_fontStyle]").val()) o["font-style"]=$(this).find("select[name*=g_fontStyle]").val();
            if($(this).find("select[name*=g_textDecoration]").val()) o["text-decoration"]=$(this).find("select[name*=g_textDecoration]").val();
            g[key]=o;
        });
        return JSON.stringify(g);
    }


    function Ok_Submit() {
        rtnValue = getGlobalTemplateStyleDetail();
        callback(rtnValue);
        window.close();
    }

    function merge(cssDesignerJson) {
        for(var o in cssDesignerJson){
            for (var i = 0; i < GlobalStyle_Config.controlType.length; i++) {
                var controlInfo = GlobalStyle_Config.controlType[i];
                if (o.indexOf(controlInfo.value) > -1 && o != controlInfo.value) {
                    if (!cssDesignerJson[controlInfo.value]) {
                        cssDesignerJson[controlInfo.value] = {};
                    }
                    $.extend(cssDesignerJson[controlInfo.value],cssDesignerJson[o]);
                    delete cssDesignerJson[o];
                }
            }
        }
    }

    $(function(){
        $("body").css("overflow", "hidden");
        //监听明细表初始化事件
        $(document).on('detaillist-init','table[showStatisticRow][id=id_g_styleDetail]',function(){
            if(!objData.cssDesigner){
                return ;
            }
            var cssDesignerJson=JSON.parse(objData.cssDesigner);
            // merge(cssDesignerJson);
            for(var o in cssDesignerJson){
                for (var i = 0; i < GlobalStyle_Config.controlType.length; i++) {
                    var controlInfo = GlobalStyle_Config.controlType[i];
                    if (controlInfo.value.indexOf(o) > -1) {
                        var row=[];
                        row["g_styleDetail.!{index}.controlType"]=controlInfo.value;
                        if(cssDesignerJson[o]["font-family"]) row["g_styleDetail.!{index}.g_fontFamily"]=cssDesignerJson[o]["font-family"];
                        if(cssDesignerJson[o]["font-size"]) row["g_styleDetail.!{index}.g_fontSize"]=cssDesignerJson[o]["font-size"];
                        if(cssDesignerJson[o]["font-weight"]) row["g_styleDetail.!{index}.g_fontWeight"]=cssDesignerJson[o]["font-weight"];
                        if(cssDesignerJson[o]["color"]) row["g_styleDetail.!{index}.g_fontColor"]=cssDesignerJson[o]["color"];
                        if(cssDesignerJson[o]["font-style"]) row["g_styleDetail.!{index}.g_fontStyle"]=cssDesignerJson[o]["font-style"];
                        if(cssDesignerJson[o]["text-decoration"]) row["g_styleDetail.!{index}.g_textDecoration"]=cssDesignerJson[o]["text-decoration"];
                        DocList_AddRow("id_g_styleDetail",null,row);
                    }
                }
            }

            $("#id_g_styleDetail").find("tr").has("input[name=DocList_Selected]").each(function(){

                var preLabel=$(this).find("label[name*=preLabel]");
                if($(this).find("select[name*=g_fontFamily]").val()) preLabel.css("fontFamily",$(this).find("select[name*=g_fontFamily]").val());
                if($(this).find("select[name*=g_fontSize]").val())  preLabel.css("fontSize",$(this).find("select[name*=g_fontSize]").val());
                if($(this).find("select[name*=g_fontWeight]").val()) preLabel.css("fontWeight",$(this).find("select[name*=g_fontWeight]").val());
                if($(this).find("input[name*=g_fontColor]").val()) preLabel.css("color",$(this).find("input[name*=g_fontColor]").val());
                if($(this).find("select[name*=g_fontStyle]").val()) preLabel.css("fontStyle",$(this).find("select[name*=g_fontStyle]").val());
                if($(this).find("select[name*=g_textDecoration]").val()) preLabel.css("textDecoration",$(this).find("select[name*=g_textDecoration]").val());
            });
        });


    });
</script>

<%@ include file="/resource/jsp/edit_down.jsp"%>