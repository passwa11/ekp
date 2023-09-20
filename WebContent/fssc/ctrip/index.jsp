<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.list" spa="true" rwd="true">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-ctrip:module.fssc.ctrip') }" />
    </template:replace>
    <template:replace name="nav">
        <ui:combin ref="menu.nav.title">
            <ui:varParam name="title" value="${ lfn:message('fssc-ctrip:module.fssc.ctrip') }"></ui:varParam>
            <ui:varParam name="operation">
				<ui:source type="Static">
					[
					
				]
				</ui:source>
			</ui:varParam>
        </ui:combin>
        <div id="menu_nav" class="lui_list_nav_frame">
			<ui:accordionpanel>
				<ui:combin ref="menu.nav.search">
				</ui:combin>
				<ui:content title="${ lfn:message('fssc-ctrip:ctrip.order.search') }">
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
		  					<ui:source type="Static">
			  					[{
			  						"text" : "${lfn:message('fssc-ctrip:table.fsscCtripOrderFlightInfo') }",
			  						"href" :  "/airOrder",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_query"
			  					},
			  					{
			  						"text" : "${lfn:message('fssc-ctrip:table.fsscCtripOrderHotelInfo') }",
			  						"href" :  "/hotelOrder",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_query"
			  					},
			  					{
			  						"text" : "${lfn:message('fssc-ctrip:table.fsscCtripOrderTrainInfo') }",
			  						"href" :  "/trainOrder",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_query"
			  					},
			  					{
			  						"text" : "${lfn:message('fssc-ctrip:table.fsscCtripOrderCar') }",
			  						"href" :  "/carOrder",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_query"
			  					}]
		 					</ui:source>
		 				</ui:varParam>
					</ui:combin>
				</ui:content>
				<ui:content title="${ lfn:message('fssc-ctrip:ctrip.settle.search') }">
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
		  					<ui:source type="Static">
			  					[{
			  						"text" : "${lfn:message('fssc-ctrip:table.fsscCtripAirSettleInfo') }",
			  						"href" :  "/airSettle",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_query"
			  					},
			  					{
			  						"text" : "${lfn:message('fssc-ctrip:table.fsscCtripHotelSettleInfo') }",
			  						"href" :  "/hotelSettle",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_query"
			  					},
			  					{
			  						"text" : "${lfn:message('fssc-ctrip:table.fsscCtripTrainSettleInfo') }",
			  						"href" :  "/trainSettle",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_query"
			  					},
			  					{
			  						"text" : "${lfn:message('fssc-ctrip:table.fsscCtripCarSettleInfo') }",
			  						"href" :  "/carSettle",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_query"
			  					}]
		 					</ui:source>
		 				</ui:varParam>
					</ui:combin>
				</ui:content>
				<kmss:authShow roles="ROLE_FSSCCTRIP1_SETTING">
				<ui:content title="${ lfn:message('list.otherOpt') }" expand="true">
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[{
		  						"text" : "${ lfn:message('list.manager') }",
		  						"href" :  "javascript:LUI.pageOpen('${LUI_ContextPath }/sys/profile/moduleindex.jsp?nav=/fssc/ctrip/tree.jsp','_rIframe');",
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
         <ui:tabpanel id="fsscCtripPanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
			<ui:content id="fsscCtripContent" title="" cfg-route="{path:'/airSettle'}">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                           
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
                            <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_air_settle_info/fsscCtripAirSettleInfo.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAllByAuth()" order="4" id="btnDelete" />
                            <ui:button text="${lfn:message('button.export')}" id="export" order="5" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.ctrip.model.FsscCtripAirSettleInfo')" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/ctrip/fssc_ctrip_air_settle_info/fsscCtripAirSettleInfo.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/ctrip/fssc_ctrip_air_settle_info/fsscCtripAirSettleInfo.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto/>
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
                    modelName: 'com.landray.kmss.fssc.ctrip.model.FsscCtripAirSettleInfo',
                    templateName: '',
                    basePath: '/fssc/ctrip/fssc_ctrip_air_settle_info/fsscCtripAirSettleInfo.do',
                    canDelete: '${canDelete}',
                    mode: 'main_template',
                    jPath: 'airSettle',
                    customOpts: {

                        ____fork__: 0
                    },
                    lang: {
                        noSelect: '${lfn:message("page.noSelect")}',
                        comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                    }

                };
                seajs.use(['lui/framework/module'],function(Module){
    				Module.install('fsscCtrip',{
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
                Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/ctrip/resource/js/", 'js', true);
            </script>
            <!-- 引入JS -->
			<script type="text/javascript" src="${LUI_ContextPath}/fssc/ctrip/resource/js/index.js"></script>
        </template:replace>
</template:include>
