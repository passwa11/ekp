<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include file="/sys/profile/resource/template/list.jsp">
   
    <template:replace name="content">
        <div style="margin:5px 10px;">  
           <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>          
            <!-- 操作 -->
            <div class="lui_list_operation">  
                             
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/third/ding/thirdDingTemplateList.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!--deleteall-->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/third/ding/thirdDingTemplateList.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false"  name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="processCode;name;description" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'send_ding',
                modelName: 'com.landray.kmss.third.ding.model.ThirdDingTemplateList',
                templateName: '',
                basePath: '/third/ding/thirdDingTemplateList.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("third-ding:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>