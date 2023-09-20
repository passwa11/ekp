<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<!DOCTYPE HTML>
<html lang="en">
<!--PDF阅读-->
<head>
    <meta charset="UTF-8">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <title>${fileName}</title>
    <link rel="stylesheet" href="${ LUI_ContextPath}/sys/attachment/sys_att_main/pdf/pdfh5/css/style.css" />
    <link rel="stylesheet" href="${ LUI_ContextPath}/sys/attachment/sys_att_main/pdf/pdfh5/css/pdfh5.css" />
</head>

<body>
<div id="context"></div>
<script>Com_IncludeFile("jquery.js");</script>
<script src="${ LUI_ContextPath}/sys/attachment/sys_att_main/pdf/pdfh5/js/pdf.js"></script>
<script src="${ LUI_ContextPath}/sys/attachment/sys_att_main/pdf/pdfh5/js/pdf.worker.js"></script>
<script src="${ LUI_ContextPath}/sys/attachment/sys_att_main/pdf/pdfh5/js/pdfh5.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    $.ajax({
        url: "${downUrl}", //假设这是pdf文件流的请求接口
        type: "post",
        mimeType: 'text/plain; charset=x-user-defined',//jq ajax请求文件流的方式
        success: function (data) {
            new Pdfh5('#context', {
                data: data
            });
        }
    });
</script>
</body>

</html>