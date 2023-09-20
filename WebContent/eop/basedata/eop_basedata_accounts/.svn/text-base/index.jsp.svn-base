<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
<template:include ref="config.list">
	<template:replace name="path">
		<span class="txtlistpath">
			<div class="lui_icon_s lui_icon_s_home" style="float: left;"></div>
			<div style="float: left;margin:5px 10px;">
				<bean:message key="page.curPath" /><%=java.net.URLDecoder.decode(request.getParameter("s_path"), "utf-8")%>
			</div></span>
    </template:replace>
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('eop-basedata:eopBasedataAccounts.fdName')}" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataAccounts" property="fdCode" expand="true" />
                <list:cri-criterion title="${lfn:message('eop-basedata:eopBasedataAccounts.fdCompanyList')}" key="fdCompanyName"  expand="true"  multi="false">
					<list:box-select>
		                <list:item-select type="lui/criteria/criterion_input!TextInput">
		                    <ui:source type="Static">
		                        [{placeholder:'${lfn:message('eop-basedata:eopBasedataAccounts.fdCompanyList')}'}]
		                    </ui:source>
		                </list:item-select>
		            </list:box-select>
				</list:cri-criterion>
                <list:cri-criterion title="${lfn:message('eop-basedata:eopBasedataAccounts.fdIsAvailable')}" key="fdIsAvailable">
					<list:box-select>
						<list:item-select  cfg-defaultValue="true">
							<ui:source type="Static">
							    [{text:'${ lfn:message('message.yes')}', value:'true'},
								{text:'${ lfn:message('message.no')}',value:'false'}]
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataAccounts" property="fdCostItem" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataAccounts" property="docCreator" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataAccounts" property="docCreateTime" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fdCode" text="${lfn:message('eop-basedata:eopBasedataAccounts.fdCode')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="9" id="btn">
                        	<ui:button text="${lfn:message('eop-basedata:button.showTree')}" onclick="switchTree()" order="1" />
                        	<%-- 是否开启下发  选择否 --%>
    						<fssc:switchOff property="fdIsOpenIssued">
                        	<kmss:auth requestURL="/eop/basedata/eop_basedata_accounts/eopBasedataAccounts.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            </fssc:switchOff>
							<kmss:auth requestURL="/eop/basedata/eop_basedata_accounts/eopBasedataAccounts.do?method=enable">
								<ui:button text="${lfn:message('eop-basedata:button.enable')}" onclick="enable()" order="3" />
							</kmss:auth>
							<kmss:auth requestURL="/eop/basedata/eop_basedata_accounts/eopBasedataAccounts.do?method=disable">
								<ui:button text="${lfn:message('eop-basedata:button.disable')}" onclick="disable()" order="4" />
							</kmss:auth>
							<%-- 是否开启下发  选择否 --%>
   							 <fssc:switchOff property="fdIsOpenIssued">
							 <kmss:auth requestURL="/eop/basedata/eop_basedata_accounts/eopBasedataAccounts.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="6" id="btnDelete" />
                            </fssc:switchOff>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/eop/basedata/eop_basedata_accounts/eopBasedataAccounts.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/eop/basedata/eop_basedata_accounts/eopBasedataAccounts.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdCompanyName;fdName;fdCode;fdCompany.name;fdParent.name;fdCostItem.name;fdIsAvailable.name;docCreator.name;docCreateTime;operations" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataAccounts',
                templateName: '',
                basePath: '/eop/basedata/eop_basedata_accounts/eopBasedataAccounts.do',
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
        <%-- 是否开启下发  选择是 --%>
        <fssc:switchOn property="fdIsOpenIssued">
        	<script>var fdIsOpenIssued=true;</script>
        </fssc:switchOn>
    </template:replace>
</template:include>
