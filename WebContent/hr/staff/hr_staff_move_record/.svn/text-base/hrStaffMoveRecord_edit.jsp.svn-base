<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

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
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/hr/staff/hr_staff_move_record/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/hr/staff/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${hrStaffMoveRecordForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('hr-staff:table.hrStaffMoveRecord') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${hrStaffMoveRecordForm.fdStaffNumber} - " />
                    <c:out value="${ lfn:message('hr-staff:table.hrStaffMoveRecord') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ hrStaffMoveRecordForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.hrStaffMoveRecordForm, 'update');}" />
                    </c:when>
                    <c:when test="${ hrStaffMoveRecordForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.hrStaffMoveRecordForm, 'save');}" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffMoveRecord') }" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
            <html:form action="/hr/staff/hr_staff_move_record/hrStaffMoveRecord.do">

                <ui:tabpage expand="false" var-navwidth="90%">
                    <ui:content title="${ lfn:message('hr-staff:py.JiBenXinXi') }" expand="true">
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdStaffNumber')}
                                </td>
                                <td width="35%">
                                    <%-- 人员编号--%>
                                    <div id="_xform_fdStaffNumber" _xform_type="text">
                                        <xform:text property="fdStaffNumber" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdStaffName')}
                                </td>
                                <td width="35%">
                                    <%-- 姓名--%>
                                    <div id="_xform_fdStaffName" _xform_type="text">
                                        <xform:text property="fdStaffName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdIsExplore')}
                                </td>
                                <td width="35%">
                                    <%-- 是否考察--%>
                                    <div id="_xform_fdIsExplore" _xform_type="text">
                                        <xform:text property="fdIsExplore" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdMoveDate')}
                                </td>
                                <td width="35%">
                                    <%-- 异动时间--%>
                                    <div id="_xform_fdMoveDate" _xform_type="datetime">
                                        <xform:datetime property="fdMoveDate" showStatus="edit" dateTimeType="date" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdInternStartDate')}
                                </td>
                                <td width="35%">
                                    <%-- 见习开始日期--%>
                                    <div id="_xform_fdInternStartDate" _xform_type="datetime">
                                        <xform:datetime property="fdInternStartDate" showStatus="edit" dateTimeType="date" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdInternEndDate')}
                                </td>
                                <td width="35%">
                                    <%-- 见习结束日期--%>
                                    <div id="_xform_fdInternEndDate" _xform_type="datetime">
                                        <xform:datetime property="fdInternEndDate" showStatus="edit" dateTimeType="date" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdMoveType')}
                                </td>
                                <td width="35%">
                                    <%-- 异动类型--%>
                                    <div id="_xform_fdMoveType" _xform_type="select">
                                        <xform:select property="fdMoveType" htmlElementProperties="id='fdMoveType'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="hr_staff_move_type" />
                                        </xform:select>
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdTransDept')}
                                </td>
                                <td width="35%">
                                    <%-- 是否跨一级部门--%>
                                    <div id="_xform_fdTransDept" _xform_type="radio">
                                        <xform:radio property="fdTransDept" htmlElementProperties="id='fdTransDept'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="hr_staff_move_trans" />
                                        </xform:radio>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </ui:content>
                    <ui:content title="${ lfn:message('hr-staff:py.YiDongQianXinXi') }" expand="true">
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforeFirstDeptName')}
                                </td>
                                <td width="35%">
                                    <%-- 异动前一级部门--%>
                                    <div id="_xform_fdBeforeFirstDeptName" _xform_type="text">
                                        <xform:text property="fdBeforeFirstDeptName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforeSecondDeptName')}
                                </td>
                                <td width="35%">
                                    <%-- 异动前二级部门--%>
                                    <div id="_xform_fdBeforeSecondDeptName" _xform_type="text">
                                        <xform:text property="fdBeforeSecondDeptName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforeThirdDeptName')}
                                </td>
                                <td width="35%">
                                    <%-- 异动前三级部门--%>
                                    <div id="_xform_fdBeforeThirdDeptName" _xform_type="text">
                                        <xform:text property="fdBeforeThirdDeptName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforeDept')}
                                </td>
                                <td width="35%">
                                    <%-- 异动前部门--%>
                                    <div id="_xform_fdBeforeDeptId" _xform_type="address">
                                        <xform:address propertyId="fdBeforeDeptId" propertyName="fdBeforeDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforePosts')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <%-- 异动前岗位--%>
                                    <div id="_xform_fdBeforePostIds" _xform_type="address">
                                        <xform:address propertyId="fdBeforePostIds" propertyName="fdBeforePostNames" mulSelect="true" orgType="ORG_TYPE_POST" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforeRank')}
                                </td>
                                <td width="35%">
                                    <%-- 异动前职级--%>
                                    <div id="_xform_fdBeforeRank" _xform_type="text">
                                        <xform:text property="fdBeforeRank" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforeLeader')}
                                </td>
                                <td width="35%">
                                    <%-- 异动前直接上级--%>
                                    <div id="_xform_fdBeforeLeaderId" _xform_type="address">
                                        <xform:address propertyId="fdBeforeLeaderId" propertyName="fdBeforeLeaderName" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </ui:content>
                    <ui:content title="${ lfn:message('hr-staff:py.YiDongHouXinXi') }" expand="true">
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterFirstDeptName')}
                                </td>
                                <td width="35%">
                                    <%-- 异动后一级部门--%>
                                    <div id="_xform_fdAfterFirstDeptName" _xform_type="text">
                                        <xform:text property="fdAfterFirstDeptName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterSecondDeptName')}
                                </td>
                                <td width="35%">
                                    <%-- 异动后二级部门--%>
                                    <div id="_xform_fdAfterSecondDeptName" _xform_type="text">
                                        <xform:text property="fdAfterSecondDeptName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterThirdDeptName')}
                                </td>
                                <td width="35%">
                                    <%-- 异动后三级部门--%>
                                    <div id="_xform_fdAfterThirdDeptName" _xform_type="text">
                                        <xform:text property="fdAfterThirdDeptName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterDept')}
                                </td>
                                <td width="35%">
                                    <%-- 异动后部门--%>
                                    <div id="_xform_fdAfterDeptId" _xform_type="address">
                                        <xform:address propertyId="fdAfterDeptId" propertyName="fdAfterDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterRank')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <%-- 异动后职级--%>
                                    <div id="_xform_fdAfterRank" _xform_type="text">
                                        <xform:text property="fdAfterRank" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterLeader')}
                                </td>
                                <td width="35%">
                                    <%-- 异动后直接上级--%>
                                    <div id="_xform_fdAfterLeaderId" _xform_type="address">
                                        <xform:address propertyId="fdAfterLeaderId" propertyName="fdAfterLeaderName" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterPosts')}
                                </td>
                                <td width="35%">
                                    <%-- 异动后岗位--%>
                                    <div id="_xform_fdAfterPostIds" _xform_type="address">
                                        <xform:address propertyId="fdAfterPostIds" propertyName="fdAfterPostNames" mulSelect="true" orgType="ORG_TYPE_POST" showStatus="edit" style="width:95%;" />
                                    </div>
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