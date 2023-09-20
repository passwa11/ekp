<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple4list">
    <template:replace name="title">
        <c:out value="${ lfn:message('eop-basedata:table.eopBasedataAuthorize') }" />
    </template:replace>
    <template:replace name="content">
        <ui:tabpanel id="eopBasedataAuthorizePanel" layout="sys.ui.tabpanel.list" cfg-router="true">
        <ui:content id="eopBasedataAuthorizeContent" title="${ lfn:message('eop-basedata:table.eopBasedataAuthorize') }">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
            	<list:cri-criterion title="${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdIsAvailable')}" key="fdIsAvailable">
					<list:box-select>
						<list:item-select  cfg-defaultValue="true">
							<ui:source type="Static">
							    [{text:'${ lfn:message('message.yes')}', value:'true'},
								{text:'${ lfn:message('message.no')}',value:'false'}]
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataAuthorize" property="fdAuthorizedBy" expand="true" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="9" id="btn">

                            <kmss:auth requestURL="/eop/basedata/eop_basedata_authorize/eopBasedataAuthorize.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                             <kmss:auth requestURL="/eop/basedata/eop_basedata_authorize/eopBasedataAuthorize.do?method=enable">
                                <ui:button text="${lfn:message('eop-basedata:button.enable')}" onclick="enable()" order="3" />
                            </kmss:auth>
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_authorize/eopBasedataAuthorize.do?method=disable">
                                <ui:button text="${lfn:message('eop-basedata:button.disable')}" onclick="disable()" order="3" />
                            </kmss:auth>
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_authorize/eopBasedataAuthorize.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />

                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/eop/basedata/eop_basedata_authorize/eopBasedataAuthorize.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/eop/basedata/eop_basedata_authorize/eopBasedataAuthorize.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdAuthorizedBy.name;fdToOrg.name;fdIsAvailable.name;docCreator.name;docCreateTime;operations" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        </ui:content>
        </ui:tabpanel>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataAuthorize',
                templateName: '',
                basePath: '/eop/basedata/eop_basedata_authorize/eopBasedataAuthorize.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("eop-basedata:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
        </script>
        <!-- 维护所有授权信息时，才有权限导入导出 -->
        <kmss:authShow roles="ROLE_EOPBASEDATA_AUTHORIZE;ROLE_EOPBASEDATA_IMPORT">
        <c:import url="/eop/basedata/eop_basedata_authorize/eopBasedataImport_include.jsp">
        </c:import>
        </kmss:authShow>
    </template:replace>
</template:include>
