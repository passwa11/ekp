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
    if("${eopBasedataMaterialForm.fdName}" != "") {
        window.document.title = "${eopBasedataMaterialForm.fdName} - ${ lfn:message('eop-basedata:table.eopBasedataMaterial') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

<div id="optBarDiv">

    <kmss:auth requestURL="/eop/basedata/eop_basedata_material/eopBasedataMaterial.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('eopBasedataMaterial.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/eop/basedata/eop_basedata_material/eopBasedataMaterial.do?method=delete&fdId=${param.fdId}">
       <%-- <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('eopBasedataMaterial.do?method=delete&fdId=${param.fdId}','_self');" />--%>
        <input type="button" value="${lfn:message('button.delete')}" onclick="deleteDoc('eopBasedataMaterial.do?method=delete&fdId=${param.fdId}&fdCode=${eopBasedataMaterialForm.fdCode}');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataMaterial') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMaterial.fdName')}
                </td>
                <td width="35%">
                    <%-- 名称--%>
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMaterial.fdCode')}
                </td>
                <td width="35%">
                    <%-- 物料编码--%>
                    <div id="_xform_fdCode" _xform_type="text">
                        <xform:text property="fdCode" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMaterial.fdSpecs')}
                </td>
                <td width="35%">
                    <%-- 规格型号--%>
                    <div id="_xform_fdSpecs" _xform_type="text">
                        <xform:text property="fdSpecs" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMaterial.fdType')}
                </td>
                <td width="35%">
                    <%-- 物料类别--%>
                    <div id="_xform_fdTypeId" _xform_type="dialog">
                        <xform:dialog propertyId="fdTypeId" propertyName="fdTypeName" showStatus="view" style="width:95%;">
                            dialogSelect(false,'eop_basedata_mate_cate_materialCategory','fdTypeId','fdTypeName');
                        </xform:dialog>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMaterial.fdUnit')}
                </td>
                <td width="35%">
                    <%-- 单位--%>
                    <div id="_xform_fdUnitId" _xform_type="select">
                        <xform:select property="fdUnitId" htmlElementProperties="id='fdUnitId'" showStatus="view">
                            <xform:beanDataSource serviceBean="eopBasedataMateUnitService" selectBlock="fdId,fdName" />
                        </xform:select>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMaterial.fdStatus')}
                </td>
                <td width="35%">
                    <%-- 状态--%>
                    <div id="_xform_fdStatus" _xform_type="radio">
                        <xform:radio property="fdStatus" htmlElementProperties="id='fdStatus'" showStatus="view">
                            <xform:enumsDataSource enumsType="eop_basedata_mate_status" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMaterial.fdPrice')}
                </td>
                <td width="35%">
                    <%-- 参考价--%>
                    <div id="_xform_fdPrice" _xform_type="text">
                        <xform:text property="fdPrice" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMaterial.fdErpCode')}
                </td>
                <td width="35%">
                    <%-- erp物料编码--%>
                    <div id="_xform_fdErpCode" _xform_type="text">
                        <xform:text property="fdErpCode" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMaterial.docCreator')}
                </td>
                <td width="35%">
                    <%-- 最近更新人--%>
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${eopBasedataMaterialForm.docCreatorId}" personName="${eopBasedataMaterialForm.docCreatorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMaterial.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 最近更新时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMaterial.fdRemarks')}
                </td>
                <td colspan="3" width="85%">
                    <%-- 物料描述--%>
                    <div id="_xform_fdRemarks" _xform_type="textarea" style="word-break:break-word;">
                        <xform:textarea property="fdRemarks" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataMaterial.attOther')}
                </td>
                <td colspan="3" width="85%">
                    <%-- 物料附件--%>
                    <c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
                        <c:param name="fdKey" value="attOther" />
                        <c:param name="formBeanName" value="eopBasedataMaterialForm" />
                        <c:param name="fdMulti" value="true" />
                    </c:import>
                </td>
            </tr>
        </table>
    </div>
</center>
<script>

    function deleteDoc(delUrl) {
        var strs = delUrl.split("&fdCode=");
        var fdCode = strs[1];
        var delUrlStr = strs[0];
        //根据物料编码查采购需求
        $.ajax({
            url: '${LUI_ContextPath}' + '/eps/require/eps_require_main/epsRequireMain.do' + '?method=isDelete',
            data: {fdCode:fdCode},
            dataType: 'json',
            type: 'POST',
            success: function(data) {
                if(!data.isAllowDelete){
                    seajs.use([ 'lui/dialog'], function(dialog) {
                        dialog.alert("${lfn:message('eop-basedata:related.material.can.not.delete')}");
                    })
                    return false;
                }else{
                    seajs.use(['lui/dialog'], function(dialog) {
                        dialog.confirm('${ lfn:message("page.comfirmDelete") }', function(isOk) {
                            if(isOk) {
                                Com_OpenWindow(delUrlStr, '_self');
                            }
                        });
                    });
                }
            }
        });
    }

    var formInitData = {

    };

    /*function confirmDelete(msg) {
        return confirm('${ lfn:message("page.comfirmDelete") }');
    }*/

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
        basePath: '/eop/basedata/eop_basedata_material/eopBasedataMaterial.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
