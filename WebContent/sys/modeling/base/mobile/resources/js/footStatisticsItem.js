/**
 * 显示项查询条件生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require('lui/topic');
    var dialog = require('lui/dialog');

    var ITEM_HTML_TEMP=
        "<tr class=\"displayCssSet\">\n" +
        "      <td class=\"displayCss\">\n" +
		"            <table class=\"footStatisticsTable tb_simple model-edit-view-oper-content-table\" width=\"100%\">" +
		"               <tr class=\"footStatisticsItemTr\" data-lui-cid=\"lui-id-63\">" +
		"           	  <td>" +
        "                   <div class=\"model-edit-view-oper\" onclick=\"switchSelectPosition(this,'right')\">\n" +
        "                       <div class=\"model-edit-view-oper-head\">\n" +
        "                       <div class=\"model-edit-view-oper-head-title\">\n" +
        "                          <div onclick=\"changeToOpenOrClose(this)\">\n" +
        "                               <i class=\"open\"></i>\n" +
        "                          </div>\n" +
        "                          <span>样式</span>\n" +
        "                       </div>\n" +
        "                       <div class=\"model-edit-view-oper-head-item\" style=\"padding-top:0px;\">\n" +
        "                           <div class=\"del\"\n >\n" +
        "                              <i></i>\n" +
        "                           </div>\n" +
        "                       </div>\n" +
        "                   </div>\n" +
        "                   <div class=\"model-edit-view-oper-content listview-content\">\n" +
        "                       <div class='model-edit-view-oper-content-item first-item last-item'>\n" +
        "                           <div class=\"display-whereType\">\n" +
        "                             <div class='display-whereType-title'>\n" +
        "                                   <span>统计规则</span>\n" +
        "                 					<span class=\"displayWhereType\">(前台可切换已选的规则)</span>\n" +
        "                 			  </div>\n" +
        "                 		  	  <div class='footStatistics-content display-whereType-content'>\n" +
        "                 		  	        <div class='footStatistics-content-type-item' statisticsType='1'>求和</div>" +
        "                 		  	        <div class='footStatistics-content-type-item' statisticsType='2'>平均值</div>" +
        "                 		  	        <div class='footStatistics-content-type-item' statisticsType='3'>最小值</div>" +
        "                 		  	        <div class='footStatistics-content-type-item footStatistics-content-type-last-item' statisticsType='4'>最大值</div>" +
        "                 		  	  </div>\n" +
        "               	 	    </div>\n" +
        "              		    </div>\n" +
        "              	    </div>\n" +
        "                 </div>\n" +
		"               </td>" +
		"             </tr>" +
		"          </table>"
        "      </td>" +
        "</tr>";
    
    var FootStatisticsItem = base.Component.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.channel = cfg.channel;
            this.index = cfg.index;
            this.field = cfg.field;
            this.text = cfg.text;
            this.statisticsType = cfg.statisticsType;
            this.childrenTemp = cfg.childrenTemp;
            this.parent = cfg.parent;
            this.valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.draw(this.index,this.text,this.statisticsType);
            
        },
	    draw:function (index,text,statisticsType) {
			var $tr = $(ITEM_HTML_TEMP);
			var self = this;
            this.element = $tr;
            this.element.find(".model-edit-view-oper").attr("data-lui-position","fdFootStatistics-"+index);
            this.element.find(".model-edit-view-oper-head-title").find("span").text(text);
            this.element.find(".model-edit-view-oper-head-title").find("span").attr("title",text);
            if(statisticsType){
                var statisticsTypeSplit = statisticsType.split(";")
                this.element.find(".footStatistics-content-type-item").each(function () {
                    f:for(var i=0;i<statisticsTypeSplit.length;i++){
                        if($(this).attr("statisticsType") == statisticsTypeSplit[i]){
                            $(this).addClass("active");
                            break f;
                        }
                    }
                })

            }
            this.element.find(".del").on("click",function(){
                dialog.confirm("确认删除“"+text+"”?",function(value){
                    if(value == true){
                        self.destroy($tr);
                        topic.publish("preview.refresh", {"key": self.channel});
                    }
                })
            })
            this.element.find(".footStatistics-content-type-item").on("click",function (){
                if($(this).hasClass("active")){
                    $(this).removeClass("active");
                }else {
                    $(this).addClass("active");
                }
                topic.publish("preview.refresh", {"key": self.channel});
            })
            this.parent.operationEle.after(this.element);
		},
        updateIndex: function (index) {
            this.index = index;
            this.element.find(".model-edit-view-oper").attr("data-lui-position","fdFootStatistics-"+index);
        },
        destroy: function ($tr) {
            this.parent.deleteWgt(this.field);
            $tr.remove();
        },
        getKeyData:function(){
            var self = this;
            var data = {};
            data.field = self.field;
            data.text = self.text;
            data.index = self.index;
            var statisticsType = '';
            this.element.find(".footStatistics-content-type-item").each(function () {
                if($(this).hasClass("active")){
                    statisticsType = statisticsType + $(this).attr("statisticsType")+';';
                }
            })
            data.statisticsType = statisticsType;
            return data;
        }
    });

    exports.FootStatisticsItem = FootStatisticsItem;
});
