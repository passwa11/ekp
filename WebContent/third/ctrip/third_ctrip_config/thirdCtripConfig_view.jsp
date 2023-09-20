<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
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
<div class="lui_list_operation">
		<div style="float:right">
             <ui:toolbar count="4">
			    <kmss:auth requestURL="/third/ctrip/third_ctrip_config/thirdCtripConfig.do?method=edit&fdId=${param.fdId}">
			        <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdCtripConfig.do?method=edit&fdId=${param.fdId}','_self');" order="1" />
			        <ui:button text="${lfn:message('third-ctrip:thirdCtripConfig.clean.time')}" onclick="cleanTime();" order="4" />
			    </kmss:auth>
			    <kmss:auth requestURL="/third/ctrip/third_ctrip_config/thirdCtripConfig.do?method=delete&fdId=${param.fdId}">
			        <ui:button text="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdCtripConfig.do?method=delete&fdId=${param.fdId}','_self');" order="2"/>
			    </kmss:auth>
			    <ui:button text="${lfn:message('button.close')}" onclick="Com_CloseWindow();" order="3"/>
    		</ui:toolbar>
	     </div>
     </div>
     <h2 align="center" style="margin:10px 0">
		<p class="txttitle">${ lfn:message('third-ctrip:table.thirdCtripConfig') }</p>
	</h2>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripConfig.fdName')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripConfig.fdAppKey')}
                </td>
                <td width="35%">
                    <div id="_xform_fdAppKey" _xform_type="text">
                        <xform:text property="fdAppKey" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripConfig.fdAppSecurity')}
                </td>
                <td width="35%">
                    <div id="_xform_fdAppSecurity" _xform_type="text">
                        <xform:text property="fdAppSecurity" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripConfig.fdCorpId')}
                </td>
                <td width="35%">
                    <div id="_xform_fdCorpId" _xform_type="text">
                        <xform:text property="fdCorpId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripConfig.fdIsAvailable')}
                </td>
                <td width="35%">
                    <div id="_xform_fdIsAvailable" _xform_type="radio">
                        <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripConfig.fdIsSysnch')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdIsSysnch" _xform_type="radio">
                        <xform:radio property="fdIsSysnch" htmlElementProperties="id='fdIsSysnch'" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
            <tr <c:if test="${thirdCtripConfigForm.fdIsSysnch=='false' }">style="display: none"</c:if>>
                <td colspan="4" width="100%">
                    <table class="tb_normal" width="100%" id="TABLE_DocList_fdAccount_Form" align="center" tbdraggable="true">
                        <tr align="center" class="tr_normal_title">
                            <td style="width:6%;">
                                ${lfn:message('page.serial')}
                            </td>
                            <td style="width:58%;">
                                ${lfn:message('third-ctrip:thirdCtripAccount.fdAccount')}
                            </td>
                            <td style="width:36%;">
                                ${lfn:message('third-ctrip:thirdCtripAccount.fdName')}
                            </td>
                        </tr>
                        <c:forEach items="${thirdCtripConfigForm.fdAccount_Form}" var="fdAccount_FormItem" varStatus="vstatus">
                            <tr KMSS_IsContentRow="1">
                                <td align="center">
                                    ${vstatus.index+1}
                                </td>
                                <td align="left">
                                    <input type="hidden" name="fdAccount_Form[${vstatus.index}].fdId" value="${fdAccount_FormItem.fdId}" />
                                    <div id="_xform_fdAccount_Form[${vstatus.index}].fdAccountIds" _xform_type="address">
                                        <xform:address propertyId="fdAccount_Form[${vstatus.index}].fdAccountIds" propertyName="fdAccount_Form[${vstatus.index}].fdAccountNames" mulSelect="true" orgType="ORG_TYPE_ALLORG" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                                <td align="left">
                                    <div id="_xform_fdAccount_Form[${vstatus.index}].fdName" _xform_type="text">
                                        <xform:text property="fdAccount_Form[${vstatus.index}].fdName" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripConfig.docCreateTime')}
                </td>
                <td width="35%">
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripConfig.docAlteror')}
                </td>
                <td width="35%">
                    <div id="_xform_docAlterorId" _xform_type="address">
                        <ui:person personId="${thirdCtripConfigForm.docAlterorId}" personName="${thirdCtripConfigForm.docAlterorName}" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripConfig.docAlterTime')}
                </td>
                <td width="35%">
                    <div id="_xform_docAlterTime" _xform_type="datetime">
                        <xform:datetime property="docAlterTime" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripConfig.docCreator')}
                </td>
                <td width="35%">
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${thirdCtripConfigForm.docCreatorId}" personName="${thirdCtripConfigForm.docCreatorName}" />
                    </div>
                </td>
            </tr>
        </table>
    </div>
</center>
<script>
	function cleanTime(){
		var url = '<c:url value="/third/ctrip/third_ctrip_config/thirdCtripConfig.do?method=updateTime" />'+"&fdId=${thirdCtripConfigForm.fdId}";
		$.ajax({
		   type: "POST",
		   url: url,
		   async:false,
		   dataType: "json",
		   success: function(data){
				alert(data.errmsg);
	   		}
		});
	}
	
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
        basePath: '/third/ctrip/third_ctrip_config/thirdCtripConfig.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
</template:replace>
</template:include>