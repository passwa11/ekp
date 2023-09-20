<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
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
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/third/ctrip/resource/js/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/ctrip/third_ctrip_hotel/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/third/ctrip/third_ctrip_hotel/thirdCtripHotel.do">
    <div id="optBarDiv">
        <%-- <c:choose>
            <c:when test="${thirdCtripHotelForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.thirdCtripHotelForm, 'update');">
            </c:when>
            <c:when test="${thirdCtripHotelForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.thirdCtripHotelForm, 'save');">
            </c:when>
        </c:choose> --%>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('third-ctrip:table.thirdCtripHotel') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripHotel.fdCityId')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCityId" _xform_type="text">
                            <xform:text property="fdCityId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripHotel.fdCityEname')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCityEname" _xform_type="text">
                            <xform:text property="fdCityEname" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripHotel.fdCityCname')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCityCname" _xform_type="text">
                            <xform:text property="fdCityCname" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripHotel.fdCitySname')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCitySname" _xform_type="text">
                            <xform:text property="fdCitySname" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripHotel.fdProvinceId')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdProvinceId" _xform_type="text">
                            <xform:text property="fdProvinceId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripHotel.fdProvinceCname')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdProvinceCname" _xform_type="text">
                            <xform:text property="fdProvinceCname" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripHotel.fdProvinceEname')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdProvinceEname" _xform_type="text">
                            <xform:text property="fdProvinceEname" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripHotel.fdCountryId')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCountryId" _xform_type="text">
                            <xform:text property="fdCountryId" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripHotel.fdCountryEname')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCountryEname" _xform_type="text">
                            <xform:text property="fdCountryEname" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripHotel.fdCountryCname')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCountryCname" _xform_type="text">
                            <xform:text property="fdCountryCname" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>