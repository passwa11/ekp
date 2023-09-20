<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<table class="tb_normal feetable" id="TABLE_DocList_fdConfig_Form" align="center" width="100%">
    <fssc:ifModuleExists path="/fssc/ctrip/;/fssc/alibtrip/;">
	<tr>
        <td class="td_normal_title" colspan="2">
            ${lfn:message('fssc-fee:fsscFeeTemplate.fdMobileCtrip.fdServiceType')}
        </td>
        <td colspan="9">
            <%-- 携程商旅--%>
            <div id="_xform_fdServiceType" _xform_type="checkbox">
                <xform:checkbox property="fdServiceType">
                	<xform:enumsDataSource enumsType="fssc_fee_mobile_config_service_type"></xform:enumsDataSource>
                </xform:checkbox>
            </div>
        </td>
    </tr>
    </fssc:ifModuleExists>
    <tr align="center" class="tr_normal_title">
        <td style="width:30px;">
            ${lfn:message('page.serial')}
        </td>
        <td width="15%">
            ${lfn:message('fssc-fee:fsscFeeMobileConfig.fdFieldName')}
        </td>
        <td width="10%">
            ${lfn:message('fssc-fee:fsscFeeMobileConfig.fdFieldType')}
        </td>
        <td width="10%">
            ${lfn:message('fssc-fee:fsscFeeMobileConfig.fdFieldPosition')}
        </td>
        <td width="15%">
            ${lfn:message('fssc-fee:fsscFeeMobileConfig.fdTemplateField')}
        </td>
        <td width="10%">
            ${lfn:message('fssc-fee:fsscFeeMobileConfig.fdValidate')}
        </td>
        <td width="10%">
            ${lfn:message('fssc-fee:fsscFeeMobileConfig.fdShowStatus')}
        </td>
        <td width="10%">
            ${lfn:message('fssc-fee:fsscFeeMobileConfig.fdInitOption')}
        </td>
        <td width="15%">
            ${lfn:message('fssc-fee:fsscFeeMobileConfig.fdBaseOn')}
        </td>
        <td width="20%">
            ${lfn:message('fssc-fee:fsscFeeMobileConfig.fdDataService')}
        </td>
    </tr>
    <c:forEach var="fsscFeeMobileConfigForm" items="${fsscFeeTemplateForm.fdConfig_Form }" varStatus="vstatus">
    <tr KMSS_IsContentRow="1">
        <td align="center" KMSS_IsRowIndex="1">
            ${vstatus.index+1 }
        </td>
        <td align="center">
            <div id="_xform_fdConfig_Form[${vstatus.index}].fdFieldName" _xform_type="text">
                <xform:text property="fdConfig_Form[${vstatus.index}].fdFieldName" required="true" subject="${lfn:message('fssc-fee:fsscFeeMobileConfig.fdFieldName')}" style="width:90%;"/>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdConfig_Form[${vstatus.index}].fdFieldType" _xform_type="text">
                <xform:select property="fdConfig_Form[${vstatus.index}].fdFieldType" onValueChange="changeFieldType" required="true" subject="${lfn:message('fssc-fee:fsscFeeMobileConfig.fdFieldType')}" style="width:90%;">
                    <xform:enumsDataSource enumsType="fssc_fee_mobile_config_field_type"/>
                </xform:select>
            </div>
            <div <c:if test="${fsscFeeMobileConfigForm.fdFieldType!='4' }">style="display:none;"</c:if>>
              	${lfn:message('fssc-fee:fsscFeeMobileConfig.fdOrgType') }:
              	<xform:radio property="fdConfig_Form[${vstatus.index}].fdOrgType">
              		<xform:simpleDataSource value="dept" bundle="fssc-fee" textKey="fsscFeeMobileConfig.fdOrgType.dept"/>
                	<xform:simpleDataSource value="person" bundle="fssc-fee" textKey="fsscFeeMobileConfig.fdOrgType.person"/>
              	</xform:radio>
              	<br>
              	${lfn:message('fssc-fee:fsscFeeMobileConfig.fdIsMulti') }:
              	<xform:checkbox property="fdConfig_Form[${vstatus.index}].fdIsMulti">
              		<xform:simpleDataSource value="true" textKey="message.yes"/>
              	</xform:checkbox>
            </div>
            <div <c:if test="${fsscFeeMobileConfigForm.fdFieldType!='5' }">style="display:none;"</c:if> class="detailInfo">
               	${lfn:message('fssc-fee:fsscFeeMobileConfig.fdLeftShow') }:
               	<xform:dialog propertyId="fdConfig_Form[${vstatus.index}].fdLeftShowId" propertyName="fdConfig_Form[${vstatus.index}].fdLeftShow"  subject="${lfn:message('fssc-fee:fsscFeeMobileConfig.fdLeftShow')}" style="width:90%;">
                    selectFormula('fdConfig_Form[${vstatus.index}].fdLeftShowId','fdConfig_Form[${vstatus.index}].fdLeftShow')
                </xform:dialog>
               	<br>
               	${lfn:message('fssc-fee:fsscFeeMobileConfig.fdRightShow') }:
               	<xform:dialog propertyId="fdConfig_Form[${vstatus.index}].fdRightShowId" propertyName="fdConfig_Form[${vstatus.index}].fdRightShow" subject="${lfn:message('fssc-fee:fsscFeeMobileConfig.fdRightShow')}" style="width:90%;">
                    selectFormula('fdConfig_Form[${vstatus.index}].fdRightShowId','fdConfig_Form[${vstatus.index}].fdRightShow')
                </xform:dialog>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdConfig_Form[${vstatus.index}].fdFieldPosition" _xform_type="text">
                <xform:select property="fdConfig_Form[${vstatus.index}].fdFieldPosition" required="true" subject="${lfn:message('fssc-fee:fsscFeeMobileConfig.fdFieldPosition')}" style="width:90%;">
                    <xform:enumsDataSource enumsType="fssc_fee_mobile_config_field_position"/>
                </xform:select>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdConfig_Form[${vstatus.index}].fdTemplateField" _xform_type="dialog">
                <xform:dialog propertyId="fdConfig_Form[${vstatus.index}].fdTemplateFieldId" propertyName="fdConfig_Form[${vstatus.index}].fdTemplateField" required="true" subject="${lfn:message('fssc-fee:fsscFeeMobileConfig.fdTemplateField')}" style="width:90%;">
                    selectFormula('fdConfig_Form[${vstatus.index}].fdTemplateFieldId','fdConfig_Form[${vstatus.index}].fdTemplateField')
                </xform:dialog>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdConfig_Form[${vstatus.index}].fdValidate" _xform_type="text">
                <xform:checkbox property="fdConfig_Form[${vstatus.index}].fdValidate" showStatus="view" subject="${lfn:message('fssc-fee:fsscFeeMobileConfig.fdValidate')}">
                	<xform:enumsDataSource enumsType="fssc_fee_mobile_config_validate"/>
                </xform:checkbox>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdConfig_Form[${vstatus.index}].fdShowStatus" _xform_type="text">
                <xform:radio property="fdConfig_Form[${vstatus.index}].fdShowStatus" required="true" subject="${lfn:message('fssc-fee:fsscFeeMobileConfig.fdShowStatus')}" style="width:90%;">
                    <xform:enumsDataSource enumsType="fssc_fee_mobile_config_show_status"/>
                </xform:radio>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdConfig_Form[${vstatus.index}].fdInitOption" _xform_type="text">
                <xform:select property="fdConfig_Form[${vstatus.index}].fdInitOption" required="true" subject="${lfn:message('fssc-fee:fsscFeeMobileConfig.fdInitOption')}" style="width:90%;">
                    <xform:enumsDataSource enumsType="fssc_fee_mobile_config_init_option"/>
                </xform:select>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdConfig_Form[${vstatus.index}].fdBaseOn" _xform_type="dialog">
                <xform:dialog propertyId="fdConfig_Form[${vstatus.index}].fdBaseOnId" propertyName="fdConfig_Form[${vstatus.index}].fdBaseOn" required="true" subject="${lfn:message('fssc-fee:fsscFeeMobileConfig.fdBaseOn')}" style="width:90%;">
                    selectFormula('fdConfig_Form[${vstatus.index}].fdBaseOnId','fdConfig_Form[${vstatus.index}].fdBaseOn')
                </xform:dialog>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdConfig_Form[${vstatus.index}].fdDataService" _xform_type="text">
                <xform:text property="fdConfig_Form[${vstatus.index}].fdDataService" subject="${lfn:message('fssc-fee:fsscFeeMobileConfig.fdDataService')}" style="width:90%;"/>
            </div>
        </td>
    </tr>
    </c:forEach>
</table>
<input type="hidden" name="fdConfig_Flag" value="1">
<script>
	DocList_Info.push('TABLE_DocList_fdConfig_Form');
</script>
