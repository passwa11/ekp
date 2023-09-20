<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<template:include ref="config.list">
	<template:replace name="content">
		<div style="margin: 5px 10px;">
			<!-- 筛选 -->
			<list:criteria id="criteria1">
				<list:cri-ref key="fdName" ref="criterion.sys.docSubject"
					title="${lfn:message('eop-basedata:eopBasedataCompany.fdName')}" />
				<list:cri-auto
					modelName="com.landray.kmss.eop.basedata.model.EopBasedataCompany"
					property="fdIden" expand="true" />
				<list:cri-criterion
					title="${lfn:message('eop-basedata:eopBasedataCompany.fdIsAvailable')}"
					key="fdIsAvailable">
					<list:box-select>
						<list:item-select cfg-defaultValue="true">
							<ui:source type="Static">
							    [{text:'${ lfn:message('message.yes')}', value:'true'},
								{text:'${ lfn:message('message.no')}',value:'false'}]
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
				<fssc:switchOn property="fdCompanyGroup">
					<list:cri-criterion
						title="${lfn:message('eop-basedata:eopBasedataCompany.fdGroup')}"
						key="fdGroup.fdId" multi="false">
						<list:box-select>
							<list:item-select>
								<ui:source type="AjaxXml">
								  {"url":"/sys/common/dataxml.jsp?s_bean=eopBasedataCompanyGroupTreeService&valid=true"}
							</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
				</fssc:switchOn>
			</list:criteria>
			<!-- 操作 -->
			<div class="lui_list_operation">

				<div style='color: #979797; float: left; padding-top: 1px;'>
					${ lfn:message('list.orderType') }：</div>
				<div style="float: left">
					<div style="display: inline-block; vertical-align: middle;">
						<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
							<list:sort property="docCreateTime"
								text="${lfn:message('eop-basedata:eopBasedataCompany.docCreateTime')}"
								group="sort.list" value="up" />
						</ui:toolbar>
					</div>
				</div>
				<div style="float: left;">
					<list:paging layout="sys.ui.paging.top" />
				</div>
				<div style="float: right">
					<div style="display: inline-block; vertical-align: middle;">
						<ui:toolbar count="8" id="btn">
							<kmss:auth
								requestURL="/eop/basedata/eop_basedata_company/eopBasedataCompany.do?method=add">
								<ui:button text="${lfn:message('button.add')}"
									onclick="addDoc()" order="1" />
							</kmss:auth>
							<kmss:auth requestURL="/eop/basedata/eop_basedata_company/eopBasedataCompany.do?method=add">
                                <ui:button id="pullBtn" text="${lfn:message('eop-basedata:pull.contbody.data')}" onclick="syncData()" order="5" ></ui:button>
                            </kmss:auth>
							<kmss:auth
								requestURL="/eop/basedata/eop_basedata_company/eopBasedataCompany.do?method=enable">
								<ui:button text="${lfn:message('eop-basedata:button.enable')}"
									onclick="enable()" order="2" />
							</kmss:auth>
							<kmss:auth
								requestURL="/eop/basedata/eop_basedata_company/eopBasedataCompany.do?method=disable">
								<ui:button text="${lfn:message('eop-basedata:button.disable')}"
									onclick="disable()" order="3" />
							</kmss:auth>
							<kmss:auth
								requestURL="/eop/basedata/eop_basedata_company/eopBasedataCompany.do?method=deleteall">
								<c:set var="canDelete" value="true" />
							</kmss:auth>
							<!---->
							<ui:button text="${lfn:message('button.deleteall')}"
								onclick="deleteAll()" order="4" id="btnDelete" />

						</ui:toolbar>
					</div>
				</div>
			</div>
			<ui:fixed elem=".lui_list_operation" />
			<!-- 列表 -->
			<list:listview id="listview">
				<ui:source type="AjaxJson">
                    {url:appendQueryParameter('/eop/basedata/eop_basedata_company/eopBasedataCompany.do?method=data')}
                </ui:source>
				<!-- 列表视图 -->
				<list:colTable isDefault="false"
					rowHref="/eop/basedata/eop_basedata_company/eopBasedataCompany.do?method=view&fdId=!{fdId}"
					name="columntable">
					<list:col-checkbox />
					<list:col-serial />
					<list:col-auto
						props="fdName;fdIden;fdAccountCurrency.name;fdIsAvailable.name;docCreator.name;docCreateTime;fdGroup.name;operations" />
				</list:colTable>
			</list:listview>
			<!-- 翻页 -->
			<list:paging />
		</div>
		<script>
			var listOption = {
				contextPath : '${LUI_ContextPath}',
				modelName : 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
				templateName : '',
				basePath : '/eop/basedata/eop_basedata_company/eopBasedataCompany.do',
				canDelete : '${canDelete}',
				mode : '',
				customOpts : {

					____fork__ : 0
				},
				lang : {
					noSelect : '${lfn:message("page.noSelect")}',
					comfirmDelete : '${lfn:message("page.comfirmDelete")}'
				}

			};
			Com_IncludeFile("list.js",
					"${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
		</script>
		<c:import
			url="/eop/basedata/resource/jsp/eopBasedataImport_include.jsp">
		</c:import>
	</template:replace>
</template:include>
