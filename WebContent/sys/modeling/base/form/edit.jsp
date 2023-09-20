<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="default.edit" sidebar="no">
    <template:replace name="content">
        <style>
            .tempTB {
                width: 100% !important;
                max-width: 100% !important;
            }
            #SubFormTable_modelingApp .xform_client_type{
                margin-bottom: -1px;
                border-top: 0px !important;
            }

            #SubFormTable_modelingApp #XForm_modelingApp_CustomTemplateRow{
                border: 1px solid #e8e8e8;
            }

            .lui_toolbar_frame_float {
                height: 50px !important;
            }

            .lui_toolbar_content {
                max-width: 100% !important;
                height: 50px !important;
                width: 100% !important;
                margin: 0px !important;
            }

            .lui_toolbar_frame_float .lui_widget_btn {
                padding: 13px 16px 7px 0 !important;
            }

            .lui_toolbar_frame_float_mark {
                height: 50px !important;
            }
            .mainModelNameTip{
            	display:inline-block;
			    margin-left:20px;
			    font-size: 14px;
			    padding-right: 10px;
			    padding-left: 20px;
            	color:#FF9431;
            	background: url(../base/resources/images/form-tip@2x.png) no-repeat left;
    			background: url(../base/resources/images/form-tip.png) no-repeat \9;
    			background-size: 15px 15px;
            }
            .xform_client_btn.is-active{
            	margin-top: -10px;
            }
            .lui_dropdown_tooltip_menu {
            	line-height: normal;
            	margin-bottom: 10px;
            	text-align: left;
			}
			.lui_prompt_tooltip_drop{
				margin-bottom: 20px;
			}
        </style>
        <ui:toolbar var-navwidth="90%" id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <ui:button text="${ lfn:message('button.save') }" order="1" height="24" width="70"
                       onclick="submitForm('update');"></ui:button>
            <ui:button text="${ lfn:message('sys-modeling-base:modeling.form.quick.config') }" order="2" height="24" width="90"
                       onclick="quickConfig('self');"></ui:button>
            <ui:button text="${lfn:message('sys-modeling-base:modeling.form.FormModificationLog')}" order="3"
                       height="24" width="140" onclick="toFormLog();"></ui:button>
        </ui:toolbar>
        <html:form action="/sys/modeling/base/modelingAppModel.do">
            <div id="baseInfo">
                <table class="tb_normal" width="100%" style="margin-top:10px;">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            <span>${lfn:message('sys-modeling-base:modeling.model.form.name')}</span>
                        </td>
                        <td width="35%">
                            <xform:xtext property="fdName" required="true" validators="maxLength(100)"></xform:xtext>
                        </td>
                        <td class="td_normal_title" width="15%">
                            <span>${lfn:message('sys-modeling-base:modeling.model.appName')}</span>
                        </td>
                        <td width="35%">
                            <span>${modelingAppModelForm.fdApplicationName}</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            <span>${lfn:message('sys-modeling-base:modeling.model.icon')}</span>
                        </td>
                        <td width="35%">
                            <a href="javascript:void(0);" onclick="selectIcon();">
                                <i class="iconfont_nav ${modelingAppModelForm.fdIcon}"
                                   style="color:#999;font-size:40px;"></i>
                            </a>
                            <html:hidden property="fdIcon"/>
                        </td>
                        <td class="td_normal_title"
                            width=17%>${lfn:message('sys-modeling-base:modeling.model.subjectRegulation')}</td>
                        <td width=83% colspan="3">
                            <html:hidden property="fdSubjectRegulationValue"/>
                            <xform:text property="fdSubjectRegulationText" style="width:50%" required="true" htmlElementProperties="readOnly"
                                        showStatus="edit"/> <a href="#"
                                                               onclick="genTitleRegByFormula('fdSubjectRegulationValue','fdSubjectRegulationText')">${lfn:message('sys-modeling-base:modeling.model.subjectRegulation.select')}</a>
                            <br/>
                                ${lfn:message('sys-modeling-base:modeling.model.subjectRegulation.tips')}
                        </td>
                    </tr>
                    <tr>
                        <!-- 表单模式  -->
                        <c:set var="fdKey" scope="request" value="modelingApp"/>
                        <c:set var="fdMainModelName" scope="request"
                               value="${JsParam.enableFlow eq 'true' ? 'com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain' : 'com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain'}"/>
                        <td class="td_normal_title" width="15%">
                            <span>${lfn:message('sys-modeling-base:modeling.model.form.mode')}</span>
                        </td>
                        <td width="35%">
                            <xform:select value="3" property="sysFormTemplateForms.modelingApp.fdMode"
                                          onValueChange="Form_ChgDisplay('modelingApp',this.value);"
                                          showStatus="edit"
                                          showPleaseSelect="false">
                                <xform:customizeDataSource
                                        className="com.landray.kmss.sys.xform.base.service.spring.SysFormModeDataSource"></xform:customizeDataSource>
                            </xform:select>
                            <c:if test="${JsParam.enableFlow}">
                            	<div class="mainModelNameTip">${lfn:message('sys-modeling-base:modeling.model.form.tip')}</div>
                        	</c:if>
                        </td>
                            <%-- 表名 --%>
                        <td class="td_normal_title" width="15%">
                            <span>${lfn:message('sys-modeling-base:modeling.model.fdTableName')}</span>
                        </td>
                        <td width="35%">
                            <c:choose>
                                <c:when test="${tableNameShowStatus=='noShow'}">
                                    <%--兼容并更新旧数据--%>
                                    ${modelingAppModelForm.fdTableName}
                                    <xform:text property="fdTableName" showStatus="noShow" required="true"
                                                validators="maxLength(30) checkTableNameValid checkTableNameNotRepeat"/>
                                </c:when>
                                <c:when test="${tableNameShowStatus=='edit'}">
                                    <input name="fdTableName" id="fdTableName"
                                           subject="${lfn:message('sys-modeling-base:modeling.model.fdTableName')}"
                                           class="inputsgl" type="text"
                                           validate="required maxLength(30) checkTableNameValid checkTableNameNotRepeat"
                                           style="width:50%"><span class="txtstrong">*</span>
                                    <a href="#"
                                       onclick="$('#fdTableName').val('${modelingAppModelForm.fdTableName}')">${lfn:message('sys-modeling-base:modeling.form.UseDefaultTableName')}</a>
                                </c:when>
                                <c:otherwise>
                                    ${modelingAppModelForm.fdTableName}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <!-- 权限 -->
                    <tr style="display:none;">
                        <td colspan=2>
                            <xform:config showStatus="edit">
                                <table class="tb_simple model-view-panel-table" width=100%>
                                    <c:import url="/sys/right/tmp_right_edit.jsp" charEncoding="UTF-8">
                                        <c:param name="formName" value="modelingAppModelForm"/>
                                        <c:param name="moduleModelName"
                                                 value="com.landray.kmss.sys.modeling.base.model.ModelingAppModel"/>
                                    </c:import>
                                </table>
                            </xform:config>
                        </td>
                    </tr>
                </table>
                <html:hidden property="fdEnableFlow"/>
                <html:hidden property="fdId" value="${modelingAppModelForm.fdId}" />
            </div>
            <c:import url="/sys/modeling/base/form/xform/template_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="modelingAppModelForm"/>
                <c:param name="fdKey" value="modelingApp"/>
                <c:param name="enableFlow" value="${JsParam.enableFlow}"/>
                <c:param name="fdMainModelName" value="${fdMainModelName}"/>
                <c:param name="messageKey" value="sys-modeling-base:kmReviewDocumentLableName.reviewContent"/>
                <c:param name="appName" value="${JsParam.appName}"/>
                <c:param name="appIcon" value="${JsParam.appIcon}"/>
            </c:import>
            <div class="km-archives-setting-div" style="display: none">
                <kmss:ifModuleExist path="/km/archives/">
                    <!-- 归档设置 -->
                    <c:import url="/km/archives/include/kmArchivesFileSetting_edit.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="modelingAppModelForm" />
                        <c:param name="fdKey" value="sysModelMainDoc" />
                        <c:param name="modelName" value="com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain" />
                        <c:param name="templateService" value="modelingAppModelMainService" />
                        <c:param name="moduleUrl" value="sys/modeling" />
                        <c:param name="mechanismMap" value="true" />
                        <c:param name="enable" value="true"></c:param>
                    </c:import>
                </kmss:ifModuleExist>
            </div>
        </html:form>
        <script>
            window.isFlow = '${JsParam.enableFlow}';
            window.fdAppModelId = "${modelingAppModelForm.fdId}";
            window.fdAppId = "${modelingAppModelForm.fdApplicationId}";
            window.firstSave = "${param.firstSave}";
            window.isModelShow = true;//针对是表单输入框否拼接明细表索引的属性的显示，不能移除
            Com_IncludeFile("data.js");
            Com_IncludeFile("xformCustomDesigner.js", Com_Parameter.ContextPath + 'sys/modeling/base/form/xform/', 'js', true);
            Com_IncludeFile("xformExtendFun.js", Com_Parameter.ContextPath + "sys/modeling/base/form/xform/", "js", true);
            var _validation = $KMSSValidation();
			
            //#130033修改表单名称后，左上角头部的没有跟着变更
            if("${JsParam.update}" == "true"){
            	window.parent.location.reload();
            } 
            //校验表名是否规范
            _validation.addValidator('checkTableNameValid', '{name}只允许英文、数字以及‘_’,且必须以"ekp_"开头。', function (v) {
                if (v == null || v == '') return true;
                var reg = new RegExp('^ekp_[a-zA-Z0-9_]{1,}$');
                return reg.test(v);
            });

            _validation.addValidator('checkTableNameNotRepeat', '{name}已存在，请重新输入。', function (v) {
                if (v == null || v == '') return true;
                let url = Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=isTableNameExist";
                let tableNameExist = true;
                $.ajax({
                    url: url,
                    type: 'get',
                    async: false,
                    data: $.param({"tableName": v.trim()}, true),
                    success: function (data) {
                        console.log(data);
                        if (data.status == '00') {
                            tableNameExist = data.data.tableNameExist;
                        }
                    }
                });
                return tableNameExist === false;
            });



            //跳转表单日志
            function toFormLog() {
                location.href = "${LUI_ContextPath}/sys/modeling/base/formlog/index.jsp?fdModelId=${modelingAppModelForm.fdId}"
            }

            // 对designer做定制
            function XForm_CustomDesigner(designer, win) {
                ModelingCustomDesigner(designer, win);
            }

            //解析公式，得到变量集
            function parseFormula(formula) {
                var mark = '$';
                var fieldIds = [];
                var index = formula.indexOf(mark);
                var rightStr = formula;
                while (index >= 0) {
                    var nIndex = rightStr.indexOf(mark, index + 1);
                    var scriptStr = rightStr.substring(index + 1, nIndex);
                    if (rightStr.length > 0 && rightStr.charAt(nIndex + 1) != '(') {//只读取变量，函数跳过
                        fieldIds.push(scriptStr);
                    }
                    rightStr = rightStr.substring(nIndex + 1);
                    index = rightStr.indexOf(mark);
                }
                return fieldIds;
            }

            function validateBeforeSubmit() {
                //主题校验：判断使用的控件是否被删除
                var subjectVal = $('input[name="fdSubjectRegulationValue"]').val();
                if (!subjectVal) {
                    return true;//为空时由校验框架校验
                }
                var designerIns = XForm_getXFormDesignerInstance_modelingApp().designer;
                var fdMode = $("[name='sysFormTemplateForms.modelingApp.fdMode']").val();
                if(fdMode === "4"){
                	//#123651：多表单模式时，选择的标题字段是默认模板的，但是现编辑页面是在多表1，保存标题提示字段不存在
                	var varInfo = designerIns ? designerIns.getObj(true,null,true) : [];
                }else{
                	var varInfo = designerIns ? designerIns.getObj(true) : [];
                }
                var fieldIds = parseFormula(subjectVal);
                for (var i = 0; i < fieldIds.length; i++) {
                    var fieldId = fieldIds[i];
                    var isExit = false;
                    for (var j = 0; j < varInfo.length; j++) {
                        if (varInfo[j] && varInfo[j].name === fieldId) {
                            isExit = true;
                            break;
                        }
                    }
                    if (!isExit) {
                        alert("${lfn:message('sys-modeling-base:xform.subject.error.hit')}");
                        return false;
                    }
                }
                return true;
            }
            seajs.use(["lui/jquery", "lui/dialog"], function ($, dialog) {
                window.submitForm = function (method) {
                    //做提交前的其他校验
                    var isPass = validateBeforeSubmit();
                    if (isPass) {
                        //#126856 fdMode为空会清理数据
                        var select = $("[name='sysFormTemplateForms.modelingApp.fdMode']");
                        select.removeAttr('disabled');
                        //使用ajax方式校验数据库长度以及类型是否改变
                        var designerIns = XForm_getXFormDesignerInstance_modelingApp().designer;
                        var xml = designerIns.getXML();
                        var fdFormId = $("input[name='fdId']").val();
                        $.ajax({
                            type: "POST",//方法类型
                            dataType: "json",//预期服务器返回的数据类型
                            async : true,
                            url: Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=checkBaseDatahbm" ,//url
                            data:{
                                xml:xml,
                                fdFormId: fdFormId
                            },
                            success: function (result) {
                                if(result.status == "1"){
                                    Com_Submit(document.modelingAppModelForm, method);
                                }else{
                                    dialog.alert(result.msg);
                                }
                            }
                        });
                    }
                }
                //快捷配置
                window.quickConfig= function (type){
                    dialog.iframe('/sys/modeling/base/form/quick_config.jsp?fdAppId='+fdAppId+'&fdId='+fdAppModelId+'&isFlow='+isFlow+'&type='+type,"快捷配置",
                        function (value){
                            // 回调方法
                            if(value) {
                                //后台刷新较慢，前端无法实时更新
                                setTimeout(function(){
                                    window.location.reload();
                                }, 1000)
                            }
                        },
                        {width:784,height:564}
                    );
                }



            });


            // 公式选择器
            function genTitleRegByFormula(fieldId, fieldName) {
                var designerIns = XForm_getXFormDesignerInstance_modelingApp().designer;
                //#120210 文档标题公式定义器屏蔽文档标题字段
                var allField = designerIns.getObj(true, null, true);
                allField=allField.filter(function (t) {
                    return t["name"] !== 'docSubject';
                });

                Formula_Dialog(fieldId, fieldName, allField, 'String');
            }

            seajs.use(["lui/jquery", "lui/dialog"], function ($, dialog) {
                window.selectIcon = function () {
                    var url = "/sys/modeling/base/resources/iconfont.jsp?type=module";
                    dialog.iframe(url, "${lfn:message('sys-modeling-base:modeling.app.selectIcon')}", changeIcon, {
                        width: 750,
                        height: 500
                    })
                }

                window.changeIcon = function (className) {
                    if (className) {
                        $("i.iconfont_nav").removeClass().addClass(className);
                        $("input[name='fdIcon']").val(className.split(" ")[1]);
                    }
                }
            });
            window.onload = function (){
                if(firstSave && firstSave === "true"){
                    window.quickConfig("auto");
                }
            }
        </script>
    </template:replace>
</template:include>