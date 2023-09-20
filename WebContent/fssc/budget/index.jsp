<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
<%@page import="com.landray.kmss.fssc.budget.util.FsscBudgetUtil" %>
    <template:include ref="default.list"  spa="true" rwd="true">
        <template:replace name="title">
            <c:out value="${ lfn:message('fssc-budget:module.fssc.budget') }-${ lfn:message('fssc-budget:table.fsscBudgetMain') }" />
        </template:replace>
        <template:replace name="nav">
        	<ui:combin ref="menu.nav.title">
	            <ui:varParam name="title" value="${ lfn:message('fssc-budget:module.fssc.budget') }" />
	            <ui:varParam name="operation">
					<ui:source type="Static">
						
					</ui:source>
				</ui:varParam>
	        </ui:combin>
            <div id="menu_nav" class="lui_list_nav_frame">
                <ui:accordionpanel>
                    <ui:content title="${ lfn:message('fssc-budget:fsscBudget.button.import') }">
						<ui:combin ref="menu.nav.simple">
							<ui:varParam name="source">
			  					<ui:source type="Static">
			  						<fssc:nav displayName="fdName" modelName="com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme"  whereBlock="eopBasedataBudgetScheme.fdIsAvailable=true"  path="budgetMain"></fssc:nav>
			  					</ui:source>
			  				</ui:varParam>
			  			</ui:combin>
					</ui:content>
					<fssc:checkVersion version="true">
					<kmss:auth requestURL="/fssc/budget/fssc_budget_main/fsscBudgetMain.do?method=initBudget">
					<ui:content title="${ lfn:message('fssc-budget:fssc.budget.init.import') }" expand="false">
                        <ui:combin ref="menu.nav.simple">
							<ui:varParam name="source">
			  					<ui:source type="Static">
			  						[
		                                {
		                               "text": "${ lfn:message('fssc-budget:fssc.budget.init.import') }",
					                    "href": "/initImportBudget",
					                    "router" : true,
					                    "icon": "lui_iconfont_navleft_com_all"
		                                }
	                                ]
			  					</ui:source>
			  				</ui:varParam>
			  			</ui:combin>
                    </ui:content>
                    </kmss:auth>
                    </fssc:checkVersion>
                    <ui:content title="${ lfn:message('fssc-budget:py.YuSuanChaXun') }" expand="false">
                        <ui:combin ref="menu.nav.simple">
							<ui:varParam name="source">
			  					<ui:source type="Static">
			  						<fssc:nav displayName="fdName" modelName="com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme"  whereBlock="eopBasedataBudgetScheme.fdIsAvailable=true"  path="budgetData"></fssc:nav>
			  					</ui:source>
			  				</ui:varParam>
			  			</ui:combin>
                    </ui:content>
                    <ui:content title="${ lfn:message('fssc-budget:py.YuSuanDiaoZheng') }" expand="false">
                        <ui:combin ref="menu.nav.simple">
							<ui:varParam name="source">
			  					<ui:source type="Static">
			  						<fssc:nav displayName="fdName" modelName="com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme"  whereBlock="eopBasedataBudgetScheme.fdIsAvailable=true"  path="budgetAdjust"></fssc:nav>
			  					</ui:source>
			  				</ui:varParam>
			  			</ui:combin>
                    </ui:content>
                    <fssc:switchOn property="fdKnots">
                    <kmss:auth requestURL="/fssc/budget/fssc_budget_main/fsscBudgetMain.do?method=transferBudget">
                    <ui:content title="${ lfn:message('fssc-budget:fsscBudgetData.transferBudget.fdDesc') }" expand="false">
                        <ui:combin ref="menu.nav.simple">
							<ui:varParam name="source">
			  					<ui:source type="Static">
			  						[
		                                {
		                               "text": "${ lfn:message('fssc-budget:fsscBudgetData.transferBudget.fdDesc') }",
					                    "href": "/transferBudget",
					                    "router" : true,
					                    "icon": "lui_iconfont_navleft_com_all"
		                                }
	                                ]
			  					</ui:source>
			  				</ui:varParam>
			  			</ui:combin>
                    </ui:content>
                    </kmss:auth>
                    </fssc:switchOn>
                    <kmss:authShow roles="ROLE_FSSCBUDGET_REPORT">
                    <ui:content title="${ lfn:message('fssc-budget:py.YuSuanBaoBiao') }" expand="false">
                        <ui:combin ref="menu.nav.simple">
	                        <ui:varParam name="source">
	                            <ui:source type="Static">
	                                [
		                                {
		                               "text": "${ lfn:message('fssc-budget:message.budget.execute.ledger') }",
					                    "href": "/executeLedger",
					                    "router" : true,
					                    "icon": "lui_iconfont_navleft_com_statistics"
		                                },
		                                {
		                               "text": "${ lfn:message('fssc-budget:message.budget.count.report') }",
					                    "href": "/countReport",
					                    "router" : true,
					                    "icon": "lui_iconfont_navleft_com_statistics"
		                                }
		                                
	                                ]
	                            </ui:source>
	                        </ui:varParam>
	                    </ui:combin>
                    </ui:content>
                    </kmss:authShow>
                    <kmss:authShow roles="ROLE_FSSCBUDGET_SETTING"><c:set var="otherOpt" value="true" /></kmss:authShow>
                    <% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain")) { %>
                    <c:set var="recycleOpt" value="true" />
                    <% } %>
                    <c:if test="${otherOpt=='true'  or recycleOpt=='true'}">
                    <ui:content title="${ lfn:message('fssc-budget:py.QiTaCaoZuo') }">
						<ui:combin ref="menu.nav.simple">
							<ui:varParam name="source">
			  					<ui:source type="Static">
			  					[
			  					<%-- 关闭回收站功能时，模块首页不显示“回收站” --%>
			  					<c:if test="${recycleOpt=='true' }">
			  					{
			  						"text" : "${ lfn:message('sys-recycle:module.sys.recycle') }",
			  						"href" :  "/recover",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_recycle"
			  					}
			  					</c:if>
		  					<kmss:authShow roles="ROLE_FSSCBUDGET_SETTING">
			  					<c:if test="${recycleOpt=='true' }">
			  					,
			  					</c:if>
			  					{
			  						"text" : "${ lfn:message('list.manager') }",
			  						"href" :  "javascript:LUI.pageOpen('${LUI_ContextPath }/sys/profile/moduleindex.jsp?nav=/fssc/budget/tree.jsp','_rIframe');",
				  					"icon" : "lui_iconfont_navleft_com_background"
			  					}
			  					</kmss:authShow>
			  					]
			  					</ui:source>
			  				</ui:varParam>
			  			</ui:combin>
					</ui:content>
					</c:if>
                </ui:accordionpanel>
            </div>
        </template:replace>
        <template:replace name="content">
                <ui:tabpanel id="fsscBudgetPanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
					<ui:content id="fsscBudgetContent" title="" cfg-route="{path:'/budgetMain'}">
					</ui:content>
				</ui:tabpanel>
		</template:replace>
		<template:replace name="script">
            <script>
                var listOption = {
                    contextPath: '${LUI_ContextPath}',
                    modelName: 'com.landray.kmss.fssc.budget.model.FsscBudgetMain',
                    templateName: '',
                    basePath: '/fssc/budget/fssc_budget_main/fsscBudgetMain.do',
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
                Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/budget/resource/js/", 'js', true);
                seajs.use(['lui/framework/module'],function(Module){
    				Module.install('fsscBudget',{
    					//模块变量
    					$var : {
    						fdSchemeId:Com_GetUrlParameter(window.location.href,"fdSchemeId")
    					},
    					//模块多语言
    					$lang : {
    						myCreate : "${ lfn:message('list.create') }",
    					},
    					//搜索标识符
    					$search : ''
    				});
    			});
                function budget_href(id, path,target) {

            		seajs.use([
            				'lui/util/str',
            				'lui/topic',
            				'lui/util/env',
            				'lui/framework/router/router-utils',
            				'lui/spa/const','lui/framework/module' ], function(
            				str,
            				topic,
            				env,
            				routerUtils,
            				spaConst,Module) {
            			Module.find('fsscBudget').$var.fdSchemeId=id;
            			if (seajs.data.env.isSpa) {
            				// 启用路由模式
            				var router = routerUtils.getRouter();

            				if (router) {
            					router.push('/'+path, {
            						'fdSchemeId' : id
            					})
            					topic.publish(spaConst.SPA_CHANGE_ADD, {
            						value : {
            							'fdSchemeId' : id
            						}
            					})
            				}

            				return;
            			}
            			window.open(str.variableResolver(env.fn
            					.formatUrl("${varParams.href}"), {
            				value : id
            			}), '_self');

            		});

            	}
                LUI.ready(function() {
                	seajs.use(['lui/util/str','lui/topic','lui/util/env','lui/framework/router/router-utils','lui/spa/const','lui/framework/module' ], function(str,topic,env,routerUtils,spaConst,Module) {
                		setTimeout(function(){
                			if (seajs.data.env.isSpa) {
                   				// 启用路由模式
                   				var router = routerUtils.getRouter();
        						var schemeId=getFromHashByKey('fdSchemeId');
        						if(schemeId){
        							$(".lui_list_nav_list").find("li[data-path*='"+schemeId+"']").addClass('lui_list_nav_selected');
        						}

                   				return true;
                   			}
                		},500);

               		});
                });
                function getFromHashByKey(key){
         			var params = window.location.hash ? window.location.hash.substr(1)
         					.split("&") : [], paramsObject = {};
         					for (var i = 0; i < params.length; i++) {
         						if (!params[i])
         							continue;
         						var a = params[i].split("=");
         						if(a[0] == key){
         							return decodeURIComponent(a[1]);
         						}
         					}
         					return "";
         		};
            </script>
            <!-- 引入JS -->
			<script type="text/javascript" src="${LUI_ContextPath}/fssc/budget/resource/js/index.js"></script>
        </template:replace>
    </template:include>
