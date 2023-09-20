<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

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
                formName: 'kmArchivesMainForm',
                modelName: 'com.landray.kmss.km.archives.model.KmArchivesMain',
                templateName: 'com.landray.kmss.km.archives.model.KmArchivesCategory',
                subjectField: 'docSubject',
                mode: 'main_scategory'


            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/km/archives/resource/js/", 'js', true);
        </script>
    </template:replace>
    <c:if test="${kmArchivesMainForm.method_GET == 'edit' || (param['i.docTemplate']!=null && param['i.docTemplate']!='') || 'true' eq newEdition}">
        <template:replace name="title">
            <c:choose>
                <c:when test="${kmArchivesMainForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('km-archives:table.kmArchivesMain') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${kmArchivesMainForm.docSubject} - " />
                    <c:out value="${ lfn:message('km-archives:table.kmArchivesMain') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
        <c:if test="${kmArchivesMainForm.docDeleteFlag ==1}">
			<ui:toolbar id="toolbar" style="display:none;"></ui:toolbar>
		</c:if>
		<c:if test="${kmArchivesMainForm.docDeleteFlag !=1}">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ kmArchivesMainForm.method_GET == 'edit' }">
                        <c:if test="${ kmArchivesMainForm.docStatus=='10' || kmArchivesMainForm.docStatus=='11' }">
                            <ui:button text="${ lfn:message('button.savedraft') }" onclick="submitForm('10','update');" />
                        </c:if>
                        <c:if test="${ kmArchivesMainForm.docStatus=='10' || kmArchivesMainForm.docStatus=='11' || kmArchivesMainForm.docStatus=='20' }">
                            <ui:button text="${ lfn:message('button.submit') }" onclick="submitForm('20','update');" />
                        </c:if>
                    </c:when>
                    <c:when test="${ kmArchivesMainForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.savedraft') }" order="2" onclick="submitForm('10','save',true);" />
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
                <ui:menu-item text="${ lfn:message('km-archives:table.kmArchivesMain') }" />
                <ui:menu-source autoFetch="false">
					<ui:source type="AjaxJson">
						{"url":"/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=path&modelName=com.landray.kmss.km.archives.model.KmArchivesCategory&categoryId=${kmArchivesMainForm.docTemplateId}&currId=!{value}&authType=2&pAdmin=!{pAdmin}"}
					</ui:source>
				</ui:menu-source>
            </ui:menu>
        </template:replace>
        <template:replace name="content">
		<!-- 软删除部署 -->
		<c:import url="/sys/recycle/import/redirect.jsp">
			<c:param name="formBeanName" value="kmArchivesMainForm"></c:param>
		</c:import>        
            <html:form action="/km/archives/km_archives_main/kmArchivesMain.do">
				<div class='lui_form_title_frame'>
                    <div class='lui_form_subject'>
                        ${lfn:message('km-archives:table.kmArchivesMain')}
                    </div>
                    <div class='lui_form_baseinfo'>

                    </div>
                </div>
                <ui:tabpage expand="false" var-navwidth="90%">
                    <ui:content title="${ lfn:message('km-archives:py.JiBenXinXi') }" expand="true">
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('km-archives:kmArchivesMain.docSubject')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <xform:text property="docSubject" showStatus="edit" style="width:95%;" />
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('km-archives:kmArchivesMain.docTemplate')}
                                </td>
                                <td width="35%">
                                	<html:hidden property="docTemplateId" />
                                    <xform:dialog propertyId="docTemplateId" propertyName="docTemplateName" showStatus="view" style="width:95%;">
                                    </xform:dialog>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('km-archives:kmArchivesMain.docNumber')}
                                </td>
                                <td width="35%">
                                	<c:if test="${kmArchivesMainForm.docStatus==10 || kmArchivesMainForm.docStatus==null || kmArchivesMainForm.docStatus=='' }">
                                		${lfn:message('km-archives:kmArchivesMain.no.per')}
                                	</c:if>
                                    <xform:text property="docNumber" showStatus="view" style="width:95%;" />
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('km-archives:kmArchivesMain.fdLibrary')}
                                </td>
                                <td width="35%">
                                    <xform:select property="fdLibrary" showStatus="edit" subject="${lfn:message('km-archives:kmArchivesMain.fdLibrary')}">
                                        <xform:beanDataSource serviceBean="kmArchivesLibraryService" selectBlock="fdName" orderBy="fdOrder" />
                                    </xform:select>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('km-archives:kmArchivesMain.fdVolumeYear')}
                                </td>
                                <td width="35%">
                                	<xform:select property="fdVolumeYear">
                                	<% int nowYear = Calendar.getInstance().get(Calendar.YEAR);
                                		for(int x = nowYear;x>=1967;x--) { 
                                		pageContext.setAttribute("selectYearIndex",x);%>
                                		<xform:simpleDataSource value="${selectYearIndex }"></xform:simpleDataSource>
                                		<%} %>
                                	</xform:select>
                                    <%-- <xform:datetime pattern="yyyy" property="fdVolumeYear" showStatus="edit" dateTimeType="date" style="width:95%;" /> --%>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('km-archives:kmArchivesMain.fdPeriod')}
                                </td>
                                <td width="35%">
                                    <xform:select property="fdPeriod" showStatus="edit" subject="${lfn:message('km-archives:kmArchivesMain.fdPeriod')}">
                                        <xform:beanDataSource serviceBean="kmArchivesPeriodService" selectBlock="fdName" whereBlock="" orderBy="fdOrder" />
                                    </xform:select>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('km-archives:kmArchivesMain.fdUnit')}
                                </td>
                                <td width="35%">
                                    <xform:select property="fdUnit" showStatus="edit" subject="${lfn:message('km-archives:kmArchivesMain.fdUnit')}">
                                        <xform:beanDataSource serviceBean="kmArchivesUnitService" selectBlock="fdName" whereBlock="" orderBy="fdOrder asc" />
                                    </xform:select>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('km-archives:kmArchivesMain.fdStorekeeper')}
                                </td>
                                <td width="35%">
                                    <xform:address propertyId="fdStorekeeperId" propertyName="fdStorekeeperName" orgType="ORG_TYPE_PERSON" showStatus="edit" required="true" subject="${lfn:message('km-archives:kmArchivesMain.fdStorekeeper')}" style="width:95%;" />
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('km-archives:kmArchivesMain.fdValidityDate')}
                                </td>
                                <td width="35%">
                                    <xform:datetime onValueChange="null" property="fdValidityDate" showStatus="edit" dateTimeType="date" style="width:95%;" />
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('km-archives:kmArchivesMain.fdDenseLevel')}
                                </td>
                                <td width="35%">
                                    <xform:select property="fdDenseLevel" showStatus="edit" subject="${lfn:message('km-archives:kmArchivesMain.fdDenseLevel')}">
                                        <xform:beanDataSource serviceBean="kmArchivesDenseService" selectBlock="fdName" orderBy="fdOrder" />
                                    </xform:select>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('km-archives:kmArchivesMain.fdFileDate')}
                                </td>
                                <td width="35%">
                                    <xform:datetime onValueChange="null" required="true" property="fdFileDate" showStatus="edit" dateTimeType="date" style="width:95%;" />
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('km-archives:kmArchivesMain.fdRemarks')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <xform:textarea property="fdRemarks" showStatus="edit" style="width:95%;" />
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('km-archives:kmArchivesMain.docCreator')}
                                </td>
                                <td width="35%">
                                    <%-- <ui:person personId="${kmArchivesMainForm.docCreatorId}" personName="${kmArchivesMainForm.docCreatorName}" /> --%>
                                	<xform:text property="docCreatorName" showStatus="view" style="width:95%;" />
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('km-archives:kmArchivesMain.docCreateTime')}
                                </td>
                                <td width="35%">
                                    <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                                </td>
                            </tr>
                        </table>
                    </ui:content>
                    <!-- 文件级 -->
                    <ui:content title="${ lfn:message('km-archives:kmArchivesMain.fileLevel') }" expand="true">
                    	<table class="tb_normal" width="100%">
	                    <c:if test="${not empty kmArchivesMainForm.extendFilePath }">
	                    	<c:import url="/sys/property/include/sysProperty_edit.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmArchivesMainForm" />
								<c:param name="fdDocTemplateId" value="${kmArchivesMainForm.docTemplateId}" />
							</c:import>
	                    </c:if>
	                    <!-- 附件机制 -->
	                    <tr>
                            <td class="td_normal_title" width="15%">
                            	${ lfn:message('km-archives:kmArchivesMain.attachement') }
                            </td>
                            <td colspan="3" width="85.0%">
	                    		<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
	                            	<c:param name="fdKey" value="attArchivesMain" />
	                            	<c:param name="formBeanName" value="kmArchivesMainForm" />
	                            	<c:param name="fdRequired" value="true" />
	                        	</c:import>
	                        </td>
	                     </tr>
                         </table>
                    </ui:content>
                    <!-- 流程 -->
                    <c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="kmArchivesMainForm" />
                        <c:param name="fdKey" value="kmArchivesMain" />
                        <c:param name="isExpand" value="true" />
                    </c:import>
					<!-- 权限-->
                    <%-- <c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="kmArchivesMainForm" />
                        <c:param name="moduleModelName" value="com.landray.kmss.km.archives.model.KmArchivesMain" />
                    </c:import> --%>
                    <c:import url="/km/archives/import/right_edit.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="kmArchivesMainForm" />
                    </c:import>
                    <!-- 嵌入 版本 模板标签的代码 -->
					<c:import url="/sys/edition/import/sysEditionMain_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmArchivesMainForm" />
					</c:import>	

                </ui:tabpage>
                <html:hidden property="fdId" />
                <html:hidden property="docStatus" />
                <html:hidden property="method_GET" />
            </html:form>
        </template:replace>

    </c:if>
</template:include>