<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<template:include ref="default.list" spa="true" rwd="true">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-expense:module.fssc.expense') }-${ lfn:message('fssc-expense:table.fsscExpenseMain') }" />
    </template:replace>
    <template:replace name="nav">
		<ui:combin ref="menu.nav.title">
            <ui:varParam name="title" value="${ lfn:message('fssc-expense:table.fsscExpenseMain') }" />
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
				<ui:content title="${ lfn:message('fssc-expense:py.CaiWuChaXun') }">
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
		  					<ui:source type="Static">
			  					[{
			  						"text" : "${lfn:message('fssc-expense:py.DaiFuKuan') }",
			  						"href" :  "/listDaiFuKuan",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_query"
			  					},{
			  						"text" : "${lfn:message('fssc-expense:py.YiFuKuan') }",
			  						"href" :  "/listYiFuKuan",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_query"
			  					}
			  					<kmss:ifModuleExist path="/fssc/cashier">
			  					,{
			  						"text" : "${lfn:message('fssc-expense:py.ChuNaGongZuoTai') }",
			  						"href" :  "/listChuNa",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_all"
			  					}
			  					</kmss:ifModuleExist>
			  					]
		 					</ui:source>
		 				</ui:varParam>
					</ui:combin>
				</ui:content>
				 <kmss:authShow roles="ROLE_FSSCEXPENSE_SEARCHLIST">
				<ui:content title="${ lfn:message('fssc-expense:py.TaiZhang') }">
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
		  					<ui:source type="Static">
			  					[{
			  						"text" : "${lfn:message('fssc-expense:py.BaoXiaoTaiZhang') }",
			  						"href" :  "/listBaoXiaoTaiZhang",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_query"
			  					}<%-- ,{
			  						"text" : "${lfn:message('fssc-expense:py.JinXiangTaiZhang') }",
			  						"href" :  "/listJinXiangTaiZhang",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_query"
			  					} --%>]
		 					</ui:source>
		 				</ui:varParam>
					</ui:combin>
				</ui:content>
				</kmss:authShow>
				<ui:content title="${ lfn:message('fssc-expense:py.QiTaCaoZuo') }">
				<ui:combin ref="menu.nav.simple">
					<ui:varParam name="source">
	  					<ui:source type="Static">
	  					[
	  					<fssc:checkVersion version="true">
	  					{
	  						"text" : "${lfn:message('fssc-expense:table.fsscExpenseShareMain') }",
	  						"href" :  "/listShare",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_com_my_drafted"
	  					},{
	  						"text" : "${lfn:message('fssc-expense:table.fsscExpenseBalance') }",
	  						"href" :  "/listBalance",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_com_my_drafted"
	  					},
	  					</fssc:checkVersion>
	  					<%-- 关闭回收站功能时，模块首页不显示“回收站” --%>
		  					<% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.fssc.expense.model.FsscExpenseMain")
		  							||com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.fssc.expense.model.FsscExpenseBalance")
		  							||com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.fssc.expense.model.FsscExpenseShareMain")) { %>
		  					{
		  						"text" : "${ lfn:message('sys-recycle:module.sys.recycle') }",
		  						"href" :  "/recover",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_com_recycle"
		  					}
		  					<% } %>
							<% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.fssc.expense.model.FsscExpenseMain")
									||com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.fssc.expense.model.FsscExpenseBalance")
									||com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.fssc.expense.model.FsscExpenseShareMain")) { %>
	  					<kmss:authShow roles="ROLE_FSSCEXPENSE_EXPENSE_SETTING;ROLE_FSSCEXPENSE_BALANCE_SETTING;ROLE_FSSCEXPENSE_SHARE_SETTING">
	  					,
	  					</kmss:authShow>
							<% } %>
	  					<kmss:authShow roles="ROLE_FSSCEXPENSE_EXPENSE_SETTING;ROLE_FSSCEXPENSE_BALANCE_SETTING;ROLE_FSSCEXPENSE_SHARE_SETTING">
	  					{
	  						"text" : "${lfn:message('list.manager') }",
	  						"href" :  "javascript:LUI.pageOpen('${LUI_ContextPath }/sys/profile/moduleindex.jsp?nav=/fssc/expense/tree.jsp','_rIframe');",
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
    <ui:tabpanel id="fsscExpensePanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
		<ui:content id="fsscExpenseContent" title="" cfg-route="{path:'/listCreate'}">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('fssc-expense:fsscExpenseMain.docSubject')}" />
                <list:cri-auto modelName="com.landray.kmss.fssc.expense.model.FsscExpenseMain" property="docNumber" expand="true" />
                <list:cri-auto modelName="com.landray.kmss.fssc.expense.model.FsscExpenseMain" property="docStatus" />
                <list:cri-criterion expand="false" title="${lfn:message('fssc-expense:fsscExpenseMain.fdPaymentStatus')}" key="fdPaymentStatus" multi="false">
                    <list:box-select>
                         <list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-enable="false">
							<ui:source type="Static">
								[{text:'${ lfn:message('fssc-expense:enums.payment_type.1')}', value:'2'},
								{text:'${ lfn:message('fssc-expense:enums.payment_type.2')}',value:'2'}]
							</ui:source>
						</list:item-select>
                    </list:box-select>
                </list:cri-criterion>
                <list:cri-criterion title="${lfn:message('fssc-expense:fsscExpenseMain.fdCompany')}" key="fdCompanyName">
                    <list:box-select>
                        <list:item-select type="lui/criteria/criterion_input!TextInput">
                            <ui:source type="Static">
                                [{placeholder:'${lfn:message('fssc-expense:fsscExpenseMain.fdCompany')}'}]
                            </ui:source>
                        </list:item-select>
                    </list:box-select>
                </list:cri-criterion>
                <list:cri-criterion title="${lfn:message('fssc-expense:fsscExpenseMain.fdProject')}" key="fdProjectName">
                    <list:box-select>
                        <list:item-select type="lui/criteria/criterion_input!TextInput">
                            <ui:source type="Static">
                                [{placeholder:'${lfn:message('fssc-expense:fsscExpenseMain.fdProject')}'}]
                            </ui:source>
                        </list:item-select>
                    </list:box-select>
                </list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.fssc.expense.model.FsscExpenseMain" property="fdClaimant" />
                <list:cri-auto modelName="com.landray.kmss.fssc.expense.model.FsscExpenseMain" property="fdClaimantDept" />
                <!-- 分类模板 -->
                <list:cri-ref ref="criterion.sys.simpleCategory" key="docTemplate" multi="false" title="${lfn:message('fssc-expense:fsscExpenseMain.docTemplate')}" expand="false">
                    <list:varParams modelName="com.landray.kmss.fssc.expense.model.FsscExpenseCategory" />
                </list:cri-ref>
                <%--当前处理人--%>
		        <list:cri-ref ref="criterion.sys.postperson.availableAll"  cfg-if="param['docStatus']!='00' && param['docStatus']!='32'" key="fdCurrentHandler" multi="false" title="${lfn:message('fssc-expense:lbpm.currentHandler')}" />
		        <%--已处理人--%>
		        <list:cri-ref ref="criterion.sys.person"  key="fdAlreadyHandler" multi="false" title="${lfn:message('fssc-expense:lbpm.approvedHandler')}" />
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fsscExpenseMain.docNumber" text="${lfn:message('fssc-expense:fsscExpenseMain.docNumber')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="6">
                        	<%-- <kmss:auth requestURL="/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=batchConfirmPayment">
                                <ui:button text="${lfn:message('fssc-expense:button.batchConfirmPayment')}" onclick="batchConfirmPayment()" order="2" />
                            </kmss:auth> --%>
							<%-- <kmss:auth requestURL="/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=downloadBankFile">
                                <ui:button text="${lfn:message('fssc-expense:button.batchExportBankFile')}" onclick="batchExportBank()" order="2" />
                            </kmss:auth> --%>
                            <kmss:auth requestURL="/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAllByAuth()" order="4" id="btnDelete" />
							<ui:button text="${lfn:message('fssc-expense:button.printall')}" onclick="printall()" order="4" />
							<ui:button text="${lfn:message('button.export')}" id="export" order="5" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseMain')" />
							<%-- 修改权限 --%>
							<c:import url="/sys/right/import/doc_right_change_button.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseMain" />
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
                    {url:appendQueryParameter('/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="docSubject;docNumber;fdTotalApprovedMoney;docStatus;docCreator.name;docCreateTime;lbpm_main_listcolumn_node;lbpm_main_listcolumn_handler" /></list:colTable>
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
                modelName: 'com.landray.kmss.fssc.expense.model.FsscExpenseMain',
                templateName: 'com.landray.kmss.fssc.expense.model.FsscExpenseCategory',
                basePath: '/fssc/expense/fssc_expense_main/fsscExpenseMain.do',
                canDelete: '${canDelete}',
                mode: 'main_scategory',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);
            seajs.use(['lui/framework/module','lui/dialog','lui/topic'],function(Module,dialog,topic){
				Module.install('fsscExpense',{
					//模块变量
					$var : {},
					//模块多语言
					$lang : {
						myCreate : "${ lfn:message('list.create') }",
						myApproval : "${ lfn:message('list.approval') }",
						myApproved : "${ lfn:message('list.approved') }",
						notClose : "${ lfn:message('fs-fee:fsFeeMain.not.close') }",
						allFlow : "${ lfn:message('list.alldoc') }",
						DaiFuKuan : "${ lfn:message('fssc-expense:py.DaiFuKuan') }",
						YiFuKuan : "${ lfn:message('fssc-expense:py.YiFuKuan') }"
					},
					//搜索标识符
					$search : ''
				});
				window.batchExportBank = function(){
					var ids = [];
					$("[name=List_Selected]:checked").each(function(){
						ids.push(this.value);
					})
					if(ids.length==0){
						dialog.alert("${lfn:message('page.noSelect')}")
						return;
					}
					var dia = dialog.loading();
                	$.ajax({
                		url:'${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=checkDownloadBank',
                		data:{'ids':ids.join(';')},
                		dataType:'json',
                		async:false,
                		success:function(rtn){
                			dia.hide();
                			if(rtn.result=='failure'){
                				dialog.alert(rtn.message);
                			}else{
                				Com_OpenWindow('${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=downloadBankFile&ids='+ids.join(";"),'_self');
                			}
                		}
                	});
				}
				window.batchConfirmPayment = function(){
					var ids = [];
					$("[name=List_Selected]:checked").each(function(){
						ids.push(this.value);
					})
					if(ids.length==0){
						dialog.alert("${lfn:message('page.noSelect')}")
						return;
					}
					dialog.confirm("${lfn:message('fssc-expense:tips.batchConfirmPayment')}",function(rtn){
						if(rtn){
							var dia = dialog.loading();
							$.ajax({
		                		url:'${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=batchConfirmPayment',
		                		data:{'ids':ids.join(';'),'type':'expenseList'},
		                		dataType:'json',
		                		async:false,
		                		success:function(rtn){
		                			dia.hide();
		                			if(rtn.result == 'success'){
		                    			dialog.success();
		                    			topic.publish('list.refresh');
		                			}else{
		                				dialog.alert(rtn.message);
		                			}
		                		}
		                	});
						}
					});
				}
				
				//批量打印
	       		window.printall = function(){
	   				if($("input[name='List_Selected']:checked").length==0){
   		                seajs.use(['lui/dialog'], function(dialog) {
   		                    dialog.alert('<bean:message key="page.noSelect"/>');
   		                });
   		                return;
	              	}
	   				if($("input[name='List_Selected']:checked").length>50){
   		                seajs.use(['lui/dialog'], function(dialog) {
   		                	dialog.alert('<bean:message bundle="fssc-expense" key="page.select.max50"/>');
   		                });
   		                return;
	              	}
	                var idArray = new Array();
	                $("input[name='List_Selected']:checked").each(function(){
	                    idArray.push($(this).val());
	                });
	                var url='${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=printall&ids='+idArray.join(",");
	                window.open(url);
	       		};
			});
        </script>
        <script type="text/javascript" src="${LUI_ContextPath}/fssc/expense/resource/js/index.js"></script>
    </template:replace>
</template:include>
