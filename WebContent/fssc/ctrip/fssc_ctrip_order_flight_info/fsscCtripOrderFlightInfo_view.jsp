<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.fssc.ctrip.util.FsscCtripUtil" %>
    
        <%
            pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
    
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

                    'fdFlightEntity': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderFlightEndtity"))}',
                    'fdSegmentPrintInfo': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderFlightSegment"))}',
                    'FlightChangeInfo': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderFlightChange"))}',
                    'fdRefundInfo': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderFlightRefundInfo"))}',
                    'fdPassenger': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderFlightPassenger"))}'
                };
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>
        <template:replace name="title">
            <c:out value="${fsscCtripOrderFlightInfoForm.fdName} - " />
            <c:out value="${ lfn:message('fssc-ctrip:table.fsscCtripOrderFlightInfo') }" />
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
                    basePath: '/fssc/ctrip/fssc_ctrip_order_flight_info/fsscCtripOrderFlightInfo.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--edit-->
                <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_order_flight_info/fsscCtripOrderFlightInfo.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscCtripOrderFlightInfo.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_order_flight_info/fsscCtripOrderFlightInfo.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscCtripOrderFlightInfo.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('fssc-ctrip:table.fsscCtripOrderFlightInfo') }" href="/fssc/ctrip/fssc_ctrip_order_flight_info/" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('fssc-ctrip:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('fssc-ctrip:table.fsscCtripOrderFlightInfo')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdUid')}
                            </td>
                            <td width="16.6%">
                                <%-- 携程卡号--%>
                                <div id="_xform_fdUid" _xform_type="text">
                                    <xform:text property="fdUid" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdRank')}
                            </td>
                            <td width="16.6%">
                                <%-- 职称--%>
                                <div id="_xform_fdRank" _xform_type="text">
                                    <xform:text property="fdRank" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdConfirmType')}
                            </td>
                            <td width="16.6%">
                                <%-- 一次授权方式--%>
                                <div id="_xform_fdConfirmType" _xform_type="text">
                                    <xform:text property="fdConfirmType" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdConfirmTypeTwo')}
                            </td>
                            <td width="16.6%">
                                <%-- 二次授权方式--%>
                                <div id="_xform_fdConfirmTypeTwo" _xform_type="text">
                                    <xform:text property="fdConfirmTypeTwo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdConfirmPerson')}
                            </td>
                            <td width="16.6%">
                                <%-- 一次授权人--%>
                                <div id="_xform_fdConfirmPerson" _xform_type="text">
                                    <xform:text property="fdConfirmPerson" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdConfirmPersonTwo')}
                            </td>
                            <td width="16.6%">
                                <%-- 二次授权人--%>
                                <div id="_xform_fdConfirmPersonTwo" _xform_type="text">
                                    <xform:text property="fdConfirmPersonTwo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdConfirmPersonCc')}
                            </td>
                            <td width="16.6%">
                                <%-- 抄送一次授权人--%>
                                <div id="_xform_fdConfirmPersonCc" _xform_type="text">
                                    <xform:text property="fdConfirmPersonCc" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdConfirmPersonCcTwo')}
                            </td>
                            <td width="16.6%">
                                <%-- 抄送二次授权人--%>
                                <div id="_xform_fdConfirmPersonCcTwo" _xform_type="text">
                                    <xform:text property="fdConfirmPersonCcTwo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdConfirmPersonName')}
                            </td>
                            <td width="16.6%">
                                <%-- 一次授权人姓名--%>
                                <div id="_xform_fdConfirmPersonName" _xform_type="text">
                                    <xform:text property="fdConfirmPersonName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdConfirmPersonNameTwo')}
                            </td>
                            <td width="16.6%">
                                <%-- 二次授权人姓名--%>
                                <div id="_xform_fdConfirmPersonNameTwo" _xform_type="text">
                                    <xform:text property="fdConfirmPersonNameTwo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdConfirmPersonCcName')}
                            </td>
                            <td width="16.6%">
                                <%-- 抄送一次授权人姓名--%>
                                <div id="_xform_fdConfirmPersonCcName" _xform_type="text">
                                    <xform:text property="fdConfirmPersonCcName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdConfirmPersonCcNameTwo')}
                            </td>
                            <td width="16.6%">
                                <%-- 抄送二次授权人姓名--%>
                                <div id="_xform_fdConfirmPersonCcNameTwo" _xform_type="text">
                                    <xform:text property="fdConfirmPersonCcNameTwo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdCostCenterOne')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心一--%>
                                <div id="_xform_fdCostCenterOne" _xform_type="text">
                                    <xform:text property="fdCostCenterOne" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdCostCenterTwo')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心二--%>
                                <div id="_xform_fdCostCenterTwo" _xform_type="text">
                                    <xform:text property="fdCostCenterTwo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdCostCenterThree')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心三--%>
                                <div id="_xform_fdCostCenterThree" _xform_type="text">
                                    <xform:text property="fdCostCenterThree" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdCostCenterFour')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心四--%>
                                <div id="_xform_fdCostCenterFour" _xform_type="text">
                                    <xform:text property="fdCostCenterFour" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdCostCenterFive')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心五--%>
                                <div id="_xform_fdCostCenterFive" _xform_type="text">
                                    <xform:text property="fdCostCenterFive" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdCostCenterSix')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心六--%>
                                <div id="_xform_fdCostCenterSix" _xform_type="text">
                                    <xform:text property="fdCostCenterSix" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdJourneyReason')}
                            </td>
                            <td width="16.6%">
                                <%-- 出行目的--%>
                                <div id="_xform_fdJourneyReason" _xform_type="text">
                                    <xform:text property="fdJourneyReason" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdProject')}
                            </td>
                            <td width="16.6%">
                                <%-- 项目号--%>
                                <div id="_xform_fdProject" _xform_type="text">
                                    <xform:text property="fdProject" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdDefineFlag')}
                            </td>
                            <td width="16.6%">
                                <%-- 自定义字段一--%>
                                <div id="_xform_fdDefineFlag" _xform_type="text">
                                    <xform:text property="fdDefineFlag" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdDefineFlagTwo')}
                            </td>
                            <td width="16.6%">
                                <%-- 自定义字段二--%>
                                <div id="_xform_fdDefineFlagTwo" _xform_type="text">
                                    <xform:text property="fdDefineFlagTwo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdEmployeeId')}
                            </td>
                            <td width="16.6%">
                                <%-- 员工编号--%>
                                <div id="_xform_fdEmployeeId" _xform_type="text">
                                    <xform:text property="fdEmployeeId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdWorkCity')}
                            </td>
                            <td width="16.6%">
                                <%-- 工作城市--%>
                                <div id="_xform_fdWorkCity" _xform_type="text">
                                    <xform:text property="fdWorkCity" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdDeptOne')}
                            </td>
                            <td width="16.6%">
                                <%-- 部门一--%>
                                <div id="_xform_fdDeptOne" _xform_type="text">
                                    <xform:text property="fdDeptOne" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdDeptTwo')}
                            </td>
                            <td width="16.6%">
                                <%-- 部门2--%>
                                <div id="_xform_fdDeptTwo" _xform_type="text">
                                    <xform:text property="fdDeptTwo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdDeptThree')}
                            </td>
                            <td width="16.6%">
                                <%-- 部门3--%>
                                <div id="_xform_fdDeptThree" _xform_type="text">
                                    <xform:text property="fdDeptThree" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdDeptFour')}
                            </td>
                            <td width="16.6%">
                                <%-- 部门4--%>
                                <div id="_xform_fdDeptFour" _xform_type="text">
                                    <xform:text property="fdDeptFour" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdDeptFive')}
                            </td>
                            <td width="16.6%">
                                <%-- 部门5--%>
                                <div id="_xform_fdDeptFive" _xform_type="text">
                                    <xform:text property="fdDeptFive" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdDeptSix')}
                            </td>
                            <td width="16.6%">
                                <%-- 部门6--%>
                                <div id="_xform_fdDeptSix" _xform_type="text">
                                    <xform:text property="fdDeptSix" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdDeptSeven')}
                            </td>
                            <td width="16.6%">
                                <%-- 部门7--%>
                                <div id="_xform_fdDeptSeven" _xform_type="text">
                                    <xform:text property="fdDeptSeven" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdDeptEigth')}
                            </td>
                            <td width="16.6%">
                                <%-- 部门8--%>
                                <div id="_xform_fdDeptEigth" _xform_type="text">
                                    <xform:text property="fdDeptEigth" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdDeptNine')}
                            </td>
                            <td width="16.6%">
                                <%-- 部门9--%>
                                <div id="_xform_fdDeptNine" _xform_type="text">
                                    <xform:text property="fdDeptNine" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdDeptTen')}
                            </td>
                            <td width="16.6%">
                                <%-- 部门10--%>
                                <div id="_xform_fdDeptTen" _xform_type="text">
                                    <xform:text property="fdDeptTen" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdCtripCardNo')}
                            </td>
                            <td width="16.6%">
                                <%-- 携程卡号--%>
                                <div id="_xform_fdCtripCardNo" _xform_type="text">
                                    <xform:text property="fdCtripCardNo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdName')}
                            </td>
                            <td width="16.6%">
                                <%-- 持卡人姓名--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdFlightClass')}
                            </td>
                            <td width="16.6%">
                                <%-- 国际/国内--%>
                                <div id="_xform_fdFlightClass" _xform_type="radio">
                                    <xform:radio property="fdFlightClass" htmlElementProperties="id='fdFlightClass'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_flight_type" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdIsOnline')}
                            </td>
                            <td width="16.6%">
                                <%-- 是否online--%>
                                <div id="_xform_fdIsOnline" _xform_type="text">
                                    <xform:text property="fdIsOnline" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdNickName')}
                            </td>
                            <td width="16.6%">
                                <%-- 昵称--%>
                                <div id="_xform_fdNickName" _xform_type="text">
                                    <xform:text property="fdNickName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdJourneyId')}
                            </td>
                            <td width="16.6%">
                                <%-- 关联行程号--%>
                                <div id="_xform_fdJourneyId" _xform_type="text">
                                    <xform:text property="fdJourneyId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdPrintTicketTime')}
                            </td>
                            <td width="16.6%">
                                <%-- 出票日期--%>
                                <div id="_xform_fdPrintTicketTime" _xform_type="text">
                                    <xform:text property="fdPrintTicketTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdOrderDate')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单日期--%>
                                <div id="_xform_fdOrderDate" _xform_type="text">
                                    <xform:text property="fdOrderDate" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdPreEmployeeId')}
                            </td>
                            <td width="16.6%">
                                <%-- 持卡人编号--%>
                                <div id="_xform_fdPreEmployeeId" _xform_type="text">
                                    <xform:text property="fdPreEmployeeId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdRebookOrderId')}
                            </td>
                            <td width="16.6%">
                                <%-- 改签原订单号（仅针对国际机票）--%>
                                <div id="_xform_fdRebookOrderId" _xform_type="text">
                                    <xform:text property="fdRebookOrderId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdRankName')}
                            </td>
                            <td width="16.6%">
                                <%-- 预订人职级--%>
                                <div id="_xform_fdRankName" _xform_type="text">
                                    <xform:text property="fdRankName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdRankNameEn')}
                            </td>
                            <td width="16.6%">
                                <%-- 预订人职级英文名--%>
                                <div id="_xform_fdRankNameEn" _xform_type="text">
                                    <xform:text property="fdRankNameEn" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdConfirmPersonEid')}
                            </td>
                            <td width="16.6%">
                                <%-- 一次授权人员工编号--%>
                                <div id="_xform_fdConfirmPersonEid" _xform_type="text">
                                    <xform:text property="fdConfirmPersonEid" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdConfirmPersonEidTwo')}
                            </td>
                            <td width="16.6%">
                                <%-- 二次授权人员工编号--%>
                                <div id="_xform_fdConfirmPersonEidTwo" _xform_type="text">
                                    <xform:text property="fdConfirmPersonEidTwo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdOrderStatus')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单状态--%>
                                <div id="_xform_fdOrderStatus" _xform_type="text">
                                    <xform:text property="fdOrderStatus" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdProjectCode')}
                            </td>
                            <td width="16.6%">
                                <%-- 项目编码--%>
                                <div id="_xform_fdProjectCode" _xform_type="text">
                                    <xform:text property="fdProjectCode" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdDeliveryInfo')}
                            </td>
                            <td width="16.6%">
                                <%-- 配送方式--%>
                                <div id="_xform_fdDeliveryInfo" _xform_type="text">
                                    <xform:text property="fdDeliveryInfo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdContactPhone')}
                            </td>
                            <td width="16.6%">
                                <%-- 联系人电话--%>
                                <div id="_xform_fdContactPhone" _xform_type="text">
                                    <xform:text property="fdContactPhone" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdContactMobile')}
                            </td>
                            <td width="16.6%">
                                <%-- 联系人手机--%>
                                <div id="_xform_fdContactMobile" _xform_type="text">
                                    <xform:text property="fdContactMobile" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdContactName')}
                            </td>
                            <td width="16.6%">
                                <%-- 联系人姓名--%>
                                <div id="_xform_fdContactName" _xform_type="text">
                                    <xform:text property="fdContactName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdDeliveryAddress')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单配送地址--%>
                                <div id="_xform_fdDeliveryAddress" _xform_type="text">
                                    <xform:text property="fdDeliveryAddress" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdContactEmail')}
                            </td>
                            <td width="16.6%">
                                <%-- 联系人邮箱--%>
                                <div id="_xform_fdContactEmail" _xform_type="text">
                                    <xform:text property="fdContactEmail" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdRelatedOrderId')}
                            </td>
                            <td width="16.6%">
                                <%-- 关联订单号--%>
                                <div id="_xform_fdRelatedOrderId" _xform_type="text">
                                    <xform:text property="fdRelatedOrderId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderFlightInfo.fdExpressId')}
                            </td>
                            <td width="16.6%">
                                <%-- 快递单号--%>
                                <div id="_xform_fdExpressId" _xform_type="text">
                                    <xform:text property="fdExpressId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td colspan="4">
                            </td>
                        </tr>
                    </table>
                </ui:content>
            </ui:tabpage>
        </template:replace>

    </template:include>
