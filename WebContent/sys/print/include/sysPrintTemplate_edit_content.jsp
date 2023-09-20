<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil,com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil"%>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil" %>
<%
     pageContext.setAttribute("_isWpsCloudEnable", new Boolean(SysAttWpsCloudUtil.isEnable()));
     pageContext.setAttribute("_isWpsWebOffice", new Boolean(SysAttWpsCloudUtil.isEnableWpsWebOffice()));
	 pageContext.setAttribute("_isWpsCenterEnable", new Boolean(SysAttWpsCenterUtil.isEnable()));
	 pageContext.setAttribute("_isWpsoaassistEmbed", new Boolean(SysAttWpsoaassistUtil.isWPSOAassistEmbed(request)));
%>

<table style="width:100%;table-layout: fixed">
	<tr>
		<td style="width: 20%;display:none;" id="SubPrint_main_tr">
			<div id="DIV_SubForm_Print" style="border:1px #d2d2d2 solid;">
				<div id="SubPrint_title_div" style="height:36px;border-bottom:1px solid #d2d2d2;">
					<table class="subTable" style="width:100%">
						<tr>
							<td style="width: 70%;padding:8px">
								<div style="margin-left:4px;font-size:12px;font-weight:bold;"><kmss:message key="sys-print:sysPrint.multiprint_configuration" /></div>
							 </td>
							<td style="width: 30%;padding:8px" align="right">
								<a href="javascript:void(0)" style="cursor:pointer;" onclick="addRow_print(this,false);">
									<img src="${LUI_ContextPath}/sys/print/resource/icon/add.png" border="0" title="<kmss:message key="sys-print:sysPrint.add" />"/>
								</a>
							</td>
						</tr>
					</table>
				</div>
				<div id="SubPrintDiv" style="overflow-x:hidden;overflow-y:auto;text-align: center;"> 
					<%@include file="/sys/print/include/sysSubPrintTemplate_edit.jsp"%>
				</div>
				<div id="SubPrintLoadMsg" style="font-weight:bold;display:none;color:rgb(153, 153, 153);margin-left: 10px;"><kmss:message key="sys-print:sysPrint.loadMsg" /></div>
				<div id="SubPrintControlsDiv" style="overflow-x:hidden;overflow-y:auto;"> 
				</div>
				<div id="print_control_load" style="display:none;"></div>
			</div>
		</td>
		<td style="width:7px;display:none" id="SubPrint_operation_tr">
			<image id="SubPrint_operation" type="image" style="cursor:pointer;" src="${LUI_ContextPath}/sys/print/resource/icon/varrowleft.gif" onclick="_Designer_SubPrintAddHide(this);">
		</td>
		<td>
			<div id="SubForm_Print_table">
				<table class="tb_normal" width=100% style="border-top:0;">
					<tr style='<c:if test="${'true' eq _isHide}">display:none</c:if>'>
						<c:if test="${'true' eq _isShowName}">
							<c:if test="${fn:length(sysPrintTemplateForm.fdSubTemplates) <= 0 ||  'true' eq _isModeling}">
								<td class='td_normal_title' width=15%>
									<kmss:message key="sys-print:sysPrintTemplate.fdName"></kmss:message>
								</td>
								<td width=20%>
									<xform:text value='${sysPrintTemplateForm.fdName }' property="sysPrintTemplateForm.fdName" showStatus='edit' style='width:90%' validators='maxLength(200) required' required='true' subject='打印模板名称'></xform:text>
								</td>
							</c:if>
						</c:if>
						<td class="td_normal_title" width=15%  style='<c:if test="${'true' eq _isHidePrintMode}">display:none</c:if>'>
							<kmss:message key="sys-print:table.sysPrint.fdPrintMode" />
						</td>
						<td width=10% style='<c:if test="${'true' eq _isHidePrintMode}">display:none;border-right-style:none</c:if><c:if test="${'true' != _isHidePrintMode}">border-right-style:none</c:if>'>
						 	<xform:select value="${sysPrintTemplateForm.fdPrintMode}" property="sysPrintTemplateForm.fdPrintMode" showPleaseSelect="false" showStatus="edit" onValueChange="onFdPrintModeChange">
								<xform:enumsDataSource enumsType="fdPrint_mode" />
							</xform:select>
							
						</td>
						<td width=20% style="border-left-style:none;">
							<span id="fd_Print_mode_word" style="visibility:hidden">&nbsp;&nbsp;&nbsp;&nbsp;
									<a  href="javascript:void(0);" onclick="wpsPrintModul()" style='color:#1b83d8;font-size:12px'> <kmss:message key="sys-print:table.sysPrint.fdPrintMode.setup.word" /></a>
							</span>
							<span id="fd_Print_mode_book" style="visibility:hidden">&nbsp;&nbsp;&nbsp;&nbsp; 
							   <a href="javascript:void(0);" onclick="onBookmarkHelp()" style='color:#1b83d8;font-size:12px'> <kmss:message key="sys-print:sysPrintEdit.edit.bookmark" /></a>
							</span> 
						</td>
						<td class="td_normal_title" width=15% style='<c:if test="${'true' eq _isHideDefaultSetting}">display:none</c:if>'>
							<c:choose>
								<c:when test="${empty param.usePrintTemplate }">
									<kmss:message key="sys-print:table.sysPrint.config" />
								</c:when>
								<c:otherwise>
									${param.usePrintTemplate }
								</c:otherwise>
							</c:choose>
						</td>
						<td style='<c:if test="${'true' eq _isHideDefaultSetting}">display:none</c:if>'>
						 	<xform:checkbox property="sysPrintTemplateForm.fdPrintTemplate" showStatus="edit" value="${sysPrintTemplateForm.fdPrintTemplate}">
							   	<xform:simpleDataSource value="true"><kmss:message key="sys-print:table.sysPrint.fdPrintTemplate" />
							   	</xform:simpleDataSource>
							</xform:checkbox>
						</td>
					</tr>
					<tr id="tr_word_printEdit" style="display: none;">
						<td class="td_normal_title" width=15%>
							<kmss:message key="sys-print:table.sysPrint.fdPrintEdit" />
						</td>
						<td colspan="4">
						 	<xform:checkbox property="sysPrintTemplateForm.fdPrintEdit" showStatus="edit">
							   	<xform:simpleDataSource value="true"><kmss:message key="message.yes" />
							   	</xform:simpleDataSource>
							</xform:checkbox>
						</td>
					</tr>
					<c:if test='${"false" eq _isHidePrintDesc}'>
						<tr id="tr_desc">
							<td class="td_normal_title" width=15%>
								<kmss:message key="sys-print:table.sysPrint.description" />
							</td>
							<td colspan="3">
							 	<kmss:message key="sys-print:table.sysPrint.description.txt" />
							</td>
						</tr>
					</c:if>
				</table>
				<div id="printTemplate">
					<%@ include file="/sys/print/include/sysPrintTemplatePanel_edit.jsp"%>
				</div>				
				<c:if test="${empty fdPrintOperType }">
					<table class="tb_normal" width=100% style="border-top:0;">
						<tr>
							<td colspan="4">
							   <%@ include file="/sys/attachment/sys_att_main/jg/sysAttMain_CheckJgSupport.jsp"%>
							   <div id="content_print" style="height: 0px;width: 0px;overflow:hidden;">
							   		<c:if test="${supportJg eq 'supported' && pageScope._isWpsWebOffice != 'true' && pageScope._isWpsoaassistEmbed != 'true'}">
							   		
						               <div id="printButtonDiv" style="text-align:right;">
						               		&nbsp;
										   <a href="javascript:void(0);" class="attbook bookmarkHelp" onclick="onBookmarkHelp()">
									       	<kmss:message key="sys-print:sysPrintEdit.edit.bookmark" />
									       </a>
									       <a href="javascript:void(0);" class="attbook imageBtn" onclick="jg_attachmentObject_sysprint_editonline.ocxObj.WebOpenPicture();">
									       	<kmss:message key="sys-print:sysPrintEdit.edit.imageBtn" />
									       </a>					       
										</div>
										
										<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_edit.jsp" charEncoding="UTF-8">
											<c:param name="fdKey" value="sysprint_editonline" />
											<c:param name="fdAttType" value="office"/>
											<c:param name="load" value="false" />
											<c:param name="fdModelId" value="${HtmlParam.fdId}"/>
											<c:param name="fdModelName" value="${HtmlParam.templateModelName}" />
											<c:param name="bindSubmit" value="false"/>
											<c:param name="buttonDiv" value="printButtonDiv" />
											<c:param name="isTemplate" value="true"/>
											<c:param name="attHeight" value="550" />
								       	</c:import>
										
							   		</c:if>
							   			
								       <c:if test="${pageScope._isWpsCloudEnable == 'true'}">
								       		<div id="printButtonDiv" style="text-align:right;">
							               		&nbsp;
											   <a href="javascript:void(0);" class="attbook bookmarkHelp" onclick="onBookmarkHelp()">
										       	<kmss:message key="sys-print:sysPrintEdit.edit.bookmark" />
										       </a>
											</div>
											
											<iframe id="wpsprintiframe" width="100%" height="100%" scrolling="no" frameborder="no" border="0" src="${LUI_ContextPath}/sys/attachment/sys_att_main/wps/cloud/sysAttMain_wps_iframe.jsp?fdKey=sysprint_editonline&fdAttType=office&load=false&bindSubmit=false&fdModelId=${templateForm.fdId}&fdModelName=${HtmlParam.templateModelName}&buttonDiv=printButtonDiv&isTemplate=true&attHeight=650&isPrint=true"></iframe>
								       		
								       		<%-- <c:import url="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_edit_print.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="sysprint_editonline" />
												<c:param name="fdAttType" value="office"/>
												<c:param name="load" value="false" />
												<c:param name="bindSubmit" value="false"/>	
												<c:param name="fdModelId" value="${templateForm.fdId}" />
												<c:param name="fdModelName" value="${HtmlParam.templateModelName}" />
												<c:param name="buttonDiv" value="printButtonDiv" />
												<c:param name="isTemplate" value="true"/>
												<c:param name="attHeight" value="550" />
												<c:param name="isPrint" value="true" />
											</c:import> --%>
								       </c:if>
								   <c:if test="${pageScope._isWpsCenterEnable == 'true'}">
									   <div id="printButtonDiv" style="text-align:right;">
										   &nbsp;
										   <a href="javascript:void(0);" class="attbook bookmarkHelp" onclick="onBookmarkHelp()">
											   <kmss:message key="sys-print:sysPrintEdit.edit.bookmark" />
										   </a>
									   </div>

									   <iframe id="wpsprintiframe" width="100%" height="100%" scrolling="no" frameborder="no" border="0" src="${LUI_ContextPath}/sys/attachment/sys_att_main/wps/center/sysAttMain_wps_iframe.jsp?fdKey=sysprint_editonline&fdAttType=office&load=false&bindSubmit=false&fdModelId=${templateForm.fdId}&fdModelName=${HtmlParam.templateModelName}&buttonDiv=printButtonDiv&isTemplate=true&attHeight=700&isPrint=true"></iframe>

									   <%-- <c:import url="/sys/attachment/sys_att_main/wps/center/sysAttMain_edit.jsp" charEncoding="UTF-8">
                                         <c:param name="fdKey" value="sysprint_editonline" />
                                         <c:param name="fdAttType" value="office"/>
                                         <c:param name="load" value="true" />
                                         <c:param name="bindSubmit" value="false"/>
                                         <c:param name="fdModelId" value="${templateForm.fdId}" />
                                         <c:param name="fdModelName" value="${HtmlParam.templateModelName}" />
                                         <c:param name="buttonDiv" value="printButtonDiv" />
                                         <c:param name="isTemplate" value="true"/>
                                         <c:param name="attHeight" value="550" />
                                         <c:param name="isPrint" value="true" />
                                    	</c:import> --%>
								   </c:if>
								   <c:if test="${pageScope._isWpsoaassistEmbed == 'true'}">

									   <div id="printButtonDiv" style="text-align:right;">
										   &nbsp;
										   <a href="javascript:void(0);" class="attbook bookmarkHelp" onclick="onBookmarkHelp()">
											   <kmss:message key="sys-print:sysPrintEdit.edit.bookmark" />
										   </a>
									   </div>

									   <iframe id="wpsprintiframe" width="100%" height="100%" scrolling="no" frameborder="no" border="0" src="${LUI_ContextPath}/sys/attachment/sys_att_main/wps/oaassist/sysAttMain_edit_print.jsp?fdKey=sysprint_editonline&fdAttType=office&load=false&bindSubmit=false&fdModelId=${HtmlParam.fdId}&fdModelName=${HtmlParam.templateModelName}&buttonDiv=printButtonDiv&isTemplate=true&isPrint=true"></iframe>

									   <%--<c:import url="/sys/attachment/sys_att_main/wps/oaassist/sysAttMain_edit_print.jsp" charEncoding="UTF-8">
										   <c:param name="fdKey" value="sysprint_editonline" />
										   <c:param name="fdAttType" value="office"/>
										   <c:param name="fdModelId" value="${HtmlParam.fdId}"/>
										   <c:param name="fdModelName" value="${HtmlParam.templateModelName}" />
										   <c:param name="isTemplate" value="true"/>
										   <c:param name="load" value="false" />
										   <c:param name="formBeanName" value="${templateForm}" />
									   </c:import>--%>
								   </c:if>
							   	</div>
							</td>
						</tr>
					</table>
				</c:if>
				
				<div id="_tmp_postData_iframe"></div>	
			</div>
		</td>
</table>