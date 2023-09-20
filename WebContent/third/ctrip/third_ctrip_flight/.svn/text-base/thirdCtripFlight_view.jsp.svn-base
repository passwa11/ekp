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

    <%-- <kmss:auth requestURL="/third/ctrip/third_ctrip_flight/thirdCtripFlight.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdCtripFlight.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/third/ctrip/third_ctrip_flight/thirdCtripFlight.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdCtripFlight.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth> --%>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-ctrip:table.thirdCtripFlight') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripFlight.fdCityId')}
                </td>
                <td width="35%">
                    <div id="_xform_fdCityId" _xform_type="text">
                        <xform:text property="fdCityId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripFlight.fdCityCode')}
                </td>
                <td width="35%">
                    <div id="_xform_fdCityCode" _xform_type="text">
                        <xform:text property="fdCityCode" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripFlight.fdCityCname')}
                </td>
                <td width="35%">
                    <div id="_xform_fdCityCname" _xform_type="text">
                        <xform:text property="fdCityCname" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripFlight.fdCityEname')}
                </td>
                <td width="35%">
                    <div id="_xform_fdCityEname" _xform_type="text">
                        <xform:text property="fdCityEname" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripFlight.fdCityPinyin')}
                </td>
                <td width="35%">
                    <div id="_xform_fdCityPinyin" _xform_type="text">
                        <xform:text property="fdCityPinyin" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripFlight.fdCitySname')}
                </td>
                <td width="35%">
                    <div id="_xform_fdCitySname" _xform_type="text">
                        <xform:text property="fdCitySname" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripFlight.fdProvinceId')}
                </td>
                <td width="35%">
                    <div id="_xform_fdProvinceId" _xform_type="text">
                        <xform:text property="fdProvinceId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripFlight.fdCountryId')}
                </td>
                <td width="35%">
                    <div id="_xform_fdCountryId" _xform_type="text">
                        <xform:text property="fdCountryId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripFlight.fdContryCode')}
                </td>
                <td width="35%">
                    <div id="_xform_fdContryCode" _xform_type="text">
                        <xform:text property="fdContryCode" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripFlight.fdContryCname')}
                </td>
                <td width="35%">
                    <div id="_xform_fdContryCname" _xform_type="text">
                        <xform:text property="fdContryCname" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripFlight.fdCountryEname')}
                </td>
                <td width="35%">
                    <div id="_xform_fdCountryEname" _xform_type="text">
                        <xform:text property="fdCountryEname" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripFlight.fdDuplicateCityNameFlag')}
                </td>
                <td width="35%">
                    <div id="_xform_fdDuplicateCityNameFlag" _xform_type="radio">
                        <xform:radio property="fdDuplicateCityNameFlag" htmlElementProperties="id='fdDuplicateCityNameFlag'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_ctrip_city_flag" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ctrip:thirdCtripFlight.fdPoiType')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdPoiType" _xform_type="radio">
                        <xform:radio property="fdPoiType" htmlElementProperties="id='fdPoiType'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_ctrip_poi" />
                        </xform:radio>
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
        basePath: '/third/ctrip/third_ctrip_flight/thirdCtripFlight.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>