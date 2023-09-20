define([
    "dojo/_base/declare"
],function(declare){
    return declare("mui.ocr.OcrConstants",null,{
        OCR_EVENT_IDENTIFY_SUCCESS:"ocr_identify_success",

        OCR_EVENT_IDENTIFY_FAIL:"ocr_identify_fail",

        OCR_EVENT_REUPLOAD:"ocr_reUpload",

        OCR_EVENT_CANCEL:"ocr_cancel",

        OCR_EVENT_ONLY_UPLOAD:"ocr_only_upload",

        OCR_EVENT_OPT_VIEWIMG:"ocr_opt_viewImg",

        OCR_EVENT_OPT_REPLACEIMG:"ocr_opt_replaceImg",

        OCR_EVENT_OPT_CANCEL:"ocr_opt_cancel",

        OCR_EVENT_FAIL_CANCEL:"ocr_fail_cancel"

    });
})