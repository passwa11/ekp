<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
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
    
</style>
<script type="text/javascript">
    var editOption = {
        formName: 'kmArchivesTemplateForm',
        modelName: 'com.landray.kmss.km.archives.model.KmArchivesTemplate'


    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/km/archives/resource/js/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/km/archives/km_archives_template/kmArchivesTemplate.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${kmArchivesTemplateForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.kmArchivesTemplateForm, 'update');">
            </c:when>
            <c:when test="${kmArchivesTemplateForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.kmArchivesTemplateForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('km-archives:table.kmArchivesTemplate') }</p>
    <center>

        <table class="tb_normal" id="Label_Tabel" width="95%">
            <tr LKS_LabelName="${ lfn:message('km-archives:py.JiBenXinXi') }">
                <td>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesTemplate.fdName')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                            </td>
                        </tr>
                        <tr>
	                        <td class="td_normal_title" width="15%">
	                            ${lfn:message('km-archives:kmArchivesMain.fdDenseLevel')}
	                        </td>
	                        <td colspan="3" width="85.0%">
	                            <xform:checkbox property="listDenseLevelIds" showStatus="edit" 
	                            		subject="${lfn:message('km-archives:kmArchivesMain.fdDenseLevel')}">
	                                <xform:beanDataSource serviceBean="kmArchivesDenseService" />
	                            </xform:checkbox>
	                        </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesTemplate.fdOrder')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                            </td>
                        </tr>
						<tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:list.isDefaultFlag')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <xform:checkbox property="fdDefaultFlag" value="${ kmArchivesTemplateForm.fdDefaultFlag}" showStatus="edit">
									<xform:simpleDataSource value="1">
										<bean:message  bundle="km-archives" key="py.default" />
									</xform:simpleDataSource>
								</xform:checkbox>
                            </td>
                        </tr>                        
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesTemplate.authReaders')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                <div class="description_txt">
									<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
									    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
									        <!-- （为空则本组织人员可使用） -->
									        <bean:message  bundle="km-archives" key="authReaders.organizationUse" />
									    <% } else { %>
									        <!-- （为空则所有内部人员可使用） -->
									        <bean:message  bundle="km-archives" key="authReaders.tip" />
									    <% } %>
									<% } else { %>
									    <!-- （为空则所有人可使用） -->
									    <bean:message  bundle="km-archives" key="description.main.tempReader.nonOrganizationAllUse" />
									<% } %>
								</div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesTemplate.authEditors')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                <div class="description_txt">
									<bean:message bundle="km-archives" key="kmArchivesTemplate.authEditors.description"/>
								</div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesTemplate.docCreator')}
                            </td>
                            <td width="35%">
                            	${kmArchivesTemplateForm.docCreatorName}
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesTemplate.docCreateTime')}
                            </td>
                            <td width="35%">
                                <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <c:import url="/sys/lbpmservice/include/sysLbpmTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="kmArchivesTemplateForm" />
                <c:param name="fdKey" value="kmArchivesBorrow" />
                <c:param name="messageKey" value="km-archives:py.LiuChengDingYi" />
            </c:import>

        </table>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>