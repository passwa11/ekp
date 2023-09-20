<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
            pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
<style type="text/css">
.lui_paragraph_title {
	font-size: 15px;
	color: #15a4fa;
	padding: 15px 0px 5px 0px;
}

.lui_paragraph_title span {
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
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
<script>
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
<ui:content title="证书信息" expand="true">
	<table class="tb_normal" width="100%">
		<tr>
			<td class="td_normal_title" width="15%">证书名字</td>
			<td width="35%">
				<%-- 证书信息--%>
				<div id="_xform_fdDn" _xform_type="text">
					<xform:text property="elecCoreCertBindForm.fdCertName" showStatus="view"
						style="width:95%;" />
				    <input name="elecCoreCertBindForm.fdCertId" hidden="true"/>
				</div>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width="15%">
				${lfn:message('elec-core-certification:elecCoreCertBind.fdDn')}</td>
			<td width="35%">
				<%-- 证书Dn号--%>
				<div id="_xform_fdDn" _xform_type="text">
					<xform:text property="elecCoreCertBindForm.fdDn" showStatus="view"
						style="width:95%;" />
				</div>
			</td>
		</tr>
	</table>
</ui:content>
