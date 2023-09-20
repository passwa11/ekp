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

    <%-- <kmss:auth requestURL="/third/ding/third_ding_oms_error/thirdDingOmsError.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdDingOmsError.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/third/ding/third_ding_oms_error/thirdDingOmsError.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdDingOmsError.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth> --%>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-ding:table.thirdDingOmsError') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOmsError.fdOms')}
                </td>
                <td width="35%">
                    <%-- 同步方--%>
                    <div id="_xform_fdOms" _xform_type="radio">
                        <xform:radio property="fdOms" htmlElementProperties="id='fdOms'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_ding_oms" />
                        </xform:radio>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOmsError.fdOper')}
                </td>
                <td width="35%">
                    <%-- 操作标志--%>
                    <div id="_xform_fdOper" _xform_type="radio">
                        <xform:radio property="fdOper" htmlElementProperties="id='fdOper'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_ding_opertype" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOmsError.fdEkpId')}
                </td>
                <td width="35%">
                    <%-- EKP组织--%>
                    <div id="_xform_fdEkpId" _xform_type="text">
                        <xform:text property="fdEkpId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOmsError.fdDingId')}
                </td>
                <td width="35%">
                    <%-- 钉钉组织--%>
                    <div id="_xform_fdDingId" _xform_type="text">
                        <xform:text property="fdDingId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOmsError.fdEkpType')}
                </td>
                <td width="35%">
                    <%-- EKP组织类型--%>
                    <div id="_xform_fdEkpType" _xform_type="radio">
                        <xform:radio property="fdEkpType" htmlElementProperties="id='fdEkpType'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_ding_ekptype" />
                        </xform:radio>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOmsError.fdDingType')}
                </td>
                <td width="35%">
                    <%-- 钉钉组织类型--%>
                    <div id="_xform_fdDingType" _xform_type="radio">
                        <xform:radio property="fdDingType" htmlElementProperties="id='fdDingType'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_ding_dingtype" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOmsError.fdEkpName')}
                </td>
                <td width="35%">
                    <%-- EKP组织名称--%>
                    <div id="_xform_fdEkpName" _xform_type="text">
                        <xform:text property="fdEkpName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOmsError.fdDingName')}
                </td>
                <td width="35%">
                    <%-- 钉钉组织名称--%>
                    <div id="_xform_fdDingName" _xform_type="text">
                        <xform:text property="fdDingName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingOmsError.fdDesc')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 异常描述--%>
                    <div id="_xform_fdDesc" _xform_type="textarea">
                        <xform:textarea property="fdDesc" showStatus="view" style="width:95%;" />
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
        basePath: '/third/ding/third_ding_oms_error/thirdDingOmsError.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>