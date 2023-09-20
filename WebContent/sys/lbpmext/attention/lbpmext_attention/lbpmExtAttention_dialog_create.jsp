<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
    <template:include ref="default.dialog">
        <template:replace name="head">
	    	 <script>
					Com_IncludeFile("dialog.js|doclist.js|calendar.js");
					Com_IncludeFile("validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		    		Com_IncludeFile("validation.jsp", null, "jsp");
				</script>
        </template:replace>
        <template:replace name="content">
            <html:form action="/sys/lbpmext/attention/lbpmExtAttention.do" styleId="lbpmExtAttentionForm">
	            <div style="margin-top:20px">
	            	<table class="tb_simple" width="100%">
		                <tr>
		                    <td class="td_normal_title" width="20%">
		                        ${lfn:message('sys-lbpmext-attention:lbpmExtAttention.fdPerson')}
		                    </td>
		                    <td colspan="3" width="80%">
		                        <%-- 名称--%>
		                        <div id="_xform_fdPerson" _xform_type="text">
		                            <xform:address propertyName="fdPersonName" propertyId="fdPersonId" mulSelect="true" orgType="ORG_TYPE_PERSON" required="true" style="width:90%"></xform:address>
		                        </div>
		                    </td>
		                </tr>
	                </table>
	            </div>
                <html:hidden property="method_GET" />
            </html:form>
            <script type="text/javascript">
	            var validation = $KMSSValidation();//校验框架
	            
	            seajs.use(['lui/dialog','lui/jquery','lui/topic', 'lang!sys-ui'], function(dialog, $, topic, ui_lang){
	            	
		      	     //提交
		      		window.clickOk=function(){
		      	    	var formObj = document.lbpmExtAttentionForm;
		      	    	if(validation.validate()){
		      	    		Com_Submit(formObj,"save");
		      	    	}
		      		};
	            });
                
            </script>
        </template:replace>


    </template:include>