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
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('eop-basedata:eopBasedataCostCenter.fdName')}" />
                <list:cri-criterion title="${lfn:message('eop-basedata:eopBasedataCostCenter.fdCompanyList')}" key="fdCompanyName" expand="true">
		            <list:box-select>
		                <list:item-select type="lui/criteria/criterion_input!TextInput">
		                    <ui:source type="Static">
		                        [{placeholder:'${lfn:message('eop-basedata:eopBasedataCostCenter.fdCompanyList')}'}]
		                    </ui:source>
		                </list:item-select>
		            </list:box-select>
		        </list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataCostCenter" property="fdCode" expand="true" />
				<!-- 成本中心 fdCostCenter -->
				<list:cri-criterion title="${lfn:message('eop-basedata:eopBasedataCostCenter.fdType')}"  key="fdType" expand="true" multi="false">
					<list:box-select>
						<list:item-select id="cost-type-id">
							<ui:source type="AjaxXml">
								  {"url":"/sys/common/dataxml.jsp?s_bean=eopBasedataCostTypeService&modelName=com.landray.kmss.eop.basedata.model.EopBasedataCostType&fdCompanyId=${param.fdCompanyId }&authCurrent=true"}
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
                <list:cri-criterion title="${lfn:message('eop-basedata:eopBasedataCostCenter.fdIsAvailable')}" key="fdIsAvailable">
					<list:box-select>
						<list:item-select  cfg-defaultValue="true">
							<ui:source type="Static">
							    [{text:'${ lfn:message('message.yes')}', value:'true'},
								{text:'${ lfn:message('message.no')}',value:'false'}]
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataCostCenter" property="fdIsGroup" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataCostCenter" property="docCreator" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataCostCenter" property="docCreateTime" />
                <!-- 成本中心 fdCostCenter -->
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fdCode" text="${lfn:message('eop-basedata:eopBasedataCostCenter.fdCode')}" group="sort.list" />
                            <list:sort property="fdOrder" text="${lfn:message('eop-basedata:eopBasedataCostCenter.fdOrder')}" group="sort.list" />
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
                        	<kmss:auth requestURL="/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenter.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
							<%-- <kmss:auth requestURL="/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenter.do?method=copy">
								<ui:button text="${lfn:message('eop-basedata:button.copy')}" onclick="copy()" order="3" />
							</kmss:auth> --%>
							<kmss:auth requestURL="/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenter.do?method=enable">
								<ui:button text="${lfn:message('eop-basedata:button.enable')}" onclick="enable()" order="3" />
							</kmss:auth>
							<kmss:auth requestURL="/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenter.do?method=disable">
								<ui:button text="${lfn:message('eop-basedata:button.disable')}" onclick="disable()" order="3" />
							</kmss:auth>
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenter.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="6" id="btnDelete" />

                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenter.do?method=data&fdCompanyId=${param.fdCompanyId }')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenter.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdCompanyName;fdName;fdCode;fdIsGroup.name;fdCompany.name;fdType.name;fdIsAvailable.name;fdOrder,docCreator.name;docCreateTime;operations" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',
                templateName: '',
                basePath: '/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenter.do',
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
