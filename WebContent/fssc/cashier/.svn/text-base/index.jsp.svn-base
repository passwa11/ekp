<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.list" spa="true" rwd="true">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-cashier:module.fssc.cashier') }-${ lfn:message('fssc-cashier:table.fsscCashierPayment') }" />
    </template:replace>
    <template:replace name="nav">
    	<ui:combin ref="menu.nav.title">
            <ui:varParam name="title" value="${ lfn:message('fssc-cashier:module.fssc.cashier') }"></ui:varParam>
            <ui:varParam name="operation">
				<ui:source type="Static">
					
				</ui:source>
			</ui:varParam>
        </ui:combin>
        <div id="menu_nav" class="lui_list_nav_frame">
			<ui:accordionpanel>
				<ui:combin ref="menu.nav.search">
					<ui:varParams modelName="com.landray.kmss.fssc.voucher.model.FsscVoucherMain"/>
				</ui:combin>
				<ui:content title="${ lfn:message('list.search') }" expand="true">
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[{
		  						"text" : "${lfn:message('fssc-cashier:table.fsscCashierPayment')}",
		  						"href" :  "/listMain",
								"router" : true,		  					
				  				"icon" : "lui_iconfont_navleft_com_all"
		  					},{
		  						"text" : "${lfn:message('fssc-cashier:table.fsscCashierPaymentDetail')}",
		  						"href" :  "/listDetail",
								"router" : true,		  					
				  				"icon" : "lui_iconfont_navleft_com_all"
		  					}]
		  					</ui:source>
		  				</ui:varParam>
		  			</ui:combin>
				</ui:content>
				<kmss:authShow roles="ROLE_FSSCCASHIER_SETTING">
				<ui:content title="${ lfn:message('list.otherOpt') }" expand="true">
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[{
		  						"text" : "${ lfn:message('list.manager') }",
		  						"href" :  "javascript:LUI.pageOpen('${LUI_ContextPath }/sys/profile/moduleindex.jsp?nav=/fssc/cashier/tree.jsp','_rIframe');",
			  					"icon" : "lui_iconfont_navleft_com_background"
		  					}]
		  					</ui:source>
		  				</ui:varParam>
		  			</ui:combin>
				</ui:content>
				</kmss:authShow>
			</ui:accordionpanel>
		</div>
    </template:replace>
    <template:replace name="content">
        <ui:tabpanel id="fsscCashierPanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
			<ui:content id="fsscCashierContent" title="" cfg-route="{path:'/listMain'}">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-auto modelName="com.landray.kmss.fssc.cashier.model.FsscCashierPayment" property="fdModelNumber" expand="true"/>
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">
                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fsscCashierPayment.docAlterTime" text="${lfn:message('fssc-cashier:fsscCashierPayment.docAlterTime')}" group="sort.list" value="down" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="5">
                            <ui:button text="${lfn:message('fssc-cashier:button.confirm.pass')}" onclick="confirmPass()" order="2" />
                            <kmss:auth requestURL="/fssc/cashier/fssc_cashier_payment/fsscCashierPayment.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!--批量删除-->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAllByAuth()" order="5" id="btnDelete" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/cashier/fssc_cashier_payment/fsscCashierPayment.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/cashier/fssc_cashier_payment/fsscCashierPayment.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdModelNumber;fdCompany.name;fdPaymentMoney;fdDesc" /></list:colTable>
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
                modelName: 'com.landray.kmss.fssc.cashier.model.FsscCashierPayment',
                templateName: '',
                basePath: '/fssc/cashier/fssc_cashier_payment/fsscCashierPayment.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("fssc-cashier:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            seajs.use(['lui/framework/module'],function(Module){
				Module.install('fsscCashier',{
					//模块变量
					$var : {
						
					},
					//模块多语言
					$lang : {
						
					},
					//搜索标识符
					$search : ''
				});
			});
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/cashier/resource/js/", 'js', true);
        </script>
        <!-- 引入JS -->
		<script type="text/javascript" src="${LUI_ContextPath}/fssc/cashier/resource/js/index.js"></script>
    </template:replace>
</template:include>
