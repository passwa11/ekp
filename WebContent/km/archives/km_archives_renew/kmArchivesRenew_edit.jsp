<%@page import="com.landray.kmss.km.archives.model.KmArchivesConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.simple" sidebar="no">
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
	    	.kmArchivesRenewTitle {
	    		margin-top: 10px;
	    	}
		</style>
		<script type="text/javascript">
			// #108295 由default.edit模板更换为default.simple, 需要引入相关样式js、校验js等
	    	seajs.use(['theme!form']);
	    	Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		
		    var editOption = {
		        formName: 'kmArchivesRenewForm',
		        modelName: 'com.landray.kmss.km.archives.model.KmArchivesRenew'
		    };
		    Com_IncludeFile("security.js");
		    Com_IncludeFile("domain.js");
		    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/km/archives/resource/js/", 'js', true);
		    function deleteRow(trolleyId){
		    	if($("[name='data-tr']").length <= 1) {
		    		alert("${lfn:message('km-archives:message.no.less')}");
		    	}else {
		    		$("#"+trolleyId).remove();
		    	}
			}
		</script>
	</template:replace>

	<!-- 续借表单 -->
	<template:replace name="body">
		<%
			KmArchivesConfig kmArchivesConfig = new KmArchivesConfig();
			request.setAttribute("fdMaxRenewDate", kmArchivesConfig.getFdMaxRenewDate());
			pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
		%>
		<html:form action="/km/archives/km_archives_renew/kmArchivesRenew.do">
		    <p class="txttitle kmArchivesRenewTitle">${ lfn:message('km-archives:table.kmArchivesRenew') }</p>
		    <center>
		
		        <div style="width:95%;margin-top: 10px">
		            <table class="tb_normal" width="100%">
		                <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('km-archives:kmArchivesRenew.docCreator')}
		                    </td>
		                    <td width="35%">
		                        <xform:address propertyName="docCreatorName" propertyId="docCreatorId" orgType="ORG_TYPE_ORGORDEPT" showStatus="readOnly"></xform:address>
		                    </td>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('km-archives:kmArchivesRenew.docCreateTime')}
		                    </td>
		                    <td width="35%">
		                        <xform:datetime property="docCreateTime" showStatus="readOnly" style="width:95%;" />
		                        <html:hidden property="fdBorrowDate" value="${fdBorrowDate}"/>
		                    </td>
		                </tr>
		                <tr>
		                	<td colspan="4" width="100%">
		                		<center style="margin-top: 20px; margin-bottom: 20px">
		                 			<span><b><bean:message bundle="km-archives" key="kmArchivesRenew.fdDetails"/></b>
		                 			</span>
		                		</center>
		                		<table class="tb_normal" width="100%" id="TABLE_DocList_detailsList" align="center" tbdraggable="true">
			                         <tr align="center" class="tr_normal_title">
			                             <td style="width:60px;">
			                                 ${lfn:message('page.serial')}
			                             </td>
			                             <td>
			                                 	${lfn:message('km-archives:kmArchivesMain.docSubject')}
			                             </td>
			                             <td>
												${lfn:message('km-archives:kmArchivesMain.docTemplate')}
										</td>
			                             <td>
			                                 	${lfn:message('km-archives:kmArchivesMain.fdValidityDate')}
			                             </td>
			                             
			                              <td>
			                                 	${lfn:message('km-archives:kmArchivesDetails.fdAuthorityRange')}
			                             </td>
			                       		<td>
			                       				${lfn:message('km-archives:kmArchivesDetails.fdReturnDate')}
			                       		</td>
			                       		<td>
			                       				${lfn:message('list.operation')}
			                       		</td>
			                         </tr>
			                         <c:forEach items="${kmArchivesRenewForm.detailsList}" var="detailsListItem" varStatus="vstatus">
										<tr name="data-tr" width="100%" id="${detailsListItem.fdArchId }">
											<td class="td_normal_title" style="width:5%;text-align:center">${vstatus.index+1}</td>
											<input type="hidden" name="detailsList[${vstatus.index }].fdId" value="${detailsListItem.fdId }"/>
											<input type="hidden" name="detailsList[${vstatus.index }].fdStatus" value="${detailsListItem.fdStatus }"/>
											<input type="hidden" name="detailsList[${vstatus.index }].fdArchId" value="${detailsListItem.fdArchId }"/>
											<input type="hidden" name="detailsList[${vstatus.index }].fdBorrowerId" value="${detailsListItem.fdBorrowerId }"/>
											<td style="width:15%;text-align:center">
										     	<c:out value="${detailsListItem.fdArchives.docSubject }"/>
											</td>
											
											<td style="width:10%;text-align:center">
										     	<c:out value="${detailsListItem.fdArchives.docTemplate.fdName }"/>
											</td>
											
											<td style="width:10%;text-align:center">
												<c:choose>
													<c:when test="${not empty detailsListItem.fdArchives.fdValidityDate}">
														<kmss:showDate value="${detailsListItem.fdArchives.fdValidityDate }" type="date"></kmss:showDate>
													</c:when>
													<c:otherwise>
														<bean:message bundle="km-archives" key="kmArchivesMain.fdValidityDate.forever"/>
													</c:otherwise>
												</c:choose>
											</td>
											
											<td style="width:30%;text-align:center">
												<xform:checkbox property="detailsList[${vstatus.index }].fdAuthorityRange" value="${detailsListItem.fdAuthorityRange }" showStatus="readOnly">
													<xform:simpleDataSource value="copy">${lfn:message('km-archives:kmArchivesConfig.fdDefaultRange.copy') }</xform:simpleDataSource>
													<xform:simpleDataSource value="download">${lfn:message('km-archives:kmArchivesConfig.fdDefaultRange.download') }</xform:simpleDataSource>
													<xform:simpleDataSource value="print">${lfn:message('km-archives:kmArchivesConfig.fdDefaultRange.print') }</xform:simpleDataSource>
												</xform:checkbox>
											</td>
											
											<td style="width:10%;text-align:center">
												<xform:datetime onValueChange="null" property="detailsList[${vstatus.index }].fdRenewReturnDate" value="${empty detailsListItem.fdRenewReturnDate?detailsListItem.fdReturnDate:detailsListItem.fdRenewReturnDate }" dateTimeType="datetime" showStatus="edit" required="true" subject="${lfn:message('km-archives:kmArchivesDetails.fdReturnDate')}" validators="after minRenewDateValidator('${empty detailsListItem.fdRenewReturnDate?detailsListItem.fdReturnDate:detailsListItem.fdRenewReturnDate }') maxRenewDateValidator('${empty detailsListItem.fdRenewReturnDate?detailsListItem.fdReturnDate:detailsListItem.fdRenewReturnDate }') returnDateValidator(${detailsListItem.fdArchives.fdValidityDate})"/>
											</td>
											
											<td class="td_normal_title" style="width:5%;text-align:center">
											    <img src="${KMSS_Parameter_StylePath}icons/delete.gif" style="cursor:pointer" onclick="deleteRow('${detailsListItem.fdArchId}')">
											</td>
										</tr>
									</c:forEach>
		                     </table>
		                	</td>
		                </tr>
		                <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('km-archives:kmArchivesRenew.fdReason')}
		                    </td>
		                    <td colspan="3" width="85.0%">
		                        <xform:textarea property="fdReason" showStatus="edit" style="width:95%;" />
		                    </td>
		                </tr>
		            </table>
		        </div>
		    </center>
		    <html:hidden property="fdId" />
		    <html:hidden property="method_GET" />
		    <html:hidden property="fdBorrowId" value="${param.fdBorrowId}"/>
		    <center>
            	<div style="height:30px"></div>
	        	<c:choose>
		            <c:when test="${kmArchivesRenewForm.method_GET=='edit'}">
		                <ui:button text="${ lfn:message('button.update') }" order="2" onclick="Com_Submit(document.kmArchivesRenewForm, 'update');"></ui:button>
		            </c:when>
		            <c:when test="${kmArchivesRenewForm.method_GET=='add'}">
		                <ui:button text="${ lfn:message('button.save') }" order="2" onclick="Com_Submit(document.kmArchivesRenewForm, 'save');"></ui:button>
		            </c:when>
		        </c:choose>
		        <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();"></ui:button>
			</center>
			
		    <script>
		        var _validation = $KMSSValidation();
		        _validation.addValidator('returnDateValidator(validityDate)',"${lfn:message('km-archives:kmArchivesRenew.returnDateValidate')}",function(v,e,o){
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
		        _validation.addValidator('maxRenewDateValidator(fdReturnDate)','<bean:message bundle="km-archives" key="kmArchivesBorrow.maxRenewDateValidate" arg0="${fdMaxRenewDate}" />',function(v,e,o){
			   		 var oldReturnDate = Com_GetDate(e.defaultValue,"datetime");
			   		 var newReturnDate = Com_GetDate(v,"datetime");
			   		 var days = (newReturnDate.getTime() - oldReturnDate.getTime())/(1000*60*60*24);
			   		 var fdMaxRenewDate = '${fdMaxRenewDate}';
			   		 if(fdMaxRenewDate==0||fdMaxRenewDate==null||fdMaxRenewDate=='undefined'){
						 return true;
					 }
			   		 if(days>fdMaxRenewDate){
			   			 return false;
			   		 }
			   		 return true;
		   	 	});
		        _validation.addValidator('minRenewDateValidator(curReturnDate)','归还时间必须大于当前归还时间',function(v,e,o){
		        	var curReturnDate = Com_GetDate(e.defaultValue,"datetime");
		        	var newReturnDate = Com_GetDate(v,"datetime");
		        	var days = (newReturnDate.getTime() - curReturnDate.getTime())/(1000*60*60*24);
		        	if(days>0){
		        		return true;
		        	}
		        	return false;
		        });
		    </script>
		</html:form>
	</template:replace>
</template:include>