<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.fssc.alitrip.util.FsscAlitripUtil" %>
    
        <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
        pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
    
    <template:include ref="default.edit">
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

                var initData = {
                    contextPath: '${LUI_ContextPath}',
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form.js");
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/alitrip/fssc_alitrip_order/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/alitrip/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${fsscAlitripOrderForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-alitrip:table.fsscAlitripOrder') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${fsscAlitripOrderForm.fdName} - " />
                    <c:out value="${ lfn:message('fssc-alitrip:table.fsscAlitripOrder') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ fsscAlitripOrderForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.fsscAlitripOrderForm, 'update');}" />
                    </c:when>
                    <c:when test="${ fsscAlitripOrderForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.fsscAlitripOrderForm, 'save');}" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('fssc-alitrip:table.fsscAlitripOrder') }" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
            <html:form action="/fssc/alitrip/fssc_alitrip_order/fsscAlitripOrder.do">

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
                                        <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.fdType')}
                                </td>
                                <td width="16.6%">
                                    <%-- 订单类型--%>
                                    <div id="_xform_fdType" _xform_type="select">
                                        <xform:select property="fdType" htmlElementProperties="id='fdType'" showStatus="edit">
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
                                        <xform:text property="id" showStatus="edit" style="width:95%;" />
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
                                        <xform:datetime property="gmtCreate" showStatus="edit" dateTimeType="datetime" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.gmtModified')}
                                </td>
                                <td width="16.6%">
                                    <%-- 更新时间--%>
                                    <div id="_xform_gmtModified" _xform_type="datetime">
                                        <xform:datetime property="gmtModified" showStatus="edit" dateTimeType="datetime" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.corpId')}
                                </td>
                                <td width="16.6%">
                                    <%-- 企业id--%>
                                    <div id="_xform_corpId" _xform_type="text">
                                        <xform:text property="corpId" showStatus="edit" style="width:95%;" />
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
                                        <xform:text property="corpName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.userId')}
                                </td>
                                <td width="16.6%">
                                    <%-- 用户id--%>
                                    <div id="_xform_userId" _xform_type="text">
                                        <xform:text property="userId" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.userName')}
                                </td>
                                <td width="16.6%">
                                    <%-- 用户名称--%>
                                    <div id="_xform_userName" _xform_type="text">
                                        <xform:text property="userName" showStatus="edit" style="width:95%;" />
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
                                        <xform:text property="departId" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.departName')}
                                </td>
                                <td width="16.6%">
                                    <%-- 部门名称--%>
                                    <div id="_xform_departName" _xform_type="text">
                                        <xform:text property="departName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.applyId')}
                                </td>
                                <td width="16.6%">
                                    <%-- 申请单id--%>
                                    <div id="_xform_applyId" _xform_type="text">
                                        <xform:text property="applyId" showStatus="edit" style="width:95%;" />
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
                                        <xform:text property="contactPhone" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.contactName')}
                                </td>
                                <td width="16.6%">
                                    <%-- 联系人名称--%>
                                    <div id="_xform_contactName" _xform_type="text">
                                        <xform:text property="contactName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.city')}
                                </td>
                                <td width="16.6%">
                                    <%-- 酒店所在城市--%>
                                    <div id="_xform_city" _xform_type="text">
                                        <xform:text property="city" showStatus="edit" style="width:95%;" />
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
                                        <xform:text property="hotelName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.checkIn')}
                                </td>
                                <td width="16.6%">
                                    <%-- 入住时间--%>
                                    <div id="_xform_checkIn" _xform_type="datetime">
                                        <xform:datetime property="checkIn" showStatus="edit" dateTimeType="datetime" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.checkOut')}
                                </td>
                                <td width="16.6%">
                                    <%-- 离店时间--%>
                                    <div id="_xform_checkOut" _xform_type="datetime">
                                        <xform:datetime property="checkOut" showStatus="edit" dateTimeType="datetime" style="width:95%;" />
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
                                        <xform:text property="roomType" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.roomNum')}
                                </td>
                                <td width="16.6%">
                                    <%-- 房间数--%>
                                    <div id="_xform_roomNum" _xform_type="text">
                                        <xform:text property="roomNum" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.night')}
                                </td>
                                <td width="16.6%">
                                    <%-- 总共住几晚--%>
                                    <div id="_xform_night" _xform_type="text">
                                        <xform:text property="night" showStatus="edit" style="width:95%;" />
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
                                        <xform:text property="costCenterNumber" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.costCenterName')}
                                </td>
                                <td width="16.6%">
                                    <%-- 成本中心名称--%>
                                    <div id="_xform_costCenterName" _xform_type="text">
                                        <xform:text property="costCenterName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.invoiceTitle')}
                                </td>
                                <td width="16.6%">
                                    <%-- 发票抬头--%>
                                    <div id="_xform_invoiceTitle" _xform_type="text">
                                        <xform:text property="invoiceTitle" showStatus="edit" style="width:95%;" />
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
                                        <xform:text property="invoiceId" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.orderStatusDesc')}
                                </td>
                                <td width="16.6%">
                                    <%-- 订单状态描述--%>
                                    <div id="_xform_orderStatusDesc" _xform_type="text">
                                        <xform:text property="orderStatusDesc" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.orderTypeDesc')}
                                </td>
                                <td width="16.6%">
                                    <%-- 订单类型描述--%>
                                    <div id="_xform_orderTypeDesc" _xform_type="text">
                                        <xform:text property="orderTypeDesc" showStatus="edit" style="width:95%;" />
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
                                        <xform:text property="guest" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.thirdpartItineraryId')}
                                </td>
                                <td width="16.6%">
                                    <%-- 第三方行程id--%>
                                    <div id="_xform_thirdpartItineraryId" _xform_type="text">
                                        <xform:text property="thirdpartItineraryId" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.orderStatus')}
                                </td>
                                <td width="16.6%">
                                    <%-- 订单状态--%>
                                    <div id="_xform_orderStatus" _xform_type="text">
                                        <xform:text property="orderStatus" showStatus="edit" style="width:95%;" />
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
                                        <xform:text property="orderType" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.depCity')}
                                </td>
                                <td width="16.6%">
                                    <%-- 出发城市--%>
                                    <div id="_xform_depCity" _xform_type="text">
                                        <xform:text property="depCity" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.arrCity')}
                                </td>
                                <td width="16.6%">
                                    <%-- 到达城市--%>
                                    <div id="_xform_arrCity" _xform_type="text">
                                        <xform:text property="arrCity" showStatus="edit" style="width:95%;" />
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
                                        <xform:datetime property="depDate" showStatus="edit" dateTimeType="datetime" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.retDate')}
                                </td>
                                <td width="16.6%">
                                    <%-- 到达日期--%>
                                    <div id="_xform_retDate" _xform_type="datetime">
                                        <xform:datetime property="retDate" showStatus="edit" dateTimeType="datetime" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.tripType')}
                                </td>
                                <td width="16.6%">
                                    <%-- 行程类型--%>
                                    <div id="_xform_tripType" _xform_type="select">
                                        <xform:select property="tripType" htmlElementProperties="id='tripType'" showStatus="edit">
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
                                        <xform:text property="passengerCount" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.cabinClass')}
                                </td>
                                <td width="16.6%">
                                    <%-- 舱位类型--%>
                                    <div id="_xform_cabinClass" _xform_type="text">
                                        <xform:text property="cabinClass" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.status')}
                                </td>
                                <td width="16.6%">
                                    <%-- 订单状态--%>
                                    <div id="_xform_status" _xform_type="select">
                                        <xform:select property="status" htmlElementProperties="id='status'" showStatus="edit">
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
                                        <xform:text property="arrAirport" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.depAirport')}
                                </td>
                                <td width="16.6%">
                                    <%-- 出发机场--%>
                                    <div id="_xform_depAirport" _xform_type="text">
                                        <xform:text property="depAirport" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.passengerName')}
                                </td>
                                <td width="16.6%">
                                    <%-- 乘机人--%>
                                    <div id="_xform_passengerName" _xform_type="text">
                                        <xform:text property="passengerName" showStatus="edit" style="width:95%;" />
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
                                        <xform:text property="flightNo" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.discount')}
                                </td>
                                <td width="16.6%">
                                    <%-- 折扣--%>
                                    <div id="_xform_discount" _xform_type="text">
                                        <xform:text property="discount" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.depStation')}
                                </td>
                                <td width="16.6%">
                                    <%-- 出发站--%>
                                    <div id="_xform_depStation" _xform_type="text">
                                        <xform:text property="depStation" showStatus="edit" style="width:95%;" />
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
                                        <xform:text property="arrStation" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.depTime')}
                                </td>
                                <td width="16.6%">
                                    <%-- 出发时间--%>
                                    <div id="_xform_depTime" _xform_type="datetime">
                                        <xform:datetime property="depTime" showStatus="edit" dateTimeType="datetime" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.arrTime')}
                                </td>
                                <td width="16.6%">
                                    <%-- 到达时间--%>
                                    <div id="_xform_arrTime" _xform_type="datetime">
                                        <xform:datetime property="arrTime" showStatus="edit" dateTimeType="datetime" style="width:95%;" />
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
                                        <xform:text property="trainNumber" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.trainType')}
                                </td>
                                <td width="16.6%">
                                    <%-- 车次类型--%>
                                    <div id="_xform_trainType" _xform_type="text">
                                        <xform:text property="trainType" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.seatType')}
                                </td>
                                <td width="16.6%">
                                    <%-- 座位类型--%>
                                    <div id="_xform_seatType" _xform_type="text">
                                        <xform:text property="seatType" showStatus="edit" style="width:95%;" />
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
                                        <xform:text property="runTime" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.ticketNo12306')}
                                </td>
                                <td width="16.6%">
                                    <%-- 12306票号--%>
                                    <div id="_xform_ticketNo12306" _xform_type="text">
                                        <xform:text property="ticketNo12306" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripOrder.riderName')}
                                </td>
                                <td width="16.6%">
                                    <%-- 乘客姓名--%>
                                    <div id="_xform_riderName" _xform_type="text">
                                        <xform:text property="riderName" showStatus="edit" style="width:95%;" />
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
                                        <xform:text property="ticketCount" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td colspan="4">
                                </td>
                            </tr>
                        </table>
                    </ui:content>
                </ui:tabpage>
                <html:hidden property="fdId" />


                <html:hidden property="method_GET" />
            </html:form>
        </template:replace>


    </template:include>
