<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.fssc.alitrip.util.FsscAlitripUtil" %>
    
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

                    'insure_info_list': '${lfn:escapeJs(lfn:message("fssc-alitrip:table.fsscAlitripInsure"))}',
                    'price_info_list': '${lfn:escapeJs(lfn:message("fssc-alitrip:table.fsscAlitripPrice"))}'
                };
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>
        <template:replace name="title">
            <c:out value="${fsscAlitripOrderForm.fdName} - " />
            <c:out value="${ lfn:message('fssc-alitrip:table.fsscAlitripOrder') }" />
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
                    basePath: '/fssc/alitrip/fssc_alitrip_order/fsscAlitripOrder.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--edit-->
                <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_order/fsscAlitripOrder.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscAlitripOrder.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_order/fsscAlitripOrder.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscAlitripOrder.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('fssc-alitrip:table.fsscAlitripOrder') }" href="/fssc/alitrip/fssc_alitrip_order/" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('fssc-alitrip:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('fssc-alitrip:table.fsscAlitripOrder')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.fdName')}
                            </td>
                            <td width="16.6%">
                                <%-- 名称--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.fdType')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单类型--%>
                                <div id="_xform_fdType" _xform_type="select">
                                    <xform:select property="fdType" htmlElementProperties="id='fdType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_alitrip_train_cate" />
                                    </xform:select>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.id')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单id--%>
                                <div id="_xform_id" _xform_type="text">
                                    <xform:text property="id" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.gmtCreate')}
                            </td>
                            <td width="16.6%">
                                <%-- 创建时间--%>
                                <div id="_xform_gmtCreate" _xform_type="datetime">
                                    <xform:datetime property="gmtCreate" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.gmtModified')}
                            </td>
                            <td width="16.6%">
                                <%-- 更新时间--%>
                                <div id="_xform_gmtModified" _xform_type="datetime">
                                    <xform:datetime property="gmtModified" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.corpId')}
                            </td>
                            <td width="16.6%">
                                <%-- 企业id--%>
                                <div id="_xform_corpId" _xform_type="text">
                                    <xform:text property="corpId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.corpName')}
                            </td>
                            <td width="16.6%">
                                <%-- 企业名称--%>
                                <div id="_xform_corpName" _xform_type="text">
                                    <xform:text property="corpName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.userId')}
                            </td>
                            <td width="16.6%">
                                <%-- 用户id--%>
                                <div id="_xform_userId" _xform_type="text">
                                    <xform:text property="userId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.userName')}
                            </td>
                            <td width="16.6%">
                                <%-- 用户名称--%>
                                <div id="_xform_userName" _xform_type="text">
                                    <xform:text property="userName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.departId')}
                            </td>
                            <td width="16.6%">
                                <%-- 部门id--%>
                                <div id="_xform_departId" _xform_type="text">
                                    <xform:text property="departId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.departName')}
                            </td>
                            <td width="16.6%">
                                <%-- 部门名称--%>
                                <div id="_xform_departName" _xform_type="text">
                                    <xform:text property="departName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.applyId')}
                            </td>
                            <td width="16.6%">
                                <%-- 申请单id--%>
                                <div id="_xform_applyId" _xform_type="text">
                                    <xform:text property="applyId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.contactPhone')}
                            </td>
                            <td width="16.6%">
                                <%-- 联系人电话--%>
                                <div id="_xform_contactPhone" _xform_type="text">
                                    <xform:text property="contactPhone" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.contactName')}
                            </td>
                            <td width="16.6%">
                                <%-- 联系人名称--%>
                                <div id="_xform_contactName" _xform_type="text">
                                    <xform:text property="contactName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.city')}
                            </td>
                            <td width="16.6%">
                                <%-- 酒店所在城市--%>
                                <div id="_xform_city" _xform_type="text">
                                    <xform:text property="city" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.hotelName')}
                            </td>
                            <td width="16.6%">
                                <%-- 酒店名称--%>
                                <div id="_xform_hotelName" _xform_type="text">
                                    <xform:text property="hotelName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.checkIn')}
                            </td>
                            <td width="16.6%">
                                <%-- 入住时间--%>
                                <div id="_xform_checkIn" _xform_type="datetime">
                                    <xform:datetime property="checkIn" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.checkOut')}
                            </td>
                            <td width="16.6%">
                                <%-- 离店时间--%>
                                <div id="_xform_checkOut" _xform_type="datetime">
                                    <xform:datetime property="checkOut" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.roomType')}
                            </td>
                            <td width="16.6%">
                                <%-- 房间类型--%>
                                <div id="_xform_roomType" _xform_type="text">
                                    <xform:text property="roomType" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.roomNum')}
                            </td>
                            <td width="16.6%">
                                <%-- 房间数--%>
                                <div id="_xform_roomNum" _xform_type="text">
                                    <xform:text property="roomNum" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.night')}
                            </td>
                            <td width="16.6%">
                                <%-- 总共住几晚--%>
                                <div id="_xform_night" _xform_type="text">
                                    <xform:text property="night" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.costCenterNumber')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心编号--%>
                                <div id="_xform_costCenterNumber" _xform_type="text">
                                    <xform:text property="costCenterNumber" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.costCenterName')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心名称--%>
                                <div id="_xform_costCenterName" _xform_type="text">
                                    <xform:text property="costCenterName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.invoiceTitle')}
                            </td>
                            <td width="16.6%">
                                <%-- 发票抬头--%>
                                <div id="_xform_invoiceTitle" _xform_type="text">
                                    <xform:text property="invoiceTitle" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.invoiceId')}
                            </td>
                            <td width="16.6%">
                                <%-- 商旅发票id--%>
                                <div id="_xform_invoiceId" _xform_type="text">
                                    <xform:text property="invoiceId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.orderStatusDesc')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单状态描述--%>
                                <div id="_xform_orderStatusDesc" _xform_type="text">
                                    <xform:text property="orderStatusDesc" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.orderTypeDesc')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单类型描述--%>
                                <div id="_xform_orderTypeDesc" _xform_type="text">
                                    <xform:text property="orderTypeDesc" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.guest')}
                            </td>
                            <td width="16.6%">
                                <%-- 入住顾客--%>
                                <div id="_xform_guest" _xform_type="text">
                                    <xform:text property="guest" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.thirdpartItineraryId')}
                            </td>
                            <td width="16.6%">
                                <%-- 第三方行程id--%>
                                <div id="_xform_thirdpartItineraryId" _xform_type="text">
                                    <xform:text property="thirdpartItineraryId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.orderStatus')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单状态--%>
                                <div id="_xform_orderStatus" _xform_type="text">
                                    <xform:text property="orderStatus" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.orderType')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单类型--%>
                                <div id="_xform_orderType" _xform_type="text">
                                    <xform:text property="orderType" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.depCity')}
                            </td>
                            <td width="16.6%">
                                <%-- 出发城市--%>
                                <div id="_xform_depCity" _xform_type="text">
                                    <xform:text property="depCity" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.arrCity')}
                            </td>
                            <td width="16.6%">
                                <%-- 到达城市--%>
                                <div id="_xform_arrCity" _xform_type="text">
                                    <xform:text property="arrCity" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.depDate')}
                            </td>
                            <td width="16.6%">
                                <%-- 出发日期--%>
                                <div id="_xform_depDate" _xform_type="datetime">
                                    <xform:datetime property="depDate" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.retDate')}
                            </td>
                            <td width="16.6%">
                                <%-- 到达日期--%>
                                <div id="_xform_retDate" _xform_type="datetime">
                                    <xform:datetime property="retDate" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.tripType')}
                            </td>
                            <td width="16.6%">
                                <%-- 行程类型--%>
                                <div id="_xform_tripType" _xform_type="select">
                                    <xform:select property="tripType" htmlElementProperties="id='tripType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_alitrip_trip_type" />
                                    </xform:select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.passengerCount')}
                            </td>
                            <td width="16.6%">
                                <%-- 乘机人数量--%>
                                <div id="_xform_passengerCount" _xform_type="text">
                                    <xform:text property="passengerCount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.cabinClass')}
                            </td>
                            <td width="16.6%">
                                <%-- 舱位类型--%>
                                <div id="_xform_cabinClass" _xform_type="text">
                                    <xform:text property="cabinClass" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.status')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单状态--%>
                                <div id="_xform_status" _xform_type="select">
                                    <xform:select property="status" htmlElementProperties="id='status'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_alitrip_plan_status" />
                                    </xform:select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.arrAirport')}
                            </td>
                            <td width="16.6%">
                                <%-- 到达机场--%>
                                <div id="_xform_arrAirport" _xform_type="text">
                                    <xform:text property="arrAirport" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.depAirport')}
                            </td>
                            <td width="16.6%">
                                <%-- 出发机场--%>
                                <div id="_xform_depAirport" _xform_type="text">
                                    <xform:text property="depAirport" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.passengerName')}
                            </td>
                            <td width="16.6%">
                                <%-- 乘机人--%>
                                <div id="_xform_passengerName" _xform_type="text">
                                    <xform:text property="passengerName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.flightNo')}
                            </td>
                            <td width="16.6%">
                                <%-- 航班号--%>
                                <div id="_xform_flightNo" _xform_type="text">
                                    <xform:text property="flightNo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.discount')}
                            </td>
                            <td width="16.6%">
                                <%-- 折扣--%>
                                <div id="_xform_discount" _xform_type="text">
                                    <xform:text property="discount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.depStation')}
                            </td>
                            <td width="16.6%">
                                <%-- 出发站--%>
                                <div id="_xform_depStation" _xform_type="text">
                                    <xform:text property="depStation" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.arrStation')}
                            </td>
                            <td width="16.6%">
                                <%-- 到达站--%>
                                <div id="_xform_arrStation" _xform_type="text">
                                    <xform:text property="arrStation" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.depTime')}
                            </td>
                            <td width="16.6%">
                                <%-- 出发时间--%>
                                <div id="_xform_depTime" _xform_type="datetime">
                                    <xform:datetime property="depTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.arrTime')}
                            </td>
                            <td width="16.6%">
                                <%-- 到达时间--%>
                                <div id="_xform_arrTime" _xform_type="datetime">
                                    <xform:datetime property="arrTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.trainNumber')}
                            </td>
                            <td width="16.6%">
                                <%-- 车次--%>
                                <div id="_xform_trainNumber" _xform_type="text">
                                    <xform:text property="trainNumber" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.trainType')}
                            </td>
                            <td width="16.6%">
                                <%-- 车次类型--%>
                                <div id="_xform_trainType" _xform_type="text">
                                    <xform:text property="trainType" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.seatType')}
                            </td>
                            <td width="16.6%">
                                <%-- 座位类型--%>
                                <div id="_xform_seatType" _xform_type="text">
                                    <xform:text property="seatType" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.runTime')}
                            </td>
                            <td width="16.6%">
                                <%-- 运行时长--%>
                                <div id="_xform_runTime" _xform_type="text">
                                    <xform:text property="runTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.ticketNo12306')}
                            </td>
                            <td width="16.6%">
                                <%-- 12306票号--%>
                                <div id="_xform_ticketNo12306" _xform_type="text">
                                    <xform:text property="ticketNo12306" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.riderName')}
                            </td>
                            <td width="16.6%">
                                <%-- 乘客姓名--%>
                                <div id="_xform_riderName" _xform_type="text">
                                    <xform:text property="riderName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-alitrip:fsscAlitripOrder.ticketCount')}
                            </td>
                            <td width="16.6%">
                                <%-- 票的数量--%>
                                <div id="_xform_ticketCount" _xform_type="text">
                                    <xform:text property="ticketCount" showStatus="view" style="width:95%;" />
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
