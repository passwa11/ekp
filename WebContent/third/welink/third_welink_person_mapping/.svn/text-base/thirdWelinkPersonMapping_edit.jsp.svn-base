<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.third.welink.util.ThirdWelinkUtil" %>
    
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
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/welink/third_welink_person_mapping/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/third/welink/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${thirdWelinkPersonMappingForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('third-welink:table.thirdWelinkPersonMapping') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${thirdWelinkPersonMappingForm.fdWelinkId} - " />
                    <c:out value="${ lfn:message('third-welink:table.thirdWelinkPersonMapping') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ thirdWelinkPersonMappingForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.thirdWelinkPersonMappingForm, 'update');}" />
                    </c:when>
                    <c:when test="${ thirdWelinkPersonMappingForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.thirdWelinkPersonMappingForm, 'save');}" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('third-welink:table.thirdWelinkPersonMapping') }" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
            <html:form action="/third/welink/third_welink_person_mapping/thirdWelinkPersonMapping.do">

                    <ui:content title="${ lfn:message('third-welink:py.JiBenXinXi') }" expand="true">
                        <div class='lui_form_title_frame'>
                            <div class='lui_form_subject'>
                                ${lfn:message('third-welink:table.thirdWelinkPersonMapping')}
                            </div>
                            <div class='lui_form_baseinfo'>

                            </div>
                        </div>
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-welink:thirdWelinkPersonMapping.fdWelinkId')}
                                </td>
                                <td width="35%">
                                    <%-- welink人员ID--%>
                                    <div id="_xform_fdWelinkId" _xform_type="text">
                                        <xform:text property="fdWelinkId" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-welink:thirdWelinkPersonMapping.fdLoginName')}
                                </td>
                                <td width="35%">
                                    <%-- 登录名--%>
                                    <div id="_xform_fdLoginName" _xform_type="text">
                                        <xform:text property="fdLoginName" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-welink:thirdWelinkPersonMapping.fdMobileNo')}
                                </td>
                                <td width="35%">
                                    <%-- 手机号--%>
                                    <div id="_xform_fdMobileNo" _xform_type="text">
                                        <xform:text property="fdMobileNo" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-welink:thirdWelinkPersonMapping.fdEkpPerson')}
                                </td>
                                <td width="35%">
                                    <%-- ekp人员--%>
                                    <div id="_xform_fdEkpPersonId" _xform_type="address">
                                        <xform:address propertyId="fdEkpPersonId" propertyName="fdEkpPersonName" orgType="ORG_TYPE_PERSON" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-welink:thirdWelinkPersonMapping.fdWelinkUserId')}
                                </td>
                                <td width="35%">
                                    <div id="_xform_fdWelinkUserId" _xform_type="text">
                                        <xform:text property="fdWelinkUserId" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" colspan="2">
                                </td>
                                
                            </tr>
                        </table>
                    </ui:content>
                <html:hidden property="fdId" />

                <html:hidden property="method_GET" />
            </html:form>
        </template:replace>


    </template:include>