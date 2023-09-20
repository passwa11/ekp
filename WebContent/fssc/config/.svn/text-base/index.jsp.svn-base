<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<template:include ref="default.list" spa="true" rwd="true">
    <template:replace name="title">
        <c:out value="报表管理" />
    </template:replace>
    <template:replace name="nav">
        <div class="lui_list_noCreate_frame">
            <ui:combin ref="menu.nav.create">
                <ui:varParam name="title" value="流程配置" />
                <ui:varParam name="button">
                    [ { "text": "", "href": "javascript:void(0);", "icon": "km_comminfo" } ]
                </ui:varParam>
            </ui:combin>
        </DIV>
        <div class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:if test="${param.j_iframe=='true'}">
                    <c:set var="j_iframe" value="?j_iframe=true" />
                </c:if>
                <ui:content title="流程配置" expand="true" style="padding-bottom:0;">
                    <ui:combin ref="menu.nav.simple">
                        <ui:varParam name="source">
                            <ui:source type="Static">
                                [
                                 { 
                                	"text" : "积分配置",
                                	"href" : "/fssc_config_score",
                                	"router" : true
                                  },{ 
                                	"text" : "物资清单",
                                	"href" : "/fssc_config_list",
                                	"router" : true
                                  }
                               ]
                            </ui:source>
                        </ui:varParam>
                    </ui:combin>
                </ui:content>
                <ui:content title="报表" expand="true" style="padding-bottom:0;">
                    <ui:combin ref="menu.nav.simple">
                        <ui:varParam name="source">
                            <ui:source type="Static">
                                [
                                 { 
                                	"text" : "积分报表",
                                	"href" : "/fssc_config_score_report",
                                	"router" : true
                                  }
                               ]
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
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/config/resource/js/", 'js', true);
        </script>
    </template:replace>
    <template:replace name="content">
        <ui:tabpanel id="fsscProjectPanel" layout="sys.ui.tabpanel.list" cfg-router="true">
            <ui:content id="ZxallProjectFrameContent" title="${lfn:message('fssc-project:table.fsscProjectFrame.frame') }" cfg-route="{path:'/frame'}">
                <ui:iframe id="frame" src="${LUI_ContextPath }/fssc/project/fssc_project_frame/index.jsp?j_iframe=true&j_aside=false">
                </ui:iframe>
            </ui:content>
        </ui:tabpanel>
    </template:replace>
    <% pageContext.setAttribute( "userId", UserUtil.getKMSSUser().getUserId()); pageContext.setAttribute( "depId", UserUtil.getKMSSUser().getDeptId()); if(UserUtil.getUser().getFdParentOrg()!=null){ pageContext.setAttribute( "orgId", UserUtil.getUser().getFdParentOrg().getFdId());
    }else{ pageContext.setAttribute( "orgId", null); } pageContext.setAttribute( "authAreaId", UserUtil.getKMSSUser().getAuthAreaId()); %>
        <template:replace name="script">
            <!-- JSP中建议只出现·安装模块·的JS代码，其余JS代码采用引入方式 -->
            <script type="text/javascript">
                seajs.use(['lui/framework/module'], function(Module) {
                    Module.install('fsscConfig', {
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
            <script type="text/javascript" src="${LUI_ContextPath}/fssc/config/resource/js/index.js"></script>
        </template:replace>
</template:include>