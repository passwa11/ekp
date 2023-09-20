<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<link type="text/css" rel="stylesheet"
      href="${KMSS_Parameter_ContextPath}sys/modeling/base/mobile/resources/css/index.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${KMSS_Parameter_ContextPath}sys/modeling/base/mobile/design/css/design.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modelTable.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${KMSS_Parameter_ContextPath}sys/modeling/base/mobile/resources/css/swiper.css?s_cache=${LUI_Cache}"/>
<link rel="stylesheet" type="text/css"
      href="${KMSS_Parameter_ContextPath}sys/mobile/css/themes/default/font-mui.css"></link>
<link rel="stylesheet" type="text/css"
      href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/collection.css"></link>
<html:form action="/sys/modeling/base/mobile/modelingAppMobile.do">
    <div class="model-body">
        <!-- 页面控制逻辑组件 -->
        <div data-lui-type="sys/modeling/base/mobile/resources/js/indexPageControl!IndexPageControl"
             style="display:none;">
            <script type="text/config">
                {
                    type : "${isNew}"==="true"?"empty":"design",
                    page : {
                        "empty" : ".model-body-wrap",
                        "design" : ".moduleDesigner"
                    }
                }
            </script>
        </div>
        <!-- 初次页面 -->
        <div class="model-body-wrap" style="display: none;">
            <div class="model-body-phone-wrap">
                <div class="model-body-phone-content">
                    <div class="model-body-phone-empty">
                        <div class="model-body-phone-empty-img">
                            <div></div>
                            <p>${lfn:message('sys-modeling-base:modeling.build.mobile.app')}</p>
                        </div>
                        <div class="model-body-phone-empty-btn" onclick="selectIndexTmp()">${lfn:message('sys-modeling-base:modeling.build.now')}</div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 模块设计 -->
        <div class="moduleDesigner" style="display: none;">
            <div class="mobileDesignerMain">
                <div class="designerMainLeft">
                    <div class="model-mind-map-center">
                        <div class="model-mind-map-title">
                                ${lfn:message('sys-modeling-base:modeling.old.version.tips')}
                        </div>
                        <i onclick="closeTips()"></i>
                    </div>
<%--                    <p class="designerMainLeft-desc" onclick="selectIndexTmp()">${lfn:message('sys-modeling-base:modeling.Switch.template')}</p>--%>
                    <div class="mobileView">
                        <div class="model-body-content-phone">
                            <div class="model-body-content-phone-wrap">
                                <div class="model-body-content-phone-view"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="designerMainright">
                    <div class="panelHead">
                        <p class="head_title">${lfn:message('sys-modeling-base:modelingAppCollecctionView.fdConfig')}</p>
                        <div class="head_btn">
                            <c:if test="${modelingAppMobileForm.method_GET=='edit'}">
                                <div class="head_btn_item" onclick="modeling_submit('update')">${lfn:message('sys-modeling-base:modeling.save')}</div>
                            </c:if>
                            <c:if test="${modelingAppMobileForm.method_GET=='add'}">
                                <div class="head_btn_item" onclick="modeling_submit('save')">${lfn:message('sys-modeling-base:modeling.button.submit')}</div>
                            </c:if>
                            <div class="head_btn_item" onclick="previewOpen()">${lfn:message('sys-modeling-base:sys.profile.modeling.preview')}</div>
                        </div>
                    </div>
                    <div class="mobileAttrPanelMain">
                    <div id="mobileAttrPanelBasic">
                        <c:import url="/sys/modeling/base/mobile/baseInfo.jsp" charEncoding="UTF-8"></c:import>
                    </div>
                    <div class="mobileAttrPanel">
                    </div>
                    </div>
                </div>
                <%--提供高级设置的位置--%>
                <div class="designerMainrightSetting"></div>
            </div>
        </div>
    </div>

    <!-- 选择模板 -->
    <div id="indexTemplate" data-lui-type="sys/modeling/base/mobile/resources/js/indexTemplate!IndexTemplate"
         class="model-mask" style="display:none;">
        <ui:source type="AjaxJson">
            { url : '/sys/modeling/base/mobile/modelingAppMobile.do?method=getIndexTemplate'}
        </ui:source>
        <div data-lui-type="lui/view/render!Template">
            <script type="text/config">
                {
                    src : '/sys/modeling/base/mobile/resources/js/indexTemplateRender.html#'
                }
            </script>
        </div>
    </div>

    <!-- 用于预防当前form只有一个input时，点击enter自动提交表单 -->
    <input type="text" style="display: none;"/>
    <html:hidden property="fdId"/>
    <html:hidden property="fdIndex"/>
    <html:hidden property="fdDesignCfg"/>
    <html:hidden property="docCreatorId"/>
    <html:hidden property="docCreateTime"/>
    <html:hidden property="fdApplicationId"/>
<script>
    Com_IncludeFile("dialog.js");
    Com_IncludeFile("swiper2.7.6.min.js", Com_Parameter.ContextPath + 'sys/modeling/base/mobile/resources/js/', 'js', true);
    //Com_IncludeFile("idangerous.swiper.js", Com_Parameter.ContextPath + 'sys/modeling/base/mobile/resources/js/', 'js', true);

    var ___appId = "${param.fdAppId}";
    var ___fdId = "${param.fdId}";

    // 选择首页模板
    function selectIndexTmp() {
        LUI("indexTemplate").select();
    }

    function formValidate(){
        var rs = true;
        // 基础信息的校验
        if(baseInfoValidate){
            rs = baseInfoValidate();
        }
        //排序号的校验是否为正整数
        if(rs){
            rs=checkOrder();
        }

        // 模板配置项的校验
        if (rs && !mobileDesigner.validate()) {
            rs = false;
        }

        return rs;
    }

    function modeling_submit(method) {
        if(!formValidate()){
            return false;
        }
        var keyData = mobileDesigner.getKeyData();
        $("[name='fdDesignCfg']").val(JSON.stringify(keyData));
        Com_Submit(document.modelingAppMobileForm, method);
    }

    seajs.use(["lui/jquery",
            "lui/topic", 'lui/dialog',
            "sys/modeling/base/mobile/design/js/designer",
            "sys/modeling/base/mobile/design/js/panel",
            "sys/modeling/base/mobile/design/mode/default/mobileDefaultView",
            "sys/modeling/base/mobile/design/mode/list/mobileList",
            "sys/modeling/base/mobile/design/mode/mportal/mobileMportal",
            "sys/modeling/base/mobile/design/mode/mportalList/mobileMportalList",
            "lui/util/env"
        ],
        function ($, topic, dialog, designer, panel, defaultView, mobileList,mobileMportal, mobileMportalList, env) {
            // 设置移动端地址
            var tempUrl = "/sys/modeling/main/mobile/modelingAppMainMobile.do?method=index&fdId=" + ___appId + "&fdMobileId=" + ___fdId;
            // 基础数据的初始化
            if(baseInfoInit){
                baseInfoInit({tempUrl: tempUrl});
            }

            /***************** moduleDesign init start ******************/
            var fdDesignCfg = JSON.parse('${modelingAppMobileForm.fdDesignCfg}' || '{}');
            console.log(fdDesignCfg);
            window.mobileDesigner = new designer.Designer({
                data: $.extend({}, {
                    mode: $("[name='fdIndex']").val() || "default"
                }, fdDesignCfg)
            });

            mobileDesigner.setAttrPanel(new panel({
                element: $(".mobileAttrPanel")
            }));
            mobileDesigner.startup();

            // 注册默认视图模板，后续可以添加多个不同视图模板
            registerMobileMode("default",$("<div class='defaultView'></div>").appendTo($(".model-body-content-phone-view")), defaultView, mobileDesigner);
            registerMobileMode("list",$("<div class='mobileList'></div>").appendTo($(".model-body-content-phone-view")), mobileList, mobileDesigner);	// 注册列表视图
            registerMobileMode("mportal",$("<div class='mportal'></div>").appendTo($(".model-body-content-phone-view")), mobileMportal, mobileDesigner);	// 注册门户视图
            registerMobileMode("mportalList",$("<div class='mportalList'></div>").appendTo($(".model-body-content-phone-view")), mobileMportalList, mobileDesigner);	// 注册门户列表视图

            mobileDesigner.changeMode(mobileDesigner.data.mode);
            //mobileDesigner.changeMode("mportal");
            /***************** moduleDesign init end ******************/

            /**
            * 注册移动模板到设计器
            */
            function registerMobileMode(key, element, claz, designer){
                var clazInstance = new claz({
                    element: element,
                    parent: designer
                });
                clazInstance.startup();
                designer.register(key, clazInstance);
            }
            topic.channel("modeling").subscribe("mobile.listview.tab.open", function (dom) {
             /*  console.log(dom)
               console.log($(dom.dom).html())
               $(".designerMainright").hide();
               $(".designer-tab-item").show();
               $(".designer-tab-item").html($(dom.dom).html());
*/
            });
            topic.channel("modeling").subscribe("mobile.listview.tab.close", function (dom) {
                console.log(dom)
               /* $(".designerMainright").show();
                $(".designer-tab-item").hide();
                $(".designer-tab-item").html(dom);*/

            });

            // 预览
            window.previewOpen = function () {
                var keyData = mobileDesigner.getKeyData();
               if(JSON.stringify(keyData) === JSON.stringify(fdDesignCfg)) {
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
               var url = '/sys/modeling/base/mobile/preview.jsp?fdAppId=${param.fdAppId}';
               var height = 674;
               var width = 638;
                dialog.iframe(url, "${lfn:message('sys-modeling-base:modeling.mobile.settings.preview')}", null, {
                    width: width,
                    height: height,
                    params: {
                        mobileUrl: tempUrl.substring(1)
                    }
                }).on("show", function () {
                    this.element.css("z-index", "1000");
                    this.element.find("iframe").attr("scrolling", "no");
                });
            }

            window.closeTips = function (){
                $(".model-mind-map-center").hide();
            }
        });
</script>
</html:form>