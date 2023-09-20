<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
    seajs.use(['lui/jquery','lui/qrcode'], function($,qrcode) {
        $("#share_friends_QRCode").empty();

        var url = "${param.fdUrl}";
        var isBitch = navigator.userAgent.indexOf("MSIE") > -1 && document.documentMode == null || document.documentMode <= 8;
        qrcode.Qrcode({
            text :url,
            element : $("#share_friends_QRCode"),
            render : isBitch ? 'table' : 'canvas',
            width:142,
            height:142
        });
    });
</script>
<body>
    <div style="padding:20px 25px 0px;">
        <table class="share_friends_tbl">
            <tbody>
            <tr>
                <!-- 二维码 -->
                <td class="share_friends_QRCode">
                    <div id="share_friends_QRCode">
                        <canvas width="150" height="150"></canvas>
                    </div>
                </td>
                <td class="share_friends_tip">
                    <div>${lfn:message('sys-evaluation:sysEvaluation.share.scan.desc')}</div>
                    <div>${lfn:message('sys-evaluation:sysEvaluation.share.qrcode.desc')}</div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</body>