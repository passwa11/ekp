<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/eas/fssc_eas_switch/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/eas/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/eas/fssc_eas_switch/fsscEasSwitch.do" styleId="fsscEasSwitchForm">
    <div id="optBarDiv">
        <input type="button" value="${ lfn:message('button.save') }" onclick="updateSwitch();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-eas:table.fsscEasSwitch') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
            	<%-- <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-eas:fsscEasSwitch.fdIp')}
                    </td>
                    <td width="35%">
                        地址
                        <div id="_xform_fdUserName" _xform_type="text">
                            <xform:text property="fdIp" value="${fromName.fdIp}" required="true" subject="${lfn:message('fssc-eas:fsscEasSwitch.fdIp')}" showStatus="edit" style="width:95%;" />
                            <br/><span style="color:red;">${lfn:message('fssc-eas:fsscEasSwitch.fdIp.message')}</span>
                        </div>
                    </td>
                </tr> --%>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-eas:fsscEasSwitch.fdUserName')}
                    </td>
                    <td width="35%">
                        <%-- 用户名--%>
                        <div id="_xform_fdUserName" _xform_type="text">
                            <xform:text property="fdUserName" value="${fromName.fdUserName}" required="true" subject="${lfn:message('fssc-eas:fsscEasSwitch.fdUserName')}" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-eas:fsscEasSwitch.fdPassword')}
                    </td>
                    <td width="35%">
                        <%-- 密码--%>
                        <div id="_xform_fdPassword" _xform_type="text">
                            <xform:text property="fdPassword" value="${fromName.fdPassword}" required="true" subject="${lfn:message('fssc-eas:fsscEasSwitch.fdPassword')}" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-eas:fsscEasSwitch.fdSlnName')}
                    </td>
                    <td width="35%">
                        <%-- 解决方案--%>
                        <div id="_xform_fdSlnName" _xform_type="text">
                            <xform:text property="fdSlnName" value="${fromName.fdSlnName}" required="true" subject="${lfn:message('fssc-eas:fsscEasSwitch.fdSlnName')}" showStatus="edit" style="width:95%;" />
                            <br/><span style="color:red;">${lfn:message('fssc-eas:fsscEasSwitch.fdSlnName.message')}</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-eas:fsscEasSwitch.fdDcName')}
                    </td>
                    <td width="35%">
                        <%-- 数据中心--%>
                        <div id="_xform_fdDcName" _xform_type="text">
                            <xform:text property="fdDcName" value="${fromName.fdDcName}" required="true" subject="${lfn:message('fssc-eas:fsscEasSwitch.fdDcName')}" showStatus="edit" style="width:95%;" />
                            <br/><span style="color:red;">${lfn:message('fssc-eas:fsscEasSwitch.fdDcName.message')}</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-eas:fsscEasSwitch.fdLanguage')}
                    </td>
                    <td width="35%">
                        <%-- 语言--%>
                        <div id="_xform_fdLanguage" _xform_type="text">
                            <xform:text property="fdLanguage" value="${fromName.fdLanguage}" required="true" subject="${lfn:message('fssc-eas:fsscEasSwitch.fdLanguage')}" showStatus="edit" style="width:95%;" />
                            <br/><span style="color:red;">${lfn:message('fssc-eas:fsscEasSwitch.fdLanguage.message')}</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-eas:fsscEasSwitch.fdDbType')}
                    </td>
                    <td width="35%">
                        <%-- 数据库类型--%>
                        <div id="_xform_fdDbType" _xform_type="text">
                            <xform:text property="fdDbType" value="${fromName.fdDbType}" required="true" subject="${lfn:message('fssc-eas:fsscEasSwitch.fdDbType')}" showStatus="edit" style="width:95%;" />
                            <br/><span style="color:red;">${lfn:message('fssc-eas:fsscEasSwitch.fdDbType.message')}</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-eas:fsscEasSwitch.fdAuthPattern')}
                    </td>
                    <td width="35%">
                        <%-- 验证类型--%>
                        <div id="_xform_fdAuthPattern" _xform_type="text">
                            <xform:text property="fdAuthPattern" value="${fromName.fdAuthPattern}" showStatus="edit" style="width:95%;" />
                            <br/><span style="color:red;">${lfn:message('fssc-eas:fsscEasSwitch.fdAuthPattern.message')}</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-eas:fsscEasSwitch.fdLoginWsdlUrl')}
                    </td>
                    <td width="35%">
                        <%-- 登录验证wsdl路径--%>
                        <div id="_xform_fdLoginWsdlUrl" _xform_type="text">
                            <xform:text property="fdLoginWsdlUrl" value="${fromName.fdLoginWsdlUrl}" required="true" subject="${lfn:message('fssc-eas:fsscEasSwitch.fdLoginWsdlUrl')}" showStatus="edit" style="width:91%;" />?wsdl
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-eas:fsscEasSwitch.fdImportVoucherWsdlUrl')}
                    </td>
                    <td width="35%">
                        <%-- 传输凭证wsdl路径--%>
                        <div id="_xform_fdImportVoucherWsdlUrl" _xform_type="text">
                            <xform:text property="fdImportVoucherWsdlUrl" value="${fromName.fdImportVoucherWsdlUrl}" required="true" subject="${lfn:message('fssc-eas:fsscEasSwitch.fdImportVoucherWsdlUrl')}" showStatus="edit" style="width:91%;" />?wsdl
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </center>
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
    </script>
    <script>
            //保存配置信息
            function updateSwitch() {
                seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
                    $.ajax({
                        url :'${LUI_ContextPath}/fssc/eas/fssc_eas_switch/fsscEasSwitch.do?method=updateSwitch',
                        type : 'POST',
                        dataType : 'json',
                        async : false,
                        data : $("#fsscEasSwitchForm").serialize(),
                        success:function(data) {
                            if (data == true) {
                                dialog.success("${lfn:message('return.optSuccess')}");
                            } else {
                                dialog.failure("${lfn:message('return.optFailure')}");
                            }
                        },
                        error: function() {
                            dialog.failure("${lfn:message('return.optFailure')}");
                        }
                    });
                });
            };
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
