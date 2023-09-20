/**
 * 显示项样式设置生成器
 */
define(function(require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require('lui/topic');
    var displayCssSetBoard = require('sys/modeling/base/listview/config/js/displayCssSetBoard'),
        calendarDisplayCssSetRender = require('sys/modeling/base/views/business/res/calendar/calendarDisplayCssSetRender');

    var DisplayCssSetContainer = base.Component.extend({
        initProps : function($super, cfg) {
            $super(cfg);
            this.container = cfg.container || null;
            this.type = cfg.type || null;
            this.showMode = cfg.showMode || null;
            this.build();

        },

        build : function() {
          if(this.type === "board"){
              this.displayCssSetInstance = new displayCssSetBoard.DisplayCssSetBoard({container:this.container});
          }else if (this.type === "calendar" ){
              this.calendarDisplayCssSetRender = new calendarDisplayCssSetRender.CalendarDisplayCssSetRender({container : this.container,showMode:this.showMode});
          }
        },

        getKeyData : function() {
            // 显示项样式对象
            var keyData = {};
            if(this.displayCssSetInstance && typeof this.displayCssSetInstance.getKeyData === "function"){
                keyData = this.displayCssSetInstance.getKeyData();
            }
            if(this.calendarDisplayCssSetRender && typeof this.calendarDisplayCssSetRender.getKeyData === "function"){
                keyData = this.calendarDisplayCssSetRender.getKeyData();
            }
            return keyData;
        }
    });

    exports.DisplayCssSetContainer = DisplayCssSetContainer;
});
