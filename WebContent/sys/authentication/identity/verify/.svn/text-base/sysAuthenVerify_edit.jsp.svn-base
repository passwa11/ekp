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
                var smssent = "${lfn:message('sys-authentication-identity:sms.sent')}";
                var resendInSeconds = "${ lfn:message('sys-authentication-identity:resend.in.seconds')}";
                var getVerificationCode = "${lfn:message('sys-authentication-identity:get.verification.code')}";  
                Com_IncludeFile("sysAuthenVerify.js", "${LUI_ContextPath}/sys/authentication/identity/verify/", 'js', true);
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/sys/authentication/identity/verify/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/sys/authentication/identity/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
   </head>
   <body style="height: 220px;padding-left: 10px">
       <html:form action="/sys/authentication/verify/sysAuthenVerify.do" style="width:570px">
                        <div class='lui_form_title_frame'>
                            <div class='lui_form_subject'>
                              
                            </div>
                            <div class='lui_form_baseinfo'>

                            </div>
                        </div>
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="25%">
                                    ${lfn:message('sys-authentication-identity:sysAuthenVerify.fdVerifyPwd')}
                                </td>
                                <td width="75%">
                                    <%-- 输入校验密码--%>
                                    <input name="fdVerifyPwd" subject="${lfn:message('sys-authentication-identity:sysAuthenVerify.fdVerifyPwd')}" class="inputsgl" value="" type="password" validate="required" style="width:150px"><span class="txtstrong">*</span>
                                </td>
                            </tr>
                            <tr>
                               <td class="td_normal_title" width="25%">
                                    ${lfn:message('sys-authentication-identity:sysAuthenVerify.fdVerifyQpwd')}
                                </td>
                                <td width="75%">
                                    <%-- 确认校验密码--%>
                                           <input name="fdVerifyQpwd" subject="${lfn:message('sys-authentication-identity:sysAuthenVerify.fdVerifyQpwd')}" class="inputsgl" value="" type="password" validate="required" style="width:150px"><span class="txtstrong">*</span>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="25%">
                                    ${lfn:message('sys-authentication-identity:sysAuthenVerify.fdVerifyNumber')}
                                </td>
                                <td width="75%">
                                    <%-- 校验码--%>
                                        <xform:text property="fdVerifyNumber" showStatus="edit" subject="${lfn:message('sys-authentication-identity:sysAuthenVerify.fdVerifyNumber')}" required="true" style="width:150px;" />
                                        <img onclick="this.src='${LUI_ContextPath}/sys/authentication/identity/verify/vcode.jsp?xx='+Math.random()" style='cursor: pointer;' src='${LUI_ContextPath}/sys/authentication/identity/verify/vcode.jsp'>
                                </td>
                            </tr>
                             <tr>
								<td  colspan="2"  align="center">
							         <ui:button text="${lfn:message('button.save') }" order="2"  onclick="if(validateDetail()){ ajaxPostData('save','pwdAuthenVerify');}"></ui:button>
								</td>						
							</tr>
                        </table>
                <html:hidden property="fdId" />
                <html:hidden property="method_GET" />
            </html:form>
            <script type="text/javascript">
				window.onload=function(){
					   $(".tempTB").remove();
				   	   $(".lui_form_path_frame").remove();
				}
				 
			    function saveConfigAjax(){
					try{
						if(validateDetail()){
							//提交表单校验
							for(var i=0; i<Com_Parameter.event["submit"].length; i++){
								if(!Com_Parameter.event["submit"][i](document.sysAuthenVerifyForm,"save")){
									if (Com_Submit.ajaxCancelSubmit) {
										Com_Submit.ajaxCancelSubmit(document.sysAuthenVerifyForm);
									}
									Com_Parameter.isSubmit = false;
									return false;
								}
							}
							$.ajax({
								url:encodeURI('${LUI_ContextPath}/sys/authentication/verify/sysAuthenVerify.do?method=save&s_css=default&s_path=身份认证导航　>　身份验证信息　>　设置&s_seq=1'+"&"+ Math.random()),
								type: 'POST',
								dataType: 'text',
								data:$("form[name='sysAuthenVerifyForm']").serializeArray(),
								async: false,
								error: function(data){
								},
								success: function(data){
								}
						   });
						}
					}
					catch(e){
						console.log(e.name + ": " + e.message);
					}
				}
    		</script>
   </body>
</html>   

           
