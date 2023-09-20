<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
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

                };
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>
        <template:replace name="title">
            <c:out value="${hrStaffMoveRecordForm.fdStaffNumber} - " />
            <c:out value="${ lfn:message('hr-staff:table.hrStaffMoveRecord') }" />
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
                    basePath: '/hr/staff/hr_staff_move_record/hrStaffMoveRecord.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--edit-->
                <kmss:auth requestURL="/hr/staff/hr_staff_move_record/hrStaffMoveRecord.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('hrStaffMoveRecord.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/hr/staff/hr_staff_move_record/hrStaffMoveRecord.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('hrStaffMoveRecord.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffMoveRecord') }" href="/hr/staff/hr_staff_move_record/" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">

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
                                    <xform:text property="fdStaffNumber" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('hr-staff:hrStaffMoveRecord.fdStaffName')}
                            </td>
                            <td width="35%">
                                <%-- 姓名--%>
                                <div id="_xform_fdStaffName" _xform_type="text">
                                    <xform:text property="fdStaffName" showStatus="view" style="width:95%;" />
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
                                    <xform:text property="fdIsExplore" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('hr-staff:hrStaffMoveRecord.fdMoveDate')}
                            </td>
                            <td width="35%">
                                <%-- 异动时间--%>
                                <div id="_xform_fdMoveDate" _xform_type="datetime">
                                    <xform:datetime property="fdMoveDate" showStatus="view" dateTimeType="date" style="width:95%;" />
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
                                    <xform:datetime property="fdInternStartDate" showStatus="view" dateTimeType="date" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('hr-staff:hrStaffMoveRecord.fdInternEndDate')}
                            </td>
                            <td width="35%">
                                <%-- 见习结束日期--%>
                                <div id="_xform_fdInternEndDate" _xform_type="datetime">
                                    <xform:datetime property="fdInternEndDate" showStatus="view" dateTimeType="date" style="width:95%;" />
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
                                    <xform:select property="fdMoveType" htmlElementProperties="id='fdMoveType'" showStatus="view">
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
                                    <xform:radio property="fdTransDept" htmlElementProperties="id='fdTransDept'" showStatus="view">
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
                                    <xform:text property="fdBeforeFirstDeptName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforeSecondDeptName')}
                            </td>
                            <td width="35%">
                                <%-- 异动前二级部门--%>
                                <div id="_xform_fdBeforeSecondDeptName" _xform_type="text">
                                    <xform:text property="fdBeforeSecondDeptName" showStatus="view" style="width:95%;" />
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
                                    <xform:text property="fdBeforeThirdDeptName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforeDept')}
                            </td>
                            <td width="35%">
                                <%-- 异动前部门--%>
                                <div id="_xform_fdBeforeDeptId" _xform_type="address">
                                    <xform:address propertyId="fdBeforeDeptId" propertyName="fdBeforeDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" style="width:95%;" />
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
                                    <xform:address propertyId="fdBeforePostIds" propertyName="fdBeforePostNames" mulSelect="true" orgType="ORG_TYPE_POST" showStatus="view" style="width:95%;" />
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
                                    <xform:text property="fdBeforeRank" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforeLeader')}
                            </td>
                            <td width="35%">
                                <%-- 异动前直接上级--%>
                                <div id="_xform_fdBeforeLeaderId" _xform_type="address">
                                    <xform:address propertyId="fdBeforeLeaderId" propertyName="fdBeforeLeaderName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
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
                                    <xform:text property="fdAfterFirstDeptName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterSecondDeptName')}
                            </td>
                            <td width="35%">
                                <%-- 异动后二级部门--%>
                                <div id="_xform_fdAfterSecondDeptName" _xform_type="text">
                                    <xform:text property="fdAfterSecondDeptName" showStatus="view" style="width:95%;" />
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
                                    <xform:text property="fdAfterThirdDeptName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterDept')}
                            </td>
                            <td width="35%">
                                <%-- 异动后部门--%>
                                <div id="_xform_fdAfterDeptId" _xform_type="address">
                                    <xform:address propertyId="fdAfterDeptId" propertyName="fdAfterDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" style="width:95%;" />
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
                                    <xform:text property="fdAfterRank" showStatus="view" style="width:95%;" />
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
                                    <xform:address propertyId="fdAfterLeaderId" propertyName="fdAfterLeaderName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterPosts')}
                            </td>
                            <td width="35%">
                                <%-- 异动后岗位--%>
                                <div id="_xform_fdAfterPostIds" _xform_type="address">
                                    <xform:address propertyId="fdAfterPostIds" propertyName="fdAfterPostNames" mulSelect="true" orgType="ORG_TYPE_POST" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </ui:content>
            </ui:tabpage>
        </template:replace>

    </template:include>