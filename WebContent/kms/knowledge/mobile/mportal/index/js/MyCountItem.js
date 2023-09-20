define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
    "dojo/dom-style",
    "dojo/_base/lang",
    "dijit/_WidgetBase",
    "dijit/_Container",
    "dijit/_Contained",
    "dojo/request",
    "mui/util",
    "mui/i18n/i18n!kms-knowledge:kms.knowledge.mobile.type"
], function(declare, domConstruct, domClass, domStyle, lang, _WidgetBase, _Container, _Contained, request, util, msg){
    return  declare("kms.knowledge.index.AllCountItem",[_WidgetBase, _Container, _Contained],{

        count: 1,

        url: "/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getIndexMyData&numtype=fdTotal&timetype=total",

        // 要显示的项
        type: "fdUpload;fdRead;fdIntroduce;fdCollect;fdComment;fdCorrection",

        uploadCount: 0,

        readCount: 0,

        commentCount: 0,

        collectCount: 0,

        introduceCount: 0,

        correctionCount: 0,

        buildRendering : function() {
            this.inherited(arguments);

            if (this.type == "") {
                this.type = "fdUpload;fdRead;fdComment;fdCollect;fdIntroduce;fdCorrection";
            }
            this.ajaxGet(util.formatUrl(this.url + "&opttype=" + this.type, true));
        },

        //构建内容
        build: function(data){
            if(data){
                console.log("数据获取成功")
                this.uploadCount = data.uploadCount;
                this.readCount = data.readCount;
                this.commentCount = data.commentCount;
                this.introduceCount = data.introCount;
                this.collectCount = data.collectCount;
                this.correctionCount=data.correctionCount;
            }

            var box = domConstruct.create("div",{
                id: "kms_knowledge_my_count_box"
            },this.domNode);

            var firstCol = domConstruct.create("div", {
                className: "col"
            }, box);
            this.buildCol(firstCol, this.uploadCount, msg['kms.knowledge.mobile.type.upload']
                , this.readCount, msg['kms.knowledge.mobile.type.read']
                , this.commentCount, msg['kms.knowledge.mobile.type.comment']
            )

            var secondCol = domConstruct.create("div", {
                className: "col"
            }, box);
            this.buildCol(secondCol, this.collectCount, msg['kms.knowledge.mobile.type.collect']
                , this.introduceCount, msg['kms.knowledge.mobile.type.introduce']
                , this.correctionCount, msg['kms.knowledge.mobile.type.correction']
            )

        },

        buildCol: function (column, leftNum, leftText
            , centerNum, centerText
            , rightNum, rightText) {

            var left = domConstruct.create("div", {
                className: "left"
            }, column);
            this.buildNode(left, leftNum, leftText);

            var center = domConstruct.create("div", {
                className: "center"
            }, column);
            this.buildNode(center, centerNum, centerText);

            var right = domConstruct.create("div", {
                className: "right"
            }, column);
            this.buildNode(right, rightNum, rightText);
        },

        buildNode: function (container, num, text) {
            var item = domConstruct.create("div", {
                className: "item"
            }, container);

            domConstruct.create("div", {
                className: "num",
                innerHTML: num
            }, item);

            domConstruct.create("div", {
                className: "text",
                innerHTML: text
            }, item);
        },

        //ajax请求，获取
        ajaxGet: function(url){
            console.log("url=>", url)
            var self = this;
            var promise = request.get(url,{
                data:{},
                handleAs:"json"
            });
            promise.response.then(function(response){
                var data =  response.data;
                console.log("data=>", data)
                self.build(data);
            },function(error){
                if(self.count > 5){
                    self.renderError();
                    console.log("error",error);
                    return;
                }
                self.count++;
                self.ajaxGet(url);
            });
        },

        renderError: function(){

        }
    })
})