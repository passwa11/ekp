<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.edit" sidebar="no">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
        <script>
            Com_IncludeFile("view.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
            Com_IncludeFile("dialog.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
            Com_IncludeFile("appList.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
        </script>
        <style>
            .lui_custom_list_boxs {
                border-top: 1px solid #d5d5d5;
                position: fixed;
                bottom: 0;
                width: 100%;
                background-color: #fff;
                z-index: 1000;
                height: 63px;
            }

            .extend {
                text-align: center;
                font-size: 14px;
                cursor: pointer;
                color: #4285F4;
                /*color: #999;*/
            }

            .extend:hover {
                color: #4285F4;
            }

        </style>
    </template:replace>
    <template:replace name="content">

        <center>

            <form>
            <div style="height: 197px;max-height: 197px;overflow-y: scroll;overflow-x: hidden">
                <table class="tb_simple model-view-panel-table" style="margin-top: 20px" width=95% >
                    <tr>
                        <td class="td_normal_title" width=15%>
                                ${lfn:message('sys-modeling-base:modeling.app.name')}
                        </td>
                        <td width=85% class="model-view-panel-table-td">
                            <div _xform_type="text">
                                <xform:text subject="${lfn:message('sys-modeling-base:modeling.app.name')}"
                                            property="fdAppName" style="width:85%" required="true" validators="maxLength(100)"/>
                            </div>

                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width=15% style="line-height: 52px;">
                                ${lfn:message('sys-modeling-base:modeling.app.icon')}
                        </td>
                        <td width=85% class="model-view-panel-table-td">
                            <div class="appMenu_main_icon"><i class="iconfont_nav"></i></div>
                            <span class="txtstrong">*</span>
                            <a href="javascript:void(0);" class="select_icon_btn" onclick="selectIcon();">
                                <!-- <i class="iconfont_nav" style="color:#999;font-size:40px;"></i> -->
                                    ${lfn:message('sys-modeling-base:modeling.form.ChangeIcon')}
                            </a>
                            <input name="fdIcon" type="hidden" value="iconfont_nav"/>
                        </td>
                    </tr>
                    <tr data-lui-mark="extendHide">
                        <td colspan="2"><p class="extend" onclick="showDetail()">
                            <span> ${lfn:message('sys-modeling-base:modeling.expand.optional')}</span>
                            <span class="extend_down_arrow"></span></p></td>
                    </tr>
                    <tr data-lui-mark="extendShow">
                        <td class="td_normal_title" width=15%>
                                ${lfn:message('sys-modeling-base:modeling.app.category')}
                        </td>
                        <td width=85% class="model-view-panel-table-td">
                            <xform:dialog propertyId="fdCategoryId" propertyName="fdCategoryName"
                                          subject="${lfn:message('sys-modeling-base:modeling.app.category')}"
                                          dialogJs="categoryDialog();" style="width:85%;"/>
                        </td>
                    </tr>
                    <tr data-lui-mark="extendShow">
                        <td class="td_normal_title" width=15%>
                                ${lfn:message('sys-modeling-base:modeling.app.desrc')}
                        </td>
                        <td width=85% class="model-view-panel-table-td">
                            <xform:textarea property="fdAppDesc" style="width:85%"/>
                        </td>
                    </tr>
                </table>
            </div>
                <div class="lui_custom_list_boxs" >
                    <center>
                        <div class="lui_custom_list_box_content_col_btn" style="text-align: right;width: 95%">
                            <a class="lui_custom_list_box_content_blue_btn" href="javascript:void(0)"
                               onclick="modeling_submit();"><bean:message key="button.save"/></a>
                            <a class="lui_custom_list_box_content_whith_btn" href="javascript:void(0)"
                               onclick="modeling_cancle()"><bean:message key="button.cancel"/></a>
                        </div>
                    </center>
                </div>

                    <%--			<center style="margin-top: 10px;">--%>
                    <%--			<!-- 保存 -->--%>
                    <%--			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="modeling_submit();" order="1" >--%>
                    <%--			</ui:button>--%>
                    <%--			<ui:button text="${lfn:message('button.close')}" height="35" width="120" onclick="modeling_cancle();" order="1" >--%>
                    <%--			</ui:button>--%>
                    <%--			</center>--%>
            </form>
        </center>
        <script type="text/javascript">
            var _validation = $KMSSValidation();

            function categoryDialog() {
                Dialog_List(false, 'fdCategoryId', 'fdCategoryName', '', "modelingAppCategoryService");
            }
            $("[data-lui-mark=\"extendShow\"]").hide();
            function showDetail() {
                $("[data-lui-mark=\"extendHide\"]").hide();
                $("[data-lui-mark=\"extendShow\"]").show()
            }
            function modeling_submit() {
                if (!_validation.validate()) {
                    return
                }
                //Com_Submit(document.modelingApplicationForm, 'createApp');
                var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=saveBaseInfoByAjax";
                $.ajax({
                    url: url,
                    type: "post",
                    data: $('form').serialize(),
                    success: function (rtn) {
                        if (rtn.status === '00') {
                            //刷新当前窗口
                            var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=appIndex&fdId=" + rtn.data.id;
                            // top.open(url,"_self");
                            $dialog.___params["formWindow"].open(url, "_blank");
                            $dialog.hide({type: 'success'});
                        } else {
                            seajs.use(["lui/jquery", "lui/dialog"], function ($, dialog) {
                                dialog.failure(rtn.errmsg);
                            });
                        }
                    }
                });
            }

            function modeling_cancle() {
                $dialog.hide({type: 'cancle'});
            }

            seajs.use(["lui/jquery", "lui/dialog"], function ($, dialog) {
                window.selectIcon = function () {
                    var url = "/sys/modeling/base/resources/iconfont.jsp?type=module";
                    dialog.iframe(url, "${lfn:message('sys-modeling-base:modeling.app.selectIcon')}", changeIcon, {
                        width: 750,
                        height: 500
                    })
                }

                function changeIcon(className) {
                    if (className) {
                        $("i.iconfont_nav").removeClass().addClass(className);
                        $("input[name='fdIcon']").val(className.split(" ")[1]);
                    }
                }

            })
        </script>
    </template:replace>
</template:include>
