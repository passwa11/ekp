<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                 <list:cri-criterion title="${lfn:message('eop-basedata:eopBasedataItemAccount.fdCompanyList')}" key="fdCompanyName"  expand="true"  multi="false">
					<list:box-select>
		                <list:item-select type="lui/criteria/criterion_input!TextInput">
		                    <ui:source type="Static">
		                        [{placeholder:'${lfn:message('eop-basedata:eopBasedataItemAccount.fdCompanyList')}'}]
		                    </ui:source>
		                </list:item-select>
		            </list:box-select>
				</list:cri-criterion>
                <list:cri-criterion title="${lfn:message('eop-basedata:eopBasedataItemAccount.fdIsAvailable')}" key="fdIsAvailable">
					<list:box-select>
						<list:item-select  cfg-defaultValue="true">
							<ui:source type="Static">
							    [{text:'${ lfn:message('message.yes')}', value:'true'},
								{text:'${ lfn:message('message.no')}',value:'false'}]
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
                <list:cri-criterion title="${lfn:message('eop-basedata:eopBasedataItemAccount.fdExpenseItem')}" key="fdExpenseItem.fdName">
                    <list:box-select>
                        <list:item-select type="lui/criteria/criterion_input!TextInput">
                            <ui:source type="Static">
                                [{placeholder:'${lfn:message('eop-basedata:eopBasedataItemAccount.fdExpenseItem')}'}]
                            </ui:source>
                        </list:item-select>
                    </list:box-select>
                </list:cri-criterion>
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="eopBasedataItemAccount.docAlterTime" text="${lfn:message('eop-basedata:eopBasedataItemAccount.docAlterTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="9" id="btn">
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_item_account/eopBasedataItemAccount.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="1" />
                            </kmss:auth>
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_item_account/eopBasedataItemAccount.do?method=add">
								<ui:button text="${lfn:message('eop-basedata:button.enable')}" onclick="enable()" order="2" />
							</kmss:auth>
							<kmss:auth requestURL="/eop/basedata/eop_basedata_item_account/eopBasedataItemAccount.do?method=add">
								<ui:button text="${lfn:message('eop-basedata:button.disable')}" onclick="disable()" order="3" />
							</kmss:auth>
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_item_account/eopBasedataItemAccount.do?method=deleteall">
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
                    {url:appendQueryParameter('/eop/basedata/eop_basedata_item_account/eopBasedataItemAccount.do?method=data&fdCompanyId=${param.fdCompanyId }')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/eop/basedata/eop_basedata_item_account/eopBasedataItemAccount.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdCompanyName;fdExpenseItem.name;fdAmortize.name;fdAccruals.name;fdIsAvailable.name;operations" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataItemAccount',
                templateName: '',
                basePath: '/eop/basedata/eop_basedata_item_account/eopBasedataItemAccount.do',
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
