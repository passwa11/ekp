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
    	.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
      		border: 0px;
      		color: #868686
    	}
    
</style>
<script type="text/javascript">
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
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
        basePath: '/fssc/budgeting/fssc_budgeting_period/fsscBudgetingPeriod.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
</template:replace>
 <template:replace name="title">
    <c:out value="${ lfn:message('fssc-budgeting:table.fsscBudgetingPeriod') }" />
</template:replace>
<div id="optBarDiv">

    
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<template:replace name="toolbar">
	     <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<kmss:auth requestURL="/fssc/budgeting/fssc_budgeting_period/fsscBudgetingPeriod.do?method=edit&fdId=${param.fdId}">
		       <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscBudgetingPeriod.do?method=edit&fdId=${param.fdId}','_self');" order="1" />
		   </kmss:auth>
		   <kmss:auth requestURL="/fssc/budgeting/fssc_budgeting_period/fsscBudgetingPeriod.do?method=delete&fdId=${param.fdId}">
		       <ui:button text="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscBudgetingPeriod.do?method=delete&fdId=${param.fdId}','_self');" order="2"/>
		   </kmss:auth>
	         <ui:button text="${ lfn:message('button.close') }" order="3" onclick="Com_CloseWindow();" />
	    </ui:toolbar>
</template:replace>
<template:replace name="path">
    <ui:menu layout="sys.ui.menu.nav">
        <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
        <ui:menu-item text="${ lfn:message('fssc-budgeting:table.fsscBudgetingPeriod') }" />
    </ui:menu>
</template:replace>
<template:replace name="content">
<p class="txttitle">${ lfn:message('fssc-budgeting:table.fsscBudgetingPeriod') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-budgeting:fsscBudgetingPeriod.fdName')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 名称--%>
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-budgeting:fsscBudgetingPeriod.fdStartPeriod')}
                </td>
                <td width="35%">
                    <%-- 开始期间--%>
                    <div id="_xform_fdStartPeriod" _xform_type="text">
                        <xform:text property="fdStartPeriod" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-budgeting:fsscBudgetingPeriod.fdEndPeriod')}
                </td>
                <td width="35%">
                    <%-- 结束期间--%>
                    <div id="_xform_fdEndPeriod" _xform_type="text">
                        <xform:text property="fdEndPeriod" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-budgeting:fsscBudgetingPeriod.fdIsAvailable')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 是否有效--%>
                    <div id="_xform_fdIsAvailable" _xform_type="radio">
                        <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-budgeting:fsscBudgetingPeriod.docCreator')}
                </td>
                <td width="35%">
                    <%-- 创建人--%>
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${fsscBudgetingPeriodForm.docCreatorId}" personName="${fsscBudgetingPeriodForm.docCreatorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-budgeting:fsscBudgetingPeriod.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-budgeting:fsscBudgetingPeriod.docAlteror')}
                </td>
                <td width="35%">
                    <%-- 修改人--%>
                    <div id="_xform_docAlterorId" _xform_type="address">
                        <ui:person personId="${fsscBudgetingPeriodForm.docAlterorId}" personName="${fsscBudgetingPeriodForm.docAlterorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-budgeting:fsscBudgetingPeriod.docAlterTime')}
                </td>
                <td width="35%">
                    <%-- 更新时间--%>
                    <div id="_xform_docAlterTime" _xform_type="datetime">
                        <xform:datetime property="docAlterTime" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
        </table>
    </div>
</center>
</template:replace>
</template:include>
