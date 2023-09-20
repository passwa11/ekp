define([
    "dojo/_base/declare",
    "mui/tabbar/TabBar",
    "mui/tabbar/TabBarButton",
    "mui/dialog/Dialog",
    "dijit/registry",
    "dojo/dom-construct",
    "dojo/dom-class",
    "dojo/_base/array",
    "dojo/_base/lang",
    "dojo/topic",
    "dojo/query",
    "mui/util",
    "dojo/request",
    "./OperTabBarButtonMixin",
    "mui/i18n/i18n!sys-modeling-main"
], function(declare, TabBar, TabBarButton, Dialog, registry, domConstruct, domClass, array, lang, topic, query, util,request, OperTabBarButtonMixin,modelingLang) {
    //事务或操作切换按钮
    return declare("sys.modeling.main.resources.js.mobile.formview.OperMoreButton", [OperTabBarButtonMixin, TabBarButton], {

        methodSwitch:true,//操作切换

        options:[],

        OPER_SWITCH: "/modeling/operation/switch",

        tmpl:'<div data-dojo-type="mui/form/RadioGroup" ' +
            'data-dojo-props="showStatus:\'edit\',name:\'_lbpm_switch_radio\',renderType:\'table\',mul:\'false\',store:{store},orient:\'vertical\'"></div>',

        buildRendering : function() {
            if(!this.label){
                this.label = modelingLang['mui.modeling.more'];
            }
            this.inherited(arguments);
        },

        postCreate: function() {
            this.inherited(arguments);
            this.subscribe(this.OPER_SWITCH,"switchButton");
        },

        startup : function() {
            if (this._started)
                return;
            this.inherited(arguments);
        },

        switchButton:function(wgt,ctx){
            if(ctx){
                var operObj = this.getOperationByOperId(ctx.value);
                if (operObj) {
                    this.doSwitchBtn(operObj);
                }
            }
        },

        doSwitchBtn : function(operObj) {
            var fdDefType = operObj.defType;
            if (fdDefType) {
                //默认操作
                if (fdDefType == 3) {
                    //编辑
                    this.edit(operObj);
                } else {
                    console.log("【业务建模】未定义的操作类型：" + fdDefType);
                }
            }else{
                var self = this;
                var url = util.formatUrl(operObj.operUrl + operObj.operId);
                request.get(url,{handleAs : 'json'}).then(function(json){
                    // 处理请求返回的数据
                    self.clickOperationCallback(json);
                });
            }
        },

        edit : function(operObj){
            if(operObj.isFlow === "false"){
                Com_OpenWindow(Com_Parameter.ContextPath + 'sys/modeling/main/modelingAppSimpleMain.do?method=edit&fdId='+ operObj.fdId +'&listviewId=' + operObj.listviewId,'_self');
            }else{
                Com_OpenWindow(Com_Parameter.ContextPath + 'sys/modeling/main/modelingAppModelMain.do?method=edit&fdId='+ operObj.fdId +'&listviewId=' + operObj.listviewId,'_self');
            }
        },

        getOperationByOperId: function(operId) {
            if(this.options.length>0){
                for (var i = 0; i < this.options.length; i++) {
                    var operObj = this.options[i];
                    if (operObj.operId === operId) {
                        return operObj;
                    }
                }
            }
        },

        onClick : function(evt) {
            if(this.dialog){
                return false;
            }
            evt.preventDefault();
            evt.stopPropagation();

            //避免ios kk 双击
            var nowTime = new Date().getTime();
            var clickTime = this.cbtime;
            if (clickTime != "undefined" && nowTime - clickTime < 500) {
                return false;
            }
            this.cbtime = nowTime;

            if(this.options.length>0){
                var operations = [];

                for (var i = 0; i < this.options.length; i++) {
                    var operation = this.options[i];
                    if (operation.isRender) {
                        operations.push(operation);
                    }
                }
                this.dialogDom = domConstruct.toDom(lang.replace(this.tmpl,
                    {store:JSON.stringify(this.options).replace(/\"/g,"\'")}));
            }
            this.defer(function(){
                if(this.dialogDom){
                    var self = this;
                    this.dialog = Dialog.element({
                        canClose : false,
                        showClass : 'muiDialogElementShow muiFormSelect',
                        element: this.dialogDom,
                        position:'bottom',
                        'scrollable' : false,
                        'parseable' : true,
                        onDrawed:function(){
                            self.defer(function(){
                                self.initOptionsEvent();
                            },100);
                        },
                        callback:function(){
                            self.dialog=null;
                        }
                    });
                }
            },500);
        },

        initOptionsEvent:function(){
            var self = this;
            var redioNodeList = query(".muiRadioTableItem",this.dialogDom);
            self._curTime = 0;
            redioNodeList.on("click",function(evt){
                evt.preventDefault();
                evt.stopPropagation();

                var curTime = new Date();
                if(curTime - self._curTime<500){
                    return;
                }
                self._curTime = curTime;
                var srcDom = evt.target;
                var fieldObj;
                if(srcDom.className!='' && srcDom.className.indexOf('muiRadioTableItem')>-1){
                    fieldObj = srcDom;
                }else{
                    fieldObj = query(evt.target).parents(".muiRadioTableItem")[0];
                }

                topic.publish(self.OPER_SWITCH,self,{
                    methodSwitch: self.methodSwitch,
                    value: query("input",fieldObj).val(),
                    label: registry.byNode(query("input",fieldObj)[0]).text
                });
                if (self.dialog){
                    self.dialog.hide();
                }
                self.dialog=null;
            });
        }
    });
});