<%@page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<%-- 表单 --%>
<c:if test="${param.approveModel ne 'right'}">
    <c:choose>
            <%-- E签宝特殊处理 --%>
            <c:when test="${modelingAppModelMainForm.sysWfBusinessForm.fdIsHander == 'true' && modelingAppModelMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.eqbSign =='true'}">
                <ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop"
                             var-count="5" var-average='false' var-useMaxWidth='true'>
                    <ui:content title="${ lfn:message('sys-modeling-main:modelingAppBaseModel.reviewContent') }" toggle="false">
                        <div id="sysModelingXform">
                            <c:import url="/sys/xform/include/sysForm_view.jsp"
                                      charEncoding="UTF-8">
                                <c:param name="formName" value="modelingAppModelMainForm"/>
                                <c:param name="fdKey" value="modelingApp"/>
                                <c:param name="messageKey" value="sys-modeling-main:modelingAppBaseModel.reviewContent"/>
                                <c:param name="useTab" value="false"/>
                            </c:import>
                        </div>
                        <script>
                            Com_IncludeFile('view_common.js', '${KMSS_Parameter_ContextPath}sys/modeling/main/resources/js/', 'js', true);
                        </script>
                    </ui:content>
                    <c:import url="/elec/eqb/elec_eqb_common_default/elecEqbCommonDefaultSign.do?method=getEqbSignPage" charEncoding="UTF-8">
                        <c:param name="signId" value="${modelingAppModelMainForm.fdId }" />
                        <c:param name="enable" value="true"></c:param>
                    </c:import>
                </ui:tabpanel>
            </c:when>
        <c:otherwise>
            <ui:content title="${ lfn:message('sys-modeling-main:modelingAppBaseModel.reviewContent') }" toggle="false">
                <div id="sysModelingXform">
                    <c:import url="/sys/xform/include/sysForm_view.jsp"
                              charEncoding="UTF-8">
                        <c:param name="formName" value="modelingAppModelMainForm"/>
                        <c:param name="fdKey" value="modelingApp"/>
                        <c:param name="messageKey" value="sys-modeling-main:modelingAppBaseModel.reviewContent"/>
                        <c:param name="useTab" value="false"/>
                    </c:import>
                </div>
                <script>
                    Com_IncludeFile('view_common.js', '${KMSS_Parameter_ContextPath}sys/modeling/main/resources/js/', 'js', true);
                </script>
            </ui:content>
        </c:otherwise>
    </c:choose>
</c:if>
<template:replace name="nav">
    <!-- 传阅意见-->
    <c:if test="${existOpinion}">
        <ui:accordionpanel style="min-width:200px;min-height:100px;" layout="sys.ui.accordionpanel.simpletitle">
            <ui:content title="${ lfn:message('km-review:kmReviewMain.circulation.option') }" id="circulation" >
                <ui:iframe src="${KMSS_Parameter_ContextPath}sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=edit&fdModelId=${modelingAppModelMainForm.fdId}&fdModelName=com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain">
                </ui:iframe>
            </ui:content>
        </ui:accordionpanel>
    </c:if>
</template:replace>
<%-- 查看视图定义标签 --%>
<c:import url="/sys/modeling/base/view/ui/viewtabs.jsp" charEncoding="UTF-8">
    <c:param name="expand" value="false"/>
</c:import>

