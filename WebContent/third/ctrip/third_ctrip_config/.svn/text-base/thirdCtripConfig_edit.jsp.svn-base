<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/ctrip/third_ctrip_config/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>
<html:form action="/third/ctrip/third_ctrip_config/thirdCtripConfig.do">
	<div class="lui_list_operation">
		<div style="float:right">
             <ui:toolbar count="3">
				<c:choose>
		            <c:when test="${thirdCtripConfigForm.method_GET=='edit'}">
		                <ui:button text="${lfn:message('button.update')}" onclick="check('update');" order="1" />
		            </c:when>
		            <c:when test="${thirdCtripConfigForm.method_GET=='add'}">
		                <ui:button text="${lfn:message('button.save')}" onclick="check('save');" order="1" />
		            </c:when>
		        </c:choose>
		        <ui:button text="${lfn:message('third-ctrip:thirdCtripConfig.clean.time')}" onclick="cleanTime();" order="3" />
		        <ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();" order="2"/>
             </ui:toolbar>
	     </div>
     </div>
    <h2 align="center" style="margin:10px 0">
    <p class="txttitle">${ lfn:message('third-ctrip:table.thirdCtripConfig') }</p>
    </h2>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripConfig.fdName')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:65%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripConfig.fdAppKey')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdAppKey" _xform_type="text">
                            <xform:text property="fdAppKey" showStatus="edit" style="width:95%;" />
                        </div>
                        <div class="txtstrong">${lfn:message('third-ctrip:thirdCtripConfig.filed.note')}</div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripConfig.fdAppSecurity')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdAppSecurity" _xform_type="text">
                            <xform:text property="fdAppSecurity" showStatus="edit" style="width:95%;" />
                        </div>
                        <div class="txtstrong">${lfn:message('third-ctrip:thirdCtripConfig.filed.note')}</div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripConfig.fdCorpId')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCorpId" _xform_type="text">
                            <xform:text property="fdCorpId" showStatus="edit" style="width:95%;" />
                        </div>
                        <div class="txtstrong">${lfn:message('third-ctrip:thirdCtripConfig.filed.note')}</div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripConfig.fdIsAvailable')}
                    </td>
                    <td width="35%">
                        <ui:switch property="fdIsAvailable" onValueChange="" checkVal="true" unCheckVal="false" checked="${thirdCtripConfigForm.fdIsAvailable}"
							enabledText="${lfn:message('third-ctrip:thirdCtripConfig.fdIsAvailable.e')}" disabledText="${lfn:message('third-ctrip:thirdCtripConfig.fdIsAvailable.d')}"></ui:switch>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripConfig.fdIsSysnch')}
                    </td>
                    <td colspan="3" width="85.0%">
                       <ui:switch property="fdIsSysnch" onValueChange="sysnch(this);" checkVal="true" unCheckVal="false" checked="${thirdCtripConfigForm.fdIsSysnch}"
						 enabledText="${lfn:message('third-ctrip:thirdCtripConfig.fdIsSysnch.e')}" disabledText="${lfn:message('third-ctrip:thirdCtripConfig.fdIsSysnch.d')}"></ui:switch>
                    </td>
                </tr>
                <tr id="dl" <c:if test="${thirdCtripConfigForm.fdIsSysnch=='false' }">style="display: none"</c:if>>
                    <td colspan="4" width="100%">
                        <table class="tb_normal" width="100%" id="TABLE_DocList_fdAccount_Form" align="center" tbdraggable="true">
                            <tr align="center" class="tr_normal_title">
                                <td style="width:6%;">
                                    ${lfn:message('page.serial')}
                                </td>
                                <td align="center" width="54%">
                                    ${lfn:message('third-ctrip:thirdCtripAccount.fdAccount')}
                                </td>
                                <td style="width:32%;">
                                    ${lfn:message('third-ctrip:thirdCtripAccount.fdName')}
                                    <div class="txtstrong">${lfn:message('third-ctrip:thirdCtripConfig.filed.note1')}</div>
                                </td>
                                <td style="width:8%;">
                                	${lfn:message('list.operation')}
                                </td>
                            </tr>
                            <tr KMSS_IsReferRow="1" style="display:none;">
                                <td align="center" KMSS_IsRowIndex="1">
                                    !{index}
                                </td>
                                <td align="left">
                                    <input type="hidden" name="fdAccount_Form[!{index}].fdId" value="" disabled="true" />
                                    <div id="_xform_fdAccount_Form[!{index}].fdAccountIds" _xform_type="address">
                                        <xform:address propertyId="fdAccount_Form[!{index}].fdAccountIds" propertyName="fdAccount_Form[!{index}].fdAccountNames" mulSelect="true" orgType="ORG_TYPE_ORGORDEPT|ORG_TYPE_PERSON" showStatus="edit" subject="${lfn:message('third-ctrip:thirdCtripAccount.fdAccount')}"
                                         style="width:95%;" /><span class="txtstrong">*</span>
                                    </div>
                                </td>
                                <td align="center">
                                    <div id="_xform_fdAccount_Form[!{index}].fdName" _xform_type="text">
                                        <xform:text property="fdAccount_Form[!{index}].fdName" showStatus="edit" subject="${lfn:message('third-ctrip:thirdCtripAccount.fdName')}" validators=" maxLength(100)" style="width:95%;" /><span class="txtstrong">*</span>
                                    </div>
                                </td>
                                <td align="center">
                                    <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
                                    </a>
                                    &nbsp;
                                    <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                                    </a>
                                </td>
                            </tr>
                            <c:forEach items="${thirdCtripConfigForm.fdAccount_Form}" var="fdAccount_FormItem" varStatus="vstatus">
                                <tr KMSS_IsContentRow="1">
                                    <td align="center">
                                        ${vstatus.index+1}
                                    </td>
                                    <td align="left">
                                        <input type="hidden" name="fdAccount_Form[${vstatus.index}].fdId" value="${fdAccount_FormItem.fdId}" />
                                        <div id="_xform_fdAccount_Form[${vstatus.index}].fdAccountIds" _xform_type="address">
                                            <xform:address propertyId="fdAccount_Form[${vstatus.index}].fdAccountIds" propertyName="fdAccount_Form[${vstatus.index}].fdAccountNames" mulSelect="true" orgType="ORG_TYPE_ORGORDEPT|ORG_TYPE_PERSON" showStatus="edit" subject="${lfn:message('third-ctrip:thirdCtripAccount.fdAccount')}"
                                            style="width:95%;" /><span class="txtstrong">*</span>
                                        </div>
                                    </td>
                                    <td align="center">
                                        <div id="_xform_fdAccount_Form[${vstatus.index}].fdName" _xform_type="text">
                                            <xform:text property="fdAccount_Form[${vstatus.index}].fdName" showStatus="edit" subject="${lfn:message('third-ctrip:thirdCtripAccount.fdName')}" validators=" maxLength(100)" style="width:95%;" /><span class="txtstrong">*</span>
                                        </div>
                                    </td>
                                    <td align="center">
                                        <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
                                            <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
                                        </a>
                                        &nbsp;
                                        <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                                            <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr type="optRow" class="tr_normal_opt" invalidrow="true">
                                <td colspan="5">
                                    <a href="javascript:void(0);" onclick="DocList_AddRow();" id="addrow">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_add.png" border="0" />
                                    </a>
                                    &nbsp;&nbsp; &nbsp;&nbsp;
                                    <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(-1);">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_up.png" border="0" />
                                    </a>
                                    &nbsp;&nbsp; &nbsp;&nbsp;
                                    <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(1);">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_down.png" border="0" />
                                    </a>
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                        <input type="hidden" name="fdAccount_Flag" value="1">
                        <script>
                            Com_IncludeFile("doclist.js");
                        </script>
                        <script>
                            DocList_Info.push('TABLE_DocList_fdAccount_Form');
                        </script>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripConfig.docCreateTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripConfig.docAlteror')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${thirdCtripConfigForm.docAlterorId}" personName="${thirdCtripConfigForm.docAlterorName}" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripConfig.docAlterTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterTime" _xform_type="datetime">
                            <xform:datetime property="docAlterTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ctrip:thirdCtripConfig.docCreator')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${thirdCtripConfigForm.docCreatorId}" personName="${thirdCtripConfigForm.docCreatorName}" />
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="fdCleanTime" value=""/>
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
    </script>
    <script>
    	function sysnch(val){
    		var synch = $("input[name='fdIsSysnch']").val();
    		if("false"==synch){
    			$("#dl").hide();
    		}else{
    			$("#dl").show();
    		}
    	}
    	function cleanTime(){
    		var url = '<c:url value="/third/ctrip/third_ctrip_config/thirdCtripConfig.do?method=updateTime" />'+"&fdId=${thirdCtripConfigForm.fdId}";
    		$.ajax({
    		   type: "POST",
    		   url: url,
    		   async:false,
    		   dataType: "json",
    		   success: function(data){
    				alert(data.errmsg);
    	   		}
    		});
    	}
    	function checkForm(){
    		var flag = true;
    		var ofdAppKey = '${thirdCtripConfigForm.fdAppKey}';
    		var fdAppKey = $("input[name='fdAppKey']").val();
    		if(fdAppKey!=ofdAppKey){
    			flag = false;
    		}
    		
    		if(flag){
	    		var ofdAppSecurity = '${thirdCtripConfigForm.fdAppSecurity}';
	    		var fdAppSecurity = $("input[name='fdAppSecurity']").val();
	    		if(fdAppSecurity!=ofdAppSecurity){
	    			flag = false;    			
	    		}
    		}
    		
    		if(flag){
    			var ofdCorpId = '${thirdCtripConfigForm.fdCorpId}';
    			var fdCorpId = $("input[name='fdCorpId']").val();
	    		if(fdCorpId!=ofdCorpId){
	    			flag = false;
	    		}
    		}
    		
    		if(flag){
    			var ofdIsAvailable = '${thirdCtripConfigForm.fdIsAvailable}';
	    		var fdIsAvailable = $("input[name='fdIsAvailable']").val();
	    		if(ofdIsAvailable=="false"&&fdIsAvailable=="true"){
	    			flag = false;
	    		}
    		}
    		
    		if(flag){
    			var ofdIsSysnch = '${thirdCtripConfigForm.fdIsSysnch}';
    			var fdIsSysnch = $("input[name='fdIsSysnch']").val();
	    		if(ofdIsSysnch=="false"&&fdIsSysnch=="true"){
	    			flag = false;
	    		}
    		}

    		if(flag){
	    		var size = parseInt('${cas}');
	    		var len = $("input[name$='.fdAccountIds']").length;
	    		if(size!=len){
	    			flag = false;
	    		}
    		}
    		
    		if(flag){
	   			var ofdAccountIds = '${fdAccountIds}'.split(";");
	   			var fdAccountId = "";
    			$("input[name$='.fdAccountIds']").each(function(i,v){
       				if(i==0){
       					fdAccountId = v.value;
       				}else{
       					fdAccountId += ";"+v.value;
       				}
           		});
    			var fdAccountIds = fdAccountId.split(";");
    			if(ofdAccountIds.length!=fdAccountIds.length){
    				flag = false;
    			}
    			if(flag){
	    			var size = ofdAccountIds.length;
    				for(var i=0;i<size;i++){
		    			var tempval = true;
    					for(var j=0;j<fdAccountIds.length;j++){
    						if(ofdAccountIds[i]==fdAccountIds[j]){
    							tempval = false;
    						}
    					}
    					if(tempval){
    						flag = false;
    						break;
    					}
    				}
    			}
    		}
    		
    		if(flag){
	    		var ofdAccountName = '${fdAccountName}'.split(";");
    			var fdAccountName = "";
    			$("input[name$='.fdName']").each(function(i,v){
       				if(i==0){
       					fdAccountName = v.value;
       				}else{
       					fdAccountName += ";"+v.value;
       				}
           		});
    			var fdAccountNames = fdAccountName.split(";");    			
	    		for(var i=0;i<ofdAccountName.length;i++){
	    			var tempval = true;
					for(var j=0;j<fdAccountNames.length;j++){
						if(ofdAccountName[i]==fdAccountNames[j]){
							tempval = false;
						}
					}
					if(tempval){
						flag = false;
						break;
					}
				}
    		}
    		
    		return flag;
    	}
        function check(method){
       		var flag = $KMSSValidation().validate();
   	    	var len = $("input[name$='.fdAccountIds']").length;
   	    	var synch = $("input[name='fdIsSysnch']").val();
           	if(flag&&len==0&&synch=="true"){
           		flag = false;
           		alert('<bean:message key="thirdCtripConfig.acc" bundle="third-ctrip"/>');
           	}
           	if(flag&&synch=="true"){
           		$("input[name$='.fdAccountIds']").each(function(i,v){
           			if(!v.value){
           				flag = false;
		           		alert('<bean:message key="thirdCtripAccount.fdAccount" bundle="third-ctrip"/>'+'<bean:message key="thirdCtripAccount.notnull" bundle="third-ctrip"/>');
           			}
           		});
           	}
           	if(flag&&synch=="true"){
           		$("input[name$='.fdName']").each(function(i,v){
           			if(!v.value){
           				flag = false;
		           		alert('<bean:message key="thirdCtripAccount.fdName" bundle="third-ctrip"/>'+'<bean:message key="thirdCtripAccount.notnull" bundle="third-ctrip"/>');
		           		return false;
           			}
           		});
           	}
   			if(flag&&synch=="true"){
   	        	var url = '<c:url value="/third/ctrip/third_ctrip_config/thirdCtripConfig.do?method=isOrgRepeat" />'+"&fdId=${thirdCtripConfigForm.fdId}";
   	        	var ids = "";
           		$("input[name$='.fdAccountIds']").each(function(i,v){
           			ids += v.value+",";
           		});
           		url += "&fdOrgIds="+ids;
   				$.ajax({
   				   type: "POST",
   				   url: url,
   				   async:false,
   				   dataType: "json",
   				   success: function(data){
   					   if(data.errcode==1){
   						   flag = false;
   						   alert(data.errmsg);
   					   }
   				   }
   				});
   			}
   			if(flag){
   				var fl = true;
   				var md = '${thirdCtripConfigForm.method_GET}';
   				if("edit"==md){
	   				var fg = checkForm();
   					if(!fg){
   						if(confirm('<bean:message key="thirdCtripConfig.acc.edit.tip" bundle="third-ctrip"/>')){
   							$("input[name='fdCleanTime']").val("1");
   						}else{
   							fl = false;
   							$("input[name='fdCleanTime']").val("0");
   						}
   					}else{
   						$("input[name='fdCleanTime']").val("0");
   					}
   				}else{
   					$("input[name='fdCleanTime']").val("0");
   				}
   				if(fl){
	   				Com_Submit(document.thirdCtripConfigForm, method);
   				}
   			}
        }
    </script>
</html:form>
</template:replace>
</template:include>