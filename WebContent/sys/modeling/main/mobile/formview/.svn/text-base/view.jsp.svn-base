<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/modeling/modeling.tld" prefix="modeling" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<c:set var="tiny" value="true" scope="request"/>
<%
    Object tabListObj = request.getAttribute("tabList");
    StringBuffer tabsStringBuffer = new StringBuffer();
    if(tabListObj instanceof List<?>){
        List<Map<String, String>> tabList = (List<Map<String, String>>) request.getAttribute("tabList");
        for(int i = 0;i < tabList.size();i++){
            Map<String, String> tab = tabList.get(i);
            String tabType = tab.get("fdTabType");
            String fdIsShow = tab.get("fdIsShow");
            if(("0".equals(tabType) || "1".equals(tabType) || "4".equals(tabType)) && "1".equals(fdIsShow)) {
                    //0:自定义标签 1：访问统计 4：传阅记录
                    String fdId = tab.get("fdId");
                    tabsStringBuffer.append(fdId).append(",");
            }
        }
    }
%>
<c:set var="tabsId" value="<%=tabsStringBuffer%>" scope="request"/>
<template:include ref="mobile.view" compatibleMode="true" gzip="true" isNative="true">
    <template:replace name="title">
        <c:out value="${modelingAppModelMainForm.fdModelName}"></c:out>
    </template:replace>
    <template:replace name="head">
        <mui:cache-file name="mui-review-view.css" cacheType="md5"/>
        <mui:cache-file name="mui-review-view.js" cacheType="md5"/>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/main/mobile/css/common.css?s_cache=${LUI_Cache}"/>
        <script type="text/javascript">
            if(dojoConfig){
                dojoConfig.tiny = true;
            }
        </script>
    </template:replace>
    <template:replace name="content">
        <html:form action="/sys/modeling/main/modelingAppModelMain.do">
            <div id="scrollView"
                 data-dojo-type="mui/view/DocScrollableView"
                 data-dojo-mixins="mui/form/_ValidateMixin" class="muiFlowBack">
                <c:import url="/sys/modeling/main/mobile/formview/view_banner.jsp"
                          charEncoding="UTF-8">
                    <c:param name="formBeanName" value="modelingAppModelMainForm"></c:param>
                    <c:param name="formType" value="main"></c:param>
                </c:import>
                <html:hidden property="fdId"/>
                <html:hidden property="fdModelId"/>
                <html:hidden property="docCreateTime"/>
                <html:hidden property="docCreatorId"/>
                <c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="modelingAppModelMainForm"/>
                    <c:param name="moduleModelName" value="com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain"/>
                </c:import>
                <div class="muiFlowInfoW">
                    <div data-dojo-type="mui/fixed/Fixed" data-dojo-props="noSrcoll:true" id="fixed">
                        <div data-dojo-type="mui/fixed/FixedItem" class="muiFlowFixedItem">
                            <!--流程未结束使用系统标签-->
                            <c:if test="${modelingAppModelMainForm.docStatus eq '30'}">
                                <div data-dojo-type="mui/nav/MobileCfgNavBar" id="modelingTab"
                                     data-dojo-props="tabId:'${tabsId}',modelId:'${modelingAppModelMainForm.fdId }',modelName:'com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain'"
                                     data-dojo-mixins="sys/modeling/main/resources/js/mobile/formview/ViewCommon">
                                </div>
                            </c:if>
                            <!--业务标签-->
                            <c:if test="${modelingAppModelMainForm.docStatus ne '30'}">
                            	<div data-dojo-type="mui/nav/NavBarStore" id="modelingTab"
                                     data-dojo-props="tabId:'${tabsId}',modelId:'${modelingAppModelMainForm.fdId }',modelName:'com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain',isOnlyShowAccessLog: false"
                                     data-dojo-mixins="sys/modeling/main/resources/js/mobile/formview/ViewCommon">
                            	</div>
	                            <div data-dojo-type="sys/modeling/main/resources/js/mobile/listView/ViewTab"
	                                 <%--data-dojo-mixins="sys/modeling/main/mobile/formview/_PromptPanelMixin"--%>
	                                 data-dojo-props="tabId:'${tabsId}',modelId:'${modelingAppModelMainForm.fdId }',modelName:'com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain'">
	                            </div>
                           </c:if>
                        </div>
                    </div>
                    <div data-dojo-type="dojox/mobile/View" id="_contentView">
                        <div data-dojo-type="mui/table/ScrollableHContainer">
                            <c:import url="/sys/xform/mobile/import/sysForm_mobile.jsp"
                                      charEncoding="UTF-8">
                                <c:param name="formName" value="modelingAppModelMainForm"/>
                                <c:param name="fdKey" value="reviewMainDoc"/>
                                <c:param name="backTo" value="scrollView"/>
                            </c:import>
                        </div>
                    </div>
                    <div data-dojo-type="mui/DelayView" id="_noteView">
                        <div class="muiFormContent">
                            <c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp"
                                      charEncoding="UTF-8">
                                <c:param name="fdModelId" value="${modelingAppModelMainForm.fdId }"/>
                                <c:param name="fdModelName"
                                         value="com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain"/>
                                <c:param name="formBeanName" value="modelingAppModelMainForm"/>
                            </c:import>
                            <xform:isExistRelationProcesses relationType="parent">
                                <xform:showParentProcesse mobile="true"/>
                            </xform:isExistRelationProcesses>

                            <xform:isExistRelationProcesses relationType="subs">
                                <xform:showSubProcesses mobile="true"/>
                            </xform:isExistRelationProcesses>
                        </div>
                    </div>
                    <!-- 动态页签内容 -->
                    <div data-dojo-type="dojox/mobile/View" id="_otherContentView">
                        <div class="muiFormContent"
                             data-dojo-mixins="sys/modeling/main/resources/js/mobile/formview/DynamicContent"
                             data-dojo-type="mui/table/ScrollableHContainer">
                        </div>
                    </div>
                </div>

                <template:include file="/sys/modeling/main/mobile/formview/tarbar.jsp"
                                  formName="modelingAppModelMainForm"
                                  viewName="lbpmView"
                                  allowReview="${modelingAppModelMainForm.fdIsMobileApprove!='false'}">
                    <!-- 流程过程中 -->
                    <template:replace name="modelingOperationsArea">
                        <ul data-dojo-type="sys/modeling/main/resources/js/mobile/formview/OperTabBar" data-dojo-props='fill:"grid",operations:${empty jsonOperList ? [] : jsonOperList }, isFlow: "true", fdId:"${param.fdId }",listviewId:"${param.listviewId }",fdModelId:"${modelingAppModelMainForm.fdModelId}"'>
                            <c:if test="${modelingAppModelMainForm.sysWfBusinessForm.fdIsHander == 'true' && modelingAppModelMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.eqbSign =='true'}">
                                <c:import url="/elec/eqb/elec_eqb_common_default/elecEqbCommonDefaultSign.do?method=getEqbSignPage" charEncoding="UTF-8">
                                    <c:param name="signId" value="${modelingAppModelMainForm.fdId }" />
                                </c:import>
                            </c:if>
                            <c:if test="${existOpinion}">
                                <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
                                    <c:param name="formName" value="modelingAppModelMainForm"></c:param>
                                    <c:param name="isNewVersion" value="true"></c:param>
                                    <c:param name="existOpinion" value="true"></c:param>
                                </c:import>
                            </c:if>
                            <c:forEach items="${operList }" var="oper">
                                <c:choose>
                                    <c:when test="${oper.fdDefType eq '13'}">
                                        <modeling:mauthurl fdOprId="${oper['fdOperId']}">
                                            <kmss:auth requestURL="${modelingAuthUrl}" requestMethod="GET">
                                                <%--传阅 --%>
                                                <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
                                                    <c:param name="formName" value="modelingAppModelMainForm"></c:param>
                                                    <c:param name="showOption" value="label"></c:param>
                                                    <c:param name="isNewVersion" value="true"></c:param>
                                                    <c:param name="label" value="${oper['text']}"></c:param>
                                                </c:import>
                                            </kmss:auth>
                                        </modeling:mauthurl>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </ul>
                    </template:replace>
                    <!-- 流程发布后 -->
                    <template:replace name="modelingOperationsPublishArea">
                        <ul data-dojo-type="sys/modeling/main/resources/js/mobile/formview/OperTabBar"
                            data-dojo-props='fill:"grid",operations:${empty jsonOperList ? [] : jsonOperList }, isFlow: true, fdId:"${param.fdId }",listviewId:"${param.listviewId }", docStatus:"${modelingAppModelMainForm.docStatus}",fdModelId:"${modelingAppModelMainForm.fdModelId}"'
                            fixed="bottom">
                            <c:if test="${existOpinion}">
                                <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
                                    <c:param name="formName" value="modelingAppModelMainForm"></c:param>
                                    <c:param name="isNewVersion" value="true"></c:param>
                                    <c:param name="existOpinion" value="true"></c:param>
                                </c:import>
                            </c:if>
                            <c:forEach items="${operList }" var="oper">
                                <c:choose>
                                    <c:when test="${oper.fdDefType eq '13'}">
                                        <modeling:mauthurl fdOprId="${oper['fdOperId']}">
                                            <kmss:auth requestURL="${modelingAuthUrl}" requestMethod="GET">
                                                <%--传阅 --%>
                                                <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
                                                    <c:param name="formName" value="modelingAppModelMainForm"></c:param>
                                                    <c:param name="showOption" value="label"></c:param>
                                                    <c:param name="isNewVersion" value="true"></c:param>
                                                    <c:param name="label" value="${oper['text']}"></c:param>
                                                </c:import>
                                            </kmss:auth>
                                        </modeling:mauthurl>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </ul>
                    </template:replace>
                </template:include>
            </div>

            <!-- 钉钉图标 -->
            <kmss:ifModuleExist path="/third/ding">
                <c:import url="/third/ding/import/ding_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="modelingAppModelMainForm"/>
                </c:import>
            </kmss:ifModuleExist>
            <kmss:ifModuleExist path="/third/lding">
                <c:import url="/third/lding/import/ding_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="modelingAppModelMainForm"/>
                </c:import>
            </kmss:ifModuleExist>
            <!-- 钉钉图标 end -->
            <c:if test="${modelingAppModelMainForm.docStatus < '30' }">
                <c:choose>
                    <c:when test="${'false' eq modelingAppModelMainForm.fdIsMobileApprove}">
                        <script type="text/javascript">
                            require(["mui/dialog/BarTip", "dojo/ready"], function(BarTip, ready) {
                                ready(function() {
                                    BarTip.tip({text: "<bean:message key='sys-modeling-base:modeling.flow.tipmessage.approve'/>"});
                                });
                            });
                        </script>
                    </c:when>
                    <c:otherwise>
                        <c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
                            <c:param name="formName" value="modelingAppModelMainForm"/>
                            <c:param name="fdKey" value="reviewMainDoc"/>
                            <c:param name="lbpmViewName" value="lbpmView"/>
                            <c:param name="onClickSubmitButton"
                                     value="Com_Submit(document.modelingAppModelMainForm, 'publishUpdate');"/>
                        </c:import>
                    </c:otherwise>
                </c:choose>
                <script type="text/javascript">
                    require(["mui/form/ajax-form!modelingAppModelMainForm"]);
                    window.addEventListener("pageshow", function (e) {
                        if (e.persisted) { // 浏览器后退的时候重新刷新
                            window.location.reload();
                        }
                    }, false);

                </script>
            </c:if>
            <c:if test="${modelingAppModelMainForm.docStatus eq '00'}">
                <script type="text/javascript">
                    require(["dojo/ready"], function (ready) {
                        ready(function () {
                            if(document.getElementById("discardStatusDiv")){
                                document.getElementById("discardStatusDiv").className = "muiProcessStatus muiDiscardStatus stamp";
                            }
                        });
                    });
                </script>
            </c:if>

        </html:form>

    </template:replace>
</template:include>