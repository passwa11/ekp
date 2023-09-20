define("sys/modeling/main/resources/js/mobile/formview/DynamicContent", [
    "dojo/_base/declare",
    "mui/pageLoading",
    "dijit/registry",
    "dojo/dom-style",
    "dojo/dom-construct",
    'dojo/_base/lang',
    "dijit/_WidgetBase",
    "dijit/_Contained",
    "dijit/_Container",
    "dojo/topic",
    "mui/util",
    "dojo/query",
    "dojo/request",
    "dojo/Deferred",
    "dojo/promise/all",
    "dojo/parser",
], function (declare, pageLoading, registry, domStyle, domConstruct, lang, WidgetBase, Contained, Container, topic, util, query, request, Deferred, all, parser) {
    return declare("mui.panel.DynamicContent", null, {

        buildRendering :function(){
            this.inherited(arguments);
            this.otherContentWrap = domConstruct.create(
                "div",
                {id: "otherContentWrap" },
                this.domNode
            );
        },

        startup : function() {
            this.inherited(arguments);
            // 监听页签选中事件
            topic.subscribe("/mui/navitem/_selected", lang.hitch(this,'handleItemSelected'));
        },

        handleItemSelected : function(evt){
            // var  _otherContentIframe = document.getElementById("_otherContentIframe");
            // if (_otherContentIframe && evt.url) {
            //     _otherContentIframe.src = evt.url;
            // }
            //  var context = document.getElementById("otherContentWrap");
            var context =  this.otherContentWrap;
            if(evt && evt.url){
                if(evt.fdType && '2' == evt.fdType){
                    if(this.iframeUrl != evt.url){
                        this.iframeUrl = evt.url;
                        domConstruct.empty(context);
                        var _otherContentIframe = domConstruct.create("iframe", {classname: ""},context);
                        if (_otherContentIframe && evt.url) {
                            _otherContentIframe.src = util.formatUrl(evt.url);
                            domStyle.set(_otherContentIframe,"min-height", "40rem")
                            domStyle.set(_otherContentIframe,"width", "100%")
                            domStyle.set(_otherContentIframe,"border", "0")
                        }
                    }
                }else{
                    //#170657 移动端页签切换后，需重置iframeUrl值，以防切换为其他页签，
                    // 再切换回原来页签时，上面的运行逻辑（'2' == evt.fdType）中的页面内容没有变换
                    if(evt.moveTo === "_otherContentView"){
                        this.iframeUrl = "";
                    }
                    var parentWgt = evt.getParent();
                    if (parentWgt && parentWgt.id == "modelingTab") {
                        var _widgets = registry.findWidgets(this.domNode, this.domNode);
                        if (_widgets) {
                            _widgets.forEach(function (childWgt) {
                                childWgt.destroy();
                            });
                        }
                        this.loadAndParse(evt.url, context);
                    }
                }
            }
        },

        //设置innerHTML并运行html中的js
        setInnerHTML: function(dom, html) {
            var allDeferred = new Deferred();
            dom.innerHTML = html;
            var scripts = dom.getElementsByTagName("script");
            var promises = [];
            for (var i = 0; i < scripts.length; i++) {
                evalScript(scripts[i]);
            }

            if (promises.length == 0) {
                allDeferred.resolve();
            } else {
                all(promises).then(function() {
                    allDeferred.resolve();
                });
            }

            //执行js
            function evalScript(elem) {
                var deferred = new Deferred();
                var data = elem.text || elem.textContent || elem.innerHTML || "",
                    src = elem.src,
                    head = document.getElementsByTagName("head")[0],
                    script = document.createElement("script");
                script.type = "text/javascript";
                if (src) {
                    script.src = src;
                    promises.push(deferred.promise);
                    script.onload = function() {
                        deferred.resolve();
                    };
                    head.appendChild(script);
                } else {
                    allDeferred.then(function() {
                        script.appendChild(document.createTextNode(data));
                        head.appendChild(script);
                    });
                }
            }

            return allDeferred;
        },
        loadAndParse: function (url, dynamicContent) {
            dojoConfig._native = true;
            dojoConfig.tiny = true;
            dojoConfig.canHash = true;
            try {
                var url = util.formatUrl(url);
                url = util.setUrlParameter(url, "include", "true");
                var self = this;
                // 设置数据请求参数
                var errorData = "";
                request
                    .get(url)
                    .then(
                        function (data) {

                            var html = data;
                            self.setInnerHTML(dynamicContent, html).then(
                                lang.hitch(self, function () {
                                    parser.parse({rootNode: dynamicContent }).then(function () {
                                        pageLoading.hide();
                                        topic.publish("parser/done");
                                    });
                                })
                            );
                        },
                        lang.hitch(this, function (res) {
                            var status = res.response.status;
                            if (this.errorCode[status]) {
                                //location.href = util.formatUrl(this.errorCode[status]);
                                // 请求403页面
                                var errorJsp = util.formatUrl(this.errorCode[status]);
                                var promise = request.post(errorJsp,{});
                                promise.response.then(function(response){
                                    errorData =  response.data;
                                    document.write(errorData);
                                });
                            }
                        })
                    );
            } catch (e) {}
        }
    });
});