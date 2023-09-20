<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil,
				 com.landray.kmss.hr.staff.util.ProvinceUtil,
				 com.landray.kmss.hr.staff.util.CitiesUtil,
				 com.landray.kmss.hr.staff.util.AreasUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.edit" sidebar="auto">
    <template:replace name="title">
        <c:choose>
            <c:when test="${ hrStaffPersonInfoForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('hr-staff:hrStaffPersonInfo.create.title') } - ${ lfn:message('hr-staff:module.hr.staff') }"></c:out>
            </c:when>
            <c:otherwise>
                ${ hrStaffPersonInfoForm.fdName } - ${ lfn:message('hr-staff:module.hr.staff') }
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="head">
        <link rel="stylesheet" href="../resource/css/common_view.css">
        <link rel="stylesheet" href="../resource/css/person_info.css">
        <style>
            .com_qrcode {
                display: none !important
            }

            .hr_select {
                width: 50%;
                max-width: 80%;
            }


            .lui-personnel-file-baseInfo-main-content .inputsgl {
                border: 1px solid #b4b4b4;
                height: 28px;
                border-radius: 4px;
            }

            .newTable tr {
                height: 40px;
                border-spacing: 0px 10px;
            }

            .datawidth .inputselectsgl, .lui-custom-Prop .inputselectsgl {
                width: 205px !important;
                height: 30px !important;
                border-radius: 4px;
                border: none;
                background-color: #F7F7F8;
            }

            .inputwidth .inputsgl, .lui-custom-Prop .inputsgl {
                height: 28px;
                width: 200px !important;
                background-color: #F7F7F8;
                border: none;
                border-radius: 4px;
            }

            .hr_select {
                width: 200px;
                height: 30px;
                border-radius: 4px;
                background-color: #F7F7F8;
                border: none !important;
            }

            .datawidth input, .lui-custom-Prop .inputselectsgl input {
                background-color: #F7F7F8 !important;
            }

            .lui-personnel-file-edit-text {

            }

            .inputselectsgl {
                width: 200px !important;
            }

            .newTable textarea {
                width: 200px;
                border: none;
                margin: 4px 0;
                background-color: #f7f7f7;
            }

            .lui_form_path_frame {
                max-width: 1200px !important;

            }

            .lui_form_body {
                background-color: white;
            }
        </style>
    </template:replace>
    <template:replace name="content">
        <html:form action="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do">
            <html:hidden property="fdId" value="${hrStaffPersonInfoForm.fdId}"/>
            <html:hidden property="fdOrgPersonId" value="${hrStaffPersonInfoForm.fdOrgPersonId }"/>
            <table class="staffInfo newTable">
                <tr>
                    <td width="15%" class="td_normal_title">
                        <bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOfficeArea"/>
                    </td>
                    <td width="35%">
                        <%=ProvinceUtil.buildProvinceHtml1("fdOfficeAreaProvinceId", request)%>
                    </td>
                    <td width="15%" class="td_normal_title">
                        <bean:message bundle="hr-staff" key=""/>
                    </td>
                    <td width="35%">
                        <%=CitiesUtil.buildCitiesHtml("fdOfficeAreaCityId", "fdOfficeAreaProvinceId", request)%>
                    </td>
                </tr>
                <tr>
                    <td width="15%" class="td_normal_title">
                        <bean:message bundle="hr-staff" key=""/>
                    </td>
                    <td width="35%">
                        <%=AreasUtil.buildAreasHtml("fdOfficeAreaAreaId", "fdOfficeAreaCityId", request)%>
                    </td>
                    <!-- 办公地点 -->
                    <td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonInfo.fdOfficeLocation') }
                    </td>
                    <td width="35%" class="inputwidth">
                        <xform:text showStatus="edit" property="fdOfficeLocation" className="inputsgl" required="true"/>
                    </td>
                </tr>
                <tr>
                    <!-- 办公电话 -->
                    <td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonInfo.fdWorkPhone') }
                    </td>
                    <td width="35%" class="inputwidth">
                        <xform:text property="fdWorkPhone" showStatus="edit" style="width:98%;" className="inputsgl" htmlElementProperties="placeholder='${ lfn:message('hr-staff:hrStaffPersonInfo.workPhoneNo.tips') }'"/>
                    </td>
                    <td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonInfo.fdOfficeLine') }
                    </td>
                    <td width="35%" class="inputwidth">
                        <xform:text showStatus="edit" property="fdOfficeLine" style="width:98%;" className="inputsgl" htmlElementProperties="placeholder='${ lfn:message('hr-staff:hrStaffPersonInfo.workPhoneNo.tips') }'"/>
                    </td>
                </tr>
                <tr>
                    <!-- 办公电话 -->
                    <td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonInfo.fdOfficeExtension') }
                    </td>
                    <td width="35%" class="inputwidth">
                        <xform:text showStatus="edit" property="fdOfficeExtension" style="width:98%;" className="inputsgl" htmlElementProperties="placeholder='${ lfn:message('hr-staff:hrStaffPersonInfo.workPhoneNo.tips') }'"/>
                    </td>
                    <!-- 邮箱 -->
                    <td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmail') }
                    </td>
                    <td width="35%" class="inputwidth">
                        <xform:text property="fdEmail" showStatus="edit" validators="email" className="inputsgl" required="true"/>
                    </td>
                </tr>
                <html:hidden property="fdOfficeAreaProvinceName"/>
                <html:hidden property="fdOfficeAreaCityName"/>
                <html:hidden property="fdOfficeAreaAreaName"/>
                <tr>
                    <!-- 手机 -->
                    <!-- 办公地点 -->
                    <td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonInfo.fdPrivateMailbox') }
                    </td>
                    <td width="35%" class="inputwidth">
                        <xform:text showStatus="edit" property="fdPrivateMailbox" style="width:98%;" className="inputsgl"/>
                    </td>
                    <td width="15%" class="td_normal_title" title='${ lfn:message('hr-staff:hrStaffPersonInfo.workPhoneNo.tips') }'>
						${ lfn:message('hr-staff:hrStaffEntry.fdMobileNo') }
                    </td>
                    <td width="35%" class="inputwidth">
                        <xform:text property="fdMobileNo" showStatus="edit" validators="phoneNumber uniqueMobileNo required" style="width:98%;" className="inputsgl" htmlElementProperties="placeholder='${ lfn:message('hr-staff:hrStaffPersonInfo.moblieNo.tips') }'"/>
                    </td>
                </tr>
                <tr>
                    <!-- 紧急联系人 -->
                    <td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmergencyContact') }
                    </td>
                    <td width="35%" class="inputwidth">
                        <xform:text showStatus="edit" property="fdEmergencyContact" className="inputsgl" required="true"/>
                    </td>

                    <!-- 紧急联系人电话 -->
                    <td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmergencyContactPhone') }
                    </td>
                    <td width="35%" class="inputwidth">
                        <xform:text showStatus="edit" property="fdEmergencyContactPhone" className="inputsgl" validators="phone" required="true"/>
                    </td>
                </tr>
                <tr>
                    <!-- 办公地点 -->
                    <td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonInfo.fdRelationsOfEmergencyContactAndEmployee') }
                    </td>
                    <td width="35%" class="inputwidth">
                        <xform:text showStatus="edit" property="fdRelationsOfEmergencyContactAndEmployee" style="width:98%;" className="inputsgl" required="true"/>
                    </td>
                    <!-- 办公电话 -->
                    <td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmergencyContactAddress') }
                    </td>
                    <td width="35%" class="inputwidth">
                        <xform:text showStatus="edit" property="fdEmergencyContactAddress" style="width:98%;" className="inputsgl"/>
                    </td>
                </tr>
				<%-- 引入动态属性 --%>
                <tr style="display:none">
                    <td colspan="4">
                        <table>
                            <c:import url="/hr/staff/hr_staff_person_info/edit/custom_fieldEdit.jsp" charEncoding="UTF-8"/>
                        </table>
                    </td>
                </tr>
            </table>
        </html:form>
        <%@ include file="/hr/staff/hr_staff_person_info/hrStaffPersonInfo_edit_script.jsp" %>
        <script>
            window.submitBtn = function () {
                //Com_Submit(document.hrStaffPersonInfoForm, 'update');
                var filed = $(document.hrStaffPersonInfoForm).serialize();
                var dataArr = filed.split("&");
                var data = {}
                $.each(dataArr, function (index, item) {
                    var itemData = item.split("=")
                    data[itemData[0]] = itemData.length > 1 ? decodeURIComponent(itemData[1]) : ""
                })
                data['type'] = "connect"
                if (_validator.validate()) {
                    $.ajax({
                        url: "<%=request.getContextPath()%>/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=updatePersonInfo&fdId=${hrStaffPersonInfoForm.fdId}",
                        method: 'post',
                        data: data,
                        success: function (res) {
                            if (res.status) {
                                window.parent.dialogObj.hide();
                                window.parent.location.reload();
                            }
                        }
                    })
                }
            }

        </script>
    </template:replace>
</template:include>