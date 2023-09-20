<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%--<%@ include file="/sys/ui/jsp/jshead.jsp" %>--%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/collection.css?s_cache=${LUI_Cache}"/>
<div class="modeling-pam-top">
    <div calss="modeling-pam-top-left">
        <div class="modeling-pam-back">
            <div onclick="returnListPagePam()">
                <i></i>
                <p>${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.back')}</p>
            </div>
        </div>
        <div class="listviewName">
        </div>
    </div>
    <div class="modeling-pam-top-center">
        <ul>
            <li value="pc" class="active" onclick="changeContentView('pc')">${lfn:message('sys-modeling-base:sysform.PC')}</li>
            <li value="mobile" onclick="changeContentView('mobile')">${lfn:message('sys-modeling-base:sysform.mobile')}</li>
        </ul>
    </div>
    <div class="modeling-pam-top-right">
        <ul>
            <li onclick="collectionDoSubmit('update')" class="active">${lfn:message('sys-modeling-base:modeling.save')}</li>
        </ul>
    </div>
</div>
<script>
    function returnListPagePam() {
        var returnUrl = '${LUI_ContextPath}/sys/modeling/base/views/collection/index_body.jsp?fdModelId=${param.fdModelId}&method=${param.method}&fdMobile=false';
        var url = returnUrl.replace(/\s+/g, "");
        var iframe = window.parent.document.getElementById("trigger_iframe");
        $(iframe).attr("src", url);
    }
</script>
