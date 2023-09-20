seajs.use(['lui/dialog','sys/mobile/js/lib/weixin/jweixin-1.0.0'], function( dialog,wx) {
    /**
     * 立即直播
     * @param options：{"liveType": 1,"theme": "新同学培训444"}
     */
    window.livingImmediately = function (options) {
        wx.invoke('startLiving', options, function (res) {
            console.log("立即直播：", res);
            if (res.err_msg == "startLiving:ok") {
                options.livingId= res.livingId;
                //创建映射
                crateMapping(options);
            }else if (res.err_msg != "startLiving:cancel") {
                dialog.alert("创建直播失败："+res.err_msg);
            }
        });
    };

    /**
     * 打开直播
     * @param linvingid
     */
    window.openLiving = function (linvingid) {
        console.log("linvingid:"+linvingid);
        wx.invoke('startLiving', {
            "livingId": linvingid,
        }, function (res) {
            console.log("打开直播res", res);
            if (res.err_msg == "startLiving:ok") {
                livingId = res.livingId;
            }else {
                dialog.alert('打开直播失败:'+JSON.stringify(res));
            }
        });
    };
});



/**
 * 保存直播映射关系
 * @param options
 */
function crateMapping(options) {
    var url = Com_Parameter.ContextPath+'third/weixin/work/third_weixin_work_living/thirdWeixinWorkLiving.do?method=createMapping';
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: options,
        error: function (data) {
            console.log("会话校验失败：",data);
        },
        success: function (data) {
            console.log("建立mapping",data)
        }
    });
}

/**
 * 检查当前文档是否存在直播
 * @param options：{"modelName":"com.landray.kmss.km.imeeting.model.KmImeetingMain","modelId":"123"};
 */
window.checkLiving = function (options) {
    url = Com_Parameter.ContextPath+'third/weixin/work/third_weixin_work_living/thirdWeixinWorkLiving.do?method=checkLiving';
    var isExist ;
    $.ajax({
        type:"post",
        url:url,
        data:options,
        async:false,
        success:function(data){
            console.log("检查结果：",data)
            data=JSON.parse(data);
            if(data.success){
                isExist=data.exist;
                window.__livingid__=data.livingId;
            }else{
                console.log(data.result);
            }
        }
    });
    return isExist;
};

/**
 * 获取直播id
 */
window.getLivingId = function (){
    return window.__livingid__;
};


(function(){
    seajs.use(['lui/jquery', 'lui/dialog', 'lang!','sys/mobile/js/lib/weixin/jweixin-1.0.0'], function($, dialog, lang,wx) {
        var jsApiList = ['startLiving', 'config', 'agentConfig'];
        url = Com_Parameter.ContextPath+'third/wxwork/jsapi/wxJsapi.do?method=jsapiSignature&type=agent_config',
            option = {data: {url: location.href}, handleAs: 'json'};
        console.log("url:",url);
        console.log("option:",option);
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: {url: location.href},
            async: true,
            error: function (data) {
                console.log("会话校验失败：",data);
            },
            success: function (data) {
                console.log("返回结果:",data);
                var signInfo = data;
                if (signInfo && signInfo.appId) {
                    wx.agentConfig({
                        beta: true,
                        debug: false,
                        corpid: signInfo.corpid, // 必填，企业微信的corpid，必须与当前登录的企业一致
                        agentid: signInfo.agentid, // 必填，企业微信的应用id （e.g. 1000247）
                        timestamp: signInfo.timestamp, // 必填，生成签名的时间戳
                        nonceStr: signInfo.noncestr, // 必填，生成签名的随机串
                        signature: signInfo.signature,// 必填，签名，见附录-JS-SDK使用权限签名算法
                        jsApiList: jsApiList, //必填，传入需要使用的接口名称
                        success: function (res) {
                            ___ready___=true;
                            console.log("agentConfig鉴权成功", res);
                        },
                        fail: function (res) {
                            ___ready___=false;
                            console.log('agentConfig失败',res);
                            console.log('agentConfig失败'+JSON.stringify(res));
                        }
                    });
                } else {
                    console.log('signInfo appId empty(wxwork)');
                }
            }
        });
    });
})();