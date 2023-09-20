<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.list" spa="true" rwd="true">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-fee:module.fssc.fee') }-${ lfn:message('fssc-fee:table.fsscFeeMain') }" />
    </template:replace>
    <template:replace name="nav">
        <ui:combin ref="menu.nav.title">
            <ui:varParam name="title" value="${ lfn:message('fssc-fee:module.fssc.fee') }"></ui:varParam>
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
				<ui:combin ref="menu.nav.search">
					<ui:varParams modelName="com.landray.kmss.fssc.fee.model.FsscFeeMain"/>
				</ui:combin>
				<ui:content title="台账">
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
		  					<ui:source type="Static">
			  					[{
			  						"text" : "台账查询",
			  						"href" :  "/listLedger",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_query"
			  					}]
		 					</ui:source>
		 				</ui:varParam>
					</ui:combin>
				</ui:content>
				<kmss:authShow roles="ROLE_FSSCFEE_SETTING"><c:set var="otherOpt" value="true" /></kmss:authShow>
                <% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.fssc.fee.model.FsscFeeMain")) { %>
                <c:set var="recycleOpt" value="true" />
                <% } %>
				<c:if test="${otherOpt=='true'  or recycleOpt=='true'}">
				<ui:content title="${ lfn:message('list.otherOpt') }" expand="true">
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
		  					<kmss:authShow roles="ROLE_FSSCFEE_SETTING">
		  					<c:if test="${recycleOpt=='true' }">
		  					,
		  					</c:if>
		  					{
		  						"text" : "${ lfn:message('list.manager') }",
		  						"href" :  "javascript:LUI.pageOpen('${LUI_ContextPath }/sys/profile/moduleindex.jsp?nav=/fssc/fee/tree.jsp','_rIframe');",
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
         <ui:tabpanel id="fsscFeePanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
			<ui:content id="fsscFeeContent" title="" cfg-route="{path:'/listCreate'}">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('fssc-fee:fsscFeeMain.docSubject')}" />
                <list:cri-criterion expand="false" title="${lfn:message('fssc-fee:fsscFeeMain.docStatus')}" key="docStatus" multi="false">
                    <list:box-select>
                         <list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-enable="false" cfg-if="(param['mydoc'] == 'all' || param['mydoc'] == 'create' || param['mydoc'] == 'approved' || param['doctype'] == 'follow') && param['docStatus'] !='00' && param['docStatus'] !='32' ">
							<ui:source type="Static">
								[{text:'${ lfn:message('status.draft')}', value:'10'},
								{text:'${ lfn:message('status.examine')}',value:'20'},
								{text:'${ lfn:message('status.refuse')}',value:'11'},
								{text:'${ lfn:message('status.discard')}',value:'00'},
								{text:'${ lfn:message('status.publish')}',value:'30'}]
							</ui:source>
						</list:item-select>
                    </list:box-select>
                </list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.fssc.fee.model.FsscFeeMain" property="docNumber" expand="true" />
                <list:cri-auto modelName="com.landray.kmss.fssc.fee.model.FsscFeeMain" property="fdIsClosed" expand="true" />
                <!-- 分类模板 -->
                <list:cri-ref ref="criterion.sys.category" key="docTemplate" multi="false" title="${lfn:message('fssc-fee:fsscFeeMain.docTemplate')}" expand="false">
                    <list:varParams modelName="com.landray.kmss.fssc.fee.model.FsscFeeTemplate" />
                </list:cri-ref>
                <list:cri-auto modelName="com.landray.kmss.fssc.fee.model.FsscFeeMain" property="docCreator" />
                <list:cri-auto modelName="com.landray.kmss.fssc.fee.model.FsscFeeMain" property="docCreateTime" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fsscFeeMain.docNumber" text="${lfn:message('fssc-fee:fsscFeeMain.docNumber')}" group="sort.list" />
                            <list:sort property="fsscFeeMain.docCreateTime" text="${lfn:message('fssc-fee:fsscFeeMain.docCreateTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="4">

                            <kmss:auth requestURL="/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="1" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAllByAuth()" order="2" id="btnDelete" />
                            <ui:button text="${lfn:message('button.export')}" id="export" order="3" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.fee.model.FsscFeeMain')" />
                      	<%-- 修改权限 --%>
							<c:import url="/sys/right/import/doc_right_change_button.jsp" charEncoding="UTF-8">
							<c:param name="modelName" value="com.landray.kmss.fssc.fee.model.FsscFeeMain" />
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
                    {url:appendQueryParameter('/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=view&fdId=!{fdId}" name="columntable">
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
                    modelName: 'com.landray.kmss.fssc.fee.model.FsscFeeMain',
                    templateName: 'com.landray.kmss.fssc.fee.model.FsscFeeTemplate',
                    basePath: '/fssc/fee/fssc_fee_main/fsscFeeMain.do',
                    canDelete: '${canDelete}',
                    mode: 'main_template',
                    templateService: 'fsscFeeTemplateService',
                    templateAlert: '${lfn:message("fssc-fee:treeModel.alert.templateAlert")}',
                    customOpts: {

                        ____fork__: 0
                    },
                    lang: {
                        noSelect: '${lfn:message("page.noSelect")}',
                        comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                    }

                };
                seajs.use(['lui/framework/module'],function(Module){
    				Module.install('fsscFee',{
    					//模块变量
    					$var : {
    						
    					},
    					//模块多语言
    					$lang : {
    						myCreate : "${ lfn:message('list.create') }",
    					},
    					//搜索标识符
    					$search : ''
    				});
    			});
                Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/fee/resource/js/", 'js', true);
            </script>
            <!-- 引入JS -->
			<script type="text/javascript" src="${LUI_ContextPath}/fssc/fee/resource/js/index.js"></script>
        </template:replace>
</template:include>
