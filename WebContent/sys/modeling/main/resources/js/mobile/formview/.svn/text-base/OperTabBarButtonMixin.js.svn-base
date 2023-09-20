define([ "dojo/_base/declare", "dijit/registry", "mui/util", "dojo/request",
        "mui/device/adapter","mui/device/weixin/adapter", "mui/dialog/Tip",
        "mui/dialog/Dialog", "dojo/dom-construct", "mui/i18n/i18n!sys-mobile",
        "dojo/_base/lang", 'mui/createUtils',"dojo/request/xhr","mui/i18n/i18n!sys-modeling-main",
        "mui/device/device","mui/form/_CategoryBase"],
    function(declare, registry, util, request, adapter,weixinAdapter,
             Tip, Dialog, domConstruct, Msg, lang, createUtils, xhr,modelingLang, device,CategoryBase) {

        var h = createUtils.createTemplate;

        return declare("sys.modeling.main.resources.js.mobile.formview.OperTabBarButtonMixin",[CategoryBase], {

            // 定义的类型
            defType : "",

            operUrl : "",

            fdId : "",

            isFlow : "false",

            operId : "",

            listviewId:"",

            fdModelId:"",

            templURL: "/sys/modeling/main/resources/js/mobile/listView/flowSelect.html!extendPara=key:!{key}&s_time=" + new Date().getTime(),

            key: '_cateSelect',

            fdAppModelId:"",

            onClick : function(evt) {
                var fdDefType = this.defType;
                if (fdDefType) {
                    //默认操作
                    if (fdDefType == 3) {
                        //编辑
                        this.edit();
                    }else if(fdDefType == 14){
                        this.copyFlow();
                    } else {
                        console.log("【业务建模】未定义的操作类型：" + fdDefType);
                    }
                }else{
                    var self = this;
                    var url = util.formatUrl(this.operUrl + this.operId);
                    var isOperationPay="";
                    //检查操作是否是支付场景下
                    var oprUrl=util.formatUrl("/sys/modeling/main/sysModelingOperation.do?method=doCheckPayment&fdOprId="+this.operId);
                    request.post(oprUrl,{handleAs:'json',sync:true}).then(function(json){
                        var data = json;
                        isOperationPay=data.status;
                    });
                    if (isOperationPay==true){
                        //统一下单 tradeType支付方式是JSPAPI移动支付
                        var fdOprId=this.operId;
                        var doPayUrl=util.formatUrl("/sys/modeling/main/sysModelingOperation.do?method=doPayOperation&tradeType=JSAPI&fdId="+this.fdId+"&fdOprId="+this.operId);
                        request.get(doPayUrl,{handleAs : 'json'}).then(function(json){
                            var data = json;
                            if(data.status){
                                self.clickPayOperationCallback(json,fdOprId);
                            }else{
                                Tip.fail({
                                    text : data.title
                                });
                                return;
                            }
                        });
                    }else{
                        //通用场景
                        request.get(url,{handleAs : 'json'}).then(function(json){
                            // 处理请求返回的数据
                            self.clickOperationCallback(json);
                        });
                    }

                }
            },
            edit : function(){
                if(this.isFlow === "false"){
                    Com_OpenWindow(Com_Parameter.ContextPath + 'sys/modeling/main/modelingAppSimpleMain.do?method=edit&fdId='+ this.fdId +'&listviewId=' + this.listviewId,'_self');
                }else{
                    Com_OpenWindow(Com_Parameter.ContextPath + 'sys/modeling/main/modelingAppModelMain.do?method=edit&fdId='+ this.fdId +'&listviewId=' + this.listviewId,'_self');
                }

            },
            copyFlow : function(){
                var href = "/sys/modeling/main/modelingAppModelMain.do?method=add&fdReviewId=" + this.fdId+"&fdAppModelId="+this.fdModelId;
                window.open(util.formatUrl(href), '_self');
            },

            clickOperationCallback : function(dataRaw){
                var data = dataRaw;
                if(!data.status){
                    dialog.result(data);
                    return;
                }
                if(!data.type){
                    return;
                }
                if(data.type == 'open'){
                    // 有视图，打开新窗口
                    var openUrl = util.formatUrl(data.url)
                    var deviceType = device.getClientType();
                    // 钉钉URl特殊处理
                    if(deviceType == 11 && !(new RegExp("^http").test(openUrl) || new RegExp("^https").test(openUrl) || new RegExp("^dingtalk").test(openUrl))){
                        openUrl = util.getHost() + openUrl;
                    }
                    adapter.open(openUrl, "_blank");
                } else if (data.type == 'msg'){
                    // 无视图
                    Tip.success({
                        text : data.title
                    });
                    if(data.status == true){
                        setTimeout(function(){
                            location.reload();
                        }, 2000);
                    }
                } else if (data.type == 'dlg'){
                    // 弹框
                    var param = data.param || {
                        params : {
                            userVars : []
                        }
                    };
                    var userVars = param.params.userVars || [];
                    if(userVars.length > 0){
                        this.openDialog(param);
                    }else{
                        if(data.url && data.url.indexOf("sys/modeling/main/flow_dialog.jsp") > -1){
                            //选流程
                            this.fdOprId = data.fdOprId;
                            this.incFdId = data.incFdId;
                            this.fdAppModelId = data.fdModelId;
                            this.fdModelId = data.fdModelId;
                            this.createUrlGenerator();
                            this.selectFlowDialog();
                        }else{
                            console.log("【业务建模】业务操作记录里面没有可供“用户输入”选项");
                        }
                    }
                }
            },
            createUrlGenerator : function(){
                var self = this;
                var flowUrl = "/sys/modeling/main/modelingAppModelMain.do?method=getModelIsFlow&fdAppModelId=" + this.fdAppModelId;
                request.get(util.formatUrl(flowUrl), {handleAs: 'json'}).then(function (flow) {
                    self.flow = flow;
                    if (flow) {
                        self.createUrl = "/sys/modeling/main/modelingAppModelMain.do?method=add&fdAppModelId=" + self.fdAppModelId + "&fdAppFlowId=!{curIds}&fdOprId=!{fdOprId}&incFdId=!{incFdId}";
                    } else {
                        self.createUrl = "/sys/modeling/main/modelingAppSimpleMain.do?method=add&fdAppModelId=" + self.fdAppModelId;
                    }
                });
            },

            afterSelectCate:function(evt){
                evt.fdOprId = this.fdOprId;
                evt.incFdId = this.incFdId;
                adapter.open(util.formatUrl(util.urlResolver(this.createUrl, evt)),"_self");
                this.curIds = "";
                this.curNames = "";
            },
            /*afterSelect: function(evt){
                this.afterSelectCate(evt);
            },*/

            selectFlowDialog : function(){
                var url = "/sys/modeling/main/modelingAppFlowMain.do?method=findFlows&fdAppModelId=" + this.fdModelId;
                var self = this;
                // 查询流程是否是只有一条，是则直接打开新建页面
                request.get(util.formatUrl(url), {handleAs: 'json'}).then(function (ret) {
                    if (ret && ret.status) {
                        if(ret.status == "success"){
                            if (ret.data && ret.data.length == 1) {
                                var id = ret.data[0].id;
                                var href = "/sys/modeling/main/modelingAppModelMain.do?method=add&fdAppModelId=" + self.fdModelId + "&fdAppFlowId=" + id;
                                //adapter.open(util.formatUrl(href), "_blank");
                                window.open(util.formatUrl(href), '_self');
                            } else if(ret.data && ret.data.length == 0){
                                Tip.warn({text:modelingLang['mui.modeling.flow.noRecord']});
                            }else {
                                self._selectCate(arguments);
                            }
                        }else{
                            alert(modelingLang['mui.modeling.findFlow.error']);
                        }

                    }
                })
            },

            clickPayOperationCallback : function(dataRaw,fdOprId){
                var data = dataRaw;
                if(!data.status){
                    dialog.result(data);
                    return;
                }
                var options = {
                    modelName:data.modelName,
                    modelId:data.modelId,
                    fdKey:data.fdKey
                };
                weixinAdapter.payRequest(options,
                    function (){
                        //付款成功后更新业务字段。
                        var doUpdatePayInfoUrl=util.formatUrl("/sys/modeling/main/sysModelingOperation.do?method=doUpdatePayInfo&modelName="+data.modelName+"&modelId="+data.modelId+"&fdKey="+data.fdKey+"&fdOprId="+fdOprId);
                        request.get(doUpdatePayInfoUrl,{handleAs : 'json'}).then(function(json){
                            var data = json;
                            if(data.status){
                                Tip.success({
                                    text : data.title
                                });
                                setTimeout(function(){
                                    location.reload();
                                }, 2000);
                            }else{
                                Tip.fail({
                                    text : data.title
                                });
                                return;
                            }
                        });
                    },
                    function (e){
                        Tip.fail({
                            text : "支付失败，或订单已支付"
                        });
                    }
                );
            },

            openDialog : function(params){
                if(!this.dialog){
                    this.dialogParms = params;
                    var element = this.createDialogContentElement(params);
                    this.dialog = Dialog.element({
                        element : element,
                        title : params.title,
                        parseable : true,
                        destroyAfterClose : false,
                        scrollable : false,
                        buttons : [ {
                            title : Msg["mui.search.cancel"],
                            fn : function(dialog) {
                                dialog.hide();
                            }
                        } ,{
                            title : Msg["mui.button.ok"],
                            fn : lang.hitch(this,function(dialog) {
                                this.doSubmit();
                                dialog.hide();
                            })
                        } ]
                    });
                }
                this.dialog.show();
            },

            createDialogContentElement : function(){
                var contentWrap = domConstruct.create("div",{
                    className : "modelingOperContent"
                });
                contentWrap.innerHTML = this.getContentHtml();
                return contentWrap;
            },

            getContentHtml : function(){
                var html = "";
                html += "<table class='muiSimple' cellpadding='0' cellspacing='0'><tbody>";
                var dialogParams = this.getDialogParams();
                for(var i = 0;i < dialogParams.userVars.length;i++){
                    var itemInfo = dialogParams.userVars[i];
                    html += "<tr>";
                    html += "<td class='muiTitle' style='width:26%;'>"+ itemInfo.widgetName +"</td>";
                    html += "<td>";
                    html += this.createWgtHtml(itemInfo);
                    html += "</td>";
                    html += "</tr>";
                }
                html += "</tbody></table>";
                return html;
            },

            createWgtHtml : function(itemInfo){
                var html = "";
                var wgtType = itemInfo.widgetType;
                if(wgtType === "String"){
                    html += h('div', {
                        dojoType: 'mui/form/Input',
                        dojoProps: {
                            name: itemInfo.triggerId + "_" + itemInfo.widgetId,
                            subject: itemInfo.widgetName,
                            required : false ,
                            showStatus : "edit",
                            _cfgInfo : itemInfo
                        }
                    });
                }else if (wgtType === "Date" || wgtType === "Time" || wgtType === "DateTime"){
                    html += h('div', {
                        dojoType: 'mui/form/DateTime',
                        dojoProps: {
                            valueField: itemInfo.triggerId + "_" + itemInfo.widgetId,
                            subject: itemInfo.widgetName,
                            required : false ,
                            showStatus : "edit",
                            _cfgInfo : itemInfo
                        },
                        dojoMixins : "mui/datetime/_"+ wgtType +"Mixin"
                    });
                }else if(wgtType.indexOf("com.landray.kmss.sys.organization") > -1){
                    html += h('div', {
                        dojoType: 'mui/form/Address',
                        dojoProps: {
                            idField: itemInfo.triggerId + "_" + itemInfo.widgetId + ".id",
                            nameField: itemInfo.triggerId + "_" + itemInfo.widgetId + ".name",
                            subject: itemInfo.widgetName,
                            required : false ,
                            showStatus : "edit",
                            _cfgInfo : itemInfo
                        }
                    });
                }else{
                    console.log("【业务建模】业务操作暂不支持该字段类型：" + wgtType);
                }
                return html;
            },

            getDialogInputVars : function(){
                var inputVars = [];
                for(var i = 0;i < this.dialog.htmlWdgts.length;i++){
                    var htmlWgt = this.dialog.htmlWdgts[i];
                    if(htmlWgt._cfgInfo){
                        var data = htmlWgt._cfgInfo;
                        var value = htmlWgt.get("value");
                        var userVar = {
                            "triggerId": data.triggerId,
                            "triggerType": data.type,
                            "type": data.type,
                            "widgetId": data.widgetId,
                            "widgetValue": value
                        }
                        inputVars.push(userVar);
                    }
                }
                return inputVars;
            },

            doSubmit : function(){
                var dialogParams = this.getDialogParams();
                var url = "/sys/modeling/main/sysModelingOperation.do?method=doOperation";
                url += "&userInput=true";
                url += "&fdOprId=" +dialogParams.fdOprId;
                url += "&fdId=" + dialogParams.fdId;
                if(dialogParams.ids){
                    url += "&List_Selected=" + (dialogParams.ids || "");
                }
                var inputVars = this.getDialogInputVars();
                url = util.formatUrl(url);

                xhr.post(url,{
                    data: JSON.stringify({"inputVars": inputVars}),
                    handleAs: "json",
                    headers: {
                        "Content-Type": "application/json",
                        Accept: "application/javascript, application/json"
                    }
                }).then(function(json){
                    if(json.status){
                        Tip.success({
                            text : modelingLang['mui.modeling.successful.operation']
                        });
                        location.reload();
                    }else{
                        Tip.fail({
                            text : modelingLang['mui.modeling.operation.failed.check.log']
                        });
                    }
                });

            },

            getDialogParams : function(){
                if(this.dialogParms){
                    return this.dialogParms.params || {};
                }
                return {};
            }
        })
    })
