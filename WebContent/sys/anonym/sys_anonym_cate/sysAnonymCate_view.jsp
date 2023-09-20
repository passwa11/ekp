<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
<%@ page import="com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils"%>
<%@ page import="com.landray.kmss.sys.anonym.forms.SysAnonymCateForm"%>
<%
String fdModelName = "";
Object obj = request.getAttribute("sysAnonymCateForm");
if(obj != null) {
	SysAnonymCateForm form = (SysAnonymCateForm)obj;
	fdModelName = form.getFdModelName();	
}
%>
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
	.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
		border: 0px;
		color: #868686
	}
</style>
<script type="text/javascript">
    if("${sysAnonymCateForm.fdName}" != "") {
        window.document.title = "${sysAnonymCateForm.fdName} - ${ lfn:message('sys-anonym:table.sysAnonymCate') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">
    <kmss:auth requestURL="/sys/anonym/sys_anonym_cate/sysAnonymCate.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('sysAnonymCate.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/sys/anonym/sys_anonym_cate/sysAnonymCate.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('sysAnonymCate.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('sys-anonym:table.sysAnonymCate') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('sys-anonym:sysAnonymCate.fdParent')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 父节点--%>
                    <a href="${KMSS_Parameter_ContextPath}sys/anonym/sys_anonym_cate/sysAnonymCate.do?method=view&fdId=${sysAnonymCateForm.fdParentId}" target="blank" class="com_btn_link">
                        <c:out value="${sysAnonymCateForm.fdParentName}" />
                    </a>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('sys-anonym:sysAnonymCate.fdName')}
                </td>
                <td colspan="3" width="85%">
                    <%-- 名称--%>
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
			</tr>
			
			<% if(SysAuthAreaUtils.isAreaEnabled(fdModelName)){ %>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-anonym" key="sysAnonymCate.fdOrgTreeName" />
				</td>
				<td width=35%>
					<c:out value="${sysAnonymCateForm.authAreaName}" />
				</td>
				<td class="td_normal_title" width="15%">
                    ${lfn:message('sys-anonym:sysAnonymCate.fdOrder')}
                </td>
                <td width="35%">
                    <%-- 排序号--%>
                    <div id="_xform_fdOrder" _xform_type="text">
                        <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
                    </div>
                </td>
			</tr>		
			<% } else { %>
			<tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('sys-anonym:sysAnonymCate.fdOrder')}
                </td>
                <td colspan="3" width="85%">
                    <%-- 排序号--%>
                    <div id="_xform_fdOrder" _xform_type="text">
                        <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>	
			<% } %>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('sys-anonym:sysAnonymCate.fdDesc')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 描述--%>
                    <div id="_xform_fdDesc" _xform_type="textarea">
                        <xform:textarea property="fdDesc" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
				<td class="td_normal_title" width=15%>
					<bean:message key="model.tempEditorName" />
				</td>
				<td colspan="3">
					<xform:address propertyId="authEditorIds" propertyName="authEditorNames" orgType="ORG_TYPE_ALL" style="width:90%" showStatus="view"/>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message key="model.tempReaderName" />
				</td>
				<td colspan="3">
					<xform:address propertyId="authReaderIds" propertyName="authReaderNames" orgType="ORG_TYPE_ALL" style="width:90%" showStatus="view"/>
				</td>
			</tr>
			<tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('sys-anonym:sysAnonymCate.docCreator')}
                </td>
                <td width="35%">
                    <%-- 创建人--%>
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${sysAnonymCateForm.docCreatorId}" personName="${sysAnonymCateForm.docCreatorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('sys-anonym:sysAnonymCate.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <c:if test="${sysAnonymCateForm.docAlterorName != ''}">
	            <tr>
	                <td class="td_normal_title" width="15%">
	                    ${lfn:message('sys-anonym:sysAnonymCate.docAlteror')}
	                </td>
	                <td width="35%">
	                    <%-- 修改人--%>
	                    <div id="_xform_docAlterorId" _xform_type="address">
	                        <ui:person personId="${sysAnonymCateForm.docAlterorId}" personName="${sysAnonymCateForm.docAlterorName}" />
	                    </div>
	                </td>
	                <td class="td_normal_title" width="15%">
	                    ${lfn:message('sys-anonym:sysAnonymCate.docAlterTime')}
	                </td>
	                <td width="35%">
	                    <%-- 更新时间--%>
	                    <div id="_xform_docAlterTime" _xform_type="datetime">
	                        <xform:datetime property="docAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
	                    </div>
	                </td>
	            </tr>
            </c:if>
            <tr style="display:none">
                <td class="td_normal_title" width="15%">
                    ${lfn:message('sys-anonym:sysAnonymCate.fdisinherituser')}
                </td>
                <td width="35%">
                    <%-- 继承可使用者--%>
                    <div id="_xform_fdisinherituser" _xform_type="radio">
                        <xform:radio property="fdisinherituser" htmlElementProperties="id='fdisinherituser'" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('sys-anonym:sysAnonymCate.fdisinheritmaintainer')}
                </td>
                <td width="35%">
                    <%-- 继承可维护者--%>
                    <div id="_xform_fdisinheritmaintainer" _xform_type="radio">
                        <xform:radio property="fdisinheritmaintainer" htmlElementProperties="id='fdisinheritmaintainer'" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</center>
<script>
    var formInitData = {

    };

    function confirmDelete(msg) {
        return confirm('${ lfn:message("page.comfirmDelete") }');
    }

    function openWindowViaDynamicForm(popurl, params, target) {
        var form = document.createElement('form');
        if(form) {
            try {
                target = !target ? '_blank' : target;
                form.style = "display:none;";
                form.method = 'post';
                form.action = popurl;
                form.target = target;
                if(params) {
                    for(var key in params) {
                        var
                        v = params[key];
                        var vt = typeof
                        v;
                        var hdn = document.createElement('input');
                        hdn.type = 'hidden';
                        hdn.name = key;
                        if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                            hdn.value =
                            v +'';
                        } else {
                            if($.isArray(
                                v)) {
                                hdn.value =
                                v.join(';');
                            } else {
                                hdn.value = toString(
                                    v);
                            }
                        }
                        form.appendChild(hdn);
                    }
                }
                document.body.appendChild(form);
                form.submit();
            } finally {
                document.body.removeChild(form);
            }
        }
    }

    function doCustomOpt(fdId, optCode) {
        if(!fdId || !optCode) {
            return;
        }

        if(viewOption.customOpts && viewOption.customOpts[optCode]) {
            var param = {
                "List_Selected_Count": 1
            };
            var argsObject = viewOption.customOpts[optCode];
            if(argsObject.popup == 'true') {
                var popurl = viewOption.contextPath + argsObject.popupUrl + '&fdId=' + fdId;
                for(var arg in argsObject) {
                    param[arg] = argsObject[arg];
                }
                openWindowViaDynamicForm(popurl, param, '_self');
                return;
            }
            var optAction = viewOption.contextPath + viewOption.basePath + '?method=' + optCode + '&fdId=' + fdId;
            Com_OpenWindow(optAction, '_self');
        }
    }
    window.doCustomOpt = doCustomOpt;
    var viewOption = {
        contextPath: '${LUI_ContextPath}',
        basePath: '/sys/anonym/sys_anonym_cate/sysAnonymCate.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>