<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<template:include ref="default.list" spa="true" rwd="true">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-loan:module.fssc.loan') }-${ lfn:message('fssc-loan:table.fsscLoanMain') }" />
    </template:replace>
    <template:replace name="nav">
        <ui:combin ref="menu.nav.title">
            <ui:varParam name="title" value="${ lfn:message('fssc-loan:table.fsscLoanMain') }" />
            <%-- 数据区 --%>
            <ui:varParam name="info" >
                <ui:source type="Static">
                </ui:source>
            </ui:varParam>
            <ui:varParam name="operation">
                <ui:source type="Static">
                    [
                    {
                    "text": "${ lfn:message('list.create') }",
                    "href": "/listCreate",
                    "router" : true,
                    "icon": "lui_iconfont_navleft_com_my_drafted"
                    },
                    {
                    "text": "${ lfn:message('list.approval') }",
                    "href": "/listApproval",
                    "router" : true,
                    "icon": "lui_iconfont_navleft_com_my_beapproval"
                    },
                    {
                    "text": "${ lfn:message('list.approved') }",
                    "href": "/listApproved",
                    "router" : true,
                    "icon": "lui_iconfont_navleft_com_my_approvaled"
                    },
                    {
                    "text": "${ lfn:message('list.alldoc') }",
                    "href": "/listAll",
                    "router" : true,
                    "icon": "lui_iconfont_navleft_com_all"
                    }
                    ]
                </ui:source>
            </ui:varParam>
        </ui:combin>
        <div id="menu_nav" class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:if test="${param.j_iframe=='true'}">
                    <c:set var="j_iframe" value="?j_iframe=true" />
                </c:if>
                <ui:content title="${ lfn:message('list.otherOpt') }">
                    <ui:combin ref="menu.nav.simple">
                        <ui:varParam name="source">
                            <ui:source type="Static">
                                [{
                                "text" : "${lfn:message('fssc-loan:table.fsscLoanRepayment') }",
                                "href" :  "/listFsscLoanRepayment",
                                "router" : true,
                                "icon" : "lui_iconfont_navleft_com_all"
                                },
                                <fssc:checkVersion version="true">
                                {
                                "text" : "${lfn:message('fssc-loan:table.fsscLoanTransfer') }",
                                "href" :  "/listFsscLoanTransfer",
                                "router" : true,
                                "icon" : "lui_iconfont_navleft_com_all"
                                },
                                </fssc:checkVersion>
                                {
                                "text" : "${lfn:message('fssc-loan:fsscLoanMain.search.person.title') }",
                                "href" :  "javascript:LUI.pageOpen('${LUI_ContextPath }/fssc/loan/portlet/fssc_loan_portlet/index.jsp','_blank');",
                                "icon" : "lui_iconfont_navleft_com_all"
                                },
                                <kmss:authShow roles="ROLE_FSSCLOAN_SEARCHLIST">
                               {
                                "text" : "${lfn:message('fssc-loan:fsscLoanMain.search.title') }",
                                "href" :  "javascript:LUI.pageOpen('${LUI_ContextPath }/fssc/loan/fssc_loan_main/fsscLoanMain_search.jsp','_blank');",
                                "icon" : "lui_iconfont_navleft_com_all"
                                },
                                </kmss:authShow>
                                <kmss:ifModuleExist path="/fssc/cashier">
                                {
                                "text" : "${lfn:message('fssc-loan:py.ChuNaGongZuoTai') }",
                                "href" :  "/listCashier",
                                "router" : true,
                                "icon" : "lui_iconfont_navleft_com_all"
                                },
                                </kmss:ifModuleExist>
                                <%-- 关闭回收站功能时，模块首页不显示“回收站” --%>
			  					<% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.fssc.loan.model.FsscLoanMain")
			  							||com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.fssc.loan.model.FsscLoanRepayment")
			  							||com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.fssc.loan.model.FsscLoanTransfer")) { %>
			  					{
			  						"text" : "${ lfn:message('sys-recycle:module.sys.recycle') }",
			  						"href" :  "/recover",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_recycle"
			  					},
			  					<% } %>
                                <kmss:authShow roles="ROLE_FSSCLOAN_SETTING">
                                {
                                "text" : "${lfn:message('list.manager') }",
                                "href" :  "javascript:LUI.pageOpen('${LUI_ContextPath }/sys/profile/moduleindex.jsp?nav=/fssc/loan/tree.jsp','_rIframe');",
                                "icon" : "lui_iconfont_navleft_com_background"
                                }
                                </kmss:authShow>
                                ]
                            </ui:source>
                        </ui:varParam>
                    </ui:combin>
                </ui:content>
            </ui:accordionpanel>
        </div>
    </template:replace>
    <template:replace name="content">
        <ui:tabpanel id="fsscLoanPanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
            <ui:content id="fsscLoanContent" title="" cfg-route="{path:'/listCreate'}">
                <!-- 筛选 -->
                <list:criteria id="criteria1">
                	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('fssc-loan:fsscLoanMain.docSubject')}" />
                    <list:cri-auto modelName="com.landray.kmss.fssc.loan.model.FsscLoanMain" property="docNumber" expand="true" />
                    <list:cri-auto modelName="com.landray.kmss.fssc.loan.model.FsscLoanMain" property="docStatus" />
                    <list:cri-ref ref="criterion.sys.simpleCategory" key="docTemplate" multi="false" title="${lfn:message('fssc-loan:fsscLoanMain.docTemplate')}" expand="false">
                        <list:varParams modelName="com.landray.kmss.fssc.loan.model.FsscLoanCategory" />
                    </list:cri-ref>
                    <list:cri-criterion title="${lfn:message('fssc-loan:fsscLoanMain.fdCompany')}" key="fdCompanyName">
	                    <list:box-select>
	                        <list:item-select type="lui/criteria/criterion_input!TextInput">
	                            <ui:source type="Static">
	                                [{placeholder:'${lfn:message('fssc-loan:fsscLoanMain.fdCompany')}'}]
	                            </ui:source>
	                        </list:item-select>
	                    </list:box-select>
	                </list:cri-criterion>
	                <list:cri-criterion title="${lfn:message('fssc-loan:fsscLoanMain.fdBasePayWay')}" key="fdBasePayWayName">
                        <list:box-select>
                            <list:item-select type="lui/criteria/criterion_input!TextInput">
                                <ui:source type="Static">
                                    [{placeholder:'${lfn:message('fssc-loan:fsscLoanMain.fdBasePayWay')}'}]
                                </ui:source>
                            </list:item-select>
                        </list:box-select>
                    </list:cri-criterion>
                    <list:cri-auto modelName="com.landray.kmss.fssc.loan.model.FsscLoanMain" property="fdLoanPerson" />
                    <%--当前处理人--%>
			        <list:cri-ref ref="criterion.sys.postperson.availableAll"  cfg-if="param['docStatus']!='00' && param['docStatus']!='32'" key="fdCurrentHandler" multi="false" title="${lfn:message('fssc-loan:lbpm.currentHandler')}" />
			        <%--已处理人--%>
			        <list:cri-ref ref="criterion.sys.person"  key="fdAlreadyHandler" multi="false" title="${lfn:message('fssc-loan:lbpm.approvedHandler')}" />
                </list:criteria>
                <!-- 操作 -->
                <div class="lui_list_operation">

                    <div style='color: #979797;float: left;padding-top:1px;'>
                            ${ lfn:message('list.orderType') }：
                    </div>
                    <div style="float:left">
                        <div style="display: inline-block;vertical-align: middle;">
                            <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                                <list:sort property="fsscLoanMain.docCreateTime" text="${lfn:message('fssc-loan:fsscLoanMain.docCreateTime')}" group="sort.list" value="down" />
                            </ui:toolbar>
                        </div>
                    </div>
                    <div style="float:left;">
                        <list:paging layout="sys.ui.paging.top" />
                    </div>
                    <div style="float:right">
                        <div style="display: inline-block;vertical-align: middle;">
                            <ui:toolbar count="3">

                                <kmss:auth requestURL="/fssc/loan/fssc_loan_main/fsscLoanMain.do?method=add">
                                    <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                                </kmss:auth>
                                <kmss:auth requestURL="/fssc/loan/fssc_loan_main/fsscLoanMain.do?method=deleteall">
                                    <c:set var="canDelete" value="true" />
                                </kmss:auth>
                                <!---->
                                <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAllByAuth()" order="4" id="btnDelete" />
                                <ui:button text="${lfn:message('button.export')}" id="export" order="5" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.loan.model.FsscLoanMain')" />
                                        	<%-- 修改权限 --%>
							<c:import url="/sys/right/import/doc_right_change_button.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.fssc.loan.model.FsscLoanMain" />
                                <c:param name="authReaderNoteFlag" value="2" />
							</c:import>		
                            </ui:toolbar>
                        </div>
                    </div>
                </div>
                <ui:fixed elem=".lui_list_operation" />
                <!-- 列表 -->
                <list:listview id="listview">
                    <ui:source type="AjaxJson">
                        {url:appendQueryParameter('/fssc/loan/fssc_loan_main/fsscLoanMain.do?method=data&q.j_path=loan')}
                    </ui:source>
                    <!-- 列表视图 -->
                    <list:colTable isDefault="false" rowHref="/fssc/loan/fssc_loan_main/fsscLoanMain.do?method=view&fdId=!{fdId}" name="columntable">
                        <list:col-checkbox />
                        <list:col-serial/>
                        <list:col-auto />
                    </list:colTable>
                </list:listview>
                <!-- 翻页 -->
                <list:paging />
            </ui:content>
        </ui:tabpanel>
    </template:replace>
    <template:replace name="script">
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'loan',
                modelName: 'com.landray.kmss.fssc.loan.model.FsscLoanMain',
                templateName: 'com.landray.kmss.fssc.loan.model.FsscLoanCategory',
                basePath: '/fssc/loan/fssc_loan_main/fsscLoanMain.do',
                canDelete: '${canDelete}',
                mode: 'main_scategory',
                templateService: 'fsscLoanCategoryService',
                templateAlert: '${lfn:message("fssc-loan:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/loan/resource/js/", 'js', true);


            seajs.use(['lui/framework/module','lui/dialog','lui/topic'],function(Module,dialog,topic){
                Module.install('fsscLoan',{
                    //模块变量
                    $var : {},
                    //模块多语言
                    $lang : {
                        myCreate : "${ lfn:message('list.create') }",
                        myApproval : "${ lfn:message('list.approval') }",
                        myApproved : "${ lfn:message('list.approved') }",
                        allFlow : "${ lfn:message('list.alldoc') }",
                    },
                    //搜索标识符
                    $search : ''
                });
            });
        </script>
        <script type="text/javascript" src="${LUI_ContextPath}/fssc/loan/resource/js/index.js"></script>
    </template:replace>
</template:include>
