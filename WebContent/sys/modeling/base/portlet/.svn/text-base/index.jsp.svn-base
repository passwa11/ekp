<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<div class="lui_modeling">
    <div class="lui_modeling_aside" >
        <div data-lui-type="lui/base!DataView" style="display:none;">
            <ui:source type="Static">
                [{
                    "text" : "${lfn:message('sys-modeling-base:sysform.PC')}",
                    "iframeId":"trigger_iframe",
                    "selected":"true",
                    "src" :  "sys/modeling/base/portlet/index_body.jsp?fdModelId=${param.fdModelId}&fdDevice=pc"
                    },{
                    "text" : "${lfn:message('sys-modeling-base:sysform.mobile')}",
                    "iframeId":"trigger_iframe",
                    "src" :  "sys/modeling/base/portlet/index_body.jsp?fdModelId=${param.fdModelId}&fdDevice=mobile"
                }]
            </ui:source>
            <ui:render type="Javascript">
                <c:import url="/sys/modeling/base/resources/js/menu_side.js" charEncoding="UTF-8"></c:import>
            </ui:render>
        </div>
    </div>

    <div class="lui_modeling_main aside_main" >
        <iframe id="trigger_iframe" class="lui_modeling_iframe_body"  frameborder="no" border="0" src="${LUI_ContextPath}/sys/modeling/base/portlet/index_body.jsp?fdModelId=${param.fdModelId}&fdDevice=pc"></iframe>
    </div>

</div>