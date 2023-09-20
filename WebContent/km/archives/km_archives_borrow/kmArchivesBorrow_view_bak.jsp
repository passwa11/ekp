<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.view">
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
    </template:replace>
    <template:replace name="title">
        <c:out value="${kmArchivesBorrowForm.docSubject} - " />
        <c:out value="${ lfn:message('km-archives:table.kmArchivesBorrow') }" />
    </template:replace>
    <template:replace name="toolbar">
        <script>
            function deleteDoc(delUrl) {
                seajs.use(['lui/dialog'], function(dialog) {
                    Com_Delete_Get(delUrl, 'com.landray.kmss.km.archives.model.KmArchivesBorrow');
                });
            }
            
            function archivesRenew(borrowId){
            	var url = "/km/archives/km_archives_renew/kmArchivesRenew.do?method=add&fdBorrowId="+borrowId;
            	seajs.use(['lui/dialog'], function(dialog) {
            		dialog.iframe(url,'${ lfn:message("km-archives:table.kmArchivesRenew") }',function(value){
            			
            		},{
    					"width" : 1000,
    					"height" : 500
    				});
            	});
            }
            
        </script>
        <c:if test="${kmArchivesBorrowForm.docDeleteFlag ==1}">
			<ui:toolbar id="toolbar" style="display:none;"></ui:toolbar>
		</c:if>
        <c:if test="${kmArchivesBorrowForm.docDeleteFlag !=1}">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5" var-navwidth="90%" style="display:none;">
            <c:if test="${ kmArchivesBorrowForm.docStatus=='10' || kmArchivesBorrowForm.docStatus=='11' || kmArchivesBorrowForm.docStatus=='20' }">
                <!--edit-->
                <kmss:auth requestURL="/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('kmArchivesBorrow.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
            </c:if>
            <!--delete-->
            <kmss:auth requestURL="/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('kmArchivesBorrow.do?method=delete&fdId=${param.fdId}');" order="4" />
            </kmss:auth>
            <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />
			<!-- 续借 -->
			<c:if test="${kmArchivesBorrowForm.docStatus=='30' && kmArchivesBorrowForm.fdBorrowerId==currentUserId }">
				<kmss:auth requestURL="/km_archives_renew/kmArchivesRenew.do?method=add">
					<ui:button text="${lfn:message('km-archives:button.renew')}" order="6" onclick="archivesRenew('${param.fdId}');" />
				</kmss:auth>
			</c:if>
        </ui:toolbar>
        </c:if>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
            <ui:menu-item text="${ lfn:message('km-archives:table.kmArchivesBorrow') }" href="/km/archives/" target="_self" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
    <!-- 软删除配置 -->
	<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="kmArchivesBorrowForm"></c:param>
	</c:import>
        <%-- <form name="kmArchivesBorrowForm" method="post" action="<c:url value='/km/archives/km_archives_borrow/kmArchivesBorrow.do'/>"> --%>
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
                                <xform:text property="docSubject" showStatus="view" style="width:95%;" />
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesBorrow.docTemplate')}
                            </td>
                            <td width="35%">
                                <xform:dialog propertyId="docTemplateId" propertyName="docTemplateName" showStatus="view" style="width:95%;">

                                </xform:dialog>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesBorrow.fdBorrowDate')}
                            </td>
                            <td width="35%">
                                <xform:datetime property="fdBorrowDate" dateTimeType="datetime" showStatus="view" style="width:95%;" />
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesBorrow.fdBorrower')}
                            </td>
                            <td width="35%">
                                <xform:address propertyId="fdBorrowerId" propertyName="fdBorrowerName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesBorrow.docDept')}
                            </td>
                            <td width="35%">
                                <xform:address propertyId="docDeptId" propertyName="docDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" style="width:95%;" />
                            </td>
                        </tr>
                        <tr>
                        	<td colspan="4" width="100%">
                        		<center style="margin-top: 20px; margin-bottom: 20px">
	                        		<span><b>${lfn:message('km-archives:table.kmArchivesDetails')}</b>
	                        		</span>
                        		</center>
                        		<table class="tb_normal" width="100%" id="TABLE_DocList_fdBorrowDetail_Form" align="center" tbdraggable="true">
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
	                              		
	                                </tr>
	                                <c:forEach items="${kmArchivesBorrowForm.fdBorrowDetail_Form}" var="fdBorrowDetail_FormItem" varStatus="vstatus">
	                                    <tr KMSS_IsContentRow="1" id="${fdBorrowDetail_FormItem.fdArchId }">
	                                    	<td align="center">
		                                        ${vstatus.index+1}
	                                        </td>
	
	                                        <td align="center">
	                                            <input type="hidden" name="fdBorrowDetail_Form[${vstatus.index}].fdId" value="${fdBorrowDetail_FormItem.fdId}" />
	                                       		
	                                             <a target="_blank" href='<c:url value="/km/archives/km_archives_main/kmArchivesMain.do?method=view&fdId=${fdBorrowDetail_FormItem.fdArchives.fdId}"/>' style="color: blue;text-decoration: underline;"><xform:text property="fdBorrowDetail_Form[${vstatus.index}].fdArchives.docSubject" style="width:90%;" /></a>
	                                        </td>
	                                        <td align="center">
	                                           <xform:text property="fdBorrowDetail_Form[${vstatus.index}].fdArchives.docTemplate.fdName" style="width:90%;" />
	                                        </td>
	                                      	
	                                       	<td align="center">
	                                       		<c:choose>
													<c:when test="${not empty fdBorrowDetail_FormItem.fdArchives.fdValidityDate}">
														<kmss:showDate value="${fdBorrowDetail_FormItem.fdArchives.fdValidityDate }" type="date"></kmss:showDate>
													</c:when>
													<c:otherwise>
														<bean:message bundle="km-archives" key="kmArchivesMain.fdValidityDate.forever"/>
													</c:otherwise>
												</c:choose>
	                                       	</td>
	                                       	
	                                       	<td align="center">
	                                       		<xform:checkbox property="fdBorrowDetail_Form[${vstatus.index}].fdAuthorityRange" value="${fdBorrowDetail_FormItem.fdAuthorityRange }">
	                                       			<xform:simpleDataSource value="copy">${lfn:message('km-archives:kmArchivesConfig.fdDefaultRange.copy') }</xform:simpleDataSource>
													<xform:simpleDataSource value="download">${lfn:message('km-archives:kmArchivesConfig.fdDefaultRange.download') }</xform:simpleDataSource>
													<xform:simpleDataSource value="print">${lfn:message('km-archives:kmArchivesConfig.fdDefaultRange.print') }</xform:simpleDataSource>
	                                       		</xform:checkbox>
	                                       	</td>
	                                       	<td align="center">
	                                       		<xform:datetime property="fdBorrowDetail_Form[${vstatus.index}].fdReturnDate" value="${empty fdBorrowDetail_FormItem.fdRenewReturnDate?fdBorrowDetail_FormItem.fdReturnDate:fdBorrowDetail_FormItem.fdRenewReturnDate }" dateTimeType="datetime"/>
	                                       	</td>
	                                       	
	                                    </tr>
	                                </c:forEach>
	                            </table>
                        	</td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesBorrow.fdBorrowReason')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <xform:textarea property="fdBorrowReason" showStatus="view" style="width:95%;" />
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesBorrow.attBorrow')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
                                    <c:param name="fdKey" value="attBorrow" />
                                    <c:param name="formBeanName" value="kmArchivesBorrowForm" />
                                    <c:param name="fdRequired" value="true" />
                                    <c:param name="fdMulti" value="true" />
                                </c:import>
                            </td>
                        </tr>
                        <c:if test="${not empty kmArchivesBorrowForm.fdRemarks }">
	                        <tr>
	                        	<td class="td_normal_title" width="15%">
	                        		${lfn:message('km-archives:kmArchivesMain.fdRemarks')}
	                        	</td>
	                        	<td colspan="3" width="85%" style="color: red;">
	                        		<xform:textarea property="fdRemarks" htmlElementProperties="data-actor-expand='true'" style="width:90%;" />
	                        	</td>
	                        </tr>
                        </c:if>
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
                                <xform:datetime dateTimeType="datetime" property="docCreateTime" showStatus="view" style="width:95%;" />
                            </td>
                        </tr>
                    </table>
                </ui:content>
                <c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="kmArchivesBorrowForm" />
                    <c:param name="fdKey" value="kmArchivesBorrow" />
                    <c:param name="isExpand" value="true" />
                </c:import>

                <c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="kmArchivesBorrowForm" />
                    <c:param name="moduleModelName" value="com.landray.kmss.km.archives.model.KmArchivesBorrow" />
                </c:import>

            </ui:tabpage>
        <!-- </form> -->
    </template:replace>

</template:include>