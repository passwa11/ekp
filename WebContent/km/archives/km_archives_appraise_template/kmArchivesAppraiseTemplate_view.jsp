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
    <kmss:auth requestURL="/km/archives/km_archives_appraise_template/kmArchivesAppraiseTemplate.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${ lfn:message('button.edit') }" onclick="Com_OpenWindow('kmArchivesAppraiseTemplate.do?method=edit&fdId=${param.fdId}','_self');">
    </kmss:auth>
    <kmss:auth requestURL="/km/archives/km_archives_appraise_template/kmArchivesAppraiseTemplate.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${ lfn:message('button.delete') }" onclick="if(!confirmDelete())return;Com_OpenWindow('kmArchivesAppraiseTemplate.do?method=delete&fdId=${param.fdId}','_self');">
    </kmss:auth>
    <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
</div>
<p class="txttitle">${ lfn:message('km-archives:table.kmArchivesAppraiseTemplate') }</p>
<center>

    <table class="tb_normal" id="Label_Tabel" width="95%">
        <tr LKS_LabelName="${ lfn:message('km-archives:py.JiBenXinXi') }">
            <td>
                <table class="tb_normal" width="100%">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('km-archives:kmArchivesAppraiseTemplate.fdName')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <xform:text property="fdName" showStatus="view" style="width:95%;" />
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('km-archives:kmArchivesAppraiseTemplate.fdOrder')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('km-archives:list.isDefaultFlag')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <xform:checkbox property="fdDefaultFlag" value="${ kmArchivesAppraiseTemplateForm.fdDefaultFlag}" showStatus="view">
								<xform:simpleDataSource value="1">
									<bean:message  bundle="km-archives" key="py.default" />
								</xform:simpleDataSource>
							</xform:checkbox>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('km-archives:kmArchivesAppraiseTemplate.authReaders')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('km-archives:kmArchivesAppraiseTemplate.authEditors')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('km-archives:kmArchivesAppraiseTemplate.docCreator')}
                        </td>
                        <td width="35%">
                            <ui:person personId="${kmArchivesAppraiseTemplateForm.docCreatorId}" personName="${kmArchivesAppraiseTemplateForm.docCreatorName}" />
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('km-archives:kmArchivesAppraiseTemplate.docCreateTime')}
                        </td>
                        <td width="35%">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <c:import url="/sys/lbpmservice/include/sysLbpmTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="kmArchivesAppraiseTemplateForm" />
            <c:param name="fdKey" value="kmArchivesAppraise" />
            <c:param name="messageKey" value="km-archives:py.LiuChengDingYi" />
        </c:import>

    </table>
</center>
<script>
    function confirmDelete(msg) {
        return confirm('${ lfn:message("page.comfirmDelete") }');
    }
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>