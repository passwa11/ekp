<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.view">
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
<template:replace name="toolbar">
	<ui:toolbar layout="sys.ui.toolbar.float" >
	    <kmss:auth requestURL="/kms/category/kms_category_main/kmsCategoryMain.do?method=edit&fdId=${param.fdId}">
	    	<ui:button text="${ lfn:message('button.edit') }" onclick="Com_OpenWindow('kmsCategoryMain.do?method=edit&fdId=${param.fdId}','_self');">
			</ui:button>
	    </kmss:auth>
	    <ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
	</ui:toolbar>
</template:replace>
<template:replace name="content">
	<p class="txttitle">${ lfn:message('kms-category:table.kmsCategoryMain') }</p>
	<center>
	
	    <div style="width:95%;">
	        <table class="tb_normal" width="100%">
	            <tr>
	                <td class="td_normal_title" width="15%">
	                    ${lfn:message('kms-category:kmsCategoryMain.fdParent')}
	                </td>
	                <td colspan="3" width="85.0%">
	                    <a href="${KMSS_Parameter_ContextPath}kms/category/kms_category_main/kmsCategoryMain.do?method=view&fdId=${kmsCategoryMainForm.fdParentId}" target="blank" class="com_btn_link">
	                        <c:out value="${kmsCategoryMainForm.fdParentName}" />
	                    </a>
	                </td>
	            </tr>
	            <tr>
	                <td class="td_normal_title" width="15%">
	                    ${lfn:message('kms-category:kmsCategoryMain.fdName')}
	                </td>
	                <td colspan="3" width="85.0%">
	                    <div id="_xform_fdName" _xform_type="text">
	                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
	                    </div>
	                </td>
	            </tr>
	            
	            <tr>
	                <td class="td_normal_title" width="15%">
	                    ${lfn:message('kms-category:kmsCategoryMain.authEditors')}
	                </td>
	                <td colspan="3" width="85.0%">
	                    <div id="_xform_authEditorIds" _xform_type="address">
	                        <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
	                    </div>
	                </td>
	            </tr>
	            
	            <!-- 知识标签 -->
				<c:import url="/sys/tag/import/sysTagMain_view.jsp" charEncoding="UTF-8">
						<c:param name="isInContent" value="true"></c:param>
						<c:param name="showTitle" value="true"/>
						<c:param name="useTab" value="true"></c:param>
						<c:param name="formName" value="kmsCategoryMainForm" />
						<c:param name="showEditButton" value="false"></c:param>
				</c:import>
	            
	            <tr>
	                <td class="td_normal_title" width="15%">
	                    ${lfn:message('kms-category:kmsCategoryMain.fdDesc')}
	                </td>
	                <td colspan="3" width="85.0%">
	                    <div id="_xform_fdDesc" _xform_type="textarea">
	                        <xform:textarea property="fdDesc" showStatus="view" style="width:95%;" />
	                    </div>
	                </td>
	            </tr>
	        </table>
	    </div>
	</center>
</template:replace>
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
        basePath: '/kms/category/kms_category_main/kmsCategoryMain.do',
        customOpts: {

            ____fork__: 0
        }
    };
</script>
</template:include>