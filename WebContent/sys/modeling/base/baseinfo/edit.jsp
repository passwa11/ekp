
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.sys.modeling.base.util.ModelingUiUtil" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.edit" sidebar="no">
    <template:replace name="content">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modelTable.css"/>
        <style>
            /*样式覆盖*/
            .tb_simple tbody tr td:last-child .inputContainer {
                width: 440px;
                white-space: nowrap;
            }

            .inputContainer input,
            .inputContainer textarea {
                width: 408px;
            }

            .inputselectsgl, .weui_switch {
                margin-left: 20px;
            }

            .inputselectsgl .input input {
                margin-left: 0;

            }
            .model-mask-panel-table .comp,
            .model-mask-panel-table input[type=text],
            .model-mask-panel-table textarea {
                font-size: 12px;
                color: #333333;
                line-height: 12px;
            }
            .model-mask-panel-table textarea{
                padding: 10px;
            }
            .inputContainer .textarea textarea {
                border: none;
                font-size: 12px;
                color: #333333;
                line-height: 12px;
            }

            .tb_simple .onlyRead input {
                border: none;
                font-size: 14px;
                color: #666666;
                line-height: 12px;
            }

            .description_txt {
                font-size: 12px;
                color: #999999;
                margin-left: 20px;
            }
            .model-mask-panel{
                background: #FFFFFF;
                border: 1px solid #DDDDDD;
                border-top: none;
                box-shadow: 0 0 6px 0 rgba(0,0,0,0.06);
                border-radius: 4px;
                border-radius: 4px;
            }
            .td_label0 > nobr {
                margin: 0px;
                cursor: pointer;
                position:relative
            }
            .td_label0 > nobr > input {
                margin: 0;
                padding: 0 20px;
                width: auto;
                height: 40px;
                line-height: 40px;
                color: #666666;
                font-size: 14px;
                border: none !important;
                background-image: none !important;
                background-color: #F6F7FA !important;


            }
            /*tab选项卡样式覆盖*/
            #Label_Tabel  .td_label0{
                background-color: #F6F7FA;
                height: 40px;
                border: 1px solid #dddddd !important;
                border-bottom: 1px solid #dddddd !important;
            }
            .td_label0 .btnlabel1,
            .td_label0 > nobr > input.btnlabel2:hover{
                background: #fff !important;
                color: #4285f4;
                border-bottom:1px solid #FFFFFF !important;
                border-right:1px solid #DDDDDD !important;
                border-left:1px solid #DDDDDD !important;
                border-top:1px solid #DDDDDD !important;
                z-index: 6;
            }
            .td_label {
                padding-top: 0px;
            }
            .pcIndexUrlPath,.mobileIndexUrlPath{
                margin-left:16px;
                margin-bottom:20px;
            }
        </style>
        <div class="lui_modeling">


            <table id="Label_Tabel" width="100%" >
                <tr LKS_LabelName="${lfn:message('sys-modeling-base:modeling.app.baseinfo')}">
                    <td>

                        <html:form action="/sys/modeling/base/modelingApplication.do">
                            <script>
                                Com_IncludeFile("doclist.js");
                            </script>
                            <div class="model-mask-panel medium">
                                <div>
                                    <div class="rightBtn">
                                        <ui:button text="${lfn:message('button.save')}"
                                                   style="position: absolute;top: 10px;right: 17px;background: #fff;font-size:14px"
                                                   height="30" width="90" onclick="modeling_Submit();"
                                                   order="1">
                                        </ui:button>
                                    </div>

                                    <div class="model-mask-panel-table">
                                        <table class="tb_simple modeling_form_table operationMainForm"
                                               mdlng-prtn-mrk="regionTable">
                                            <tbody>
                                            <tr>
                                                <td class="td_normal_title" width=15%>
                                                        ${lfn:message('sys-modeling-base:modeling.app.name')}
                                                </td>
                                                <td colspan="3" width=85%>
                                                    <div class="inputContainer">
                                                        <xform:text property="fdAppName" required="true" validators="maxLength(100)"/>
                                                            <%--                                                    <c:if test="${modelingApplicationForm.fdValid eq 'true' }">--%>
                                                            <%--                                                        <a href="javascript:void(0);"--%>
                                                            <%--                                                           style="color:#1b83d8;float:right;"--%>
                                                            <%--                                                           onclick="linkToIndex();">--%>
                                                            <%--                                                                ${lfn:message('sys-modeling-base:modeling.app.home')}--%>
                                                            <%--                                                        </a>--%>
                                                            <%--                                                    </c:if>--%>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="td_normal_title" width=15%>
                                                        ${lfn:message('sys-modeling-base:modeling.app.desrc')}
                                                </td>
                                                <td colspan="3" width=85%>
                                                    <div class="inputContainer">
                                                        <xform:textarea property="fdAppDesc" style="height:90px" placeholder="${lfn:message('sys-modeling-base:modeling.baseinfo.ApplicationDescription')}"/>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="td_normal_title" width=15%>
                                                        ${lfn:message('sys-modeling-base:modeling.app.category')}
                                                </td>
                                                <td width="85%">
                                                    <div class="inputContainer">
                                                        <xform:dialog propertyId="fdCategoryId"
                                                                      propertyName="fdCategoryName" subject="${lfn:message('sys-modeling-base:modeling.baseinfo.ApplicationOfClassification')}"
                                                                      dialogJs="categoryDialog();"
                                                                      style="width:408px;"/>
                                                    </div>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td class="td_normal_title" width=15%>
                                                        ${lfn:message('sys-modeling-base:modeling.app.icon')}
                                                </td>
                                                <td width="85%">
                                                    <div class="inputContainer">
                                                        <a href="javascript:void(0);" onclick="selectIcon();">
                                                            <i class="iconfont_nav ${modelingApplicationForm.fdIcon}"
                                                               style="color:#999;font-size:40px;margin-left: 20px"></i>
                                                        </a>
                                                        <html:hidden property="fdIcon"/>
                                                    </div>
                                                </td>
                                            </tr>
                                            <c:import url="/sys/modeling/base/baseinfo/dynamic_link.jsp" charEncoding="UTF-8">
                                                <c:param name="fdId" value="${param.fdId}"/>
                                            </c:import>
                                            <tr>
                                                <td class="td_normal_title" width=15%>
                                                        ${lfn:message('sys-modeling-base:modeling.app.status')}
                                                </td>
                                                <td width=85%>
                                                    <div style="padding-left: 20px;line-height: 33px;">
                                                        <xform:radio property="fdPublish" showStatus="view">
                                                            <xform:simpleDataSource value="true">${lfn:message('sys-modeling-base:modeling.baseinfo.Publish')}</xform:simpleDataSource>
                                                            <xform:simpleDataSource value="false">${lfn:message('sys-modeling-base:modeling.baseinfo.NotPublish')}</xform:simpleDataSource>
                                                        </xform:radio>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="td_normal_title" width=15%>
                                                        ${lfn:message('sys-modeling-base:modeling.app.useStatus')}
                                                </td>
                                                <td width=85%>
                                                    <div style="padding-left: 20px;line-height: 33px;">
                                                        <xform:radio property="fdValid" showStatus="view">
                                                            <xform:simpleDataSource value="true">${lfn:message('sys-modeling-base:modeling.app.status.open')}</xform:simpleDataSource>
                                                            <xform:simpleDataSource value="false">${lfn:message('sys-modeling-base:modeling.app.status.forbid')}</xform:simpleDataSource>
                                                        </xform:radio>
                                                    </div>
                                                </td>
                                            </tr>
                                                <%--	暂无
                                                <kmss:ifModuleExist path="/dbcenter/echarts/">
                                                    <tr>
                                                        <td class="td_normal_title" width=15%>
                                                            图表中心
                                                        </td>
                                                        <td colspan="3" width=85%>
                                                            <ui:switch property="fdEnableDbCenter" enabledText="${lfn:message('sys-modeling-base:modeling.app.status.open')}"
                                                                disabledText="${lfn:message('sys-modeling-base:modeling.app.status.forbid')}"></ui:switch>
                                                        </td>
                                                    </tr>
                                                </kmss:ifModuleExist> --%>

                                            <tr>
                                                <td class="td_normal_title" width=15%>
                                                        ${lfn:message('sys-modeling-base:modeling.app.maintainablePerson')}
                                                </td>
                                                <td colspan="3" width=85%>
                                                    <div class="inputContainer">
                                                        <xform:address textarea="true" mulSelect="true"
                                                                       propertyId="authEditorIds"
                                                                       propertyName="authEditorNames"
                                                                       style="width: 408px;height:90px;margin-left: 20px;"></xform:address>
                                                    </div>
                                                    <div class="description_txt">
                                                            ${lfn:message('sys-modeling-base:modeling.app.maintainablePerson.desrc')}
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="td_normal_title" width=15%>
                                                        ${lfn:message('sys-modeling-base:modeling.app.userAvailable')}
                                                </td>
                                                <td width=85%>
                                                    <div class="inputContainer">
                                                        <div>
                                                            <label>
                                                                <input style="width: 20px;margin-bottom: 10px;margin-left: 20px"
                                                                       type="checkbox" name="authNotReaderFlag"
                                                                       value="${modelingApplicationForm.authNotReaderFlag}"
                                                                       onclick="Cate_CheckNotReaderFlag(this);"
                                                                       <c:if test="${modelingApplicationForm.authNotReaderFlag eq 'true'}">checked</c:if> />
                                                                    ${lfn:message('sys-modeling-base:modeling.app.notAvailableToAll')}
                                                            </label>
                                                            <div id="Cate_AllUserId">
                                                                <html:hidden property="authReaderIds"/>
                                                                <xform:address textarea="true" mulSelect="true"
                                                                               propertyId="authReaderIds"
                                                                               propertyName="authReaderNames"
                                                                               style="width: 408px;height:90px;margin-left: 20px;"></xform:address>
                                                            </div>
                                                        </div>
                                                        <div id="Cate_AllUserNote" class="description_txt">
                                                                 <% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>

                                                        <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
                                                        <!-- （为空则本组织人员可使用） -->
                                                        <bean:message bundle="sys-modeling-base" key="modeling.app.userAvailable.desrc.organizationUse" />
                                                        <% } else { %>
                                                        <!-- （为空则所有内部人员可使用） -->
                                                        <bean:message bundle="sys-modeling-base" key="modeling.app.userAvailable.desrc.allUse" />
                                                        <% } %>
                                                        <% }else { %>
                                                        <bean:message bundle="sys-modeling-base" key="modeling.app.userAvailable.desrc.nonOrganizationAllUse" />
                                                        <% } %>
                                                        </div>
                                                    </div>

                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="td_normal_title" width=15%>
                                                        ${lfn:message('sys-modeling-base:modeling.app.businessAdministrator')}
                                                </td>
                                                <td colspan="3" width=85%>
                                                    <div class="inputContainer">
                                                        <xform:address textarea="true" mulSelect="true"
                                                                       propertyId="fdBusinessAdminIds"
                                                                       propertyName="fdBusinessAdminNames"
                                                                       style="width: 408px;height:90px;margin-left: 20px;"></xform:address>
                                                    </div>
                                                    <div class="description_txt">
                                                            ${lfn:message('sys-modeling-base:modeling.app.businessAdministrator.desrc')}
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <!-- 创建人员 -->
                                                <td class="td_normal_title" width=15%><bean:message
                                                        key="model.fdCreator"/></td>

                                                <td width=85%>
                                                    <div class="onlyRead">
                                                        <html:text property="docCreatorName" readonly="true"/>
                                                    </div>
                                                </td>
                                                <!-- 创建时间 -->
                                            </tr>
                                            <tr>
                                                <td class="td_normal_title" width=15%><bean:message
                                                        key="model.fdCreateTime"/></td>
                                                <td width=85%>
                                                    <div class="onlyRead">
                                                        <html:text property="docCreateTime" readonly="true"/>
                                                    </div>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <html:hidden property="fdId"/>
                            <!-- 表单相关js -->
                            <script type="text/javascript">
                                var modeling_validation = $KMSSValidation();
                                var isTrueLink = true;

                                /**
                                 *分类选择
                                 */
                                function categoryDialog() {
                                    Dialog_List(false, 'fdCategoryId', 'fdCategoryName', null, "modelingAppCategoryService");
                                }

                                /**
                                 * 设置权限
                                 * @param el
                                 * @constructor
                                 */
                                function Cate_CheckNotReaderFlag(el) {
                                    document.getElementById("Cate_AllUserId").style.display = el.checked ? "none" : "";
                                    document.getElementById("Cate_AllUserNote").style.display = el.checked ? "none" : "";
                                    el.value = el.checked;
                                    seajs.use(["lui/util/env"],function(env){
                                        // 设置pc首页和移动首页的路径
                                        <%--var pcIndexUrl = env.fn.formatUrl("/sys/modeling/main/index.jsp?fdAppId=${param.fdId}",true);--%>
                                        <%--$(".pcIndexUrlPath").html(pcIndexUrl);--%>
                                        <%--var mobileIndexUrl = env.fn.formatUrl("/sys/modeling/main/mobile/modelingAppMainMobile.do?method=index&fdId=${param.fdId}",true);--%>
                                        <%--$(".mobileIndexUrlPath").html(mobileIndexUrl);            --%>
                                    });
                                }
                                function Cate_Win_Onload() {
                                    Cate_CheckNotReaderFlag(document.getElementsByName("authNotReaderFlag")[0]);
                                }
                                Com_AddEventListener(window, "load", Cate_Win_Onload);

                                seajs.use(["lui/jquery", "lui/dialog"], function ($, dialog) {
                                    /**
                                     * icon选择
                                     */
                                    window.selectIcon = function () {
                                        var url = "/sys/modeling/base/resources/iconfont.jsp?type=module";
                                        dialog.iframe(url, "${lfn:message('sys-modeling-base:modeling.app.selectIcon')}", changeIcon, {
                                            width: 750,
                                            height: 500
                                        })
                                    };

                                    //校验关键字的唯一性 已废弃
                                    window.validateKeyUnique = function () {
                                        var fdUrl = document.getElementsByName("fdUrl")[0];
                                        var isUnique = true;
                                        if (fdUrl && fdUrl.value != '') {
                                            var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=isUnique&fdUrl=" + fdUrl.value + "&fdId=${param.fdId}";
                                            $.ajax({
                                                url: url, async: false, dataType: "json", cache: false, success: function (rtn) {
                                                    if ("true" != rtn.isUnique) {
                                                        isUnique = false;
                                                        dialog.alert("${lfn:message('sys-modeling-base:modeling.baseinfo.ApplicationPathAlert')}");
                                                    }
                                                }
                                            });
                                        }
                                        return isUnique;
                                    }

                                    function changeIcon(className) {
                                        if (className) {
                                            $("i.iconfont_nav").removeClass().addClass(className);
                                            $("input[name='fdIcon']").val(className.split(" ")[1]);
                                        }
                                    }
                                });

                                function linkToIndex() {
                                    var url = "${LUI_ContextPath}/sys/modeling/main/index.jsp?fdAppId=${param.fdId}";
                                    Com_OpenWindow(url);
                                }

                                function modeling_Submit() {
                                    if (isTrueLink){
                                    Com_Submit(document.modelingApplicationForm, 'update');
                                    }
                                }

                                function modeling_beforeSubmitValidate() {
                                    if (!validateKeyUnique()) {
                                        return false;
                                    }
                                    return true;
                                }

                                Com_Parameter.event["submit"].push(modeling_beforeSubmitValidate);

                            </script>
                        </html:form>
                    </td>
                </tr>
                    <%--版本管理 当有版本时才显示--%>
                <c:if test="${modelingApplicationForm.fdVersionStatus != null && modelingApplicationForm.fdVersionStatus != 'dra'}">
                    <tr LKS_LabelName="${lfn:message('sys-modeling-base:table.modelingAppVersion')}">
                        <td align="center">
                            <iframe width="95%" height="900px" style="border:none;" id="versionIframe"
                                    src="${LUI_ContextPath}/sys/modeling/base/appVersion/index.jsp?fdAppId=${param.fdId}"></iframe>
                        </td>
                    </tr>
                </c:if>

                    <%--操作日志--%>
                <kmss:auth requestURL="/sys/modeling/base/modelingOperLog.do?method=data&fdAppId=${param.fdAppId}">
                    <tr LKS_LabelName="${lfn:message('sys-modeling-base:table.modelingOperLog')}" id="_tab_modelingOperLog">
                        <td align="center">
                            <iframe width="95%" height="900px" style="border:none;" id="operlogIframe"
                                    src="${LUI_ContextPath}/sys/modeling/base/operLog/index.jsp?fdAppId=${param.fdId}"></iframe>
                        </td>
                    </tr>
                </kmss:auth>
            </table>
        </div>
        <script type="text/javascript">
            // #117162
            function refreshListInit(){
                var lks_idx = $("#_tab_modelingOperLog").attr("lks_labelindex");
                $("#Label_Tabel_Label_Btn_"+lks_idx).on("click",function () {
                    $("#operlogIframe")[0].contentWindow.__refreshList();
                })
            }
            Com_AddEventListener(window, "load", refreshListInit);

        </script>
    </template:replace>
</template:include>
