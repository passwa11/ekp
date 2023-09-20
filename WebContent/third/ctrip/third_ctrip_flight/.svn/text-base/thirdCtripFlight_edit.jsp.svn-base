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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/ctrip/third_ctrip_flight/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/third/ctrip/third_ctrip_flight/thirdCtripFlight.do">
    <div id="optBarDiv">
        <%-- <c:choose>
            <c:when test="${thirdCtripFlightForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.thirdCtripFlightForm, 'update');">
            </c:when>
            <c:when test="${thirdCtripFlightForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.thirdCtripFlightForm, 'save');">
            </c:when>
        </c:choose> --%>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('third-ctrip:table.thirdCtripFlight') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripFlight.fdCityId')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCityId" _xform_type="text">
                            <xform:text property="fdCityId" showStatus="edit" validators=" digits" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripFlight.fdCityCode')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCityCode" _xform_type="text">
                            <xform:text property="fdCityCode" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripFlight.fdCityCname')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCityCname" _xform_type="text">
                            <xform:text property="fdCityCname" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripFlight.fdCityEname')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCityEname" _xform_type="text">
                            <xform:text property="fdCityEname" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripFlight.fdCityPinyin')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCityPinyin" _xform_type="text">
                            <xform:text property="fdCityPinyin" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripFlight.fdCitySname')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCitySname" _xform_type="text">
                            <xform:text property="fdCitySname" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripFlight.fdProvinceId')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdProvinceId" _xform_type="text">
                            <xform:text property="fdProvinceId" showStatus="edit" validators=" digits" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripFlight.fdCountryId')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCountryId" _xform_type="text">
                            <xform:text property="fdCountryId" showStatus="edit" validators=" digits" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripFlight.fdContryCode')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdContryCode" _xform_type="text">
                            <xform:text property="fdContryCode" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripFlight.fdContryCname')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdContryCname" _xform_type="text">
                            <xform:text property="fdContryCname" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripFlight.fdCountryEname')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCountryEname" _xform_type="text">
                            <xform:text property="fdCountryEname" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripFlight.fdDuplicateCityNameFlag')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdDuplicateCityNameFlag" _xform_type="radio">
                            <xform:radio property="fdDuplicateCityNameFlag" htmlElementProperties="id='fdDuplicateCityNameFlag'" showStatus="edit" validators=" digits">
                                <xform:enumsDataSource enumsType="third_ctrip_city_flag" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripFlight.fdPoiType')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdPoiType" _xform_type="radio">
                            <xform:radio property="fdPoiType" htmlElementProperties="id='fdPoiType'" showStatus="edit" validators=" digits">
                                <xform:enumsDataSource enumsType="third_ctrip_poi" />
                            </xform:radio>
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