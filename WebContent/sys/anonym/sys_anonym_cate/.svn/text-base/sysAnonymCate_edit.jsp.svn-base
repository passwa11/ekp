<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
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
    if("${sysAnonymCateForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('sys-anonym:table.sysAnonymCate') }";
    }
    if("${sysAnonymCateForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('sys-anonym:table.sysAnonymCate') }";
    }
    var formInitData = {

    };
    var messageInfo = {

    };
    var initData = {
        contextPath: '${LUI_ContextPath}',
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/sys/anonym/sys_anonym_cate/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/sys/anonym/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/sys/anonym/sys_anonym_cate/sysAnonymCate.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${sysAnonymCateForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.sysAnonymCateForm, 'update');}">
            </c:when>
            <c:when test="${sysAnonymCateForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.sysAnonymCateForm, 'save');}">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
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
                        <div id="_xform_fdParentId" _xform_type="dialog">
                            <xform:dialog propertyId="fdParentId" propertyName="fdParentName" showStatus="edit" style="width:95%;">
                                Dialog_Tree(false, 'fdParentId', 'fdParentName', ',', 'sysAnonymCateService&modelName=<%=fdModelName %>&parentId=!{value}&fdId=${sysAnonymCateForm.fdParentId}&showType=edit', '${lfn:message('sys-anonym:treeModel.alert.cateAlert')}', null, null, null, null, false, null);
                            </xform:dialog>
                        </div>
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
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" required="true" validators="uniqueName"/>
                        </div>
                    </td>
                </tr>
                
				<tr>
					<td class="td_normal_title" width="15%">
                        ${lfn:message('sys-anonym:sysAnonymCate.fdOrder')}
                    </td>
                    <td colspan="3" width="85%">
                        <%-- 排序号--%>
                        <div id="_xform_fdOrder" _xform_type="text">
                            <xform:text property="fdOrder" showStatus="edit" style="width:25%;" />
                        </div>
                    </td>
                </tr>		
                
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('sys-anonym:sysAnonymCate.fdDesc')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 描述--%>
                        <div id="_xform_fdDesc" _xform_type="textarea">
                            <xform:textarea property="fdDesc" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
					<td class="td_normal_title" width=15%>
						<bean:message key="model.tempEditorName" />
					</td>
					<td colspan="3">
						<xform:address textarea="true" mulSelect="true" propertyId="authEditorIds" propertyName="authEditorNames" orgType="ORG_TYPE_ALL" style="width:90%" />
						<div class="description_txt">
							<bean:message	bundle="sys-anonym" key="description.main.cateEditor" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message key="model.tempReaderName" />
					</td>
					<td colspan="3">
						<xform:address textarea="true" mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" orgType="ORG_TYPE_ALL" style="width:90%" />
						<div class="description_txt">
							<bean:message	bundle="sys-anonym" key="description.main.cateReader" />
						</div>
					</td>
				</tr>
                <tr style="display:none">
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('sys-anonym:sysAnonymCate.fdisinherituser')}
                    </td>
                    <td width="35%">
                        <%-- 继承可使用者--%>
                        <div id="_xform_fdisinherituser" _xform_type="radio">
                            <xform:radio property="fdisinherituser" htmlElementProperties="id='fdisinherituser'" showStatus="edit" value="1">
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
                            <xform:radio property="fdisinheritmaintainer" htmlElementProperties="id='fdisinheritmaintainer'" showStatus="edit" value="1">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message key="model.fdCreator" />
					</td>
					<td width=35%>
						${sysAnonymCateForm.docCreatorName}
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message key="model.fdCreateTime" />
					</td>
					<td width=35%>
						<xform:datetime property="docCreateTime" showStatus="view"/>
					</td>
				</tr>
				<c:if test="${sysAnonymCateForm.method_GET!='add'}">
					<c:if test="${sysAnonymCateForm.docAlterorName!=''}">
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message key="model.docAlteror" />
							</td>
							<td width=35%>
								${sysAnonymCateForm.docAlterorName}
							</td>
							<td class="td_normal_title" width=15%>
								<bean:message key="model.fdAlterTime" />
							</td>
							<td width=35%>
								<xform:datetime property="docAlterTime" showStatus="view" />
							</td>
						</tr>
					</c:if>
				</c:if>
            </table>
        </div>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <html:hidden property="fdModelName" />
    <script>
        $KMSSValidation();
    </script>
    <script type="text/javascript">
    	validations = $KMSSValidation();
    	validations.addValidator(
			"uniqueName","<bean:message key='error.same.cateName' bundle='sys-anonym'/>",
			function(value,object,text) {
				return checkUniqueName(value);
			}
    	);
    	
    	function checkUniqueName(cateName) {
    		var flag = false;
    		var url = Com_Parameter.ContextPath + "sys/anonym/sys_anonym_cate/sysAnonymCate.do?method=checkUniqueName";
    		var cateId = "${sysAnonymCateForm.fdId}";
    		var modelName = "${sysAnonymCateForm.fdModelName}";
    		var fdParentId = document.getElementsByName('fdParentId')[0].value;
    		$.ajax({
    			url : url,
    			type : 'POST',
    			async : false,
    			data : {
    				"fdId" : cateId,
    				"fdParentId" : fdParentId,
    				"modelName" : modelName,
    				"cateName" : cateName
    			},
    			success : function(data) {
    				data = eval ("(" + data + ")");
    				if (data) {
    					if (data.flag == "true") {
    						flag = true;
    					} 
    				} 
    			}
    		});
    		return flag;
    	}
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>