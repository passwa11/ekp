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
    if("${eopBasedataMateCateForm.fdName}" != "") {
        window.document.title = "${eopBasedataMateCateForm.fdName} - ${ lfn:message('eop-basedata:table.eopBasedataMateCate') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/eop/basedata/eop_basedata_mate_cate/eopBasedataMateCate.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('eopBasedataMateCate.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/eop/basedata/eop_basedata_mate_cate/eopBasedataMateCate.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('eopBasedataMateCate.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataMateCate') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMateCate.fdName')}
                </td>
                <td width="35%">
                    <%-- 类别名称--%>
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMateCate.fdCode')}
                </td>
                <td width="35%">
                        <%-- 类别编码--%>
                    <div id="_xform_fdCode" _xform_type="text">
                        <xform:text property="fdCode" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMateCate.fdParent')}
                </td>
                <td width="35%">
                    <%-- 父类别名称--%>
                    <div id="_xform_fdParentId" _xform_type="dialog">
                        <xform:dialog propertyId="fdParentId" propertyName="fdParentName" showStatus="view" style="width:95%;">
                            dialogSelect(false,'eop_basedata_mate_cate_materialCategory','fdParentId','fdParentName');
                        </xform:dialog>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMateCate.fdOrder')}
                </td>
                <td width="35%">
                    <%-- 排序号--%>
                    <div id="_xform_fdOrder" _xform_type="text">
                        <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMateCate.fdStatus')}
                </td>
                <td width="35%">
                    <%-- 状态--%>
                    <div id="_xform_fdStatus" _xform_type="radio">
                        <xform:radio property="fdStatus" htmlElementProperties="id='fdStatus'" showStatus="view">
                            <xform:enumsDataSource enumsType="eop_basedata_mate_status" />
                        </xform:radio>
                    </div>
                </td>
                <td colspan="2" width="50.0%">
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMateCate.fdDesc')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 类别描述--%>
                    <div id="_xform_fdDesc" _xform_type="textarea">
                        <xform:textarea property="fdDesc" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMateCate.docCreator')}
                </td>
                <td width="35%">
                    <%-- 最近更新人--%>
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${eopBasedataMateCateForm.docCreatorId}" personName="${eopBasedataMateCateForm.docCreatorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMateCate.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 最近更新时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
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
        basePath: '/eop/basedata/eop_basedata_mate_cate/eopBasedataMateCate.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
