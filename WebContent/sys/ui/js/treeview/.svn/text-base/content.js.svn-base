/*内容组件，和树组件交互，树点击后产生的效果*/
define(function(require,exports,module){
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require("lui/topic");
    var lang = require('lang!sys-ui');
    var lang1 = require('lang!sys-lbpmperson');

    var Content = base.Component.extend({

        initProps: function ($super, cfg) {
            this.linkUrl = cfg.linkUrl || '';//内容跳转链接
            this.ajaxParam = cfg.ajaxParam;//请求的内容
            this.total = 0; //累计请求数据条数
            this.totalRows = 0; //总数
            this.isLoadMore = true; //是否还需要加载
            this.pageno = 1; // 页码
            this.parentId = ''; //父ID
            this.parentText = ''; //父文本
            this.parentKey = '';//父key
            this.isSearch = false; //是否是搜索
            this.keyword = ''; // 搜索关键词
            this.groupNo = 1; // 分组编号，用于标题背景颜色的分组轮询
            this.titleText = cfg.titleText || '';
            this.modelName = cfg.modelName || '';
            this.initAllModules = cfg.initAllModules || false;
            this.expand = cfg.expand || false;//是否支持内容收起功能，默认为FALSE
            this.showSummary = cfg.showSummary || false;//是否按摘要的模式显示，默认为FALSE
            this.button = cfg.button || {};
            this.loadLevel = cfg.loadLevel;
            $super(cfg);
        },

        startup: function ($super) {
            if (this.isStartup) {
                return;
            }
            $(this.element).addClass("lui_treeview_list_container");
            this.handleEvent();
            $super();
            this.isStartup = true;
        },

        //加载数据
        loadData:function(){
            if(this.initAllModules){
                return false;
            }
            //正在加载或者不需要加载更多的时候直接结束加载
            if(this.isLoading == true || this.rendering == true || this.isLoadMore == false){
                return false;
            }
            var _self = this;
            //加载开始
            this.isLoading = true;
            //构建参数
            var ajaxParam = {};
            for(var key in this.ajaxParam){
                if(key == "data"){
                    var data = {};
                    for(var key1 in this.ajaxParam.data){
                        data[key1] = this.ajaxParam.data[key1];
                    }
                    ajaxParam[key] = data;
                }else{
                    ajaxParam[key] = this.ajaxParam[key];
                }
            }
            var data = ajaxParam.data || {};
            if(!this.isSearch && (!_self.expand || !_self.showSummary)){
                data.pageno = this.pageno;
                data.rowsize = data.rowsize || 10;
            }else{
                delete data.pageno;
                delete data.rowsize;
            }
            if(this.modelName){
                data.modelName = this.modelName;
            }
            if(this.isSearch){
                data.qSearch = this.isSearch;
                data.searchText = this.keyword;
            }
            data.parentId = this.parentId || '';
            data.moduleText = this.parentText || "";
            data.key = this.parentKey || '';
            data.loadLevel = this.loadLevel || 3;//该组件默认为3级（0-2），0是本身，如果需要调整级别，需要更具后续的UI优化+算法调整，和doRenderParents对应上
            ajaxParam.data = data;
            $.ajax($.extend(true, {
                dataType: "json",
                success: function(response) {
                    if(!_self.isSearch && (!_self.expand || !_self.showSummary)){
                        //总数
                        _self.totalRows = response.totalRows || 0;
                        _self.total += _self.ajaxParam.data.rowsize || response.datas.length || 0;
                        if(_self.total >= _self.totalRows){
                            //当请求数达到总数后不再请求
                            _self.isLoadMore = false;
                            _self.loadMoreNode.hide();
                        }else{
                            //请求成功后并且还有下一页，自动加上一页
                            _self.pageno += 1;
                        }

                    }else{
                        //如果是搜索则一次性加载，就不会继续加载
                        _self.isLoadMore = false;
                        _self.loadMoreNode.hide();
                    }
                    //搜索情况，非搜索情况的首次请求都可能为空
                    if(_self.isSearch || response.datas.length > 0 || (_self.pageno == 1 && response.datas.length == 0)){
                        _self.doRender(response.datas);
                    }
                },
                error: function(a, b){

                },
                complete:function(){
                    //加载结束
                    _self.isLoading = false;
                }
            }, ajaxParam));
            return true;
        },

        //渲染
        draw:function($super){
            this.contentNode = $("<div class='lui_list_content'/>").appendTo(this.element);
            this.loadMoreNode = $("<div class='lui_list_content_more'></div>").appendTo(this.element);
            this.loadMoreNode.html("<i></i>"+lang['address.loading']);
            var isLoad = this.loadData();
            if(!isLoad){
                this.loadMoreNode.hide();
                this.doRenderEmpty();
            }

            $super();
            var _self = this;
            if(!this.isSearch && (!_self.expand || !_self.showSummary)){
                //自动加载，为了保证初始化的时候内容的高度能超过容器，这样才能实现滚动加载
                var num = window.setInterval(function(){
                    //获取渲染后的高度
                    var containerHeight = $(_self.element).height();//容器高度
                    var contentHeight = $(_self.element).find(".lui_list_content").height();//内容高度
                    if(_self.isLoadMore == false || containerHeight == null || contentHeight == null || (contentHeight/2) >= containerHeight){
                        window.clearInterval(num);
                        return;
                    }
                    //当前没有在加载也没有渲染
                    if(_self.isLoading == false && _self.rendering == false){
                        _self.loadData();
                    }
                },100);
            }
        },

        //渲染
        doRender:function(datas){
            this.rendering = true;
            if(datas && datas.length > 0){
                if(this.isSearch){
                    //搜索情况下的渲染
                    this.doRenderSearch(datas);
                }else{
                    if(!this.parentId){
                        //渲染所有
                        this.doRenderAll(datas);
                    }else{
                        //渲染部分
                        this.doRenderItem(datas,{"text":this.parentText});
                    }
                }
            }else{
                //无数据的情况渲染
                this.doRenderEmpty();
            }
            topic.publish("lui.treeview.content.render.finish",this);
            this.rendering = false;
        },

        //事件处理
        handleEvent:function(){
            var _self = this;
            //滚动加载事件
            if(!_self.expand && !_self.showSummary){
                $(this.element).scroll(function(){
                    var scrollTop = $(this).scrollTop();
                    var maxHeight = $(this).height();
                    var height = $(_self.contentNode).height();
                    var difH = height - maxHeight;
                    if(difH > 0 && (scrollTop * 2 > difH) && !_self.isSearch){
                        _self.loadData();
                    }
                });
            }
            //监听树节点点击事件，进行对应的数据加载
            topic.subscribe("lui.treeview.node.click",function(data){
                _self.initAllModules = false;
                _self.reload(data);
            })
        },

        //重新加载
        reload:function(data){
            //重置属性
            this.total=0;
            this.totalRows=0;
            this.isLoadMore=true;
            this.pageno=1;
            if(data.parentType == "module"){
                this.modelName = data.parentId;
                this.parentId = '';
            }else{
                this.modelName = data.parentModelName;
                this.parentId = data.parentId || '';
            }
            this.parentText = data.parentText || '';
            this.parentKey = data.parentKey || "";
            this.keyword = data.keyword || '';
            this.isSearch = data.isSearch || false;
            this.groupNo = 1;

            this.refresh();
        },

        //搜索
        search:function(param){
            var data = {};
            if(typeof param == "object"){
                data.parentModelName = param.modelName;
                data.keyword = param.keyword;
            }else{
                data.keyword = param;
            }
            if(data.keyword){
                data.isSearch = true;
            }else{
                data.isSearch = false;
            }
            this.reload(data);
        },

        //渲染搜索的结果
        doRenderSearch:function(datas){
            var html = "<div class='lui_content_item' dataGroup='1'>";
            html += "<div class='lui_content_item_header'>";
            var className = this.showSummary ? 'lui_summary_dataview_content ' : '';
            if(this.expand){
                className += " expand ";
                html += "<div class='lui_content_item_header_title expand'><i class='icon_expand_up'></i><span></span></div>";
            }else{
                html += "<div class='lui_content_item_header_title expand'><span></span></div>";
            }
            html += "</div>";
            html += "<div class='"+ className +"lui_content_item_detail'>";
            html += this.doRenderChilds(datas);
            html += "</div>";
            var $dom = $(html);
            $dom.find("div.lui_content_item_header_title").attr("title",this.titleText);
            $dom.find("div.lui_content_item_header_title>span").text(this.titleText);
            $dom.find("div.lui_content_item_header_title>i").unbind("click").bind("click",function(){
                $dom.find("div.lui_content_item_detail").toggle();
            });
            $dom.find("li.lui_detail_data_item").unbind("click").bind("click",function(){
                Com_OpenNewWindow(this);
            });
            $dom.find("li.lui_summary_item").mouseenter(function(){
                $(".lui_summary_item_btn",this).show();
            });
            $dom.find("li.lui_summary_item").mouseleave(function(){
                $(".lui_summary_item_btn",this).hide();
            });
            $dom.find("li.lui_summary_item .lui_summary_item_btn").unbind("click").bind("click",{type:"content"},this.button.click);
            $dom.appendTo(this.contentNode);
        },

        //渲染一个
        doRenderItem:function(datas,header,groupNo){
            var parents = [];
            var childrens = [];
            for(var index in datas){
                var data = datas[index];
                if(data.isParent){
                    parents.push(data);
                }else{
                    childrens.push(data);
                }
            }
            groupNo = groupNo || this.groupNo;
            var html = "<div class='lui_content_item' dataGroup='"+groupNo+"'>";
            html += "<div class='lui_content_item_header'>";
            var className = this.showSummary ? 'lui_summary_dataview_content ' : '';
            if(this.expand){
                className += " expand ";
                html += "<div class='lui_content_item_header_title expand'><i className='icon_expand_up'></i><span></span></div>";
            }else{
                html += "<div class='lui_content_item_header_title'><span></span></div>";
            }
            html += "</div>";
            html += "<div class='"+className+"lui_content_item_detail'>";
            html += this.doRenderChilds(childrens);
            html += this.doRenderParents(parents,header.text);
            html += "</div>";

            var $dom = $(html);
            $dom.find("div.lui_content_item_header_title").attr("title",header.text);
            $dom.find("div.lui_content_item_header_title>span").text(header.text);
            $dom.find("div.lui_content_item_header_title").unbind("click").bind("click",function(){
                $dom.find("div.lui_content_item_detail").toggle();
                $dom.find("div.lui_content_item_header_title>i").toggleClass("hidden");
            });
            $dom.find("div.lui_content_item_detail .lui_detail_title").unbind("click").bind("click",function(){
                $(this).closest("div.lui_item_detail").find("ul.lui_detail_data").toggle();
                $(this).find(">i").toggleClass("hidden");
            });
            $dom.find("li.lui_detail_data_item").unbind("click").bind("click",function(){
                Com_OpenNewWindow(this);
            });
            $dom.find("li.lui_summary_item").mouseenter(function(){
                $(".lui_summary_item_btn",this).show();
            });
            $dom.find("li.lui_summary_item").mouseleave(function(){
                $(".lui_summary_item_btn",this).hide();
            });
            $dom.find("li.lui_summary_item .lui_summary_item_btn").unbind("click").bind("click",{type:"content"},this.button.click);
            $dom.appendTo(this.contentNode);
        },

        //渲染所有
        doRenderAll:function(datas){
            for(var i in datas){
                var data = datas[i];
                this.doRenderItem(data.childrens, {text:data.text});
                if(this.groupNo >= 5){
                    this.groupNo = 1;
                }else{
                    this.groupNo++;
                }
            }
        },

        //渲染空页
        doRenderEmpty:function(){
            var html = "<div class='lui_list_empty'></div>";
            html += "<div class='lui_list_empty_text'>"+lang['treeview.data.empty']+"</div>";
            $(this.contentNode).append(html);
        },

        //渲染内容（理解为三级模板）
        doRenderChilds:function(datas){
            if(!datas || datas.length == 0){
                return "";
            }
            var html = "";
            var className = this.showSummary ? 'lui_summary_list ' : '';
            html += "<div class='"+className+"lui_item_detail'>";
            html += "<ul class='lui_detail_data'>";
            for(var i in datas){
                var data = datas[i];
                var linkUrl = this.linkUrl.replace(new RegExp("!{id}","gm"),data.value);
                if(linkUrl.indexOf("/") == 0){
                    linkUrl = Com_Parameter.ContextPath + linkUrl.substring(1);
                }
                if(this.showSummary){
                    var item = {};
                    item.value = data.value;
                    item.icon = data.fdIcon;
                    item.text = data.text;
                    item.path = data.path;
                    item.addUrl = data.addUrl;
                    item.isFavorite = data.isFavorite;
                    item.modelName = data.modelName;
                    html += this.getDetailItemHtml(item);
                }else{
                    var $liNode = $("<li class='lui_detail_data_item'></li>").attr("data-href",linkUrl);
                    $liNode.append("<i></i>").append($("<span></span>").text(data.text).attr("title",data.text));
                    html += $liNode[0].outerHTML;
                }
            }
            html += "</ul>";
            html += "</div>";
            return html;
        },

        //渲染内容（理解为二级分类）
        doRenderParents:function(datas,path){
            if(!datas || datas.length == 0){
                return "";
            }
            var html = "";
            for(var i in datas){
                var data = datas[i];
                var classNmae = this.showSummary ? 'lui_summary_list ' : '';
                html += "<div class='"+classNmae+"lui_item_detail'>";
                var $divNode;
                if(this.expand){
                    $divNode = $("<div class='lui_detail_title expand'><i className='icon_expand_up'></i><span></span></div>").attr("title",data.text);
                }else{
                    $divNode = $("<div class='lui_detail_title'><span></span></div>").attr("title",data.text);
                }
                $divNode.find(">span").text(data.text);
                html += $divNode[0].outerHTML;
                if(data.childrens){
                    html += "<ul class='lui_detail_data'>";
                    //该组件目前只支持3级，如果多级别支持，请后续根据UI稿+算法调整实现
                    for(var j in data.childrens){
                        var child = data.childrens[j];
                        if(child.isParent){
                            continue;
                        }
                        var linkUrl = this.linkUrl.replace(new RegExp("!{id}","gm"),child.value);
                        if(linkUrl.indexOf("/") == 0){
                            linkUrl = Com_Parameter.ContextPath + linkUrl.substring(1);
                        }
                        if(this.showSummary){
                            var item = {};
                            item.value = child.value;
                            item.icon = child.fdIcon;
                            item.text = child.text;
                            item.path = child.path;
                            item.addUrl = child.addUrl;
                            item.modelName = child.modelName;
                            item.isFavorite = child.isFavorite;
                            html += this.getDetailItemHtml(item);
                        }else{
                            var $liNode = $("<li class='lui_detail_data_item'></li>").attr("data-href",linkUrl);
                            $liNode.append("<i></i>").append($("<span></span>").attr("title",child.text).text(child.text));
                            html += $liNode[0].outerHTML;
                        }
                    }
                    html += "</ul>";
                }
                html += "</div>";
            }

            return html;
        },

        getDetailItemHtml:function(item){
            item.text = $("<div></div>").text(item.text).html();
            item.path = $("<div></div>").text(item.path).html();
            var btnCustomClass = (item.isFavorite == "true" ? "active" : "");
            var html = "";
            html += '<li class="lui_summary_item" data-item-fdId="'+item.value+'" data-item-fdName="'+item.text+'" data-item-modelName="'+ ((this.modelName != null && this.modelName != undefined )? this.modelName: item.modelName) +'" data-href="'+Com_Parameter.ContextPath+item.addUrl+'" onclick="Com_OpenNewWindow(this)">' +
                '<div class="lui_summary_item_icon">';
            if(item.icon){
                if(item.icon.indexOf('/') == -1){
                    html += "<i class='iconfont_nav lux_personal_stat_rt_1 "+item.icon+" item_icon'></i>";
                }else{
                    if(item.icon.indexOf(Com_Parameter.ContextPath)==0 && Com_Parameter.ContextPath != "/"){
                        html += '<img src="'+item.icon+'" class=""></img>';
                    }else{
                        if(Com_Parameter.ContextPath == "/"){
                            html += '<img src="'+item.icon+'" class=""></img>';
                        }else{
                            html += '<img src="'+Com_Parameter.ContextPath+item.icon.substring(1)+'" class=""></img>';
                        }
                    }
                }
            }else{
                html += '<img class="" src="'+Com_Parameter.ContextPath +'sys/ui/resource/images/icon_office.png">';
            }
            html += '</div>' +
                '<div class="lui_summary_item_text">' +
                '<div class="lui_summary_item_title" title="'+item.text+'">'+item.text+'</div>' +
                '<div class="lui_summary_item_parent" title="'+item.path+'">'+item.path+'</div>' +
                '</div>' +
                '<div class="lui_summary_item_btn '+btnCustomClass+'">' +
                '<i title="'+lang1["ui.recently.add"]+'" class="lui_summary_item_btn_custom '+this.button.className+'"></i>' +
                '</div>' +
                '</li>';
            return html;
        }
    });

    exports.Content = Content;
})