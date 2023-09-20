<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<div class="lui_modeling">
    <div id="modelingAsideOld" style="display: none">
        <div class="lui_modeling_aside" id="modelingAside">
            <div data-lui-type="lui/base!DataView" style="display:none;">
                <ui:source type="Static">
                    [
                        {
                        "text" : "${lfn:message('sys-modeling-base:listview.PC.mobile') }",
                        "iframeId":"trigger_iframe",
                        "selected":"true",
                        "src" :  "sys/modeling/base/pcAndMobile/view/index_body.jsp?fdModelId=${param.fdModelId}&method=${param.method}"
                        },
                        {
                        "text" : "${lfn:message('sys-modeling-base:sysform.PC') }",
                        "iframeId":"trigger_iframe",
                        "src" :  "sys/modeling/base/view/config/index_body.jsp?fdModelId=${param.fdModelId}&fdMobile=false"
                        },{
                        "text" : "${lfn:message('sys-modeling-base:sysform.mobile') }",
                        "iframeId":"trigger_iframe",
                        "src" :  "sys/modeling/base/view/config/index_body.jsp?fdModelId=${param.fdModelId}&fdMobile=true"
                    }]
                </ui:source>
                <ui:render type="Javascript">
                    <c:import url="/sys/modeling/base/resources/js/menu_side.js" charEncoding="UTF-8"></c:import>
                </ui:render>
            </div>
        </div>
    </div>

    <div class="lui_modeling_main aside_main" >
        <iframe id="trigger_iframe" class="lui_modeling_iframe_body"  frameborder="no" border="0" src="${LUI_ContextPath}/sys/modeling/base/view/config/new/index_body.jsp?fdModelId=${param.fdModelId}&method=${param.method}"></iframe>
    </div>

</div>
<script>
	$(".lui_modeling").css({"background-color":"#E8ECEF","padding":"10px"});
</script>