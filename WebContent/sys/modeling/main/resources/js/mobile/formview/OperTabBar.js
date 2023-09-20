define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dojo/_base/window",
    "mui/tabbar/TabBar",
    "mui/dialog/Dialog",
    "mui/history/listener",
    "dijit/registry",
    "dojo/_base/array",
    "mui/tabbar/TabBarButton",
    "dojo/dom-style",
    "dojo/dom",
    "dojo/_base/window",
    "./OperButton",
    "./OperMoreButton"
], function(declare, lang, win, TabBar, Dialog, listener, registry, array, TabBarButton,domStyle,dom, win,OperButton, OperMoreButton) {

    return declare("sys.modeling.main.resources.js.mobile.formview.OperTarBar", [TabBar], {

        fill: 'grid',

        operations: [],

        showBtnSize: 3,

        listviewId : "",

        fdId : "",

        isFlow : false,

        docStatus : null,

        isListView:"",

        fdModelId:"",

        buildRendering : function() {
            this.inherited(arguments);
            this.initOperateInfo();
        },

        postCreate: function() {
            this.inherited(arguments);
            this.subscribe("/modeling/operation/switch","switchButton");
            // 列表视图监听导航项切换事件
            this.subscribe('/mui/navitem/_selected', 'handleNavChanged');
        },
        handleNavChanged: function (item, data) {
            //每次页签改变会监听两次，防止重复请求对第二次过滤掉。
            if (item.appModelId === this.fdAppModelId){
                return;
            }
            if (this.isListView ==="true"){
            var oldOpt = this.getChildren();
            var operations = this.getOldOperations(oldOpt);
            for (var i = 0;i<operations.length;i++){
                operations[i].destroy();
            }
            this.operations = item.operations;
            this.initOperateInfo();
            this.renderTabrBar();
            var newOpt = this.getChildren();
            var screenWidth =  win.global.innerWidth;
            var ItemWidth = screenWidth/newOpt.length+"px";
            // 日历视图没有操作按钮，
            if (this.operations){
                //由于新建按钮外面的jsp画好了，这里判断构建的按钮比当前页签的操作按钮多一个的情况。
                if (newOpt.length -this.operations.length == 1){
                    for (var i = 0;i< newOpt.length;i++){
                        //通过srcNodeRef来判断当前按钮是否是新建按钮
                        if ( newOpt[i].srcNodeRef ){
                            domStyle.set( newOpt[i].domNode,{'display':"none"});
                        }
                        domStyle.set( newOpt[i].domNode,{'width':ItemWidth});
                    }
                    return;
                }else {
                    for (var i = 0;i< newOpt.length;i++){
                        domStyle.set( newOpt[i].domNode,{'width':ItemWidth});
                        domStyle.set( newOpt[i].domNode,{'display':"block"});
                    }
                }
            }

            }

        },

        initOperateInfo:function(){
            var opers = [];
            this.renderByTabBarOpers = [];
            if (this.operations) {
                for (var i = 0; i < this.operations.length; i++) {
                    var oper = this.operations[i];
                    if (oper.fdDefType != "13") {
                        if (oper.fdDefType == "3" && this.docStatus == 30) {
                            continue;
                        }
                        if (oper.fdDefType == "0" ) {
                            continue;
                        }
                        oper.isRender = true;
                        this.renderByTabBarOpers.push(oper);
                    }
                }
            }
        },

        startup : function() {
            if (this._started)
                return;
            this.renderTabrBar();
            this.inherited(arguments);
        },

        switchButton:function(wgt,ctx){
            if(ctx){
            }
        },

        getChildren : function(){
            var children = this.inherited(arguments);
            var childrenWgts = [];
            array.filter(children,function(childWgt,idx){
                if(childWgt.isInstanceOf(TabBarButton)){
                    childrenWgts.push(childWgt);
                    return true;
                }
                return false;
            });
            return childrenWgts;
        },
        getOldOperations :function (oldOpt){
            var childrenWgts = [];
            for (var i = 0;i<oldOpt.length;i++){
               var srcNodeRef = oldOpt[i].srcNodeRef;
               if (srcNodeRef){
                   continue;
               }
                childrenWgts.push(oldOpt[i]);
            }
            return childrenWgts;
        },

        renderTabrBar:function(){
            var childrenWgts = this.getChildren();
            var renderOpers = this.renderByTabBarOpers.concat(childrenWgts);
            var curOperations = this.operations;
            if (this.isFlow == "true") {
                this.showBtnSize = 3;
            }
            if(renderOpers.length > 0){
                var maxIdx=0;
                var buttonIdx = 0;
                var needMore=false;
                if(renderOpers.length<=this.showBtnSize){
                    maxIdx = renderOpers.length;
                }else{
                    maxIdx = this.showBtnSize-1;
                    needMore = true;
                }
                for(var i=0;i<maxIdx;i++){
                    var operation = renderOpers[i];
                    if (operation.isRender) {
                        this.addChild(new OperButton({label:operation.text,operUrl:operation.url,
                            operId: operation.fdOperId, tabIndex:buttonIdx,defType: operation.fdDefType,
                            listviewId: this.listviewId, fdId: this.fdId, isFlow: this.isFlow,fdModelId:this.fdModelId,key:operation.fdOperId}),buttonIdx);
                        buttonIdx++;
                    } else if (operation.isInstanceOf(TabBarButton)) {
                        this.addChild(operation);
                    }
                }
                if(needMore){
                    var options=[];
                    for(var i=maxIdx;i<renderOpers.length;i++){
                        var operation = renderOpers[i];
                        if (operation.isRender) {
                            options.push({label:operation.text,operUrl:operation.url,operId: operation.fdOperId,
                                defType: operation.fdDefType,tabIndex:buttonIdx, listviewId: this.listviewId,
                                isFlow: this.isFlow, fdId: this.fdId, text: operation.text, value: operation.fdOperId,key:operation.fdOperId});
                        } else if (operation.isInstanceOf(TabBarButton)) {
                            this.addChild(operation);
                            buttonIdx++;
                        }
                    }
                    this.addChild(new OperMoreButton({options:options}), buttonIdx);
                }
            }
        }
    });
});