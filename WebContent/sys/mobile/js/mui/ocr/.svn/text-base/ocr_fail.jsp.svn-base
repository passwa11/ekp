<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="muiOcrFailContent">
    <div class="muiOcrFailDesc">
        <p>${lfn:message("sys-mobile:mui.ocr.identify.fail.dialog.desc1")}</p>
        <p>${lfn:message("sys-mobile:mui.ocr.identify.fail.dialog.desc2")}</p>
        <p>${lfn:message("sys-mobile:mui.ocr.identify.fail.dialog.desc3")}</p>
    </div>
    <div class="muiOcrFailBtns">
        <c:if test="${param.isUpload == 'true'}">
            <div class="muiOcrFailBtn muiOcrRedoBtn" data-dojo-type="mui/ocr/OcrButton" data-dojo-props="html:'${lfn:message("sys-mobile:mui.ocr.identify.button.reUpload")}',type:'reUpload',fdKey:'{categroy.fdKey}'"></div>
            <div class="muiOcrFailBtn muiOcrUploadBtn" data-dojo-type="mui/ocr/OcrButton" data-dojo-props="html:'${lfn:message("sys-mobile:mui.ocr.identify.button.onlyUpload")}',type:'onlyUpload',fdKey:'{categroy.fdKey}'"></div>
            <div class="muiOcrFailBtn muiOcrCancelBtn" data-dojo-type="mui/ocr/OcrButton" data-dojo-props="html:'${lfn:message("sys-mobile:mui.ocr.identify.button.cancel")}',type:'cancel',fdKey:'{categroy.fdKey}'"></div>
        </c:if>
        <c:if test="${param.isUpload != 'true'}">
            <div class="muiOcrFailBtn muiOcrCancelBtn muiOcrHalfBtn" data-dojo-type="mui/ocr/OcrButton" data-dojo-props="html:'${lfn:message("sys-mobile:mui.ocr.identify.button.cancel")}',type:'fail_cancel',fdKey:'{categroy.fdKey}'"></div>
            <div class="muiOcrFailBtn muiOcrRedoBtn muiOcrHalfBtn" data-dojo-type="mui/ocr/OcrButton" data-dojo-props="html:'${lfn:message("sys-mobile:mui.ocr.identify.button.reUpload")}',type:'reUpload',fdKey:'{categroy.fdKey}'"></div>
        </c:if>
    </div>
</div>