define(["dojo/_base/declare", "mui/util", "dojo/request", "mui/device/adapter", "dojo/topic","mui/dialog/Tip", "mui/i18n/i18n!sys-modeling-main"
       ], function (declare, util, request, adapter, topic,tip,modelingLang) {
    window.SYS_CATEGORY_TYPE_CATEGORY = 0 //"CATEGORY" 类别

    window.SYS_CATEGORY_TYPE_TEMPLATE = 1 //"TEMPLATE" 模板

    var sysCategory = declare("sys.modeling.main.mobile.listView.buttonMixin", null, {
        type: window.SYS_CATEGORY_TYPE_TEMPLATE,

        showCommonCate: false,

        confirm: !this.isMul,

        isMul: false,

        flow: true,

        createUrl: "",

        fdAppModelId:"",

        templURL: "/sys/modeling/main/resources/js/mobile/listView/flowSelect.html!extendPara=key:!{key}&s_time=" + new Date().getTime(),

        postCreate: function () {
            this.inherited(arguments);
            // 监听导航项切换事件
            this.subscribe('/mui/navitem/_selected', 'handleNavChanged');
        },
        handleNavChanged: function (item, data) {
            //每次页签改变会监听两次，防止重复请求对第二次过滤掉。
            if (item.appModelId === this.fdAppModelId){
                return;
            }
            this.fdAppModelId = item.appModelId;
            var flowUrl = "/sys/modeling/main/modelingAppModelMain.do?method=getModelIsFlow&fdAppModelId=" + this.fdAppModelId;
            var self = this;
            request.get(util.formatUrl(flowUrl), {handleAs: 'json'}).then(function (flow) {
                self.flow = flow;
                if (flow) {
                    self.createUrl = "/sys/modeling/main/modelingAppModelMain.do?method=add&fdAppModelId=" + self.fdAppModelId + "&fdAppFlowId=!{curIds}";
                } else {
                    self.createUrl = "/sys/modeling/main/modelingAppSimpleMain.do?method=add&fdAppModelId=" + self.fdAppModelId;
                }
            });
        },
        _onClick: function (evt) {

            if (this.flow) {
                var url = "/sys/modeling/main/modelingAppFlowMain.do?method=findFlows&fdAppModelId=" + this.fdAppModelId;
                var self = this;
                // 查询流程是否是只有一条，是则直接打开新建页面
                request.get(util.formatUrl(url), {handleAs: 'json'}).then(function (ret) {
                    if (ret && ret.status) {
                        if(ret.status == "success"){
                            if (ret.data && ret.data.length == 1) {
                                var id = ret.data[0].id;
                                var href = "/sys/modeling/main/modelingAppModelMain.do?method=add&fdAppModelId=" + self.fdAppModelId + "&fdAppFlowId=" + id;
                                //adapter.open(util.formatUrl(href), "_blank");
                                window.open(util.formatUrl(href), '_self');
                            } else if(ret.data && ret.data.length == 0){
                                tip.warn({text:modelingLang['mui.modeling.flow.noRecord']});
                            }else {
                                self._selectCate(arguments);
                            }
                        }else{
                            alert(modelingLang['mui.modeling.findFlow.error']);
                        }

                    }
                },function (err) {
                    //#169527 该处需要兼容未登录状态，目前除了登录过滤器以及建模发布状态过滤器这两个，没有考虑到其他情况会走向该分支
                    if(err.response.text.indexOf("module.main.444.flag")>-1){
                        window.document.write(err.response.text)
                    }else{
                        window.location.reload();
                    }
                });
            } else {
                var href = this.createUrl;
                window.open(util.formatUrl(href), '_self');
            }
        },
    })
    return sysCategory
})
