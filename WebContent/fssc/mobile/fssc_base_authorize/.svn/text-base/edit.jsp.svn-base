<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ include file="/fssc/mobile/resource/jsp/mobile_include.jsp" %>
<%@ include file="/fssc/mobile/common/organization/organization_include.jsp" %>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <title></title>
    <link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/swiper.min.css?s_cache=${LUI_Cache }">
	<link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/layer.css?s_cache=${LUI_Cache }">
	<link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/newApplicationForm.css?s_cache=${LUI_Cache }">
	<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/city.css?s_cache=${LUI_Cache }" >
	<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/home.css?s_cache=${LUI_Cache }">
	<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/notSubmit.css?s_cache=${LUI_Cache }">

  <script>
    var formInitData = {
    		"LUI_ContextPath":"${LUI_ContextPath}" 
    };
   function submitForm(method){
    	var tips="${lfn:message('errors.required')}";
    	var fdDesc=$("input[name='fdDesc']").val();
    	if(!fdDesc){
    		jqtoast(tips.replace("{0}","${lfn:message('eop-basedata:eopBasedataAuthorize.fdDesc')}"));
			return false;
    	}
    	var fdToOrgNames=$("input[name='fdToOrgNames']").val();
    	if(!fdToOrgNames){
    		jqtoast(tips.replace("{0}","${lfn:message('eop-basedata:eopBasedataAuthorize.fdToOrg')}"));
			return false;
    	}
		Com_Submit(document.forms['eopBasedataAuthorizeForm'], method);
	}
    </script>
</head>
<body style="margin-top:-20px;">
<form action="${LUI_ContextPath }/eop/basedata/eop_basedata_authorize/eopMobileAuthorize.do"  name="eopBasedataAuthorizeForm" method="post">
 <div class="ld-newApplicationForm">
        <div class="ld-newApplicationForm-info">
            <div>
                <span>${lfn:message("eop-basedata:eopBasedataAuthorize.fdDesc")}</span>
                <div>
                     <input type="text"  name="fdDesc" value="${eopBasedataAuthorizeForm.fdDesc }" placeholder='${lfn:message("fssc-mobile:fssc.mobile.placeholder.input")}${lfn:message("eop-basedata:eopBasedataAuthorize.fdDesc")}'  />
                     <span style="margin-left:2px;color:#d02300;">*</span>
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
                     <input type="text" placeholder='${lfn:message("fssc-mobile:fssc.mobile.placeholder.select")}${lfn:message("eop-basedata:eopBasedataAuthorize.fdToOrg")}' name="fdToOrgNames"  onclick="selectOrgElement('fdToOrgIds','fdToOrgNames',null,'true','person');" value="${eopBasedataAuthorizeForm.fdToOrgNames }" readonly="readonly" >
                     <input name='fdToOrgIds' id="fdToOrgIds" value="${eopBasedataAuthorizeForm.fdToOrgIds }" hidden='true'/>
                     <span style="margin-left:2px;color:#d02300;">*</span>
                    <i></i>
                </div>
            </div>
   	</div>
   		<div class="ld-footer">
         <c:if test="${eopBasedataAuthorizeForm.method_GET=='add' }">
            <div class="ld-footer-blueBg" style="width:100%" onclick="submitForm('save');" >${ lfn:message('button.submit') }</div>
         </c:if>
         <c:if test="${eopBasedataAuthorizeForm.method_GET=='edit' }">
            <div class="ld-footer-blueBg" style="width:100%" onclick="submitForm('update');" >${ lfn:message('button.submit') }</div>
         </c:if>
       </div>
   </div>
</form>
</body>
<%@ include file="/resource/jsp/edit_down.jsp" %>
