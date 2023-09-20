<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple4list">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-cashier:module.fssc.cashier') }" />
    </template:replace>
    <template:replace name="nav">
        <ui:combin ref="menu.nav.create">
            <ui:varParam name="title" value="${ lfn:message('fssc-cashier:module.fssc.cashier') }" />
            <ui:varParam name="button">
                [ {"text": "","href": "javascript:void(0);","icon": "/fssc/cashier/fssc_cashier_payment/fsscCashierPayment.do"} ]
            </ui:varParam>
        </ui:combin>
        <div class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:if test="${param.j_iframe=='true'}">
                    <c:set var="j_iframe" value="?j_iframe=true" />
                </c:if>
                <ui:content title="${ lfn:message('fssc-cashier:module.fssc.fsscCashierPayment') }">
                    <ul class='lui_list_nav_list'>
                        <li><a href="${LUI_ContextPath}/fssc/cashier/fssc_cashier_payment/index.jsp${j_iframe}">${lfn:message('fssc-cashier:table.fsscCashierPayment')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/cashier/fssc_cashier_payment_detail/index.jsp${j_iframe}">${lfn:message('fssc-cashier:table.fsscCashierPaymentDetail')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/sys/profile/index.jsp#app/ekp/fssc/cashier" target="_blank">${ lfn:message('list.manager') }</a>
                        </li>
                    </ul>
                </ui:content>
            </ui:accordionpanel>
        </div>
    </template:replace>
    <template:replace name="content">
        <ui:tabpanel id="fsscCashierMainPanel" layout="sys.ui.tabpanel.list" cfg-router="true">
			<ui:content id="fsscCashierMainContent" title="${ lfn:message('fssc-cashier:table.fsscCashierPayment') }">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-auto modelName="com.landray.kmss.fssc.cashier.model.FsscCashierPayment" property="fdModelNumber" expand="true"/>
                <list:cri-criterion title="${ lfn:message('fssc-cashier:lbpm.my.doc') }" key="myflow"  expand="true">
					<list:box-select>
						<list:item-select  cfg-defaultValue="approval">
							<ui:source type="Static">
							    [{text:'${ lfn:message('list.approval')}', value:'approval'},
								{text:'${ lfn:message('list.approved')}',value:'approved'}]
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
                 <list:cri-auto modelName="com.landray.kmss.fssc.cashier.model.FsscCashierPayment" property="docCreateTime" expand="false"/>
                <list:cri-criterion title="${lfn:message('fssc-cashier:fsscCashierPayment.fdCompany')}" key="fdCompany.fdId"  expand="true"  multi="false">
				<list:box-select>
					<list:item-select id="company-id">
						<ui:source type="AjaxXml" >
							  {"url":"/sys/common/dataxml.jsp?s_bean=fsscCashierPaymentService&type=fdCompany&authCurrent=true"}
						</ui:source>
						<!-- 根据所选的公司，联动判断是否是公司财务 -->
							<ui:event event="selectedChanged" args="evt">
								var vals = evt.values;
								 if (vals.length > 0 && vals[0] != null) {
										var val = vals[0].value;
										var data = new KMSSData();
										var isStaff = data.AddBeanData("fsscCashierPaymentService&authCurrent=true&type=isStaff&fdCompanyId="+val).GetHashMapArray()[0]['isStaff'];
										if(isStaff=='true'){
											LUI('btnPass').setVisible(true);  //显示确认通过按钮
										}else{
											LUI('btnPass').setVisible(false);	//隐藏确认通过按钮
										}
									}
							</ui:event>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
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
                            <ui:button text="${lfn:message('fssc-cashier:button.confirm.pass')}" onclick="confirmPass()" id="btnPass" order="2" />
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
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/cashier/resource/js/", 'js', true);
        </script>
        <script>
            //确认通过
            function confirmPass(){
                seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
                    var ids = new Array();
                    $("input[name='List_Selected']:checked").each(function(){
                        ids.push($(this).val());
                    });

                    if(ids.length==0){
                        dialog.alert('<bean:message key="page.noSelect"/>');
                        return;
                    }
                    dialog.confirm("${ lfn:message('fssc-cashier:confirm') }${ lfn:message('fssc-cashier:button.confirm.pass') }?",function(value){
                        if(value==true){
                            var del_load = dialog.loading("${lfn:message('fssc-cashier:button.confirm.pass.in')}");
                            $.ajax({
                            	url:'${LUI_ContextPath}/fssc/cashier/fssc_cashier_payment/fsscCashierPayment.do?method=checkConfirm',
                            	data:$.param({"List_Selected":ids},true),
                            	dataType:'json',
                            	type:'POST',
                            	success:function(data){
                            		 $.post('${LUI_ContextPath}/fssc/cashier/fssc_cashier_payment/fsscCashierPayment.do?method=confirmPass',
                                             $.param({"ids":ids},true),function(data){
                                                 var errorStr = data.error;
                                                 if(console){
                                                     console.log(errorStr);
                                                     console.log(errorStr.length);
                                                     console.log((errorStr && errorStr.length > 0));
                                                 }
                                                 if(del_load!=null){
                                                     del_load.hide();
                                                     topic.publish("list.refresh");
                                                 }
                                                 if(errorStr && errorStr.length > 0){
                                                     dialog.alert(errorStr);
                                                 }else{
                                                     dialog.alert('<bean:message bundle="fssc-cashier" key="return.success"/>');
                                                     setTimeout(function(){window.location.reload();},1000);
                                                 }
                                             },'json');
                            	},
                            	error:function(req){
                            		if(req.responseJSON){
                            			var data = req.responseJSON;
                            			if(!data.status){
                            				dialog.failure('${lfn:message("fssc-cashier:tips.check.confirm.error")}');
                            			}
                            		}else{
                            			dialog.failure('操作失败');
                            		}
                            		del_load.hide();
                            	}
                            });
                        }
                    });
                });
            }

        </script>
        <input type="hidden" name="canPass" />
    </template:replace>
</template:include>
