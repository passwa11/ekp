<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.list" spa="true" rwd="true">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-voucher:module.fssc.voucher') }-${ lfn:message('fssc-voucher:table.fsscVoucherMain') }" />
    </template:replace>
    <template:replace name="nav">
    	<ui:combin ref="menu.nav.title">
            <ui:varParam name="title" value="${ lfn:message('fssc-voucher:module.fssc.voucher') }"></ui:varParam>
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
		  						"text" : "${ lfn:message('list.alldoc') }",
		  						"href" :  "/listAll",
								"router" : true,		  					
				  				"icon" : "lui_iconfont_navleft_com_all"
		  					}]
		  					</ui:source>
		  				</ui:varParam>
		  			</ui:combin>
				</ui:content>
				<kmss:authShow roles="ROLE_FSSCVOUCHER_SETTING;ROLE_FSSCVOUCHER_RULE">
				<ui:content title="${ lfn:message('list.otherOpt') }" expand="true">
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[{
		  						"text" : "${ lfn:message('list.manager') }",
		  						"href" :  "javascript:LUI.pageOpen('${LUI_ContextPath }/sys/profile/moduleindex.jsp?nav=/fssc/voucher/tree.jsp','_rIframe');",
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
        <ui:tabpanel id="fsscVoucherPanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
			<ui:content id="fsscVoucherContent" title="" cfg-route="{path:'/listAll'}">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="docNumber" ref="criterion.sys.docSubject" title="${lfn:message('fssc-voucher:fsscVoucherMain.docNumber')}" />
                <list:cri-auto modelName="com.landray.kmss.fssc.voucher.model.FsscVoucherMain" property="docFinanceNumber" expand="true"/>
                <list:cri-auto modelName="com.landray.kmss.fssc.voucher.model.FsscVoucherMain" property="fdModelNumber" expand="true"/>
                <list:cri-auto modelName="com.landray.kmss.fssc.voucher.model.FsscVoucherMain" property="fdAccountingYear" expand="true"/>
				<list:cri-criterion title="${lfn:message('fssc-voucher:fsscVoucherMain.fdCompany')}" key="fdCompanyName">
                    <list:box-select>
                        <list:item-select type="lui/criteria/criterion_input!TextInput">
                            <ui:source type="Static">
                                [{placeholder:'${lfn:message('fssc-voucher:fsscVoucherMain.fdCompany')}'}]
                            </ui:source>
                        </list:item-select>
                    </list:box-select>
                </list:cri-criterion>
				 <list:cri-criterion title="${ lfn:message('fssc-voucher:fsscVoucherMain.fdBookkeepingStatus')}" key="fdBookkeepingStatus" expand="true">
                    <list:box-select>
                        <list:item-select >
                            <ui:source type="Static">
                                [{text:'${ lfn:message('fssc-voucher:enums.fd_bookkeeping_status.10')}', value:'10'},
                                 {text:'${ lfn:message('fssc-voucher:enums.fd_bookkeeping_status.11')}',value:'11'},
                                 {text:'${ lfn:message('fssc-voucher:enums.fd_bookkeeping_status.20')}',value:'20'},
                                 {text:'${ lfn:message('fssc-voucher:enums.fd_bookkeeping_status.30')}',value:'30'}]
                            </ui:source>
                        </list:item-select>
                    </list:box-select>
                </list:cri-criterion>
				 <list:cri-auto modelName="com.landray.kmss.fssc.voucher.model.FsscVoucherMain" property="fdPeriod" expand="false"/>
				 <list:cri-auto modelName="com.landray.kmss.fssc.voucher.model.FsscVoucherMain" property="fdVoucherDate" expand="false"/>
				 <list:cri-auto modelName="com.landray.kmss.fssc.voucher.model.FsscVoucherMain" property="fdBookkeepingDate" expand="false"/>
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fsscVoucherMain.docNumber" text="${lfn:message('fssc-voucher:fsscVoucherMain.docNumber')}" group="sort.list" value="down" />
                            <list:sort property="fsscVoucherMain.docAlterTime" text="${lfn:message('fssc-voucher:fsscVoucherMain.docAlterTime')}" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
                        	<!-- 批量记账 -->
                        	<kmss:auth requestURL="/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=bookkeeping">
								<ui:button text="${lfn:message('fssc-voucher:button.bookkeeping.batch')}" onclick="batchBookkeeping()" order="3" />
							</kmss:auth>
                            <kmss:auth requestURL="/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAllByAuth()" order="4" id="btnDelete" />
                            <ui:button text="${lfn:message('button.export')}" id="export" order="5" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.voucher.model.FsscVoucherMain')" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=view&fdId=!{fdId}" name="columntable">
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
                modelName: 'com.landray.kmss.fssc.voucher.model.FsscVoucherMain',
                templateName: '',
                basePath: '/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("fssc-voucher:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            seajs.use(['lui/framework/module'],function(Module){
				Module.install('fsscVoucher',{
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
            
            function batchBookkeeping(){
            	if($("input[name='List_Selected']:checked").length==0){
                    seajs.use(['lui/dialog'], function(dialog) {
                        dialog.alert('<bean:message key="page.noSelect"/>');
                    });
                    return;
                }
                var idArray = new Array();
                $("input[name='List_Selected']:checked").each(function(){
                    idArray.push($(this).val());
                });
                
                 seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
                     dialog.confirm('${ lfn:message("fssc-voucher:button.bookkeeping.confirm") }', function(isOk) {
                         if(isOk) {
                             var del_load = dialog.loading("${lfn:message('fssc-voucher:bookkeeping.loading')}");
                             $.post('${LUI_ContextPath}/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=batchBookkeeping&fdId=${param.fdId}',
                                 $.param({"fdVoucherMainIds":idArray.join(",")},true),function(data){
                                     if(del_load != null){
                                         del_load.hide();
                                     }
                                     var fdIsBoolean = data.fdIsBoolean;//true 记账成功 false 记账失败
                                     var messageStr = data.messageStr;
                                     if(messageStr && messageStr.length > 0){
                                         dialog.alert(data.messageStr);
                                         topic.publish("list.refresh");
                                     }else{
                                         dialog.alert('<bean:message bundle="fssc-voucher" key="bookkeeping.success"/>');
                                         setTimeout(function(){window.location.reload();},1000);
                                     }
                             },'json');
                         }
                     });
                 });
            }
            
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/voucher/resource/js/", 'js', true);
        </script>
        <!-- 引入JS -->
		<script type="text/javascript" src="${LUI_ContextPath}/fssc/voucher/resource/js/index.js"></script>
    </template:replace>
</template:include>
