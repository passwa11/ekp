<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<c:set var="tiny" value="true" scope="request"/>
<template:include ref="mobile.edit" compatibleMode="true" gzip="true" isNative="true">
    <template:replace name="title">
        <c:out value="${modelingAppSimpleMainForm.fdModelName}"></c:out>
    </template:replace>
    <template:replace name="head">
        <mui:min-file name="mui-review-view.css"/>
        <mui:min-file name="sys-lbpm.css"/>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/main/mobile/css/common.css?s_cache=${LUI_Cache}"/>
    </template:replace>
    <template:replace name="content">
        <html:hidden property="detailOperationAuthConfig" value="${detailOperationAuthConfig}"/>
        <html:form action="/sys/modeling/main/externalShare.do" enctype="multipart/form-data" method="post">
            <html:hidden property="listviewId" value="${param.listviewId}"/>
            <html:hidden property="fdId" value="${modelingAppSimpleMainForm.fdId}"/>
            <html:hidden property="docStatus"/>
            <html:hidden property="fdModelId"/>
            <html:hidden property="docCreateTime"/>
            <html:hidden property="docCreatorId"/>
            <html:hidden property="fdTreeNodeData"/>
            <div>
                <div data-dojo-type="mui/view/DocScrollableView"
                     data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView">
                    <div class="muiFlowInfoW muiFormContent">
                        <html:hidden property="fdId"/>
                        <c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
                            <c:param name="formName" value="modelingAppSimpleMainForm"/>
                            <c:param name="moduleModelName"
                                     value="com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain"/>
                        </c:import>
                    </div>
                    <div class="muiFlowInfoW muiFormContent">
                        <c:import url="/sys/xform/mobile/import/sysForm_mobile.jsp" charEncoding="UTF-8">
                            <c:param name="formName" value="modelingAppSimpleMainForm"/>
                            <c:param name="fdKey" value="modelingApp"/>
                            <c:param name="backTo" value="scrollView"/>
                        </c:import>
                    </div>
                    <ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
                        <li data-dojo-type="mui/tabbar/TabBarButton" class="mainTabBarButton"
                            data-dojo-props='onClick:modeling_submit'>
                            ${lfn:message('sys-modeling-main:modeling.submit') }
                        </li>
                    </ul>
                </div>

            </div>
        </html:form>
        <script type="text/javascript">
            var validation = $KMSSValidation();
            Com_IncludeFile("detailOperationAuth.js", "${LUI_ContextPath}/sys/modeling/main/resources/js/", 'js', true);
            require(["mui/form/ajax-form!modelingAppSimpleMainForm"]);
            var modelingAttachments = {};
            var externalConfig={
                "fileLimitCount":${fileLimitCount},
                "singleFileSize":${singleFileSize},
                "fileMaxSize":${fileMaxSize},
                "fileEnabledType":'${fileEnabledType}',
            };
            var attachmentCount = {
                fileSizeCount : 0,
                fileCount:0
            };
            require(["dijit/registry", 'mui/dialog/Confirm',"mui/dialog/Tip"], function (registry, Confirm,Tip) {

                function controlShield(){
                    //图片
                    $("[_xform_type='xform_docimg']").empty();
                    //数据填充
                    //大数据呈现
                    $("[_xform_type='massData']").empty();
                    //excel导入
                    $(".muiSimple").each(function (index) {
                        if(0 < index){
                            if($(this).parents('.detailTableContent').length == 0){
                                $(this).empty();
                            }
                        }
                    });

                }

                controlShield();

                window.modeling_submit = function () {
                    var scrollViewWgt = registry.byId("scrollView");
                    if (validation.validate() && Modeling_DataUniqueValidate()) {
                        // 执行校验
                        if (scrollViewWgt.performTransition()) {
                            var method = Com_GetUrlParameter(location.href, 'method');
                            if (method == 'add') {
                                var formObj = document.modelingAppSimpleMainForm;
                                var i;
                                var url = Com_CopyParameter(formObj.action);
                                url = Com_SetUrlParameter(url, "method", "save");
                                var seq = parseInt(Com_GetUrlParameter(url, "s_seq"));
                                seq = isNaN(seq)?1:seq+1;
                                url = Com_SetUrlParameter(url, "s_seq", seq);
                                formObj.action = url;
                                var btns = document.getElementsByTagName("INPUT");
                                for(i=0; i<btns.length; i++)
                                    if(btns[i].type=="button" || btns[i].type=="image")
                                        btns[i].disabled = true;
                                btns = document.getElementsByTagName("A");
                                for(i=0; i<btns.length; i++){
                                    btns[i].disabled = true;
                                    btns[i].removeAttribute("href");
                                    btns[i].onclick = null;
                                }
                                var formData = new FormData(formObj);
                                for(var flagId in modelingAttachments){
                                    var modelingAttach = modelingAttachments[flagId];
                                    var fileList = modelingAttach.filekeys;
                                    for(var i=0;i <= fileList.length-1;i++){
                                        var file = fileList[i];
                                        formData.append(flagId, file);
                                    }
                                }
                                $.ajax({
                                    //几个参数需要注意一下
                                    type: "POST",//方法类型
                                    dataType: "json",//预期服务器返回的数据类型
                                    url: url,
                                    data: formData,
                                    cache: false,
                                    contentType: false,
                                    processData: false,
                                    success: function (result) {
                                        if (result["status"] === "1") {
                                            window.location.href=Com_Parameter.ContextPath + "third/pda/resource/jsp/mobile/success.jsp";
                                        }else{
                                            var tips = result["msg"];
                                            Tip.fail({
                                                text: tips,
                                                callback: function(){
                                                    scrollViewWgt.show();
                                                }
                                            });
                                        }
                                    },
                                    error : function() {
                                        alert("异常！");
                                    }
                                });
                            }
                        }
                    }
                }
                window.Modeling_DataUniqueValidate = function () {
                    var isUnique = true;
                    var url = Com_Parameter.ContextPath + "sys/modeling/main/externalShare.do?method=dataValidate";
                    $.ajax({
                        url: url,
                        type: "post",
                        data: $('form').serialize(),
                        async: false,
                        success: function (rtn) {
                            if(!rtn.status){
                                var tips = rtn.errmsg || "${lfn:message('sys-modeling-main:modeling.data.uniqueness.verification.tips') }";
                                Tip.fail({
                                    text: tips,
                                    callback: function(){

                                    }
                                });
                                isUnique = false;
                            }else if (rtn.status === '01') {
                                var tips = rtn.errmsg || "${lfn:message('sys-modeling-main:modeling.data.uniqueness.verification.tips') }";
                                Tip.fail({
                                    text: tips,
                                    callback: function(){

                                    }
                                });
                                isUnique = false;
                            }
                        },
                        error: function (rtn) {
                            var tips = rtn.errmsg || "${lfn:message('sys-modeling-main:modeling.data.uniqueness.verification.tips') }";
                            Tip.fail({
                                text: tips,
                                callback: function(){

                                }
                            });
                            isUnique = false;
                        }
                    });

                    return isUnique;
                }
            })
            require(["dojo/ready","dijit/registry",'sys/modeling/main/resources/js/mobile/externalShare/shareAttachment'], function (ready,registry,shareAttachment) {
                ready(function () {
                    initDetailOperationAuth($("input[name='detailOperationAuthConfig']").val(), false, "${param.method}", null, true);
                    //处理表单附件支持对外分享
                    $("[_xform_type='attachment']").each(function (i){
                        var $attachment = $(this);
                        //找到附件机制组件
                        var widgetid = $attachment.find('[data-dojo-type="sys/attachment/mobile/js/AttachmentList"]').attr("widgetid");
                        var attachmentWgt = registry.byId(widgetid);
                        if(attachmentWgt){
                            //对外分享附件函数
                            var attachment = new shareAttachment();
                            //附件机制混入对外分享的函数，由对外分享替换附件机制的上传函数
                            dojo.safeMixin(attachmentWgt,attachment);
                            attachmentWgt.initEvent();
                            // 支持免登上传
                            if (window.attachmentConfig) {
                                window.attachmentConfig.isSupportShare = true;
                            }
                            modelingAttachments[attachmentWgt.fdKey] = attachmentWgt;
                        }
                    })
                });
            });
        </script>
    </template:replace>
</template:include>
