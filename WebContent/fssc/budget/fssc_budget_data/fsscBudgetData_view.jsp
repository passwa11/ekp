<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.view">
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
            		
        </style>
        <script type="text/javascript">
            var formInitData = {

            };
            var messageInfo = {

            };
        </script>
    </template:replace>
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-budget:table.fsscBudgetData') }" />
    </template:replace>
    <template:replace name="toolbar">
        <script>
            seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
                window.listExport = function(url, id){
                    var json = new Array();
                    var values;
                    var prefix = "";
                    if(id){
                        prefix = "#" + id + " ";
                    }
                    var ths = LUI.$(prefix+".lui_listview_columntable_listtable").find("th");
                    var thsValues=null;
                    var index=0;
                    LUI.$(prefix + "input[name='List_Selected']:checked").each(function(j){
                        var tds = LUI.$(this).parent().parent().find("td");
                        var data = new Array();
                        for (var i = 1; i < ths.length; i++) {
                            var input=LUI.$(ths[i]).find("input[type='checkbox'][name='List_Tongle']");
                            if(LUI.$(input).length==0){
                                if(tds[i].innerText=='' && LUI.$(tds[i]).children("img").length>0){
                                    if(LUI.$(tds[i]).children("img").eq(0).attr("title")){
                                        data.push([ths[i].innerText,LUI.$(tds[i]).children("img").eq(0).attr("title")]);
                                    }else{
                                        data.push([ths[i].innerText,encodeHTML(tds[i].innerText)]);
                                    }
                                }else{
                                    data.push([ths[i].innerText,encodeHTML(tds[i].innerText)]);
                                }
                            }
                        }
                        json.push(["json"+j,data]);
                        index=j;
                    });
                    if(json.length!=0){
                        for (var i = 1; i < ths.length; i++) {
                            if(thsValues==null){
                                thsValues=ths[i].innerText;
                            }else{
                                thsValues=thsValues+","+ths[i].innerText;
                            }
                        }
                        openWindowWithPost(url,"post","json",encodeURI(LUI.stringify(json)),"ths",encodeURI(thsValues));
                        if(window.export_load!=null){
                            window.export_load.hide();
                        }
                    }else{
                        if(window.export_load!=null){
                            window.export_load.hide();
                        }
                        dialog.alert('<bean:message key="page.noSelect"/>');
                        return;
                    }
                };
                 encodeHTML=function(str) {
                    return str.replace(/&/g,"&amp;")
                        .replace(/</g,"&lt;")
                        .replace(/>/g,"&gt;")
                        .replace(/\"/g,"&quot;")
                        .replace(/¹/g, "&sup1;")
                        .replace(/²/g, "&sup2;")
                        .replace(/³/g, "&sup3;");
                };
                window.openWindowWithPost = function(url,name,key,value,thkey,thvalue){
                    var newWindow = window.open(name);
                    if (!newWindow)
                        return false;
                    var html = "";
                    html += "<html><head></head><body><form id='formid' method='post' action='" + url + "'>";
                    if (key && value)
                    {
                        html += "<input id='"+key+"' type='hidden' name='" + key + "' value='" +value+ "'/>";

                    }
                    if(thkey && thvalue){
                        html += "<input id='"+thkey+"' type='hidden' name='" + thkey + "' value='" +thvalue+ "'/>";
                    }
                    html += "</form><script type='text/javascript'>document.getElementById('formid').submit();";
                    html += "<\/script></body></html>".toString().replace(/^.+?\*|\\(?=\/)|\*.+?$/gi, "");
                    newWindow.document.write(html);
                    return newWindow;
                };
            });

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
            function _rowClick(fdModelId,fdModelName){
				if(fdModelName.indexOf("FsscBudgetAdjustMain")!=-1){
					 window.open("${KMSS_Parameter_ContextPath}fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=view&fdId="+fdModelId,"_self");	
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
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.fssc.budget.model.FsscBudgetData',
                templateName: '',
                basePath: '/fssc/budget/fssc_budget_data/fsscBudgetData.do',
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

            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js|data.js");
            Com_IncludeFile("fsscBudgetData.js", "${LUI_ContextPath}/fssc/budget/fssc_budget_data/", 'js', true);
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/budget/fssc_budget_data/", 'js', true);
            Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
        	<kmss:authShow roles="ROLE_FSSCBUDGET_ADJUST_AUTH">
                <ui:button text="${lfn:message('sys-right:right.button.changeRight.view')}" onclick="changeRightCheckSelect('${lfn:escapeHtml(param.fdId)}');" order="1"></ui:button>
            </kmss:authShow>
        	<kmss:auth requestURL="/fssc/budget/fssc_budget_data/fsscBudgetData.do?method=adjustBudgetData">
        		<ui:button text="${lfn:message('fssc-budget:py.YuSuanDiaoZheng')}" id="btnAdjust" onclick="adjustBudgetData('${lfn:escapeHtml(param.fdId)}')" order="1" />
        	</kmss:auth>
        	<kmss:authShow roles="ROLE_FSSCBUDGET_RESTART_PAUSE">
	        	<ui:button text="${lfn:message('fssc-budget:fsscBudget.button.budget.stop')}" id="btnStop" onclick="updateBudgetStatus('stop')" order="2" />
	            <ui:button text="${lfn:message('fssc-budget:fsscBudget.button.budget.restart')}" id="btnRestart" onclick="updateBudgetStatus('restart')" order="3" />
            </kmss:authShow>
            <kmss:authShow roles="ROLE_FSSCBUDGET_CLOSE">
            	<ui:button text="${lfn:message('fssc-budget:fsscBudget.button.budget.close')}" id="btnClose" onclick="updateBudgetStatus('close')" order="4" />
          	</kmss:authShow>
            <ui:button text="${lfn:message('button.close')}" order="4" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
            <ui:menu-item text="${ lfn:message('fssc-budget:table.fsscBudgetData') }" href="/fssc/budget/fssc_budget_data/" target="_self" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">

        <ui:tabpage expand="false" var-navwidth="90%">
            <ui:content title="${ lfn:message('fssc-budget:py.JiBenXinXi') }" expand="true">
                <table class="tb_normal" width="100%">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdYear')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdYear" _xform_type="text">
                               	<kmss:showPeriod property="fdYear" value="${fsscBudgetDataForm.fdYear}"></kmss:showPeriod>
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdPeriodType')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdPeriodType" _xform_type="text">
                            	<c:if test="${not empty fsscBudgetDataForm.fdPeriod}">
	                                <c:if test="${fsscBudgetDataForm.fdPeriod<9}">
						        		<c:out value="${fn:substring(fsscBudgetDataForm.fdPeriod,1,2)+1}"></c:out>
						        	</c:if>
						        	<c:if test="${fsscBudgetDataForm.fdPeriod >8}">
						        		<c:out value="${fsscBudgetDataForm.fdPeriod+1}"></c:out>
						        	</c:if>
					        	</c:if>
        						<sunbor:enumsShow enumsType="fssc_budget_period_type_name" value="${fsscBudgetDataForm.fdPeriodType}"></sunbor:enumsShow>
                            </div>
                        </td>
                    </tr>
                    <tr>
                      <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdBudgetScheme')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdBudgetSchemeId" _xform_type="radio">
                            	${fsscBudgetDataForm.fdBudgetSchemeName}
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdCompanyGroup')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdCompanyGroupId" _xform_type="dialog">
                                <xform:dialog propertyId="fdCompanyGroupId" propertyName="fdCompanyGroupName" showStatus="view" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_company_group_fdGroup','fdCompanyGroupId','fdCompanyGroupName');
                                </xform:dialog>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdCompany')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdCompanyId" _xform_type="dialog">
                                <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="view" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
                                </xform:dialog>
                            </div>
                        </td>
                        <%--20220828隐藏成本中心组
                         <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdCostCenterGroup')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdCostCenterGroupId" _xform_type="dialog">
                                <xform:dialog propertyId="fdCostCenterGroupId" propertyName="fdCostCenterGroupName" showStatus="view" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_cost_center_fdParent','fdCostCenterGroupId','fdCostCenterGroupName');
                                </xform:dialog>
                            </div>
                        </td> --%>
                        <td class="td_normal_title" width="15%">
                            	成本中心所属组
                        </td>
                        <td width="35%">
                            <div id="_xform_fdCostCenterParentName" _xform_type="dialog">
                            ${fsscBudgetDataForm.fdCostCenterParentName }
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdCostCenter')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdCostCenterId" _xform_type="dialog">
                                <xform:dialog propertyId="fdCostCenterId" propertyName="fdCostCenterName" showStatus="view" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdCostCenterId','fdCostCenterName');
                                </xform:dialog>
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdDept')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdDeptId" _xform_type="dialog">
                                <xform:address propertyName="fdDeptName" propertyId="fdDeptId"></xform:address>
                            </div>
                        </td>
                    </tr>
                    <tr>
                    	<td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdBudgetItem')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdBudgetItemId" _xform_type="dialog">
                                <xform:dialog propertyId="fdBudgetItemId" propertyName="fdBudgetItemName" showStatus="view" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_budget_item_com_fdBudgetItem','fdBudgetItemId','fdBudgetItemName');
                                </xform:dialog>
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdProject')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdProjectId" _xform_type="dialog">
                                <xform:dialog propertyId="fdProjectId" propertyName="fdProjectName" showStatus="view" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_project_project','fdProjectId','fdProjectName');
                                </xform:dialog>
                            </div>
                        </td>
                    </tr>
                    <tr>
                    	<td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdInnerOrder')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdInnerOrderId" _xform_type="dialog">
                                <xform:dialog propertyId="fdInnerOrderId" propertyName="fdInnerOrderName" showStatus="view" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_inner_order_fdInnerOrder','fdInnerOrderId','fdInnerOrderName');
                                </xform:dialog>
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdWbs')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdWbsId" _xform_type="dialog">
                                <xform:dialog propertyId="fdWbsId" propertyName="fdWbsName" showStatus="view" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_wbs_fdWbs','fdWbsId','fdWbsName');
                                </xform:dialog>
                            </div>
                        </td>
                    </tr>
                    <tr>
                    	<td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdPerson')}
                        </td>
                        <td width="35%" colspan="3">
                            <div id="_xform_fdPersonId" _xform_type="address">
                                <xform:address propertyId="fdPersonId" propertyName="fdPersonName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdCurrency')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdCurrencyId" _xform_type="dialog">
                                <xform:dialog propertyId="fdCurrencyId" propertyName="fdCurrencyName" showStatus="view" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_currency_fdCurrency','fdCurrencyId','fdCurrencyName');
                                </xform:dialog>
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdMoney')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdMoney" _xform_type="text">
                            	
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdAdjustMoney')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdAdjustMoney" _xform_type="text">
                                
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdTotalMoney')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdTotalMoney" _xform_type="text">
                                
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdRollMoney')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdRollMoney" _xform_type="text">
                                
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdAlreadyUsedMoney')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdAlreadyUsedMoney" _xform_type="text">
                                
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdOccupyMoney')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdOccupyMoney" _xform_type="text">
                                
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdCanUseMoney')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdCanUseMoney" _xform_type="text">
                                
                            </div>
                        </td>
                    </tr>
                    <tr>
                    	<td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdElasticPercent')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdElasticPercent" _xform_type="text">
                                <c:if test="${empty  fsscBudgetDataForm.fdElasticPercent}">
					        		0
					        	</c:if>
					        	<c:if test="${not empty  fsscBudgetDataForm.fdElasticPercent}">
					        		<c:out value="${fsscBudgetDataForm.fdElasticPercent}" />
					        	</c:if>
					        	%
                            </div>
                        </td>
                    	<td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdApply')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdApply" _xform_type="text">
                                <sunbor:enumsShow enumsType="fssc_budget_apply" value="${fsscBudgetDataForm.fdApply}"></sunbor:enumsShow>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdBudgetStatus')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdBudgetStatus" _xform_type="text">
                            	<sunbor:enumsShow enumsType="fssc_budget_status" value="${fsscBudgetDataForm.fdBudgetStatus}"></sunbor:enumsShow>
                            </div>
                            <xform:text property="fdBudgetStatus" value="${fsscBudgetDataForm.fdBudgetStatus}" showStatus="noShow"></xform:text>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetData.fdRule')}
                        </td>
                        <td width="35%">
                            <div id="_xform_fdRule" _xform_type="text">
                                <sunbor:enumsShow enumsType="fssc_budget_rule" value="${fsscBudgetDataForm.fdRule}"></sunbor:enumsShow>
                            </div>
                        </td>
                    </tr>
                </table>
            </ui:content>
            <ui:content title="${lfn:message('fssc-budget:table.fsscBudgetAdjustLog')}" expand="false" id="adjust_content">
					<div>
                        <div style="float:right">
                            <ui:button text="${lfn:message('button.export')}"  onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseMain','listview_adjust')" />
                        </div>
						<list:listview id="listview_adjust" channel="adjust">
							<ui:source type="AjaxJson">
								{url:'/fssc/budget/fssc_budget_adjust_log/fsscBudgetAdjustLog.do?method=data&fdBudgetId=${fsscBudgetDataForm.fdId }'}
							</ui:source>
							<list:colTable name="columntable" layout="sys.ui.listview.listtable" onRowClick="_rowClick('!{fdModelId}','!{fdModelName}');">
                                <list:col-checkbox />
								<list:col-serial title="NO"></list:col-serial>
								<list:col-auto props="fdAmount;docCreator.name;docCreateTime" ></list:col-auto>
							</list:colTable>
							<list:paging layout="sys.ui.paging.simple" channel="adjust"></list:paging>
						</list:listview>
						<list:paging></list:paging>
					</div>
				</ui:content>
           <ui:content title="${lfn:message('fssc-budget:fsscBudgetData.occupy.detail')}" expand="false" id="occu_content">
					<div>
						<list:listview id="listview_occu" channel="occu">
							<ui:source type="AjaxJson" >
								{url:'/fssc/budget/fssc_budget_execute/fsscBudgetExecute.do?method=executeData&fdType=2&fdBudgetId=${fsscBudgetDataForm.fdId }'}
							</ui:source>
							<list:colTable rowHref="!{fdUrl}" layout="sys.ui.listview.listtable">
								<list:col-serial title="NO"></list:col-serial>
								<list:col-auto props="fdModel.docSubject;fdModel.docNumber;fdModel.fdName;docCreateName;realUser;fdMoney" ></list:col-auto>
							</list:colTable>
							<list:paging layout="sys.ui.paging.simple" channel="occu"></list:paging>
						</list:listview>
						<list:paging></list:paging>
					</div>
				</ui:content>
            <ui:content title="${lfn:message('fssc-budget:fsscBudgetData.used.detail')}" expand="false" id="used_content">
					<div>
                        <div style="float:right">
                            <ui:button text="${lfn:message('button.export')}"  onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseMain','listview_used')" />
                        </div>
						<list:listview id="listview_used" channel="used">
							<ui:source type="AjaxJson" >
								{url:'/fssc/budget/fssc_budget_execute/fsscBudgetExecute.do?method=executeData&fdType=3&fdBudgetId=${fsscBudgetDataForm.fdId }'}
							</ui:source>
							<list:colTable rowHref="!{fdUrl}" layout="sys.ui.listview.listtable">
                                <list:col-checkbox />
								<list:col-serial title="NO"></list:col-serial>
								<list:col-auto props="fdModel.docSubject;fdModel.docNumber;fdModel.fdName;docCreateName;realUser;realDept;fdMoney" ></list:col-auto>
							</list:colTable>
							<list:paging layout="sys.ui.paging.simple" channel="used"></list:paging>
						</list:listview>
						<list:paging></list:paging>
					</div>
				</ui:content>
				<ui:content title="${lfn:message('sys-right:right.moduleName')}">
                    <%--权限机制 --%>
                    <c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="fsscBudgetDataForm" />
                        <c:param name="moduleModelName" value="com.landray.kmss.fssc.budget.model.FsscBudgetData" />
                    </c:import>
                </ui:content>
        </ui:tabpage>

    </template:replace>

</template:include>
