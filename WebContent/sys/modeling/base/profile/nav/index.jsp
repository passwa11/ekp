<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<style>
	body{
		margin:0;
	}
</style>
<div class="lui_modeling">
    <div class="lui_modeling_main aside_main" >
        <iframe id="trigger_iframe" style="width:100%;height:100%;" class="lui_modeling_iframe_body"  frameborder="no" border="0" src="${LUI_ContextPath}/sys/modeling/base/profile/nav/index_body.jsp?fdAppId=${param.fdAppId}"></iframe>
    </div>
</div>
