<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="muiOcrOptContent">
    <div class="muiOcrOptBlock">
        <div class="muiOcrOptBtn muiOcrViewBtn" data-dojo-type="mui/ocr/OcrButton" data-dojo-props="html:'${lfn:message("sys-mobile:mui.ocr.identify.button.viewImg")}',type:'opt_viewImg',fdKey:'{categroy.fdKey}'"></div>
        <div class="muiOcrOptBtn muiOcrReplaceBtn" data-dojo-type="mui/ocr/OcrButton" data-dojo-props="html:'${lfn:message("sys-mobile:mui.ocr.identify.button.replaceImg")}',type:'opt_replaceImg',fdKey:'{categroy.fdKey}'"></div>
        <div class="muiOcrOptBtn muiOcrIdentifyBtn" data-dojo-type="mui/ocr/OcrButton" data-dojo-props="html:'${lfn:message("sys-mobile:mui.ocr.identify.button.reUpload")}',type:'opt_reUpload',fdKey:'{categroy.fdKey}'"></div>
        <div class="muiOcrOptBtn muiOcrDelBtn" data-dojo-type="mui/ocr/OcrButton" data-dojo-props="html:'${lfn:message("sys-mobile:mui.ocr.identify.button.del")}',type:'opt_del',fdKey:'{categroy.fdKey}'"></div>
    </div>
    <div class="muiOcrOptBlock muiOcrOptDownBlock">
        <div class="muiOcrOptBtn muiOcrCancelBtn" data-dojo-type="mui/ocr/OcrButton" data-dojo-props="html:'${lfn:message("sys-mobile:mui.ocr.identify.button.cancel")}',type:'opt_cancel',fdKey:'{categroy.fdKey}'"></div>
    </div>
</div>