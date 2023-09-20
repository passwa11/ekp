<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('eop-basedata:eopBasedataCountry.fdName')}" />
                <list:cri-criterion title="${lfn:message('eop-basedata:eopBasedataCountry.fdCompanyList')}" key="fdCompanyName"  expand="true"  multi="false">
					<list:box-select>
		                <list:item-select type="lui/criteria/criterion_input!TextInput">
		                    <ui:source type="Static">
		                        [{placeholder:'${lfn:message('eop-basedata:eopBasedataCountry.fdCompanyList')}'}]
		                    </ui:source>
		                </list:item-select>
		            </list:box-select>
				</list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataCountry" property="fdCode" expand="true" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataCountry" property="fdIsAvailable" expand="true" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataCountry" property="docCreator" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataCountry" property="docCreateTime" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="eopBasedataCountry.docCreateTime" text="${lfn:message('eop-basedata:eopBasedataCountry.docCreateTime')}" group="sort.list" />
                            <list:sort property="eopBasedataCountry.fdCode" text="${lfn:message('eop-basedata:eopBasedataCountry.fdCode')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="9" id="btn">

                            <kmss:auth requestURL="/eop/basedata/eop_basedata_country/eopBasedataCountry.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_country/eopBasedataCountry.do?method=add">
								<ui:button text="${lfn:message('eop-basedata:button.enable')}" onclick="enable()" order="2" />
							</kmss:auth>
							<kmss:auth requestURL="/eop/basedata/eop_basedata_country/eopBasedataCountry.do?method=add">
								<ui:button text="${lfn:message('eop-basedata:button.disable')}" onclick="disable()" order="3" />
							</kmss:auth>
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_country/eopBasedataCountry.do?method=deleteall">
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
                    {url:appendQueryParameter('/eop/basedata/eop_basedata_country/eopBasedataCountry.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/eop/basedata/eop_basedata_country/eopBasedataCountry.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdCompanyName;fdName;fdCode;fdIsAvailable.name;docCreator.name;docCreateTime;operations" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCountry',
                templateName: '',
                basePath: '/eop/basedata/eop_basedata_country/eopBasedataCountry.do',
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
        <c:import url="/eop/basedata/resource/jsp/eopBasedataImport_include.jsp">
        </c:import>
    </template:replace>
</template:include>