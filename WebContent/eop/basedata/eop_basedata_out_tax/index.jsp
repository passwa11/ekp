<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdRate" ref="criterion.sys.docSubject" title="${lfn:message('eop-basedata:eopBasedataOutTax.fdRate')}" />
                <fssc:switchOn property="fdShowType">
                    <list:cri-criterion title="${lfn:message('eop-basedata:eopBasedataAccounts.fdCompany')}" key="fdCompany.fdId"  expand="true"  multi="false">
                        <list:box-select>
                            <list:item-select>
                                <ui:source type="AjaxXml" >
                                    {"url":"/sys/common/dataxml.jsp?s_bean=eopBasedataCompanyTreeService&valid=true"}
                                </ui:source>
                            </list:item-select>
                        </list:box-select>
                    </list:cri-criterion>
                </fssc:switchOn>
                <list:cri-criterion title="${lfn:message('eop-basedata:eopBasedataOutTax.fdAccount')}" key="fdAccount.fdName">
                    <list:box-select>
                        <list:item-select type="lui/criteria/criterion_input!TextInput">
                            <ui:source type="Static">
                                [{placeholder:'${lfn:message('eop-basedata:eopBasedataOutTax.fdAccount')}'}]
                            </ui:source>
                        </list:item-select>
                    </list:box-select>
                </list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataOutTax" property="docCreator" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataOutTax" property="docCreateTime" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                        ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fdOrder" text="${lfn:message('eop-basedata:eopBasedataOutTax.fdOrder')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="9" id="btn">
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_out_tax/eopBasedataOutTax.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="1" />
                            </kmss:auth>
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_out_tax/eopBasedataOutTax.do?method=enable">
                                <ui:button text="${lfn:message('eop-basedata:button.enable')}" onclick="enable()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_out_tax/eopBasedataOutTax.do?method=disable">
                                <ui:button text="${lfn:message('eop-basedata:button.disable')}" onclick="disable()" order="3" />
                            </kmss:auth>
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_out_tax/eopBasedataOutTax.do?method=deleteall">
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
                    {url:appendQueryParameter('/eop/basedata/eop_basedata_out_tax/eopBasedataOutTax.do?method=data&fdCompanyId=${param.fdCompanyId }')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/eop/basedata/eop_basedata_out_tax/eopBasedataOutTax.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdCompanyName;fdRate;fdAccount.name;fdIsAvailable.name;docCreator.name;docCreateTime;operations" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataOutTax',
                templateName: '',
                basePath: '/eop/basedata/eop_basedata_out_tax/eopBasedataOutTax.do',
                canDelete: '${canDelete}',
                mode: '',
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
        <c:import url="/eop/basedata/resource/jsp/eopBasedataImport_include.jsp">
        </c:import>
    </template:replace>
</template:include>
