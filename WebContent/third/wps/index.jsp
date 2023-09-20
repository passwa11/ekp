<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<template:include ref="default.list" spa="true" rwd="true">
    <template:replace name="title">
        <c:out value="${ lfn:message('third-wps:module.third.wps') }" />
    </template:replace>
    <template:replace name="nav">
        <div class="lui_list_noCreate_frame">
            <ui:combin ref="menu.nav.create">
                <ui:varParam name="title" value="${ lfn:message('third-wps:module.third.wps') }" />
                <ui:varParam name="button">
                    [ { "text": "", "href": "javascript:void(0);", "icon": "third_im_rtx" } ]
                </ui:varParam>
            </ui:combin>
        </DIV>
        <div class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:if test="${param.j_iframe=='true'}">
                    <c:set var="j_iframe" value="?j_iframe=true" />
                </c:if>

                <ui:content title="${ lfn:message('third-wps:py.MoKuaiDaoHang') }">
                    <ui:combin ref="menu.nav.simple">

                        <ui:varParam name="source">

                            <ui:source type="Static">
                                [ { "text" : "${ lfn:message('third-wps:py.WpsJiChengPeiZhi') }", "href" : "javascript:LUI.pageOpen('${LUI_ContextPath }/sys/profile/index.jsp#app/ekp/third/wps','_blank');" }, { "text" : "${ lfn:message('third-wps:py.ZuZhiJiaGouTong') }", "href" : "javascript:LUI.pageOpen('${LUI_ContextPath
                                }/sys/profile/index.jsp#app/ekp/third/wps','_blank');" }, ]
                            </ui:source>
                        </ui:varParam>
                    </ui:combin>

                </ui:content>
            </ui:accordionpanel>
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                customOpts: {
                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }
            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/third/wps/resource/js/", 'js', true);
        </script>
    </template:replace>
    <template:replace name="content">
        <ui:tabpanel id="thirdWpsPanel" layout="sys.ui.tabpanel.list" cfg-router="true">
        </ui:tabpanel>
    </template:replace>
    <% pageContext.setAttribute( "userId", UserUtil.getKMSSUser().getUserId()); pageContext.setAttribute( "depId", UserUtil.getKMSSUser().getDeptId()); if(UserUtil.getUser().getFdParentOrg()!=null){ pageContext.setAttribute( "orgId", UserUtil.getUser().getFdParentOrg().getFdId());
    }else{ pageContext.setAttribute( "orgId", null); } pageContext.setAttribute( "authAreaId", UserUtil.getKMSSUser().getAuthAreaId()); %>
        <template:replace name="script">
            <!-- JSP中建议只出现·安装模块·的JS代码，其余JS代码采用引入方式 -->
            <script type="text/javascript">
                seajs.use(['lui/framework/module'], function(Module) {
                    Module.install('thirdWps', {
                        //模块变量
                        $var: {
                            userId: '${userId}',
                            depId: '${depId}',
                            orgId: '${orgId}',
                            authAreaId: '${authAreaId}'
                        },
                        //模块多语言
                        $lang: {

                        },
                        //搜索标识符
                        $search: ''
                    });
                });
            </script>
            <!-- 引入JS -->
            <script type="text/javascript" src="${LUI_ContextPath}/third/wps/resource/js/index.js"></script>
        </template:replace>
</template:include>