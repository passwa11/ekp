/**
 * 移动列表视图的排序行组件
 */
define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        base = require('lui/base'),
        topic = require('lui/topic');

    var HeadGenerator = base.Component.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.text = cfg.text || "";
            this.status = cfg.status || 1; //1：展开, 0: 关闭,默认展开
            this.channel = cfg.channel;
            this.type = cfg.type; //order: 排序, operate: 操作
            this.index = 0;
        },

        draw : function($super, cfg){
            $super(cfg);
            var self = this;
            this.head = $("<div class='model-edit-view-oper-head'></div>");
            this.content.append(this.head);
            this.headTitle = $("<div class='model-edit-view-oper-head-title'></div>");
            this.head.append(this.headTitle);
            this.openIcon = $("<div><i class='open'></i></div>");
            this.headTitle.append(this.openIcon);
            this.openIcon.click(function(){
                self.changeToOpenOrClose(this);
            });
            this.textEle = $("<span>" + this.text + "</span>");
            this.headTitle.append(this.textEle);
            this.headItem = $("<div class='model-edit-view-oper-head-item'></div>");
            this.head.append(this.headItem);
            this.delIcon = $("<div class='del'><i></i></div>");
            this.headItem.append(this.delIcon);
            this.delIcon.click(function(){
                var srcElement = this;
                // self.updateItemAttr(0,null,srcElement);
                self.delItem(srcElement);
                // self.updateItemAttr();
            });
            this.headItem.append("<div class='sortableIcon' ><i></i></div>");
            //只有一个不显示排序跟删除图标
            if(!this.isShowIcon()) {
                $(this.container).find(".sortableIcon").addClass("hide");
            } else {
                $(this.container).find(".sortableIcon").removeClass("hide");
            }
            this.postscript();
        },

        isShowIcon: function() {
           return  this.container.find(".item").length > 1
        },

        changeToOpenOrClose : function(obj){
            var $parent = $(obj).parents(".model-edit-view-oper").eq(0);
            if($parent.find(".model-edit-view-oper-content.close")[0]){
                this.open(obj, $parent);
            }else{
                this.close(obj, $parent);
            }
        },

        //关闭状态 - 打开状态
        open : function(obj, $parent) {
            this.status = 1;
            $(obj).find("i").removeClass("close");
            $parent.find(".model-edit-view-oper-content").removeClass("close");
        },

        //开始状态 - 关闭状态
        close : function(obj, $parent) {
            this.status = 0;
            $(obj).find("i").addClass("close");
            $parent.find(".model-edit-view-oper-content").addClass("close");
        },

        updateItemAttr : function(direct,type,thisObj) {
            //更新角标
            var index = this.head.find(".title-index").text();
            this.head.find(".title-index").text(parseInt(index)+1);
            //刷新预览
            topic.publish("preview.refresh", {key: this.channel});
        },

        delItem : function (dom) {
            var $item = $(dom).closest(".item");
            var curIndex = $item.attr("index");
            var luiId = $item.parents("[data-lui-cid]").eq(0).attr("data-lui-cid");
            var kClass = LUI(luiId);
            if (this.type == 'order') {
                var orderCollection = kClass.orderCollection;
                var wgt = orderCollection[curIndex];
            } else if (this.type == 'operate') {
                var operateCollection = kClass.operateCollection;
                var wgt = operateCollection[curIndex];
            } else if (this.type == 'statistics') {
                var statisticsCollection = kClass.statisticsCollection;
                var wgt = statisticsCollection[curIndex];
            }else if (this.type == 'groupCustom') {
                var groupCustomCollection = kClass.groupCustomCollection;
                var wgt = groupCustomCollection[curIndex];
            }
            $item.remove();
            topic.channel(this.channel).publish(this.type + ".delete", {"wgt": wgt});
            topic.channel(this.channel).unsubscribe("field.change");
            wgt.destroy();
            //刷新预览
            if (this.isShowIcon()){
                $(this.container).find(".sortableIcon").removeClass("hide");
            } else {
                $(this.container).find(".sortableIcon").addClass("hide");
            }
            topic.publish("preview.refresh",{key: this.channel});
            return;
        },

        setText : function(text) {
            this.textEle.text(text);
        },

        postscript : function() {
            //选择框切换数据后事件
            var self = this;
            topic.channel(this.channel).subscribe("field.change", function (data) {
                if (data.wgt.parent === self) {
                    self.setText(data.text);
                    //刷新预览
                    topic.publish("preview.refresh", {key: self.channel});
                }
            });
        }

    })

    module.exports = HeadGenerator;

})