<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="${ LUI_ContextPath}/sys/attachment/sys_att_main/dianju/format/css/style.css?s_cache=${ LUI_Cache }" rel="stylesheet" type="text/css">
    <script charset="UTF-8" type="text/javascript" src="${ LUI_ContextPath}/sys/attachment/sys_att_main/dianju/format/js/AIP_MAIN_aip.js?s_cache=${ LUI_Cache }"></script>
    <!--该事件在AIP引擎初始化完毕之后触发-->
    <SCRIPT LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyCtrlReady>
        initAipProperties();
        openIncludeFile("${dianJuDownloadUrl}");
    </SCRIPT>
</head>

<body>
<input type="hidden" name="downloadUrl" value="${dianJuDownloadUrl}">
<input type="hidden" name="openType" value="include">
<div class="dianjuReaderTop">
    <div class="dianjuTopRight">
            <ul>
                <li style="list-style-type:none;">
                    <div >

                        <a href="javascript:addseal(1);" class="tipFont">
                            <img src="${KMSS_Parameter_ContextPath}sys/attachment/view/img/esignature-min.png" style="vertical-align:text-top;">${lfn:message('sys-attachment:attachment.signature.e')}</a>
                       &nbsp;&nbsp;
                        <a href="javascript:saveFileServer('${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/dianju/format/dianju_format_save.jsp?attMainId=${attMainId}&type=others&fileName=${fileName}&modelId=${modelId}&modelName=${modelName}','${fileName}');" class="tipFont">
                            <img src="${KMSS_Parameter_ContextPath}sys/attachment/view/img/savefile-min.png" style="vertical-align:text-top;">${lfn:message('sys-attachment:mui.sysAttMain.savefile')}</a>
                    </div>
                </li>
            </ul>
    </div>
</div>
<table class="TableBlock" width="100%">
    <tr>
        <td width="85%" height="768" valign="top">
            <script src="${ LUI_ContextPath}/sys/attachment/sys_att_main/dianju/format/js/Loadaip.js?s_cache=${ LUI_Cache }"></script>
        </td>
    </tr>
</table>

</body>

</html>
