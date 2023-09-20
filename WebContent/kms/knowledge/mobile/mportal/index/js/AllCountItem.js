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
    "mui/i18n/i18n!kms-knowledge:kmsKnowledge.portlet.allCount",
    ], function (declare, domConstruct, domClass, domStyle, lang, _WidgetBase, _Container, _Contained, request, util, msg) {
        return declare("kms.knowledge.indexAllCount", [_WidgetBase, _Container, _Contained], {
            count: 1,

            url: "/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getKnowledgeIndexCountData",

            type: "",

            // 知识总数
            totalNum : 0,

            // 文档总数
            docNum: 0,

            // 维基总数
            wikiNum : 0,

            buildRendering : function() {
                this.inherited(arguments);

                this.ajaxGet(util.formatUrl(this.url + "&type=" + this.type, true));
            },

            //构建内容
            build: function (data) {
                if(data){
                   this.totalNum = data.totalCount;
                   this.docNum = data.docCount;
                   this.wikiNum = data.wikiCount;
                }

                var box = domConstruct.create("div", {
                    id: "kms_knowledge_all_count_box"
                }, this.domNode);

                var ul = domConstruct.create("ul", {}, box);

                // 知识总数
                this.buildNode(ul, "total", msg['kmsKnowledge.portlet.allCount.total'], this.totalNum);
                // 文档总数
                this.buildNode(ul, "doc", msg['kmsKnowledge.portlet.allCount.doc'], this.docNum);
                // 维基总数
                this.buildNode(ul, "wiki", msg['kmsKnowledge.portlet.allCount.wiki'], this.wikiNum);
            },

            buildNode: function (container, itemClass, itemTitle, num) {
                var wrap = domConstruct.create("li", {
                    className: itemClass
                }, container);
                var item = domConstruct.create("div", {
                    className: "item"
                }, wrap);

                domConstruct.create("div", {
                    className: "num",
                    innerHTML: num
                }, item);
                domConstruct.create("div", {
                    className: "text",
                    innerHTML: itemTitle
                }, item);
                domConstruct.create("div", {
                    className: "icon"
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
        });
    }
)