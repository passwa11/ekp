/*树图组件*/
define(function(require, exports, module){
    require("lui/jquery");
    var base = require("lui/base");
    var topic = require("lui/topic");
    var lang = require('lang!sys-ui');
    require("lui/jquery-ui");
    require("sys/ui/resource/css/jquery.treeview.css");
    require("sys/ui/resource/js/jquery.treeview.js");

    var TreeView = base.Component.extend({

        initProps : function($super, cfg) {
            $super(cfg);
            //设置树组件的属性
            this.searchPlaceholder = cfg.searchPlaceholder || '';
            this.titleText = cfg.titleText || '';
            this.ajaxParam = cfg.ajaxParam;
            this.initAllModulesAjaxParam = cfg.initAllModulesAjaxParam;
            this.keyword = '';
            this.isSearch = false;
            //是否初始化所有模块，当该值为TRUE是，为模块树，为FALSE是，展示全部分类节点
            this.initAllModules = cfg.initAllModules || false;
            this.modelName = cfg.modelName;
        },

        startup : function($super){
            if(this.isStartup){
                return;
            }
            //增加一个class做样式
            $(this.element).addClass("lui_treeView_container");
            this.drawSearch();
            this.drawTree();
            this.handleEvent();
            $super();
            this.isStartup = true;
        },

        //draw搜索框
        drawSearch:function(){
            this.searchNode = $("<div class='lui_treeView_search'></div>").appendTo(this.element);
            this.searchInputNode = $("<input type='text' placeholder='"+this.searchPlaceholder+"'/>").appendTo(this.searchNode);
            this.searchRestBtnNode = $("<i></i>").appendTo(this.searchNode);
        },

        //draw树
        drawTree:function(data){
            if(data){
                this.postProcessBeforeDrawTree(data);
            }
            if(this.initAllModules){
                if(data && data.isSearch){
                    if(this.treeViewContent){
                        //清空树图内容容器
                        $(this.treeViewContent,this.element).empty();
                    }else{
                        //创建树图内容容器
                        this.treeViewContent = $("<div class='lui_treeView_content'></div>").appendTo(this.element);
                    }
                    this.rootNode = $("<ul></ul>").appendTo(this.treeViewContent);
                    this.drawChildren("",this.rootNode);
                }else{
                    //draw所有模块节点
                    this.drawModuleRoots();
                }
            }else{
                //draw根节点
                this.drawRoot();
                //draw子节点
                this.drawChildren("",$(this.rootNode).find("ul")[0]);
                //默认设置根节点为活动状态
                this.setActiveStatus("");
            }
        },

        //前置处理，draw树之前
        postProcessBeforeDrawTree:function(data){
            this.isSearch = data.isSearch || false;
            this.keyword = data.keyword || "";
        },

        drawModuleRoots:function(){
            var _self = this;
            var data = _self.initAllModulesAjaxParam.data;
            data.initAllModules = this.initAllModules;
            _self.initAllModulesAjaxParam.data = data;
            $.ajax($.extend(true, {
                dataType: "json",
                success: function(response) {
                    if(response.datas && response.datas.length > 0){
                        if(_self.treeViewContent){
                            //清空树图内容容器
                            $(_self.treeViewContent,_self.element).empty();
                        }else{
                            //创建树图内容容器
                            _self.treeViewContent = $("<div class='lui_treeView_content'></div>").appendTo(_self.element);
                        }
                        _self.rootNode = $("<ul></ul>").appendTo(_self.treeViewContent);
                        _self.doRender(response.datas,_self.rootNode);
                        //默认设置根节点为活动状态
                        var firstModule = response.datas[0];
                        if(firstModule.hasMultiModel == true || firstModule.showTemp == false){
                            //点击继续请求
                            $(_self.rootNode).find(">li:eq(0)>span").click();
                        }else{
                            _self.firstModule = firstModule;
                            _self.setActiveStatus(firstModule.value);
                            //发出事件
                            topic.publish("lui.treeview.node.click", {parentId: firstModule.value, parentText: firstModule.text, parentType: firstModule.nodeType, parentKey:firstModule.key})
                        }
                    }
                }
            }, _self.initAllModulesAjaxParam));
        },

        //draw根节点root
        drawRoot:function(){
            if(this.treeViewContent){
                //清空树图内容容器
                $(this.treeViewContent,this.element).empty();
            }else{
                //创建树图内容容器
                this.treeViewContent = $("<div class='lui_treeView_content'></div>").appendTo(this.element);
            }
            //创建根节点数据：全部流程
            this.rootNodeData = {
                "value":"",
                "text":this.titleText,
                "hasChildren":true,
                "isParent":true,
                "expanded":true
            };
            //在树图上渲染出根节点
            this.rootNode = $("<ul></ul>").appendTo(this.treeViewContent);
            this.doRender([this.rootNodeData],this.rootNode);
        },

        //在父节点下draw子节点
        drawChildren:function(parentId,parentNode){
            var _self = this;
            if(this.ajaxParam && this.ajaxParam.url){
                var data = this.ajaxParam.data || {};
                //data = eval('('+JSON.stringify(this.data)+')');
                //加||可以避免undefined的出现
                if(this.modelName){
                    data.modelName = this.modelName;
                }
                if(this.nodeType){
                    data.nodeType = this.nodeType;
                }
                data.moduleKey = this.moduleKey || "";
                data.parentId = parentId || "";
                data.tSearch = this.isSearch || false;
                data.searchText = this.keyword || "";
                data.hasMultiModel = this.hasMultiModel || "";
                data.key = this.key || "";
                this.ajaxParam.data = data;
                $.ajax($.extend(true, {
                    dataType: "json",
                    success: function(response) {
                        _self.isSearch = false;
                        _self.keyword = "";
                        _self.doRender(response.datas,parentNode);
                        //若请求的是多主文档的情况，则默认选择第一个
                        if(_self.hasMultiModel){
                            var firstNode = response.datas[0];
                            if(firstNode){
                                _self.setActiveStatus(firstNode.value, parentNode);
                                //发出事件
                                topic.publish("lui.treeview.node.click", {parentId: firstNode.value, parentText: firstNode.text, parentType: firstNode.nodeType, parentKey:firstNode.key});
                            }
                        }
                    }
                }, _self.ajaxParam));
            }
        },

        //执行渲染，draw调用
        doRender:function(response,parentNode){
            var _self = this;
            var childs = this.createTreeNode(response,parentNode);
            if(childs.length > 0){
                var options = {add:childs};
                options.collapsed = false;
                options.toggle = function(event) {
                    var $this = $(this);
                    if(!$this.attr("data-id")){
                        return;//点击全部流程不进行加载
                    }
                    _self.nodeToggle($this);
                };
                var treeViewObj = $(parentNode).treeview(options);
                $(parentNode).find(":has(>ul):not(:has(>a))").find(">span").unbind("click.treeview");
                $(parentNode).find(":not(:has(>ul)):not(:has(>a))").find(">span").add( $("a",treeViewObj) ).hoverClass()
            }
        },

        //创建树节点
        createTreeNode:function(nodeDatas,parentNode){
            var _self = this;
            var childs = [];
            $.each(nodeDatas, function(index,nodeData) {
                if(nodeData.isParent) {
                    $(parentNode).show();
                    var modelName = "";
                    if(_self.initAllModules == true){
                        modelName = nodeData.modelName || _self.modelName || nodeData.value;
                    }else{
                        modelName = _self.modelName;
                    }
                    var showTemp = nodeData.showTemp == true ? true : (nodeData.showTemp == false) ? false : "";
                    var hasMultiModel = nodeData.hasMultiModel == true ? true : (nodeData.hasMultiModel == false) ? false : "";
                    var current = $("<li/>").attr("data-modelName", modelName).attr("data-nodeType",nodeData.nodeType || "")
                        .attr("data-id", nodeData.value || "").attr("data-text", nodeData.text || "").attr("data-hasMultiModel", hasMultiModel)
                        .attr("data-key", nodeData.key || "").attr("data-moduleKey", nodeData.moduleKey || "").attr("data-showTemp", showTemp)
                        .append($("<span></span>").text(nodeData.text)).appendTo(parentNode);
                    current.children("span:eq(0)").click(function () {
                        if(_self.lastClickSpanNode){
                            $(_self.lastClickSpanNode).removeClass("active");
                        }
                        _self.lastClickSpanNode = this;
                        var $parent = $(this).parents("li:eq(0)");
                        var hasMultiModel = $parent.attr("data-hasMultiModel");
                        var showTemp = $parent.attr("data-showTemp");
                        if(hasMultiModel == "true" || showTemp == "false"){//点击展开二级，而不处理数据
                            //jQuery($parent)[ jQuery($parent).is(":hidden") ? "show" : "hide" ]();
                            //_self.nodeToggle($parent);
                            $parent.closest("li").find("div.hitarea").click();
                        }else{
                            $(this).addClass("active");
                            var treeNodeId = $parent.attr("data-id");
                            var treeNodeText = $parent.attr("data-text");
                            var treeNodeType = $parent.attr("data-nodeType");
                            var modelName = $parent.attr("data-modelName");
                            var key = $parent.attr("data-key");
                            topic.publish("lui.treeview.node.click", {parentId: treeNodeId, parentText: treeNodeText, parentType: treeNodeType, parentModelName: modelName, parentKey: key})
                        }
                    })
                    if (nodeData.classes) {
                        current.children("span").addClass(nodeData.classes);
                    }
                    if (nodeData.expanded || this.isSearch == true) {
                        current.addClass("open");
                    }
                    if (nodeData.hasChildren || (nodeData.childrens && nodeData.childrens.length)) {
                        var branch = $("<ul/>").appendTo(current);
                        if (nodeData.hasChildren) {
                            current.addClass("hasChildren");
                        }
                        if (nodeData.childrens && nodeData.childrens.length) {
                            var childsTemp = _self.createTreeNode(nodeData.childrens, branch);
                            for (var i in childsTemp) {
                                childs.push(childsTemp[i]);
                            }
                        } else {
                            branch.hide();
                        }
                    }
                    childs.push(current);
                }
            });
            return childs;
        },

        //搜索树
        doSearch:function(keyword){
            var data = {};
            if(keyword){
                data.isSearch = true;
            }else{
                data.isSearch = false;
            }
            data.keyword = keyword;
            this.drawTree(data);
        },

        nodeToggle:function($this){
            var _self = this;
            if ($this.hasClass("hasChildren")) {
                var chilList = $this.removeClass("hasChildren").find("ul");
                if(chilList.find("li").length == 0){
                    var parentId = $this.attr("data-id");
                    var parentType = $this.attr("data-nodeType");
                    var modelName = $this.attr("data-modelName");
                    _self.nodeType = $this.attr("data-nodeType");
                    _self.moduleKey = $this.attr("data-moduleKey");
                    _self.key = $this.attr("data-key");
                    if(parentType == "module"){
                        var hasMultiModel = $this.attr("data-hasMultiModel");
                        if(hasMultiModel == "true"){//说明该模块存在不同的流程module，二级要显示这些数据，并且，一级点击要加载这些数据
                            _self.hasMultiModel = hasMultiModel;
                        }else{
                            _self.hasMultiModel = "";
                        }
                        _self.modelName = parentId;
                        _self.drawChildren("",chilList[0]);//继续加载数据
                    }else{
                        _self.modelName = modelName;
                        _self.drawChildren(parentId,chilList[0]);//继续加载数据
                    }
                }
            }
        },

        //事件处理
        handleEvent:function(){
            var _self = this;
            //搜索框回车事件
            $(this.searchInputNode).keyup(function(event){
                if(event.keyCode == 13){//回车
                    var keyword = $(this).val();
                    _self.doSearch(keyword);
                }
            })
            //搜索框聚焦事件
            // $(this.searchInputNode).focus(function(event){
            //     if($(this).val()){
            //         $(_self.searchRestBtnNode).show();
            //     }
            // })
            $(this.searchInputNode).bind('input propertychange', function(){
                if($(this).val()){
                    $(_self.searchRestBtnNode).show();
                }else{
                    $(_self.searchRestBtnNode).hide();
                }
            })
            //隐藏重置按钮事件
            // $(document.body).click(function(event){
            //     var target = event.target;
            //     if(target == _self.searchInputNode[0] || target == _self.searchRestBtnNode[0]){
            //         return;
            //     }else{
            //         $(_self.searchRestBtnNode).hide();
            //     }
            // });
            //重置按钮点击事件
            $(this.searchRestBtnNode).click(function(event){
                $(_self.searchInputNode).val("");
                //清空后自动触发搜索
                _self.doSearch("");
                $(_self.searchInputNode).focus();
                $(this).hide();
            });

            topic.subscribe("lui.content.reload",function(data){
                if(_self.lastClickSpanNode == null && data.doLast == true){
                    //发出事件
                    var firstModule = _self.firstModule;
                    _self.setActiveStatus(firstModule.value);
                    topic.publish("lui.treeview.node.click", {parentId: firstModule.value, parentText: firstModule.text, parentType: firstModule.nodeType, parentKey:firstModule.key})
                }else{
                    var $node = $(_self.lastClickSpanNode).closest("li");
                    var modelName = $node.attr("data-modelName");
                    var hasMultiModel = $node.attr("data-hasMultiModel");
                    var showTemp = $node.attr("data-showTemp");
                    if(data && (data.modelName == modelName || data.doLast == true) && (hasMultiModel != "true" || showTemp != "false")){
                        var treeNodeId = $node.attr("data-id");
                        var treeNodeText = $node.attr("data-text");
                        var treeNodeType = $node.attr("data-nodeType");
                        var key = $node.attr("data-key");
                        _self.setActiveStatus(treeNodeId);
                        topic.publish("lui.treeview.node.click", {parentId: treeNodeId, parentText: treeNodeText, parentType: treeNodeType, parentModelName: modelName, parentKey: key})
                    }
                }
            });
        },

        //设置活动节点的状态：目的是修改样式效果
        setActiveStatus:function(nodeId, parentNode){
            if(this.lastClickSpanNode){
                $(this.lastClickSpanNode).removeClass("active");
            }
            $("li[data-id='"+nodeId+"']",parentNode || this.rootNode).find(">span").eq(0).addClass("active");
            this.lastClickSpanNode = $("li[data-id='"+nodeId+"']",this.rootNode).find(">span")[0];
        }
    });

    exports.TreeView = TreeView;
})