/*
该组件继承数据视图组件DataView，既是摘要视图组件，又是列表组件
包含数据视图的行为，也包含新增的列表的行为
 */
define(function(require, exports, module) {
    require('theme!dataview');
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var render = require('lui/view/render');
    var topic = require("lui/topic");
    require('lui/jquery-ui');

    var SimpleSummaryDataView = base.DataView.extend({

        initProps : function($super, cfg) {
            if(cfg.button && cfg.button.click){
                this.button = cfg.button;
            }
            if(cfg.showMore == true || cfg.showMore == false){
                this.showMore = cfg.showMore;
            }else{
                this.showMore = true;
            }
            $super(cfg);
        },

        startup : function($super){
            if(this.isStartup){
                return;
            }
            //默认渲染处理，render
            //renderTemplate主要来自子类组件的默认实现覆盖
            if(!this.render){
                this.setRender(new render.Template({
                    src : require.resolve('./tmpl/summaryDataView.jsp#'),
                    parent : this
                }));
                this.render.startup();
                this.children.push(this.render);
            }
            $(this.element).addClass("lui_summary_dataview_container");
            $super();
            this.isStartup = true;
        },

        reload:function(isTrigger){
            // if(isTrigger){
            //     topic.publish("lui.summary.dataview.reload",{
            //         eventSrc:this
            //     })
            // }
            if(this.source){
                this.source.get();
            }
        },

        doRender:function($super,html){
            var _self = this;
            if(!html){
                this.element.html("");
            }
            $super(html);
            if(this.showMore){
                this.styleSetting();
            }
            this.bindEvent();
            //样式设置在不同的浏览器渲染流程好像不一致，导致有些项没有正确的显示或者隐藏，因此增加一个延迟设置的动作
            setTimeout(function(){
                var minTop = null;
                $(".lui_summary_item",_self.element).each(function(index){
                    if($(this).attr("class").indexOf("lui_summary_item_more") > -1){
                        return;
                    }
                    var top = $(this).offset().top;
                    if(index == 0){
                        minTop = top;
                    }else {
                        if (minTop && minTop < top) {
                            $(this).addClass("lui_summary_item_more");
                            $(".lui_more_btn",_self.element).show();
                        }
                    }
                })
            },300);
        },

        styleSetting:function(){
            var minTop = null;
            var line = 0;
            $(".lui_summary_item",this.element).each(function(index){
                var top = $(this).offset().top;
                if(index == 0){
                    minTop = top;
                    line = 1;
                }else {
                    if (minTop && minTop < top) {
                        $(this).addClass("lui_summary_item_more");
                        line = 2;
                    }
                }
            })
            //如果没有第二行，则隐藏更多的按钮
            if(line < 2){
                $(".lui_more_btn",this.element).hide();
            }
        },

        bindEvent:function(){//绑定事件
            var _self = this;
            //更多按钮
            $(".lui_more_btn",this.element).on("click",function(){
                if($(this).hasClass("showMore")){
                    _self.showMore = false;
                }else{
                    _self.showMore = true;
                }
                $(this).toggleClass("showMore");
                $(".lui_summary_list",_self.element).toggleClass("lui_summary_list_more");
                $(".lui_summary_item_more",_self.element).toggle();
            });
            if(this.showMore){
                $(".lui_more_btn",this.element).click();
            }
            if(this.button){
                //悬停效果
                $(".lui_summary_item",this.element).mouseenter(function(){
                    $(".lui_summary_item_btn",this).show();
                });
                $(".lui_summary_item",this.element).mouseleave(function(){
                    $(".lui_summary_item_btn",this).hide();
                });
                //点击按钮
                if(this.button.className){
                    $(".lui_summary_item_btn_custom",this.element).addClass(this.button.className);
                }
                $(".lui_summary_item_btn",this.element).click(this,this.button.click);
            }
            // topic.subscribe("lui.summary.dataview.reload",function(params){
            //     var eventSrc = params.eventSrc;
            //     if(eventSrc != _self){
            //         //false，不再触发发送事件，避免形成闭环
            //         _self.reload(false);
            //     }
            // })
        }
    });

    var SortableSummaryDataView = SimpleSummaryDataView.extend({

        initProps:function($super, cfg){
            if(cfg.sortable){
                this.sortable = cfg.sortable;
            }
            $super(cfg);
        },

        doSort:function(target,sortable){
            var option = {items:"li"};// 拖拽的元素
            if(sortable.stopCallback){
                option.stop = sortable.stopCallback;//拖动结束之后
            }
            //保证juquery.ui已经引入
            var num = window.setInterval(function(){
                if(target.sortable){
                    window.clearInterval(num);
                    target.sortable(option);
                    return;
                }
            },200);
        },

        doRender:function($super,html){
            $super(html);
            if(this.sortable){
                //拖拽排序
                this.doSort($(".lui_summary_dataview_content",this.element),this.sortable);
            }
        }
    });

    exports.SimpleSummaryDataView = SimpleSummaryDataView;
    exports.SortableSummaryDataView = SortableSummaryDataView;
});