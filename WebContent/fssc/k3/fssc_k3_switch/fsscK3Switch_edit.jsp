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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/k3/fssc_k3_switch/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/k3/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/k3/fssc_k3_switch/fsscK3Switch.do" styleId="fsscK3SwitchForm">
    <div id="optBarDiv">
        <input type="button" value="${ lfn:message('button.save') }" onclick="updateSwitch();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-k3:table.fsscK3Switch') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                	<td class="td_normal_title" align="center" width="5%">
			            ${lfn:message('page.serial')}
			        </td>
                	<td class="td_normal_title" align="center" width="10%">
                        ${lfn:message('fssc-k3:fsscK3Switch.fdCostItem')}
                    </td>
                    <td class="td_normal_title" align="center" width="10%">
                        ${lfn:message('fssc-k3:fsscK3Switch.fdCode')}
                    </td>
                    
                    <td class="td_normal_title" align="center" width="10%">
                        ${lfn:message('fssc-k3:fsscK3Switch.fdName')}
                    </td>
                </tr>
                <c:forEach items="${fromList}" var="fdDetail" varStatus="vstatus">
			        <tr KMSS_IsContentRow="1">
			            <td align="center">
			                ${vstatus.index+1}
			            </td>
				    	<td align="center">
			                <xform:text property="fdDetail.${vstatus.index}.fdCostItem" showStatus="view" value="${fdDetail.fdCostItem}" />
			            </td>
			            <td align="center">
			                <xform:text property="fdDetail.${vstatus.index}.fdCode" showStatus="edit" value="${fdDetail.fdCode}" />
			            </td>
			            <td align="center">
			                <xform:text property="fdDetail.${vstatus.index}.fdName" showStatus="edit" value="${fdDetail.fdName}" />
			            </td>      
			        </tr>
    			</c:forEach>
            </table>
            <span style="color:red;"><font size="3">${lfn:message('fssc-k3:fsscK3Switch.message')}</font></span>
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
                        url :'${LUI_ContextPath}/fssc/k3/fssc_k3_switch/fsscK3Switch.do?method=updateSwitch',
                        type : 'POST',
                        dataType : 'json',
                        async : false,
                        data : $("#fsscK3SwitchForm").serialize(),
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
