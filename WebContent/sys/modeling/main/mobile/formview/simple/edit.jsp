<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
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
        <script type="text/javascript">
            if(dojoConfig){
                dojoConfig.tiny = true;
            }
        </script>
    </template:replace>
    <template:replace name="content">
        <html:hidden property="detailOperationAuthConfig" value="${detailOperationAuthConfig}"/>
        <html:form action="/sys/modeling/main/modelingAppSimpleMain.do">
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
                <script type="text/javascript">
                    Com_IncludeFile("detailOperationAuth.js", "${LUI_ContextPath}/sys/modeling/main/resources/js/", 'js', true);
                    require(["mui/form/ajax-form!modelingAppSimpleMainForm"]);
                    require(["dijit/registry", 'mui/dialog/Confirm'], function (registry, Confirm) {
                        window.modeling_submit = function () {
                            var scrollViewWgt = registry.byId("scrollView");
                            if (Modeling_DataUniqueValidate()) {
                                // 执行校验
                                if (scrollViewWgt.performTransition()) {
                                    var method = Com_GetUrlParameter(location.href, 'method');
                                    if (method == 'add') {
                                        Com_Submit(document.forms[0], 'save');
                                    } else {
                                        Com_Submit(document.forms[0], 'update');

                                    }
                                }
                            }
                        }
                        window.Modeling_DataUniqueValidate = function () {
                            var isUnique = false;
                            var url = Com_Parameter.ContextPath + "sys/modeling/main/dataValidate.do?method=dataValidate";
                            $.ajax({
                                url: url,
                                type: "post",
                                data: $('form').serialize(),
                                async: false,
                                success: function (rtn) {
                                    if (rtn.status  === '00') {
                                        isUnique = true;
                                    }else{
                                        var tips = rtn.errmsg || "${lfn:message('sys-modeling-main:modeling.data.uniqueness.verification.tips') }";
                                        Confirm(tips, null, function (status) {
                                        });
                                        isUnique = false;
                                    }
                                },
                                error: function (rtn) {
                                    var tips = rtn.errmsg || "${lfn:message('sys-modeling-main:modeling.data.uniqueness.verification.tips') }";
                                    Confirm(tips, null, function (status) {
                                    });
                                    isUnique = false;
                                }
                            });

                            return isUnique;
                        }
                    })
                    require(["dojo/ready"], function (ready) {
                        ready(function () {
                            initDetailOperationAuth($("input[name='detailOperationAuthConfig']").val(), false, "${param.method}", null, true);
                        });
                    });
                </script>
            </div>
        </html:form>
    </template:replace>
</template:include>
