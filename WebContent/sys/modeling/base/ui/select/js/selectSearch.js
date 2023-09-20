/**
 * 下拉选择带搜索
 */
define(function(require, exports, module){
    let $ = require("lui/jquery");
    let base = require("lui/base");
    let topic = require("lui/topic");

    let SelectSearch;
    SelectSearch = base.Container.extend({

        keyword: null,
        data: [],
        config:{},
        url :"sys/modeling/base/modeling.do?method=getAllApplicationInfos",
        currentAppId:null,
        $element : null,
        $placeholder : null,
        $keyword : null,
        $content : null,
        $result :null,
        channel : null,
        /********************************************************************
         * 功能：初始化
         ********************************************************************/
        initProps: function ($super, _config) {
            $super(_config);
            this.config = _config;
            this.url = this.config.url || this.url;
            this.currentAppId = this.config.appId || this.currentAppId;
            this.channel = this.config.channel || this.channel;
        },
        startup : function($super,cfg){
            $super(cfg);
            this.render();
            this.$element = this.config.viewContainer.find(".select_view");
            this.$placeholder = this.config.viewContainer.find(".select_placeholder_view");
            this.$keyword = this.config.viewContainer.find(".select_keyword");
            this.$content = this.config.viewContainer.find(".select_content_view");
            this.$result = this.config.viewContainer.find(".select_result_view");
            this.load();
            this.renderDetail();
            this.setDefaultChecked();
            this.bindEvent();
        },
        bindEvent: function () {
            let self = this;
            self.$element.on("click",function (e) {
                e.stopPropagation();
            });
            self.$placeholder.on("click",function (e) {
                self.$keyword.val("");
                if(self.$content.is(":hidden")){
                    self.renderDetail();
                    self.showContent();
                }else{
                    self.hideContent();
                }
                $('.select_result_view').scrollTop(0);
            });
            self.$result.on("click","li",function(e){
                if($(this).data("id")){
                    topic.channel(self.channel).publish("app_select",$(this).data("id"));
                    self.$placeholder.find('.select_placeholder_text').text($(this).html())
                    self.hideContent();
                    self.currentAppId = $(this).data("id");
                }
            }).on("mouseover", "li", function() {
                self.$result.find("li").removeClass("status_hover");
                $(this).addClass("status_hover");
            });
            self.$keyword.on("keydown",function(e){
                if(e.keyCode==13){
                    self.keyword = $(this).val();
                    self.search();
                }
            });
            $(document).on("click",function(e){
                self.$keyword.val("");
                self.hideContent();
            })
        },
        showContent: function () {
            this.$content.show();
            this.$placeholder.addClass("active");
        },

        hideContent: function () {
            this.$keyword.val("");
            this.$content.hide();
            this.$placeholder.removeClass("active");
        },
        search : function () {
            let self = this;
            if(self.keyword){
                let items = [];
                $.each(self.data, function(i, n) {
                    if(n.text.toUpperCase().indexOf(self.keyword.toUpperCase()) > -1) {
                        items.push('<li data-id="' + n.value + '">' + n.text + '</li>');
                    }
                });
                if(items.length < 1) {
                    items.push('<li class="search_nodata">无数据</li>');
                }
                this.renderItems(items);
            }else{
                this.renderDetail();
            }
            $('.select_result_view').scrollTop(0);
        },
        render : function(){
            let $element = $("<div class='select_view'/>");
            let $placeholder = $("<div class='select_placeholder_view'/>");
            let $placeholderText = $("<div class='select_placeholder_text'/>");
            $placeholderText.append("应用名称");
            $placeholder.append($placeholderText);
            $placeholder.append($("<div class='select_placeholder_arrowhead'/>"))
            $placeholder.appendTo($element);
            let $content = $("<div class='select_content_view'/>");
            let $search = $("<div class='select_search_view'/>");
            let $keyword = $("<input class='select_keyword' name='keyword' placeholder='搜索'/>");
            $keyword.appendTo($search);
            $search.appendTo($content);
            let $result = $("<ul class='select_result_view'/>");
            $result.appendTo($content);
            $content.hide();
            $content.appendTo($element);
            this.config.viewContainer.append($element);
        },

        renderDetail : function(){
            let items = [];
            let self = this;
            $.each(self.data, function(i, n) {
                items.push('<li data-id="' + n.value + '">' + n.text + '</li>');
            });
            if(items.length < 1) {
                items.push('<li class="search_nodata">无数据</li>');
            }
            this.renderItems(items);
        },
        renderItems : function(items){
            this.$result.html("");
            this.$result.append(items.join(""));
        },

        load : function(){
            let self = this;
            self.url = Com_Parameter.ContextPath+self.url;
            $.ajax({
                url: self.url,
                type: "get",
                async: false,
                success: function (data, status) {
                    if (data) {
                        let dataJson = JSON.parse(data);
                        self.data=dataJson.data;
                    }
                }
            });
        },

        setDefaultChecked : function(){
            let self = this;
            $.each(self.data, function(i, n) {
                if(self.currentAppId == n.value){
                    self.$placeholder.find('.select_placeholder_text').text(n.text);
                    return;
                }
            });
        }

    });

    exports.SelectSearch = SelectSearch;
});