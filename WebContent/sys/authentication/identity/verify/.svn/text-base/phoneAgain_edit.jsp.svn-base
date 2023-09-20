<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.landray.kmss.util.StringUtil,com.landray.kmss.util.SpringBeanUtil,
                com.landray.kmss.sys.authentication.identity.model.SysAuthenEntity,
                com.landray.kmss.sys.authentication.identity.service.ISysAuthenEntityService"%>
<%@page import="com.landray.kmss.sys.profile.model.PasswordSecurityConfig"%>                
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
        SysAuthenEntity sysAuthenEntity = ((ISysAuthenEntityService)SpringBeanUtil.getBean("sysAuthenEntityService")).saveAndGetEntity();
    	if(sysAuthenEntity!=null && StringUtil.isNotNull(sysAuthenEntity.getFdMobileNo())){
    		String mobileNo =sysAuthenEntity.getFdMobileNo();
    		String mobileNoShow = mobileNo.substring(0,3)+"****"+mobileNo.substring(7);
    		request.setAttribute("mobileNoShow", mobileNoShow);
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
                		.lui_form_subject {
						    margin-bottom: 10px;
						    padding: 0;
						    text-align: center;
						    color: #333;
						    font-size: 24px;
						    line-height: 30px;
						}
                		
            </style>
             
           		 		 
            <script type="text/javascript">
                var formInitData = {

                };
                var messageInfo = {

                };

                var initData = {
                    contextPath: '${LUI_ContextPath}',
                };
          
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                 Com_IncludeFile("form.js");
                 var INTERVAL_TIME = ${time};
                 var smssent = "${lfn:message('sys-authentication-identity:sms.sent')}";
                 var resendInSeconds = "${ lfn:message('sys-authentication-identity:resend.in.seconds')}";
                 var getVerificationCode = "${lfn:message('sys-authentication-identity:get.verification.code')}";  
                Com_IncludeFile("sysAuthenVerify.js", "${LUI_ContextPath}/sys/authentication/identity/verify/", 'js', true);
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/sys/authentication/identity/verify/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/sys/authentication/identity/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
       </head>
       <body style="height: 200px">
          <div class='lui_form_subject' id="titleSHow" style="width:550px;height: 150px">
                                ${lfn:message('sys-authentication-identity:you.have.bound.your.mobile.number')}<br>
                                <ui:button text="${lfn:message('sys-authentication-identity:rebind.phone.number')}" onclick="showStatus()" />
                                
         </div>
          <html:form action="/sys/authentication/verify/sysAuthenVerify.do"  style="display:none;width:590px">
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="20%">
                                     ${lfn:message('sys-authentication-identity:currently.bound.mobile.phone')}
                                </td>
                                <td width="80%">
                                     ${mobileNoShow}
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="20%">
                                     ${lfn:message('sys-authentication-identity:new.mobile.number')}
                                </td>
                                <td width="80%">
                                    <%-- 输入手机号码--%>
                                    <input name="fdPhone" subject="${lfn:message('sys-authentication-identity:phone.number')}" class="inputsgl" value="" type="text" validate="maxLength(11) required phone" style="width:150px"
                                    __validate_serial="_validate_1"><span class="txtstrong">*</span>
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
                             <tr id="sumbmitId" style="display:none;">
								<td  colspan="2"  align="center">
							         <ui:button text="${lfn:message('button.save') }" order="2" onclick="if(validateDetail()){ ajaxPostData('updatePhone','phoneAuthenVerify');}"></ui:button>
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
			    
			    function showStatus(){
			    	$("#titleSHow").hide();
			    	$("form[name='sysAuthenVerifyForm']").show()
			    	$("#sumbmitId").show();
			    }
			    
			  </script>
			   
       </body>
    </html>
    

 
  