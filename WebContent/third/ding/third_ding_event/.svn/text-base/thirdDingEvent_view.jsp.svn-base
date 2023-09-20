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
    	.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
      		border: 0px;
      		color: #868686
    	}
    
</style>
<script type="text/javascript">
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <%-- <kmss:auth requestURL="/third/ding/third_ding_event/thirdDingEvent.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdDingEvent.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth> --%>
   <%--  <kmss:auth requestURL="/third/ding/third_ding_event/thirdDingEvent.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdDingEvent.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth> --%>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-ding:table.thirdDingEvent') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingEvent.fdName')}
                </td>
                <td width="35%"  colspan="3">
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <%-- <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingEvent.fdOrder')}
                </td>
                <td width="35%">
                    <div id="_xform_fdOrder" _xform_type="text">
                        <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
                    </div>
                </td> --%>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingEvent.fdTag')}
                </td>
                <td width="35.0%">
                    <div id="_xform_fdTag" _xform_type="text">
                        <xform:text property="fdTag" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                 <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingEvent.fdIsStatus')}
                </td>
                <td width="35.0%">
                    <div id="_xform_fdTag" _xform_type="text">
                        <xform:radio property="fdIsStatus" htmlElementProperties="id='fdIsStatus'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_ding_event_start" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingEvent.fdCallbackUrl')}
                </td>
                <td width="35%"   colspan="3">
                    <div id="_xform_fdCallbackUrl" _xform_type="text">
                        <xform:text property="fdCallbackUrl" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <%-- <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingEvent.fdIsAvailable')}
                </td>
                <td width="35%">
                    <div id="_xform_fdIsAvailable" _xform_type="radio">
                        <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio>
                    </div>
                </td> --%>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingEvent.docCreator')}
                </td>
                <td width="35%">
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${thirdDingEventForm.docCreatorId}" personName="${thirdDingEventForm.docCreatorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingEvent.docCreateTime')}
                </td>
                <td width="35%">
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <c:if test="${not thirdDingEventForm.docAlterorId}">
	            <tr>
	                <td class="td_normal_title" width="15%">
	                    ${lfn:message('third-ding:thirdDingEvent.docAlteror')}
	                </td>
	                <td width="35%">
	                    <div id="_xform_docAlterorId" _xform_type="address">
	                        <ui:person personId="${thirdDingEventForm.docAlterorId}" personName="${thirdDingEventForm.docAlterorName}" />
	                    </div>
	                </td>
	                <td class="td_normal_title" width="15%">
	                    ${lfn:message('third-ding:thirdDingEvent.docAlterTime')}
	                </td>
	                <td width="35%">
	                    <div id="_xform_docAlterTime" _xform_type="datetime">
	                        <xform:datetime property="docAlterTime" showStatus="view" style="width:95%;" />
	                    </div>
	                </td>
	            </tr>
            </c:if>
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
        basePath: '/third/ding/third_ding_event/thirdDingEvent.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>