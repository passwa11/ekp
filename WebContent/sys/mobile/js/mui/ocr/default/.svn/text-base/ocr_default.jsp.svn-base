<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:choose>
    <c:when test="${param.showStatus == 'view'}">
        <c:if test="${param.isUpload == 'false'}">
            <div class="muiOcrDefaultNoRemainView muiOcrDefaultNoRemain {categroy.certificateType}">
                <div class="muiOcrDefaultBlock">
                    <div class="muiOcrDefaultSelect" clickEvent="selectImg"></div>
                </div>
            </div>
        </c:if>
        <c:if test="${param.isUpload == 'true'}">
            <div class="muiOcrDefaultContentView muiOcrDefaultContent {categroy.certificateType}">
                <div class="muiOcrDefaultSelect muiOcrDefaultJust <c:if test="${param.front == '0'}">muiOcrDefaultBack</c:if>" clickEvent="selectImg"></div>
            </div>
        </c:if>
    </c:when>
    <c:otherwise>
        <c:if test="${param.isUpload == 'false'}">
            <div class="muiOcrDefaultNoRemainEdit muiOcrDefaultNoRemain {categroy.certificateType}" clickEvent="selectImg">
                <div class="muiOcrDefaultBlock">
                    <div class="muiOcrDefaultDesc">${lfn:message(JsParam.ocrDesc)}</div>
                    <div class="muiOcrDefaultSelect"></div>
                </div>
                <div style="display: none" class="muiOcrDefaultFail">${lfn:message(JsParam.ocrTip)}</div>
            </div>
        </c:if>
        <c:if test="${param.isUpload == 'true'}">
            <div class="muiOcrDefaultContentEdit muiOcrDefaultContent {categroy.certificateType}">
                <div class="muiOcrDefaultSelect muiOcrDefaultJust <c:if test="${param.front == '0'}">muiOcrDefaultBack</c:if>" clickEvent="selectImg"></div>
                <div class="muiOcrDefaultDesc">${lfn:message(JsParam.ocrDesc)}</div>
                <div style="display: none" class="muiOcrDefaultFail">${lfn:message(JsParam.ocrTip)}</div>
            </div>
        </c:if>
    </c:otherwise>
</c:choose>
<c:if test="${showStatus == 'edit'}">

</c:if>
