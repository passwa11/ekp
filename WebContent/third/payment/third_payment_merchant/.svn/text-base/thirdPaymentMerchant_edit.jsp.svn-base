<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.third.payment.util.ThirdPaymentUtil" %>
    
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

                };

                var initData = {
                    contextPath: '${LUI_ContextPath}'
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form.js");
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/payment/third_payment_merchant/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/third/payment/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${thirdPaymentMerchantForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('third-payment:table.thirdPaymentMerchant') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${thirdPaymentMerchantForm.fdMerchId} - " />
                    <c:out value="${ lfn:message('third-payment:table.thirdPaymentMerchant') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ thirdPaymentMerchantForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.thirdPaymentMerchantForm, 'update');}" />
                    </c:when>
                    <c:when test="${ thirdPaymentMerchantForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.thirdPaymentMerchantForm, 'save');}" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('third-payment:table.thirdPaymentMerchant') }" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
            <html:form action="/third/payment/third_payment_merchant/thirdPaymentMerchant.do">

                <ui:tabpage expand="false" var-navwidth="90%">
                    <ui:content title="${ lfn:message('third-payment:py.JiBenXinXi') }" expand="true">
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-payment:thirdPaymentMerchant.fdMerchId')}
                                </td>
                                <td width="35%">
                                    <%-- 商户号--%>
                                    <div id="_xform_fdMerchId" _xform_type="text">
                                        <xform:text property="fdMerchId" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-payment:thirdPaymentMerchant.fdMerchType')}
                                </td>
                                <td width="35%">
                                    <%-- 商户类型--%>
                                    <div id="_xform_fdMerchType" _xform_type="select">
                                        <xform:select property="fdMerchType" htmlElementProperties="id='fdMerchType'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="third_payment_merch_type" />
                                        </xform:select>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-payment:thirdPaymentMerchant.fdMerchName')}
                                </td>
                                <td width="35%">
                                    <%-- 商户简称--%>
                                    <div id="_xform_fdMerchName" _xform_type="text">
                                        <xform:text property="fdMerchName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-payment:thirdPaymentMerchant.fdCorpName')}
                                </td>
                                <td width="35%">
                                    <%-- 企业名称--%>
                                    <div id="_xform_fdCorpName" _xform_type="text">
                                        <xform:text property="fdCorpName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-payment:thirdPaymentMerchant.fdMerchStatus')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <%-- 状态--%>
                                    <div id="_xform_fdMerchStatus" _xform_type="select">
                                        <xform:select property="fdMerchStatus" htmlElementProperties="id='fdMerchStatus'" showStatus="edit" validators=" digits">
                                            <xform:enumsDataSource enumsType="third_payment_merch_status" />
                                        </xform:select>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-payment:thirdPaymentMerchant.docCreator')}
                                </td>
                                <td width="35%">
                                    <%-- 创建人--%>
                                    <div id="_xform_docCreatorId" _xform_type="address">
                                        <ui:person personId="${thirdPaymentMerchantForm.docCreatorId}" personName="${thirdPaymentMerchantForm.docCreatorName}" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-payment:thirdPaymentMerchant.docAlteror')}
                                </td>
                                <td width="35%">
                                    <%-- 修改人--%>
                                    <div id="_xform_docAlterorId" _xform_type="address">
                                        <ui:person personId="${thirdPaymentMerchantForm.docAlterorId}" personName="${thirdPaymentMerchantForm.docAlterorName}" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-payment:thirdPaymentMerchant.docCreateTime')}
                                </td>
                                <td width="35%">
                                    <%-- 创建时间--%>
                                    <div id="_xform_docCreateTime" _xform_type="datetime">
                                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-payment:thirdPaymentMerchant.docAlterTime')}
                                </td>
                                <td width="35%">
                                    <%-- 更新时间--%>
                                    <div id="_xform_docAlterTime" _xform_type="datetime">
                                        <xform:datetime property="docAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </ui:content>
                    <c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="thirdPaymentMerchantForm" />
                        <c:param name="moduleModelName" value="com.landray.kmss.third.payment.model.ThirdPaymentMerchant" />
                    </c:import>

                </ui:tabpage>
                <html:hidden property="fdId" />
                <html:hidden property="fdPayWay" />

                <html:hidden property="method_GET" />
            </html:form>
        </template:replace>


    </template:include>