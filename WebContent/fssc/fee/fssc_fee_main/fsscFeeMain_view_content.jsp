<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
    <template:replace name="head">
        <style type="text/css">
            
            			.lui_paragraph_title{
            				font-size: 15px;
            				color: #15a4fa;
            		    	padding: 15px 0px 5px 0px;
            			}
            			.lui_paragraph_title span{
            				display: inline-block;
            				margin: -2px 5px 0px 0px;
            			}
            			.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
            			    border: 0px;
            			    color: #868686
            			}
						.lui_dialog_content{
							background-color: inherit !important;
						}
        </style>
        <script type="text/javascript">
            var formInitData = {
            	docStatus:'${fsscFeeMainForm.docStatus}',
            	approveModel:'${param.approveType}',
            };
            var messageInfo = {

            };
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            Com_IncludeFile("controlView.js", "${LUI_ContextPath}/fssc/common/resource/js/", "js", true);
            Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/fee/resource/js/", 'js', true);
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/fee/fssc_fee_main/", 'js', true);
        </script>
        <link rel="stylesheet" href="${LUI_ContextPath }/fssc/common/resource/layui/css/layui.css"  media="all">
        <script src="${LUI_ContextPath }/fssc/common/resource/layui/layui.js" charset="utf-8"></script>
    </template:replace>
    <template:replace name="title">
        <c:out value="${fsscFeeMainForm.docSubject} - " />
        <c:out value="${ lfn:message('fssc-fee:table.fsscFeeMain') }" />
    </template:replace>
    <template:replace name="toolbar">
        <script>
            function deleteDoc(delUrl) {
                seajs.use(['lui/dialog'], function(dialog) {
                    dialog.confirm('${ lfn:message("page.comfirmDelete") }', function(isOk) {
                        if(isOk) {
                            Com_OpenWindow(delUrl, '_self');
                        }
                    });
                });
            }

            function openWindowViaDynamicForm(popurl, params, target) {
                var form = document.createElement('form');
                if(form) {
                    try {
                        target = !target ? '_blank' : target;
                        form.style = "display:none;";
                        form.method = 'post';
                        form.action = popurl;
                        form.target = target;
                        if(params) {
                            for(var key in params) {
                                var
                                v = params[key];
                                var vt = typeof
                                v;
                                var hdn = document.createElement('input');
                                hdn.type = 'hidden';
                                hdn.name = key;
                                if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                                    hdn.value =
                                    v +'';
                                } else {
                                    if($.isArray(
                                        v)) {
                                        hdn.value =
                                        v.join(';');
                                    } else {
                                        hdn.value = toString(
                                            v);
                                    }
                                }
                                form.appendChild(hdn);
                            }
                        }
                        document.body.appendChild(form);
                        form.submit();
                    } finally {
                        document.body.removeChild(form);
                    }
                }
            }

            function doCustomOpt(fdId, optCode) {
                if(!fdId || !optCode) {
                    return;
                }

                if(viewOption.customOpts && viewOption.customOpts[optCode]) {
                    var param = {
                        "List_Selected_Count": 1
                    };
                    var argsObject = viewOption.customOpts[optCode];
                    if(argsObject.popup == 'true') {
                        var popurl = viewOption.contextPath + argsObject.popupUrl + '&fdId=' + fdId;
                        for(var arg in argsObject) {
                            param[arg] = argsObject[arg];
                        }
                        openWindowViaDynamicForm(popurl, param, '_self');
                        return;
                    }
                    var optAction = viewOption.contextPath + viewOption.basePath + '?method=' + optCode + '&fdId=' + fdId;
                    Com_OpenWindow(optAction, '_self');
                }
            }
            window.doCustomOpt = doCustomOpt;
            var viewOption = {
                contextPath: '${LUI_ContextPath}',
                basePath: '/fssc/fee/fssc_fee_main/fsscFeeMain.do',
                customOpts: {

                    ____fork__: 0
                }
            };
            //打印
            function printDoc() {
                var url = '${LUI_ContextPath}/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=print&fdId=${param.fdId}';
                Com_OpenWindow(url);
            }
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            seajs.use(['lui/dialog'],function(dialog){
            	window.Fssc_FeeToExpense = function(){
            		var fdId='${fsscFeeMainForm.fdId}';
            		$.ajax({
            			type:'post',
						url: '${LUI_ContextPath}/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=checkToExpense',
						data:{fdId:fdId},
						dataType: 'json',
						async:true,
						success:function(rtn){
							 if(rtn.message=='connectMore'){
            					dialogSelect(false,'fssc_fee_to_expense',"fdExpenseId","fdExpenseName",FSSC_Connection,{docTemplateId:rtn.docTemplateId,connFlag:'connectMore'},null);
            				}else if(rtn.message=='noSelect'){
            					dialogSelect(false,'fssc_fee_to_expense',"fdExpenseId","fdExpenseName",FSSC_Connection,null,null);
            			  	}else if(rtn.message=='connectOne'){
            					Com_OpenWindow('${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=add&i.docTemplate='+rtn.docTemplateId+"&fdFeeMainId=${fsscFeeMainForm.fdId}")
            				}else{
            					dialog.alert(rtn.message);
            				}
            			},error:function(){
        	    		}
						
            		});
            	}
            	function FSSC_Connection(){
            		var fdExpenseId=$("input[name='fdExpenseId']").val();
            		Com_OpenWindow('${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=add&i.docTemplate='+fdExpenseId+"&fdFeeMainId=${fsscFeeMainForm.fdId}")
            	}
            	window.Fssc_CloseFee = function(){
            		dialog.confirm("${lfn:message('fssc-fee:tips.closeFee.confirm')}",function(res){
            			if(res){
            				$.post(
                       			'${LUI_ContextPath}/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=checkCloseFee',
                       			{
                       				fdId:'${fsscFeeMainForm.fdId}'
                       			},
                       			function(rtn){
                       				rtn = JSON.parse(rtn);
                       				if(rtn.message){
                       					dialog.alert(rtn.message);
                       				}else{
                       					var dia = dialog.loading();
                       					$.post(
                     							'${LUI_ContextPath}/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=closeFee',
                                     			{
                                     				fdId:'${fsscFeeMainForm.fdId}'
                                     			},
                                     			function(rtn){
                                     				dia.hide();
                                     				rtn = JSON.parse(rtn);
                                     				if(rtn.result=='success'){
                                     					dialog.success();
                                     					window.location.reload();
                                     				}else{
                                     					dialog.failure();
                                     				}
                                     			}
                       					);
                       				}
                       			}
                       		);
            			}
            		});
            	}
            })
        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<c:if test="${ fsscFeeMainForm.docStatus=='30'&&KMSS_Parameter_CurrentUserId==fsscFeeMainForm.docCreatorId && fsscFeeMainForm.fdIsClosed!='true'}">
                <kmss:auth requestURL="/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=toExpense&fdId=${param.fdId}">
                	<kmss:ifModuleExist path="/fssc/expense">
                		<ui:button text="${lfn:message('fssc-fee:py.toExpense')}" onclick="Fssc_FeeToExpense();" order="2" />
                	</kmss:ifModuleExist>
                </kmss:auth>
            </c:if>
            <kmss:auth requestURL="/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=closeFee&fdId=${param.fdId}">
                <ui:button text="${lfn:message('fssc-fee:py.closeFee')}" onclick="Fssc_CloseFee();" order="2" />
            </kmss:auth>
            <c:if test="${ fsscFeeMainForm.docStatus=='10' || fsscFeeMainForm.docStatus=='11' || fsscFeeMainForm.docStatus=='20' }">
                <!--edit-->
                <kmss:auth requestURL="/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscFeeMain.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
            </c:if>
            <kmss:auth requestURL="/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=print&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.print')}" onclick="printDoc()">
                </ui:button>
            </kmss:auth>
            <!--delete-->
            <kmss:auth requestURL="/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscFeeMain.do?method=delete&fdId=${param.fdId}');" order="4" />
            </kmss:auth>
            <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
            <ui:menu-item text="${ lfn:message('fssc-fee:table.fsscFeeMain') }" href="/fssc/fee/fssc_fee_main/" target="_self" />
             <ui:menu-item text="${docTemplateName }"  />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
    <!-- 流程状态标识 -->
	<c:import url="/eop/basedata/resource/jsp/fssc_banner.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="fsscFeeMainForm" />
		<c:param name="approveType" value="${param.approveType}" />
	</c:import>
<c:if test="${param.approveType ne 'right'}">
	<form action="${LUI_ContextPath }/fssc/fee/fssc_fee_main/fsscFeeMain.do" name="fsscFeeMainForm"  method="post">
</c:if> 
            <ui:tabpage expand="false" var-navwidth="90%" id="reviewTabPage" collapsed="true" >
            <c:if test="${param.approveType eq 'right'}">
            	<script>
					LUI.ready(function(){
						setTimeout(function(){
							var reviewTabPage = LUI("reviewTabPage");
							if(reviewTabPage!=null){
								reviewTabPage.element.find(".lui_tabpage_float_collapse").hide();
								reviewTabPage.element.find(".lui_tabpage_float_navs_mark").hide();
							}
						},100)
					})
				</script>
				</c:if>
              	 <div class='lui_form_title_frame'>
                    <div class='lui_form_subject'>
                        ${fsscFeeMainForm.docSubject}
                    </div>
                    <%--条形码--%>
		            <div id="barcodeTarget" style="float:right;margin-right:10px;margin-top: -20px;" ></div>
                </div>
                  <table class="tb_normal" width="100%">
                      <tr>
                      	<td class="td_normal_title" width="16.6%">
                              ${lfn:message('fssc-fee:fsscFeeMain.docSubject')}
                          </td>
                          <td colspan="5">
                              <div id="_xform_docSubject" _xform_type="address">
                              	${fsscFeeMainForm.docSubject }
                              </div>
                          </td>
                      </tr>
                  </table>
                  <c:if test="${param.approveType ne 'right'}">
                  <ui:content title="${lfn:message('fssc-fee:py.BiaoDanNeiRong')}" expand="true">
                      <c:if test="${fsscFeeMainForm.docUseXform == 'false'}">
                          <table class="tb_normal" width=100%>
                              <tr>
                                  <td colspan="2">
                                      <kmss:editor property="docXform" width="95%" />
                                  </td>
                              </tr>
                          </table>
                      </c:if>
                      <c:if test="${fsscFeeMainForm.docUseXform == 'true' || empty fsscFeeMainForm.docUseXform}">
                          <c:import url="/sys/xform/include/sysForm_view.jsp" charEncoding="UTF-8">
                              <c:param name="formName" value="fsscFeeMainForm" />
                              <c:param name="fdKey" value="fsscFeeMain" />
                              <c:param name="useTab" value="false" />
                          </c:import>
                      </c:if>
                  </ui:content>
                  <c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
                      <c:param name="formName" value="fsscFeeMainForm" />
                      <c:param name="fdKey" value="fsscFeeMain" />
                      <c:param name="isExpand" value="true" />
                      <c:param name="onClickSubmitButton" value="Com_Submit(document.fsscFeeMainForm,'update');" />
                  </c:import>
	                <%--传阅机制 --%>
	                <c:import url="/sys/circulation/import/sysCirculationMain_view.jsp" charEncoding="UTF-8">
	                    <c:param name="formName" value="fsscFeeMainForm" />
	                </c:import>
	                <%--权限 --%>
	                <c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
	                    <c:param name="formName" value="fsscFeeMainForm" />
	                    <c:param name="moduleModelName" value="com.landray.kmss.fssc.fee.model.FsscFeeMain" />
	                </c:import>
                  </c:if>
                  <c:if test="${fsscFeeMainForm.docStatus=='30' }">
                  <ui:content title="${lfn:message('fssc-fee:fsscFeeMain.useInfo')}" expand="false" id="used_content">
						<list:listview id="listview_used" channel="used">
							<ui:source type="AjaxJson" >
								{url:'/fssc/fee/fssc_fee_ledger/fsscFeeLedger.do?method=executeData&fdFeeId=${fsscFeeMainForm.fdId }&fdType=2;3'}
							</ui:source>
							<list:colTable rowHref="!{fdUrl}" layout="sys.ui.listview.listtable">
								<list:col-serial title="NO"></list:col-serial>
								<list:col-auto props="docSubject;docNumber;fdModelName;fdType;fdMoney" ></list:col-auto>
							</list:colTable>
							<list:paging layout="sys.ui.paging.simple" channel="used"></list:paging>
						</list:listview>
						<list:paging></list:paging>
				</ui:content>
				</c:if>
              </ui:tabpage>
              <c:if test="${param.approveType eq 'right'}">
                <ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'>
                    <ui:content title="${lfn:message('fssc-fee:py.BiaoDanNeiRong')}" expand="true">
                        <c:if test="${fsscFeeMainForm.docUseXform == 'true' || empty fsscFeeMainForm.docUseXform}">
                            <c:import url="/sys/xform/include/sysForm_view.jsp" charEncoding="UTF-8">
                                <c:param name="formName" value="fsscFeeMainForm" />
                                <c:param name="fdKey" value="fsscFeeMain" />
                                <c:param name="useTab" value="false" />
                            </c:import>
                        </c:if>
                    </ui:content>
                    <c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="fsscFeeMainForm" />
                        <c:param name="fdKey" value="fsscFeeMain" />
                        <c:param name="isExpand" value="true" />
                        <c:param name="approveType" value="right" />
                       <%-- <c:param name="needInitLbpm" value="true" />--%>
                    </c:import>
	                <%--传阅机制 --%>
	                <c:import url="/sys/circulation/import/sysCirculationMain_view.jsp" charEncoding="UTF-8">
	                    <c:param name="formName" value="fsscFeeMainForm" />
	                    <c:param name="order" value="10" />
	                </c:import>
	                <%--权限 --%>
	                <c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
	                    <c:param name="formName" value="fsscFeeMainForm" />
	                    <c:param name="moduleModelName" value="com.landray.kmss.fssc.fee.model.FsscFeeMain" />
	                </c:import>
                    <c:if test="${fsscFeeMainForm.docStatus=='30' }">
	                  <ui:content title="${lfn:message('fssc-fee:fsscFeeMain.useInfo')}" expand="true">
						<list:listview>
							<ui:source type="AjaxJson" >
								{url:'/fssc/fee/fssc_fee_ledger/fsscFeeLedger.do?method=executeData&fdFeeId=${fsscFeeMainForm.fdId }&fdType=2;3'}
							</ui:source>
							<list:colTable rowHref="!{fdUrl}" layout="sys.ui.listview.listtable">
								<list:col-serial title="NO"></list:col-serial>
								<list:col-auto props="docSubject;docNumber;fdModelName;fdType;fdMoney" ></list:col-auto>
							</list:colTable>
							<list:paging layout="sys.ui.paging.simple"></list:paging>
						</list:listview>
						<list:paging></list:paging>
					</ui:content>
					</c:if>
                    </ui:tabpanel>
                </c:if>
              <%-- 条形码公共页面 --%>
              	<c:import url="/eop/basedata/resource/jsp/barcode.jsp" charEncoding="UTF-8">
	        		<c:param name="docNumber">${fsscFeeMainForm.docNumber }</c:param>
	        	</c:import>
                <%--查看预算按钮--%>
                <kmss:ifModuleExist path="/fssc/budget">
                    <c:import url="/fssc/budget/resource/jsp/fsscBudgetBill_view.jsp" charEncoding="UTF-8">
                        <c:param name="fdModelId">${fsscFeeMainForm.fdId }</c:param>
                        <c:param name="docStatus">${fsscFeeMainForm.docStatus }</c:param>
                    </c:import>
                </kmss:ifModuleExist>
	        	<html:hidden property="method_GET" value="${fsscFeeMainForm.method_GET }"/>
	        	<html:hidden property="docTemplateId" value="${fsscFeeMainForm.docTemplateId }"/>
				<xform:dialog propertyId="fdExpenseId" propertyName="fdExpenseName" showStatus="noShow" >
				</xform:dialog>
	 <c:if test="${param.approveType ne 'right'}">
	  </form>
	 </c:if>
    </template:replace>
	<c:if test="${param.approveType eq 'right' }">
		<template:replace name="barRight">
			<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
				<c:if test="${fsscFeeMainForm.docStatus ne '00' and  fsscFeeMainForm.docStatus ne '30'}">
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscFeeMainForm" />
					<c:param name="fdKey" value="fsscFeeMain" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
					<c:param name="onClickSubmitButton" value="Com_Submit(document.fsscFeeMainForm,'update');" />
				</c:import>
				</c:if>
				<c:import url="/fssc/fee/fssc_fee_main/fsscFeeMain_baseInfo_right.jsp"></c:import>
				<!-- 关联配置 -->
				<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscFeeMainForm" />
					<c:param name="approveType" value="right" />
                    <c:param name="needTitle" value="true" />
				</c:import>
			</ui:tabpanel>
		</template:replace>
	</c:if>
	<c:if test="${param.approveType ne 'right'}">
		<template:replace name="nav">
			<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="fsscFeeMainForm" />
			</c:import>
		</template:replace>
	</c:if>