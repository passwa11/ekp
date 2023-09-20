<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdName')}" />
                <kmss:ifModuleExist path="/fssc/common">
                <list:cri-criterion title="${lfn:message('eop-basedata:eopBasedataSupplier.fdCompanyList')}" key="fdCompanyName" expand="true">
		            <list:box-select>
		                <list:item-select type="lui/criteria/criterion_input!TextInput">
		                    <ui:source type="Static">
		                        [{placeholder:'${lfn:message('eop-basedata:eopBasedataSupplier.fdCompanyList')}'}]
		                    </ui:source>
		                </list:item-select>
		            </list:box-select>
		        </list:cri-criterion>
		        </kmss:ifModuleExist>
		        <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataSupplier" property="fdCode" expand="true" />
                <list:cri-criterion title="${lfn:message('eop-basedata:eopBasedataSupplier.fdIsAvailable')}" key="fdIsAvailable">
					<list:box-select>
						<list:item-select  cfg-defaultValue="true">
							<ui:source type="Static">
							    [{text:'${ lfn:message('message.yes')}', value:'true'},
								{text:'${ lfn:message('message.no')}',value:'false'}]
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataSupplier" property="docCreateTime" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="7" id="btn">

                            <kmss:auth requestURL="/eop/basedata/eop_basedata_supplier/eopBasedataSupplier.do?method=add">
                                <!--add-->
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc('')" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_supplier/eopBasedataSupplier.do?method=add">
								<ui:button text="${lfn:message('eop-basedata:button.enable')}" onclick="enable()" order="2" />
							</kmss:auth>
							<kmss:auth requestURL="/eop/basedata/eop_basedata_supplier/eopBasedataSupplier.do?method=add">
								<ui:button text="${lfn:message('eop-basedata:button.disable')}" onclick="disable()" order="3" />
							</kmss:auth>
							<kmss:auth requestURL="/eop/basedata/eop_basedata_supplier/eopBasedataSupplier.do?method=add">
								<ui:button parentId="btn" text="${lfn:message('eop-basedata:button.exportTemplate') }" onclick="downloadTemp()"></ui:button>
								<ui:button parentId="btn" text="${lfn:message('button.import') }" onclick="importSupplier()"></ui:button>
								<ui:button parentId="btn" text="${lfn:message('button.export') }" onclick="exportSupplier()"></ui:button>
							</kmss:auth>
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_customer/eopBasedataCustomer.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="5" id="btnDelete" />

                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/eop/basedata/eop_basedata_supplier/eopBasedataSupplier.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/eop/basedata/eop_basedata_supplier/eopBasedataSupplier.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdCompanyName;fdCode;fdName;fdAbbreviation;contactName;contactPhone;fdIsAvailable.name;docCreateTime;operations" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataSupplier',
                templateName: '',
                basePath: '/eop/basedata/eop_basedata_supplier/eopBasedataSupplier.do',
                codePath: '/eop/basedata/eop_basedata_sup_code/eopBasedataSupCode.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("eop-basedata:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}',
                    codeIsNotExist: '${lfn:message("eop-basedata:code.is.not.exist")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
            Com_IncludeFile("index.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_supplier/", 'js', true);
        </script>
    </template:replace>
</template:include>
