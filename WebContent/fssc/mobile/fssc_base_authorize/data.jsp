<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${lfn:message('fssc-fee:module.fssc.fee')}</title>
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/reset.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/common.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/applicationFormList.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/search.css">
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/jquery.min.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/rem.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/dyselect.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/picker.min.js"></script>
    <script>
    function backToHome(){
    		window.location.href='${LUI_ContextPath}/fssc/mobile/index.jsp'
    }
    $(document).ready(function(){
    	
	});
    </script>
</head>
<body>
    <div class="ld-application-form-list">
    	<c:if test="${authorizeList!= null and fn:length(authorizeList) > 0}">
        <ul id="list_data">
        	<c:forEach var="authorize" items="${authorizeList}" varStatus="status">
	        	<li onclick="viewFee('${authorize.fdId}');">
                <div class="ld-application-form-list-item-top">
                		<div style="width:60%;word-break: break-all;">
                    <c:if test="${fn:length(authorize.fdDesc)>40 }">${fn:substring(authorize.fdDesc,0,37)}...</c:if>
                    <c:if test="${fn:length(authorize.fdDesc)<=40 }">${authorize.fdDesc}</c:if>
                    </div>
                    <div style="width:40%;text-align:right;">
                    	${authorize.fdAuthorizedBy.fdName}
                   </div>
                </div>
                <div class="ld-application-form-list-item-time">
               		<div style="width:40%;float:left;">
               			<kmss:showDate value="${authorize.docCreateTime}" type="date"></kmss:showDate>
               		</div>
                </div>
            </li>
        	</c:forEach>
        </ul>
        </c:if>
        <c:if test="${authorizeList== null or fn:length(authorizeList) == 0}">
				<div style="text-align:center; line-height:50;">
					暂无内容
				</div>
        </c:if>
    </div>
    <div class="create—expense-btn" id="expenseBtn" onclick="addAuthorize();"></div>
    <div class="backHome" id="createExpense" onclick="backToHome()"></div>
    <script>
		function viewFee(id){
			window.open("${LUI_ContextPath}/eop/basedata/eop_basedata_authorize/eopMobileAuthorize.do?method=view&fdId="+id,'_self');
		}
		function addAuthorize(){
			window.open("${LUI_ContextPath}/eop/basedata/eop_basedata_authorize/eopMobileAuthorize.do?method=add",'_self');
		}
    </script>
</body>
</html>
