<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
    function shareGroupOpt() {
        this.onload=function(){
            $("#fdGroupId").change(function () {
                if($(this).val()==""){
                    $("#fdGroupIdAdvice").show();
                }else{
                    $("#fdGroupIdAdvice").hide();
                }
            });

            //提交按钮事件初始化
            $("#share_to_group_button").click(function(){
                var fdGroupId = $("#fdGroupId").val();
                if(fdGroupId==""){
                    $("#fdGroupIdAdvice").show();
                    return ;
                }

                // var reasonIframeBody = $('iframe[id="shareGroupIframe"]')[0].contentDocument.body;
                // var fdTopic = reasonIframeBody.innerHTML;
                // if(fdTopic==""){
                //     $("#fdGroupTitleAdvice").show();
                //    return ;
                // }
                var fdTopic = parent.window.document.title;

                // 分享内容
                var shareTxt = parent.window.document.title;

                // 是否通知创建者
                var fdIsNotify = $("#fdGroupIsNotify").is(":checked");

                // 通知类型
                var fdNotifyType = $("input[name='fdGroupNotifyType']")[0].value;

                // 分享url
                var shareUrl = parent.window.location.href;
 
                var paramDara={
                    fdShareMode: "3", // 1个人  3圈子
                    docSubject: shareTxt,
                    fdUrl: shareUrl,
                    fdGroupId: fdGroupId,
                    fdTopic: fdTopic,
                    fdIsNotify: fdIsNotify,
                    fdShareType: fdNotifyType,
                    fdModelId: "${param.fdModelId}",
                    fdModelName: "${param.fdModelName}"
                };

                seajs.use(['lui/dialog'],
                    function(dialog) {
                        var loading = dialog.loading();
                        LUI.$.ajax({
                            type : "POST",
                            url :  "<c:url value='/kms/common/kms_share/kmsShareMain.do?method=shareToGroup'/>",
                            data: paramDara,
                            dataType : 'json',
                            async: false,
                            success : function(data) {
                                if (data && data['flag'] === true) {
									if(parent.$("#share_count_${param.fdModelId}")[0]) {
										var shareCount = parent.$("#share_count_${param.fdModelId}")[0].innerText;
										parent.$("#share_action").attr("class","kms_share_icon_on");
										parent.$("#share_count_${param.fdModelId}").html(parseInt(shareCount)+1);
									}
                                	
                                    loading.hide();
                                    $dialog.hide(true);//true 用于回调，判断是否分享成功
                                    dialog.success("${ lfn:message('kms-common:kmsShareMain.shareSuccess') }");
                                }else{
                                    dialog.failure("${lfn:message('return.optFailure')}");
                                }
                            },
                            error: function(e) {
                                console.error(e)
                            }
                        });
                    }
                );
            });
        }
    }

    //字数校验
    function checkGroupWordsNum(isPaste){
        var reasonIframeBody = $('iframe[id="shareGroupIframe"]')[0].contentDocument.body;
        var shareTxt = reasonIframeBody.innerHTML;

        if(shareTxt==""){
            $("#fdGroupTitleAdvice").show();
        }else{
            $("#fdGroupTitleAdvice").hide();
        }

        var l = 0;
        var tmpArr = shareTxt.split("");
        for (var i = 0; i < tmpArr.length; i++) {
            if (tmpArr[i].charCodeAt(0) < 299) {
                l++;
            } else {
                l += 2;
            }
        }
        var promptVar = $("#group_share_prompt");
        promptVar.html('<font style="color:#F19703">' +
            Math.abs(parseInt(l/ 2))+"</font>"+"/60");
        promptVar.css({'color':''});
        if(l<=120){
            enabledGroupBtn(true);
        }else{
            if(isPaste){
                reasonIframeBody.innerHTML = subString1(reasonIframeBody.innerText.trim(), 60*2);
                shareTxt = reasonIframeBody.innerHTML;
            }
            enabledGroupBtn(false);
        }
    }

    /**
     * 截取字符串 中英文混合
     * @param str	待处理字符串
     * @param len	截取字节长度 中文2字节 英文1字节
     */
    function subString1(str, len){
        // 去除空格
        str= str.replace(/\s/g,"");

        var regexp = /[^\x00-\xff]/g;// 正在表达式匹配中文
        // 当字符串字节长度小于指定的字节长度时
        if (str.replace(regexp, "aa").length <= len) {
            return str;
        }
        // 假设指定长度内都是中文
        var m = Math.floor(len/2);
        for (var i = m, j = str.length; i < j; i++) {
            // 当截取字符串字节长度满足指定的字节长度
            if (str.substring(0, i).replace(regexp, "aa").length >= len) {
                return str.substring(0, i);
            }
        }
        return str;
    }

    //button事件处理
    function enabledGroupBtn(enable){
        var buttonVar = $("#share_to_group_button");
        if(enable==true){
            buttonVar.removeAttr("disabled");
            buttonVar.removeClass("share_button_disable");
        }else{
            buttonVar.attr("disabled","disabled");
            buttonVar.addClass("share_button_disable");
        }
    };

    function bindGroupIframeEvent() {
        $('iframe[id="shareGroupIframe"]').contents().find("body").bind({
            "keyup": function (e) {
                checkGroupWordsNum();
            },
            "focus mouseup": function () {
                checkGroupWordsNum();
            },
            // 兼容右键粘贴字数限制
            "input": function () {
                checkGroupWordsNum();
            },
            //兼容ie浏览器右键粘贴
            "paste cut": function () {
                var $this = $(this);
                var _this = this;

                setTimeout(function () {
                    checkGroupWordsNum(true);
                }, 2);
            }
        })
    }
</script>