<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>

<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <title></title>
</head>
<link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/reset.css?s_cache=${LUI_Cache }">
<link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/common.css?s_cache=${LUI_Cache }">
<link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/swiper.min.css?s_cache=${LUI_Cache }">
<link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/newApplicationForm.css?s_cache=${LUI_Cache }">
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/city.css?s_cache=${LUI_Cache }" >
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/home.css?s_cache=${LUI_Cache }">
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/Mdate.css?s_cache=${LUI_Cache }">
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/iScroll.js?s_cache=${LUI_Cache }"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/rem.js?s_cache=${LUI_Cache }"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/common.js?s_cache=${LUI_Cache }"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/Mdate.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/common/attachement/attachment.js"></script>
<body style="margin-top:-20px;">
 <div class="ld-newApplicationForm">
        <div class="ld-newApplicationForm-info">
            <div>
                <span>${lfn:message("eop-basedata:eopBasedataAuthorize.fdDesc")}</span>
                <div>
                     <input type="text"  name="fdDesc" value="${eopBasedataAuthorizeForm.fdDesc }" readonly="readonly"  />
                </div>
            </div>
            <div>
                <span>${lfn:message("eop-basedata:eopBasedataAuthorize.fdAuthorizedBy")}</span>
                <div>
                     <input type="text" id="fdAuthorizedByName" name="fdAuthorizedByName" value="${eopBasedataAuthorizeForm.fdAuthorizedByName }" readonly="readonly" />
                     <input type="hidden" id="fdAuthorizedById" name="fdAuthorizedById"  value="${eopBasedataAuthorizeForm.fdAuthorizedById }" />
                </div>
            </div>
             <div>
                <span>${lfn:message("eop-basedata:eopBasedataAuthorize.fdToOrg")}</span>
                <div class="ld-selectPersion">
                     <input type="text" name="fdToOrgNames" value="${eopBasedataAuthorizeForm.fdToOrgNames }" readonly="readonly" >
                     <input name='fdToOrgIds' id="fdToOrgIds" value="${eopBasedataAuthorizeForm.fdToOrgIds }" hidden='true'/>
                </div>
            </div>
   	</div>
   		<div class="ld-footer">
            <div class="ld-footer-blueBg" style="width:100%" onclick="window.open('${LUI_ContextPath}/eop/basedata/eop_basedata_authorize/eopMobileAuthorize.do?method=edit&fdId=${param.fdId}','_self');" >${ lfn:message('button.edit') }</div>
       </div>
   </div>
</body>
<%@ include file="/resource/jsp/edit_down.jsp" %>
