define(function (require, exports, module) {
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var strutil = require('lui/util/str');
    var env = require('lui/util/env');

    var FillingDialog = base.Container.extend({
        initProps : function($super, cfg) {
            $super(cfg);
            this.element = cfg.element;
            this.fillingMapArr = cfg.fillingMapArr;
            this.fillingCfgInfos = cfg.fillingCfgInfos;
            this.flagid = cfg.flagid;
            this.modelId = cfg.modelId;
            this.bindDom = cfg.bindDom;
            this.build();
        },
        startup: function ($super, cfg) {
            $super(cfg);
        },
        build : function() {
            var self = this;
            var url = Com_Parameter.ContextPath + "sys/modeling/main/modelingAppXFormMain.do?method=getFillingRelation";
            self.searchFillingRelation(url,self.bindDom);
            this.element.find(".fillling-left-button-field").eq(0).trigger("click");
        },
        searchFillingRelation : function(url,bindDom){
            var self = this;
            if(bindDom && bindDom.indexOf(".") > -1){
                bindDom = bindDom.replace(/\.(\S+)\./g , '.');
            }
            var flag = this.flagid || "";
            var url = url+"&fdWidgetId="
                + bindDom + "&fdAppModelId=" + self.modelId +"&flagId="+flag;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                cache: false,
                success: function (data) {
                    self.renderLeft(data);
                }
            });
        },
        renderLeft : function(data){
            var self = this;
            for(var i = 0; i < data.length; i++){
                var relationId = data[i].relationId;
                var widgetId = data[i].widgetId;
                fillingRelationMap[relationId] = widgetId;
                var $div = $("<div class='fillling-left-button-field'>");
                $div.append("<div class=\"ele-tabs-ink-bar ele-tabs-ink-bar-animated\" style=\"display: inline-block;float:left;height: 47px;\"></div>");
                $div.attr("id",widgetId );
                $div.attr("relationId",relationId );
                var $span = $("<span title='"+data[i].name+"'>");
                $span.text(data[i].name);
                $div.append($span);
                $div.append("<input type='hidden' name='cfgInfo' value='"+JSON.stringify(data[i].cfgInfo)+"'>");
                $div.on("click", function () {
                    $(".fillling-left-button-field").removeClass("active");
                    $(".ele-tabs-ink-bar").removeClass("active");
                    $(this).addClass("active");
                    $(this).find(".ele-tabs-ink-bar").addClass("active");
                    self.showContent(this);
                });
                this.element.append($div);
            }
        },
        showContent: function(e) {
            var self = this;
            var key = $(e).attr("id");
            var relationId = $(e).attr("relationId");
            var cfgInfo = $(e).closest(".fillling-left-button-field").find("[name='cfgInfo']").val();
            //构建前端搜索排序和列表需要的数据格式：fillingCfg
            var fillingCfg = self.fillingCfgInfos[relationId];
            fillingCfg.cfgInfo = JSON.parse(cfgInfo);
            fillingCfg.relationId = relationId;
            // 画搜索项
            var searchWgt = LUI("dialogSearch");
            var cfg = {
                data:fillingCfg.cfgInfo.search,
                land:{
                    clean:"清空",
                    search:"搜索",
                    expandFilter:"展开过滤",
                    collapseFilter:"收起过滤",
                    choose:"选择",
                    enterWord:"请输入"
                }
            }
            searchWgt.initSourceData(cfg);
            searchWgt.onRefresh();

            // 画排序项
            var sortWgt = LUI("dialogSort");
            var order = '排序'+'：';
            var moreOrder = '更多排序';
            sortWgt.initSourceData({"list":fillingCfg.cfgInfo.sort,"land":{"order":order,"moreOrder":moreOrder}});
            sortWgt.onRefresh();

            //设置搜索项每行的列数
            var searchNumber =  fillingCfg.cfgInfo.searchNumber;
            if(searchNumber){
                setShowCol(searchNumber);
            }else{
                defaultShow("50");
            }

            // 画列表
            var listViewWgt = LUI("listView");
            listViewWgt.init(fillingCfg);
            listViewWgt.fetch();
            // $(".filling-right-content").css("width","80%");
        }
    });

    exports.FillingDialog = FillingDialog;
})