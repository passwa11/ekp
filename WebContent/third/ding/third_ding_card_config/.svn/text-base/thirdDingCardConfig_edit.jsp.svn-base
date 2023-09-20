<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%@ page import="com.landray.kmss.third.ding.forms.ThirdDingCardConfigForm" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>

<%
    pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
    pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
    pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
    pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
    if(UserUtil.getUser().getFdParentOrg() != null) {
        pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
    } else {
        pageContext.setAttribute("currentOrg", "");
    }
    ThirdDingCardConfigForm thirdDingCardConfigForm= (ThirdDingCardConfigForm) request.getAttribute("thirdDingCardConfigForm");
    String fdConfig=thirdDingCardConfigForm.getFdConfig();
    if(StringUtil.isNotNull(fdConfig)){
        JSONObject fdDetailJSON = JSONObject.fromObject(fdConfig);
        request.setAttribute("configData", fdDetailJSON.getJSONArray("data"));
    }
%>
    
    <template:include ref="default.edit">
        <template:replace name="head">
            <style type="text/css">
                .lui_paragraph_title span{
                    display: inline-block;
                    margin: -2px 5px 0px 0px;
                }
            </style>
            <script type="text/javascript">
                var formInitData = {

                };
                var messageInfo = {

                };

                var initData = {
                    contextPath: '${LUI_ContextPath}'
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form.js");
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/ding/third_ding_card_config/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${thirdDingCardConfigForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('third-ding:table.thirdDingCardConfig') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${thirdDingCardConfigForm.fdName} - " />
                    <c:out value="${ lfn:message('third-ding:table.thirdDingCardConfig') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ thirdDingCardConfigForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="saveConfig('update')" />
                    </c:when>
                    <c:when test="${ thirdDingCardConfigForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="saveConfig('save')" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="content">
            <html:form action="/third/ding/third_ding_card_config/thirdDingCardConfig.do">
                <div class='lui_form_title_frame'>
                    <div class='lui_form_subject'>
                        ${lfn:message('third-ding:table.thirdDingCardConfig')}
                    </div>
                    <div class='lui_form_baseinfo'>

                    </div>
                </div>
                <table class="tb_normal" width="100%">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('third-ding:thirdDingCardConfig.fdName')}
                        </td>
                        <td width="35%">
                            <%-- 名称--%>
                            <div id="_xform_fdName" _xform_type="text">
                                <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('third-ding:thirdDingCardConfig.fdCardId')}
                        </td>
                        <td width="35%">
                            <%-- 卡片ID--%>
                            <div id="_xform_fdCardId" _xform_type="text">
                                <xform:text property="fdCardId" subject="卡片ID" validators="cardIdCheck" showStatus="edit" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('third-ding:thirdDingCardConfig.fdType')}
                        </td>
                        <td width="35%">
                            <%-- 卡片类型--%>
                            <div id="_xform_fdType" _xform_type="select">
                                <xform:select property="fdType" required="true" showPleaseSelect="false"  htmlElementProperties="id='fdType'" showStatus="edit">
                                    <xform:enumsDataSource enumsType="third_ding_card_type" />
                                </xform:select>
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('third-ding:thirdDingCardConfig.fdStatus')}
                        </td>
                        <td width="35%">
                            <%-- 卡片状态--%>
                            <div id="_xform_fdStatus" _xform_type="radio">
                                <xform:radio property="fdStatus" htmlElementProperties="id='fdStatus'" showStatus="edit">
                                    <xform:enumsDataSource enumsType="third_ding_card_status" />
                                </xform:radio>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('third-ding:thirdDingCardConfig.fdModelNameText')}
                        </td>
                        <td width="35%">
                                <%-- 所属模块名称--%>
                            <div id="_xform_fdModelName" _xform_type="select">
                                <html:hidden property="fdModelName" />
                                <xform:dialog required="true" subject="${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.fdModleName') }" propertyId="fdModelName" style="width:90%"
                                              propertyName="fdModelNameText" dialogJs="XForm_selectModelNameDialog();">
                                </xform:dialog>
                            </div>
                        </td>

                        <td class="td_normal_title" width="15%">
                            <div name="templateData">
                                    ${lfn:message('third-ding:thirdDingCardConfig.fdTemplateNameText')}
                            </div>
                        </td>

                        <td width="35%" >
                                <%-- 表单模版id--%>
                            <div id="_xform_fdTemplateId" _xform_type="text" name="templateData">
                                <input type="hidden" value="${thirdDingCardConfigForm.fdTemplateId }" id='fdTemplateId' name='fdTemplateId' />
                                <span onclick="selectForm();">
                                <xform:text property="fdTemplateName"  htmlElementProperties="id='fdTemplateName' readonly  autocomplete='off'"  ></xform:text>
                                <a href="javascript:void(0);">选择</a>
                              </span>
                            </div>
                        </td>
                    </tr>
                    <!-- 卡片参数配置 -->
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('third-ding:thirdDingCardConfig.fdConfig')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <span class="txtstrong">后台内置了一些参数：<br>
                                公有数据(模板可直接使用)：pcUrl(pc端地址), mUrl(移动端地址), agreeText(肯定按钮的文案), refuseText(否定按钮的文案), displayText(状态按钮的文案)<br>
                                私有数据(由业务传递)：canOperate(Y表示可出现审批按钮), showStatus(Y表示可出现状态按钮)<br>
                            </span>
                            <div width=100%>
                                <table id="card_param_config" style="text-align: center; margin: 0px 0px;" class="tb_normal" width=100% >
                                    <tr>
                                        <td  style="width: 20%">钉钉卡片变量</td>
                                        <td  style="width: 60%">绑定EKP字段</td>
                                        <td  style="width: 20%">操作</td>
                                    </tr>
                                    <!-- 基准行-->
                                    <tr KMSS_IsReferRow="1" class="sys_property">
                                        <td align="center"  width="15%" style="width: 20%">
                                            <xform:text property="param[!{index}].key" style="width:85%;" htmlElementProperties="placeholder='请填写变量'" showStatus="edit" required="true" subject="参数"/>
                                        </td>

                                        <td width="60%" align="left">
                                            <div class="" style="display: block">
                                                <select name="selectData" class="fdDetail_Form_select" validate="required" index="1" onchange="fdDetail_Form_select_change(!{index},this)">
                                                    <option>==请选择==</option>
                                                </select>
                                            </div>
                                        </td>
                                        <td>
                                            <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                                                <span style="margin-top: 2px;padding-left: 15px"><img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" /> </span>${lfn:message('third-weixin-work:wxOms.sync.disable.handle0')}
                                            </a>
                                        </td>
                                    </tr>
                                    <!--内容行-->
                                    <c:forEach items="${configData}" var="formItem" varStatus="vstatus">
                                        <tr KMSS_IsContentRow="1" class="sys_property">
                                            <td align="center"  style="width: 20%">
                                                <xform:text property="param[${vstatus.index}].key" value="${formItem.param}" style="width:85%;" htmlElementProperties="placeholder='请填写变量'" showStatus="edit" required="true" subject="参数"/>
                                            </td>

                                            <td width="60%" align="left">
                                                <div class="" style="display: block">
                                                    <select name="selectData" class="fdDetail_Form_select" att="${formItem.key}" validate="required" index="1" onchange="fdDetail_Form_select_change(${vstatus.index},this)">
                                                        <option value="">==请选择==</option>
                                                    </select>
                                                    <c:if test="${formItem.key=='$constant$'}">
                                                        <span name='constantTab'><input type='text' subject='常量值' fromform='false' value="${formItem.constant}" name='constant' validate='required maxLength(200)' class='inputsgl'/><span class='txtstrong'>*</span></span>
                                                    </c:if>
                                                </div>
                                            </td>
                                            <td>
                                                <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                                                    <span style="margin-top: 2px;padding-left: 15px"><img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" /> </span>${lfn:message('third-weixin-work:wxOms.sync.disable.handle0')}
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    <tr>
                                        <td colspan="3" >
                                            <div style="text-align: center ">
                                                <span style="color:red;text-align: left;padding-right: 10px ">${lfn:message('third-weixin-work:third.weixin.work.config.field')}</span>
                                                <ui:button text="+添加变量" onclick="addRow();" style="vertical-align: top;"></ui:button>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                                <input type="hidden" name="fdDetail_Flag" value="1">
                                <script>
                                    Com_IncludeFile("doclist.js");
                                </script>
                                <script>
                                    DocList_Info.push('card_param_config');
                                </script>

                            </div>
                        </td>
                    </tr>

                </table>
                <html:hidden property="fdId" />
                <html:hidden property="fdConfig" />
                <html:hidden property="method_GET" />
            </html:form>
            <script>
                var _html=""; //下拉内容
                init();
                function init(){
                    //是否展示表单模版选择
                    var modelName= "${thirdDingCardConfigForm.fdModelName }";
                    var fdTemplateId = "${thirdDingCardConfigForm.fdTemplateId}";
                    if("com.landray.kmss.km.review.model.KmReviewMain" == modelName){
                        $("[name='templateData']").css('display','block');
                    }else{
                        $("[name='templateData']").css('display','none');
                    }
                    //获取模块和表单字段
                    initFormFiled(modelName,fdTemplateId);
                }

                function initFormFiled(modelName,fdTemplateId){
                    if(modelName && modelName != null){
                        _html = getModeleFields(modelName);
                        //为每个下拉生成option
                        var $allSelect = $('#card_param_config').find('.fdDetail_Form_select');
                        var switchModel=_html;
                        $allSelect.each(function(index,element){
                            //加上常量
                            var  tempHtml=tempHtml+"<option value='$constant$' fromForm='false' type='$constant$'>"+"==常量=="+"</option>"
                            tempHtml=tempHtml+_html;
                            $(this).append(tempHtml);
                        });
                        //生成表单字段
                        if(fdTemplateId && fdTemplateId != null){
                            getTemplateFields(fdTemplateId);
                        }
                        //选中对应的字段
                        $allSelect.each(function(index,element){
                            var att = $(this).attr("att");
                            if(att){
                               var keys = att.split("\.");
                               if(keys.length==1){
                                   var numbers = $(this).find("option"); //获取select下拉框的所有值
                                   for (var j = 0; j < numbers.length; j++) {
                                       if ($(numbers[j]).val() == keys[0]) {
                                           $(numbers[j]).attr("selected", "selected");
                                       };
                                   }
                               }else{
                                   //复合
                                   for(var k = 0;k<keys.length;k++){
                                       var tagetSelect=$(this).closest("div").find("select:last");
                                       var numbers = tagetSelect.find("option"); //获取select下拉框的所有值
                                       var type="";
                                       for (var j = 0; j < numbers.length; j++) {
                                           if ($(numbers[j]).val() == keys[k]) {
                                              $(numbers[j]).attr("selected", "selected");
                                              type= $(numbers[j]).attr("type");
                                           };
                                       }
                                       if(type.indexOf("com.landray.kmss") == 0){
                                           fdDetail_Form_select_change(k+1,tagetSelect);
                                       }
                                   }
                               }
                            }
                        });

                    }

                }
                //弹出系统内数据对话框
                function XForm_selectModelNameDialog(){
                    window.focus();
                    seajs.use(['lui/dialog'], function(dialog) {
                        var url = "/third/ding/third_ding_todo_template/thirdDingTodoTemplate_edit_dialog.jsp";
                        var height = document.documentElement.clientHeight * 0.78;
                        var width = document.documentElement.clientWidth * 0.6;
                        var dialog = dialog.iframe(url,"所属模块选择",xform_main_data_setAttr,{width:width,height : height,close:false});
                    });
                }
                //关闭、确定对话框的回调函数
                function xform_main_data_setAttr(value){
                    if(value){
                        if(value.modelName){
                            $("input[name='fdModelName']").val(value.modelName);
                            if("com.landray.kmss.km.review.model.KmReviewMain" == value.modelName){
                                $("[name='templateData']").css('display','block');
                            }else{
                                //后台校验模块是否存在了
                                var modelExist = false;
                                $.ajax({
                                    url:"${LUI_ContextPath}/third/ding/third_ding_card_config/thirdDingCardConfig.do?method=checkModel&fdModelName="+value.modelName,
                                    type:"GET",
                                    async:false,
                                    success:function(result){
                                        if("1" == result){
                                            alert("在所属模块已存在一个卡片模版，请选择其他模块！");
                                            modelExist = true;
                                        }

                                    }
                                });
                                if(modelExist){
                                    return;
                                }
                                $("[name='templateData']").css('display','none');
                                $("input[name='fdIscustom']").val("0");
                                $("input[name='fdTemplateClass']").val("");
                                $("input[name='fdTemplateId']").val("");
                            }
                        }
                        if(value.modelNameText){
                            $("input[name='fdModelNameText']").val(value.modelNameText);
                        }

                        if(value.modelName && value.modelNameText){
                            $KMSSValidation().validateElement($("input[name='fdModelNameText']")[0]);
                        }
                        if(value.data){
                            // 设置属性
                            var data;
                            try{
                                data = $.parseJSON(value.data);
                            }catch(e){
                                alert("${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.lookLog') }");
                            }
                            createSelectOption(data,"true");
                        }

                    }
                }

                function createSelectOption(data,isClear){
                    _html="";
                    if(data != null && data != ''){

                        var fieldArray = data['fieldArray'];
                        for(var i=0,l=fieldArray.length;i<l;i++){
                            var field = fieldArray[i]['field'];
                            var type = fieldArray[i]['fieldType'];
                            var fieldText = fieldArray[i]['fieldText'];
                            console.log("field:"+fieldArray[i]['field']);
                            console.log("fieldText:"+fieldArray[i]['fieldText']);
                            _html=_html+"<option value='"+field+"' fromForm='false' type='"+type+"'>"+fieldText+"</option>";
                        }
                    }
                    if(isClear=="true"){
                        //清理所有
                        $('#card_param_config').find("a[onclick='DocList_DeleteRow();']").click();
                    }
                }

                function addRow(){
                    DocList_AddRow();
                    $('#card_param_config tr:nth-last-child(2)').find("[name='selectData']").find("option").remove();
                    //加上 请选择 的option
                    var tempHtml = "<option value='' att=''>"+"==${lfn:message('third-ding:thirdDingTodoTemplate.select')}=="+"</option>";
                    //加上常量
                    tempHtml=tempHtml+"<option value='$constant$' fromForm='false' type='$constant$'>"+"==常量=="+"</option>"
                    tempHtml=tempHtml+_html;
                    $('#card_param_config tr:nth-last-child(2)').find("[name='selectData']").append(tempHtml);
                }

                function selectForm(){
                    var mainModelName = $("input[name='fdModelName']").val();
                    if(!mainModelName || mainModelName==null){
                        alert("请先选择表单模块");
                        return;
                    }
                    seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($,dialog,topic) {

                            dialog.categoryForNewFile(
                                'com.landray.kmss.km.review.model.KmReviewTemplate',
                                null,false,null,function(rtn) {
                                    // 门户无分类状态下（一般于门户快捷操作）创建文档，取消操作同时关闭当前窗口
                                    console.log(rtn);
                                    if(!rtn){
                                        return;
                                    }
                                    //校验表单是否存在了
                                    var modelExist = false;
                                    $.ajax({
                                        url:"${LUI_ContextPath}/third/ding/third_ding_card_config/thirdDingCardConfig.do?method=checkModel&fdTemplateId="+rtn.id+"&fdModelName="+mainModelName,
                                        type:"GET",
                                        async:false,
                                        success:function(result){
                                            if("1" == result){
                                                alert("表单"+rtn.name+" 模板已存在！");
                                                modelExist = true;
                                            }

                                        }
                                    });
                                    if(modelExist){
                                        return;
                                    }

                                    $("#fdTemplateName").val(decodeURIComponent(rtn.name));
                                    $("#fdTemplateId").val(rtn.id);
                                    //填充表单信息到下拉框
                                    getTemplateFields(rtn.id);
                                },'',null,null,true);

                        }
                    );
                }

                //把原有的表单内容清空，然后再填充  fromForm="true"
                function switchSelectFormOption(rs){
                    console.log("_html:",_html);
                    console.log("rs:",rs);
                    if(_html&&_html.indexOf("'</option>'") >-1){
                        var tempHtml = "";
                        var htmlArray=new Array();
                        htmlArray=_html.split('</option>');
                        for(var j=0;j<htmlArray.length;j++){
                            if(htmlArray[j].indexOf("fromForm='true'") != -1){
                                continue;
                            }
                            if(htmlArray[j] != ""){
                                tempHtml += htmlArray[j]+"</option>";
                            }

                        }
                        _html=tempHtml;
                    }
                    var form_html= "";
                    if(rs != null && rs != ''){
                        for(var i=0,l=rs.length;i<l;i++){
                            var field = rs[i]['key'];
                            var fieldText = rs[i]['name'];
                            var type = rs[i]['type'];
                            form_html = form_html+"<option value='"+field+"' fromForm='true' type='"+type+"'>"+fieldText+"</option>";
                            _html=_html+"<option value='"+field+"' fromForm='true' type='"+type+"'>"+fieldText+"</option>";
                        }
                    }
                    console.log("_html 222:",_html);
                    //切换表单后，要把已有的事项数据更新
                    var $allSelect = $('#card_param_config').find('.fdDetail_Form_select');
                    var switchModel=_html;
                    $allSelect.each(function(index,element){
                        // $(this).empty();  不清所有，只是清表单字段
                        var numbers = $(this).find("option"); //获取select下拉框的所有值
                        for (var j = 0; j < numbers.length; j++) {
                            var fromform = $(numbers[j]).attr("fromform");
                            if(fromform == "true"){
                                $(numbers[j]).remove();
                            }
                        }
                        if($(this).attr("index") == 1){
                            $(this).append(form_html);
                        }

                    });

                }

                function fdDetail_Form_select_change(_index,$select){
                    _self=$($select);
                    var selectIndex =_self.attr("index");
                    var type = _self.find("option:selected").attr("type");
                    var $allSelect=_self.closest("div").find(".fdDetail_Form_select");
                    //清除 selectIndex后的select数据
                    $allSelect.each(function(index,element){
                        var tempIndex = $(this).attr("index");
                        if(tempIndex > selectIndex){
                            $(this).remove();
                        }
                    });
                    //清除常量
                    _self.closest("div").find("[name='constantTab']").remove();

                    var index = Number(selectIndex)+1;
                    if(type.indexOf("com.landray.kmss") == 0){
                        var formHtml="<select validate='required' onchange='fdDetail_Form_select_change("+_index+",this)' index='"+index+"' class='fdDetail_Form_select' style='width: 35%'><option value=''>"+"==${lfn:message('third-ding:thirdDingTodoTemplate.select')}=="+"</option></select>"

                        var $form = $(formHtml);
                        _self.closest("div").append($form);
                        getModeleFields(type,$form)

                    }else if(type=="$constant$"){
                        var formHtml="<span name='constantTab'><input type='text' subject='常量值' fromform='false' name='constant' validate='required maxLength(200)' class='inputsgl'/><span class='txtstrong'>*</span></span>"
                        var $form = $(formHtml);
                        _self.closest("div").append($form);
                    }

                }

                //获取模块字段
                function getModeleFields(modelName,taget){
                    var _formHtml="";
                    var url = "${LUI_ContextPath}/sys/xform/maindata/main_data_insystem/sysFormMainDataInsystem.do?method=findFieldDictByModelName&modelName="+modelName;
                    $.ajax({
                        url:url,
                        type:"GET",
                        async:false,
                        success:function(result){
                            var rs = $.parseJSON(result);
                            var fieldArray = rs['fieldArray'];
                            for(var i=0,l=fieldArray.length;i<l;i++){
                                var field = fieldArray[i]['field'];
                                var type = fieldArray[i]['fieldType'];
                                var fieldText = fieldArray[i]['fieldText'];
                                _formHtml=_formHtml+"<option value='"+field+"' fromForm='false' type='"+type+"'>"+fieldText+"</option>";
                            }
                            if(taget){
                                taget.append(_formHtml)
                            }
                        }
                    });
                    return _formHtml;
                }

                //获取表单字段
                function getTemplateFields(fdTemplateId){
                    var _formHtml="";
                    $.ajax({
                        url:"${KMSS_Parameter_ContextPath}third/ding/third_ding_todo_template/thirdDingTodoTemplate.do?method=findFieldDictByModelName&fdTemplateId="+fdTemplateId+"&fdTemplateClass=com.landray.kmss.km.review.model.KmReviewTemplate",
                        type:"GET",
                        async:false,
                        success:function(result){
                            //alert("表单内容："+result);
                            var rs = $.parseJSON(result);
                            switchSelectFormOption(rs);
                        }
                    });

                }

                function setConfig(){
                    var isFlag = true;
                    var fdConfig={};
                    var data = new Array();

                    //判断所属模块的模版是否存在
                    var method_GET = $("input[name='method_GET']").val();
                    if(method_GET == "add"){
                        var fdTemplateId = $("input[name='fdTemplateId']").val();
                        var fdModelName = $("input[name='fdModelName']").val();
                        //alert("fdTemplateId:"+fdTemplateId+" fdModelName:"+fdModelName)
                        $.ajax({
                            url:"${LUI_ContextPath}/third/ding/third_ding_card_config/thirdDingCardConfig.do?method=checkModel&fdTemplateId="+fdTemplateId+"&fdModelName="+fdModelName,
                            type:"GET",
                            async:false,
                            success:function(result){
                                if("1" == result){
                                    alert("在所属模块已存在卡片模版，请选择其他模块！");
                                    isFlag = false;
                                }

                            }
                        });
                    }

                    var $allSelectTr= $('#card_param_config').find('.sys_property');
                    if($allSelectTr.size()==0){
                        alert("参数列表不能为空");
                        return false;
                    }
                    $allSelectTr.each(function(index,element){

                        var $allSelectForm=$(this).find(".fdDetail_Form_select");
                        //清除 selectIndex后的select数据
                        var key="";
                        var name="";
                        var fromForm="false";
                        var lastSelectType ="";
                        var constant="";
                        $allSelectForm.each(function(index2,element){
                            var val = $(this).val();
                            if(val == null || val ==""){
                                alert("第"+(index+1)+"行第"+(index2+1)+"个下拉列表不能为空！")
                                isFlag = false;
                            }
                            if(index2 == 0){
                                fromForm = $(this).find("option:selected").attr("fromForm");
                                key =$(this).val();
                                name =$(this).find("option:selected").text();
                            }else{
                                key =key+"."+ $(this).val();
                                name = name+"."+ $(this).find("option:selected").text();
                            }
                            if(index2 == ($allSelectForm.size()-1)){
                                lastSelectType = $(this).find("option:selected").attr("type");
                            }
                        });

                        //常量
                        if(lastSelectType=='$constant$'){
                            constant=$(this).find("[name='constant']").val();
                        }

                        //遍历标题
                        var param="";
                        var $allLangForm=$(this).find("input[name='param["+index+"].key']");
                        $allLangForm.each(function(index3,element){
                            param = $(this).val();
                        });

                        var obj_1 = { 'key':key, 'name':name,'fromForm':fromForm,'param':param,'lastSelectType':lastSelectType,'constant':constant};
                        data.push(obj_1);

                    });
                    fdConfig['data']=data;
                    $("input[name='fdConfig']").val(JSON.stringify(fdConfig));
                    return isFlag;
                }

                function saveConfig(method){
                    if($KMSSValidation().validate()){
                        if(setConfig()){
                            Com_Submit(document.thirdDingCardConfigForm, method);
                        }
                    }
                }

                $KMSSValidation().addValidator('cardIdCheck','{name}',function(v,e,o){
                    var fdCardId = $('[name="fdCardId"]').val();
                    var flag=false;
                    var errMsg="卡片ID 不能为空";
                    var method_GET = $("input[name='method_GET']").val();
                    var fdId="";
                    if("edit"==method_GET){
                        fdId=$("input[name='fdId']").val();
                    }
                    if(fdCardId &&fdCardId!="" && fdCardId!=null){
                        flag = true;
                        //卡片ID不进行去重校验，允许多个模块用同一个卡片模板
                       /* $.ajax({
                            url:"${LUI_ContextPath}/third/ding/third_ding_card_config/thirdDingCardConfig.do?method=checkCardId&fdCardId="+fdCardId+"&fdId="+fdId,
                            type:"GET",
                            async:false,
                            success:function(result){
                                if("0" == result){
                                    flag = true;
                                }else if("1" == result){
                                    errMsg="卡片ID重复!";
                                }else {
                                    errMsg="卡片ID查询异常!";
                                }
                            }
                        });*/
                    }
                    if(!flag){
                        $('[name="fdCardId"]').attr("subject",errMsg)
                    }
                    return flag;
                });
            </script>
        </template:replace>


    </template:include>