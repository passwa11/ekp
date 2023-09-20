

require(['dojo/topic', 'dojo/ready','dijit/registry','dojo/query', "dojo/request","lib/weixin/jweixin-1.0.0","mui/util","mui/dialog/Tip"],
    function(topic,ready,registry,query,request,wx,util,Tip) {

        var ___ready___ = false;

        /**
         * 立即直播
         * @param options：{"liveType": 1,"theme": "新同学培训444"}
         */
        window.livingImmediately = function (options) {
           // options={"liveType": 1,"theme": "新同学培训444","modelName":"com.landray.kmss.km.imeeting.model.KmImeetingMain","modelId":"123"};
            this.agentConfig(function (){
                wx.invoke('startLiving', options, function (res) {
                    console.log("立即直播：", res);
                    if (res.err_msg == "startLiving:ok") {
                        options.livingId= res.livingId;
                        //创建映射
                        crateMapping(options);
                    }else if (res.err_msg != "startLiving:cancel") {
                        Tip.fail({
                            text : '创建直播失败:'+JSON.stringify(res)
                        });
                    }
                });
            });
        };

        /**
         * 保存直播映射关系
         * @param options
         */
        function crateMapping(options) {
            url = util.formatUrl('/third/weixin/work/third_weixin_work_living/thirdWeixinWorkLiving.do?method=createMapping');
            //后端获取签名信息
            request.post(url, {
                data:options,
                handleAs:'json',
                headers: {"accept": "application/json"}
            }).response.then(function (rtn) {
               console.log("建立mapping",rtn)
            });
        }

        /**
         * 检查当前文档是否存在直播
         * @param options：{"modelName":"com.landray.kmss.km.imeeting.model.KmImeetingMain","modelId":"123"};
         */
        window.checkLiving = function (options) {
            url = util.formatUrl('/third/weixin/work/third_weixin_work_living/thirdWeixinWorkLiving.do?method=checkLiving');
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
                        Tip.fail({
                            text : data.result
                        });
                    }
                }
            });
            return isExist;
        };

        /*
         * 直播开关是否打开
         */
        window.canLiving = function (options) {
            url = util.formatUrl('/third/weixin/work/third_weixin_work_living/thirdWeixinWorkLiving.do?method=canLiving');
            var result;
            $.ajax({
                type:"post",
                url:url,
                data:options,
                async:false,
                success:function(data){
                    console.log(data)
                    data=JSON.parse(data);
                    result = data.success;
                }
            });
            return result;
        };

        /**
         * 获取直播id
         */
        window.getLivingId = function (){
            return window.__livingid__;
        }
        /**
         * 避免鉴权还没有成功就调接口
         * @param callback
         */
        window.agentConfig = function (callback){
            if(___ready___){
                callback && callback.call(this);
            }else{
                console.log("---延迟500毫秒----");
                setTimeout(callback,500);
            }
        };

        /**
         * 打开直播
         * @param linvingid
         */
        window.openLiving = function (linvingid) {
            console.log("linvingid:"+linvingid);
            this.agentConfig(function (){
                wx.invoke('startLiving', {
                    "livingId": linvingid,
                }, function (res) {
                    console.log("打开直播res", res);
                    if (res.err_msg == "startLiving:ok") {
                        livingId = res.livingId;
                    }else {
                        Tip.fail({
                            text : '打开直播失败:'+JSON.stringify(res)
                        });
                    }
                });
            });
        };

        (function(){
            var jsApiList = ['startLiving', 'config', 'agentConfig'];
            url = util.formatUrl('/third/wxwork/jsapi/wxJsapi.do?method=jsapiSignature&type=agent_config'),
                option = {data: {url: location.href}, handleAs: 'json'};
            console.log("url:",url);
            //后端获取签名信息
            request.post(url, option).response.then(function (rtn) {
                var signInfo = rtn.data;
                if (signInfo && signInfo.appId) {
                    console.log("signInfo", signInfo);
                    wx.agentConfig({
                        beta: true,
                        debug: true,
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
                            Tip.fail({
                                text : 'agentConfig失败:'+JSON.stringify(res)
                            });
                            console.log('agentConfig失败',res);
                        }
                    });

                } else {
                    console.log('signInfo appId empty(wxwork)');
                }
            });
        })();
    });