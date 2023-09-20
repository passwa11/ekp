<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link type="text/css" rel="stylesheet"
      href="${KMSS_Parameter_ContextPath}sys/authentication/token/server/resource/css/shareButton.css?cache=${LUI_Cache}"/>
<kmss:authShow roles="ROLE_SYSTOKEN_SETTING">
    <div class="shareButton">
        ${ lfn:message('kms-common:kmsShareMain.share') }
    </div>
</kmss:authShow>
<script>
    seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
        $('.shareButton').on("click",function (evt) {
            var method = getQueryVariable('method');
            var fdModelName = '${param.fdModelName}';
            var fdModelId = '${param.fdModelId}';
            var targetUrl = window.location.href;
            dialog.iframe("/sys/authentication/token/server/setShareCfgDialog.jsp", "${ lfn:message("sys-authentication:token.share.setting") }", function (data) {
                if(!data){
                    return;
                }
                //打开分享链接的弹窗
                openShareLinkDialog(data);
            }, {
                width: 650,
                height: 450,
                params: {
                    method:method,
                    fdModelName:fdModelName,
                    fdModelId:fdModelId,
                    targetUrl:targetUrl
                }
            });
        });

        function openShareLinkDialog(token) {
            dialog.iframe("/sys/authentication/token/server/shareLinkDialog.jsp", "${ lfn:message("sys-authentication:token.share.link") }", function (data) {
                if(!data){
                    return;
                }
            }, {
                width: 700,
                height: 250,
                params: {
                    token:token
                }
            });
        }

        function getQueryVariable(variable) {
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i=0;i<vars.length;i++) {
                var pair = vars[i].split("=");
                if(pair[0] == variable){return pair[1];}
            }
            return(false);
        }
    })

</script>