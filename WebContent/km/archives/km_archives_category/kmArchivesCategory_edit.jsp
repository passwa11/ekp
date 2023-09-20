<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%> 
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
        formName: 'kmArchivesCategoryForm',
        modelName: 'com.landray.kmss.km.archives.model.KmArchivesCategory'


    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/km/archives/resource/js/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/km/archives/km_archives_category/kmArchivesCategory.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${kmArchivesCategoryForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.kmArchivesCategoryForm, 'update');">
            </c:when>
            <c:when test="${kmArchivesCategoryForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.kmArchivesCategoryForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('km-archives:table.kmArchivesCategory') }</p>
    <center>

        <table class="tb_normal" id="Label_Tabel" width="95%">
            <tr LKS_LabelName="${ lfn:message('km-archives:py.JiBenXinXi') }">
                <td>
                    <table class="tb_normal" width="100%">
                    	<tr>
                    		<td colspan="4" style="color:red;">
								${lfn:message('km-archives:number.cannot.chinese')}
							</td>
                    	</tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesCategory.fdParent')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <xform:dialog propertyId="fdParentId" propertyName="fdParentName" showStatus="edit" style="width:95%;">
                                    Dialog_SimpleCategory('com.landray.kmss.km.archives.model.KmArchivesCategory','fdParentId','fdParentName',false,Cate_getParentMaintainer,'01',null,false,'${JsParam.fdId}');
                                </xform:dialog>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesCategory.fdName')}
                            </td>
                            <td width="35%">
                                <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesCategory.fdOrder')}
                            </td>
                            <td width="35%">
                                <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                            </td>
                        </tr>
                        <tr>
							<td class="td_normal_title" width=15%><bean:message bundle="sys-simplecategory"
								key="sysSimpleCategory.parentMaintainer" /></td>
							<td colspan="3" id="parentMaintainerId">${parentMaintainer}</td>
						</tr>
                        <%--属性 --%>
						<c:import url="/sys/property/include/sysPropertyTemplate_select.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmArchivesCategoryForm" />
							<c:param name="mainModelName" value="com.landray.kmss.km.archives.model.KmArchivesMain" />
						</c:import>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${ lfn:message('km-archives:py.BiaoQian') }
                            </td>
                            <td colspan="3" width="85.0%">
                                <c:import url="/sys/tag/import/sysTagTemplate_edit.jsp" charEncoding="UTF-8">
                                    <c:param name="formName" value="kmArchivesCategoryForm" />
                                    <c:param name="fdKey" value="kmArchivesMain" />
                                    <c:param name="useTab" value="false" />
                                </c:import>

                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesCategory.authEditors')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                <div class="description_txt">
									${lfn:message('km-archives:kmArchivesCategory.authEditors.description')}
								</div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesCategory.authReaders')}
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
                        <c:if test="${kmArchivesCategoryForm.method_GET!='add' }">	
							<tr>
								<td class="td_normal_title" width="15%"> 
									将修改应用到
								</td>
								<td colspan=3>
									 <input type='checkbox' name="appToMyDoc"  value='appToMyDoc'/>本分类下的文档
									 <input type='checkbox' name="appToChildren"  value='appToChildren'/>子分类设置
								</td>
							</tr>
						</c:if>	
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesCategory.docCreator')}
                            </td>
                            <td width="35%">
                            	${kmArchivesCategoryForm.docCreatorName}
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesCategory.docCreateTime')}
                            </td>
                            <td width="35%">
                                <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <c:import url="/sys/lbpmservice/include/sysLbpmTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="kmArchivesCategoryForm" />
                <c:param name="fdKey" value="kmArchivesMain" />
                <c:param name="messageKey" value="km-archives:py.LiuChengDingYi" />
            </c:import>

            <c:import url="/sys/number/include/sysNumberMappTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="kmArchivesCategoryForm" />
                <c:param name="modelName" value="com.landray.kmss.km.archives.model.KmArchivesMain" />
                <c:param name="messageKey" value="km-archives:py.BianHaoGuiZe" />
            </c:import>
			<tr LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />">
                <td>
                    <table class="tb_normal" width=100%>
                        <c:import url="/sys/right/tmp_right_edit.jsp" charEncoding="UTF-8">
                            <c:param name="formName" value="kmArchivesCategoryForm" />
                            <c:param name="moduleModelName" value="com.landray.kmss.km.archives.model.KmArchivesCategory" />
                        </c:import>
                    </table>
                </td>
            </tr> 

        </table>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
        
        function Cate_getParentMaintainer(){
    		<%
	    		String requestURL = "/km/archives/km_archives_category/kmArchivesCategory.do?method=add";
	    		requestURL = StringEscapeUtils.escapeHtml(requestURL);
	    		requestURL = requestURL.replaceAll("&amp;", "&");
	    				
	    		requestURL = requestURL.substring(0,requestURL.indexOf("?"));
	    		if(requestURL.startsWith("/")){
	    			requestURL = requestURL.substring(1);
	    		}
    		%>
    		var parameters ="parentId="+document.getElementsByName("fdParentId")[0].value;
    		var s_url = Com_Parameter.ContextPath+"<%=requestURL%>?method=getParentMaintainer";
    		$.ajax({
   				url: s_url,
   				type: "GET",
   				data: parameters,
   				dataType:"text",
   				async: false,
   				success: function(text){
   					$(document.getElementById("parentMaintainerId")).text(text);
   				}
    		});
    	}
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>