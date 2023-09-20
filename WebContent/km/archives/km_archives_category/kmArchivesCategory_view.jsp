<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
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
<div id="optBarDiv">
    <kmss:auth requestURL="/km/archives/km_archives_category/kmArchivesCategory.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${ lfn:message('button.edit') }" onclick="Com_OpenWindow('kmArchivesCategory.do?method=edit&fdId=${param.fdId}','_self');">
    </kmss:auth>
    <kmss:auth requestURL="/km/archives/km_archives_category/kmArchivesCategory.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${ lfn:message('button.delete') }" onclick="if(!confirmDelete())return;Com_OpenWindow('kmArchivesCategory.do?method=delete&fdId=${param.fdId}','_self');">
    </kmss:auth>
    <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
</div>
<p class="txttitle">${ lfn:message('km-archives:table.kmArchivesCategory') }</p>
<center>

    <table class="tb_normal" id="Label_Tabel" width="95%">
        <tr LKS_LabelName="${ lfn:message('km-archives:py.JiBenXinXi') }">
            <td>
                <table class="tb_normal" width="100%">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('km-archives:kmArchivesCategory.fdParent')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <a href="${KMSS_Parameter_ContextPath}km/archives/km_archives_category/kmArchivesCategory.do?method=view&fdId=${kmArchivesCategoryForm.fdParentId}" target="blank" class="com_btn_link">
                                <xform:dialog propertyId="fdParentId" propertyName="fdParentName" showStatus="view" style="width:95%;">
                                    Dialog_SimpleCategory('com.landray.kmss.km.archives.model.KmArchivesCategory','fdParentId','fdParentName',false,null,'01',null,false,'${JsParam.fdId}');
                                </xform:dialog>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('km-archives:kmArchivesCategory.fdName')}
                        </td>
                        <td width="35%">
                            <xform:text property="fdName" showStatus="view" style="width:95%;" />
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('km-archives:kmArchivesCategory.fdOrder')}
                        </td>
                        <td width="35%">
                            <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${ lfn:message('km-archives:py.BiaoQian') }
                        </td>
                        <td colspan="3" width="85.0%">
                            <c:import url="/sys/tag/import/sysTagTemplate_view.jsp" charEncoding="UTF-8">
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
                            <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('km-archives:kmArchivesCategory.authReaders')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('km-archives:kmArchivesCategory.docCreator')}
                        </td>
                        <td width="35%">
                            <ui:person personId="${kmArchivesCategoryForm.docCreatorId}" personName="${kmArchivesCategoryForm.docCreatorName}" />
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
        <c:import url="/sys/lbpmservice/include/sysLbpmTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="kmArchivesCategoryForm" />
            <c:param name="fdKey" value="kmArchivesMain" />
            <c:param name="messageKey" value="km-archives:py.LiuChengDingYi" />
        </c:import>

        <c:import url="/sys/number/include/sysNumberMappTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="kmArchivesCategoryForm" />
            <c:param name="modelName" value="com.landray.kmss.km.archives.model.KmArchivesMain" />
            <c:param name="messageKey" value="km-archives:py.BianHaoGuiZe" />
        </c:import>

        <%-- <tr LKS_LabelName="${ lfn:message('km-archives:py.MoRenQuanXian') }">
            <td>
                <table class="tb_normal" width=100%>
                    <c:import url="/sys/right/tmp_right_view.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="kmArchivesCategoryForm" />
                        <c:param name="moduleModelName" value="com.landray.kmss.km.archives.model.KmArchivesCategory" />
                    </c:import>
                </table>
            </td>
        </tr> --%>

    </table>
</center>
<script>
    function confirmDelete(msg) {
        return confirm('${ lfn:message("page.comfirmDelete") }');
    }
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>