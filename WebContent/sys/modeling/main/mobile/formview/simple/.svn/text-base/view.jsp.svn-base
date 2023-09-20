<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/modeling/modeling.tld" prefix="modeling" %>
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
            if(("0".equals(tabType) || "1".equals(tabType) || "4".equals(tabType)) && "1".equals(fdIsShow)){
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
        <c:out value="${modelingAppSimpleMainForm.fdModelName}"></c:out>
    </template:replace>
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/main/mobile/css/common.css?s_cache=${LUI_Cache}"/>
        <script type="text/javascript">
            if(dojoConfig){
                dojoConfig.tiny = true;
            }
        </script>
    </template:replace>
    <template:replace name="content">
        <html:form action="/sys/modeling/main/modelingAppSimpleMain.do">
            <c:import url="/sys/modeling/main/mobile/formview/view_banner.jsp"
                      charEncoding="UTF-8">
                <c:param name="formBeanName" value="modelingAppSimpleMainForm"></c:param>
                <c:param name="formType" value="simple"></c:param>
            </c:import>
            <html:hidden property="fdId"/>
            <html:hidden property="fdModelId"/>
            <html:hidden property="docCreateTime"/>
            <html:hidden property="docCreatorId"/>
            <c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="modelingAppSimpleMainForm"/>
                <c:param name="moduleModelName"
                         value="com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain"/>
            </c:import>
            <div id="scrollView"
                 data-dojo-type="mui/view/DocScrollableView"
                 data-dojo-mixins="mui/form/_ValidateMixin" class="muiFlowBack">
                <div class="muiFlowInfoW">
                    <div data-dojo-type="mui/fixed/Fixed" data-dojo-props="noSrcoll:true" id="fixed">
                        <div data-dojo-type="mui/fixed/FixedItem" class="muiFlowFixedItem">
                            <div data-dojo-type="mui/nav/MobileCfgNavBar" id="modelingTab"
                                 data-dojo-props="tabId:'${tabsId}',modelId:'${modelingAppSimpleMainForm.fdId }',modelName:'com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain'"
                                 data-dojo-mixins="sys/modeling/main/resources/js/mobile/formview/SimpleViewCommon">
                            </div>
                            <!-- 业务标签
                            <div data-dojo-type="sys/modeling/main/resources/js/mobile/listView/ViewTab"
                                 data-dojo-mixins="sys/modeling/main/mobile/formview/_PromptPanelMixin"
                                 data-dojo-props="tabId:'${tabsId}',url:'/sys/modeling/main/mobile/modelingAppMainMobileView.do?method=getMobileDataNavItem&fdModelName=!{modelName}&fdTabId=!{tabId}&fdModelId=!{modelId}',
                                 modelId:'${modelingAppSimpleMainForm.fdId }',
                                 modelName:'com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain'">
                            </div>
							-->
                        </div>
                    </div>

                    <div data-dojo-type="dojox/mobile/View" id="_contentView">
                        <div data-dojo-type="mui/table/ScrollableHContainer">
                            <c:import url="/sys/xform/mobile/import/sysForm_mobile.jsp"
                                      charEncoding="UTF-8">
                                <c:param name="formName" value="modelingAppSimpleMainForm"/>
                                <c:param name="fdKey" value="reviewMainDoc"/>
                                <c:param name="backTo" value="scrollView"/>
                            </c:import>
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

                <div data-dojo-type="mui/tabbar/TabBarGroup" fixed="bottom">
                    <ul data-dojo-type="sys/modeling/main/resources/js/mobile/formview/OperTabBar" data-dojo-props='fill:"grid",operations:${empty jsonOperList ? [] : jsonOperList }, isFlow: "false", fdId:"${param.fdId }",listviewId:"${param.listviewId }"'>
                        <c:set var="isShowCirculation" value="false"></c:set>
                        <c:set var="hasShowCirculation" value="false"></c:set>
                        <c:set var="cir_lable" value=""></c:set>
                        <c:if test="${existOpinion}">
                            <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
                                <c:param name="formName" value="modelingAppSimpleMainForm"></c:param>
                                <c:param name="isNewVersion" value="true"></c:param>
                                <c:param name="existOpinion" value="true"></c:param>
                            </c:import>
                        </c:if>
                        <c:forEach items="${operList }" var="oper">
                            <c:choose>
                                <c:when test="${oper.fdDefType eq '13'}">
                                    <c:set var="isShowCirculation" value="true"></c:set>
                                    <c:set var="cir_lable" value="${oper['text']}"></c:set>
                                    <modeling:mauthurl fdOprId="${oper['fdOperId']}">
                                        <kmss:auth requestURL="${modelingAuthUrl}" requestMethod="GET">
                                            <c:set var="hasShowCirculation" value="true"></c:set>
                                            <%--传阅 --%>
                                            <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
                                                <c:param name="formName" value="modelingAppSimpleMainForm"></c:param>
                                                <c:param name="showOption" value="label"></c:param>
                                                <c:param name="isNewVersion" value="true"></c:param>
                                                <c:param name="label" value="${cir_lable}"></c:param>
                                            </c:import>
                                        </kmss:auth>
                                    </modeling:mauthurl>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                        <!-- 继续传阅：无流程 -->
                        <c:if test="${hasShowCirculation eq 'false' && isShowCirculation eq 'true'}">
                            <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
                                <c:param name="formName" value="modelingAppSimpleMainForm"></c:param>
                                <c:param name="showOption" value="label"></c:param>
                                <c:param name="isNewVersion" value="true"></c:param>
                                <c:param name="label" value="${cir_lable}"></c:param>
                            </c:import>
                        </c:if>
                    </ul>
                    <%--<ul data-dojo-type="mui/tabbar/TabBar" data-dojo-props='fill:"grid"'>
                        <c:set var="isShowCirculation" value="false"></c:set>
                        <c:set var="hasShowCirculation" value="false"></c:set>
                        <c:if test="${existOpinion}">
                            <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
                                <c:param name="formName" value="modelingAppSimpleMainForm"></c:param>
                                <c:param name="isNewVersion" value="true"></c:param>
                                <c:param name="existOpinion" value="true"></c:param>
                            </c:import>
                        </c:if>
                        <c:forEach items="${operList }" var="oper">
                            <c:choose>
                                <c:when test="${oper.fdDefType eq '13'}">
                                    <c:set var="isShowCirculation" value="true"></c:set>
                                    <modeling:mauthurl fdOprId="${oper['fdOperId']}">
                                        <kmss:auth requestURL="${modelingAuthUrl}" requestMethod="GET">
                                            <c:set var="hasShowCirculation" value="true"></c:set>
                                            &lt;%&ndash;传阅 &ndash;%&gt;
                                            <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
                                                <c:param name="formName" value="modelingAppSimpleMainForm"></c:param>
                                                <c:param name="showOption" value="label"></c:param>
                                                <c:param name="isNewVersion" value="true"></c:param>
                                            </c:import>
                                        </kmss:auth>
                                    </modeling:mauthurl>
                                </c:when>
                                <c:otherwise>
                                    <modeling:mauthurl fdOprId="${oper['fdOperId']}">
                                        <kmss:auth requestURL="${modelingAuthUrl}" requestMethod="GET">
                                            <li data-dojo-type="mui/tabbar/TabBarButton"
                                                data-dojo-props='label:"${oper.text}",defType:"${oper.fdDefType}", operUrl:"${oper.url}", operId:"${oper.fdOperId}", fdId:"${param.fdId }",listviewId:"${param.listviewId }",isFlow:"false"'
                                                data-dojo-mixins="<%=request.getContextPath()%>/sys/modeling/main/resources/js/mobile/formview/OperTabBarButtonMixin.js"></li>
                                        </kmss:auth>
                                    </modeling:mauthurl>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <!-- 继续传阅：无流程 -->
                        <c:if test="${hasShowCirculation eq 'false' && isShowCirculation eq 'true'}">
                            <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
                                <c:param name="formName" value="modelingAppSimpleMainForm"></c:param>
                                <c:param name="showOption" value="label"></c:param>
                                <c:param name="isNewVersion" value="true"></c:param>
                            </c:import>
                        </c:if>
                    </ul>--%>
                </div>
            </div>

            <!-- 钉钉图标 -->
            <kmss:ifModuleExist path="/third/ding">
                <c:import url="/third/ding/import/ding_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="modelingAppSimpleMainForm"/>
                </c:import>
            </kmss:ifModuleExist>
            <kmss:ifModuleExist path="/third/lding">
                <c:import url="/third/lding/import/ding_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="modelingAppSimpleMainForm"/>
                </c:import>
            </kmss:ifModuleExist>
            <!-- 钉钉图标 end -->
            <c:if test="${modelingAppSimpleMainForm.docStatus eq '00'}">
                <script type="text/javascript">
                    require(["dojo/ready"], function (ready) {
                        ready(function () {
                            document.getElementById("discardStatusDiv").className = "muiProcessStatus muiDiscardStatus stamp";
                        });
                    });
                </script>
            </c:if>
            <script type="text/javascript">
                require(["mui/form/ajax-form!modelingAppSimpleMainForm"]);
            </script>
        </html:form>

    </template:replace>
</template:include>