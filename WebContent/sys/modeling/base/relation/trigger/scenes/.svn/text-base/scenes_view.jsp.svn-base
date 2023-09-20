<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.modeling.base.util.SysModelingUtil" %>

<% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
    pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
    pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
    pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
    if (UserUtil.getUser().getFdParentOrg() != null) {
        pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
    } else {
        pageContext.setAttribute("currentOrg", "");
    } %>

<template:include ref="default.edit">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/relation/trigger/behavior/css/behavior.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modelTable.css?s_cache=${LUI_Cache}"/>
        <style type="text/css">

            .lui_paragraph_title {
                font-size: 15px;
                color: #15a4fa;
                padding: 15px 0px 5px 0px;
            }

            .lui_paragraph_title span {
                display: inline-block;
                margin: -2px 5px 0px 0px;
            }

            .inputsgl[readonly], .tb_normal .inputsgl[readonly] {
                border: 0px;
                color: #868686
            }

            .fdCronTimeBox {
                display: inline-block;
                width: 100px;
            }

            .fdCronTimeBox span {
                color: #999;
            }

            .paramSelect {
                cursor: pointer;
                color: #39c;
            }

            .fdBehaviorBox span {
                display: inline-block;
                padding: 2px 5px;
                border: 1px solid #333;
                color: #333;
                margin-left: 10px;
                border-radius: 5px;
            }

            .fdBehaviorBox span[dataMsgType='add'] {
                border: 1px solid #0aa908;
                color: #0aa908;
            }

            .fdBehaviorBox span[dataMsgType='remove'] {
                border: 1px solid #d67c1c;
                color: #d67c1c;
            }

            .fdCron_Str input[type='number']{
                padding-left: 10px;
                width:50px;
            }
        </style>
        <script type="text/javascript">
            var formInitData = {};
            var messageInfo = {};
            var behaviorPref = {
                "0": "【消息】",
                "1": "【新建】",
                "2": "【删除】",
                "3": "【更新】",
                "4": "【自定义】"

            };
            var initData = {
                contextPath: '${LUI_ContextPath}',
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("plugin.js");
            Com_IncludeFile("validation.js");
        </script>
    </template:replace>
    <template:replace name="toolbar">
        <html:form action="/sys/modeling/base/sysModelingScenes.do">
            <div class="model-mask-panel medium scenes_view" style="padding-bottom: 72px">
                <div>
                    <div class="model-mask-panel-table">
                        <table class="tb_simple modeling_form_table " mdlng-rltn-mrk="regionTable" id="TB_MainTable">
                            <tbody>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                        ${lfn:message('sys-modeling-base:sysModelingScenes.fdName')}
                                </td>
                                <td width="85%">
                                        <%-- 名称--%>
                                    <div id="_xform_fdName" _xform_type="text">
                                        <html:text  property="fdName" disabled="true" style="width:95%;"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                        ${lfn:message('sys-modeling-base:sysModelingScenes.fdType')}
                                </td>
                                <td width="85%">
                                        <%-- 触发类型--%>
                                    <div id="_xform_fdType" _xform_type="radio">
                                        <xform:radio property="fdType" onValueChange="selectScenesType"
                                                     htmlElementProperties="id='fdType'" showStatus="view">
                                            <xform:enumsDataSource enumsType="sys_modeling_scenes"/>
                                        </xform:radio>
                                    </div>
                                </td>
                            </tr>
                            <tr id="fdRunType_tr">
                                <td class="td_normal_title" width="15%">
                                        ${lfn:message('sys-modeling-base:sysModelingScenes.fdRunType')}
                                </td>
                                <td width="85%">
                                        <%-- 动作类型--%>
                                    <div id="_xform_fdRunType" _xform_type="radio">
                                        <xform:radio property="fdRunType" showStatus="view">
                                            <xform:simpleDataSource value="1">${lfn:message('sys-modeling-base:behavior.sequential.execution')}</xform:simpleDataSource>
                                            <xform:simpleDataSource value="0">${lfn:message('sys-modeling-base:behavior.synchronous.execution')}</xform:simpleDataSource>
                                        </xform:radio>
                                    </div>
                                </td>
                            </tr>
                            <tr id="fdCronType_tr">
                                <td class="td_normal_title" width="15%">
                                        ${lfn:message('sys-modeling-base:sysModelingScenes.fdCronType')}
                                </td>
                                <td width="85%">
                                    <div id="_xform_fdCronType" _xform_type="radio">
                                        <label><input type="radio" name="fdCronType" value="0" checked
                                                      ><bean:message
                                                bundle="sys-quartz"
                                                key="sysQuartzJob.cronExpression.inputType.normal"/></label>
                                        <label><input type="radio" name="fdCronType" value="1"
                                                     ><bean:message
                                                bundle="sys-quartz"
                                                key="sysQuartzJob.cronExpression.inputType.cronExpression"/></label>
                                    </div>
                                </td>
                            </tr>

                            <tr id="fdCron_Times" kmss_inputtype="0">
                                <td class="td_normal_title" width="15%">
                                    运行频率
                                </td>
                                <td colspan="3" width="85.0%">
                                        <%-- 运行频率。。--%>
                                    <div>
                                        <select name="fdCronTimes" onchange="refreshInpuType0();" disabled>
                                            <option value=""><bean:message key="page.firstOption"/></option>
                                            <option value="once"><bean:message bundle="sys-quartz"
                                                                               key="sysQuartzJob.cronExpression.frequency.once"/></option>
                                            <option value="year"><bean:message bundle="sys-quartz"
                                                                               key="sysQuartzJob.cronExpression.frequency.year"/></option>
                                            <option value="month"><bean:message bundle="sys-quartz"
                                                                                key="sysQuartzJob.cronExpression.frequency.month"/></option>
                                            <option value="week"><bean:message bundle="sys-quartz"
                                                                               key="sysQuartzJob.cronExpression.frequency.week"/></option>
                                            <option value="day"><bean:message bundle="sys-quartz"
                                                                              key="sysQuartzJob.cronExpression.frequency.day"/></option>
                                            <option value="hour"><bean:message bundle="sys-quartz"
                                                                               key="sysQuartzJob.cronExpression.frequency.hour"/></option>
                                            <option value="every"><bean:message bundle="sys-quartz"
                                                                                key="sysQuartzJob.cronExpression.frequency.every"/></option>
                                        </select>
                                    </div>
                                </td>
                            </tr>

                            <tr kmss_inputtype="0" class="fdCron_Str" id="TR_FrequencySetting" style="display:none">
                                <td class="td_normal_title">
                                    <bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.timeSetting"/>
                                </td>
                                <td colspan="3" style="padding-left: 25px;">
                                    <div>
                                        <span>
                                            <bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.field.year1"/>
                                            <input name="fdYear" size="4" type="number" class="inputSgl" disabled>
                                            <bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.field.year2"/>
                                        </span>
                                        <span>
                                            <bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.field.month1"/>
                                            <input name="fdMonth" size="2" type="number" class="inputSgl" disabled>
                                            <bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.field.month2"/>
                                        </span>
                                        <span>
                                            <bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.field.day1"/>
                                            <input name="fdDay" size="2" type="number" class="inputSgl" disabled>
                                            <bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.field.day2"/>
                                        </span>
                                        <span>
                                            <bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.field.week1"/>
                                            <select name="fdWeek" disabled>
                                                <c:forEach begin="0" end="6" var="i">
                                                    <option value="${i+1}">
                                                        <bean:message key="date.weekDay${i}"/>
                                                    </option>
                                                </c:forEach>
                                            </select>
                                            <bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.field.week2"/>
                                        </span>
                                        <span>
                                            <bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.field.hour1"/>
                                            <input name="fdHour" size="2"  type="number" class="inputSgl" disabled>
                                            <bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.field.hour2"/>
                                        </span>
                                        <span>
                                            <bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.field.minute1"/>
                                            <input name="fdMinute" size="2"  type="number" class="inputSgl" disabled>
                                            <bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.field.minute2"/>
                                        </span>
                                        <span>
                                            <bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.field.second1"/>
                                            <input name="fdSecond" size="2"  type="number" class="inputSgl" disabled>
                                            <bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.field.second2"/>
                                        </span>
                                        <span>
                                            <bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.field.every1"/>
                                            <select name="fdEvery" disabled>
                                                <option value="5">5</option>
                                                <option value="10">10</option>
                                                <option value="15">15</option>
                                                <option value="20">20</option>
                                                <option value="30">30</option>
									        </select>
                                            <bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.field.every2"/>
                                        </span>
                                    </div>
                                </td>
                            </tr>
                            <tr kmss_inputtype="1" class="fdCron_Str" style="display:none">
                                <td class="td_normal_title">
                                    <bean:message bundle="sys-quartz"
                                                  key="sysQuartzJob.cronExpression.inputType.cronExpression"/>
                                </td>
                                <td colspan="3">
                                    <div>
                                        <html:text property="fdCron" style="width:80%" disabled="true"/>
                                        <a href="#" onclick="isShowHelp=!isShowHelp; refreshInpuType1();">
                                            <bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.help"/>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                            <tr kmss_inputtype="1" class="fdCron_Str" id="TR_CronExpressionHelp" style="display:none">
                                <td class="td_normal_title">
                                    <bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.help"/>
                                </td>
                                <td colspan="3">
                                    <div style="padding-left: 20px">
                                        <%@ include
                                                file="/sys/quartz/sys_quartz_job/sysQuartzJob_cronExpressionHelp.jsp" %>
                                        <br><bean:message bundle="sys-quartz"
                                                          key="sysQuartzJob.cronExpression.helpLink"/>
                                        <a href="http://quartz.sourceforge.net/javadoc/org/quartz/CronTrigger.html"
                                           target="_blank">
                                            http://quartz.sourceforge.net/javadoc/org/quartz/CronTrigger.html
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td class="td_normal_title" width="15%">
                                        ${lfn:message('sys-modeling-base:sysModelingScenes.fdDesc')}
                                </td>
                                <td colspan="3" width="85.0%">
                                        <%-- 描述--%>
                                    <div id="_xform_fdDesc" _xform_type="textarea">
                                        <html:text  property="fdDesc" disabled="true" style="width:95%;"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                        ${lfn:message('sys-modeling-base:sysModelingScenes.fdBehaviors')}
                                </td>
                                <td width="85%">
                                        <%-- 触发动作--%>
                                    <div class="model-mask-panel-table-base" lui-mark-prop="behavior">
                                            <%--                                        <div data-lui-cid="lui-id-15">--%>
                                        <span class="behaviorSelect highLight"
                                              style="margin-bottom: 10px;display: none">选择</span>
                                        <table class="tb_normal field_table fdBehaviorBox" width="100%">
                                            <thead>
                                            <tr>
                                                <td>触发名</td>
                                                <td width="30%">类型</td>
                                            </tr>
                                            </thead>
                                        </table>
                                            <%--                                        </div>--%>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <input name="fdBehaviorNames" type="hidden" value="${sysModelingScenesForm.fdBehaviorNames}"/>
                <input name="fdBehaviorIds" type="hidden" value="${sysModelingScenesForm.fdBehaviorIds}"/>
                <input name="fdExtension" type="hidden" value="${sysModelingScenesForm.fdExtension}"/>
            </div>


            <html:hidden property="fdId"/>
            <c:if test="${sysModelingScenesForm.method_GET == 'add' }">
                <html:hidden property="modelMainId" value="${param.modelId }"/>
            </c:if>
            <c:if test="${sysModelingScenesForm.method_GET == 'edit' }">
                <html:hidden property="modelMainId" value="${sysModelingScenesForm.modelMainId }"/>
            </c:if>
            <html:hidden property="method_GET"/>
        </html:form>
        <script type="text/javascript">
            var formOption = {
                formName: 'sysModelingScenesForm',
                modelName: 'com.landray.kmss.sys.modeling.base.model.SysModelingScenes',
                templateName: '',
                subjectField: 'fdName',
                mode: '',
                dialogLinks: [],
                valueLinks: [],
                attrLinks: [],
                optionLinks: [],
                linkValidates: [],
                detailTables: [],
                dataType: {},
                detailNotNullProp: {}
            };
            var validation = $KMSSValidation();
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic',
                'lui/util/str', "sys/modeling/base/relation/trigger/scenes/js/scenes"
            ], function ($, dialog, topic, str, scenes) {
                var cronType = "0";
                var scenesType = '${sysModelingScenesForm.fdType }';
                window.scenesInstance = new scenes.Scenes({
                    "viewContainer": $(".scenes_view"),
                    "scenesType": scenesType
                });
                scenesInstance.startup();

                window.selectScenesType = function (val, name) {
                    scenesInstance.selectScenesType(val, name);
                    scenesType = val;
                    if (val == "0") {
                        $("#fdCronType_tr").show();
                        cronType = $("[name=fdCronType]").val();
                        if (cronType == "0") {
                            $("#fdCron_Times").show();
                        } else {
                            $("#fdCron_Times").hide();
                        }
                    } else {
                        changeInputType("0");
                        $("#fdCronType_tr").hide();
                        $(".fdCron_Str").hide();
                        $("#fdCron_Times").hide();

                    }
                };
                window.doSubmit = function (type) {
                    buildCronExpression();
                    //触发扩展
                    var msgList = $(".fdBehaviorBox  .dataItem");
                    var fdExtension = {};
                    $.each(msgList, function (idx, item) {
                        var ext = {
                            "type": $(item).attr("dataType"),
                        };
                        if ($(item).attr("dataMsgType")) {
                            ext.doMsgType = $(item).attr("dataMsgType")
                        }
                        fdExtension[$(item).attr("msgId")] = ext;
                    });
                    $("[name=fdExtension]").val(JSON.stringify(fdExtension));
                    Com_Submit(document.sysModelingScenesForm, type)
                }

                window.onload = function () {

                    selectScenesType("${sysModelingScenesForm.fdType}", "");
                    if ("${sysModelingScenesForm.fdType}" === "0") {
                        changeInputType("${sysModelingScenesForm.fdCronType}");
                    }
                    //x消息
                    var ext = {};
                    if ('${sysModelingScenesForm.fdExtension}') {
                        ext = JSON.parse('${sysModelingScenesForm.fdExtension}')
                    }
                    $("[name='fdBehaviorIds']").val("${sysModelingScenesForm.fdBehaviorIds}");
                    $("[name='fdBehaviorNames']").val("${sysModelingScenesForm.fdBehaviorNames}")
                    scenesInstance.initByStoreData(ext)
                }
            });


        </script>
        <script type="text/javascript">

            /*********************************/
            var isShowHelp = false;
            var fieldMessages = "<bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.fields"/>".split(",");
            var re_Number = /[^\d]/gi;

            function changeInputType(value) {
                if (value == "0") {
                    //切换到常用编辑模式，解释CronExpression
                    parseCronExpression();
                } else {
                    //切换到CronExpression编辑模式，构造CronExpression，若构造失败，不切换编辑模式
                    if (!buildCronExpression()) {

                        document.getElementsByName("fdCronType")[0].checked = true;
                        return;
                    }
                    document.getElementsByName("fdCronType")[1].checked = true;
                }
                //根据kmss_inputtype显示/隐藏相关行
                var tbObj = document.getElementById("TB_MainTable");
                for (var i = 0; i < tbObj.rows.length; i++) {
                    var att = tbObj.rows[i].getAttribute("kmss_inputtype");
                    if (att == null)
                        continue;
                    tbObj.rows[i].style.display = att == value ? "table-row" : "none";
                }
                //对隐藏的行进行调整
                if (value == "0") {
                    refreshInpuType0();
                } else {
                    refreshInpuType1();
                }
            }

            function refreshInpuType0() {
                var frequencyField = document.getElementsByName("fdCronTimes")[0];
                var trObj = document.getElementById("TR_FrequencySetting");
                if (frequencyField.selectedIndex < 1) {
                    //频率没有设置，隐藏设置栏
                    trObj.style.display = "none";
                    return;
                }
                trObj.style.display = "table-row";
                //调整设置项的显示
                var displayArr; //年,月,日,星期,时,分,秒,间隔
                switch (frequencyField.options[frequencyField.selectedIndex].value) {
                    case "once":
                        displayArr = new Array("", "", "", "none", "", "", "", "none");
                        break;
                    case "year":
                        displayArr = new Array("none", "", "", "none", "", "", "", "none");
                        break;
                    case "month":
                        displayArr = new Array("none", "none", "", "none", "", "", "", "none");
                        break;
                    case "week":
                        displayArr = new Array("none", "none", "none", "", "", "", "", "none");
                        break;
                    case "day":
                        displayArr = new Array("none", "none", "none", "none", "", "", "", "none");
                        break;
                    case "hour":
                        displayArr = new Array("none", "none", "none", "none", "none", "", "", "none");
                        break;
                    case "every":
                        displayArr = new Array("none", "none", "none", "none", "none", "none", "", "");
                }
                var spanArr = trObj.cells[1].getElementsByTagName("SPAN");
                for (var i = 0; i < spanArr.length; i++)
                    spanArr[i].style.display = displayArr[i];
            }

            function refreshInpuType1() {
                //调整帮助栏的显示
                if (isShowHelp)
                    $("#TR_CronExpressionHelp").show();
                else
                    $("#TR_CronExpressionHelp").hide();
            }

            //构造CronExpression，并写入fdCronExpression中，返回false则表示构造失败
            function buildCronExpression() {
                var frequencyField = document.getElementsByName("fdCronTimes")[0];
                var expressionField = document.getElementsByName("fdCron")[0];
                //若没有选择频率，不处理
                if (frequencyField.selectedIndex < 1)
                    return true;
                //获取所有设置项的信息
                var year = document.getElementsByName("fdYear")[0].value;
                var month = document.getElementsByName("fdMonth")[0].value;
                var day = document.getElementsByName("fdDay")[0].value;
                var week = document.getElementsByName("fdWeek")[0].options[document.getElementsByName("fdWeek")[0].selectedIndex].value;
                var hour = document.getElementsByName("fdHour")[0].value;
                var minute = document.getElementsByName("fdMinute")[0].value;
                var second = document.getElementsByName("fdSecond")[0].value;
                var every = document.getElementsByName("fdEvery")[0].options[document.getElementsByName("fdEvery")[0].selectedIndex].value;
                var frequency = frequencyField.options[frequencyField.selectedIndex].value;
                //根据频率调整参数
                switch (frequency) {
                    case "every":
                        minute = "0/" + every;
                    case "hour":
                        hour = "*";
                    case "day":
                        day = "*";
                    case "week":
                    case "month":
                        month = "*";
                    case "year":
                        year = "";
                        break;
                }
                //构造CronExpression
                try {
                    var expression = formatCronExpressionField(second, fieldMessages[0], 0, 59) + " ";
                    if (frequency == "every")
                        expression += minute + " ";
                    else
                        expression += formatCronExpressionField(minute, fieldMessages[1], 0, 59) + " ";
                    expression += formatCronExpressionField(hour, fieldMessages[2], 0, 23) + " ";
                    if (frequency == "week") {
                        expression += "? ";
                        expression += formatCronExpressionField(month, fieldMessages[4], 1, 12) + " ";
                        expression += week;
                    } else {
                        expression += formatCronExpressionField(day, fieldMessages[3], 1, 31) + " ";
                        expression += formatCronExpressionField(month, fieldMessages[4], 1, 12) + " ";
                        expression += "?";
                    }
                    if (year != "")
                        expression += " " + formatCronExpressionField(year, fieldMessages[6], 1970, 2099);
                } catch (e) {
                    //构造过程校验出错，返回false，e==""表示是formatCronExpressionField函数抛出的错误
                    if (e == "")
                        return false;
                    throw e;
                }
                expressionField.value = expression;
                return true;
            }

            //整理域的信息，若校验出错，抛出""
            function formatCronExpressionField(value, fieldMsg, minValue, maxValue) {
                if (value == "*")
                    return value;
                if (value == "")
                    return minValue;
                value = parseInt(value, 10);
                if (isNaN(value)) {
                    alert(errorInteger.replace(/\{0\}/, fieldMsg));
                    throw "";
                }
                if (value < minValue || value > maxValue) {
                    var msg = errorRange.replace(/\{0\}/, fieldMsg);
                    msg = msg.replace(/\{1\}/, minValue);
                    msg = msg.replace(/\{2\}/, maxValue);
                    alert(msg);
                    throw "";
                }
                if (value < minValue || value > maxValue)
                    return value;
                return value;
            }

            //解释CronExpression，并将值写入到相关的设置项中
            function parseCronExpression() {
                var expressionField = document.getElementsByName("fdCron")[0];
                var expression;
                expression = expressionField.value.split(/\s+/gi);
                var data = new Array();
                var frequency = null;
                try {
                    switch (expression.length) {
                        case 7:
                            //判断年
                            if (!checkCronExpressionField("year", expression[6], data, frequency))
                                frequency = "once";
                        case 6:
                            //判断月
                            if (!checkCronExpressionField("month", expression[4], data, frequency) && frequency == null)
                                frequency = "year";
                            //判断星期
                            if (expression[5] != "?") {
                                if (expression[3] != "?" || frequency != null)
                                    throw "";
                                if (expression[5] != "*") {
                                    if (re_Number.test(expression[5]))
                                        throw "";
                                    data.week = expression[5];
                                    frequency = "week";
                                }
                            } else {
                                //判断日期
                                if (!checkCronExpressionField("day", expression[3], data, frequency) && frequency == null)
                                    frequency = "month";
                            }
                            //判断小时
                            if (!checkCronExpressionField("hour", expression[2], data, frequency) && frequency == null) {
                                if (data.week == null)
                                    frequency = "day";
                                else
                                    frequency = "week";
                            }
                            //判断分
                            if (expression[1] == "*")
                                throw "";
                            if (re_Number.test(expression[1])) {
                                if (frequency != null)
                                    throw "";
                                var tmpArr = expression[1].split("/");
                                if (tmpArr.length != 2 || re_Number.test(tmpArr[0]) || re_Number.test(tmpArr[1]))
                                    throw "";
                                data.every = tmpArr[1];
                                frequency = "every";
                            } else {
                                if (frequency == null)
                                    frequency = "hour";
                                data.minute = expression[1];
                            }
                            //判断秒
                            if (checkCronExpressionField("second", expression[0], data, frequency))
                                throw "";
                    }
                } catch (e) {
                    if (e == "")
                        frequency = null;
                    else
                        throw e;
                }
                if (frequency == null)
                    data = new Array();
                else
                    data.frequency = frequency;
                setCronExpressionField(data);
            }

            /*
            校验CronExpression的域（年、月、时、秒），并把值写到data中。
            返回：true（该字段未限定）false（该字段已经限定）
            抛出：""，该域无法解释
            */
            function checkCronExpressionField(fieldName, fieldValue, data, frequency) {
                if (fieldValue == "*" || fieldValue == "") {
                    //若前面频率已经确定，但当前字段却没有限定，不满足常用的模式，抛出无法解释
                    if (frequency != null)
                        throw "";
                    return true;
                }
                if (re_Number.test(fieldValue))
                    throw "";
                data[fieldName] = fieldValue;
                return false;
            }

            //将数据写入设置数据项中
            function setCronExpressionField(data) {
                document.getElementsByName("fdYear")[0].value = data.year == null ? "" : data.year;
                document.getElementsByName("fdMonth")[0].value = data.month == null ? "" : data.month;
                document.getElementsByName("fdDay")[0].value = data.day == null ? "" : data.day;
                setSelectFieldValue(document.getElementsByName("fdWeek")[0], data.week);
                document.getElementsByName("fdHour")[0].value = data.hour == null ? 0 : data.hour;
                document.getElementsByName("fdMinute")[0].value = data.minute == null ? 0 : data.minute;
                document.getElementsByName("fdSecond")[0].value = data.second == null ? 0 : data.second;
                setSelectFieldValue(document.getElementsByName("fdEvery")[0], data.every);
                setSelectFieldValue(document.getElementsByName("fdCronTimes")[0], data.frequency);
            }

            //设置下拉框的信息
            function setSelectFieldValue(fieldObj, value) {
                fieldObj.selectedIndex = 0;
                if (value == null)
                    return;
                for (var i = 0; i < fieldObj.options.length; i++) {
                    if (fieldObj.options[i].value == value) {
                        fieldObj.selectedIndex = i;
                        return;
                    }
                }
            }
        </script>
    </template:replace>
</template:include>