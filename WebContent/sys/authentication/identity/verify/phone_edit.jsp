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
        } 
        int time = com.landray.kmss.sys.profile.model.PasswordSecurityConfig.newInstance().getReSentIntervalTime();
        request.setAttribute("time", time);
        %>
<html>
   <head>
      <meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="renderer" content="webkit" />
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<script type="text/javascript">
		seajs.use(['theme!form']);
		Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
		Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
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
                var initData = {
                    contextPath: '${LUI_ContextPath}',
                };
                var smssent = "${lfn:message('sys-authentication-identity:sms.sent')}";
                var resendInSeconds = "${ lfn:message('sys-authentication-identity:resend.in.seconds')}";
                var getVerificationCode = "${lfn:message('sys-authentication-identity:get.verification.code')}";
                var INTERVAL_TIME = ${time};
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form.js");
                Com_IncludeFile("sysAuthenVerify.js", "${LUI_ContextPath}/sys/authentication/identity/verify/", 'js', true);
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/sys/authentication/identity/verify/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/sys/authentication/identity/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
               
            </script>
   </head>
   <body>
      <html:form action="/sys/authentication/verify/sysAuthenVerify.do" style="width:590px">
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="20%">
                                      ${lfn:message('sys-authentication-identity:set.binding.mobile.phone')}
                                </td>
                                <td width="80%">
                                    <%-- 设置绑定手机--%>
                                    <input name="fdPhone" subject="${lfn:message('sys-authentication-identity:phone.number')}" class="inputsgl" value="" type="text" validate="maxLength(11) required phone"  style="width:150px"><span class="txtstrong">*</span>
                                </td>
                            </tr>
                            <tr>
									<td class="td_normal_title" width="15%">${lfn:message('sys-authentication-identity:verification.Code')}</td>
									<td width="35%" colspan="3"><input class="muiInput" name="vcode" id="m_code"
										placeholder="${lfn:message('sys-authentication-identity:get.sms.verification.code')}" subject="${lfn:message('sys-authentication-identity:sms.verification.code')}" disabled="disabled" validate="required maxLength(6)"/>
										<input type="button" class="com_bgcolor_d lui_verify_form2"
	                                      value="${lfn:message('sys-authentication-identity:get.sms.verification.code')}"  id="button_sendCode" onclick="sendMobileValidationCode(1);" /> 
	                                      <span style="padding-left: 30px;"><font color="red" size="3pt" id="msg_block">${errMsg}</font></span>
									</td>
								</tr>
                            <tr>
								<td  colspan="2"  align="center">
							         <ui:button text="${lfn:message('button.save') }" order="2" onclick="if(validateDetail()){ ajaxPostData('savePhone','phoneAuthenVerify');}"></ui:button>
								</td>						
							</tr>
                        </table>
                <html:hidden property="fdId" />
                <html:hidden property="method_GET" />
            </html:form>
            <c:import url="/sys/organization/sys_org_person/common_mobileNo_check.jsp" charEncoding="UTF-8">
	            <c:param name="formName" value="sysAuthenVerifyForm" />
             </c:import>  
			  <script type="text/javascript">
			    
			   window.onload=function(){
				   $(".tempTB").remove();
			   	   $(".lui_form_path_frame").remove();
			   }
			
			  </script>
   </body>
</html>
   