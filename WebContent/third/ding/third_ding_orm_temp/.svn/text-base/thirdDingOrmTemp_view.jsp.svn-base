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

    <kmss:auth requestURL="/third/ding/third_ding_orm_temp/thirdDingOrmTemp.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdDingOrmTemp.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/third/ding/third_ding_orm_temp/thirdDingOrmTemp.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdDingOrmTemp.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-ding:table.thirdDingOrmTemp') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOrmTemp.fdName')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOrmTemp.fdTemplateId')}
                </td>
                <td width="35%">
                    <div id="_xform_fdTemplateId" _xform_type="text">
                        <xform:text property="fdTemplateId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOrmTemp.fdProcessCode')}
                </td>
                <td width="35%">
                    <div id="_xform_fdProcessCode" _xform_type="text">
                        <xform:text property="fdProcessName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <%-- <tr>
            	<td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOrmTemp.fdOrder')}
                </td>
                <td width="35%">
                    <div id="_xform_fdOrder" _xform_type="text">
                        <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOrmTemp.fdStartFlow')}
                </td>
                <td width="35%">
                    <div id="_xform_fdStartFlow" _xform_type="radio">
                        <xform:radio property="fdStartFlow" htmlElementProperties="id='fdStartFlow'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_ding_start_flow" />
                        </xform:radio>
                    </div>
                </td>
            </tr> --%>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOrmTemp.fdIsAvailable')}
                </td>
                <td width="35%">
                    <div id="_xform_fdIsAvailable" _xform_type="radio">
                        <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOrmTemp.fdDingTemplateType')}
                </td>
                <td width="35%">
                    <div id="_xform_fdDingTemplateType" _xform_type="radio">
                        <xform:radio property="fdDingTemplateType" htmlElementProperties="id='fdDingTemplateType'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_ding_dt_type" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="4" width="100%">
                    <table class="tb_normal" width="100%" id="TABLE_DocList_fdDetail_Form" align="center" tbdraggable="true">
                        <tr align="center" class="tr_normal_title">
                            <td style="width:40px;">
                                ${lfn:message('page.serial')}
                            </td>
                            <td>
                                ${lfn:message('third-ding:thirdDingOrmDe.fdEkpField')}
                            </td>
                            <td>
                                ${lfn:message('third-ding:thirdDingOrmDe.fdDingField')}
                            </td>
                        </tr>
                        <c:forEach items="${thirdDingOrmTempForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
                            <tr KMSS_IsContentRow="1">
                                <td align="center">
                                    ${vstatus.index+1}
                                </td>
                                <td align="center">
                                    <input type="hidden" name="fdDetail_Form[${vstatus.index}].fdId" value="${fdDetail_FormItem.fdId}" />
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdEkpField" _xform_type="text">
                                        <xform:text property="fdDetail_Form[${vstatus.index}].fdEkpFieldName" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                                <td align="center">
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdDingField" _xform_type="text">
                                        <xform:text property="fdDetail_Form[${vstatus.index}].fdDingField" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOrmTemp.docCreator')}
                </td>
                <td width="35%">
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${thirdDingOrmTempForm.docCreatorId}" personName="${thirdDingOrmTempForm.docCreatorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOrmTemp.docCreateTime')}
                </td>
                <td width="35%">
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOrmTemp.docAlteror')}
                </td>
                <td width="35%">
                    <div id="_xform_docAlterorId" _xform_type="address">
                        <ui:person personId="${thirdDingOrmTempForm.docAlterorId}" personName="${thirdDingOrmTempForm.docAlterorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOrmTemp.docAlterTime')}
                </td>
                <td width="35%">
                    <div id="_xform_docAlterTime" _xform_type="datetime">
                        <xform:datetime property="docAlterTime" showStatus="view" style="width:95%;" />
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
        basePath: '/third/ding/third_ding_orm_temp/thirdDingOrmTemp.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>