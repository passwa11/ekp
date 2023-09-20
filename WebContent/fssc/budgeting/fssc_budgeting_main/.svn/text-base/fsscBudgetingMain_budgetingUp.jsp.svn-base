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
            	childAmountJson:'${childAmountJson}',
            	selfAmountJson:'${selfAmountJson}',
            };
            var messageInfo = {

            };
            Com_IncludeFile("common.js|data.js");
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", "js", true);
        </script>
    </template:replace>
    <template:replace name="title">
        <c:out value="${fsscBudgetingMainForm.fdYear} - " />
        <c:out value="${ lfn:message('fssc-budgeting:table.fsscBudgetingMain') }" />
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
                basePath: '/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do',
                customOpts: {

                    ____fork__: 0
                }
            };

            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

            <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


        </ui:toolbar>
    </template:replace>
    <template:replace name="content">
         <table class="tb_normal" width="100%">
             <tr>
                 <td class="td_normal_title" width="15%">
                     ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdYear')}
                 </td>
                 <td class="td_normal_title" width="45%">
                     ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdOrgId')}
                 </td>
                 <td class="td_normal_title" width="20%">
                     ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdOrgType')}
                 </td>
                 <td class="td_normal_title" width="20%">
                     ${lfn:message('fssc-budgeting:fsscBudgetingMain.fdTotalMoney')}
                 </td>
             </tr>
             <c:forEach items="${tempList}" var="tempForm">
             	<tr>
             		<td>
             			${tempForm.fdYear}
             		</td>
             		<td>
             			${tempForm.fdName}
             		</td>
             		<td>
             			${tempForm.fdOrgType}
             		</td>
             		<td>
             			<kmss:showNumber value="${tempForm.fdTotal}" pattern="###,##0.00"></kmss:showNumber> 
             		</td>
             	</tr>
             </c:forEach>
         </table>
    </template:replace>

</template:include>
