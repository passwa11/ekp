<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.profile.model.SysCommonSensitiveConfig" %>
<%
    String isCheckWord = SysCommonSensitiveConfig.newInstance().getIsSensitiveCheck();
    request.setAttribute("isCheckWord",isCheckWord);
%>
<script type="text/javascript">
    seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
        // 敏感词过滤是否开启
        window.isSensitiveCheck = "${isCheckWord}";

        //判断是否含有敏感词
        window.checkIsHasSenWords= function(content, title) {
            var result = false;
            var url = "${LUI_ContextPath}/sys/profile/sysCommonSensitiveConfig.do?method=getIsHasSensitiveword";
            var data ={content:encodeURIComponent(content), formName:"kmsMultidocKnowledgeForm"};

            LUI.$.ajax({
                url: url,
                type: 'post',
                dataType: 'json',
                async: false,
                data: data,
                success: function(data, textStatus, xhr) {
                    var flag = data.flag;
                    if(flag){
                        var msg = "${lfn:message('errors.sensitive.word.warn')}"
                            .replace("{0}", title).replace("{1}",
                                '<span style="color:#cc0000">'+data.senWords+'</span>' );
                        dialog.alert(msg);
                        result = true;
                    }

                }
            });
            return result;
        };


    });
</script>
