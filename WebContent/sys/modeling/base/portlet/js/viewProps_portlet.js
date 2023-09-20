/**
 *
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var viewProps = require("sys/modeling/base/relation/relation/js/props/viewProps");
    var ViewPropsPortlet = viewProps.ViewProps.extend({
        viewSet: {
            "type": "1",
            "modelId": "1"
        },
        set: function (d) {
            if (typeof d == "string") {
                try {
                    this.config.data = JSON.parse(d);
                } catch (e) {
                    this.config.data = {};
                }
            }
            if (typeof d == "object") {
                this.config.data = d;
            }
        },
        /*
        container;
        modelId:
        viewType:
        title,
        modelWidget
         */
        initProps: function ($super, cfg) {
            //方便初始化
            this.$valueContainer ={};

            this.viewSet.modelId = cfg.modelId;
            this.modelWidgets = {};
            this.modelWidgets[ cfg.modelId] =  cfg.modelWidget;
            this.set(cfg.data);
            this.viewSet.type =  cfg.viewType;
            this.trClass=cfg.trClass;
            $super(cfg);
            //model
        },
        //视图结构生成
        build: function () {
            var self = this;
            //绘制标题域
            var $ele = $("<tr class='relation_props_tr '/>")
            $ele.addClass( this.trClass);
            var $nameTd = $("<td class='td_normal_title' width='15%' />");
            $nameTd.html(self.config.title);

            //绘制输入域
            var $valTd = $("<td width='85%' />");
            var $valDiv = $(" <div id='_xform_" + self.key + "Line'/>");
            //绘制输入域表格
            var $valTable = $("<table class=' tb_simple vieSetTable' width='100%'/>");
            //是否
            var $selectTr = $("<tr/>");

            //视图选择
            var $viewTd = self.drawView();
            $viewTd.attr("colspan", 2);
            $viewTd.addClass("viewSet_display");
            $selectTr.append($viewTd);

            //入参行
            var $incTr = $("<tr class='viewSet_display'/>");
            var $incTd = $("<td colspan='3'/>");
            $incTd.append(self.drawViewInc());
            $incTr.append($incTd);

            $valTable.append($selectTr);
            $valTable.append($incTr);

            $valDiv.append($valTable);
            //初始化数据
            $valTd.append($valDiv);

            $ele.append($nameTd);
            $ele.append($valTd);

            this.config.mainContainer.append($ele);
            this.$valueContainer = this.config.mainContainer.find("#" + self.key + "Table");
            //操作域事件绑定
            $ele.find(".relation_addLine").on("click", function () {
                var wgt = self.getGenerator()
                wgt.draw(self.$valueContainer);
            });
            self.element = $ele;
            return self.element
        },
        getTargetData: function () {
            var data = {
                "data": this.widgets.passive
            };
            return data;
        },
        getModelData: function () {
            var modelData = {
                "modelName": this.element.find(".viewSetModel_name").html(),
                "modelId": this.viewSet.modelId
            };

            return modelData;
        },
        setModelData: function (modelData) {
           // console.warn("disable setModelData")
        },
        getViewType: function () {
            return this.viewSet.type;
        },
        setViewType: function (val) {
         //   console.warn("disable setModelData");
        },
        getKeyData: function ($super) {
            $super();
            var data  = this.get();
            this.set(data);
            return data;
        }

    });

    exports.ViewPropsPortlet = ViewPropsPortlet;

})
;
