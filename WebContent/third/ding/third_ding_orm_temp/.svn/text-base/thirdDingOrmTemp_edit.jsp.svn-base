<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
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
    Com_IncludeFile("dialog.js");
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/ding/third_ding_orm_temp/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
    
    
    //选择模板
    function selectForm(){
    	var mainModelName = "com.landray.kmss.km.review.model.KmReviewMain";
    	seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($,dialog,topic) {
    			dialog.categoryForNewFile(
    					"com.landray.kmss.km.review.model.KmReviewTemplate",
    					null,false,null,function(rtn) {
    						if(rtn){
    							$("#fdTemplateName").val(rtn.name);
    							$("#fdTemplateId").val(rtn.id);
    						}
    					},'',null,null,true);
    	});
    }
    
    //批量导入表单参数
   	 function importInFields(){
   	    	var fdModelName = "com.landray.kmss.km.review.model.KmReviewMain";
   	    	var fdModelId = $("input[name='fdTemplateId']").val();
   	    	if(!fdModelName || !fdModelId){
   	    		alert("请先选择表单模板");
   	    		return;
   	    	}
   	    	var serviceUrl = "dingFormTreeService&templateModelName=com.landray.kmss.km.review.model.KmReviewTemplate&templateId="+fdModelId+"&modelName="+fdModelName;
  	    	Dialog_List(true, "importInFieldIds", "importInFieldNames", null, serviceUrl,afterInFieldsSelect,null,null,null,"");
   	  }
   	 function afterInFieldsSelect(returnData){
   		if(!returnData){
     		return;
     	}
     	var tableObj = $('#TABLE_DocList_fdDetail_Form');
     	trObj=$("#TABLE_DocList_fdDetail_Form tr:not(:first)");
     	
     /* 	var fdTemplateId = $("input[name='fdTemplateId']").val();
     	if(!window.fdTemplateId||fdTemplateId!= window.fdTemplateId){
     	    window.fdTemplateId = fdTemplateId;
     	    trObj.remove(); 
     	} */
     	var result = new Array();
     	var datas = returnData.data;
     	for(var i=0;i<datas.length;i++){
     		var id = datas[i].id;
     		var name = datas[i].name;
     		if(!isNameContains(id)){
	     		var fieldValues = new Object();
	     		fieldValues["fdDetail_Form[!{index}].fdEkpField"]=id;
	     		fieldValues["fdDetail_Form[!{index}].fdEkpFieldName"]=name;
	     		DocList_AddRow(document.getElementById("TABLE_DocList_fdDetail_Form"),null,fieldValues);
     		}
     	}
     }
   	 function isNameContains(value){
   		 var flag = false;
   		$("#TABLE_DocList_fdDetail_Form").find("tr").each(function(i){
   			if(i != 0){
    		   	var tdArr = $(this).children();
    		    var index = i - 1;
    		    var fdEkpField = tdArr.find("input[name='fdDetail_Form["+index+"].fdEkpField']").val();
    		    if(value == fdEkpField){
    		    	flag = true;
    		    }
   			}
   		});
     	return flag;
     }
   	 
   	 
   	 
   	 //获取钉钉流程模板
   	function initDingProcess(){
   		var serviceUrl = "dingProcessTreeService&userId=${userId}";
   		Dialog_List(false, "fdProcessCode", "fdProcessName", null, serviceUrl,afterInDingSelect,null,null,null,"");
   	};
   	function afterInDingSelect(returnData){
   		if(!returnData){
     		return;
     	}
     }
    
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>
    <% pageContext.setAttribute("userId", UserUtil.getUser().getFdId()); %>

<html:form action="/third/ding/third_ding_orm_temp/thirdDingOrmTemp.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${thirdDingOrmTempForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="check('update');">
            </c:when>
            <c:when test="${thirdDingOrmTempForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="check('save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('third-ding:table.thirdDingOrmTemp') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        <%-- ${lfn:message('third-ding:thirdDingOrmTemp.fdName')} --%>EKP流程模板
                    </td>
                    <td width="35.0%">
                        <div id="_xform_fdName" _xform_type="text">
                           <%--  <xform:text property="fdName" showStatus="edit" style="width:95%;" /> --%>
                            <input type="hidden" value="${thirdDingOrmTempForm.fdTemplateId }" id='fdTemplateId' name='fdTemplateId' />
					        <xform:text property="fdName"  htmlElementProperties="id='fdTemplateName'" value="" style="width:50%;"></xform:text>
        					<a href="javascript:void(0);" onClick="selectForm();">选择</a>
                        </div>
                    </td>
                     <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOrmTemp.fdIsAvailable')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdIsAvailable" _xform_type="radio">
                            <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOrmTemp.fdProcessCode')}
                    </td>
                    <td width="35%" id="dingProcess">
                        <div id="_xform_fdProcessCode" _xform_type="text">
                        
                        	 <input type="hidden" value="${thirdDingOrmTempForm.fdProcessCode }" id='fdProcessCode' name='fdProcessCode' />
                            <xform:text property="fdProcessName" showStatus="edit" style="width:50%;" required="true"/>
                            <a href="javascript:void(0);" onClick="initDingProcess();">选择</a>
                        </div>
                    </td>
                     <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOrmTemp.fdDingTemplateType')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdDingTemplateType" _xform_type="radio">
                            <xform:radio property="fdDingTemplateType" htmlElementProperties="id='fdDingTemplateType'" showStatus="edit">
                                <xform:enumsDataSource enumsType="third_ding_dt_type" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <%-- <tr>
                     <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOrmTemp.fdOrder')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdOrder" _xform_type="text">
                            <xform:text property="fdOrder" showStatus="edit" style="width:95%;" validators="number"/>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOrmTemp.fdStartFlow')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdStartFlow" _xform_type="radio">
                            <xform:radio property="fdStartFlow" htmlElementProperties="id='fdStartFlow'" showStatus="edit">
                                <xform:enumsDataSource enumsType="third_ding_start_flow" />
                            </xform:radio>
                        </div>
                    </td>
                </tr> --%>
                <tr>
                    <td colspan="4" width="100%">
                        <table class="tb_normal" width="100%" id="TABLE_DocList_fdDetail_Form" align="center" tbdraggable="true">
                            <tr align="center" class="tr_normal_title">
                               <!--  <td width="10%"></td> -->
                                <td width="5%">
                                    ${lfn:message('page.serial')}
                                </td>
                                <td width="30%">
                                    ${lfn:message('third-ding:thirdDingOrmDe.fdEkpField')}
                                </td>
                                <td width="30%">
                                    EKP字段ID
                                </td>
                                <td width="30%">
                                    ${lfn:message('third-ding:thirdDingOrmDe.fdDingField')}
                                </td>
                                <td width="5%">
                                	<a href="javaScript:void();" onclick="importInFields();" style="color: #2574ad">导入表单字段</a>
                                </td>
                                  <input type="hidden" name="importInFieldIds" />
                        		  <input type="hidden" name="importInFieldNames" />
                            </tr>
                            <tr KMSS_IsReferRow="1" style="display:none;">
                               <!--  <td align="center">
                                    <input type='checkbox' name='DocList_Selected' />
                                </td> -->
                                <td align="center" KMSS_IsRowIndex="1">
                                    !{index}
                                </td>
                                <td align="center">
                                    <input type="hidden" name="fdDetail_Form[!{index}].fdId" value="" disabled="true" />
                                    <div id="_xform_fdDetail_Form[!{index}].fdEkpField" _xform_type="text">
                                    	<xform:text property="fdDetail_Form[!{index}].fdEkpFieldName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td align="center">
                                    <div id="_xform_fdDetail_Form[!{index}].fdEkpField" _xform_type="text">
                                    	<xform:text property="fdDetail_Form[!{index}].fdEkpField" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td align="center">
                                    <div id="_xform_fdDetail_Form[!{index}].fdDingField" _xform_type="text">
                                        <xform:text property="fdDetail_Form[!{index}].fdDingField" showStatus="edit" subject="${lfn:message('third-ding:thirdDingOrmDe.fdDingField')}" validators=" maxLength(50)" style="width:95%;" required="true"/>
                                    </div>
                                </td>
                                <td align="center">
                                   <%--  <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
                                    </a>
                                    &nbsp; --%>
                                    <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                                    </a>
                                </td>
                            </tr>
                            <c:forEach items="${thirdDingOrmTempForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
                                <tr KMSS_IsContentRow="1">
                                    <td align="center">
                                        ${vstatus.index+1}
                                    </td>
                                    <td align="center">
                                        <input type="hidden" name="fdDetail_Form[${vstatus.index}].fdId" value="${fdDetail_FormItem.fdId}" />
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdEkpField" _xform_type="text">
                                            <xform:text property="fdDetail_Form[${vstatus.index}].fdEkpFieldName" showStatus="edit" subject="${lfn:message('third-ding:thirdDingOrmDe.fdEkpField')}" validators=" maxLength(50)" style="width:95%;" required="true"/>
                                        </div>
                                    </td>
                                    <td align="center">
	                                    <div id="_xform_fdDetail_Form[!{index}].fdEkpField" _xform_type="text">
	                                    	<xform:text property="fdDetail_Form[${vstatus.index}].fdEkpField" showStatus="edit" style="width:95%;" />
	                                    </div>
	                                </td>
                                    <td align="center">
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdDingField" _xform_type="text">
                                            <xform:text property="fdDetail_Form[${vstatus.index}].fdDingField" showStatus="edit" subject="${lfn:message('third-ding:thirdDingOrmDe.fdDingField')}" validators=" maxLength(50)" style="width:95%;" required="true"/>
                                        </div>
                                    </td>
                                    <td align="center">
                                        <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                                            <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <%-- <tr type="optRow" class="tr_normal_opt" invalidrow="true">
                                <td colspan="5">
                                    <a href="javascript:void(0);" onclick="DocList_AddRow();">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_add.png" border="0" />
                                    </a>
                                    &nbsp;
                                    <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(-1);">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_up.png" border="0" />
                                    </a>
                                    &nbsp;
                                    <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(1);">
                                        <img src="${KMSS_Parameter_StylePath}icons/icon_down.png" border="0" />
                                    </a>
                                    &nbsp;
                                </td>
                            </tr> --%>
                        </table>
                        <input type="hidden" name="fdDetail_Flag" value="1">
                        <script>
                            Com_IncludeFile("doclist.js");
                        </script>
                        <script>
                            DocList_Info.push('TABLE_DocList_fdDetail_Form');
                        </script>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOrmTemp.docCreator')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${thirdDingOrmTempForm.docCreatorId}" personName="${thirdDingOrmTempForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOrmTemp.docCreateTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOrmTemp.docAlteror')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${thirdDingOrmTempForm.docAlterorId}" personName="${thirdDingOrmTempForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('third-ding:thirdDingOrmTemp.docAlterTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterTime" _xform_type="datetime">
                            <xform:datetime property="docAlterTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                	<td colspan="4" style="color: red;">
                		${lfn:message('third-ding:third.ding.flow.tip')}
                		{"startTime":"2018-01-16","finishTime":"2018-01-17”,"unit":"day","attType":"事假"}
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
    <script>
    function check(method){
    	var flag = true;
    	var fdId = $("input[name='fdId']").val();
    	var tempId = $("input[name='fdTemplateId']").val();
    	var pcode = $("input[name='fdProcessCode']").val();
    	var available = $("input[name='fdIsAvailable']:checked").val();
    	if(available=="true"){
			var url = '<c:url value="/third/ding/third_ding_orm_temp/thirdDingOrmTemp.do?method=check" />&fdTemplateId='+tempId+"&fdProcessCode="+pcode+"&fdId="+fdId;
			$.ajax({
			   type: "POST",
			   url: url,
			   async:false,
			   dataType: "json",
			   success: function(data){
					if(data.status=="0"){
						flag = false;
						alert(data.msg);
					}
			   }
			});
    	}
    	if(flag){
	    	Com_Submit(document.thirdDingOrmTempForm, method);
    	}
	}
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>