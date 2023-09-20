<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/mobile/resources/css/swiper.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/preview.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/views/business/res/sourcePanel.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/collection.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/showFilters.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${KMSS_Parameter_ContextPath}sys/modeling/base/space/res/css/common.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${KMSS_Parameter_ContextPath}sys/modeling/base/space/res/css/template.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${KMSS_Parameter_ContextPath}sys/modeling/base/space/res/css/styleLayout.css?s_cache=${LUI_Cache}"/>
<link rel="stylesheet" type="text/css"
      href="${KMSS_Parameter_ContextPath}sys/mobile/css/themes/default/font-mui.css"></link>
<link type="text/css" rel="stylesheet"
      href="${KMSS_Parameter_ContextPath}sys/modeling/base/mobile/design/css/design.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/mobile/design/css/custom.css?s_cache=${LUI_Cache}">
<script type="text/javascript">
    Com_IncludeFile("dialog.js");
    Com_IncludeFile("swiper2.7.6.min.js", Com_Parameter.ContextPath + 'sys/modeling/base/mobile/resources/js/', 'js', true);
    Com_IncludeFile("select.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("formula.js");
    Com_IncludeFile("doclist.js");
    Com_IncludeFile("calendar.js");
    Com_IncludeFile("Sortable.min.js", Com_Parameter.ContextPath + 'sys/modeling/base/resources/js/', 'js', true);
    Com_IncludeFile("view_common.js", Com_Parameter.ContextPath
        + 'sys/modeling/base/resources/js/', 'js', true);
</script>
<div class="model-body-content" id="editContent_resPanel">
    <div class="model-mind-map-top">
        <div class="model-mind-map-left">
            <div class="modeling-pam-back">
                <div onclick="returnListPage()">
                    <i></i>
                    <p>${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.back')}</p>
                </div>
            </div>
            <div class="model-mind-map-title listviewName" title="${modelingAppMobileForm.docSubject}">
                <c:if test="${empty modelingAppMobileForm.docSubject}">
                    ${lfn:message('sys-modeling-base:modeling.untitled.mobile.home')}
                </c:if>
                ${modelingAppMobileForm.docSubject}
            </div>
        </div>
        <div class="modeling-pam-top-right">
            <ul>
                <c:if test="${modelingAppMobileForm.method_GET=='edit'}">
                    <li class="active" onclick="modeling_submit('update')">${lfn:message('sys-modeling-base:modeling.save')}</li>
                </c:if>
                <c:if test="${modelingAppMobileForm.method_GET=='add'}">
                    <li class="active" onclick="modeling_submit('save')">${lfn:message('sys-modeling-base:modeling.button.submit')}</li>
                </c:if>
                <li class="" onclick="previewOpen()">${lfn:message('sys-modeling-base:sys.profile.modeling.preview')}</li>
            </ul>
        </div>
    </div>
    <div class="model-edit">
        <!-- 左侧预览 starts -->
        <div class="model-edit-left">
            <div class="modelAppSpaceBox"></div>
        </div>
        <!-- 左侧预览 end -->
        <!-- 右侧编辑 starts -->
        <div class="model-edit-right">
            <div class="model-edit-right-wrap">
                <div class="model-edit-view-bar">
                    <div resPanel-bar-mark="basic">${lfn:message('sys-modeling-base:modeling.basic.setting')}</div>
                    <div resPanel-bar-mark="content">${lfn:message('sys-modeling-base:modeling.portlet.setting')}</div>
                </div>
                <div class="model-edit-view-content">
                    <div class="model-edit-view-content-wrap">
                        <html:form action="/sys/modeling/base/mobile/modelingAppMobile.do">
                            <div style="box-sizing: border-box;overflow-y: auto" id="mindMapEdit">
                                <center>
                                    <table class="tb_simple model-view-panel-table" width="100%" id="mindMapEditTable">
                                            <%--基本信息--%>
                                        <tr class="resPanel-bar-content" resPanel-bar-content="basic">
                                            <td>
                                                <table id="mindMapBasicDom" class="tb_simple model-view-panel-table"
                                                       width="100%">
                                                    <tr>
                                                        <td class="td_normal_title title_required">
                                                            <bean:message bundle="sys-modeling-base"
                                                                          key="modelingAppMobile.docSubject"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td">
                                                            <div id="_xform_docSubject" _xform_type="text" class="mind-map-fdName" style="width:99%">
                                                                <input name="docSubject"  class="name-content-text" value="${modelingAppMobileForm.docSubject}" type="text"
                                                                       validate="required maxLength(36)" style="width:97%;">
                                                                <span class="txtstrong">*</span>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="td_normal_title">
                                                                ${lfn:message('sys-modeling-base:modeling.address')}
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td">
                                                            <div id="_xform_fdUrl" class="modelingMobileUrl" _xform_type="text"
                                                                 style="width:97%">
                                                                <input name="__fdUrl" class="name-content-text" type="text" readonly placeholder="${lfn:message('sys-modeling-base:modeling.please.enter')}" style="width:100%;">
                                                                <div class="modelingMobileUrlIconDiv" onclick="openMobileIndex(this)">
                                                                    <i class=""></i>
                                                                    <div>${lfn:message('sys-modeling-base:modeling.open.link')}</div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="td_normal_title">
                                                            <bean:message bundle="sys-modeling-base"
                                                                          key="modelingAppMobile.fdOrder"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td">
                                                            <div id="_xform_fdOrder" _xform_type="text"
                                                                 style="width:98%">
                                                                <xform:text property="fdOrder" style="width:99%"
                                                                                showStatus="edit" htmlElementProperties="placeholder=\"${lfn:message('sys-modeling-base:modeling.please.enter')}\""
                                                                                validators="digits min(-100) max(100) "/>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="td_normal_title">
                                                            <bean:message bundle="sys-modeling-base"
                                                                          key="modelingAppMobile.fdDesc"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td">
<%--                                                            <div id="_xform_fdDesc" _xform_type="text"--%>
<%--                                                                 style="width:97%">--%>
<%--                                                                --%>
<%--                                                            </div>--%>
                                                            <div class="textarea-div">
                                                                <div class="modelAppSpacePorletsTextarea textContent" maxlength="500">
                                                                    <xform:textarea property="fdDesc" style="width:100%"
                                                                                    showStatus="edit" placeholder="${lfn:message('sys-modeling-base:modeling.please.enter')}"
                                                                                    validators="maxLength(500)" />
                                                                    <span><span class="modelAppSpaceDescContentLength">0</span>/500</span>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="td_normal_title title_required">
                                                                ${lfn:message('sys-modeling-base:modelingAppMobile.fdAuthReaders')}
                                                        </td>
                                                    </tr>
                                                    <tr id="fdOrgElementTr">
                                                        <td width=100% class="model-view-panel-table-td">
                                                            <xform:address textarea="true" mulSelect="true"
                                                                           propertyId="authReaderIds" propertyName="authReaderNames"
                                                                           style="width: 96%;height:90px;"></xform:address>
                                                            <div style="color: #999999;font-size: 12px;">
                                                                <% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
                                                                <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
                                                                <!-- （为空则本组织人员可使用） -->
                                                                <bean:message bundle="sys-right" key="right.all.person.outter" arg0="${ecoName}" />
                                                                <% } else { %>
                                                                <!-- （为空则所有内部人员可使用） -->
                                                                <bean:message bundle="sys-modeling-base" key="modeling.empty.inner.access" />
                                                                <% } %>
                                                                <% } else { %>
                                                                <!-- （为空则所有人可使用） -->
                                                                <bean:message bundle="sys-modeling-base" key="modeling.empty.everyone.access" />
                                                                <% } %>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="td_normal_title">
                                                            ${lfn:message("sys-modeling-base:modeling.page.background")}
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td">
                                                            <div id="_xform_fdBackground" _xform_type="text"
                                                                 style="width:100%">
                                                                <xform:radio property="fdBackground" value="0" showStatus="edit" alignment="H">
                                                                    <xform:simpleDataSource value="0">${lfn:message("sys-modeling-base:modeling.page.background.default")}</xform:simpleDataSource>
                                                                    <xform:simpleDataSource value="1">${lfn:message("sys-modeling-base:modeling.page.background.blue")}</xform:simpleDataSource>
                                                                    <xform:simpleDataSource value="2">${lfn:message("sys-modeling-base:modeling.page.background.gray")}</xform:simpleDataSource>
                                                                </xform:radio>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                            <%--部件设置--%>
                                        <tr class="resPanel-bar-content" resPanel-bar-content="content">
                                            <td>
                                                <div class="mind-map-portlet-setting">
                                                    <div class="panel-portlet-content">
                                                        <div class="panel-portlet-main"></div>
                                                        <div class="panel-portlet-header" style="width: 100% ;height: 600px;" >
                                                            <div >
                                                            </div>
                                                            <div style="position: absolute;top: 350px;text-align: center; width: 100%;color: #999">
                                                                <p style="font-size: 12px;font-family: PingFangSC-Regular;margin-bottom: 8px;">${lfn:message('sys-modeling-base:modelingAppSpace.pc.no.components')}</p>
                                                                <p style="font-size: 12px;font-family: PingFangSC-Regular">${lfn:message('sys-modeling-base:modelingAppSpace.pc.please.toadd.portlet')}</p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </center>
                                <br>
                            </div>
                            <html:hidden property="fdId"/>
                            <html:hidden property="fdIndex"/>
                            <html:hidden property="fdDesignCfg"/>
                            <html:hidden property="docCreatorId"/>
                            <html:hidden property="docCreateTime"/>
                            <html:hidden property="fdApplicationId"/>
                            <html:hidden property="method_GET"/>
                        </html:form>
                    </div>
                </div>
            </div>
        </div>
        <!-- 右侧编辑 end -->
    </div>
</div>
<script>
            Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
            window.colorChooserHintInfo = {
                cancelText: '${ lfn:message('sys-modeling-base:modeling.Cancel') }',
                chooseText: '${ lfn:message('sys-modeling-base:modeling.button.ok') }'
            };

            Com_IncludeFile("spectrum.js", Com_Parameter.ContextPath + 'resource/js/colorpicker/', 'js', true);
            Com_IncludeFile("spectrum.css", Com_Parameter.ContextPath + 'resource/js/colorpicker/css/', 'css', true);
            Com_IncludeFile("spectrumColorPicker.js", Com_Parameter.ContextPath + 'sys/modeling/base/views/business/res/', 'js', true);
            var ___appId = "${param.fdAppId}";
            var ___fdId = "${param.fdId}";
            var formValidation = $KMSSValidation();
            formValidation.addElements($("[name='docSubject']")[0],"required");
            function formValidate(){
            	var rs = true;
            	// 基础信息的校验
                rs = formValidation.validateElement($("[name='docSubject']")[0])
                if(rs){
                    $("[name='docSubject']").closest("td").find(".validation-advice").hide();
                }
                if(!rs){
                    $(".model-edit-view-bar").find("[resPanel-bar-mark='basic']").trigger($.Event("click"));
                    seajs.use(['lui/dialog'],
                        function (dialog) {
                            dialog.alert('${lfn:message("sys-modeling-base:modeling.submit.check.not.pass")}');
                        })
                }
            	
            	// 模板配置项的校验
            	if (rs && !window.mobileCustomDesigner.validate()) {
            		rs = false;
                }

            	return rs;
            }

            function modeling_submit(method) {
                if(!formValidate()){
                	return false;
                }
                var keyData = window.mobileCustomDesigner.getKeyData();
                $("[name='fdIndex']").val("custom");
                $("[name='fdDesignCfg']").val(JSON.stringify(keyData));
                Com_Submit(document.modelingAppMobileForm, method);
            }

            function openMobileIndex(dom){
                var fdUrl = $(dom).closest(".modelingMobileUrl").find("[name='__fdUrl']").val();
                Com_OpenWindow(fdUrl, "_blank");
            }

            seajs.use(["lui/jquery",
                    "lui/topic", 'lui/dialog',
                    "sys/modeling/base/mobile/design/custom/designer",
                    "lui/util/env"
                ],
                function ($, topic, dialog, designer, env) {
            		// 设置移动端地址
            		var tempUrl = "/sys/modeling/main/mobile/modelingAppMainMobile.do?method=index&fdId=" + ___appId + "&fdMobileId=" + ___fdId;
                    // 基础数据的初始化
                    function baseInfoInit(params){
                        seajs.use(["lui/jquery", "lui/util/env"],function($, env){
                            // 初始化访问地址
                            var mobileIndexUrl = env.fn.formatUrl(params.tempUrl, true);
                            $("[name='__fdUrl']").val(mobileIndexUrl);
                        })
                    }
                    baseInfoInit({tempUrl: tempUrl});
            		
                    /***************** moduleDesign init start ******************/
                    var fdDesignCfg = JSON.parse($("[name=fdDesignCfg]").val() || '{}');

                    window.mobileCustomDesigner = new designer.Designer({
                        mode:$("[name=fdIndex]").val()||'custom',
                        data:fdDesignCfg
                    });
                    window.mobileCustomDesigner.startup();
                    window.mobileCustomDesigner.initByStoreData();
                    // 预览
                    window.previewOpen = function () {
                        var keyData = window.mobileCustomDesigner.getKeyData();
                        var fdDesignCfgJSON = JSON.parse($("[name=fdDesignCfg]").val() || '{}');
                        if(JSON.stringify(keyData) === JSON.stringify(fdDesignCfgJSON)) {
                           preview()
                        }else {
                            dialog.confirm('${lfn:message('sys-modeling-base:modeling.previewTemplate.save.configuration')}',function(value){
                                    if(value==true){
                                    var pass = modeling_submit("save");
                                    if (pass != false){
                                        preview()
                                    }
                                }
                            });
                        }
                   }
                   function preview (){
                       var url = '/sys/modeling/base/mobile/preview_custom.jsp?fdAppId=${param.fdAppId}';
                       var height = 800;
                       var width = 638;
                       var docSubject = $("[name='docSubject']").val();
                        dialog.iframe(url, "${lfn:message('sys-modeling-base:modeling.mobile.settings.preview')}", null, {
                            width: width,
                            height: height,
                            params: {
                                mobileUrl: tempUrl.substring(1),
                                docSubject:docSubject
                            }
                        }).on("show", function () {
                            this.element.css("z-index", "1000");
                            // this.element.find("iframe").attr("scrolling", "no");
                        });
                    }

                    window.returnListPage = function() {
                        var keyData = window.mobileCustomDesigner.getKeyData();
                        var fdDesignCfgJSON = JSON.parse($("[name=fdDesignCfg]").val() || '{}');
                        if(JSON.stringify(keyData) === JSON.stringify(fdDesignCfgJSON)) {
                            _closeWindow();
                        }else{
                            dialog.confirm(Com_Parameter.CloseInfo,
                                function(value) {
                                    if (value) {
                                        _closeWindow();
                                    } else
                                        return;
                                });
                        }
                        return false;
                    }
                    function _closeWindow(){
                        var url = Com_Parameter.ContextPath + 'sys/modeling/base/mobile/index.jsp?fdAppId=${modelingAppMobileForm.fdApplicationId}';
                        var iframe = window.parent.document.getElementById("trigger_iframe");
                        var tabTitle = window.parent.document.getElementById("space-title");
                        $(tabTitle).css("display","block");
                        $(iframe).attr("src", url);
                    }
                    $("[name='fdDesc']").on('input propertychange', function(){
                        // 中文字符长度为3
                        var newvalue = $(this).val().replace(/[^\x00-\xff]/g, "***");
                        if(newvalue>500){
                            formValidation.validateElement(this);
                        }
                        $(".modelAppSpaceDescContentLength").text(newvalue.length);
                    });
                    $("[name='fdDesc']").trigger($.Event("propertychange"));
                });
        </script>
