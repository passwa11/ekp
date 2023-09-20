<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<template:include ref="default.list" spa="true" rwd="true">
    <template:replace name="title">
        <c:out value="${ lfn:message('hr-ratify:module.hr.ratify') }" />
    </template:replace>
    <template:replace name="head">
    	<style>
        	.lui_tabpanel_list_navs_item_l{
				max-width: 20%!important;
			}
        </style>
    </template:replace>
    <template:replace name="nav">
        <div class="lui_list_noCreate_frame">
            <ui:combin ref="menu.nav.title">
                <ui:varParam name="title" value="${ lfn:message('hr-ratify:module.hr.ratify') }" />
                <%-- 操作区 --%>
				<ui:varParam name="operation">
					<ui:source type="Static">
					[
						{
							"text": "${ lfn:message('hr-ratify:py.WoQiCaoDe') }",
							"href": "/mainCriterionMydoc0",
							"router" : true,
							"icon": "lui_iconfont_navleft_com_my_drafted"
						},
						{
							"text": "${ lfn:message('hr-ratify:lbpm.approval.my') }",
							"href": "/mainCriterionMydoc1",
							"router" : true,
							"icon": "lui_iconfont_navleft_com_my_beapproval"
						},
						{
							"text": "${ lfn:message('hr-ratify:lbpm.approved.my') }",
							"href": "/mainCriterionMydoc2",
							"router" : true,
							"icon": "lui_iconfont_navleft_com_my_approvaled"
						},
						{
							"text": "${ lfn:message('hr-ratify:py.FenLeiGaiLan') }",
							"href": "/custome0",
							"router" : true,
							"icon": "lui_iconfont_navleft_com_classify"
						}
					]
					</ui:source>
				</ui:varParam>
            </ui:combin>
        </DIV>
        <div class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:if test="${param.j_iframe=='true'}">
                    <c:set var="j_iframe" value="?j_iframe=true" />
                </c:if>

                <ui:content title="${ lfn:message('hr-ratify:py.YeWuDaoHang') }" expand="false">
                    <ui:combin ref="menu.nav.simple">
                        <ui:varParam name="source">
                            <ui:source type="Static">
                                [ 
                                	{ 
                                		"text" : "${ lfn:message('hr-ratify:py.SuoYouLiuCheng') }",
                                		"href" : "/main",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_com_all"
                                	},
                                	{ 
                                		"text" : "${ lfn:message('hr-ratify:py.LiuChengGenZong')}",
                                		"href" : "/follow",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_review_track"
                                	},
                                	{ 
                                		"text" : "${ lfn:message('hr-ratify:table.hrRatifyFeedback') }",
                                		"href" : "/feedback",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_review_feedback"
                                	}
                                	<%if(com.landray.kmss.sys.subordinate.util.SubordinateUtil.getInstance().getModelByModuleAndUser("hr-ratify:module.hr.ratify").size() > 0) {%>
                                		,{
											"text" : "${lfn:message('hr-ratify:py.XiaShuGongZuo') }",
											"href" :  "/sys/subordinate",
											"router" : true,
											"icon" : "lui_iconfont_navleft_subordinate"
										 }
                                	<%} %> 
                                ]
                            </ui:source>
                        </ui:varParam>
                    </ui:combin>
                </ui:content>
                <!-- 员工关系 -->
                <kmss:authShow roles="ROLE_HRRATIFY_STAFF_CONCERN">
	                <ui:content title="${lfn:message('hr-ratify:hrRatifyEntry.relation.employment') }" expand="true">
	                	<ui:combin ref="menu.nav.simple">
	                        <ui:varParam name="source">
	                            <ui:source type="Static">
	                            	[ 
	                                	<kmss:auth requestURL="/hr/ratify/hr_ratify_main_staff_config/hrRatifyStaffConfig.do?method=entry">
	                                		{ 
		                                		"text" : "<bean:message bundle="hr-ratify" key="hrRatify.entry.manage"/>",
		                                		"href" : "/entryManageWait",
		                                		"router" : true,
		                                		"icon" : "lui_iconfont_navleft_ratify_entry"
		                                	},
	                                	</kmss:auth>
	                                	<kmss:auth requestURL="/hr/ratify/hr_ratify_main_staff_config/hrRatifyStaffConfig.do?method=leave">
		                                	{ 
		                                		"text" : "<bean:message bundle="hr-ratify" key="hrRatify.leave.manage"/>",
		                                		"href" : "/leaveManageWait",
		                                		"router" : true,
		                                		"icon" : "lui_iconfont_navleft_ratify_transfer"
		                                	},
	                                	</kmss:auth>
	                                	<kmss:auth requestURL="/hr/ratify/hr_ratify_main_staff_config/hrRatifyStaffConfig.do?method=positive">
		                                	{ 
		                                		"text" : "<bean:message bundle="hr-ratify" key="hrRatify.positive.manage"/>",
		                                		"href" : "/positiveManage",
		                                		"router" : true,
		                                		"icon" : "lui_iconfont_navleft_ratify_become"
		                                	},
	                                	</kmss:auth>
										<kmss:auth requestURL="/hr/ratify/hr_ratify_main_staff_config/hrRatifyStaffConfig.do?method=transfer">
											{ 
		                                		"text" : "<bean:message bundle="hr-ratify" key="hrRatify.transfer.manage"/>",
		                                		"href" : "/transferManage",
		                                		"router" : true,
		                                		"icon" : "lui_iconfont_navleft_ratify_transfer"
		                                	},
										</kmss:auth>
	                                	<kmss:auth requestURL="/hr/ratify/hr_ratify_main_staff_config/hrRatifyStaffConfig.do?method=contract">
		                                	{ 
		                                		"text" : "<bean:message bundle="hr-ratify" key="hrRatify.contract.manage"/>",
		                                		"href" : "/contractNoSign",
		                                		"router" : true,
		                                		"icon" : "lui_iconfont_navleft_ratify_transfer"
		                                	}
	                                	</kmss:auth>
	                                ]
	                            </ui:source>
	                        </ui:varParam>
	                    </ui:combin>
	                </ui:content>
                </kmss:authShow>
                <!-- 人事异动 -->
                <ui:content title="${lfn:message('hr-ratify:module.hr.ratify') }" expand="false">
                    <ui:combin ref="menu.nav.simple">
                        <ui:varParam name="source">
                            <ui:source type="Static">
                                [ 
                                	{ 
                                		"text" : "<bean:message bundle="hr-ratify" key="table.hrRatifyEntry"/>",
                                		"href" : "/entry",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_ratify_entry"
                                	},
                                	{ 
                                		"text" : "<bean:message bundle="hr-ratify" key="table.hrRatifyPositive"/>",
                                		"href" : "/positive",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_ratify_become"
                                	},
                                	{ 
                                		"text" : "<bean:message bundle="hr-ratify" key="table.hrRatifyTransfer"/>",
                                		"href" : "/transfer",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_ratify_transfer"
                                	},
                                	{ 
                                		"text" : "<bean:message bundle="hr-ratify" key="table.hrRatifyLeave"/>",
                                		"href" : "/leave",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_ratify_leave"
                                	},
                                	{
				  						"text" : "<bean:message bundle="hr-ratify" key="table.hrRatifyFire"/>",
				  						"href" :  "/fire",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_ratify_dismiss"
				  					},
				  					{
				  						"text" : "<bean:message bundle="hr-ratify" key="table.hrRatifyRetire"/>",
				  						"href" :  "/retire",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_ratify_retire"
				  					},
				  					{
				  						"text" : "<bean:message bundle="hr-ratify" key="table.hrRatifyRehire"/>",
				  						"href" :  "/rehire",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_ratify_rehire"
				  					},
				  					{
				  						"text" : "<bean:message bundle="hr-ratify" key="table.hrRatifySalary"/>",
				  						"href" :  "/salary",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_ratify_raise"
				  					} 
                                ]
                            </ui:source>
                        </ui:varParam>
                    </ui:combin>
                </ui:content>
                <!-- 人事合同 -->
                <ui:content title="${ lfn:message('hr-ratify:py.RenShiHeTong') }" expand="false">
                    <ui:combin ref="menu.nav.simple">
                        <ui:varParam name="source">
                            <ui:source type="Static">
                                [ 
                                	{ 
                                		"text" : "<bean:message bundle="hr-ratify" key="table.hrRatifySign"/>",
                                		"href" : "/sign",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_ratify_sign"
                                	},
                                	{ 
                                		"text" : "<bean:message bundle="hr-ratify" key="table.hrRatifyChange"/>",
                                		"href" : "/change",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_ratify_change"
                                	},
                                	{
                                		"text" : "<bean:message bundle="hr-ratify" key="table.hrRatifyRemove"/>",
                                		"href" : "/remove",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_ratify_remove"
                                	} 
                                ]
                            </ui:source>
                        </ui:varParam>
                    </ui:combin>
                </ui:content>
               <!-- 流程查询 -->
                <ui:content title="${ lfn:message('hr-ratify:py.LiuChengChaXun') }" expand="false">
                	<ui:combin ref="menu.nav.simple">
                		<ui:varParam name="source">
                			<ui:source type="Static">
                				[
                					{
				  						"text" : "${ lfn:message('hr-ratify:py.LiuChengChaXun1') }",
				  						"href" :  "/search1",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_com_query"
				  					},
                                	{
				  						"text" : "${ lfn:message('hr-ratify:py.LiuChengChaXun2') }",
				  						"href" :  "/search2",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_com_query"
				  					},
                                	{
				  						"text" : "${ lfn:message('hr-ratify:py.LiuChengChaXun3') }",
				  						"href" :  "/search3",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_com_query"
				  					},
                                	{
				  						"text" : "${ lfn:message('hr-ratify:py.LiuChengChaXun4') }",
				  						"href" :  "/search4",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_com_query"
				  					},
                                	{
				  						"text" : "${ lfn:message('hr-ratify:py.LiuChengChaXun5') }",
				  						"href" :  "/search5",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_com_query"
				  					},
                                	{
				  						"text" : "${ lfn:message('hr-ratify:py.LiuChengChaXun6') }",
				  						"href" :  "/search6",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_com_query"
				  					},
                                	{
				  						"text" : "${ lfn:message('hr-ratify:py.LiuChengChaXun7') }",
				  						"href" :  "/search7",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_com_query"
				  					},
                                	{
				  						"text" : "${ lfn:message('hr-ratify:py.LiuChengChaXun8') }",
				  						"href" :  "/search8",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_com_query"
				  					},
                                	{
				  						"text" : "${ lfn:message('hr-ratify:py.LiuChengChaXun9') }",
				  						"href" :  "/search9",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_com_query"
				  					},
                                	{
				  						"text" : "${ lfn:message('hr-ratify:py.LiuChengChaXun10') }",
				  						"href" :  "/search10",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_com_query"
				  					},
                                	{
				  						"text" : "${ lfn:message('hr-ratify:py.LiuChengChaXun11') }",
				  						"href" :  "/search11",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_com_query"
				  					},
                                	{
				  						"text" : "${ lfn:message('hr-ratify:py.LiuChengChaXun12') }",
				  						"href" :  "/search12",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_com_query"
				  					}
                				]
                			</ui:source>
                		</ui:varParam>
                	</ui:combin>
                </ui:content>
                <ui:content title="${ lfn:message('hr-ratify:py.QiTaCaoZuo') }" expand="false">
                    <ui:combin ref="menu.nav.simple">

                        <ui:varParam name="source">

                            <ui:source type="Static">
                                [{
			  						"text" : "${ lfn:message('hr-ratify:hrRatifyMain.fillingBox') }",
			  						"href" :  "/listFiling",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_file"
			  					},
                                <%-- 回收站*12--%>
								<% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.hr.ratify.model.HrRatifyMain")){%>
							    { 
								    "text" : "${ lfn:message('hr-ratify:hrRatifyMain.recyclebin') }",
							        "href" : "/recover",
							        "router" : true, 
							 	    "icon" : "lui_iconfont_navleft_com_recycle" 
								 },
								 <% } %> 
                                <kmss:authShow roles="ROLE_HRRATIFY_SETTING">
	                             {
									"text" : "${ lfn:message('list.manager') }",
									"icon" : "lui_iconfont_navleft_com_background",
									"router" : true,
									"href" : "/management"
								}
                                </kmss:authShow>	
                                ]
                            </ui:source>
                          
			
                        </ui:varParam>
                    </ui:combin>

                </ui:content>
            </ui:accordionpanel>
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                customOpts: {
                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }
            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/hr/ratify/resource/js/", 'js', true);
        </script>
    </template:replace>
    <template:replace name="content">
        <ui:tabpanel id="hrRatifyPanel" layout="sys.ui.tabpanel.list" cfg-router="true">
            <ui:content id="HrRatifyMainContent" title="${lfn:message('hr-ratify:table.hrRatifyMain') }" cfg-route="{path:'/mainCriterionMydoc0'}">
                <ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_ratify_main/index.jsp"></ui:iframe>
            </ui:content>
            <ui:content id="HrRatifyEntryContent" title="${lfn:message('hr-ratify:table.hrRatifyEntry') }" cfg-route="{path:'/entry'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_ratify_entry/index.jsp"></ui:iframe>
            </ui:content>
            <ui:content id="HrRatifyPositiveContent" title="${lfn:message('hr-ratify:table.hrRatifyPositive') }" cfg-route="{path:'/positive'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_ratify_positive/index.jsp"></ui:iframe>
            </ui:content>
            <ui:content id="HrRatifyTransferContent" title="${lfn:message('hr-ratify:table.hrRatifyTransfer') }" cfg-route="{path:'/transfer'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_ratify_transfer/index.jsp"></ui:iframe>
            </ui:content>
            <ui:content id="HrRatifyLeaveContent" title="${lfn:message('hr-ratify:table.hrRatifyLeave') }" cfg-route="{path:'/leave'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_ratify_leave/index.jsp"></ui:iframe>
            </ui:content>
            <ui:content id="HrRatifyFireContent" title="${lfn:message('hr-ratify:table.hrRatifyFire') }" cfg-route="{path:'/fire'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_ratify_fire/index.jsp"></ui:iframe>
            </ui:content>
            <ui:content id="HrRatifyRetireContent" title="${lfn:message('hr-ratify:table.hrRatifyRetire') }" cfg-route="{path:'/retire'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_ratify_retire/index.jsp"></ui:iframe>
            </ui:content>
            <ui:content id="HrRatifyRehireContent" title="${lfn:message('hr-ratify:table.hrRatifyRehire') }" cfg-route="{path:'/rehire'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_ratify_rehire/index.jsp"></ui:iframe>
            </ui:content>
            <ui:content id="HrRatifySalaryContent" title="${lfn:message('hr-ratify:table.hrRatifySalary') }" cfg-route="{path:'/salary'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_ratify_salary/index.jsp"></ui:iframe>
            </ui:content>
            <ui:content id="HrRatifySignContent" title="${lfn:message('hr-ratify:table.hrRatifySign') }" cfg-route="{path:'/sign'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_ratify_sign/index.jsp"></ui:iframe>
            </ui:content>
            <ui:content id="HrRatifyChangeContent" title="${lfn:message('hr-ratify:table.hrRatifyEntryChange') }" cfg-route="{path:'/change'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_ratify_change/index.jsp"></ui:iframe>
            </ui:content>
            <ui:content id="HrRatifyRemoveContent" title="${lfn:message('hr-ratify:table.hrRatifyEntryRemove') }" cfg-route="{path:'/remove'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_ratify_remove/index.jsp"></ui:iframe>
            </ui:content>
            <!-- 入职管理 -->
            <ui:content id="HrEntryManageWait" title="${lfn:message('hr-ratify:hrRatifyEntry.toBeEmployed') }" cfg-route="{path:'/entryManageWait'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_staff_concern/hrEntryManage_index.jsp?fdStatus=1"></ui:iframe>
            </ui:content>
            <ui:content id="HrEntryManageRecent" title="${lfn:message('hr-ratify:hrRatifyEntry.latestEmployed') }" cfg-route="{path:'/entryManageRecent'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_staff_concern/hrEntryManage_index.jsp?fdStatus=2"></ui:iframe>
            </ui:content>
            <ui:content id="HrEntryManageAbandon" title="${lfn:message('hr-ratify:hrRatifyEntry.alredyGiveup') }" cfg-route="{path:'/entryManageAbandon'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_staff_concern/hrEntryManage_index.jsp?fdStatus=3"></ui:iframe>
            </ui:content>
            <ui:content id="HrLeaveManageWait" title="${lfn:message('hr-ratify:hrRatifyEntry.toLeave') }" cfg-route="{path:'/leaveManageWait'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_staff_concern/hrLeaveManage_index.jsp?fdLeaveStatus=1"></ui:iframe>
            </ui:content>
            <ui:content id="HrLeaveManageContent" title="${lfn:message('hr-ratify:hrRatifyEntry.resigned') }" cfg-route="{path:'/leaveManage'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_staff_concern/hrHasLeaveManage_index.jsp"></ui:iframe>
            </ui:content>
            <!-- 转正管理 -->
             <ui:content id="HrPositiveManageContent" title="${lfn:message('hr-ratify:hrRatifyEntry.management.employmentConfirmation') }" cfg-route="{path:'/positiveManage'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_staff_concern/hrPositiveManage_index.jsp"></ui:iframe>
            </ui:content>
            <!-- 人事调动 -->
             <ui:content id="HrTransferContent" title="${lfn:message('hr-ratify:hrRatifyEntry.position.adjustment') }" cfg-route="{path:'/transferManage'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_staff_concern/hrTransferManage_index.jsp?type=post"></ui:iframe>
            </ui:content>
             <ui:content id="HrTransferContentSalary" title="${lfn:message('hr-ratify:hrRatifyEntry.salary.adjustment') }" cfg-route="{path:'/transferManageSalary'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_staff_concern/hrTransferManage_index.jsp?type=salary"></ui:iframe>
            </ui:content>
            <!-- 人事合同 -->
            <ui:content id="HrContractNoSignContent" title="${lfn:message('hr-ratify:hrRatifyEntry.contracts.notSigned') }" cfg-route="{path:'/contractNoSign'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_staff_concern/hrContractManage_index.jsp?fdSignType=0"></ui:iframe>
            </ui:content>
             <ui:content id="HrContractSignContent" title="${lfn:message('hr-ratify:hrRatifyEntry.contracts.signed') }" cfg-route="{path:'/contractSign'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_staff_concern/hrContractManage_index.jsp?fdSignType=1"></ui:iframe>
            </ui:content>
            <ui:content title="" id="HrRatifySearchEntryContent" cfg-route="{path:'/search1'}">
            	<ui:iframe src="${LUI_ContextPath }/sys/search/ui/nav_search_new.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyEntry"></ui:iframe>
            </ui:content>
            <ui:content title="" id="HrRatifySearchPositiveContent" cfg-route="{path:'/search2'}">
            	<ui:iframe src="${LUI_ContextPath }/sys/search/ui/nav_search_new.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyPositive"></ui:iframe>
            </ui:content>
            <ui:content title="" id="HrRatifySearchTransferContent" cfg-route="{path:'/search3'}">
            	<ui:iframe src="${LUI_ContextPath }/sys/search/ui/nav_search_new.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyTransfer"></ui:iframe>
            </ui:content>
            <ui:content title="" id="HrRatifySearchLeaveContent" cfg-route="{path:'/search4'}">
            	<ui:iframe src="${LUI_ContextPath }/sys/search/ui/nav_search_new.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyLeave"></ui:iframe>
            </ui:content>
            <ui:content title="" id="HrRatifySearchFireContent" cfg-route="{path:'/search5'}">
            	<ui:iframe src="${LUI_ContextPath }/sys/search/ui/nav_search_new.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyFire"></ui:iframe>
            </ui:content>
            <ui:content title="" id="HrRatifySearchRetireContent" cfg-route="{path:'/search6'}">
            	<ui:iframe src="${LUI_ContextPath }/sys/search/ui/nav_search_new.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyRetire"></ui:iframe>
            </ui:content>
            <ui:content title="" id="HrRatifySearchRehireContent" cfg-route="{path:'/search7'}">
            	<ui:iframe src="${LUI_ContextPath }/sys/search/ui/nav_search_new.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyRehire"></ui:iframe>
            </ui:content>
            <ui:content title="" id="HrRatifySearchSalaryContent" cfg-route="{path:'/search8'}">
            	<ui:iframe src="${LUI_ContextPath }/sys/search/ui/nav_search_new.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifySalary"></ui:iframe>
            </ui:content>
            <ui:content title="" id="HrRatifySearchSignContent" cfg-route="{path:'/search9'}">
            	<ui:iframe src="${LUI_ContextPath }/sys/search/ui/nav_search_new.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifySign"></ui:iframe>
            </ui:content>
            <ui:content title="" id="HrRatifySearchChangeContent" cfg-route="{path:'/search10'}">
            	<ui:iframe src="${LUI_ContextPath }/sys/search/ui/nav_search_new.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyChange"></ui:iframe>
            </ui:content>
            <ui:content title="" id="HrRatifySearchRemoveContent" cfg-route="{path:'/search11'}">
            	<ui:iframe src="${LUI_ContextPath }/sys/search/ui/nav_search_new.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyRemove"></ui:iframe>
            </ui:content>
            <ui:content title="" id="HrRatifySearchOtherContent" cfg-route="{path:'/search12'}">
            	<ui:iframe src="${LUI_ContextPath }/sys/search/ui/nav_search_new.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyOther"></ui:iframe>
            </ui:content>
            <ui:content title="" id="HrRatifyCustomeContent" cfg-route="{path:'/custome0'}">
            	<ui:iframe src="${LUI_ContextPath }/hr/ratify/hr_ratify_main/hrRatifyMain_preview.jsp"></ui:iframe>
            </ui:content>
            
        </ui:tabpanel>
    </template:replace>
    <% pageContext.setAttribute( "userId", UserUtil.getKMSSUser().getUserId()); pageContext.setAttribute( "depId", UserUtil.getKMSSUser().getDeptId()); if(UserUtil.getUser().getFdParentOrg()!=null){ pageContext.setAttribute( "orgId", UserUtil.getUser().getFdParentOrg().getFdId());
    }else{ pageContext.setAttribute( "orgId", null); } pageContext.setAttribute( "authAreaId", UserUtil.getKMSSUser().getAuthAreaId()); %>
        <template:replace name="script">
            <!-- JSP中建议只出现·安装模块·的JS代码，其余JS代码采用引入方式 -->
            <script type="text/javascript">
                seajs.use(['lui/framework/module'], function(Module) {
                    Module.install('hrRatify', {
                        //模块变量
                        $var: {
                            userId: '${userId}',
                            depId: '${depId}',
                            orgId: '${orgId}',
                            authAreaId: '${authAreaId}'
                        },
                        //模块多语言
                        $lang: {
                        	pageNoSelect : '${lfn:message("page.noSelect")}',
     						optSuccess : '${lfn:message("return.optSuccess")}',
     						optFailure : '${lfn:message("return.optFailure")}',
     						buttonDelete : '${lfn:message("button.deleteall")}',
                        	mainCriterionMydoc0 : '${lfn:message("hr-ratify:py.WoQiCaoDe")}',
                        	mainCriterionMydoc1 : '${lfn:message("hr-ratify:lbpm.approval.my")}',
                        	mainCriterionMydoc2 : "${lfn:message('hr-ratify:lbpm.approved.my')}",
                        	main : '${lfn:message("hr-ratify:py.SuoYouLiuCheng")}',
                        	follow : '${lfn:message("hr-ratify:py.LiuChengGenZong")}',
                        	feedback : '${lfn:message("hr-ratify:table.hrRatifyFeedback")}',
                        	search : '${lfn:message("hr-ratify:py.LiuChengChaXun")}',
                        	custom0 : '${lfn:message("hr-ratify:py.FenLeiGaiLan")}'
                        },
                        //搜索标识符
                        $search: ''
                    });
                });
            </script>
            <!-- 引入JS -->
            <script type="text/javascript" src="${LUI_ContextPath}/hr/ratify/resource/js/index.js"></script>
        </template:replace>
</template:include>