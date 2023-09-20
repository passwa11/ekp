<%@page import="com.landray.kmss.km.archives.model.KmArchivesConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

    <% 
    	pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
    	KmArchivesConfig kmArchivesConfig = new KmArchivesConfig();
    	request.setAttribute("fdMaxRenewDate", kmArchivesConfig.getFdMaxRenewDate());
    %>

<template:include ref="default.edit">
    <template:replace name="head">
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
            		
        </style>
        <script type="text/javascript">
            var editOption = {
                formName: 'kmArchivesBorrowForm',
                modelName: 'com.landray.kmss.km.archives.model.KmArchivesBorrow',
                templateName: 'com.landray.kmss.km.archives.model.KmArchivesTemplate',
                subjectField: 'docSubject',
                mode: ''


            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/km/archives/resource/js/", 'js', true);
            function selectDept(){
	        	var url = "${KMSS_Parameter_ContextPath}km/archives/km_archives_borrow/kmArchivesBorrow.do?method=getApplicantDept";
	        	var fdBorrowerId = document.getElementsByName("fdBorrowerId")[0];
	        	$(".detailBorrows").each(function(){
	        		$(this).val(fdBorrowerId.value);
	        	});
	        	 $.ajax({     
	           	     type:"post",   
	           	     url:url,     
	           	     data:{fdBorrowerId:fdBorrowerId.value},    
	           	     async:false,    //用同步方式 
	           	     success:function(data){
	           	 	    var results =  eval("("+data+")");
	           	 	    if(results['deptId']!=""&&results['deptName']!=""){
	           	 	     	//document.getElementsByName("docDeptId")[0].value = results['deptId'];
	           	 	     	//document.getElementsByName("docDeptName")[0].value = results['deptName'];
	           	 	    	var kmssData = new KMSSData();
	      	          		kmssData.AddHashMap({deptId:results['deptId'],deptName:results['deptName']});
	      	          		kmssData.PutToField("deptId:deptName", "docDeptId:docDeptName", "", false);
	           	   	 	}else{
	           	   	 		//document.getElementsByName("docDeptId")[0].value = "";
	          	 	     	//document.getElementsByName("docDeptName")[0].value = "";
		           	   	 	var address = Address_GetAddressObj("docDeptName");
		    				address.emptyAddress(true);
	           	   	 	}
	           		 }    
	             });
	        }
        </script>
    </template:replace>
    <template:replace name="title">
          <c:choose>
              <c:when test="${kmArchivesBorrowForm.method_GET == 'add' }">
                  <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('km-archives:table.kmArchivesBorrow') }" />
              </c:when>
              <c:otherwise>
                  <c:out value="${kmArchivesBorrowForm.docSubject} - " />
                  <c:out value="${ lfn:message('km-archives:table.kmArchivesBorrow') }" />
              </c:otherwise>
          </c:choose>
    </template:replace>
    <template:replace name="toolbar">
    <!-- 软删除配置 -->
    <c:if test="${kmArchivesBorrowForm.docDeleteFlag ==1}">
		<ui:toolbar id="toolbar" style="display:none;"></ui:toolbar>
	</c:if>
	<c:if test="${kmArchivesBorrowForm.docDeleteFlag !=1}">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ kmArchivesBorrowForm.method_GET == 'edit' }">
                    <c:if test="${ kmArchivesBorrowForm.docStatus=='10' || kmArchivesBorrowForm.docStatus=='11' }">
                        <ui:button text="${ lfn:message('button.savedraft') }" onclick="submitForm('10','update');" />
                    </c:if>
                    <c:if test="${ kmArchivesBorrowForm.docStatus=='10' || kmArchivesBorrowForm.docStatus=='11' || kmArchivesBorrowForm.docStatus=='20' }">
                        <ui:button text="${ lfn:message('button.submit') }" onclick="submitForm('20','update');" />
                    </c:if>
                </c:when>
                <c:when test="${ kmArchivesBorrowForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.savedraft') }" order="2" onclick="submitForm('10','save');" />
                    <ui:button text="${ lfn:message('button.submit') }" order="2" onclick="submitForm('20','save');" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </c:if>
    </template:replace>
    <template:replace name="path">
       <ui:menu layout="sys.ui.menu.nav">
           <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
           <ui:menu-item text="${ lfn:message('km-archives:table.kmArchivesBorrow') }" />
       </ui:menu>
   </template:replace>
   <template:replace name="content">
	<!-- 软删除配置 -->
	<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="kmArchivesBorrowForm"></c:param>
	</c:import>   
        <html:form action="/km/archives/km_archives_borrow/kmArchivesBorrow.do">
			<div class='lui_form_title_frame'>
                <div class='lui_form_subject'>
                    ${lfn:message('km-archives:table.kmArchivesBorrow')}
                </div>
                <div class='lui_form_baseinfo'>

                </div>
            </div>
            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('km-archives:py.JiBenXinXi') }" expand="true">
                    
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesBorrow.docSubject')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <xform:text property="docSubject" showStatus="edit" style="width:95%;" />
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesBorrow.docTemplate')}
                            </td>
                            <td width="35%">
                            	<div id="selectTemplet">
								</div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesBorrow.fdBorrowDate')}
                            </td>
                            <td width="35%">
                                <xform:datetime onValueChange="null" property="fdBorrowDate" showStatus="edit" dateTimeType="datetime" style="width:95%;" required="true" />
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesBorrow.fdBorrower')}
                            </td>
                            <td width="35%">
                                <xform:address propertyId="fdBorrowerId" propertyName="fdBorrowerName" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:95%;" subject="${lfn:message('km-archives:kmArchivesBorrow.fdBorrower')}" required="true" onValueChange="selectDept"/>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesBorrow.docDept')}
                            </td>
                            <td width="35%">
                                <xform:address propertyId="docDeptId" propertyName="docDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="edit" style="width:95%;" />
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4">
								<table class="tb_normal" width=100% style="border:none">
										<tr style="border:none">
											<td style="border:none">
											 <c:import url="/km/archives/km_archives_borrow_option/kmArchMainOption_edit.jsp"
												charEncoding="UTF-8">
											</c:import>
											</td>
										</tr>
								</table>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesBorrow.fdBorrowReason')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <xform:textarea property="fdBorrowReason" showStatus="edit" style="width:95%;" />
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesBorrow.attBorrow')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
                                    <c:param name="fdKey" value="attBorrow" />
                                    <c:param name="formBeanName" value="kmArchivesBorrowForm" />
                                    <c:param name="fdRequired" value="false" />
                                    <c:param name="fdMulti" value="true" />
                                </c:import>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesBorrow.docCreator')}
                            </td>
                            <td width="35%">
                                <ui:person personId="${kmArchivesBorrowForm.docCreatorId}" personName="${kmArchivesBorrowForm.docCreatorName}" />
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesBorrow.docCreateTime')}
                            </td>
                            <td width="35%">
                                <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                            </td>
                        </tr>
                    </table>
                </ui:content>
                <c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="kmArchivesBorrowForm" />
                    <c:param name="fdKey" value="kmArchivesBorrow" />
                    <c:param name="isExpand" value="true" />
                </c:import>

                <c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="kmArchivesBorrowForm" />
                    <c:param name="moduleModelName" value="com.landray.kmss.km.archives.model.KmArchivesBorrow" />
                </c:import>

            </ui:tabpage>
            <html:hidden property="fdId" />
            <html:hidden property="docStatus" />
            <html:hidden property="method_GET" />
        </html:form>
    </template:replace>
</template:include>
<script type="text/javascript">
	$(document).ready(function(){
		  initKmArchType();
	});
	
	function initKmArchType(){
		var url ="${KMSS_Parameter_ContextPath}km/archives/km_archives_template/kmArchivesTemplate.do?method=getTemplete";
		$.post(url,function(results){
			var DivObj = $('#selectTemplet');
			if(results == null || results.length == 0){
				DivObj.hide();
			}
			DivObj.html("");
			//只有一个可用模板时不显示下拉框
			if(results.length ==1){
				DivObj.append('<input type="hidden" name="docTemplateId" value="'+results[0].fdId+'"/>'+results[0].fdName);
			}else{
				var selectObj = '<select name="docTemplateId" onchange="if(!onChangeKmArchTemplate(this.value))return;">'
				var optionHtml = '';
				for(var i=0;i<results.length;i++){
					optionHtml +='<option value="'+results[i].fdId+'"';
					if( results[i].fdId =="${kmArchivesBorrowForm.docTemplateId}"){
						optionHtml += ' selected="selected"';
					}
					optionHtml +='>' +results[i].fdName+'</option>';
				}
				selectObj += optionHtml;
				selectObj += '</select>';
				DivObj.append(selectObj);
			}
		},'json');
	}
	
	function onChangeKmArchTemplate(fdTempId){
		//location.href = '${LUI_ContextPath}/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=add' 
		//		+ '&fdMainId=${param.fdMainId}&docTemplateId=' + fdTempId
				
		document.kmArchivesBorrowForm.action = Com_SetUrlParameter(location.href,"method","add");
		document.kmArchivesBorrowForm.action = Com_SetUrlParameter(document.kmArchivesBorrowForm.action,"type","change");
		document.kmArchivesBorrowForm.action = Com_SetUrlParameter(document.kmArchivesBorrowForm.action,"docTemplateId",fdTempId);
		document.kmArchivesBorrowForm.submit();
	}
	var _validation = $KMSSValidation();
	//校验用品不为空
	_validation.addValidator("archNotNull","${lfn:message('km-archives:kmArchivesBorrow.archNotNull')}",function(v, e, o) {
		if($("#TABLE_DocList tr:not(:first)").length>0){
	         return true;
		}else{
			 return false;
		}
	});
	 _validation.addValidator('returnDateValidator(validityDate)',"${lfn:message('km-archives:kmArchivesBorrow.returnDateValidate')}",function(v,e,o){
		var validityDateStr =  o['validityDate'];
		if(validityDateStr != ''){
			var validityDate = Com_GetDate(validityDateStr);
	     	var returnDate = Com_GetDate(v);
	     	if(returnDate.getTime() > validityDate.getTime()){
	     		return false;
	     	}
		}
     	return true;
     });
	 
</script>