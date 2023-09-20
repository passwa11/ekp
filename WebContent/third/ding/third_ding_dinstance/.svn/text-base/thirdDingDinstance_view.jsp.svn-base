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
    if("${thirdDingDinstanceForm.fdName}" != "") {
        window.document.title = "${thirdDingDinstanceForm.fdName} - ${ lfn:message('third-ding:table.thirdDingDinstance') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <%-- <kmss:auth requestURL="/third/ding/third_ding_dinstance/thirdDingDinstance.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdDingDinstance.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/third/ding/third_ding_dinstance/thirdDingDinstance.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdDingDinstance.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth> --%>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-ding:table.thirdDingDinstance') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDinstance.fdName')}
                </td>
                <td width="35%">
                    <%-- 名称--%>
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDinstance.fdTemplate')}
                </td>
                <td width="35%">
                    <%-- 所属模板--%>
                    <div id="_xform_fdTemplateId" _xform_type="select">
                        <xform:select property="fdTemplateId" htmlElementProperties="id='fdTemplateId'" showStatus="view">
                            <xform:beanDataSource serviceBean="thirdDingDtemplateService" selectBlock="fdId,fdName" />
                        </xform:select>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDinstance.fdUrl')}
                </td>
                <td width="35%">
                    <%-- 文档地址--%>
                    <div id="_xform_fdUrl" _xform_type="text">
                        <xform:text property="fdUrl" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDinstance.fdInstanceId')}
                </td>
                <td width="35%">
                    <%-- 实例Id--%>
                    <div id="_xform_fdInstanceId" _xform_type="text">
                        <xform:text property="fdInstanceId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDinstance.fdEkpInstanceId')}
                </td>
                <td width="35%">
                    <%-- 文档Id--%>
                    <div id="_xform_fdEkpInstanceId" _xform_type="text">
                        <xform:text property="fdEkpInstanceId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDinstance.fdCreator')}
                </td>
                <td width="35%">
                    <%-- 发起人--%>
                    <div id="_xform_fdCreatorId" _xform_type="address">
                        <xform:address propertyId="fdCreatorId" propertyName="fdCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDinstance.fdDingUserId')}
                </td>
                <td width="35%">
                    <%-- 钉钉接收人--%>
                    <div id="_xform_fdDingUserId" _xform_type="text">
                        <xform:text property="fdDingUserId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDinstance.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
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
                                ${lfn:message('third-ding:thirdDingInstanceDetail.fdName')}
                            </td>
                            <td>
                                ${lfn:message('third-ding:thirdDingInstanceDetail.fdType')}
                            </td>
                            <td>
                                ${lfn:message('third-ding:thirdDingInstanceDetail.fdValue')}
                            </td>
                        </tr>
                        <c:forEach items="${thirdDingDinstanceForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
                            <tr KMSS_IsContentRow="1" class="docListTr">
                                <td class="docList" align="center">
                                    ${vstatus.index+1}
                                </td>
                                <td class="docList" align="center">
                                    <%-- 控件名称--%>
                                    <input type="hidden" name="fdDetail_Form[${vstatus.index}].fdId" value="${fdDetail_FormItem.fdId}" />
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdName" _xform_type="text">
                                        <xform:text property="fdDetail_Form[${vstatus.index}].fdName" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="docList" align="center">
                                    <%-- 类型--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdType" _xform_type="radio">
                                        <xform:radio property="fdDetail_Form[${vstatus.index}].fdType" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdType'" showStatus="view">
                                            <xform:enumsDataSource enumsType="third_ding_field_type" />
                                        </xform:radio>
                                    </div>
                                </td>
                                <td class="docList" align="center">
                                    <%-- 控件值--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdValue" _xform_type="text">
                                        <xform:text property="fdDetail_Form[${vstatus.index}].fdValue" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
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
        basePath: '/third/ding/third_ding_dinstance/thirdDingDinstance.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>