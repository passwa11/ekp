<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<div id="mainContent" class="lui_list_mainContent fullscreen" style="margin: 0;padding: 0;">

    <div class="lui_modeling">
    <div class="lui_modeling_main aside_main" >
        <iframe id="trigger_iframe" class="lui_modeling_iframe_body" width="100%"  frameborder="no" border="0" src="${LUI_ContextPath}/sys/modeling/base/formlog/index_body.jsp?fdModelId=${param.fdModelId}"></iframe>
    </div>
    </div>
</div>